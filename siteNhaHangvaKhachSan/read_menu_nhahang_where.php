<?php
    require 'connectMySQL.php';

    $ma=$_GET['ma'];
    class menu_nhahang{
        function menu_nhahang($mamon,$hinhchung,$tenmonan,$dongia){
            $this->MAMON=$mamon;
            $this->HINHCHUNG=$hinhchung;
            $this->TENMONAN=$tenmonan;  
            $this->DONGIA=$dongia;
        }
    }
    $query ="select*from menu_nhahang where MAMON='$ma'";
    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new menu_nhahang($dong['MAMON'],$dong['HINHCHUNG'],$dong['TENMONAN'],$dong['DONGIA']));
  
    }
    echo json_encode($mangketqua);
    ?>