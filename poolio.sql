-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 14, 2016 at 08:07 PM
-- Server version: 5.5.3-m3-community
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `poolio`
--

-- --------------------------------------------------------

--
-- Table structure for table `registrations`
--

CREATE TABLE IF NOT EXISTS `registrations` (
  `mobile` varchar(11) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(10) NOT NULL,
  `registration_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id` int(8) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile` (`mobile`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `registrations`
--

INSERT INTO `registrations` (`mobile`, `first_name`, `last_name`, `email`, `password`, `registration_time`, `id`) VALUES
('1234', 'asd', 'qweasdsws', 'qwewqe', '1234', '2016-06-10 19:41:43', 1),
('2', 'Yd', 'EU', 'fh', 'w', '2016-06-11 12:33:27', 2),
('Ery', 'Tc', 'R', 't', 'y', '2016-06-11 19:08:00', 3);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
