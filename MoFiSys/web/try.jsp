
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="photos/favicon.png" type="image/png" sizes="16x16">
        <title>USTFU - MOFISYS</title>
        <link rel="stylesheet" href="css/home.css">
    </head>
    <body background = "photos/trial2.png">
            
            <div id ="buttons">
                <a href="try2.jsp" class="button" style="position:absolute;left:250px;top:360px;">Create New Account</a><br>
            </div>
        
            
     <div id="signin">
        <div class="ovrectangle">
            <form action="dashboard" method="POST">
            <input readonly type="text" value="SELECT" name="function" hidden>
            <input type="text" id="name" name="uname" placeholder="Username" required>
            <input type="password" id="pass" name="pass" placeholder="Password" required>
            <a href="#forgotpass"><p class="home" style="position:absolute;right:-215px;top:265px;"><u><span style="background-color: #FFE164">Forgot password?</span></u></p></a>
            <input type="submit" class="fbutton" style="position:absolute;right:-224px;top:320px;" value="SIGN IN"><br>
            </form>
        </div>
     </div>
        

        
     <div id="forgotpass" class="overlay">
        <div class="popup">
         <a class="close" href="#">&times;</a>
            <div class="content">
                            <center>
                        <p class="title">FORGOT PASSWORD</p>
                        <p class="text" style="position:absolute;top:50px;">Don't worry, you'll be up and running in seconds!<br>
                            Enter your username below and we will notify your admin regarding your password.</p>
                            </center>
                        <form action ="dashboard">
                        <input readonly type="text" value="UPDATE" name="function" hidden/>
                        <input type ="text" required name="ChUsername" id="admin" placeholder="Username" style="position:absolute;top:140px;right:100px;">
                        <input type="submit" class="fbutton" value="SEND" style="position:absolute;right:168px;top:179.5px;"><br>
                        </form>
                    </div>
       
                </form>
            </div>
        </div>
  
    </body>
    
    
</html>
