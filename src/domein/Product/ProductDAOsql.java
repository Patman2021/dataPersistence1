package domein.Product;

import domein.adres.Adres;
import domein.ovChip.OvChipDao;
import domein.ovChip.OvChipkaart;
import domein.reiziger.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ProductDAOsql  implements  ProductDao{

    private Connection conn;
    private OvChipDao ovChipDao;

    public ProductDAOsql(Connection conn){
        this.conn = conn;
    }
    @Override
    public boolean save(Product product) throws SQLException {
        try {
                String tmpSave= "insert into product (product_nummer, naam, beschrijving, prijs)  values (?, ?, ?, ?) " +
                        "ON CONFLICT  ON constraint product_pkey DO NOTHING;";
                PreparedStatement ps =preparedStatementAanmaken(tmpSave);
                ps.setInt(1,product.getProductNummer());
                ps.setString(2, product.getNaam());
                ps.setString(3, product.getBeschrijving());
                ps.setFloat(4, product.getPrijs());
                ps.executeUpdate();
                ps.close();
            if (product.getOvChipkaarten().size() != 0){
                for (OvChipkaart ov: product.getOvChipkaarten()){
                    String tmpSave2= "insert into ov_chipkaart_product (kaart_nummer,product_nummer, last_update)  values (?, ?,?);";
                    PreparedStatement ps2 =preparedStatementAanmaken(tmpSave2);
                    ps2.setInt(1,ov.getKaartNummer());
                    ps2.setInt(2, product.getProductNummer());
                    ps2.setDate(3,  Date.valueOf(LocalDate.now()));
                    ps2.executeUpdate();
                    ps2.close();
                }
            }
            return true;


        } catch (Exception e) {
            System.out.println( e );
            return false;
        }
    }

    @Override
    public ArrayList<Product> findByOvkaart(OvChipkaart ovChipkaart) throws SQLException {
        ArrayList<Product> productsExportList= new ArrayList<>();
        String tmpLinksOv = "select  *  from ov_chipkaart_product inner join product on" +
                " product.product_nummer = ov_chipkaart_product.product_nummer where " +
                "kaart_nummer=? ;";
        PreparedStatement ps =preparedStatementAanmaken(tmpLinksOv);
        ps.setInt(1, ovChipkaart.getKaartNummer());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            productsExportList.add(nieuwProductAanmaken(rs));
        }

        return productsExportList;

    }


    // Er zijn extra methodes aangemaakt die update aanroept. Zo blijft het overzichtelijk en zijn
// onderdelen iets meer van erlkaar geschieden.
    @Override
    public boolean update(Product product) throws SQLException{
        try {
            // Updaten van het product zelf
            String tmpUpdate = "update  product SET  naam=? , beschrijving=?, prijs=?" +
                    "where product_nummer=?;";
            PreparedStatement ps = preparedStatementAanmaken(tmpUpdate);
            ps.setString(1, product.getNaam());
            ps.setString(2, product.getBeschrijving());
            ps.setFloat(3, product.getPrijs());
            ps.setInt(4, product.getProductNummer());
            ps.executeUpdate();
            ps.close();
            // Controleren of er ovchipkaarten gelinkt zijn aan dit product
            if (product.getOvChipkaarten().size() != 0) {
                for (OvChipkaart ov : product.getOvChipkaarten()) {
                    // alle ovkaarten invoeren voor het geval ze nieuw zijn, anders wordt de error afgehandeld door ON 'CONFLICT  DO NOTHING'
                    String tmpSave2 = "UPDATE ov_chipkaart_product SET kaart_nummer= ?, product_nummer=? WHERE kaart_nummer=?  ON CONFLICT  DO NOTHING;";
                    PreparedStatement ps2 = preparedStatementAanmaken(tmpSave2);
                    ps2.setInt(1, ov.getKaartNummer());
                    ps2.setInt(2, product.getProductNummer());
                    ps2.setInt(3, product.getProductNummer());
                    ps2.executeUpdate();
                    ps2.close();
                }
                // In de deleteOldLinkd array worden de oude ov- kaarten geplaats die verwijderd zijn uit het
                // object maar nog steeds in de database zatten.
                // om een  java.util.ConcurrentModificationException te verkomen moest ik een andere array aanmaken
                ArrayList<OvChipkaart> deleteOldLinks = new ArrayList<OvChipkaart>();
                for (OvChipkaart ov : findLinksProduct(product)){
                    // als de ov-kaarten lijst van product het ov niet heeft, moet deze verwijderd worden
                    if(!product.getOvChipkaarten().contains(ov)){
                        deleteOldLinks.add(ov);
                    }
                }
                // verwijderen van data in de tussentabel
                deleteOldLinksVanOvChipkaartProdocut(deleteOldLinks);
            }
        }catch (Exception e ){
        return false;}
   return true; }




    @Override
    public boolean delete(Product product) {
        try {
            if (product.getOvChipkaarten().size() !=0 ){
                String tmpdeleteOvLink= "DELETE FROM   ov_chipkaart_product WHERE product_nummer=?;";
                PreparedStatement ps2= preparedStatementAanmaken(tmpdeleteOvLink);
                ps2.setInt(1, product.getProductNummer());
                ps2.executeUpdate();
                ps2.close();
            }
            String tmpdelete= "DELETE  FROM product WHERE product_nummer=?;";
            PreparedStatement ps= preparedStatementAanmaken(tmpdelete);
            ps.setInt(1, product.getProductNummer());
            ps.executeUpdate();
            ps.close();
            return true;


        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public ArrayList<Product> findAll() throws SQLException {
        ArrayList listProduct= new ArrayList<Product>();
        String tmpSelectAll = "select  * from  product;";
        ResultSet myre = preparedStatementAanmaken(tmpSelectAll).executeQuery();
        while (myre.next()){
            // De links worden opgehaald in de nieuwProductAanmaken() methode
            listProduct.add(nieuwProductAanmaken(myre));

        }
        myre.close();
        return listProduct;
    }


    public PreparedStatement preparedStatementAanmaken(String q) throws SQLException {
        return conn.prepareStatement(q);
    }
    // findLinksProduct zoekt alle data op van een product in de tussentabel
    public ArrayList<OvChipkaart> findLinksProduct(Product product) throws SQLException {
        ArrayList lijstOvchipkaarten= new ArrayList<OvChipkaart>();
        // Alle links met het product opzoeken.
        String tmpfindLinksProduct = "select  *  from ov_chipkaart_product where " +
                "product_nummer=? ;";
        PreparedStatement ps =preparedStatementAanmaken(tmpfindLinksProduct);
        ps.setInt(1, product.getProductNummer());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            // De gevonden kaarten toevoegen aan de lijst
            OvChipkaart ov =ovChipDao.findByKaart_Nummer(rs.getInt("kaart_nummer"));
            lijstOvchipkaarten.add(ov);
        }
        ps.close();
        rs.close();
        return lijstOvchipkaarten;
    }


    // Deze functie verwijderd de doorgeven links tussen ovChipkaart en product in de tussentabel
    public boolean deleteOldLinksVanOvChipkaartProdocut(ArrayList<OvChipkaart> lijst){
        return true;
    }

    public void setOvChipDao(OvChipDao ovChipDao) {
        this.ovChipDao = ovChipDao;
    }


    public Product nieuwProductAanmaken(ResultSet rset) throws SQLException {
        Product tmpProduct = new Product(rset.getInt("product_nummer"), rset.getString("naam"),
                rset.getString("beschrijving"), rset.getFloat("prijs"));
        if (ovChipDao != null) {
            for (OvChipkaart ov: ovChipDao.findByProduct(tmpProduct)){
            tmpProduct.addOvkaart(ov);
        }


    }
        return tmpProduct;
    }

}
