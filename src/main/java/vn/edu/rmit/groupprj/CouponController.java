package vn.edu.rmit.groupprj;

import vn.edu.rmit.groupprj.Coupons.Coupon;
import vn.edu.rmit.groupprj.Coupons.PercentCoupon;
import vn.edu.rmit.groupprj.Coupons.PriceCoupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CouponController {
    static Map<String, ArrayList<Coupon>> CouponCata = new HashMap<>();
    private static boolean couponAdded;
    static ArrayList<Coupon> AvailableCoupons = new ArrayList<>();

    public static boolean isCouponAdded() {
        return couponAdded;
    }

    public static void setCouponAdded(boolean couponAdded) {
        CouponController.couponAdded = couponAdded;
    }

    public static ArrayList<Coupon> allAvailableCoupon(int activeCart) {
        int count = 0;
        AvailableCoupons.clear();
        for (Map.Entry<String, Integer> pairEntry : ShoppingCart.cartList.get(activeCart).cart.entrySet()) {
            if (CouponCata.containsKey(pairEntry.getKey())) {
                for (int i = 0; i < CouponCata.get(pairEntry.getKey()).size(); i++) {
                    count++;
                    System.out.println(count + ". " + CouponCata.get(pairEntry.getKey()).get(i).getCouponDesc());
                    AvailableCoupons.add(CouponCata.get(pairEntry.getKey()).get(i));
                }
            }
        }
        return AvailableCoupons;
    }


    // public static Double applyCoupon(int coupo) {
    //     double amountOff = 0;
    //     AvailableCoupons.get(coupo);
    //     if (AvailableCoupons.get(coupo).getCouponType() == "PERCENT"){
    //         amountOff = ((PercentCoupon) AvailableCoupons.get(coupo)).getPercentOff() / 100 * Product.catalogue.get(AvailableCoupons.get(coupo).getCouponName()).getpPrice();
    //     } else if (AvailableCoupons.get(coupo).getCouponType() == "PRICE") {
    //         amountOff = ((PriceCoupon) AvailableCoupons.get(coupo)).getPriceOff();
    //     } else {
    //         System.out.println("How?");
    //     }
    //     setCouponAdded(true);
    //     return amountOff;
    // }

    public static String applyCoupon(int coupo) {
        if (AvailableCoupons.get(coupo).getCouponType() == "PERCENT") {
            setCouponAdded(true);
            return "PERCENT";
        } else if (AvailableCoupons.get(coupo).getCouponType() == "PRICE") {
            setCouponAdded(true);
            return "PRICE";
        } else {
            return "No such coupon exists";
        }
    }

    public static Double calcAmountOff(String couponType, int coupo) {
        double amountOff = 0;
        if (couponType == "PERCENT") {
            amountOff = ((PercentCoupon) (AvailableCoupons.get(coupo))).getPercentOff() * Product.catalogue.get(AvailableCoupons.get(coupo).getCouponName()).getpPrice() / 100;
        } else if (couponType == "PRICE") {
            amountOff = ((PriceCoupon) AvailableCoupons.get(coupo)).getPriceOff();
        } else {
            amountOff = 0;
        }
        return amountOff;
    }


    public static void generateCoupons() {
        CouponCata.put(Product.catalogue.get("towel").getpName(), new ArrayList<>());
        CouponCata.get("towel").add(new PercentCoupon("towel", "30% off for your towel purchases' total", 30));
        CouponCata.get("towel").add(new PriceCoupon("towel", "5 off for your towel purchases' total", 5));


        CouponCata.put(Product.catalogue.get("album").getpName(), new ArrayList<>());
        CouponCata.get("album").add(new PercentCoupon("album", "30% off for your album purchases' total", 30));
        CouponCata.get("album").add(new PriceCoupon("album", "5 off for your album purchases' total", 5));
    }
}

