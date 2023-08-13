package cn.jxust.dao;

import cn.jxust.market.Mobile;

public class DiscountMobile extends Mobile implements DiscountInterface {
    double discount;
    double totalPayment,discountPayment;
    public DiscountMobile(String name, double price, int count, String desc, String brand, String cpu, String os,
                          int MemorySize,double discount) {
        super(name, price, count, desc, brand, cpu, os, MemorySize);
        this.discount=discount;
    }


    @Override
    public double getDiscountPrice(int amount, double discount) {
        totalPayment=super.getShopPrice(amount);
        if(amount>=2) {
            return totalPayment*discount;
        }
        return totalPayment;
    }
    @Override
    public double getShopPrice(int amount) {
        discountPayment=getDiscountPrice(amount, discount);
        return discountPayment;
    }

}
