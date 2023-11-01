<?php
    require 'connectMySQL.php';

    $mahopthoai=$_GET['MAHOPTHOAI'];
    $makhachhang=$_GET['MAKHACHHANG'];
    class tinnhan_dangsoantin{
        function tinnhan_dangsoantin($sothutu,$mahopthoai,$makhachhang,$tinhtrang){
            $this->SOTHUTU=$sothutu;
            $this->MAHOPTHOAI=$mahopthoai;
            $this->MAKHACHHANG=$makhachhang;
            $this->TINHTRANG=$tinhtrang;

        }
    }
     $query ="select*from tinnhan_dangsoantin where MAHOPTHOAI='$mahopthoai' and MAKHACHHANG='$makhachhang'";

    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new tinnhan_dangsoantin($dong['SOTHUTU'],$dong['MAHOPTHOAI'],$dong['MAKHACHHANG'],$dong['TINHTRANG']));
    }
        echo json_encode($mangketqua);
    ?>