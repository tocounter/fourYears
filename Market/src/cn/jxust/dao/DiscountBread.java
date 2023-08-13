package cn.jxust.dao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


public class DiscountBread extends Bread implements DiscountInterface,ActionListener{

    private double discount;
    double totalPayment,discountPayment;
    public DiscountBread(String name, int price, int count, String desc, Date produceDate,Date valiDate,double discount) {
        super(name, price, count, desc, produceDate,valiDate);
        this.discount=discount;
    }
    @Override
    public double getPrice() {
        return super.getPrice();
    }
    @Override
    public double getDiscountPrice(int amount, double discount) {
        double rawPrice=super.getShopPrice(amount);
        Date now=new Date();
        if(now.after(this.getValidDate()))
            return 0;
        int validDay=(int)((this.getProduceDate().getTime()-now.getTime()
                /24*60*60*1000));
        if(validDay<=1)
            return rawPrice *discount;
        return rawPrice;
    }
    @Override
    public String detail() throws OutofDateException{
        StringBuffer buffer=new StringBuffer();
        buffer.append(super.detail());
        buffer.append("\n");
        buffer.append(printDiscountMessage(this.discount));
        return buffer.toString();
    }
    public double getShopPrice(int amount) {
        return (super.getShopPrice(amount)*discount);
    }
    @Override
    public String printDiscountMessage(double discount) {
        return DiscountInterface.super.printDiscountMessage(discount);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
