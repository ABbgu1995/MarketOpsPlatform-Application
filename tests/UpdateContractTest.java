package tests;

import BL.Suppliers.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UpdateContractTest {

    private static Supplier supplier;
    private static Contract contract;
    private static Product product;


    @Before
    public void setUp()  {
        supplier = new Supplier(1, "beershave", true, "sapak1",10);
        contract = new Contract(true, PaymentTypes.NET);
        product = new Product(1,10,13,13,"milk");
        supplier.setCurrentContract(contract);

    }

//    @Test
//    public void addProductToContract(){
//        supplier.getCurrentContract().addProduct(product);
//        assertEquals(1, supplier.getCurrentContract().getAmountOfProduct());
//    }

    @Test
    public void totalPercentageCalculation(){
        supplier.getCurrentContract().getTotalAmountDiscount().updatePolicy(10,5);
        supplier.getCurrentContract().getTotalPaymentDiscount().updatePolicy(20, 7);
        supplier.getCurrentContract().getTotalPaymentDiscount().updatePolicy(30, 10);
        supplier.getCurrentContract().getTotalPaymentDiscount().setTotalPaymentValue(25);
        supplier.getCurrentContract().getTotalAmountDiscount().setTotalAmountValue(13);
        assertEquals((long)12, (long)supplier.getCurrentContract().TotalPercentageDiscount());
    }

    @Test
    public void addShippingDay(){
        supplier.getCurrentContract().addShippigDay(Days.SUNDAY);
        assertEquals(1, supplier.getCurrentContract().getAmountOfShippingDays());
    }
    @Test
    public void deleteShippingDay(){
        supplier.getCurrentContract().deleteShippigDay(Days.SUNDAY);
        assertEquals(0, supplier.getCurrentContract().getAmountOfShippingDays());
    }

//    @Test
//    public void findProduct(){
//        supplier.getCurrentContract().addProduct(product);
//        assertSame(product, supplier.getCurrentContract().findProduct(1));
//    }


    @Test
    public void addProductKtavKamot(){
        supplier.getCurrentContract().getKtavKamot().addProductAmountDiscount(product);
        assertEquals(1,supplier.getCurrentContract().getKtavKamot().amountOfProductAmountDiscount());
    }

    @Test
    public void addRuleToDiscount(){
        supplier.getCurrentContract().getKtavKamot().addProductAmountDiscount(product);
        supplier.getCurrentContract().getKtavKamot().updateProductAmountDiscount(product,10,10);
        supplier.getCurrentContract().getKtavKamot().updateProductAmountDiscount(product,50,20);
        supplier.getCurrentContract().getKtavKamot().updateProductAmountDiscount(product,55,30);
        assertEquals((long)20,(long)supplier.getCurrentContract().getKtavKamot().getProductAmountDiscount(product,52).getDiscountPercentage());
    }

    }

