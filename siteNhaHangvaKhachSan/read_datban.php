<?php
    require 'connectMySQL.php';

    class datban{
        function datban($maban,$makhachhang,$ghichu){
            $this->MABAN=$maban;
            $this->MAKHACHHANG=$makhachhang;
            $this->GHICHU=$ghichu;
        }
    }
    $query ="select*from datban";
    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new datban($dong['MABAN'],$dong['MAKHACHHANG'],$dong['GHICHU']));
    }
        echo json_encode($mangketqua);
    ?>