import java.sql.*;

public class Main {
    public  static   Connection connection;


    public static Statement getconnection(){
        try {
            // connecten met database in postgres
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "student", "student");
            connection = myConn;
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
        Statement statement =getconnection();
        ResultSet myre = statement.executeQuery("select  * from  reiziger");
        System.out.println("Alle reizigers:");
        while (myre.next()){
            System.out.println("#"+myre.getString("reiziger_id") + ": "+myre.getString("voorletters") + " "+ myre.getString("achternaam") );
        }

    closeConnection();
        myre.close();
    }

    public static   void main(String [] args) throws SQLException {
        alleReizigersUitprinten();





    }

    public  static  void closeConnection() throws SQLException {
        connection.close();

    }

}
