<?php
error_reporting(0);
$uid = file_get_contents('php://input');


$result = json_decode($uid, true);

$KYCId= $result['KYCid'];
 require_once('../library/dbConnect.php');
 
 
 
 /* change character set to utf8 */
if (!$con->set_charset("utf8")) {
    printf("", $con->error);
    exit();
} else {
    printf("", $con->character_set_name());
}



 $fetchdoc =  mysqli_query($con,"SELECT * FROM adminentry");
	$documents=array();

 while ($row = mysqli_fetch_assoc($fetchdoc)) {
		$data = array();
		$sd   = array();
		foreach ($row as $key => $value) {
		 $data[$key]=$value;
		
		}
		 $entry_id = $data['id'];
		 $admin_id = $data['admin'];
		 
		 $result = mysqli_query($con, "SELECT * FROM `user_response` WHERE entry_id='$entry_id' AND user_id='$KYCId'"); // no need of extra quote
if ($result->num_rows == 0 ){
	$data['user-response']= "0";
}
else{
	$data['user-response']= "1";
}
		
		
		$fetchdoc3 = mysqli_query($con,"SELECT * FROM `admin` WHERE id=$admin_id");
		if($rowdoc3 = mysqli_fetch_assoc($fetchdoc3)){
			 $data['adminName']= $rowdoc3['role'];
			 
		}
		
		
		$fetch2 =  mysqli_query($con,"SELECT * FROM `list-item` WHERE entry_id=$entry_id");

		
        while ($row2 = mysqli_fetch_assoc($fetch2)) {
            $data2 = array();
            foreach ($row2 as $key2 => $value2) {
                $data2[$key2] = $value2;
			} 
			
		$sd[] = $data2;
		}
		 
		 
		/*$fetchdoc2 = mysqli_query($con,"SELECT * FROM user_doc WHERE doc_id='$document_id' AND user_id='$KYCId' AND verification!=''");
		if($rowdoc2 = mysqli_fetch_assoc($fetchdoc2)){
			 $data['user_doc_id']= $rowdoc2['verification'];
			 
			 $data['status']= $rowdoc2['status'];
			 
			 
			 //  101 - Verified
			 //  100 - Pending
			 //  102 - Cancel
		}*/
		
		
		
	
	 $data['options'] = $sd;
	 $documents[] = $data;
			
 }
  echo json_encode(compact('documents'));
	
 
 
mysqli_close($con);

 exit();
 
 ?>
 
 