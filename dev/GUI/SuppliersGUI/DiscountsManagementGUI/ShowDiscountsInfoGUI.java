package GUI.SuppliersGUI.DiscountsManagementGUI;

import BL.Suppliers.TotalAmountDiscount;
import BL.Suppliers.TotalPaymentDiscount;
import DAL.Suppliers.TotalAmountDiscountDao;
import DAL.Suppliers.TotalPaymentDiscountDao;
import GUI.ReportsGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ShowDiscountsInfoGUI extends JFrame implements ActionListener {

    JButton button;
    JButton button1;

    JButton button2;
    JTable table;
    JScrollPane sp;

    JTextField supplierIdText;

    MainDiscountsManagementGUI mainDiscountsManagementGUI;

    TotalAmountDiscountDao totalAmountDiscountDao;

    TotalAmountDiscount totalAmountDiscount;
    TotalPaymentDiscountDao totalPaymentDiscountDao;

    TotalPaymentDiscount totalPaymentDiscount;

    ReportsGUI reportsGui;

    public ShowDiscountsInfoGUI(Object gui, String typeGUI) {
        if (typeGUI == "manager") {
            this.reportsGui = (ReportsGUI) gui;
            this.mainDiscountsManagementGUI = null;
        } else {
            this.mainDiscountsManagementGUI = (MainDiscountsManagementGUI) gui;
            this.reportsGui = null;
        }

        this.setTitle("Show supplier discounts");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        table = new JTable();
        table.setBounds(400, 50, 200, 430);
        sp = new JScrollPane(table);
        sp.setBounds(400, 50, 200, 430);

        button = new JButton("Show supplier total amount discounts");
        button.addActionListener(this);
        button.setBounds(50, 150, 300, 40); // Set button's position and size
        button.setFont(new Font("David", Font.BOLD, 15));

        button2 = new JButton("Show supplier total payment discounts");
        button2.addActionListener(this);
        button2.setBounds(50, 200, 300, 40); // Set button's position and size
        button2.setFont(new Font("David", Font.BOLD, 15));

        button1 = new JButton("<-");
        button1.addActionListener(this);
        button1.setBounds(25, 8, 55, 25); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 18));

        JLabel labelId = new JLabel("Enter supplierId:");
        labelId.setBounds(60, 100, 200, 30); // Set label's position and size
        labelId.setFont(new Font("David", Font.BOLD, 18));

        supplierIdText = new JTextField();
        supplierIdText.setPreferredSize(new Dimension(250, 40));
        supplierIdText.setBounds(230, 100, 100, 30);

        totalAmountDiscountDao = TotalAmountDiscountDao.getInstance();
        totalPaymentDiscountDao = TotalPaymentDiscountDao.getInstance();
        this.add(button);
        this.add(button1);
        this.add(button2);
        this.add(supplierIdText);
        this.add(labelId);
        this.add(sp);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            int supplierId = Integer.parseInt(supplierIdText.getText());
            totalAmountDiscount = totalAmountDiscountDao.find(supplierId);

            if (totalAmountDiscount != null) {
                String[][] all_products = new String[totalAmountDiscount.getPolicyList().size()][2];
                String[] title = new String[]{"totalAmount", "discount"};
                int rowIndex = 0;
                for (Map.Entry<Double, Double> entry : totalAmountDiscount.getPolicyList().entrySet()) {
                        Double key = entry.getKey();
                        Double value = entry.getValue();
                        all_products[rowIndex][0] = String.valueOf(entry.getKey());
                        all_products[rowIndex][1] = String.valueOf(entry.getValue());
                        rowIndex++;
                    }

                //Arrays.sort(all_suppliers, Comparator.comparingInt(item -> Integer.parseInt(item[0])));
                DefaultTableModel model = new DefaultTableModel(all_products, title);

                table.setModel(model);
                Component parent = table.getParent();
                if (parent instanceof JScrollPane) {
                    sp = (JScrollPane) parent;
                    sp.setViewportView(table);
                }
            } else {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                table.setModel(model);
            }
        }

        if (e.getSource() == button2) {
            int supplierId = Integer.parseInt(supplierIdText.getText());
            totalPaymentDiscount = totalPaymentDiscountDao.find(supplierId);

            if (totalPaymentDiscount != null) {
                String[][] all_products = new String[totalPaymentDiscount.getPolicyList().size()][2];
                String[] title = new String[]{"totalPayment", "discount"};
                int rowIndex = 0;
                for (Map.Entry<Double, Double> entry : totalPaymentDiscount.getPolicyList().entrySet()) {
                    Double key = entry.getKey();
                    Double value = entry.getValue();
                    all_products[rowIndex][0] = String.valueOf(entry.getKey());
                    all_products[rowIndex][1] = String.valueOf(entry.getValue());
                    rowIndex++;
                }

                //Arrays.sort(all_suppliers, Comparator.comparingInt(item -> Integer.parseInt(item[0])));
                DefaultTableModel model = new DefaultTableModel(all_products, title);

                table.setModel(model);
                Component parent = table.getParent();
                if (parent instanceof JScrollPane) {
                    sp = (JScrollPane) parent;
                    sp.setViewportView(table);
                }
            } else {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                table.setModel(model);
            }
        }
        if (e.getSource() == button1) {
            this.dispose();
            if (reportsGui != null) {
                reportsGui.showManagerGUI();
            } else {
                mainDiscountsManagementGUI.showMainDiscountsManagementGUI();
            }
        }
    }

}
