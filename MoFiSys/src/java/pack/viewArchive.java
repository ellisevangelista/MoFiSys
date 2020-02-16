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
public class viewArchive extends HttpServlet {
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
        ResultSet docuin = null, docuout = null, book = null;
        int pg = Integer.parseInt(request.getParameter("pg"));
        try(PrintWriter out = response.getWriter()){
            out.println("<head><style type=\"text/css\">"
                        + "body{"                   
                        + "font-family: Segoe UI Symbol;"
                        + "color: #515453;"
                        + "}"
                        + "a{"
                        + "color: white;"
                        + "text-decoration:none;"
                        + "font-size: 18px;"
                        + "}"
                        + "</style></head><body>Page " + pg);

            docuin = bean.viewArcDocumentsIn(connection, pg);
            
            while(docuin.next()){
                out.println("<div style=\"padding:10px;margin:10px;background-color:#FFE164;\">");
                out.println("<img src=\"photos/incoming.png\" style=\"left:25px;position:absolute;width:70px;height:70px;\">");
                out.println("<div style=\"right:28px;position:absolute;\">"
                        + "<a href=\"recover?docno=" + docuin.getString("DOCNO").trim() + "&phase=in\" onclick=\"return confirm('Are you sure you want to recover?');\"\">Recover</a> |"
                        + "<a href=\"deleteDocu?docno=" + docuin.getString("DOCNO").trim() + "&phase=in\" onclick=\"return confirm('Are you sure you want to delete?');\"\">Delete</a>"
                        + "</div> <br/>");               
                out.println("<div style=\"left:100px;position:absolute;\">ARCHIVEDDOCINNO-" + docuin.getString("DOCNO").trim() + "</div><br>");
                out.println("<div style=\"left:100px;position:absolute;\">" + docuin.getString("TITLE").trim() + "</div><br/>");
                out.println("</div>");
            }
            
            docuout = bean.viewArcDocumentsOut(connection, pg);
            
            while(docuout.next()){
                out.println("<div style=\"padding:10px;margin:10px;background-color:#FFE164;\">");
                out.println("<img src=\"photos/outgoing.png\" style=\"left:25px;position:absolute;width:70px;height:70px;\">");
                out.println("<div style=\"right:28px;position:absolute;\">"
                        + "<a href=\"recover?docno=" + docuout.getString("DOCNO").trim() + "&phase=out\" onclick=\"return confirm('Are you sure you want to recover?');\"\">Recover</a> | "
                        + "<a href=\"deleteDocu?docno=" + docuout.getString("DOCNO").trim() + "&phase=out\" onclick=\"return confirm('Are you sure you want to delete?');\"\">Delete</a>"
                        + "</div> <br/>");               
                out.println("<div style=\"left:100px;position:absolute;\">ARCHIVEDDOCOUTNO-" + docuout.getString("DOCNO").trim() + "</div><br>");
                out.println("<div style=\"left:100px;position:absolute;\">" + docuout.getString("TITLE").trim() + "</div><br/>");
                out.println("</div>");
            }
            
            book = bean.viewArcBooks(connection, pg);
            
            while(book.next()){
                out.println("<div style=\"padding:10px;margin:10px;background-color:#FFE164;\">");
                out.println("<img src=\"photos/book.png\" style=\"left:25px;position:absolute;width:70px;height:70px;\">");
                out.println("<div style=\"right:28px;position:absolute;\">"
                        + "<a href=\"recover?docno=" + book.getString("ACCOUNTNO").trim() + "&phase=book\" onclick=\"return confirm('Are you sure you want to recover?');\"\">Recover</a> | "
                        + "<a href=\"deleteBook?accno=" + book.getString("ACCOUNTNO").trim() + "\" onclick=\"return confirm('Are you sure you want to delete?');\"\">Delete</a>"
                        + "</div> <br/>");               
                out.println("<div style=\"left:100px;position:absolute;\">ARCHIVEDBOOKNO-" + book.getString("ACCOUNTNO").trim() + "</div><br>");
                out.println("<div style=\"left:100px;position:absolute;\">" + book.getString("TITLE").trim() + "</div><br/>");
                out.println("</div>");
            }
            
            
            if(pg > 1){
            out.println("<a style=\"color: #515453;text-align:center;\" href=\"viewArchive?pg=" + (pg-1) + "\">Previous Page</a>");}
            
            int in = bean.getALimitIn(connection);
            int ou = bean.getALimitOut(connection);
            int bk = bean.getALimit(connection);
            
            int l;
            if(in > ou){l = in;}
            else {l = ou;}
            
            int l2;
            if(l > bk){l2 = l;}
            else{l2 = bk;}
            
            double a = Math.ceil(l2/5);
            if(a >= pg){
            out.println("<a style=\"color: #515453;text-align:center;\" href=\"viewArchive?pg=" + (pg+1) + "\">Next Page</a>");}
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
            Logger.getLogger(viewArchive.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(viewArchive.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(viewArchive.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(viewArchive.class.getName()).log(Level.SEVERE, null, ex);
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
