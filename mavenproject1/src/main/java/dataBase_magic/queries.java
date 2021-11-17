/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase_magic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author User
 */
public class queries {
    
   static Connection conn = connection.tryConnection();

    
    public static int register(JTextField tb, JPasswordField pf) throws SQLException{
        
        String login = tb.getText();
        String password = pf.getText();
        
        
        String registerScript = "insert into users_table (user_id, user_name, password) "
                + "values ((select count(user_id) from users_table) + 1,'"+login+"' , '"+password+"')";
        Statement statement = conn.createStatement();
        var executeUpdate = statement.executeUpdate(registerScript);
        if (executeUpdate > 0)
            return 1;
        else return 0;
    }
}
