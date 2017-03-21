<?php
session_start();
require('../../library/dbConnect.php');
if(!isset($_SESSION['id']))
{
	header("location: ../index.php?token=". $_SESSION['token'] );
}
// Admin Login
if(isset($_POST['logIn']))
{
	$username=$_POST['username'];
	$password=$_POST['password'];
	$_SESSION['id']=$username;
	if($username== NULL)
	{
		session_unset();
		session_destroy();
	    header("location: ../index.php?token=". $_SESSION['token'] );
	}
	if($password== NULL)
	{
		session_unset();
		session_destroy();
	    header("location: ../index.php?token=". $_SESSION['token'] );
	}
	if(isset($_SESSION['id']))
	{	
		$result = mysqli_query($con,"SELECT username,password FROM admin where username='$username' and password='$password'");
		$row = mysqli_fetch_array($result);
		if($row["username"]==$username && $row["password"]==$password)
		{
			$session_id = session_id();
			$tokenGeneric = $session_id.$_SERVER["SERVER_NAME"];
			$data=$username.$password.TIME();
		    $_SESSION['token'] = hash('md5', $tokenGeneric.$data);
		    mysqli_query($con,"UPDATE admin SET token='".$_SESSION['token']."' WHERE username='$username'");
		    header("location: ../home/index.php?token=". $_SESSION['token'] );
		}
		else
		{
		    header("location: ../index.php?token=". $_SESSION['token'] );
			session_unset();
			session_destroy();
			?>
			<script>
				alert('Invalid login');
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

