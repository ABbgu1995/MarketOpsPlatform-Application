package GUI;

import BL.Inventory.*;
import DAL.Inventory.ReportDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ShowReportGUI extends JFrame implements ActionListener {
    JButton button;
    JButton button1;
    JComboBox comboBox1;
    JComboBox comboBox2;
    JTextField text1;
    JTextArea text2;
    JTable table;
    BranchController branchController;
    int reportID;
    ReportDAO reportDAO;
    JScrollPane sp;
    int branchNum;
    ReportsGUI reportsGui;
    ShowReportGUI(ReportsGUI reportsGui) {
        this.reportsGui = reportsGui;
        this.setTitle("Show Reports");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        String[] branches = {"1", "2"};
        String[] reportsName = {"Shortage", "Inventory","Damage"};

        JLabel label1 = new JLabel("Choose branch number:");
        label1.setBounds(30, 50, 200, 30); // Set label's position and size
        label1.setFont(new Font("David", Font.BOLD, 18));
        comboBox1 = new JComboBox(branches);
        comboBox1.addActionListener(this);
        comboBox1.setBounds(250, 50, 100, 30);

        JLabel label2 = new JLabel("Choose report type:");
        label2.setBounds(30, 100, 200, 30); // Set label's position and size
        label2.setFont(new Font("David", Font.BOLD, 18));
        comboBox2 = new JComboBox(reportsName);
        comboBox2.addActionListener(this);
        comboBox2.setBounds(250, 100, 100, 30);

        JLabel label3 = new JLabel("Enter report ID:");
        label3.setBounds(30, 150, 200, 30); // Set label's position and size
        label3.setFont(new Font("David", Font.BOLD, 18));
        text1 = new JTextField();
        text1.setPreferredSize(new Dimension(250, 40));
        text1.setBounds(250, 150, 100, 30);

        text2 = new JTextArea();
        text2.setPreferredSize(new Dimension(250, 40));
        text2.setBounds(55, 450, 320, 60);
        text2.setFont(new Font("David", Font.BOLD, 18));

        table = new JTable();
//        table.setBounds(400, 100, 300, 430);
        sp = new JScrollPane(table);
        sp.setBounds(20, 200, 750, 200);

        button = new JButton("Show report");
        button.addActionListener(this);
        button.setBounds(400, 100, 150, 50); // Set button's position and size
        button.setFont(new Font("David", Font.BOLD, 18));

        button1 = new JButton("Back to the main menu");
        button1.addActionListener(this);
        button1.setBounds(450, 450, 250, 50); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 18));

        reportDAO = ReportDAO.getInstance();
        this.add(comboBox1);
        this.add(comboBox2);
        this.add(text1);
        this.add(text2);
        this.add(button);
        this.add(button1);
        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(sp);
        this.setVisible(true);
    }

//    public static void main(String[] args) throws SQLException, InterruptedException {
//        // Create an instance of your GUI class
////        Database.clearDatabase(Database.connect());
////        SupplierController.clearAllTables();
////        setupInformation();
////        upDateDataBase();
//
//        ShowReportGUI Show_Item_amountGUI = new ShowReportGUI(new GUIManager());
//    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            branchNum = Integer.parseInt(String.valueOf(comboBox1.getSelectedItem()));
            reportID = Integer.parseInt(text1.getText());
            if(comboBox2.getSelectedItem()=="Shortage") {
                RShortage currReport = reportDAO.getRSortage(reportID, branchNum);
                if (currReport != null) {
                    String[] title = new String[]{"Catalog Number", "Amount"};
                    Map<String, Integer> map = currReport.getAllShortage();

                    String[][] all_shortage = new String[map.size()][2];
                    int rowIndex = 0;

                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        all_shortage[rowIndex][0] = entry.getKey();
                        all_shortage[rowIndex][1] = String.valueOf(entry.getValue());
                        rowIndex++;
                    }
                    DefaultTableModel model = new DefaultTableModel(all_shortage, title);

                    table.setModel(model);
                    Component parent = table.getParent();
                    if (parent instanceof JScrollPane) {
                        sp = (JScrollPane) parent;
                        sp.setViewportView(table);
                    }
                    text2.setText("");
                } else {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    table.setModel(model);
                    text2.setText("There is no shortage report number " + reportID + "\nin branch number " + branchNum);
                }
            }if(comboBox2.getSelectedItem()=="Damage") {
                RDamage currReport = reportDAO.getRDamage(reportID, branchNum);
                if (currReport != null) {
                    String[] title = new String[]{"Catalog_Number", "Category ",
                            "ItemID", "Damage/Expired ", "Damage_type", "Days_expired"};
                    Map<Item, damageType> map = currReport.getAllDamage();

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
                    DefaultTableModel model = new DefaultTableModel(all_damage, title);

                    table.setModel(model);
                    Component parent = table.getParent();
                    if (parent instanceof JScrollPane) {
                        sp = (JScrollPane) parent;
                        sp.setViewportView(table);
                    }
                    text2.setText("");
                } else {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    table.setModel(model);
                    text2.setText("There is no damage report number " + reportID + "\nin branch number " + branchNum);
                }
            } if(comboBox2.getSelectedItem()=="Inventory") {
                RInventory currReport = reportDAO.getRInventory(reportID, branchNum);
                if (currReport != null) {
                    String[] title = new String[]{"Catalog Number", "Amount"};
                    Map<String, Integer> map = currReport.getAllInventory();

                    String[][] all_inventory = new String[map.size()][2];
                    int rowIndex = 0;

                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
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
                } else {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    table.setModel(model);
                    text2.setText("There is no inventory report number " + reportID + "\nin branch number " + branchNum);
                }
            }

        }if(e.getSource() == button1){
            this.dispose();
            reportsGui.showManagerGUI();
        }
    }
}
