
package edu.jsu.mcis.cs425.project_1;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.sql.*;


@WebServlet(name = "register", urlPatterns =  {"/register"})
public class register extends HttpServlet {
    
        protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
                response.setContentType("application/json;charset=UTF-8");

                String stuff = request.getParameter("rcode");

                String[] args = stuff.split(";");
                
                String fname = args[0];
                String lname = args[1];
                String displayname = args[2];
                String sesid = args[3];
                Database datab = new Database();
                String result = datab.registrationAdd(fname, lname, displayname, sesid);
               
                try (PrintWriter out = response.getWriter()){
                    
                    System.out.println(result);
                }


        
    }
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");

                PrintWriter out = response.getWriter();
                Database datab = new Database();

                try {
                    String table = datab.getQueryResults(request.getParameter("rcode"));
                    out.println(table);

                }
                catch(Exception expone){System.err.println(expone);

                }
    }

    
    
    @Override
    @SuppressWarnings("empty-statement")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
              try {
                  processRequest(request, response);
              }

              catch (SQLException exptwo){
                  Logger.getLogger(register.class.getName()).log(Level.SEVERE, null, exptwo);
              }      

   
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
                try {
                    
                    processPost(request, response);
                } 
                catch (SQLException expthree) {
                    
                    Logger.getLogger(register.class.getName()).log(Level.SEVERE, null, expthree);
                }
    }

    
    @Override
    public String getServletInfo() {
        return "Stuff";
    }

}