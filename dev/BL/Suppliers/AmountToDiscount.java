package BL.Suppliers;

public class AmountToDiscount {
    int amount;
    double percentage;
    int supplierId;

    int shipmentTime;

    private double priceAfterDiscount;

    public AmountToDiscount(int amount, double percentage, double priceAfterDiscount, int supplierId,int shipmentTime ) {
        this.amount = amount;
        this.percentage = percentage;
        this.supplierId = supplierId;
        this.priceAfterDiscount=priceAfterDiscount;
        this.shipmentTime=shipmentTime;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getAmount() {
        return amount;
    }

    public double getPercentage() {
        return percentage;
    }

    public double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public int getShipmentTime() {
        return shipmentTime;
    }

    public void setShipmentTime(int shipmentTime) {
        this.shipmentTime = shipmentTime;
    }
}

