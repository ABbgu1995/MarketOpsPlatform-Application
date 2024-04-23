package DAL.Suppliers;

import BL.Suppliers.Product;
import BL.Suppliers.TotalAmountDiscount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalAmountDiscountDao implements IDAO<TotalAmountDiscount>{

    private HashMap<Integer,TotalAmountDiscount> totalAmountDiscountCache;
    private Connection conn;

    private static TotalAmountDiscountDao instance;

    private TotalAmountDiscount findInIdentity(int supId) {
        for (Map.Entry<Integer, TotalAmountDiscount> entry : totalAmountDiscountCache.entrySet()) {
            if (entry.getKey() == supId)
                return entry.getValue();
        }
        return null;
    }

    private TotalAmountDiscountDao()  {
        this.totalAmountDiscountCache = new HashMap<Integer,TotalAmountDiscount>();
    }


    public static synchronized TotalAmountDiscountDao getInstance()  {
        if (instance==null){
            instance=new TotalAmountDiscountDao();
        }
        return instance;
    }


    @Override
    public TotalAmountDiscount find(int supplierId) {
        try {
//            if (findInIdentity(supplierId) != null) {
//                return findInIdentity(supplierId);
//            } else {
                this.conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM TotalAmountDiscount WHERE supplierId = ?");
                stmt.setInt(1, supplierId);
                ResultSet rs = stmt.executeQuery();
                TotalAmountDiscount totalAmountDiscount = new TotalAmountDiscount();
                while (rs.next()) {
                    totalAmountDiscount.updatePolicy(rs.getDouble("TotalAmount"), rs.getDouble("discount"));
                }
                this.totalAmountDiscountCache.put(supplierId, totalAmountDiscount);
                Database.closeConnection(conn);
                return totalAmountDiscount;
//            }
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
            return null;
        }
    }



    public void insertRole(int supplierId, int totalAmount, double discount)  {
        try {
            this.conn=Database.connect();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO TotalAmountDiscount VALUES (?,?,?)");
            stmt.setInt(1, supplierId);
            stmt.setInt(2, totalAmount);
            stmt.setDouble(3, discount);
            stmt.executeUpdate();
            Database.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    public void deleteRule(int supplierId, double totalAmount, double discount){
        try{
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM TotalAmountDiscount WHERE supplierId = ? and totalAmount = ? and discount = ?");
            stmt.setInt(1, supplierId);
            stmt.setDouble(2, totalAmount);
            stmt.setDouble(3, discount);
            stmt.executeUpdate();
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }

    public void updateRule(int supplierId, double totalAmount, double discount){
        try{
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("UPDATE TotalAmountDiscount SET discount = ? WHERE supplierId = ? and TotalAmount = ?");
            stmt.setInt(2, supplierId);
            stmt.setDouble(3, totalAmount);
            stmt.setDouble(1, discount);
            stmt.executeUpdate();
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }



    public void insert(TotalAmountDiscount totalAmountDiscount) throws SQLException {}

    @Override
    public void update(TotalAmountDiscount totalAmountDiscount, String[] params) {

    }

    @Override
    public void delete(TotalAmountDiscount totalAmountDiscount) throws SQLException {

    }


    public void deleteAllRecords() {
        try {
            this.conn=Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM TotalAmountDiscount");
            stmt.executeUpdate();
            Database.closeConnection(conn);
         } catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }
}
