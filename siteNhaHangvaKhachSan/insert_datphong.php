<?php
require 'connectMySQL.php';


$MAPHONG=$_POST['MAPHONG'];
$MAKHACHHANG=$_POST['MAKHACHHANG'];
$GHICHU=$_POST['GHICHU'];




$query ="insert into datphong values('$MAPHONG','$MAKHACHHANG','$GHICHU')";
if(mysqli_query($connect,$query)){
    echo"success";
}else{
    echo"error";
}
?>