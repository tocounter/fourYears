package cn.jxust.market;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cn.jxust.dao.OrderStatics;

@SuppressWarnings("serial")
public class MyshopCart extends JPanel implements ActionListener{
    List<Shopitem>shopItemlist;
    JTable carttJTable;
    Object data[][];
    Object title[];
    double totalPay,payMoney;
    JButton btnBack,btnStatic,btnPay;
    String userName;
    Container con;
    public static boolean isPay;
    public MyshopCart(Container con,String userName,List<Shopitem> shopItemlist) {
        this.shopItemlist=shopItemlist;
        this.userName=userName;
        this.con=con;
        isPay=false;

        setLayout(new BorderLayout());
        JLabel label1=new JLabel(userName+"的购物车",JLabel.CENTER);
        label1.setFont(new Font("隶书",Font.BOLD,30));
        label1.setForeground(Color.blue);
        add(label1,BorderLayout.NORTH);

        title=new Object[]{"商品名称","商品单价","购买数量","金额"};
        data=new Object[shopItemlist.size()][title.length];
        for(int i=0;i<shopItemlist.size();i++){
            data[i][0]=shopItemlist.get(i).getGoods().getName();
            data[i][1]=shopItemlist.get(i).getGoods().getPrice();
            data[i][2]=shopItemlist.get(i).getAmount();
            double d=shopItemlist.get(i).getGoods().getShopPrice(shopItemlist.get(i).getAmount());
            data[i][3]=d;
            totalPay+=d;
        }
        carttJTable=new JTable(data,title);
        add(new JScrollPane(carttJTable), BorderLayout.CENTER);
        JPanel Panel0=new JPanel();
        JLabel labelamt=new JLabel("总金额："+totalPay+"元");
        btnBack=new JButton("返回");
        btnPay=new JButton("支付");
        btnStatic=new JButton("成交查询");
        btnBack.addActionListener(this);
        btnPay.addActionListener(this);
        btnStatic.addActionListener(this);
        Panel0.add(labelamt);
        Panel0.add(btnPay);
        Panel0.add(btnStatic);
        Panel0.add(btnBack);
        add(Panel0, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnBack) {
            Mainpage.con.removeAll();
            Mainpage.con.add(new MyPanel2(userName));
            Mainpage.con.validate();
            MyPanel2.shopItemList=new ArrayList<Shopitem>();
        }
        if(e.getSource()==btnPay) {
            String str=(String)JOptionPane.showInputDialog(null,"请输入金额:\n","输入付款金额",
                    JOptionPane.INFORMATION_MESSAGE,null,null,""+totalPay);
            payMoney=Double.parseDouble(str);
            if(payMoney<totalPay) {
                JOptionPane.showInputDialog(null,"金额不足","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                JOptionPane.showMessageDialog(null,"支付成功，找零："+(payMoney-totalPay),"支付成功",
                        JOptionPane.INFORMATION_MESSAGE);
                OrderStatics.add(new Date());
                isPay=true;
                shopItemlist.removeAll(shopItemlist);
            }
        }
        if(e.getSource()==btnStatic) {
            SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd");
            JOptionPane.showMessageDialog(null,"支付时间\t"+"\t支付订单数\n"+date.format(new Date())+"\t"+1,"成功订单显示",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
