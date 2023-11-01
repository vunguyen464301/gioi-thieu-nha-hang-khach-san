<?php
require 'connectMySQL.php';


$SODIENTHOAI=$_POST['SODIENTHOAI'];
$TENKHACHHANG=$_POST['TENKHACHHANG'];
$NGAYTHANGNAMSINH=$_POST['NGAYTHANGNAMSINH'];
$DIACHI=$_POST['DIACHI'];
$MATKHAU=$_POST['MATKHAU'];


$query ="insert into khachhang values(null,'$SODIENTHOAI',null,'$TENKHACHHANG','$NGAYTHANGNAMSINH','$DIACHI','$MATKHAU')";
if(mysqli_query($connect,$query)){
    echo"success";
}else{
    echo"error";
}
?>