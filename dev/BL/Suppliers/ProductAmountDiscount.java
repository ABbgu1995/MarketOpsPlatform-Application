package BL.Suppliers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductAmountDiscount extends Discount {
    private double totalRequiredProductAmount;

    private double unitPriceAfterDiscount;
    private Product product;



    public ProductAmountDiscount(Product product){
        this.totalRequiredProductAmount=0;
        this.product=product;
    }

    public void setTotalRequiredProductAmount(int totalRequiredProductAmount){
        this.totalRequiredProductAmount=totalRequiredProductAmount;
        calculatePercentageDiscount();
        calculatePriceAfterDiscount(); //not ideal but will explain the steps we're doing
        calculateUnitPriceAfterDiscount();
    }

    public Product getProduct() {
        return product;
    }

    public void updatePolicy(double amountOfProduct, double percentageDiscount) {
        this.policyList.put(amountOfProduct, percentageDiscount);
    }

    public void deletePolicyRule(int amountOfProduct){
        this.policyList.remove(amountOfProduct);
    }
    public void calculatePercentageDiscount() {
        List<Map.Entry<Double, Double>> sortedPayments = new ArrayList<>(this.policyList.entrySet());
        this.discountPercentage=0;
        // Sort the list based on the values
        sortedPayments.sort(Map.Entry.comparingByKey());
        for (int i = sortedPayments.size() - 1; i >= 0; i--) {
            if (sortedPayments.get(i).getKey() <= this.totalRequiredProductAmount) {
                this.discountPercentage = sortedPayments.get(i).getValue();
                break;
            }
        }
    }

    public void calculatePriceAfterDiscount(){
        this.priceAfterDiscount=this.product.getBasePrice()*((100-this.getDiscountPercentage())/100)*this.totalRequiredProductAmount;
    }


    public void calculateUnitPriceAfterDiscount(){
        this.unitPriceAfterDiscount=this.product.getBasePrice()*((100-this.getDiscountPercentage())/100);
    }

    public double getDiscountPercentage() {
        return super.getDiscountPercentage();
    }

    public double getUnitPriceAfterDiscount() {
        return this.unitPriceAfterDiscount;
    }
}


