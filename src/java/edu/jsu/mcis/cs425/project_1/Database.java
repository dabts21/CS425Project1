
package edu.jsu.mcis.cs425.project_1;

import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Database {
    
    String sesID;
    
    private Connection getConnection() {
        
        Connection conn = null;
        
        try {
            
            Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
        }        
        catch (Exception exp) { exp.printStackTrace(); }
        return conn;
    }
    
    public String getQueryResults(String sessionID) throws SQLException {
    
        
        StringBuilder tbl = new StringBuilder();
        String query;
        
        this.sesID = sessionID;
        Connection aconn = getConnection();
        
        query = "SELECT * FROM registrations r WHERE sessionid = ?;";
        
        try { 
            PreparedStatement statement = aconn.prepareStatement(query);
            statement.setString(0, sesID);
            
            if (statement.execute()) {
                ResultSet result = statement.getResultSet();
                
                tbl.append("<table>");
                
                while (result.next()){
                    
                    tbl.append("<tr>");
                    tbl.append("<td>").append(result.getString("id")).append("</td>");
                    tbl.append("<td>").append(result.getString("firstname")).append("</td>");
                    tbl.append("<td>").append(result.getString("lastname")).append("</td>");
                    tbl.append("<td>").append(result.getString("displayname")).append("</td>");
                    tbl.append("<td>").append(sessionID).append("</td>");
                    tbl.append("</tr>");
                    
                }
                
                tbl.append("</table>");
                     
                
            }
            
            
        }
        catch(Exception exptwo){System.err.println(exptwo);}
        return (tbl.toString());
    }
       
    public String registrationAdd(String fName, String lName, String displayName, String sessionID) throws SQLException{

        int id = 0;
        int result = 0;
        String query;
        String registrationCode;
        ResultSet keys;
        JSONObject json = new JSONObject();
        String results = "";
        
        query = "INSERT INTO registrations (firstname, lastname, displayname, sessionid)"  + "VALUES (?, ?, ?, ?); ";
        Connection aconn = getConnection();
        
        try {
            PreparedStatement p_statement = aconn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            
            p_statement.setString(0, fName);
            p_statement.setString(1, lName);
            p_statement.setString(2, displayName);
            p_statement.setString(3, sessionID);
            
            result = p_statement.executeUpdate();
            
            if (result ==1){
                
                keys = p_statement.getGeneratedKeys();
                if (keys.next()){
                    id = keys.getInt(1);
                }    
            }
            
            String rcode = String.format("%06d", id);
            registrationCode = "R";
            registrationCode += rcode;
            json.put("registration_code", registrationCode);
            json.put("displayname", displayName);
            
            results = JSONValue.toJSONString(json);
            
        }
        catch(Exception expthree){
            System.out.println(expthree.toString());
        }
           
            return (results.trim());
        
        }
       
}

