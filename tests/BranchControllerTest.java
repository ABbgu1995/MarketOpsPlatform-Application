package tests;

import BL.Inventory.BranchController;
import BL.Inventory.Item;
import DAL.Inventory.Database;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class BranchControllerTest {


    public Item it1;
    public Item it2;
    public BranchController branchController;

    @BeforeEach
    public void setUp() throws SQLException {
        Database.clearDatabase(Database.connect());
        branchController = BranchController.getInstance();
        Map<String, Integer> mapOfMin_branch1 = new HashMap<>();

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
        it1 = branchController.createItemToBranch(1, "mh00", "tnuva", "tnuva_supplier", 5.90, 7.50, newYears, "mutzar_halav", "halav",
                "1 liter");
        calendar.set(2023, Calendar.APRIL, 07, 0, 0, 0);
        Date newYears3 = calendar.getTime();
        it2 = branchController.createItemToBranch(1, "mh22", "tara", "tara_supplier", 3.90, 5.50, newYears3, "mutzar_halav", "halav",
                "1 liter");

    }

    @Test
    void testA_foundItemInBranch() throws SQLException {
        assertEquals(it1.getItemID(), this.branchController.foundItemInBranch(1, it1.getItemID()).getItemID(), "the test of add item to branch have failed");
    }


    @Test
    void testB_deleteItemFromBranch() throws SQLException {
        branchController.deleteItemFromBranch(it2, 1);
        assertEquals(branchController.countItemsInBranch(1).size(), 1, "the delete test is failed");
    }
}