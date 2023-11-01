<?php
require 'connectMySQL.php';


$MABAN=$_POST['MABAN'];



$query ="delete from datban where MABAN='$MABAN'";
if(mysqli_query($connect,$query)){
    echo"success";
}else{
    echo"error";
}
?>