<?php
session_start();
error_reporting(0);
require('../../library/dbConnect.php');
$token=$_GET['token'];
$result = mysqli_query($con,"SELECT token from admin WHERE username='".$_SESSION['id']."'");
$row = mysqli_fetch_array($result);
if($row["token"] != $token)
{
	session_unset();
	session_destroy();
	header("location: ../index.php?token=". $_SESSION['token'] );
}
if(!isset($_SESSION['id']))
{
	session_unset();
	session_destroy();
	header("location: ../index.php?token=". $_SESSION['token'] );
}
$user=$_SESSION['id'];

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ADMIN AREA | EASY SURVEY | BARSPAN</title>

    <!-- Vendor's CSS -->
    <link href="../vendors/bower_components/fullcalendar/dist/fullcalendar.min.css" rel="stylesheet">
    <link href="../vendors/bower_components/animate.css/animate.min.css" rel="stylesheet">
    <link href="../vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css" rel="stylesheet">
    <link href="../vendors/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css" rel="stylesheet">        
    <link href="../vendors/bower_components/google-material-color/dist/palette.css" rel="stylesheet">
	
        <link href="../vendors/bower_components/bootstrap-select/dist/css/bootstrap-select.css" rel="stylesheet">

    <!-- CSS -->
    <link href="../css/app.min.1.css" rel="stylesheet">
    <link href="../css/app.min.2.css" rel="stylesheet"> 
<style>
@media (max-width:768px){
	.size-1of2, .size-2of2 {
    width: 100%;
}
}

</style>
</head>

<body data-ma-header="teal">
    <header id="header" class="media">
        <div class="pull-left h-logo">
            <a href="../index.php?token=<?php echo $_SESSION['token']; ?>" class="hidden-xs">
                BARSPAN EASYSURVEY
                <small>Admin Dashboard </small>
            </a>

            <div class="menu-collapse" data-ma-action="sidebar-open" data-ma-target="main-menu">
                <div class="mc-wrap">
                    <div class="mcw-line top palette-White bg"></div>
                    <div class="mcw-line center palette-White bg"></div>
                    <div class="mcw-line bottom palette-White bg"></div>
                </div>
            </div>
        </div>

     

    </header>

    <section id="main">

        <!--Side Nav-->
        <aside id="s-main-menu" class="sidebar">
            <div class="smm-header">
                <i class="zmdi zmdi-long-arrow-left" data-ma-action="sidebar-close"></i>
            </div>
            <br><br><br><br><br><br> <!-- align menu -->

            <ul class="main-menu">
                <li><a href="../home/<?php echo "?token=". $_SESSION['token']?>"><i class="zmdi zmdi-home"></i> Dashboard</a></li>
                <li><a href="../home/addnew.php<?php echo "?token=". $_SESSION['token']?>"><i class="zmdi zmdi-menu"></i> Add New Survey</a></li>
				
                
				 <li>
                    <a href="../home/history.php<?php echo "?token=". $_SESSION['token']?>" data-ma-action="submenu-toggle"><i class="zmdi zmdi-collection-text"></i> History</a>
					
                </li>
				
				
            </ul>
        </aside>
