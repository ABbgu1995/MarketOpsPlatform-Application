package tests;

import BL.Inventory.BranchController;
import BL.Inventory.Item;
import BL.Inventory.place;
import DAL.Inventory.Database;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ItemControllerTest {
    @Test
    void setItemPlace() throws SQLException {
        Database.clearDatabase(Database.connect());
        BranchController branchController2 = BranchController.getInstance();
        Map<String , Integer> mapOfMin_branch1  = new HashMap<>();

        mapOfMin_branch1.put("mh00", 0);
        mapOfMin_branch1.put("mh22", 0);
        mapOfMin_branch1.put("mh44", 1);
        mapOfMin_branch1.put("sh00", 0);
        mapOfMin_branch1.put("sh22", 0);
        mapOfMin_branch1.put("sh33", 0);
        mapOfMin_branch1.put("cl00", 0);
        mapOfMin_branch1.put("cl22", 3);
        branchController2.createNewBranch(1, mapOfMin_branch1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 10, 0, 0, 0);
        Date newYears = calendar.getTime();
        Item it1 = branchController2.createItemToBranch(1 , "mh00", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears , "mutzar_halav", "halav",
                "1 liter" );
        assertEquals(place.stock,branchController2.foundItemInBranch(1, 1).getItemPlace(), "the test of set item place failed");
    }
}