<?php
require 'connectMySQL.php';


$SODIENTHOAI=$_POST['SODIENTHOAI'];
$EMAIL=$_POST['EMAIL'];
$GHICHU=$_POST['GHICHU'];




$query ="insert into forget_password values(null,'$SODIENTHOAI','$EMAIL','$GHICHU')";
if(mysqli_query($connect,$query)){
    echo"success";
}else{
    echo"error";
}
?>