<%-- 
    Document   : success
    Created on : Jan 4, 2017, 11:31:40 AM
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
        <title>Congratulations!</title>
    </head>
    <body background="photos/createaccount.png">

  <%String qry = (String)session.getAttribute("qry");%>
    <% if(qry.equals("INSERT")){%>

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
