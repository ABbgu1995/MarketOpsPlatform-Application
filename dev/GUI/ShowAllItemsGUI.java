package GUI;

import BL.Inventory.Item;
import DAL.Inventory.ItemDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class ShowAllItemsGUI extends JFrame implements ActionListener {

    JButton button;
    JButton button1;
    JTextArea text2;
    JTextField text1;
    JTable table;
    JScrollPane sp;
    int branchNum;
    ItemDAO itemDAO;
    ReportsGUI reportsGui;
    GUIStorekeeper guiStorekeeper;
    ShowAllItemsGUI( Object gui, String typeGUI){
        if(typeGUI=="manager"){
            this.reportsGui = (ReportsGUI) gui;
            this.guiStorekeeper=null;
        }else{
            this.guiStorekeeper = (GUIStorekeeper) gui;
            this.reportsGui =null;
        }
        this.setTitle("Show All Items In Branch");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        JLabel label1 = new JLabel("Enter branch number:");
        label1.setBounds(30, 50, 200, 30); // Set label's position and size
        label1.setFont(new Font("David", Font.BOLD, 18));

        text1 = new JTextField();
        text1.setPreferredSize(new Dimension(250, 40));
        text1.setBounds(250, 50, 100, 30);

        text2 = new JTextArea();
        text2.setPreferredSize(new Dimension(250, 40));
        text2.setBounds(55, 190, 320, 60);
        text2.setFont(new Font("David", Font.BOLD, 18));

        table = new JTable();
        table.setBounds(400, 50, 300, 430);
        sp = new JScrollPane(table);
        sp.setBounds(400, 50, 300, 430);

        button = new JButton("Show all items");
        button.addActionListener(this);
        button.setBounds(75, 100, 150, 50); // Set button's position and size
        button.setFont(new Font("David", Font.BOLD, 18));

        button1 = new JButton("Back to the main menu");
        button1.addActionListener(this);
        button1.setBounds(75, 300, 250, 50); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 18));

        itemDAO = ItemDAO.getInstance();
        this.add(text1);
        this.add(text2);
        this.add(button);
        this.add(button1);
        this.add(label1);
        this.add(sp);
        this.setVisible(true);


    }


//    public static void main(String[] args) throws SQLException, InterruptedException {
//        // Create an instance of your GUI class
////        Database.clearDatabase(Database.connect());
////        SupplierController.clearAllTables();
////        setupInformation();
////        upDateDataBase();
//        ShowAllItemsGUI Show_Items_GUI = new ShowAllItemsGUI(new GUIManager());
//    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            branchNum = Integer.parseInt(text1.getText());
            Map<Integer, Item> map = itemDAO.getAllItems(branchNum);
            if (!map.isEmpty()){
            String[][] all_items = new String[map.size()][2];
            String[] title = new String[]{"ItemID", "catalog number"};

            int rowIndex = 0;
            for (Map.Entry<Integer, Item> entry : map.entrySet()) {
                all_items[rowIndex][0] = entry.getKey().toString();
                all_items[rowIndex][1] = String.valueOf(entry.getValue().getCatalogNumber());
                rowIndex++;
            }
                Arrays.sort(all_items, Comparator.comparingInt(item -> Integer.parseInt(item[0])));
            DefaultTableModel model = new DefaultTableModel(all_items,title);

            table.setModel(model);
            Component parent = table.getParent();
            if (parent instanceof JScrollPane) {
                sp = (JScrollPane) parent;
                sp.setViewportView(table);
            }
            text2.setText("");}
            else {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                table.setModel(model);
                text2.setText("There is no items in branch number " +branchNum);

            }
        }
        if(e.getSource() == button1){
            this.dispose();
            if(reportsGui !=null) {
                reportsGui.showManagerGUI();
            }else{
                guiStorekeeper.showStorekeeperGUI();
            }
        }
    }
}
