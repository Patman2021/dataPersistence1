import reiziger.Reiziger;
import reiziger.ReizigerDAO;
import reiziger.ReizigerDAOPsql;

import java.sql.*;
import java.util.List;

public class Main {
    private   static   Connection connection;
    private  ReizigerDAO rdao;
    private Reiziger reiziger;


    public static   void main(String [] args) throws SQLException {
        getconnection();
        testReizigerDAO(new ReizigerDAOPsql(connection));
        closeConnection();


    }

    public static void getconnection(){
        try {
            // connecten met database in postgres
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "student", "student");
            connection = myConn;
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public  static  void closeConnection() throws SQLException {
        connection.close();

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

        // Maak een nieuwe reiziger aan en persisteer deze in de database
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

        //Ophalen van reiziger met id 5;
        System.out.print("[Test] findById(): Met reiziger_id:5, wordt opgehaald:\n");
        if ( null != rdao.findById(5)){
            System.out.println( rdao.findById(5).toString());
        }else {
            System.out.println("Oeps er ging iets fout");
        }


        //Ophalen van reiziger met de datum;
        System.out.print("\n[Test] findByGbdatum(): Met geboortedatum= 2002-10-22, worden opgehaald:\n");
        List<Reiziger> lijst=rdao.findByGbdatum("2002-10-22");
        if (lijst.isEmpty()){
            System.out.println("oeps de test is niet gehaald");
        }
        else{ System.out.println("Yes de test is geslaagds! hier is de lijst " +lijst);}

        //Updaten van een reiziger;
        System.out.print("\n[Test] update(): De Reiziger id die gebruikt wordt is 5. Als het werkt moet er geen G. Piccardo uitkomen\n");
        Reiziger testReizigerUpdate= new Reiziger(5,"J.A.J.","", "Bosman",java.sql.Date.valueOf("1998-09-01"));
        if (rdao.update(testReizigerUpdate)){
            System.out.println("Yes de test is geslaagds! hier zijn de nieuwe gegevens= " +  rdao.findById(5));
        }
        else{
            System.out.println("oeps er ging iets mis ");
        }




    }

}
