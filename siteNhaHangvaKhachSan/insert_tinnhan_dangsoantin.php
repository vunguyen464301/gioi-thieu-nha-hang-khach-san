<?php
require 'connectMySQL.php';


$mahopthoai=$_POST['MAHOPTHOAI'];
$makhachhang=$_POST['MAKHACHHANG'];
$tinhtrang=$_POST['TINHTRANG'];

$query ="insert into tinnhan_dangsoantin values(null,'$mahopthoai','$makhachhang','$tinhtrang')";
if(mysqli_query($connect,$query)){
    echo"success";
}else{
    echo"error";
}
?>