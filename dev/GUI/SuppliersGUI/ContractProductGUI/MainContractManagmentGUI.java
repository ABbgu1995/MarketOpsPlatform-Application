package GUI.SuppliersGUI.ContractProductGUI;

import BL.Suppliers.SupplierController;
import GUI.SuppliersGUI.MainSupplierGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static UI.Suppliers.Menu.setupInformation;

public class MainContractManagmentGUI extends JFrame implements ActionListener {
    JButton button1;
    JButton button2;

    JButton button3;
    AddContractProductGUI addContractProduct;

    MainSupplierGUI mainSupplierGUI;


    ShowProductOfContractGUI showProductOfContractGUI;


    public MainContractManagmentGUI(MainSupplierGUI mainSupplierGUI) {
        // Set the title of the window
        this.mainSupplierGUI=mainSupplierGUI;
        this.setTitle("contract management options");

        // Set the size of the window
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use null layout to manually set component positions
        this.setLayout(null);

        JLabel label1 = new JLabel("Click your option");
        label1.setBounds(250, 5, 300, 30); // Set label's position and size
        label1.setFont(new Font("David", Font.BOLD, 30));


        button3 = new JButton("<-");
        button3.addActionListener(this);
        button3.setBounds(25, 8, 55, 25); // Set button's position and size
        button3.setFont(new Font("David", Font.BOLD, 18));

        button1 = new JButton();
        button1.addActionListener(this);
        button1.setText("add new product to supplier contract");
        button1.setFocusable(false);
        button1.setBounds(200, 70, 400, 50); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 17));

        button2 = new JButton();
        button2.addActionListener(this);
        button2.setText("show supplier contract products");
        button2.setFocusable(false);
        button2.setBounds(200, 160, 400, 50); // Set button's position and size
        button2.setFont(new Font("David", Font.BOLD, 17));


        this.add(label1);
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.setVisible(true);
    }

    public void showManagerGUI() {
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            this.setVisible(false);
            addContractProduct = new AddContractProductGUI(this);

        }

        if (e.getSource() == button2) {
            this.setVisible(false);
            showProductOfContractGUI = new ShowProductOfContractGUI(this, "contract manager");
        }
        if (e.getSource() == button3) {
            this.dispose();
            mainSupplierGUI.showSMainSupplierGUI();
        }
    }

    public void showMainContractManagerGUI() {
        this.setVisible(true);
    }
}

