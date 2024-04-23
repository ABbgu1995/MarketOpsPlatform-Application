package tests;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import BL.Inventory.BranchController;
import BL.Inventory.Item;
import BL.Inventory.ReportController;
import BL.Suppliers.*;
import DAL.Inventory.Database;
import DAL.Suppliers.PeriodicOrderDao;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class OrderControllerTest {

    private static Supplier supplier;
    private static Product product;
    private static Product product2;
    private static Contract contract;

    private static Map<String , Integer> periodicProducts;

    private static SupplierController sctrl;
    private static OrderController orderController;

    private static BranchController branchController;
    private static Map<String , Integer> mapOfMin_branch1;

    @BeforeEach
    public void setUp() throws SQLException {

        SupplierController.clearAllTables();
        Database.clearDatabase(Database.connect());

        mapOfMin_branch1  = new HashMap<String , Integer>();
        sctrl=SupplierController.getInstance();
        orderController=OrderController.getInstance();
        branchController = BranchController.getInstance();

        periodicProducts = new HashMap<String , Integer>();
        supplier = new Supplier(1, "beershave", true, "sapak1", 10);
        contract = new Contract(true, PaymentTypes.NET60EOM);
        supplier.setCurrentContract(contract);
        SupplierController.addSupplier(supplier);
        Product product = new Product(1, 40, 5, 10.5, "econumika");
        SupplierController.addMapping(1, product.getSuppCatalogId(), "cl00");
        SupplierController.addProductToContract(product);
        Product product2 = new Product(1, 1000, 10, 1.5, "econumika");
        SupplierController.addMapping(1, product2.getSuppCatalogId(), "cl22");
        SupplierController.addProductToContract(product2);

    }


    @Test
    void test5_addPeriodicOrder() {
        periodicProducts.put("cl00",5);
        OrderController.addPeriodicOrder(1,Days.SUNDAY,periodicProducts);
        assertEquals(5,OrderController.findPeriodicOrder(1, Days.SUNDAY).getLines().get(0).getAmount());
        assertEquals("cl00",SupplierController.getItemId(1,OrderController.findPeriodicOrder(1, Days.SUNDAY).getLines().get(0).getSupplierCategoryId()));


    }
    @Test
    void test4_UpdatePeriodicOrderAmount() {
        periodicProducts.put("cl00",5);
        OrderController.addPeriodicOrder(1,Days.SUNDAY,periodicProducts);
        OrderController.updatePeriodicOrderProductAmount(1,SupplierController.getSupplierCategoryId("cl00",1),Days.SUNDAY,13);
        assertEquals(13,OrderController.findPeriodicOrder(1, Days.SUNDAY).getLines().get(0).getAmount());
    }

    @Test
    void test3_addPeriodicOrderItem() {
        Product product = new Product(1, 10, 5, 10.5, "econumika");
        SupplierController.addMapping(1, product.getSuppCatalogId(), "cl22");
        SupplierController.addProductToContract(product);
        periodicProducts.put("cl00",5);
        OrderController.addPeriodicOrder(1,Days.SUNDAY,periodicProducts);
        OrderController.addProductToPeriodicOrder(1,SupplierController.getSupplierCategoryId("cl22",1),7,Days.SUNDAY);
        assertEquals(2,OrderController.findPeriodicOrder(1, Days.SUNDAY).getLines().size());
    }

    @Test
    void test2_deletePeriodicOrderItem() {
        Product product = new Product(1, 10, 5, 10.5, "econumika");
        SupplierController.addMapping(1, product.getSuppCatalogId(), "cl22");
        SupplierController.addProductToContract(product);
        periodicProducts.put("cl00",5);
        periodicProducts.put("cl22",5);
        OrderController.addPeriodicOrder(1,Days.SUNDAY,periodicProducts);
        OrderController.removeProductFromPeriodicOrder(1,SupplierController.getSupplierCategoryId("cl00",1),Days.SUNDAY);
        assertEquals(1,OrderController.findPeriodicOrder(1, Days.SUNDAY).getLines().size());
    }
    @Test
    public void test1_executeOrderBasedOnMissingReport() {

//        mapOfMin_branch1.put("mh00", 0);
//        mapOfMin_branch1.put("mh22", 0);
//        mapOfMin_branch1.put("mh44", 1);
        mapOfMin_branch1.put("cl22", 3);
        mapOfMin_branch1.put("cl00",3);
        branchController.createNewBranch(1, mapOfMin_branch1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 10, 0, 0, 0);
        Date newYears = calendar.getTime();
        Item item13 = branchController.createItemToBranch(1 , "cl00", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears , "mutzar_halav", "halav",
                "1 liter" );
        Item item14 = branchController.createItemToBranch(1 , "cl22", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears , "mutzar_halav", "halav",
                "1 liter" );

//        Item item1 = branchController.createItemToBranch(1 , "mh00", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears , "mutzar_halav", "halav",
//                "1 liter" );
//        calendar.set(2023, Calendar.APRIL, 07, 0, 0, 0);
//        Date newYears3 = calendar.getTime();
//        Item item2 =branchController.createItemToBranch(1, "mh22", "tara", "tara_supplier", 3.90, 5.50,newYears3 , "mutzar_halav", "halav",
//                "1 liter");
//        calendar.set(2023, Calendar.MAY, 14, 0, 0, 0);
//        Date newYears6 = calendar.getTime();
//        Item item3 =branchController.createItemToBranch(1,"mh44", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears6 , "mutzar_halav", "gavnatz",
//                "1 liter");
//        Item item4 =branchController.createItemToBranch(1,"mh44", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears6 , "mutzar_halav", "gavnatz",
//                "1 liter");
//        Item item5 =branchController.createItemToBranch(1,"mh44", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears6 , "mutzar_halav", "gavnatz",
//                "1 liter");

        HashMap<String, Integer> itemIdAmount = new HashMap<String, Integer>();
        ReportController reportController = ReportController.getInstance();
        OrderController orderController = OrderController.getInstance();
        Map<String, Integer> shortage_rep = reportController.createNewShortageReport(1);
        orderController.setProductMissingReport(shortage_rep);
        orderController.executeOrder();
        HashMap<Integer, Integer> supCatAmount =  OrderController.allOrderProductSupId();
        for(Integer supId : supCatAmount.keySet()){
            itemIdAmount.put(SupplierController.getItemId(1,supId), supCatAmount.get(supId));
        }
        assertEquals(shortage_rep,itemIdAmount, "the test is failed");
    }


}