package cn.jxust.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class OrderStatics {
    private static Map<Date, Integer>orderNumByTime=new TreeMap<Date, Integer>();

    public static void add(Date date) {
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND, 0);
        Date key=c.getTime();

        int value=orderNumByTime.getOrDefault(key, 0)+1;
        orderNumByTime.put(key, value);
    }
    public static void print(){
        String format="yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        System.out.println("支付时间\t\t支付订单数");
        for (Date key:orderNumByTime.keySet()) {
            System.out.println(sdf.format(key)+"\t"+orderNumByTime.get(key));
        }
    }
    public static StringBuilder output() {
        StringBuilder sb=new StringBuilder();
        String format="yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        System.out.println("支付时间\t\t支付订单数\n");
        sb.append("    支付时间        支付订单数\n");
        for(Date key:orderNumByTime.keySet()) {
            sb.append(sdf.format(key)+"                "+orderNumByTime.get(key)+"\n");
            System.out.println(sdf.format(key)+"\t"+orderNumByTime.get(key));
        }
        return sb;
    }

}
