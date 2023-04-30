package vn.edu.rmit.indivproject.Coupons;

public class PriceCoupon extends Coupon{
    private double priceOff;

    public double getPriceOff() {
        return priceOff;
    }

    public void setPriceOff(double priceOff) {
        this.priceOff = priceOff;
    }

    public PriceCoupon(String couponName, double priceOff) {
        super(couponName);
        this.priceOff = priceOff;
    }

    public String getCouponType(){
        return "PRICE";
    }
}
