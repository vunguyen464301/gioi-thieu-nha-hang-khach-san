<?php
require 'connectMySQL.php';

$sodt=$_GET['SODT'];

class khachhang{
    function khachhang ($makhachhang,$sodienthoai,$hinhanh,$tenkhachhang,$ngaythangnamsinh,$diachi,$matkhau){
        $this->MAKHACHHANG=$makhachhang;
        $this->SODIENTHOAI=$sodienthoai;
        $this->HINHANH=$hinhanh;
        $this->TENKHACHHANG=$tenkhachhang;
        $this->NGAYTHANGNAMSINH=$ngaythangnamsinh;
        $this->DIACHI=$diachi;
        $this->MATKHAU=$matkhau;

    }
}
$query ="select*from khachhang where sodienthoai='$sodt'";
$result =mysqli_query($connect,$query);
$mangketqua =array();
while($dong =mysqli_fetch_assoc($result)){
    array_push($mangketqua,new khachhang($dong['MAKHACHHANG'],$dong['SODIENTHOAI'],$dong['HINHANH'],$dong['TENKHACHHANG'],$dong['NGAYTHANGNAMSINH'],$dong['DIACHI'],$dong['MATKHAU']));
}
    echo json_encode($mangketqua);
?>