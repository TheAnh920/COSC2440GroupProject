package vn.edu.rmit.groupprj.Coupons;

public class PriceCoupon extends Coupon {
    private double priceOff;

    public double getPriceOff() {
        return priceOff;
    }

    public void setPriceOff(double priceOff) {
        this.priceOff = priceOff;
    }

    public PriceCoupon(String couponName, String couponDesc, double priceOff) {
        super(couponName, couponDesc);
        this.priceOff = priceOff;
    }

    @Override
    public String getCouponType() {
        return "PRICE";
    }
}
