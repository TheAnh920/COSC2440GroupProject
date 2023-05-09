package vn.edu.rmit.groupprj.Coupons;

public abstract class Coupon {
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

    public abstract String getCouponType();
}
