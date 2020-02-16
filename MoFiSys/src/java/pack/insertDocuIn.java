/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

/**
 *
 * @author ellisevangelista
 */
@WebServlet(name = "insertDocuIn", urlPatterns = {"/insertDocuIn"})
@MultipartConfig(maxFileSize = 27177215)  
public class insertDocuIn extends HttpServlet {
    public String userName;
    private String dbURL = "jdbc:mysql://localhost:3306/MoFiSys";
    private String dbUser = "ustfu";
    private String dbPass = "27811821";
    /*private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 200 * 1024;
    private int maxMemSize = 254 * 1024;
    private File file ;*/
    
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
        
         String Title = request.getParameter("doc_title");
         String Department = request.getParameter("doc_dept");
         //String Doc_Add = request.getParameter("doc_add");
         String Doc_Rcvd = request.getParameter("doc_rcvd");
         String Doc_Dscrptn = request.getParameter("doc_dscrptn");
         String FileType = "Incoming";
         String DateReceived = request.getParameter("doc_datercvd");
         String Status = request.getParameter("doc_status");
         String Sensitivity = request.getParameter("doc_sensitivity");
         String Doc_Folder = request.getParameter("doc_folder");
         String Location = request.getParameter("doc_location");
         //String Doc_File = request.getParameter("doc_file");
         String Doc_Remarks = request.getParameter("doc_remarks");
         String NotifDate = request.getParameter("doc_notifDate");
         //String DateDue = request.getParameter("doc_datedue");
         String Username = request.getParameter("user_name");
         
         //String DateReleased = request.getParameter("doc_daterelease");
         //String FILENAME = "";
        
        InputStream inputStream = null;
         
        Part filePart = request.getPart("doc_file");

        if (filePart != null) {
            System.out.println(filePart.getName());
            System.out.println(filePart.getSubmittedFileName());
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
            
            //String fileName_split[] = fileName.split(".");
            
            String sql = "INSERT INTO DocumentsIn(TITLE, DEPARTMENT, DOC_DSCRPTN, FILETYPE, DOC_RCVD, DATERECEIVED, STATUS, SENSITIVITY, DOC_FOLDER, LOCATION, DOC_FILE, FILENAME, DOC_REMARKS, DATEADDED, USERNAME, NOTIFDATE, NOTIFSTAT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
                  statement.setString(1, Title);                 
                  statement.setString(2, Department);
                  //String fileupload = request.getParameter("doc_file");
                  if (Doc_Dscrptn != null) {
                    statement.setString(3, Doc_Dscrptn);
                  }
                  else{
                    statement.setString(3, "");
                  }
                  //
                  statement.setString(4, FileType);
                  //
                  if (Doc_Rcvd != null) {
                    statement.setString(5, Doc_Rcvd);
                  }
                  else{
                    statement.setString(5, "");
                  }
                  if (DateReceived != null) {
                  statement.setString(6, DateReceived);
                  }
                  else{
                    statement.setString(6, "");
                  }
                  
                  //
                  statement.setString(7, Status);
                  //
                  if (Sensitivity != null) {
                  statement.setString(8, Sensitivity);
                  }
                  else{
                    statement.setString(8, "");
                  }
                  if (Doc_Folder != null) {
                  statement.setString(9, Doc_Folder);
                  }
                  else{
                    statement.setString(9, "");
                  }
                  if (Location != null) {
                  statement.setString(10, Location);
                  }
                  else{
                    statement.setString(10, "");
                  }
                  //statement.setString(15, Doc_File);
                  if (Doc_Remarks != null) {
                  statement.setString(13, Doc_Remarks);
                  }
                  else{
                    statement.setString(13, "");
                  }
                  statement.setTimestamp(14, getCurrentTimeStamp());
             
            if (inputStream != null) {
                statement.setBlob(11, inputStream);
                statement.setString(12, fileName);
            }
            /*if (Filename!= null){
            statement.setString(12, Filename);
            }
            else{
            statement.setString(12, "");
            }*/
            statement.setString(15, Username);
            if (NotifDate!= null) {
            statement.setString(16, NotifDate);
            }
            else{
                statement.setString(16, toDate());
                }
            if (Status.equals("Pending")){
            statement.setString(17, "1");
            }
            else{
            statement.setString(17, "0");
            }
            
            int row = statement.executeUpdate();
            
            String userNamee = Dummy.user;
            
            PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
            pstmt2.setString(1, userNamee);
            pstmt2.setString(2, "Insert Incoming Document");
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
        
        private String toDate() {
        long timestamp = 0;
        Date date = new Date (timestamp * 1000);
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }
        
}


