/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author valel
 */
public class trace extends HttpServlet {
    ServletConfig config;
    Connection connection = null;
    
    public void init(ServletConfig config) throws ServletException {
            super.init(config);
            
            try {	
                    Class.forName(config.getInitParameter("jdbcClassName"));
                    //System.out.println("jdbcClassName: " + config.getInitParameter("jdbcClassName"));
                    String username = config.getInitParameter("dbUserName");
                    String password = config.getInitParameter("dbPassword");
                    StringBuffer url = new StringBuffer(config.getInitParameter("jdbcDriverURL"))
                            .append("://")
                            .append(config.getInitParameter("dbHostName"))
                            .append(":")
                            .append(config.getInitParameter("dbPort"))
                            .append("/")
                            .append(config.getInitParameter("databaseName"));
                    connection = 
                      DriverManager.getConnection(url.toString(),username,password);
            } catch (SQLException sqle){
                    System.out.println("SQLException error occured - " 
                            + sqle.getMessage());
            } catch (ClassNotFoundException nfe){
                    System.out.println("ClassNotFoundException error occured - " 
                    + nfe.getMessage());
            }
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        int pg = Integer.parseInt(request.getParameter("pg"));
        String keyword = request.getParameter("keyword");
        bean bean = new bean();
        try(PrintWriter out = response.getWriter()){
            ResultSet activities = bean.userTrace(connection, pg, keyword);
            
            out.println("<head><style type=\"text/css\">"
                    + "body{"                   
                    + "font-family: Segoe UI Symbol;"
                    + "color: #515453;"
                    + "}"
                    + "</style></head>"
                    + "<body>"
                    + "<form action=\"trace?pg=1\" class=\"search-box\">\n" 
                    + "<input type=\"text\" placeholder=\"Search user's activity here...\" name=\"keyword\">\n"
                    + "<input type=\"hidden\" name=\"pg\" value=\"1\">\n"
                    + "<button type=\"submit\">Search</button>\n" 
                    + "</form>");
            
            while(activities.next()){
                out.println("<div style=\"padding:10px;margin:10px;background-color:#FFE164;\">");
                out.println("<div style=\"left:30px;position:absolute;\">Username: " + activities.getString("USERNAME").trim() + "</div>");
                out.println("<div style=\"right:30px;position:absolute;\">Action: [" + activities.getString("ACTION").trim() + "] " + activities.getString("DATA").trim() + "</div><br />");
                out.println("<div style=\"left:30px;position:absolute;\">Timestamp: " + activities.getString("DATEADDED").trim() + "</div><br />");
                out.println("</div>");
            }
            if(pg > 1){
            out.println("<a style=\"color: #515453;text-align:center;\" href=\"trace?pg=" + (pg-1) + "&keyword=" + keyword + "\">Previous Page</a>");}
            
            int l = bean.getLimitU(connection, keyword);
            double a = Math.ceil(l/15);
            if(a >= pg){
            out.println("<a style=\"color: #515453;text-align:center;\" href=\"trace?pg=" + (pg+1) + "&keyword=" + keyword + "\">Next Page</a>");}
            
            out.println("</body>");
            out.println("</body>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(trace.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(trace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(trace.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(trace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
