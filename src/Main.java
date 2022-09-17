import domein.adres.Adres;
import domein.adres.AdresDAO;
import domein.adres.AdressDAOPsql;
import domein.reiziger.Reiziger;
import domein.reiziger.ReizigerDAO;
import domein.reiziger.ReizigerDAOPsql;

import java.sql.*;
import java.util.List;

public class Main {
    private   static   Connection connection;
    private  ReizigerDAO rdao;
    private Reiziger reiziger;


    public static   void main(String [] args) throws SQLException {
         ReizigerDAOPsql rdao = new ReizigerDAOPsql(getconnection());
         AdressDAOPsql adao= new AdressDAOPsql(getconnection());
         rdao.setAdresDAO(adao);
         adao.setReizigerDAO(rdao);
        testReizigerDAO( rdao);
        testCrudAdress(adao, rdao);
        closeConnection();


    }

    public  static Connection getconnection() throws SQLException {
        if (connection == null){
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "student", "student");
        }
        return connection;
    }

    public  static  void closeConnection() throws SQLException {
        connection.close();
        connection= null;

    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe domein.reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        //Verwijderen van Reiziger sietske;
        System.out.print("[Test] ReizigerDAO.delete():"+ sietske.getNaam()+" Met reiziger_id: "+sietske.getId()+" wordt verwijderd:\n");
        if (rdao.delete(sietske)) {
            System.out.println("Test is gelukt! \n");
        } else {
            System.out.println("Oeps er ging iets mis \n");
        }

        //Ophalen van domein.reiziger met id 5;
        System.out.print("[Test] findById(): Met reiziger_id:5, wordt opgehaald:\n");
        if ( null != rdao.findById(5)){
            System.out.println( rdao.findById(5).toString());
        }else {
            System.out.println("Oeps er ging iets fout");
        }


        //Ophalen van domein.reiziger met de datum;
        System.out.print("\n[Test] findByGbdatum(): Met geboortedatum= 2002-10-22, worden opgehaald:\n");
        List<Reiziger> lijst=rdao.findByGbdatum("2002-10-22");
        if (lijst.isEmpty()){
            System.out.println("oeps de test is niet gehaald");
        }
        else{ System.out.println("Yes de test is geslaagds! hier is de lijst " +lijst);}

        //Updaten van een domein.reiziger;
        System.out.print("\n[Test] update(): De Reiziger id die gebruikt wordt is 5. Als het werkt moet er geen G. Piccardo uitkomen\n");
        Reiziger testReizigerUpdate= new Reiziger(5,"J.A.J.","", "Bosman",java.sql.Date.valueOf("1998-09-01"));
        if (rdao.update(testReizigerUpdate)){
            System.out.println("Yes de test is geslaagds! hier zijn de nieuwe gegevens= " +  rdao.findById(5));
        }

        else{
            System.out.println("oeps er ging iets mis ");
        }







    }
    //Crud testen voor de class adres
    private static void testCrudAdress(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // adress vinde doormiddel van adres id
        System.out.println("\n[Test find by id]");
        if ( null != adao.findById(5)){
            System.out.println( adao.findById(5));
        }else {
            System.out.println("Oeps er ging iets fout\n");
        }

        // Maak een nieuw adres aan en persisteer deze in de database

        String gbdatum = "1981-03-14";
        Adres saveAdres = new Adres(101, "2312xx", "187A", "oude vest", "Leiden", 69);
        Reiziger tmpReiziger111=new Reiziger(69, "m", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.save(tmpReiziger111);
        System.out.print("\n[Test Save()]\n");
        adao.save(saveAdres);
        System.out.println( " het volgende adres is corect opgeslagen:" + adao.findById(101).toString() +"\n");

        // adress updaten
        System.out.print("[Test Updaten()] van " + adao.findById(101) +"\n" );
        Adres updateAdres101=new Adres(101, "6537JH", "5449", "Meijhorst ", "Nijmegen", 69);
         if (adao.update(updateAdres101)){
             System.out.println("updated adress:"+ rdao.findById(69) + "\n");
         }
         else {
             System.out.println("oeps de test ging niet goed");
         }

        System.out.print("[Test delete]\n");
        if (adao.delete(updateAdres101)) {
            System.out.println("Het verwijderen  van adress_id= 101 is voltooid! en de reizger met id= 69 \n");
        } else {
            System.out.println("Oeps er ging iets mis \n");
        }



        System.out.print("[Test findByReiziger]\n");
        System.out.println(adao.findByReiziger(new Reiziger(5,"","", "",java.sql.Date.valueOf("1998-09-01"))));









    }


}
