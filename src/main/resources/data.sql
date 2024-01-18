-- MySQL dump 10.13  Distrib 8.2.0, for Linux (aarch64)
--
-- Host: localhost    Database: wooriAccount
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `abstract_tx_history`
--

DROP TABLE IF EXISTS `abstract_tx_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `abstract_tx_history` (
                                       `dtype` varchar(31) COLLATE utf8mb4_general_ci NOT NULL,
                                       `tx_id` bigint NOT NULL AUTO_INCREMENT,
                                       `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
                                       `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '이용가능여부',
                                       `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
                                       `amount` decimal(19,2) NOT NULL,
                                       `balance_after_tx` decimal(19,2) NOT NULL,
                                       `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                       `receiver_account_id` bigint DEFAULT NULL,
                                       `sender_account_id` bigint DEFAULT NULL,
                                       PRIMARY KEY (`tx_id`),
                                       KEY `FK71us879ya3c1s35qwygq8fhus` (`receiver_account_id`),
                                       KEY `FKd72qmkfi05apa07vluc304u5d` (`sender_account_id`),
                                       CONSTRAINT `FK71us879ya3c1s35qwygq8fhus` FOREIGN KEY (`receiver_account_id`) REFERENCES `accounts` (`account_id`),
                                       CONSTRAINT `FKd72qmkfi05apa07vluc304u5d` FOREIGN KEY (`sender_account_id`) REFERENCES `accounts` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abstract_tx_history`
--

LOCK TABLES `abstract_tx_history` WRITE;
/*!40000 ALTER TABLE `abstract_tx_history` DISABLE KEYS */;
INSERT INTO `abstract_tx_history` VALUES ('withdraw_tx',1,'2023-01-18 11:43:58',_binary '\0','2023-01-18 11:43:58',1234.00,24955491.00,'ㅈㄷㄱㅎㄴ',1,2),('deposit_tx',2,'2023-01-18 11:43:58',_binary '\0','2023-01-18 11:43:58',1234.00,51234.80,'ㅈㄷㄱㅎㄴ',2,1),('withdraw_tx',3,'2023-02-25 11:43:58',_binary '\0','2023-02-25 11:43:58',457.00,24955034.00,'135ㄴㄹㅇㅎ',1,2),('deposit_tx',4,'2023-02-25 11:43:58',_binary '\0','2023-02-25 11:43:58',457.00,51691.80,'135ㄴㄹㅇㅎ',2,1),('withdraw_tx',5,'2023-03-01 11:44:32',_binary '\0','2023-03-01 11:44:32',587456.00,24367578.00,'ㅓ서ㅜㅇㅅㅎ',1,2),('deposit_tx',6,'2023-03-01 11:44:32',_binary '\0','2023-03-01 11:44:32',587456.00,639147.80,'ㅓ서ㅜㅇㅅㅎ',2,1),('withdraw_tx',7,'2023-04-21 11:44:32',_binary '\0','2023-04-21 11:44:32',36436.00,24331142.00,'ㅓㄱㅅㄱ석서',1,2),('deposit_tx',8,'2023-04-21 11:44:32',_binary '\0','2023-04-21 11:44:32',36436.00,675583.80,'ㅓㄱㅅㄱ석서',2,1),('withdraw_tx',9,'2023-05-17 11:44:32',_binary '\0','2023-05-17 11:44:32',68374.00,24262768.00,'ㅎㅎㅂ',1,2),('deposit_tx',10,'2023-05-17 11:44:32',_binary '\0','2023-05-17 11:44:32',68374.00,743957.80,'ㅎㅎㅂ',2,1),('withdraw_tx',11,'2023-06-19 11:44:32',_binary '\0','2023-06-19 11:44:32',3423.00,24259345.00,'425ㄱㅎㄷㅈ',1,2),('deposit_tx',12,'2023-06-19 11:44:32',_binary '\0','2023-06-19 11:44:32',3423.00,747380.80,'425ㄱㅎㄷㅈ',2,1),('withdraw_tx',13,'2023-07-19 11:44:32',_binary '\0','2023-07-19 11:44:32',312453.00,23946892.00,'ㄷㄱㅁㄹㅁㅇㄹㅁㄹㅈㄷㄹ',1,2),('deposit_tx',14,'2023-07-19 11:44:32',_binary '\0','2023-07-19 11:44:32',312453.00,1059833.80,'ㄷㄱㅁㄹㅁㅇㄹㅁㄹㅈㄷㄹ',2,1),('withdraw_tx',15,'2023-08-04 11:44:32',_binary '\0','2023-08-04 11:44:32',4353.00,23942539.00,'ㄷㅈㄱㅎ',1,2),('deposit_tx',16,'2023-08-04 11:44:32',_binary '\0','2023-08-04 11:44:32',4353.00,1064186.80,'ㄷㅈㄱㅎ',2,1),('withdraw_tx',17,'2023-09-14 11:44:32',_binary '\0','2023-09-14 11:44:32',4353.00,23938186.00,'ㄷㅈㄱㅎ',1,2),('deposit_tx',18,'2023-09-14 11:44:32',_binary '\0','2023-09-14 11:44:32',4353.00,1068539.80,'ㄷㅈㄱㅎ',2,1),('withdraw_tx',19,'2023-10-11 11:47:37',_binary '\0','2023-10-11 11:47:37',34345.00,23903841.00,'ㄷㅈㄱㅎ',1,2),('deposit_tx',20,'2023-10-11 11:47:37',_binary '\0','2023-10-11 11:47:37',34345.00,1102884.80,'ㄷㅈㄱㅎ',2,1),('withdraw_tx',21,'2023-10-18 11:47:53',_binary '\0','2023-10-18 11:47:53',435625.00,23468216.00,'543ㅅㅈㄷㄹㅁ',1,2),('deposit_tx',22,'2023-10-18 11:47:53',_binary '\0','2023-10-18 11:47:53',435625.00,1538509.80,'543ㅅㅈㄷㄹㅁ',2,1),('withdraw_tx',23,'2023-11-11 11:47:59',_binary '\0','2023-11-11 11:47:59',523.00,23467693.00,'ㅗㄷㄱㅅㅎ',1,2),('deposit_tx',24,'2023-11-11 11:47:59',_binary '\0','2023-11-11 11:47:59',523.00,1539032.80,'ㅗㄷㄱㅅㅎ',2,1),('withdraw_tx',25,'2023-11-18 11:48:05',_binary '\0','2023-11-18 11:48:05',24662.00,23443031.00,'좋ㄱㅈㅅㄱㄷㅎㄴ',1,2),('deposit_tx',26,'2023-11-18 11:48:05',_binary '\0','2023-11-18 11:48:05',24662.00,1563694.80,'좋ㄱㅈㅅㄱㄷㅎㄴ',2,1),('withdraw_tx',27,'2023-11-29 11:48:10',_binary '\0','2023-11-29 11:48:10',754.00,23442277.00,'ㅗㅗㄴㅎㄴ',1,2),('deposit_tx',28,'2023-11-29 11:48:10',_binary '\0','2023-11-29 11:48:10',754.00,1564448.80,'ㅗㅗㄴㅎㄴ',2,1),('withdraw_tx',29,'2023-12-04 11:48:15',_binary '\0','2023-12-04 11:48:15',47443.00,23394834.00,'롢ㄹ',1,2),('deposit_tx',30,'2023-12-04 11:48:15',_binary '\0','2023-12-04 11:48:15',47443.00,1611891.80,'롢ㄹ',2,1),('withdraw_tx',31,'2024-01-05 04:18:56',_binary '\0','2024-01-05 04:18:56',6856.00,23387978.00,'ㅇㄹ훙휴ㅠ',1,2),('deposit_tx',32,'2024-01-05 04:18:56',_binary '\0','2024-01-05 04:18:56',6856.00,1618747.80,'ㅇㄹ훙휴ㅠ',2,1),('withdraw_tx',33,'2024-01-11 11:48:48',_binary '\0','2024-01-11 11:48:48',45746.00,23342232.00,'ㄹ놓ㄷ',1,2),('deposit_tx',34,'2024-01-11 11:48:48',_binary '\0','2024-01-11 11:48:48',45746.00,1664493.80,'ㄹ놓ㄷ',2,1);
/*!40000 ALTER TABLE `abstract_tx_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
                            `account_id` bigint NOT NULL AUTO_INCREMENT,
                            `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
                            `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '이용가능여부',
                            `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
                            `account_balance` decimal(19,2) DEFAULT NULL,
                            `account_limit` decimal(19,2) DEFAULT NULL,
                            `account_number` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                            `customer_id` bigint DEFAULT NULL,
                            PRIMARY KEY (`account_id`),
                            UNIQUE KEY `UK_6kplolsdtr3slnvx97xsy2kc8` (`account_number`),
                            KEY `FKn6x8pdp50os8bq5rbb792upse` (`customer_id`),
                            CONSTRAINT `FKn6x8pdp50os8bq5rbb792upse` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'2024-01-11 10:16:40',_binary '\0','2024-01-11 11:48:48',1664493.80,1000000.00,'57464811',1),(2,'2024-01-11 10:16:43',_binary '\0','2024-01-11 11:48:48',23342232.00,1000000.00,'32455122',1),(3,'2024-01-11 10:16:46',_binary '\0','2024-01-11 01:18:29',9485829522.00,1000000.00,'11351033',1),(4,'2024-01-11 10:16:49',_binary '\0','2024-01-11 01:18:29',346876740.40,1000000.00,'64467470',1),(5,'2024-01-11 10:18:44',_binary '\0','2024-01-11 10:18:44',1000.00,1000000.00,'86608602',1);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
                             `customer_id` bigint NOT NULL AUTO_INCREMENT,
                             `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
                             `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '이용가능여부',
                             `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
                             `customer_email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                             `customer_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                             `customer_phone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                             `customer_pwd` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                             PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'2024-01-11 10:12:56',_binary '\0','2024-01-11 10:12:56','asdf@asdf.asdf','asdfasdf','01010101001010','$2a$10$cuyNnPfov0hKiZO2sg.WfOlHmSmlBkqxCbTlW6jdySp3ivCV57atu');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deposit_tx_history`
--

DROP TABLE IF EXISTS `deposit_tx_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deposit_tx_history` (
                                      `tx_id` bigint NOT NULL,
                                      PRIMARY KEY (`tx_id`),
                                      CONSTRAINT `FKo60meixuonn7is3oklkom8ski` FOREIGN KEY (`tx_id`) REFERENCES `abstract_tx_history` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deposit_tx_history`
--

LOCK TABLES `deposit_tx_history` WRITE;
/*!40000 ALTER TABLE `deposit_tx_history` DISABLE KEYS */;
INSERT INTO `deposit_tx_history` VALUES (2),(4),(6),(8),(10),(12),(14),(16),(18),(20),(22),(24),(26),(28),(30),(32),(34);
/*!40000 ALTER TABLE `deposit_tx_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `withdraw_tx_history`
--

DROP TABLE IF EXISTS `withdraw_tx_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `withdraw_tx_history` (
                                       `tx_id` bigint NOT NULL,
                                       PRIMARY KEY (`tx_id`),
                                       CONSTRAINT `FKmvexb1mk1vbfkjvcpirlbchq0` FOREIGN KEY (`tx_id`) REFERENCES `abstract_tx_history` (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `withdraw_tx_history`
--

LOCK TABLES `withdraw_tx_history` WRITE;
/*!40000 ALTER TABLE `withdraw_tx_history` DISABLE KEYS */;
INSERT INTO `withdraw_tx_history` VALUES (1),(3),(5),(7),(9),(11),(13),(15),(17),(19),(21),(23),(25),(27),(29),(31),(33);
/*!40000 ALTER TABLE `withdraw_tx_history` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-11 12:55:32