<?php
require 'connectMySQL.php';

$maphienban = $_GET['MAPHIENBAN'];
class app_phienban
{
    function app_phienban($sothutu, $maphienban)
    {
        $this->SOTHUTU = $sothutu;
        $this->MAPHIENBAN = $maphienban;
    }
}
$query = "select*from app_phienban where MAPHIENBAN='$maphienban'";


$result = mysqli_query($connect, $query);
$mangketqua = array();
while ($dong = mysqli_fetch_assoc($result)) {
    array_push($mangketqua, new app_phienban($dong['SOTHUTU'], $dong['MAPHIENBAN']));
}
echo json_encode($mangketqua);
