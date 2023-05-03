package vn.edu.rmit.groupprj.Coupons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.edu.rmit.groupprj.Product;

public class CouponController {
    static Map<String, ArrayList<Coupon>> CouponCata = new HashMap<>();
    static ArrayList<Coupon> CouponList = new ArrayList<>();
    private static boolean couponAdded;

    public static boolean isCouponAdded() {
        return couponAdded;
    }

    public static void setCouponAdded(boolean couponAdded) {
        CouponController.couponAdded = couponAdded;
    }

    public void allAvailableCoupon(){
        
        // for (Map.Entry<String, Integer> pairEntry: ShoppingCart.cart.entrySet()) {
        //     if (CouponCata.containsKey(pairEntry.getKey())){
        //         for (int i = 0; i < CouponCata.get(pairEntry.getKey()).size(); i++){
        //             System.out.println(CouponCata.get(pairEntry.getKey()).get(i));
        //         }
        //     }
            
        // }


    }

    public static void generateCoupons(){
        CouponList.add(new PercentCoupon("towel", "30% off for your towel purchases' total", 30));
        CouponList.add(new PriceCoupon("towel", "5000 off for your towel purchases' total", 5000));
        CouponCata.put(Product.catalogue.get("towel").getpName(), CouponList);
        CouponList.clear();

        CouponList.add(new PercentCoupon("album", "30% off for your album purchases' total", 30));
        CouponList.add(new PriceCoupon("album", "5000 off for your album purchases' total", 5000));
        CouponCata.put(Product.catalogue.get("album").getpName(), CouponList);
        CouponList.clear();
    }

}
