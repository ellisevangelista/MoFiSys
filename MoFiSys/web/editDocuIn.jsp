<%-- 
    Document   : editdocuin
    Created on : 01 22, 17, 7:57:47 PM
    Author     : Laurenz
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ResultSet docuin = (ResultSet)session.getAttribute("docuin");
    String com = (String)session.getAttribute("cmt");
    String phase = (String)session.getAttribute("phase");
    while(docuin.next()){
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
        <form action="updateDocuments" target="my_iframe">
                 <table>
                
                <tr>
                    <td>
                        <div class="insrt-hdr" style="float:left; width:230px;">
                            <text class="header2">DOCUMENT DESCRIPTION</text>
                        </div>
                    </td>  
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Title:</p>
                    </td>
                    <td>
                        <input type="text" name="doc_title" id="doc_title" value="<%=docuin.getString("TITLE").trim()%>">
                    </td>
                </tr>
                
                
                <tr>
                    <td>
                        <p class ="b-text">Department:</p>
                    </td>
                    <td>
                        <input type="text" name="doc_dept" value="<%=docuin.getString("DEPARTMENT").trim()%>">
                    </td>
                </tr>
                
                
                 <tr>
                    <td>
                        <p class ="b-text">Brief Description:</p>                 
                    </td>
                        
                    <td>
                         <textarea name="doc_dscrptn" id="dscrptnIn" cols="34" rows="4" maxlength="255"
                             onFocus="countChars('dscrptnIn','char_count', 255)" onKeyDown="countChars('dscrptnIn','char_count', 255)" onKeyUp="countChars('dscrptnIn','char_count', 255)">              
                         </textarea>
                         
                        <br><text class ="label"><span id="char_count"></span></text><br>
                    </td>
                </tr>
                
                <script>
                        document.getElementById("dscrptnIn").defaultValue = "<%=docuin.getString("DOC_DSCRPTN").trim()%>";                    
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
                        <input type="text" name="doc_rcvd" value="<%=docuin.getString("DOC_RCVD").trim()%>">
                    </td>
                </tr>
                <tr>
                    <td>
                         <p class ="b-text">Incoming Date: </p>            
                    </td>
                        
                    <td>
                         <input type="date" name="doc_datercvd" value="<%=docuin.getString("DATERECEIVED").trim()%>">
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
                        <text class="header2">DOCUMENT STATUS</text>
                        </div>              
                    </td>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Status:
                        </p>                 
                    </td>
                    <%
                        String stat=docuin.getString("STATUS");
                        if (stat.equals("Settled")){%>    
                    <td>
                        <input type="radio" name="doc_status" onclick="javascript:yesnoCheck();" id="yesCheck" value="Pending"><text class ="b-text">&nbsp;Pending</text> 
                        &nbsp;&nbsp;
                        <input type="radio" name="doc_status" onclick="javascript:yesnoCheck();" value="Settled" checked id="yesCheck"><text class ="b-text">&nbsp;Settled</text>
             
                            <div id="ifYes" style="display:none">
                                <br><br>
                                <input type="checkbox" name="doc_notif">
                                <text class ="b-text">
                                Notify me in: <input type="date" name="doc_notifDate" value="2050-01-01">
                                </text>
                                <br><br><br>
                            </div>                                  
                    </td>
                    <%}else{%>
                    <td>
                        <input type="radio" name="doc_status" onclick="javascript:yesnoCheck();" id="yesCheck" checked value="Pending"><text class ="b-text">&nbsp;Pending</text> 
                        &nbsp;&nbsp;
                        <input type="radio" name="doc_status" onclick="javascript:yesnoCheck();" value="Settled" id="yesCheck"><text class ="b-text">&nbsp;Settled</text>
             
                            <div id="ifYes" style="display:none">
                                <br><br>
                                <input type="checkbox" name="doc_notif">
                                <text class ="b-text">
                                Notify me in: <input type="date" name="doc_notifDate" value="<%=docuin.getString("NOTIFDATE").trim()%>">
                                </text>
                                <br><br><br>
                            </div>                                  
                    </td>
                    <%}%>
                </tr>
                
    <script>
         function yesnoCheck() {
            if (document.getElementById('yesCheck').checked) {
                document.getElementById('ifYes').style.display = 'block';
                }
                else document.getElementById('ifYes').style.display = 'none';
            }
    </script>
               
                
                <%if(com.equals("Administrative")){%>
                <tr>
                    <td>
                        <p class ="b-text">Sensitivity:</p>
                    </td>
                    <%String sens=docuin.getString("SENSITIVITY");%>
                    <td>
                        <input type="radio" name="doc_sensitivity" <%if(sens.equals("None")){%>checked<%}%> value="None"><text class ="b-text">&nbsp;None</text> 
                        &nbsp;&nbsp;
                        <input type="radio" name="doc_sensitivity" <%if(sens.equals("Low")){%>checked<%}%> value="Low"><text class ="b-text">&nbsp;Low</text>
                        &nbsp;&nbsp;
                        <input type="radio" name="doc_sensitivity" <%if(sens.equals("High")){%>checked<%}%> value="High"><text class ="b-text">&nbsp;High</text>                     
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;            
                    </td>
                </tr>
                <%}%>
                
                <tr>
                    <td>
                        <div class="insrt-hdr" style="float:left;">
                        <text class="header2">DOCUMENT LOCATION</text>
                    </div>               
                    </td>
                        
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Folder:</p>
                    </td>
                    <td>
                        <input type="text" name="doc_folder" value="<%=docuin.getString("DOC_FOLDER").trim()%>">
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <p class ="b-text">Location Path:</p>
                    </td>
                    <td>
                        <input type="text" name="doc_location" value="<%=docuin.getString("LOCATION").trim()%>">
                    </td>
                </tr>
                <%--
                <tr>
                    <td>
                        <p class ="b-text">File Upload:</p>
                        <input type="file" id="fileupload" name="doc_file" accept=".jpg,.jpeg,.pdf,.doc,.docx,.xls,.xlsx">
                    </td>
                </tr>
                --%>
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
                        document.getElementById("remarksIn").defaultValue = "<%=docuin.getString("DOC_REMARKS").trim()%>";
                        function countChars(remarksIn, counter, max){
                        var count = max - document.getElementById(remarksIn).value.length;
                        document.getElementById(counter).innerHTML = count + " characters remaining"; 
                        }
                </script>
                    
                <input type="hidden" name="docno" value="<%=docuin.getString("DOCNO").trim()%>">
                <input type="hidden" name="type" value="in">
            </table>
            
                <br/>
                <%if (phase.equals("onein2")){%>
                <button onclick="confirm2()" class="update" style="float:right;"/>UPDATE</button><br/>
                <%}else{%>
                <button onclick="confirm()" class="update" style="float:right;"/>UPDATE</button><br/>
                <%}%>
         <script>
        function confirm(){
            swal({
                title: "Are you sure?",
                text: "Make sure to fill out all the important fields!",
                type: "warning",
                confirmButtonColor: "#FFE164",
                confirmButtonText: "Update Document",
                },
            function(){
                window.location.href="viewDocuments?pg=1&cmt=<%=com%>";
                });
                }
            </script>
            
            <script>
        function confirm2(){
            swal({
                title: "Are you sure?",
                text: "Make sure to fill out all the important fields!",
                type: "warning",
                confirmButtonColor: "#FFE164",
                confirmButtonText: "Update Document",
                },
            function(){
                        
        window.location.href="viewPending?cmt=<%=com%>";
                
                });
                }
            </script>
            </form>
        
           
    </body>
</html>
<%}%>
