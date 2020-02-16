<%-- 
    Document   : viewSpecificDocu
    Created on : Feb 5, 2017, 1:01:48 PM
    Author     : ellisevangelista
--%>

<%@page import="java.io.InputStream"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="photos/favicon.png" type="image/png" sizes="16x16">
        <link rel="stylesheet" href="css/designcss.css">
        <title>Document Details</title>
        <%ResultSet rs_title = (ResultSet)session.getAttribute("rs_title");
        %>
    </head>
    <body>
        <table>
            <col width="180">
            <col width="0">
            <col width="0">
            <col width="0">
            <col width="900">
        <tr>
            <td><img src="photos/book.png">
                    <br>
                <%while(rs_title.next()){%>

                <text class ="subtitle">
                BOOKNO-00<%=rs_title.getString("ACCOUNTNO").trim()%> <br />
                </text><br>
                <a class = "download" href="viewFile_Book?title=<%=rs_title.getString("TITLE").trim()%>"download>Download Book</a><br>
            </td>
            
            <div class="vr">&nbsp;</div>           
            <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
            
            <td>            
                <text class ="title"> <strong><%=rs_title.getString("TITLE").trim()%></strong></text><br />
                <text class ="normal">
                Author: <%=rs_title.getString("AUTHOR").trim()%><br />
                Year: <%=rs_title.getString("BOOK_YEAR").trim()%><br />
                Publication: <%=rs_title.getString("PUBLICATION").trim()%><br />
                Subject: <%=rs_title.getString("SUBJECT").trim()%><br>
                Description: <%=rs_title.getString("BOOK_DSCRPTN").trim()%><br/> 
                </text>
            </td>
        </tr>
        </table>    
            <%}%>     
    </body>
</html>
