import java.sql.*;

public class Main {
    public  static   Connection connection;

    public  static Connection getconnection() throws SQLException {
        if (connection == null){
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "student", "student");
        }
        return connection;
    }



    public   static void  alleReizigersUitprinten() throws SQLException {
        Statement statement =getconnection().createStatement();
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
        connection= null;

    }

}
