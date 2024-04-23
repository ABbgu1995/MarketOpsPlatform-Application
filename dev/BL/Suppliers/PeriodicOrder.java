package BL.Suppliers;

public class PeriodicOrder extends SupplierOrder{

    private Days shipmentDay;
    //private int periodicOrderId;

    public PeriodicOrder(Supplier s, Days shipmentDay) {
        super(s);
        this.shipmentDay = shipmentDay;
        //this.periodicOrderId=periodicOrderId
    }

    public void setShipmentDay(Days shipmentDay) {
        this.shipmentDay = shipmentDay;
    }

    public Days getShipmentDay() {
        return shipmentDay;
    }


}
