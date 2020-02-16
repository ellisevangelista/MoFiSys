package pack;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Laurenz
 */
@WebServlet(name = "updateDocuments", urlPatterns = {"/updateDocuments"})
public class updateDocuments extends HttpServlet {
    Connection connection;

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
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        
        response.setContentType("text/html;charset=UTF-8");
        String Docno = request.getParameter("docno");
        bean bean = new bean();
        String type = request.getParameter("type");
        if(type.equals("in")){
            String Title = request.getParameter("doc_title");//
            String Department = request.getParameter("doc_dept");//
            //String Doc_Add = request.getParameter("doc_add");
            String Doc_Rcvd = request.getParameter("doc_rcvd");//
            String Doc_Dscrptn = request.getParameter("doc_dscrptn");
            String Sensitivity = request.getParameter("doc_sensitivity");//
            String Doc_Folder = request.getParameter("doc_folder");//
            String Location = request.getParameter("doc_location");//
            //String Doc_File = request.getParameter("doc_file");
            String Doc_Remarks = request.getParameter("doc_remarks");
            String Status = request.getParameter("doc_status");
            String DateReceived = request.getParameter("doc_datercvd");//
            String DateChange = request.getParameter("doc_notifDate");//

            int update = bean.updateDocumentsIn(Title, Department, Doc_Dscrptn, Doc_Rcvd, DateReceived, Sensitivity, Doc_Folder, Location, Doc_Remarks, Docno, Status, DateChange, connection);
            
        }else if(type.equals("out")){
            String Title = request.getParameter("doc_title");//
            String Department = request.getParameter("doc_dept");//
            //String Doc_Add = request.getParameter("doc_add");
            String Doc_Add = request.getParameter("doc_add");//
            String Doc_Dscrptn = request.getParameter("doc_dscrptn");
            String Sensitivity = request.getParameter("doc_sensitivity");//
            String Doc_Folder = request.getParameter("doc_folder");//
            String Location = request.getParameter("doc_location");//
            //String Doc_File = request.getParameter("doc_file");
            String Doc_Remarks = request.getParameter("doc_remarks");
            String Status = request.getParameter("doc_status");
            String DateReleased = request.getParameter("doc_daterelease");//
            String DateChange = request.getParameter("doc_notifDate");//
            
            int update = bean.updateDocumentsOut(Title, Department, Doc_Dscrptn, Doc_Add, DateReleased, Sensitivity, Doc_Folder, Location, Doc_Remarks, Docno, Status, DateChange, connection);
        }
        /*try {
            
            if (row > 0) {
                message = "File uploaded and saved into database";
            }
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            request.setAttribute("Message", message);
        }*/
        
    
        
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(updateDocuments.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(updateDocuments.class.getName()).log(Level.SEVERE, null, ex);
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
