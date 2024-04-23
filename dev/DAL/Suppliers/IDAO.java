package DAL.Suppliers;

import java.util.Optional;
import java.sql.SQLException;
import java.util.*;

public interface IDAO <T> {
   T find(int key) throws SQLException;
    void insert(T t) throws SQLException;
    void update (T t, String[] params);
    void delete(T t) throws SQLException;
    //Object create
}
