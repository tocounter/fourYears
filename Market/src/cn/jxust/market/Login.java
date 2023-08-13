package cn.jxust.market;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//@SuppressWarnings("serial")
public class Login extends JFrame {
    JLabel label1, label2, label3, label4, label5;
    JTextField field1, field4;
    JPasswordField field2, field3;
    JButton btnRegister, btnLogin;
    PicPanel picPanel;
    boolean isRegister = false;
    private static String userName = "";
    private static String pwd = "";

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hsj_java";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456";

    public Login(String title) {
        super(title);
        setBounds(500, 300, 300, 300);
        setBackground(Color.gray);
        init();
        setContentPane(picPanel);
        setVisible(true);
    }

    private void init() {
        picPanel = new PicPanel();
        picPanel.setLayout(null);
        label1 = new JLabel("用户名:");
        label2 = new JLabel("密    码:");
        label3 = new JLabel("再次输入:");
        label4 = new JLabel("验证码:");
        label5 = new JLabel("0000");
        field1 = new JTextField(20);
        field2 = new JPasswordField(20);
        field3 = new JPasswordField(20);
        field4 = new JTextField(10);
        btnRegister = new JButton("注册");
        btnLogin = new JButton("登录");

        label1.setBounds(60, 60, 80, 25);
        label2.setBounds(60, 100, 80, 25);
        label3.setBounds(60, 140, 80, 25);
        label4.setBounds(60, 180, 80, 25);
        label5.setBounds(190, 180, 60, 25);
        field1.setBounds(120, 60, 120, 25);
        field2.setBounds(120, 100, 120, 25);
        field3.setBounds(120, 140, 120, 25);
        field4.setBounds(120, 180, 60, 25);
        btnRegister.setBounds(100, 220, 70, 25);
        btnLogin.setBounds(190, 220, 70, 25);

        picPanel.add(label1);
        picPanel.add(field1);
        picPanel.add(label2);
        picPanel.add(field2);
        picPanel.add(label3);
        picPanel.add(field3);
        picPanel.add(label4);
        picPanel.add(field4);
        picPanel.add(label5);
        picPanel.add(btnRegister);
        picPanel.add(btnLogin);

        int checkedNumber = (int) (Math.random() * 9000 + 1000);
        label5.setText("" + checkedNumber);

        btnRegister.addActionListener(e -> register());

        btnLogin.addActionListener(e -> login());
    }

    public static void main(String[] args) {

        new Login("理工超市");
    }

    private void register() {
        String uName = field1.getText().trim();
        String uPwd = field2.getText().trim();
        String inputNumber = field4.getText().trim();
        String confirmPwd = field3.getText().trim();

        if (uName.isEmpty() || uPwd.isEmpty()) {
            JOptionPane.showMessageDialog(null, "用户名密码不能为空", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!confirmPwd.equals(uPwd)) {
            JOptionPane.showMessageDialog(null, "两次密码输入不同", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!inputNumber.equals(label5.getText().trim())) {
            JOptionPane.showMessageDialog(null, "验证码输入错误", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Thread registerThread = new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8888);

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject("register");
                oos.writeObject(uName);
                oos.writeObject(uPwd);

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                boolean registerSuccess = (boolean) ois.readObject(); // 接收注册结果

                oos.close();
                ois.close();
                socket.close();

                if (registerSuccess) {
                    saveToDatabase(uName, uPwd);
                    JOptionPane.showMessageDialog(null, "注册成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    isRegister = true;
                    field1.setText("");
                    field2.setText("");
                    field3.setText("");
                    field4.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "注册失败，请重新注册", "提示", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        registerThread.start();
    }

    private void saveToDatabase(String uName, String uPwd) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, uName);
            stmt.setString(2, uPwd);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void login() {
        String uName = field1.getText().trim();
        String uPwd = field2.getText().trim();

        Thread loginThread = new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8888);

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject("login");
                oos.writeObject(uName);
                oos.writeObject(uPwd);

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                boolean loginSuccess = (boolean) ois.readObject();

                oos.close();
                ois.close();
                socket.close();

                if (loginSuccess) {
                    JOptionPane.showMessageDialog(null, "登录成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
                    new Mainpage(uName);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "登录失败，请检查用户名和密码", "提示", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        loginThread.start();
    }
}
