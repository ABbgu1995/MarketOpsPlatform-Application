package GUI.SuppliersGUI.KtavKamotGUI;

import BL.Suppliers.SupplierController;
import GUI.SuppliersGUI.MainSupplierGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;


import static UI.Suppliers.Menu.setupInformation;


public class ManagmentKtavKamotGUI extends JFrame implements ActionListener {
    JButton addNewRule;
    JButton editExitRule;
    JButton showKtavKamot;

    JButton button3;
    MainSupplierGUI mainSupplierGUI;
    EditKtavKamotGUI editKtavKamotGUI;
    ShowKtavKamotGUI showKtavKamotGUI;


    public ManagmentKtavKamotGUI(MainSupplierGUI mainSupplierGUI) {
        this.mainSupplierGUI = mainSupplierGUI;
        // Set the title of the window

        this.setTitle("KtavKamot management options");

        // Set the size of the window
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use null layout to manually set component positions
        this.setLayout(null);

        JLabel label1 = new JLabel("Click your option");
        label1.setBounds(250, 5, 300, 30); // Set label's position and size
        label1.setFont(new Font("David", Font.BOLD, 30));


        editExitRule = new JButton();
        editExitRule.addActionListener(this);
        editExitRule.setText("edit exit rule of a product in ktavKamot");
        editExitRule.setFocusable(false);
        editExitRule.setBounds(200, 70, 400, 50); // Set button's position and size
        editExitRule.setFont(new Font("David", Font.BOLD, 17));

        showKtavKamot = new JButton();
        showKtavKamot.addActionListener(this);
        showKtavKamot.setText("display exit rule of a product in ktavKamot");
        showKtavKamot.setFocusable(false);
        showKtavKamot.setBounds(200, 160, 400, 50); // Set button's position and size
        showKtavKamot.setFont(new Font("David", Font.BOLD, 17));


        button3 = new JButton("<-");
        button3.addActionListener(this);
        button3.setBounds(25, 8, 55, 25); // Set button's position and size
        button3.setFont(new Font("David", Font.BOLD, 18));

        this.add(label1);
        this.add(editExitRule);
        this.add(showKtavKamot);
        this.add(button3);
        this.setVisible(true);
    }

    public void showManagerGUI() {
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editExitRule) {
            this.setVisible(false);
            editKtavKamotGUI = new EditKtavKamotGUI(this);

        }

        if (e.getSource() == showKtavKamot) {
            this.setVisible(false);
            showKtavKamotGUI = new ShowKtavKamotGUI(this,"ktav kamot info");
        }

        if (e.getSource() == button3) {
            this.dispose();
            mainSupplierGUI.showSMainSupplierGUI();
        }
    }

    public void showManagmentKtavKamotGUI() {
        this.setVisible(true);
    }

}
