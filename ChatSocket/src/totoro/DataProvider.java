/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totoro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Totoro
 */
public class DataProvider {
    //JDBC Driver for MS SQL Server 2014
    private final String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=ChatSocket;user=sa;password=123456";
    private Connection connection = null;
    
    // Data Access Object
    public void connect() {
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(connectionString);
            if (connection != null) {
                //Logger.getLogger("connected");
                System.out.println("connected to database");
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(e.toString());
        }
    }

    public void disConnect() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Transport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
