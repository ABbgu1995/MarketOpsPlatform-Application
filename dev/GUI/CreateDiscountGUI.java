package GUI;

import BL.Inventory.Discount;
import BL.Inventory.DiscountController;
import BL.Inventory.Item;
import BL.Inventory.status;
import BL.Suppliers.SupplierController;
import DAL.Inventory.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static UI.Inventory.UserInterface.upDateDataBase;
import static UI.Suppliers.Menu.setupInformation;

public class CreateDiscountGUI  extends JFrame implements ActionListener {

    JButton button;
    JButton button1;
    JTextField text1;
    JTextField text2;
    JTextField text3;
    JTextField text4;
    JTextArea text5;
    JTextField text6;
    JComboBox comboBox1;
    int branchNum;
    GUIStorekeeper guiStorekeeper;
    CreateDiscountGUI(GUIStorekeeper guiStorekeeper){
        this.guiStorekeeper = guiStorekeeper;
        this.setTitle("Create Discount");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        button = new JButton("Create Discount");
        button.addActionListener(this);
        button.setBounds(450, 50, 200, 50); // Set button's position and size
        button.setFont(new Font("David", Font.BOLD, 18));

        button1 = new JButton("Back to the main menu");
        button1.addActionListener(this);
        button1.setBounds(425, 450, 250, 50); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 18));

        JLabel label2 = new JLabel("Enter branch number:");
        label2.setBounds(30, 50, 200, 30); // Set label's position and size
        label2.setFont(new Font("David", Font.BOLD, 18));

        JLabel label3 = new JLabel("Enter discount percent:");
        label3.setBounds(30, 90, 200, 30); // Set label's position and size
        label3.setFont(new Font("David", Font.BOLD, 18));

        JLabel label4 = new JLabel("Date from: (dd/MM/yyyy):");
        label4.setBounds(30, 130, 300, 30); // Set label's position and size
        label4.setFont(new Font("David", Font.BOLD, 18));

        JLabel label5 = new JLabel("Date to: (dd/MM/yyyy):");
        label5.setBounds(30, 170, 300, 30); // Set label's position and size
        label5.setFont(new Font("David", Font.BOLD, 18));

        JLabel label6 = new JLabel("Choose a type:");
        label6.setBounds(30, 210, 200, 30); // Set label's position and size
        label6.setFont(new Font("David", Font.BOLD, 18));

        JLabel label7 = new JLabel("Enter catalog number or category:");
        label7.setBounds(30, 250, 300, 30); // Set label's position and size
        label7.setFont(new Font("David", Font.BOLD, 18));

        text1 = new JTextField();
        text1.setPreferredSize(new Dimension(250, 40));
        text1.setBounds(250, 50, 100, 30);

        text2 = new JTextField();
        text2.setPreferredSize(new Dimension(250, 40));
        text2.setBounds(250, 90, 100, 30);

        text3 = new JTextField();
        text3.setPreferredSize(new Dimension(250, 40));
        text3.setBounds(250, 130, 100, 30);

        text4 = new JTextField();
        text4.setPreferredSize(new Dimension(250, 40));
        text4.setBounds(250, 170, 100, 30);

        String[] type = {"Category", "Catalog number"};
        comboBox1 = new JComboBox(type);
        comboBox1.addActionListener(this);
        comboBox1.setBounds(250, 210, 100, 30);

        text5 = new JTextArea();
        text5.setPreferredSize(new Dimension(250, 40));
        text5.setBounds(410, 150, 300, 150);
        text5.setFont(new Font("David", Font.BOLD, 18));

        text6 = new JTextField();
        text6.setPreferredSize(new Dimension(250, 40));
        text6.setBounds(250, 290, 100, 30);

        this.add(button);
        this.add(button1);

        this.add(label2);
        this.add(label3);
        this.add(label4);
        this.add(label5);
        this.add(label6);
        this.add(label7);
        this.add(comboBox1);
        this.add(text1);
        this.add(text2);
        this.add(text3);
        this.add(text4);
        this.add(text5);
        this.add(text6);
        this.setVisible(true);


    }

//    public static void main(String[] args) throws SQLException, InterruptedException {
//        // Create an instance of your GUI class
//        Database.clearDatabase(Database.connect());
//        SupplierController.clearAllTables();
//        setupInformation();
//        upDateDataBase();
//
//        CreateDiscountGUI gui = new CreateDiscountGUI();
//    }





    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            branchNum = Integer.parseInt(text1.getText());
            int discount = Integer.parseInt(text2.getText());
            DiscountController discountController = DiscountController.getInstance();
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            Date dateFrom=null;
            try {
                dateFrom = dateFormat1.parse(text3.getText());
            } catch (ParseException ex) {
                System.out.println("Invalid date format");
            }

            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
            Date dateTo=null;
            try {
                dateTo = dateFormat2.parse(text4.getText());
            } catch (ParseException ex) {
                System.out.println("Invalid date format");
            }
            status curr_sts=status.failure;
            if (comboBox1.getSelectedItem()=="Category"){
                String category = text6.getText();
                curr_sts = discountController.createNewDiscountByCategory(branchNum, discount, dateFrom, dateTo, category);
            }

            if (comboBox1.getSelectedItem()=="Catalog number"){
                String catalogNum = text6.getText();
                curr_sts = discountController.createNewDiscountByCatalogNumber(branchNum, discount, dateFrom, dateTo, catalogNum);
            }
            if(curr_sts==status.failure){
                text5.setText("The discount has not been updated, please try again");
            }else {
                text5.setText("The discount has been updated");
            }

        }
        if(e.getSource() == button1){
            this.dispose();
            guiStorekeeper.showStorekeeperGUI();
        }
    }
}
