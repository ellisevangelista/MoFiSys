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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author GIan
 */
public class viewNotif extends HttpServlet {
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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()){
            String user_name = request.getParameter("user_name");
            java.util.Date today = new java.util.Date();
            bean bean = new bean();
            String dateToday = bean.dateConvert(today);
            ResultSet cha = bean.viewForgot(connection);
            ResultSet pen = bean.viewNotifOut(user_name, dateToday, connection);
            int count = Integer.parseInt(request.getParameter("count"));
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
                    + "</style></head><body>");
            
            while(cha.next()){
                out.println("<div style=\"padding:10px;margin:10px;background-color:#FF9999;\">");
                out.println("<img src=\"photos/accounta.png\" style=\"left:25px;position:absolute;width:70px;height:70px;\">");
                out.println("<div style=\"left:100px;position:absolute;\"><b>FORGOT PASSWORD</b>&nbsp;&nbsp;"
                        + "</div>");   
                out.println("<div style=\"right:28px;top:absolute;position:absolute;\"><a href=\"updateUser?user=" + cha.getString("USERNAME").trim() + "&phase=four\">Reset Passsword</a>&nbsp;&nbsp;"
                        + "</div> <br/>");               
                out.println("<div style=\"left:100px;position:absolute;\">USERNAME: " + cha.getString("USERNAME").trim() + "</div><br>");
                out.println("<div style=\"left:100px;position:absolute;\">COMMITTEE: " + cha.getString("CMT").trim() + "</div><br/>");
                out.println("</div>");
            }
            
            while(pen.next()){
                out.println("<div style=\"padding:10px;margin:10px;background-color:#FFE164;\">");
                out.println("<img src=\"photos/outgoinga.png\" style=\"left:25px;position:absolute;width:70px;height:70px;\">");
                out.println("<div style=\"left:100px;position:absolute;\"><b>PENDING DOCUMENT</b>&nbsp;&nbsp;"
                        + "</div>"); 
                out.println("<div style=\"right:28px;top:absolute;position:absolute;\"><a href=\"updateNotifOut?docno=" + pen.getString("DOCNO").trim() + "\" >Dismiss</a>&nbsp;&nbsp;"
                        + "</div> <br/>");               
                out.println("<div style=\"left:100px;position:absolute;\">DOCINNO-" + pen.getString("DOCNO").trim() + "</div><br>");
                out.println("<div style=\"left:100px;position:absolute;\">" + pen.getString("TITLE").trim() + "</div><br/>");
                out.println("</div>");
            }      
            
             pen = bean.viewNotifIn(user_name, dateToday, connection);
            
             while(pen.next()){
                out.println("<div style=\"padding:10px;margin:10px;background-color:#FFE164;\">");
                out.println("<img src=\"photos/incominga.png\" style=\"left:25px;position:absolute;width:70px;height:70px;\">");
                out.println("<div style=\"left:100px;position:absolute;\"><b>PENDING DOCUMENT</b>&nbsp;&nbsp;"
                        + "</div>"); 
                out.println("<div style=\"right:28px;top:absolute;position:absolute;\"><a href=\"updateNotifIn?docno=" + pen.getString("DOCNO").trim() + "\" >Dismiss</a>&nbsp;&nbsp;"
                        + "</div> <br/>");               
                out.println("<div style=\"left:100px;position:absolute;\">DOCINNO-" + pen.getString("DOCNO").trim() + "</div><br>");
                out.println("<div style=\"left:100px;position:absolute;\">" + pen.getString("TITLE").trim() + "</div><br/>");
                out.println("</div>");
            }  
             if (count==0){
                out.println("<div style=\"padding:10px;margin:10px;background-color:#FFE164;\">");
                out.println("<div style=\"middle:28px;position:absolute;\">You have no new notifications. &nbsp;&nbsp;"
                        + "</div> <br/>");              
                out.println("</div>");
             }
            out.println("</body>");
            
        /*RequestDispatcher suc = request.getRequestDispatcher("useraccounts.jsp");
        bean bean = new bean();
        
        
        HttpSession sesh = request.getSession();
        
        sesh.setAttribute("user", docu);
        suc.forward(request, response);*/
        
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
            Logger.getLogger(viewNotif.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(viewNotif.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(viewNotif.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(viewNotif.class.getName()).log(Level.SEVERE, null, ex);
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
