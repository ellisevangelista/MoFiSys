/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



//new imports

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;

/**
 *
 * @author YakuKun
 */
@WebServlet(name = "genReportServ", urlPatterns = {"/genReportServ"})
public class genReportServ extends HttpServlet {
ServletConfig config;
    Connection connection = null;
    
    

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
        String kind = request.getParameter("doc_type");
        String dateFrom = request.getParameter("gen_from");
        String dateTo = request.getParameter("gen_to");
        String stat = request.getParameter("stat_type");
        
        System.out.println("DETAILS!!!!!: "+kind + " " + dateFrom + " " + dateTo);
        
        if(kind.equals("Incoming")){
            if(stat.equals("Pending")||stat.equals("Settled")){
            genrepPDFInPS(kind, dateFrom, dateTo, stat);
            }
            else if(stat.equals("Both")){
            genrepPDFInBoth(kind, dateFrom, dateTo, stat);
            }
        }
        else if(kind.equals("Outgoing")){
            if(stat.equals("Pending")||stat.equals("Settled")){
            genrepPDFOutPS(kind, dateFrom, dateTo, stat);
            }
            else if(stat.equals("Both")){
            genrepPDFOutBoth(kind, dateFrom, dateTo, stat);
            }
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
        Logger.getLogger(genReportServ.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(genReportServ.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(genReportServ.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(genReportServ.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private  java.sql.Timestamp getCurrentTimeStamp() {
      java.util.Date today = new java.util.Date();
      return new java.sql.Timestamp(today.getTime());
    }

    
    //PDF START
    
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ellisevangelista
 */
public void genrepPDFInPS (String kind, String dateFrom, String dateTo, String stat) throws SQLException, FileNotFoundException {
       Document document = new Document();
       Font header = new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD);
       Font intro1 = new Font(Font.FontFamily.HELVETICA, 12,Font.ITALIC);
       Font intro2 = new Font(Font.FontFamily.HELVETICA, 12,Font.NORMAL);
       Font space = new Font(Font.FontFamily.HELVETICA, 4,Font.NORMAL);
       Font content = new Font(Font.FontFamily.HELVETICA, 11,Font.NORMAL);
       
      try
      {
          
        int countOld = 1;
        int countNew = 1;
        ArrayList<String> docNo = new ArrayList<String>();
        ArrayList<String> title = new ArrayList<String>();
        ArrayList<String> docType= new ArrayList<String>();
        ArrayList<String> dateRec = new ArrayList<String>();
        ArrayList<String> recBy = new ArrayList<String>();
        //start
        
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
            
      Statement pstmt = conn.createStatement();
      Statement pstmt6 = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM counter WHERE countPrim = 'counter'");
        ResultSet rs2 = pstmt6.executeQuery("select * from documentsin where DATERECEIVED between '"+dateFrom+" 00:00:00' and '"+dateTo+" 23:59:00' order by DATERECEIVED;");
        //ResultSet rs2 = pstmt6.executeQuery("select * from documentsin");
    

            while(rs.next()){
                countOld = rs.getInt("countVal");
            }
            
            System.out.println("Counter VALUE = " + countOld);
            
            countNew = countOld+1;
            
            
            while(rs2.next()){
                if(rs2.getString("STATUS").equals(stat)){
                docNo.add(rs2.getString("DOCNO").trim());
                title.add(rs2.getString("TITLE").trim());
                docType.add(rs2.getString("FILETYPE").trim());
                dateRec.add(rs2.getString("DATERECEIVED").trim());
                recBy.add(rs2.getString("DOC_RCVD").trim());
                }
                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
      PreparedStatement pstmt2 = conn.prepareStatement("UPDATE counter SET countVal = ? WHERE countPrim = 'counter'");
      
      pstmt2.setInt(1, countNew);
      pstmt2.executeUpdate();  
      
      userName = Dummy.user;
      PreparedStatement ac = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
      ac.setString(1, userName);
      ac.setString(2, "Generate Summary Report");
      ac.setString(3, "Report #" + countOld);
      ac.setTimestamp(4, getCurrentTimeStamp());
      ac.setString(5, "User");
      ac.executeUpdate();
          
          
        String userHomeFolder = System.getProperty("user.home")+ "/Desktop/SummaryReports";
        File file = new File(userHomeFolder, "SumRep - "+countOld+" of "+dateFrom+" to "+dateTo+".pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
 
        float[] columnWidths = {1f, 4.2f, 1.5f, 1.4f, 2.9f};
        table.setWidths(columnWidths);
 
        Paragraph preface = new Paragraph();
        document.add(new Paragraph("Summary Report",header));
        document.add(new Paragraph("__________________________________________________________________________________________________________________________________________",space));
        document.add(new Paragraph("University of Santo Tomas Faculty Union",intro1));
        document.add(new Paragraph("Monitoring and Filing Management System",intro1));
        document.add(new Paragraph("__________________________________________________________________________________________________________________________________________",space));
        document.add(new Paragraph("Report No:        "+countOld,intro2));
        document.add(new Paragraph("Report Type:     "+kind +" - "+stat,intro2));
        document.add(new Paragraph("From:                "+dateFrom,intro2));
        document.add(new Paragraph("To:                    "+dateTo,intro2));
        
        
        PdfPCell cell1 = new PdfPCell(new Paragraph("Doc No.",content));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
 
        PdfPCell cell2 = new PdfPCell(new Paragraph("Title",content));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        PdfPCell cell3 = new PdfPCell(new Paragraph("Document Type",content));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell4 = new PdfPCell(new Paragraph("Date Received",content));
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell5 = new PdfPCell(new Paragraph("Received By",content));
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        
        for(int i=0; i<docNo.size(); i++){
            
            PdfPCell cellDocNo = new PdfPCell(new Paragraph(docNo.get(i),content));
            PdfPCell cellTitle = new PdfPCell(new Paragraph(title.get(i),content));
            PdfPCell cellDocType = new PdfPCell(new Paragraph(docType.get(i),content));
            PdfPCell cellDateRec = new PdfPCell(new Paragraph(dateRec.get(i),content));
            PdfPCell cellRecBy = new PdfPCell(new Paragraph(recBy.get(i),content));
            
            table.addCell(cellDocNo);
            table.addCell(cellTitle);
            table.addCell(cellDocType);
            table.addCell(cellDateRec);
            table.addCell(cellRecBy);
        }
        
        
 
        document.add(table);

        document.close();
        writer.close();
      } catch (DocumentException e)
      {
         e.printStackTrace();
      }
    
    
}

//in both
public void genrepPDFInBoth (String kind, String dateFrom, String dateTo, String stat) throws SQLException, FileNotFoundException {
       Document document = new Document();
       Font header = new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD);
       Font intro1 = new Font(Font.FontFamily.HELVETICA, 12,Font.ITALIC);
       Font intro2 = new Font(Font.FontFamily.HELVETICA, 12,Font.NORMAL);
       Font space = new Font(Font.FontFamily.HELVETICA, 4,Font.NORMAL);
       Font content = new Font(Font.FontFamily.HELVETICA, 11,Font.NORMAL);
       
      try
      {
          
        int countOld = 1;
        int countNew = 1;
        ArrayList<String> docNo = new ArrayList<String>();
        ArrayList<String> title = new ArrayList<String>();
        ArrayList<String> docType= new ArrayList<String>();
        ArrayList<String> dateRec = new ArrayList<String>();
        ArrayList<String> recBy = new ArrayList<String>();
        ArrayList<String> status = new ArrayList<String>();
        //start
        
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
            
      Statement pstmt = conn.createStatement();
      Statement pstmt6 = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM counter WHERE countPrim = 'counter'");
        ResultSet rs2 = pstmt6.executeQuery("select * from documentsin where DATERECEIVED between '"+dateFrom+" 00:00:00' and '"+dateTo+" 23:59:00' order by DATERECEIVED;");
        //ResultSet rs2 = pstmt6.executeQuery("select * from documentsin");
    

            while(rs.next()){
                countOld = rs.getInt("countVal");
            }
            
            System.out.println("Counter VALUE = " + countOld);
            
            countNew = countOld+1;
            
            
            while(rs2.next()){
                docNo.add(rs2.getString("DOCNO").trim());
                title.add(rs2.getString("TITLE").trim());
                docType.add(rs2.getString("FILETYPE").trim());
                dateRec.add(rs2.getString("DATERECEIVED").trim());
                recBy.add(rs2.getString("DOC_RCVD").trim());
                status.add(rs2.getString("STATUS").trim());
                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
      PreparedStatement pstmt2 = conn.prepareStatement("UPDATE counter SET countVal = ? WHERE countPrim = 'counter'");
      
      pstmt2.setInt(1, countNew);
      pstmt2.executeUpdate();
      
      userName = Dummy.user;
      PreparedStatement ac = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
      ac.setString(1, userName);
      ac.setString(2, "Generate Summary Report");
      ac.setString(3, "Report #" + countOld);
      ac.setTimestamp(4, getCurrentTimeStamp());
      ac.setString(5, "User");
      ac.executeUpdate();
          
          
        String userHomeFolder = System.getProperty("user.home")+ "/Desktop/SummaryReports";
        File file = new File(userHomeFolder, "SumRep - "+countOld+" of "+dateFrom+" to "+dateTo+".pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
 
        float[] columnWidths = {1f, 4f, 1.2f, 1.4f, 2.4f, 1f};
        table.setWidths(columnWidths);
 
        Paragraph preface = new Paragraph();
        document.add(new Paragraph("Summary Report",header));
        document.add(new Paragraph("__________________________________________________________________________________________________________________________________________",space));
        document.add(new Paragraph("University of Santo Tomas Faculty Union",intro1));
        document.add(new Paragraph("Monitoring and Filing Management System",intro1));
        document.add(new Paragraph("__________________________________________________________________________________________________________________________________________",space));
        document.add(new Paragraph("Report No:        "+countOld,intro2));
        document.add(new Paragraph("Report Type:     "+kind +" - "+stat,intro2));
        document.add(new Paragraph("From:                "+dateFrom,intro2));
        document.add(new Paragraph("To:                    "+dateTo,intro2));
        
        
        PdfPCell cell1 = new PdfPCell(new Paragraph("Doc No.",content));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
 
        PdfPCell cell2 = new PdfPCell(new Paragraph("Title",content));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        PdfPCell cell3 = new PdfPCell(new Paragraph("Document Type",content));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell4 = new PdfPCell(new Paragraph("Date Received",content));
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell5 = new PdfPCell(new Paragraph("Received By",content));
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell6 = new PdfPCell(new Paragraph("Status",content));
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        for(int i=0; i<docNo.size(); i++){
            
            PdfPCell cellDocNo = new PdfPCell(new Paragraph(docNo.get(i),content));
            PdfPCell cellTitle = new PdfPCell(new Paragraph(title.get(i),content));
            PdfPCell cellDocType = new PdfPCell(new Paragraph(docType.get(i),content));
            PdfPCell cellDateRec = new PdfPCell(new Paragraph(dateRec.get(i),content));
            PdfPCell cellRecBy = new PdfPCell(new Paragraph(recBy.get(i),content));
            PdfPCell cellStatus = new PdfPCell(new Paragraph(status.get(i),content));
            
            table.addCell(cellDocNo);
            table.addCell(cellTitle);
            table.addCell(cellDocType);
            table.addCell(cellDateRec);
            table.addCell(cellRecBy);
            table.addCell(cellStatus);
        }
        
        
 
        document.add(table);

        document.close();
        writer.close();
      } catch (DocumentException e)
      {
         e.printStackTrace();
      }
    
    
}

//in both
//pdf out

public void genrepPDFOutPS (String kind, String dateFrom, String dateTo, String stat) throws SQLException, FileNotFoundException {
       Document document = new Document();
       Font header = new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD);
       Font intro1 = new Font(Font.FontFamily.HELVETICA, 12,Font.ITALIC);
       Font intro2 = new Font(Font.FontFamily.HELVETICA, 12,Font.NORMAL);
       Font space = new Font(Font.FontFamily.HELVETICA, 4,Font.NORMAL);
       Font content = new Font(Font.FontFamily.HELVETICA, 11,Font.NORMAL);
       
      try
      {
          
        int countOld = 1;
        int countNew = 1;
        ArrayList<String> docNo = new ArrayList<String>();
        ArrayList<String> title = new ArrayList<String>();
        ArrayList<String> docType= new ArrayList<String>();
        ArrayList<String> dateRel = new ArrayList<String>();
        ArrayList<String> addTo = new ArrayList<String>();
        //start
        
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
            
            Statement pstmt = conn.createStatement();
            Statement pstmt6 = conn.createStatement();
            ResultSet rs = pstmt.executeQuery("SELECT * FROM counter WHERE countPrim = 'counter'");
            ResultSet rs2 = pstmt6.executeQuery("select * from documentsout where DATERELEASED between '"+dateFrom+" 00:00:00' and '"+dateTo+" 23:59:00'" +"order by DATERELEASED");
            //ResultSet rs2 = pstmt6.executeQuery("select * from documentsout");
            
            while(rs.next()){
                countOld = rs.getInt("countVal");
            }
            
            System.out.println("Counter VALUE = " + countOld);
            
            countNew = countOld+1;
            
            
            while(rs2.next()){
                if(rs2.getString("STATUS").equals(stat)){
                docNo.add(rs2.getString("DOCNO").trim());
                title.add(rs2.getString("TITLE").trim());
                docType.add(rs2.getString("FILETYPE").trim());
                dateRel.add(rs2.getString("DATERELEASED").trim());
                addTo.add(rs2.getString("DOC_ADD").trim());
                }
            }  
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
      PreparedStatement pstmt2 = conn.prepareStatement("UPDATE counter SET countVal = ? WHERE countPrim = 'counter'");
      
      pstmt2.setInt(1, countNew);
      pstmt2.executeUpdate();  
          
      userName = Dummy.user;
      PreparedStatement ac = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
      ac.setString(1, userName);
      ac.setString(2, "Generate Summary Report");
      ac.setString(3, "Report #" + countOld);
      ac.setTimestamp(4, getCurrentTimeStamp());
      ac.setString(5, "User");
      ac.executeUpdate();
          
        String userHomeFolder = System.getProperty("user.home")+ "/Desktop/SummaryReports";
        File file = new File(userHomeFolder, "SumRep - "+countOld+" of "+dateFrom+" to "+dateTo+".pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
 
        float[] columnWidths = {1f, 4.2f, 1.5f, 1.4f, 2.9f};
        table.setWidths(columnWidths);
 
        Paragraph preface = new Paragraph();
        document.add(new Paragraph("Summary Report",header));
        document.add(new Paragraph("__________________________________________________________________________________________________________________________________________",space));
        document.add(new Paragraph("University of Santo Tomas Faculty Union",intro1));
        document.add(new Paragraph("Monitoring and Filing Management System",intro1));
        document.add(new Paragraph("__________________________________________________________________________________________________________________________________________",space));
        document.add(new Paragraph("Report No:        "+countOld,intro2));
        document.add(new Paragraph("Report Type:     "+kind+" - "+stat,intro2));
        document.add(new Paragraph("From:                "+dateFrom,intro2));
        document.add(new Paragraph("To:                    "+dateTo,intro2));
        
        
        PdfPCell cell1 = new PdfPCell(new Paragraph("Doc No.",content));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
 
        PdfPCell cell2 = new PdfPCell(new Paragraph("Title",content));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        PdfPCell cell3 = new PdfPCell(new Paragraph("Document Type",content));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell4 = new PdfPCell(new Paragraph("Date Released",content));
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell5 = new PdfPCell(new Paragraph("Addressed To",content));
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        
        for(int i=0; i<docNo.size(); i++){
            
            PdfPCell cellDocNo = new PdfPCell(new Paragraph(docNo.get(i),content));
            PdfPCell cellTitle = new PdfPCell(new Paragraph(title.get(i),content));
            PdfPCell cellDocType = new PdfPCell(new Paragraph(docType.get(i),content));
            PdfPCell cellDateRel = new PdfPCell(new Paragraph(dateRel.get(i),content));
            PdfPCell cellAddTo = new PdfPCell(new Paragraph(addTo.get(i),content));
            
            table.addCell(cellDocNo);
            table.addCell(cellTitle);
            table.addCell(cellDocType);
            table.addCell(cellDateRel);
            table.addCell(cellAddTo);
        }
        
        
 
        document.add(table);

        document.close();
        writer.close();
      } catch (DocumentException e)
      {
         e.printStackTrace();
      }
    
    
}



//pdf out end

public void genrepPDFOutBoth (String kind, String dateFrom, String dateTo, String stat) throws SQLException, FileNotFoundException {
       Document document = new Document();
       Font header = new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD);
       Font intro1 = new Font(Font.FontFamily.HELVETICA, 12,Font.ITALIC);
       Font intro2 = new Font(Font.FontFamily.HELVETICA, 12,Font.NORMAL);
       Font space = new Font(Font.FontFamily.HELVETICA, 4,Font.NORMAL);
       Font content = new Font(Font.FontFamily.HELVETICA, 11,Font.NORMAL);
       
      try
      {
          
        int countOld = 1;
        int countNew = 1;
        ArrayList<String> docNo = new ArrayList<String>();
        ArrayList<String> title = new ArrayList<String>();
        ArrayList<String> docType= new ArrayList<String>();
        ArrayList<String> dateRel = new ArrayList<String>();
        ArrayList<String> addTo = new ArrayList<String>();
        ArrayList<String> status = new ArrayList<String>();
        //start
        
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
            
            Statement pstmt = conn.createStatement();
            Statement pstmt6 = conn.createStatement();
            ResultSet rs = pstmt.executeQuery("SELECT * FROM counter WHERE countPrim = 'counter'");
            ResultSet rs2 = pstmt6.executeQuery("select * from documentsout where DATERELEASED between '"+dateFrom+" 00:00:00' and '"+dateTo+" 23:59:00'" +"order by DATERELEASED");
            //ResultSet rs2 = pstmt6.executeQuery("select * from documentsout");
            
            while(rs.next()){
                countOld = rs.getInt("countVal");
            }
            
            System.out.println("Counter VALUE = " + countOld);
            
            countNew = countOld+1;
            
            
            while(rs2.next()){
                docNo.add(rs2.getString("DOCNO").trim());
                title.add(rs2.getString("TITLE").trim());
                docType.add(rs2.getString("FILETYPE").trim());
                dateRel.add(rs2.getString("DATERELEASED").trim());
                addTo.add(rs2.getString("DOC_ADD").trim());
                status.add(rs2.getString("STATUS").trim());
            }  
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
      PreparedStatement pstmt2 = conn.prepareStatement("UPDATE counter SET countVal = ? WHERE countPrim = 'counter'");
      
      pstmt2.setInt(1, countNew);
      pstmt2.executeUpdate();  
          
          
        String userHomeFolder = System.getProperty("user.home")+ "/Desktop/SummaryReports";
        File file = new File(userHomeFolder, "SumRep - "+countOld+" of "+dateFrom+" to "+dateTo+".pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
 
        float[] columnWidths = {1f, 4f, 1.2f, 1.4f, 2.4f, 1f};
        table.setWidths(columnWidths);
 
        Paragraph preface = new Paragraph();
        document.add(new Paragraph("Summary Report",header));
        document.add(new Paragraph("__________________________________________________________________________________________________________________________________________",space));
        document.add(new Paragraph("University of Santo Tomas Faculty Union",intro1));
        document.add(new Paragraph("Monitoring and Filing Management System",intro1));
        document.add(new Paragraph("__________________________________________________________________________________________________________________________________________",space));
        document.add(new Paragraph("Report No:        "+countOld,intro2));
        document.add(new Paragraph("Report Type:     "+kind+" - "+stat,intro2));
        document.add(new Paragraph("From:                "+dateFrom,intro2));
        document.add(new Paragraph("To:                    "+dateTo,intro2));
        
        
        PdfPCell cell1 = new PdfPCell(new Paragraph("Doc No.",content));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
 
        PdfPCell cell2 = new PdfPCell(new Paragraph("Title",content));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        PdfPCell cell3 = new PdfPCell(new Paragraph("Document Type",content));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell4 = new PdfPCell(new Paragraph("Date Released",content));
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell5 = new PdfPCell(new Paragraph("Addressed To",content));
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell6 = new PdfPCell(new Paragraph("Status",content));
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        
        for(int i=0; i<docNo.size(); i++){
            
            PdfPCell cellDocNo = new PdfPCell(new Paragraph(docNo.get(i),content));
            PdfPCell cellTitle = new PdfPCell(new Paragraph(title.get(i),content));
            PdfPCell cellDocType = new PdfPCell(new Paragraph(docType.get(i),content));
            PdfPCell cellDateRel = new PdfPCell(new Paragraph(dateRel.get(i),content));
            PdfPCell cellAddTo = new PdfPCell(new Paragraph(addTo.get(i),content));
            PdfPCell cellStatus = new PdfPCell(new Paragraph(status.get(i),content));
            
            table.addCell(cellDocNo);
            table.addCell(cellTitle);
            table.addCell(cellDocType);
            table.addCell(cellDateRel);
            table.addCell(cellAddTo);
            table.addCell(cellStatus);
        }
        
        
 
        document.add(table);

        document.close();
        writer.close();
      } catch (DocumentException e)
      {
         e.printStackTrace();
      }
    
    
}

//outboth



//outboth end

    //PDF END
    
        }
    




