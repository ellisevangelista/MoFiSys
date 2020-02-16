<%-- 
    Document   : searchResults
    Created on : Feb 2, 2017, 9:02:43 PM
    Author     : ellisevangelista
--%>

<%@ page import="java.util.*" %>
 <%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <link rel="stylesheet" href="css/design.css">
    </head>
    <body>
        <%
            String title = (String) request.getAttribute("title");
            %>
            Search results for <strong><%=title%></strong> 
            
            <br><br>
        <table width="625px" align="center">
            <tr>
                <td colspan=4 align="center">
                    <b><text class="header">Document Records</text></b></td>
            </tr>
            <tr>
                <th><b>DOC. NO.</b></th>
                <th><b>TITLE</b></th>
                <th><b>DEPARTMENT</b></th>
                <th><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></th>
            </tr>
            <%
                int count = 0;
                String color = "#515453";
                if (request.getAttribute("piList") != null) {
                    ArrayList al = (ArrayList) request.getAttribute("piList");
                    System.out.println(al);
                    Iterator itr = al.iterator();
                    while (itr.hasNext()) {

                        if ((count % 2) == 0) {
                            color = "#FFE164";
                        }
                        count++;
                        ArrayList pList = (ArrayList) itr.next();
            %>
            <tr >
                <td><%=pList.get(0)%></td>
                <td><%=pList.get(1)%></td>
                <td><%=pList.get(2)%></td>
                <% if (pList.get(3).equals("Incoming")){%>
                <td style="padding:0px;"><a href="viewDocuFull?docno=<%=pList.get(0)%>&phase=onein" class="button">View</a></td>
                <%}%>
                <% if (pList.get(3).equals("Outgoing")){%>
                <td style="padding:0px;"><a href="viewDocuFull?docno=<%=pList.get(0)%>&phase=oneout" class="button">View</a></td>
                <%}%>
            </tr>
            <%
                
                    }
                }
                if (count == 0) {
            %>
            <tr>
                <td colspan=4 align="center"><b>No Record Found..</b></td>
            </tr>
            <%}
            %>
        </table>
    </body>
            <style>
            body {
                color:#515453;
                font-family: Segoe UI Symbol;
            }
            table {
                background-color: #FFE164;
            }

            th {
                padding: 15px;
                text-align: center;
                }

            td {
                padding: 15px;
                text-align: center;
                background-color: white;
                }
        </style>
</html>