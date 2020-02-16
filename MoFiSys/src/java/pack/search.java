/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

/**
 *
 * @author ellisevangelista
 */
@WebServlet(name = "search", urlPatterns = {"/search"})
public class search extends HttpServlet {

    private String dbURL = "jdbc:mysql://localhost:3306/MoFiSys";
    private String dbUser = "ustfu";
    private String dbPass = "27811821";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "MoFiSys";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "ustfu";
        String password = "27811821";
        Statement st=null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            System.out.println("connected!.....");
            String title = request.getParameter("keyword");
            ArrayList al = null;
            ArrayList pid_list = new ArrayList();
            String query = "SELECT * FROM DocumentsIn where TITLE LIKE '%" + title + "%' OR DEPARTMENT LIKE '%" + title + "%' OR DOC_RCVD LIKE '%" + title + "%' "
                    + "UNION SELECT * FROM DocumentsOut where TITLE LIKE '%" + title + "%' OR DEPARTMENT LIKE '%" + title + "%' OR DOC_ADD LIKE '%" + title + "%' ";
            System.out.println("query " + query);
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) {
                al = new ArrayList();

                al.add(rs.getString(1));
                al.add(rs.getString(2));
                al.add(rs.getString(3));
                al.add(rs.getString(5));
                System.out.println("al :: " + al);
                pid_list.add(al);
            }
            
            request.setAttribute("piList", pid_list);
            request.setAttribute("title", title);
            RequestDispatcher view = request.getRequestDispatcher("searchResults.jsp");
            view.forward(request, response);
            conn.close();
            System.out.println("Disconnected!");
        } catch (Exception e) {
            e.printStackTrace();
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
        processRequest(request, response);
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
            
            
                  
             
            //request.setAttribute("Message", message);
            //getServletContext().getRequestDispatcher("/docu.jsp").forward(request, response);
        
    
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>



    
    private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
    

}