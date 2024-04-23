package BL.Suppliers;

import DAL.Suppliers.*;

import java.sql.SQLException;
import java.util.*;

public class SupplierController {


    private static SupplierController instance;

    public SupplierController() {
    }

    static SupplierDao supplierDao;

    static {
        supplierDao = SupplierDao.getInstance();
    }



    static KtavKamutDao ktavKamutDao;

    static {
            ktavKamutDao = KtavKamutDao.getInstance();
    }
    static ProductDao productDao;

    static {
            productDao = ProductDao.getInstance();
    }
    static MappingDao mappingDao;

    static {
        try {
            mappingDao = MappingDao.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static TotalAmountDiscountDao totalAmountDiscountDao;

    static {
            totalAmountDiscountDao = TotalAmountDiscountDao.getInstance();
    }

    static TotalPaymentDiscountDao totalPaymentDiscountDao;

    static {
            totalPaymentDiscountDao = TotalPaymentDiscountDao.getInstance();
    }


    public static synchronized SupplierController getInstance() {
        if (instance==null){
            instance=new SupplierController();
        }
        return instance;
    }

    public static void deleteSupplier(Supplier supplier) {
        supplierDao.delete(supplier);
    }

    public Supplier findSupplier(int supplierId) {
        return supplierDao.find(supplierId);
    }
    public static void addSupplier(Supplier newSupplier) {
        supplierDao.insert(newSupplier);
    }

    public static void addProductToContract(Product product) {
        productDao.insert(product);
    }

    public static void deleteProductFromContract(Product product) {
        productDao.delete(product);
    }

    public static Supplier getSupplier(int supplierId)  {
        return supplierDao.find(supplierId);
    }
//
    public static Product getProduct(int supplierCategoryId, int supplierId) {
        return productDao.find(supplierCategoryId, supplierId);
    }

    public static List<Product> getAllSupplierProduct(int supplierId){
        return productDao.getAllSupplierProduct(supplierId);
    }

    public static int getSupplierCategoryId(String itemId, int supplierId) {
        return mappingDao.findSupCatID(itemId, supplierId);
    }
    public static String getItemId(int supplierId, int supplierCategoryId){
        return mappingDao.itemIDForSupplerCategoryId(supplierId,supplierCategoryId);
    }

    public static ProductAmountDiscount getProductAmountDiscount(Product p) {
        return ktavKamutDao.getProductAmountDiscount(p);
    }
    public static void addMapping(int supplierId, int supplierCategoryId, String itemId)  {
        mappingDao.insert(supplierId, supplierCategoryId, itemId);
    }

    public static void addProductToAmountRole(int supplierId, int supplierCategoryId, int amount, double discount)  {
        ktavKamutDao.insertProductAmountRole(supplierId, supplierCategoryId,  amount, discount);
    }
    public static void updateProductToAmountRole(int supplierId, int supplierCategoryId, int amount, double discount){
        ktavKamutDao.updateProductAmountRole(supplierId, supplierCategoryId,  amount, discount);
    }

    public static List<Supplier> getAllSuppliers() {
        return supplierDao.getAllSuppliers();
    }

    public static void addPaymentDiscountToSupplier (int supplierId, double totalPayment, double percentage){
    }

    public static void addTotalAmountRule (int supplierId, int totalAmount, double percentage) {
        totalAmountDiscountDao.insertRole(supplierId, totalAmount,percentage);
    }

    public static void deleteTotalAmountRule (int supplierId, int totalAmount, double percentage) {
        totalAmountDiscountDao.deleteRule(supplierId, totalAmount, percentage);
    }

    public static void editTotalAmountRule (int supplierId, int totalAmount, double percentage) {
        totalAmountDiscountDao.updateRule(supplierId, totalAmount, percentage);
    }







    public static void removeProductToAmountRule(int suppler, String itemId, int amount, double discount){
        ktavKamutDao.deleteRule(suppler, mappingDao.findSupCatID(itemId,suppler), amount, discount);
    }

    public static double getDiscountKtavKamot(int suppler, String itemId, int amount){
        return ktavKamutDao.getDiscount(suppler,mappingDao.findSupCatID(itemId,suppler), amount);
    }

    public static void addTotalPaymentRule (int supplierId, double totalPayment, double percentage) {
        totalPaymentDiscountDao.insertRole(supplierId, totalPayment, percentage);
    }

    public static void deleteTotalPaymentRule (int supplierId, double totalPayment, double percentage) {
        totalPaymentDiscountDao.deleteRule(supplierId, totalPayment, percentage);
    }

    public static void editTotalPaymentRule (int supplierId, double totalPayment, double percentage) {
        totalPaymentDiscountDao.updateRule(supplierId, totalPayment, percentage);
    }

    public static TotalAmountDiscount getSupplierTotalAmountDiscount(int supplierId){
        return totalAmountDiscountDao.find(supplierId);
    }

    public static TotalPaymentDiscount getSupplierTotalPaymentDiscount(int supplierId){
        return totalPaymentDiscountDao.find(supplierId);
    }



    public static boolean checkProductAmountSupply(Product p, int amount){
        return p.getQuantity() >= amount;
    }

    public static boolean checkSupplierIsPeriodic(int supplierId) throws SQLException {
        return  supplierDao.getConstantDaysShipment(supplierId);
    }

    public static void removePaymentDiscountToSupplier (int supplierId, double totalAmount, double percentage){}

    public static void clearAllTables() {
        ktavKamutDao.deleteAllKtavKamotRecords();
        supplierDao.deleteAllSuppliersRecords();
        productDao.deleteAllProductRecords();
        mappingDao.deleteAllMappingRecords();
        totalPaymentDiscountDao.deleteAllRecords();
        totalAmountDiscountDao.deleteAllRecords();
        try {
            PeriodicOrderDao.getInstance().deleteAllPeriodicRecords();
            OrderDao.getInstance().deleteAllOrderHistoryRecords();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
