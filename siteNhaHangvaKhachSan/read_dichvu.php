<?php
    require 'connectMySQL.php';

    class dichvu{
        function dichvu($madichvu,$hinhchung,$tendichvu,$chitiet){
            $this->MADICHVU=$madichvu;
            $this->HINHCHUNG=$hinhchung;
            $this->TENDICHVU=$tendichvu;
            $this->CHITIET=$chitiet;
        }
    }
    $query ="select*from dichvu";
    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new dichvu($dong['MADICHVU'],$dong['HINHCHUNG'],$dong['TENDICHVU'],$dong['CHITIET']));
        
    }
    echo json_encode($mangketqua);
    ?>