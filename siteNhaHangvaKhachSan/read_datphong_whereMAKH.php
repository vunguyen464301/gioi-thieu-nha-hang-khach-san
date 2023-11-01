<?php
    require 'connectMySQL.php';

    $makhachhang=$_GET['MAKHACHHANG'];
    class datphong{
        function datphong($maphong,$makhachhang,$ghichu){
            $this->MAPHONG=$maphong;
            $this->MAKHACHHANG=$makhachhang;
            $this->GHICHU=$ghichu;

        }
    }
    $query ="select*from datphong where MAKHACHHANG='$makhachhang'";
    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new datphong($dong['MAPHONG'],$dong['MAKHACHHANG'],$dong['GHICHU']));
    }
        echo json_encode($mangketqua);
    ?>