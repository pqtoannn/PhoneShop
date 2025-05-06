-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 13, 2023 lúc 12:57 PM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `mobileshop`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `brand`
--

CREATE TABLE `brand` (
  `id` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `brand`
--

INSERT INTO `brand` (`id`, `name`, `image`) VALUES
('BR0001', 'Xiaomi', 'Xiaomi.png'),
('BR0002', 'Samsung', 'Samsung.png'),
('BR0003', 'Huawei', 'Huawei.png'),
('BR0004', 'Apple', 'Apple.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `color`
--

CREATE TABLE `color` (
  `rgb` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `color`
--

INSERT INTO `color` (`rgb`, `name`) VALUES
('119;117;121', 'color 12'),
('167;186;203', 'color 6'),
('186;12;47', 'color 3'),
('187;181;169', 'color 4'),
('227;228;229', 'color 13'),
('229;221;234', 'color 7'),
('250;247;242', 'color 2'),
('251;226;221', 'color 10'),
('255;244;152', 'color 8'),
('52;59;67', 'color 1'),
('67;118;145', 'color 9'),
('71;88;70', 'color 11'),
('77;84;100', 'color 5');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `color_product`
--

CREATE TABLE `color_product` (
  `color` varchar(50) NOT NULL,
  `productid` varchar(20) NOT NULL,
  `images` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `color_product`
--

INSERT INTO `color_product` (`color`, `productid`, `images`) VALUES
('119;117;121', 'PR00001', ''),
('119;117;121', 'PR00002', ''),
('119;117;121', 'PR00003', ''),
('119;117;121', 'PR00007', ''),
('167;186;203', 'PR00009', ''),
('167;186;203', 'PR00010', 'C:\\xampp\\htdocs\\API_PhoneShop\\images\\PR00010\\PR00010_167;186;203__0.png'),
('186;12;47', 'PR00001', ''),
('186;12;47', 'PR00004', ''),
('186;12;47', 'PR00008', ''),
('186;12;47', 'PR00009', ''),
('186;12;47', 'PR00010', ''),
('187;181;169', 'PR00001', ''),
('187;181;169', 'PR00003', ''),
('187;181;169', 'PR00006', ''),
('187;181;169', 'PR00010', ''),
('227;228;229', 'PR00001', ''),
('227;228;229', 'PR00009', ''),
('227;228;229', 'PR00010', ''),
('229;221;234', 'PR00001', ''),
('229;221;234', 'PR00006', ''),
('229;221;234', 'PR00007', ''),
('229;221;234', 'PR00008', ''),
('229;221;234', 'PR00009', ''),
('229;221;234', 'PR00010', ''),
('251;226;221', 'PR00001', ''),
('251;226;221', 'PR00010', ''),
('255;244;152', 'PR00001', ''),
('255;244;152', 'PR00005', ''),
('255;244;152', 'PR00006', ''),
('255;244;152', 'PR00010', ''),
('52;59;67', 'PR00004', ''),
('67;118;145', 'PR00001', ''),
('71;88;70', 'PR00006', ''),
('77;84;100', 'PR00010', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `exportdetail`
--

CREATE TABLE `exportdetail` (
  `exid` varchar(20) NOT NULL,
  `optionid` varchar(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `totalprice` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `exportdetail`
--

INSERT INTO `exportdetail` (`exid`, `optionid`, `quantity`, `totalprice`) VALUES
('EX00001', 'OP00047', 3, 30600000),
('EX00001', 'OP00052', 5, 102000000),
('EX00001', 'OP00095', 4, 81600000),
('EX00001', 'OP00098', 5, 51000000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `exportinvoice`
--

CREATE TABLE `exportinvoice` (
  `id` varchar(20) NOT NULL,
  `userid` varchar(20) NOT NULL,
  `initdate` datetime NOT NULL,
  `invoiceprice` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `exportinvoice`
--

INSERT INTO `exportinvoice` (`id`, `userid`, `initdate`, `invoiceprice`) VALUES
('EX00001', 'US00001', '2023-11-12 22:43:59', 265200000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `importdetail`
--

CREATE TABLE `importdetail` (
  `imid` varchar(20) NOT NULL,
  `optionid` varchar(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `totalprice` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `importdetail`
--

INSERT INTO `importdetail` (`imid`, `optionid`, `quantity`, `totalprice`) VALUES
('IM00001', 'OP00175', 5, 150000000),
('IM00001', 'OP00176', 5, 150000000),
('IM00001', 'OP00177', 5, 150000000),
('IM00002', 'OP00009', 5, 250000000),
('IM00002', 'OP00010', 5, 250000000),
('IM00002', 'OP00011', 5, 250000000),
('IM00003', 'OP00136', 5, 50000000),
('IM00003', 'OP00137', 5, 150000000),
('IM00003', 'OP00138', 5, 150000000),
('IM00004', 'OP00051', 5, 100000000),
('IM00004', 'OP00052', 5, 100000000),
('IM00004', 'OP00053', 5, 100000000),
('IM00005', 'OP00057', 5, 250000000),
('IM00005', 'OP00105', 5, 200000000),
('IM00005', 'OP00106', 5, 50000000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `importinvoice`
--

CREATE TABLE `importinvoice` (
  `id` varchar(20) NOT NULL,
  `userid` varchar(20) NOT NULL,
  `initdate` datetime NOT NULL,
  `invoiceprice` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `importinvoice`
--

INSERT INTO `importinvoice` (`id`, `userid`, `initdate`, `invoiceprice`) VALUES
('IM00001', 'US00009', '2023-07-12 00:00:25', 450000000),
('IM00002', 'US00009', '2023-10-20 17:00:07', 750000000),
('IM00003', 'US00009', '2023-09-07 17:00:39', 350000000),
('IM00004', 'US00009', '2023-11-13 17:01:04', 300000000),
('IM00005', 'US00009', '2023-08-31 17:02:25', 500000000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `option`
--

CREATE TABLE `option` (
  `id` varchar(20) NOT NULL,
  `productid` varchar(20) NOT NULL,
  `color` varchar(50) NOT NULL,
  `rom` int(11) NOT NULL,
  `ram` int(11) NOT NULL,
  `importprice` double NOT NULL,
  `saleprice` double NOT NULL,
  `remain` int(11) NOT NULL,
  `sold` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `option`
--

INSERT INTO `option` (`id`, `productid`, `color`, `rom`, `ram`, `importprice`, `saleprice`, `remain`, `sold`) VALUES
('OP00001', 'PR00009', '167;186;203', 128, 2, 10000000, 10200000, 0, 0),
('OP00002', 'PR00009', '186;12;47', 128, 2, 10000000, 10200000, 0, 0),
('OP00003', 'PR00009', '227;228;229', 128, 2, 10000000, 10200000, 0, 0),
('OP00004', 'PR00009', '229;221;234', 128, 2, 10000000, 10200000, 0, 0),
('OP00005', 'PR00009', '167;186;203', 128, 7, 30000000, 30600000, 0, 0),
('OP00006', 'PR00009', '186;12;47', 128, 7, 30000000, 30600000, 0, 0),
('OP00007', 'PR00009', '227;228;229', 128, 7, 30000000, 30600000, 0, 0),
('OP00008', 'PR00009', '229;221;234', 128, 7, 30000000, 30600000, 0, 0),
('OP00009', 'PR00009', '167;186;203', 128, 8, 50000000, 51000000, 5, 0),
('OP00010', 'PR00009', '186;12;47', 128, 8, 50000000, 51000000, 5, 0),
('OP00011', 'PR00009', '227;228;229', 128, 8, 50000000, 51000000, 5, 0),
('OP00012', 'PR00009', '229;221;234', 128, 8, 50000000, 51000000, 0, 0),
('OP00013', 'PR00005', '255;244;152', 1000, 4, 20000000, 20400000, 0, 0),
('OP00014', 'PR00005', '255;244;152', 512, 4, 10000000, 10200000, 0, 0),
('OP00015', 'PR00004', '186;12;47', 256, 7, 10000000, 10200000, 0, 0),
('OP00016', 'PR00004', '52;59;67', 256, 7, 10000000, 10200000, 0, 0),
('OP00017', 'PR00001', '119;117;121', 1000, 4, 20000000, 20400000, 0, 0),
('OP00018', 'PR00001', '186;12;47', 1000, 4, 20000000, 20400000, 0, 0),
('OP00019', 'PR00001', '187;181;169', 1000, 4, 20000000, 20400000, 0, 0),
('OP00020', 'PR00001', '227;228;229', 1000, 4, 20000000, 20400000, 0, 0),
('OP00021', 'PR00001', '229;221;234', 1000, 4, 20000000, 20400000, 0, 0),
('OP00022', 'PR00001', '251;226;221', 1000, 4, 20000000, 20400000, 0, 0),
('OP00023', 'PR00001', '255;244;152', 1000, 4, 20000000, 20400000, 0, 0),
('OP00024', 'PR00001', '67;118;145', 1000, 4, 20000000, 20400000, 0, 0),
('OP00025', 'PR00001', '119;117;121', 16, 4, 40000000, 40800000, 0, 0),
('OP00026', 'PR00001', '186;12;47', 16, 4, 40000000, 40800000, 0, 0),
('OP00027', 'PR00001', '187;181;169', 16, 4, 40000000, 40800000, 0, 0),
('OP00028', 'PR00001', '227;228;229', 16, 4, 40000000, 40800000, 0, 0),
('OP00029', 'PR00001', '229;221;234', 16, 4, 40000000, 40800000, 0, 0),
('OP00030', 'PR00001', '251;226;221', 16, 4, 40000000, 40800000, 0, 0),
('OP00031', 'PR00001', '255;244;152', 16, 4, 40000000, 40800000, 0, 0),
('OP00032', 'PR00001', '67;118;145', 16, 4, 40000000, 40800000, 0, 0),
('OP00033', 'PR00001', '119;117;121', 512, 4, 40000000, 40800000, 0, 0),
('OP00034', 'PR00001', '186;12;47', 512, 4, 40000000, 40800000, 0, 0),
('OP00035', 'PR00001', '187;181;169', 512, 4, 40000000, 40800000, 0, 0),
('OP00036', 'PR00001', '227;228;229', 512, 4, 40000000, 40800000, 0, 0),
('OP00037', 'PR00001', '229;221;234', 512, 4, 40000000, 40800000, 0, 0),
('OP00038', 'PR00001', '251;226;221', 512, 4, 40000000, 40800000, 0, 0),
('OP00039', 'PR00001', '255;244;152', 512, 4, 40000000, 40800000, 0, 0),
('OP00040', 'PR00001', '67;118;145', 512, 4, 40000000, 40800000, 0, 0),
('OP00041', 'PR00001', '119;117;121', 2000, 4, 10000000, 10200000, 0, 0),
('OP00042', 'PR00001', '186;12;47', 2000, 4, 10000000, 10200000, 0, 0),
('OP00043', 'PR00001', '187;181;169', 2000, 4, 10000000, 10200000, 0, 0),
('OP00044', 'PR00001', '227;228;229', 2000, 4, 10000000, 10200000, 0, 0),
('OP00045', 'PR00001', '229;221;234', 2000, 4, 10000000, 10200000, 0, 0),
('OP00046', 'PR00001', '251;226;221', 2000, 4, 10000000, 10200000, 0, 0),
('OP00047', 'PR00001', '255;244;152', 2000, 4, 10000000, 10200000, 0, 3),
('OP00048', 'PR00001', '67;118;145', 2000, 4, 10000000, 10200000, 0, 0),
('OP00049', 'PR00001', '119;117;121', 256, 4, 20000000, 20400000, 0, 0),
('OP00050', 'PR00001', '186;12;47', 256, 4, 20000000, 20400000, 0, 0),
('OP00051', 'PR00001', '187;181;169', 256, 4, 20000000, 20400000, 5, 0),
('OP00052', 'PR00001', '227;228;229', 256, 4, 20000000, 20400000, 5, 5),
('OP00053', 'PR00001', '229;221;234', 256, 4, 20000000, 20400000, 5, 0),
('OP00054', 'PR00001', '251;226;221', 256, 4, 20000000, 20400000, 0, 0),
('OP00055', 'PR00001', '255;244;152', 256, 4, 20000000, 20400000, 0, 0),
('OP00056', 'PR00001', '67;118;145', 256, 4, 20000000, 20400000, 0, 0),
('OP00057', 'PR00010', '167;186;203', 256, 5, 50000000, 51000000, 5, 0),
('OP00058', 'PR00010', '186;12;47', 256, 5, 50000000, 51000000, 0, 0),
('OP00059', 'PR00010', '187;181;169', 256, 5, 50000000, 51000000, 0, 0),
('OP00060', 'PR00010', '227;228;229', 256, 5, 50000000, 51000000, 0, 0),
('OP00061', 'PR00010', '229;221;234', 256, 5, 50000000, 51000000, 0, 0),
('OP00062', 'PR00010', '251;226;221', 256, 5, 50000000, 51000000, 0, 0),
('OP00063', 'PR00010', '255;244;152', 256, 5, 50000000, 51000000, 0, 0),
('OP00064', 'PR00010', '77;84;100', 256, 5, 50000000, 51000000, 0, 0),
('OP00065', 'PR00010', '167;186;203', 256, 4, 30000000, 30600000, 0, 0),
('OP00066', 'PR00010', '186;12;47', 256, 4, 30000000, 30600000, 0, 0),
('OP00067', 'PR00010', '187;181;169', 256, 4, 30000000, 30600000, 0, 0),
('OP00068', 'PR00010', '227;228;229', 256, 4, 30000000, 30600000, 0, 0),
('OP00069', 'PR00010', '229;221;234', 256, 4, 30000000, 30600000, 0, 0),
('OP00070', 'PR00010', '251;226;221', 256, 4, 30000000, 30600000, 0, 0),
('OP00071', 'PR00010', '255;244;152', 256, 4, 30000000, 30600000, 0, 0),
('OP00072', 'PR00010', '77;84;100', 256, 4, 30000000, 30600000, 0, 0),
('OP00073', 'PR00010', '167;186;203', 128, 4, 10000000, 10200000, 0, 0),
('OP00074', 'PR00010', '186;12;47', 128, 4, 10000000, 10200000, 0, 0),
('OP00075', 'PR00010', '187;181;169', 128, 4, 10000000, 10200000, 0, 0),
('OP00076', 'PR00010', '227;228;229', 128, 4, 10000000, 10200000, 0, 0),
('OP00077', 'PR00010', '229;221;234', 128, 4, 10000000, 10200000, 0, 0),
('OP00078', 'PR00010', '251;226;221', 128, 4, 10000000, 10200000, 0, 0),
('OP00079', 'PR00010', '255;244;152', 128, 4, 10000000, 10200000, 0, 0),
('OP00080', 'PR00010', '77;84;100', 128, 4, 10000000, 10200000, 0, 0),
('OP00081', 'PR00010', '167;186;203', 64, 4, 40000000, 40800000, 0, 0),
('OP00082', 'PR00010', '186;12;47', 64, 4, 40000000, 40800000, 0, 0),
('OP00083', 'PR00010', '187;181;169', 64, 4, 40000000, 40800000, 0, 0),
('OP00084', 'PR00010', '227;228;229', 64, 4, 40000000, 40800000, 0, 0),
('OP00085', 'PR00010', '229;221;234', 64, 4, 40000000, 40800000, 0, 0),
('OP00086', 'PR00010', '251;226;221', 64, 4, 40000000, 40800000, 0, 0),
('OP00087', 'PR00010', '255;244;152', 64, 4, 40000000, 40800000, 0, 0),
('OP00088', 'PR00010', '77;84;100', 64, 4, 40000000, 40800000, 0, 0),
('OP00089', 'PR00010', '167;186;203', 64, 5, 20000000, 20400000, 0, 0),
('OP00090', 'PR00010', '186;12;47', 64, 5, 20000000, 20400000, 0, 0),
('OP00091', 'PR00010', '187;181;169', 64, 5, 20000000, 20400000, 0, 0),
('OP00092', 'PR00010', '227;228;229', 64, 5, 20000000, 20400000, 0, 0),
('OP00093', 'PR00010', '229;221;234', 64, 5, 20000000, 20400000, 0, 0),
('OP00094', 'PR00010', '251;226;221', 64, 5, 20000000, 20400000, 0, 0),
('OP00095', 'PR00010', '255;244;152', 64, 5, 20000000, 20400000, 0, 4),
('OP00096', 'PR00010', '77;84;100', 64, 5, 20000000, 20400000, 0, 0),
('OP00097', 'PR00010', '167;186;203', 128, 5, 10000000, 10200000, 0, 0),
('OP00098', 'PR00010', '186;12;47', 128, 5, 10000000, 10200000, 0, 5),
('OP00099', 'PR00010', '187;181;169', 128, 5, 10000000, 10200000, 0, 0),
('OP00100', 'PR00010', '227;228;229', 128, 5, 10000000, 10200000, 0, 0),
('OP00101', 'PR00010', '229;221;234', 128, 5, 10000000, 10200000, 0, 0),
('OP00102', 'PR00010', '251;226;221', 128, 5, 10000000, 10200000, 0, 0),
('OP00103', 'PR00010', '255;244;152', 128, 5, 10000000, 10200000, 0, 0),
('OP00104', 'PR00010', '77;84;100', 128, 5, 10000000, 10200000, 0, 0),
('OP00105', 'PR00002', '119;117;121', 512, 2, 40000000, 40800000, 5, 0),
('OP00106', 'PR00002', '119;117;121', 512, 5, 10000000, 10200000, 5, 0),
('OP00107', 'PR00008', '186;12;47', 64, 5, 40000000, 40800000, 0, 0),
('OP00108', 'PR00008', '229;221;234', 64, 5, 40000000, 40800000, 0, 0),
('OP00109', 'PR00008', '186;12;47', 512, 2, 40000000, 40800000, 0, 0),
('OP00110', 'PR00008', '229;221;234', 512, 2, 40000000, 40800000, 0, 0),
('OP00111', 'PR00008', '186;12;47', 256, 2, 50000000, 51000000, 0, 0),
('OP00112', 'PR00008', '229;221;234', 256, 2, 50000000, 51000000, 0, 0),
('OP00113', 'PR00008', '186;12;47', 64, 2, 20000000, 20400000, 0, 0),
('OP00114', 'PR00008', '229;221;234', 64, 2, 20000000, 20400000, 0, 0),
('OP00115', 'PR00008', '186;12;47', 1000, 2, 10000000, 10200000, 0, 0),
('OP00116', 'PR00008', '229;221;234', 1000, 2, 10000000, 10200000, 0, 0),
('OP00117', 'PR00008', '186;12;47', 128, 5, 50000000, 51000000, 0, 0),
('OP00118', 'PR00008', '229;221;234', 128, 5, 50000000, 51000000, 0, 0),
('OP00119', 'PR00008', '186;12;47', 256, 5, 30000000, 30600000, 0, 0),
('OP00120', 'PR00008', '229;221;234', 256, 5, 30000000, 30600000, 0, 0),
('OP00121', 'PR00008', '186;12;47', 128, 2, 10000000, 10200000, 0, 0),
('OP00122', 'PR00008', '229;221;234', 128, 2, 10000000, 10200000, 0, 0),
('OP00123', 'PR00008', '186;12;47', 512, 5, 50000000, 51000000, 0, 0),
('OP00124', 'PR00008', '229;221;234', 512, 5, 50000000, 51000000, 0, 0),
('OP00125', 'PR00008', '186;12;47', 2000, 2, 50000000, 51000000, 0, 0),
('OP00126', 'PR00008', '229;221;234', 2000, 2, 50000000, 51000000, 0, 0),
('OP00127', 'PR00008', '186;12;47', 1000, 5, 30000000, 30600000, 0, 0),
('OP00128', 'PR00008', '229;221;234', 1000, 5, 30000000, 30600000, 0, 0),
('OP00129', 'PR00007', '119;117;121', 128, 7, 30000000, 30600000, 0, 0),
('OP00130', 'PR00007', '229;221;234', 128, 7, 30000000, 30600000, 0, 0),
('OP00131', 'PR00007', '119;117;121', 1000, 4, 50000000, 51000000, 0, 0),
('OP00132', 'PR00007', '229;221;234', 1000, 4, 50000000, 51000000, 0, 0),
('OP00133', 'PR00007', '119;117;121', 1000, 8, 40000000, 40800000, 0, 0),
('OP00134', 'PR00007', '229;221;234', 1000, 8, 40000000, 40800000, 0, 0),
('OP00135', 'PR00007', '119;117;121', 128, 4, 10000000, 10200000, 0, 0),
('OP00136', 'PR00007', '229;221;234', 128, 4, 10000000, 10200000, 5, 0),
('OP00137', 'PR00007', '119;117;121', 16, 8, 30000000, 30600000, 5, 0),
('OP00138', 'PR00007', '229;221;234', 16, 8, 30000000, 30600000, 5, 0),
('OP00139', 'PR00007', '119;117;121', 16, 4, 30000000, 30600000, 0, 0),
('OP00140', 'PR00007', '229;221;234', 16, 4, 30000000, 30600000, 0, 0),
('OP00141', 'PR00007', '119;117;121', 16, 7, 50000000, 51000000, 0, 0),
('OP00142', 'PR00007', '229;221;234', 16, 7, 50000000, 51000000, 0, 0),
('OP00143', 'PR00007', '119;117;121', 1000, 7, 20000000, 20400000, 0, 0),
('OP00144', 'PR00007', '229;221;234', 1000, 7, 20000000, 20400000, 0, 0),
('OP00145', 'PR00007', '119;117;121', 128, 8, 40000000, 40800000, 0, 0),
('OP00146', 'PR00007', '229;221;234', 128, 8, 40000000, 40800000, 0, 0),
('OP00147', 'PR00006', '187;181;169', 64, 6, 20000000, 20400000, 0, 0),
('OP00148', 'PR00006', '229;221;234', 64, 6, 20000000, 20400000, 0, 0),
('OP00149', 'PR00006', '255;244;152', 64, 6, 20000000, 20400000, 0, 0),
('OP00150', 'PR00006', '71;88;70', 64, 6, 20000000, 20400000, 0, 0),
('OP00151', 'PR00006', '187;181;169', 2000, 5, 20000000, 20400000, 0, 0),
('OP00152', 'PR00006', '229;221;234', 2000, 5, 20000000, 20400000, 0, 0),
('OP00153', 'PR00006', '255;244;152', 2000, 5, 20000000, 20400000, 0, 0),
('OP00154', 'PR00006', '71;88;70', 2000, 5, 20000000, 20400000, 0, 0),
('OP00155', 'PR00006', '187;181;169', 512, 6, 30000000, 30600000, 0, 0),
('OP00156', 'PR00006', '229;221;234', 512, 6, 30000000, 30600000, 0, 0),
('OP00157', 'PR00006', '255;244;152', 512, 6, 30000000, 30600000, 0, 0),
('OP00158', 'PR00006', '71;88;70', 512, 6, 30000000, 30600000, 0, 0),
('OP00159', 'PR00006', '187;181;169', 32, 6, 20000000, 20400000, 0, 0),
('OP00160', 'PR00006', '229;221;234', 32, 6, 20000000, 20400000, 0, 0),
('OP00161', 'PR00006', '255;244;152', 32, 6, 20000000, 20400000, 0, 0),
('OP00162', 'PR00006', '71;88;70', 32, 6, 20000000, 20400000, 0, 0),
('OP00163', 'PR00006', '187;181;169', 32, 5, 40000000, 40800000, 0, 0),
('OP00164', 'PR00006', '229;221;234', 32, 5, 40000000, 40800000, 0, 0),
('OP00165', 'PR00006', '255;244;152', 32, 5, 40000000, 40800000, 0, 0),
('OP00166', 'PR00006', '71;88;70', 32, 5, 40000000, 40800000, 0, 0),
('OP00167', 'PR00006', '187;181;169', 2000, 6, 10000000, 10200000, 0, 0),
('OP00168', 'PR00006', '229;221;234', 2000, 6, 10000000, 10200000, 0, 0),
('OP00169', 'PR00006', '255;244;152', 2000, 6, 10000000, 10200000, 0, 0),
('OP00170', 'PR00006', '71;88;70', 2000, 6, 10000000, 10200000, 0, 0),
('OP00171', 'PR00006', '187;181;169', 64, 5, 50000000, 51000000, 0, 0),
('OP00172', 'PR00006', '229;221;234', 64, 5, 50000000, 51000000, 0, 0),
('OP00173', 'PR00006', '255;244;152', 64, 5, 50000000, 51000000, 0, 0),
('OP00174', 'PR00006', '71;88;70', 64, 5, 50000000, 51000000, 0, 0),
('OP00175', 'PR00003', '119;117;121', 32, 6, 30000000, 30600000, 5, 0),
('OP00176', 'PR00003', '187;181;169', 32, 6, 30000000, 30600000, 5, 0),
('OP00177', 'PR00003', '119;117;121', 128, 2, 30000000, 30600000, 5, 0),
('OP00178', 'PR00003', '187;181;169', 128, 2, 30000000, 30600000, 0, 0),
('OP00179', 'PR00003', '119;117;121', 1000, 6, 10000000, 10200000, 0, 0),
('OP00180', 'PR00003', '187;181;169', 1000, 6, 10000000, 10200000, 0, 0),
('OP00181', 'PR00003', '119;117;121', 32, 2, 40000000, 51000000, 0, 0),
('OP00182', 'PR00003', '187;181;169', 32, 2, 40000000, 51000000, 0, 0),
('OP00183', 'PR00003', '119;117;121', 1000, 2, 10000000, 10200000, 0, 0),
('OP00184', 'PR00003', '187;181;169', 1000, 2, 10000000, 10200000, 0, 0),
('OP00185', 'PR00003', '119;117;121', 128, 6, 30000000, 30600000, 0, 0),
('OP00186', 'PR00003', '187;181;169', 128, 6, 30000000, 30600000, 0, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order`
--

CREATE TABLE `order` (
  `id` varchar(20) NOT NULL,
  `userid` varchar(20) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `address` text NOT NULL,
  `initdate` datetime NOT NULL,
  `orderprice` double NOT NULL,
  `delidate` date DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 0,
  `pay` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `order`
--

INSERT INTO `order` (`id`, `userid`, `phone`, `address`, `initdate`, `orderprice`, `delidate`, `status`, `pay`) VALUES
('OD00001', 'US00001', '0901234567', 'New Address', '2023-11-09 11:17:15', 265200000, '2023-10-22', 4, 0),
('OD00002', 'US00002', '0901234567', 'New Address', '2023-10-12 11:17:16', 326400000, NULL, 0, 0),
('OD00003', 'US00003', '0901234567', 'New Address', '2023-09-25 11:17:16', 367200000, NULL, 0, 0),
('OD00004', 'US00001', '0901234567', 'New Address', '2023-08-05 11:17:17', 204000000, NULL, 0, 0),
('OD00005', 'US00004', '0901234567', 'New Address', '2023-10-02 11:17:18', 224400000, NULL, 0, 0),
('OD00006', 'US00004', '0901234567', 'New Address', '2023-07-26 11:17:19', 142800000, NULL, 0, 0),
('OD00007', 'US00003', '0901234567', 'New Address', '2023-09-29 11:17:19', 408000000, NULL, 0, 0),
('OD00008', 'US00001', '0901234567', 'New Address', '2023-07-19 11:17:20', 397800000, NULL, 0, 0),
('OD00009', 'US00004', '0901234567', 'New Address', '2023-08-16 11:17:21', 550800000, NULL, 0, 0),
('OD00010', 'US00003', '0901234567', 'New Address', '2023-11-01 11:17:22', 142800000, NULL, 0, 0),
('OD00011', 'US00002', '0901234567', 'New Address', '2023-07-19 23:41:41', 61200000, NULL, 6, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orderdetail`
--

CREATE TABLE `orderdetail` (
  `orderid` varchar(20) NOT NULL,
  `optionid` varchar(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `totalprice` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `orderdetail`
--

INSERT INTO `orderdetail` (`orderid`, `optionid`, `quantity`, `totalprice`) VALUES
('OD00001', 'OP00047', 3, 30600000),
('OD00001', 'OP00052', 5, 102000000),
('OD00001', 'OP00095', 4, 81600000),
('OD00001', 'OP00098', 5, 51000000),
('OD00002', 'OP00087', 5, 204000000),
('OD00002', 'OP00137', 4, 122400000),
('OD00003', 'OP00090', 1, 20400000),
('OD00003', 'OP00109', 1, 40800000),
('OD00003', 'OP00111', 2, 102000000),
('OD00003', 'OP00147', 4, 81600000),
('OD00003', 'OP00159', 4, 81600000),
('OD00003', 'OP00170', 4, 40800000),
('OD00004', 'OP00126', 3, 153000000),
('OD00004', 'OP00131', 1, 51000000),
('OD00005', 'OP00006', 2, 61200000),
('OD00005', 'OP00111', 2, 102000000),
('OD00005', 'OP00161', 3, 61200000),
('OD00006', 'OP00014', 1, 10200000),
('OD00006', 'OP00024', 2, 40800000),
('OD00006', 'OP00046', 3, 30600000),
('OD00006', 'OP00150', 3, 61200000),
('OD00007', 'OP00004', 1, 10200000),
('OD00007', 'OP00060', 5, 255000000),
('OD00007', 'OP00127', 4, 122400000),
('OD00007', 'OP00169', 2, 20400000),
('OD00008', 'OP00006', 3, 91800000),
('OD00008', 'OP00054', 5, 102000000),
('OD00008', 'OP00072', 1, 30600000),
('OD00008', 'OP00095', 4, 81600000),
('OD00008', 'OP00115', 5, 51000000),
('OD00008', 'OP00146', 1, 40800000),
('OD00009', 'OP00006', 3, 91800000),
('OD00009', 'OP00030', 2, 81600000),
('OD00009', 'OP00060', 3, 153000000),
('OD00009', 'OP00118', 2, 102000000),
('OD00009', 'OP00145', 3, 122400000),
('OD00010', 'OP00059', 2, 102000000),
('OD00010', 'OP00148', 2, 40800000),
('OD00011', 'OP00017', 1, 20400000),
('OD00011', 'OP00018', 1, 20400000),
('OD00011', 'OP00019', 1, 20400000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `id` varchar(20) NOT NULL,
  `brandid` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `minimportprice` double NOT NULL,
  `minsaleprice` double NOT NULL,
  `detail` text NOT NULL,
  `warranty` double NOT NULL,
  `status` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`id`, `brandid`, `name`, `minimportprice`, `minsaleprice`, `detail`, `warranty`, `status`) VALUES
('PR00001', 'BR0003', 'product PR00001', 10000000, 10200000, 'Day la san pham test', 13, 1),
('PR00002', 'BR0002', 'product PR00002', 10000000, 10200000, 'Day la san pham test', 23, 1),
('PR00003', 'BR0001', 'product PR00003', 10000000, 10200000, 'Day la san pham test', 9, 1),
('PR00004', 'BR0003', 'product PR00004', 10000000, 10200000, 'Day la san pham test', 21, 1),
('PR00005', 'BR0003', 'product PR00005', 10000000, 10200000, 'Day la san pham test', 11, 1),
('PR00006', 'BR0001', 'product PR00006', 10000000, 10200000, 'Day la san pham test', 3, 1),
('PR00007', 'BR0001', 'product PR00007', 10000000, 10200000, 'Day la san pham test', 21, 1),
('PR00008', 'BR0001', 'product PR00008', 10000000, 10200000, 'Day la san pham test', 10, 1),
('PR00009', 'BR0004', 'product PR00009', 10000000, 10200000, 'Day la san pham test', 21, 1),
('PR00010', 'BR0002', 'product PR00010', 10000000, 10200000, 'Day la san pham test', 16, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ram_product`
--

CREATE TABLE `ram_product` (
  `ram` int(11) NOT NULL,
  `productid` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `ram_product`
--

INSERT INTO `ram_product` (`ram`, `productid`) VALUES
(2, 'PR00002'),
(2, 'PR00003'),
(2, 'PR00004'),
(2, 'PR00005'),
(2, 'PR00008'),
(2, 'PR00009'),
(4, 'PR00001'),
(4, 'PR00004'),
(4, 'PR00005'),
(4, 'PR00007'),
(4, 'PR00010'),
(5, 'PR00002'),
(5, 'PR00006'),
(5, 'PR00008'),
(5, 'PR00010'),
(6, 'PR00003'),
(6, 'PR00006'),
(7, 'PR00004'),
(7, 'PR00007'),
(7, 'PR00009'),
(8, 'PR00007'),
(8, 'PR00009');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `rom_product`
--

CREATE TABLE `rom_product` (
  `rom` int(11) NOT NULL,
  `productid` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `rom_product`
--

INSERT INTO `rom_product` (`rom`, `productid`) VALUES
(16, 'PR00001'),
(16, 'PR00005'),
(16, 'PR00006'),
(16, 'PR00007'),
(32, 'PR00003'),
(32, 'PR00005'),
(32, 'PR00006'),
(64, 'PR00005'),
(64, 'PR00006'),
(64, 'PR00008'),
(64, 'PR00010'),
(128, 'PR00003'),
(128, 'PR00004'),
(128, 'PR00006'),
(128, 'PR00007'),
(128, 'PR00008'),
(128, 'PR00009'),
(128, 'PR00010'),
(256, 'PR00001'),
(256, 'PR00004'),
(256, 'PR00005'),
(256, 'PR00008'),
(256, 'PR00010'),
(512, 'PR00001'),
(512, 'PR00002'),
(512, 'PR00004'),
(512, 'PR00005'),
(512, 'PR00006'),
(512, 'PR00008'),
(1000, 'PR00001'),
(1000, 'PR00002'),
(1000, 'PR00003'),
(1000, 'PR00004'),
(1000, 'PR00005'),
(1000, 'PR00007'),
(1000, 'PR00008'),
(2000, 'PR00001'),
(2000, 'PR00006'),
(2000, 'PR00008');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` varchar(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `avatar` text NOT NULL,
  `phone` varchar(20) NOT NULL,
  `address` text NOT NULL,
  `permission` tinyint(4) NOT NULL DEFAULT 0,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `initdate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `username`, `fullname`, `email`, `password`, `avatar`, `phone`, `address`, `permission`, `status`, `initdate`) VALUES
('PR00009', 'Đại Sư Cặc Lỏd', 'Lê Duy Human', 'donlai@gmail.co', '123', 'C:\\xampp\\htdocs\\API_PhoneShop\\avatar\\PR00009.png', '0903331342', 'Sao Hỏa', 0, 1, '2023-11-11'),
('PR00011', 'Đại sư card k lỏd', 'LDN', 'abscb@gmail.com', 'abscb', 'C:\\xampp\\htdocs\\API_PhoneShop\\avatar\\PR00011.png', '0122813981', 'Sao trời', 0, 1, '2023-11-11'),
('PR00012', 'ádasfas', 'ádfasfdasdfs', 'zxc@gmail.com', 'zxc', 'C:\\xampp\\htdocs\\API_PhoneShop\\avatar\\PR00012.png', '1231321315', 'fghdfg', 0, 1, '2023-11-11'),
('PR00013', 'ádasdfasxczx', 'zxcZxcXz', 'qwe@gmail.com', 'qwe', 'C:\\xampp\\htdocs\\API_PhoneShop\\avatar\\PR00013.png', '5678576856', 'sdfa', 0, 1, '2023-11-11'),
('PR00014', 'wer', 'kjkjb', 'wer@gmail.com', 'wer', 'C:\\xampp\\htdocs\\API_PhoneShop\\avatar\\PR00014.jpg', '3423423453', 'ggggg', 1, 1, '2023-11-11'),
('US00001', 'benlun1201', 'Lê Duy Nhân', 'benlun1201@gmail.com', '123', 'C:\\xampp\\htdocs\\API_PhoneShop\\avatar\\US00001.jpg', '0901234567', 'New Address', 0, 1, '2023-10-30'),
('US00002', 'usertest', 'Lê Văn A', 'test@gmail.com', '12345', '', '0901200002', 'New Address', 1, 1, '2023-10-30'),
('US00003', 'UserTestUS00003', '', 'US00003@gmail.com', '123', '', '0901200003', 'New Address', 0, 1, '2023-10-31'),
('US00004', 'UserTestUS00004', 'Full name of user US00004', 'US00004@gmail.com', '123', '', '0901200004', 'New Address', 0, 1, '2023-11-06'),
('US00005', 'UserTestUS00005', 'Full name of user US00005', 'US00005@gmail.com', '123', '', '0901200005', 'New Address', 0, 1, '2023-11-06'),
('US00006', 'UserTestUS00006', 'Full name of user US00006', 'US00006@gmail.com', '123', '', '0901500006', 'New Address', 0, 1, '2023-11-06'),
('US00007', 'UserTestUS00007', 'Full name of user US00007', 'US00007@gmail.com', '123', '', '0901200007', 'New Address', 0, 1, '2023-11-06'),
('US00008', 'UserTestUS00008', 'Full name of user US00008', 'US00008@gmail.com', '123', '', '0901200008', 'New Address', 0, 1, '2023-11-06'),
('US00009', 'HumanLE', 'Le Duy Human', '1', '1', '', '0123456789', 'Sao kim', 1, 1, '2023-11-14');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `warranty`
--

CREATE TABLE `warranty` (
  `id` varchar(20) NOT NULL,
  `userid` varchar(20) NOT NULL,
  `optionid` varchar(20) NOT NULL,
  `initdate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `warranty`
--

INSERT INTO `warranty` (`id`, `userid`, `optionid`, `initdate`) VALUES
('PR00001_1', 'US00001', 'OP00047', '2023-11-12 23:17:41'),
('PR00001_2', 'US00001', 'OP00047', '2023-11-12 23:17:41'),
('PR00001_3', 'US00001', 'OP00047', '2023-11-12 23:17:41'),
('PR00001_4', 'US00001', 'OP00052', '2023-11-12 23:17:41'),
('PR00001_5', 'US00001', 'OP00052', '2023-11-12 23:17:41'),
('PR00001_6', 'US00001', 'OP00052', '2023-11-12 23:17:41'),
('PR00001_7', 'US00001', 'OP00052', '2023-11-12 23:17:41'),
('PR00001_8', 'US00001', 'OP00052', '2023-11-12 23:17:41'),
('PR00010_1', 'US00001', 'OP00095', '2023-11-12 23:17:41'),
('PR00010_2', 'US00001', 'OP00095', '2023-11-12 23:17:41'),
('PR00010_3', 'US00001', 'OP00095', '2023-11-12 23:17:41'),
('PR00010_4', 'US00001', 'OP00095', '2023-11-12 23:17:41'),
('PR00010_5', 'US00001', 'OP00098', '2023-11-12 23:17:41'),
('PR00010_6', 'US00001', 'OP00098', '2023-11-12 23:17:41'),
('PR00010_7', 'US00001', 'OP00098', '2023-11-12 23:17:41'),
('PR00010_8', 'US00001', 'OP00098', '2023-11-12 23:17:41'),
('PR00010_9', 'US00001', 'OP00098', '2023-11-12 23:17:41');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `warrantyinvoice`
--

CREATE TABLE `warrantyinvoice` (
  `id` varchar(20) NOT NULL,
  `warrantyid` varchar(20) NOT NULL,
  `describe` text NOT NULL,
  `initdate` datetime NOT NULL,
  `expense` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `warrantyinvoice`
--

INSERT INTO `warrantyinvoice` (`id`, `warrantyid`, `describe`, `initdate`, `expense`) VALUES
('WA00001', 'PR00001_1', '', '2023-11-13 15:11:36', 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `brand`
--
ALTER TABLE `brand`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `color`
--
ALTER TABLE `color`
  ADD PRIMARY KEY (`rgb`);

--
-- Chỉ mục cho bảng `color_product`
--
ALTER TABLE `color_product`
  ADD PRIMARY KEY (`color`,`productid`),
  ADD KEY `fk_color_productid` (`productid`);

--
-- Chỉ mục cho bảng `exportdetail`
--
ALTER TABLE `exportdetail`
  ADD PRIMARY KEY (`exid`,`optionid`),
  ADD KEY `fk_exportdetail_option_id` (`optionid`);

--
-- Chỉ mục cho bảng `exportinvoice`
--
ALTER TABLE `exportinvoice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_exportinvoice_user_id` (`userid`);

--
-- Chỉ mục cho bảng `importdetail`
--
ALTER TABLE `importdetail`
  ADD PRIMARY KEY (`imid`,`optionid`),
  ADD KEY `fk_importdetail_option_id` (`optionid`);

--
-- Chỉ mục cho bảng `importinvoice`
--
ALTER TABLE `importinvoice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_importinvoice_user_id` (`userid`);

--
-- Chỉ mục cho bảng `option`
--
ALTER TABLE `option`
  ADD PRIMARY KEY (`id`,`productid`,`color`,`rom`,`ram`),
  ADD KEY `fk_option_product_id` (`productid`),
  ADD KEY `fk_option_rom` (`rom`),
  ADD KEY `fk_option_ram` (`ram`),
  ADD KEY `fk_option_color` (`color`);

--
-- Chỉ mục cho bảng `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_order_user_id` (`userid`);

--
-- Chỉ mục cho bảng `orderdetail`
--
ALTER TABLE `orderdetail`
  ADD PRIMARY KEY (`orderid`,`optionid`),
  ADD KEY `fk_orderdetail_option_id` (`optionid`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_product_brand_id` (`brandid`);

--
-- Chỉ mục cho bảng `ram_product`
--
ALTER TABLE `ram_product`
  ADD PRIMARY KEY (`ram`,`productid`),
  ADD KEY `fk_ram_productid` (`productid`);

--
-- Chỉ mục cho bảng `rom_product`
--
ALTER TABLE `rom_product`
  ADD PRIMARY KEY (`rom`,`productid`),
  ADD KEY `fk_rom_productid` (`productid`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `warranty`
--
ALTER TABLE `warranty`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_warranty_user_id` (`userid`),
  ADD KEY `fk_warranty_option_id` (`optionid`);

--
-- Chỉ mục cho bảng `warrantyinvoice`
--
ALTER TABLE `warrantyinvoice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_warrantyinvoice_warranty_id` (`warrantyid`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `color_product`
--
ALTER TABLE `color_product`
  ADD CONSTRAINT `fk_color_productid` FOREIGN KEY (`productid`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_color_rgb` FOREIGN KEY (`color`) REFERENCES `color` (`rgb`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `exportdetail`
--
ALTER TABLE `exportdetail`
  ADD CONSTRAINT `fk_exportdetail_exportinvoice_id` FOREIGN KEY (`exid`) REFERENCES `exportinvoice` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_exportdetail_option_id` FOREIGN KEY (`optionid`) REFERENCES `option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `exportinvoice`
--
ALTER TABLE `exportinvoice`
  ADD CONSTRAINT `fk_exportinvoice_user_id` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `importdetail`
--
ALTER TABLE `importdetail`
  ADD CONSTRAINT `fk_importdetail_importinvoice_id` FOREIGN KEY (`imid`) REFERENCES `importinvoice` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_importdetail_option_id` FOREIGN KEY (`optionid`) REFERENCES `option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `importinvoice`
--
ALTER TABLE `importinvoice`
  ADD CONSTRAINT `fk_importinvoice_user_id` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `option`
--
ALTER TABLE `option`
  ADD CONSTRAINT `fk_option_color` FOREIGN KEY (`color`) REFERENCES `color` (`rgb`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_option_product_id` FOREIGN KEY (`productid`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_option_ram` FOREIGN KEY (`ram`) REFERENCES `ram_product` (`ram`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_option_rom` FOREIGN KEY (`rom`) REFERENCES `rom_product` (`rom`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `fk_order_user_id` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `orderdetail`
--
ALTER TABLE `orderdetail`
  ADD CONSTRAINT `fk_orderdetail_option_id` FOREIGN KEY (`optionid`) REFERENCES `option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_orderdetail_order_id` FOREIGN KEY (`orderid`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `fk_product_brand_id` FOREIGN KEY (`brandid`) REFERENCES `brand` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `ram_product`
--
ALTER TABLE `ram_product`
  ADD CONSTRAINT `fk_ram_productid` FOREIGN KEY (`productid`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `rom_product`
--
ALTER TABLE `rom_product`
  ADD CONSTRAINT `fk_rom_productid` FOREIGN KEY (`productid`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `warranty`
--
ALTER TABLE `warranty`
  ADD CONSTRAINT `fk_warranty_option_id` FOREIGN KEY (`optionid`) REFERENCES `option` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_warranty_user_id` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `warrantyinvoice`
--
ALTER TABLE `warrantyinvoice`
  ADD CONSTRAINT `fk_warrantyinvoice_warranty_id` FOREIGN KEY (`warrantyid`) REFERENCES `warranty` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
