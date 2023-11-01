<?php
    require 'connectMySQL.php';

    class loaiban{
        function loaiban($maloaiban,$hinhchung,$tenban,$soluong){
            $this->MALOAIBAN=$maloaiban;
            $this->HINHCHUNG=$hinhchung;
            $this->TENBAN=$tenban;
            $this->SOLUONG=$soluong;
        }
    }
    $query ="select*from loaiban";
    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new loaiban($dong['MALOAIBAN'],$dong['HINHCHUNG'],$dong['TENBAN'],$dong['SOLUONG']));
    }
        echo json_encode($mangketqua);
    ?>