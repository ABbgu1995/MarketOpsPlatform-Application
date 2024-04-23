package BL.Inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {


    private static int nextID = 1;
    private int itemID;
    private String catalogNumber;
    private String maker;
    private String supplier;
    private double supplierPrice;
    private double storePrice;
    private double storePriceAfterDiscount;
    private Date expiredDate;
    private String category;
    private String subCategory;
    private String subsubCategory;
    private damageType damage;
    private List<Discount> allDiscount;
    private String DamageTypeItem;
    private place itemPlace;

    public Item(int id){
        this.itemID = id;
    }

    public Item(String Catalognumber,String Maker,String Supplier, double SupplierPrice,
                double StorePrice, Date ExpiredDate,String Category, String SubCategory, String SubSubCategory){
        this.catalogNumber=Catalognumber;
        this.itemID=nextID++;
        this.maker=Maker;
        this.supplier=Supplier;
        this.supplierPrice=SupplierPrice;
        this.storePrice=StorePrice;
        this.storePriceAfterDiscount = -1;
        this.expiredDate=ExpiredDate;
        this.category=Category;
        this.subCategory=SubCategory;
        this.subsubCategory=SubSubCategory;
        this.damage = damageType.None;
        this.DamageTypeItem = null;
        this.allDiscount = new ArrayList<Discount>();
        this.itemPlace = place.stock;
    }

    public int getItemID(){
        return this.itemID;
    }
    public String getSupplier(){
        return this.supplier;
    }
    public String getSubCategory(){
        return this.subCategory;
    }
    public String getSubsubCategory(){
        return this.subsubCategory;
    }
    public Date getExpiredDate(){
        return this.expiredDate;
    }
    public double getItemStorePrice(){
        return this.storePrice;
    }

    public void setItemStorePrice(double newStorePrice){
        this.storePrice = newStorePrice;
    }
    public void setItemStorePriceWithDiscount(double new_price){
        this.storePriceAfterDiscount = new_price;
    }
    public double getItemStorePriceWithDiscount(){
        return this.storePriceAfterDiscount;
    }

    public double getSupplierPrice(){
        return this.supplierPrice;
    }

    public String getMaker(){
        return this.maker;
    }

    public boolean checkIfDamage(){
        return this.damage != damageType.None;
    }

    public void setDamage(damageType newDamage){
        this.damage = newDamage;
    }
    public String getCategory(){
        return this.category;
    }

    public String getCatalogNumber() {
        return this.catalogNumber;
    }

    public damageType getDamage(){
        return this.damage;
    }

    public void setDiscount(Discount newDiscount){
        this.allDiscount.add(newDiscount);
    }
    public List<Discount> getDiscount(){
        return this.allDiscount;
    }

    public String getDamageTypeItem(){
        return this.DamageTypeItem;
    }

    public void setDamageTypeItem(String new_damage_type){
        this.DamageTypeItem = new_damage_type;
    }

    public void setItemPlace(place pl){
        this.itemPlace = pl;
    }
    public place getItemPlace(){
        return this.itemPlace;
    }
    public void setCatalogNumber( String catNum){
        this.catalogNumber = catNum;
    }
    public void setMaker(String maker){
        this.maker = maker;
    }
    public void setSupplier(String supp){
        this.supplier = supp;
    }
    public void setSupplierPrice(double supPrice){
        this.supplierPrice = supPrice;
    }
    public void setStorePrice(double storePrice){
        this.storePrice = storePrice;
    }

     public void setExpiredDate(Date date){
        this.expiredDate = date;
     }

     public void setCategory(String category){
        this.category = category;
     }


    public void setSubCategory(String subCategory){
        this.subCategory = subCategory;
    }


    public void setSubSubCategory(String subSubCategory){
        this.subsubCategory = subSubCategory;
    }


}

