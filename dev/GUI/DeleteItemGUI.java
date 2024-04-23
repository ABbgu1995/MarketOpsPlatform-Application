package GUI;

import BL.Inventory.BranchController;
import BL.Inventory.Item;
import BL.Suppliers.SupplierController;
import DAL.Inventory.Database;
import DAL.Inventory.ItemDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;

import static UI.Inventory.UserInterface.upDateDataBase;
import static UI.Suppliers.Menu.setupInformation;

public class DeleteItemGUI extends JFrame implements ActionListener {

    JButton button;
    JButton button1;
    JTextField text1;
    JTextField text2;
    JTextArea text3;
    JTextField text4;

    ItemDAO itemDAO;
    int branchNum;
    int itemID;
    BranchController branchController;
    GUIStorekeeper guiStorekeeper;
    DeleteItemGUI(GUIStorekeeper guiStorekeeper) {
        this.guiStorekeeper = guiStorekeeper;
        branchController = BranchController.getInstance();
        itemDAO = ItemDAO.getInstance();
        this.setTitle("Delete Item");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        button = new JButton("Delete item");
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

        JLabel label2 = new JLabel("Enter itemID:");
        label2.setBounds(30, 100, 200, 30); // Set label's position and size
        label2.setFont(new Font("David", Font.BOLD, 18));

        text1 = new JTextField();
        text1.setPreferredSize(new Dimension(250, 40));
        text1.setBounds(235, 50, 100, 30);

        text2 = new JTextField();
        text2.setPreferredSize(new Dimension(250, 40));
        text2.setBounds(235, 100, 100, 30);

        text3 = new JTextArea();
        text3.setPreferredSize(new Dimension(250, 40));
        text3.setBounds(65, 250, 330, 70);
        text3.setFont(new Font("David", Font.BOLD, 18));


        this.add(button);
        this.add(button1);
        this.add(label1);
        this.add(label2);
        this.add(text1);
        this.add(text2);
        this.add(text3);
        this.setVisible(true);

    }


//    public static void main(String[] args) throws InterruptedException, SQLException {
//        // Create an instance of your GUI class
//        Database.clearDatabase(Database.connect());
//        SupplierController.clearAllTables();
//        setupInformation();
//        upDateDataBase();
//        DeleteItemGUI Delete_ItemGUI = new DeleteItemGUI();
//    }


    @Override
    public void actionPerformed (ActionEvent e){
        if (e.getSource() == button) {
            branchNum = Integer.parseInt(text1.getText());
            itemID = Integer.parseInt(text2.getText());
            Item item2 = branchController.foundItemInBranch(branchNum, itemID);
            if (item2 == null) {
                text3.setText("There is no item id " + itemID + "\nIn branch number " + branchNum);
            }else {
                boolean remove_status = branchController.deleteItemFromBranch(item2, branchNum);
                if (remove_status) {
                    text3.setText("The deletion was completed successfully");
                } else {
                    text3.setText("The item was not deleted, please try again");

                }
            }
        }
        if(e.getSource() == button1){
            this.dispose();
            guiStorekeeper.showStorekeeperGUI();
        }
    }
}