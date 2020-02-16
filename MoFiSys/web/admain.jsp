<%--
    Document   : main
    Created on : Dec 28, 2016, 11:14:13 PM
    Author     : ellisevangelista
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="org.apache.commons.io.FilenameUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to MOFISYS</title>
    </head>
    
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/sweetalert.css">
    <script src="css/sweetalert1.js"></script>
    <link rel="icon" href="photos/favicon.png" type="image/png" sizes="16x16">
    
    <body>
        <%HttpSession sesh = request.getSession();%>
        <%-- Header --%>
        <div class="header"></div>
        <div class ="welcome">
             <%String qry = (String)session.getAttribute("qry");%>
             <% if(qry.equals("SELECT")){%>
             <%  ResultSet rs = (ResultSet)session.getAttribute("user"); //<-
                 ResultSet rsn = (ResultSet)session.getAttribute("notif");
                sesh.setAttribute("user", rs);
                sesh.setAttribute("notif", rsn);
                while ((rs.next())&&(rsn.next())) {//->z %>
            <p class="text" style="position:absolute;left:40px;top:-8px;"> 
            Welcome, <%out.print(rs.getString("FNAME"));String cmt = rs.getString("CMT");sesh.setAttribute("cmt", cmt);%>!</p>
            <%if(Integer.parseInt(rsn.getString(1))!=0){%>
            <a class="notifbttn" href="#notifpop" onclick="viewNotif();" style="position:absolute;right:30px;top:7px;"><%out.print(rsn.getString(1));%></a>
            <%}else{%>
            <%--<a class="notifbttn" href="javascript:void();" onclick="viewNotif();" style="position:absolute;right:30px;top:7px;"><%out.print(rsn.getString(1));%></a>--%>
            <%}%>
             <%-- Settings --%>
            <div class="dropdown" style="position:absolute;right:6px;top:5px;">
                <text class="normal">&#x25BC;<text>
                <div class="dropdown-content">
                    <a href="#myacc">My Account</a>
                    <a href="javascript:void();" onclick="logOut();">Log out</a>
                </div>
            </div>
                    <script>
                        function logOut() {
                        window.open("logout.jsp",'_blank');
                        window.open("","main.jsp","");
                        window.close();
                        }
                    </script>
            
                    
           
            <%-- End of Settings --%>
            
        </div>
        <%-- End of Header --%>
       
        
        <%-- Sidebar --%>
   
        <div class ="sidebar" style="display: block">
            <nav class="vertical">
              <ul> 
                <li>
                    <a href="#" style="position:absolute;left:-49px;top:-12px;"><b>ADD</b> +</a><br>
                        <ul>
                            <li><a href="javascript:void();" onclick="insertDocuIn();">INCOMING</a></li>
                            <li><a href="javascript:void();" onclick="insertDocuOut();">OUTGOING</a> </li>
                            <li><a href="javascript:void();" onclick="insertBook();">BOOK</a></li>
                        </ul>
                </li>
               </ul> 
            </nav>
            <a href="javascript:void();" onclick="viewDocuments()" class="sidebar-button" style="position:absolute;left:6px;top:78px;"><b>DOCUMENTS</b></a><br>
            <a href="javascript:void();" onclick="viewBooks()" class="sidebar-button" style="position:absolute;left:6px;top:136px;"><b>BOOKS</b></a><br>
            <a href="javascript:void();" onclick="viewPending()" class="sidebar-button" style="position:absolute;left:6px;top:194px;"><b>PENDING</b></a><br>
            <%if(cmt.equals("Administrative")){%><a href="javascript:void();" onclick="userAccounts()" class="sidebar-button" style="position:absolute;left:6px;top:252px;"><b>USER ACCOUNTS</b></a><br>
            <%}else{%><a href="#nonadmin" class="sidebar-button" style="position:absolute;left:6px;top:252px;"><b>USER ACCOUNTS</b></a><br><%}%>
            <a href="javascript:void();" onclick="traceActivities()" class="sidebar-button" style="position:absolute;left:6px;top:310px;"><b>TRACE ACTIVITIES</b></a><br>
            <a href="javascript:void();" onclick="generateReport();" class="sidebar-button" style="position:absolute;left:6px;top:368px;"><b>GENERATE REPORTS</b></a><br>
            <a href="javascript:void();" onclick="viewArchive();" class="sidebar-button" style="position:absolute;left:6px;top:426px;"><b>ARCHIVE</b></a><br>
        </div>
        
        <%-- End of Sidebar --%>

        <%-- Search bar --%>
        <iframe style="display: none;" id="iframe" name="search_iframe"></iframe>
        <div class="search-buttonbox" style="position:absolute;left:940px;top:45px;">         
            <form action="search" class="search-box" target="search_frame">
                <input type="text" placeholder="Search documents here..." name="keyword">
                <button type="submit" onclick="searchHere();">Search</button>
            </form>
        </div>            
            <div class="bodybox" style="display:none;" id="searchMe">
                    <iframe src="search" align="top" name ="search_frame"width="650" height="400">Please update your browser.</iframe>                 
            </div>
        <%-- End of Search bar --%>
        
        <%-- Tips Corner --%>
        
        <div class ="tips">      
            <a href ="tips/How-to-ADD.jsp" target="_blank"><img class="mySlides animate" src="photos/How-to-ADD.png"></a>
            <a href ="tips/How-to-VIEW.jsp" target="_blank"><img class="mySlides animate" src="photos/How-to-VIEW.png"></a>
            <a href ="tips/How-to-TRACE.jsp" target="_blank"><img class="mySlides animate" src="photos/How-to-TRACE.png"></a>
            <a href ="tips/How-to-GENERATE.jsp" target="_blank"><img class="mySlides animate" src="photos/How-to-GENERATE.png"></a>
        </div>
        
        <script type="text/javascript">
        var myIndex = 0;
        carousel();

        function carousel() {
            var i;
            var x = document.getElementsByClassName("mySlides");
            for (i = 0; i < x.length; i++) {
            x[i].style.display = "none";  
        }
            myIndex++;
            if (myIndex > x.length) {myIndex = 1}    
            x[myIndex-1].style.display = "block";  
            setTimeout(carousel, 5000);    
        }
        </script>
        <%-- End of Tips Corner --%>
        
        
        <%-- Time --%>
        <center>
        <div class ="rightbox">
                <div id="clockdate-full">
                    <div id="clock-large"></div>
                    <div id="date-large"></div>
                </div>
        </div>
        </center>
   <script>
    function showTime() {
        var a_p = "";
        var today = new Date();
        var curr_hour = today.getHours();
        var curr_minute = today.getMinutes();
        var curr_second = today.getSeconds();
        if (curr_hour < 12) {
            a_p = "<span>AM</span>";
        } else {
            a_p = "<span>PM</span>";
        }
        if (curr_hour == 0) {
            curr_hour = 12;
        }
        if (curr_hour > 12) {
            curr_hour = curr_hour - 12;
        }
        curr_hour = checkTime(curr_hour);
        curr_minute = checkTime(curr_minute);
        curr_second = checkTime(curr_second);
     document.getElementById('clock-large').innerHTML= curr_hour + " : " + curr_minute + " " + a_p;
        }

    function checkTime(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }
    setInterval(showTime, 500);

    var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    var date = new Date();
    var day = date.getDate();
    var month = date.getMonth();
    var yy = date.getYear();
    var year = (yy < 1000) ? yy + 1900 : yy;
     document.getElementById('date-large').innerHTML="<b>" + "</b>" + "&nbsp;" + day + " " + months[month] + " " + year;      
    </script> 
        
        <%-- End of Time--%>
        
        <%-- Popup for Notif --%>
        <div id="notifpop" class="overlay">
        <div class="popup">
         <a class="close" href="#">&times;</a>
            <div class="content">
                            <center>
                                <text class="header">&nbsp;HEADS UP!</text>
                        <p class="text"style="position:absolute;top:50px;left:45px;">
                        <%if(Integer.parseInt(rsn.getString(1))==1){%>
                        You have <%out.print(rsn.getString(1));%> new notification.<br>
                        <%}else{%>
                        You have <%out.print(rsn.getString(1));%> new notifications.<br>
                        <%}%>
                        </p>
                            </center>
                    </div>
            </div>
        </div>
        <%-- End of Popup for Notif --%>
        
        <%-- Popup for Non-ADMIN --%>
        <div id="nonadmin" class="overlay">
        <div class="popup">
         <a class="close" href="#">&times;</a>
            <div class="content">
                            <center>
                                <text class="header">&#9888;&nbsp;STOP</text>
                        <p class="text"style="position:absolute;top:50px;left:45px;">
                        Only administrator users can access user accounts.<br>
                        </p>
                            </center>
                    </div>
            </div>
        </div>
        <%-- End of Popup for Non-ADMIN --%>
                
        
        <%-- Footer --%>
        <div id ="footer">
            <footer class="main">    
            </footer>
        </div>
        <%-- End of Footer --%>
        
        <%-- Footer for Docu --%>
        <div id ="footer-docu" style="display: none">
            <footer class="docu">    
            </footer>
        </div>
        <%-- End of Footer for Docu--%>
        
        <%-- Footer for Book --%>
        <div id ="footer-book" style="display: none">
            <footer class="book">    
            </footer>
        </div>
        <%-- End of Footer for Book--%>
        
        
        <%-- In start --%>
        <iframe style="display: none;" id="iframe" name="my_iframe"></iframe>
        <div id="insertDocuIn" style="display: none">
         <div class="bodybox">
                    <div class="insrt-mainhdr" style="float:right;">
                        <text class="title2">INCOMING DOCUMENT</text>
                    </div>
            <br><br><br><br>
            <form method="post" action="insertDocuIn?user_name=<%=rs.getString("USERNAME")%>" name="docuin" enctype="multipart/form-data" target="my_iframe">
            <input readonly type="text" value="INSERTDOCU" name="function" hidden/>
            
            <table>
                
                <tr>
                    <div class="insrt-hdr" style="float:left;">
                        <text class="header">DOCUMENT DESCRIPTION</text>
                    </div>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Title:</p>
                    </td>
                    <td>
                        <input type="text" name="doc_title" required>
                    </td>
                </tr>
                
                
                <tr>
                    <td>
                        <p class ="b-text">Department:</p>
                    </td>
                    <td>
                        <input type="text" name="doc_dept" required>
                    </td>
                </tr>
                
                
                 <tr>
                    <td>
                        <p class ="b-text">Brief Description:</p>                 
                    </td>
                        
                    <td>
                         <textarea name="doc_dscrptn" id="dscrptnIn" cols="34" rows="4" maxlength="255"
                                   onFocus="countChars('dscrptnIn','char_count', 255)" onKeyDown="countChars('dscrptnIn','char_count', 255)" onKeyUp="countChars('dscrptnIn','char_count', 255)"></textarea>
                         
                        <br><text class ="label"><span id="char_count"></span></text><br>
                    </td>
                </tr>
                
                <script>
                        function countChars(dscrptnIn, counter, max){
                        var count = max - document.getElementById(dscrptnIn).value.length;
                        document.getElementById(counter).innerHTML = count + " characters remaining"; 
                        }
                </script>
                
                <tr>
                    <td>
                         <p class ="b-text">Received by: </p>             
                    </td>
                        
                    <td>
                         <input type="text" name="doc_rcvd">
                    </td>
                </tr>
                <tr>
                    <td>
                         <p class ="b-text">Incoming Date: </p>            
                    </td>
                        
                    <td>
                         <input type="date" name="doc_datercvd">
                    </td>
                </tr>
                              
                
    <script>
         function typeCheck() {
            if (document.getElementById('ifInType').checked) {
                document.getElementById('ifIn').style.display = 'block';
                document.getElementById('ifOut').style.display = 'none';
                }
            if (document.getElementById('ifOutType').checked) {
                    document.getElementById('ifOut').style.display = 'block';
                    document.getElementById('ifIn').style.display = 'none';
                }
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
                        <text class="header">DOCUMENT STATUS</text>
                        </div>              
                    </td>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Status:
                        </p>                 
                    </td>
                        
                    <td>
                        <input type="radio" name="doc_status" onclick="javascript:yesnoCheck();" id="yesCheck" value="Pending"><text class ="b-text">&nbsp;Pending</text> 
                        &nbsp;&nbsp;
                        <input type="radio" name="doc_status" onclick="javascript:yesnoCheck();" value="Settled" checked id="yesCheck"><text class ="b-text">&nbsp;Settled</text>
             
                            <div id="ifYes" style="display:none">
                                <br><br>
                                <input type="checkbox" name="doc_notif">
                                <text class ="b-text">
                                Notify me in: <input type="date" name="doc_notifDate" value="2050-05-05">
                                </text>
                                <br><br><br>
                            </div>                                  
                    </td>
                </tr>
                
    <script>
         function yesnoCheck() {
            if (document.getElementById('yesCheck').checked) {
                document.getElementById('ifYes').style.display = 'block';
                }
                else document.getElementById('ifYes').style.display = 'none';
            }
    </script>
               
                
                
                <tr>
                    <td>
                        <p class ="b-text">Sensitivity:</p>
                    </td>
                    <td>
                        <input type="radio" name="doc_sensitivity" value="None" checked><text class ="b-text">&nbsp;None</text> 
                        &nbsp;&nbsp;
                        <input type="radio" name="doc_sensitivity" value="Low"><text class ="b-text">&nbsp;Low</text>
                        &nbsp;&nbsp;
                        <input type="radio" name="doc_sensitivity" value="High"><text class ="b-text">&nbsp;High</text>                     
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <div class="tooltip"><img src="photos/helpsentisitivity.png">
                        <span class="tooltiptext">
                            <table style="width:353px">
                                <tr><td><b>NONE</b></td><td>No restrictions to users and administrators</td></tr>
                                <tr><td></td></tr>
                                <tr><td><b>LOW</b></td><td>Editable only by administrators but viewable by both users and administrators</td></tr>
                                <tr><td></td></tr>
                                <tr><td><b>HIGH</b></td><td>Editable and viewable only by administrators</td></tr>
                        </span>
                            </table>
                        </div>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <div class="insrt-hdr" style="float:left;">
                        <text class="header">DOCUMENT LOCATION</text>
                    </div>               
                    </td>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Folder:</p>
                    </td>
                    <td>
                        <input type="text" name="doc_folder" value="">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Location Path:</p>
                    </td>
                    <td>
                        <input type="text" name="doc_location">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">File Upload:</p>
                        <input type="file" id="fileupload" name="doc_file" accept=".jpg,.jpeg,.pdf,.doc,.docx,.xls,.xlsx, .mp3, .flac, .ogg, .wav" required>
                        <p class ="b-text">Max: 16MB</p>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        &nbsp;                 
                    </td>
                        
                </tr>
                
                 <tr>
                    <td>
                        <p class ="b-text">Remarks:</p>
                    </td>
                    <td>
                        <textarea name="doc_remarks" id = "remarksIn" cols="34" rows="4" maxlength="255"
                        onFocus="countChars('remarksIn','char_count2', 255)" onKeyDown="countChars('remarksIn','char_count2', 255)" onKeyUp="countChars('remarksIn','char_count2', 255)"></textarea>
                         
                        <br><text class ="label"><span id="char_count2"></span></text><br>
                    </td>
                </tr>
                
                <script>
                        function countChars(remarksIn, counter, max){
                        var count = max - document.getElementById(remarksIn).value.length;
                        document.getElementById(counter).innerHTML = count + " characters remaining"; 
                        }
                </script>
                    
                
            </table>
            
            <br><br>
            
            <div align="right">
                <button type="reset"class="button">Reset</button>
                <button type="submit"class="button" onclick="validateInForm()">Save</button>
            </div>
            
            <script>
                function validateInForm()
                {
                var a=document.forms["docuin"]["doc_title"].value;
                var b=document.forms["docuin"]["doc_dept"].value;
                var c=document.forms["docuin"]["doc_rcvd"].value;
                var d=document.forms["docuin"]["doc_datercvd"].value;
                var e=document.forms["docuin"]["doc_file"].value;
                if (a==null || a=="",b==null || b=="",c==null || c=="",d==null || d=="", e==null || e==""){
                swal("Oops...", "Something went wrong! Please try again and make sure to fill out the important fields before submitting.", "error");;
                }     
                else if (a!=null || a!="",b!=null || b!="",c!=null || c!="",d!=null || d!="", e!=null || e!="" ) {
                swal("Success!", "Document record has been added successfully!", "success");;
                window.setTimeout(location.reload.bind(location), 4000);
                };
                
                }
                
                
               
                </script>
             </form>
            
            </div>
        </div>
        
        <%--in end --%>
        
        <%--out start --%>
        
        <iframe style="display: none;" id="iframe" name="my_oframe"></iframe>
        <div id="insertDocuOut" style="display: none">
         <div class="bodybox">
                    <div class="insrt-mainhdr" style="float:right;">
                        <text class="title2">OUTGOING DOCUMENT</text>
                    </div>
            <br><br><br><br>
            <form method="post" action="insertDocuOut?user_name=<%=rs.getString("USERNAME")%>" name="docuout" enctype="multipart/form-data" target="my_oframe">
            <input readonly type="text" value="INSERTDOCU" name="function" hidden/>
            
            <table>
                
                <tr>
                    <div class="insrt-hdr" style="float:left;">
                        <text class="header">DOCUMENT DESCRIPTION</text>
                    </div>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Title:</p>
                    </td>
                    <td>
                        <input type="text" name="doc_title" required>
                    </td>
                </tr>
                
                
                <tr>
                    <td>
                        <p class ="b-text">Department:</p>
                    </td>
                    <td>
                        <input type="text" name="doc_dept" required>
                    </td>
                </tr>
                
                
                <tr>
                    <td>
                        <p class ="b-text">Brief Description:</p>                 
                    </td>
                        
                    <td>
                         <textarea name="doc_dscrptn" id="dscrptnOut" cols="34" rows="4" maxlength="255"
                                   onFocus="countChars('dscrptnOut','char_count3', 255)" onKeyDown="countChars('dscrptnOut','char_count3', 255)" onKeyUp="countChars('dscrptnOut','char_count3', 255)"></textarea>
                         
                        <br><text class ="label"><span id="char_count3"></span></text><br>
                    </td>
                </tr>
                
                <script>
                        function countChars(dscrptnOut, counter, max){
                        var count = max - document.getElementById(dscrptnOut).value.length;
                        document.getElementById(counter).innerHTML = count + " characters remaining"; 
                        }
                </script>
                
                
                <tr>
                    <td>
                        <p class ="b-text">Addressed to:</p>                  
                    </td>
                        
                    <td>
                        <input type="text" name="doc_add">
                    </td>
                </tr>
                <tr>
                    <td>
                        <p class ="b-text">Outgoing Date: </p>             
                    </td>
                        
                    <td>
                        <input type="date" name="doc_daterelease">
                    </td>
                </tr>
                
    <script>
         function typeCheck() {
            if (document.getElementById('ifInType').checked) {
                document.getElementById('ifIn').style.display = 'block';
                document.getElementById('ifOut').style.display = 'none';
                }
            if (document.getElementById('ifOutType').checked) {
                    document.getElementById('ifOut').style.display = 'block';
                    document.getElementById('ifIn').style.display = 'none';
                }
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
                        <text class="header">DOCUMENT STATUS</text>
                        </div>              
                    </td>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Status:
                        </p>                 
                    </td>
                        
                    <td>
                        <input type="radio" name="doc_status" onclick="javascript:yesnoCheck2();" id="yesCheck2" value="Pending"><text class ="b-text">&nbsp;Pending</text> 
                        &nbsp;&nbsp;
                        <input type="radio" name="doc_status" onclick="javascript:yesnoCheck2();" value="Settled" checked id="yesCheck"><text class ="b-text">&nbsp;Settled</text>
             
                            <div id="ifYes2" style="display:none">
                                <br><br>
                                <input type="checkbox" name="doc_notif">
                                <text class ="b-text">
                                Notify me in: <input type="date" name="doc_notifDate" value="2050-05-05">
                                </text>
                                <br><br><br>
                            </div>                                  
                    </td>
                </tr>
                
    <script>
         function yesnoCheck2() {
            if (document.getElementById('yesCheck2').checked) {
                document.getElementById('ifYes2').style.display = 'block';
                }
                else document.getElementById('ifYes2').style.display = 'none';
            }
    </script>
                
                <tr>
                    <td>
                        <p class ="b-text">Sensitivity:</p>
                    </td>
                    <td>
                        <input type="radio" name="doc_sensitivity" value="None" checked><text class ="b-text">&nbsp;None</text> 
                        &nbsp;&nbsp;
                        <input type="radio" name="doc_sensitivity" value="Low"><text class ="b-text">&nbsp;Low</text>
                        &nbsp;&nbsp;
                        <input type="radio" name="doc_sensitivity" value="High"><text class ="b-text">&nbsp;High</text>                     
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <div class="tooltip"><img src="photos/helpsentisitivity.png">
                        <span class="tooltiptext">
                            <table style="width:353px">
                                <tr><td><b>NONE</b></td><td>No restrictions to users and administrators</td></tr>
                                <tr><td></td></tr>
                                <tr><td><b>LOW</b></td><td>Editable only by administrators but viewable by both users and administrators</td></tr>
                                <tr><td></td></tr>
                                <tr><td><b>HIGH</b></td><td>Editable and viewable only by administrators</td></tr>
                        </span>
                            </table>
                        </div>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <div class="insrt-hdr" style="float:left;">
                        <text class="header">DOCUMENT LOCATION</text>
                    </div>               
                    </td>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Folder:</p>
                    </td>
                    <td>
                        <input type="text" name="doc_folder">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Location Path:</p>
                    </td>
                    <td>
                        <input type="text" name="doc_location">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">File Upload:</p>
                        <input type="file" id="fileupload" name="doc_file" accept=".jpg,.jpeg,.pdf,.doc,.docx,.xls,.xlsx, .mp3, .flac, .ogg, .wav" required>
                        <p class ="b-text">Max: 16MB</p>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        &nbsp;                 
                    </td>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Remarks:</p>
                    </td>
                    <td>
                        <textarea name="doc_remarks" id="remarksOut" cols="34" rows="4" maxlength="255"
                        onFocus="countChars('remarksOut','char_count4', 255)" onKeyDown="countChars('remarksOut','char_count4', 255)" onKeyUp="countChars('remarksOut','char_count4', 255)"></textarea>
                         
                        <br><text class ="label"><span id="char_count4"></span></text><br>
                    </td>
                </tr>
                
                <script>
                        function countChars(remarksOut, counter, max){
                        var count = max - document.getElementById(remarksOut).value.length;
                        document.getElementById(counter).innerHTML = count + " characters remaining"; 
                        }
                </script>
                
            </table>
            
            <br><br>
            
            <div align="right">
                <button type="reset"class="button">Reset</button>
                    <button type="submit"class="button" onclick="validateOutForm()">Save</button>
            </div>
            
            <script>
                function validateOutForm()
                {
                var a=document.forms["docuout"]["doc_title"].value;
                var b=document.forms["docuout"]["doc_dept"].value;
                var c=document.forms["docuout"]["doc_add"].value;
                var d=document.forms["docuout"]["doc_daterelease"].value;
                var e=document.forms["docuout"]["doc_file"].value;
                if (a==null || a=="",b==null || b=="",c==null || c=="",d==null || d=="", e==null || e==""){
                swal("Oops...", "Something went wrong! Please try again and make sure to fill out the important fields before submitting.", "error");;
                }     
                else if (a!=null || a!="",b!=null || b!="",c!=null || c!="",d!=null || d!="", e!=null || e!="") {
                swal("Success!", "Document record has been added successfully!", "success");;
                window.setTimeout(location.reload.bind(location), 4000);
                };
                }
               
                </script>
            
             </form>
            
            </div>
        </div>
        
        <%--out end --%>
        
        <%-- Insert Book --%>
        <iframe style="display: none;" id="iframe" name="book_iframe"></iframe>
        <div id ="insertBook" style="display: none">
            
            <div class="bodybox">
                    <div class="insrt-mainhdr" style="float:right;">
                        <text class="title">ADD BOOK</text>
                    </div>
            <br><br><br><br>
            
        <form method="POST" action="insertThisBook" name="book" enctype="multipart/form-data" target="book_iframe">
            <table>
                
                <tr>
                    <div class="insrt-hdr" style="float:left;">
                        <text class="header">BOOK DESCRIPTION</text>
                    </div>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Call Number:</p>
                    </td>
                    <td>
                        <input type="text" name="book_callno" style="width: 40%" required>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Title:</p>
                    </td>
                    <td>
                        <input type="text" name="book_title" required>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Author:</p>
                    </td>
                    <td>
                        <input type="text" name="book_author">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Year: </p>
                        
                    </td>
                    
                    <td>
                        <input type="number" name="book_year" style="width: 25%">
                    </td>
                </tr>
                
                
                <tr>
                    <td>
                        <p class ="b-text">Publication:</p>
                    </td>
                    <td>
                        <input type="text" name="book_pub">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Subject:</p>
                    </td>
                    <td>
                        <input type="text" name="book_subj">
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
                        <text class="header">BOOK LOCATION</text>
                    </div>               
                    </td>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Folder:</p>
                    </td>
                    <td>
                        <input type="text" name="book_folder">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Location:</p>
                    </td>
                    <td>
                        <input type="text" name="book_location">
                    </td>
                </tr>
                    <td>
                        <p class ="b-text">File Upload:</p>
                        <input type="file" id="fileupload" name="book_file" accept=".jpg,.jpeg,.pdf,.doc,.docx" required>
                        <p class ="b-text">Max: 16MB</p>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        &nbsp;                 
                    </td>
                        
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
                        function countChars(remarksBook, counter, max){
                        var count = max - document.getElementById(remarksBook).value.length;
                        document.getElementById(counter).innerHTML = count + " characters remaining"; 
                        }
                </script>
                
            </table>
            
            <br><br>
            
            <div align="right">
                <button type="reset"class="button">Reset</button>
                <button type="submit"class="button" onclick="validateBookForm()">Save</button>
            </div>
            
            <script>
                function validateBookForm()
                {
                var a=document.forms["book"]["book_callno"].value;
                var b=document.forms["book"]["book_title"].value;
                var c=document.forms["book"]["book_file"].value;
                if (a==null || a=="",b==null || b=="",c==null || c==""){
                swal("Oops...", "Something went wrong! Please try again and make sure to fill out the important fields before submitting.", "error");;
                }     
                else if (a!=null || a!="",b!=null || b!="",c!=null || c!="") {
                swal("Success!", "Book record has been added successfully!", "success");;
                window.setTimeout(location.reload.bind(location), 4000);
                };
                }
               
                </script>
            
            </form>    
        </div>      
      </div>
            
        <%-- End of Insert Book --%>
        
        <%-- Generate Report --%>
        <iframe style="display: none;" id="iframe" name="gen_iframe"></iframe>
        <div id ="generateReport" style="display: none">
            <div class="bodybox">
                    <div class="insrt-mainhdr" style="float:right;">
                        <text class="title">GENERATE REPORTS</text>
                    </div>
            <br><br><br><br>
            
            <form action ="genReportServ" name="generate" target="gen_iframe">
                
            <table>
                <tr>
                    <td>
                        <p class ="b-text">Type:</p>                 
                    </td>
                        
                    <td>
                        <input type="radio" name="doc_type"  value="Incoming"><text class ="b-text">&nbsp;Incoming</text> 
                        &nbsp;&nbsp;
                        <input type="radio" name="doc_type"  value="Outgoing"><text class ="b-text">&nbsp;Outgoing</text>
             
   
                                <br><br>
                                <input type="radio" name="stat_type"  value="Pending"><text class ="b-text">&nbsp;Pending</text>&nbsp;&nbsp;
                                <input type="radio" name="stat_type"  value="Settled"><text class ="b-text">&nbsp;Settled</text>&nbsp;&nbsp;
                                <input type="radio" name="stat_type"  value="Both"><text class ="b-text">&nbsp;Both</text>
 
                    </td>
                </tr>
               
                
                <tr>
                    <td>
                        <p class ="b-text">Date From: </p>
                    </td>
                    
                    <td>                       
                        <input type="date" name="gen_from">
                    </td>
                   
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Date To:</p>
                    </td>
                    
                    <td>                       
                        <input type="date" name="gen_to">
                    </td>
                   
                </tr>

                
            </table>
            
            <br><br>
            
            <div align="right">
                <button type="reset"class="button">Reset</button>
               <button type="submit"class="button" onclick="validateGenForm()">Save</button>
            </div>
            
            <script>
                function validateGenForm()
                {
                var a=document.forms["generate"]["doc_type"].value;
                var b=document.forms["generate"]["gen_from"].value;
                var c=document.forms["generate"]["gen_to"].value;
                if (a==null || a=="",b==null || b=="",c==null || c==""){
                swal("Oops...", "Something went wrong! Please try again and make sure to fill out the important fields before submitting.", "error");;
                }     
                else if (a!=null || a!="",b!=null || b!="",c!=null || c!="") {
                swal("Good job!", "Report request finished!", "success")
                }
                }
                
                </script>
            
            </form>
        </div>
            
        </div>
        
        <%-- End of Generate Report --%>
        
        <%-- User Accounts--%>
        
        <div id ="userAccounts" style="display: none">
            
            <div class="bodybox">
                    <div class="insrt-mainhdr" style="float:right;">
                        <text class="title">USER ACCOUNTS</text>
                    </div>
            <br><br><br><br>
            
            <iframe src="userAccounts" align="top" width="650" height="400">Please update your browser.</iframe>
            </div>
        </div>
            
        <%-- End of User Accounts --%>
        
        <%-- View Archive --%>
        
        <div id ="viewArchive" style="display: none">
            
            <div class="bodybox">
                    <div class="insrt-mainhdr" style="float:right;">
                        <text class="title">VIEW ARCHIVE</text>
                    </div>
            <br><br><br><br>
            
            <iframe src="viewArchive?pg=1" align="top" width="650" height="1000">Please update your browser.</iframe>
            </div>
        </div>
            
        <%-- End of View Archive --%>

        <%-- View Documents--%>
        
        <div id ="viewDocuments" style="display: none">
            
            <div class="bodybox">
                    <div class="insrt-mainhdr" style="float:right;">
                        <text class="title">DOCUMENTS</text>
                    </div>
            <br><br><br><br>
            
            <iframe src="viewDocuments?pg=1&cmt=<%=rs.getString("CMT")%>" align="top" width="650" height="1000" scrolling="no">Please update your browser.</iframe>
            </div>
        </div>
            
        <%-- End of View Documents --%>
        
        <%-- Trace User Activites --%>
        
        <div id ="traceActivities" style="display: none">
            
            <div class="bodybox">
                    <div class="insrt-mainhdr" style="float:right;">
                        <text class="title">TRACE ACTIVITIES</text>
                    </div>
            <br><br><br><br>
            
            <iframe src="traceActivities?pg=1" align="top" width="650" height="1000">Please update your browser.</iframe>
            </div>
        </div>
            
        <%-- End of Trace User Activites --%>
        
        <%-- View Books --%>
        
        <div id ="viewBooks" style="display: none">
            
            <div class="bodybox">
                    <div class="insrt-mainhdr" style="float:right;">
                        <text class="title">BOOKS</text>
                    </div>
            <br><br><br><br>
            
            <iframe src="viewBooks?pg=1" align="top" width="650" height="1000">Please update your browser.</iframe>
            </div>
        </div>
            
        <%-- End of View Books --%>
        
        <%-- View Pending--%>
        
        <div id ="viewPending" style="display: none">
            
            <div class="bodybox">
                    <div class="insrt-mainhdr" style="float:right;">
                        <text class="title">PENDING</text>
                    </div>
            <br><br><br><br>
            
            <iframe src="viewPending?cmt=<%=rs.getString("CMT").trim()%>" align="top" width="650" height="1000">Please update your browser.</iframe>
            </div>
        </div>
            
        <%-- End of View Pending --%>
        
        <%-- View Notif--%>
        
        <div id ="viewNotif" style="display: none">
            
            <div class="bodybox">
                    <div class="insrt-mainhdr" style="float:right;">
                        <text class="title">NOTIFICATIONS</text>
                    </div>
            <br><br><br><br>
            
            <iframe src="viewNotif?user_name=<%=rs.getString("USERNAME").trim()%>&count=<%=rsn.getString(1)%>" align="top" width="650" height="1000">Please update your browser.</iframe>
            </div>
        </div>
            
        <%-- End of View Notif --%>
        
        <%-- My Account --%>

        <%-- End of My Account --%>
        
        <div id="myacc" class="overlay">
        <div class="popup">
         <a class="close" href="#">&times;</a>
            <div class="content">
                            <center>
                                <text class="header">ACCOUNT INFORMATION</text>                            
            <table style="width:325px;">
                <tr>                
                    <td></td>
                </tr>                
                <tr>
                <tr>                
                    <td></td>
                </tr>                
                <tr>   
                    <td><text class="w-normal"><strong>Name</strong></text></td>
                    <td>
                        <text class="w-normal">
                        <%out.print(rs.getString("FNAME").trim());%>
                        <%out.print(rs.getString("LNAME").trim());%>
                        </text>
                    </td>
                </tr>
                
                <tr>                
                    <td><text class="w-normal"><strong>Committee</strong></text></td>
                    <td>
                        <text class="w-normal">
                        <%out.print(rs.getString("CMT").trim());%>
                        </text>
                    </td>
                </tr>

                <tr>                
                    <td><text class="w-normal"><strong>Username</strong></text></td>
                    <td>
                        <text class="w-normal">
                        <%out.print(rs.getString("USERNAME").trim());%>
                        </text>
                    </td>
                </tr>                            
             </table>
                
                <%}%>
            <%}%>
                            </center>
              </div>
            </div>
        </div>
                            
   </body>                         
        
        
    <script type="text/javascript">
        function insertDocuIn() {
            document.getElementById("insertDocuIn").style.display = "block";
            document.getElementById("insertDocuOut").style.display = "none";
            document.getElementById("footer-docu").style.display = "block";            
            document.getElementById("insertBook").style.display = "none";
            document.getElementById("generateReport").style.display = "none";
            document.getElementById("footer").style.display = "none";
            document.getElementById("footer-book").style.display = "none";
            document.getElementById("userAccounts").style.display="none";
            document.getElementById("searchMe").style.display="none";
            document.getElementById("viewDocuments").style.display = "none";
            document.getElementById("viewBooks").style.display = "none";
            document.getElementById("viewPending").style.display = "none";
            document.getElementById("viewNotif").style.display = "none"; 
            document.getElementById("traceActivities").style.display = "none";
            document.getElementById("viewArchive").style.display = "none";
        }
        
        function insertDocuOut() {
            document.getElementById("insertDocuIn").style.display = "none";
            document.getElementById("insertDocuOut").style.display = "block";
            document.getElementById("footer-docu").style.display = "block";            
            document.getElementById("insertBook").style.display = "none";
            document.getElementById("generateReport").style.display = "none";
            document.getElementById("footer").style.display = "none";
            document.getElementById("footer-book").style.display = "none";
            document.getElementById("userAccounts").style.display="none";
            document.getElementById("searchMe").style.display="none";
            document.getElementById("viewDocuments").style.display = "none";
            document.getElementById("viewBooks").style.display = "none";
            document.getElementById("viewPending").style.display = "none";
            document.getElementById("viewNotif").style.display = "none"; 
            document.getElementById("traceActivities").style.display = "none";
            document.getElementById("viewArchive").style.display = "none";
        }
        
        function insertBook() {
            document.getElementById("insertBook").style.display = "block";
            document.getElementById("footer-book").style.display = "block";
            document.getElementById("insertDocuIn").style.display = "none";
            document.getElementById("insertDocuOut").style.display = "none";
            document.getElementById("generateReport").style.display = "none";
            document.getElementById("footer").style.display = "none";
            document.getElementById("footer-docu").style.display = "none";
            document.getElementById("userAccounts").style.display="none";
            document.getElementById("searchMe").style.display="none";
            document.getElementById("viewDocuments").style.display = "none";
            document.getElementById("viewBooks").style.display = "none";
            document.getElementById("viewPending").style.display = "none";
            document.getElementById("viewNotif").style.display = "none"; 
            document.getElementById("traceActivities").style.display = "none";
            document.getElementById("viewArchive").style.display = "none";
        }
        
        function userAccounts() {    
            document.getElementById("userAccounts").style.display="block";
            document.getElementById("insertBook").style.display = "none";
            document.getElementById("footer-book").style.display = "none";
            document.getElementById("insertDocuIn").style.display = "none";
            document.getElementById("insertDocuOut").style.display = "none";
            document.getElementById("generateReport").style.display = "none";
            document.getElementById("footer").style.display = "none";
            document.getElementById("footer-docu").style.display = "none";
            document.getElementById("searchMe").style.display="none";
            document.getElementById("viewDocuments").style.display = "none";
            document.getElementById("viewBooks").style.display = "none";   
            document.getElementById("viewPending").style.display = "none";
            document.getElementById("viewNotif").style.display = "none"; 
            document.getElementById("traceActivities").style.display = "none";
            document.getElementById("viewArchive").style.display = "none";
        }
        
        function generateReport() {
            document.getElementById("generateReport").style.display = "block";
            document.getElementById("footer").style.display = "block";
            document.getElementById("insertDocuIn").style.display = "none";
            document.getElementById("insertDocuOut").style.display = "none";
            document.getElementById("insertBook").style.display = "none";
            document.getElementById("footer-docu").style.display = "none";
            document.getElementById("footer-book").style.display = "none";;
            document.getElementById("userAccounts").style.display="none";
            document.getElementById("searchMe").style.display="none";
            document.getElementById("viewDocuments").style.display = "none";
            document.getElementById("viewBooks").style.display = "none";
            document.getElementById("viewPending").style.display = "none";
            document.getElementById("viewNotif").style.display = "none"; 
            document.getElementById("traceActivities").style.display = "none";
            document.getElementById("viewArchive").style.display = "none";
        }
        
        function searchHere() {
            document.getElementById("searchMe").style.display="block";
            document.getElementById("generateReport").style.display = "none";
            document.getElementById("footer").style.display = "none";
            document.getElementById("insertDocuIn").style.display = "none";
            document.getElementById("insertDocuOut").style.display = "none";
            document.getElementById("insertBook").style.display = "none";
            document.getElementById("footer-docu").style.display = "none";
            document.getElementById("footer-book").style.display = "none";
            document.getElementById("userAccounts").style.display="none";
            document.getElementById("viewDocuments").style.display = "none";
            document.getElementById("viewBooks").style.display = "none";
            document.getElementById("viewPending").style.display = "none";
            document.getElementById("viewNotif").style.display = "none"; 
            document.getElementById("traceActivities").style.display = "none";
            document.getElementById("viewArchive").style.display = "none";
        }
        
        function viewDocuments() {
            document.getElementById("viewDocuments").style.display = "block";
            document.getElementById("insertBook").style.display = "none";
            document.getElementById("insertDocuIn").style.display = "none";
            document.getElementById("insertDocuOut").style.display = "none";
            document.getElementById("footer-book").style.display = "none";
            document.getElementById("generateReport").style.display = "none";
            document.getElementById("footer").style.display = "none";
            document.getElementById("footer-docu").style.display = "block";
            document.getElementById("userAccounts").style.display="none";
            document.getElementById("searchMe").style.display="none";
            document.getElementById("viewBooks").style.display = "none";
            document.getElementById("viewPending").style.display = "none";
            document.getElementById("viewNotif").style.display = "none"; 
            document.getElementById("traceActivities").style.display = "none";
            document.getElementById("viewArchive").style.display = "none";
        }
        
        function viewBooks() {
            document.getElementById("viewBooks").style.display = "block";
            document.getElementById("viewDocuments").style.display = "none";
            document.getElementById("insertBook").style.display = "none";
            document.getElementById("insertDocuIn").style.display = "none";
            document.getElementById("insertDocuOut").style.display = "none";
            document.getElementById("footer-book").style.display = "none";
            document.getElementById("generateReport").style.display = "none";
            document.getElementById("footer").style.display = "none";
            document.getElementById("footer-docu").style.display = "block";
            document.getElementById("userAccounts").style.display="none";
            document.getElementById("searchMe").style.display="none";
            document.getElementById("viewPending").style.display = "none";
            document.getElementById("viewNotif").style.display = "none"; 
            document.getElementById("traceActivities").style.display = "none";
            document.getElementById("viewArchive").style.display = "none";
        }        
        
        function viewPending() {
            document.getElementById("viewPending").style.display = "block";
            document.getElementById("viewDocuments").style.display = "none";
            document.getElementById("insertBook").style.display = "none";
            document.getElementById("insertDocuIn").style.display = "none";
            document.getElementById("insertDocuOut").style.display = "none";
            document.getElementById("footer-book").style.display = "none";
            document.getElementById("generateReport").style.display = "none";
            document.getElementById("footer").style.display = "none";
            document.getElementById("footer-docu").style.display = "block";
            document.getElementById("userAccounts").style.display="none";
            document.getElementById("searchMe").style.display="none";
            document.getElementById("viewBooks").style.display = "none";
            document.getElementById("viewNotif").style.display = "none"; 
            document.getElementById("traceActivities").style.display = "none";  
            document.getElementById("viewArchive").style.display = "none";
        }
        
        function viewNotif() {
            document.getElementById("viewNotif").style.display = "block";
            document.getElementById("viewBooks").style.display = "none";
            document.getElementById("viewDocuments").style.display = "none";
            document.getElementById("insertBook").style.display = "none";
            document.getElementById("insertDocuIn").style.display = "none";
            document.getElementById("insertDocuOut").style.display = "none";
            document.getElementById("footer-book").style.display = "none";
            document.getElementById("generateReport").style.display = "none";
            document.getElementById("footer").style.display = "none";
            document.getElementById("footer-docu").style.display = "none";
            document.getElementById("userAccounts").style.display="none";
            document.getElementById("searchMe").style.display="none";
            document.getElementById("viewPending").style.display = "none";
            document.getElementById("traceActivities").style.display = "none";
            document.getElementById("viewArchive").style.display = "none";
        } 
        function traceActivities() {
            document.getElementById("traceActivities").style.display = "block";
            document.getElementById("viewNotif").style.display = "none";
            document.getElementById("viewBooks").style.display = "none";
            document.getElementById("viewDocuments").style.display = "none";
            document.getElementById("insertBook").style.display = "none";
            document.getElementById("insertDocuIn").style.display = "none";
            document.getElementById("insertDocuOut").style.display = "none";
            document.getElementById("footer-book").style.display = "none";
            document.getElementById("generateReport").style.display = "none";
            document.getElementById("footer").style.display = "none";
            document.getElementById("footer-docu").style.display = "block";
            document.getElementById("userAccounts").style.display="none";
            document.getElementById("searchMe").style.display="none";
            document.getElementById("viewPending").style.display = "none";
            document.getElementById("viewArchive").style.display = "none";
        }
        
        function viewArchive() {
            document.getElementById("viewArchive").style.display = "block";
            document.getElementById("traceActivities").style.display = "none";
            document.getElementById("viewNotif").style.display = "none";
            document.getElementById("viewBooks").style.display = "none";
            document.getElementById("viewDocuments").style.display = "none";
            document.getElementById("insertBook").style.display = "none";
            document.getElementById("insertDocuIn").style.display = "none";
            document.getElementById("insertDocuOut").style.display = "none";
            document.getElementById("footer-book").style.display = "none";
            document.getElementById("generateReport").style.display = "none";
            document.getElementById("footer").style.display = "none";
            document.getElementById("footer-docu").style.display = "none";
            document.getElementById("userAccounts").style.display="none";
            document.getElementById("searchMe").style.display="none";
            document.getElementById("viewPending").style.display = "none";
        }
    </script>
</html>