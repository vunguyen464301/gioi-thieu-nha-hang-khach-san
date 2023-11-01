<?php
require 'connectMySQL.php';


$mahopthoai=$_POST['MAHOPTHOAI'];
$makhachhang=$_POST['MAKHACHHANG'];
$noidung=$_POST['NOIDUNG'];

$query ="insert into tinnhan values(null,'$mahopthoai','$makhachhang','$noidung')";
if(mysqli_query($connect,$query)){
    echo"success";
}else{
    echo"error";
}
?>