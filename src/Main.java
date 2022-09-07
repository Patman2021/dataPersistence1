import java.sql.*;

public class Main {


    public static Statement connecten(){
        try {
            // connecten met database in postgres
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "student", "student");
            // statement maken
            Statement statement = myConn.createStatement();
            return statement;
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return null;
    }

    public   static void  alleReizigersUitprinten() throws SQLException {
        Statement statement =connecten();
        ResultSet myre = statement.executeQuery("select  * from  reiziger");
        System.out.println("Alle reizigers:");
        while (myre.next()){
            System.out.println("#"+myre.getString("reiziger_id") + ": "+myre.getString("voorletters") + " "+ myre.getString("achternaam") );
        }

    }

    public static   void main(String [] args) throws SQLException {
        alleReizigersUitprinten();





    }

}
