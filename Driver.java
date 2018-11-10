
import java.sql.*;

/**
 * Created by OwenTsai on 2018-11-09.
 */
public class Driver {
    private static Driver driverInstance;
    private Connection con;
    private Statement stmt;


    /*
    * Constructor
     */
    private Driver() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
            System.exit(-1);
        }
    }

    /*
    * singleton
     */
    public static Driver getInstance() {
        if (driverInstance == null) {
            driverInstance = new Driver();
        }
        return driverInstance;
    }

    /*
    * connects to Oracle database named using username and password
     */
    private boolean connect(String username, String password) {
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", username, password);
            System.out.println("\nConnected to Oracle");
            stmt = con.createStatement();
            return true;
            // not sure if the url is correct
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
            System.out.println("\nFailed to connect to Oracle");
            return false;
        }
    }

    /*
    * execute any SQL statements that return nothing
     */
    public void executeUpdate(String sqlstmt) {
        try {
            stmt.executeUpdate(sqlstmt);
            con.commit();
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
            System.out.println(("\nUnable to execute: " + sqlstmt));
        }
    }

    /*
    * execute query statements
     */
    public ResultSet executeQuery(String sqlstmt) {
        try {
            return stmt.executeQuery(sqlstmt);
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
            System.out.println("\nUnable to execute: " + sqlstmt);
            return null;
        }
    }



    /*
    * disconnect from Oracle database
     */
    public boolean disconnect() {
        try {
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
            System.out.println("\nFailed to disconnect from Oracle");
            return false;
        }
    }

}
