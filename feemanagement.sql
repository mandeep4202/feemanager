CREATE DATABASE  IF NOT EXISTS `libmanagement` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `libmanagement`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: libmanagement
-- ------------------------------------------------------
-- Server version	5.6.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `batch_details`
--

DROP TABLE IF EXISTS `batch_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batch_details` (
  `sessionid` int(11) NOT NULL DEFAULT '0',
  `studentsesion` varchar(30) DEFAULT NULL,
  `createdby` varchar(30) DEFAULT NULL,
  `creationdate` date DEFAULT NULL,
  `lastmodified` date DEFAULT NULL,
  `lastmodifiedby` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`sessionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_details`
--

LOCK TABLES `batch_details` WRITE;
/*!40000 ALTER TABLE `batch_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `batch_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_details`
--

DROP TABLE IF EXISTS `class_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class_details` (
  `classid` int(11) NOT NULL,
  `classname` varchar(20) DEFAULT NULL,
  `groupid` int(11) NOT NULL,
  `createdby` varchar(30) DEFAULT NULL,
  `creationdate` date DEFAULT NULL,
  `lastmodified` date DEFAULT NULL,
  `lastmodifiedby` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`classid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_details`
--

LOCK TABLES `class_details` WRITE;
/*!40000 ALTER TABLE `class_details` DISABLE KEYS */;
INSERT INTO `class_details` VALUES (300,'1st',100,'admin','2015-08-03','2015-08-03','admin'),(301,'2nd',100,'admin','2015-08-03','2015-08-03','admin'),(302,'MBA',101,'admin','2015-08-03','2015-08-03','admin'),(303,'MCA',101,'admin','2015-08-03','2015-08-03','admin'),(304,'Btech',102,'admin','2015-08-03','2015-08-03','admin'),(305,'Mtech',102,'admin','2015-08-03','2015-08-03','admin');
/*!40000 ALTER TABLE `class_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fee_details`
--

DROP TABLE IF EXISTS `fee_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fee_details` (
  `feeid` int(7) NOT NULL,
  `sessionid` int(7) DEFAULT NULL,
  `classid` int(7) DEFAULT NULL,
  `groupid` int(7) DEFAULT NULL,
  `particularsname` varchar(30) DEFAULT NULL,
  `particularamount` int(11) DEFAULT NULL,
  `createdby` varchar(30) DEFAULT NULL,
  `creationdate` date DEFAULT NULL,
  `lastmodified` date DEFAULT NULL,
  `lastmodifiedby` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`feeid`),
  UNIQUE KEY `unique_index` (`classid`,`sessionid`,`particularsname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fee_details`
--

LOCK TABLES `fee_details` WRITE;
/*!40000 ALTER TABLE `fee_details` DISABLE KEYS */;
INSERT INTO `fee_details` VALUES (5000,700,300,100,'Tution',500,'admin','2015-08-03','2015-08-03','admin'),(5001,700,300,100,'Sport',300,'admin','2015-08-03','2015-08-03','admin'),(5002,700,300,100,'Library',200,'admin','2015-08-03','2015-08-03','admin'),(5003,700,301,100,'Tution',500,'admin','2015-08-03','2015-08-03','admin'),(5004,700,301,100,'Sport',300,'admin','2015-08-03','2015-08-03','admin'),(5005,700,301,100,'Library',200,'admin','2015-08-03','2015-08-03','admin'),(5006,700,302,101,'Tution',5000,'admin','2015-08-03','2015-08-03','admin'),(5007,700,302,101,'Sport',2000,'admin','2015-08-03','2015-08-03','admin'),(5008,700,302,101,'library',700,'admin','2015-08-03','2015-08-03','admin'),(5009,700,302,101,'other',1000,'admin','2015-08-03','2015-08-03','admin'),(5010,700,303,101,'Tution',5000,'admin','2015-08-03','2015-08-03','admin'),(5011,700,303,101,'Sport',2000,'admin','2015-08-03','2015-08-03','admin'),(5012,700,303,101,'library',700,'admin','2015-08-03','2015-08-03','admin'),(5013,700,303,101,'other',1000,'admin','2015-08-03','2015-08-03','admin'),(5014,700,304,102,'Fine',400,'admin','2015-08-03','2015-08-03','admin'),(5015,700,305,102,'Fine',200,'admin','2015-08-03','2015-08-03','admin'),(5016,700,300,100,'Fine',200,'admin','2015-08-03','2015-08-03','admin'),(5017,700,301,100,'Fine',200,'admin','2015-08-03','2015-08-03','admin'),(5018,700,302,101,'Fine',200,'admin','2015-08-03','2015-08-03','admin'),(5019,700,303,101,'Fine',200,'admin','2015-08-03','2015-08-03','admin');
/*!40000 ALTER TABLE `fee_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fine_details`
--

DROP TABLE IF EXISTS `fine_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fine_details` (
  `fineid` int(5) NOT NULL,
  `fineamount` int(7) DEFAULT NULL,
  `regdno` int(15) DEFAULT NULL,
  `sessionid` int(11) DEFAULT NULL,
  `createdby` varchar(30) DEFAULT NULL,
  `creationdate` date DEFAULT NULL,
  `lastmodified` date DEFAULT NULL,
  `lastmodifiedby` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`fineid`),
  UNIQUE KEY `regdno` (`regdno`,`sessionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fine_details`
--

LOCK TABLES `fine_details` WRITE;
/*!40000 ALTER TABLE `fine_details` DISABLE KEYS */;
INSERT INTO `fine_details` VALUES (250,400,50011,700,'manu','2014-06-06','2015-08-03','admin'),(251,290,50012,700,'manu','2014-06-07','2015-08-09','manu'),(252,376,50013,700,'manu','2015-08-09','2015-08-03','admin');
/*!40000 ALTER TABLE `fine_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_details`
--

DROP TABLE IF EXISTS `group_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_details` (
  `groupid` int(11) NOT NULL DEFAULT '0',
  `groupname` varchar(30) DEFAULT NULL,
  `createdby` varchar(30) DEFAULT NULL,
  `creationdate` date DEFAULT NULL,
  `lastmodified` date DEFAULT NULL,
  `lastmodifiedby` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_details`
--

LOCK TABLES `group_details` WRITE;
/*!40000 ALTER TABLE `group_details` DISABLE KEYS */;
INSERT INTO `group_details` VALUES (100,'Primary','admin','2015-08-03','2015-08-03','admin'),(101,'SN College','admin','2015-08-03','2015-08-03','admin'),(102,'JP College','admin','2015-08-03','2015-08-03','admin');
/*!40000 ALTER TABLE `group_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paidfee_details`
--

DROP TABLE IF EXISTS `paidfee_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paidfee_details` (
  `feepayid` int(10) NOT NULL,
  `regdno` int(10) DEFAULT NULL,
  `classid` int(10) DEFAULT NULL,
  `sessionid` int(10) DEFAULT NULL,
  `groupid` int(10) DEFAULT NULL,
  `voucherid` int(10) DEFAULT NULL,
  `payamount` int(11) DEFAULT NULL,
  `remainingfee` int(11) DEFAULT NULL,
  `paydate` date DEFAULT NULL,
  `paytime` time DEFAULT NULL,
  `createdby` varchar(30) DEFAULT NULL,
  `creationdate` date DEFAULT NULL,
  `lastmodified` date DEFAULT NULL,
  `lastmodifiedby` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`feepayid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paidfee_details`
--

LOCK TABLES `paidfee_details` WRITE;
/*!40000 ALTER TABLE `paidfee_details` DISABLE KEYS */;
INSERT INTO `paidfee_details` VALUES (5000,50013,303,700,101,555,8000,1276,'2015-08-03','12:11:34','admin','2015-08-03','2015-08-03','admin'),(5001,50013,303,700,101,556,1276,0,'2015-08-03','12:11:57','admin','2015-08-03','2015-08-03','admin');
/*!40000 ALTER TABLE `paidfee_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_details`
--

DROP TABLE IF EXISTS `payment_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_details` (
  `paymentmodeid` int(15) NOT NULL,
  `bankname` varchar(45) DEFAULT NULL,
  `accountnumber` int(11) DEFAULT NULL,
  `chequenumber` varchar(45) DEFAULT NULL,
  `tellernumber` int(11) DEFAULT NULL,
  `pin` varchar(10) DEFAULT NULL,
  `createdby` varchar(30) DEFAULT NULL,
  `creationdate` date DEFAULT NULL,
  `lastmodified` date DEFAULT NULL,
  `lastmodifiedby` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_details`
--

LOCK TABLES `payment_details` WRITE;
/*!40000 ALTER TABLE `payment_details` DISABLE KEYS */;
INSERT INTO `payment_details` VALUES (700,'sbi',789789,'787',789,'798778','admin','2015-08-03','2015-08-03','admin'),(701,'sbi',789789,'787',789,'798778','admin','2015-08-03','2015-08-03','admin');
/*!40000 ALTER TABLE `payment_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `securitycheck`
--

DROP TABLE IF EXISTS `securitycheck`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `securitycheck` (
  `userid` int(11) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `lastlogindate` date DEFAULT NULL,
  `lastlogintime` time DEFAULT NULL,
  `createdby` varchar(30) DEFAULT NULL,
  `creationdate` date DEFAULT NULL,
  `lastmodified` date DEFAULT NULL,
  `lastmodifiedby` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `userid_UNIQUE` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `securitycheck`
--

LOCK TABLES `securitycheck` WRITE;
/*!40000 ALTER TABLE `securitycheck` DISABLE KEYS */;
INSERT INTO `securitycheck` VALUES (1000,'admin','admin','2014-05-06',NULL,NULL,NULL,NULL,NULL),(1002,'manu','manu','2014-05-06',NULL,NULL,NULL,NULL,NULL),(1003,'system','manager','2014-05-06',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `securitycheck` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_details`
--

DROP TABLE IF EXISTS `session_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session_details` (
  `sessionid` int(11) NOT NULL,
  `studentsession` varchar(35) DEFAULT NULL,
  `createdby` varchar(30) DEFAULT NULL,
  `creationdate` date DEFAULT NULL,
  `lastmodified` date DEFAULT NULL,
  `lastmodifiedby` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_details`
--

LOCK TABLES `session_details` WRITE;
/*!40000 ALTER TABLE `session_details` DISABLE KEYS */;
INSERT INTO `session_details` VALUES (700,'01/03/2014 to 28/02/2015','manu','2014-06-12','2014-06-12','manu'),(701,'01/03/2015 to 29/02/2016','manu','2014-06-12','2014-06-12','manu'),(702,'01/03/2016 to 28/02/2017','manu','2014-06-12','2014-06-12','manu'),(703,'01/03/2017 to 28/02/2018','manu','2014-06-12','2014-06-12',NULL),(704,'01/03/2019 to 28/02/2020','manu','2014-06-12','2014-06-12','manu');
/*!40000 ALTER TABLE `session_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_details`
--

DROP TABLE IF EXISTS `student_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_details` (
  `regdno` int(15) NOT NULL,
  `firstname` varchar(30) DEFAULT NULL,
  `lastname` varchar(30) DEFAULT NULL,
  `fatherfirstname` varchar(30) DEFAULT NULL,
  `fatherlastname` varchar(30) DEFAULT NULL,
  `classid` int(10) DEFAULT NULL,
  `sessionid` int(10) DEFAULT NULL,
  `groupid` int(10) DEFAULT NULL,
  `isactive` int(1) DEFAULT NULL,
  `createdby` varchar(30) DEFAULT NULL,
  `creationdate` date DEFAULT NULL,
  `lastmodified` date DEFAULT NULL,
  `lastmodifiedby` varchar(30) DEFAULT NULL,
  `addressid` int(7) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`regdno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_details`
--

LOCK TABLES `student_details` WRITE;
/*!40000 ALTER TABLE `student_details` DISABLE KEYS */;
INSERT INTO `student_details` VALUES (50011,'mandeep','singh','gian','singh',308,700,104,1,'manu','2015-06-06','2015-06-06','manu',12000,'male'),(50012,'nishu','rana','rajveer','singh',306,700,103,1,'manu','2015-06-06','2015-06-06','manu',12001,'female'),(50013,'Navdeep','SINGH','bikram','singh',303,700,101,1,'manu','2015-06-06','2015-06-06','manu',12003,'male');
/*!40000 ALTER TABLE `student_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher_details`
--

DROP TABLE IF EXISTS `voucher_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voucher_details` (
  `voucherid` int(11) NOT NULL DEFAULT '0',
  `vouchername` varchar(45) DEFAULT NULL,
  `ledgertype` varchar(45) DEFAULT NULL,
  `paymentmode` varchar(45) DEFAULT NULL,
  `paymentmodeid` varchar(15) DEFAULT NULL,
  `createdby` varchar(30) DEFAULT NULL,
  `creationdate` date DEFAULT NULL,
  `lastmodified` date DEFAULT NULL,
  `lastmodifiedby` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`voucherid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher_details`
--

LOCK TABLES `voucher_details` WRITE;
/*!40000 ALTER TABLE `voucher_details` DISABLE KEYS */;
INSERT INTO `voucher_details` VALUES (555,'Receipt ( paidin )','Admission','Cheque','700','admin','2015-08-03','2015-08-03','admin'),(556,'Receipt ( paidin )','Admission','Cash','701','admin','2015-08-03','2015-08-03','admin');
/*!40000 ALTER TABLE `voucher_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-12 12:03:23
