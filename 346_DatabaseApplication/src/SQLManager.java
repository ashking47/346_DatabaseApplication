import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

/**
 * Created by ashly on 4/22/2017.
 */
public class SQLManager {
    public static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static Connection conn = null;
    private static String url = "jdbc:oracle:thin:@csor12c.dhcp.bsu.edu:1521:or12cdb";

    public static void openConnection() throws ClassNotFoundException, SQLException {
        try{
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("No driver.");
            e.printStackTrace();
            throw e;
        }
        try{
            conn = DriverManager.getConnection(url, "aeking2", "7661");
        } catch(SQLException e2) {
            System.out.println("Connection not established.");
            e2.printStackTrace();
            throw e2;
        }
    }

    public static void closeConnection() throws SQLException {
        try{
            if (conn != null && !conn.isClosed()){
                conn.close();
            }
        } catch (Exception e3){
            throw e3;
        }
    }

    public static ResultSet retrieveQuery(String input) throws SQLException, ClassNotFoundException {
        Statement stmt;
        ResultSet rst;
        CachedRowSet rowSet = null;
        try{
            openConnection();
            stmt = conn.createStatement();
            rst = stmt.executeQuery(input);

            //rowSet = new CachedRowSetImpl();
            //rowSet.populate(rst);
        } catch (SQLException e4){
            e4.printStackTrace();
            throw e4;
        }

        return rst;
    }

    public static int columnCount(ResultSet result) throws SQLException {
        ResultSetMetaData rsmd = result.getMetaData();
        int count = rsmd.getColumnCount();
        return count+1;
    }
}
