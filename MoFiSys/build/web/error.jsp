<%-- 
    Document   : error
    Created on : 09 21, 16, 11:33:01 AM
    Author     : ellisevangelista
--%>

<%@page import="pack.dashboard"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/popup.css">
        <link rel="icon" href="photos/favicon.png" type="image/png" sizes="16x16">
        <title>Error!</title>
    </head>
    <body background="photos/error_up.png">

  <%String func = (String)session.getAttribute("func");%>
    <% if(func.equals("SELECTe1")){%>

                <center>
                  <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                  <br><br><br><br><br><br><br><br><br><br><br><br><br>
                <a href="/MoFiSys" class="button">BACK TO HOME</a><br>
                </center>
                
            <footer class="main">    
            </footer>
     <%}else if(func.equals("SELECTe2")){%>
        
                <center>
                  <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                  <br><br><br><br><br><br><br><br><br><br><br><br><br>
                <a href="/dashboard" class="button">GO BACK</a><br>
                </center>
                
            <footer class="main">    
            </footer>
    <%}%>
    </body>
</html>
