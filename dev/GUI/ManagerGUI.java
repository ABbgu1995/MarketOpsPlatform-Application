package GUI;

import BL.Suppliers.SupplierController;
import GUI.SuppliersGUI.MainSupplierGUI;
import GUI.SuppliersGUI.SupplierManagmentGUI.SupplierManagmentMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static UI.Suppliers.Menu.setupInformation;

public class ManagerGUI extends JFrame implements ActionListener {

    JButton Reports;
    JButton SupplierManagerOptions;
    JButton StoreKeeperOptions;






    public ManagerGUI(){
        this.setTitle("supplier manager options");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        JLabel label1 = new JLabel("Click your option");
        label1.setBounds(250, 5, 300, 30); // Set label's position and size
        label1.setFont(new Font("David", Font.BOLD, 30));

        Reports = new JButton();
        Reports.addActionListener(this);
        Reports.setText("Reports");
        Reports.setFocusable(false);
        Reports.setBounds(240, 70, 250, 50); // Set button's position and size
        Reports.setFont(new Font("David", Font.BOLD, 17));

        SupplierManagerOptions = new JButton();
        SupplierManagerOptions.addActionListener(this);
        SupplierManagerOptions.setText("Supplier manager options");
        SupplierManagerOptions.setFocusable(false);
        SupplierManagerOptions.setBounds(240, 150, 250, 50); // Set button's position and size
        SupplierManagerOptions.setFont(new Font("David", Font.BOLD, 17));

        StoreKeeperOptions = new JButton();
        StoreKeeperOptions.addActionListener(this);
        StoreKeeperOptions.setText("Store keeper options");
        StoreKeeperOptions.setFocusable(false);
        StoreKeeperOptions.setBounds(240, 230, 250, 50); // Set button's position and size
        StoreKeeperOptions.setFont(new Font("David", Font.BOLD, 17));

        this.add(label1);
        this.add(Reports);
        this.add(SupplierManagerOptions);
        this.add(StoreKeeperOptions);
        this.setVisible(true);
    }

    public static void main(String[] args) throws SQLException, InterruptedException {

        SupplierController.clearAllTables();
        setupInformation();
        ManagerGUI managerGUI = new ManagerGUI();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SupplierManagerOptions) {
            this.setVisible(false);
            MainSupplierGUI mainSupplierGUI = new MainSupplierGUI(this, "managergui");
        }

        if (e.getSource() == StoreKeeperOptions) {
            this.setVisible(false);
            GUIStorekeeper guiStorekeeper = new GUIStorekeeper(this,"managergui");
        }

        if (e.getSource() == Reports) {
            this.setVisible(false);
            ReportsGUI reportsGUI = new ReportsGUI(this,"managergui");
        }

    }
        public void showManagerGUI () {
            this.setVisible(true);
        }



}
