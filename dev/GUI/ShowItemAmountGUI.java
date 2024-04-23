package GUI;

import BL.Inventory.BranchController;
import DAL.Inventory.ItemDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ShowItemAmountGUI extends JFrame implements ActionListener {
    JButton button;
    JButton button1;
    JTextField text1;
    JTextField text2;
    JTextArea text3;
    JTextField text4;
    JTextArea text5;
    ItemDAO itemDAO;
    int branchNum;
    String catalog_number;
    BranchController branchController;
    ReportsGUI reportsGui;
    GUIStorekeeper guiStorekeeper;

    ShowItemAmountGUI(Object gui, String typeGUI){
        if(typeGUI=="manager"){
            this.reportsGui = (ReportsGUI) gui;
            this.guiStorekeeper=null;
        }else{
            this.guiStorekeeper = (GUIStorekeeper) gui;
            this.reportsGui =null;
        }
        branchController = BranchController.getInstance();

        itemDAO = ItemDAO.getInstance();
        this.setTitle("Show Item amount");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        button = new JButton("Show amount");
        button.addActionListener(this);
        button.setBounds(65, 165, 150, 50); // Set button's position and size
        button.setFont(new Font("David", Font.BOLD, 18));

        button1 = new JButton("Back to the main menu");
        button1.addActionListener(this);
        button1.setBounds(65, 405, 250, 50); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 18));

        JLabel label1 = new JLabel("Enter branch number:");
        label1.setBounds(30, 50, 200, 30); // Set label's position and size
        label1.setFont(new Font("David", Font.BOLD, 18));

        JLabel label2 = new JLabel("Enter catalog number:");
        label2.setBounds(30, 100, 200, 30); // Set label's position and size
        label2.setFont(new Font("David", Font.BOLD, 18));

        JLabel label3 = new JLabel("The amount is:");
        label3.setBounds(350, 75, 200, 30); // Set label's position and size
        label3.setFont(new Font("David", Font.BOLD, 18));

        text1 = new JTextField();
        text1.setPreferredSize(new Dimension(250, 40));
        text1.setBounds(235, 50, 100, 30);

        text2 = new JTextField();
        text2.setPreferredSize(new Dimension(250, 40));
        text2.setBounds(235, 100, 100, 30);

        text3 = new JTextArea();
        text3.setPreferredSize(new Dimension(250, 40));
        text3.setBounds(65, 250, 300, 50);
        text3.setFont(new Font("David", Font.BOLD, 18));

        text4 = new JTextField();
        text4.setPreferredSize(new Dimension(250, 40));
        text4.setBounds(485, 65, 60, 50);

        this.add(button);
        this.add(button1);
        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(text1);
        this.add(text2);
        this.add(text3);
        this.add(text4);
        text4.setEditable(false);

        this.setVisible(true);

    }



//    public static void main(String[] args) throws SQLException, InterruptedException {
//        // Create an instance of your GUI class
//        Database.clearDatabase(Database.connect());
//        SupplierController.clearAllTables();
//        setupInformation();
//        upDateDataBase();
//        ShowItemAmountGUI Show_Item_amountGUI = new ShowItemAmountGUI(new GUIManager());
//    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button) {
            branchNum = Integer.parseInt(text1.getText());
            catalog_number = text2.getText();
            Map<String, Integer> map = branchController.countItemsInBranch(branchNum);
            Integer int1 = map.get(catalog_number);
            if (int1 != null) {
                text4.setText(int1.toString());
                text3.setText("");

            }else{
                text3.setText("there is no catalog number " +catalog_number+ "\nin branch number "+branchNum);
            }
        }if(e.getSource() == button1){
            this.dispose();
            if(reportsGui !=null) {
                reportsGui.showManagerGUI();
            }else{
                guiStorekeeper.showStorekeeperGUI();
            }
        }
    }
}
