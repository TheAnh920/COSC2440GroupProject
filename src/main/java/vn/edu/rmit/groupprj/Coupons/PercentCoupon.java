package vn.edu.rmit.groupprj.Coupons;

public class PercentCoupon extends Coupon{
    private int percentOff;

    public int getPercentOff() {
        return percentOff;
    }

    public PercentCoupon(String couponName, int percentOff) {
        super(couponName);
        this.percentOff = percentOff;
    }

    public String getCouponType(){
        return "PERCENT";
    }

    
}
