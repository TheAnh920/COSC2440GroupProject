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

    public static boolean isCouponAdded() {
        return couponAdded;
    }

    public static void setCouponAdded(boolean couponAdded) {
        CouponController.couponAdded = couponAdded;
    }

    public static void allAvailableCoupon(int activeCart) {
        int count = 0;
        for (Map.Entry<String, Integer> pairEntry : ShoppingCart.cartList.get(activeCart).cart.entrySet()) {
            if (CouponCata.containsKey(pairEntry.getKey())) {
                for (int i = 0; i < CouponCata.get(pairEntry.getKey()).size(); i++) {
                    count++;
                    System.out.println(count + ". " + CouponCata.get(pairEntry.getKey()).get(i).getCouponDesc());
                }
            }


        }

    }


    public static void generateCoupons() {
        CouponCata.put(Product.catalogue.get("towel").getpName(), new ArrayList<>());
        CouponCata.get("towel").add(new PercentCoupon("towel", "30% off for your towel purchases' total", 30));
        CouponCata.get("towel").add(new PriceCoupon("towel", "5000 off for your towel purchases' total", 5000));


        CouponCata.put(Product.catalogue.get("album").getpName(), new ArrayList<>());
        CouponCata.get("album").add(new PercentCoupon("album", "30% off for your album purchases' total", 30));
        CouponCata.get("album").add(new PriceCoupon("album", "5000 off for your album purchases' total", 5000));
    }
}

