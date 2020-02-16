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
        <link rel="stylesheet" href="css/design.css">
        <title>Document Details</title>
        <%ResultSet rs_title = (ResultSet)session.getAttribute("rs_title");%>
    </head>
    <body>
        <table>
            <col width="180">
            <col width="0">
            <col width="0">
            <col width="0">
            <col width="900">
            <tr>
                <td><img src="photos/file.png">
                <br>
        <%while(rs_title.next()){%>
        <% if (rs_title.getString("FILETYPE").trim().equals("Incoming")){ %>
        <a class = "download" href="viewFile?title=<%=rs_title.getString("TITLE").trim()%>"download>Download File</a><br>
            <text class ="subtitle">
            DOCINNO-00<%=rs_title.getString("DOCNO").trim()%> <br />
            </text><br>
            
            <text class ="label">
            Folder
            </text><br>
            <text class ="normal">
            <%=rs_title.getString("DOC_FOLDER").trim()%><br/><br>
            </text>
            
            <text class ="label">
            Date Added
            </text><br>
            <text class ="normal">
            <%=rs_title.getTimestamp("DATEADDED")%>
            </text><br/><br/>    
            </td>
            
            
            <div class="vr">&nbsp;</div>           
            <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
            
            <td>            
            <text class ="title"> <strong><%=rs_title.getString("TITLE").trim()%></strong></text><br />
            <text class ="normal">
            <%=rs_title.getString("DEPARTMENT").trim()%><br />
            <%=rs_title.getString("FILETYPE").trim()%> Document<br><br/>
            </text>
            
            <text class ="label">
            <i>Short Description</i>
            </text>
            
            <text class ="normal">
            <br><%=rs_title.getString("DOC_DSCRPTN").trim()%><br><br/>
            </text>
           
            <table width="300">
                <tr>
                    <td><text class ="label">Received by</text></td>
                    <td><text class ="label">Date Received</text></td>
                    <td><text class ="title">&nbsp;&nbsp;</text></td>
                    <td><text class ="label">Status</text></td>
                </tr>
                
                <tr>
                    <td><text class ="normal"><%=rs_title.getString("DOC_RCVD").trim()%></text></td>
                    <td><text class ="normal"><%=rs_title.getString("DATERECEIVED").trim()%></text></td>
                    <td><text class ="title">&nbsp;&nbsp;</text></td>
                    <td><text class ="normal"><%=rs_title.getString("STATUS").trim()%></text></td>
                    
                    
                </tr>
            </table>              
                
            <br/> 
            <text class ="label"><i>Remarks</i></text><br>
            <text class ="normal">
            <%=rs_title.getString("DOC_REMARKS").trim()%>
            </text><br/>
            <%}%>
            
            <% if (rs_title.getString("FILETYPE").trim().equals("Outgoing")){ %>
            <a class = "download" href="viewFile?title=<%=rs_title.getString("TITLE").trim()%>"download>Download File</a><br>
            <text class ="subtitle">
            DOCOUTNO-00<%=rs_title.getString("DOCNO").trim()%> <br />
            </text><br>
            
            <text class ="label">
            Folder
            </text><br>
            <text class ="normal">
            <%=rs_title.getString("DOC_FOLDER").trim()%><br/><br>
            </text>
            
            <text class ="label">
            Date Added
            </text><br>
            <text class ="normal">
            <%=rs_title.getTimestamp("DATEADDED")%>
            </text><br/><br/>    
            </td>
            
            
            <div class="vr">&nbsp;</div>           
            <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
            
            <td>            
            <text class ="title"> <strong><%=rs_title.getString("TITLE").trim()%></strong></text><br />
            <text class ="normal">
            <%=rs_title.getString("DEPARTMENT").trim()%><br />
            <%=rs_title.getString("FILETYPE").trim()%> Document<br><br/>
            </text>
            
            <text class ="label">
            <i>Short Description</i>
            </text>
            
            <text class ="normal">
            <br><%=rs_title.getString("DOC_DSCRPTN").trim()%><br><br/>
            </text>
           
            <table width="300">
                <tr>
                    <td><text class ="label">Addressed to</text></td>
                    <td><text class ="label">Date Released</text></td>
                    <td><text class ="title">&nbsp;&nbsp;</text></td>
                    <td><text class ="label">Status</text></td>
                </tr>
                
                <tr>
                    <td><text class ="normal"><%=rs_title.getString("DOC_ADD").trim()%></text></td>
                    <td><text class ="normal"><%=rs_title.getString("DATERELEASED").trim()%></text></td>
                    <td><text class ="title">&nbsp;&nbsp;</text></td>
                    <td><text class ="normal"><%=rs_title.getString("STATUS").trim()%></text></td>
                    
                    
                </tr>
            </table>              
                
            <br/> 
            <text class ="label"><i>Remarks</i></text><br>
            <text class ="normal">
            <%=rs_title.getString("DOC_REMARKS").trim()%>
            </text><br/>
            <%}%>
            
            </td>
            </tr>     
        <%}%>
        </table>
    </body>
</html>
