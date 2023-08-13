package cn.jxust.market;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import cn.jxust.dao.OutofDateException;

@SuppressWarnings("serial")
public class Mainpage extends JFrame implements ActionListener{
    String userName;
    JMenuBar menuBar;
    JMenu menu1,menu2,menu3;
    JMenuItem item11,item12,item21,item22,item23,item31;
    public static Container con=null;
    public Mainpage(String name) {
        super("理工超市管理系统");
        this.userName=name;
        initmenu();
        con=getContentPane();
        con.add(new MyPanel2(userName));
        setBounds(100, 100, 500, 300);
        setBackground(Color.gray);
        setVisible(true);
    }

    private void initmenu() {
        menuBar=new JMenuBar();
        menu1=new JMenu("商品");
        menu2=new JMenu("购物车");
        menu3=new JMenu("退出");

        item11=new JMenuItem("商品列表");
        item12=new JMenuItem("商品详情");
        item21=new JMenuItem("添加购物车");
        item22=new JMenuItem("查询购物车");
        item23=new JMenuItem("商品结算");
        item31=new JMenuItem("系统退出");

        item12.addActionListener(this);
        item21.addActionListener(this);
        item22.addActionListener(this);
        item31.addActionListener(this);

        menu1.add(item11);menu1.add(item12);
        menu2.add(item21);menu2.add(item22);menu2.add(item23);
        menu3.add(item31);
        menuBar.add(menu1);menuBar.add(menu2);menuBar.add(menu3);
        setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==item31) {
            System.exit(0);
        }
        if(e.getSource()==item12) {
            int row=MyPanel2.table1.getSelectedRow();
            if(row==-1) {
                JOptionPane.showMessageDialog(null, "先选择商品","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            String str="";
            try {
                str=MyPanel2.goodsList.get(row).detail();
            } catch (OutofDateException e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, str,"详情",JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
