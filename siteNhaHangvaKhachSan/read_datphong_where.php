<?php
    require 'connectMySQL.php';

    $maphong=$_GET['MAPHONG'];
    class datphong{
        function datphong($maphong,$makhachhang,$ghichu){
            $this->MAPHONG=$maphong;
            $this->MAKHACHHANG=$makhachhang;
            $this->GHICHU=$ghichu;

        }
    }
    $query ="select*from datphong where MAPHONG='$maphong'";
    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new datphong($dong['MAPHONG'],$dong['MAKHACHHANG'],$dong['GHICHU']));
    }
        echo json_encode($mangketqua);
    ?>