package DAL.Suppliers;

import BL.Suppliers.KtavKamot;
import BL.Suppliers.Product;
import BL.Suppliers.ProductAmountDiscount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KtavKamutDao implements IDAO<KtavKamot> {
    private List<KtavKamot> ktavKamuts;
    private Connection conn;

    private static KtavKamutDao instance;

    ///singleton

    private KtavKamutDao() {
        this.ktavKamuts = new ArrayList<KtavKamot>();
    }

    private KtavKamot findInIdentity(int supId) {
        for (KtavKamot k : this.ktavKamuts) {
            if (k.getSupplierId() == supId) {
                return k;
            } else {
                return null;
            }
        }
        return null;
    }

    public void insertProductAmountRole(int supplierId, int supplierCategoryId, int amount, double discount) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO KtavKamut VALUES (?,?,?,?)");
            stmt.setInt(1, supplierId);
            stmt.setInt(2, supplierCategoryId);
            stmt.setInt(3, amount);
            stmt.setDouble(4, discount);
            stmt.executeUpdate();
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }

    }

    public void updateProductAmountRole(int supplierId, int supplierCategoryId, int amount, double discount) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("UPDATE KtavKamut SET discountPerAmount = ? WHERE supplierId = ? AND supplierCategoryId =? AND amount = ?");
            stmt.setInt(2, supplierId);
            stmt.setInt(3, supplierCategoryId);
            stmt.setInt(4, amount);
            stmt.setDouble(1, discount);
            stmt.executeUpdate();
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }

    }

    public double getDiscount(int supplierId, int supplierCategoryId, int amount) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT discountPerAmount FROM KtavKamut WHERE supplierId = ? AND supplierCategoryId=? AND amount <= ? order by amount DESC LIMIT 1");
            stmt.setInt(1, supplierId);
            stmt.setInt(2, supplierCategoryId);
            stmt.setInt(3, amount);
            ResultSet rs = stmt.executeQuery();
            double discount = rs.getDouble("discountPerAmount");
            this.conn.close();
            return discount;

        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
        return 0;
    }

    public ProductAmountDiscount getProductAmountDiscount(Product p) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM KtavKamut WHERE supplierId = ? AND supplierCategoryId= ?");
            stmt.setInt(1, p.getSupplierId());
            stmt.setInt(2, p.getSuppCatalogId());
            ResultSet rs = stmt.executeQuery();
            ProductAmountDiscount productAmountDiscount = new ProductAmountDiscount(p);
            if (rs == null) {
                this.conn.close();
                return null;
            }
            else {
                while (rs.next()) {
                    productAmountDiscount.updatePolicy(rs.getInt("amount"), rs.getDouble("discountPerAmount"));
                }
                this.conn.close();
                return productAmountDiscount;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
        return null;
    }


    public static synchronized KtavKamutDao getInstance() {
        if (instance == null) {
            instance = new KtavKamutDao();
        }
        return instance;
    }


    @Override
    public KtavKamot find(int supplierId) {
        try {
            if (findInIdentity(supplierId) != null) {
                return findInIdentity(supplierId);
            } else {
                this.conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM KtavKamut WHERE supplierId = ?");
                stmt.setInt(1, supplierId);
                ResultSet rs = stmt.executeQuery();
                KtavKamot ktavKamot = new KtavKamot(supplierId);
                while (rs.next()) {
                    Product p = ProductDao.getInstance().find(rs.getInt(2), supplierId);
                    ktavKamot.addProductAmountDiscount(p);
                    ktavKamot.updateProductAmountDiscount(p, rs.getInt("amount"), rs.getInt(4));
                }
                this.conn.close();
                return ktavKamot;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
        return null;
    }




    @Override
    public void insert(KtavKamot ktavKamot) {

    }

    @Override
    public void update(KtavKamot ktavKamot, String[] params) {

    }

    public void deleteRule(int supplierId, int supplierCategoryId, int amount, double discount){
        try{
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM KtavKamut WHERE supplierId = ? and supplierCategoryId = ? and amount = ? and discountPerAmount = ?");
            stmt.setInt(1, supplierId);
            stmt.setInt(2, supplierCategoryId);
            stmt.setInt(3, amount);
            stmt.setDouble(4, discount);
            stmt.executeUpdate();
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }

    @Override
    public void delete(KtavKamot ktavKamot) {

    }

    public void deleteAllKtavKamotRecords()  {
        try{
            this.conn = Database.connect();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM KtavKamut");
        stmt.executeUpdate();
        this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }
}
