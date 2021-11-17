/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase_magic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class connection{
    public static Connection tryConnection(){
        String jdbcURL = "jdbc:postgresql://localhost:5432/UCED_DB";
        String username = "postgres";
        String password = "123";
        Connection connection = null;
    
    try {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(jdbcURL, username, password);
        //return "Connection succeed";
        
    } catch (SQLException ex) {
        Logger.getLogger(connection.class.getName()).log(Level.SEVERE, null, ex);
        //return "Connection failed";
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    
    
}
