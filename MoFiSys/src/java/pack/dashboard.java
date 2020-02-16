/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ellisevangelista
 */
public class dashboard extends HttpServlet {
    
    Connection conn = null;
    public static String func;
    public static String qry;
    ServletConfig config;
    
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
                    conn = 
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
     * @param request dashboard request
     * @param response dashboard response
     * @throws ServletException if a dashboard-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        
            RequestDispatcher suc = request.getRequestDispatcher("main.jsp");
            RequestDispatcher admin_suc = request.getRequestDispatcher("admain.jsp");
            RequestDispatcher insrt = request.getRequestDispatcher("success.jsp");
            RequestDispatcher err = request.getRequestDispatcher("error.jsp");
            RequestDispatcher chang = request.getRequestDispatcher("changePassword.jsp");
            
            bean bean = new bean();
            HttpSession session = request.getSession();
            
        try (PrintWriter out = response.getWriter()) {
            qry = request.getParameter("function");
            HttpSession sesh = request.getSession();
            sesh.setAttribute("qry", qry);
            
            java.util.Date today = new java.util.Date();
            
                if(qry.equals("SELECT")){
                    String username = request.getParameter("uname");
                    String pass = request.getParameter("pass");
                    String admin_uname = request.getParameter("uname");
                    String admin_pass = request.getParameter("pass");
                    String dateToday = bean.dateConvert(today);
                    
                    if(!"".equals(username) || !"".equals(pass)){
                        ResultSet rs_user = bean.getUser(username, pass, conn);
                        ResultSet rs_adminuser = bean.getAdmin(admin_uname, admin_pass, conn);
                        
                            if (!rs_user.next() && !rs_adminuser.next()){
                                session.setAttribute("func", "SELECTe1");
                                err.forward(request, response);
                            }
                            else {
                                if(!rs_adminuser.next()){
                                    String cmt = rs_user.getString("CMT").trim();
                                    session.setAttribute("user", bean.getUser(username, pass, conn));
                                    session.setAttribute("notif", bean.getNotif(username, dateToday, cmt, conn));
                                    Dummy.user = username;
                                    suc.forward(request, response);
                                }
                                else{
                                   String cmt = rs_adminuser.getString("CMT").trim(); 
                                   session.setAttribute("user", bean.getAdmin(admin_uname, admin_pass, conn));
                                   session.setAttribute("notif", bean.getNotif(admin_uname, dateToday, "Administrative", conn));
                                   Dummy.user = username;
                                   admin_suc.forward(request, response); 
                                }
                                    
                            }
                            
                        }
                    }
                    
                
                  /*
                    else{
                        session.setAttribute("func", "SELECTe2");
                        err.forward(request, response);
                    }
                    */
                
                    
                else if(qry.equals("INSERT")){
                    String fname = request.getParameter("fname");
                    String lname = request.getParameter("lname");
                    String cmt = request.getParameter("cmt");
                    String bday = request.getParameter("pass");
                    String uname = request.getParameter("uname");
                    String pass = request.getParameter("pass");
                    String admin_uname = request.getParameter("admin_uname");
                    String admin_pass = request.getParameter("admin_pass");
                    
                    if(!"".equals(admin_uname) || !"".equals(admin_pass)){
                        ResultSet rs_adminuser = bean.getAdmin(admin_uname, admin_pass, conn);
                        
                            if (!rs_adminuser.next()){
                                session.setAttribute("func", "SELECTe1");
                                err.forward(request, response);
                            }
                            else{
                                session.setAttribute("user", bean.getAdmin(admin_uname, admin_pass, conn));
                                    if(!"".equals(fname) && !"".equals(lname)&& !"".equals(cmt) && !"".equals(uname) && !"".equals(pass)){
                                    int i = bean.insertDB(admin_uname, fname, lname, cmt, bday, uname, pass, conn);

                                    if(i > 0){
                                        insrt.forward(request, response);
                                             }
                                    else {
                                        session.setAttribute("func", "INSERTe1");
                                        err.forward(request, response);
                                         }
                                }
                            }
                        }
                }                                  

                    /*
                    else{
                        session.setAttribute("func", "INSERTe2");
                        err.forward(request, response);
                    }
                    */
                    
                    /*
                    else{
                        session.setAttribute("func", "INSERTe2");
                        err.forward(request, response);
                    }
                    */
                                        
                                        
                
                
                else if(qry.equals("UPDATE")){
                    String ChUsername = request.getParameter("ChUsername");
                    ResultSet ch_user = bean.getUser(ChUsername, conn);
                    session.setAttribute("user", bean.getUser(ChUsername, conn));
                                    if(!"".equals(ChUsername)){
                                        int i = bean.forgotPass(ChUsername, conn);
                                    
                                        if(i > 0){
                                            chang.forward(request, response);
                                                 }
                                        else {
                                            session.setAttribute("func", "INSERTe1");
                                            err.forward(request, response);
                                             }
                            }
                }
                         
                        /*
                    if(!"".equals(v1) && !"".equals(v2)){
                        int i = bean.updateDB(a1, v1, a2, v2, conn);

                        if(i > 0){
                            suc.forward(request, response);
                        }else{
                            session.setAttribute("func", "UPDATEe1");
                            err.forward(request, response);
                        }
                    }else{
                        session.setAttribute("func", "UPDATEe2");
                        err.forward(request, response);
                    }
                }
                        
                
                
                else{
                    String a = request.getParameter("attribute");
                    String x = request.getParameter("value");

                    if(!"".equals(x)){
                        int i = bean.deleteDB(a, x, conn);

                        if(i > 0){
                            suc.forward(request, response);
                        }else{
                            session.setAttribute("func", "DELETEe1");
                            err.forward(request, response);
                        }
                    }else{
                        err.forward(request, response);
                        }    
                    }
            } 
                        */
                          
                
                
            }
            
        }
    
        
        
   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request dashboard request
     * @param response dashboard response
     * @throws ServletException if a dashboard-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request dashboard request
     * @param response dashboard response
     * @throws ServletException if a dashboard-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            processRequest(request, response);
            out.println("</body>");
        } catch (SQLException ex) {
            Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the dashboard.
     *
     * @return a String containing dashboard description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
    


