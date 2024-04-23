package GUI.SuppliersGUI.DiscountsManagementGUI;
import BL.Suppliers.SupplierController;
import GUI.SuppliersGUI.KtavKamotGUI.EditKtavKamotGUI;
import GUI.SuppliersGUI.KtavKamotGUI.ManagmentKtavKamotGUI;
import GUI.SuppliersGUI.KtavKamotGUI.ShowKtavKamotGUI;
import GUI.SuppliersGUI.MainSupplierGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;


import static UI.Suppliers.Menu.setupInformation;
public class MainDiscountsManagementGUI extends JFrame implements ActionListener {
    JButton totalPaymentDiscountManagement;
    JButton totalAmountDiscountManagement;

    JButton button3;

    JButton displayDiscountInfo;

    ShowDiscountsInfoGUI showDiscountsInfoGUI;

    MainSupplierGUI mainSupplierGUI;
    TotalPaymentDiscountGUI totalPaymentDiscountGUI;
    TotalAmountDiscountGUI totalAmountDiscountGUI;

    public MainDiscountsManagementGUI(MainSupplierGUI mainSupplierGUI){
        this.setTitle("discount management");
        this.mainSupplierGUI = mainSupplierGUI;

        // Set the size of the window
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use null layout to manually set component positions
        this.setLayout(null);

        JLabel label1 = new JLabel("Click your option");
        label1.setBounds(250, 5, 300, 30); // Set label's position and size
        label1.setFont(new Font("David", Font.BOLD, 30));

        totalPaymentDiscountManagement = new JButton();
        totalPaymentDiscountManagement.addActionListener(this);
        totalPaymentDiscountManagement.setText("total payment discount management");
        totalPaymentDiscountManagement.setFocusable(false);
        totalPaymentDiscountManagement.setBounds(200, 70, 400, 50); // Set button's position and size
        totalPaymentDiscountManagement.setFont(new Font("David", Font.BOLD, 17));

        totalAmountDiscountManagement = new JButton();
        totalAmountDiscountManagement.addActionListener(this);
        totalAmountDiscountManagement.setText("total amount discount management");
        totalAmountDiscountManagement.setFocusable(false);
        totalAmountDiscountManagement.setBounds(200, 160, 400, 50); // Set button's position and size
        totalAmountDiscountManagement.setFont(new Font("David", Font.BOLD, 17));

        displayDiscountInfo = new JButton();
        displayDiscountInfo.addActionListener(this);
        displayDiscountInfo.setText("show discount information");
        displayDiscountInfo.setFocusable(false);
        displayDiscountInfo.setBounds(200, 250, 400, 50); // Set button's position and size
        displayDiscountInfo.setFont(new Font("David", Font.BOLD, 17));

        button3 = new JButton("<-");
        button3.addActionListener(this);
        button3.setBounds(25, 8, 55, 25); // Set button's position and size
        button3.setFont(new Font("David", Font.BOLD, 18));

        this.add(label1);
        this.add(button3);
        this.add(totalAmountDiscountManagement);
        this.add(totalPaymentDiscountManagement);
        this.add(displayDiscountInfo);
        this.setVisible(true);
    }

//    public void paint(Graphics g) {
//
//        Toolkit t=Toolkit.getDefaultToolkit();
//        Image i=t.getImage("C:\\Users\\amita\\Desktop\\amnon.png");
//        g.drawImage(i, 250,270,this);
//
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == totalAmountDiscountManagement) {
            this.setVisible(false);
            totalAmountDiscountGUI = new TotalAmountDiscountGUI(this);
        }
        if (e.getSource() == totalPaymentDiscountManagement) {
            this.setVisible(false);
            totalPaymentDiscountGUI = new TotalPaymentDiscountGUI(this);
        }

        if (e.getSource() == displayDiscountInfo) {
            this.dispose();
            showDiscountsInfoGUI = new ShowDiscountsInfoGUI(this, "discount manager");
        }

        if (e.getSource() == button3) {
            this.dispose();
            mainSupplierGUI.showSMainSupplierGUI();
        }
    }

    public void showMainDiscountsManagementGUI() {
        this.setVisible(true);
    }
}
