package tests;

import BL.Suppliers.Contract;
import BL.Suppliers.PaymentTypes;
import BL.Suppliers.Product;
import BL.Suppliers.Supplier;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UpdateDiscountTest {
    private static Supplier supplier;
    private static Product product;
    private static Contract contract;
    @Before
    public void setUp() {
        supplier = new Supplier(1, "beershave", true, "sapak1",10);
        contract = new Contract(true, PaymentTypes.NET);
        supplier.setCurrentContract(contract);
        product =new Product(11,50,4,100,"coteg");


    }
//    @Test
//    public void addProductAmountDiscount(){
//        supplier.getCurrentContract().getKtavKamot().addProductAmountDiscount(product);
//        assertNotNull(supplier.getCurrentContract().getKtavKamot().getProductBasedProductId(11));
//    }
    @Test
    public void updateProductAmountDiscount(){
        supplier.getCurrentContract().getKtavKamot().addProductAmountDiscount(product);
        supplier.getCurrentContract().getKtavKamot().updateProductAmountDiscount(product,50,10);
        assertEquals((long) 10,(long)supplier.getCurrentContract().getKtavKamot().getProductAmountDiscount(product,60).getDiscountPercentage());
    }

    @Test
    public void calculatePercentageDiscountAmount() {

        supplier.getCurrentContract().getTotalAmountDiscount().updatePolicy(1000, 20);
        supplier.getCurrentContract().getTotalAmountDiscount().setTotalAmountValue(1500);
        assertEquals(20, (int)supplier.getCurrentContract().getTotalAmountDiscount().getDiscountPercentage());
    }
    @Test
    public void calculatePriceAfterDiscountAmount(){
        supplier.getCurrentContract().getTotalAmountDiscount().updatePolicy(1000, 20);
        supplier.getCurrentContract().getTotalAmountDiscount().setTotalAmountValue(1500);
        assertEquals(1200, (int)supplier.getCurrentContract().getTotalAmountDiscount().getPriceAfterDiscount());
    }
    @Test
    public void TotalPaymentDiscount(){

        assertNotNull(supplier.getCurrentContract().getTotalPaymentDiscount());
    }
    @Test
    public void calculatePercentageDiscountPayment(){
        supplier.getCurrentContract().getTotalPaymentDiscount().updatePolicy(1000, 20);
        supplier.getCurrentContract().getTotalPaymentDiscount().setTotalPaymentValue(1500);
        assertEquals(20, (int)supplier.getCurrentContract().getTotalPaymentDiscount().getDiscountPercentage());
    }
    @Test
    public void calculatePriceAfterDiscountPayment(){
        supplier.getCurrentContract().getTotalPaymentDiscount().updatePolicy(1000, 20);
        supplier.getCurrentContract().getTotalPaymentDiscount().setTotalPaymentValue(1500);
        assertEquals(1200, (int)supplier.getCurrentContract().getTotalPaymentDiscount().getPriceAfterDiscount());
    }
}
