package BL.Suppliers;

public class OrderLine {
    private int supplierCategoryId;
    private String productName;
    private double basePrice;
    private double discount;
    private double finalPrice;
    private int amount;

    public OrderLine(int supplierCategoryId, String productName, int amount, double basePrice, double discount){
        this.supplierCategoryId=supplierCategoryId;
        this.productName=productName;
        this.amount=amount;
        this.basePrice=basePrice;
        this.discount=discount;
        calculateFinalPrice();

    }

    public int getAmount() {
        return amount;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void calculateFinalPrice(){
        this.finalPrice=(this.basePrice-(this.basePrice*(this.discount/100)))*this.amount;
    }

    public String printLineInfo(){
        return ("supplierCategoryId: "+ this.supplierCategoryId + "\n" +
                "productName: " + this.productName + "\n" +
                "amount: " + this.amount + "\n" +
                "basePrice: " + this.basePrice + "\n" +
                "discount: " + this.discount + "\n" +
                "finalPrice: " + finalPrice);
    }

    public int getSupplierCategoryId() {
        return supplierCategoryId;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getDiscount() {
        return discount;
    }

    public String getProductName() {
        return productName;
    }
}
