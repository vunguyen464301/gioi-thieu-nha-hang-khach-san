<?php
require 'connectMySQL.php';

$mahopthoai=$_POST['MAHOPTHOAI'];
$makhachhang=$_POST['MAKHACHHANG'];



$query ="delete from tinnhan_dangsoantin where MAHOPTHOAI='$mahopthoai' and MAKHACHHANG='$makhachhang'";
if(mysqli_query($connect,$query)){
    echo"success";
}else{
    echo"error";
}
?>