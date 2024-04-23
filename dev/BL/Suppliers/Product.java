package BL.Suppliers;

import java.util.Random;

public class Product {

    private int supplierId;
    private String name;
    private static int suppCatalogTypeId = 0;
    private int suppCatalogId;
    private int quantity;
    private double weight;

    private double basePrice;

    public Product(int supplierId, int quantity, double basePrice, double weight,String name) {

        this.supplierId=supplierId;
        this.quantity = quantity;
        this.basePrice = basePrice;
        this.weight = weight;
        Random rand = new Random();
        this.suppCatalogId = ++suppCatalogTypeId + rand.nextInt(10000);
        this.name=name;

    }

//    public boolean equals(Product product){
//        if (this.productId==product.getProductId())
//            return true;
//        return false;
//    }

    public int getSuppCatalogId() {
        return suppCatalogId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSuppCatalogId(int suppCatalogId) {
        this.suppCatalogId = suppCatalogId;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public static void setSuppCatalogTypeId(int suppCatalogTypeId) {
        Product.suppCatalogTypeId = suppCatalogTypeId;
    }
}
