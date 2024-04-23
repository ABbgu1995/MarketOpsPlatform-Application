package GUI;
import BL.Inventory.*;
import BL.Suppliers.SupplierController;
import DAL.Inventory.Database;
import DAL.Inventory.ItemDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static UI.Inventory.UserInterface.upDateDataBase;
import static UI.Suppliers.Menu.setupInformation;

public class AddItemGUI extends JFrame implements ActionListener {
    JButton button;
    JButton button1;
    JTextField text2;
    JTextField text3;
    JTextField text4;
    JTextField text5;
    JTextField text6;
    JTextField text7;
    JTextField text9;
    JTextField text10;
    JTextField text11;
    JTextField text12;

    JComboBox comboBox1;
    JComboBox comboBox2;
    ItemDAO itemDAO;
    JTextArea text15;
    int branchNum;
    BranchController branchController;
    GUIStorekeeper guiStorekeeper;
    AddItemGUI( GUIStorekeeper guiStorekeeper) {
        this.guiStorekeeper = guiStorekeeper;
        itemDAO = ItemDAO.getInstance();
        branchController = BranchController.getInstance();
        this.setTitle("Add Item");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        button = new JButton("Add item");
        button.addActionListener(this);
        button.setBounds(480, 50, 150, 50); // Set button's position and size
        button.setFont(new Font("David", Font.BOLD, 18));

        button1 = new JButton("Back to the main menu");
        button1.addActionListener(this);
        button1.setBounds(425, 450, 250, 50); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 18));


        JLabel label2 = new JLabel("Enter branchNum:");
        label2.setBounds(30, 10, 150, 30); // Set label's position and size
        label2.setFont(new Font("David", Font.BOLD, 18));

        JLabel label3 = new JLabel("Catalog number:");
        label3.setBounds(30, 50, 150, 30); // Set label's position and size
        label3.setFont(new Font("David", Font.BOLD, 18));

        JLabel label4 = new JLabel("Maker:");
        label4.setBounds(30, 90, 150, 30); // Set label's position and size
        label4.setFont(new Font("David", Font.BOLD, 18));

        JLabel label5 = new JLabel("Supplier:");
        label5.setBounds(30, 130, 150, 30); // Set label's position and size
        label5.setFont(new Font("David", Font.BOLD, 18));

        JLabel label6 = new JLabel("Supplier price:");
        label6.setBounds(30, 170, 150, 30); // Set label's position and size
        label6.setFont(new Font("David", Font.BOLD, 18));

        JLabel label7 = new JLabel("Store price:");
        label7.setBounds(30, 210, 150, 30); // Set label's position and size
        label7.setFont(new Font("David", Font.BOLD, 18));

        JLabel label9 = new JLabel("Category:");
        label9.setBounds(30, 250, 150, 30); // Set label's position and size
        label9.setFont(new Font("David", Font.BOLD, 18));

        JLabel label10 = new JLabel("Sub category:");
        label10.setBounds(30, 290, 150, 30); // Set label's position and size
        label10.setFont(new Font("David", Font.BOLD, 18));

        JLabel label11 = new JLabel("Sub sub category:");
        label11.setBounds(30, 330, 150, 30); // Set label's position and size
        label11.setFont(new Font("David", Font.BOLD, 18));

        JLabel label12 = new JLabel("Expire date:");
        label12.setBounds(30, 370, 150, 30); // Set label's position and size
        label12.setFont(new Font("David", Font.BOLD, 18));

        JLabel label13 = new JLabel("Damage type:");
        label13.setBounds(30, 410, 150, 30); // Set label's position and size
        label13.setFont(new Font("David", Font.BOLD, 18));

        JLabel label14 = new JLabel("Location:");
        label14.setBounds(30, 450, 150, 30); // Set label's position and size
        label14.setFont(new Font("David", Font.BOLD, 18));

        text2 = new JTextField();
        text2.setPreferredSize(new Dimension(250, 40));
        text2.setBounds(250, 10, 100, 30);

        text3 = new JTextField();
        text3.setPreferredSize(new Dimension(250, 40));
        text3.setBounds(250, 50, 100, 30);

        text4 = new JTextField();
        text4.setPreferredSize(new Dimension(250, 40));
        text4.setBounds(250, 90, 100, 30);

        text5 = new JTextField();
        text5.setPreferredSize(new Dimension(250, 40));
        text5.setBounds(250, 130, 100, 30);

        text6 = new JTextField();
        text6.setPreferredSize(new Dimension(250, 40));
        text6.setBounds(250, 170, 100, 30);

        text7 = new JTextField();
        text7.setPreferredSize(new Dimension(250, 40));
        text7.setBounds(250, 210, 100, 30);

//        text8 = new JTextField();
//        text8.setPreferredSize(new Dimension(250, 40));
//        text8.setBounds(250, 250, 100, 30);

        text9 = new JTextField();
        text9.setPreferredSize(new Dimension(250, 40));
        text9.setBounds(250, 250, 100, 30);

        text10 = new JTextField();
        text10.setPreferredSize(new Dimension(250, 40));
        text10.setBounds(250, 290, 100, 30);

        text11 = new JTextField();
        text11.setPreferredSize(new Dimension(250, 40));
        text11.setBounds(250, 330, 100, 30);

        text12 = new JTextField();
        text12.setPreferredSize(new Dimension(250, 40));
        text12.setBounds(250, 370, 100, 30);

        String[] damageType = {"None", "Expire date", "Damage"};
        comboBox1 = new JComboBox(damageType);
        comboBox1.addActionListener(this);
        comboBox1.setBounds(250, 410, 100, 30);

        String[] location = {"Store", "Stock"};
        comboBox2 = new JComboBox(location);
        comboBox2.addActionListener(this);
        comboBox2.setBounds(250, 450, 100, 30);

        text15 = new JTextArea();
        text15.setPreferredSize(new Dimension(250, 40));
        text15.setBounds(410, 150, 300, 150);
        text15.setFont(new Font("David", Font.BOLD, 18));

        this.add(button);
        this.add(button1);

        this.add(label2);
        this.add(label3);
        this.add(label4);
        this.add(label5);
        this.add(label6);
        this.add(label7);
        this.add(label9);
        this.add(label10);
        this.add(label11);
        this.add(label12);
        this.add(label13);
        this.add(label14);
        this.add(comboBox1);
        this.add(comboBox2);
        this.add(text2);
        this.add(text3);
        this.add(text4);
        this.add(text5);
        this.add(text6);
        this.add(text7);
        this.add(text9);
        this.add(text10);
        this.add(text11);
        this.add(text12);
        this.add(text15);
        this.setVisible(true);

    }

//    public static void main(String[] args) throws SQLException, InterruptedException {
//        // Create an instance of your GUI class
//        Database.clearDatabase(Database.connect());
//        SupplierController.clearAllTables();
//        setupInformation();
//        upDateDataBase();
//
//        AddItemGUI gui = new AddItemGUI();
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            branchNum = Integer.parseInt(text2.getText());
            String catalogNumber = text3.getText();
            String maker = text4.getText();
            String supplier = text5.getText();
            Double supplierPrice = Double.valueOf(text6.getText());
            Double StorePrice = Double.valueOf(text7.getText());
            String category = text9.getText();
            String subCat = text10.getText();
            String subSubCat = text11.getText();
            DateFormat dateFormat = new SimpleDateFormat(text12.getText());
            Date expireDate;
            try {
                expireDate = dateFormat.parse(text12.getText());
            } catch (ParseException ex) {
                expireDate = null;
            }
            damageType damageType1 = damageType.None;
            if (comboBox1.getSelectedItem()=="Damage"){
                damageType1=damageType.Damage;
            }
            if (comboBox1.getSelectedItem()=="None"){
                damageType1=damageType.None;
            }
            if (comboBox1.getSelectedItem()=="Expire date"){
                damageType1=damageType.ExpiredDay;
            }
            place loc = place.store;
            if (comboBox2.getSelectedItem()=="Store"){
                loc=place.store;
            }
            if (comboBox2.getSelectedItem()=="Stock"){
                loc=place.stock;
            }

             Item item = branchController.createItemToBranch(branchNum,catalogNumber,maker, supplier, supplierPrice, StorePrice, expireDate, category, subCat, subSubCat);
            item.setItemPlace(loc);
            item.setDamage(damageType1);
            text15.setText("The item has been set. \nThe itemID is: " + item.getItemID());
        }if(e.getSource() == button1){
            this.dispose();
            guiStorekeeper.showStorekeeperGUI();
        }
    }
}

