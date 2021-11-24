/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase_magic;

import Parsing_magic.parsing;
import java.awt.Component;
import java.awt.List;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    
    public static int findUser(JTextField tb, JPasswordField pf) throws SQLException{
        
        String login = tb.getText();
        String password = pf.getText();
        
        String findUser = "select * from users_table "
                + "where user_name = '"+login+"' and password = '"+password+"'";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(findUser);
        while (rs.next()) {
            if(rs.getString("user_name") != null){
//                String lastName = rs.getString("user_name");
//                String pass = rs.getString("password");
//                System.out.println(lastName + "\t" + pass);
                return 1;
            }
            else{
                return 0;
            }
                
        }
        return 2;
    }
    
    public static void updateProducts(String shop_source) throws SQLException, IOException, Exception{
        
        String clearTableScript = "truncate products cascade";
        Statement statement = conn.createStatement();
        statement.executeUpdate(clearTableScript);
        
        String creator, updateScript, link;//,prod_names;
        Elements names = parsing.getText(shop_source);
        for (Element name: names){
            creator = parsing.getCreatorFromName(name);
            link = parsing.getTextLink(name);
            //prod_names = parsing.getNames(shop_source);
            updateScript = "insert into products (id, name, group_name, creator, link) "
                + "values ((select count(id) from products) + 1,'"+name.text()+"' , (select group_name from groups where group_name = 'body'), '"+creator+"', '"+link+"')";
            statement = conn.createStatement();
            statement.executeUpdate(updateScript);
        }
    }
    
    public static void saveFilters(JPanel panel, String username) throws SQLException{
        
        
        if(panel.getName() == "bodyPanel"){
            
        Component myComps[] = panel.getComponents();
        ArrayList parameters = new ArrayList();
        ArrayList filter_names = new ArrayList();
        String filter, param;
        
        for(int i = 0; i < myComps.length; i++){
            
            
            if(myComps[i] instanceof JLabel){
               JLabel myLabel = (JLabel) myComps[i];
               filter = myLabel.getText();
               filter_names.add(filter);
            }
            if(myComps[i] instanceof JComboBox){
               JComboBox comboBox = (JComboBox) myComps[i];
               param = (String) comboBox.getSelectedItem();
               parameters.add(param);
            }
            
        }
        
        
        
        for(int i = 0; i < filter_names.size(); i++){
            
            filter = (String) filter_names.get(i);
            param = (String) parameters.get(i);
            
            String updateScript = "insert into filters (id, parameter, value, user_id, type) "
                + "values ((select count(id) from filters) + 1 , '"+filter+"',"
                    + "'"+param+"', (select user_id from users_table where user_name = '"+username+"'), 'body')";
                Statement statement = conn.createStatement();
                statement.executeUpdate(updateScript);
        }
        
      }
    }
    
    public static void getBodiesByParams(String form, String type, JTable table, String creator) throws IOException, SQLException{
        
        ArrayList<String> params = new ArrayList<String>();
        params = parsing.getTextParameters(parsing.getParameters("https://www.regard.ru/catalog/group11000.htm"));
        ResultSet rs1 = null;
        DefaultTableModel table_model = (DefaultTableModel)table.getModel();
        while(table_model.getRowCount() > 0)
        {
            table_model.removeRow(0);
        }
        for(int i = 0; i < params.size(); i++){
            
            if(params.get(i).contains(form) && params.get(i).contains(type)){
                
                String getNamesAndLinksScript = "select name, link from products where id = '"+i+"' and creator = '"+creator+"'";
                Statement statement = conn.createStatement();
                rs1 = statement.executeQuery(getNamesAndLinksScript);
                while(rs1.next())
                    table_model.addRow(new Object[] {rs1.getString("name"), rs1.getString("link")});
            }
            
        }
        
    }
}
