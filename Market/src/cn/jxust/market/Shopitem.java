package cn.jxust.market;

import cn.jxust.dao.Goods;

public class Shopitem {
    private Goods goods;
    private int amount;
    public Shopitem() {

        super();
    }
    public Shopitem(Goods goods,int amount) {
        this.goods=goods;
        this.amount=amount;
    }
    public int getAmount() {

        return amount;
    }
    public Goods getGoods() {

        return goods;
    }

}
