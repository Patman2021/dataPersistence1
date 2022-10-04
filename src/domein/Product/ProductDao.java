package domein.Product;

import domein.ovChip.OvChipkaart;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDao {
    boolean save(Product product) throws SQLException;

    ArrayList<Product> findByOvkaart(OvChipkaart ovChipkaart) throws SQLException;

    boolean update(Product product) throws SQLException;

    boolean delete(Product product);


    ArrayList<Product> findAll() throws SQLException;
}
