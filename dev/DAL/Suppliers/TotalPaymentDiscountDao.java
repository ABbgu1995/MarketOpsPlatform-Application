package DAL.Suppliers;

import BL.Suppliers.TotalAmountDiscount;
import BL.Suppliers.TotalPaymentDiscount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalPaymentDiscountDao implements IDAO<TotalPaymentDiscount>{

    private  HashMap<Integer,TotalPaymentDiscount> totalPaymentDiscountCache;
    private Connection conn;

    private static TotalPaymentDiscountDao instance;

    private TotalPaymentDiscountDao()  {
        this.totalPaymentDiscountCache = new HashMap<Integer,TotalPaymentDiscount>();
    }


    public static synchronized TotalPaymentDiscountDao getInstance()  {
        if (instance==null){
            instance=new TotalPaymentDiscountDao();
        }
        return instance;
    }

    private TotalPaymentDiscount findInIdentity(int supId) {
        for (Map.Entry<Integer, TotalPaymentDiscount> entry : totalPaymentDiscountCache.entrySet()) {
            if (entry.getKey() == supId)
                return entry.getValue();
        }
        return null;
    }

    @Override
    public TotalPaymentDiscount find(int supplierId) {
        try{
//        if (findInIdentity(supplierId) != null) {
//            return findInIdentity(supplierId);
//        }
//        else {
            this.conn=Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM TotalPaymentDiscount WHERE supplierId = ?");
            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();
            TotalPaymentDiscount totalPaymentDiscount = new TotalPaymentDiscount();
            while (rs.next()) {
                totalPaymentDiscount.updatePolicy(rs.getDouble("TotalPayment"), rs.getDouble("discount"));
            }
            this.totalPaymentDiscountCache.put(supplierId,totalPaymentDiscount);
            Database.closeConnection(conn);
            return totalPaymentDiscount;
        //}

        } catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
            return null;
        }
    }


    public void insertRole(int supplierId, double totalPayment, double discount)  {
        try{
            this.conn=Database.connect();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO TotalPaymentDiscount VALUES (?,?,?)");
        stmt.setInt(1, supplierId);
        stmt.setDouble(2, totalPayment);
        stmt.setDouble(3, discount);
        stmt.executeUpdate();
        Database.closeConnection(conn);
        } catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    public void deleteRule(int supplierId, double totalPayment, double discount){
        try{
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM TotalPaymentDiscount WHERE supplierId = ? and TotalPayment = ? and discount = ?");
            stmt.setInt(1, supplierId);
            stmt.setDouble(2, totalPayment);
            stmt.setDouble(3, discount);
            stmt.executeUpdate();
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }

    public void updateRule(int supplierId, double totalPayment, double discount){
        try{
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("UPDATE TotalPaymentDiscount SET discount = ? WHERE supplierId = ? and TotalPayment = ?");
            stmt.setInt(2, supplierId);
            stmt.setDouble(3, totalPayment);
            stmt.setDouble(1, discount);
            stmt.executeUpdate();
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }


    @Override
    public void insert(TotalPaymentDiscount totalPaymentDiscount) throws SQLException {

    }

    @Override
    public void update(TotalPaymentDiscount totalPaymentDiscount, String[] params) {

    }

    @Override
    public void delete(TotalPaymentDiscount totalPaymentDiscount) {

    }


    public void deleteAllRecords()  {
        try {
            this.conn=Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM TotalPaymentDiscount");
            stmt.executeUpdate();
            Database.closeConnection(conn);
        } catch (SQLException e){
        e.printStackTrace();
        Database.closeConnection(conn);
    }

    }
}
