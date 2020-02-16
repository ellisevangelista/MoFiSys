<%-- 
    Document   : useraccounts
    Created on : 01 22, 17, 4:55:18 PM
    Author     : Laurenz
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="photos/favicon.png" type="image/png" sizes="16x16">
        <title>User Accounts</title>
        <%ResultSet users = (ResultSet)session.getAttribute("user");%>
    </head>
    <body>
        <%while(users.next()){%>
        <div>
            Username: <%=users.getString("USERNAME").trim()%> <br />
            Name: <%=users.getString("LNAME").trim()%>, <%=users.getString("FNAME").trim()%> <br />
            Committee: <%=users.getString("CMT").trim()%>
        </div>
        <%}%>
    </body>
</html>
