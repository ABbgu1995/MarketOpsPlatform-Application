package BL.Suppliers;

import BL.Suppliers.Discount;

import java.util.*;

public class TotalPaymentDiscount extends Discount {

    double totalPaymentValue;


    public TotalPaymentDiscount() {
        this.totalPaymentValue = 0;
        this.policyList = new HashMap<Double, Double>();

    }

    public double getPaymentPolicy(double totalPaymentValue){
        for (Map.Entry<Double, Double> entry : policyList.entrySet()) {
            if(entry.getValue() == totalPaymentValue)
                return entry.getValue();
        }
        return -1;
    }

    public void setTotalPaymentValue(double totalPaymentValue){
        this.totalPaymentValue=totalPaymentValue;
        calculatePriceAfterDiscount();
    }

    // use for also add
    public void updatePolicy(double totalPayment, double percentageDiscount) {
        policyList.put(totalPayment, percentageDiscount);

    }

    public void deletePolicyRule(double totalPayment){
        this.policyList.remove(totalPayment);
    }

    public void getPolicy() {
        for (Map.Entry<Double, Double> entry : policyList.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    // the function return the discount percentage the market granted
    protected void calculatePercentageDiscount() {
        this.discountPercentage = 0; //no total payment based discount offers - the total payment is lower than discount total payments agreed
        List<Map.Entry<Double, Double>> sortedPayments = new ArrayList<>(this.policyList.entrySet());

        // Sort the list based on the values
        sortedPayments.sort(Map.Entry.comparingByKey());
        for (int i = sortedPayments.size() - 1; i >= 0; i--) {
            if (sortedPayments.get(i).getKey() <= this.totalPaymentValue) {
                this.discountPercentage = sortedPayments.get(i).getValue();

            }
        }
    }

    protected void calculatePriceAfterDiscount(){
        calculatePercentageDiscount();
        this.priceAfterDiscount=this.totalPaymentValue*((100-this.discountPercentage)/100);
    }
}
