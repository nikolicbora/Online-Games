-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Aug 31, 2019 at 08:24 PM
-- Server version: 5.7.24
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `igre`
--

-- --------------------------------------------------------

--
-- Table structure for table `5puta5`
--

DROP TABLE IF EXISTS `5puta5`;
CREATE TABLE IF NOT EXISTS `5puta5` (
  `id_5puta5` int(11) NOT NULL AUTO_INCREMENT,
  `rec1` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `rec2` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `rec3` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `rec4` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `rec5` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id_5puta5`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `5puta5`
--

INSERT INTO `5puta5` (`id_5puta5`, `rec1`, `rec2`, `rec3`, `rec4`, `rec5`) VALUES
(1, 'staja', 'valok', 'epovi', 'tivat', 'irina'),
(2, 'disko', 'usput', 'skoti', 'korac', 'opaki');

-- --------------------------------------------------------

--
-- Table structure for table `anagram`
--

DROP TABLE IF EXISTS `anagram`;
CREATE TABLE IF NOT EXISTS `anagram` (
  `id_anagrama` int(11) NOT NULL AUTO_INCREMENT,
  `pitanje1` varchar(50) NOT NULL,
  `odgovor1` varchar(50) NOT NULL,
  PRIMARY KEY (`id_anagrama`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `anagram`
--

INSERT INTO `anagram` (`id_anagrama`, `pitanje1`, `odgovor1`) VALUES
(1, 'krasan je odmor', 'jadransko more'),
(2, 'Ura, bekrije!', 'Bure rakije'),
(3, 'Preki driblaju blesave', 'Vlada republike Srbije');

-- --------------------------------------------------------

--
-- Table structure for table `geografija`
--

DROP TABLE IF EXISTS `geografija`;
CREATE TABLE IF NOT EXISTS `geografija` (
  `id_geografija` int(11) NOT NULL AUTO_INCREMENT,
  `slovo` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `rec_drzava` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rec_grad` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rec_jezero` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rec_planina` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rec_reka` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rec_zivotinja` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rec_biljka` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rec_grupa` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_geografija`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `geografija`
--

INSERT INTO `geografija` (`id_geografija`, `slovo`, `rec_drzava`, `rec_grad`, `rec_jezero`, `rec_planina`, `rec_reka`, `rec_zivotinja`, `rec_biljka`, `rec_grupa`) VALUES
(1, 'g', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(31, 'o', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(32, 'p', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(33, 'b', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(34, 'b', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(35, 'h', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(36, 'i', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(37, 'h', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(38, 'd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(39, 't', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(40, 'r', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(41, 'd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(42, 'k', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(43, 'o', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(44, 'f', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(45, 'h', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(46, 'k', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `kategorija_reci`
--

DROP TABLE IF EXISTS `kategorija_reci`;
CREATE TABLE IF NOT EXISTS `kategorija_reci` (
  `id_kategorija_reci` int(11) NOT NULL AUTO_INCREMENT,
  `kategorija` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id_kategorija_reci`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `kategorija_reci`
--

INSERT INTO `kategorija_reci` (`id_kategorija_reci`, `kategorija`) VALUES
(1, 'drzava'),
(2, 'grad'),
(3, 'jezero'),
(4, 'planina'),
(5, 'reka'),
(6, 'zivotinja'),
(7, 'biljka'),
(8, 'muzicka grupa');

-- --------------------------------------------------------

--
-- Table structure for table `partija`
--

DROP TABLE IF EXISTS `partija`;
CREATE TABLE IF NOT EXISTS `partija` (
  `dan` date NOT NULL,
  `id_anagrama` int(11) NOT NULL,
  `id_5puta5` int(11) NOT NULL,
  `id_geografija` int(11) NOT NULL,
  `id_pehar` int(11) NOT NULL,
  PRIMARY KEY (`dan`),
  KEY `ida` (`id_anagrama`),
  KEY `partija_ibfk_2_idx` (`id_5puta5`),
  KEY `partija_ibfk_3_idx` (`id_geografija`),
  KEY `partija_ibfk_4_idx` (`id_pehar`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `partija`
--

INSERT INTO `partija` (`dan`, `id_anagrama`, `id_5puta5`, `id_geografija`, `id_pehar`) VALUES
('2019-08-22', 1, 1, 1, 1),
('2019-08-26', 3, 1, 31, 1),
('2019-08-27', 2, 2, 33, 3),
('2019-08-28', 2, 2, 40, 3),
('2019-08-29', 2, 2, 41, 3),
('2019-08-30', 2, 2, 42, 3),
('2019-09-01', 1, 2, 44, 1),
('2019-09-02', 3, 1, 43, 1),
('2019-09-03', 3, 2, 45, 3),
('2019-09-04', 3, 2, 46, 3);

-- --------------------------------------------------------

--
-- Table structure for table `pehar`
--

DROP TABLE IF EXISTS `pehar`;
CREATE TABLE IF NOT EXISTS `pehar` (
  `id_pehar` int(11) NOT NULL AUTO_INCREMENT,
  `p1` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o1` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL,
  `p2` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o2` varchar(8) COLLATE utf8_unicode_ci DEFAULT NULL,
  `p3` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o3` varchar(7) COLLATE utf8_unicode_ci DEFAULT NULL,
  `p4` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o4` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `p5` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o5` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `p6` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o6` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `p7` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o7` varchar(3) COLLATE utf8_unicode_ci DEFAULT NULL,
  `p8` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o8` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `p9` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o9` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `p10` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o10` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `p11` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o11` varchar(7) COLLATE utf8_unicode_ci DEFAULT NULL,
  `p12` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o12` varchar(8) COLLATE utf8_unicode_ci DEFAULT NULL,
  `p13` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `o13` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_pehar`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `pehar`
--

INSERT INTO `pehar` (`id_pehar`, `p1`, `o1`, `p2`, `o2`, `p3`, `o3`, `p4`, `o4`, `p5`, `o5`, `p6`, `o6`, `p7`, `o7`, `p8`, `o8`, `p9`, `o9`, `p10`, `o10`, `p11`, `o11`, `p12`, `o12`, `p13`, `o13`) VALUES
(1, 'Vrsta ratnog broda', 'krstarica', 'Mala krasta', 'krastica', 'Stara zena', 'starica', 'Ime pevacice Montiel', 'Sarita', 'Covek koji koristi reseto', 'sitar', 'Ime glumice Hayworth', 'Rita', 'Konji u narodnim pesmama', 'ati', 'Prkos ili', 'inat', 'Stara mera za tecnost', 'pinta', 'Zelenkasta rdja', 'patina', 'Izvrsiti napad', 'napasti', 'Drzava u Aziji', 'Pakistan', 'Odobrenje ili saglasnost', 'pristanak'),
(3, 'Kraljevski dvorac u blizini Madrida', 'Eskorijal', 'Vozači kola', 'kolesari', 'splavari ili', 'skelari', 'Nemački šahovski velemajstor Emanuel', 'Lasker', 'Programski jezik za pocetnike', 'Karel', 'Vodeni tok', 'reka', 'Vodeni ljuskar', 'rak', 'Takmičenje u brzini', 'trka', 'Drugi naziv za ulaznice', 'karte', 'Osoba koja pravi četke', 'četkar', 'Obrtač ili', 'okretač', 'Žene koje se bave stočarstvom', 'stočarke', 'Glasnik, glasonoša ili', 'skoroteča');

-- --------------------------------------------------------

--
-- Table structure for table `recnik`
--

DROP TABLE IF EXISTS `recnik`;
CREATE TABLE IF NOT EXISTS `recnik` (
  `id_reci` int(11) NOT NULL AUTO_INCREMENT,
  `rec` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `id_kategorija_reci` int(11) NOT NULL,
  PRIMARY KEY (`id_reci`),
  KEY `recnik_ibfk_1_idx` (`id_kategorija_reci`)
) ENGINE=InnoDB AUTO_INCREMENT=318 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `recnik`
--

INSERT INTO `recnik` (`id_reci`, `rec`, `id_kategorija_reci`) VALUES
(1, 'srbija', 1),
(2, 'amerika', 1),
(3, 'turska', 1),
(4, 'ukrajina', 1),
(5, 'bosna i hercegovina', 1),
(6, 'crna gora', 1),
(7, 'makedonija', 1),
(8, 'rumunija', 1),
(9, 'grcka', 1),
(10, 'spanija', 1),
(12, 'belgija', 1),
(13, 'norveska', 1),
(14, 'zambija', 1),
(15, 'tajland', 1),
(16, 'kanada', 1),
(17, 'gornji milanovac', 2),
(20, 'albanija', 1),
(21, 'austrija', 1),
(22, 'australija', 1),
(24, 'atina', 2),
(25, 'aman', 2),
(26, 'aleksinac', 2),
(27, 'alzir', 2),
(28, 'avala', 4),
(29, 'alpi', 4),
(30, 'andi', 4),
(31, 'ararat', 4),
(32, 'amazon', 5),
(33, 'amur', 5),
(34, 'angara', 5),
(35, 'arno', 5),
(36, 'albano', 3),
(37, 'aralsko', 3),
(38, 'arenal', 3),
(39, 'ananas', 7),
(40, 'aloja', 7),
(41, 'aronija', 7),
(42, 'anis', 7),
(43, 'ajkula', 6),
(44, 'antilopa', 6),
(45, 'belgija', 1),
(46, 'banglades', 1),
(47, 'bugarska', 1),
(48, 'brzail', 1),
(49, 'budimpesta', 2),
(50, 'bratislava', 2),
(51, 'bagdad', 2),
(52, 'berlin', 2),
(53, 'begej', 5),
(54, 'bistrica', 5),
(55, 'bojana', 5),
(56, 'bosna', 4),
(57, 'bobija', 4),
(58, 'bukulja', 4),
(59, 'bajkalsko', 3),
(60, 'bled', 3),
(61, 'bik', 6),
(62, 'bizon', 6),
(63, 'bagrem', 7),
(64, 'borovnica', 7),
(65, 'bukva', 7),
(66, 'cirih', 2),
(67, 'cetinje', 2),
(68, 'cirih', 2),
(69, 'crnica', 5),
(70, 'cetina', 5),
(71, 'cer', 4),
(72, 'crni vrh', 4),
(73, 'crno jezero', 3),
(74, 'crv', 6),
(75, 'crvcak', 6),
(76, 'celer', 7),
(77, 'cvekla', 7),
(78, 'danska', 1),
(79, 'dominika', 1),
(80, 'dablin', 2),
(81, 'dubrovnik', 2),
(82, 'dimitrovgrad', 2),
(83, 'drina', 5),
(84, 'dunav', 5),
(85, 'drava', 5),
(86, 'durmitor', 4),
(87, 'dukat', 4),
(88, 'dojransko', 3),
(89, 'dabar', 6),
(90, 'delfin', 6),
(91, 'detlic', 6),
(92, 'dinja', 7),
(93, 'dunja', 7),
(94, 'egipat', 1),
(95, 'estonija', 1),
(96, 'etiopija', 1),
(97, 'edinburg', 2),
(98, 'elba', 5),
(99, 'eufrat', 5),
(100, 'etna', 4),
(101, 'er', 3),
(102, 'emu', 6),
(103, 'eukaliptus', 7),
(104, 'francuska', 1),
(105, 'finska', 1),
(106, 'frankfurt', 2),
(107, 'fulda', 5),
(108, 'fruska gora', 4),
(109, 'fitri', 3),
(110, 'fazan', 6),
(111, 'foka', 6),
(112, 'fikus', 7),
(113, 'gana', 1),
(114, 'gruzija', 1),
(115, 'grac', 2),
(116, 'gradac', 5),
(117, 'gang', 5),
(118, 'golija', 4),
(119, 'gazivode', 3),
(120, 'golub', 6),
(121, 'guska', 6),
(122, 'gepard', 6),
(123, 'grasak', 7),
(124, 'holandija', 1),
(125, 'hrvatska', 1),
(126, 'hamburg', 2),
(127, 'hamza', 5),
(128, 'hadson', 5),
(129, 'himalaji', 4),
(130, 'hasan', 3),
(131, 'hijena', 6),
(132, 'hrcak', 6),
(133, 'hobotnica', 6),
(134, 'hrast', 7),
(135, 'hmelj', 7),
(136, 'japan', 1),
(137, 'jamajka', 1),
(138, 'jagodina', 2),
(139, 'jordan', 5),
(140, 'jelica', 4),
(141, 'jarevac', 3),
(142, 'jarac', 6),
(143, 'jelen', 6),
(144, 'jabuka', 7),
(145, 'javor', 7),
(146, 'kanada', 1),
(147, 'kolumbija', 1),
(148, 'kijev', 2),
(149, 'kraljevo', 2),
(150, 'kolorado', 5),
(151, 'kolubara', 5),
(152, 'kopaonik', 4),
(153, 'kosmaj', 4),
(154, 'kaspijsko', 3),
(155, 'kamila', 6),
(156, 'kornjaca', 6),
(157, 'kamilica', 7),
(158, 'laos', 1),
(159, 'letonija', 1),
(160, 'london', 2),
(161, 'lim', 5),
(162, 'lovcen', 4),
(163, 'ladoga', 3),
(164, 'lav', 6),
(165, 'leopard', 6),
(166, 'lala', 7),
(175, 'vijetnam', 1),
(176, 'valjevo', 2),
(177, 'vardar', 5),
(178, 'vltava', 5),
(179, 'vardenik', 4),
(180, 'vlasinsko', 3),
(181, 'veverica', 6),
(182, 'vuk', 6),
(183, 'visnja', 7),
(184, 'visibaba', 7),
(185, 'nemacka', 1),
(186, 'norveska', 1),
(187, 'nis', 2),
(188, 'negotin', 2),
(189, 'neretva', 5),
(190, 'nimba', 4),
(191, 'nuga', 3),
(192, 'nosorog', 6),
(193, 'noj', 6),
(194, 'nar', 7),
(195, 'nana', 7),
(196, 'makedonija', 1),
(197, 'mlava', 5),
(198, 'madrid', 2),
(199, 'minhen', 2),
(200, 'medvednik', 4),
(201, 'micigen', 3),
(202, 'medved', 6),
(203, 'magarac', 6),
(204, 'macka', 6),
(205, 'mak', 7),
(206, 'maslacak', 7),
(207, 'ruanda', 1),
(208, 'rim', 2),
(209, 'roterdam', 2),
(210, 'rajna', 5),
(211, 'resava', 5),
(212, 'rudnik', 4),
(213, 'rtanj', 4),
(214, 'ribnicko', 3),
(215, 'rak', 6),
(216, 'roda', 6),
(217, 'ruza', 7),
(218, 'ren', 7),
(219, 'tajland', 1),
(220, 'turska', 1),
(221, 'tunis', 2),
(222, 'tuzla', 2),
(223, 'tisa', 5),
(224, 'toplica', 5),
(225, 'tara', 4),
(226, 'tuz', 3),
(227, 'tigar', 6),
(228, 'tvor', 6),
(229, 'tresnja', 7),
(230, 'zambija', 1),
(231, 'zimbabve', 1),
(232, 'zajecar', 2),
(233, 'zenica', 2),
(234, 'zapadna morava', 5),
(235, 'zlatica', 5),
(236, 'zlatibor', 4),
(237, 'zlatarsko jezero', 3),
(238, 'zlatnicko jezero', 3),
(239, 'zec', 6),
(240, 'zmija', 6),
(241, 'zlatnica', 7),
(242, 'uganda', 1),
(243, 'uzice', 2),
(244, 'urosevac', 2),
(245, 'ub', 5),
(246, 'uvac', 5),
(247, 'ural', 4),
(248, 'uvacko jeezro', 3),
(249, 'umbra', 6),
(250, 'uva', 7),
(251, 'italija', 1),
(252, 'indija', 1),
(253, 'irak', 1),
(254, 'iran', 1),
(255, 'istanbul', 2),
(256, 'ibar', 5),
(257, 'igman', 4),
(258, 'iri', 3),
(259, 'irvas', 6),
(260, 'imela', 7),
(261, 'oman', 1),
(262, 'otava', 2),
(263, 'ohrid', 2),
(264, 'ob', 5),
(265, 'olimp', 4),
(266, 'ovcar', 4),
(267, 'okanj', 3),
(268, 'orao', 6),
(269, 'osa', 6),
(270, 'orah', 7),
(271, 'pakistan', 1),
(272, 'poljska', 1),
(273, 'podgorica', 2),
(274, 'pancevo', 2),
(275, 'piva', 5),
(276, 'po', 5),
(277, 'povlen', 4),
(278, 'perucac', 3),
(279, 'pas', 6),
(280, 'panda', 6),
(281, 'paun', 6),
(282, 'persun', 7),
(283, 'pirinac', 7),
(284, 'ac/dc', 8),
(285, 'bauhaus', 8),
(286, 'carlos santana', 8),
(287, 'david bowie', 8),
(288, 'elton john', 8),
(289, 'frank sinatra', 8),
(290, 'green day', 8),
(291, 'guns n\'roses', 8),
(292, 'ian dury', 8),
(293, 'jimi hendrix', 8),
(294, 'kiss', 8),
(295, 'led zeppelin', 8),
(296, 'madness', 8),
(297, 'nirvana', 8),
(298, 'oasis', 8),
(299, 'pearls jam', 8),
(300, 'rolling stones', 8),
(301, 'sex pistols', 8),
(302, 'tom jones', 8),
(303, 'u2', 8),
(304, 'van morrison', 8),
(305, 'abba', 8),
(306, 'san marino', 2),
(307, 'solun', 2),
(308, 'sofija', 2),
(309, 'sava', 5),
(310, 'stara planina', 4),
(311, 'suva planina', 4),
(312, 'skadarsko', 4),
(313, 'slon', 6),
(314, 'svinja', 6),
(315, 'smokva', 7),
(316, 'suncokret', 7),
(317, 'venecuela', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `ime` varchar(50) NOT NULL,
  `prezime` varchar(50) NOT NULL,
  `mail` varchar(50) NOT NULL,
  `zanimanje` varchar(50) NOT NULL,
  `pol` varchar(50) NOT NULL,
  `jmbg` varchar(50) NOT NULL,
  `pitanje` varchar(50) NOT NULL,
  `odgovor` varchar(50) NOT NULL,
  `tip` varchar(50) NOT NULL,
  `odobren` int(11) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `password`, `ime`, `prezime`, `mail`, `zanimanje`, `pol`, `jmbg`, `pitanje`, `odgovor`, `tip`, `odobren`) VALUES
('bora', 'Sifra123!', 'bora', 'boric', 'bora@gmail.com', 'lekar', 'M', '1234567891234', 'sta mislite?', 'ne znam', 'admin', 1),
('dalibor', 'Sifra123!', 'dalibor', 'dalic', 'dalibor@gmail.com', 'lekar', 'M', '1234567891123', 'boja?', 'plava', 'ucesnik', 1),
('darko', 'Sifra123!', 'darko', 'daric', 'darko@gmail.com', 'lekar', 'M', '1234567891123', 'boja?', 'plava', 'ucesnik', 1),
('dragan', 'Sifra123!', 'dragan', 'dragic', 'dragan@gmail.com', 'lekar', 'M', '1234567891123', 'boja?', 'plava', 'ucesnik', 1),
('gaga', 'Sifra123!', 'gaga', 'gagic', 'gaga@gmail.com', 'lekar', 'Z', '1234567891123', 'boja?', 'plava', 'admin', 0),
('jana', 'Sifra123!', 'jana', 'janic', 'jana@gmail.com', 'lekar', 'M', '1234567891123', 'boja?', 'plava', 'ucesnik', 1),
('john', 'Sifra456!', 'john', 'pajic', 'john@gmail.com', 'lekar', 'M', '1234567891123', 'omiljena boja?', 'plava', 'ucesnik', 1),
('kaja', 'Sifra123!', 'kaja', 'kajic', 'kaja@gmail.com', 'lekar', 'M', '1234567891123', 'boja?', 'plava', 'ucesnik', 1),
('marko', 'Sifra123!', 'marko', 'maric', 'mare@gmail.com', 'lekar', 'M', '1234567891123', 'boja?', 'plava', 'ucesnik', 1),
('milos', 'Sifra123!', 'milos', 'misic', 'milos@gmail.com', 'lekar', 'M', '1234567891123', 'omilena boja?', 'plava', 'ucesnik', 1),
('paja', 'Babc1%34', 'paja', 'pajic', 'paja@gmail.com', 'lekar', 'M', '1234567891123', 'omiljena boja?', 'zuta', 'ucesnik', 0),
('smiljana', 'Sifra123!', 'smiljana', 'smilic', 'smilja@gmail.com', 'lekar', 'Z', '1234567891123', 'boja?', 'plava', 'ucesnik', 1),
('sonja', 'Sifra123!', 'sonja', 'sonjic', 'sonja@gmail.com', 'lekar', 'Z', '1234567891123', 'boja?', 'plava', 'ucesnik', 1),
('stefan', 'Sifra123!', 'stefan', 'stevic', 'stefan@gmail.com', 'lekar', 'M', '1234567891123', 'boja?', 'plava', 'ucesnik', 1),
('steven', 'Sifra123!', 'steven', 'shapiro', 'steven@gmail.com', 'lekar', 'M', '1234567891123', 'omiljena boja?', 'plava', 'supervizor', 1),
('tanja', 'Sifra123!', 'tanja', 'tanjic', 'tanja@gmail.com', 'lekar', 'Z', '1234567891123', 'boja?', 'plava', 'ucesnik', 1),
('tara', 'Sifra123!', 'dadadd', 'adsda', 'dadadad', 'adadsda', 'M', '1234567891234', 'sta mislite?', 'ne znam', 'ucesnik', 1),
('zara', 'Sifra123!', 'zara', 'zaric', 'zara@gmail.com', 'lekar', 'Z', '1234567891123', 'omiljena boja?', 'plava', 'ucesnik', 1),
('zora', 'Sifra123!', 'zora', 'zoric', 'zora@gmail.com', 'lekar', 'Z', '1234567891123', 'boja?', 'plava', 'ucesnik', 1);

-- --------------------------------------------------------

--
-- Table structure for table `veza`
--

DROP TABLE IF EXISTS `veza`;
CREATE TABLE IF NOT EXISTS `veza` (
  `id_veza` int(11) NOT NULL AUTO_INCREMENT,
  `dan` date NOT NULL,
  `username` varchar(50) NOT NULL,
  `status` int(11) NOT NULL,
  `poeni1` int(11) NOT NULL DEFAULT '0',
  `poeni2` int(11) NOT NULL DEFAULT '0',
  `poeni3` int(11) NOT NULL DEFAULT '0',
  `poeni4` int(11) NOT NULL DEFAULT '0',
  `poeni5` int(11) NOT NULL DEFAULT '0',
  `provera` text,
  PRIMARY KEY (`id_veza`),
  UNIQUE KEY `unique_kolone` (`dan`,`username`),
  KEY `dan` (`dan`),
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `veza`
--

INSERT INTO `veza` (`id_veza`, `dan`, `username`, `status`, `poeni1`, `poeni2`, `poeni3`, `poeni4`, `poeni5`, `provera`) VALUES
(10, '2019-08-22', 'tara', 0, 0, 0, 20, 0, 0, ''),
(15, '2019-08-22', 'john', 0, 0, 0, 20, 12, 0, ''),
(16, '2019-08-29', 'john', 0, 0, 10, 20, 6, 0, ''),
(17, '2019-08-29', 'dalibor', 0, 10, 10, 2, 2, 2, NULL),
(18, '2019-08-29', 'darko', 0, 10, 10, 4, 4, 4, NULL),
(19, '2019-08-29', 'dragan', 0, 10, 10, 6, 6, 6, NULL),
(20, '2019-08-29', 'jana', 0, 10, 0, 8, 8, 8, NULL),
(21, '2019-08-29', 'kaja', 0, 10, 10, 0, 0, 0, NULL),
(28, '2019-08-29', 'marko', 0, 0, 0, 2, 2, 4, NULL),
(29, '2019-08-29', 'smiljana', 0, 0, 0, 2, 4, 2, NULL),
(30, '2019-08-29', 'sonja', 0, 10, 0, 2, 0, 2, NULL),
(31, '2019-08-29', 'stefan', 0, 0, 10, 0, 10, 10, NULL),
(32, '2019-08-29', 'tanja', 0, 10, 10, 20, 0, 20, NULL),
(33, '2019-08-29', 'zora', 0, 0, 0, 6, 8, 6, NULL),
(34, '2019-08-29', 'tara', 0, 0, 10, 20, 0, 0, ''),
(35, '2019-08-29', 'milos', 0, 0, 0, 20, 0, 0, '');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `partija`
--
ALTER TABLE `partija`
  ADD CONSTRAINT `partija_ibfk_1` FOREIGN KEY (`id_anagrama`) REFERENCES `anagram` (`id_anagrama`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `partija_ibfk_2` FOREIGN KEY (`id_5puta5`) REFERENCES `5puta5` (`id_5puta5`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `partija_ibfk_3` FOREIGN KEY (`id_geografija`) REFERENCES `geografija` (`id_geografija`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `partija_ibfk_4` FOREIGN KEY (`id_pehar`) REFERENCES `pehar` (`id_pehar`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `recnik`
--
ALTER TABLE `recnik`
  ADD CONSTRAINT `recnik_ibfk_1` FOREIGN KEY (`id_kategorija_reci`) REFERENCES `kategorija_reci` (`id_kategorija_reci`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `veza`
--
ALTER TABLE `veza`
  ADD CONSTRAINT `veza_ibfk_1` FOREIGN KEY (`dan`) REFERENCES `partija` (`dan`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `veza_ibfk_2` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
