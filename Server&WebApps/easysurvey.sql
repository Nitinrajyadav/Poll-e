-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 20, 2017 at 12:07 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `easysurvey`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `role` varchar(20) NOT NULL,
  `email` varchar(64) NOT NULL,
  `token` varchar(128) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
`id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`username`, `password`, `role`, `email`, `token`, `created_date`, `id`) VALUES
('root', 'root', 'SuperAdmin', 'rvshekhar10@gmail.com', '9018038f83e53ced2a8718b3a262e700', '2017-03-18 09:39:40', 1);

-- --------------------------------------------------------

--
-- Table structure for table `adminentry`
--

CREATE TABLE IF NOT EXISTS `adminentry` (
`id` int(11) NOT NULL,
  `name` text NOT NULL,
  `type` int(1) NOT NULL,
  `admin` int(11) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `adminentry`
--

INSERT INTO `adminentry` (`id`, `name`, `type`, `admin`, `created_date`) VALUES
(1, 'Who is the next PM ?', 1, 1, '2017-03-17 23:39:58'),
(2, 'Main Reasons for last Accident', 2, 1, '2017-03-17 23:39:58'),
(3, 'New Road is Necessary in Village ', 3, 1, '2017-03-17 23:40:45');

-- --------------------------------------------------------

--
-- Table structure for table `list-item`
--

CREATE TABLE IF NOT EXISTS `list-item` (
`id` int(11) NOT NULL,
  `entry_id` int(11) NOT NULL,
  `name` text NOT NULL,
  `count` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `list-item`
--

INSERT INTO `list-item` (`id`, `entry_id`, `name`, `count`) VALUES
(1, 1, 'Narendra Modi', 2),
(2, 1, 'Rahul Gandhi', 1),
(3, 2, 'Heavy Traffic', 0),
(4, 2, 'Bad Roads', 1),
(5, 2, 'Heavy Rainfall', 2),
(6, 2, 'No Order', 0),
(7, 3, 'Rating', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
`id` int(11) NOT NULL,
  `CATEGORY_DESC_ENG` varchar(11) DEFAULT NULL,
  `STATE` varchar(64) NOT NULL,
  `MOTHER_NAME_ENG` varchar(225) DEFAULT NULL,
  `HOUSE_NUMBER_ENG` varchar(20) DEFAULT NULL,
  `RELATION_ENG` varchar(225) DEFAULT NULL,
  `DOB` date NOT NULL,
  `MEMBER_AADHAR_ID` varchar(64) DEFAULT NULL,
  `ECONOMIC_GROUP` varchar(64) DEFAULT NULL,
  `BUILDING_NO_ENG` varchar(64) DEFAULT NULL,
  `BHAMASHAH_ID` varchar(64) NOT NULL,
  `STREET_NAME_ENG` varchar(64) DEFAULT NULL,
  `IFSC_CODE` varchar(64) DEFAULT NULL,
  `M_ID` varchar(64) DEFAULT NULL,
  `FAMILYIDNO` varchar(64) DEFAULT NULL,
  `PIN_CODE` varchar(64) DEFAULT NULL,
  `LANDLINE_NO` varchar(64) DEFAULT NULL,
  `VILLAGE_NAME` varchar(64) DEFAULT NULL,
  `GP_WARD` varchar(64) DEFAULT NULL,
  `EMAIL` varchar(225) DEFAULT NULL,
  `SPOUCE_NAME_ENG` varchar(225) DEFAULT NULL,
  `IS_RURAL` varchar(64) DEFAULT NULL,
  `DISTRICT` varchar(64) DEFAULT NULL,
  `EDUCATION_DESC_ENG` varchar(64) DEFAULT NULL,
  `ACC_NO` varchar(64) DEFAULT NULL,
  `ADDRESS` text,
  `INCOME_DESC_ENG` varchar(64) DEFAULT NULL,
  `BANK_NAME` varchar(64) DEFAULT NULL,
  `LAND_MARK_ENG` varchar(64) DEFAULT NULL,
  `RATION_CARD_NO` varchar(64) DEFAULT NULL,
  `NAME_ENG` varchar(64) NOT NULL,
  `MOBILE` varchar(64) NOT NULL,
  `GENDER` varchar(64) NOT NULL,
  `FATHER_NAME_ENG` varchar(64) DEFAULT NULL,
  `FAX_NO` varchar(64) DEFAULT NULL,
  `BLOCK_CITY` varchar(64) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `CATEGORY_DESC_ENG`, `STATE`, `MOTHER_NAME_ENG`, `HOUSE_NUMBER_ENG`, `RELATION_ENG`, `DOB`, `MEMBER_AADHAR_ID`, `ECONOMIC_GROUP`, `BUILDING_NO_ENG`, `BHAMASHAH_ID`, `STREET_NAME_ENG`, `IFSC_CODE`, `M_ID`, `FAMILYIDNO`, `PIN_CODE`, `LANDLINE_NO`, `VILLAGE_NAME`, `GP_WARD`, `EMAIL`, `SPOUCE_NAME_ENG`, `IS_RURAL`, `DISTRICT`, `EDUCATION_DESC_ENG`, `ACC_NO`, `ADDRESS`, `INCOME_DESC_ENG`, `BANK_NAME`, `LAND_MARK_ENG`, `RATION_CARD_NO`, `NAME_ENG`, `MOBILE`, `GENDER`, `FATHER_NAME_ENG`, `FAX_NO`, `BLOCK_CITY`, `created_date`) VALUES
(1, NULL, 'Rajasthan', NULL, NULL, NULL, '1992-02-16', NULL, NULL, NULL, '11111', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Seeta', '9549783966', 'Female', NULL, NULL, NULL, '2017-03-15 20:06:40'),
(2, NULL, 'Rajasthan', NULL, NULL, NULL, '1989-10-18', NULL, NULL, NULL, '22222', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Rohan', '8520852085', 'Male', NULL, NULL, NULL, '2017-03-15 20:21:40');

-- --------------------------------------------------------

--
-- Table structure for table `user_response`
--

CREATE TABLE IF NOT EXISTS `user_response` (
`id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `entry_id` int(11) NOT NULL,
  `listItem_id` int(11) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_response`
--

INSERT INTO `user_response` (`id`, `user_id`, `entry_id`, `listItem_id`, `createdDate`) VALUES
(1, 1, 1, 1, '2017-03-19 07:39:49');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `adminentry`
--
ALTER TABLE `adminentry`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `list-item`
--
ALTER TABLE `list-item`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_response`
--
ALTER TABLE `user_response`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `adminentry`
--
ALTER TABLE `adminentry`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `list-item`
--
ALTER TABLE `list-item`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `user_response`
--
ALTER TABLE `user_response`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
