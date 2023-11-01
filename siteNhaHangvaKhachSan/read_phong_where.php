<?php
require 'connectMySQL.php';

$maloaiphong=$_GET['MALOAIPHONG'];

class phong{
    function phong ($maphong,$mota,$hinhanh,$maloaiphong,$gia1gio,$dongiangay,$dongiadem,$dongiangaydem,$tiennghi,$tinhtrang){
        $this->MAPHONG=$maphong;
        $this->MOTA=$mota;
        $this->HINHANH=$hinhanh;
        $this->MALOAIPHONG=$maloaiphong;
        $this->GIA1GIO=$gia1gio;
        $this->DONGIANGAY=$dongiangay;
        $this->DONGIADEM=$dongiadem;
        $this->DONGIANGAYDEM=$dongiangaydem;
        $this->TIENNGHI=$tiennghi;
        $this->TINHTRANG=$tinhtrang;

    }
}
$query ="select*from phong where MALOAIPHONG='$maloaiphong'";
$result =mysqli_query($connect,$query);
$mangketqua =array();
while($dong =mysqli_fetch_assoc($result)){
    array_push($mangketqua,new phong($dong['MAPHONG']
    ,$dong['MOTA'],$dong['HINHANH'],$dong['MALOAIPHONG']
    ,$dong['GIA1GIO'],$dong['DONGIANGAY'],$dong['DONGIADEM']
    ,$dong['DONGIANGAYDEM'],$dong['TIENNGHI'],$dong['TINHTRANG']));
}
    echo json_encode($mangketqua);
?>