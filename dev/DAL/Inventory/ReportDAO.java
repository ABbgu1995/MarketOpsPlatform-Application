package DAL.Inventory;


import BL.Inventory.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReportDAO {

    private Connection conn;
    private static ReportDAO instance;
    public Map<Integer, RInventory> allReportsInventory;
    public Map<Integer, RDamage> allReportsDamage;
    public Map<Integer, RShortage> allReportsShortage;
    public Map<Integer, Report> allReport;

    public ReportDAO() {
        allReport = new HashMap<>();
        allReportsInventory = new HashMap<>();
        allReportsDamage = new HashMap<>();
        allReportsShortage = new HashMap<>();
    }

    public static ReportDAO getInstance() {
        if (instance == null) {
            instance = new ReportDAO();
        }
        return instance;
    }

    public void insert(Report rep, int branchNum) {
        try {
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Reports (reportID, branchNumber, reportName) VALUES (?, ?, ?)");
            stmt.setInt(1, rep.getReportID());
            stmt.setInt(2, branchNum);
            stmt.setString(3, rep.getReportName());
            stmt.executeUpdate();
            allReport.put(rep.getReportID(), rep);
            insertRInventory(rep);
            insertRShortage(rep);
            insertRDamage(rep);
            Database.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    public void delete(Report rep) {
        try {
            conn = Database.connect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Reports WHERE reportID = ?");
            stmt.setInt(1, rep.getReportID());
            stmt.executeUpdate();
            allReport.remove(rep.getReportID());
            deleteRInventory(rep);
            deleteRShortage(rep);
            deleteRDamage(rep);
            Database.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }


    public void insertRInventory(Report rep) {
        if (Objects.equals(rep.getReportName(), "Inventory")) {
            try {
                conn = Database.connect();
                RInventory in = (RInventory) rep;
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO allInventory (reportID, catalogNumber, amount) VALUES (?, ?, ?)");
                for (Map.Entry<String, Integer> entry : in.getAllInventory().entrySet()) {
                    stmt.setInt(1, rep.getReportID());
                    stmt.setString(2, entry.getKey());
                    stmt.setInt(3, entry.getValue());
                    stmt.executeUpdate();
                }
                allReportsInventory.put(rep.getReportID(), in);
                Database.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
                Database.closeConnection(conn);
            }
        }
    }

    public void deleteRInventory(Report rep) {
        if (Objects.equals(rep.getReportName(), "Inventory")) {
            try {
                conn = Database.connect();
                RInventory in = (RInventory) rep;
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM allInventory WHERE reportID = ?");
                stmt.setInt(1, in.getReportID());
                stmt.executeUpdate();
                allReportsInventory.remove(rep.getReportID());
                Database.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
                Database.closeConnection(conn);
            }
        }
    }

    public void insertRShortage(Report rep) {
        if (Objects.equals(rep.getReportName(), "Shortage")) {
            try {
                conn = Database.connect();
                RShortage in = (RShortage) rep;
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO allShortage (reportID, catalogNumber, amount) VALUES (?, ?, ?)");
                for (Map.Entry<String, Integer> entry : in.getAllShortage().entrySet()) {
                    stmt.setInt(1, rep.getReportID());
                    stmt.setString(2, entry.getKey());
                    stmt.setInt(3, entry.getValue());
                    stmt.executeUpdate();
                }
                allReportsShortage.put(rep.getReportID(), in);
                Database.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
                Database.closeConnection(conn);
            }
        }
    }

    public void deleteRShortage(Report rep) {
        if (Objects.equals(rep.getReportName(), "Shortage")) {
            try {
                conn = Database.connect();
                RShortage in = (RShortage) rep;
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM allShortage WHERE reportID = ?");
                stmt.setInt(1, in.getReportID());
                stmt.executeUpdate();
                allReportsShortage.remove(rep.getReportID());
                Database.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
                Database.closeConnection(conn);
            }
        }
    }

    public void insertRDamage(Report rep) {
        if (Objects.equals(rep.getReportName(), "Damage")) {
            try {
                conn = Database.connect();
                RDamage in = (RDamage) rep;
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO allDamage (reportID, itemID, damageType) VALUES (?, ?, ?)");
                for (Map.Entry<Item, damageType> entry : in.getAllDamage().entrySet()) {
                    stmt.setInt(1, rep.getReportID());
                    stmt.setInt(2, entry.getKey().getItemID());
                    stmt.setString(3, entry.getValue().name());
                    stmt.executeUpdate();
                }
                allReportsDamage.put(rep.getReportID(), in);
                Database.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
                Database.closeConnection(conn);
            }
        }
    }

    public void deleteRDamage(Report rep) {
        try {
            conn = Database.connect();
            if (Objects.equals(rep.getReportName(), "Damage")) {
                RDamage in = (RDamage) rep;
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM allDamage WHERE reportID = ?");
                stmt.setInt(1, in.getReportID());
                stmt.executeUpdate();
                allReportsDamage.remove(rep.getReportID());
            }
            Database.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }

    public RShortage getRSortage(int reportID, int branchNum) {
        try {
            conn = Database.connect();
            if (allReportsShortage.get(reportID) == null) {
                Map<String, Integer> mapCatalogInt = new HashMap<>();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM allShortage WHERE reportID = ?");
                stmt.setInt(1, reportID);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {

                    String catalogNum = rs.getString("catalogNumber");
                    int amount = rs.getInt("amount");
                    mapCatalogInt.put(catalogNum, amount);
                }
                if (!mapCatalogInt.isEmpty()) {
                    RShortage curr_shortage = new RShortage(mapCatalogInt, "Shortage");
                    allReportsShortage.put(reportID, curr_shortage);
                }
                Database.closeConnection(conn);
                return allReportsShortage.get(reportID);
            }else {
                PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM Reports WHERE reportID = ? AND branchNumber = ?");
                stmt1.setInt(1, reportID);
                stmt1.setInt(2, branchNum);
                ResultSet rs = stmt1.executeQuery();
                if (rs.next()) {
                    Database.closeConnection(conn);
                    return allReportsShortage.get(reportID);
                }else{
                    Database.closeConnection(conn);
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }

        return null;
    }
    public RDamage getRDamage( int reportID, int branchNum){
        try {
            conn = Database.connect();
            if (allReportsDamage.get(reportID) == null) {

                Map<Item, damageType> map = new HashMap<>();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM allDamage WHERE reportID = ?");
                stmt.setInt(1, reportID);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {

                    int itemID = rs.getInt("itemID");
                    damageType damage_type = damageType.valueOf(rs.getString("damage"));
                    Item item = ItemDAO.getInstance().getItemByID(itemID, branchNum);
                    if (item != null) {
                        map.put(item, damage_type);
                    }
                }
                if (!map.isEmpty()) {
                    RDamage curr_damage = new RDamage(map, "Damage");
                    allReportsDamage.put(reportID, curr_damage);
                }
                Database.closeConnection(conn);
                return allReportsDamage.get(reportID);
            }else{
                PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM Reports WHERE reportID = ? AND branchNumber = ?");
                stmt1.setInt(1, reportID);
                stmt1.setInt(2, branchNum);
                ResultSet rs = stmt1.executeQuery();
                if (rs.next()) {
                    Database.closeConnection(conn);
                    return allReportsDamage.get(reportID);
                }else{
                    Database.closeConnection(conn);
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        } return null;

    }
    public RInventory getRInventory( int reportID, int branchNum){//problem branch num
        try {
            conn = Database.connect();
            if (allReportsInventory.get(reportID) == null) {
                Map<String, Integer> map = new HashMap<>();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM allInventory WHERE reportID = ?");
                stmt.setInt(1, reportID);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String catalogNum = rs.getString("catalogNumber");
                    int amount = rs.getInt("amount");
                    map.put(catalogNum, amount);
                }
                if (!map.isEmpty()) {
                    RInventory curr_damage = new RInventory(map, "Inventory");
                    allReportsInventory.put(reportID, curr_damage);
                }
                Database.closeConnection(conn);
                return allReportsInventory.get(reportID);
            }else{
                PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM Reports WHERE reportID = ? AND branchNumber = ?");
                stmt1.setInt(1, reportID);
                stmt1.setInt(2, branchNum);
                ResultSet rs = stmt1.executeQuery();
                if (rs.next()) {
                    Database.closeConnection(conn);
                    return allReportsInventory.get(reportID);
                }else{
                    Database.closeConnection(conn);
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(conn);
        }
        return null;
    }

}
