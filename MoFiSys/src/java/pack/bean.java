package pack;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ellisevangelista
 */
public class bean {
    public String userName;
     public bean(){
         userName = Dummy.user;
     }
     private  java.sql.Timestamp getCurrentTimeStamp() {
      java.util.Date today = new java.util.Date();
      return new java.sql.Timestamp(today.getTime());
    }
    public static ResultSet getUser(String username, String pword, Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE USERNAME = ? AND PASS = ?");
        
      pstmt.setString(1, username);
      pstmt.setString(2, pword);
      ResultSet rs = pstmt.executeQuery();
      
      return rs;
    }
    
    public static ResultSet getUser(String username, Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE USERNAME = ?");
        
      pstmt.setString(1, username);
      ResultSet rs = pstmt.executeQuery();
      
      return rs;
    }
    
    public static ResultSet getAdmin(String username, String pword, Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE USERNAME = ? AND PASS = ? AND CMT = ?");
        
      pstmt.setString(1, username);
      pstmt.setString(2, pword);
      pstmt.setString(3, "Administrative");
      ResultSet rs = pstmt.executeQuery();
      
      return rs;
    }
    
    public static ResultSet getBooks(String accountno, Connection conn) throws IOException, SQLException, ClassNotFoundException {      
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Books WHERE ACCOUNTNO = ?");
        
      pstmt.setString(1, accountno);
      ResultSet rs = pstmt.executeQuery();
      
      return rs;
    }
    
    
    public static ResultSet getDocumentsIn (String docno, Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM DocumentsIn WHERE DOCNO = ?");
        
      pstmt.setString(1, docno);
      ResultSet rs = pstmt.executeQuery();
      
      return rs;
    }
    
    public static ResultSet getDocumentsOut (String docno, Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM DocumentsOut WHERE DOCNO = ?");
        
      pstmt.setString(1, docno);
      ResultSet rs = pstmt.executeQuery();
      
      return rs;
    }
    
    public static ResultSet unameDB(String username, Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE USERNAME" + " = ?");
        
      pstmt.setString(1, username);
      ResultSet rs = pstmt.executeQuery();
      
      return rs;
    }
    
   public static ResultSet passDB(String pass, Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE PASS" + " = ?");
        
      pstmt.setString(1, pass);
      ResultSet rs = pstmt.executeQuery();
      
      return rs;
    }
    
    public  int updateUser(String fname, String lname, String cmt, String pass, String uname, Connection conn) throws IOException, SQLException, ClassNotFoundException{
      PreparedStatement pstmt = conn.prepareStatement("UPDATE Users SET FNAME = ?, LNAME = ?, CMT = ?, PASS = ? WHERE USERNAME = ?");
      
      pstmt.setString(1, fname);
      pstmt.setString(2, lname);
      pstmt.setString(3, cmt);
      pstmt.setString(4, pass);
      pstmt.setString(5, uname);
      int i = pstmt.executeUpdate();
      
      PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
      pstmt2.setString(1, userName);
      pstmt2.setString(2, "Update User Details");
      pstmt2.setString(3, uname);
      pstmt2.setTimestamp(4, getCurrentTimeStamp());
      pstmt2.setString(5, "Administrative");
      pstmt2.executeUpdate();
      
      return i;
    }
    
    public static int updateNonAdmin(String pass, String uname, Connection conn) throws IOException, SQLException, ClassNotFoundException{
      PreparedStatement pstmt = conn.prepareStatement("UPDATE Users SET PASS = ? WHERE USERNAME = ?");
      
      pstmt.setString(1, pass);
      pstmt.setString(2, uname);
      int i = pstmt.executeUpdate();
      
      return i;
    }
    
    public static int forgotPass(String username, Connection conn) throws IOException, SQLException, ClassNotFoundException{
      PreparedStatement pstmt = conn.prepareStatement("UPDATE Users SET NOTIFSTAT = '1' WHERE USERNAME = ?");
      
      pstmt.setString(1, username);
      int i = pstmt.executeUpdate();
      
      return i;
    }
    
    public static int changeForgot(String pass, String uname, Connection conn) throws IOException, SQLException, ClassNotFoundException{
      PreparedStatement pstmt = conn.prepareStatement("UPDATE Users SET PASS = BDAY, NOTIFSTAT = '0' WHERE USERNAME = ?");
      
      pstmt.setString(1, uname);
      int i = pstmt.executeUpdate();
      
      return i;
    }
    
    public  int insertDB(String admin_uname, String fname, String lname, String cmt, String bday, String uname, String pass, Connection conn) throws IOException, SQLException, ClassNotFoundException{
      PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Users(FNAME, LNAME, CMT, BDAY, USERNAME, PASS, NOTIFSTAT) VALUES (?, ?, ?, ?, ?, ?, ?)");
      
      pstmt.setString(1, fname);
      pstmt.setString(2, lname);
      pstmt.setString(3, cmt);
      pstmt.setString(4, bday);
      pstmt.setString(5, uname);
      pstmt.setString(6, pass);
      pstmt.setString(7, "0");
    
      int i = pstmt.executeUpdate();
      
      PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
      pstmt2.setString(1, admin_uname);
      pstmt2.setString(2, "Created Account");
      pstmt2.setString(3, uname);
      pstmt2.setTimestamp(4, getCurrentTimeStamp());
      pstmt2.setString(5, cmt);
      pstmt2.executeUpdate();
      return i;
    }   
    
    public static ResultSet viewUsers(Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM Users ORDER BY LNAME");
      
      return rs;
    }
    
    public static ResultSet viewDocumentsIn(Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM DocumentsIn");
      
      return rs;
    }
    
    public static ResultSet viewDocumentsIn(Connection conn, int pg ,String cmt) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs;
      if (cmt.equals("Administrative")){  
        rs = pstmt.executeQuery("SELECT * FROM DocumentsIn ORDER BY docno DESC LIMIT 5 OFFSET " + (pg-1)*5);
      }
      else {
        rs = pstmt.executeQuery("SELECT * FROM DocumentsIn ORDER BY docno DESC LIMIT 5 OFFSET " + (pg-1)*5 + " AND (sensitivity = 'None' OR sensitivity = 'Low')");
      }
      return rs;
    }
    
    public static ResultSet viewArcDocumentsIn(Connection conn, int pg) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM ArchivedDocumentsIn ORDER BY docno DESC LIMIT 5 OFFSET " + (pg-1)*5);
      
      return rs;
    }
    
    public static ResultSet viewDocumentsOut(Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM DocumentsOut");
      
      return rs;
    }
    
    public static ResultSet viewDocumentsOut(Connection conn, int pg, String cmt) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs;
      if (cmt.equals("Administrative")){  
        rs = pstmt.executeQuery("SELECT * FROM DocumentsOut ORDER BY docno DESC LIMIT 5 OFFSET " + (pg-1)*5);
      }
      else {
        rs = pstmt.executeQuery("SELECT * FROM DocumentsOut ORDER BY docno DESC LIMIT 5 OFFSET " + (pg-1)*5 + " AND (sensitivity = 'None' OR sensitivity = 'Low')");
      }
      return rs;
    }
    
    public static ResultSet viewArcDocumentsOut(Connection conn, int pg) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM ArchivedDocumentsOut ORDER BY docno DESC LIMIT 5 OFFSET " + (pg-1)*5);
      return rs;
    }
    
    public static ResultSet viewForgot(Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM Users WHERE NOTIFSTAT ='1'");
      
      return rs;
    }
    
    public static ResultSet viewBooks(Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM Books ORDER BY ACCOUNTNO");
      
      return rs;
    }
    
    public static ResultSet viewBooks(Connection conn, int pg) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM Books ORDER BY ACCOUNTNO DESC LIMIT 10 OFFSET " + (pg-1)*10);
      
      return rs;
    }
    
    public static ResultSet viewArcBooks(Connection conn, int pg) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM ArchivedBooks ORDER BY ACCOUNTNO DESC LIMIT 5 OFFSET " + (pg-1)*5);
      
      return rs;
    }
    
    
    public static ResultSet search(String keyword, Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Documents WHERE TITLE like" + " = ?");     
      
      pstmt.setString(1, keyword);    
      ResultSet rs = pstmt.executeQuery();
      
      return rs;
    }
    
    public static ResultSet adminTrace(Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM useractivities ORDER BY DATEADDED DESC");
      
      return rs;
    }
    
    public static ResultSet userTrace(Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM useractivities WHERE CMT != 'Administrative' ORDER BY DATEADDED DESC");
      
      return rs;
    }
    
    public static ResultSet userTrace(Connection conn, int pg) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM useractivities ORDER BY DATEADDED DESC LIMIT 15 OFFSET " + (pg-1)*15);
      
      return rs;
    }
    
    public static ResultSet userTrace(Connection conn, int pg, String keyword) throws IOException, SQLException, ClassNotFoundException{      
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM useractivities WHERE USERNAME = ? ORDER BY DATEADDED DESC LIMIT 15 OFFSET " + (pg-1)*15);
      pstmt.setString(1, keyword);
      ResultSet rs = pstmt.executeQuery();
      
      return rs;
    }
    
    public  int deleteUser(String username, Connection conn) throws IOException, SQLException, ClassNotFoundException{
      PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Users WHERE USERNAME = ?");
      
      pstmt.setString(1, username);
      int i = pstmt.executeUpdate();
      
      PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
      pstmt2.setString(1, userName);
      pstmt2.setString(2, "Delete User");
      pstmt2.setString(3, username);
      pstmt2.setTimestamp(4, getCurrentTimeStamp());
      pstmt2.setString(5, "Administrative");
      pstmt2.executeUpdate();
      
      return i;
    }
    
    public static int getLimitIn(Connection conn) throws IOException, SQLException, ClassNotFoundException{
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT count(*) AS highest FROM documentsin");
      if (rs.next()){
        int i = rs.getInt("highest");
        return i;
      }
      return 0;
    }
    
    public static int getLimitOut(Connection conn) throws IOException, SQLException, ClassNotFoundException{
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT count(*) AS highest FROM documentsout");
      if (rs.next()){
        int i = rs.getInt("highest");
        return i;
      }
      return 0;
    }
    
    public static int getLimit(Connection conn) throws IOException, SQLException, ClassNotFoundException{
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT count(*) AS highest FROM books");
      if (rs.next()){
        int i = rs.getInt("highest");
        return i;
      }
      return 0;
    }
    
    public static int getALimitIn(Connection conn) throws IOException, SQLException, ClassNotFoundException{
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT count(*) AS highest FROM archiveddocumentsin");
      if (rs.next()){
        int i = rs.getInt("highest");
        return i;
      }
      return 0;
    }
    
    public static int getALimitOut(Connection conn) throws IOException, SQLException, ClassNotFoundException{
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT count(*) AS highest FROM archiveddocumentsout");
      if (rs.next()){
        int i = rs.getInt("highest");
        return i;
      }
      return 0;
    }
    
    public static int getALimit(Connection conn) throws IOException, SQLException, ClassNotFoundException{
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT count(*) AS highest FROM archivedbooks");
      if (rs.next()){
        int i = rs.getInt("highest");
        return i;
      }
      return 0;
    }
    
    public static int getLimitU(Connection conn) throws IOException, SQLException, ClassNotFoundException{
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT count(*) AS highest FROM useractivities");
      if (rs.next()){
        int i = rs.getInt("highest");
        return i;
      }
      return 0;
    }
    
    public static int getLimitU(Connection conn, String keyword) throws IOException, SQLException, ClassNotFoundException{
      PreparedStatement pstmt = conn.prepareStatement("SELECT count(*) AS highest FROM useractivities WHERE username = ?");
      pstmt.setString(1, keyword);
      ResultSet rs = pstmt.executeQuery();
      
      if (rs.next()){
        int i = rs.getInt("highest");
        return i;
      }
      return 0;
    }
    
   public int updateDocumentsIn(String Title, String Department, String Doc_Dscrptn, String Doc_Rcvd, String DateReceived, String Sensitivity, String Doc_Folder, String Location, String Doc_Remarks, String Docno, String Status, String DateChange, Connection conn) throws IOException, SQLException, ClassNotFoundException{
      PreparedStatement pstmt = conn.prepareStatement("UPDATE DocumentsIn SET TITLE = ?, DEPARTMENT = ?, DOC_DSCRPTN = ?, DOC_RCVD = ?, DATERECEIVED = ?, USERNAME =?, SENSITIVITY = ?, DOC_FOLDER = ?, LOCATION = ?, DOC_REMARKS = ?, STATUS = ?, NOTIFDATE = ?, NOTIFSTAT = ? WHERE DOCNO = ?");
      
      pstmt.setString(1, Title);
      pstmt.setString(2, Department);
      pstmt.setString(3, Doc_Dscrptn);
      pstmt.setString(4, Doc_Rcvd);
      pstmt.setString(5, DateReceived);
      pstmt.setString(6, userName);
      pstmt.setString(7, Sensitivity);
      pstmt.setString(8, Doc_Folder);
      pstmt.setString(9, Location);
      pstmt.setString(10, Doc_Remarks);
      pstmt.setString(11, Status);
      if(Status.equals("Pending")){
      pstmt.setString(12, DateChange);
      pstmt.setString(13, "1");
      }
      else{
      pstmt.setString(12, DateChange);
      pstmt.setString(13, "0");
      }
      pstmt.setString(14, Docno);
      int i = pstmt.executeUpdate();
      
      PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
      pstmt2.setString(1, userName);
      pstmt2.setString(2, "Update Incoming Document");
      pstmt2.setString(3, Title);
      pstmt2.setTimestamp(4, getCurrentTimeStamp());
      pstmt2.setString(5, "User");
      pstmt2.executeUpdate();
      
      return i;
    }
    
    public int updateDocumentsOut(String Title, String Department, String Doc_Dscrptn, String Doc_Add, String DateReleased, String Sensitivity, String Doc_Folder, String Location, String Doc_Remarks, String Docno, String Status, String DateChange, Connection conn) throws IOException, SQLException, ClassNotFoundException{
      PreparedStatement pstmt = conn.prepareStatement("UPDATE DocumentsOut SET TITLE = ?, DEPARTMENT = ?, DOC_DSCRPTN = ?, DOC_ADD = ?, DATERELEASED = ?, USERNAME =?, SENSITIVITY = ?, DOC_FOLDER = ?, LOCATION = ?, DOC_REMARKS = ?, STATUS = ?, NOTIFDATE = ?, NOTIFSTAT = ? WHERE DOCNO = ?");
      
      pstmt.setString(1, Title);
      pstmt.setString(2, Department);
      pstmt.setString(3, Doc_Dscrptn);
      pstmt.setString(4, Doc_Add);
      pstmt.setString(5, DateReleased);
      pstmt.setString(6, userName);
      pstmt.setString(7, Sensitivity);
      pstmt.setString(8, Doc_Folder);
      pstmt.setString(9, Location);
      pstmt.setString(10, Doc_Remarks);
      pstmt.setString(11, Status);
      if(Status.equals("Pending")){
      pstmt.setString(12, DateChange);    
      pstmt.setString(13, "1");
      }
      else{
      pstmt.setString(12, DateChange);
      pstmt.setString(13, "0");
      }
      pstmt.setString(14, Docno);
      int i = pstmt.executeUpdate();
      
      PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
      pstmt2.setString(1, userName);
      pstmt2.setString(2, "Update Outgoing Document");
      pstmt2.setString(3, Title);
      pstmt2.setTimestamp(4, getCurrentTimeStamp());
      pstmt2.setString(5, "User");
      pstmt2.executeUpdate();
      
      return i;
    }
    
    public int updateBooks(String Callno, String Title, String Author, String Book_Year, String Publication, String Subject, String Book_Dscrptn, String Book_Folder, String Location, String Book_Remarks, String Accountno, Connection conn) throws IOException, SQLException, ClassNotFoundException{
      PreparedStatement pstmt = conn.prepareStatement("UPDATE Books SET CALLNO = ?, TITLE = ?, AUTHOR = ?, BOOK_YEAR = ?, PUBLICATION = ?, SUBJECT = ?, BOOK_DSCRPTN = ?, BOOK_FOLDER = ?, LOCATION = ?, BOOK_REMARKS = ? WHERE ACCOUNTNO = ?");

      pstmt.setString(1, Callno);
      pstmt.setString(2, Title);
      pstmt.setString(3, Author);
      pstmt.setString(4, Book_Year);
      pstmt.setString(5, Publication);
      pstmt.setString(6, Subject);
      pstmt.setString(7, Book_Dscrptn);
      pstmt.setString(8, Book_Folder);
      pstmt.setString(9, Location);
      pstmt.setString(10, Book_Remarks);
      pstmt.setString(11, Accountno);
      int i = pstmt.executeUpdate();
      
      PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
      pstmt2.setString(1, userName);
      pstmt2.setString(2, "Update Book");
      pstmt2.setString(3, Title);
      pstmt2.setTimestamp(4, getCurrentTimeStamp());
      pstmt2.setString(5, "User");
      pstmt2.executeUpdate();
      
      return i;
      
      
    }
    public String dateConvert(Date date)throws IOException{
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date preCon = date;        
    String dateCon = df.format(preCon);
    
    return dateCon;
    }
        public static ResultSet getNotif(String username, String date, String cmt, Connection conn) throws IOException, SQLException, ClassNotFoundException{      
        if (cmt.equals("Administrative")){
        PreparedStatement pstmt = conn.prepareStatement("SELECT (SELECT COUNT(*) FROM DOCUMENTSIN WHERE STATUS='PENDING' AND NOTIFSTAT='1' AND USERNAME =? AND NOTIFDATE <=?)"
                                                        + " + (SELECT COUNT(*) FROM DOCUMENTSOUT WHERE STATUS='PENDING' AND NOTIFSTAT='1' AND USERNAME = ? AND NOTIFDATE <=?)"
                                                        + " + (SELECT COUNT(*) FROM USERS WHERE NOTIFSTAT='1')");
        pstmt.setString(1, username);
        pstmt.setString(2, date);
        pstmt.setString(3, username);
        pstmt.setString(4, date);
        ResultSet rs = pstmt.executeQuery();
        return rs;
      }else{
        PreparedStatement pstmt = conn.prepareStatement("SELECT (SELECT COUNT(*) FROM DOCUMENTSIN WHERE STATUS='PENDING' AND NOTIFSTAT='1' AND USERNAME =? AND NOTIFDATE <=?)"
                                                        + "+ (SELECT COUNT(*) FROM DOCUMENTSOUT WHERE STATUS='PENDING' AND NOTIFSTAT='1' AND USERNAME = ? AND NOTIFDATE <=?)");
        pstmt.setString(1, username);
        pstmt.setString(2, date);
        pstmt.setString(3, username);
        pstmt.setString(4, date);
        ResultSet rs = pstmt.executeQuery();
        return rs;
      }    
    }
        
    public static ResultSet viewPendingOut(Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM DocumentsOut WHERE STATUS = 'PENDING'");
      return rs;
    }
    
    public static ResultSet viewPendingIn(Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      Statement pstmt = conn.createStatement();
      ResultSet rs = pstmt.executeQuery("SELECT * FROM DocumentsIn WHERE STATUS = 'PENDING'");
      return rs;
    }
        
    public static ResultSet viewNotifOut(String username, String date, Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM DOCUMENTSOUT WHERE STATUS = 'PENDING' AND NOTIFSTAT ='1' AND USERNAME = ? AND NOTIFDATE <= ?");
      pstmt.setString(1, username);
      pstmt.setString(2, date);
      ResultSet rs = pstmt.executeQuery();
      return rs;
    }
    
    public static ResultSet viewNotifIn(String username, String date, Connection conn) throws IOException, SQLException, ClassNotFoundException{      
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM DOCUMENTSIN WHERE STATUS = 'PENDING' AND NOTIFSTAT ='1' AND USERNAME = ? AND NOTIFDATE <= ?");
      pstmt.setString(1, username);
      pstmt.setString(2, date);
      ResultSet rs = pstmt.executeQuery();
      return rs;
    }

    public static int updateNotifOut(String Docno, Connection conn) throws IOException, SQLException, ClassNotFoundException{
      PreparedStatement pstmt = conn.prepareStatement("UPDATE DOCUMENTSOUT SET NOTIFSTAT = '0' WHERE DOCNO = ?");  
      pstmt.setString(1, Docno);
      int i = pstmt.executeUpdate();
      return i;
    }

    public static int updateNotifIn(String Docno, Connection conn) throws IOException, SQLException, ClassNotFoundException{
      PreparedStatement pstmt = conn.prepareStatement("UPDATE DOCUMENTSIN SET NOTIFSTAT = '0' WHERE DOCNO = ?");
      pstmt.setString(1, Docno);
      int i = pstmt.executeUpdate(); 
      return i;
    } 
    
    public int deleteDocumentIn(String docno, Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM archiveddocumentsin WHERE DOCNO = ?");
        pstmt.setString(1, docno);
        
        int i = pstmt.executeUpdate();
        PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
        pstmt2.setString(1, userName);
        pstmt2.setString(2, "Delete Incoming Document");
        pstmt2.setString(3, "DOCINNO-" + docno + "");
        pstmt2.setTimestamp(4, getCurrentTimeStamp());
        pstmt2.setString(5, "User");
        pstmt2.executeUpdate();
        
        return i;
    }
    
    public int archiveDocumentIn(String docno, Connection conn) throws SQLException {
        PreparedStatement pstmta = conn.prepareStatement("SELECT * FROM documentsin WHERE DOCNO = ?");
        pstmta.setString(1, docno);
        ResultSet rs = pstmta.executeQuery();
        
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM documentsin WHERE DOCNO = ?");
        pstmt.setString(1, docno);
        pstmt.executeUpdate();
        
        try{
        while(rs.next()){
            String sql = "INSERT INTO ArchivedDocumentsIn(TITLE, DEPARTMENT, DOC_DSCRPTN, FILETYPE, DOC_RCVD, DATERECEIVED, DOC_FOLDER, LOCATION, DOC_FILE, DOC_REMARKS, DATEADDED, USERNAME, STATUS, NOTIFDATE, NOTIFSTAT, SENSITIVITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
                  statement.setString(1, rs.getString("TITLE").trim());                 
                  statement.setString(2, rs.getString("DEPARTMENT").trim());
                  if (rs.getString("DOC_DSCRPTN").trim() != null) {
                    statement.setString(3, rs.getString("DOC_DSCRPTN").trim());
                  }else{
                    statement.setString(3, "");
                  }
                  //
                  statement.setString(4, rs.getString("FILETYPE").trim());
                  //
                  if (rs.getString("DOC_RCVD").trim() != null) {
                    statement.setString(5, rs.getString("DOC_RCVD").trim());
                  }else{
                    statement.setString(5, "");
                  }
                  if (rs.getString("DATERECEIVED").trim() != null) {
                  statement.setString(6, rs.getString("DATERECEIVED").trim());
                  }else{
                    statement.setString(6, "");
                  }
                  if (rs.getString("DOC_FOLDER").trim() != null) {
                  statement.setString(7, rs.getString("DOC_FOLDER").trim());
                  }else{
                    statement.setString(7, "");
                  }
                  if (rs.getString("LOCATION").trim() != null) {
                  statement.setString(8, rs.getString("LOCATION").trim());
                  }else{
                    statement.setString(8, "");
                  }
                  if (rs.getString("DOC_REMARKS").trim() != null) {
                  statement.setString(10, rs.getString("DOC_REMARKS").trim());
                  }else{
                    statement.setString(10, "");
                  }
                  statement.setString(11, rs.getString("DATEADDED").trim());
                  
                  if (rs.getString("STATUS").trim() != null) {
                  statement.setString(13, rs.getString("STATUS").trim());
                  }else{
                    statement.setString(13, "");
                  }
                  if (rs.getString("NOTIFDATE").trim() != null) {
                  statement.setString(14, rs.getString("NOTIFDATE").trim());
                  }else{
                    statement.setString(14, "");
                  }
                  if (rs.getString("NOTIFSTAT").trim() != null) {
                  statement.setString(15, rs.getString("NOTIFSTAT").trim());
                  }else{
                    statement.setString(15, "");
                  }
                  statement.setString(16, rs.getString("SENSITIVITY").trim());
             
            if (rs.getBlob("DOC_FILE") != null) {
                statement.setBlob(9, rs.getBlob("DOC_FILE"));
            }else{
                statement.setBlob(9, rs.getBlob("DOC_FILE"));
            }
            
            statement.setString(12, rs.getString("USERNAME").trim());
            
            
            int row = statement.executeUpdate();
        }
        }catch(Exception e){
            System.out.println(e);
        }
        
        System.out.println("What");
        PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
        pstmt2.setString(1, userName);
        pstmt2.setString(2, "Archive Incoming Document");
        pstmt2.setString(3, rs.getString("TITLE").trim());
        pstmt2.setTimestamp(4, getCurrentTimeStamp());
        pstmt2.setString(5, "User");
        int i = pstmt2.executeUpdate();
        
        return i;
    }
    
    public int recoverDocumentIn(String docno, Connection conn) throws SQLException {
        PreparedStatement pstmta = conn.prepareStatement("SELECT * FROM archiveddocumentsin WHERE DOCNO = ?");
        pstmta.setString(1, docno);
        ResultSet rs = pstmta.executeQuery();
        
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM archiveddocumentsin WHERE DOCNO = ?");
        pstmt.setString(1, docno);
        pstmt.executeUpdate();
        
        try{
        while(rs.next()){
            String sql = "INSERT INTO DocumentsIn(TITLE, DEPARTMENT, DOC_DSCRPTN, FILETYPE, DOC_RCVD, DATERECEIVED, DOC_FOLDER, LOCATION, DOC_FILE, DOC_REMARKS, DATEADDED, USERNAME, STATUS, NOTIFDATE, NOTIFSTAT, SENSITIVITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
                  statement.setString(1, rs.getString("TITLE").trim());                 
                  statement.setString(2, rs.getString("DEPARTMENT").trim());
                  if (rs.getString("DOC_DSCRPTN").trim() != null) {
                    statement.setString(3, rs.getString("DOC_DSCRPTN").trim());
                  }else{
                    statement.setString(3, "");
                  }
                  //
                  statement.setString(4, rs.getString("FILETYPE").trim());
                  //
                  if (rs.getString("DOC_RCVD").trim() != null) {
                    statement.setString(5, rs.getString("DOC_RCVD").trim());
                  }else{
                    statement.setString(5, "");
                  }
                  if (rs.getString("DATERECEIVED").trim() != null) {
                  statement.setString(6, rs.getString("DATERECEIVED").trim());
                  }else{
                    statement.setString(6, "");
                  }
                  if (rs.getString("DOC_FOLDER").trim() != null) {
                  statement.setString(7, rs.getString("DOC_FOLDER").trim());
                  }else{
                    statement.setString(7, "");
                  }
                  if (rs.getString("LOCATION").trim() != null) {
                  statement.setString(8, rs.getString("LOCATION").trim());
                  }else{
                    statement.setString(8, "");
                  }
                  if (rs.getString("DOC_REMARKS").trim() != null) {
                  statement.setString(10, rs.getString("DOC_REMARKS").trim());
                  }else{
                    statement.setString(10, "");
                  }
                  statement.setString(11, rs.getString("DATEADDED").trim());
                  
                  if (rs.getString("STATUS").trim() != null) {
                  statement.setString(13, rs.getString("STATUS").trim());
                  }else{
                    statement.setString(13, "");
                  }
                  if (rs.getString("NOTIFDATE").trim() != null) {
                  statement.setString(14, rs.getString("NOTIFDATE").trim());
                  }else{
                    statement.setString(14, "");
                  }
                  if (rs.getString("NOTIFSTAT").trim() != null) {
                  statement.setString(15, rs.getString("NOTIFSTAT").trim());
                  }else{
                    statement.setString(15, "");
                  }
                  statement.setString(16, rs.getString("SENSITIVITY").trim());
             
            if (rs.getBlob("DOC_FILE") != null) {
                statement.setBlob(9, rs.getBlob("DOC_FILE"));
            }else{
                statement.setBlob(9, rs.getBlob("DOC_FILE"));
            }
            
            statement.setString(12, rs.getString("USERNAME").trim());
            
            
            int row = statement.executeUpdate();
        }
        }catch(Exception e){
            System.out.println(e);
        }
        
        PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
        pstmt2.setString(1, userName);
        pstmt2.setString(2, "Recover Incoming Document");
        pstmt2.setString(3, rs.getString("TITLE").trim());
        pstmt2.setTimestamp(4, getCurrentTimeStamp());
        pstmt2.setString(5, "User");
        int i = pstmt2.executeUpdate();
        
        return i;
    }
    
    public int deleteDocumentOut(String docno, Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM archiveddocumentsout WHERE DOCNO = ?");
        pstmt.setString(1, docno);
        
        int i = pstmt.executeUpdate();
        
        PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
        pstmt2.setString(1, userName);
        pstmt2.setString(2, "Delete Outgoing Document");
        pstmt2.setString(3, "DOCOUTNO-" + docno + "");
        pstmt2.setTimestamp(4, getCurrentTimeStamp());
        pstmt2.setString(5, "User");
        pstmt2.executeUpdate();
        return i;
    }

    public int archiveDocumentOut(String docno, Connection conn) throws SQLException {
        PreparedStatement pstmta = conn.prepareStatement("SELECT * FROM documentsout WHERE DOCNO = ?");
        pstmta.setString(1, docno);
        ResultSet rs = pstmta.executeQuery();
        
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM documentsout WHERE DOCNO = ?");
        pstmt.setString(1, docno);
        pstmt.executeUpdate();
        
        try{
        while(rs.next()){
            String sql = "INSERT INTO ArchivedDocumentsOut(TITLE, DEPARTMENT, DOC_DSCRPTN, FILETYPE, DOC_ADD, DATERELEASED, DOC_FOLDER, LOCATION, DOC_FILE, DOC_REMARKS, DATEADDED, USERNAME, STATUS, NOTIFDATE, NOTIFSTAT, SENSITIVITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
                  statement.setString(1, rs.getString("TITLE").trim());                 
                  statement.setString(2, rs.getString("DEPARTMENT").trim());
                  if (rs.getString("DOC_DSCRPTN").trim() != null) {
                    statement.setString(3, rs.getString("DOC_DSCRPTN").trim());
                  }else{
                    statement.setString(3, "");
                  }
                  //
                  statement.setString(4, rs.getString("FILETYPE").trim());
                  //
                  if (rs.getString("DOC_ADD").trim() != null) {
                    statement.setString(5, rs.getString("DOC_ADD").trim());
                  }else{
                    statement.setString(5, "");
                  }
                  if (rs.getString("DATERELEASED").trim() != null) {
                  statement.setString(6, rs.getString("DATERELEASED").trim());
                  }else{
                    statement.setString(6, "");
                  }
                  if (rs.getString("DOC_FOLDER").trim() != null) {
                  statement.setString(7, rs.getString("DOC_FOLDER").trim());
                  }else{
                    statement.setString(7, "");
                  }
                  if (rs.getString("LOCATION").trim() != null) {
                  statement.setString(8, rs.getString("LOCATION").trim());
                  }else{
                    statement.setString(8, "");
                  }
                  if (rs.getString("DOC_REMARKS").trim() != null) {
                  statement.setString(10, rs.getString("DOC_REMARKS").trim());
                  }else{
                    statement.setString(10, "");
                  }
                  statement.setString(11, rs.getString("DATEADDED").trim());
                  
                  if (rs.getString("STATUS").trim() != null) {
                  statement.setString(13, rs.getString("STATUS").trim());
                  }else{
                    statement.setString(13, "");
                  }
                  if (rs.getString("NOTIFDATE").trim() != null) {
                  statement.setString(14, rs.getString("NOTIFDATE").trim());
                  }else{
                    statement.setString(14, "");
                  }
                  if (rs.getString("NOTIFSTAT").trim() != null) {
                  statement.setString(15, rs.getString("NOTIFSTAT").trim());
                  }else{
                    statement.setString(15, "");
                  }
                  statement.setString(16, rs.getString("SENSITIVITY").trim());
             
            if (rs.getBlob("DOC_FILE") != null) {
                statement.setBlob(9, rs.getBlob("DOC_FILE"));
            }
            
            statement.setString(12, rs.getString("USERNAME").trim());
            int row = statement.executeUpdate();
        }
        }catch(Exception e){
            System.out.println(e);
        }
        
        PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
        pstmt2.setString(1, userName);
        pstmt2.setString(2, "Archive Outgoing Document");
        pstmt2.setString(3, rs.getString("TITLE").trim());
        pstmt2.setTimestamp(4, getCurrentTimeStamp());
        pstmt2.setString(5, "User");
        int i = pstmt2.executeUpdate();
        return i;
    }
    
    public int recoverDocumentOut(String docno, Connection conn) throws SQLException {
        PreparedStatement pstmta = conn.prepareStatement("SELECT * FROM archiveddocumentsout WHERE DOCNO = ?");
        pstmta.setString(1, docno);
        ResultSet rs = pstmta.executeQuery();
        
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM archiveddocumentsout WHERE DOCNO = ?");
        pstmt.setString(1, docno);
        pstmt.executeUpdate();
        
        try{
        while(rs.next()){
            String sql = "INSERT INTO DocumentsOut(TITLE, DEPARTMENT, DOC_DSCRPTN, FILETYPE, DOC_ADD, DATERELEASED, DOC_FOLDER, LOCATION, DOC_FILE, DOC_REMARKS, DATEADDED, USERNAME, STATUS, NOTIFDATE, NOTIFSTAT, SENSITIVITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
                  statement.setString(1, rs.getString("TITLE").trim());                 
                  statement.setString(2, rs.getString("DEPARTMENT").trim());
                  if (rs.getString("DOC_DSCRPTN").trim() != null) {
                    statement.setString(3, rs.getString("DOC_DSCRPTN").trim());
                  }else{
                    statement.setString(3, "");
                  }
                  //
                  statement.setString(4, rs.getString("FILETYPE").trim());
                  //
                  if (rs.getString("DOC_ADD").trim() != null) {
                    statement.setString(5, rs.getString("DOC_ADD").trim());
                  }else{
                    statement.setString(5, "");
                  }
                  if (rs.getString("DATERELEASED").trim() != null) {
                  statement.setString(6, rs.getString("DATERELEASED").trim());
                  }else{
                    statement.setString(6, "");
                  }
                  if (rs.getString("DOC_FOLDER").trim() != null) {
                  statement.setString(7, rs.getString("DOC_FOLDER").trim());
                  }else{
                    statement.setString(7, "");
                  }
                  if (rs.getString("LOCATION").trim() != null) {
                  statement.setString(8, rs.getString("LOCATION").trim());
                  }else{
                    statement.setString(8, "");
                  }
                  if (rs.getString("DOC_REMARKS").trim() != null) {
                  statement.setString(10, rs.getString("DOC_REMARKS").trim());
                  }else{
                    statement.setString(10, "");
                  }
                  statement.setString(11, rs.getString("DATEADDED").trim());
                  
                  if (rs.getString("STATUS").trim() != null) {
                  statement.setString(13, rs.getString("STATUS").trim());
                  }else{
                    statement.setString(13, "");
                  }
                  if (rs.getString("NOTIFDATE").trim() != null) {
                  statement.setString(14, rs.getString("NOTIFDATE").trim());
                  }else{
                    statement.setString(14, "");
                  }
                  if (rs.getString("NOTIFSTAT").trim() != null) {
                  statement.setString(15, rs.getString("NOTIFSTAT").trim());
                  }else{
                    statement.setString(15, "");
                  }
                  statement.setString(16, rs.getString("SENSITIVITY").trim());
             
            if (rs.getBlob("DOC_FILE") != null) {
                statement.setBlob(9, rs.getBlob("DOC_FILE"));
            }
            
            statement.setString(12, rs.getString("USERNAME").trim());
            int row = statement.executeUpdate();
        }
        }catch(Exception e){
            System.out.println(e);
        }
        
        PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
        pstmt2.setString(1, userName);
        pstmt2.setString(2, "Recover Outgoing Document");
        pstmt2.setString(3, rs.getString("TITLE").trim());
        pstmt2.setTimestamp(4, getCurrentTimeStamp());
        pstmt2.setString(5, "User");
        int i = pstmt2.executeUpdate();
        return i;
    }
    
    public int deleteBook(String accountno, Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM archivedbooks WHERE ACCOUNTNO = ?");
        pstmt.setString(1, accountno);
        
        int i = pstmt.executeUpdate();
        PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
        pstmt2.setString(1, userName);
        pstmt2.setString(2, "Delete Book");
        pstmt2.setString(3, "BOOKNO-" + accountno + "");
        pstmt2.setTimestamp(4, getCurrentTimeStamp());
        pstmt2.setString(5, "User");
        pstmt2.executeUpdate();
        return i;
    }
    
    public int archiveBook(String accountno, Connection conn) throws SQLException {
        PreparedStatement pstmta = conn.prepareStatement("SELECT * FROM books WHERE ACCOUNTNO = ?");
        pstmta.setString(1, accountno);
        ResultSet rs = pstmta.executeQuery();
        
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM books WHERE ACCOUNTNO = ?");
        pstmt.setString(1, accountno);
        pstmt.executeUpdate();
        
        String title = "";
        
        try{
        while(rs.next()){
            String sql = "INSERT INTO ArchivedBooks(CALLNO, TITLE, AUTHOR, BOOK_YEAR, PUBLICATION, SUBJECT, BOOK_DSCRPTN, BOOK_FOLDER, LOCATION, BOOK_FILE, BOOK_REMARKS, DATEADDED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
                  statement.setString(1, rs.getString("CALLNO").trim());   
                  title = rs.getString("TITLE").trim();
                  statement.setString(2, title);
                  if (rs.getString("AUTHOR").trim() != null) {
                    statement.setString(3, rs.getString("AUTHOR").trim());
                  }else{
                    statement.setString(3, "");
                  }
                  if (rs.getString("BOOK_YEAR").trim() != null) {
                    statement.setString(4, rs.getString("BOOK_YEAR").trim());
                  }else{
                    statement.setString(4, "");
                  }
                  if (rs.getString("PUBLICATION").trim() != null) {
                    statement.setString(5, rs.getString("PUBLICATION").trim());
                  }else{
                    statement.setString(5, "");
                  }
                  if (rs.getString("SUBJECT").trim() != null) {
                  statement.setString(6, rs.getString("SUBJECT").trim());
                  }else{
                    statement.setString(6, "");
                  }
                  if (rs.getString("BOOK_DSCRPTN").trim() != null) {
                  statement.setString(7, rs.getString("BOOK_DSCRPTN").trim());
                  }else{
                    statement.setString(7, "");
                  }
                  if (rs.getString("BOOK_FOLDER").trim() != null) {
                  statement.setString(8, rs.getString("BOOK_FOLDER").trim());
                  }else{
                    statement.setString(8, "");
                  }
                  if (rs.getString("LOCATION").trim() != null) {
                  statement.setString(9, rs.getString("LOCATION").trim());
                  }else{
                    statement.setString(9, "");
                  }
                  if (rs.getString("BOOK_REMARKS").trim() != null) {
                  statement.setString(11, rs.getString("BOOK_REMARKS").trim());
                  }else{
                    statement.setString(11, "");
                  }
                  statement.setTimestamp(12, getCurrentTimeStamp());
             
            if (rs.getBlob("BOOK_FILE") != null) {
                statement.setBlob(10, rs.getBlob("BOOK_FILE"));
            }
            statement.executeUpdate();
        }
        }catch(Exception e){
            System.out.println(e);
        }
        
        PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
        pstmt2.setString(1, userName);
        pstmt2.setString(2, "Archive Book");
        pstmt2.setString(3, title);
        pstmt2.setTimestamp(4, getCurrentTimeStamp());
        pstmt2.setString(5, "User");
        int i = pstmt2.executeUpdate();
        return i;
    }
    
    public int recoverBook(String accountno, Connection conn) throws SQLException {
        PreparedStatement pstmta = conn.prepareStatement("SELECT * FROM archivedbooks WHERE ACCOUNTNO = ?");
        pstmta.setString(1, accountno);
        ResultSet rs = pstmta.executeQuery();
        
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM archivedbooks WHERE ACCOUNTNO = ?");
        pstmt.setString(1, accountno);
        pstmt.executeUpdate();
        
        String title = "";
        
        try{
        while(rs.next()){
            String sql = "INSERT INTO Books(CALLNO, TITLE, AUTHOR, BOOK_YEAR, PUBLICATION, SUBJECT, BOOK_DSCRPTN, BOOK_FOLDER, LOCATION, BOOK_FILE, BOOK_REMARKS, DATEADDED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
                  statement.setString(1, rs.getString("CALLNO").trim());  
                  title = rs.getString("TITLE").trim();
                  statement.setString(2, title);
                  if (rs.getString("AUTHOR").trim() != null) {
                    statement.setString(3, rs.getString("AUTHOR").trim());
                  }else{
                    statement.setString(3, "");
                  }
                  if (rs.getString("BOOK_YEAR").trim() != null) {
                    statement.setString(4, rs.getString("BOOK_YEAR").trim());
                  }else{
                    statement.setString(4, "");
                  }
                  if (rs.getString("PUBLICATION").trim() != null) {
                    statement.setString(5, rs.getString("PUBLICATION").trim());
                  }else{
                    statement.setString(5, "");
                  }
                  if (rs.getString("SUBJECT").trim() != null) {
                  statement.setString(6, rs.getString("SUBJECT").trim());
                  }else{
                    statement.setString(6, "");
                  }
                  if (rs.getString("BOOK_DSCRPTN").trim() != null) {
                  statement.setString(7, rs.getString("BOOK_DSCRPTN").trim());
                  }else{
                    statement.setString(7, "");
                  }
                  if (rs.getString("BOOK_FOLDER").trim() != null) {
                  statement.setString(8, rs.getString("BOOK_FOLDER").trim());
                  }else{
                    statement.setString(8, "");
                  }
                  if (rs.getString("LOCATION").trim() != null) {
                  statement.setString(9, rs.getString("LOCATION").trim());
                  }else{
                    statement.setString(9, "");
                  }
                  if (rs.getString("BOOK_REMARKS").trim() != null) {
                  statement.setString(11, rs.getString("BOOK_REMARKS").trim());
                  }else{
                    statement.setString(11, "");
                  }
                  statement.setTimestamp(12, getCurrentTimeStamp());
             
            if (rs.getBlob("BOOK_FILE") != null) {
                statement.setBlob(10, rs.getBlob("BOOK_FILE"));
            }
            statement.executeUpdate();
        }
        }catch(Exception e){
            System.out.println(e);
        }
        
        PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO useractivities (USERNAME, ACTION, DATA, DATEADDED, CMT) VALUES (?, ?, ?, ?, ?)");
        pstmt2.setString(1, userName);
        pstmt2.setString(2, "Recover Book");
        pstmt2.setString(3, title);
        pstmt2.setTimestamp(4, getCurrentTimeStamp());
        pstmt2.setString(5, "User");
        int i = pstmt2.executeUpdate();
        return i;
    }
}
