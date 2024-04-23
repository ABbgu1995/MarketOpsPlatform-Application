package BL.Suppliers;

import java.util.*;

public class SupplierOrder {
protected int orderId;
protected Supplier supplier;
protected static int orderTypeId = 0;
protected double totalOrderPayment;

protected List<OrderLine> lines;



public SupplierOrder(Supplier s){
    Random rand = new Random();
    this.orderId = ++orderTypeId + rand.nextInt(10000);
    this.supplier=s;//maybe copy constructor
    lines=new ArrayList<OrderLine>();
}


public void calculateTotalOrderPayment(){
    int sumAmount=0;
    double sumPayment=0;
    for (OrderLine line: lines){
        sumAmount+=line.getAmount();
        sumPayment+=line.getFinalPrice();
    }
    this.supplier.getCurrentContract().getTotalPaymentDiscount().setTotalPaymentValue(sumPayment);
    this.supplier.getCurrentContract().getTotalAmountDiscount().setTotalAmountValue(sumAmount);
    this.totalOrderPayment=sumPayment*((100-this.supplier.getCurrentContract().TotalPercentageDiscount())/100);
}
public void addLine(OrderLine line)
{
    this.lines.add(line);

}

    public double getTotalOrderPayment() {
        calculateTotalOrderPayment();
        return totalOrderPayment;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public int getAmountOfLines(){
        return this.lines.size();
    }

    public void printOrder(){
        System.out.println("------ orders for supplier: " + this.supplier.getSupplierId() + "orderId:"+this.orderId+ " ------\n");
        for (OrderLine line: this.lines)
            System.out.println(line.printLineInfo());
    }
    public String printOrdertoform() {
        String s = new String(("------ orders for supplier: " + this.supplier.getSupplierId() + " ------ orderId:"+this.orderId+ "\n"));

        for (OrderLine line : this.lines) {
            s+=line.printLineInfo()+"\n";

        }
        return s;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public int getOrderId() {
        return orderId;
    }
}

