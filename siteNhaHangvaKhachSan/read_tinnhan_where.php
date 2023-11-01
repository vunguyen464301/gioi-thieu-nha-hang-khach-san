<?php
    require 'connectMySQL.php';

    $mahopthoai=$_GET['MAHOPTHOAI'];
    class tinnhan{
        function tinnhan($sothutu,$mahopthoai,$makhachhang,$noidung){
            $this->SOTHUTU=$sothutu;
            $this->MAHOPTHOAI=$mahopthoai;
            $this->MAKHACHHANG=$makhachhang;
            $this->NOIDUNG=$noidung;

        }
    }
    $query ="select*from tinnhan where MAHOPTHOAI='$mahopthoai'";

    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new tinnhan($dong['SOTHUTU'],$dong['MAHOPTHOAI'],$dong['MAKHACHHANG'],$dong['NOIDUNG']));
    }
        echo json_encode($mangketqua);
    ?>