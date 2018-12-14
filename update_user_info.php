<?php
class update_user_info{
	function StoreUserInfo($name, $surname, $email, $password, $sport, $team) {
        $hash = $this->hashFunction($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
 
        $stmt = $this->conn->prepare("INSERT INTO users(name, surname, email, encrypted_password, salt, sport, team) VALUES(?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("ssssss", $name, $surname, $email, $encrypted_password, $salt, $sport, $team);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT name, surname, email, encrypted_password, salt, sport, team FROM users WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $stmt-> bind_result($token2,$token3,$token4,$token5,$token6,$token7,$token8);
 
            while ( $stmt-> fetch() ) {
               $user["name"] = $token2;
               $user["surname"] = $token3;
               $user["email"] = $token4;
               $user["sport"] = $token7;
               $user["team"] = $token8;
            }
            $stmt->close();
            return $user;
        } else {
          return false;
        }
    }
 
    function hashFunction($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }

    function VerifyUserAuthentication($email, $password) {
 
        $stmt = $this->conn->prepare("SELECT name, surname, email, encrypted_password, salt, sport, team FROM users WHERE email = ?");
 
        $stmt->bind_param("s", $email);
 
        if ($stmt->execute()) {
            $stmt-> bind_result($token2,$token3,$token4,$token5,$token6,$token7,$token8);
 
            while ( $stmt-> fetch() ) {
               $user["name"] = $token2;
               $user["surname"] = $token3;
               $user["email"] = $token4;
               $user["encrypted_password"] = $token5;
               $user["salt"] = $token6;
               $user["sport"] = $token7;
               $user["team"] = $token8;
            }
 
            $stmt->close();
 
            // verifying user password
            $salt = $token6;
            $encrypted_password = $token5;
            $hash = $this->CheckHashFunction($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $user;
            }
        } else {
            return NULL;
        }
    }
 
	function checkHashFunction($salt, $password) {
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
        return $hash;
    }

    function CheckExistingUser($email) {
        $stmt = $this->conn->prepare("SELECT email from users WHERE email = ?");
 
        $stmt->bind_param("s", $email);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }

}
?>