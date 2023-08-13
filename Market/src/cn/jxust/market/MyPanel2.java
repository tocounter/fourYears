package cn.jxust.market;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cn.jxust.dao.Bread;
import cn.jxust.dao.DiscountBread;
import cn.jxust.dao.DiscountMobile;
import cn.jxust.dao.Goods;
import cn.jxust.dao.OutofDateException;


@SuppressWarnings("serial")
public class MyPanel2 extends JPanel implements ActionListener{
    public static JTable table1;
    Object data[][];
    Object title[];
    JPanel panel1;

    JButton btnShowInfo,btnAddCart,btnShowCart,btnExit;
    public static List<Shopitem>shopItemList=new ArrayList<Shopitem>();
    public static List<Goods> goodsList;
    String userName;

    public MyPanel2(String userName) {
        this.userName=userName;
        initData();
        initTable();
        setLayout(new BorderLayout());
        JLabel label1=new JLabel("欢迎 "+userName+" 来到理工超市管理系统  ",JLabel.CENTER);
        label1.setFont(new Font("楷书",Font.BOLD,30));
        label1.setForeground(Color.blue);
        add(label1,BorderLayout.NORTH);
        add(new JScrollPane(table1),BorderLayout.CENTER);
        JPanel panel0=new JPanel();
        btnShowInfo=new JButton("商品详情");
        btnAddCart=new JButton("添加购物车");
        btnShowCart=new JButton("显示购物车");
        btnExit=new JButton("系统退出");
        btnShowInfo.addActionListener(this);
        btnAddCart.addActionListener(this);
        btnShowCart.addActionListener(this);
        btnExit.addActionListener(this);
        panel0.add(btnShowInfo);
        panel0.add(btnAddCart);
        panel0.add(btnShowCart);
        panel0.add(btnExit);
        add(panel0,BorderLayout.SOUTH);
    }
    private void initData() {
        goodsList=new ArrayList<Goods>();
        goodsList.add(new Mobile("OPPOR11", 899, 10, "智能手机","OPPO","R7","Android",4));
        goodsList.add(new DiscountMobile("小米note8", 999, 4, "2台打折","小米","R8","Android",6,0.9));
        goodsList.add(new Goods("华为P30", 2118, 6 , "华为5G手机"));
        goodsList.add(new Goods( "华为Mate8", 5118, 5 , "华为新的5G手机"));

        Date proDate=new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(proDate);
        c.add(Calendar.DAY_OF_MONTH,-3);
        proDate=c.getTime();
        Date validDate=new Date();
        Calendar c2=Calendar.getInstance();
        c2.setTime(validDate);
        c2.add(Calendar.DAY_OF_MONTH,-1);
        validDate=c2.getTime();
        goodsList.add(new Bread( "菲尔雪面包", 20, 15 , "菲尔雪面包好贵",proDate,validDate));

        //打折面包
        Date proDate1=new Date();
        Calendar c1=Calendar.getInstance();
        c1.setTime(proDate1);
        c1.add(Calendar.DAY_OF_MONTH,-5);
        proDate1=c1.getTime();
        Date validDate1=new Date();
        Calendar c11=Calendar.getInstance();
        c11.setTime(validDate1);
        c11.add(Calendar.DAY_OF_MONTH,1);
        validDate1=c11.getTime();
        goodsList.add(new DiscountBread( "甜甜圈", 20, 15, "甜甜圈很甜",proDate1,validDate1,0.75));
    }

    private void initTable() {
        title=new Object[]{"产品编号", "产品名称", "单价", "数量", "简介"};

        data=new Object[goodsList.size()][title.length];
        for(int i=0;i<goodsList.size();i++){
            data[i][0]=i+1;
            data[i][1]=goodsList.get(i).getName();
            data[i][2]=goodsList.get(i).getPrice();
            data[i][3]=goodsList.get(i).getCount();
            data[i][4]=goodsList.get(i).getDesc();
        }
        table1=new JTable(data,title);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnExit) {
            System.exit(0);
        }
        if(e.getSource()==btnShowInfo) {
            int row=table1.getSelectedRow();
            if(row==-1) {
                JOptionPane.showMessageDialog(null, "先选择商品","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            String str="";
            try {
                str=goodsList.get(row).detail();
            } catch (OutofDateException e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, str,"详情",JOptionPane.INFORMATION_MESSAGE);
        }
        if(e.getSource()==btnAddCart) {
            int row=table1.getSelectedRow();
            if(row==-1) {
                JOptionPane.showMessageDialog(null, "先选择商品","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            String str=(String)JOptionPane.showInputDialog(null,"请输入购买数量:\n","提示",
                    JOptionPane.PLAIN_MESSAGE,null,null,"1");
            int amount=0;
            try {
                amount=Integer.parseInt(str);
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, "输入数量不对,重新输入","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }

            shopItemList.add(new Shopitem(MyPanel2.goodsList.get(row),amount));
        }
        if(e.getSource()==btnShowCart) {
            Mainpage.con.removeAll();
            Mainpage.con.add(new MyshopCart(Mainpage.con,userName,shopItemList));
            Mainpage.con.validate();
        }

    }
    public static void main(String[] args) {
        new Mainpage("aaa");
    }
}
