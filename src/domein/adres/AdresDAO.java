package domein.adres;

import domein.reiziger.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {

    boolean save(Adres adres) throws SQLException;
    Adres findByReiziger(Reiziger reiziger);
    boolean update(Adres adres);

    boolean delete(Adres adres);

    Adres findById(int id);




}
