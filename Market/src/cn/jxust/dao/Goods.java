package cn.jxust.dao;

public class Goods {
    private String name;
    private double price;
    private int count;
    private String desc;

    public Goods(String name,double price,int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Goods(String name,double price,int count,String desc) {
        this(name, price, count);
        this.desc=desc;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }
    public String getDesc() {
        return desc;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {

        return this.name+"\t"+this.price+"\t"+this.count;
    }

    public String detail() throws OutofDateException {
        String str="";
        str="商品名称：" + this.name+"\n"+
                "商品价格：" + this.price+"\n"+
                "商品库存：" + this.count+"\n"+
                "商品描述：" + this.desc;
        return str;
    }

    public double getShopPrice(int amount) {
        return amount*price;
    }

}
