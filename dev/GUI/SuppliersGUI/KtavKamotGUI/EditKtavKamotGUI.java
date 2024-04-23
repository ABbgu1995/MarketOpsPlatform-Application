package GUI.SuppliersGUI.KtavKamotGUI;

import BL.Suppliers.AmountToDiscount;
import BL.Suppliers.SupplierController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;

import static UI.Suppliers.Menu.setupInformation;


public class EditKtavKamotGUI extends JFrame implements ActionListener{
    JRadioButton editExitsRule;
    JRadioButton deleteExistRule;

    JRadioButton addNewRule;


    JButton executeButton;

    JButton undoButton;
    JButton backButton;

    JTextArea feedbackText;
    JTextField supplierIdText;
    JTextField itemIdText;
    JTextField amountText;
    JTextField discountText;

    AmountToDiscount undoDiscount;

    int operateToUndo;
    double undoUpdate;

    String itemId;

    ManagmentKtavKamotGUI managmentKtavKamotGUI;

    SupplierController supplierController;

    public EditKtavKamotGUI(ManagmentKtavKamotGUI managmentKtavKamotGUI){
        this.managmentKtavKamotGUI = managmentKtavKamotGUI;
        supplierController = SupplierController.getInstance();
        itemId = "";
        operateToUndo=0;

        this.setTitle("edit rules");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        feedbackText = new JTextArea();
        feedbackText.setPreferredSize(new Dimension(250, 40));
        feedbackText.setBounds(360, 150, 390, 100);
        feedbackText.setFont(new Font("David", Font.BOLD, 18));

        JLabel labelId = new JLabel("Enter supplierId:");
        labelId.setBounds(30, 100, 200, 30); // Set label's position and size
        labelId.setFont(new Font("David", Font.BOLD, 18));

        supplierIdText = new JTextField();
        supplierIdText.setPreferredSize(new Dimension(250, 40));
        supplierIdText.setBounds(235, 100, 100, 30);

        JLabel labelItemId = new JLabel("Enter itemId:");
        labelItemId.setBounds(30, 150, 200, 30); // Set label's position and size
        labelItemId.setFont(new Font("David", Font.BOLD, 18));

        itemIdText = new JTextField();
        itemIdText.setPreferredSize(new Dimension(250, 40));
        itemIdText.setBounds(235, 150, 100, 30);

        JLabel labelAmount = new JLabel("Enter amount:");
        labelAmount.setBounds(30, 200, 200, 30); // Set label's position and size
        labelAmount.setFont(new Font("David", Font.BOLD, 18));

        amountText = new JTextField();
        amountText.setPreferredSize(new Dimension(250, 40));
        amountText.setBounds(235, 200, 100, 30);

        JLabel labelDiscount = new JLabel("Enter discount:");
        labelDiscount.setBounds(30, 250, 200, 30); // Set label's position and size
        labelDiscount.setFont(new Font("David", Font.BOLD, 18));

        discountText = new JTextField();
        discountText.setPreferredSize(new Dimension(250, 40));
        discountText.setBounds(235, 250, 100, 30);

        editExitsRule = new JRadioButton("edit rule");
        editExitsRule.setBounds(30,40,80,30);

        deleteExistRule = new JRadioButton("delete rule");
        deleteExistRule.setBounds(130,40,150,30);

        addNewRule = new JRadioButton("add new rule");
        addNewRule.setBounds(230,40,200,30);

        ButtonGroup bg=new ButtonGroup();
        bg.add(editExitsRule);
        bg.add(deleteExistRule);
        bg.add(addNewRule);

        executeButton = new JButton("perform operation");
        executeButton.addActionListener(this);
        executeButton.setBounds(30, 300, 200, 50); // Set button's position and size
        executeButton.setFont(new Font("David", Font.BOLD, 18));

        backButton = new JButton("<-");
        backButton.addActionListener(this);
        backButton.setBounds(25, 8, 55, 25); // Set button's position and size
        backButton.setFont(new Font("David", Font.BOLD, 18));

        undoButton = new JButton("Undo");
        undoButton.addActionListener(this);
        undoButton.setBounds(248, 290, 70, 25); // Set button's position and size
        undoButton.setFont(new Font("David", Font.BOLD, 12));

        this.add(editExitsRule);
        this.add(addNewRule);
        this.add(undoButton);
        this.add(backButton);
        this.add(labelAmount);
        this.add(labelId);
        this.add(labelItemId);
        this.add(labelDiscount);
        this.add(deleteExistRule);
        this.add(executeButton);
        this.add(amountText);
        this.add(supplierIdText);
        this.add(discountText);
        this.add(itemIdText);
        this.add(feedbackText);

//        setSize(300,300);
//        setLayout(null);
        setVisible(true);
    }
    public static void main(String[] args) throws InterruptedException, SQLException {

        SupplierController.clearAllTables();
        setupInformation();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == executeButton) {
            int supplierId = Integer.parseInt(supplierIdText.getText());
            itemId = itemIdText.getText();
            int supplierCategoryId = SupplierController.getSupplierCategoryId(itemId, supplierId);
            int amount = Integer.parseInt(amountText.getText());
            int discount = Integer.parseInt(discountText.getText());

            if(!editExitsRule.isSelected() && !deleteExistRule.isSelected() && !addNewRule.isSelected())
                feedbackText.setText("invalid - choose operation\n");
            else {
                if (supplierCategoryId == 0) {
                    feedbackText.setText("itemId doesn't contain in contract\n");
                } else {
                    if (editExitsRule.isSelected()) {
                        undoUpdate = SupplierController.getDiscountKtavKamot(supplierId,itemId,amount);
                        undoDiscount = new AmountToDiscount(amount,discount,0,supplierId,supplierCategoryId);

                        SupplierController.updateProductToAmountRole(supplierId, supplierCategoryId, amount, discount);
                        feedbackText.setText("change discount percentage successfully\n");
                        operateToUndo=1;
                    }
                    if (deleteExistRule.isSelected()) {
                        SupplierController.removeProductToAmountRule(supplierId, itemId, amount, discount);
                        feedbackText.setText("remove product to amount discount successfully\n");
                        undoDiscount = new AmountToDiscount(amount,discount,0,supplierId,supplierCategoryId);
                        operateToUndo=2;
                    }
                    if(addNewRule.isSelected()){
                        SupplierController.addProductToAmountRole(supplierId,supplierCategoryId,amount,discount);
                        feedbackText.setText("add new product to amount discount successfully\n");
                        undoDiscount = new AmountToDiscount(amount,discount,0,supplierId,supplierCategoryId);
                        operateToUndo=3;
                    }
                }
            }
        }
            if (e.getSource() == backButton) {
                this.dispose();
                this.managmentKtavKamotGUI.showManagmentKtavKamotGUI();
        }
            if(e.getSource()==undoButton){
                switch (operateToUndo){
                    case 0:
                        feedbackText.setText("no available to operation to undo\n");
                        break;

                    case 1:
                        SupplierController.updateProductToAmountRole(undoDiscount.getSupplierId(),undoDiscount.getShipmentTime(), undoDiscount.getAmount(), undoUpdate);
                        feedbackText.setText("undo update successfully\n");
                        operateToUndo=0;
                        break;
                    case 2:
                        SupplierController.addProductToAmountRole(undoDiscount.getSupplierId(),undoDiscount.getShipmentTime(), undoDiscount.getAmount(), undoDiscount.getPercentage());
                        feedbackText.setText("undo delete successfully\n");
                        operateToUndo=0;
                        break;
                    case 3:
                        SupplierController.removeProductToAmountRule(undoDiscount.getSupplierId(),itemId, undoDiscount.getAmount(), undoDiscount.getPercentage());
                        feedbackText.setText("undo add new discount rule successfully\n");
                        operateToUndo=0;
                        break;
                }
            }
    }
}
