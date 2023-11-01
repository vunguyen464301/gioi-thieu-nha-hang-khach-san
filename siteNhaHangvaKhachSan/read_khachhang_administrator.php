<?php
    require 'connectMySQL.php';
    $sodt=$_GET['SODT'];

    class khachhang_quantrivien{
        function khachhang_quantrivien ($sothutu,$sodienthoai){
            $this->SOTHUTU=$sothutu;
            $this->SODIENTHOAI=$sodienthoai;
    
        }
    }
    $query ="select*from khachhang_quantrivien where sodienthoai='$sodt'";
    $result =mysqli_query($connect,$query);
    $mangketqua =array();
    while($dong =mysqli_fetch_assoc($result)){
        array_push($mangketqua,new khachhang_quantrivien($dong['SOTHUTU'],$dong['SODIENTHOAI']));
    }
        echo json_encode($mangketqua);
    ?>