package GUI.SuppliersGUI.ContractProductGUI;

import BL.Suppliers.Product;
import DAL.Suppliers.ProductDao;
import GUI.ReportsGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowProductOfContractGUI extends JFrame implements ActionListener {

    JButton button;
    JButton button1;
    JTable table;
    JScrollPane sp;

    JTextField supplierIdText;
    ProductDao productDao;

    MainContractManagmentGUI mainContractManagmentGUI;
    ReportsGUI reportsGui;


    public ShowProductOfContractGUI(Object gui, String typeGUI) {

        if (typeGUI == "manager") {
            this.reportsGui = (ReportsGUI) gui;
            this.mainContractManagmentGUI= null;
        } else {
            this.mainContractManagmentGUI = (MainContractManagmentGUI) gui;
            this.reportsGui = null;
        }

        JLabel labelId = new JLabel("Enter supplierId:");
        labelId.setBounds(30, 50, 200, 30); // Set label's position and size
        labelId.setFont(new Font("David", Font.BOLD, 18));

        supplierIdText = new JTextField();
        supplierIdText.setPreferredSize(new Dimension(250, 40));
        supplierIdText.setBounds(170, 50, 100, 30);

        this.setTitle("supplier contract products");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        table = new JTable();
        table.setBounds(320, 50, 620, 430);
        sp = new JScrollPane(table);
        sp.setBounds(320, 50, 620, 430);

        button = new JButton("Show supplier contract products");
        button.addActionListener(this);
        button.setBounds(30, 100, 270, 50); // Set button's position and size
        button.setFont(new Font("David", Font.BOLD, 15));

        button1 = new JButton("<-");
        button1.addActionListener(this);
        button1.setBounds(25, 8, 55, 25); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 18));

        productDao = ProductDao.getInstance();
        this.add(button);
        this.add(labelId);
        this.add(supplierIdText);
        this.add(button1);
        this.add(sp);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {

            int supplierId = Integer.parseInt(supplierIdText.getText());
            List<Product> productList = productDao.getAllSupplierProduct(supplierId);

            if (!productList.isEmpty()) {
                String[][] all_products = new String[productList.size()][6];
                String[] title = new String[]{"supplierCategoryId", "supplierId", "weight", "basePrice","supplyAmount", "name"};

                int rowIndex = 0;
                for (Product p : productList) {
                    all_products[rowIndex][0] = String.valueOf(p.getSuppCatalogId());
                    all_products[rowIndex][1] = String.valueOf(p.getSupplierId());
                    all_products[rowIndex][2] = String.valueOf(p.getWeight());
                    all_products[rowIndex][3] = String.valueOf(p.getBasePrice());
                    all_products[rowIndex][4] = String.valueOf(p.getQuantity());
                    all_products[rowIndex][5] = p.getName();
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

                mainContractManagmentGUI.showMainContractManagerGUI();
            }


        }
    }
}
