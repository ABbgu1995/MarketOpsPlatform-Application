package GUI.SuppliersGUI.KtavKamotGUI;
import BL.Suppliers.KtavKamot;
import BL.Suppliers.ProductAmountDiscount;
import DAL.Suppliers.KtavKamutDao;
import GUI.ReportsGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ShowKtavKamotGUI extends JFrame implements ActionListener {

    JButton button;
    JButton button1;
    JTable table;
    JScrollPane sp;

    JTextField supplierIdText;


    KtavKamot ktavKamot;

    ManagmentKtavKamotGUI managmentKtavKamotGUI;

    KtavKamutDao ktavKamutDao;

    ReportsGUI reportsGui;


    public ShowKtavKamotGUI(Object gui, String typeGUI){
        if (typeGUI == "manager") {
            this.reportsGui = (ReportsGUI) gui;
            this.managmentKtavKamotGUI = null;
        } else {
            this.managmentKtavKamotGUI = (ManagmentKtavKamotGUI) gui;
            this.reportsGui = null;
        }

        this.setTitle("Show supplier KtavKamot");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        table = new JTable();
        table.setBounds(350, 50, 350, 430);
        sp = new JScrollPane(table);
        sp.setBounds(350, 50, 350, 430);

        button = new JButton("Show supplier KtavKamot");
        button.addActionListener(this);
        button.setBounds(50, 150, 230, 40); // Set button's position and size
        button.setFont(new Font("David", Font.BOLD, 15));

        button1 = new JButton("<-");
        button1.addActionListener(this);
        button1.setBounds(25, 8, 55, 25); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 18));

        JLabel labelId = new JLabel("Enter supplierId:");
        labelId.setBounds(30, 100, 200, 30); // Set label's position and size
        labelId.setFont(new Font("David", Font.BOLD, 18));

        supplierIdText = new JTextField();
        supplierIdText.setPreferredSize(new Dimension(250, 40));
        supplierIdText.setBounds(200, 100, 100, 30);

        ktavKamutDao = KtavKamutDao.getInstance();
        this.add(button);
        this.add(button1);
        this.add(supplierIdText);
        this.add(labelId);
        this.add(sp);
        this.setVisible(true);
    }



    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {

            int supplierId = Integer.parseInt(supplierIdText.getText());
            ktavKamot = ktavKamutDao.find(supplierId);
            if (ktavKamot != null) {
                String[][] all_products = new String[ktavKamot.getRulesTotalAmount()][3];
                String[] title = new String[]{"supplierCategoryId", "amount", "discount"};
                int rowIndex = 0;
                for (ProductAmountDiscount p: ktavKamot.getProductToDiscount()) {
                    for (Map.Entry<Double, Double> entry : p.getPolicyList().entrySet()) {
                        all_products[rowIndex][0] = String.valueOf(p.getProduct().getSuppCatalogId());
                        Double key = entry.getKey();
                        Double value = entry.getValue();
                        all_products[rowIndex][1] = String.valueOf(key);
                        all_products[rowIndex][2] = String.valueOf(value);
                        rowIndex++;
                    }

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
                managmentKtavKamotGUI.showManagmentKtavKamotGUI();
            }
        }
    }
}
