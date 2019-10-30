
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
    
    String sessID;
    
    private Connection getConnection() {
        
        Connection conn = null;
        
        try {
            
            Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
        }        
        catch (Exception stinkystinky) { stinkystinky.printStackTrace(); }
        
        return conn;

    }
    
    public String getQueryResults(String sessionID) throws SQLException {
    
        
        StringBuilder tbl = new StringBuilder();
        String query;
        
        this.sessID = sessionID;
        Connection defcon = getConnection();
        
        query = "SELECT * FROM registrations r WHERE sessionid = ?;";
        
        try { 
            PreparedStatement statement = defcon.prepareStatement(query);
            statement.setString(0, sessID);
            
            if (statement.execute()) {
                ResultSet result = statement.getResultSet();
                
                //  MAKE TABLE
                tbl.append("<table>");
                
                //POPULATE TABLE
                
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
        catch(Exception blahblahblah){System.err.println(blahblahblah);}
        return (tbl.toString());
    }
    
}