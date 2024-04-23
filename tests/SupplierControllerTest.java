package tests;

import BL.Inventory.BranchController;
import BL.Inventory.Item;
import BL.Inventory.ReportController;
import BL.Suppliers.*;
import DAL.Inventory.Database;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class SupplierControllerTest {


    private static Supplier supplier;
    private static Product product;
    private static Product product2;
    private static Contract contract;

    private static Map<String , Integer> periodicProducts;

//    private static SupplierController sctrl;
//    private static OrderController orderController;
//
    private static BranchController branchController;
    private static HashMap<String , Integer> mapOfMin_branch1;
    @BeforeEach
    void setUp() throws SQLException {

    }


//    @org.junit.jupiter.api.Test
//    void findSupplier() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void addSupplier() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void addProductToContract() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void getSupplier() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void getProduct() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void getSupplierCategoryId() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void addProductToAmountRole() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void updateProductToAmountRole() {
//    }
}