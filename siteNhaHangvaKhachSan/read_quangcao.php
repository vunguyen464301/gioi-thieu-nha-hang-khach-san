<?php
    require 'connectMySQL.php';

    class quangcao{
        function quangcao($sothutu,$hinhanh){
            $this->SOTHUTU=$sothutu;
            $this->HINHANH=$hinhanh;
        }
    }
    
    $query ="select*from quangcao";

    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new quangcao($dong['SOTHUTU'],$dong['HINHANH']));
    }
        echo json_encode($mangketqua);
    ?>