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

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

/**
 *
 * @author ellisevangelista
 */
@WebServlet(name = "insertThisBook", urlPatterns = {"/insertThisBook"})
@MultipartConfig(maxFileSize = 16177215)  
public class insertThisBook extends HttpServlet {
    public String userName;
    private String dbURL = "jdbc:mysql://localhost:3306/MoFiSys";
    private String dbUser = "ustfu";
    private String dbPass = "27811821";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet fileUpload</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet fileUpload at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
         String Callno = request.getParameter("book_callno");
         String Title = request.getParameter("book_title");
         String Author = request.getParameter("book_author");
         String Year = request.getParameter("book_year");
         String Publication = request.getParameter("book_pub");
         String Subject = request.getParameter("book_subj");
         String Book_Dscrptn = request.getParameter("book_dscrptn");
         String Book_Folder = request.getParameter("book_folder");
         String Location = request.getParameter("book_location");
         String Book_Remarks = request.getParameter("book_remarks");
         
        InputStream inputStream = null;
         
        Part filePart = request.getPart("book_file");
        if (filePart != null) {
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());             
            inputStream = filePart.getInputStream();
        }
        
        InputStream isblob = filePart.getInputStream();
        //InputStream isay = filePart.getInputStream();
        String fileName = filePart.getSubmittedFileName();
        
        Connection conn = null;
        String message = null;
         
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            
 
            String sql = "INSERT INTO Books(CALLNO, TITLE, AUTHOR, BOOK_YEAR, PUBLICATION, SUBJECT, BOOK_DSCRPTN, BOOK_FOLDER, LOCATION, BOOK_FILE, BOOK_REMARKS, DATEADDED, BOOK_FILENAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
                  statement.setString(1, Callno);                 
                  statement.setString(2, Title);
                  if (Author != null) {
                    statement.setString(3, Author);
                  }
                  else{
                    statement.setString(3, "");
                  }
                  if (Year != null) {
                    statement.setString(4, Year);
                  }
                  else{
                    statement.setString(4, "");
                  }
                  if (Publication != null) {
                    statement.setString(5, Publication);
                  }
                  else{
                    statement.setString(5, "");
                  }
                  if (Subject != null) {
                  statement.setString(6, Subject);
                  }
                  else{
                    statement.setString(6, "");
                  }
                  if (Book_Dscrptn != null) {
                  statement.setString(7, Book_Dscrptn );
                  }
                  else{
                    statement.setString(7, "");
                  }
                  if (Book_Folder != null) {
                  statement.setString(8, Book_Folder);
                  }
                  else{
                    statement.setString(8, "");
                  }
                  if (Location != null) {
                  statement.setString(9, Location);
                  }
                  else{
                    statement.setString(9, "");
                  }
                  if (Book_Remarks != null) {
                  statement.setString(11, Book_Remarks);
                  }
                  else{
                    statement.setString(11, "");
                  }
                  statement.setTimestamp(12, getCurrentTimeStamp());
             
            if (inputStream != null) {
                statement.setBlob(10, inputStream);
                statement.setString(13, fileName);
            }
            int row = statement.executeUpdate();
            String userNamee = Dummy.user;
            PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
            pstmt2.setString(1, userNamee);
            pstmt2.setString(2, "Insert Book");
            pstmt2.setString(3, Title);
            pstmt2.setTimestamp(4, getCurrentTimeStamp());
            pstmt2.setString(5, "User");
            pstmt2.executeUpdate();
            
            if (row > 0) {
                message = "File uploaded and saved into database";
            }
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            request.setAttribute("Message", message);
            getServletContext().getRequestDispatcher("/docu.jsp").forward(request, response);
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

    
    	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
}


