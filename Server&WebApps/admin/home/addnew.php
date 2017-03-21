<?php
$active="home";
include('../includes/header.php');
?>


            <section id="content">
                <div class="container">
                     <div class="card">
                        <div class="card-header">
                            <h2>Add new Survey <small>Select What Kind of Survey you want and publish for results.</small></h2>
                        </div>

                        <div class="card-body card-padding">
                            <div role="tabpanel">
                                <ul class="tab-nav" role="tablist">
                                    <li class="active"><a href="#sq" aria-controls="sq" role="tab" data-toggle="tab">Single Choice</a></li>
                                    <li><a href="#mcq" aria-controls="mcq" role="tab" data-toggle="tab">Multiple Choice / Polling</a></li>
                                    <li><a href="#rtng" aria-controls="rtng" role="tab" data-toggle="tab">Rating</a></li>
                                </ul>

                                <div class="tab-content">
                                    <div role="tabpanel" class="tab-pane active" id="sq">
									<form action="addnewapi.php" method="post" enctype="multipart/form-data">
			      <input type="hidden" class="form-control" name="type" value="1" required>
                                       
									<div class="col-sm-12">
                                    <div class="form-group">
                                        <div class="fg-line">
                                            <input type="text" class="form-control" name="topic" placeholder="Topic" required>
                                        </div>
                                    </div>
                                </div>
									  <div class="input_fields_wrap ">
							<div>
							</div>
							<div class="add_field_button btn palette-Green bg waves-effect">Add More Options</div>
							<br/><br/>
							</div>
									<div class="col-sm-12 text-center">
									<button type="submit" name ="type1" class="btn btn palette-Green bg waves-effect">Save</button>
									</div>
									
									</form>
									  </div>
									  
									  
                                    <div role="tabpanel" class="tab-pane" id="mcq">
                                    
									<form action="addnewapi.php" method="post" enctype="multipart/form-data">
									<input type="hidden" class="form-control" name="type" value="2" required>
                                       
									<div class="col-sm-12">
                                    <div class="form-group">
                                        <div class="fg-line">
                                            <input type="text" class="form-control" name="topic" placeholder="Topic" required>
                                        </div>
                                    </div>
                                </div>
									  <div class="input_fields_wrap ">
							<div> 
							</div>
							<div class="add_field_button btn palette-Green bg waves-effect">Add More Options</div>
							<br/><br/>
							</div>
						<div class="col-sm-12 text-center">
									<button type="submit" name="type1" class="btn btn palette-Green bg waves-effect">Save</button>
									</div>
									</form>
									 </div>
									 
									 
                                    <div role="tabpanel" class="tab-pane" id="rtng">
                                    
									<form action="addnewapi.php" method="post" enctype="multipart/form-data">
									      <input type="hidden" class="form-control" name="type" value="3" required>
                                    <div class="row">  
									<div class="col-sm-12">
                                    <div class="form-group">
                                        <div class="fg-line">
                                            <input type="text" class="form-control" name="topic" placeholder="Topic" required>
                                        </div>
                                    </div>
									</div>
									
									<div class="col-sm-12 text-center">
									<button type="submit" name="type3" class="btn btn palette-Green bg waves-effect">Save</button>
									</div>
									
									</div>
									</form>
									</div>
                                    
                                </div>
                            </div>

                            <br/>
                            <br/>

                        </div>
                    </div>

                </div>
				</section>


    <script src="../vendors/bower_components/jquery/dist/jquery.min.js"></script>				
				<script>
   $(document).ready(function() {
    var max_fields      = 5; //maximum input boxes allowed	
    var wrapper         = $(".input_fields_wrap"); //Fields wrapper
    var add_button      = $(".add_field_button"); //Add button ID
    
    var x = 1; //initlal text box count
    $(add_button).click(function(e){ //on add input button click
        e.preventDefault();
        if(x < max_fields){ //max input box allowed
            x++; //text box increment
            $(wrapper).append('<div> <div class="row" id="wv"> <div class="col-sm-6"><div class="form-group"><div class="fg-line"><input type="text" class="form-control" name="dynfields[]" placeholder="Write Options Here"></div></div></div></div>	</div><br/><a href="#" class="remove_field"><div class="btn btn-danger btn-icon waves-effect waves-circle waves-float"><i class="zmdi zmdi-close"></i></div>        Remove This Option<br/><br/></a>'); //add input box
        }
    });
    
    $(wrapper).on("click",".remove_field", function(e){ //user click on remove text
        e.preventDefault(); 
		$(this).prev().remove();		
		$(this).prev().remove();				
		$(this).remove();
		x--;
		
    })
});
    </script>  
				<?php
include('../includes/footer.php');
?>