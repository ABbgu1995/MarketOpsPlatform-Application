package UI.Inventory;
import BL.Inventory.*;
import BL.Suppliers.OrderController;
import Service.ServiceInventory;
import Service.ServiceSupplier;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static UI.Suppliers.Menu.SupplierMenu;
import static UI.Suppliers.Menu.updatePeriodicOrder;

public class UserInterface {

    public static void startManager() throws SQLException, InterruptedException {
        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerString = new Scanner(System.in);
        int choice_while = 0;
        ItemController itemController = ItemController.getInstance();
        BranchController branchController = BranchController.getInstance();
        DiscountController discountController = DiscountController.getInstance();
        ReportController reportController = ReportController.getInstance();
        discountController.allBranchSetDiscount();
        while (choice_while == 0) {
            System.out.println("Choose an option:");
            System.out.println("1. Manager options");
            System.out.println("2. Supplier manager options");
            System.out.println("3. Storekeeper options");
            System.out.println("0. Exit");
            String choice = scannerString.nextLine();
            switch (choice) {
                case "1":
                    int choice_while1 = 0;
                    while (choice_while1 == 0) {
                        System.out.println("Choose an option:");
                        System.out.println("1. Show details of item");
                        System.out.println("2. Show amount of item");
                        System.out.println("3. Show report");
                        System.out.println("4. Show all item in branch");
                        System.out.println("0. Exit");
                        String choice1 = scannerString.nextLine();
                        switch (choice1) {
                            case "1":
                                System.out.println("Enter item ID");
                                int itemId3;
                                try {
                                    itemId3 = scannerInt.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input");
                                    scannerInt.next();
                                    break;
                                }
                                System.out.println("Enter branch number");
                                int branchNum3;
                                try {
                                    branchNum3 = scannerInt.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input");
                                    scannerInt.next();
                                    break;
                                }
                                Item item3 = branchController.foundItemInBranch(branchNum3, itemId3);
                                if (item3 == null) {
                                    System.out.println("the item is not exist");
                                    break;
                                }
                                System.out.println("Item catalog number: " + item3.getCatalogNumber());
                                System.out.println("Item Id: " + item3.getItemID());
                                System.out.println("Item maker: " + item3.getMaker());
                                System.out.println("Item supplier: " + item3.getSupplier());
                                System.out.println("Item supplier price: " + item3.getSupplierPrice());
                                System.out.println("Item store price: " + item3.getItemStorePrice());
                                System.out.println("Item store price after discount: " + item3.getItemStorePriceWithDiscount());
                                System.out.println("Item category: " + item3.getCategory());
                                System.out.println("Item sub category: " + item3.getSubCategory());
                                System.out.println("Item sub sub category: " + item3.getSubsubCategory());
                                System.out.println("Item expiredDate: " + item3.getExpiredDate());
                                System.out.println("Item damage: " + item3.getDamage() + ", the damage is " + item3.getDamageTypeItem());
                                System.out.println("Item location is: " + item3.getItemPlace());
                                break;
                            case "2":
                                System.out.println("Enter branch number");
                                int branchNum4;
                                try {
                                    branchNum4 = scannerInt.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input");
                                    scannerInt.next();
                                    break;
                                }
                                System.out.println("Enter item catalog number ");
                                String itemCatalogNum4 = scannerString.nextLine();
                                Map<String, Integer> map1 = branchController.countItemsInBranch(branchNum4);
                                if (map1 == null) {
                                    System.out.println("the branch is not exist");
                                    break;
                                }
                                int num4;
                                if (map1.get(itemCatalogNum4) == null) {
                                    num4 = 0;
                                } else {
                                    num4 = map1.get(itemCatalogNum4);
                                }
                                System.out.println("The amount of the item in branch is: " + num4);
                                break;
                            case "3":
                                System.out.println("Enter branch number");
                                int branchNum7;
                                try {
                                    branchNum7 = scannerInt.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input");
                                    scannerInt.next();
                                    break;
                                }
                                Branch branch6 = branchController.getBranchByNumber(branchNum7);
                                if (branch6 == null) {
                                    System.out.println("There is no branch with this number ");
                                    break;
                                }
                                System.out.println("Enter report ID");
                                int reportID;
                                try {
                                    reportID = scannerInt.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input");
                                    scannerInt.next();
                                    break;
                                }
                                System.out.println("Choose the report type: ");
                                System.out.println("1. Inventory");
                                System.out.println("2. Damage");
                                System.out.println("3. Shortage");
                                String user_choice7 = scannerString.nextLine();
                                Formatter formatter = new Formatter();
                                switch (user_choice7) {
                                    case "1":
                                        Map<String, Integer> inventory_rep = reportController.getInventoryReport(branchNum7, reportID).getAllInventory();
                                        System.out.println("Which items do you want?: ");
                                        System.out.println("1. all");
                                        System.out.println("2. I want to choose");
                                        String user_choice61 = scannerString.nextLine();
                                        switch (user_choice61) {
                                            case "1":
                                                formatter.format("%-15s %-15s\n", "Catalog_Number", "Amount");
                                                formatter.format("--------------------%n");
                                                // Format the table rows
                                                for (Map.Entry<String, Integer> entry : inventory_rep.entrySet()) {
                                                    formatter.format("%-15s %-15d\n", entry.getKey(), entry.getValue());
                                                }
                                                String formattedString = formatter.toString();
                                                System.out.println(formattedString);
                                                break;
                                            case "2":
                                                Map<String, List<String>> mapCategoryCatalog = branch6.getMapByCategoryCatalogNumber();
                                                System.out.println("Enter the category you want (separated by spaces): ");
                                                String input = scannerString.nextLine();
                                                String[] stringsOfCategories = input.split("\\s+");
                                                Map<String, Integer> itemInMapInventory = new HashMap<>();
                                                for (String s : stringsOfCategories) {
                                                    List<String> value = mapCategoryCatalog.get(s);
                                                    if (value != null) {
                                                        for (String str : value) {
                                                            if (inventory_rep.containsKey(str)) {
                                                                itemInMapInventory.put(str, inventory_rep.get(str));
                                                            }
                                                        }
                                                    }
                                                }
                                                // Format the table header
                                                formatter.format("%-15s %-15s\n", "Catalog_Number", "Amount");
                                                formatter.format("--------------------%n");
                                                // Format the table rows
                                                if (itemInMapInventory.size() != 0) {
                                                    for (Map.Entry<String, Integer> entry : itemInMapInventory.entrySet()) {
                                                        formatter.format("%-15s %-15d\n", entry.getKey(), entry.getValue());
                                                    }
                                                    String formattedString1 = formatter.toString();
                                                    System.out.println(formattedString1);
                                                } else {
                                                    System.out.println("There are no such categories in the store ");
                                                }
                                                break;
                                            default:
                                                System.out.println("Invalid choice ");
                                                break;
                                        }
                                        break;
                                    case "2":

                                        Map<Item, damageType> damage_rep = reportController.getDamageReport(branchNum7, reportID).getAllDamage();
                                        formatter.format("%-15s %-15s %-15s %-15s %-15s %-15s\n", "Catalog_Number", "Category ",
                                                "ItemID", "Damage/Expired ", "Damage_type", "Days_expired");
                                        formatter.format("-----------------------------------------------------------------------------------------%n");
                                        for (Map.Entry<Item, damageType> entry : damage_rep.entrySet()) {
                                            formatter.format("%-15s %-15s %-15s %-15s %-15s %-15s\n", entry.getKey().getCatalogNumber(),
                                                    entry.getKey().getCategory(), entry.getKey().getItemID(),
                                                    entry.getValue(), entry.getKey().getDamageTypeItem(),
                                                    itemController.checkHowManyDayExpired(entry.getKey()));
                                        }
                                        String formattedString2 = formatter.toString();
                                        System.out.println(formattedString2);
                                        break;
                                    case "3":
                                        Map<String, Integer> shortage_rep = reportController.getShortageReport(reportID, branchNum7).getAllShortage();
                                        // Format the table header
                                        formatter.format("%-20s%-10s%n", "Catalog Number", "amount");
                                        formatter.format("----------------------------%n");
                                        if (shortage_rep == null) {
                                            System.out.println("there is no shortage in branch!");
                                        } else {
                                            // Format the table rows
                                            for (Map.Entry<String, Integer> str : shortage_rep.entrySet()) {
                                                formatter.format("%-20s%-10s%n", str.getKey(), str.getValue());
                                            }
                                            String formattedString3 = formatter.toString();
                                            System.out.println(formattedString3);
                                        }
                                }
                                break;
                            case "4":
                                System.out.println("Enter branch number");
                                int branchNum9;
                                try {
                                    branchNum9 = scannerInt.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input");
                                    scannerInt.next();
                                    break;
                                }
                                Branch branch9 = branchController.getBranchByNumber(branchNum9);
                                if (branch9 == null) {
                                    System.out.println("There is no branch with this number ");
                                    break;
                                }
                                List<Item> all_in_branch = branch9.getItemsInBranch();
                                System.out.println("Item in branch: ");
                                for (Item item1 : all_in_branch) {
                                    System.out.println("Item Id: " + item1.getItemID() + " , item catalog num: " + item1.getCatalogNumber());
                                }
                                break;
                            case "0":
                                choice_while1++;
                                break;
                            default:
                                System.out.println("Invalid choice");
                                break;

                        }
                    }
                    break;
                case "2":
                    startStorekeeper();
                    break;
                case "3":
                    SupplierMenu();
                    break;
                case "0":
                    choice_while++;
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }


    public static void startStorekeeper() {
        Scanner scannerDouble = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerString = new Scanner(System.in);
        int choice_while = 0;
        ItemController itemController = ItemController.getInstance();
        BranchController branchController = BranchController.getInstance();
        DiscountController discountController = DiscountController.getInstance();
        ReportController reportController = ReportController.getInstance();


        discountController.allBranchSetDiscount();

        while (choice_while == 0) {
            System.out.println("Choose an option:");
            System.out.println("1. Add item");
            System.out.println("2. Delete item");
            System.out.println("3. Show details of item");
            System.out.println("4. Show amount of item");
            System.out.println("5. Create discount");
            System.out.println("6. Create report");
            System.out.println("7. Edit item");
            System.out.println("8. Print all item in branch");
            System.out.println("9. Issuing an order from a supplier due to a shortage");
            System.out.println("10. Edit a periodic order from a supplier");
            System.out.println("0. Exit");
            String choice = scannerString.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Enter the branch number");
                    int branchNum = 0;
                    try {
                        branchNum = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }
                    Branch curr_branch = branchController.getBranchByNumber(branchNum);
                    if (curr_branch == null) {
                        System.out.println("There is no branch with this number ");
                        break;
                    }
                    System.out.println("Enter the catalog number of the item");
                    String catalogNum = scannerString.nextLine();
                    System.out.println("Enter the name of the item maker");
                    String makerName = scannerString.nextLine();
                    System.out.println("Enter the name of the item supplier");
                    String supplierName = scannerString.nextLine();
                    System.out.println("Enter the supplier price of the item");
                    double supplierPrice;
                    try {
                        supplierPrice = scannerDouble.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerDouble.next();
                        break;
                    }
                    System.out.println("Enter the store price of the item");
                    double storePrice;
                    try {
                        storePrice = scannerDouble.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerDouble.next();
                        break;
                    }
                    System.out.println("The item has expired Date?: ");
                    System.out.println("1. yes");
                    System.out.println("2. no");
                    String user_choice11 = scannerString.nextLine();
                    Date expiredDate = null;
                    switch (user_choice11) {
                        case "1":
                            System.out.println("Enter the item expired Date yyyy-MM-dd: ");
                            String inputDate = scannerString.nextLine();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                expiredDate = dateFormat.parse(inputDate);
                            } catch (ParseException e) {
                                System.out.println("Invalid date format");
                                break;
                            }
                            break;
                        case "2":
                            break;
                        default:
                            System.out.println("incorrect number ");
                            break;
                    }
                    System.out.println("Enter the category of the item");
                    String category = scannerString.nextLine();
                    System.out.println("Enter the sub category of the item");
                    String subcategory = scannerString.nextLine();
                    System.out.println("Enter the sub sub Category of the item");
                    String subsubCategory = scannerString.nextLine();

//                    Item new_item = branchController.createItemToBranch(branchNum, catalogNum, makerName, supplierName, supplierPrice, storePrice, expiredDate, category, subcategory, subsubCategory);
                    System.out.println("Enter the amount of min");
                    int minAmount = 0;
                    try {
                        minAmount = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }
//                    branchController.updateMinAmountOfCatalog(new_item, branchNum, minAmount);
                    System.out.println("Where do you want to add the item?");
                    System.out.println("1. in store");
                    System.out.println("2. in stock");
                    String userChoice = scannerString.nextLine();
                    place userPlace = place.stock;
                    switch (userChoice) {
                        case "1":
                            userPlace = place.store;
//                            itemController.setItemPlace(place.store, new_item);
                            System.out.println("the item set in store");
                            break;
                        case "2":
                            System.out.println("the item set in stock");
                            break;
                        default:
                            System.out.println("Invalid choice");
                            break;
                    }
                    placeNewItemInBranch(branchNum, catalogNum, makerName, supplierName, supplierPrice, storePrice, expiredDate, category, subcategory, subsubCategory, minAmount, userPlace);
                    break;

                case "2":
                    System.out.println("Enter item ID");
                    int itemId2;
                    try {
                        itemId2 = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }
                    System.out.println("Enter branch number");
                    int branchNum2;
                    try {
                        branchNum2 = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }

                    Item item2 = branchController.foundItemInBranch(branchNum2, itemId2);
                    if (item2 == null) {
                        System.out.println("The item is not exist");
                        break;
                    }
                    boolean remove_status = branchController.deleteItemFromBranch(item2, branchNum2);
                    if (remove_status) {
                        System.out.println("The item has been removed");
                    } else {
                        System.out.println("The object was not removed");
                    }
                    break;

                case "3":
                    System.out.println("Enter item ID");
                    int itemId3;
                    try {
                        itemId3 = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }
                    System.out.println("Enter branch number");
                    int branchNum3;
                    try {
                        branchNum3 = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }
                    Item item3 = branchController.foundItemInBranch(branchNum3, itemId3);
                    if (item3 == null) {
                        System.out.println("the item is not exist");
                        break;
                    }
                    System.out.println("Item catalog number: " + item3.getCatalogNumber());
                    System.out.println("Item Id: " + item3.getItemID());
                    System.out.println("Item maker: " + item3.getMaker());
                    System.out.println("Item supplier: " + item3.getSupplier());
                    System.out.println("Item supplier price: " + item3.getSupplierPrice());
                    System.out.println("Item store price: " + item3.getItemStorePrice());
                    System.out.println("Item store price after discount: " + item3.getItemStorePriceWithDiscount());
                    System.out.println("Item category: " + item3.getCategory());
                    System.out.println("Item sub category: " + item3.getSubCategory());
                    System.out.println("Item sub sub category: " + item3.getSubsubCategory());
                    System.out.println("Item expiredDate: " + item3.getExpiredDate());
                    System.out.println("Item damage: " + item3.getDamage() + ", the damage is " + item3.getDamageTypeItem());
                    System.out.println("Item location is: " + item3.getItemPlace());
                    break;

                case "4":
                    System.out.println("Enter branch number");
                    int branchNum4;
                    try {
                        branchNum4 = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }
                    System.out.println("Enter item catalog number ");
                    String itemCatalogNum4 = scannerString.nextLine();
                    Map<String, Integer> map1 = branchController.countItemsInBranch(branchNum4);
                    if (map1 == null) {
                        System.out.println("the branch is not exist");
                        break;
                    }
                    int num4;
                    if (map1.get(itemCatalogNum4) == null) {
                        num4 = 0;
                    } else {
                        num4 = map1.get(itemCatalogNum4);
                    }
                    System.out.println("The amount of the item in branch is: " + num4);
                    break;
                case "5":
                    System.out.println("Enter branch number");
                    int branchNum6;
                    try {
                        branchNum6 = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }
                    System.out.println("Enter Discount Percent");
                    double Discountpercent6;
                    try {
                        Discountpercent6 = scannerDouble.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerDouble.next();
                        break;
                    }
                    System.out.println("Enter the item DateFrom (dd/MM/yyyy): ");
                    String inputDateFrom6 = scannerString.nextLine();
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateFrom6;
                    try {
                        dateFrom6 = dateFormat1.parse(inputDateFrom6);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format");
                        break;
                    }

                    System.out.println("Enter the item DateTo (dd/MM/yyyy): ");
                    String inputDateTo6 = scannerString.nextLine();
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateTo6;
                    try {
                        dateTo6 = dateFormat2.parse(inputDateTo6);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format");
                        break;
                    }
                    System.out.println("Choose the type: ");
                    System.out.println("1. Category");
                    System.out.println("2. Catalog Number");
                    String user_choice6 = scannerString.nextLine();
                    switch (user_choice6) {
                        case "1":
                            System.out.println("Enter the category: ");
                            String category6 = scannerString.nextLine();
                            status curr_sts = discountController.createNewDiscountByCategory(branchNum6, Discountpercent6, dateFrom6, dateTo6, category6);
                            if (curr_sts == status.failure) {
                                System.out.println("The discount was not initialized, the branch does not exist ");
                                break;
                            }
                            System.out.println("The discount for the category: " + category6 + " is set ");
                            break;
                        case "2":
                            System.out.println("Enter the catalog number: ");
                            String catalogNumber6 = scannerString.nextLine();
                            status curr_sts1 = discountController.createNewDiscountByCatalogNumber(branchNum6, Discountpercent6, dateFrom6, dateTo6, catalogNumber6);
                            if (curr_sts1 == status.failure) {
                                System.out.println("The discount was not initialized, the branch does not exist ");
                                break;
                            }
                            System.out.println("The discount for the catalog number: " + catalogNumber6 + " is set ");
                            break;
                        default:
                            System.out.println("Invalid choice ");
                            break;
                    }
                    break;
                case "6":
                    System.out.println("Enter branch number");
                    int branchNum7;
                    try {
                        branchNum7 = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }
                    Branch branch6 = branchController.getBranchByNumber(branchNum7);
                    if (branch6 == null) {
                        System.out.println("There is no branch with this number ");
                        break;
                    }
                    System.out.println("Choose the report type: ");
                    System.out.println("1. Inventory");
                    System.out.println("2. Damage");
                    System.out.println("3. Shortage");
                    String user_choice7 = scannerString.nextLine();
                    Formatter formatter = new Formatter();
                    switch (user_choice7) {
                        case "1":
                            Map<String, Integer> inventory_rep = reportController.createNewInventoryReport(branchNum7);
                            System.out.println("Which items do you want?: ");
                            System.out.println("1. all");
                            System.out.println("2. I want to choose");
                            String user_choice61 = scannerString.nextLine();
                            switch (user_choice61) {
                                case "1":
                                    formatter.format("%-15s %-15s\n", "Catalog_Number", "Amount");
                                    formatter.format("--------------------%n");
                                    // Format the table rows
                                    for (Map.Entry<String, Integer> entry : inventory_rep.entrySet()) {
                                        formatter.format("%-15s %-15d\n", entry.getKey(), entry.getValue());
                                    }
                                    String formattedString = formatter.toString();
                                    System.out.println(formattedString);
                                    break;
                                case "2":
                                    Map<String, List<String>> mapCategoryCatalog = branch6.getMapByCategoryCatalogNumber();
                                    System.out.println("Enter the category you want (separated by spaces): ");
                                    String input = scannerString.nextLine();
                                    String[] stringsOfCategories = input.split("\\s+");
                                    Map<String, Integer> itemInMapInventory = new HashMap<>();
                                    for (String s : stringsOfCategories) {
                                        List<String> value = mapCategoryCatalog.get(s);
                                        if (value != null) {
                                            for (String str : value) {
                                                if (inventory_rep.containsKey(str)) {
                                                    itemInMapInventory.put(str, inventory_rep.get(str));
                                                }
                                            }
                                        }
                                    }
                                    // Format the table header
                                    formatter.format("%-15s %-15s\n", "Catalog_Number", "Amount");
                                    formatter.format("--------------------%n");
                                    // Format the table rows
                                    if (itemInMapInventory.size() != 0) {
                                        for (Map.Entry<String, Integer> entry : itemInMapInventory.entrySet()) {
                                            formatter.format("%-15s %-15d\n", entry.getKey(), entry.getValue());
                                        }
                                        String formattedString1 = formatter.toString();
                                        System.out.println(formattedString1);
                                    } else {
                                        System.out.println("There are no such categories in the store ");
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid choice ");
                                    break;
                            }
                            break;
                        case "2":
                            Map<Item, damageType> damage_rep = reportController.createNewDamageReport(branchNum7);
                            formatter.format("%-15s %-15s %-15s %-15s %-15s %-15s\n", "Catalog_Number", "Category ",
                                    "ItemID", "Damage/Expired ", "Damage_type", "Days_expired");
                            formatter.format("-----------------------------------------------------------------------------------------%n");
                            for (Map.Entry<Item, damageType> entry : damage_rep.entrySet()) {
                                formatter.format("%-15s %-15s %-15s %-15s %-15s %-15s\n", entry.getKey().getCatalogNumber(),
                                        entry.getKey().getCategory(), entry.getKey().getItemID(),
                                        entry.getValue(), entry.getKey().getDamageTypeItem(),
                                        itemController.checkHowManyDayExpired(entry.getKey()));
                            }
                            String formattedString2 = formatter.toString();
                            System.out.println(formattedString2);
                            break;
                        case "3":
                            Map<String, Integer> shortage_rep = reportController.createNewShortageReport(branchNum7);

                            // Format the table header
                            formatter.format("%-20s%-10s%n", "Catalog Number", "amount");
                            formatter.format("----------------------------%n");
                            if (shortage_rep == null) {
                                System.out.println("there is no shortage in branch!");
                            } else {
                                // Format the table rows
                                for (Map.Entry<String, Integer> str : shortage_rep.entrySet()) {
                                    formatter.format("%-20s%-10s%n", str.getKey(), str.getValue());
                                }
                                String formattedString3 = formatter.toString();
                                System.out.println(formattedString3);
                            }

                            break;
                        default:
                            System.out.println("Invalid choice ");
                            break;
                    }
                    break;
                case "7":
                    System.out.println("Enter item ID");
                    int itemId8;
                    try {
                        itemId8 = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }
                    System.out.println("Enter branch number");
                    int branchNum8;
                    try {
                        branchNum8 = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }
                    Item item8 = branchController.foundItemInBranch(branchNum8, itemId8);
                    Branch branch8 = branchController.getBranchByNumber(branchNum8);
                    if (branch8 == null) {
                        System.out.println("There is no branch with this number ");
                        break;
                    }
                    System.out.println("What would you like to edit in the item?: ");
                    System.out.println("1. Moving the item from the stock to the store");
                    System.out.println("2. Set damage/ expire date ");
                    String user_choice8 = scannerString.nextLine();
                    switch (user_choice8) {
                        case "1":
                            itemController.setItemPlace(place.store, item8);
                            System.out.println("The item " + itemId8 + " has been moved from the stock to the store ");
                            break;
                        case "2":
                            System.out.println("Choose the damage type: ");
                            System.out.println("1. ExpireDay");
                            System.out.println("2. Damage");
                            String user_choice5 = scannerString.nextLine();
                            switch (user_choice5) {
                                case "1":
                                    itemController.setItemDamage(damageType.ExpiredDay, item8);
                                    System.out.println("the expire date of item " + itemId8 + " is set ");
                                    break;
                                case "2":
                                    itemController.setItemDamage(damageType.Damage, item8);
                                    System.out.println("Enter the damage ");
                                    String new_dam = scannerString.nextLine();
                                    itemController.setItemDamageName(new_dam, item8);
                                    System.out.println("the damage of item " + itemId8 + " is set and the damage type is " + new_dam);
                                    break;
                                default:
                                    System.out.println("Invalid choice ");
                                    break;
                            }
                            break;
                        default:
                            System.out.println("Invalid choice ");
                            break;
                    }
                    break;
                case "8":
                    System.out.println("Enter branch number");
                    int branchNum9;
                    try {
                        branchNum9 = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }
                    Branch branch9 = branchController.getBranchByNumber(branchNum9);
                    if (branch9 == null) {
                        System.out.println("There is no branch with this number ");
                        break;
                    }
                    List<Item> all_in_branch = branch9.getItemsInBranch();
                    System.out.println("Item in branch: ");
                    for (Item item1 : all_in_branch) {
                        System.out.println("Item Id: " + item1.getItemID() + " , item catalog num: " + item1.getCatalogNumber());
                    }
                    break;

                case "9":
                    System.out.println("Enter branch number");
                    int branchNum10;
                    try {
                        branchNum10 = scannerInt.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scannerInt.next();
                        break;
                    }
                    OrderController orderController = OrderController.getInstance();
//                    Map<String, Integer> shortage_rep = reportController.createNewShortageReport(branchNum10);
                    Map<String, Integer> shortage_rep = ServiceInventory.getInstance().getShortageReportMap(branchNum10);
                    if (shortage_rep != null) {
                        // service
                        ServiceSupplier.getInstance().executeShortageOrderService(shortage_rep);

                    }
                    else {
                        System.out.println("There is no branch with this number ");
                        break;
                    }
                    break;
                case "10":
                    // service
                        ServiceSupplier.getInstance().updatePeriodicOrderService();
                    break;
                case "0":
                    System.out.println("Exiting the program");
                    choice_while++;
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }


    /*this function update the database of the system*/
    public static void upDateDataBase(){
        BranchController branchController2 = BranchController.getInstance();
        ItemController itemController2 = ItemController.getInstance();

        Map<String , Integer> mapOfMin_branch1  = new HashMap<>();
        Map<String , Integer> mapOfMin_branch2  = new HashMap<>();

        mapOfMin_branch1.put("mh00", 0);
        mapOfMin_branch1.put("mh22", 0);
        mapOfMin_branch1.put("mh44", 1);
        mapOfMin_branch1.put("sh00", 0);
        mapOfMin_branch1.put("sh22", 0);
        mapOfMin_branch1.put("sh33", 0);
        mapOfMin_branch1.put("cl00", 0);
        mapOfMin_branch1.put("cl22", 3);

        mapOfMin_branch2.put("mh11", 0);
        mapOfMin_branch2.put("mh33", 0);
        mapOfMin_branch2.put("sh11", 2);
        mapOfMin_branch2.put("cl00", 0);
        mapOfMin_branch2.put("cl11", 0);


        branchController2.createNewBranch(1, mapOfMin_branch1);
        Branch branch1 = branchController2.getBranchByNumber(1);
        branchController2.createNewBranch(2, mapOfMin_branch2);
        Branch branch2 = branchController2.getBranchByNumber(2);




        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 10, 0, 0, 0);
        Date newYears = calendar.getTime();
        Item it1 = branchController2.createItemToBranch(1 , "mh00", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears , "mutzar_halav", "halav",
                "1 liter" );
        calendar.set(2023, Calendar.MAY, 22, 0, 0, 0);
        Date newYears2 = calendar.getTime();
        Item it2 =branchController2.createItemToBranch(2, "mh11", "tnuva", "tnuva_supplier", 5.50, 7.20,newYears2 , "mutzar_halav", "halav",
                "0.5 liter" );
        calendar.set(2023, Calendar.APRIL, 07, 0, 0, 0);
        Date newYears3 = calendar.getTime();
        Item it3 =branchController2.createItemToBranch(1, "mh22", "tara", "tara_supplier", 3.90, 5.50,newYears3 , "mutzar_halav", "halav",
                "1 liter");
        calendar.set(2023, Calendar.APRIL, 11, 0, 0, 0);
        Date newYears4 = calendar.getTime();
        Item it4 =branchController2.createItemToBranch(2, "mh33", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears4 , "mutzar_halav", "koteg",
                "100 gram");
        calendar.set(2023, Calendar.MARCH, 14, 0, 0, 0);
        Date newYears5 = calendar.getTime();
        Item it5 =branchController2.createItemToBranch(1, "mh44", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears5 , "mutzar_halav", "gavnatz",
                "1 liter");
        calendar.set(2023, Calendar.MARCH, 14, 0, 0, 0);
        Date newYears6 = calendar.getTime();
        Item it6 =branchController2.createItemToBranch(1,"mh44", "tnuva", "tnuva_supplier", 5.90, 7.50,newYears5 , "mutzar_halav", "gavnatz",
                "1 liter");

        Item it7 =branchController2.createItemToBranch(1, "sh00", "pinuk", "pinuk_supplier", 8.20, 11.20,null , "mutzar_rahatza", "shampoo",
                "0.5 liter");
        Item it8 =branchController2.createItemToBranch(2, "sh11", "hawai", "hawai_supplier", 8.90, 11.50,null , "mutzar_rahatza", "shampoo",
                "0.25 liter");
        Item it9 =branchController2.createItemToBranch(2, "sh11", "hawai", "hawai_supplier", 8.90, 11.50,null , "mutzar_rahatza", "shampoo",
                "0.25 liter" );
        Item it10 =branchController2.createItemToBranch(2, "sh11", "hawai", "hawai_supplier", 8.90, 11.50,null , "mutzar_rahatza", "shampoo",
                "0.25 liter" );
        Item it11 =branchController2.createItemToBranch(1, "sh22", "pinuk", "pinuk_supplier", 6.90, 10.50,null , "mutzar_rahatza", "conditioner",
                "0.5 liter" );
        Item it12 =branchController2.createItemToBranch(1, "sh33", "panten", "panten_supplier", 7.40, 12.80,null , "mutzar_rahatza", "shampoo",
                "0.5 liter" );

        Item it13 = branchController2.createItemToBranch(2, "cl00", "tnx", "tnx_supplier", 10.20, 20.20,null , "mutzar_nikaion", "echonomika",
                "3 liter" );
        Item it14 = branchController2.createItemToBranch(2, "cl11", "shufersal", "shufersal_supplier", 12.20, 25.20,null , "mutzar_nikaion", "ritzpaz",
                "2 liter" );
        Item it15 = branchController2.createItemToBranch(1, "cl22", "shufersal", "shufersal_supplier", 11.90, 25.20,null , "mutzar_nikaion", "echonomika",
                "4 liter" );
        Item it16 = branchController2.createItemToBranch(1, "cl22", "shufersal", "shufersal_supplier", 11.90, 25.20,null , "mutzar_nikaion", "echonomika",
                "4 liter" );
        Item it17 = branchController2.createItemToBranch(1, "cl22", "shufersal", "shufersal_supplier", 11.90, 25.20,null , "mutzar_nikaion", "echonomika",
                "4 liter" );

//        Item it18 = branchController2.createItemToBranch(1, "cl22", "shufersal", "shufersal_supplier", 11.90, 25.20,null , "mutzar_nikaion", "echonomika",
//                "4 liter" );
        itemController2.setItemDamage(damageType.Damage, it17);

    }



    public static void placeNewItemInBranch(int branchNum, String catalogNum, String makerName, String supplierName, double supplierPrice, double storePrice, Date expiredDate,  String category, String subcategory,  String subsubCategory, int minAmount, place userPlace) {
        BranchController branchController = BranchController.getInstance();
        Item new_item = branchController.createItemToBranch(branchNum, catalogNum, makerName, supplierName, supplierPrice, storePrice, expiredDate, category, subcategory, subsubCategory);
        branchController.updateMinAmountOfCatalog(new_item, branchNum, minAmount);
    }

}