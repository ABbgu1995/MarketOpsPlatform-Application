package DAL.Inventory;


import BL.Inventory.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchDAO {

    private Connection conn;
    private static BranchDAO instance;
    public Map<Integer, Branch> allBranch;

    public BranchDAO()  {
        allBranch = new HashMap<>();
    }

    public static BranchDAO getInstance() {
        if (instance == null) {
            instance = new BranchDAO();
        }
        return instance;
    }

    public void insert(Branch branch) {
        try {
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Branch (branchNumber) VALUES (?)");
            stmt.setInt(1, branch.getBranchNumber());
            stmt.executeUpdate();
            allBranch.put(branch.getBranchNumber(), branch);
//            insertMinAmount(branch.getMinAmountOfAllItemByCatalog(), branch.getBranchNumber());
            insertMinAmount(branch);
            insertCategoryByCatalogNumber(branch.getMapByCategoryCatalogNumber(), branch.getBranchNumber());
            insertMissingProduct(branch.getMapMissingProduct(), branch.getBranchNumber());
            Database.closeConnection(conn);
        }catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    public void delete(Branch branch) {
        try {
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Branch WHERE branchNumber = ?");
            stmt.setInt(1, branch.getBranchNumber());
            stmt.executeUpdate();
            allBranch.remove(branch.getBranchNumber());
            deleteMinAmount(branch.getBranchNumber());
            deleteMissingProduct(branch.getBranchNumber());
            deleteCategoryByCatalogNumber(branch.getBranchNumber());
            Database.closeConnection(conn);
        }catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    public void insertMinAmount(Branch branch) {
        Map<String, Integer> minAmount = branch.getMinAmountOfAllItemByCatalog();
        int branchNum = branch.getBranchNumber();
        if (minAmount != null) {
            try{
                conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO minAmountInBranch (branchNumber, catalogNumber, amount) VALUES (?, ?, ?)");
                for (Map.Entry<String, Integer> entry : minAmount.entrySet()) {
                    stmt.setInt(1, branchNum);
                    stmt.setString(2, entry.getKey());
                    stmt.setInt(3, entry.getValue());
                    stmt.executeUpdate();
                }
                Database.closeConnection(conn);
            }catch (SQLException e){
                e.printStackTrace();
                Database.closeConnection(conn);
            }

        }
    }

    public void updateMinAmount(Branch branch){
        int branchNum = branch.getBranchNumber();
//        String catalogNumber = item.getCatalogNumber();
        Map<String, Integer> mapMinAmount = branch.getMinAmountOfAllItemByCatalog();
        try{
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM minAmountInBranch WHERE branchNumber = ? AND catalogNumber = ?");
            for (Map.Entry<String, Integer> entry : mapMinAmount.entrySet()) {
                stmt.setInt(1, branchNum);
                stmt.setString(2, entry.getKey());
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) {
                    // Row does not exist
                    PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO minAmountInBranch (branchNumber,catalogNumber, amount) VALUES (?, ?, ?)");
                    stmt1.setInt(1, branchNum);
                    stmt1.setString(2, entry.getKey());
                    stmt1.setInt(3, entry.getValue());
                    stmt1.executeUpdate();
                }
            }
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    public void deleteMinAmount(int branchNum) {
        try{
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM minAmountInBranch WHERE branchNumber = ? ");
            stmt.setInt(1, branchNum);
            stmt.executeUpdate();
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    public boolean checkIfCatalogExist(String catNum, int branchNum){
        try {
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM minAmountInBranch WHERE branchNumber = ? AND catalogNumber = ?");
            stmt.setInt(1, branchNum);
            stmt.setString(2, catNum);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                Database.closeConnection(conn);
                return false;
            }else {
                Database.closeConnection(conn);
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
        return false;
    }

    public void insertMissingProduct(Map<String, Integer> missingProduct, int branchNum) {
        try{
            conn = Database.connect();
            if (missingProduct != null) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO missingProduct (branchNumber, catalogNumber, amount) VALUES (?, ?, ?)");
                for (Map.Entry<String, Integer> entry : missingProduct.entrySet()) {
                    stmt.setInt(1, branchNum);
                    stmt.setString(2, entry.getKey());
                    stmt.setInt(3, entry.getValue());
                    stmt.executeUpdate();
                }
            }
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }


    public void deleteMissingProduct(int branchNum){
        try{
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM missingProduct WHERE branchNumber = ? ");
            stmt.setInt(1, branchNum);
            stmt.executeUpdate();
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }


    public void updateMissingProduct(Branch branch) {
        try{
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("UPDATE missingProduct SET amount = ? WHERE branchNumber = ? AND catalogNumber = ?");
            for (Map.Entry<String, Integer> entry : branch.getMapMissingProduct().entrySet()) {
                stmt.setInt(1, entry.getValue());
                stmt.setInt(2, branch.getBranchNumber());
                stmt.setString(3, entry.getKey());
                stmt.executeUpdate();
            }
            allBranch.replace(branch.getBranchNumber(), branch);
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }


    public void insertCategoryByCatalogNumber(Map<String, List<String>> mapCategoryCatalog, int branchNum) throws SQLException {
        if (mapCategoryCatalog != null) {
            try{
                conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO CategoryByCatalogNumber (branchNumber,category, catalogNumber) VALUES (?, ?, ?)");
                for (Map.Entry<String, List<String>> entry : mapCategoryCatalog.entrySet()) {
                    for (String catNum : entry.getValue()) {
                        stmt.setInt(1, branchNum);
                        stmt.setString(2, entry.getKey());
                        stmt.setString(3, catNum);
                        stmt.executeUpdate();
                    }
                }
                Database.closeConnection(conn);
            }catch (SQLException e){
                e.printStackTrace();
                Database.closeConnection(conn);
            }
        }
    }

    public void updateCategoryByCatalogNumber(Item item, int branchNum){
        String catalogNumber = item.getCatalogNumber();
        String category = item.getCategory();
        try{
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CategoryByCatalogNumber WHERE branchNumber = ? AND category = ? AND catalogNumber = ?");
            stmt.setInt(1, branchNum);
            stmt.setString(2, category);
            stmt.setString(3, catalogNumber);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                // Row does not exist
                PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO CategoryByCatalogNumber (branchNumber,category, catalogNumber) VALUES (?, ?, ?)");
                stmt1.setInt(1, branchNum);
                stmt1.setString(2, category);
                stmt1.setString(3, catalogNumber);
                stmt1.executeUpdate();
            }
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);

        }
    }

    public void deleteCategoryByCatalogNumber(int branchNum)  {
        try{
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM CategoryByCatalogNumber WHERE branchNumber = ? ");
            stmt.setInt(1, branchNum);
            stmt.executeUpdate();
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);

        }
    }

    public Map<Integer, Branch> getAllBranch()  {
        try{
            conn = Database.connect();
            Statement stmt11 = conn.createStatement();
            ResultSet rs11 = stmt11.executeQuery("SELECT * FROM Branch");
            while (rs11.next()) {
                int number = rs11.getInt("branchNumber");
                getBranch(number);
            }
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);

        }

        return allBranch;
    }

    public Branch getBranchByNumber(int number) {
        getBranch(number);
        if (allBranch.get(number) == null) {
            return null;
        } else {
            return allBranch.get(number);
        }
    }

    private void getBranch(int number) {
        if (allBranch.get(number) == null) {
            try{
                conn = Database.connect();
                Map<String, List<String>> mapCategoryCatalog = new HashMap<>();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CategoryByCatalogNumber WHERE branchNumber = ?");
                stmt.setInt(1, number);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String category = rs.getString("category");
                    String catalogNum = rs.getString("catalogNumber");

                    List<String> catalogs = mapCategoryCatalog.getOrDefault(category, new ArrayList<>());
                    catalogs.add(catalogNum);
                    mapCategoryCatalog.put(category, catalogs);
                }

                Map<String, Integer> mapMissingProduct = new HashMap<>();
                PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM missingProduct WHERE branchNumber = ?");
                stmt1.setInt(1, number);
                ResultSet rs1 = stmt1.executeQuery();
                while (rs1.next()) {
                    String catalogNumber = rs1.getString("catalogNumber");
                    int amount = rs1.getInt("amount");
                    mapMissingProduct.put(catalogNumber, amount);
                }

                Map<String, Integer> mapMinAmount = new HashMap<>();
                PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM minAmountInBranch WHERE branchNumber = ?");
                stmt1.setInt(1, number);
                ResultSet rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    String catalogNumber = rs2.getString("catalogNumber");
                    int amount = rs2.getInt("amount");
                    mapMinAmount.put(catalogNumber, amount);
                }
                ItemDAO itemDAO = ItemDAO.getInstance();
                List<Item> allItemsByBranch = itemDAO.getAllItemsByBranch(number);
                if(!(allItemsByBranch.isEmpty() && mapMissingProduct.isEmpty() && mapMinAmount.isEmpty() && mapCategoryCatalog.isEmpty())){
                    Branch branch = new Branch(number, allItemsByBranch, mapMinAmount, mapCategoryCatalog, mapMissingProduct);
                    allBranch.put(number, branch);
                }
                Database.closeConnection(conn);
            }catch (SQLException e){
                e.printStackTrace();
                Database.closeConnection(conn);
            }
        }
    }


}
