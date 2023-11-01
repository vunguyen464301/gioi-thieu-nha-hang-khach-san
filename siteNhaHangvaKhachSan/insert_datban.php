<?php
require 'connectMySQL.php';


$MABAN=$_POST['MABAN'];
$MAKHACHHANG=$_POST['MAKHACHHANG'];
$GHICHU=$_POST['GHICHU'];




$query ="insert into datban values('$MABAN','$MAKHACHHANG','$GHICHU')";
if(mysqli_query($connect,$query)){
    echo"success";
}else{
    echo"error";
}
?>