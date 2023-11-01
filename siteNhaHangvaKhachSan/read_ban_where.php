<?php
    require 'connectMySQL.php';

    $maloaiban=$_GET['MALOAIBAN'];
    class ban{
        function ban($maban,$mota,$maloaiban,$tinhtrang){
            $this->MABAN=$maban;
            $this->MOTA=$mota;
            $this->MALOAIBAN=$maloaiban;
            $this->TINHTRANG=$tinhtrang;
        }
    }
    $query ="select*from ban where MALOAIBAN='$maloaiban'";
    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new ban($dong['MABAN'],$dong['MOTA'],$dong['MALOAIBAN'],$dong['TINHTRANG']));
    }
        echo json_encode($mangketqua);
    ?>