package DAL.Suppliers;

import BL.Suppliers.Days;
import BL.Suppliers.OrderLine;
import BL.Suppliers.PeriodicOrder;
import BL.Suppliers.SupplierController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeriodicOrderDao implements IDAO<PeriodicOrder>{
    private List<PeriodicOrder> periodicOrders;
    private Connection conn;


    private PeriodicOrder findInIdentity(int supplierId, Days shipmentDay){
        for (PeriodicOrder p : this.periodicOrders ) {
            if (p.getSupplier().getSupplierId() == supplierId && p.getShipmentDay() == shipmentDay) {
                return p;
            }
        }
        return null;
    }

    //singleton
    private static PeriodicOrderDao instance;


    private PeriodicOrderDao() {
            this.periodicOrders = new ArrayList<PeriodicOrder>();
    }




    public static synchronized PeriodicOrderDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new PeriodicOrderDao();
        }
        return instance;
    }

    public int getAmountFromPeriodicOrder(int supplierId, int supplierCategoryId, Days shipmentDay){
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT amount FROM periodicOrders WHERE supplierId = ? AND supplierCategoryId = ? AND shipmentDay = ?");
            stmt.setInt(1, supplierId);
            stmt.setInt(2, supplierCategoryId);
            stmt.setString(3, String.valueOf(shipmentDay));
            ResultSet rs = stmt.executeQuery();
            int amount = rs.getInt("amount");
            this.conn.close();
            return amount;
        }
        catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
        return -1;
    }

    public PeriodicOrder find(int supplierId, Days shipmentDay) {
        try {
            if (findInIdentity(supplierId, shipmentDay) != null) {
                return findInIdentity(supplierId, shipmentDay);
            } else {
                this.conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM periodicOrders WHERE supplierId = ? AND shipmentDay=? ");
                stmt.setInt(1, supplierId);
                stmt.setString(2, String.valueOf(shipmentDay));
                ResultSet rs = stmt.executeQuery();
                if (rs.getInt("supplierId")==supplierId) {
                    PeriodicOrder p = new PeriodicOrder(SupplierController.getInstance().findSupplier(supplierId), shipmentDay);
                    while(rs.next()){
                        OrderLine line = new OrderLine(rs.getInt("supplierCategoryId"),rs.getString("name"),
                                rs.getInt("amount"),rs.getDouble("basePrice"),rs.getDouble("discount"));
                        p.addLine(line);
                    }
                    return p;
                }
                this.conn.close();
            }
        }
        catch (SQLException e) {
                e.printStackTrace();
                Database.closeConnection(this.conn);
            }
        return null;
        }
        public List<PeriodicOrder> getPeriodicOrderBasedDay(Days shipmentDay){
        try{
            List<Integer> supplierIds = new ArrayList<Integer>();
            List<PeriodicOrder> periodicOrders = new ArrayList<PeriodicOrder>();
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT distinct supplierId FROM periodicOrders WHERE shipmentDay=? ");
            stmt.setString(1, String.valueOf(shipmentDay));
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                supplierIds.add(rs.getInt("supplierId"));
            }
            for (Integer supplierId: supplierIds){
                periodicOrders.add(find(supplierId,shipmentDay));
            }
            this.conn.close();
            return periodicOrders;
        }
        catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
            return null;
        }


    @Override
    public PeriodicOrder find(int key) {
        return null;
    }

    @Override
    public void insert(PeriodicOrder periodicOrder)  {
        try {
            this.conn = Database.connect();
            for (OrderLine line : periodicOrder.getLines()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO periodicOrders VALUES (?,?,?,?,?,?,?,?)");
                stmt.setInt(1, periodicOrder.getSupplier().getSupplierId());
                stmt.setString(2, String.valueOf(periodicOrder.getShipmentDay()));
                stmt.setInt(3, line.getSupplierCategoryId());
                stmt.setInt(4, line.getAmount());
                stmt.setDouble(5, line.getBasePrice());
                stmt.setDouble(6, line.getDiscount());
                stmt.setString(7, line.getProductName());
                stmt.setDouble(8, line.getFinalPrice());
                stmt.executeUpdate();
            }
            this.conn.close();
        }
        catch (SQLException e) {
                e.printStackTrace();
                Database.closeConnection(this.conn);
            }
        }


    public void addNewProductToPeriodicOrder(OrderLine line, int supplierId, Days shipmentDay) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO periodicOrders VALUES (?,?,?,?,?,?,?,?)");
            stmt.setInt(1, supplierId);
            stmt.setString(2, String.valueOf(shipmentDay));
            stmt.setInt(3, line.getSupplierCategoryId());
            stmt.setInt(4, line.getAmount());
            stmt.setDouble(5, line.getBasePrice());
            stmt.setDouble(6, line.getDiscount());
            stmt.setString(7, line.getProductName());
            stmt.setDouble(8, line.getFinalPrice());
            stmt.executeUpdate();
            this.conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }

    public void deleteProductFromPeriodicOrder(int supplierId, int supplierCategoryId, Days shipmentDay){
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM periodicOrders WHERE supplierId = ? AND supplierCategoryId = ? AND shipmentDay = ?");
            stmt.setInt(1, supplierId);
            stmt.setInt(2, supplierCategoryId);
            stmt.setString(3, String.valueOf(shipmentDay));
            stmt.executeUpdate();
            this.conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }

    public void updatePeriodicProductAmount(int supplierId, int supplierCategoryId, Days shipmentDay, int amount) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM periodicOrders WHERE supplierId=? AND supplierCategoryId=? AND shipmentDay =?");
            stmt1.setInt(1, supplierId);
            stmt1.setInt(2, supplierCategoryId);
            stmt1.setString(3,String.valueOf(shipmentDay));
            ResultSet rs = stmt1.executeQuery();
            OrderLine updateLine = new OrderLine(supplierCategoryId,rs.getString("name"),amount,rs.getDouble("basePrice"),KtavKamutDao.getInstance().getDiscount(supplierId,supplierCategoryId,amount));
            PreparedStatement stmt = conn.prepareStatement("UPDATE periodicOrders SET amount = ?, discount= ?, finalPrice=? WHERE supplierId=? AND supplierCategoryId=? AND shipmentDay =? ");
            stmt.setInt(1, amount);
            stmt.setDouble(2, updateLine.getDiscount());
            stmt.setDouble(3, updateLine.getFinalPrice());
            stmt.setInt(4, supplierId);
            stmt.setInt(5, supplierCategoryId);
            stmt.setString(6,String.valueOf(shipmentDay));
            stmt.executeUpdate();
            this.conn.close();

        } catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }

    public void deletePeriodicProduct(int supplierId, Days shipmentDay) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM periodicOrders WHERE supplierId=? AND shipmentDay=?");
            stmt1.setInt(1, supplierId);
            stmt1.setString(2, String.valueOf(shipmentDay));
            stmt1.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }

    @Override
    public void update(PeriodicOrder PeriodicOrder, String[] params) {

    }

    @Override
    public void delete(PeriodicOrder PeriodicOrder) {

    }

    public void deleteAllPeriodicRecords() {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM periodicOrders");
            stmt.executeUpdate();
            this.conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }
}
