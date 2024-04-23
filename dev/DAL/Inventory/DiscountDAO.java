package DAL.Inventory;

import BL.Inventory.Discount;
import BL.Inventory.Item;
import BL.Inventory.status;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DiscountDAO{
    private Connection conn;
    private static DiscountDAO instance;
    public Map<Integer, Discount> allDiscount;

    public DiscountDAO(){
//        conn = Database.connect();
        allDiscount = new HashMap<>();
    }
    public static DiscountDAO getInstance(){
        if (instance == null) {
            instance = new DiscountDAO();
        }
        return instance;
    }

    public void insert(Discount dis){
        try {
            conn = Database.connect();

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Discount (discountID, discountPercent, dateFrom, dateTo, type) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, dis.getID());
            stmt.setDouble(2, dis.getDiscountPercent());
            stmt.setDate(3, new java.sql.Date(dis.getTheFirstDayOfDiscount().getTime()));
            stmt.setDate(4, new java.sql.Date(dis.getTheLimitDayOfDiscount().getTime()));
            stmt.setString(5, dis.getType());
            stmt.executeUpdate();
            allDiscount.put(dis.getID(), dis);
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    public void update(Discount dis){
        try {
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("UPDATE Discount SET discountPercent = ?, dateFrom = ?, dateTo = ? , type = ? WHERE discountID = ?");
            stmt.setDouble(1, dis.getDiscountPercent());
            stmt.setDate(2, new java.sql.Date(dis.getTheFirstDayOfDiscount().getTime()));
            stmt.setDate(3, new java.sql.Date(dis.getTheLimitDayOfDiscount().getTime()));
            stmt.setString(4, dis.getType());
            stmt.setInt(5, dis.getID());
            stmt.executeUpdate();
            allDiscount.replace(dis.getID(), dis);
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }

    }
    public void delete(Discount dis){
        try {
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Discount WHERE discountID = ?");
            stmt.setInt(1, dis.getID());
            stmt.executeUpdate();
            allDiscount.remove(dis.getID());
            Database.closeConnection(conn);
        }catch(SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

//    public Map<Integer, Discount> getAllDiscount() {
//        return allDiscount;
//    }

}
