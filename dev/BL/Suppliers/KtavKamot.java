package BL.Suppliers;

import java.util.ArrayList;
import java.util.List;

public class KtavKamot {

    int suppliierId;
    private List<ProductAmountDiscount> productToDiscount;
    private double totalDiscount;
    int supplierId;

    public int getSupplierId() {
        return suppliierId;
    }

    public int getRulesTotalAmount() {
        int total=0;
        for(ProductAmountDiscount p : productToDiscount)
            total += p.policyList.size();
        return total;
    }

    public List<ProductAmountDiscount> getProductToDiscount() {
        return productToDiscount;
    }

    public KtavKamot(int supplierId) {
        this.productToDiscount = new ArrayList<ProductAmountDiscount>();
        this.suppliierId=supplierId;

    }

    public KtavKamot() {
        this.productToDiscount = new ArrayList<ProductAmountDiscount>();
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public Product getProductFromKtavKamot(int supplierCategoryId){
        for (ProductAmountDiscount p : this.productToDiscount) {
            if (p.getProduct().getSuppCatalogId()==supplierCategoryId) {
                return p.getProduct();
            }
        }
        return null;
    }

    public ProductAmountDiscount getProductAmountDiscount(Product product, int productRequiredAmount) {
        for (ProductAmountDiscount p : this.productToDiscount) {
            if (p.getProduct().equals(product)) {
                p.setTotalRequiredProductAmount(productRequiredAmount);
                return p;
            }
        }
        return null;
    }


    public void addProductAmountDiscount(Product product) {
        ProductAmountDiscount newProductAmountDiscount = new ProductAmountDiscount(product);
        this.productToDiscount.add(newProductAmountDiscount);
        calculateTotalDiscount();
    }

    public int amountOfProductAmountDiscount(){
        return this.productToDiscount.size();
    }

    public ProductAmountDiscount ProductAmountDiscountBasedProductId(int productId){
        for (ProductAmountDiscount p : this.productToDiscount) {
            if (p.getProduct().getSuppCatalogId()==productId) {
                return p;
            }
        }
        return null;
    }

    public void updateProductAmountDiscount(Product updateProduct, int amountOfProduct, double percentageDiscount) {
        for (ProductAmountDiscount p : this.productToDiscount) {
            if (p.getProduct().equals(updateProduct)) {
                p.updatePolicy(amountOfProduct, percentageDiscount);
                break;
            }
        }
        calculateTotalDiscount();
    }

    // the function return total percentage discount
    public void calculateTotalDiscount() {
        this.totalDiscount = 0;
        for (ProductAmountDiscount p : this.productToDiscount) {
            this.totalDiscount += p.getDiscountPercentage();
        }
    }

    public double getTotalDiscount() {
        return this.totalDiscount;
    }

    public Product getProductBasedProductId(int productId){
        for (ProductAmountDiscount p : this.productToDiscount){
            if (p.getProduct().getSuppCatalogId()==productId)
                return p.getProduct();
        }
        return null;
    }

}

