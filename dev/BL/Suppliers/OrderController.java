package BL.Suppliers;

import DAL.Suppliers.*;

import java.sql.SQLException;
import java.util.*;


public class OrderController {
    private Map<String, Integer> productMissingReport;
    //private Map<Integer, Boolean> orderStatus;
    private List<SupplierOrder> orders;
    private List<Supplier> suppliers;
    List<AmountToDiscount> AmountDiscountPairList;
    public static PeriodicOrderDao periodicOrderDao;

    static {
        try {
            periodicOrderDao = PeriodicOrderDao.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static OrderDao orderDao;

    static {
        try {
            orderDao = OrderDao.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    static SupplierDao supplierDao;

    static {
        supplierDao = SupplierDao.getInstance();
    }
    static MappingDao mappingDao;

    static {
        try {
            mappingDao = MappingDao.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    static ProductDao productDao;

    static {
            productDao = ProductDao.getInstance();
    }

    static KtavKamutDao ktavKamutDao;

    static {
            ktavKamutDao = KtavKamutDao.getInstance();
    }


    private static OrderController instance;


    public static synchronized OrderController getInstance() {
        if (instance==null){
            instance=new OrderController();
        }
        return instance;
    }

    private OrderController() {
        this.orders = new ArrayList<SupplierOrder>();
        this.AmountDiscountPairList = new ArrayList<AmountToDiscount>();
    }

    public void setProductMissingReport(Map<String, Integer> productMissingReport) {
        this.productMissingReport = productMissingReport;
    }

    public void addSupplier(Supplier s) {
        suppliers.add(s);
    }

    public SupplierOrder findSupplierOrder(int supplierId){
        for (SupplierOrder s : this.orders){
            if(s.getSupplier().getSupplierId()==supplierId)
                return s;
        }
        return null;
    }




    public List<SupplierOrder> productToSupplier()  {

        this.orders.clear();
        // iterate over the missing report
        Iterator<Map.Entry<String, Integer>> iterator = productMissingReport.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            for (Supplier s : supplierDao.getAllSuppliers()) {
                Product testedProduct = productDao.find(SupplierController.getSupplierCategoryId(entry.getKey(), s.getSupplierId()), s.getSupplierId());
                //Product testedProduct = (s.getCurrentContract().getKtavKamot().getProductFromKtavKamot(SupplierController.getSupplierCategoryId(s.getSupplierId(),entry.getKey())));
                if (testedProduct != null) {
                    int supplyAmount = testedProduct.getQuantity();
                    double percentage;
                    double priceAfterDiscount;
                    if(SupplierController.getProductAmountDiscount(testedProduct)==null) {
                        percentage = 0;
                        priceAfterDiscount = testedProduct.getBasePrice();
                    }
                    else {
                        ProductAmountDiscount productAmountDiscount =  SupplierController.getProductAmountDiscount(testedProduct);
                        productAmountDiscount.setTotalRequiredProductAmount(entry.getValue());
                        percentage = productAmountDiscount.getDiscountPercentage();
                        priceAfterDiscount=productAmountDiscount.getUnitPriceAfterDiscount();
                    }
                    AmountToDiscount pair = new AmountToDiscount(supplyAmount,percentage,priceAfterDiscount,s.getSupplierId(),s.getShipmentTime());
                    AmountDiscountPairList.add(pair);
                }
            }

            AmountDiscountPairList.sort(Comparator.comparing(AmountToDiscount::getPriceAfterDiscount));
            AmountDiscountPairList.sort(Comparator.comparing(AmountToDiscount::getAmount, Comparator.reverseOrder()));
            AmountDiscountPairList.sort(Comparator.comparing(AmountToDiscount::getShipmentTime));



            int totalRequiredAmount = entry.getValue();
            int totalSuppliersAmount = 0;
            for (AmountToDiscount s : AmountDiscountPairList) {
                totalSuppliersAmount += s.getAmount();
            }

            if (totalSuppliersAmount < totalRequiredAmount) {
                //orderStatus.put(entry.getKey(), false);
                AmountDiscountPairList.clear();
            }
            else {

                // create order to each supplier

                for (AmountToDiscount s : AmountDiscountPairList) {
                    OrderLine currentLine;

                    Product p = productDao.find(SupplierController.getSupplierCategoryId(entry.getKey(), s.getSupplierId()), s.getSupplierId());
                    if (totalRequiredAmount > s.getAmount()) {

                        currentLine = new OrderLine(p.getSuppCatalogId(), p.getName(), s.amount, p.getBasePrice(), s.getPercentage());
                        totalRequiredAmount -= s.amount;
                    } else {
                        currentLine = new OrderLine(p.getSuppCatalogId(), p.getName(), totalRequiredAmount, p.getBasePrice(), s.getPercentage());
                        totalRequiredAmount = 0;
                    }
                    if (findSupplierOrder(s.supplierId) == null) {
                        SupplierOrder order = new SupplierOrder(supplierDao.find(s.supplierId));
                        order.addLine(currentLine);
                        this.orders.add(order);
                    } else {
                        // the order from the supplier already exists
                        findSupplierOrder(s.supplierId).addLine(currentLine);
                    }
                    if (totalRequiredAmount == 0) {
                        //orderStatus.put(entry.getKey(), true);
                        AmountDiscountPairList.clear();
                        break;
                    }
                }
            }
        }
        // update order history table
        for (SupplierOrder so: this.orders){
            updateOrderHistory(so);
        }
        return this.orders;

    }

    public  List<SupplierOrder> executeOrder(){
        List<SupplierOrder> supplierOrders=productToSupplier();
        return supplierOrders;
    }

//    public void PrintOrderStatus(){
//        for (Integer productId: this.orderStatus.keySet()){
//            int pid=productId;
//            boolean status=this.orderStatus.get(productId);
//            System.out.println(pid + "," + status + "\n");
//        }
//    }
    public void printTotalPaymentsPerSupplier(){
        for (SupplierOrder s: this.orders){
            System.out.println("Total payment for supplier " + s.getSupplier().getSupplierId() + " is " + s.getTotalOrderPayment() + "\n");
        }
    }

    public void printOrders(){
        for (SupplierOrder s1: this.orders)
            s1.printOrder();
    }

    public static HashMap<Integer,Integer> allOrderProductSupId(){
        return orderDao.getAllRecordsByCategoryId();
    }



    public static  boolean addPeriodicOrder(int supplierId, Days shipmentDay, Map<String,Integer> productToAmount) {
        //cheack if exsist db;
        if (periodicOrderDao.find(supplierId,shipmentDay)!=null){return false;}
        //if not exsist create an empty periodicOrder
        PeriodicOrder PO=new PeriodicOrder(supplierDao.find(supplierId),shipmentDay);
        //add products to periodc

        Iterator<Map.Entry<String,Integer>> it=productToAmount.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,Integer>entry = it.next();
            int supCatId=mappingDao.findSupCatID(entry.getKey(), supplierId);
            double productBasePrice=productDao.findPrice(supplierId,supCatId);
            double discount = ktavKamutDao.getDiscount(supplierId, supCatId, entry.getValue());
            String name = productDao.findName(supplierId,supCatId);
            OrderLine newProductLine=new OrderLine(supCatId,name,entry.getValue(),productBasePrice,discount);
            PO.addLine(newProductLine);
        }
        periodicOrderDao.insert(PO);
        return true;
    }

    public static void deleteEntirePeriodicOrder(int supplierId, Days shipmentDay){
        periodicOrderDao.deletePeriodicProduct(supplierId,shipmentDay);
    }

    public static PeriodicOrder findPeriodicOrder(int supplierId, Days shipmentDay){
        return periodicOrderDao.find(supplierId,shipmentDay);
    }

    public static KtavKamot getSupplierKtavKamot(int supplierId){
        return ktavKamutDao.find(supplierId);
    }

    public static int getAmountPeriodicOrderItem(int supplierId, int supplierCategoryId, Days shipmentDay){
        return periodicOrderDao.getAmountFromPeriodicOrder(supplierId, supplierCategoryId, shipmentDay);
    }

    public static void addProductToPeriodicOrder(int supplierId, int supplierCategoryId, int amount, Days shipmentDay) {
        double productBasePrice=productDao.findPrice(supplierId,supplierCategoryId);
        double discount = ktavKamutDao.getDiscount(supplierId, supplierCategoryId, amount);
        String name = productDao.findName(supplierId,supplierCategoryId);
        OrderLine newProductLine=new OrderLine(supplierCategoryId,name,amount,productBasePrice,discount);
        periodicOrderDao.addNewProductToPeriodicOrder(newProductLine,supplierId,shipmentDay);
    }

    public static void removeProductFromPeriodicOrder(int supplierId, int supplierCategoryId, Days shipmentDay){
        periodicOrderDao.deleteProductFromPeriodicOrder(supplierId, supplierCategoryId,shipmentDay);
    }
    public static void updatePeriodicOrderProductAmount(int supplierId, int supplierCategoryId, Days shipmentDay, int amount) {
        periodicOrderDao.updatePeriodicProductAmount(supplierId, supplierCategoryId, shipmentDay, amount);
    }

    public static void updateOrderHistory(SupplierOrder supplierOrder) {
        orderDao.insert(supplierOrder);
    }

    public static List<PeriodicOrder> getPeriodicSuppliersNextDay(Days shipmentDay){
        Days nextDay=Days.values()[shipmentDay.ordinal()+1];
        return periodicOrderDao.getPeriodicOrderBasedDay(nextDay);
    }
}




