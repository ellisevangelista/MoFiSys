<%-- 
    Document   : changePassword
    Created on : 03 24, 17, 7:45:29 PM
    Author     : GIan
--%>
<%@page import="pack.dashboard"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/popup.css">
        <link rel="icon" href="photos/favicon.png" type="image/png" sizes="16x16">
        <title>Congratulations!</title>
    </head>
    <body background="photos/success_reset.png">

  <%String qry = (String)session.getAttribute("qry");%>
    <% if(qry.equals("UPDATE")){%>

                <center>
                  <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                  <br><br><br><br><br><br><br><br><br><br><br><br>
                <a href="/MoFiSys" class="button">SIGN IN TO CONTINUE</a><br>
                </center>
                
            <footer class="main">    
            </footer>
    <%}%>
    </body>
</html>
