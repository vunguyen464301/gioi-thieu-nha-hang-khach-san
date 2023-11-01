<?php
require "connectMySQL.php";

$maban=$_POST['maban'];
$tinhtrang=$_POST['tinhtrang'];

$query ="update ban set TINHTRANG='$tinhtrang' where MABAN='$maban'";

if(mysqli_query($connect,$query)){
    //thành công
    echo "success";
}else{
    //lỗi
    echo "error";
}
?>