package UI.Suppliers;

import java.sql.SQLException;
import java.util.*;

import BL.Suppliers.*;

public class Menu {

    //static List<Supplier>Suppliers=new ArrayList<Supplier>();
    //static List<Product>Products=new ArrayList<Product>();
    //static List<OrderController> OrderHistory=new ArrayList<OrderController>();

    static Map<String, Integer> missingReport = new HashMap<String,Integer>();


    //singleton
    static OrderController orderController = OrderController.getInstance();

    //    private static Supplier findeSupplier(int supplierId) {
//        for (Supplier s : Suppliers) {
//            if (s.getSupplierId() == supplierId) {
//                return s;
//            }
//        }
//        return null;
//    }

    public static void setupInformation() throws InterruptedException {



            // add suppliers and contracts to DB (suppliers)
            Supplier s1 = new Supplier(1, "beershave", true, "sapak1", 10);
            Supplier s2 = new Supplier(2, "telaviv", true, "sapak2", 15);
            Supplier s3 = new Supplier(3, "haifa", true, "sapak3", 10);
            Contract c1 = new Contract(true, PaymentTypes.NET60EOM);
            Contract c2 = new Contract(true, PaymentTypes.NET30EOM);
            Contract c3 = new Contract(false, PaymentTypes.NET);
            s1.setCurrentContract(c1);
            s2.setCurrentContract(c2);
            s3.setCurrentContract(c3);
            SupplierController.addSupplier(s1);
            SupplierController.addSupplier(s2);
            SupplierController.addSupplier(s3);

            // add Total and Payment discounts rules
            SupplierController.addTotalAmountRule(1, 2, 10);
            SupplierController.addTotalAmountRule(1, 1, 5);
            SupplierController.addTotalAmountRule(2, 2, 23.3);
            SupplierController.addTotalPaymentRule(1, 10, 3);
            SupplierController.addTotalPaymentRule(1, 15, 10);
            SupplierController.addTotalPaymentRule(2, 7, 2.5);


            // add product to a supplier and insert to DB (Products, ProductToSupliierCategoryId)
            Product milk_s1 = new Product(1, 10, 5, 10.5, "milk");
            SupplierController.addMapping(1, milk_s1.getSuppCatalogId(), "cl00");
            SupplierController.addProductToContract(milk_s1);

            Product milk_s2 = new Product(2, 1000, 10, 1.5, "milk");
            SupplierController.addMapping(2, milk_s2.getSuppCatalogId(), "cl00");
            SupplierController.addProductToContract(milk_s2);

            Product bread_s1 = new Product(1, 400, 100, 0, "bread");
            SupplierController.addMapping(1, bread_s1.getSuppCatalogId(), "cl22");
            SupplierController.addProductToContract(bread_s1);


//        Product coteg_s1=new Product(1,100, 4.99,5, "coteg");
//        SupplierController.addMapping(1,coteg_s1.getSuppCatalogId(),"p3");
//        SupplierController.addProductToContract(coteg_s1);
//
//
//        Product coteg_s2=new Product(2,50, 5.99,5, "coteg");
//        SupplierController.addMapping(2,coteg_s2.getSuppCatalogId(),"p3");
//        SupplierController.addProductToContract(coteg_s2);
//
//        Product coteg_s3=new Product(3,10, 3.99,5, "coteg");
//        SupplierController.addMapping(3,coteg_s3.getSuppCatalogId(),"p3");
//        SupplierController.addProductToContract(coteg_s3);


            // add product amount discount

            SupplierController.addProductToAmountRole(1, SupplierController.getSupplierCategoryId("cl00", 1), 3, 10);
            SupplierController.addProductToAmountRole(1, SupplierController.getSupplierCategoryId("cl22", 1), 5, 15);
            SupplierController.addProductToAmountRole(2, SupplierController.getSupplierCategoryId("cl00", 2), 5, 15);


    }
    public static void addSupplierAndContract() throws SQLException {
        Scanner sc= new Scanner(System.in);
        System.out.println("BL.Suppliers.Supplier Format: String name String address int supplierId boolean mobility int shipmentTime");
        String address=sc.nextLine();
        String name = sc.nextLine();
        int supplierId = sc.nextInt();
        boolean mobility = sc.nextBoolean();
        int shipmentTime = sc.nextInt();
        Supplier newSupplier = new Supplier(supplierId,address,mobility,name, shipmentTime);
        attachContract(newSupplier);
        SupplierController.addSupplier(newSupplier);
    }

    public static void attachContract(Supplier s1){
        Scanner sc= new Scanner(System.in);
        System.out.println("BL.Suppliers.Contract Format: BL.Suppliers.PaymentTypes BL.Suppliers.PaymentTypes.NET30EOM/NET60EOM/NET, boolean constantDaysShipment");
        PaymentTypes pay = PaymentTypes.valueOf(sc.nextLine());
        boolean constantDaysShipment = sc.nextBoolean();
        Contract c = new Contract(constantDaysShipment,pay);
        s1.setCurrentContract(c);
    }
    //
//    public static void printSuppliers(){
//        for (Supplier s : Suppliers){
//            System.out.println(s);
//        }
//    }
//
    public static void addProduct() throws SQLException, InterruptedException {
        Scanner sc= new Scanner(System.in);
        System.out.println("ProductFormat Format: String name int supplierId String itemId int quantity int basePrice double weight");
        String name = sc.nextLine();
        int supplierId= sc.nextInt();
        String itemId = sc.next();
        int quantity = sc.nextInt();
        double basePrice =sc.nextDouble();
        double weight = sc.nextDouble();
        Product p = new Product(supplierId,quantity,basePrice,weight,name);
        SupplierController.addProductToContract(p);
        SupplierController.addMapping(supplierId,p.getSuppCatalogId(),itemId);
    }

    //    public static void addProductToContract(Product p){
//        Scanner sc= new Scanner(System.in);
//        System.out.println("product to contract Format: int supplierId");
//        int supplierId = sc.nextInt();
//        for (Supplier s : Suppliers){
//            if(s.getSupplierId()==supplierId) {
//                s.getCurrentContract().addProduct(p);
//                break;
//            }
//        }
//    }
//
//
    public static void newProductAmountDiscount() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("product to contract Format: int supplier int itemId int amountOfProduct double percentageDiscount");
        int supplierId = sc.nextInt();
        String itemId = sc.next();
        int amountOfProduct = sc.nextInt();
        double percentageDiscount = sc.nextDouble();
        SupplierController.addProductToAmountRole(supplierId,SupplierController.getSupplierCategoryId(itemId,supplierId),amountOfProduct, percentageDiscount);
//        Product p = SupplierController.getProduct(SupplierController.getSupplierCategoryId(itemId,supplierId),supplierId);
//        SupplierController.getSupplier(supplierId).getCurrentContract().getKtavKamot().updateProductAmountDiscount(p, amountOfProduct,percentageDiscount);
    }

    public static void updateProductAmountDiscount() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("update product to contract Format: int supplier int itemId int amountOfProduct double percentageDiscount");
        int supplierId = sc.nextInt();
        String itemId = sc.nextLine();
        int amountOfProduct = sc.nextInt();
        double percentageDiscount = sc.nextDouble();
        SupplierController.updateProductToAmountRole(supplierId,SupplierController.getSupplierCategoryId(itemId,supplierId),amountOfProduct, percentageDiscount);
    }


        public static void addTotalAmountDiscount() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter supplier ID:");
        int supplierId = sc.nextInt();
        System.out.println("Enter amount:");
        int amount = sc.nextInt();
        System.out.println("Enter percentage discount:");
        double percentageDiscount = sc.nextDouble();
        SupplierController.addTotalAmountRule(supplierId,amount,percentageDiscount);
    }

    public static void addTotalPaymentDiscount() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter supplier ID:");
        int supplierId = sc.nextInt();
        System.out.println("Enter payment:");
        double payment = sc.nextDouble();
        System.out.println("Enter percentage discount:");
        double percentageDiscount = sc.nextDouble();
        SupplierController.addTotalPaymentRule(supplierId, payment, percentageDiscount);

    }

    public static void executeOrderBasedOnMissingReport(Map<String, Integer> missingReport) throws SQLException {
        orderController.setProductMissingReport(missingReport);
        orderController.executeOrder();
    }
    public static void createMissingReport(){
        boolean status=true;
        Scanner sc = new Scanner(System.in);
        String itemId;
        int missingAmount=0;
        while (status) {
            System.out.println("add product to missing report, format: int itemId int amountMissing. if done, enter -1");
            itemId=sc.nextLine();
            missingAmount=sc.nextInt();
            if(itemId.equals("-1") || missingAmount==-1) {
                status = false;
                break;
            }
            missingReport.put(itemId, missingAmount);
        }
    }
//
    public static void addNewPeriodicOrder() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int SupplierId;
        System.out.println("enter supplierId");
        SupplierId = sc.nextInt();
        if (SupplierController.checkSupplierIsPeriodic(SupplierId)) {
            Days shipmentDay;
            System.out.println("enter shipmentDay");
            // also check the days
            shipmentDay = Days.valueOf(sc.next());

            Map<String, Integer> productToAmount = new HashMap<String, Integer>();
            boolean status = true;
            String itemId;
            int amount;
            while (status) {
                System.out.println("add product and amount, format: int itemId int amount. if done, enter -1");
                itemId = sc.next();
                amount = sc.nextInt();
                if (itemId.equals("-1") || amount == -1) {
                    status = false;
                    break;
                }
                if (checkSupplyConditions(itemId, amount, SupplierId))
                    productToAmount.put(itemId, amount);
            }
            orderController.addPeriodicOrder(SupplierId, shipmentDay, productToAmount);
        }
        else
            System.out.println("The supplierId you entered isn't a periodic one");
    }

    private static boolean checkSupplyConditions(String itemId, int amount, int supplierId) throws SQLException {
        Product p = SupplierController.getProduct(SupplierController.getSupplierCategoryId(itemId, supplierId), supplierId);
        if (p == null) {
            System.out.println("the supplier cannot supply this item based on contract agreement");
            return false;
        }
        if (!SupplierController.checkProductAmountSupply(p, amount)){
            System.out.println("the supplier cannot supply the require amount of product you enter based on contract agreement");
            return false;
        }
        return true;
    }


    public static void executePeriodicOrder(){
        Days shipmentDay;
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the current day");
        shipmentDay = Days.valueOf(sc.next());
        List<PeriodicOrder> prs = OrderController.getPeriodicSuppliersNextDay(shipmentDay);
        for (PeriodicOrder pr: prs){
            pr.printOrder();
        }
        int ch = 0;
        System.out.println("" +
                "1. ack and execute orders\n" +
                "2. update or exit the orders\n");
        ch = sc.nextInt();
        switch (ch) {
            case 1:
                for (PeriodicOrder pr: prs){
                    OrderController.updateOrderHistory(pr);
                }
                break;
            case 2:
                updatePeriodicOrder();
                break;
        }
    }

    public static void updatePeriodicOrder() {
        // sub menu
        int ch = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("" +
                "1. add product supply mapping\n" +
                "2. delete product supply mapping\n" +
                "3. change supply amount of existing periodic product");
        ch = sc.nextInt();
        switch (ch) {
            case 1:
                System.out.println("add new product to periodic order Format: Day shipmentDay int supplier String itemId int amountOfProduct");

                String shipment = sc.next();
                int supplierIdAdd = sc.nextInt();
                String itemIdAdd = sc.next();
                int amountOfProduct = sc.nextInt();
                Days shipmentDayAdd = Days.valueOf(shipment);
                OrderController.addProductToPeriodicOrder(supplierIdAdd, SupplierController.getSupplierCategoryId(itemIdAdd, supplierIdAdd), amountOfProduct, shipmentDayAdd);
                break;
            case 2:
                System.out.println("delete product from periodic order Format: Day shipmentDay int supplier String itemId");
                Days shipmentDayDelete = Days.valueOf(sc.next());
                int supplierIdDelete = sc.nextInt();
                String itemIdDelete = sc.next();
                OrderController.removeProductFromPeriodicOrder(supplierIdDelete, SupplierController.getSupplierCategoryId(itemIdDelete, supplierIdDelete), shipmentDayDelete);
                break;
            case 3:
                System.out.println("change amount of product of periodic order Format: Day shipmentDay int supplier String itemId int amount");
                Days shipmentDayEdit = Days.valueOf(sc.next());
                int supplierIdEdit = sc.nextInt();
                String itemIdEdit = sc.next();
                int amountEdit = sc.nextInt();
                OrderController.updatePeriodicOrderProductAmount(supplierIdEdit, SupplierController.getSupplierCategoryId(itemIdEdit, supplierIdEdit), shipmentDayEdit, amountEdit);
        }
    }

    public static void SupplierMenu() throws SQLException, InterruptedException {

        int ch = 0;
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("" +
                    "1. add new supplier and a contract\n" +
                    "2. add new product to ktavKamot for a supplier\n" +
                    "3. add new product to amount discount for a supplier\n" +
                    "4, edit product to amount discount for a supplier\n" +
                    "5. add policy rule to total amount discount for a supplier\n" +
                    "6. add policy rule to total payment discount for a supplier\n" +
                    "7. add new periodic order\n" +
                    "8. update periodic order\n" +
                    "9. execute periodic order for next day\n" +
                    "10. exit");
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    addSupplierAndContract();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    newProductAmountDiscount();
                    break;
                case 4:
                    updateProductAmountDiscount();
                    break;
                case 5:
                    addTotalPaymentDiscount();
                    break;
                case 6:
                    addTotalAmountDiscount();
                    break;
                case 7:
                    addNewPeriodicOrder();
                    break;
                case 8:
                    updatePeriodicOrder();
                    break;
                case 9:
                    executePeriodicOrder();
                    break;
//                    createMissingReport();
//                    executeOrderBasedOnMissingReport(missingReport);
                case 10:
                    flag = false;
                    break;
            }
        }
    }
}
