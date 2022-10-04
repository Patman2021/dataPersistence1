package domein.ovChip;

import domein.Product.Product;
import domein.adres.Adres;
import domein.reiziger.Reiziger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OvChipDao {



     OvChipkaart nieuwOvChipkaartAanmaken(ResultSet rset) throws SQLException;
    boolean save(OvChipkaart ovChipkaart) throws SQLException;

    ArrayList<OvChipkaart>  findByReiziger(Reiziger reiziger);

    boolean update(OvChipkaart ovChipkaart);

    boolean delete(OvChipkaart ovChipkaart);

    OvChipkaart findByKaart_Nummer(int id);

    ArrayList<OvChipkaart> findByProduct(Product product) throws SQLException;




}
