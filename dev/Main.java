import BL.Suppliers.SupplierController;
import DAL.Inventory.*;
import GUI.ManagerGUI;
import GUI.ReportsGUI;
import GUI.GUIStorekeeper;
import GUI.SuppliersGUI.MainSupplierGUI;
import UI.Inventory.UserInterface;

import java.sql.SQLException;
import java.util.Objects;

import static UI.Inventory.UserInterface.upDateDataBase;
import static UI.Suppliers.Menu.SupplierMenu;
import static UI.Suppliers.Menu.setupInformation;


public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException {
        Database.clearDatabase(Database.connect());
        SupplierController.clearAllTables();
        setupInformation();
        upDateDataBase();
        String menu = args[0];
        String user = args[1];
        if (Objects.equals(menu, "CLI")) {
            if (Objects.equals(user, "StoreManager")) {
                UserInterface.startManager();
            }
            if (Objects.equals(user, "Storekeeper")) {
                UserInterface.startStorekeeper();
            }
            if (Objects.equals(user, "Supplier")) {
                SupplierMenu();
            }
        }else if(Objects.equals(menu, "GUI")){
            if (Objects.equals(user, "StoreManager")) {
                new ManagerGUI();
            }
            if (Objects.equals(user, "Storekeeper")) {
                Object temp = new Object();
                new GUIStorekeeper(temp,"direct");
            }
            if (Objects.equals(user, "Supplier")) {
                Object temp = new Object();
                new MainSupplierGUI(temp,"direct");
            }
        }
//        Database.clearDatabase(Database.connect());
//        SupplierController.clearAllTables();
//        setupInformation();
//        upDateDataBase();
//        Scanner scannerString = new Scanner(System.in);
//        int choice_while = 0;
//        while (choice_while == 0) {
//            System.out.println("Choose an option:");
//            System.out.println("1. Supplier");
//            System.out.println("2. Storekeeper");
//            System.out.println("3. Store manager");
//            System.out.println("0. Exit from the program");
//
////            String choice = scannerString.nextLine();
//            switch (choice){
//                case "1":
//                    SupplierMenu();
//                    break;
//                case "2":
//                    UserInterface.startStorekeeper();
//                    break;
//                case "3":
//                    UserInterface.startManager();
//                    break;
//                case "0":
//                    System.out.println("Exiting the program");
//                    choice_while++;
//                    break;
//                default:
//                    System.out.println("Invalid choice");
//                    break;
//            }
//        }
    }
}