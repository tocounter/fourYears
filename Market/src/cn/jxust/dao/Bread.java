package cn.jxust.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Bread extends Goods{
    private Date produceDate;
    private Date validDate;
    public Bread(String name,int price,int count,Date produceDate) {
        super(name, price, count);
        this.produceDate=produceDate;
    }
    public Bread(String name,int price,int count,String desc,Date produceDate,Date validDate) {
        super(name, price, count,desc);
        this.produceDate=produceDate;
        this.validDate=validDate;
    }
    public Date getProduceDate() {
        return produceDate;
    }
    public Date getValidDate() {
        return validDate;
    }
    @Override
    public String detail() throws OutofDateException{
        String str="";
        SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
        if(this.validDate.before(new Date())) {
            str="有效期至"+df.format(this.validDate)+"，商品已过期!";

        }else {
            str=super.detail()+"\n商品生产日期："+df.format(this.produceDate)+"\n商品有效期至："+df.format(this.validDate);
        }
        return str;
    }
}
