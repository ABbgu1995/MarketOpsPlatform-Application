package BL.Suppliers;

import BL.Suppliers.Discount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalAmountDiscount extends Discount {
    private double totalAmountValue;



    public TotalAmountDiscount() {
        this.policyList = new HashMap<Double, Double>();

    }

    public void setTotalAmountValue(double totalAmountValue) {
        this.totalAmountValue = totalAmountValue;
        calculatePriceAfterDiscount();
    }

    // use for also add
    public void updatePolicy(double totalAmountValue, double percentageDiscount) {
        policyList.put(totalAmountValue, percentageDiscount);
    }

    public void deletePolicyRule(double totalPayment){
        this.policyList.remove(totalPayment);
    }

    public void getPolicy() {
        for (Map.Entry<Double, Double> entry : policyList.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    public double getAmountPolicy(int totalAmountValue){
        for (Map.Entry<Double, Double> entry : policyList.entrySet()) {
            if(entry.getKey() == totalAmountValue)
                return entry.getValue();
        }
        return -1;
    }

    // the function return the discount percentage the market granted
    protected void calculatePercentageDiscount() {

        this.discountPercentage = 0; //no total payment based discount offers - the total payment is lower than discount total payments agreed
        List<Map.Entry<Double, Double>> sortedAmounts = new ArrayList<>(this.policyList.entrySet());

        // Sort the list based on the values
        sortedAmounts.sort(Map.Entry.comparingByKey());
        for (int i = sortedAmounts.size() - 1; i >= 0; i--) {
            if (sortedAmounts.get(i).getKey() <=this.totalAmountValue) {
                this.discountPercentage = sortedAmounts.get(i).getValue();

            }
        }
    }

    protected void calculatePriceAfterDiscount(){
        calculatePercentageDiscount();
        this.priceAfterDiscount=this.totalAmountValue*((100-this.discountPercentage)/100);
    }

}
