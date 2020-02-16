/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pack;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
 
/**
 *
 * @author ellisevangelista
 */
@WebServlet(name = "viewFile", urlPatterns = {"/viewFile"})
public class viewFile extends HttpServlet {

 
    // size of byte buffer to send file
    private static final int BUFFER_SIZE = 16384;   
     
    // database connection settings
    private String dbURL = "jdbc:mysql://localhost:3306/MoFiSys";
    private String dbUser = "ustfu";
    private String dbPass = "27811821";

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        
        String title = request.getParameter("title");
        
        // get upload id from URL's parameters
        //int uploadId = Integer.parseInt(request.getParameter("docno"));
         
        Connection conn = null; 
        
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            String sql = "SELECT * FROM documentsin WHERE TITLE = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // gets file name and file blob data
                String fileName = rs.getString("FILENAME");
                Blob blob = rs.getBlob("DOC_FILE");
                InputStream inp = blob.getBinaryStream();
                int fileLength = inp.available();
                
                System.out.println("fileLength = " + fileLength);
                
                ServletContext context = getServletContext();
                
                // sets MIME type for the file download
                String mimeType = context.getMimeType(fileName);
                if (mimeType == null) {        
                    mimeType = "application/octet-stream";
                } 
                
                // set content properties and header attributes for the response
                response.setContentType(mimeType);
                response.setContentLength(fileLength);
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", fileName);
                response.setHeader(headerKey, headerValue);
                
                // writes the file to the client
                OutputStream outStream = response.getOutputStream();
                
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                 
                while ((bytesRead = inp.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                 
                inp.close();
                outStream.close(); 
                
            }else {
                // no file found
                response.getWriter().print("File not found for the title: " + title);  
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.getWriter().print("SQL Error: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            response.getWriter().print("IO Error: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }          
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

    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }
    
    

}