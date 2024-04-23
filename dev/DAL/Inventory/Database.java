package DAL.Inventory;
import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite::resource:SuppliersInventoryTogether.sqlite";
//    private static final String DB_URL = "jdbc:C:\\Users\\lior2\\Java\\ADSS_Group_H\\identifier.sqlite";
    //"C:\Users\lior2\Java\ADSS_Group_H\identifier.sqlite"
    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(DB_URL);
    }
    public static void clearDatabase(Connection conn) {
        try {
            conn = Database.connect();
            Statement stmt = conn.createStatement();
            String[] tablesToClear = {"Branch", "Item", "Discount", "Reports", "missingProduct", "minAmountInBranch",
                    "allDamage", "allInventory", "allShortage", "CategoryByCatalogNumber"}; // Add all the tables you want to clear
            for (String table : tablesToClear) {
                String sql = "DELETE FROM " + table; // Use DELETE FROM to delete all rows from the table
                stmt.executeUpdate(sql);
            }
            Database.closeConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            Database.closeConnection(conn);
        }
    }


    public static void closeConnection(Connection conn){
        try {
            if (conn!= null && !conn.isClosed()){
                conn.close();;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
