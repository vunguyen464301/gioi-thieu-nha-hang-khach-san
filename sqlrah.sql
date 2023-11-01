-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 27, 2018 at 09:00 AM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 5.6.37

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";



/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


--
-- Database: `sqlrah`
--
CREATE DATABASE IF NOT EXISTS `sqlrah` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `sqlrah`;
-- --------------------------------------------------------

--
-- Table structure for table `app_phienban`
--

CREATE TABLE `app_phienban` (
  `SOTHUTU` int(11) NOT NULL,
  `MAPHIENBAN` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `app_phienban`
--

INSERT INTO `app_phienban` (`SOTHUTU`, `MAPHIENBAN`) VALUES
(1, '1.0');

-- --------------------------------------------------------

--
-- Table structure for table `ban`
--

CREATE TABLE `ban` (
  `MABAN` char(4) NOT NULL,
  `MOTA` varchar(100) DEFAULT NULL,
  `MALOAIBAN` int(11) NOT NULL,
  `TINHTRANG` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ban`
--

INSERT INTO `ban` (`MABAN`, `MOTA`, `MALOAIBAN`, `TINHTRANG`) VALUES
('RA01', 'Không biết', 1, 'Chưa đặt'),
('RA02', 'Không biết', 1, 'Chưa đặt'),
('RA03', 'Không biết', 1, 'Chưa đặt'),
('RA04', 'Không biết', 1, 'Chưa đặt'),
('RA05', 'Không biết', 1, 'Chưa đặt'),
('RB02', 'Không biết', 2, 'Chưa đặt'),
('RC03', 'Không biết', 3, 'Chưa đặt'),
('RD04', 'Không biết', 4, 'Chưa đặt'),
('RE05', 'Không biết', 5, 'Chưa đặt');

-- --------------------------------------------------------

--
-- Table structure for table `datban`
--

CREATE TABLE `datban` (
  `MABAN` char(4) NOT NULL,
  `MAKHACHHANG` int(11) DEFAULT NULL,
  `GHICHU` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `datphong`
--

CREATE TABLE `datphong` (
  `MAPHONG` char(4) NOT NULL,
  `MAKHACHHANG` int(11) NOT NULL,
  `GHICHU` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `datphong`
--

INSERT INTO `datphong` (`MAPHONG`, `MAKHACHHANG`, `GHICHU`) VALUES
('A002', 42, 'Đã duyệt');

-- --------------------------------------------------------

--
-- Table structure for table `dichvu`
--

CREATE TABLE `dichvu` (
  `MADICHVU` int(11) NOT NULL,
  `HINHCHUNG` varchar(100) NOT NULL,
  `TENDICHVU` varchar(100) NOT NULL,
  `CHITIET` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dichvu`
--

INSERT INTO `dichvu` (`MADICHVU`, `HINHCHUNG`, `TENDICHVU`, `CHITIET`) VALUES
(1, 'spa.jpg', 'Spa,duỗi tóc ,xả tóc ...', 'Là dịch vụ bậc nhất sang chảnh ,ở đây quý khách không nhứng được làm đẹp mà còn là một nơi thư giãn ,mà nơi đây chúng tôi có đủ thể loại tới bình dân ,kính mong được phục vụ quý khách !'),
(2, 'trongtre.jpg', 'Trông trẻ', 'Nếu quý khách bận đi chơi ,bận đi dã ngoài mà có con nít mà không trông được ,tại đây chúng tôi sẵn sàng làm việc trông trẻ để quý khách có thể đi chơi một cách yên tâm nhất với giá cả rất phải chăng ,chúng tôi cũng làm việc vào ngày lễ ,tết ,rất mong được phục vụ quý khách !'),
(3, 'chothuenguoiyeu.jpg', 'Cho thuê người yêu', 'Nếu quý khách muốn gặp mặt ba mẹ mà không có người yêu thì sao ?có phải bị giục là mau có bạn gái đúng không ?tại đây chúng tôi có phương pháp bảo kê cho khách hàng với cha mẹ khách hàng với dịch vụ cho thuê người yêu ,chúng tôi rất mong được phục vụ quý khách !'),
(4, 'luyentapyoga.jpg', 'Luyện tập Yoga', 'Nếu quý khách về già mà xương ko chắc khỏe hay muốn dẻo dai sức khỏe thì hãy đến với chúng tôi ,chúng tôi có những bài tập ,những lớp tập luyện yoga ,rất mong được phục vụ quý khách !'),
(5, 'tapgym.jpg', 'Phòng tập Gym', 'Không cần phải đi đâu xa ,ngay tại khách sạn chúng tôi cũng trang bị những phòng tập GYM ,nếu quý khách muốn có 6 múi hay lười đi ra ngoài mà muốn cái thiện sức khỏe thì hãy đến phòng tập GYM của chúng tôi ,đảm bảo làm vừa lòng quý khách ,nếu quý khách không vừa lòng thì hãy tập tới chừng nào vừa lòng thì chúng toi mới yên tâm ,xin cảm ơn quý khách !');

-- --------------------------------------------------------

--
-- Table structure for table `forget_password`
--

CREATE TABLE `forget_password` (
  `SOTHUTU` int(11) NOT NULL,
  `SODIENTHOAI` char(11) NOT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `GHICHU` int(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `forget_password`
--

INSERT INTO `forget_password` (`SOTHUTU`, `SODIENTHOAI`, `EMAIL`, `GHICHU`) VALUES
(1, '', '', 0),
(2, '', '', 0),
(3, '01675934643', '23423424@234', 0),
(4, '01675934643', '123423@g', 0),
(5, '01675934643', '123423@g', 0),
(6, '01675934643', 'asdf@gmail.com', 0),
(7, '01675934643', '2342@gmail.com', 0);

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
--

CREATE TABLE `khachhang` (
  `MAKHACHHANG` int(11) NOT NULL,
  `SODIENTHOAI` char(11) DEFAULT NULL,
  `HINHANH` varchar(100) DEFAULT NULL,
  `TENKHACHHANG` varchar(100) DEFAULT NULL,
  `NGAYTHANGNAMSINH` varchar(100) DEFAULT NULL,
  `DIACHI` varchar(100) DEFAULT NULL,
  `MATKHAU` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `khachhang`
--

INSERT INTO `khachhang` (`MAKHACHHANG`, `SODIENTHOAI`, `HINHANH`, `TENKHACHHANG`, `NGAYTHANGNAMSINH`, `DIACHI`, `MATKHAU`) VALUES
(1, '01675934643', 'avatar.jpg', 'Nguyen Anh Vu', '1/11/2018', '137 Nguyễn Lộ ', '12345'),
(33, '01697447099', 'avatar.jpg', 'Quyền cao cấp', '', 'null', '12345'),
(34, '0167593464', NULL, 'nguyan', '1/11/2018', '', '12345'),
(35, '01524323423', NULL, '', '1/11/2018', '', '12345'),
(36, '01675934641', NULL, '', 'Nhập ngày/tháng/năm sinh', '', '12345'),
(37, '01674324234', NULL, '', '', '', '12345'),
(38, '01234234234', NULL, '', '15/8/2018', '', '12345'),
(39, '0999999999', NULL, '', '', '', '12345'),
(40, '0123456789', NULL, 'anh', '', '', '12345'),
(41, '0111111111', NULL, 'adf', '18/11/2018', 'ádf545', '12345'),
(42, '01234567890', NULL, 'dau kho', '', '', '12345');

-- --------------------------------------------------------

--
-- Table structure for table `khachhang_quantrivien`
--

CREATE TABLE `khachhang_quantrivien` (
  `SOTHUTU` int(11) NOT NULL,
  `SODIENTHOAI` char(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `khachhang_quantrivien`
--

INSERT INTO `khachhang_quantrivien` (`SOTHUTU`, `SODIENTHOAI`) VALUES
(1, '01697447099');

-- --------------------------------------------------------

--
-- Table structure for table `loaiban`
--

CREATE TABLE `loaiban` (
  `MALOAIBAN` int(11) NOT NULL,
  `HINHCHUNG` varchar(100) DEFAULT NULL,
  `TENBAN` varchar(100) DEFAULT NULL,
  `SOLUONG` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `loaiban`
--

INSERT INTO `loaiban` (`MALOAIBAN`, `HINHCHUNG`, `TENBAN`, `SOLUONG`) VALUES
(1, 'homepage_restaurent_4ghe.jpg', 'Bàn 4 ghế', 1),
(2, 'homepage_restaurent_6ghe.jpg', 'Bàn 6 ghế', 1),
(3, 'homepage_restaurent_8ghe.jpg', 'Bàn 8 ghế', 1),
(4, 'homepage_restaurent_12ghe.jpg', 'Bàn 12 ghế', 1);

-- --------------------------------------------------------

--
-- Table structure for table `loaiphong`
--

CREATE TABLE `loaiphong` (
  `MALOAIPHONG` int(11) NOT NULL,
  `HINHCHUNG` varchar(100) DEFAULT NULL,
  `TENPHONG` varchar(100) DEFAULT NULL,
  `SOLUONG` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `loaiphong`
--

INSERT INTO `loaiphong` (`MALOAIPHONG`, `HINHCHUNG`, `TENPHONG`, `SOLUONG`) VALUES
(1, 'homepage_hotel_singer.jpg', 'Giường đơn', 5),
(2, 'homepage_hotel_2singer.jpg', '2 giường đơn', 8),
(3, 'homepage_hotel_double.jpg', 'Giường đôi', 2),
(4, 'homepage_hotel_2double.jpg', '2 giường đôi', 5),
(5, 'homepage_hotel_doubleandsinger.jpg', '1 giường đôi + 1 giường đơn', 1);

-- --------------------------------------------------------

--
-- Table structure for table `menu_nhahang`
--

CREATE TABLE `menu_nhahang` (
  `MAMON` int(11) NOT NULL,
  `HINHCHUNG` varchar(100) DEFAULT NULL,
  `TENMONAN` varchar(100) DEFAULT NULL,
  `DONGIA` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `menu_nhahang`
--

INSERT INTO `menu_nhahang` (`MAMON`, `HINHCHUNG`, `TENMONAN`, `DONGIA`) VALUES
(1, 'homepage_restaurent_menu_1.jpg', 'THỊT GÀ XÔNG KHÓI', 20000),
(2, 'homepage_restaurent_menu_2.jpg', 'THỊT GÀ CHIÊN DẦU', 20000),
(3, 'homepage_restaurent_menu_3.jpg', 'Bà Hầm Bắc Kinh', 20000),
(4, 'homepage_restaurent_menu_4.jpg', 'Vịt quay thành phẩm', 20000),
(5, 'homepage_restaurent_menu_5.jpg', 'Lợn quay 100 độ', 20000),
(6, 'homepage_restaurent_menu_6.jpg', 'Thịt bò bít tết', 20000),
(7, 'homepage_restaurent_menu_7.jpg', 'Thịt không rõ nguồn gốc(do tải ảnh xuống quên lưu lại giờ nhìn chẳng biết thịt gì nữa )', 20000),
(8, 'homepage_restaurent_menu_8.jpg', 'Thịt heo quay', 20000),
(9, 'homepage_restaurent_menu_9.jpg', 'Bê thui', 20000),
(10, 'homepage_restaurent_menu_10.jpg', 'Thịt dê', 20000);

-- --------------------------------------------------------

--
-- Table structure for table `phong`
--

CREATE TABLE `phong` (
  `MAPHONG` char(4) NOT NULL,
  `MOTA` varchar(100) DEFAULT NULL,
  `HINHANH` varchar(100) DEFAULT NULL,
  `MALOAIPHONG` char(4) NOT NULL,
  `GIA1GIO` int(11) DEFAULT NULL,
  `DONGIANGAY` int(11) DEFAULT NULL,
  `DONGIADEM` int(11) DEFAULT NULL,
  `DONGIANGAYDEM` int(11) DEFAULT NULL,
  `TIENNGHI` varchar(200) DEFAULT NULL,
  `TINHTRANG` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `phong`
--

INSERT INTO `phong` (`MAPHONG`, `MOTA`, `HINHANH`, `MALOAIPHONG`, `GIA1GIO`, `DONGIANGAY`, `DONGIADEM`, `DONGIANGAYDEM`, `TIENNGHI`, `TINHTRANG`) VALUES
('A001', 'Chưa biết', 'homepage_hotel_singer_1.jpg', '1', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('A002', 'Chưa biết', 'homepage_hotel_singer_2.jpg', '1', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Đã đặt'),
('A003', 'Chưa biết', 'homepage_hotel_singer_3.jpg', '1', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('A004', 'Chưa biết', 'homepage_hotel_singer_4.jpg', '1', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('A005', 'Chưa biết', 'homepage_hotel_singer_5.jpg', '1', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('B001', 'Chưa biết', 'homepage_hotel_2singer_1.jpg', '2', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('B002', 'Chưa biết', 'homepage_hotel_2singer_2.jpg', '2', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('B003', 'Chưa biết', 'homepage_hotel_2singer_3.jpg', '2', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('B004', 'Chưa biết', 'homepage_hotel_2singer_4.jpg', '2', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('B005', 'Chưa biết', 'homepage_hotel_2singer_5.jpg', '2', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('B006', 'Chưa biết', 'homepage_hotel_2singer_6.jpg', '2', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('B007', 'Chưa biết', 'homepage_hotel_2singer_7.jpg', '2', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('B008', 'Chưa biết', 'homepage_hotel_2singer_8.jpg', '2', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('C001', 'Chưa biết', 'homepage_hotel_double_1.jpg', '3', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('C002', 'Chưa biết', 'homepage_hotel_double_2.jpg', '3', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('D001', 'Chưa biết', 'homepage_hotel_2double_1.jpg', '4', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('D002', 'Chưa biết', 'homepage_hotel_2double_2.jpg', '4', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('D003', 'Chưa biết', 'homepage_hotel_2double_3.jpg', '4', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('D004', 'Chưa biết', 'homepage_hotel_2double_4.jpg', '4', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('D005', 'Chưa biết', 'homepage_hotel_2double_5.jpg', '4', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt'),
('E001', 'Chưa biết', 'homepage_hotel_doubleandsinger.jpg', '5', 40000, 120000, 90000, 200000, 'Phòng tắm,Máy pha cà phê,Bình đun nước,Máy sấy tóc,Phòng bếp,Wifi miễn phí,Tủ lạnh,Lò vi sóng,Bếp cảm ứng,Điều hòa,Điện thoại,Tivi,Máy lạnh.', 'Chưa đặt');

-- --------------------------------------------------------

--
-- Table structure for table `quangcao`
--

CREATE TABLE `quangcao` (
  `SOTHUTU` int(11) NOT NULL,
  `HINHANH` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `quangcao`
--

INSERT INTO `quangcao` (`SOTHUTU`, `HINHANH`) VALUES
(1, 'ad_1.jpg'),
(2, 'ad_2.jpg'),
(3, 'ad_3.jpg'),
(4, 'ad_4.jpg'),
(5, 'ad_5.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `tinnhan`
--

CREATE TABLE `tinnhan` (
  `SOTHUTU` int(11) NOT NULL,
  `MAHOPTHOAI` int(11) NOT NULL,
  `MAKHACHHANG` int(11) NOT NULL,
  `NOIDUNG` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tinnhan`
--

INSERT INTO `tinnhan` (`SOTHUTU`, `MAHOPTHOAI`, `MAKHACHHANG`, `NOIDUNG`) VALUES
(1, 1, 1, 'xin hoi'),
(2, 1, 33, 'xin chao quy khach ak !'),
(49, 1, 33, 'hay lam'),
(50, 1, 1, 'few'),
(51, 1, 1, 'thanh cong roi'),
(52, 1, 1, '2'),
(53, 1, 1, 'haha'),
(55, 1, 1, 'biet roi'),
(58, 1, 33, 'hay lamw'),
(59, 1, 33, 'xin chao quy khach ak !234234'),
(60, 1, 1, 'ok'),
(62, 1, 1, 'xin chao'),
(63, 1, 1, 'ok'),
(64, 1, 1, 'hi'),
(65, 1, 33, 'xin chao quy khach'),
(66, 1, 33, 'quy khach co viec gi khon'),
(67, 1, 1, 'khong co gi'),
(68, 1, 1, ''),
(69, 1, 33, ''),
(70, 1, 33, '32'),
(71, 1, 33, 'chuan roi'),
(72, 1, 1, 'hu'),
(73, 1, 1, 'xin chao'),
(74, 1, 33, 'vi'),
(75, 1, 1, 'thanh cong roi do'),
(76, 1, 1, 'xin cho hoi cai nay ?'),
(77, 1, 33, 'vay thi sao nao ?'),
(78, 1, 1, 'haiz'),
(79, 1, 33, 'xin chao'),
(80, 1, 1, 'xin chao'),
(81, 1, 33, 'hi'),
(82, 1, 33, 'thanh cong roi nhe'),
(83, 1, 1, 'hel'),
(84, 1, 1, 'haha'),
(85, 1, 1, 'fg'),
(86, 1, 33, 'ok'),
(87, 1, 1, 'uff'),
(88, 1, 1, 'dhjhg'),
(89, 1, 1, 'fsdfsd'),
(90, 1, 1, 'fxc'),
(91, 1, 33, 'ok'),
(92, 1, 1, '34234342234'),
(93, 1, 1, 'xin chao lam quen nhe'),
(94, 1, 33, 'cung dc ,tam chap nhan'),
(95, 1, 1, 'duoc biet la nhu vay'),
(96, 1, 1, '2344'),
(97, 1, 1, 'xin cho phep hoi tham 1 x'),
(98, 1, 1, 'xin chao'),
(99, 1, 1, 'xem loi nhu the nao'),
(100, 1, 1, 'hello'),
(101, 1, 33, 'khoa nick'),
(102, 1, 1, 'xin chao sd!'),
(103, 1, 33, 'cuoi cung la sao ?'),
(104, 1, 33, 'sdfsdf'),
(105, 1, 1, 'cham hoi'),
(106, 1, 33, 'a'),
(107, 1, 1, 'bc'),
(108, 42, 42, 'xin chao'),
(109, 42, 33, 'xin chao ban'),
(110, 42, 42, 'ghhj');

-- --------------------------------------------------------

--
-- Table structure for table `tinnhan_dangsoantin`
--

CREATE TABLE `tinnhan_dangsoantin` (
  `SOTHUTU` int(11) NOT NULL,
  `MAHOPTHOAI` int(11) NOT NULL,
  `MAKHACHHANG` int(11) NOT NULL,
  `TINHTRANG` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `app_phienban`
--
ALTER TABLE `app_phienban`
  ADD PRIMARY KEY (`SOTHUTU`);

--
-- Indexes for table `ban`
--
ALTER TABLE `ban`
  ADD PRIMARY KEY (`MABAN`),
  ADD KEY `MALOAIBAN` (`MALOAIBAN`);

--
-- Indexes for table `datban`
--
ALTER TABLE `datban`
  ADD PRIMARY KEY (`MABAN`),
  ADD KEY `MABAN` (`MABAN`),
  ADD KEY `MAKHACHHANG` (`MAKHACHHANG`);

--
-- Indexes for table `datphong`
--
ALTER TABLE `datphong`
  ADD PRIMARY KEY (`MAPHONG`),
  ADD KEY `MAPHONG` (`MAPHONG`),
  ADD KEY `MAKHACHHANG` (`MAKHACHHANG`);

--
-- Indexes for table `dichvu`
--
ALTER TABLE `dichvu`
  ADD PRIMARY KEY (`MADICHVU`);

--
-- Indexes for table `forget_password`
--
ALTER TABLE `forget_password`
  ADD PRIMARY KEY (`SOTHUTU`);

--
-- Indexes for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MAKHACHHANG`);

--
-- Indexes for table `khachhang_quantrivien`
--
ALTER TABLE `khachhang_quantrivien`
  ADD PRIMARY KEY (`SOTHUTU`),
  ADD KEY `SODIENTHOAI` (`SODIENTHOAI`);

--
-- Indexes for table `loaiban`
--
ALTER TABLE `loaiban`
  ADD PRIMARY KEY (`MALOAIBAN`);

--
-- Indexes for table `loaiphong`
--
ALTER TABLE `loaiphong`
  ADD PRIMARY KEY (`MALOAIPHONG`);

--
-- Indexes for table `menu_nhahang`
--
ALTER TABLE `menu_nhahang`
  ADD PRIMARY KEY (`MAMON`);

--
-- Indexes for table `phong`
--
ALTER TABLE `phong`
  ADD PRIMARY KEY (`MAPHONG`),
  ADD KEY `MALOAIPHONG` (`MALOAIPHONG`),
  ADD KEY `MALOAIPHONG_2` (`MALOAIPHONG`);

--
-- Indexes for table `quangcao`
--
ALTER TABLE `quangcao`
  ADD PRIMARY KEY (`SOTHUTU`);

--
-- Indexes for table `tinnhan`
--
ALTER TABLE `tinnhan`
  ADD PRIMARY KEY (`SOTHUTU`,`MAHOPTHOAI`,`MAKHACHHANG`),
  ADD KEY `MAKHACHHANG` (`MAKHACHHANG`);

--
-- Indexes for table `tinnhan_dangsoantin`
--
ALTER TABLE `tinnhan_dangsoantin`
  ADD PRIMARY KEY (`SOTHUTU`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `app_phienban`
--
ALTER TABLE `app_phienban`
  MODIFY `SOTHUTU` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `dichvu`
--
ALTER TABLE `dichvu`
  MODIFY `MADICHVU` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `forget_password`
--
ALTER TABLE `forget_password`
  MODIFY `SOTHUTU` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `MAKHACHHANG` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `khachhang_quantrivien`
--
ALTER TABLE `khachhang_quantrivien`
  MODIFY `SOTHUTU` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `loaiban`
--
ALTER TABLE `loaiban`
  MODIFY `MALOAIBAN` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `loaiphong`
--
ALTER TABLE `loaiphong`
  MODIFY `MALOAIPHONG` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `menu_nhahang`
--
ALTER TABLE `menu_nhahang`
  MODIFY `MAMON` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `quangcao`
--
ALTER TABLE `quangcao`
  MODIFY `SOTHUTU` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tinnhan`
--
ALTER TABLE `tinnhan`
  MODIFY `SOTHUTU` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;

--
-- AUTO_INCREMENT for table `tinnhan_dangsoantin`
--
ALTER TABLE `tinnhan_dangsoantin`
  MODIFY `SOTHUTU` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
