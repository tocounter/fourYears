package cn.jxust.market;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hsj_java";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456";

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("服务器已启动，等待客户端连接...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("客户端已连接");

                Thread thread = new Thread(new ClientHandler(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {

            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                String action = (String) ois.readObject();

                if (action.equals("register")) {
                    String username = (String) ois.readObject();
                    String password = (String) ois.readObject();

                    boolean registerSuccess = registerUser(username, password);

                    oos.writeObject(registerSuccess);
                } else if (action.equals("login")) {
                    String username = (String) ois.readObject();
                    String password = (String) ois.readObject();

                    boolean loginSuccess = loginUser(username, password);
                    oos.writeObject(loginSuccess);
                }

                ois.close();
                oos.close();
                socket.close();
            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

        private boolean registerUser(String username, String password) throws SQLException {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                String checkSql = "SELECT * FROM users WHERE username = ?";
                stmt = conn.prepareStatement(checkSql);
                stmt.setString(1, username);
                ResultSet resultSet = stmt.executeQuery();

                if (resultSet.next()) {
                    return false;
                }

                String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
                stmt = conn.prepareStatement(insertSql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();

                return true;
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
        }

        private boolean loginUser(String username, String password) throws SQLException {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet resultSet = stmt.executeQuery();

                return resultSet.next();
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
        }
    }
}