<%-- 
    Document   : updateuser
    Created on : 01 22, 17, 7:57:47 PM
    Author     : Laurenz
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ResultSet user = (ResultSet)session.getAttribute("user");
    String cmt = (String)session.getAttribute("cmt");
    while(user.next()){
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="photos/favicon.png" type="image/png" sizes="16x16">
        <link rel="stylesheet" href="css/design.css">
        <link rel="stylesheet" href="css/sweetalert.css">
        <script src="css/sweetalert1.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <%if(cmt.equals("Administrative")){%>
        <iframe style="display: none;" id="iframe" name="my_iframe"></iframe>
        <form action="updateUser" method="POST" target="my_iframe">
            <p class="b-text">Username: <strong><%=user.getString("USERNAME").trim()%></strong><br><br>
                First Name<br> <input type="text" name="fname" id="fname" value="<%=user.getString("FNAME").trim()%>"/><br/><br/>
                Last Name<br> <input type="text" name="lname" id="lname" value="<%=user.getString("LNAME").trim()%>"/><br /><br/>
                Committee<br> <input type="text" name="cmt" id="cmt" value="<%=user.getString("CMT").trim()%>"/><br /><br/>
                Password<br> <input type="password" name="pass" id="pass" title="Password must contain at least 8 characters, including UPPER/lowercase and numbers" type = "password" required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" value="<%=user.getString("PASS").trim()%>"/></p>
            <input type="hidden" name="phase" value="two"/><input type="hidden" name="user" value="<%=user.getString("USERNAME").trim()%>"/>
            <button class="update" onclick="confirm()"/>Update</button><br/>
         <script>
        function confirm(){
            swal({
                title: "Are you sure?",
                text: "Make sure to inform the user after making changes!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#FFE164",
                confirmButtonText: "Update User",
                closeOnConfirm: false
            },
            function(){
                window.location.href="userAccounts?cmt=Administrative";
                });
        }
                
            </script>
        </form>
           
    </body>
</html>
<%}}%>
