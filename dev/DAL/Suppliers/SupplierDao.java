package DAL.Suppliers;

import BL.Suppliers.Contract;
import BL.Suppliers.PaymentTypes;
import BL.Suppliers.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDao implements IDAO<Supplier> {

    private List<Supplier> suppliers;
    private Connection conn;

    private Supplier findInIdentity(int supId) {
        for (Supplier s : this.suppliers) {
            if (s.getSupplierId() == supId) {
                return s;
            }
        }
        return null;
    }

    private static SupplierDao instance;

    private SupplierDao() {
        this.suppliers = new ArrayList<Supplier>();
    }

    public static synchronized SupplierDao getInstance()  {
        if (instance == null) {
            instance = new SupplierDao();
        }
        return instance;
    }

    @Override
    public Supplier find(int supplierId) {
        try {
            if (findInIdentity(supplierId) != null) {
                return findInIdentity(supplierId);
            } else {
                this.conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Suppliers WHERE supplierId = ?");
                stmt.setInt(1, supplierId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Supplier supplier = new Supplier(rs.getInt("supplierId"), rs.getString("address"), rs.getBoolean("mobility"), rs.getString("name"), rs.getInt("shipmentTime"));
                    supplier.setCurrentContract(new Contract(rs.getBoolean("constantDaysShipment"), PaymentTypes.valueOf(rs.getString("paymentTypeId"))));
                    supplier.getCurrentContract().setKtavKamot(KtavKamutDao.getInstance().find(supplierId));
                    supplier.getCurrentContract().setTotalAmountDiscount(TotalAmountDiscountDao.getInstance().find(supplierId));
                    supplier.getCurrentContract().setTotalPaymentDiscount(TotalPaymentDiscountDao.getInstance().find(supplierId));
                    this.suppliers.add(supplier);
                    Database.closeConnection(conn);
                    return supplier;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
        return null;
    }


    public void insert(Supplier s) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Suppliers VALUES (?,?,?,?,?,?,?)");
            stmt.setInt(1, s.getSupplierId());
            stmt.setString(2, s.getName());
            stmt.setString(3, s.getAddress());
            stmt.setInt(4, (s.getMobility() ? 1 : 0));
            stmt.setString(5, String.valueOf(s.getCurrentContract().getPaymentTerms()));
            stmt.setInt(6, s.getCurrentContract().getConstantDaysShipment() ? 1 : 0);
            stmt.setInt(7, s.getShipmentTime());
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    @Override
    public void update(Supplier s, String[] params) {
    }

    public boolean getConstantDaysShipment(int supplierId) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT ConstantDaysShipment FROM Suppliers WHERE supplierId =?");
            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();
            boolean constship = (rs.getInt("ConstantDaysShipment")) == 1;
            Database.closeConnection(conn);
            return constship;
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
        return false;
    }

    public List<Supplier> getAllSuppliers() {
        try {
            this.conn = Database.connect();
            List<Supplier> suppliers = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM suppliers");
            while (rs.next()) {
                suppliers.add(find(rs.getInt("supplierId")));
                this.suppliers.add(find(rs.getInt("supplierId")));
            }
            Database.closeConnection(conn);
            return suppliers;
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
        return null;
    }

    @Override

    public void delete(Supplier s) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Suppliers WHERE supplierId = ?");
            stmt.setInt(1, s.getSupplierId());
            stmt.executeUpdate();
            Database.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    public void deleteAllSuppliersRecords() {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Suppliers");
            stmt.executeUpdate();
            Database.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }
}
