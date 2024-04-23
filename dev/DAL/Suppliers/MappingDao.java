package DAL.Suppliers;

import BL.Suppliers.Product;
import BL.Suppliers.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MappingDao implements IDAO{
    private Connection conn;
    private static MappingDao instance;

    private List<mapPairForCache> pairsCache;

    public class mapPairForCache {
        int supplierId;
        String itemId;
        int supplierCategoryId;

        public mapPairForCache(int supplierId, String itemId, int supplierCategoryId) {
            this.supplierId = supplierId;
            this.itemId = itemId;
            this.supplierCategoryId = supplierCategoryId;
        }

        public void setSupplierId(int supplierId) {
            this.supplierId = supplierId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public void setSupplierCategoryId(int supplierCategoryId) {
            this.supplierCategoryId = supplierCategoryId;
        }

        public int getSupplierId() {
            return supplierId;
        }

        public String getItemId() {
            return itemId;
        }

        public int getSupplierCategoryId() {
            return supplierCategoryId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            mapPairForCache that = (mapPairForCache) o;
            return supplierId == that.supplierId && supplierCategoryId == that.supplierCategoryId && Objects.equals(itemId, that.itemId);
        }
        @Override
        public int hashCode() {
            return Objects.hash(supplierId, itemId, supplierCategoryId);
        }
    }


    private MappingDao() {
        this.pairsCache = new ArrayList<mapPairForCache>() ;
    }
    public static synchronized MappingDao getInstance() throws SQLException {
        if (instance==null){
            instance=new MappingDao();
        }
        return instance;
    }

    public int findSupCatID(String itemId,int supllierId){
        try {
            for (mapPairForCache pair : this.pairsCache) {
                if (pair.getSupplierId() == supllierId && pair.getItemId().equals(itemId))
                    return pair.getSupplierCategoryId();
            }
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT supplierCategoryId FROM ProductToSupliierCategoryId WHERE itemId = ? AND supplierId=?");
            stmt.setString(1, itemId);
            stmt.setInt(2, supllierId);
            ResultSet rs = stmt.executeQuery();
            int supCat = rs.getInt("supplierCategoryId");
            this.pairsCache.add(new mapPairForCache(supllierId,itemId,supCat));
            conn.close();
            return supCat;
        }
        catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
        return 0;
    }

    public String itemIDForSupplerCategoryId(int supplierId, int supplierCategoryId) {

        try {
            this.conn = Database.connect();
            for (mapPairForCache pair : this.pairsCache) {
                if (pair.getSupplierId() == supplierId && pair.getSupplierCategoryId()==supplierCategoryId)
                    return pair.getItemId();
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT itemID FROM ProductToSupliierCategoryId WHERE supplierId=? AND supplierCategoryId =? ");
            stmt.setInt(1, supplierId);
            stmt.setInt(2, supplierCategoryId);
            ResultSet rs = stmt.executeQuery();
            String itemId = rs.getString("itemId");
            this.pairsCache.add(new mapPairForCache(supplierId,itemId,supplierCategoryId));
            this.conn.close();
            return itemId;
        }
        catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
        return null;
    }
    public Object find(int key) throws SQLException {
        return null;
    }


    public void insert(int supplierId, int supplierCategoryId, String itemId) {
        try {
            this.conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO ProductToSupliierCategoryId VALUES (?,?,?)");
            stmt.setInt(1, supplierId);
            stmt.setInt(2, supplierCategoryId);
            stmt.setString(3, itemId);
            stmt.executeUpdate();
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }

    }
    public void insert(Object o) {
    }

    @Override
    public void update(Object o, String[] params) {

    }

    @Override
    public void delete(Object o) throws SQLException {

    }


    public void deleteAllMappingRecords()  {
        try{
        this.conn = Database.connect();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM ProductToSupliierCategoryId");
        stmt.executeUpdate();
        conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(this.conn);
        }
    }
}
