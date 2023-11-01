<?php
    require 'connectMySQL.php';

    class tinnhan{
        function tinnhan($mahopthoai){

            $this->MAHOPTHOAI=$mahopthoai;
            

        }
    }
    $query ="select DISTINCT MAHOPTHOAI from tinnhan";

    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new tinnhan($dong['MAHOPTHOAI']));
    }
        echo json_encode($mangketqua);
    ?>