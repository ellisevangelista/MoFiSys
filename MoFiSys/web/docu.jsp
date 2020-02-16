<%-- 
    Document   : docu
    Created on : Jan 9, 2017, 6:44:52 PM
    Author     : ellisevangelista
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/popup.css">
        <link rel="icon" href="photos/favicon.png" type="image/png" sizes="16x16">
        <title>JSP Page</title>
    </head>
    <body background="photos/save_popup.png">
        <div id="nonadmin" class="overlay">
        <div class="popup">
         <a class="close" href="main.jsp">&times;</a>
            <div class="content">
                            <center>
                                <text class="header">INSERT RECORD</text>
                        <p class="text"style="position:absolute;top:50px;left:45px;">
                        Only administrator users can access user accounts.<br>
                        </p>
                            </center>
                    </div>
            </div>
        </div>
    </body>
</html>
