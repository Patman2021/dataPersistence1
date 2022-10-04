package domein.ovChip;

import domein.Product.Product;
import domein.Product.ProductDao;
import domein.adres.Adres;
import domein.reiziger.Reiziger;
import domein.reiziger.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;

public class OvChipDAOsql  implements  OvChipDao{
    private ReizigerDAO reizigerDAO;
    private OvChipkaart ovChipkaart;
    private ProductDao productDao;

    private Connection conn;

    public  OvChipDAOsql(Connection conn){
        this.conn= conn;
    }
    @Override
    public boolean save(OvChipkaart ovChipkaart) throws SQLException {
        String tmpSave= "insert into ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id)  values (?, ?, ?, ?,?);";
        PreparedStatement ps =preparedStatementAanmaken(tmpSave);
        ps.setInt(1, ovChipkaart.getKaartNummer());
        ps.setDate(2, (Date) ovChipkaart.getGeligTot());
        ps.setInt(3, ovChipkaart.getKlasse());
        ps.setFloat(4, ovChipkaart.getSaldo());
        ps.setInt(5, ovChipkaart.getReizigerId());
        ps.executeUpdate();
        ps.close();
        if (ovChipkaart.getProductenLijst().size() != 0){
            for (Product p: ovChipkaart.getProductenLijst()){
                productDao.save(p);
            }

        }
        return true;

    }

    @Override
    public ArrayList<OvChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            ArrayList<OvChipkaart> array = new ArrayList<>();
            String tmpfindByReiziger= "select  * from ov_chipkaart where reiziger_id=?;";
            PreparedStatement ps = preparedStatementAanmaken(tmpfindByReiziger);
            ps.setInt(1, reiziger.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
            OvChipkaart ov = nieuwOvChipkaartAanmaken(rs);
            array.add(ov);}
            ps.close();
            rs.close();
            return array;





        }catch (Exception e){
            return null;
        }

    }

    @Override
    public boolean update(OvChipkaart ovChipkaart) {
        try {
            String tmpUpdate= "update  ov_chipkaart SET  geldig_tot=? , klasse=? , saldo=?" +
                    "where kaart_nummer=? ;";
            PreparedStatement ps =preparedStatementAanmaken(tmpUpdate);
            ps.setDate(1, (Date) ovChipkaart.getGeligTot());
            ps.setInt(2, ovChipkaart.getKlasse());
            ps.setFloat(3, ovChipkaart.getSaldo());
            ps.setInt(4, ovChipkaart.getKaartNummer());
            ps.executeUpdate();
            ps.close();
            if (ovChipkaart.getProductenLijst().size() != 0){
                for (Product p: ovChipkaart.getProductenLijst()){
                    productDao.update(p);
                }

            }
            return true;


        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(OvChipkaart ovChipkaart) {
        try {
            if (ovChipkaart.getProductenLijst().size() != 0){
                for (Product p: ovChipkaart.getProductenLijst()){
                productDao.delete(p);
                }
            String tmpUpdate= "DELETE FROM ov_chipkaart WHERE kaart_nummer=?;";
            PreparedStatement ps= preparedStatementAanmaken(tmpUpdate);
            ps.setInt(1, ovChipkaart.getKaartNummer());
            ps.executeUpdate();
            ps.close();


            }
            return true;

        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public OvChipkaart findByKaart_Nummer(int id) {
        try {
            String findByKaart_NummerTmp = "SELECT *  from ov_chipkaart WHERE kaart_nummer=?;";
            PreparedStatement ps = preparedStatementAanmaken(findByKaart_NummerTmp);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            OvChipkaart o = nieuwOvChipkaartAanmaken(rs);
            ps.close();
            rs.close();
            return o;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public PreparedStatement preparedStatementAanmaken(String q) throws SQLException {
        return conn.prepareStatement(q);
    }

     public  ArrayList<OvChipkaart> findByProduct(Product product) throws SQLException {
         ArrayList listOV= new ArrayList<Product>();
         String tmpSelectAll = "select  * from  ov_chipkaart" +
                 " inner join ov_chipkaart_product on ov_chipkaart.kaart_nummer = ov_chipkaart_product.kaart_nummer  where product_nummer=?;";
          PreparedStatement ps =preparedStatementAanmaken(tmpSelectAll);
          ps.setInt(1, product.getProductNummer());
         ResultSet myre = ps.executeQuery();
         while (myre.next()){
             listOV.add(nieuwOvChipkaartAanmaken(myre));

         }
         myre.close();
         return  listOV;
     }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public OvChipkaart nieuwOvChipkaartAanmaken(ResultSet rset) throws SQLException {
        OvChipkaart tmpOvChipkaart = new OvChipkaart(rset.getInt("kaart_nummer"), rset.getDate("geldig_tot"),
                rset.getInt("klasse"), rset.getFloat("saldo"),
                rset.getInt("reiziger_id"));
        return  tmpOvChipkaart;
    }

}
