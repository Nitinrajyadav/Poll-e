<?php
				include('../../library/dbConnect.php');
				
session_start();
if (isset($_POST['type1'])) {
$type=$_POST['type'];	
$topic=$_POST['topic'];

$query="INSERT INTO adminentry(name,type,admin) VALUES ('$topic','$type','1')";
                            if(mysqli_query($con,$query))
                            {
								$entryId = mysqli_insert_id($con);
								if ($_POST['dynfields']) {

								foreach ( $_POST['dynfields'] as $key=>$value ) {

								$options = mysql_real_escape_string($value);
								
$query2="INSERT INTO `list-item` (entry_id,name) VALUES ('$entryId','$options')";
mysqli_query($con,$query2);

}

	header("location:addnew.php?token=". $_SESSION['token'] );
}
}
}
else if (isset($_POST['type3'])) {
$type=$_POST['type'];	
$topic=$_POST['topic'];

$query="INSERT INTO adminentry(name,type,admin) VALUES ('$topic','$type','1')";
                            if(mysqli_query($con,$query))
                            {
								$entryId = mysqli_insert_id($con);
								
$query2="INSERT INTO `list-item` (entry_id,name) VALUES ('$entryId','Rating')";
mysqli_query($con,$query2);

	header("location:addnew.php?token=". $_SESSION['token'] );

}
}
?>
