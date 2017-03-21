

<?php

// JSON decode
$signUp = file_get_contents('php://input');


$result = json_decode($signUp, true);

//$adhr_id= $result['hof_Details'];
//$adhr_id=$adhr_id['AADHAR_ID'];
//$file = "resident_signup/".$adhr_id.".txt";

$bhamashahId= $result['bhamashahId'];




 

 require_once('../library/dbConnect.php');
 $sql = "SELECT * FROM user WHERE BHAMASHAH_ID='$bhamashahId'";
 
 $check = mysqli_fetch_array(mysqli_query($con,$sql));
 
 if(isset($check)){
	$fetch = mysqli_query($con,"SELECT * FROM user WHERE BHAMASHAH_ID=$bhamashahId");
	
	while ($row = mysqli_fetch_assoc($fetch)) {
		
		foreach ($row as $key => $value) {
			 
			if($value!=NULL && $value!=null && $value!=''){
				$data[$key]=$value;
			}
			
			} 
			
			$resident_details = $data; }
			
			
 }
 
 else{
	// fetch data from api for the bhamashah id given
	

	
	$resident_details=array('value' => '2','message' =>'This function is not allowed right now.');
 
 }
 
 
echo json_encode(compact('resident_details'),JSON_FORCE_OBJECT);
			mysqli_close($con);

 exit();
 
 ?>
 
 
 
 <?php 
// commented for later
$signUp = file_get_contents('php://input');

$result = json_decode($signUp, true);

$bhamashahId=$result['bhamashahId'];




// below $option=array('trace',1); 
// correct one is below 
$options = array(
                'soap_version'=>SOAP_1_1,
                'exceptions'=>true,
                'trace'=>1,
                'cache_wsdl'=>WSDL_CACHE_NONE
            );
$client=new SoapClient('http://164.100.222.243/Service/Action/Action.wsdl',$option); 

//build the parameters for the SearchTargetGroup
$questionTargetGroup = array (
    "CbhamashahInfo" => "10,,infoFlag='PFE'"
);

$response = $client->searchTargetGroup($questionTargetGroup, 500, 1, 1, "passstring"); 
print_r($response);
exit();
?> 



<?php
$signUp = file_get_contents('php://input');

$result = json_decode($signUp, true);

$bhamashahId=$result['bhamashahId'];




$client = new SoapClient('http://164.100.222.243/Service/Action/Action.wsdl');
var_dump($client->__getFunctions());


exit();
?>