package cn.jxust.dao;

public interface DiscountInterface {
    public double getDiscountPrice(int amount,double discount);
    default String printDiscountMessage(double discount) {
        String discountMEssage;
        if(discount==1) {
            discountMEssage="不打折";
        }
        else {
            discountMEssage=discount*10+"折";
        }
        return "商品折扣率"+discountMEssage;
    }
}
