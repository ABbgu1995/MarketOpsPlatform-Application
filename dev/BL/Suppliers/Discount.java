package BL.Suppliers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Discount {
    protected double discountPercentage;
    protected double priceAfterDiscount;

    protected Map<Double, Double> policyList;

    protected abstract void calculatePercentageDiscount();
    protected abstract void calculatePriceAfterDiscount();

    public Discount(){
        policyList = new HashMap<Double,Double>();
    }
    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public double getPriceAfterDiscount(){
        return this.priceAfterDiscount;
    }

    public Map<Double, Double> getPolicyList() {
        return policyList;
    }
}
