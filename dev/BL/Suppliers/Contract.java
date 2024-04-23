package BL.Suppliers;

import java.util.ArrayList;
import java.util.List;

public class Contract {

    private boolean constantDaysShipment;
    private PaymentTypes paymentTerms;
    private List<Days> shippingDays;
    private BL.Suppliers.KtavKamot KtavKamot;

    private TotalAmountDiscount totalAmountDiscount;
    private TotalPaymentDiscount totalPaymentDiscount;

    public Contract(boolean constantDaysShipment, PaymentTypes paymentTerms) {
        this.constantDaysShipment = constantDaysShipment;
        this.KtavKamot = new KtavKamot();
        this.paymentTerms = paymentTerms;
        this.shippingDays = new ArrayList<Days>();
        this.totalPaymentDiscount = new TotalPaymentDiscount();
        this.totalAmountDiscount= new TotalAmountDiscount();
    }


    public void setTotalAmountDiscount(TotalAmountDiscount totalAmountDiscount) {
        this.totalAmountDiscount = totalAmountDiscount;
    }

    public void setTotalPaymentDiscount(TotalPaymentDiscount totalPaymentDiscount) {
        this.totalPaymentDiscount = totalPaymentDiscount;
    }



    public boolean getConstantDaysShipment() {
        return this.constantDaysShipment;
    }
    public void setConstantDaysShipment(boolean constantDaysShipment) {
        this.constantDaysShipment = constantDaysShipment;
    }

    public void addShippigDay(Days day) {
        this.shippingDays.add(day);
    }

    public void deleteShippigDay(Days day) {
        this.shippingDays.remove(day);
    }

    public void updatePaymentTerms(PaymentTypes paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public KtavKamot getKtavKamot() {
        return KtavKamot;
    }

    public void addProduct(Product product) {

        this.KtavKamot.addProductAmountDiscount(product);
    }

    public void setKtavKamot(BL.Suppliers.KtavKamot ktavKamot) {
        KtavKamot = ktavKamot;
    }

//    public Product findProduct(int id) {
//        for (Product p : this.products) {
//            if (p.getSuppCatalogId() == id) {
//                return p;
//            }
//        }
//        return null;
//    }

    public TotalAmountDiscount getTotalAmountDiscount() {
        return totalAmountDiscount;
    }

    public TotalPaymentDiscount getTotalPaymentDiscount() {
        return totalPaymentDiscount;
    }

//    public void setTotalAmountDiscount(BL.Suppliers.TotalAmountDiscount totalAmountDiscount) {
//        this.totalAmountDiscount = totalAmountDiscount;
//    }
//
//    public void setTotalPaymentDiscount(BL.Suppliers.TotalPaymentDiscount totalPaymentDiscount) {
//        this.totalPaymentDiscount = totalPaymentDiscount;
//    }

//    public int getAmountOfProduct(){
//        return this.products.size();
//    }

    public int getAmountOfShippingDays(){
        return this.shippingDays.size();
    }
    public double TotalPercentageDiscount(){
        return  this.totalAmountDiscount.getDiscountPercentage()+this.totalPaymentDiscount.getDiscountPercentage();
        }

    public PaymentTypes getPaymentTerms() {
        return paymentTerms;
    }
}


