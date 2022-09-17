package domein.reiziger;



import domein.adres.Adres;
import domein.adres.AdresDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {

    private  Connection conn;
    private AdresDAO adresDAO;



    public  ReizigerDAOPsql(Connection conn){
        this.conn= conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            String tmpSave= "insert into reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum)  values (?,?,?,?,?)   ;";
            PreparedStatement ps =preparedStatementAanmaken(tmpSave);
            ps.setInt(1,reiziger.getId());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setDate(5, (Date) reiziger.getGeboortedatum());
            ps.executeUpdate();
            ps.close();
            adresDAO.save(reiziger.getAdres());
            return true;


        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger){
        try {
            String tmpUpdate= "update  reiziger SET voorletters=?, tussenvoegsel=? , achternaam=? , geboortedatum=?" +
                    "where reiziger.reiziger_id=?;";
            PreparedStatement ps =preparedStatementAanmaken(tmpUpdate);
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, (Date) reiziger.getGeboortedatum());
            ps.setInt(5,reiziger.getId());
            ps.executeUpdate();
            ps.close();
            reiziger.adresToevoegenAanReiziger(adresDAO.findByReiziger(reiziger));

            return true;


        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            Adres adres =  adresDAO.findByReiziger(reiziger);
            if (reiziger.getAdres()!= null){
                adresDAO.delete(adres);
            }
            String tmpUpdate= "DELETE FROM reiziger WHERE reiziger_id=?;";
            PreparedStatement ps= preparedStatementAanmaken(tmpUpdate);
            ps.setInt(1, reiziger.getId());
             ps.executeUpdate();
            ps.close();

            return true;

        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Reiziger findById(int id){
        try {
            String findByIdTmp= "SELECT *  from reiziger WHERE reiziger_id=?;";
            PreparedStatement ps = preparedStatementAanmaken(findByIdTmp);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Reiziger tmpReiziger = nieuweReizigerAanmaken(rs);
            Adres adresOpZoeken=adresDAO.findByReiziger(tmpReiziger);
            if ( adresOpZoeken!= null){
                tmpReiziger.adresToevoegenAanReiziger(adresOpZoeken);
            }
            ps.close();
            rs.close();
            return tmpReiziger;

        }
        catch (Exception e){
            System.out.println(e.getMessage());
         return null;}
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        List lijstReizigers= new ArrayList<Reiziger>();
        try {
            String findByDate= "SELECT *  from reiziger WHERE geboortedatum=?::date ;";
            PreparedStatement ps = preparedStatementAanmaken(findByDate);
            ps.setString(1, datum);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Reiziger tmpReiziger= nieuweReizigerAanmaken(rs);
                Adres adres =adresDAO.findByReiziger(tmpReiziger);
                tmpReiziger.adresToevoegenAanReiziger(adres);
                lijstReizigers.add(tmpReiziger);
                Adres adresOpZoeken=adresDAO.findByReiziger(tmpReiziger);
                if ( adresOpZoeken!= null){
                    tmpReiziger.adresToevoegenAanReiziger(adresOpZoeken);
                }

            }
            ps.close();
            rs.close();
            return lijstReizigers;

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;}
    }


    @Override
    public List<Reiziger> findAll() throws SQLException {
        List lijstReizigers= new ArrayList<Reiziger>();
        String tmpSelectAll = "select  * from  reiziger;";
        ResultSet myre = preparedStatementAanmaken(tmpSelectAll).executeQuery();
        while (myre.next()){
            Reiziger tmpReiziger= nieuweReizigerAanmaken(myre);
            Adres adres =adresDAO.findByReiziger(tmpReiziger);
            tmpReiziger.adresToevoegenAanReiziger(adres);
            lijstReizigers.add(tmpReiziger);
             Adres adresOpZoeken=adresDAO.findByReiziger(tmpReiziger);
            if ( adresOpZoeken!= null){
                tmpReiziger.adresToevoegenAanReiziger(adresOpZoeken);
            }

        }
        myre.close();
        return lijstReizigers;
    }

    public Reiziger nieuweReizigerAanmaken(ResultSet rset) throws SQLException {
        return new Reiziger(rset.getInt("reiziger_id"), rset.getString("voorletters"),
                rset.getString("tussenvoegsel"), rset.getString("achternaam"),
                rset.getDate("geboortedatum"));
    }

    public PreparedStatement preparedStatementAanmaken(String q) throws SQLException {
        return conn.prepareStatement(q);
    }


    public void setAdresDAO(AdresDAO adresDAO) {
        this.adresDAO = adresDAO;
    }
}
