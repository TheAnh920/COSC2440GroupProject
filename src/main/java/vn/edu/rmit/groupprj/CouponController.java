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

    public static String applyCoupon(int coupo) {
        if (AvailableCoupons.get(coupo).getCouponType().equals("PERCENT")) {
            setCouponAdded(true);
            return "PERCENT";
        } else if (AvailableCoupons.get(coupo).getCouponType().equals("PRICE")) {
            setCouponAdded(true);
            return "PRICE";
        } else {
            return "No such coupon exists";
        }
    }

    public static Double calcAmountOff(String couponType, int coupo) {
        double amountOff;
        if (couponType.equals("PERCENT")) {
            amountOff = ((PercentCoupon) (AvailableCoupons.get(coupo))).getPercentOff() * Product.catalogue.get(AvailableCoupons.get(coupo).getCouponName()).getpPrice() / 100;
        } else if (couponType.equals("PRICE")) {
            amountOff = ((PriceCoupon) AvailableCoupons.get(coupo)).getPriceOff();
        } else {
            amountOff = 0;
        }
        return amountOff;
    }


    public static void generateCoupons() {
        CouponCata.put(Product.catalogue.get("Towel").getpName(), new ArrayList<>());
        CouponCata.get("Towel").add(new PercentCoupon("Towel", "30% off for your towel purchases' total", 30));
        CouponCata.get("Towel").add(new PriceCoupon("Towel", "5 off for your towel purchases' total", 5));


        CouponCata.put(Product.catalogue.get("Album").getpName(), new ArrayList<>());
        CouponCata.get("Album").add(new PercentCoupon("Album", "30% off for your album purchases' total", 30));
        CouponCata.get("Album").add(new PriceCoupon("Album", "5 off for your album purchases' total", 5));
    }
}

