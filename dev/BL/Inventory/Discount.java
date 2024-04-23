package BL.Inventory;

import java.util.Date;

public class Discount {

    private double discountPercent;
    private Date dateFrom;
    private Date dateTo;
    private String type;
    private static int nextID = 1;
    private final int discountID;

    public Discount(double DiscountPercent, Date DateFrom, Date DateTo,String Type){
        this.discountPercent=DiscountPercent;
        this.dateFrom=DateFrom;
        this.dateTo=DateTo;
        this.type=Type;
        this.discountID=nextID++;
    }

    public Date getTheLimitDayOfDiscount(){
        return this.dateTo;
    }

    public Date getTheFirstDayOfDiscount(){
        return this.dateFrom;
    }

    public String getType() {
        return type;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }
    public int getID(){
        return this.discountID;
    }

}
