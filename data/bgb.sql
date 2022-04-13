-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: sodacooky.plus    Database: bgb
-- ------------------------------------------------------
-- Server version	8.0.24

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
-- Table structure for table `collection`
--

DROP TABLE IF EXISTS `collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `collection` (
  `uid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`uid`,`cid`),
  KEY `collection_FK` (`cid`),
  CONSTRAINT `collection_FK` FOREIGN KEY (`cid`) REFERENCES `content` (`cid`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `collection_FK_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collection`
--

LOCK TABLES `collection` WRITE;
/*!40000 ALTER TABLE `collection` DISABLE KEYS */;
INSERT INTO `collection` VALUES ('1','1','2022-04-07 21:05:42'),('1','2','2022-04-09 21:05:54'),('2','3','2022-04-09 23:53:01'),('3','4','2022-04-09 23:53:16'),('3','5','2022-04-08 23:54:47');
/*!40000 ALTER TABLE `collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `content`
--

DROP TABLE IF EXISTS `content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `content` (
  `cid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `main_text` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `type` varchar(32) NOT NULL DEFAULT '0',
  `like_amount` int unsigned NOT NULL DEFAULT '0',
  `uid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`cid`),
  KEY `content_FK` (`uid`),
  KEY `content_FK_1` (`sid`),
  CONSTRAINT `content_FK` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`),
  CONSTRAINT `content_FK_1` FOREIGN KEY (`sid`) REFERENCES `resource` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content`
--

LOCK TABLES `content` WRITE;
/*!40000 ALTER TABLE `content` DISABLE KEYS */;
INSERT INTO `content` VALUES ('1','彩布里来咯','驱逐舰\'特装型布里MKIII\'参上!“Burin!~感动吧!在你面前的是布里家族传说的隐藏角色，也就是我!为你那无与伦比的好运而欢呼吧bulin!”','2022-04-01 16:29:38','picture',111,'1','1'),('10','4bulibuli','4bulibuli','2022-04-02 00:00:00','picture',222,'1','1'),('2','彩布里来咯','驱逐舰\'特装型布里MKIII\'参上!“Burin!~感动吧!在你面前的是布里家族传说的隐藏角色，也就是我!为你那无与伦比的好运而欢呼吧bulin!”','2022-04-06 16:29:38','picture',666,'1','1'),('3','bulibuli','bulibuli','2022-04-07 00:00:00','picture',555,'4','4'),('4','4bulibuli','4bulibuli','2022-04-05 00:00:00','picture',777,'1','1'),('5','5bulibuli','5bulibuli','2022-04-08 00:00:00','picture',888,'1','1'),('6','4bulibuli','4bulibuli','2022-04-10 00:00:00','picture',999,'1','1'),('8','金布里来咯','布里布里','2022-06-09 17:02:08','video',2000,'4','5'),('9','4bulibuli','4bulibuli','2022-04-11 00:00:00','picture',3000,'1','1');
/*!40000 ALTER TABLE `content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow` (
  `uid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `follower` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`uid`,`follower`),
  KEY `follow_FK_1` (`follower`),
  CONSTRAINT `follow_FK` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`),
  CONSTRAINT `follow_FK_1` FOREIGN KEY (`follower`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES ('1','1','2022-04-13 21:11:42'),('1','2','2022-04-08 21:11:32'),('1','3','2022-04-06 21:11:51'),('1','4','2022-04-12 21:53:54'),('2','1','2022-04-06 21:11:51'),('2','2','2022-04-06 21:11:51'),('2','3','2022-04-06 21:11:51'),('6','1','2022-04-06 21:11:51'),('6','2','2022-04-06 21:11:51'),('7','1','2022-04-06 21:11:51');
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reply` (
  `rid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(32) NOT NULL,
  `content` text NOT NULL,
  `goal` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `like_amount` int unsigned NOT NULL DEFAULT '0',
  `date` datetime NOT NULL,
  `uid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`rid`),
  KEY `reply_FK` (`uid`),
  CONSTRAINT `reply_FK` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` VALUES ('1','top','是我的错觉吗，这孩子好像在瑟瑟发抖呢…','4',5,'2022-03-28 17:00:00','4'),('2','inner','一定是兴奋的颤抖！','1',6,'2022-03-28 17:01:00','3'),('3','top','太粗暴可不行哦，灵敏？','4',666,'2022-03-28 17:03:00','5'),('4','inner','嘿嘿，放心吧！我对机械一向是非常温柔的~','3',999,'2022-03-28 17:11:00','3');
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `uid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` datetime NOT NULL,
  `solved` tinyint(1) NOT NULL,
  `reason` text,
  PRIMARY KEY (`uid`,`cid`),
  KEY `report_FK_1` (`cid`),
  CONSTRAINT `report_FK` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`),
  CONSTRAINT `report_FK_1` FOREIGN KEY (`cid`) REFERENCES `content` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resource` (
  `sid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `paths` json NOT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES ('1','picture','[\"img/3am4494wob84kxiqsdm1xeh043ajiz6.jpg\", \"img/adjwiajdioawhiod.jpg\"]'),('2','picture','[\"img/ee8v30tmblui1tigtxq9vyklap9r8tn.jpg\", \"img/1ulrgd95x6755wkmxtywfc8f22srnqt.jpg\"]'),('3','picture','[\"img/sehmjhg3eid8shy4k6lyy77qhu9px8s.png\"]'),('4','picture','[\"img/lmlh.jpg\", \"img/SN_Soobrazitelny.jpg\"]'),('5','video','[\"vid/op.mp4\"]');
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `uid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'uid',
  `nickname` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `gender` tinyint(1) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '0',
  `avatar_path` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1','新的昵称','新的简介',0,'2021-05-01',0,'avatar/cbl.jpg'),('2','金布里','试作型布里MKII UNIV Trial Bulin MKII 試作型ブリMKII',0,'2017-04-01',0,'avatar/jbl.jpg'),('3','灵敏','Pr.7-U型前哨级驱逐舰 灵敏 SN Soobrazitelny ソオブラジ－テリヌイ',0,'1941-06-07',0,'avatar/lm.jpg'),('4','阿尔汉格尔斯克','SN Arkhangelsk',0,'1939-01-01',0,'avatar/aehgesk.jpg'),('5','伏尔加','Pr.72型航空母舰 伏尔加 SN Volga',0,'2022-02-24',1,'avatar/fej.jpg'),('6','紫布里','试作型布里MKII UNIV Trial Bulin MKII 試作型ブリMKII',0,'2017-04-01',0,'avatar/jbl.jpg'),('7','彩布里','试作型布里MKII UNIV Trial Bulin MKII 試作型ブリMKII',0,'2017-04-01',0,'avatar/cbl.jpg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'bgb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-13 18:53:46
