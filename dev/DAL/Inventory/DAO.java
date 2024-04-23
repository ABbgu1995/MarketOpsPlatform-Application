package DAL.Inventory;

import BL.Inventory.*;

import java.sql.SQLException;

public interface DAO {
    public status insert(Object ob) throws SQLException;
    public status update(Object ob) throws SQLException;
    public status delete(Object ob) throws SQLException;

}
