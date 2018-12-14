<?php
	require_once 'update_user_info.php';
	$db = new update_user_info();
	  
	// json response array
	$response = array("error" => FALSE);
	  
	if (isset($_POST['name']) && isset($_POST['surname']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['sport']) && isset($_POST['team'])) {
	 
	    // receiving the post params
	    $name = $_POST['name'];
	    $surname = $_POST['surname'];
	    $email = $_POST['email'];
	    $password = $_POST['password'];
	    $gender = $_POST['sport'];
	    $age = $_POST['team'];
	 
	    // check if user is already existed with the same email
	    if ($db->CheckExistingUser($email)) {
	        // user already existed
	        $response["error"] = TRUE;
	        $response["error_msg"] = "User already existed with " . $email;
	        echo json_encode($response);
	    } else {
	        // create a new user
	        $user = $db->StoreUserInfo($name, $surname, $email, $password, $gender, $age);
	        if ($user) {
	            // user stored successfully
	            $response["error"] = FALSE;
	            $response["user"]["name"] = $user["name"];
	            $response["user"]["surname"] = $user["surname"];
	            $response["user"]["email"] = $user["email"];
	            $response["user"]["sport"] = $user["sport"];
	            $response["user"]["team"] = $user["team"];
	            echo json_encode($response);
	        } else {
	            // user failed to store
	            $response["error"] = TRUE; 
	            $response["error_msg"] = "Unknown error occurred in registration!";
	            echo json_encode($response);
	        }
	     }
	} else {
	    $response["error"] = TRUE;
	    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
	    echo json_encode($response);
	}
?>