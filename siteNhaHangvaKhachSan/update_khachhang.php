<?php
require "connectMySQL.php";

$sodienthoai=$_POST['sodienthoai'];
$tenkhachhang=$_POST['tenkhachhang'];
$ngaythangnamsinh=$_POST['ngaythangnamsinh'];
$diachi=$_POST['diachi'];

$query ="update khachhang set TENKHACHHANG='$tenkhachhang',
NGAYTHANGNAMSINH='$ngaythangnamsinh',
DIACHI='$diachi'
 where SODIENTHOAI='$sodienthoai'";

if(mysqli_query($connect,$query)){
    //thành công
    echo "success";
}else{
    //lỗi
    echo "error";
}
?>