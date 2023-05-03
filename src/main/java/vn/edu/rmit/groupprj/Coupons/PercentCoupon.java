package vn.edu.rmit.groupprj.Coupons;

public class PercentCoupon extends Coupon{
    private int percentOff;

    public int getPercentOff() {
        return percentOff;
    }

    public PercentCoupon(String couponName, String couponDesc, int percentOff) {
        super(couponName, couponDesc);
        this.percentOff = percentOff;
    }

    public String getCouponType(){
        return "PERCENT";
    }

    
}
