package GUI.SuppliersGUI.ContractProductGUI;

import BL.Suppliers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static UI.Suppliers.Menu.setupInformation;

public class AddContractProductGUI extends JFrame implements ActionListener {
    JButton backButton;
    JButton addButton;
    JButton undoButton;
    JTextField supplierIdText;
    JTextArea feedbackText;
    JTextField quantityText;
    JTextField basePriceText;
    JTextField nameText;
    JTextField weightText;
    MainContractManagmentGUI  mainContractManagmentGUI;

    SupplierController supplierController;

    Product lastAddProduct;

    public AddContractProductGUI(MainContractManagmentGUI mainContractManagmentGUI) {


        this.mainContractManagmentGUI = mainContractManagmentGUI;
        supplierController = SupplierController.getInstance();

        this.setTitle("Add new product to contract");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);


        JLabel labelId = new JLabel("Enter supplierId:");
        labelId.setBounds(30, 50, 200, 30); // Set label's position and size
        labelId.setFont(new Font("David", Font.BOLD, 18));

        supplierIdText = new JTextField();
        supplierIdText.setPreferredSize(new Dimension(250, 40));
        supplierIdText.setBounds(235, 50, 100, 30);


        JLabel labelAddress = new JLabel("Enter product quantity:");
        labelAddress.setBounds(30, 100, 200, 30); // Set label's position and size
        labelAddress.setFont(new Font("David", Font.BOLD, 18));

        quantityText = new JTextField();
        quantityText.setPreferredSize(new Dimension(250, 40));
        quantityText.setBounds(235, 100, 100, 30);


        JLabel labelMobility = new JLabel("Enter product base price");
        labelMobility.setBounds(30, 150, 200, 30); // Set label's position and size
        labelMobility.setFont(new Font("David", Font.BOLD, 18));

        basePriceText = new JTextField();
        basePriceText.setPreferredSize(new Dimension(250, 40));
        basePriceText.setBounds(235, 150, 100, 30);


        JLabel labelName = new JLabel("Enter product name:");
        labelName.setBounds(30, 200, 200, 30); // Set label's position and size
        labelName.setFont(new Font("David", Font.BOLD, 18));

        nameText = new JTextField();
        nameText.setPreferredSize(new Dimension(250, 40));
        nameText.setBounds(235, 200, 100, 30);


        JLabel shipmentTimeLable = new JLabel("Enter product weight:");
        shipmentTimeLable.setBounds(30, 250, 200, 30); // Set label's position and size
        shipmentTimeLable.setFont(new Font("David", Font.BOLD, 18));

        weightText = new JTextField();
        weightText.setPreferredSize(new Dimension(250, 40));
        weightText.setBounds(235, 250, 100, 30);


        //back button
        backButton = new JButton("<-");
        backButton.addActionListener(this);
        backButton.setBounds(25, 8, 55, 25); // Set button's position and size
        backButton.setFont(new Font("David", Font.BOLD, 18));

        //enter button
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        addButton.setBounds(30, 300, 170, 50); // Set button's position and size
        addButton.setFont(new Font("David", Font.BOLD, 18));

        undoButton = new JButton("Undo");
        undoButton.addActionListener(this);
        undoButton.setBounds(250, 300, 70, 25); // Set button's position and size
        undoButton.setFont(new Font("David", Font.BOLD, 12));


        //feedback text box
        feedbackText = new JTextArea();
        feedbackText.setPreferredSize(new Dimension(250, 40));
        feedbackText.setBounds(400, 150, 350, 150);
        feedbackText.setFont(new Font("David", Font.BOLD, 18));

        this.add(addButton);
        this.add(backButton);
        this.add(undoButton);
        this.add(labelId);
        this.add(labelAddress);
        this.add(labelMobility);
        this.add(labelName);
        this.add(shipmentTimeLable);
        this.add(feedbackText);
        this.add(basePriceText);
        this.add(quantityText);
        this.add(nameText);
        this.add( weightText);
        this.add(supplierIdText);
        this.setVisible(true);


    }

    public static void main(String[] args) throws InterruptedException, SQLException {

        SupplierController.clearAllTables();
        setupInformation();

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        //int supplierId, int quantity, double basePrice, double weight,String name)

        if (e.getSource() == addButton) {
            int supplierId = Integer.parseInt(supplierIdText.getText());
            int quantity = Integer.parseInt(this.quantityText.getText());
            String name = nameText.getText();
            double basePrice = Double.parseDouble(this.basePriceText.getText());
            double weight = Double.parseDouble(this.weightText.getText());
            lastAddProduct = new Product(supplierId,quantity,basePrice,weight,name);
            SupplierController.addProductToContract(lastAddProduct);
            feedbackText.setText("product added successfully!\n");


        }

        if (e.getSource() == undoButton) {
            if(lastAddProduct!=null) {
                SupplierController.deleteProductFromContract(lastAddProduct);
                lastAddProduct = null;
                feedbackText.setText("undo successfully!\n");
            }
        }

        if (e.getSource() == backButton) {
            this.dispose();
            this.mainContractManagmentGUI.showMainContractManagerGUI();

        }
    }
}
