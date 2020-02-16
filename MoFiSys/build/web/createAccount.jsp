
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="photos/favicon.png" type="image/png" sizes="16x16">
        <title>USTFU - MOFISYS</title>
        <link rel="stylesheet" href="css/home.css">
    </head>
    
    <body background = "photos/create_home.png">
            
            <div id ="buttons">
                <a href="index.html" class="button" style="position:absolute;left:250px;top:360px;">Sign In Here</a><br>
            </div>
        
            
        
     <div id="new">
        <div class="ovrectangle">
           <form name = "myform" action="dashboard" method="POST">
            <input readonly type="text" value="INSERT" name="function" hidden/>
            <input type="text" id="fname" name="fname" placeholder="First Name" required>
            <input type="text" id="lname" name="lname" placeholder="Last Name" required>
            <select name="cmt" id="cmt" required>
                <option disabled selected hidden style="color:white;">User Type</option>
                <option value="Administrative"> Administrative</option>
                <option value="User"> User </option>
            </select>
            <input type="text" id="uname" name="uname" placeholder="Employee ID" required>
            <input type="date" id="cpass" name="pass" placeholder="Birthday" required>
            <br>
            <span id="confirmMessage" class="confirmMessage" style="position:absolute;right:50px;top:320px;"></span>
            <a href="#createacc" class="log" style="position:absolute;right:-224px;top:400px;">CREATE</a><br> 
        </div>
    </div>
         
     <div id="createacc" class="overlay">
        <div class="popup">
         <a class="close" href="#">&times;</a>
            <div class="content">
                <br>
                <center><p class="text">Enter admin's username and password</p></center>
                <input type ="text" required name="admin_uname" id="admin" placeholder="Username" style="position:absolute;top:90px;right:100px;">
                <input type ="password" required name="admin_pass" id="adminpass" placeholder="Password"style="position:absolute;top:120px;right:100px;">
                <input type="submit" class="fbutton" value="VERIFY" style="position:absolute;right:170px;top:180px;"><br>
                </form>
            </div>
        </div>
    </div>
        
    
    </body>
    
</html>
