package tests;

import BL.Inventory.BranchController;
import BL.Inventory.DiscountController;
import BL.Inventory.Item;
import BL.Inventory.status;
import DAL.Inventory.Database;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DiscountControllerTest {
    @Test
    void insertNewCatalogNumberDiscount() throws SQLException {
        Database.clearDatabase(Database.connect());
        BranchController branchController = BranchController.getInstance();
        DiscountController discountController = DiscountController.getInstance();
        Map<String , Integer> mapOfMin_branch1  = new HashMap<>();

        mapOfMin_branch1.put("mh00", 0);
        mapOfMin_branch1.put("mh22", 0);
        mapOfMin_branch1.put("mh44", 1);
        mapOfMin_branch1.put("sh00", 0);
        mapOfMin_branch1.put("sh22", 0);
        mapOfMin_branch1.put("sh33", 0);
        mapOfMin_branch1.put("cl00", 0);
        mapOfMin_branch1.put("cl22", 3);
        branchController.createNewBranch(1, mapOfMin_branch1);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 10, 0, 0, 0);
        Date newYears = calendar.getTime();
        Item item1 = branchController.createItemToBranch(1 , "mh00", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears , "mutzar_halav", "halav",
                "1 liter" );

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2023, Calendar.MAY, 10, 0, 0, 0);
        Date dateFrom6 = calendar2.getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2023, Calendar.MAY, 20, 0, 0, 0);
        Date dateTo6 = calendar1.getTime();

        status curr_sts1 = discountController.createNewDiscountByCatalogNumber(1, 20, dateFrom6, dateTo6, "mh00");

        assertEquals(status.success, curr_sts1, "the discount is failed");
        assertEquals(6, item1.getItemStorePriceWithDiscount(), "the price is failed");

    }
    @Test
    void insertNewCatalogDiscount() throws SQLException{
        Database.clearDatabase(Database.connect());
        BranchController branchController = BranchController.getInstance();
        DiscountController discountController = DiscountController.getInstance();
        Map<String , Integer> mapOfMin_branch1  = new HashMap<>();

        mapOfMin_branch1.put("mh00", 0);
        mapOfMin_branch1.put("mh22", 0);
        mapOfMin_branch1.put("mh44", 1);
        mapOfMin_branch1.put("sh00", 0);
        mapOfMin_branch1.put("sh22", 0);
        mapOfMin_branch1.put("sh33", 0);
        mapOfMin_branch1.put("cl00", 0);
        mapOfMin_branch1.put("cl22", 3);
        branchController.createNewBranch(1, mapOfMin_branch1);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 10, 0, 0, 0);
        Date newYears = calendar.getTime();
        Item item1 = branchController.createItemToBranch(1 , "mh00", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears , "mutzar_halav", "halav",
                "1 liter" );
        calendar.set(2023, Calendar.APRIL, 07, 0, 0, 0);
        Date newYears3 = calendar.getTime();
        Item item2 =branchController.createItemToBranch(1, "mh22", "tara", "tara_supplier", 3.90, 5.50,newYears3 , "mutzar_halav", "halav",
                "1 liter");
        calendar.set(2023, Calendar.MAY, 14, 0, 0, 0);
        Date newYears6 = calendar.getTime();
        Item item3 =branchController.createItemToBranch(1,"mh44", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears6 , "mutzar_halav", "gavnatz",
                "1 liter");
        Item item4 =branchController.createItemToBranch(1,"mh44", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears6 , "mutzar_halav", "gavnatz",
                "1 liter");
        Item item5 =branchController.createItemToBranch(1,"mh44", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears6 , "mutzar_halav", "gavnatz",
                "1 liter");
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2023, Calendar.MAY, 10, 0, 0, 0);
        Date dateFrom6 = calendar2.getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2023, Calendar.MAY, 20, 0, 0, 0);
        Date dateTo6 = calendar1.getTime();

        status curr_sts = discountController.createNewDiscountByCategory(1, 20, dateFrom6, dateTo6, "mutzar_halav");
        assertEquals(status.success, curr_sts, "the discount is failed");
        assertEquals(6, item1.getItemStorePriceWithDiscount(), "the price is failed");
//        status curr_sts1 = discountController.createNewDiscountByCatalogNumber(1, Discountpercent6, dateFrom6, dateTo6, catalogNumber6);

    }

}