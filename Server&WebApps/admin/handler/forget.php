<?php
session_start();
require('../../library/dbConnect.php');
if(!isset($_SESSION['id']))
{
	header("location: ../index.php?token=". $_SESSION['token'] );
}

// Admin Forget Password

if(isset($_POST['forget']))
{
	$email=$_POST['email'];
	$_SESSION['id']=$email;
	if($email== NULL)
	{
		session_unset();
		session_destroy();
	    header("location: ../index.php?token=". $_SESSION['token'] );
	}

	if(isset($_SESSION['id']))
	{	
		$result = mysqli_query($con,"SELECT username,password FROM admin where email='$email'");
		$row = mysqli_fetch_array($result);
		
		
		
		if($row["username"]!='')
		{
			echo $username=$row["username"];
			echo $password=$row["password"];
			
			//Send Email to the admin with password.
			// code goes here
			
		}
		else
		{
		    header("location: ../index.php?token=". $_SESSION['token'] );
			session_unset();
			session_destroy();
			?>
			<script>
				alert('Invalid Attempt, Please Try Later');
			</script>	
			<?php
		}
		mysqli_close($con);
	}
	else
	{
	    header("location: ../index.php?token=". $_SESSION['token'] );
	    session_unset();
		session_destroy();
	}
}	
?>