package domein.ovChip;

import domein.adres.Adres;
import domein.reiziger.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OvChipDao {




    boolean save(OvChipkaart ovChipkaart) throws SQLException;

    ArrayList<OvChipkaart>  findByReiziger(Reiziger reiziger);

    boolean update(OvChipkaart ovChipkaart);

    boolean delete(OvChipkaart ovChipkaart);

    OvChipkaart findByKaart_Nummer(int id);


}
