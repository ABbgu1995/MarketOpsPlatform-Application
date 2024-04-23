package GUI;

import BL.Inventory.BranchController;
import BL.Inventory.ReportController;
import BL.Suppliers.OrderController;
import BL.Suppliers.SupplierController;
import BL.Suppliers.SupplierOrder;
import DAL.Inventory.Database;
import Service.ServiceSupplier;
import org.junit.jupiter.api.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static UI.Inventory.UserInterface.upDateDataBase;
import static UI.Suppliers.Menu.setupInformation;

public class IssuingShortageOrderGUI extends JFrame implements ActionListener {

    JButton button;
    JButton button1;
    JComboBox comboBox1;
    int branchNum;
    JTextArea text2;
    JTextArea text3;
    ReportController reportController;
    OrderController orderController;
    GUIStorekeeper guiStorekeeper;
    IssuingShortageOrderGUI(GUIStorekeeper guiStorekeeper){
        this.guiStorekeeper = guiStorekeeper;
        this.reportController = ReportController.getInstance();
        this.orderController = OrderController.getInstance();
        this.setTitle("Issuing Shortage Order");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        String[] branches = {"1", "2"};

        JLabel label1 = new JLabel("Choose branch number:");
        label1.setBounds(30, 50, 200, 30); // Set label's position and size
        label1.setFont(new Font("David",Font.BOLD, 18));
        comboBox1 = new JComboBox(branches);
        comboBox1.addActionListener(this);
        comboBox1.setBounds(240, 50, 100, 30);

        button = new JButton("Issuing Shortage Order");
        button.addActionListener(this);
        button.setBounds(450, 40, 270, 50); // Set button's position and size
        button.setFont(new Font("David", Font.BOLD, 18));

        button1 = new JButton("Back to the main menu");
        button1.addActionListener(this);
        button1.setBounds(450, 460, 250, 50); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 18));

        text2 = new JTextArea();
        text2.setPreferredSize(new Dimension(250, 40));
        text2.setBounds(55, 440, 340, 85);
        text2.setFont(new Font("David", Font.BOLD, 18));

        text3 = new JTextArea();
        text3.setPreferredSize(new Dimension(250, 40));
        text3.setBounds(100, 150, 500, 250);
        text3.setFont(new Font("David", Font.BOLD, 18));


        this.add(comboBox1);
        this.add(button);
        this.add(button1);
        this.add(label1);
        this.add(text2);
        this.add(text3);
        this.setVisible(true);
    }

//    public static void main(String[] args) throws SQLException, InterruptedException {
//        // Create an instance of your GUI class
//        Database.clearDatabase(Database.connect());
//        SupplierController.clearAllTables();
//        setupInformation();
//        upDateDataBase();
//
//        IssuingShortageOrderGUI GUI = new IssuingShortageOrderGUI();
//    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            branchNum = Integer.parseInt(String.valueOf(comboBox1.getSelectedItem()));
            Map<String, Integer> map = reportController.createNewShortageReport(branchNum);
            if (map != null) {
                text3.setText("");
                text2.setText("");
                List<SupplierOrder>supplierOrders=ServiceSupplier.getInstance().executeShortageOrderService(map);
                for (SupplierOrder so:supplierOrders) {
                    text3.append(so.printOrdertoform());
                }

                //orderController.setProductMissingReport(map);
                //orderController.executeOrder();

                //todo: amit and barak- show the order on screen
            }else{
                text3.setText("");
                text2.setText("");
                text2.setText("There is no shortage in branch number: " + branchNum);
            }
        }if(e.getSource() == button1){
            this.dispose();
            guiStorekeeper.showStorekeeperGUI();
        }
    }
}
