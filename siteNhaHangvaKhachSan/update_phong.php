<?php
require 'connectMySQL.php';

$maphong=$_POST['maphong'];
$tinhtrang=$_POST['tinhtrang'];

$query ="update phong set TINHTRANG='$tinhtrang' where MAPHONG='$maphong'";

if(mysqli_query($connect,$query)){
    //thành công
    echo "success";
}else{
    //lỗi
    echo "error";
}
?>