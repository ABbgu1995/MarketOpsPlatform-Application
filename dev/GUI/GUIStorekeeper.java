package GUI;

import BL.Inventory.ReportController;
import BL.Suppliers.SupplierController;
import DAL.Inventory.Database;
import GUI.SuppliersGUI.PeriodicOrderManagementGUI.UpdatePeriodicOrderGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static UI.Inventory.UserInterface.upDateDataBase;
import static UI.Suppliers.Menu.setupInformation;

public class GUIStorekeeper extends JFrame implements ActionListener {
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JButton button5;
    JButton button6;
    JButton button7;
    JButton button8;
    JButton button9;
    JButton button10;

    JButton backButton;

    ManagerGUI managerGUI;
    AddItemGUI addItemGUI;
    DeleteItemGUI deleteItemGUI;
    ShowItemGUI showItemGUI;
    ShowItemAmountGUI showItemAmountGUI;
    CreateDiscountGUI createDiscountGUI;
    CreateReportGUI createReportGUI;
    EditItemGUI editItemGUI;
    ShowAllItemsGUI showAllItemsGUI;
    IssuingShortageOrderGUI issuingShortageOrderGUI;
    UpdatePeriodicOrderGUI updatePeriodicOrderGUI;




    public GUIStorekeeper(Object gui, String typeGUI) {

        if(typeGUI=="managergui")
            this.managerGUI = (ManagerGUI)gui;
        else
            this.managerGUI=null;

        // Set the title of the window
        this.setTitle("Storekeeper options");

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
        button1.setText("Add item");
        button1.setFocusable(false);
        button1.setBounds(100, 70, 250, 50); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 17));

        button2 = new JButton();
        button2.addActionListener(this);
        button2.setText("Delete item");
        button2.setFocusable(false);
        button2.setBounds(100, 160, 250, 50); // Set button's position and size
        button2.setFont(new Font("David", Font.BOLD, 17));

        button3 = new JButton();
        button3.addActionListener(this);
        button3.setText("Show details of item");
        button3.setFocusable(false);
        button3.setBounds(100, 250, 250, 50); // Set button's position and size
        button3.setFont(new Font("David", Font.BOLD, 17));

        button4 = new JButton();
        button4.addActionListener(this);
        button4.setText("Show amount of item");
        button4.setFocusable(false);
        button4.setBounds(100, 340, 250, 50); // Set button's position and size
        button4.setFont(new Font("David", Font.BOLD, 17));

        button5 = new JButton();
        button5.addActionListener(this);
        button5.setText("Create discount");
        button5.setFocusable(false);
        button5.setBounds(100, 430, 250, 50); // Set button's position and size
        button5.setFont(new Font("David", Font.BOLD, 17));

        button6 = new JButton();
        button6.addActionListener(this);
        button6.setText("Create report");
        button6.setFocusable(false);
        button6.setBounds(420, 70, 250, 50); // Set button's position and size
        button6.setFont(new Font("David", Font.BOLD, 17));

        button7 = new JButton();
        button7.addActionListener(this);
        button7.setText("Edit item");
        button7.setFocusable(false);
        button7.setBounds(420, 160, 250, 50); // Set button's position and size
        button7.setFont(new Font("David", Font.BOLD, 17));

        button8 = new JButton();
        button8.addActionListener(this);
        button8.setText("Print all item in branch");
        button8.setFocusable(false);
        button8.setBounds(420, 250, 250, 50); // Set button's position and size
        button8.setFont(new Font("David", Font.BOLD, 17));

        button9 = new JButton();
        button9.addActionListener(this);
        button9.setText("Issuing shortage order");
        button9.setFocusable(false);
        button9.setBounds(420, 340, 250, 50); // Set button's position and size
        button9.setFont(new Font("David", Font.BOLD, 17));

        button10 = new JButton();
        button10.addActionListener(this);
        button10.setText("Edit a periodic order");
        button10.setFocusable(false);
        button10.setBounds(420, 430, 250, 50); // Set button's position and size
        button10.setFont(new Font("David", Font.BOLD, 17));

        backButton = new JButton("<-");
        backButton.addActionListener(this);
        backButton.setBounds(25, 8, 55, 25); // Set button's position and size
        backButton.setFont(new Font("David", Font.BOLD, 18));
        if(this.managerGUI==null)
            backButton.setVisible(false);

        this.add(backButton);

        this.add(label1);
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(button4);
        this.add(button5);
        this.add(button6);
        this.add(button7);
        this.add(button8);
        this.add(button9);
        this.add(button10);
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
//        GUIStorekeeper gui = new GUIStorekeeper();
//    }
    public void showStorekeeperGUI() {
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            this.setVisible(false);
            addItemGUI = new AddItemGUI(this);
        }
        if (e.getSource() == button2) {
            this.setVisible(false);
            deleteItemGUI = new DeleteItemGUI(this);
        }
        if (e.getSource() == button3) {
            this.setVisible(false);
            showItemGUI = new ShowItemGUI(this, "storekeeper");
        }
        if (e.getSource() == button4) {
            this.setVisible(false);
            showItemAmountGUI = new ShowItemAmountGUI(this,"storekeeper");
        }
        if (e.getSource() == button5) {
            this.setVisible(false);
            createDiscountGUI = new CreateDiscountGUI(this);
        }
        if (e.getSource() == button6) {
            this.setVisible(false);
            createReportGUI = new   CreateReportGUI(this);
        }
        if (e.getSource() == button7) {
            this.setVisible(false);
            editItemGUI = new EditItemGUI(this);
        }
        if (e.getSource() == button8) {
            this.setVisible(false);
            showAllItemsGUI = new ShowAllItemsGUI(this,"storekeeper");
        }
        if (e.getSource() == button9) {
            this.setVisible(false);
            issuingShortageOrderGUI = new IssuingShortageOrderGUI(this);
        }
        if (e.getSource() == button10) {
            this.setVisible(false);
            updatePeriodicOrderGUI= new UpdatePeriodicOrderGUI(this,"storekeeper");
        }
        if (e.getSource() == backButton) {
            this.dispose();
            this.managerGUI.showManagerGUI();
        }
    }
}

