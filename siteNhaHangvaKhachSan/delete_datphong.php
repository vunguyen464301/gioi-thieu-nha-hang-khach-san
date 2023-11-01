<?php
require 'connectMySQL.php';


$MAPHONG=$_POST['MAPHONG'];


$query ="delete from datphong where MAPHONG='$MAPHONG'";
if(mysqli_query($connect,$query)){
    echo"success";
}else{
    echo"error";
}
?>