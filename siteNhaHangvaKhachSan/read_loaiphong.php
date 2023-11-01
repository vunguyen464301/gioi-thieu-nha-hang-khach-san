<?php
    require 'connectMySQL.php';

    class loaiphong{
        function loaiphong($maloaiphong,$hinhchung,$tenphong,$soluong){
            $this->MALOAIPHONG=$maloaiphong;
            $this->HINHCHUNG=$hinhchung;
            $this->TENPHONG=$tenphong;
            $this->SOLUONG=$soluong;
        }
    }
    $query ="select*from loaiphong";
    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new loaiphong($dong['MALOAIPHONG'],$dong['HINHCHUNG'],$dong['TENPHONG'],$dong['SOLUONG']));
    }
        echo json_encode($mangketqua);
    ?>