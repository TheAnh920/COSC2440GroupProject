package vn.edu.rmit.groupprj.Coupons;

public class Coupon {
    private String couponName;
    private String couponDesc;

    public String getCouponName() {
        return couponName;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public Coupon(String couponName, String couponDesc) {
        this.couponName = couponName;
        this.couponDesc = couponDesc;
    }


}
