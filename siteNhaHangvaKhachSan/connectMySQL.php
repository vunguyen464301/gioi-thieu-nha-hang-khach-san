<?php
$hostname = "127.0.0.1";
$username = "root";
$password = "root@123456";
$database = "sqlrah";

$port = 3306;

// Create a connection
$connect = mysqli_connect($hostname, $username, $password, $database, $port);
mysqli_query($connect, "SET NAMES 'utf8'");
