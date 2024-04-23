package GUI.SuppliersGUI.DiscountsManagementGUI;

import BL.Suppliers.AmountToDiscount;
import BL.Suppliers.SupplierController;
import BL.Suppliers.TotalPaymentDiscount;
import GUI.SuppliersGUI.KtavKamotGUI.ManagmentKtavKamotGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;

import static UI.Suppliers.Menu.setupInformation;

public class TotalPaymentDiscountGUI extends JFrame implements ActionListener{

    JRadioButton editExitsRule;
    JRadioButton deleteExistRule;

    JRadioButton addNewRule;


    JButton executeButton;

    JButton undoButton;
    JButton backButton;

    JTextArea feedbackText;
    JTextField supplierIdText;
    JTextField totalPaymentText;
    JTextField discountText;

    int operateToUndo;

    int supplierId = -1;
    double totalPayment =-1;
    double discount = -1;

    double undoDiscount=-1;


    MainDiscountsManagementGUI mainDiscountsManagementGUI;

    SupplierController supplierController;


    public TotalPaymentDiscountGUI(MainDiscountsManagementGUI mainDiscountsManagementGUI){
    this.mainDiscountsManagementGUI = mainDiscountsManagementGUI;

        this.setTitle("edit total payment discounts");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        feedbackText = new JTextArea();
        feedbackText.setPreferredSize(new Dimension(250, 40));
        feedbackText.setBounds(400, 150, 370, 100);
        feedbackText.setFont(new Font("David", Font.BOLD, 18));

        JLabel labelId = new JLabel("Enter supplierId:");
        labelId.setBounds(30, 100, 200, 30); // Set label's position and size
        labelId.setFont(new Font("David", Font.BOLD, 18));

        supplierIdText = new JTextField();
        supplierIdText.setPreferredSize(new Dimension(250, 40));
        supplierIdText.setBounds(250, 100, 100, 30);

        JLabel labelPaymentValue = new JLabel("Enter total payment:");
        labelPaymentValue.setBounds(30, 150, 200, 30); // Set label's position and size
        labelPaymentValue.setFont(new Font("David", Font.BOLD, 18));

        totalPaymentText = new JTextField();
        totalPaymentText.setPreferredSize(new Dimension(250, 40));
        totalPaymentText.setBounds(250, 150, 100, 30);

        JLabel labelDiscount = new JLabel("Enter discount:");
        labelDiscount.setBounds(30, 200, 200, 30); // Set label's position and size
        labelDiscount.setFont(new Font("David", Font.BOLD, 18));

        discountText = new JTextField();
        discountText.setPreferredSize(new Dimension(250, 40));
        discountText.setBounds(250, 200, 100, 30);

        editExitsRule = new JRadioButton("edit rule");
        editExitsRule.setBounds(30,40,80,30);

        deleteExistRule = new JRadioButton("delete rule");
        deleteExistRule.setBounds(130,40,100,30);

        addNewRule = new JRadioButton("add new rule");
        addNewRule.setBounds(230,40,200,30);

        ButtonGroup bg=new ButtonGroup();
        bg.add(editExitsRule);
        bg.add(deleteExistRule);
        bg.add(addNewRule);

        executeButton = new JButton("perform operation");
        executeButton.addActionListener(this);
        executeButton.setBounds(30, 240, 200, 50); // Set button's position and size
        executeButton.setFont(new Font("David", Font.BOLD, 18));

        backButton = new JButton("<-");
        backButton.addActionListener(this);
        backButton.setBounds(25, 8, 55, 25); // Set button's position and size
        backButton.setFont(new Font("David", Font.BOLD, 18));

        undoButton = new JButton("Undo");
        undoButton.addActionListener(this);
        undoButton.setBounds(265, 235, 65, 25); // Set button's position and size
        undoButton.setFont(new Font("David", Font.BOLD, 12));

        this.add(editExitsRule);
        this.add(addNewRule);
        this.add(undoButton);
        this.add(backButton);
        this.add(labelId);
        this.add(labelPaymentValue);
        this.add(labelDiscount);
        this.add(deleteExistRule);
        this.add(executeButton);
        this.add(totalPaymentText);
        this.add(supplierIdText);
        this.add(discountText);
        this.add(discountText);
        this.add(feedbackText);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == executeButton) {
            supplierId = Integer.parseInt(supplierIdText.getText());
            totalPayment = Double.parseDouble(totalPaymentText.getText());
            discount = Double.parseDouble(discountText.getText());

            if(!editExitsRule.isSelected() && !deleteExistRule.isSelected() && !addNewRule.isSelected())
                feedbackText.setText("invalid - choose operation\n");
            else {

                    if (editExitsRule.isSelected()) {
                        undoDiscount = SupplierController.getSupplierTotalPaymentDiscount(supplierId).getPaymentPolicy(totalPayment);
                        SupplierController.editTotalPaymentRule(supplierId,totalPayment,discount);
                        feedbackText.setText("change discount percentage successfully\n");
                        operateToUndo=1;
                    }
                    if (deleteExistRule.isSelected()) {
                        undoDiscount = SupplierController.getSupplierTotalPaymentDiscount(supplierId).getPaymentPolicy(totalPayment);
                        SupplierController.deleteTotalPaymentRule(supplierId,totalPayment,discount);
                        feedbackText.setText("remove total payment discount successfully\n");
                        operateToUndo=2;
                    }
                    if(addNewRule.isSelected()){
                        undoDiscount = SupplierController.getSupplierTotalPaymentDiscount(supplierId).getPaymentPolicy(totalPayment);
                        SupplierController.addTotalPaymentRule(supplierId,totalPayment,discount);
                        feedbackText.setText("add new total payment discount successfully\n");
                        operateToUndo=3;

                }
            }
        }
        if (e.getSource() == backButton) {
            this.dispose();
            this.mainDiscountsManagementGUI.showMainDiscountsManagementGUI();

        }
        if(e.getSource()==undoButton){
            switch (operateToUndo){
                case 0:
                    feedbackText.setText("no available to operation to undo\n");
                    break;

                case 1:
                    SupplierController.editTotalPaymentRule(supplierId,totalPayment,undoDiscount);
                    feedbackText.setText("undo update successfully\n");
                    operateToUndo=0;
                    break;
                case 2:
                    SupplierController.addTotalPaymentRule(supplierId,totalPayment,undoDiscount);
                    feedbackText.setText("undo delete successfully\n");
                    operateToUndo=0;
                    break;
                case 3:
                    SupplierController.deleteTotalPaymentRule(supplierId,totalPayment,undoDiscount);
                    feedbackText.setText("undo add new discount rule successfully\n");
                    operateToUndo=0;
                    break;
            }
        }
    }
}