package tests;

import BL.Inventory.BranchController;
import BL.Inventory.Item;
import BL.Inventory.ReportController;
import DAL.Inventory.Database;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportControllerTest {
    @Test
    public void createInventoryReport()throws SQLException {
        Database.clearDatabase(Database.connect());
        BranchController branchController = BranchController.getInstance();
        ReportController reportController = ReportController.getInstance();
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
        Map<String, Integer> inventory_rep = reportController.createNewInventoryReport(1);
        Map<String, Integer> excepted = new HashMap<>();
        excepted.put("mh22", 1);
        excepted.put("mh00", 1);
        excepted.put("mh44", 3);
        assertEquals(excepted, inventory_rep, "the inventoryReport test is failed");
    }
    @Test
    public void createShortageReport()throws SQLException {
        Database.clearDatabase(Database.connect());
        BranchController branchController = BranchController.getInstance();
        ReportController reportController = ReportController.getInstance();
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
        Map<String, Integer> shortage_rep = reportController.createNewShortageReport(1);
        Map<String, Integer> excepted = new HashMap<>();
        excepted.put("sh00", 10);
        excepted.put("sh22", 10);
        excepted.put("sh33", 10);
        excepted.put("cl00", 10);
        excepted.put("cl22", 15);
        assertEquals(excepted, shortage_rep, "the shortageReport test is failed");
    }

}