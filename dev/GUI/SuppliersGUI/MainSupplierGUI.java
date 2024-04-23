package GUI.SuppliersGUI;

import BL.Suppliers.SupplierController;
import GUI.ManagerGUI;
import GUI.SuppliersGUI.ContractProductGUI.MainContractManagmentGUI;
import GUI.SuppliersGUI.DiscountsManagementGUI.MainDiscountsManagementGUI;
import GUI.SuppliersGUI.KtavKamotGUI.ManagmentKtavKamotGUI;
import GUI.SuppliersGUI.OrderHistoryGui.ShowOrderHistory;
import GUI.SuppliersGUI.PeriodicOrderManagementGUI.MainPeriodicOrderManagementGUI;
import GUI.SuppliersGUI.SupplierManagmentGUI.AddNewSupplierGUI;
import GUI.SuppliersGUI.SupplierManagmentGUI.ShowAllSuppliersGUI;
import GUI.SuppliersGUI.SupplierManagmentGUI.SupplierManagmentMain;
import com.sun.tools.javac.Main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static UI.Suppliers.Menu.setupInformation;

public class MainSupplierGUI extends JFrame implements ActionListener {


    JButton supplierManagement;
    JButton contractInfoManagement;
    JButton KtavKamotManagement;
    JButton discountManagement;
    JButton orderHistoryManagement;
    JButton periodicOrderManagement;
    JButton backButton;

    ManagerGUI managerGUI;



    public MainSupplierGUI(Object gui, String typeGUI) {
        if(typeGUI=="managergui")
            this.managerGUI = (ManagerGUI)gui;
        else
            this.managerGUI=null;

        // Set the title of the window
        this.setTitle("Supplier management");

        // Set the size of the window
        this.setSize(800, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use null layout to manually set component positions
        this.setLayout(null);

        JLabel label1 = new JLabel("Click your option");
        label1.setBounds(250, 5, 300, 30); // Set label's position and size
        label1.setFont(new Font("David", Font.BOLD, 30));

        supplierManagement = new JButton();
        supplierManagement.addActionListener(this);
        supplierManagement.setText("supplier management");
        supplierManagement.setFocusable(false);
        supplierManagement.setBounds(240, 70, 320, 50); // Set button's position and size
        supplierManagement.setFont(new Font("David", Font.BOLD, 17));

        contractInfoManagement = new JButton();
        contractInfoManagement.addActionListener(this);
        contractInfoManagement.setText("contract information management");
        contractInfoManagement.setFocusable(false);
        contractInfoManagement.setBounds(240, 160, 320, 50); // Set button's position and size
        contractInfoManagement.setFont(new Font("David", Font.BOLD, 17));


        KtavKamotManagement = new JButton();
        KtavKamotManagement.addActionListener(this);
        KtavKamotManagement.setText("KtavKamot agreement management");
        KtavKamotManagement.setFocusable(false);
        KtavKamotManagement.setBounds(240, 250, 320, 50); // Set button's position and size
        KtavKamotManagement.setFont(new Font("David", Font.BOLD, 17));


        discountManagement = new JButton();
        discountManagement.addActionListener(this);
        discountManagement.setText("general discounts management");
        discountManagement.setFocusable(false);
        discountManagement.setBounds(240, 340, 320, 50); // Set button's position and size
        discountManagement.setFont(new Font("David", Font.BOLD, 17));

        orderHistoryManagement = new JButton();
        orderHistoryManagement.addActionListener(this);
        orderHistoryManagement.setText("Order history options");
        orderHistoryManagement.setFocusable(false);
        orderHistoryManagement.setBounds(240, 430, 320, 50); // Set button's position and size
        orderHistoryManagement.setFont(new Font("David", Font.BOLD, 17));

        periodicOrderManagement = new JButton();
        periodicOrderManagement.addActionListener(this);
        periodicOrderManagement.setText("periodic order management");
        periodicOrderManagement.setFocusable(false);
        periodicOrderManagement.setBounds(240, 520, 320, 50); // Set button's position and size
        periodicOrderManagement.setFont(new Font("David", Font.BOLD, 17));


        backButton = new JButton("<-");
        backButton.addActionListener(this);
        backButton.setBounds(25, 8, 55, 25); // Set button's position and size
        backButton.setFont(new Font("David", Font.BOLD, 18));
        if(this.managerGUI==null)
            backButton.setVisible(false);

        this.add(label1);
        this.add(supplierManagement);
        this.add(backButton);
        this.add(contractInfoManagement);
        this.add(KtavKamotManagement);
        this.add(discountManagement);
        this.add(orderHistoryManagement);
        this.add(periodicOrderManagement);
        this.setVisible(true);
    }

//    public static void main(String[] args) throws SQLException, InterruptedException {
//
//        SupplierController.clearAllTables();
//        setupInformation();
//        MainSupplierGUI mainsupplierGUI = new MainSupplierGUI();
//
//    }

    public void showManagerGUI() {
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == supplierManagement) {
            this.setVisible(false);
            SupplierManagmentMain supplierManagmentMain = new SupplierManagmentMain(this);
        }
        if (e.getSource()==orderHistoryManagement){
            this.setVisible(false);
            try {
                ShowOrderHistory showOrderHistory=new ShowOrderHistory(this,"supplier mannager");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==contractInfoManagement){
            this.setVisible(false);
            MainContractManagmentGUI mainContractManagmentGUI = new MainContractManagmentGUI(this);
        }

        if(e.getSource()==KtavKamotManagement){
            this.setVisible(false);
            ManagmentKtavKamotGUI managmentKtavKamotGUI = new ManagmentKtavKamotGUI(this);
        }

        if(e.getSource()==discountManagement){
            this.setVisible(false);
            MainDiscountsManagementGUI mainDiscountsManagementGUI = new MainDiscountsManagementGUI(this);
        }

        if(e.getSource()==periodicOrderManagement){
            this.setVisible(false);
            MainPeriodicOrderManagementGUI mainPeriodicOrderManagementGUI = new MainPeriodicOrderManagementGUI(this);
        }

        if (e.getSource() == backButton) {
            this.dispose();
            this.managerGUI.showManagerGUI();
        }


    }
        public void showSMainSupplierGUI () {
            this.setVisible(true);
        }
    }




