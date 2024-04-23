package DAL.Suppliers;

import BL.Suppliers.OrderLine;
import BL.Suppliers.SupplierOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDao implements IDAO<SupplierOrder>{
    private List<SupplierOrder> orders;
    private Connection conn;

    private static OrderDao instance;

    public OrderDao()  {
        this.orders= new ArrayList<SupplierOrder>();
    }

    public static synchronized OrderDao getInstance() throws SQLException {
        if (instance==null){
            instance=new OrderDao();
        }
        return instance;
    }

    @Override
    public SupplierOrder find(int key) {
        return null;
    }
    public HashMap<Integer, Integer> getAllRecordsByCategoryId(){
        HashMap<Integer, Integer> supplierCategoryIds = new HashMap<Integer, Integer>();
        try{
            this.conn=Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM OrderedProducts");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                supplierCategoryIds.put(rs.getInt("supplierCategoryId"), rs.getInt("amount"));
            }
            this.conn.close();
            return supplierCategoryIds;
    } catch (SQLException e) {
        e.printStackTrace();
        Database.closeConnection(this.conn);
    }
        return null;
    }
    @Override
    public void insert(SupplierOrder SO) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO OrdersHistory VALUES (?,?,?)");
            stmt.setInt(1, SO.getOrderId());
            stmt.setString(2, String.valueOf(java.time.LocalDate.now()));
            stmt.setDouble(3, SO.getTotalOrderPayment());
            stmt.executeUpdate();

            for (OrderLine line : SO.getLines()) {
                PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO OrderedProducts VALUES (?,?,?,?,?,?)");
                stmt2.setInt(1, SO.getOrderId());
                stmt2.setInt(2, line.getSupplierCategoryId());
                stmt2.setInt(3, line.getAmount());
                stmt2.setDouble(4, line.getBasePrice());
                stmt2.setDouble(5, line.getDiscount());
                stmt2.setDouble(6, line.getFinalPrice());
                stmt2.executeUpdate();
            }
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }


    @Override
    public void update(SupplierOrder SO, String[] params) {

    }

    @Override
    public void delete(SupplierOrder SO) {

    }

    public void deleteAllOrderHistoryRecords() {
        try{
            this.conn = Database.connect();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM OrdersHistory");
        PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM OrderedProducts");
        stmt.executeUpdate();
        stmt2.executeUpdate();
        this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }
}
