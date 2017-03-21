<?php
$user = file_get_contents('php://input');

require_once('../library/dbConnect.php');

$payLoad = json_decode($user, true);
$KYCId= $payLoad['KYCid'];
$entry_id= $payLoad['entry_id'];
$type= $payLoad['type'];
if($type!=3){
foreach ($payLoad['options'] as $key => $val) {
    $sql1 = "INSERT INTO user_response(user_id , entry_id, listItem_id) VALUES ('$KYCId','$entry_id','$val')";
	mysqli_query($con,$sql1);
	
	
	
	$updatesql="UPDATE `list-item` SET count=count+1 WHERE id=$val";
	mysqli_query($con,$updatesql);
	}
}
elseif($type=3){
	$val= $payLoad['options'];
	$sql1 = "INSERT INTO user_response(user_id , entry_id, listItem_id) VALUES ('$KYCId','$entry_id','$val')";
	mysqli_query($con,$sql1);
	
	$updatesql="UPDATE `list-item` SET count=count+1 WHERE id=$val";
	mysqli_query($con,$updatesql);
}
$userResponse=array('message' =>'Response Recorded.');
 
  echo json_encode(compact('userResponse'));
?>