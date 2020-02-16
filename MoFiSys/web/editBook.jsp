<%-- 
    Document   : editdocuin
    Created on : 01 22, 17, 7:57:47 PM
    Author     : Laurenz
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ResultSet book = (ResultSet)session.getAttribute("book");
    while(book.next()){
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="photos/favicon.png" type="image/png" sizes="16x16">
        <link rel="stylesheet" href="css/designcss.css">
        <link rel="stylesheet" href="css/sweetalert.css">
        <script src="css/sweetalert1.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <iframe style="display: none;" id="iframe" name="my_iframe"></iframe>
        <form action="updateBooks" target="my_iframe">
               <table>
                
                <tr>
                    <td>
                        <div class="insrt-hdr" style="float:left;">
                            <text class="header2">BOOK DESCRIPTION</text>
                        </div>
                    </td>    
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Call Number:</p>
                    </td>
                    <td>
                        <input type="text" name="book_callno" style="width: 40%" value="<%=book.getString("CALLNO").trim()%>">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Title:</p>
                    </td>
                    <td>
                        <input type="text" name="book_title" value="<%=book.getString("TITLE").trim()%>">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Author:</p>
                    </td>
                    <td>
                        <input type="text" name="book_author" value="<%=book.getString("AUTHOR").trim()%>">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Year: </p>
                        
                    </td>
                    
                    <td>
                        <input type="number" name="book_year" style="width: 25%" value="<%=book.getString("BOOK_YEAR").trim()%>">
                    </td>
                </tr>
                
                
                <tr>
                    <td>
                        <p class ="b-text">Publication:</p>
                    </td>
                    <td>
                        <input type="text" name="book_pub" value="<%=book.getString("PUBLICATION").trim()%>">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Subject:</p>
                    </td>
                    <td>
                        <input type="text" name="book_subj" value="<%=book.getString("SUBJECT").trim()%>">
                    </td>
                </tr>
                
                
                
                <tr>
                    <td>
                        <p class ="b-text">Brief Description:</p>                 
                    </td>
                        
                    <td>
                         <textarea name="book_dscrptn" id="dscrptnBook" cols="34" rows="4" maxlength="255"
                         onFocus="countChars('dscrptnBook','char_count5', 255)" onKeyDown="countChars('dscrptnBook','char_count5', 255)" onKeyUp="countChars('dscrptnBook','char_count5', 255)"></textarea>
                         
                        <br><text class ="label"><span id="char_count5"></span></text><br>
                    </td>
                </tr>
                
                <script>
                        document.getElementById("dscrptnBook").defaultValue = "<%=book.getString("BOOK_DSCRPTN").trim()%>";
                        function countChars(dscrptnBook, counter, max){
                        var count = max - document.getElementById(dscrptnBook).value.length;
                        document.getElementById(counter).innerHTML = count + " characters remaining"; 
                        }
                </script>
                
                <tr>
                    <td>
                        &nbsp;                 
                    </td>
                        
                </tr>
                        
                
                <tr>
                    <td>
                        <div class="insrt-hdr" style="float:left;">
                        <text class="header2">BOOK LOCATION</text>
                    </div>               
                    </td>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Folder:</p>
                    </td>
                    <td>
                        <input type="text" name="book_folder" value="<%=book.getString("BOOK_FOLDER").trim()%>">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Location:</p>
                    </td>
                    <td>
                        <input type="text" name="book_location" value="<%=book.getString("LOCATION").trim()%>">
                    </td>
                </tr>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Remarks:</p>
                    </td>
                    <td>
                        <textarea name="book_remarks" id="remarksBook" cols="34" rows="4" maxlength="140"
                         onFocus="countChars('remarksBook','char_count6', 140)" onKeyDown="countChars('remarksBook','char_count6', 140)" onKeyUp="countChars('remarksBook','char_count6', 140)"></textarea>
                         
                        <br><text class ="label"><span id="char_count6"></span></text><br>
                    </td>
                </tr>
                
                <script>
                        document.getElementById("remarksBook").defaultValue = "<%=book.getString("BOOK_REMARKS").trim()%>";
                        function countChars(remarksBook, counter, max){
                        var count = max - document.getElementById(remarksBook).value.length;
                        document.getElementById(counter).innerHTML = count + " characters remaining"; 
                        }
                </script>
                <input type="hidden" name="accountno" value="<%=book.getString("ACCOUNTNO").trim()%>">
            </table>
            
            <br><br>
            
            
                <button onclick="confirm()" class="update"/>Submit</button><br/>
            
         <script>
        function confirm(){
            swal({
                title: "Are you sure?",
                text: "Make sure to fill out all the important fields!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#FFE164",
                confirmButtonText: "Update Book",
                closeOnConfirm: false
            },
            function(){
                window.location.href="viewBooks?pg=1";
                });
                }
            </script>
            </form>
        
           
    </body>
</html>
<%}%>
