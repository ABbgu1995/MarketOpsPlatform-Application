package GUI;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class ManagerGUI extends JFrame implements ActionListener{
//    JButton button;
//    public ManagerGUI() {
//
//        // Set the title of the window
//        this.setTitle("Manager options");
//
//        // Set the size of the window
//        this.setSize(400, 300);
//         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
//        JLabel label1 = new JLabel("Click your option");
//        button = new JButton();
//        button.setAlignmentX(100);
////        button.setAlignmentY(50);
//        button.setBounds(150, 50, 200, 200);
//        button.addActionListener((ActionListener) this);
//        button.setText("Show item");
//        button.setFocusable(false);
//
//
//        label1.setFont(new Font("David", Font.BOLD, 20));
//        this.add(label1);
//        this.add(button);
//        this.setVisible(true);
//
//    }
//
//    public static void main(String[] args) {
//        // Create an instance of your GUI class
//        ManagerGUI gui = new ManagerGUI();
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource() == button){
//            System.out.println("lclclcl");
//
//        }
//    }
//}
import BL.Inventory.ReportController;
import BL.Suppliers.SupplierController;
import DAL.Inventory.Database;
import GUI.SuppliersGUI.SupplierManagmentGUI.ShowAllSuppliersGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static UI.Inventory.UserInterface.upDateDataBase;
import static UI.Suppliers.Menu.setupInformation;

public class ReportsGUI extends JFrame implements ActionListener {
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;

    JButton button5;
    ShowItemGUI showItemGUI;
    ShowAllItemsGUI showAllItemsGUI;
    ShowReportGUI showReportGUI;
    ShowItemAmountGUI showItemAmountGUI;
    ShowAllSuppliersGUI showAllSuppliersGUI;

    JButton backButton;

    ManagerGUI managerGUI;

    public ReportsGUI(Object gui, String typeGUI) {

        if(typeGUI=="managergui")
            this.managerGUI = (ManagerGUI)gui;
        else
            this.managerGUI=null;
        // Set the title of the window
        this.setTitle("Manager options");

        // Set the size of the window
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use null layout to manually set component positions
        this.setLayout(null);

        JLabel label1 = new JLabel("Click your option");
        label1.setBounds(250, 5, 300, 30); // Set label's position and size
        label1.setFont(new Font("David", Font.BOLD, 30));

        button1 = new JButton();
        button1.addActionListener(this);
        button1.setText("Show details of item");
        button1.setFocusable(false);
        button1.setBounds(240, 70, 250, 50); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 17));

        button2 = new JButton();
        button2.addActionListener(this);
        button2.setText("Show amount of item");
        button2.setFocusable(false);
        button2.setBounds(240, 160, 250, 50); // Set button's position and size
        button2.setFont(new Font("David", Font.BOLD, 17));

        button3 = new JButton();
        button3.addActionListener(this);
        button3.setText("Show report");
        button3.setFocusable(false);
        button3.setBounds(240, 250, 250, 50); // Set button's position and size
        button3.setFont(new Font("David", Font.BOLD, 17));

        button4 = new JButton();
        button4.addActionListener(this);
        button4.setText("Show all item in branch");
        button4.setFocusable(false);
        button4.setBounds(240, 340, 250, 50); // Set button's position and size
        button4.setFont(new Font("David", Font.BOLD, 17));

        button5 = new JButton();
        button5.addActionListener(this);
        button5.setText("Show all suppliers");
        button4.setFocusable(false);
        button5.setBounds(240, 430, 250, 50); // Set button's position and size
        button5.setFont(new Font("David", Font.BOLD, 17));

        backButton = new JButton("<-");
        backButton.addActionListener(this);
        backButton.setBounds(25, 8, 55, 25); // Set button's position and size
        backButton.setFont(new Font("David", Font.BOLD, 18));
        if(this.managerGUI==null)
            backButton.setVisible(false);

        this.add(label1);
        this.add(button1);
        this.add(backButton);
        this.add(button2);
        this.add(button3);
        this.add(button4);
        this.add(button5);
        this.setVisible(true);
    }

//    public static void main(String[] args) throws SQLException, InterruptedException {
//        // Create an instance of your GUI class
//        Database.clearDatabase(Database.connect());
//        SupplierController.clearAllTables();
//        setupInformation();
//        upDateDataBase();
//        ReportController b = ReportController.getInstance();
//        b.createNewShortageReport(1);
//        b.createNewInventoryReport(2);
//        b.createNewDamageReport(1);
//        ReportsGUI gui = new ReportsGUI();
//    }
    public void showManagerGUI() {
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            this.setVisible(false);
            showItemGUI = new ShowItemGUI(this, "manager");
        }
        if (e.getSource() == button2) {
            this.setVisible(false);
            showItemAmountGUI = new ShowItemAmountGUI(this,"manager");
        }
        if (e.getSource() == button3) {
            this.setVisible(false);
            showReportGUI = new ShowReportGUI(this);
        }
        if (e.getSource() == button4) {
            this.setVisible(false);
            showAllItemsGUI = new ShowAllItemsGUI(this,"manager" );
        }

        if (e.getSource() == button5) {
            this.setVisible(false);
            showAllSuppliersGUI = new ShowAllSuppliersGUI(this,"manager" );
        }

        if (e.getSource() == backButton) {
            this.dispose();
            this.managerGUI.showManagerGUI();
        }
    }
}
