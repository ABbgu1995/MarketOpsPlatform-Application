package GUI;


import BL.Inventory.Item;
import DAL.Inventory.ItemDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ShowItemGUI extends JFrame implements ActionListener {
    JButton button;
    JButton button1;
    JTextField text;
    JTextField text2;
    JTextField text3;
    JTextField text4;
    JTextField text5;
    JTextField text6;
    JTextField text7;
    JTextField text8;
    JTextField text9;
    JTextField text10;
    JTextField text11;
    JTextField text12;
    JTextField text13;
    JTextField text14;
    JTextArea text15;
    ItemDAO itemDAO;
    int branchNum;
    int itemID;
    GUIStorekeeper guiStorekeeper;
    ReportsGUI reportsGui;
    ShowItemGUI( Object guiManager, String typeGUI){
        if(typeGUI=="manager"){
            this.reportsGui = (ReportsGUI) guiManager;
            this.guiStorekeeper=null;
        }else{
            this.guiStorekeeper = (GUIStorekeeper) guiManager;
            this.reportsGui =null;
        }
        itemDAO = ItemDAO.getInstance();
        this.setTitle("Show Item");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        button = new JButton("Show details");
        button.addActionListener(this);
        button.setBounds(65, 165, 150, 50); // Set button's position and size
        button.setFont(new Font("David", Font.BOLD, 18));

        button1 = new JButton("Back to the main menu");
        button1.addActionListener(this);
        button1.setBounds(65, 400, 250, 50); // Set button's position and size
        button1.setFont(new Font("David", Font.BOLD, 18));

        JLabel label1 = new JLabel("Enter itemID:");
        label1.setBounds(30, 50, 150, 30); // Set label's position and size
        label1.setFont(new Font("David", Font.BOLD, 18));

        JLabel label2 = new JLabel("Enter branchNum:");
        label2.setBounds(30, 100, 150, 30); // Set label's position and size
        label2.setFont(new Font("David", Font.BOLD, 18));

        JLabel label3 = new JLabel("Catalog number:");
        label3.setBounds(350, 50, 150, 30); // Set label's position and size
        label3.setFont(new Font("David", Font.BOLD, 18));

        JLabel label4 = new JLabel("Maker:");
        label4.setBounds(350, 90, 150, 30); // Set label's position and size
        label4.setFont(new Font("David", Font.BOLD, 18));

        JLabel label5 = new JLabel("Supplier:");
        label5.setBounds(350, 130, 150, 30); // Set label's position and size
        label5.setFont(new Font("David", Font.BOLD, 18));

        JLabel label6 = new JLabel("Supplier price:");
        label6.setBounds(350, 170, 150, 30); // Set label's position and size
        label6.setFont(new Font("David", Font.BOLD, 18));

        JLabel label7 = new JLabel("Store price:");
        label7.setBounds(350, 210, 150, 30); // Set label's position and size
        label7.setFont(new Font("David", Font.BOLD, 18));

        JLabel label8 = new JLabel("Store price after discount:");
        label8.setBounds(350, 250, 210, 30); // Set label's position and size
        label8.setFont(new Font("David", Font.BOLD, 18));

        JLabel label9 = new JLabel("Category:");
        label9.setBounds(350, 290, 150, 30); // Set label's position and size
        label9.setFont(new Font("David", Font.BOLD, 18));

        JLabel label10 = new JLabel("Sub category:");
        label10.setBounds(350, 330, 150, 30); // Set label's position and size
        label10.setFont(new Font("David", Font.BOLD, 18));

        JLabel label11 = new JLabel("Sub sub category:");
        label11.setBounds(350, 370, 150, 30); // Set label's position and size
        label11.setFont(new Font("David", Font.BOLD, 18));

        JLabel label12 = new JLabel("Expire date:");
        label12.setBounds(350, 410, 150, 30); // Set label's position and size
        label12.setFont(new Font("David", Font.BOLD, 18));

        JLabel label13 = new JLabel("Damage type:");
        label13.setBounds(350, 450, 150, 30); // Set label's position and size
        label13.setFont(new Font("David", Font.BOLD, 18));

        JLabel label14 = new JLabel("Location:");
        label14.setBounds(350, 490, 150, 30); // Set label's position and size
        label14.setFont(new Font("David", Font.BOLD, 18));


        // Set the size of the window

        text = new JTextField();
        text.setPreferredSize(new Dimension(250, 40));
        text.setBounds(200, 50, 100, 30);

        text2 = new JTextField();
        text2.setPreferredSize(new Dimension(250, 40));
        text2.setBounds(200, 100, 100, 30);

        text3 = new JTextField();
        text3.setPreferredSize(new Dimension(250, 40));
        text3.setBounds(570, 50, 100, 30);

        text4 = new JTextField();
        text4.setPreferredSize(new Dimension(250, 40));
        text4.setBounds(570, 90, 100, 30);

        text5 = new JTextField();
        text5.setPreferredSize(new Dimension(250, 40));
        text5.setBounds(570, 130, 100, 30);

        text6 = new JTextField();
        text6.setPreferredSize(new Dimension(250, 40));
        text6.setBounds(570, 170, 100, 30);

        text7 = new JTextField();
        text7.setPreferredSize(new Dimension(250, 40));
        text7.setBounds(570, 210, 100, 30);

        text8 = new JTextField();
        text8.setPreferredSize(new Dimension(250, 40));
        text8.setBounds(570, 250, 100, 30);

        text9 = new JTextField();
        text9.setPreferredSize(new Dimension(250, 40));
        text9.setBounds(570, 290, 100, 30);

        text10 = new JTextField();
        text10.setPreferredSize(new Dimension(250, 40));
        text10.setBounds(570, 330, 100, 30);

        text11 = new JTextField();
        text11.setPreferredSize(new Dimension(250, 40));
        text11.setBounds(570, 370, 100, 30);

        text12 = new JTextField();
        text12.setPreferredSize(new Dimension(250, 40));
        text12.setBounds(570, 410, 100, 30);

        text13 = new JTextField();
        text13.setPreferredSize(new Dimension(250, 40));
        text13.setBounds(570, 450, 100, 30);

        text14 = new JTextField();
        text14.setPreferredSize(new Dimension(250, 40));
        text14.setBounds(570, 490, 100, 30);

        text15 = new JTextArea();
        text15.setPreferredSize(new Dimension(250, 40));
        text15.setBounds(40, 230, 270, 70);
        text15.setFont(new Font("David", Font.BOLD, 18));

        this.add(button);
        this.add(button1);
        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(label4);
        this.add(label5);
        this.add(label6);
        this.add(label7);
        this.add(label8);
        this.add(label9);
        this.add(label10);
        this.add(label11);
        this.add(label12);
        this.add(label13);
        this.add(label14);
        this.add(text);
        this.add(text2);
        this.add(text3);
        this.add(text4);
        this.add(text5);
        this.add(text6);
        this.add(text7);
        this.add(text8);
        this.add(text9);
        this.add(text10);
        this.add(text11);
        this.add(text12);
        this.add(text13);
        this.add(text14);
        this.add(text15);
        text3.setEditable(false);
        text4.setEditable(false);
        text5.setEditable(false);
        text6.setEditable(false);
        text7.setEditable(false);
        text8.setEditable(false);
        text9.setEditable(false);
        text10.setEditable(false);
        text11.setEditable(false);
        text12.setEditable(false);
        text13.setEditable(false);
        text14.setEditable(false);
        text15.setEditable(false);

        this.setVisible(true);
    }
//    public static void main(String[] args) throws SQLException, InterruptedException {
////        // Create an instance of your GUI class
//////        Database.clearDatabase(Database.connect());
//////        SupplierController.clearAllTables();
//////        setupInformation();
//////        upDateDataBase();
////
//        ShowItemGUI Show_ItemGUI = new ShowItemGUI(new GUIManager());
//    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            branchNum = Integer.parseInt(text2.getText());
            itemID = Integer.parseInt(text.getText());
            Item item = itemDAO.getItemByID(itemID, branchNum);
            if(item != null) {
                text3.setText(item.getCatalogNumber());
                text4.setText(item.getMaker());
                text5.setText(item.getSupplier());
                text6.setText(String.valueOf(item.getSupplierPrice()));
                text7.setText(String.valueOf(item.getItemStorePrice()));
                text8.setText(String.valueOf(item.getItemStorePriceWithDiscount()));
                text9.setText(item.getCategory());
                text10.setText(item.getSubCategory());
                text11.setText(item.getSubsubCategory());
                if (item.getExpiredDate() != null) {
                    text12.setText(item.getExpiredDate().toString());
                } else {
                    text12.setText("null");
                }
                text13.setText(String.valueOf(item.getDamageTypeItem()));
                text14.setText(item.getItemPlace().toString());
                text15.setText("");
            }else{
                text15.setText("there is no item number " +itemID+ "\nin branch number "+branchNum);


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
