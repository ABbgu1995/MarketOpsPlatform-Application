package GUI;

import BL.Inventory.*;
import BL.Suppliers.SupplierController;
import DAL.Inventory.Database;
import DAL.Inventory.ReportDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static UI.Inventory.UserInterface.upDateDataBase;
import static UI.Suppliers.Menu.setupInformation;


public class CreateReportGUI extends JFrame implements ActionListener {
    JButton button;
    JButton button1;
    JComboBox comboBox1;
    JComboBox comboBox2;

    JTextArea text2;
    JTable table;
    BranchController branchController;
    ReportDAO reportDAO;
    JScrollPane sp;
    int branchNum;
    ReportController reportController;
    GUIStorekeeper guiStorekeeper;
    CreateReportGUI(GUIStorekeeper guiStorekeeper) {
        this.guiStorekeeper = guiStorekeeper;
        this.reportController = ReportController.getInstance();
        this.branchController = BranchController.getInstance();
        this.setTitle("Create Report");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        String[] branches = {"1", "2"};
        String[] reportsName = {"Shortage", "Inventory","Damage"};
        String[] option = {"all", "choose"};

        JLabel label1 = new JLabel("Choose branch number:");
        label1.setBounds(30, 50, 200, 30); // Set label's position and size
        label1.setFont(new Font("David", Font.BOLD, 18));
        comboBox1 = new JComboBox(branches);
        comboBox1.addActionListener(this);
        comboBox1.setBounds(220, 50, 100, 30);

        JLabel label2 = new JLabel("Choose report type:");
        label2.setBounds(30, 100, 200, 30); // Set label's position and size
        label2.setFont(new Font("David", Font.BOLD, 18));
        comboBox2 = new JComboBox(reportsName);
        comboBox2.addActionListener(this);
        comboBox2.setBounds(220, 100, 100, 30);



        text2 = new JTextArea();
        text2.setPreferredSize(new Dimension(250, 40));
        text2.setBounds(55, 440, 340, 85);
        text2.setFont(new Font("David", Font.BOLD, 18));

        table = new JTable();
//        table.setBounds(400, 100, 350, 450);
        sp = new JScrollPane(table);
        sp.setBounds(20, 150, 750, 250);

        button = new JButton("Show report");
        button.addActionListener(this);
        button.setBounds(390, 60, 150, 50); // Set button's position and size
        button.setFont(new Font("David", Font.BOLD, 18));

        button1 = new JButton("Back to the main menu");
        button1.addActionListener(this);
        button1.setBounds(450, 460, 250, 50); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 18));

        reportDAO = ReportDAO.getInstance();
        this.add(comboBox1);
        this.add(comboBox2);
        this.add(text2);
        this.add(button);
        this.add(button1);
        this.add(label1);
        this.add(label2);
        this.add(sp);
        this.setVisible(true);
    }

//    public static void main(String[] args) throws SQLException, InterruptedException {
//        // Create an instance of your GUI class
//        Database.clearDatabase(Database.connect());
//        SupplierController.clearAllTables();
//        setupInformation();
//        upDateDataBase();
//
//        CreateReportGUI Show_Item_amountGUI = new CreateReportGUI();
//    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            branchNum = Integer.parseInt(String.valueOf(comboBox1.getSelectedItem()));
            if(comboBox2.getSelectedItem()=="Shortage") {
                Map<String, Integer> map = reportController.createNewShortageReport(branchNum);
                    String[] title = new String[]{"Catalog Number", "Amount"};

                    String[][] all_shortage = new String[map.size()][2];
                    int rowIndex = 0;

                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        all_shortage[rowIndex][0] = entry.getKey();
                        all_shortage[rowIndex][1] = String.valueOf(entry.getValue());
                        rowIndex++;
                    }
                DefaultTableModel model1 = new DefaultTableModel(all_shortage, title);

                    table.setModel(model1);
                    Component parent = table.getParent();
                    if (parent instanceof JScrollPane) {
                        sp = (JScrollPane) parent;
                        sp.setViewportView(table);
                    }
                    text2.setText("");
            }if(comboBox2.getSelectedItem()=="Damage") {
                Map<Item, damageType> map = reportController.createNewDamageReport(branchNum);
                String[] title = new String[]{"Catalog_Number", "Category ",
                        "ItemID", "Damage/Expired ", "Damage_type", "Days_expired"};
                String[][] all_damage = new String[map.size()][6];
                int rowIndex = 0;

                for (Map.Entry<Item, damageType> entry : map.entrySet()) {
                    all_damage[rowIndex][0] = String.valueOf(entry.getKey().getCatalogNumber());
                    all_damage[rowIndex][1] = String.valueOf(entry.getKey().getCategory());
                    all_damage[rowIndex][2] = String.valueOf(entry.getKey().getItemID());
                    all_damage[rowIndex][3] = String.valueOf(entry.getValue().toString());
                    all_damage[rowIndex][4] = String.valueOf(entry.getKey().getDamageTypeItem());
                    all_damage[rowIndex][5] = String.valueOf(entry.getKey().getExpiredDate());
                    rowIndex++;
                }
                DefaultTableModel model2 = new DefaultTableModel(all_damage, title);

                table.setModel(model2);
                Component parent = table.getParent();
                if (parent instanceof JScrollPane) {
                    sp = (JScrollPane) parent;
                    sp.setViewportView(table);
                }
            }
            if(comboBox2.getSelectedItem()=="Inventory") {
                Map<String, Integer> inventory_rep = reportController.createNewInventoryReport(branchNum);
                String[] title = new String[]{"Catalog Number", "Amount"};
                int rowIndex = 0;
                String[][] all_inventory = new String[inventory_rep.size()][2];
                for (Map.Entry<String, Integer> entry : inventory_rep.entrySet()) {
                    all_inventory[rowIndex][0] = entry.getKey();
                    all_inventory[rowIndex][1] = String.valueOf(entry.getValue());
                    rowIndex++;
                }
                DefaultTableModel model = new DefaultTableModel(all_inventory, title);

                table.setModel(model);
                Component parent = table.getParent();
                if (parent instanceof JScrollPane) {
                    sp = (JScrollPane) parent;
                    sp.setViewportView(table);
                }
                text2.setText("");
                }
            }
        if(e.getSource() == button1){
            this.dispose();
            guiStorekeeper.showStorekeeperGUI();
        }
    }
}
