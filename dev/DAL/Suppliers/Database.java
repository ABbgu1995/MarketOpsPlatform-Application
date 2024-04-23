package DAL.Suppliers;
import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite::resource:SuppliersInventoryTogether.sqlite";
    //private static final String DB_URL_Inventory = "jdbc:sqlite:C:\\Users\\amita\\IdeaProjects\\ADSS_Group_H\\doc\\Inventory.db";
//    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\User\\IdeaProjects\\ADSS_Group_H3\\Suppliers.db";

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(DB_URL);
    }

//    public static Connection connect_inventory() throws SQLException{
//        return DriverManager.getConnection(DB_URL_Inventory);
//    }

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
