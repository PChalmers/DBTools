/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Main
 */
public class DBTools {

    private Connection conn = null;
    private final String CONNECTIONSTRING = "jdbc:mysql://localhost/world?" + "user=root&password=";
    private final String queryDB = "SELECT * FROM countrylanguage";
    //private final String queryDB = "SELECT * FROM city WHERE Population > 1000000";
    private ResultSet resultSet;

    public DBTools() throws SQLException {
        connectToDB();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            DBTools obj = new DBTools();
            
            obj.processQuerry();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void processQuerry() {
        ResultSet rs = getData(queryDB);
        
        try {
            while(rs.next()) {
                
                Vector rowData;
                rowData = new Vector();
                
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                
                for (int i = 1; i < columnsNumber; i++) {
                    System.out.println(i);
                    String currentname = rs.getString(i);
                    System.out.println(currentname);
                    boolean add;
                    add = rowData.add((Object)currentname);
                }

                // model.addRow(rowData);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void connectToDB() throws SQLException {
        try {
            conn = DriverManager.getConnection(CONNECTIONSTRING);
            // Do something with the Connection
            System.out.println("DB catelog name: " + conn.getCatalog());
            
            testDBExists();
             

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            
            throw ex;
        }

        System.out.println("Connection done ");
    }
    
    private void testDBExists() {

        try {
            // Connection connection = <your java.sql.Connection>
            resultSet = conn.getMetaData().getCatalogs();
            
            //iterate each catalog in the ResultSet
            while (resultSet.next()) {
                // Get the database name, which is at position 1
                String databaseName = resultSet.getString(1);
                System.out.println("DB name -> " + databaseName);
            }
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getDBData() {

        try {
            // Connection connection = <your java.sql.Connection>
            return conn.getMetaData().getCatalogs();

        } catch (SQLException ex) {
            Logger.getLogger(DBTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
        
    public ResultSet getData(String query) {
        // assume that conn is an already created JDBC connection (see previous examples)
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            // Now do something with the ResultSet ....
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } 
        return rs;
    }



}
