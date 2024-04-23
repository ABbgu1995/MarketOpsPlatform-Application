package tests;

import BL.Suppliers.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.Date;


public class OrderTest {
    private static Supplier supplier;
    private static Product product1;
    private static Product product2;
    private static Contract contract;


    private static SupplierOrder supplierOrder;


    @Before
    public void setUp()  {
        supplier = new Supplier(1, "beershave", true, "sapak1",10);
        product1 = new Product(1,10,10,10,"m1");
        product2 = new Product(2,10,10,10,"m2");
        contract = new Contract(true, PaymentTypes.NET);
        supplier.setCurrentContract(contract);
        Date d1 = new Date();
        supplierOrder = new SupplierOrder(supplier);
    }
    @Test
    public void calculateTotalPrice(){
        OrderLine line1 = new OrderLine(3,"m1", 10, 10, 10);
        OrderLine line2 = new OrderLine(4,"m1", 5, 10, 20);
        supplierOrder.addLine(line1);
        supplierOrder.addLine(line2);
        assertEquals((long)130, (long)supplierOrder.getTotalOrderPayment());
    }

    @Test
    public void addLine(){
        OrderLine line1 = new OrderLine(3,"m1", 10, 10, 10);
        OrderLine line2 = new OrderLine(5,"m1", 10, 10, 10);
        OrderLine line3 = new OrderLine(4,"m1", 5, 10, 20);
        supplierOrder.addLine(line1);
        supplierOrder.addLine(line2);
        supplierOrder.addLine(line3);
        assertEquals(3, supplierOrder.getAmountOfLines());
    }

    @Test
    public void getSupplier(){
        assertSame(supplier, supplierOrder.getSupplier());
    }


}
