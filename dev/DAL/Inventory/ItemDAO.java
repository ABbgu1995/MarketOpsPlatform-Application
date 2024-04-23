package DAL.Inventory;

import BL.Inventory.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDAO {
    private Connection conn;
    public Map<Integer, Item> allItems;
    private static ItemDAO instance;
    public ItemDAO(){

        allItems = new HashMap<>();
    }
    public static ItemDAO getInstance(){
        if (instance == null) {
            instance = new ItemDAO();
        }
        return instance;
    }


    public void insert(Item item, int branchNum) {
        try {
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Item (itemID, catalogNumber, maker, supplier," +
                    " supplierPrice, storePrice, storePriceAfterDiscount, expiredDate, category, subCategory," +
                    " subsubCategory, damage, DamageTypeItem, itemPlace, branchNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
            stmt.setInt(1, item.getItemID());
            stmt.setString(2, item.getCatalogNumber());
            stmt.setString(3, item.getMaker());
            stmt.setString(4, item.getSupplier());
            stmt.setDouble(5, item.getSupplierPrice());
            stmt.setDouble(6, item.getItemStorePrice());
            stmt.setDouble(7, item.getItemStorePriceWithDiscount());
            if(item.getExpiredDate() != null){
                stmt.setDate(8, new java.sql.Date(item.getExpiredDate().getTime()));
            }else{
                stmt.setDate(8, null);
            }
            stmt.setString(9, item.getCategory());
            stmt.setString(10, item.getSubCategory());
            stmt.setString(11, item.getSubsubCategory());
            stmt.setString(12, item.getDamage().name());
            stmt.setString(13, item.getDamageTypeItem());
            stmt.setString(14, item.getItemPlace().name());
            stmt.setInt(15, branchNum);
            stmt.executeUpdate();
            allItems.put(item.getItemID(), item);
            Database.closeConnection(conn);

        }
        catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);

        }
    }


    public void update(Item item){
        try {
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("UPDATE Item SET catalogNumber = ?, maker = ?, supplier = ?," +
                    " supplierPrice = ?, storePrice = ?, storePriceAfterDiscount = ?, expiredDate = ?, category = ?, subCategory = ?," +
                    " subsubCategory = ?, damage = ?, DamageTypeItem = ?, itemPlace = ? WHERE itemId = ?");
            stmt.setString(1, item.getCatalogNumber());
            stmt.setString(2, item.getMaker());
            stmt.setString(3, item.getSupplier());
            stmt.setDouble(4, item.getSupplierPrice());
            stmt.setDouble(5, item.getItemStorePrice());
            stmt.setDouble(6, item.getItemStorePriceWithDiscount());
            if(item.getExpiredDate() != null){
                stmt.setDate(7, new java.sql.Date(item.getExpiredDate().getTime()));
            }else{
                stmt.setDate(7, null);
            }
            stmt.setString(8, item.getCategory());
            stmt.setString(9, item.getSubCategory());
            stmt.setString(10, item.getSubsubCategory());
            stmt.setString(11, item.getDamage().name());
            stmt.setString(12, item.getDamageTypeItem());
            stmt.setString(13, item.getItemPlace().name());
            stmt.setInt(14, item.getItemID());
            stmt.executeUpdate();
            allItems.replace(item.getItemID(), item);
            Database.closeConnection(conn);

        }
        catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }

    }
    public void delete(Item item){
        try {
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Item WHERE itemID = ?");
            stmt.setInt(1, item.getItemID());
            stmt.executeUpdate();
            allItems.remove(item.getItemID());
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }


    public Map<Integer, Item> getAllItems(int branchNum) {
        Map<Integer, Item> itemsInBranch = new HashMap<>();
        try {
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Item WHERE branchNumber = ?");
            stmt.setInt(1, branchNum);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                int id = rs.getInt("itemID");
                if(allItems.get(id) == null) {
                    Item item = new Item(id);
                    item.setCatalogNumber(rs.getString("catalogNumber"));
                    item.setMaker(rs.getString("maker"));
                    item.setSupplier(rs.getString("supplier"));
                    item.setSupplierPrice(rs.getDouble("supplierPrice"));
                    item.setStorePrice(rs.getDouble("storePrice"));
                    item.setItemStorePriceWithDiscount(rs.getDouble("storePriceAfterDiscount"));
                    item.setExpiredDate(rs.getDate("expiredDate"));
                    item.setCategory(rs.getString("category"));
                    item.setSubCategory(rs.getString("subCategory"));
                    item.setSubSubCategory(rs.getString("subsubCategory"));
                    item.setDamage(damageType.valueOf(rs.getString("damage")));
                    item.setDamageTypeItem(rs.getString("DamageTypeItem"));
                    item.setItemPlace(place.valueOf(rs.getString("itemPlace")));
                    allItems.put(id, item);
                    itemsInBranch.put(id, item);
                }else {
                    Item item = allItems.get(id);
                    itemsInBranch.put(id, item);
                }
            }
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
//        if(itemsInBranch.isEmpty()){
//            return null;
//        }else {
            return itemsInBranch;
//        }
    }


    public List<Item> getAllItemsByBranch(int branchNum){
        List<Item> allItemsByBranch = new ArrayList<>();
        try {
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Item WHERE branchNumber = ?");
            stmt.setInt(1, branchNum);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("itemID");
                Item item = new Item(id);
                item.setCatalogNumber(rs.getString("catalogNumber"));
                item.setMaker(rs.getString("maker"));
                item.setSupplier(rs.getString("supplier"));
                item.setSupplierPrice(rs.getDouble("supplierPrice"));
                item.setStorePrice(rs.getDouble("storePrice"));
                item.setItemStorePriceWithDiscount(rs.getDouble("storePriceAfterDiscount"));
                item.setExpiredDate(rs.getDate("expiredDate"));
                item.setCategory(rs.getString("category"));
                item.setSubCategory(rs.getString("subCategory"));
                item.setSubSubCategory(rs.getString("subsubCategory"));
                item.setDamage(damageType.valueOf(rs.getString("damage")));
                item.setDamageTypeItem(rs.getString("DamageTypeItem"));
                item.setItemPlace(place.valueOf(rs.getString("itemPlace")));
                allItemsByBranch.add(item);
            }
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
        return allItemsByBranch;
    }

    public Item getItemByID(int id, int branchNum) {
        try {
            if(allItems.get(id) == null){
                conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Item WHERE itemID = ? AND branchNumber = ?");
                stmt.setInt(1, id);
                stmt.setInt(2, branchNum);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    Item item = new Item(id);
                    item.setCatalogNumber(rs.getString("catalogNumber"));
                    item.setMaker(rs.getString("maker"));
                    item.setSupplier(rs.getString("supplier"));
                    item.setSupplierPrice(rs.getDouble("supplierPrice"));
                    item.setStorePrice(rs.getDouble("storePrice"));
                    item.setItemStorePriceWithDiscount(rs.getDouble("storePriceAfterDiscount"));
                    item.setExpiredDate(rs.getDate("expiredDate"));
                    item.setCategory(rs.getString("category"));
                    item.setSubCategory(rs.getString("subCategory"));
                    item.setSubSubCategory(rs.getString("subsubCategory"));
                    item.setDamage(damageType.valueOf(rs.getString("damage")));
                    item.setDamageTypeItem(rs.getString("DamageTypeItem"));
                    item.setItemPlace(place.valueOf(rs.getString("itemPlace")));
                    allItems.put(id, item);
                    Database.closeConnection(conn);
                    return allItems.get(id);
                }return null;
            }else{
                conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Item WHERE itemID = ? AND branchNumber = ?");
                stmt.setInt(1, id);
                stmt.setInt(2, branchNum);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Database.closeConnection(conn);
                    return allItems.get(id);
                }else{
                    Database.closeConnection(conn);
                    return null;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
        return null;
    }
}
