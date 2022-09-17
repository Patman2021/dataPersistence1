package domein.adres;

import domein.reiziger.Reiziger;
import domein.reiziger.ReizigerDAO;
import domein.reiziger.ReizigerDAOPsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdressDAOPsql implements AdresDAO {

    private Connection conn;

    private  Reiziger reiziger;
    private ReizigerDAO reizigerDAO;

    public AdressDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public  Adres findByReiziger(Reiziger reiziger){
        try {
            String tmpfindByReiziger= "select  * from adres where reiziger_id=?;";
            PreparedStatement ps = preparedStatementAanmaken(tmpfindByReiziger);
            ps.setInt(1, reiziger.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            Adres a = nieuwAdresAanmaken(rs);
            ps.close();
            rs.close();
            return a;





        }catch (Exception e){
            return null;
        }
    }




    @Override
    public boolean save(Adres adres) throws SQLException {
        try {
            String tmpSave= "insert into adres   values (?, ?, ?, ?,?,?);";
            PreparedStatement ps =preparedStatementAanmaken(tmpSave);
            ps.setInt(1,adres.getId());
            ps.setString(2, adres.getPostcode());
            ps.setString(3, adres.getHuisnummer());
            ps.setString(4, adres.getStraat());
            ps.setString(5,  adres.getWoonplaats());
            ps.setInt(6, adres.getReizigerId());
            ps.executeUpdate();
            ps.close();
            return true;


        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            String tmpUpdate= "update  adres SET postcode=?, huisnummer=? , straat=? , woonplaats=?" +
                    "where adres.adres_id=?;";
            PreparedStatement ps =preparedStatementAanmaken(tmpUpdate);
            ps.setString(1, adres.getPostcode());
            ps.setString(2, adres.getHuisnummer());
            ps.setString(3,  adres.getStraat());
            ps.setString(4, adres.getWoonplaats());
            ps.setInt(5, adres.getId());
            ps.executeUpdate();
            ps.close();
            reizigerDAO.findById(adres.getReizigerId()).adresToevoegenAanReiziger(adres);
            return true;


        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            String tmpUpdate= "DELETE FROM adres WHERE adres_id=?;";
            PreparedStatement ps= preparedStatementAanmaken(tmpUpdate);
            ps.setInt(1, adres.getId());
            ps.executeUpdate();
            ps.close();
            reizigerDAO.delete(reizigerDAO.findById(adres.getReizigerId()));
            return true;

        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Adres findById(int id) {
        try {
            String findByIdTmp = "SELECT *  from adres WHERE adres_id=?;";
            PreparedStatement ps = preparedStatementAanmaken(findByIdTmp);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Adres a = nieuwAdresAanmaken(rs);
            ps.close();
            rs.close();
            return a;

        } catch (Exception e) {
            ;
            return null;
        }
    }


    public Adres nieuwAdresAanmaken(ResultSet rset) throws SQLException {
        return new Adres(rset.getInt("adres_id"), rset.getString("postcode"),
                rset.getString("huisnummer"), rset.getString("straat"),
                rset.getString("woonplaats"),
                rset.getInt("reiziger_id"));
    }


    public PreparedStatement preparedStatementAanmaken(String q) throws SQLException {
        return conn.prepareStatement(q);
    }

    public void setReizigerDAO(ReizigerDAO reizigerDAO) {
        this.reizigerDAO = reizigerDAO;
    }
}


