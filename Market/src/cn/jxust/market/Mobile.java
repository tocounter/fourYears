package cn.jxust.market;

import cn.jxust.dao.Goods;
import cn.jxust.dao.OutofDateException;

public class Mobile extends Goods{
    private String brand;
    private String cpu;
    private String os;
    private int MemorySize;
    public Mobile(String name, double price, int count,String desc,String brand,String cpu,String os,int MemorySize) {
        super(name, price, count,desc);
        this.brand=brand;
        this.cpu=cpu;
        this.os=os;
        this.MemorySize=MemorySize;
    }
    @Override
    public String detail() throws OutofDateException {
        return super.detail()+"品牌："+brand+"\n操作系统："+os+"\nCPU："+cpu+"\n内存大小："+MemorySize+"G";
    }

}
