package DAL.Suppliers;

import BL.Suppliers.Product;
import BL.Suppliers.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements  IDAO<Product> {
    private List<Product> products;
    private Connection conn;

    private static ProductDao instance;

    private ProductDao() {
        this.products = new ArrayList<Product>();
    }

    public static synchronized ProductDao getInstance() {
        if (instance == null) {
            instance = new ProductDao();
        }
        return instance;
    }

    private Product findInIdentity(int supId, int supCatId){
        if(this.products.size()==0)
            return null;
        for (Product p : this.products){
            if(p.getSupplierId()==supId && p.getSuppCatalogId()==supCatId){
                return p;
            }
        }
        return null;
    }

    @Override
    public Product find(int key) throws SQLException {
        return null;
    }

    public List<Product> getAllSupplierProduct(int supplierId){
        try {
            this.conn = Database.connect();
            List<Product> productsList = new ArrayList<>();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Products WHERE supplierId = ?");
            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productsList.add(find(rs.getInt("supplierCategoryId"),rs.getInt("supplierId")));
            }
            Database.closeConnection(conn);
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
        return null;
    }


    public Product find(int supplierCategoryId, int supplierId) {
        try {
            if (findInIdentity(supplierId, supplierCategoryId) != null) {
                return findInIdentity(supplierId, supplierCategoryId);
            }
            else {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Products WHERE supplierCategoryId = ? AND supplierId = ? ");
            stmt.setInt(1, supplierCategoryId);
            stmt.setInt(2, supplierId);
            ResultSet rs = stmt.executeQuery();
            //if (rs.isBeforeFirst())
            if (rs.next()) {
                Product product = new Product(supplierId, rs.getInt("supplyAmount"), rs.getDouble("basePrice"), rs.getDouble("weight"), rs.getString("name"));
                product.setSuppCatalogId(rs.getInt("supplierCategoryId"));
                this.products.add(product);
                conn.close();
                return product;
            }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
        return null;
    }


    public double findPrice(int supplierId, int supplierCategoryId)  {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT basePrice FROM Products WHERE supplierId = ? AND supplierCategoryId= ?");
            stmt.setInt(1, supplierId);
            stmt.setInt(2, supplierCategoryId);
            ResultSet rs = stmt.executeQuery();
            double basePrice = rs.getDouble("basePrice");
            conn.close();
            return basePrice;
        }
        catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
            return 0;
        }
    }

    public String findName(int supplierId, int supplierCategoryId) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT name FROM Products WHERE supplierId = ? AND supplierCategoryId= ?");
            stmt.setInt(1, supplierId);
            stmt.setInt(2, supplierCategoryId);
            ResultSet rs = stmt.executeQuery();
            String name = rs.getString("name");
            conn.close();
            return name;
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
            return null;
        }
    }

    @Override
    public void insert(Product product) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Products VALUES (?,?,?,?,?,?)");
            stmt.setInt(1, product.getSuppCatalogId());
            stmt.setInt(2, product.getSupplierId());
            stmt.setDouble(3, product.getWeight());
            stmt.setDouble(4, product.getBasePrice());
            stmt.setInt(5, product.getQuantity());
            stmt.setString(6, product.getName());
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    @Override
    public void update(Product p, String[] params) {

    }

    @Override
    public void delete(Product p) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM products where supplierId = ? AND supplierCategoryId = ?");
            stmt.setInt(1, p.getSupplierId());
            stmt.setInt(2, p.getSuppCatalogId());
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    public void deleteAllProductRecords() {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM products");
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }
}
