-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: localhost    Database: mulanbay_init
-- ------------------------------------------------------
-- Server version	8.0.33-0ubuntu0.20.04.2

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

set sql_mode='NO_AUTO_VALUE_ON_ZERO';

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `card_no` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` smallint NOT NULL,
  `amount` decimal(9,2) NOT NULL,
  `status` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (12,0,'默认账户',NULL,3,0.00,1,NULL,'2019-03-30 22:16:43',NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_flow`
--

DROP TABLE IF EXISTS `account_flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_flow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `account_id` bigint NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `card_no` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` smallint DEFAULT NULL,
  `before_amount` decimal(9,2) NOT NULL,
  `after_amount` decimal(9,2) NOT NULL,
  `adjust_type` smallint NOT NULL,
  `snapshot_id` bigint DEFAULT NULL,
  `status` smallint NOT NULL DEFAULT '1',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_flow`
--

LOCK TABLES `account_flow` WRITE;
/*!40000 ALTER TABLE `account_flow` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_flow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_snapshot_info`
--

DROP TABLE IF EXISTS `account_snapshot_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_snapshot_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `period` smallint DEFAULT NULL,
  `buss_key` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_snapshot_info`
--

LOCK TABLES `account_snapshot_info` WRITE;
/*!40000 ALTER TABLE `account_snapshot_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_snapshot_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `body_abnormal_record`
--

DROP TABLE IF EXISTS `body_abnormal_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `body_abnormal_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `organ` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '器官',
  `disease` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '疾病',
  `last_days` int NOT NULL COMMENT '持续天数',
  `pain_level` int NOT NULL COMMENT '疼痛等级',
  `important_level` decimal(5,1) NOT NULL COMMENT '重要等级',
  `occur_date` date NOT NULL COMMENT '发生日期',
  `finish_date` date DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `body_abnormal_record`
--

LOCK TABLES `body_abnormal_record` WRITE;
/*!40000 ALTER TABLE `body_abnormal_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `body_abnormal_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `body_basic_info`
--

DROP TABLE IF EXISTS `body_basic_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `body_basic_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `record_date` datetime NOT NULL,
  `weight` decimal(9,2) NOT NULL COMMENT '体重',
  `height` int NOT NULL COMMENT '身高',
  `bmi` decimal(5,2) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='身体基本情况表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `body_basic_info`
--

LOCK TABLES `body_basic_info` WRITE;
/*!40000 ALTER TABLE `body_basic_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `body_basic_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_category`
--

DROP TABLE IF EXISTS `book_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '1',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `order_index` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_category`
--

LOCK TABLES `book_category` WRITE;
/*!40000 ALTER TABLE `book_category` DISABLE KEYS */;
INSERT INTO `book_category` VALUES (12,0,'文学类',1,NULL,'2020-02-11 18:53:00','2020-02-13 21:39:32'),(13,0,'旅游类',2,NULL,'2020-02-11 18:53:08',NULL),(14,0,'社科类',3,NULL,'2020-02-11 18:53:15',NULL),(15,0,'专业类',4,NULL,'2020-02-11 18:53:22',NULL);
/*!40000 ALTER TABLE `book_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget`
--

DROP TABLE IF EXISTS `budget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` smallint NOT NULL,
  `period` smallint NOT NULL,
  `expect_paid_time` datetime DEFAULT NULL,
  `first_paid_time` datetime DEFAULT NULL,
  `last_paid_time` datetime DEFAULT NULL,
  `amount` decimal(9,2) NOT NULL,
  `remind` tinyint(1) NOT NULL DEFAULT '1',
  `status` smallint NOT NULL,
  `keywords` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `goods_type_id` int DEFAULT NULL,
  `sub_goods_type_id` int DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget`
--

LOCK TABLES `budget` WRITE;
/*!40000 ALTER TABLE `budget` DISABLE KEYS */;
/*!40000 ALTER TABLE `budget` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget_log`
--

DROP TABLE IF EXISTS `budget_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `budget_id` bigint DEFAULT NULL,
  `period` smallint NOT NULL,
  `buss_key` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `occur_date` datetime NOT NULL,
  `budget_amount` decimal(9,2) NOT NULL,
  `nc_amount` decimal(9,2) NOT NULL,
  `bc_amount` decimal(9,2) NOT NULL DEFAULT '0.00',
  `tr_amount` decimal(9,2) NOT NULL DEFAULT '0.00',
  `income_amount` decimal(9,2) DEFAULT '0.00',
  `account_change_amount` decimal(9,2) DEFAULT NULL,
  `source` smallint DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_USERID_BUSSKEY` (`user_id`,`buss_key`,`budget_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget_log`
--

LOCK TABLES `budget_log` WRITE;
/*!40000 ALTER TABLE `budget_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `budget_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget_snapshot`
--

DROP TABLE IF EXISTS `budget_snapshot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget_snapshot` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `budget_log_id` bigint DEFAULT NULL,
  `from_id` bigint NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` smallint NOT NULL,
  `period` smallint NOT NULL,
  `buss_key` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `expect_paid_time` datetime DEFAULT NULL,
  `first_paid_time` datetime DEFAULT NULL,
  `last_paid_time` datetime DEFAULT NULL,
  `amount` decimal(9,2) NOT NULL,
  `remind` tinyint(1) NOT NULL DEFAULT '1',
  `status` smallint NOT NULL,
  `keywords` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `goods_type_id` int DEFAULT NULL,
  `sub_goods_type_id` int DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget_snapshot`
--

LOCK TABLES `budget_snapshot` WRITE;
/*!40000 ALTER TABLE `budget_snapshot` DISABLE KEYS */;
/*!40000 ALTER TABLE `budget_snapshot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget_timeline`
--

DROP TABLE IF EXISTS `budget_timeline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget_timeline` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `period` smallint NOT NULL,
  `buss_key` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `buss_day` date NOT NULL,
  `total_days` int NOT NULL,
  `pass_days` int NOT NULL,
  `budget_amount` decimal(9,2) NOT NULL,
  `nc_amount` decimal(9,2) NOT NULL,
  `bc_amount` decimal(9,2) NOT NULL DEFAULT '0.00',
  `tr_amount` decimal(9,2) NOT NULL DEFAULT '0.00',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget_timeline`
--

LOCK TABLES `budget_timeline` WRITE;
/*!40000 ALTER TABLE `budget_timeline` DISABLE KEYS */;
/*!40000 ALTER TABLE `budget_timeline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_trip`
--

DROP TABLE IF EXISTS `business_trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_trip` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `company_id` bigint NOT NULL COMMENT '公司',
  `trip_date` date DEFAULT NULL,
  `country` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '国家',
  `province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '省份',
  `city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '城市',
  `days` int NOT NULL COMMENT '天数',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `AK_Key_1` (`id`),
  KEY `FK_ecy3fln5j24pu5jpcfx5mxgm2` (`company_id`),
  CONSTRAINT `FK_ecy3fln5j24pu5jpcfx5mxgm2` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='出差记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_trip`
--

LOCK TABLES `business_trip` WRITE;
/*!40000 ALTER TABLE `business_trip` DISABLE KEYS */;
/*!40000 ALTER TABLE `business_trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buy_record`
--

DROP TABLE IF EXISTS `buy_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buy_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL,
  `buy_type_id` int NOT NULL,
  `goods_type_id` int NOT NULL,
  `sub_goods_type_id` int DEFAULT NULL,
  `goods_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `shop_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `price` decimal(9,2) NOT NULL COMMENT '商品单价',
  `amount` int NOT NULL DEFAULT '1' COMMENT '数量',
  `shipment` decimal(9,2) NOT NULL COMMENT '运费',
  `total_price` decimal(9,2) NOT NULL COMMENT '总价',
  `payment` smallint DEFAULT NULL,
  `buy_date` datetime DEFAULT NULL COMMENT '购买日期',
  `consume_date` datetime DEFAULT NULL,
  `sold_price` decimal(9,2) DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL,
  `expect_delete_date` datetime DEFAULT NULL,
  `status` smallint NOT NULL COMMENT '状态',
  `secondhand` bit(1) NOT NULL,
  `keywords` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `brand` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `statable` bit(1) DEFAULT NULL,
  `consume_type` smallint NOT NULL DEFAULT '0',
  `sku_info` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `pid` bigint DEFAULT NULL,
  `treat_record_id` bigint DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `BR_USERID_BUYDATE` (`user_id`,`buy_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='购买记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buy_record`
--

LOCK TABLES `buy_record` WRITE;
/*!40000 ALTER TABLE `buy_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `buy_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buy_record_match_log`
--

DROP TABLE IF EXISTS `buy_record_match_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buy_record_match_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `compare_id` bigint DEFAULT NULL,
  `buy_record_id` bigint NOT NULL,
  `goods_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `goods_type_id` int NOT NULL,
  `sub_goods_type_id` int DEFAULT NULL,
  `shop_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `brand` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ai_match` decimal(6,4) NOT NULL,
  `ac_match` decimal(6,4) NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='消费记录匹配日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buy_record_match_log`
--

LOCK TABLES `buy_record_match_log` WRITE;
/*!40000 ALTER TABLE `buy_record_match_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `buy_record_match_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buy_type`
--

DROP TABLE IF EXISTS `buy_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buy_type` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `user_id` bigint NOT NULL DEFAULT '1',
  `status` smallint NOT NULL COMMENT '状态',
  `order_index` smallint NOT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 COMMENT='购买方式';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buy_type`
--

LOCK TABLES `buy_type` WRITE;
/*!40000 ALTER TABLE `buy_type` DISABLE KEYS */;
INSERT INTO `buy_type` VALUES (15,'淘宝',0,1,1),(16,'京东',0,1,2),(17,'实体店',0,1,3),(18,'其他',0,1,4);
/*!40000 ALTER TABLE `buy_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chart_config`
--

DROP TABLE IF EXISTS `chart_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chart_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `para` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  `related_beans` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `level` int NOT NULL,
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `detail_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `chart_type` smallint NOT NULL DEFAULT '0',
  `support_date` tinyint NOT NULL DEFAULT '1',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chart_config`
--

LOCK TABLES `chart_config` WRITE;
/*!40000 ALTER TABLE `chart_config` DISABLE KEYS */;
INSERT INTO `chart_config` VALUES (1,'消费记录实时分析(商品类型)','消费记录实时分析(商品类型)','{\"groupField\":\"goods_type_id\",\"subGoodsType\":\"\",\"keywords\":\"\",\"endDate\":\"{1}\",\"name\":\"\",\"chartType\":\"PIE\",\"buyType\":\"\",\"type\":\"TOTALPRICE\",\"startDate\":\"{0}\",\"consumeType\":\"\",\"goodsType\":\"\"}',1,1,'BuyRecord',3,'/buyRecord/analyseStat',NULL,0,1,NULL,'2020-01-18 10:27:15','2020-01-18 13:48:58'),(2,'消费记录实时分析{某个分组类型}','消费记录实时分析{某个分组类型}','{\"groupField\":\"{2}\",\"subGoodsType\":\"\",\"keywords\":\"\",\"endDate\":\"{1}\",\"name\":\"\",\"chartType\":\"PIE\",\"buyType\":\"\",\"type\":\"TOTALPRICE\",\"startDate\":\"{0}\",\"consumeType\":\"\",\"goodsType\":\"\"}',1,2,'BuyRecord',3,'/buyRecord/analyseStat',NULL,0,1,NULL,'2020-01-18 13:39:38',NULL),(3,'消费统计(时间维度)','消费统计(时间维度)','{\"subGoodsType\":\"\",\"endDate\":\"{1}\",\"priceType\":\"TOTALPRICE\",\"chartType\":\"BAR\",\"buyType\":\"\",\"compliteDate\":\"false\",\"dateGroupType\":\"{2}\",\"startDate\":\"{0}\",\"consumeType\":\"\",\"goodsType\":\"\"}',1,3,'BuyRecord',3,'/buyRecord/dateStat',NULL,9,1,NULL,'2020-01-18 14:06:38','2021-01-29 10:35:25'),(4,'账户统计','账户统计','{\"groupType\":\"NAME\",\"snapshotId\":\"\",\"type\":\"\",\"status\":\"\"}',1,4,'Account',3,'/account/stat',NULL,0,0,NULL,'2020-01-18 14:14:00','2020-01-18 15:43:05'),(5,'预算统计','预算统计','{\"period\":\"\",\"statType\":\"NAME\",\"type\":\"\"}',1,5,'Account',3,'/budget/stat',NULL,0,0,NULL,'2020-01-18 14:45:55','2020-01-18 15:43:12'),(6,'消费标签统计','消费标签统计','{\"subGoodsType\":\"\",\"keywords\":\"\",\"endDate\":\"{1}\",\"startDate\":\"{0}\",\"goodsType\":\"\"}',1,6,'BuyRecord',3,'/buyRecord/keywordsStat',NULL,9,1,NULL,'2020-01-18 14:52:45','2021-01-29 10:42:27'),(7,'收入统计','收入统计','{\"accountId\":\"\",\"endDate\":\"{1}\",\"startDate\":\"{0}\",\"status\":\"\"}',1,7,'Account',3,'/income/stat',NULL,0,1,NULL,'2020-01-18 15:24:50',NULL),(8,'音乐练习统计(某个乐器)','音乐练习统计(某个乐器)','{\"endDate\":\"{1}\",\"musicInstrumentId\":\"{2}\",\"chartType\":\"BAR\",\"compliteDate\":\"false\",\"dateGroupType\":\"MONTH\",\"startDate\":\"{0}\",\"tune\":\"\",\"tuneType\":\"TUNE\"}',1,8,'MusicPractice',3,'/musicPractice/dateStat',NULL,9,1,NULL,'2020-01-18 15:29:06','2021-01-29 11:40:33'),(9,'音乐练习曲子统计(某个乐器)','音乐练习曲子统计(某个乐器)','{\"endDate\":\"{1}\",\"musicInstrumentId\":\"{2}\",\"tuneName\":\"\",\"startDate\":\"{0}\",\"tuneType\":\"TUNE\"}',1,9,'MusicPractice',3,'/musicPracticeTune/stat',NULL,2,1,NULL,'2020-01-18 15:34:16',NULL),(10,'梦想统计','梦想统计','{\"groupType\":\"STATUS\",\"endDate\":\"{1}\",\"chartType\":\"PIE\",\"dateQueryType\":\"proposed_date\",\"startDate\":\"{0}\"}',1,10,'Dream',3,'/dream/statList',NULL,0,1,NULL,'2020-01-18 15:44:33',NULL),(11,'锻炼统计(某个运动类型+时间维度)','锻炼统计(某个运动类型+时间维度)','{\"endDate\":\"{1}\",\"fullStat\":\"false\",\"compliteDate\":\"false\",\"dateGroupType\":\"{3}\",\"startDate\":\"{0}\",\"sportTypeId\":\"{2}\"}',1,11,'SportExercise',3,'/sportExercise/dateStat',NULL,9,1,NULL,'2020-01-18 15:53:16','2021-01-29 11:39:23'),(12,'看病分析(某个分组类型)','看病分析(某个分组类型)','{\"groupField\":\"{2}\",\"groupType\":\"COUNT\",\"endDate\":\"{1}\",\"feeField\":\"total_fee\",\"chartType\":\"PIE\",\"name\":\"\",\"treatType\":\"\",\"sick\":\"\",\"startDate\":\"{0}\"}',1,12,'TreatRecord',3,'/treatRecord/analyseStat',NULL,0,1,NULL,'2020-01-18 21:22:50',NULL),(13,'看病统计(时间维度)','看病统计(时间维度)','{\"disease\":\"\",\"endDate\":\"{1}\",\"feeField\":\"total_fee\",\"name\":\"\",\"chartType\":\"BAR\",\"compliteDate\":\"false\",\"treatType\":\"\",\"dateGroupType\":\"{2}\",\"startDate\":\"{0}\"}',1,12,'TreatRecord',3,'/treatRecord/dateStat',NULL,9,1,NULL,'2020-01-18 21:27:41','2021-01-29 11:39:30'),(14,'身体基本情况统计(时间维度)','身体基本情况统计(时间维度)','{\"endDate\":\"{1}\",\"chartType\":\"LINE\",\"compliteDate\":\"false\",\"dateGroupType\":\"{2}\",\"startDate\":\"{0}\"}',1,14,'SportExercise',3,'/bodyBasicInfo/dateStat',NULL,9,1,NULL,'2020-01-18 21:37:14','2021-01-29 11:39:35'),(15,'身体不适分析(某个分组类型)','身体不适分析(某个分组类型)','{\"groupField\":\"{2}\",\"endDate\":\"{1}\",\"chartType\":\"PIE\",\"startDate\":\"{0}\"}',1,15,'TreatRecord',3,'/bodyAbnormalRecord/stat',NULL,0,1,NULL,'2020-01-18 22:06:40',NULL),(16,'身体不适分析(时间维度)','身体不适分析(时间维度)','{\"disease\":\"\",\"endDate\":\"{1}\",\"name\":\"\",\"chartType\":\"BAR\",\"compliteDate\":\"false\",\"dateGroupType\":\"{2}\",\"startDate\":\"{0}\"}',1,16,'TreatRecord',3,'/bodyAbnormalRecord/dateStat',NULL,9,1,NULL,'2020-01-18 22:15:08','2021-01-29 11:39:41'),(17,'睡眠分析','睡眠分析','{\"ygroupType\":\"{3}\",\"endDate\":\"{1}\",\"xgroupType\":\"{2}\",\"startDate\":\"{0}\"}',1,17,'Sleep',3,'/sleep/analyseSat',NULL,6,1,NULL,'2020-01-18 22:30:00',NULL),(18,'人生经历统计(时间维度)','人生经历统计(时间维度)','{\"endDate\":\"{1}\",\"compliteDate\":\"false\",\"dateGroupType\":\"{2}\",\"startDate\":\"{0}\"}',1,18,'LifeExperience',3,'/lifeExperience/dateStat',NULL,9,1,NULL,'2020-01-19 13:26:48','2021-01-29 11:39:45'),(19,'阅读统计(时间维度)','阅读统计(时间维度)','{\"endDate\":\"{1}\",\"chartType\":\"LINE\",\"compliteDate\":\"false\",\"dateGroupType\":\"{2}\",\"startDate\":\"{0}\"}',1,19,'ReadingRecord',3,'/readingRecord/dateStat',NULL,1,1,NULL,'2020-01-19 14:05:19',NULL),(20,'阅读分析(某个分组类型)','阅读分析(某个分组类型)','{\"groupType\":\"{2}\",\"endDate\":\"{1}\",\"dateQueryType\":\"finished_date\",\"startDate\":\"{0}\",\"status\":\"\"}',1,20,'ReadingRecord',3,'/readingRecord/analyseStat',NULL,0,1,NULL,'2020-01-19 14:08:42',NULL),(21,'加班统计(时间维度)','加班统计(时间维度)','{\"endDate\":\"{1}\",\"chartType\":\"BAR\",\"compliteDate\":\"false\",\"dateGroupType\":\"{2}\",\"startDate\":\"{0}\"}',1,21,'WorkOvertime',3,'/workOvertime/dateStat',NULL,9,1,NULL,'2020-01-19 14:16:04','2021-01-29 11:39:16'),(22,'饮食价格分析(时间维度)','饮食价格分析(时间维度)','{\"endDate\":\"{1}\",\"foodType\":\"\",\"minPrice\":\"0\",\"dietType\":\"\",\"compliteDate\":\"false\",\"dateGroupTypeStr\":\"{2}\",\"startDate\":\"{0}\",\"dietSource\":\"\"}',1,22,'Diet',3,'/diet/priceAnalyse',NULL,9,1,NULL,'2020-01-19 14:26:16','2021-01-29 11:38:57'),(23,'通用记录统计(某个类型+时间维度)','通用记录统计(某个类型+时间维度)','{\"CommonRecordTypeId\":\"{2}\",\"endDate\":\"{1}\",\"name\":\"\",\"compliteDate\":\"false\",\"dateGroupType\":\"{3}\",\"startDate\":\"{0}\"}',1,23,'CommonRecord',3,'/commonRecord/dateStat',NULL,9,1,NULL,'2020-01-19 14:29:47','2021-01-29 11:37:42'),(24,'用户行为分析(月报)','用户行为分析(月报)','{\"date\":\"{0}\",\"dataType\":\"\",\"name\":\"\",\"userBehaviorId\":\"\",\"dateGroupType\":\"MONTH\"}',1,24,'CommonRecord',3,'/userBehavior/stat',NULL,8,1,NULL,'2020-01-19 14:39:26',NULL),(25,'八小时外分析','八小时外分析','{\"dataGroup\":\"DETAIL\",\"endDate\":\"{1}\",\"chartType\":\"PIE\",\"type\":\"MINUTES\",\"startDate\":\"{0}\"}',1,25,'CommonRecord',3,'/dataAnalyse/afterEightHourAnalyseStat',NULL,0,1,NULL,'2020-01-19 14:49:55',NULL),(26,'用户评分统计','用户评分统计','{\"minScore\":\"\",\"endDate\":\"{1}\",\"chartType\":\"LINE\",\"maxScore\":\"\",\"startDate\":\"{0}\"}',1,26,'CommonRecord',3,'/userScore/stat',NULL,1,1,NULL,'2020-01-19 14:52:47',NULL),(27,'用户积分统计(时间维度)','用户积分统计(时间维度)','{\"endDate\":\"{1}\",\"dateGroupType\":\"{2}\",\"startDate\":\"{0}\"}',1,27,'CommonRecord',3,'/userRewardPointRecord/pointsTimelineStat',NULL,9,1,NULL,'2020-01-19 14:55:52','2021-01-29 11:38:22');
/*!40000 ALTER TABLE `chart_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `id` int NOT NULL DEFAULT '0' COMMENT 'ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Name',
  `map_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `province_id` int DEFAULT NULL COMMENT 'PROVINCE',
  `zip_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'ZIPCODE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='城市表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'北京市','北京市',1,'100000'),(2,'天津市','天津市',2,'100000'),(3,'石家庄市','石家庄市',3,'050000'),(4,'唐山市','唐山市',3,'063000'),(5,'秦皇岛市','秦皇岛市',3,'066000'),(6,'邯郸市','邯郸市',3,'056000'),(7,'邢台市','邢台市',3,'054000'),(8,'保定市','保定市',3,'071000'),(9,'张家口市','张家口市',3,'075000'),(10,'承德市','承德市',3,'067000'),(11,'沧州市','沧州市',3,'061000'),(12,'廊坊市','廊坊市',3,'065000'),(13,'衡水市','衡水市',3,'053000'),(14,'太原市','太原市',4,'030000'),(15,'大同市','大同市',4,'037000'),(16,'阳泉市','阳泉市',4,'045000'),(17,'长治市','长治市',4,'046000'),(18,'晋城市','晋城市',4,'048000'),(19,'朔州市','朔州市',4,'036000'),(20,'晋中市','晋中市',4,'030600'),(21,'运城市','运城市',4,'044000'),(22,'忻州市','忻州市',4,'034000'),(23,'临汾市','临汾市',4,'041000'),(24,'吕梁市','吕梁市',4,'030500'),(25,'呼和浩特市','呼和浩特市',5,'010000'),(26,'包头市','包头市',5,'014000'),(27,'乌海市','乌海市',5,'016000'),(28,'赤峰市','赤峰市',5,'024000'),(29,'通辽市','通辽市',5,'028000'),(30,'鄂尔多斯市','鄂尔多斯市',5,'010300'),(31,'呼伦贝尔市','呼伦贝尔市',5,'021000'),(32,'巴彦淖尔市','巴彦淖尔市',5,'014400'),(33,'乌兰察布市','乌兰察布市',5,'011800'),(34,'兴安盟','兴安盟',5,'137500'),(35,'锡林郭勒盟','锡林郭勒盟',5,'011100'),(36,'阿拉善盟','阿拉善盟',5,'016000'),(37,'沈阳市','沈阳市',6,'110000'),(38,'大连市','大连市',6,'116000'),(39,'鞍山市','鞍山市',6,'114000'),(40,'抚顺市','抚顺市',6,'113000'),(41,'本溪市','本溪市',6,'117000'),(42,'丹东市','丹东市',6,'118000'),(43,'锦州市','锦州市',6,'121000'),(44,'营口市','营口市',6,'115000'),(45,'阜新市','阜新市',6,'123000'),(46,'辽阳市','辽阳市',6,'111000'),(47,'盘锦市','盘锦市',6,'124000'),(48,'铁岭市','铁岭市',6,'112000'),(49,'朝阳市','朝阳市',6,'122000'),(50,'葫芦岛市','葫芦岛市',6,'125000'),(51,'长春市','长春市',7,'130000'),(52,'吉林市','吉林市',7,'132000'),(53,'四平市','四平市',7,'136000'),(54,'辽源市','辽源市',7,'136200'),(55,'通化市','通化市',7,'134000'),(56,'白山市','白山市',7,'134300'),(57,'松原市','松原市',7,'131100'),(58,'白城市','白城市',7,'137000'),(59,'延边朝鲜族自治州','延边朝鲜族自治州',7,'133000'),(60,'哈尔滨市','哈尔滨市',8,'150000'),(61,'齐齐哈尔市','齐齐哈尔市',8,'161000'),(62,'鸡西市','鸡西市',8,'158100'),(63,'鹤岗市','鹤岗市',8,'154100'),(64,'双鸭山市','双鸭山市',8,'155100'),(65,'大庆市','大庆市',8,'163000'),(66,'伊春市','伊春市',8,'152300'),(67,'佳木斯市','佳木斯市',8,'154000'),(68,'七台河市','七台河市',8,'154600'),(69,'牡丹江市','牡丹江市',8,'157000'),(70,'黑河市','黑河市',8,'164300'),(71,'绥化市','绥化市',8,'152000'),(72,'大兴安岭地区','大兴安岭地区',8,'165000'),(73,'上海市','上海市',9,'200000'),(74,'南京市','南京市',10,'210000'),(75,'无锡市','无锡市',10,'214000'),(76,'徐州市','徐州市',10,'221000'),(77,'常州市','常州市',10,'213000'),(78,'苏州市','苏州市',10,'215000'),(79,'南通市','南通市',10,'226000'),(80,'连云港市','连云港市',10,'222000'),(81,'淮安市','淮安市',10,'223200'),(82,'盐城市','盐城市',10,'224000'),(83,'扬州市','扬州市',10,'225000'),(84,'镇江市','镇江市',10,'212000'),(85,'泰州市','泰州市',10,'225300'),(86,'宿迁市','宿迁市',10,'223800'),(87,'杭州市','杭州市',11,'310000'),(88,'宁波市','宁波市',11,'315000'),(89,'温州市','温州市',11,'325000'),(90,'嘉兴市','嘉兴市',11,'314000'),(91,'湖州市','湖州市',11,'313000'),(92,'绍兴市','绍兴市',11,'312000'),(93,'金华市','金华市',11,'321000'),(94,'衢州市','衢州市',11,'324000'),(95,'舟山市','舟山市',11,'316000'),(96,'台州市','台州市',11,'318000'),(97,'丽水市','丽水市',11,'323000'),(98,'合肥市','合肥市',12,'230000'),(99,'芜湖市','芜湖市',12,'241000'),(100,'蚌埠市','蚌埠市',12,'233000'),(101,'淮南市','淮南市',12,'232000'),(102,'马鞍山市','马鞍山市',12,'243000'),(103,'淮北市','淮北市',12,'235000'),(104,'铜陵市','铜陵市',12,'244000'),(105,'安庆市','安庆市',12,'246000'),(106,'黄山市','黄山市',12,'242700'),(107,'滁州市','滁州市',12,'239000'),(108,'阜阳市','阜阳市',12,'236100'),(109,'宿州市','宿州市',12,'234100'),(110,'巢湖市','巢湖市',12,'238000'),(111,'六安市','六安市',12,'237000'),(112,'亳州市','亳州市',12,'236800'),(113,'池州市','池州市',12,'247100'),(114,'宣城市','宣城市',12,'366000'),(115,'福州市','福州市',13,'350000'),(116,'厦门市','厦门市',13,'361000'),(117,'莆田市','莆田市',13,'351100'),(118,'三明市','三明市',13,'365000'),(119,'泉州市','泉州市',13,'362000'),(120,'漳州市','漳州市',13,'363000'),(121,'南平市','南平市',13,'353000'),(122,'龙岩市','龙岩市',13,'364000'),(123,'宁德市','宁德市',13,'352100'),(124,'南昌市','南昌市',14,'330000'),(125,'景德镇市','景德镇市',14,'333000'),(126,'萍乡市','萍乡市',14,'337000'),(127,'九江市','九江市',14,'332000'),(128,'新余市','新余市',14,'338000'),(129,'鹰潭市','鹰潭市',14,'335000'),(130,'赣州市','赣州市',14,'341000'),(131,'吉安市','吉安市',14,'343000'),(132,'宜春市','宜春市',14,'336000'),(133,'抚州市','抚州市',14,'332900'),(134,'上饶市','上饶市',14,'334000'),(135,'济南市','济南市',15,'250000'),(136,'青岛市','青岛市',15,'266000'),(137,'淄博市','淄博市',15,'255000'),(138,'枣庄市','枣庄市',15,'277100'),(139,'东营市','东营市',15,'257000'),(140,'烟台市','烟台市',15,'264000'),(141,'潍坊市','潍坊市',15,'261000'),(142,'济宁市','济宁市',15,'272100'),(143,'泰安市','泰安市',15,'271000'),(144,'威海市','威海市',15,'265700'),(145,'日照市','日照市',15,'276800'),(146,'莱芜市','莱芜市',15,'271100'),(147,'临沂市','临沂市',15,'276000'),(148,'德州市','德州市',15,'253000'),(149,'聊城市','聊城市',15,'252000'),(150,'滨州市','滨州市',15,'256600'),(151,'荷泽市','荷泽市',15,'255000'),(152,'郑州市','郑州市',16,'450000'),(153,'开封市','开封市',16,'475000'),(154,'洛阳市','洛阳市',16,'471000'),(155,'平顶山市','平顶山市',16,'467000'),(156,'安阳市','安阳市',16,'454900'),(157,'鹤壁市','鹤壁市',16,'456600'),(158,'新乡市','新乡市',16,'453000'),(159,'焦作市','焦作市',16,'454100'),(160,'濮阳市','濮阳市',16,'457000'),(161,'许昌市','许昌市',16,'461000'),(162,'漯河市','漯河市',16,'462000'),(163,'三门峡市','三门峡市',16,'472000'),(164,'南阳市','南阳市',16,'473000'),(165,'商丘市','商丘市',16,'476000'),(166,'信阳市','信阳市',16,'464000'),(167,'周口市','周口市',16,'466000'),(168,'驻马店市','驻马店市',16,'463000'),(169,'武汉市','武汉市',17,'430000'),(170,'黄石市','黄石市',17,'435000'),(171,'十堰市','十堰市',17,'442000'),(172,'宜昌市','宜昌市',17,'443000'),(173,'襄樊市','襄樊市',17,'441000'),(174,'鄂州市','鄂州市',17,'436000'),(175,'荆门市','荆门市',17,'448000'),(176,'孝感市','孝感市',17,'432100'),(177,'荆州市','荆州市',17,'434000'),(178,'黄冈市','黄冈市',17,'438000'),(179,'咸宁市','咸宁市',17,'437000'),(180,'随州市','随州市',17,'441300'),(181,'恩施土家族苗族自治州','恩施土家族苗族自治州',17,'445000'),(182,'神农架','神农架',17,'442400'),(183,'长沙市','长沙市',18,'410000'),(184,'株洲市','株洲市',18,'412000'),(185,'湘潭市','湘潭市',18,'411100'),(186,'衡阳市','衡阳市',18,'421000'),(187,'邵阳市','邵阳市',18,'422000'),(188,'岳阳市','岳阳市',18,'414000'),(189,'常德市','常德市',18,'415000'),(190,'张家界市','张家界市',18,'427000'),(191,'益阳市','益阳市',18,'413000'),(192,'郴州市','郴州市',18,'423000'),(193,'永州市','永州市',18,'425000'),(194,'怀化市','怀化市',18,'418000'),(195,'娄底市','娄底市',18,'417000'),(196,'湘西土家族苗族自治州','湘西土家族苗族自治州',18,'416000'),(197,'广州市','广州市',19,'510000'),(198,'韶关市','韶关市',19,'521000'),(199,'深圳市','深圳市',19,'518000'),(200,'珠海市','珠海市',19,'519000'),(201,'汕头市','汕头市',19,'515000'),(202,'佛山市','佛山市',19,'528000'),(203,'江门市','江门市',19,'529000'),(204,'湛江市','湛江市',19,'524000'),(205,'茂名市','茂名市',19,'525000'),(206,'肇庆市','肇庆市',19,'526000'),(207,'惠州市','惠州市',19,'516000'),(208,'梅州市','梅州市',19,'514000'),(209,'汕尾市','汕尾市',19,'516600'),(210,'河源市','河源市',19,'517000'),(211,'阳江市','阳江市',19,'529500'),(212,'清远市','清远市',19,'511500'),(213,'东莞市','东莞市',19,'511700'),(214,'中山市','中山市',19,'528400'),(215,'潮州市','潮州市',19,'515600'),(216,'揭阳市','揭阳市',19,'522000'),(217,'云浮市','云浮市',19,'527300'),(218,'南宁市','南宁市',20,'530000'),(219,'柳州市','柳州市',20,'545000'),(220,'桂林市','桂林市',20,'541000'),(221,'梧州市','梧州市',20,'543000'),(222,'北海市','北海市',20,'536000'),(223,'防城港市','防城港市',20,'538000'),(224,'钦州市','钦州市',20,'535000'),(225,'贵港市','贵港市',20,'537100'),(226,'玉林市','玉林市',20,'537000'),(227,'百色市','百色市',20,'533000'),(228,'贺州市','贺州市',20,'542800'),(229,'河池市','河池市',20,'547000'),(230,'来宾市','来宾市',20,'546100'),(231,'崇左市','崇左市',20,'532200'),(232,'海口市','海口市',21,'570000'),(233,'三亚市','三亚市',21,'572000'),(234,'重庆市','重庆市',22,'400000'),(235,'成都市','成都市',23,'610000'),(236,'自贡市','自贡市',23,'643000'),(237,'攀枝花市','攀枝花市',23,'617000'),(238,'泸州市','泸州市',23,'646100'),(239,'德阳市','德阳市',23,'618000'),(240,'绵阳市','绵阳市',23,'621000'),(241,'广元市','广元市',23,'628000'),(242,'遂宁市','遂宁市',23,'629000'),(243,'内江市','内江市',23,'641000'),(244,'乐山市','乐山市',23,'614000'),(245,'南充市','南充市',23,'637000'),(246,'眉山市','眉山市',23,'612100'),(247,'宜宾市','宜宾市',23,'644000'),(248,'广安市','广安市',23,'638000'),(249,'达州市','达州市',23,'635000'),(250,'雅安市','雅安市',23,'625000'),(251,'巴中市','巴中市',23,'635500'),(252,'资阳市','资阳市',23,'641300'),(253,'阿坝藏族羌族自治州','阿坝藏族羌族自治州',23,'624600'),(254,'甘孜藏族自治州','甘孜藏族自治州',23,'626000'),(255,'凉山彝族自治州','凉山彝族自治州',23,'615000'),(256,'贵阳市','贵阳市',24,'55000'),(257,'六盘水市','六盘水市',24,'553000'),(258,'遵义市','遵义市',24,'563000'),(259,'安顺市','安顺市',24,'561000'),(260,'铜仁地区','铜仁地区',24,'554300'),(261,'黔西南布依族苗族自治州','黔西南布依族苗族自治州',24,'551500'),(262,'毕节地区','毕节地区',24,'551700'),(263,'黔东南苗族侗族自治州','黔东南苗族侗族自治州',24,'551500'),(264,'黔南布依族苗族自治州','黔南布依族苗族自治州',24,'550100'),(265,'昆明市','昆明市',25,'650000'),(266,'曲靖市','曲靖市',25,'655000'),(267,'玉溪市','玉溪市',25,'653100'),(268,'保山市','保山市',25,'678000'),(269,'昭通市','昭通市',25,'657000'),(270,'丽江市','丽江市',25,'674100'),(271,'思茅市','思茅市',25,'665000'),(272,'临沧市','临沧市',25,'677000'),(273,'楚雄彝族自治州','楚雄彝族自治州',25,'675000'),(274,'红河哈尼族彝族自治州','红河哈尼族彝族自治州',25,'654400'),(275,'文山壮族苗族自治州','文山壮族苗族自治州',25,'663000'),(276,'西双版纳傣族自治州','西双版纳傣族自治州',25,'666200'),(277,'大理白族自治州','大理白族自治州',25,'671000'),(278,'德宏傣族景颇族自治州','德宏傣族景颇族自治州',25,'678400'),(279,'怒江傈僳族自治州','怒江傈僳族自治州',25,'671400'),(280,'迪庆藏族自治州','迪庆藏族自治州',25,'674400'),(281,'拉萨市','拉萨市',26,'850000'),(282,'昌都地区','昌都地区',26,'854000'),(283,'山南地区','山南地区',26,'856000'),(284,'日喀则地区','日喀则地区',26,'857000'),(285,'那曲地区','那曲地区',26,'852000'),(286,'阿里地区','阿里地区',26,'859100'),(287,'林芝地区','林芝地区',26,'860100'),(288,'西安市','西安市',27,'710000'),(289,'铜川市','铜川市',27,'727000'),(290,'宝鸡市','宝鸡市',27,'721000'),(291,'咸阳市','咸阳市',27,'712000'),(292,'渭南市','渭南市',27,'714000'),(293,'延安市','延安市',27,'716000'),(294,'汉中市','汉中市',27,'723000'),(295,'榆林市','榆林市',27,'719000'),(296,'安康市','安康市',27,'725000'),(297,'商洛市','商洛市',27,'711500'),(298,'兰州市','兰州市',28,'730000'),(299,'嘉峪关市','嘉峪关市',28,'735100'),(300,'金昌市','金昌市',28,'737100'),(301,'白银市','白银市',28,'730900'),(302,'天水市','天水市',28,'741000'),(303,'武威市','武威市',28,'733000'),(304,'张掖市','张掖市',28,'734000'),(305,'平凉市','平凉市',28,'744000'),(306,'酒泉市','酒泉市',28,'735000'),(307,'庆阳市','庆阳市',28,'744500'),(308,'定西市','定西市',28,'743000'),(309,'陇南市','陇南市',28,'742100'),(310,'临夏回族自治州','临夏回族自治州',28,'731100'),(311,'甘南藏族自治州','甘南藏族自治州',28,'747000'),(312,'西宁市','西宁市',29,'810000'),(313,'海东地区','海东地区',29,'810600'),(314,'海北藏族自治州','海北藏族自治州',29,'810300'),(315,'黄南藏族自治州','黄南藏族自治州',29,'811300'),(316,'海南藏族自治州','海南藏族自治州',29,'813000'),(317,'果洛藏族自治州','果洛藏族自治州',29,'814000'),(318,'玉树藏族自治州','玉树藏族自治州',29,'815000'),(319,'海西蒙古族藏族自治州','海西蒙古族藏族自治州',29,'817000'),(320,'银川市','银川市',30,'750000'),(321,'石嘴山市','石嘴山市',30,'753000'),(322,'吴忠市','吴忠市',30,'751100'),(323,'固原市','固原市',30,'756000'),(324,'中卫市','中卫市',30,'751700'),(325,'乌鲁木齐市','乌鲁木齐市',31,'830000'),(326,'克拉玛依市','克拉玛依市',31,'834000'),(327,'吐鲁番地区','吐鲁番地区',31,'838000'),(328,'哈密地区','哈密地区',31,'839000'),(329,'昌吉回族自治州','昌吉回族自治州',31,'831100'),(330,'博尔塔拉蒙古自治州','博尔塔拉蒙古自治州',31,'833400'),(331,'巴音郭楞蒙古自治州','巴音郭楞蒙古自治州',31,'841000'),(332,'阿克苏地区','阿克苏地区',31,'843000'),(333,'克孜勒苏柯尔克孜自治州','克孜勒苏柯尔克孜自治州',31,'835600'),(334,'喀什地区','喀什地区',31,'844000'),(335,'和田地区','和田地区',31,'848000'),(336,'伊犁哈萨克自治州','伊犁哈萨克自治州',31,'833200'),(337,'塔城地区','塔城地区',31,'834700'),(338,'阿勒泰地区','阿勒泰地区',31,'836500'),(339,'石河子市','石河子市',31,'832000'),(340,'阿拉尔市','阿拉尔市',31,'843300'),(341,'图木舒克市','图木舒克市',31,'843900'),(342,'五家渠市','五家渠市',31,'831300'),(343,'香港特别行政区','香港特别行政区',32,'000000'),(344,'澳门特别行政区','澳门特别行政区',33,'000000'),(345,'台湾省','台湾省',34,'000000');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city_location`
--

DROP TABLE IF EXISTS `city_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city_location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `location` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `lat` decimal(9,6) NOT NULL,
  `lon` decimal(9,6) DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=283 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city_location`
--

LOCK TABLES `city_location` WRITE;
/*!40000 ALTER TABLE `city_location` DISABLE KEYS */;
INSERT INTO `city_location` VALUES (1,'海门',31.890000,121.150000,'自动导入','2017-01-18 23:13:34',NULL),(2,'鄂尔多斯',39.608266,109.781327,'自动导入','2017-01-18 23:13:34',NULL),(3,'招远',37.350000,120.380000,'自动导入','2017-01-18 23:13:34',NULL),(4,'舟山',29.985295,122.207216,'自动导入','2017-01-18 23:13:34',NULL),(5,'齐齐哈尔',47.330000,123.970000,'自动导入','2017-01-18 23:13:34',NULL),(6,'盐城',33.380000,120.130000,'自动导入','2017-01-18 23:13:34',NULL),(7,'赤峰',42.280000,118.870000,'自动导入','2017-01-18 23:13:34',NULL),(8,'青岛',36.070000,120.330000,'自动导入','2017-01-18 23:13:34',NULL),(9,'乳山',36.890000,121.520000,'自动导入','2017-01-18 23:13:34',NULL),(10,'金昌',38.520089,102.188043,'自动导入','2017-01-18 23:13:34',NULL),(11,'泉州',24.930000,118.580000,'自动导入','2017-01-18 23:13:34',NULL),(12,'莱西',36.860000,120.530000,'自动导入','2017-01-18 23:13:34',NULL),(13,'日照',35.420000,119.460000,'自动导入','2017-01-18 23:13:34',NULL),(14,'胶南',35.880000,119.970000,'自动导入','2017-01-18 23:13:34',NULL),(15,'南通',32.080000,121.050000,'自动导入','2017-01-18 23:13:34',NULL),(16,'拉萨',29.970000,91.110000,'自动导入','2017-01-18 23:13:34',NULL),(17,'云浮',22.930000,112.020000,'自动导入','2017-01-18 23:13:34',NULL),(18,'梅州',24.550000,116.100000,'自动导入','2017-01-18 23:13:34',NULL),(19,'文登',37.200000,122.050000,'自动导入','2017-01-18 23:13:34',NULL),(20,'上海',31.220000,121.480000,'自动导入','2017-01-18 23:13:34',NULL),(21,'攀枝花',26.582347,101.718637,'自动导入','2017-01-18 23:13:34',NULL),(22,'威海',37.500000,122.100000,'自动导入','2017-01-18 23:13:34',NULL),(23,'承德',40.970000,117.930000,'自动导入','2017-01-18 23:13:34',NULL),(24,'厦门',24.460000,118.100000,'自动导入','2017-01-18 23:13:34',NULL),(25,'汕尾',22.786211,115.375279,'自动导入','2017-01-18 23:13:34',NULL),(26,'潮州',23.680000,116.630000,'自动导入','2017-01-18 23:13:34',NULL),(27,'丹东',40.130000,124.370000,'自动导入','2017-01-18 23:13:34',NULL),(28,'太仓',31.450000,121.100000,'自动导入','2017-01-18 23:13:34',NULL),(29,'曲靖',25.510000,103.790000,'自动导入','2017-01-18 23:13:34',NULL),(30,'烟台',37.520000,121.390000,'自动导入','2017-01-18 23:13:34',NULL),(31,'福州',26.080000,119.300000,'自动导入','2017-01-18 23:13:34',NULL),(32,'瓦房店',39.627114,121.979603,'自动导入','2017-01-18 23:13:34',NULL),(33,'即墨',36.380000,120.450000,'自动导入','2017-01-18 23:13:34',NULL),(34,'抚顺',41.970000,123.970000,'自动导入','2017-01-18 23:13:34',NULL),(35,'玉溪',24.350000,102.520000,'自动导入','2017-01-18 23:13:34',NULL),(36,'张家口',40.820000,114.870000,'自动导入','2017-01-18 23:13:34',NULL),(37,'阳泉',37.850000,113.570000,'自动导入','2017-01-18 23:13:34',NULL),(38,'莱州',37.177017,119.942327,'自动导入','2017-01-18 23:13:34',NULL),(39,'湖州',30.860000,120.100000,'自动导入','2017-01-18 23:13:34',NULL),(40,'汕头',23.390000,116.690000,'自动导入','2017-01-18 23:13:34',NULL),(41,'昆山',31.390000,120.950000,'自动导入','2017-01-18 23:13:34',NULL),(42,'宁波',29.860000,121.560000,'自动导入','2017-01-18 23:13:34',NULL),(43,'湛江',21.270708,110.359377,'自动导入','2017-01-18 23:13:34',NULL),(44,'揭阳',23.550000,116.350000,'自动导入','2017-01-18 23:13:34',NULL),(45,'荣成',37.160000,122.410000,'自动导入','2017-01-18 23:13:34',NULL),(46,'连云港',34.590000,119.160000,'自动导入','2017-01-18 23:13:34',NULL),(47,'葫芦岛',40.711052,120.836932,'自动导入','2017-01-18 23:13:34',NULL),(48,'常熟',31.640000,120.740000,'自动导入','2017-01-18 23:13:34',NULL),(49,'东莞',23.040000,113.750000,'自动导入','2017-01-18 23:13:34',NULL),(50,'河源',23.730000,114.680000,'自动导入','2017-01-18 23:13:34',NULL),(51,'淮安',33.500000,119.150000,'自动导入','2017-01-18 23:13:34',NULL),(52,'泰州',32.490000,119.900000,'自动导入','2017-01-18 23:13:34',NULL),(53,'南宁',22.840000,108.330000,'自动导入','2017-01-18 23:13:34',NULL),(54,'营口',40.650000,122.180000,'自动导入','2017-01-18 23:13:34',NULL),(55,'惠州',23.090000,114.400000,'自动导入','2017-01-18 23:13:34',NULL),(56,'江阴',31.910000,120.260000,'自动导入','2017-01-18 23:13:34',NULL),(57,'蓬莱',37.800000,120.750000,'自动导入','2017-01-18 23:13:34',NULL),(58,'韶关',24.840000,113.620000,'自动导入','2017-01-18 23:13:34',NULL),(59,'嘉峪关',39.773130,98.289152,'自动导入','2017-01-18 23:13:34',NULL),(60,'广州',23.160000,113.230000,'自动导入','2017-01-18 23:13:34',NULL),(61,'延安',36.600000,109.470000,'自动导入','2017-01-18 23:13:34',NULL),(62,'太原',37.870000,112.530000,'自动导入','2017-01-18 23:13:34',NULL),(63,'清远',23.700000,113.010000,'自动导入','2017-01-18 23:13:34',NULL),(64,'中山',22.520000,113.380000,'自动导入','2017-01-18 23:13:34',NULL),(65,'昆明',25.040000,102.730000,'自动导入','2017-01-18 23:13:34',NULL),(66,'寿光',36.860000,118.730000,'自动导入','2017-01-18 23:13:34',NULL),(67,'盘锦',41.119997,122.070714,'自动导入','2017-01-18 23:13:34',NULL),(68,'长治',36.180000,113.080000,'自动导入','2017-01-18 23:13:34',NULL),(69,'深圳',22.620000,114.070000,'自动导入','2017-01-18 23:13:34',NULL),(70,'珠海',22.300000,113.520000,'自动导入','2017-01-18 23:13:34',NULL),(71,'宿迁',33.960000,118.300000,'自动导入','2017-01-18 23:13:34',NULL),(72,'咸阳',34.360000,108.720000,'自动导入','2017-01-18 23:13:34',NULL),(73,'铜川',35.090000,109.110000,'自动导入','2017-01-18 23:13:34',NULL),(74,'平度',36.770000,119.970000,'自动导入','2017-01-18 23:13:34',NULL),(75,'佛山',23.050000,113.110000,'自动导入','2017-01-18 23:13:34',NULL),(76,'海口',20.020000,110.350000,'自动导入','2017-01-18 23:13:34',NULL),(77,'江门',22.610000,113.060000,'自动导入','2017-01-18 23:13:34',NULL),(78,'章丘',36.720000,117.530000,'自动导入','2017-01-18 23:13:34',NULL),(79,'肇庆',23.050000,112.440000,'自动导入','2017-01-18 23:13:34',NULL),(80,'大连',38.920000,121.620000,'自动导入','2017-01-18 23:13:34',NULL),(81,'临汾',36.080000,111.500000,'自动导入','2017-01-18 23:13:34',NULL),(82,'吴江',31.160000,120.630000,'自动导入','2017-01-18 23:13:34',NULL),(83,'石嘴山',39.040000,106.390000,'自动导入','2017-01-18 23:13:34',NULL),(84,'沈阳',41.800000,123.380000,'自动导入','2017-01-18 23:13:34',NULL),(85,'苏州',31.320000,120.620000,'自动导入','2017-01-18 23:13:34',NULL),(86,'茂名',21.680000,110.880000,'自动导入','2017-01-18 23:13:34',NULL),(87,'嘉兴',30.770000,120.760000,'自动导入','2017-01-18 23:13:34',NULL),(88,'长春',43.880000,125.350000,'自动导入','2017-01-18 23:13:34',NULL),(89,'胶州',36.264622,120.033360,'自动导入','2017-01-18 23:13:34',NULL),(90,'银川',38.470000,106.270000,'自动导入','2017-01-18 23:13:34',NULL),(91,'张家港',31.875428,120.555821,'自动导入','2017-01-18 23:13:34',NULL),(92,'三门峡',34.760000,111.190000,'自动导入','2017-01-18 23:13:34',NULL),(93,'锦州',41.130000,121.150000,'自动导入','2017-01-18 23:13:34',NULL),(94,'南昌',28.680000,115.890000,'自动导入','2017-01-18 23:13:34',NULL),(95,'柳州',24.330000,109.400000,'自动导入','2017-01-18 23:13:34',NULL),(96,'三亚',18.252847,109.511909,'自动导入','2017-01-18 23:13:34',NULL),(97,'自贡',29.339030,104.778442,'自动导入','2017-01-18 23:13:34',NULL),(98,'吉林',43.870000,126.570000,'自动导入','2017-01-18 23:13:34',NULL),(99,'阳江',21.850000,111.950000,'自动导入','2017-01-18 23:13:34',NULL),(100,'泸州',28.910000,105.390000,'自动导入','2017-01-18 23:13:34',NULL),(101,'西宁',36.560000,101.740000,'自动导入','2017-01-18 23:13:34',NULL),(102,'宜宾',29.770000,104.560000,'自动导入','2017-01-18 23:13:34',NULL),(103,'呼和浩特',40.820000,111.650000,'自动导入','2017-01-18 23:13:34',NULL),(104,'成都',30.670000,104.060000,'自动导入','2017-01-18 23:13:34',NULL),(105,'大同',40.120000,113.300000,'自动导入','2017-01-18 23:13:34',NULL),(106,'镇江',32.200000,119.440000,'自动导入','2017-01-18 23:13:34',NULL),(107,'桂林',25.290000,110.280000,'自动导入','2017-01-18 23:13:34',NULL),(108,'张家界',29.117096,110.479191,'自动导入','2017-01-18 23:13:34',NULL),(109,'宜兴',31.360000,119.820000,'自动导入','2017-01-18 23:13:34',NULL),(110,'北海',21.490000,109.120000,'自动导入','2017-01-18 23:13:34',NULL),(111,'西安',34.270000,108.950000,'自动导入','2017-01-18 23:13:34',NULL),(112,'金坛',31.740000,119.560000,'自动导入','2017-01-18 23:13:34',NULL),(113,'东营',37.460000,118.490000,'自动导入','2017-01-18 23:13:34',NULL),(114,'牡丹江',44.600000,129.580000,'自动导入','2017-01-18 23:13:34',NULL),(115,'遵义',27.700000,106.900000,'自动导入','2017-01-18 23:13:34',NULL),(116,'绍兴',30.010000,120.580000,'自动导入','2017-01-18 23:13:34',NULL),(117,'扬州',32.390000,119.420000,'自动导入','2017-01-18 23:13:34',NULL),(118,'常州',31.790000,119.950000,'自动导入','2017-01-18 23:13:34',NULL),(119,'潍坊',36.620000,119.100000,'自动导入','2017-01-18 23:13:34',NULL),(120,'重庆',29.590000,106.540000,'自动导入','2017-01-18 23:13:34',NULL),(121,'台州',28.656386,121.420757,'自动导入','2017-01-18 23:13:34',NULL),(122,'南京',32.040000,118.780000,'自动导入','2017-01-18 23:13:34',NULL),(123,'滨州',37.360000,118.030000,'自动导入','2017-01-18 23:13:34',NULL),(124,'贵阳',26.570000,106.710000,'自动导入','2017-01-18 23:13:34',NULL),(125,'无锡',31.590000,120.290000,'自动导入','2017-01-18 23:13:34',NULL),(126,'本溪',41.300000,123.730000,'自动导入','2017-01-18 23:13:34',NULL),(127,'克拉玛依',45.590000,84.770000,'自动导入','2017-01-18 23:13:34',NULL),(128,'渭南',34.520000,109.500000,'自动导入','2017-01-18 23:13:34',NULL),(129,'马鞍山',31.560000,118.480000,'自动导入','2017-01-18 23:13:34',NULL),(130,'宝鸡',34.380000,107.150000,'自动导入','2017-01-18 23:13:34',NULL),(131,'焦作',35.240000,113.210000,'自动导入','2017-01-18 23:13:34',NULL),(132,'句容',31.950000,119.160000,'自动导入','2017-01-18 23:13:34',NULL),(133,'北京',39.920000,116.460000,'自动导入','2017-01-18 23:13:34',NULL),(134,'徐州',34.260000,117.200000,'自动导入','2017-01-18 23:13:34',NULL),(135,'衡水',37.720000,115.720000,'自动导入','2017-01-18 23:13:34',NULL),(136,'包头',40.580000,110.000000,'自动导入','2017-01-18 23:13:34',NULL),(137,'绵阳',31.480000,104.730000,'自动导入','2017-01-18 23:13:34',NULL),(138,'乌鲁木齐',43.770000,87.680000,'自动导入','2017-01-18 23:13:34',NULL),(139,'枣庄',34.860000,117.570000,'自动导入','2017-01-18 23:13:34',NULL),(140,'杭州',30.260000,120.190000,'自动导入','2017-01-18 23:13:34',NULL),(141,'淄博',36.780000,118.050000,'自动导入','2017-01-18 23:13:34',NULL),(142,'鞍山',41.120000,122.850000,'自动导入','2017-01-18 23:13:34',NULL),(143,'溧阳',31.430000,119.480000,'自动导入','2017-01-18 23:13:34',NULL),(144,'库尔勒',41.680000,86.060000,'自动导入','2017-01-18 23:13:34',NULL),(145,'安阳',36.100000,114.350000,'自动导入','2017-01-18 23:13:34',NULL),(146,'开封',34.790000,114.350000,'自动导入','2017-01-18 23:13:34',NULL),(147,'济南',36.650000,117.000000,'自动导入','2017-01-18 23:13:34',NULL),(148,'德阳',31.130000,104.370000,'自动导入','2017-01-18 23:13:34',NULL),(149,'温州',28.010000,120.650000,'自动导入','2017-01-18 23:13:34',NULL),(150,'九江',29.710000,115.970000,'自动导入','2017-01-18 23:13:34',NULL),(151,'邯郸',36.600000,114.470000,'自动导入','2017-01-18 23:13:34',NULL),(152,'临安',30.230000,119.720000,'自动导入','2017-01-18 23:13:34',NULL),(153,'兰州',36.030000,103.730000,'自动导入','2017-01-18 23:13:34',NULL),(154,'沧州',38.330000,116.830000,'自动导入','2017-01-18 23:13:34',NULL),(155,'临沂',35.050000,118.350000,'自动导入','2017-01-18 23:13:34',NULL),(156,'南充',30.837793,106.110698,'自动导入','2017-01-18 23:13:34',NULL),(157,'天津',39.130000,117.200000,'自动导入','2017-01-18 23:13:34',NULL),(158,'富阳',30.070000,119.950000,'自动导入','2017-01-18 23:13:34',NULL),(159,'泰安',36.180000,117.130000,'自动导入','2017-01-18 23:13:34',NULL),(160,'诸暨',29.710000,120.230000,'自动导入','2017-01-18 23:13:34',NULL),(161,'郑州',34.760000,113.650000,'自动导入','2017-01-18 23:13:34',NULL),(162,'哈尔滨',45.750000,126.630000,'自动导入','2017-01-18 23:13:34',NULL),(163,'聊城',36.450000,115.970000,'自动导入','2017-01-18 23:13:34',NULL),(164,'芜湖',31.330000,118.380000,'自动导入','2017-01-18 23:13:34',NULL),(165,'唐山',39.630000,118.020000,'自动导入','2017-01-18 23:13:34',NULL),(166,'平顶山',33.750000,113.290000,'自动导入','2017-01-18 23:13:34',NULL),(167,'邢台',37.050000,114.480000,'自动导入','2017-01-18 23:13:34',NULL),(168,'德州',37.450000,116.290000,'自动导入','2017-01-18 23:13:34',NULL),(169,'济宁',35.380000,116.590000,'自动导入','2017-01-18 23:13:34',NULL),(170,'荆州',30.335165,112.239741,'自动导入','2017-01-18 23:13:34',NULL),(171,'宜昌',30.700000,111.300000,'自动导入','2017-01-18 23:13:34',NULL),(172,'义乌',29.320000,120.060000,'自动导入','2017-01-18 23:13:34',NULL),(173,'丽水',28.450000,119.920000,'自动导入','2017-01-18 23:13:34',NULL),(174,'洛阳',34.700000,112.440000,'自动导入','2017-01-18 23:13:34',NULL),(175,'秦皇岛',39.950000,119.570000,'自动导入','2017-01-18 23:13:34',NULL),(176,'株洲',27.830000,113.160000,'自动导入','2017-01-18 23:13:34',NULL),(177,'石家庄',38.030000,114.480000,'自动导入','2017-01-18 23:13:34',NULL),(178,'莱芜',36.190000,117.670000,'自动导入','2017-01-18 23:13:34',NULL),(179,'常德',29.050000,111.690000,'自动导入','2017-01-18 23:13:34',NULL),(180,'保定',38.850000,115.480000,'自动导入','2017-01-18 23:13:34',NULL),(181,'湘潭',27.870000,112.910000,'自动导入','2017-01-18 23:13:34',NULL),(182,'金华',29.120000,119.640000,'自动导入','2017-01-18 23:13:34',NULL),(183,'岳阳',29.370000,113.090000,'自动导入','2017-01-18 23:13:34',NULL),(184,'长沙',28.210000,113.000000,'自动导入','2017-01-18 23:13:34',NULL),(185,'衢州',28.970000,118.880000,'自动导入','2017-01-18 23:13:34',NULL),(186,'廊坊',39.530000,116.700000,'自动导入','2017-01-18 23:13:34',NULL),(187,'菏泽',35.233750,115.480656,'自动导入','2017-01-18 23:13:34',NULL),(188,'合肥',31.860000,117.270000,'自动导入','2017-01-18 23:13:34',NULL),(189,'武汉',30.520000,114.310000,'自动导入','2017-01-18 23:13:34',NULL),(190,'丽江',26.882372,100.228539,'人工添加','2017-01-18 23:13:34','2017-01-18 23:23:12'),(191,'大庆',46.580000,125.030000,'自动导入','2017-01-18 23:13:34',NULL),(192,'文昌',19.554334,110.800080,NULL,'2017-01-18 23:23:57',NULL),(193,'木兰湾',20.162153,110.695448,NULL,'2017-01-18 23:25:26',NULL),(194,'临海',28.854685,121.145067,NULL,'2017-01-18 23:27:15',NULL),(195,'康定',30.004751,101.963390,NULL,'2017-01-18 23:28:58',NULL),(196,'理塘',30.000390,100.275594,NULL,'2017-01-18 23:29:40',NULL),(197,'新都桥',30.053854,101.483690,NULL,'2017-01-18 23:30:21',NULL),(198,'博鳌',19.165494,110.592991,NULL,'2017-01-18 23:31:33',NULL),(199,'大理',25.693871,100.170522,NULL,'2017-01-21 09:34:42',NULL),(200,'泸沽湖',26.602021,100.184670,NULL,'2017-01-21 09:36:20',NULL),(201,'香格里拉',27.818556,99.711203,NULL,'2017-01-21 09:38:00',NULL),(202,'稻城亚丁',29.046955,100.305217,NULL,'2017-01-21 09:38:52',NULL),(203,'雅安',30.015774,103.044779,NULL,'2017-01-21 13:51:41',NULL),(204,'新沟',29.930867,102.393936,NULL,'2017-01-21 13:52:28',NULL),(205,'雅江',30.037961,101.020364,NULL,'2017-01-21 13:53:19',NULL),(206,'巴塘',30.010945,99.110207,NULL,'2017-01-21 13:57:27',NULL),(207,'芒康',29.622525,98.357347,NULL,'2017-01-21 13:59:09',NULL),(208,'左贡',29.676948,97.847471,NULL,'2017-01-21 13:59:53',NULL),(209,'邦达',30.213557,97.299188,NULL,'2017-01-21 14:01:03',NULL),(210,'八宿',30.059030,96.924410,NULL,'2017-01-21 14:01:54',NULL),(211,'然乌',29.509466,96.759249,NULL,'2017-01-21 14:02:44',NULL),(212,'波密',29.865288,95.774010,NULL,'2017-01-21 14:03:28',NULL),(213,'鲁朗',29.952641,94.804924,NULL,'2017-01-21 14:04:12',NULL),(214,'八一',29.645728,94.366665,NULL,'2017-01-21 14:04:51',NULL),(215,'工布江达',29.885312,93.246110,NULL,'2017-01-21 14:05:28',NULL),(216,'松多',29.899096,92.484207,NULL,'2017-01-21 14:06:12',NULL),(217,'墨竹工卡',29.840756,91.729598,NULL,'2017-01-21 14:06:51',NULL),(218,'纳木错',30.778925,90.878466,NULL,'2017-01-21 14:09:23',NULL),(219,'曲水',29.359589,90.749985,NULL,'2017-01-21 14:12:14',NULL),(220,'羊湖',28.954098,90.808844,NULL,'2017-01-21 14:13:17',NULL),(221,'浪卡子',28.973061,90.408374,NULL,'2017-01-21 14:14:00',NULL),(222,'江孜',28.917509,89.611918,NULL,'2017-01-21 14:14:37',NULL),(223,'日喀则',29.272918,88.887724,NULL,'2017-01-21 14:15:25',NULL),(224,'铺前镇',20.029568,110.586669,NULL,'2017-01-21 14:20:18',NULL),(225,'抱罗镇',19.849751,110.755365,NULL,'2017-01-21 14:21:24',NULL),(226,'兴隆',18.711437,110.254904,NULL,'2017-01-21 14:23:07',NULL),(227,'亚龙湾',18.236155,109.654556,NULL,'2017-01-21 14:23:41',NULL),(228,'棋子湾',19.370987,108.703115,NULL,'2017-01-21 14:24:50',NULL),(229,'虎跳峡',27.195168,100.119685,NULL,'2017-01-21 14:30:14',NULL),(230,'腾冲',25.026283,98.498472,NULL,'2017-01-21 14:30:52',NULL),(231,'六库',25.849765,98.861116,NULL,'2017-01-21 14:31:40',NULL),(232,'福贡',26.907975,98.875428,NULL,'2017-01-21 14:32:09',NULL),(233,'贡山',27.750051,98.673654,NULL,'2017-01-21 14:32:41',NULL),(234,'丙中洛',28.027623,98.652049,NULL,'2017-01-21 14:33:08',NULL),(235,'察瓦龙',28.482386,98.470042,NULL,'2017-01-21 14:37:42',NULL),(236,'玉龙',26.828960,100.235859,NULL,'2017-01-21 14:43:47',NULL),(237,'蒙自',23.395404,103.311692,NULL,'2017-01-21 14:44:18',NULL),(238,'元阳',23.222038,102.830789,NULL,'2017-01-21 14:44:47',NULL),(239,'新叶村',29.335691,119.342555,NULL,'2017-01-21 14:50:01',NULL),(240,'大慈岩',29.333071,119.301811,NULL,'2017-01-21 14:50:29',NULL),(241,'新安江',29.484725,119.294686,NULL,'2017-01-21 14:50:58',NULL),(242,'乌镇',30.754100,120.497786,NULL,'2017-01-21 14:54:04',NULL),(243,'西塘',30.945624,120.894519,NULL,'2017-01-21 14:55:02',NULL),(244,'安昌古镇',30.141842,120.502674,NULL,'2017-01-21 14:57:30',NULL),(245,'同里',31.161831,120.725909,NULL,'2017-01-21 14:58:07','2017-02-26 15:17:00'),(246,'新定日',28.469456,86.963813,NULL,'2017-01-21 15:02:13',NULL),(247,'珠峰大本营',28.143433,86.859569,NULL,'2017-01-21 15:02:48',NULL),(248,'聂拉木',28.160730,85.988889,NULL,'2017-01-21 15:03:28',NULL),(249,'樟木镇',27.993542,85.989902,NULL,'2017-01-21 15:03:59',NULL),(250,'白坝乡',28.638303,87.179378,NULL,'2017-01-21 15:05:55',NULL),(251,'西昌',27.900523,102.270474,NULL,'2017-01-21 15:20:10',NULL),(252,'木里',27.900523,102.270474,NULL,'2017-01-21 15:20:40',NULL),(253,'九所镇',18.459808,108.960347,NULL,'2017-01-21 15:21:41',NULL),(254,'尖峰镇',18.696234,108.798911,NULL,'2017-01-21 15:22:16',NULL),(255,'板桥镇',18.800325,108.695150,NULL,'2017-01-21 15:22:57',NULL),(256,'邦溪镇',19.376327,109.110202,NULL,'2017-01-21 15:23:33',NULL),(257,'多文镇',19.779719,109.777491,NULL,'2017-01-21 15:24:07',NULL),(258,'建德',29.481607,119.287680,NULL,'2017-02-26 15:15:21',NULL),(259,'俄亚大村',27.936056,100.388934,NULL,'2017-03-30 08:06:39',NULL),(260,'南浔',30.881192,120.437076,NULL,'2017-05-16 09:19:43',NULL),(261,'婺源',29.260049,117.871840,NULL,'2017-05-18 15:54:51',NULL),(262,'椒江',28.678034,121.439173,NULL,'2017-05-18 15:59:33',NULL),(263,'温岭',28.376312,121.389273,NULL,'2017-05-18 16:00:08',NULL),(264,'周庄',31.119265,120.855966,NULL,'2017-05-22 08:22:01',NULL),(265,'加德满都',27.768092,85.356766,NULL,'2017-06-19 16:08:38','2017-08-23 14:16:43'),(266,'博卡拉',0.000000,0.000000,NULL,'2017-06-19 16:09:25','2017-09-02 10:44:24'),(267,'嘉措乡',0.000000,0.000000,NULL,'2017-06-19 16:10:32','2017-09-06 12:28:21'),(268,'昌洒镇',19.767873,110.907114,NULL,'2017-09-10 17:07:59',NULL),(269,'瓦厂镇',28.151431,100.849699,NULL,'2017-09-12 13:35:57',NULL),(270,'俄碧村',27.891460,100.447058,NULL,'2017-09-12 13:37:59',NULL),(271,'牯岭镇',29.575344,115.990964,NULL,'2018-10-02 12:28:21',NULL),(272,'东林寺',29.604479,115.955644,NULL,'2018-10-02 12:29:26',NULL),(273,'沈家湾',30.607321,122.139999,NULL,'2019-01-18 22:45:40',NULL),(274,'嵊泗',30.715131,122.471389,NULL,'2019-01-18 22:50:21',NULL),(275,'枸杞岛',30.721006,122.786402,NULL,'2019-01-18 22:51:30',NULL),(276,'沈家门',29.947178,122.326926,NULL,'2019-01-18 22:53:01',NULL),(277,'东福山',30.138752,122.776313,NULL,'2019-03-15 15:18:43',NULL),(278,'庙子湖',30.202726,122.688940,NULL,'2019-03-15 15:20:24',NULL),(279,'遂昌',28.343833,119.088374,'南尖岩景区','2019-10-21 10:47:21',NULL),(280,'歙县',29.944556,118.791271,NULL,'2019-11-24 10:10:42',NULL),(281,'嵊州',29.565317,120.827086,'','2019-12-09 21:53:05','2019-12-22 16:44:03'),(282,'新昌',29.332468,120.725524,'外婆坑','2020-05-30 21:37:12',NULL);
/*!40000 ALTER TABLE `city_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `command_config`
--

DROP TABLE IF EXISTS `command_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `command_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `scode` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `level` smallint NOT NULL DEFAULT '0',
  `order_index` smallint NOT NULL DEFAULT '0',
  `status` smallint DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `command_config`
--

LOCK TABLES `command_config` WRITE;
/*!40000 ALTER TABLE `command_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `command_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `common_record`
--

DROP TABLE IF EXISTS `common_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `common_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `common_record_type_id` int DEFAULT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `value` int NOT NULL,
  `occur_time` datetime NOT NULL,
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `common_record`
--

LOCK TABLES `common_record` WRITE;
/*!40000 ALTER TABLE `common_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `common_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `common_record_type`
--

DROP TABLE IF EXISTS `common_record_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `common_record_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint NOT NULL,
  `unit` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `month_stat` bit(1) NOT NULL DEFAULT b'0',
  `year_stat` bit(1) NOT NULL DEFAULT b'0',
  `over_work_stat` bit(1) NOT NULL DEFAULT b'0',
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  `reward_point` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `common_record_type`
--

LOCK TABLES `common_record_type` WRITE;
/*!40000 ALTER TABLE `common_record_type` DISABLE KEYS */;
INSERT INTO `common_record_type` VALUES (15,'默认分类',0,'分钟',_binary '',_binary '',_binary '',1,1,0);
/*!40000 ALTER TABLE `common_record_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `days` int NOT NULL COMMENT '天数',
  `entry_date` date NOT NULL COMMENT '入职日期',
  `quit_date` date DEFAULT NULL COMMENT '离职日期',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='公司';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consume_type`
--

DROP TABLE IF EXISTS `consume_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consume_type` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `user_id` bigint NOT NULL DEFAULT '1',
  `status` smallint NOT NULL COMMENT '状态',
  `order_index` smallint NOT NULL COMMENT '排序号',
  `statable` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人生经历消费类别';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consume_type`
--

LOCK TABLES `consume_type` WRITE;
/*!40000 ALTER TABLE `consume_type` DISABLE KEYS */;
INSERT INTO `consume_type` VALUES (14,'餐饮',0,1,1,_binary ''),(15,'交通',0,1,2,_binary ''),(16,'住宿',0,1,3,_binary ''),(17,'门票',0,1,4,_binary '');
/*!40000 ALTER TABLE `consume_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cn_name` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `en_name` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `en_code2` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `en_code3` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `code` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `location` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `order_index` smallint DEFAULT NULL,
  `status` smallint DEFAULT '1',
  `remark` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=327 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (80,'安道尔','Andorra','AD','AND','20',NULL,0,1,NULL),(81,'阿联酋','United Arab Emirates','AE','ARE','784',NULL,1,1,NULL),(82,'阿富汗','Afghanistan','AF','AFG','4',NULL,2,1,NULL),(83,'安提瓜和巴布达','Antigua & Barbuda','AG','ATG','28',NULL,3,1,NULL),(84,'安圭拉','Anguilla','AI','AIA','660',NULL,4,1,NULL),(85,'阿尔巴尼亚','Albania','AL','ALB','8',NULL,5,1,NULL),(86,'亚美尼亚','Armenia','AM','ARM','51',NULL,6,1,NULL),(87,'安哥拉','Angola','AO','AGO','24',NULL,7,1,NULL),(88,'南极洲','Antarctica','AQ','ATA','10',NULL,8,1,NULL),(89,'阿根廷','Argentina','AR','ARG','32',NULL,9,1,NULL),(90,'美属萨摩亚','American Samoa','AS','ASM','16',NULL,10,1,NULL),(91,'奥地利','Austria','AT','AUT','40',NULL,11,1,NULL),(92,'澳大利亚','Australia','AU','AUS','36',NULL,12,1,NULL),(93,'阿鲁巴','Aruba','AW','ABW','533',NULL,13,1,NULL),(94,'奥兰群岛','?aland Island','AX','ALA','248',NULL,14,1,NULL),(95,'阿塞拜疆','Azerbaijan','AZ','AZE','31',NULL,15,1,NULL),(96,'波黑','Bosnia & Herzegovina','BA','BIH','70',NULL,16,1,NULL),(97,'巴巴多斯','Barbados','BB','BRB','52',NULL,17,1,NULL),(98,'孟加拉','Bangladesh','BD','BGD','50',NULL,18,1,NULL),(99,'比利时','Belgium','BE','BEL','56',NULL,19,1,NULL),(100,'布基纳法索','Burkina','BF','BFA','854',NULL,20,1,NULL),(101,'保加利亚','Bulgaria','BG','BGR','100',NULL,21,1,NULL),(102,'巴林','Bahrain','BH','BHR','48',NULL,22,1,NULL),(103,'布隆迪','Burundi','BI','BDI','108',NULL,23,1,NULL),(104,'贝宁','Benin','BJ','BEN','204',NULL,24,1,NULL),(105,'圣巴泰勒米岛','Saint Barthélemy','BL','BLM','652',NULL,25,1,NULL),(106,'百慕大','Bermuda','BM','BMU','60',NULL,26,1,NULL),(107,'文莱','Brunei','BN','BRN','96',NULL,27,1,NULL),(108,'玻利维亚','Bolivia','BO','BOL','68',NULL,28,1,NULL),(109,'荷兰加勒比区','Caribbean Netherlands','BQ','BES','535',NULL,29,1,NULL),(110,'巴西','Brazil','BR','BRA','76',NULL,30,1,NULL),(111,'巴哈马','The Bahamas','BS','BHS','44',NULL,31,1,NULL),(112,'不丹','Bhutan','BT','BTN','64',NULL,32,1,NULL),(113,'布韦岛','Bouvet Island','BV','BVT','74',NULL,33,1,NULL),(114,'博茨瓦纳','Botswana','BW','BWA','72',NULL,34,1,NULL),(115,'白俄罗斯','Belarus','BY','BLR','112',NULL,35,1,NULL),(116,'伯利兹','Belize','BZ','BLZ','84',NULL,36,1,NULL),(117,'加拿大','Canada','CA','CAN','124',NULL,37,1,NULL),(118,'科科斯群岛','Cocos (Keeling) Islands','CC','CCK','166',NULL,38,1,NULL),(119,'中非','Central African Republic','CF','CAF','140',NULL,39,1,NULL),(120,'瑞士','Switzerland','CH','CHE','756',NULL,40,1,NULL),(121,'智利','Chile','CL','CHL','152',NULL,41,1,NULL),(122,'喀麦隆','Cameroon','CM','CMR','120',NULL,42,1,NULL),(123,'哥伦比亚','Colombia','CO','COL','170',NULL,43,1,NULL),(124,'哥斯达黎加','Costa Rica','CR','CRI','188',NULL,44,1,NULL),(125,'古巴','Cuba','CU','CUB','192',NULL,45,1,NULL),(126,'佛得角','Cape Verde','CV','CPV','132',NULL,46,1,NULL),(127,'圣诞岛','Christmas Island','CX','CXR','162',NULL,47,1,NULL),(128,'塞浦路斯','Cyprus','CY','CYP','196',NULL,48,1,NULL),(129,'捷克','Czech Republic','CZ','CZE','203',NULL,49,1,NULL),(130,'德国','Germany','DE','DEU','276',NULL,50,1,NULL),(131,'吉布提','Djibouti','DJ','DJI','262',NULL,51,1,NULL),(132,'丹麦','Denmark','DK','DNK','208',NULL,52,1,NULL),(133,'多米尼克','Dominica','DM','DMA','212',NULL,53,1,NULL),(134,'多米尼加','Dominican Republic','DO','DOM','214',NULL,54,1,NULL),(135,'阿尔及利亚','Algeria','DZ','DZA','12',NULL,55,1,NULL),(136,'厄瓜多尔','Ecuador','EC','ECU','218',NULL,56,1,NULL),(137,'爱沙尼亚','Estonia','EE','EST','233',NULL,57,1,NULL),(138,'埃及','Egypt','EG','EGY','818',NULL,58,1,NULL),(139,'西撒哈拉','Western Sahara','EH','ESH','732',NULL,59,1,NULL),(140,'厄立特里亚','Eritrea','ER','ERI','232',NULL,60,1,NULL),(141,'西班牙','Spain','ES','ESP','724',NULL,61,1,NULL),(142,'芬兰','Finland','FI','FIN','246',NULL,62,1,NULL),(143,'斐济群岛','Fiji','FJ','FJI','242',NULL,63,1,NULL),(144,'马尔维纳斯群岛（ 福克兰）','Falkland Islands','FK','FLK','238',NULL,64,1,NULL),(145,'密克罗尼西亚联邦','Federated States of Micronesia','FM','FSM','583',NULL,65,1,NULL),(146,'法罗群岛','Faroe Islands','FO','FRO','234',NULL,66,1,NULL),(147,'法国','France','FR','FRA','250',NULL,-8,1,NULL),(148,'加蓬','Gabon','GA','GAB','266',NULL,68,1,NULL),(149,'格林纳达','Grenada','GD','GRD','308',NULL,69,1,NULL),(150,'格鲁吉亚','Georgia','GE','GEO','268',NULL,70,1,NULL),(151,'法属圭亚那','French Guiana','GF','GUF','254',NULL,71,1,NULL),(152,'加纳','Ghana','GH','GHA','288',NULL,72,1,NULL),(153,'直布罗陀','Gibraltar','GI','GIB','292',NULL,73,1,NULL),(154,'格陵兰','Greenland','GL','GRL','304',NULL,74,1,NULL),(155,'几内亚','Guinea','GN','GIN','324',NULL,75,1,NULL),(156,'瓜德罗普','Guadeloupe','GP','GLP','312',NULL,76,1,NULL),(157,'赤道几内亚','Equatorial Guinea','GQ','GNQ','226',NULL,77,1,NULL),(158,'希腊','Greece','GR','GRC','300',NULL,78,1,NULL),(159,'南乔治亚岛和南桑威奇群岛','South Georgia and the South Sandwich Islands','GS','SGS','239',NULL,79,1,NULL),(160,'危地马拉','Guatemala','GT','GTM','320',NULL,80,1,NULL),(161,'关岛','Guam','GU','GUM','316',NULL,81,1,NULL),(162,'几内亚比绍','Guinea-Bissau','GW','GNB','624',NULL,82,1,NULL),(163,'圭亚那','Guyana','GY','GUY','328',NULL,83,1,NULL),(164,'香港','Hong Kong','HK','HKG','344',NULL,84,0,NULL),(165,'赫德岛和麦克唐纳群岛','Heard Island and McDonald Islands','HM','HMD','334',NULL,85,1,NULL),(166,'洪都拉斯','Honduras','HN','HND','340',NULL,86,1,NULL),(167,'克罗地亚','Croatia','HR','HRV','191',NULL,87,1,NULL),(168,'海地','Haiti','HT','HTI','332',NULL,88,1,NULL),(169,'匈牙利','Hungary','HU','HUN','348',NULL,89,1,NULL),(170,'印尼','Indonesia','ID','IDN','360',NULL,90,1,NULL),(171,'爱尔兰','Ireland','IE','IRL','372',NULL,91,1,NULL),(172,'以色列','Israel','IL','ISR','376',NULL,92,1,NULL),(173,'马恩岛','Isle of Man','IM','IMN','833',NULL,93,1,NULL),(174,'印度','India','IN','IND','356',NULL,94,1,NULL),(175,'英属印度洋领地','British Indian Ocean Territory','IO','IOT','86',NULL,95,1,NULL),(176,'伊拉克','Iraq','IQ','IRQ','368',NULL,96,1,NULL),(177,'伊朗','Iran','IR','IRN','364',NULL,97,1,NULL),(178,'冰岛','Iceland','IS','ISL','352',NULL,98,1,NULL),(179,'意大利','Italy','IT','ITA','380',NULL,99,1,NULL),(180,'泽西岛','Jersey','JE','JEY','832',NULL,100,1,NULL),(181,'牙买加','Jamaica','JM','JAM','388',NULL,101,1,NULL),(182,'约旦','Jordan','JO','JOR','400',NULL,102,1,NULL),(183,'日本','Japan','JP','JPN','392',NULL,103,1,NULL),(184,'柬埔寨','Cambodia','KH','KHM','116',NULL,104,1,NULL),(185,'基里巴斯','Kiribati','KI','KIR','296',NULL,105,1,NULL),(186,'科摩罗','The Comoros','KM','COM','174',NULL,106,1,NULL),(187,'科威特','Kuwait','KW','KWT','414',NULL,107,1,NULL),(188,'开曼群岛','Cayman Islands','KY','CYM','136',NULL,108,1,NULL),(189,'黎巴嫩','Lebanon','LB','LBN','422',NULL,109,1,NULL),(190,'列支敦士登','Liechtenstein','LI','LIE','438',NULL,110,1,NULL),(191,'斯里兰卡','Sri Lanka','LK','LKA','144',NULL,111,1,NULL),(192,'利比里亚','Liberia','LR','LBR','430',NULL,112,1,NULL),(193,'莱索托','Lesotho','LS','LSO','426',NULL,113,1,NULL),(194,'立陶宛','Lithuania','LT','LTU','440',NULL,114,1,NULL),(195,'卢森堡','Luxembourg','LU','LUX','442',NULL,115,1,NULL),(196,'拉脱维亚','Latvia','LV','LVA','428',NULL,116,1,NULL),(197,'利比亚','Libya','LY','LBY','434',NULL,117,1,NULL),(198,'摩洛哥','Morocco','MA','MAR','504',NULL,118,1,NULL),(199,'摩纳哥','Monaco','MC','MCO','492',NULL,119,1,NULL),(200,'摩尔多瓦','Moldova','MD','MDA','498',NULL,120,1,NULL),(201,'黑山','Montenegro','ME','MNE','499',NULL,121,1,NULL),(202,'法属圣马丁','Saint Martin (France)','MF','MAF','663',NULL,122,1,NULL),(203,'马达加斯加','Madagascar','MG','MDG','450',NULL,123,1,NULL),(204,'马绍尔群岛','Marshall islands','MH','MHL','584',NULL,124,1,NULL),(205,'马其顿','Republic of Macedonia (FYROM)','MK','MKD','807',NULL,125,1,NULL),(206,'马里','Mali','ML','MLI','466',NULL,126,1,NULL),(207,'缅甸','Myanmar (Burma)','MM','MMR','104',NULL,127,1,NULL),(208,'澳门','Macao','MO','MAC','446',NULL,128,0,NULL),(209,'马提尼克','Martinique','MQ','MTQ','474',NULL,129,1,NULL),(210,'毛里塔尼亚','Mauritania','MR','MRT','478',NULL,130,1,NULL),(211,'蒙塞拉特岛','Montserrat','MS','MSR','500',NULL,131,1,NULL),(212,'马耳他','Malta','MT','MLT','470',NULL,132,1,NULL),(213,'马尔代夫','Maldives','MV','MDV','462',NULL,133,1,NULL),(214,'马拉维','Malawi','MW','MWI','454',NULL,134,1,NULL),(215,'墨西哥','Mexico','MX','MEX','484',NULL,135,1,NULL),(216,'马来西亚','Malaysia','MY','MYS','458',NULL,136,1,NULL),(217,'纳米比亚','Namibia','NA','NAM','516',NULL,137,1,NULL),(218,'尼日尔','Niger','NE','NER','562',NULL,138,1,NULL),(219,'诺福克岛','Norfolk Island','NF','NFK','574',NULL,139,1,NULL),(220,'尼日利亚','Nigeria','NG','NGA','566',NULL,140,1,NULL),(221,'尼加拉瓜','Nicaragua','NI','NIC','558',NULL,141,1,NULL),(222,'荷兰','Netherlands','NL','NLD','528',NULL,142,1,NULL),(223,'挪威','Norway','NO','NOR','578',NULL,143,1,NULL),(224,'尼泊尔','Nepal','NP','NPL','524',NULL,144,1,NULL),(225,'瑙鲁','Nauru','NR','NRU','520',NULL,145,1,NULL),(226,'阿曼','Oman','OM','OMN','512',NULL,146,1,NULL),(227,'巴拿马','Panama','PA','PAN','591',NULL,147,1,NULL),(228,'秘鲁','Peru','PE','PER','604',NULL,148,1,NULL),(229,'法属波利尼西亚','French polynesia','PF','PYF','258',NULL,149,1,NULL),(230,'巴布亚新几内亚','Papua New Guinea','PG','PNG','598',NULL,150,1,NULL),(231,'菲律宾','The Philippines','PH','PHL','608',NULL,151,1,NULL),(232,'巴基斯坦','Pakistan','PK','PAK','586',NULL,152,1,NULL),(233,'波兰','Poland','PL','POL','616',NULL,153,1,NULL),(234,'皮特凯恩群岛','Pitcairn Islands','PN','PCN','612',NULL,154,1,NULL),(235,'波多黎各','Puerto Rico','PR','PRI','630',NULL,155,1,NULL),(236,'巴勒斯坦','Palestinian territories','PS','PSE','275',NULL,156,1,NULL),(237,'帕劳','Palau','PW','PLW','585',NULL,157,1,NULL),(238,'巴拉圭','Paraguay','PY','PRY','600',NULL,158,1,NULL),(239,'卡塔尔','Qatar','QA','QAT','634',NULL,159,1,NULL),(240,'留尼汪','Réunion','RE','REU','638',NULL,160,1,NULL),(241,'罗马尼亚','Romania','RO','ROU','642',NULL,161,1,NULL),(242,'塞尔维亚','Serbia','RS','SRB','688',NULL,162,1,NULL),(243,'俄罗斯','Russian Federation','RU','RUS','643',NULL,-7,1,NULL),(244,'卢旺达','Rwanda','RW','RWA','646',NULL,164,1,NULL),(245,'所罗门群岛','Solomon Islands','SB','SLB','90',NULL,165,1,NULL),(246,'塞舌尔','Seychelles','SC','SYC','690',NULL,166,1,NULL),(247,'苏丹','Sudan','SD','SDN','729',NULL,167,1,NULL),(248,'瑞典','Sweden','SE','SWE','752',NULL,168,1,NULL),(249,'新加坡','Singapore','SG','SGP','702',NULL,169,1,NULL),(250,'斯洛文尼亚','Slovenia','SI','SVN','705',NULL,170,1,NULL),(251,'斯瓦尔巴群岛和 扬马延岛','Template:Country data SJM Svalbard','SJ','SJM','744',NULL,171,1,NULL),(252,'斯洛伐克','Slovakia','SK','SVK','703',NULL,172,1,NULL),(253,'塞拉利昂','Sierra Leone','SL','SLE','694',NULL,173,1,NULL),(254,'圣马力诺','San Marino','SM','SMR','674',NULL,174,1,NULL),(255,'塞内加尔','Senegal','SN','SEN','686',NULL,175,1,NULL),(256,'索马里','Somalia','SO','SOM','706',NULL,176,1,NULL),(257,'苏里南','Suriname','SR','SUR','740',NULL,177,1,NULL),(258,'南苏丹','South Sudan','SS','SSD','728',NULL,178,1,NULL),(259,'圣多美和普林西比','Sao Tome & Principe','ST','STP','678',NULL,179,1,NULL),(260,'萨尔瓦多','El Salvador','SV','SLV','222',NULL,180,1,NULL),(261,'叙利亚','Syria','SY','SYR','760',NULL,181,1,NULL),(262,'斯威士兰','Swaziland','SZ','SWZ','748',NULL,182,1,NULL),(263,'特克斯和凯科斯群岛','Turks & Caicos Islands','TC','TCA','796',NULL,183,1,NULL),(264,'乍得','Chad','TD','TCD','148',NULL,184,1,NULL),(265,'多哥','Togo','TG','TGO','768',NULL,185,1,NULL),(266,'泰国','Thailand','TH','THA','764',NULL,186,1,NULL),(267,'托克劳','Tokelau','TK','TKL','772',NULL,187,1,NULL),(268,'东帝汶','Timor-Leste (East Timor)','TL','TLS','626',NULL,188,1,NULL),(269,'突尼斯','Tunisia','TN','TUN','788',NULL,189,1,NULL),(270,'汤加','Tonga','TO','TON','776',NULL,190,1,NULL),(271,'土耳其','Turkey','TR','TUR','792',NULL,191,1,NULL),(272,'图瓦卢','Tuvalu','TV','TUV','798',NULL,192,1,NULL),(273,'坦桑尼亚','Tanzania','TZ','TZA','834',NULL,193,1,NULL),(274,'乌克兰','Ukraine','UA','UKR','804',NULL,194,1,NULL),(275,'乌干达','Uganda','UG','UGA','800',NULL,195,1,NULL),(276,'美国','United States of America (USA)','US','USA','840',NULL,-10,1,NULL),(277,'乌拉圭','Uruguay','UY','URY','858',NULL,197,1,NULL),(278,'梵蒂冈','Vatican City (The Holy See)','VA','VAT','336',NULL,198,1,NULL),(279,'委内瑞拉','Venezuela','VE','VEN','862',NULL,199,1,NULL),(280,'英属维尔京群岛','British Virgin Islands','VG','VGB','92',NULL,200,1,NULL),(281,'美属维尔京群岛','United States Virgin Islands','VI','VIR','850',NULL,201,1,NULL),(282,'越南','Vietnam','VN','VNM','704',NULL,202,1,NULL),(283,'瓦利斯和富图纳','Wallis and Futuna','WF','WLF','876',NULL,203,1,NULL),(284,'萨摩亚','Samoa','WS','WSM','882',NULL,204,1,NULL),(285,'也门','Yemen','YE','YEM','887',NULL,205,1,NULL),(286,'马约特','Mayotte','YT','MYT','175',NULL,206,1,NULL),(287,'南非','South Africa','ZA','ZAF','710',NULL,207,1,NULL),(288,'赞比亚','Zambia','ZM','ZMB','894',NULL,208,1,NULL),(289,'津巴布韦','Zimbabwe','ZW','ZWE','716',NULL,209,1,NULL),(290,'中国','China','CN','CHN','156',NULL,-999,1,NULL),(291,'刚果（布）','Republic of the Congo','CG','COG','178',NULL,211,1,NULL),(292,'刚果（金）','Democratic Republic of the Congo','CD','COD','180',NULL,212,1,NULL),(293,'莫桑比克','Mozambique','MZ','MOZ','508',NULL,213,1,NULL),(294,'根西岛','Guernsey','GG','GGY','831',NULL,214,1,NULL),(295,'冈比亚','Gambia','GM','GMB','270',NULL,215,1,NULL),(296,'北马里亚纳群岛','Northern Mariana Islands','MP','MNP','580',NULL,216,1,NULL),(297,'埃塞俄比亚','Ethiopia','ET','ETH','231',NULL,217,1,NULL),(298,'新喀里多尼亚','New Caledonia','NC','NCL','540',NULL,218,1,NULL),(299,'瓦努阿图','Vanuatu','VU','VUT','548',NULL,219,1,NULL),(300,'法属南部领地','French Southern Territories','TF','ATF','260',NULL,220,1,NULL),(301,'纽埃','Niue','NU','NIU','570',NULL,221,1,NULL),(302,'美国本土外小岛屿','United States Minor Outlying Islands','UM','UMI','581',NULL,222,1,NULL),(303,'库克群岛','Cook Islands','CK','COK','184',NULL,223,1,NULL),(304,'英国','Great Britain (United Kingdom; England)','GB','GBR','826',NULL,-9,1,NULL),(305,'特立尼达和多巴哥','Trinidad & Tobago','TT','TTO','780',NULL,225,1,NULL),(306,'圣文森特和格林纳丁斯','St. Vincent & the Grenadines','VC','VCT','670',NULL,226,1,NULL),(307,'台湾','Taiwan','TW','TWN','158',NULL,227,0,NULL),(308,'新西兰','New Zealand','NZ','NZL','554',NULL,228,1,NULL),(309,'沙特阿拉伯','Saudi Arabia','SA','SAU','682',NULL,229,1,NULL),(310,'老挝','Laos','LA','LAO','418',NULL,230,1,NULL),(311,'朝鲜 北朝鲜','North Korea','KP','PRK','408',NULL,231,1,NULL),(312,'韩国 南朝鲜','South Korea','KR','KOR','410',NULL,232,1,NULL),(313,'葡萄牙','Portugal','PT','PRT','620',NULL,233,1,NULL),(314,'吉尔吉斯斯坦','Kyrgyzstan','KG','KGZ','417',NULL,234,1,NULL),(315,'哈萨克斯坦','Kazakhstan','KZ','KAZ','398',NULL,235,1,NULL),(316,'塔吉克斯坦','Tajikistan','TJ','TJK','762',NULL,236,1,NULL),(317,'土库曼斯坦','Turkmenistan','TM','TKM','795',NULL,237,1,NULL),(318,'乌兹别克斯坦','Uzbekistan','UZ','UZB','860',NULL,238,1,NULL),(319,'圣基茨和尼维斯','St. Kitts & Nevis','KN','KNA','659',NULL,239,1,NULL),(320,'圣皮埃尔和密克隆','Saint-Pierre and Miquelon','PM','SPM','666',NULL,240,1,NULL),(321,'圣赫勒拿','St. Helena & Dependencies','SH','SHN','654',NULL,241,1,NULL),(322,'圣卢西亚','St. Lucia','LC','LCA','662',NULL,242,1,NULL),(323,'毛里求斯','Mauritius','MU','MUS','480',NULL,243,1,NULL),(324,'科特迪瓦','C?te d\'Ivoire','CI','CIV','384',NULL,244,1,NULL),(325,'肯尼亚','Kenya','KE','KEN','404',NULL,245,1,NULL),(326,'蒙古国 蒙古','Mongolia','MN','MNG','496',NULL,246,1,NULL);
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_input_analyse`
--

DROP TABLE IF EXISTS `data_input_analyse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_input_analyse` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `table_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `buss_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `input_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_input_analyse`
--

LOCK TABLES `data_input_analyse` WRITE;
/*!40000 ALTER TABLE `data_input_analyse` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_input_analyse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `database_clean`
--

DROP TABLE IF EXISTS `database_clean`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `database_clean` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `table_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `date_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `days` int NOT NULL,
  `clean_type` smallint NOT NULL,
  `extra_condition` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `last_clean_time` datetime DEFAULT NULL,
  `last_clean_counts` bigint DEFAULT NULL,
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `database_clean`
--

LOCK TABLES `database_clean` WRITE;
/*!40000 ALTER TABLE `database_clean` DISABLE KEYS */;
/*!40000 ALTER TABLE `database_clean` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diary`
--

DROP TABLE IF EXISTS `diary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `first_day` date DEFAULT NULL,
  `year` int NOT NULL,
  `words` int NOT NULL,
  `pieces` int NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diary`
--

LOCK TABLES `diary` WRITE;
/*!40000 ALTER TABLE `diary` DISABLE KEYS */;
/*!40000 ALTER TABLE `diary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_group`
--

DROP TABLE IF EXISTS `dict_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dict_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_group`
--

LOCK TABLES `dict_group` WRITE;
/*!40000 ALTER TABLE `dict_group` DISABLE KEYS */;
INSERT INTO `dict_group` VALUES (1,'用户消费标签','BUY_RECORD',1,1),(2,'食物','FOODS',1,2),(3,'食物标签','FOODS_TAG',1,3),(4,'店铺品牌','SHOP_BRAND',1,4),(5,'疾病症状','DISEASE',1,5),(6,'器官','ORGAN',1,6),(7,'商品统计分组','BUY_TYPE_GROUP_FIELD',1,7),(8,'二手类型','SECONDHAND',1,8),(9,'排序方式','SORT_TYPE',1,9),(10,'消费记录查询排序字段','BUY_RECORD_SORT_FIELD',1,10),(11,'时间维度统计类分组','CHART_DATE_GROUP',1,11),(12,'同期比对统计分组','CHART_YOY_DATE_GROUP',1,12),(13,'消费记录统计分组字段','BUY_RECORD_GROUP_FIELD',1,13),(14,'消费记录词云统计字段','BUY_RECORD_WORDCLOUD_FIELD',1,14),(15,'音乐练习分析分组字段','CHART_TIME_STAT_DATE_GROUP',1,15),(16,'时间维度统计类分组(全)','CHART_DATE_GROUP_FULL',1,16),(17,'梦想的时间查询类型','DREAM_DATE_QUERT_TYPE',1,17),(18,'梦想列表的排序方式','DREAM_ORDER_BY_TYPE',1,18),(19,'运动锻炼的排序字段','SPORT_EXERCISE_ORDER_BY_FIELD',1,19),(20,'请求支持的方式','URL_SUPPORT_METHODS',1,20),(21,'运动锻炼的同期对比分组字段','SPORT_EXERCISE_YOY_GROUP_TYPE',1,21),(22,'看病的门诊类型','TREAT_OS_NAME',1,22),(23,'药品使用方式','DRUG_USE_WAY',1,23),(24,'药品的用药单位','DRUG_EU',1,24),(25,'手术的分类','OPERATION_CATEGORY',1,25),(26,'检验报告结果','TREAT_TEST_RESULT',1,26),(27,'看病统计的统计字段','TREAT_DATE_STAT_FIELD',1,27),(28,'看病分析统计的分组字段','TREAT_ANALYSE_GROUP_FIELD',1,28),(29,'看病分析统计的统计字段','TREAT_ANALYSE_GROUP_TYPE',1,29),(30,'身体基本数据同期比对的分组字段','BBI_YOY_STAT_GROUP_TYPE',1,30),(31,'睡眠统计的X轴字段','SLEEP_STAT_X_FIELD',1,31),(32,'人生经历消费记录关联消费记录的时间段选择','LEC_CONSUME_BR_DAYS',1,32),(33,'统计图表的地图类型','STAT_MAP_TYPE',1,33),(34,'计划报告排序字段','PLAN_REPORT_SORD_FIELD',1,34),(35,'阅读分析的时间分组字段','READING_RECORD_DATE_QUERT_TYPE',1,35),(36,'阅读分析的分组字段','READING_RECORD_DATE_GROUP_TYPE',1,36),(37,'食物大类','DIET_CATEGORY_TYPE',1,37),(38,'食物分析的统计分组字段','DIET_ANALYSE_FIELD',1,38),(39,'食物比对的统计字段','DIET_COMPARE_FIELD',1,39),(40,'食物价格分析的分组字段','DIET_PRICE_ANALYSE_FIELD',1,40),(41,'食物词云的统计字段','DIET_WORD_CLOUD_FIELD',1,41),(42,'数据录入的延迟类型','DATA_INPUT_ANALYSE_COMPARE_TYPE',1,42),(43,'用户行为统计的数据分组','USER_BEHAVIOR_STAT_DATA_TYPE',1,43),(44,'积分比较类型','URP_COMPARE_TYPE',1,44),(45,'用户行为统计的数据源','USER_BEHAVIOR_DATA_SOURCE',1,45),(46,'梦想的时间统计类型','DREAM_DATE_STAT_TYPE',1,18),(47,'系统日志分析分组字段','SYSTEM_LOG_DATE_GROUP_FIELD',1,46),(48,'人生经历标签','LIFE_EXPERIENCE_TAGS',1,48),(49,'标签下拉更多天数选择','TAGS_DAYS_OPTION',1,49),(50,'时间线统计的类型','TIMELINE_STAT_TYPE',1,50),(51,'基于日期统计的图表类型','DATE_STAT_CHART_TYPE',1,51),(52,'预算周期','BUDGET_PERIOD_TYPE',1,52),(53,'人生经历词云统计字段','LIFE_EXPERIENCE_WORDCLOUD_FIELD',1,53),(54,'操作日志流水类型','OPERATION_LOG_FLOW_TYPE',1,54),(55,'总体全局统计分组','CHART_OVERALL_GROUP',1,55),(56,'药品的单位','DRUG_UNIT',1,24),(57,'消费记录预期作废时间选择','BUY_RECORD_DELETE_DATE_PERIOD',1,56),(58,'消费记录匹配类型','BUY_RECORD_MATCH_LOG_COMPARE_TYPE',1,58),(59,'国家列表','COUNTRY',1,59),(60,'图书语言','BOOK_LANGUAGE',1,60);
/*!40000 ALTER TABLE `dict_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_item`
--

DROP TABLE IF EXISTS `dict_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dict_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` bigint NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=297 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_item`
--

LOCK TABLES `dict_item` WRITE;
/*!40000 ALTER TABLE `dict_item` DISABLE KEYS */;
INSERT INTO `dict_item` VALUES (1,1,'回家',NULL,1,1),(2,1,'春节',NULL,1,2),(3,1,'苹果产品',NULL,1,3),(4,2,'米饭',NULL,1,1),(5,2,'猪肉',NULL,1,2),(6,2,'青菜',NULL,1,3),(7,3,'炒年糕',NULL,1,1),(8,3,'春节',NULL,1,2),(9,4,'高祖生煎',NULL,1,1),(10,5,'腹胀',NULL,1,1),(11,5,'流鼻涕',NULL,1,2),(12,5,'牙痛',NULL,1,3),(13,6,'肠胃',NULL,1,1),(14,6,'牙齿',NULL,1,2),(15,6,'心脏',NULL,1,3),(16,6,'咽喉',NULL,1,4),(17,5,'头痛',NULL,1,4),(18,7,'商品名称','goods_name',0,0),(19,7,'商品类型','goods_type_id',1,1),(20,7,'购买来源','buy_type_id',1,2),(21,7,'商品子类','sub_goods_type_id',1,3),(22,7,'支付方式','payment',1,4),(23,7,'店铺名称','shop_name',1,5),(24,7,'品牌','brand',1,6),(25,8,'二手','true',1,0),(26,8,'非二手','false',1,1),(27,9,'倒序','desc',1,0),(28,9,'顺序','asc',1,1),(29,10,'购买时间','buyDate',1,0),(30,10,'总价','totalPrice',1,1),(31,10,'商品价格','price',1,2),(32,10,'运费','shipment',1,3),(33,11,'月','MONTH',1,0),(34,11,'天','DAY',1,1),(35,11,'天(日历)','DAYCALENDAR',1,2),(36,11,'周','WEEK',1,3),(37,11,'年','YEAR',1,4),(38,11,'年月','YEARMONTH',1,5),(39,11,'天的序号','DAYOFMONTH',1,6),(40,11,'星期的序号','DAYOFWEEK',1,7),(41,12,'月','MONTH',1,0),(42,12,'周','WEEK',1,1),(43,12,'天','DAY',1,2),(44,13,'花费','TOTALPRICE',1,0),(45,13,'次数','COUNT',1,1),(46,13,'运费','SHIPMENT',1,2),(47,14,'商品名称','goodsName',1,0),(48,14,'店铺','shopName',1,1),(49,14,'品牌','brand',1,2),(50,15,'小时点','HOUR',1,0),(51,15,'练习时长','MINUTE',1,1),(52,15,'天的序号','DAYOFMONTH',1,2),(53,15,'星期的序号','DAYOFWEEK',1,3),(54,16,'小时点','HOUR',1,0),(55,16,'时分','HOURMINUTE',1,1),(56,16,'分钟','MINUTE',1,2),(57,16,'天的序号','DAYOFMONTH',1,3),(58,16,'星期的序号','DAYOFWEEK',1,4),(59,16,'月','MONTH',1,5),(60,16,'天','DAY',1,6),(61,16,'天(日历)','DAYCALENDAR',1,7),(62,16,'周','WEEK',1,8),(63,16,'年','YEAR',1,9),(64,16,'年月','YEARMONTH',1,10),(65,17,'期望实现日期','proposedDate',1,0),(66,17,'完成日期','finishedDate',1,1),(67,17,'最晚截止日期','deadline',1,2),(68,17,'创建日期','createdTime',1,3),(69,18,'期望实现日期','proposedDate',1,0),(70,18,'创建时间','createdTime',1,1),(71,18,'状态','status',1,2),(72,18,'困难程度','difficulty',1,3),(73,18,'重要程度','importantLevel',1,4),(74,18,'进度','rate',1,5),(75,18,'实现日期','finishedDate',1,6),(76,18,'最晚截止日期','deadline',1,7),(77,19,'锻炼日期','EXERCISEDATE',1,0),(78,19,'公里数','KILOMETRES',1,1),(79,19,'锻炼时间','MINUTES',1,2),(80,19,'平均速度','SPEED',1,3),(81,19,'最佳速度','MAXSPEED',1,4),(82,19,'平均配速','PACE',1,5),(83,19,'最佳配速','MAXPACE',1,6),(84,19,'最大心率','MAXHEARTRATE',1,7),(85,19,'平均心率','AVERAGEHEART',1,8),(86,20,'POST','POST',1,0),(87,20,'GET','GET',1,1),(88,20,'GET,POST','GET,POST',1,2),(89,21,'次数','COUNT',1,0),(90,21,'锻炼值','KILOMETRES',1,1),(91,21,'锻炼时间','MINUTES',1,2),(92,21,'平均速度','SPEED',1,3),(93,21,'配速','PACE',1,4),(94,21,'最大心率','MAXHEARTRATE',1,5),(95,21,'平均心率','AVERAGEHEART',1,6),(96,22,'普通','普通',1,0),(97,22,'专家','专家',1,1),(98,22,'急诊','急诊',1,2),(99,23,'餐前','餐前',1,0),(100,23,'餐时','餐时',1,1),(101,23,'餐后','餐后',1,2),(102,23,'早晚','早晚',1,3),(103,23,'外敷','外敷',1,4),(104,24,'片','片',1,0),(105,24,'粒','粒',1,1),(106,24,'支','支',1,2),(107,25,'B超','B超',1,0),(108,25,'X光','X光',1,1),(109,25,'CT','CT',1,2),(110,25,'内镜检查','内镜检查',1,3),(111,25,'血液检查','血液检查',1,4),(112,25,'尿液检查','尿液检查',1,5),(113,25,'粪便检查','粪便检查',1,6),(114,25,'口腔检查','口腔检查',1,7),(115,25,'常规检查','常规检查',1,8),(116,25,'体检','体检',1,9),(117,25,'其他','其他',1,10),(118,26,'过低','-1',1,0),(119,26,'正常','0',1,1),(120,26,'过高','1',1,2),(121,26,'确诊疾病','2',1,3),(122,27,'总费用','total_fee',1,0),(123,27,'医保花费','medical_insurance_paid_fee',1,1),(124,27,'个人支付费用','personal_paid_fee',1,2),(125,27,'手术费用','operation_fee',1,3),(126,27,'药费','drug_fee',1,4),(127,28,'确诊疾病','diagnosed_disease',1,0),(128,28,'疾病症状','disease',1,1),(129,28,'器官','organ',1,2),(130,28,'医院','hospital',1,3),(131,28,'科室','department',1,4),(132,28,'标签','tags',1,5),(133,28,'门诊类型','os_name',1,6),(134,29,'次数','COUNT',1,0),(135,29,'费用','TOTALPRICE',1,1),(136,30,'体重','WEIGHT',1,0),(137,30,'身高','HEIGHT',1,1),(138,30,'BMI指数','BMI',1,2),(139,30,'次数','COUNT',1,3),(140,31,'星期的序号','DAYOFWEEK',1,0),(141,31,'天的序号','DAYOFMONTH',1,1),(142,31,'月','MONTH',1,2),(143,31,'天','DAY',1,3),(144,31,'周','WEEK',1,4),(145,31,'年','YEAR',1,5),(146,31,'年月','YEARMONTH',1,6),(147,32,'当天','0',1,0),(148,32,'三天内','3',1,1),(149,32,'一周内','7',1,2),(150,32,'一个月内','30',1,3),(151,32,'一年内','365',1,4),(152,33,'中国','CHINA',1,0),(153,33,'世界','WORLD',1,1),(154,33,'地点(中国)','LOCATION',1,2),(155,34,'业务日期','bussStatDate',1,0),(156,34,'统计时间','createdTime',1,1),(157,34,'业务日期索引','bussDay',1,2),(158,35,'完成日期','finished_date',1,0),(159,35,'开始日期','begin_date',1,1),(160,35,'期望完成日期','proposed_date',1,2),(161,36,'图书分类','BOOKCATEGORY',1,0),(162,36,'书籍类型','BOOKTYPE',1,1),(163,36,'语言','LANGUAGE',1,2),(164,36,'评分','SCORE',1,3),(165,36,'状态','STATUS',1,4),(166,36,'出版年份','PUBLISHEDYEAR',1,5),(167,36,'出版社','PRESS',1,6),(168,36,'国家','COUNTRY',1,7),(169,36,'完成天数','PERIOD',1,8),(170,36,'花费时间','TIME',1,9),(171,37,'素','素',1,0),(172,37,'荤','荤',1,1),(173,38,'食物','FOODS',1,0),(174,38,'标签','TAGS',1,1),(175,38,'店铺/品牌','SHOP',1,2),(176,38,'食物来源','DIET_SOURCE',1,3),(177,38,'食物类型','FOOD_TYPE',1,4),(178,38,'餐次','DIET_TYPE',1,5),(179,38,'食品小类','CLASS_NAME',1,6),(180,38,'食品大类','TYPE',1,7),(181,39,'总价格','TOTAL_PRICE',1,0),(182,39,'平均价格','AVG_PRICE',1,1),(183,39,'平均评分','AVG_SCORE',1,2),(184,39,'次数','COUNTS',1,3),(185,40,'月','MONTH',1,0),(186,40,'天','DAY',1,1),(187,40,'价格区间','DAYCALENDAR',1,2),(188,40,'周','WEEK',1,3),(189,40,'年','YEAR',1,4),(190,40,'食物来源','DIET_SOURCE',1,5),(191,40,'食物类型','FOOD_TYPE',1,6),(192,40,'餐次','DIET_TYPE',1,7),(193,40,'年月','YEARMONTH',1,8),(194,40,'天的序号','DAYOFMONTH',1,9),(195,40,'星期的序号','DAYOFWEEK',1,10),(196,41,'食物','foods',1,0),(197,41,'标签','tags',1,1),(198,41,'店铺/品牌','shop',1,2),(199,42,'超前','n<0',1,0),(200,42,'当天','n=0',1,1),(201,42,'超过一天','n>0',1,2),(202,42,'1-3天','1<=n<=3',1,3),(203,42,'3天到一个星期内','3<n<=7',1,4),(204,42,'一星期内','n<=7',1,5),(205,42,'一个星期到一个月内','7<n<=30',1,6),(206,42,'超过一个月','n>30',1,7),(207,43,'原始值','',1,0),(208,43,'天(以月的维度)','DAYOFMONTH',1,1),(209,43,'星期的序号','DAYOFWEEK',1,2),(210,43,'天(以年的维度)','DAY',1,3),(211,44,'大于','GT',1,0),(212,44,'大于等于','GTE',1,1),(213,44,'小于','LT',1,2),(214,44,'小于等于','LTE',1,3),(215,44,'等于','EQ',1,4),(216,45,'行为数据','BEHAVIOR_DATA',1,0),(217,45,'操作数据','OPERATION_DATA',1,1),(218,46,'期望实现日期','proposed_date',1,0),(219,46,'完成日期','finished_date',1,1),(220,46,'最晚截止日期','deadline',1,2),(221,46,'创建日期','created_time',1,3),(222,47,'异常类型','exception_class_name',1,0),(223,47,'错误代码','error_code',1,1),(224,47,'异常级别','log_level',1,2),(225,48,'一日户外','一日户外',1,0),(226,48,'两日户外','两日户外',1,1),(227,48,'周末游','周末游',1,2),(228,48,'短线','短线',1,3),(229,48,'长线','长线',1,4),(230,48,'骑行','骑行',1,5),(231,48,'徒步','徒步',1,6),(232,48,'自驾','自驾',1,7),(233,48,'公益','公益',1,8),(234,48,'节假日','节假日',1,2),(235,48,'古镇','古镇',1,9),(236,48,'海岛','海岛',1,10),(237,48,'高原','高原',1,11),(238,48,'古村','古村',1,12),(239,48,'背包','背包',1,13),(240,33,'经历(中国)','LC_NAME',1,3),(241,49,'六个月','180',1,0),(242,49,'一年','365',1,1),(243,49,'三年','1095',1,2),(244,49,'五年','1825',1,3),(245,49,'十年','3650',1,4),(246,49,'全部','36500',1,5),(247,50,'按比例','RATE',1,0),(248,50,'按原始值','VALUE',1,1),(249,51,'混合图形','MIX_LINE_BAR',1,0),(250,51,'柱状图','BAR',1,1),(251,51,'折线图','LINE',1,2),(252,11,'时分','HOURMINUTE',1,8),(253,52,'单次','ONCE',1,0),(254,52,'每天','DAILY',1,1),(255,52,'每月','MONTHLY',1,2),(256,52,'每年','YEARLY',1,3),(257,48,'国内','国内',1,14),(258,48,'国外','国外',1,15),(259,48,'跟团','跟团',1,16),(260,48,'自由行','自由行',1,17),(261,53,'标签','tags',1,0),(262,53,'名称','name',1,1),(263,53,'城市','city',1,2),(264,14,'标签','keywords',1,3),(265,14,'SKU','skuInfo',1,4),(266,54,'新增','CREATE',1,0),(267,54,'修改','EDIT',1,1),(268,54,'删除','DELETE',1,2),(269,55,'月','MONTH',1,0),(270,55,'天的序号','DAYOFMONTH',1,1),(271,55,'周','WEEK',1,2),(272,55,'年','YEAR',1,3),(273,55,'星期的序号','DAYOFWEEK',1,4),(274,55,'天','DAY',1,5),(275,55,'小时点','HOUR',1,6),(276,31,'天（年）','DAYCALENDAR',1,7),(277,56,'片','片',1,0),(278,56,'瓶','瓶',1,1),(279,56,'盒','盒',1,2),(280,56,'颗','颗',1,3),(281,23,'睡前','睡前',1,5),(282,57,'一个月','30',1,0),(283,57,'三个月','90',1,1),(284,57,'半年','180',1,2),(285,57,'一年','365',1,3),(286,57,'五年','1825',1,4),(287,57,'十年','3650',1,5),(288,58,'历史商品','NOT_NULL',1,0),(289,58,'商品类型','NULL',1,1),(290,59,'中国','中国',1,0),(291,59,'尼泊尔','尼泊尔',1,1),(292,59,'美国','美国',1,2),(293,60,'中文','中文',1,0),(294,60,'英文','英文',1,1),(295,60,'法文','法文',1,2),(296,36,'图书来源','SOURCE',1,10);
/*!40000 ALTER TABLE `dict_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diet`
--

DROP TABLE IF EXISTS `diet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diet` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `diet_type` smallint NOT NULL,
  `diet_source` smallint NOT NULL,
  `foods` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `food_type` smallint NOT NULL DEFAULT '0',
  `occur_time` datetime NOT NULL,
  `shop` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `location` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tags` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `price` decimal(9,2) NOT NULL DEFAULT '0.00',
  `score` int DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diet`
--

LOCK TABLES `diet` WRITE;
/*!40000 ALTER TABLE `diet` DISABLE KEYS */;
/*!40000 ALTER TABLE `diet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diet_category`
--

DROP TABLE IF EXISTS `diet_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diet_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `keywords` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `class_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diet_category`
--

LOCK TABLES `diet_category` WRITE;
/*!40000 ALTER TABLE `diet_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `diet_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diet_variety_log`
--

DROP TABLE IF EXISTS `diet_variety_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diet_variety_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `diet_type` smallint DEFAULT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `variety` decimal(9,2) NOT NULL,
  `remark` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diet_variety_log`
--

LOCK TABLES `diet_variety_log` WRITE;
/*!40000 ALTER TABLE `diet_variety_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `diet_variety_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `district`
--

DROP TABLE IF EXISTS `district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `district` (
  `id` int NOT NULL DEFAULT '0' COMMENT 'ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'NAME',
  `map_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `city_id` int DEFAULT NULL COMMENT 'CITY',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='区';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `district`
--

LOCK TABLES `district` WRITE;
/*!40000 ALTER TABLE `district` DISABLE KEYS */;
INSERT INTO `district` VALUES (1,'东城区','东城区',1),(2,'西城区','西城区',1),(3,'崇文区','崇文区',1),(4,'宣武区','宣武区',1),(5,'朝阳区','朝阳区',1),(6,'丰台区','丰台区',1),(7,'石景山区','石景山区',1),(8,'海淀区','海淀区',1),(9,'门头沟区','门头沟区',1),(10,'房山区','房山区',1),(11,'通州区','通州区',1),(12,'顺义区','顺义区',1),(13,'昌平区','昌平区',1),(14,'大兴区','大兴区',1),(15,'怀柔区','怀柔区',1),(16,'平谷区','平谷区',1),(17,'密云县','密云县',1),(18,'延庆县','延庆县',1),(19,'和平区','和平区',2),(20,'河东区','河东区',2),(21,'河西区','河西区',2),(22,'南开区','南开区',2),(23,'河北区','河北区',2),(24,'红桥区','红桥区',2),(25,'塘沽区','塘沽区',2),(26,'汉沽区','汉沽区',2),(27,'大港区','大港区',2),(28,'东丽区','东丽区',2),(29,'西青区','西青区',2),(30,'津南区','津南区',2),(31,'北辰区','北辰区',2),(32,'武清区','武清区',2),(33,'宝坻区','宝坻区',2),(34,'宁河县','宁河县',2),(35,'静海县','静海县',2),(36,'蓟县','蓟县',2),(37,'长安区','长安区',3),(38,'桥东区','桥东区',3),(39,'桥西区','桥西区',3),(40,'新华区','新华区',3),(41,'井陉矿区','井陉矿区',3),(42,'裕华区','裕华区',3),(43,'井陉县','井陉县',3),(44,'正定县','正定县',3),(45,'栾城县','栾城县',3),(46,'行唐县','行唐县',3),(47,'灵寿县','灵寿县',3),(48,'高邑县','高邑县',3),(49,'深泽县','深泽县',3),(50,'赞皇县','赞皇县',3),(51,'无极县','无极县',3),(52,'平山县','平山县',3),(53,'元氏县','元氏县',3),(54,'赵县','赵县',3),(55,'辛集市','辛集市',3),(56,'藁城市','藁城市',3),(57,'晋州市','晋州市',3),(58,'新乐市','新乐市',3),(59,'鹿泉市','鹿泉市',3),(60,'路南区','路南区',4),(61,'路北区','路北区',4),(62,'古冶区','古冶区',4),(63,'开平区','开平区',4),(64,'丰南区','丰南区',4),(65,'丰润区','丰润区',4),(66,'滦县','滦县',4),(67,'滦南县','滦南县',4),(68,'乐亭县','乐亭县',4),(69,'迁西县','迁西县',4),(70,'玉田县','玉田县',4),(71,'唐海县','唐海县',4),(72,'遵化市','遵化市',4),(73,'迁安市','迁安市',4),(74,'海港区','海港区',5),(75,'山海关区','山海关区',5),(76,'北戴河区','北戴河区',5),(77,'青龙满族自治县','青龙满族自治县',5),(78,'昌黎县','昌黎县',5),(79,'抚宁县','抚宁县',5),(80,'卢龙县','卢龙县',5),(81,'邯山区','邯山区',6),(82,'丛台区','丛台区',6),(83,'复兴区','复兴区',6),(84,'峰峰矿区','峰峰矿区',6),(85,'邯郸县','邯郸县',6),(86,'临漳县','临漳县',6),(87,'成安县','成安县',6),(88,'大名县','大名县',6),(89,'涉县','涉县',6),(90,'磁县','磁县',6),(91,'肥乡县','肥乡县',6),(92,'永年县','永年县',6),(93,'邱县','邱县',6),(94,'鸡泽县','鸡泽县',6),(95,'广平县','广平县',6),(96,'馆陶县','馆陶县',6),(97,'魏县','魏县',6),(98,'曲周县','曲周县',6),(99,'武安市','武安市',6),(100,'桥东区','桥东区',7),(102,'邢台县','邢台县',7),(103,'临城县','临城县',7),(104,'内丘县','内丘县',7),(105,'柏乡县','柏乡县',7),(106,'隆尧县','隆尧县',7),(107,'任县','任县',7),(108,'南和县','南和县',7),(109,'宁晋县','宁晋县',7),(110,'巨鹿县','巨鹿县',7),(111,'新河县','新河县',7),(112,'广宗县','广宗县',7),(113,'平乡县','平乡县',7),(114,'威县','威县',7),(115,'清河县','清河县',7),(116,'临西县','临西县',7),(117,'南宫市','南宫市',7),(118,'沙河市','沙河市',7),(119,'新市区','新市区',8),(120,'北市区','北市区',8),(121,'南市区','南市区',8),(122,'满城县','满城县',8),(123,'清苑县','清苑县',8),(124,'涞水县','涞水县',8),(125,'阜平县','阜平县',8),(126,'徐水县','徐水县',8),(127,'定兴县','定兴县',8),(128,'唐县','唐县',8),(129,'高阳县','高阳县',8),(130,'容城县','容城县',8),(131,'涞源县','涞源县',8),(132,'望都县','望都县',8),(133,'安新县','安新县',8),(134,'易县','易县',8),(135,'曲阳县','曲阳县',8),(136,'蠡县','蠡县',8),(137,'顺平县','顺平县',8),(138,'博野县','博野县',8),(139,'雄县','雄县',8),(140,'涿州市','涿州市',8),(141,'定州市','定州市',8),(142,'安国市','安国市',8),(143,'高碑店市','高碑店市',8),(144,'桥东区','桥东区',9),(145,'桥西区','桥西区',9),(146,'宣化区','宣化区',9),(147,'下花园区','下花园区',9),(148,'宣化县','宣化县',9),(149,'张北县','张北县',9),(150,'康保县','康保县',9),(151,'沽源县','沽源县',9),(152,'尚义县','尚义县',9),(153,'蔚县','蔚县',9),(154,'阳原县','阳原县',9),(155,'怀安县','怀安县',9),(156,'万全县','万全县',9),(157,'怀来县','怀来县',9),(158,'涿鹿县','涿鹿县',9),(159,'赤城县','赤城县',9),(160,'崇礼县','崇礼县',9),(161,'双桥区','双桥区',10),(162,'双滦区','双滦区',10),(163,'鹰手营子矿区','鹰手营子矿区',10),(164,'承德县','承德县',10),(165,'兴隆县','兴隆县',10),(166,'平泉县','平泉县',10),(167,'滦平县','滦平县',10),(168,'隆化县','隆化县',10),(169,'丰宁满族自治县','丰宁满族自治县',10),(170,'宽城满族自治县','宽城满族自治县',10),(171,'围场满族蒙古族自治县','围场满族蒙古族自治县',10),(172,'新华区','新华区',11),(173,'运河区','运河区',11),(174,'沧县','沧县',11),(175,'青县','青县',11),(176,'东光县','东光县',11),(177,'海兴县','海兴县',11),(178,'盐山县','盐山县',11),(179,'肃宁县','肃宁县',11),(180,'南皮县','南皮县',11),(181,'吴桥县','吴桥县',11),(182,'献县','献县',11),(183,'孟村回族自治县','孟村回族自治县',11),(184,'泊头市','泊头市',11),(185,'任丘市','任丘市',11),(186,'黄骅市','黄骅市',11),(187,'河间市','河间市',11),(188,'安次区','安次区',12),(189,'广阳区','广阳区',12),(190,'固安县','固安县',12),(191,'永清县','永清县',12),(192,'香河县','香河县',12),(193,'大城县','大城县',12),(194,'文安县','文安县',12),(195,'大厂回族自治县','大厂回族自治县',12),(196,'霸州市','霸州市',12),(197,'三河市','三河市',12),(198,'桃城区','桃城区',13),(199,'枣强县','枣强县',13),(200,'武邑县','武邑县',13),(201,'武强县','武强县',13),(202,'饶阳县','饶阳县',13),(203,'安平县','安平县',13),(204,'故城县','故城县',13),(205,'景县','景县',13),(206,'阜城县','阜城县',13),(207,'冀州市','冀州市',13),(208,'深州市','深州市',13),(209,'小店区','小店区',14),(210,'迎泽区','迎泽区',14),(211,'杏花岭区','杏花岭区',14),(212,'尖草坪区','尖草坪区',14),(213,'万柏林区','万柏林区',14),(214,'晋源区','晋源区',14),(215,'清徐县','清徐县',14),(216,'阳曲县','阳曲县',14),(217,'娄烦县','娄烦县',14),(218,'古交市','古交市',14),(219,'城区','城区',15),(220,'矿区','矿区',15),(221,'南郊区','南郊区',15),(222,'新荣区','新荣区',15),(223,'阳高县','阳高县',15),(224,'天镇县','天镇县',15),(225,'广灵县','广灵县',15),(226,'灵丘县','灵丘县',15),(227,'浑源县','浑源县',15),(228,'左云县','左云县',15),(229,'大同县','大同县',15),(230,'城区','城区',16),(231,'矿区','矿区',16),(232,'郊区','郊区',16),(233,'平定县','平定县',16),(234,'盂县','盂县',16),(235,'城区','城区',17),(236,'郊区','郊区',17),(237,'长治县','长治县',17),(238,'襄垣县','襄垣县',17),(239,'屯留县','屯留县',17),(240,'平顺县','平顺县',17),(241,'黎城县','黎城县',17),(242,'壶关县','壶关县',17),(243,'长子县','长子县',17),(244,'武乡县','武乡县',17),(245,'沁县','沁县',17),(246,'沁源县','沁源县',17),(247,'潞城市','潞城市',17),(248,'城区','城区',18),(249,'沁水县','沁水县',18),(250,'阳城县','阳城县',18),(251,'陵川县','陵川县',18),(252,'泽州县','泽州县',18),(253,'高平市','高平市',18),(254,'朔城区','朔城区',19),(255,'平鲁区','平鲁区',19),(256,'山阴县','山阴县',19),(257,'应县','应县',19),(258,'右玉县','右玉县',19),(259,'怀仁县','怀仁县',19),(260,'榆次区','榆次区',20),(261,'榆社县','榆社县',20),(262,'左权县','左权县',20),(263,'和顺县','和顺县',20),(264,'昔阳县','昔阳县',20),(265,'寿阳县','寿阳县',20),(266,'太谷县','太谷县',20),(267,'祁县','祁县',20),(268,'平遥县','平遥县',20),(269,'灵石县','灵石县',20),(270,'介休市','介休市',20),(271,'盐湖区','盐湖区',21),(272,'临猗县','临猗县',21),(273,'万荣县','万荣县',21),(274,'闻喜县','闻喜县',21),(275,'稷山县','稷山县',21),(276,'新绛县','新绛县',21),(277,'绛县','绛县',21),(278,'垣曲县','垣曲县',21),(279,'夏县','夏县',21),(280,'平陆县','平陆县',21),(281,'芮城县','芮城县',21),(282,'永济市','永济市',21),(283,'河津市','河津市',21),(284,'忻府区','忻府区',22),(285,'定襄县','定襄县',22),(286,'五台县','五台县',22),(287,'代县','代县',22),(288,'繁峙县','繁峙县',22),(289,'宁武县','宁武县',22),(290,'静乐县','静乐县',22),(291,'神池县','神池县',22),(292,'五寨县','五寨县',22),(293,'岢岚县','岢岚县',22),(294,'河曲县','河曲县',22),(295,'保德县','保德县',22),(296,'偏关县','偏关县',22),(297,'原平市','原平市',22),(298,'尧都区','尧都区',23),(299,'曲沃县','曲沃县',23),(300,'翼城县','翼城县',23),(301,'襄汾县','襄汾县',23),(302,'洪洞县','洪洞县',23),(304,'安泽县','安泽县',23),(305,'浮山县','浮山县',23),(306,'吉县','吉县',23),(307,'乡宁县','乡宁县',23),(308,'大宁县','大宁县',23),(309,'隰县','隰县',23),(310,'永和县','永和县',23),(311,'蒲县','蒲县',23),(312,'汾西县','汾西县',23),(313,'侯马市','侯马市',23),(314,'霍州市','霍州市',23),(315,'离石区','离石区',24),(316,'文水县','文水县',24),(317,'交城县','交城县',24),(318,'兴县','兴县',24),(319,'临县','临县',24),(320,'柳林县','柳林县',24),(321,'石楼县','石楼县',24),(322,'岚县','岚县',24),(323,'方山县','方山县',24),(324,'中阳县','中阳县',24),(325,'交口县','交口县',24),(326,'孝义市','孝义市',24),(327,'汾阳市','汾阳市',24),(328,'新城区','新城区',25),(329,'回民区','回民区',25),(330,'玉泉区','玉泉区',25),(331,'赛罕区','赛罕区',25),(332,'土默特左旗','土默特左旗',25),(333,'托克托县','托克托县',25),(334,'和林格尔县','和林格尔县',25),(335,'清水河县','清水河县',25),(336,'武川县','武川县',25),(337,'东河区','东河区',26),(338,'昆都仑区','昆都仑区',26),(339,'青山区','青山区',26),(340,'石拐区','石拐区',26),(341,'白云矿区','白云矿区',26),(342,'九原区','九原区',26),(343,'土默特右旗','土默特右旗',26),(344,'固阳县','固阳县',26),(345,'达尔罕茂明安联合旗','达尔罕茂明安联合旗',26),(346,'海勃湾区','海勃湾区',27),(347,'海南区','海南区',27),(348,'乌达区','乌达区',27),(349,'红山区','红山区',28),(350,'元宝山区','元宝山区',28),(351,'松山区','松山区',28),(352,'阿鲁科尔沁旗','阿鲁科尔沁旗',28),(353,'巴林左旗','巴林左旗',28),(354,'巴林右旗','巴林右旗',28),(355,'林西县','林西县',28),(356,'克什克腾旗','克什克腾旗',28),(357,'翁牛特旗','翁牛特旗',28),(358,'喀喇沁旗','喀喇沁旗',28),(359,'宁城县','宁城县',28),(360,'敖汉旗','敖汉旗',28),(361,'科尔沁区','科尔沁区',29),(362,'科尔沁左翼中旗','科尔沁左翼中旗',29),(363,'科尔沁左翼后旗','科尔沁左翼后旗',29),(364,'开鲁县','开鲁县',29),(365,'库伦旗','库伦旗',29),(366,'奈曼旗','奈曼旗',29),(367,'扎鲁特旗','扎鲁特旗',29),(368,'霍林郭勒市','霍林郭勒市',29),(369,'东胜区','东胜区',30),(370,'达拉特旗','达拉特旗',30),(371,'准格尔旗','准格尔旗',30),(372,'鄂托克前旗','鄂托克前旗',30),(373,'鄂托克旗','鄂托克旗',30),(374,'杭锦旗','杭锦旗',30),(375,'乌审旗','乌审旗',30),(376,'伊金霍洛旗','伊金霍洛旗',30),(377,'海拉尔区','海拉尔区',31),(378,'阿荣旗','阿荣旗',31),(379,'莫力达瓦达斡尔族自治旗','莫力达瓦达斡尔族自治旗',31),(380,'鄂伦春自治旗','鄂伦春自治旗',31),(381,'鄂温克族自治旗','鄂温克族自治旗',31),(382,'陈巴尔虎旗','陈巴尔虎旗',31),(383,'新巴尔虎左旗','新巴尔虎左旗',31),(384,'新巴尔虎右旗','新巴尔虎右旗',31),(385,'满洲里市','满洲里市',31),(386,'牙克石市','牙克石市',31),(387,'扎兰屯市','扎兰屯市',31),(388,'额尔古纳市','额尔古纳市',31),(389,'根河市','根河市',31),(390,'临河区','临河区',32),(391,'五原县','五原县',32),(392,'磴口县','磴口县',32),(393,'乌拉特前旗','乌拉特前旗',32),(394,'乌拉特中旗','乌拉特中旗',32),(395,'乌拉特后旗','乌拉特后旗',32),(396,'杭锦后旗','杭锦后旗',32),(397,'集宁区','集宁区',33),(398,'卓资县','卓资县',33),(399,'化德县','化德县',33),(400,'商都县','商都县',33),(401,'兴和县','兴和县',33),(402,'凉城县','凉城县',33),(403,'察哈尔右翼前旗','察哈尔右翼前旗',33),(405,'察哈尔右翼后旗','察哈尔右翼后旗',33),(406,'四子王旗','四子王旗',33),(407,'丰镇市','丰镇市',33),(408,'乌兰浩特市','乌兰浩特市',34),(409,'阿尔山市','阿尔山市',34),(410,'科尔沁右翼前旗','科尔沁右翼前旗',34),(411,'科尔沁右翼中旗','科尔沁右翼中旗',34),(412,'扎赉特旗','扎赉特旗',34),(413,'突泉县','突泉县',34),(414,'二连浩特市','二连浩特市',35),(415,'锡林浩特市','锡林浩特市',35),(416,'阿巴嘎旗','阿巴嘎旗',35),(417,'苏尼特左旗','苏尼特左旗',35),(418,'苏尼特右旗','苏尼特右旗',35),(419,'东乌珠穆沁旗','东乌珠穆沁旗',35),(420,'西乌珠穆沁旗','西乌珠穆沁旗',35),(421,'太仆寺旗','太仆寺旗',35),(422,'镶黄旗','镶黄旗',35),(423,'正镶白旗','正镶白旗',35),(424,'正蓝旗','正蓝旗',35),(425,'多伦县','多伦县',35),(426,'阿拉善左旗','阿拉善左旗',36),(427,'阿拉善右旗','阿拉善右旗',36),(428,'额济纳旗','额济纳旗',36),(429,'和平区','和平区',37),(430,'沈河区','沈河区',37),(431,'大东区','大东区',37),(432,'皇姑区','皇姑区',37),(433,'铁西区','铁西区',37),(434,'苏家屯区','苏家屯区',37),(435,'东陵区','东陵区',37),(436,'新城子区','新城子区',37),(437,'于洪区','于洪区',37),(438,'辽中县','辽中县',37),(439,'康平县','康平县',37),(440,'法库县','法库县',37),(441,'新民市','新民市',37),(442,'中山区','中山区',38),(443,'西岗区','西岗区',38),(444,'沙河口区','沙河口区',38),(445,'甘井子区','甘井子区',38),(446,'旅顺口区','旅顺口区',38),(447,'金州区','金州区',38),(448,'长海县','长海县',38),(449,'瓦房店市','瓦房店市',38),(450,'普兰店市','普兰店市',38),(451,'庄河市','庄河市',38),(452,'铁东区','铁东区',39),(453,'铁西区','铁西区',39),(454,'立山区','立山区',39),(455,'千山区','千山区',39),(456,'台安县','台安县',39),(457,'岫岩满族自治县','岫岩满族自治县',39),(458,'海城市','海城市',39),(459,'新抚区','新抚区',40),(460,'东洲区','东洲区',40),(461,'望花区','望花区',40),(462,'顺城区','顺城区',40),(463,'抚顺县','抚顺县',40),(464,'新宾满族自治县','新宾满族自治县',40),(465,'清原满族自治县','清原满族自治县',40),(466,'平山区','平山区',41),(467,'溪湖区','溪湖区',41),(468,'明山区','明山区',41),(469,'南芬区','南芬区',41),(470,'本溪满族自治县','本溪满族自治县',41),(471,'桓仁满族自治县','桓仁满族自治县',41),(472,'元宝区','元宝区',42),(473,'振兴区','振兴区',42),(474,'振安区','振安区',42),(475,'宽甸满族自治县','宽甸满族自治县',42),(476,'东港市','东港市',42),(477,'凤城市','凤城市',42),(478,'古塔区','古塔区',43),(479,'凌河区','凌河区',43),(480,'太和区','太和区',43),(481,'黑山县','黑山县',43),(482,'义县','义县',43),(483,'凌海市','凌海市',43),(484,'北宁市','北宁市',43),(485,'站前区','站前区',44),(486,'西市区','西市区',44),(487,'鲅鱼圈区','鲅鱼圈区',44),(488,'老边区','老边区',44),(489,'盖州市','盖州市',44),(490,'大石桥市','大石桥市',44),(491,'海州区','海州区',45),(492,'新邱区','新邱区',45),(493,'太平区','太平区',45),(494,'清河门区','清河门区',45),(495,'细河区','细河区',45),(496,'阜新蒙古族自治县','阜新蒙古族自治县',45),(497,'彰武县','彰武县',45),(498,'白塔区','白塔区',46),(499,'文圣区','文圣区',46),(500,'宏伟区','宏伟区',46),(501,'弓长岭区','弓长岭区',46),(502,'太子河区','太子河区',46),(503,'辽阳县','辽阳县',46),(504,'灯塔市','灯塔市',46),(506,'兴隆台区','兴隆台区',47),(507,'大洼县','大洼县',47),(508,'盘山县','盘山县',47),(509,'银州区','银州区',48),(510,'清河区','清河区',48),(511,'铁岭县','铁岭县',48),(512,'西丰县','西丰县',48),(513,'昌图县','昌图县',48),(514,'调兵山市','调兵山市',48),(515,'开原市','开原市',48),(516,'双塔区','双塔区',49),(517,'龙城区','龙城区',49),(518,'朝阳县','朝阳县',49),(519,'建平县','建平县',49),(520,'喀喇沁左翼蒙古族自治县','喀喇沁左翼蒙古族自治县',49),(521,'北票市','北票市',49),(522,'凌源市','凌源市',49),(523,'连山区','连山区',50),(524,'龙港区','龙港区',50),(525,'南票区','南票区',50),(526,'绥中县','绥中县',50),(527,'建昌县','建昌县',50),(528,'兴城市','兴城市',50),(529,'南关区','南关区',51),(530,'宽城区','宽城区',51),(531,'朝阳区','朝阳区',51),(532,'二道区','二道区',51),(533,'绿园区','绿园区',51),(534,'双阳区','双阳区',51),(535,'农安县','农安县',51),(536,'九台市','九台市',51),(537,'榆树市','榆树市',51),(538,'德惠市','德惠市',51),(539,'昌邑区','昌邑区',52),(540,'龙潭区','龙潭区',52),(541,'船营区','船营区',52),(542,'丰满区','丰满区',52),(543,'永吉县','永吉县',52),(544,'蛟河市','蛟河市',52),(545,'桦甸市','桦甸市',52),(546,'舒兰市','舒兰市',52),(547,'磐石市','磐石市',52),(548,'铁西区','铁西区',53),(549,'铁东区','铁东区',53),(550,'梨树县','梨树县',53),(551,'伊通满族自治县','伊通满族自治县',53),(552,'公主岭市','公主岭市',53),(553,'双辽市','双辽市',53),(554,'龙山区','龙山区',54),(555,'西安区','西安区',54),(556,'东丰县','东丰县',54),(557,'东辽县','东辽县',54),(558,'东昌区','东昌区',55),(559,'二道江区','二道江区',55),(560,'通化县','通化县',55),(561,'辉南县','辉南县',55),(562,'柳河县','柳河县',55),(563,'梅河口市','梅河口市',55),(564,'集安市','集安市',55),(565,'八道江区','八道江区',56),(566,'抚松县','抚松县',56),(567,'靖宇县','靖宇县',56),(568,'长白朝鲜族自治县','长白朝鲜族自治县',56),(569,'江源县','江源县',56),(570,'临江市','临江市',56),(571,'宁江区','宁江区',57),(572,'前郭尔罗斯蒙古族自治县','前郭尔罗斯蒙古族自治县',57),(573,'长岭县','长岭县',57),(574,'乾安县','乾安县',57),(575,'扶余县','扶余县',57),(576,'洮北区','洮北区',58),(577,'镇赉县','镇赉县',58),(578,'通榆县','通榆县',58),(579,'洮南市','洮南市',58),(580,'大安市','大安市',58),(581,'延吉市','延吉市',59),(582,'图们市','图们市',59),(583,'敦化市','敦化市',59),(584,'珲春市','珲春市',59),(585,'龙井市','龙井市',59),(586,'和龙市','和龙市',59),(587,'汪清县','汪清县',59),(588,'安图县','安图县',59),(589,'道里区','道里区',60),(590,'南岗区','南岗区',60),(591,'道外区','道外区',60),(592,'香坊区','香坊区',60),(593,'动力区','动力区',60),(594,'平房区','平房区',60),(595,'松北区','松北区',60),(596,'呼兰区','呼兰区',60),(597,'依兰县','依兰县',60),(598,'方正县','方正县',60),(599,'宾县','宾县',60),(600,'巴彦县','巴彦县',60),(601,'木兰县','木兰县',60),(602,'通河县','通河县',60),(603,'延寿县','延寿县',60),(604,'阿城市','阿城市',60),(605,'双城市','双城市',60),(607,'五常市','五常市',60),(608,'龙沙区','龙沙区',61),(609,'建华区','建华区',61),(610,'铁锋区','铁锋区',61),(611,'昂昂溪区','昂昂溪区',61),(612,'富拉尔基区','富拉尔基区',61),(613,'碾子山区','碾子山区',61),(614,'梅里斯达斡尔族区','梅里斯达斡尔族区',61),(615,'龙江县','龙江县',61),(616,'依安县','依安县',61),(617,'泰来县','泰来县',61),(618,'甘南县','甘南县',61),(619,'富裕县','富裕县',61),(620,'克山县','克山县',61),(621,'克东县','克东县',61),(622,'拜泉县','拜泉县',61),(623,'讷河市','讷河市',61),(624,'鸡冠区','鸡冠区',62),(625,'恒山区','恒山区',62),(626,'滴道区','滴道区',62),(627,'梨树区','梨树区',62),(628,'城子河区','城子河区',62),(629,'麻山区','麻山区',62),(630,'鸡东县','鸡东县',62),(631,'虎林市','虎林市',62),(632,'密山市','密山市',62),(633,'向阳区','向阳区',63),(634,'工农区','工农区',63),(635,'南山区','南山区',63),(636,'兴安区','兴安区',63),(637,'东山区','东山区',63),(638,'兴山区','兴山区',63),(639,'萝北县','萝北县',63),(640,'绥滨县','绥滨县',63),(641,'尖山区','尖山区',64),(642,'岭东区','岭东区',64),(643,'四方台区','四方台区',64),(644,'宝山区','宝山区',64),(645,'集贤县','集贤县',64),(646,'友谊县','友谊县',64),(647,'宝清县','宝清县',64),(648,'饶河县','饶河县',64),(649,'萨尔图区','萨尔图区',65),(650,'龙凤区','龙凤区',65),(651,'让胡路区','让胡路区',65),(652,'红岗区','红岗区',65),(653,'大同区','大同区',65),(654,'肇州县','肇州县',65),(655,'肇源县','肇源县',65),(656,'林甸县','林甸县',65),(657,'杜尔伯特蒙古族自治县','杜尔伯特蒙古族自治县',65),(658,'伊春区','伊春区',66),(659,'南岔区','南岔区',66),(660,'友好区','友好区',66),(661,'西林区','西林区',66),(662,'翠峦区','翠峦区',66),(663,'新青区','新青区',66),(664,'美溪区','美溪区',66),(665,'金山屯区','金山屯区',66),(666,'五营区','五营区',66),(667,'乌马河区','乌马河区',66),(668,'汤旺河区','汤旺河区',66),(669,'带岭区','带岭区',66),(670,'乌伊岭区','乌伊岭区',66),(671,'红星区','红星区',66),(672,'上甘岭区','上甘岭区',66),(673,'嘉荫县','嘉荫县',66),(674,'铁力市','铁力市',66),(675,'永红区','永红区',67),(676,'向阳区','向阳区',67),(677,'前进区','前进区',67),(678,'东风区','东风区',67),(679,'郊区','郊区',67),(680,'桦南县','桦南县',67),(681,'桦川县','桦川县',67),(682,'汤原县','汤原县',67),(683,'抚远县','抚远县',67),(684,'同江市','同江市',67),(685,'富锦市','富锦市',67),(686,'新兴区','新兴区',68),(687,'桃山区','桃山区',68),(688,'茄子河区','茄子河区',68),(689,'勃利县','勃利县',68),(690,'东安区','东安区',69),(691,'阳明区','阳明区',69),(692,'爱民区','爱民区',69),(693,'西安区','西安区',69),(694,'东宁县','东宁县',69),(695,'林口县','林口县',69),(696,'绥芬河市','绥芬河市',69),(697,'海林市','海林市',69),(698,'宁安市','宁安市',69),(699,'穆棱市','穆棱市',69),(700,'爱辉区','爱辉区',70),(701,'嫩江县','嫩江县',70),(702,'逊克县','逊克县',70),(703,'孙吴县','孙吴县',70),(704,'北安市','北安市',70),(705,'五大连池市','五大连池市',70),(706,'北林区','北林区',71),(708,'兰西县','兰西县',71),(709,'青冈县','青冈县',71),(710,'庆安县','庆安县',71),(711,'明水县','明水县',71),(712,'绥棱县','绥棱县',71),(713,'安达市','安达市',71),(714,'肇东市','肇东市',71),(715,'海伦市','海伦市',71),(716,'呼玛县','呼玛县',72),(717,'塔河县','塔河县',72),(718,'漠河县','漠河县',72),(719,'黄浦区','黄浦区',73),(720,'卢湾区','卢湾区',73),(721,'徐汇区','徐汇区',73),(722,'长宁区','长宁区',73),(723,'静安区','静安区',73),(724,'普陀区','普陀区',73),(725,'闸北区','闸北区',73),(726,'虹口区','虹口区',73),(727,'杨浦区','杨浦区',73),(728,'闵行区','闵行区',73),(729,'宝山区','宝山区',73),(730,'嘉定区','嘉定区',73),(731,'浦东新区','浦东新区',73),(732,'金山区','金山区',73),(733,'松江区','松江区',73),(734,'青浦区','青浦区',73),(735,'南汇区','南汇区',73),(736,'奉贤区','奉贤区',73),(737,'崇明县','崇明县',73),(738,'玄武区','玄武区',74),(739,'白下区','白下区',74),(740,'秦淮区','秦淮区',74),(741,'建邺区','建邺区',74),(742,'鼓楼区','鼓楼区',74),(743,'下关区','下关区',74),(744,'浦口区','浦口区',74),(745,'栖霞区','栖霞区',74),(746,'雨花台区','雨花台区',74),(747,'江宁区','江宁区',74),(748,'六合区','六合区',74),(749,'溧水县','溧水县',74),(750,'高淳县','高淳县',74),(751,'崇安区','崇安区',75),(752,'南长区','南长区',75),(753,'北塘区','北塘区',75),(754,'锡山区','锡山区',75),(755,'惠山区','惠山区',75),(756,'滨湖区','滨湖区',75),(757,'江阴市','江阴市',75),(758,'宜兴市','宜兴市',75),(759,'鼓楼区','鼓楼区',76),(760,'云龙区','云龙区',76),(761,'九里区','九里区',76),(762,'贾汪区','贾汪区',76),(763,'泉山区','泉山区',76),(764,'丰县','丰县',76),(765,'沛县','沛县',76),(766,'铜山县','铜山县',76),(767,'睢宁县','睢宁县',76),(768,'新沂市','新沂市',76),(769,'邳州市','邳州市',76),(770,'天宁区','天宁区',77),(771,'钟楼区','钟楼区',77),(772,'戚墅堰区','戚墅堰区',77),(773,'新北区','新北区',77),(774,'武进区','武进区',77),(775,'溧阳市','溧阳市',77),(776,'金坛市','金坛市',77),(777,'沧浪区','沧浪区',78),(778,'平江区','平江区',78),(779,'金阊区','金阊区',78),(780,'虎丘区','虎丘区',78),(781,'吴中区','吴中区',78),(782,'相城区','相城区',78),(783,'常熟市','常熟市',78),(784,'张家港市','张家港市',78),(785,'昆山市','昆山市',78),(786,'吴江市','吴江市',78),(787,'太仓市','太仓市',78),(788,'崇川区','崇川区',79),(789,'港闸区','港闸区',79),(790,'海安县','海安县',79),(791,'如东县','如东县',79),(792,'启东市','启东市',79),(793,'如皋市','如皋市',79),(794,'通州市','通州市',79),(795,'海门市','海门市',79),(796,'连云区','连云区',80),(797,'新浦区','新浦区',80),(798,'海州区','海州区',80),(799,'赣榆县','赣榆县',80),(800,'东海县','东海县',80),(801,'灌云县','灌云县',80),(802,'灌南县','灌南县',80),(803,'清河区','清河区',81),(804,'楚州区','楚州区',81),(805,'淮阴区','淮阴区',81),(806,'清浦区','清浦区',81),(807,'涟水县','涟水县',81),(809,'盱眙县','盱眙县',81),(810,'金湖县','金湖县',81),(811,'亭湖区','亭湖区',82),(812,'盐都区','盐都区',82),(813,'响水县','响水县',82),(814,'滨海县','滨海县',82),(815,'阜宁县','阜宁县',82),(816,'射阳县','射阳县',82),(817,'建湖县','建湖县',82),(818,'东台市','东台市',82),(819,'大丰市','大丰市',82),(820,'广陵区','广陵区',83),(821,'邗江区','邗江区',83),(822,'维扬区','维扬区',83),(823,'宝应县','宝应县',83),(824,'仪征市','仪征市',83),(825,'高邮市','高邮市',83),(826,'江都市','江都市',83),(827,'京口区','京口区',84),(828,'润州区','润州区',84),(829,'丹徒区','丹徒区',84),(830,'丹阳市','丹阳市',84),(831,'扬中市','扬中市',84),(832,'句容市','句容市',84),(833,'海陵区','海陵区',85),(834,'高港区','高港区',85),(835,'兴化市','兴化市',85),(836,'靖江市','靖江市',85),(837,'泰兴市','泰兴市',85),(838,'姜堰市','姜堰市',85),(839,'宿城区','宿城区',86),(840,'宿豫区','宿豫区',86),(841,'沭阳县','沭阳县',86),(842,'泗阳县','泗阳县',86),(843,'泗洪县','泗洪县',86),(844,'上城区','上城区',87),(845,'下城区','下城区',87),(846,'江干区','江干区',87),(847,'拱墅区','拱墅区',87),(848,'西湖区','西湖区',87),(849,'滨江区','滨江区',87),(850,'萧山区','萧山区',87),(851,'余杭区','余杭区',87),(852,'桐庐县','桐庐县',87),(853,'淳安县','淳安县',87),(854,'建德市','建德市',87),(855,'富阳市','富阳市',87),(856,'临安市','临安市',87),(857,'海曙区','海曙区',88),(858,'江东区','江东区',88),(859,'江北区','江北区',88),(860,'北仑区','北仑区',88),(861,'镇海区','镇海区',88),(862,'鄞州区','鄞州区',88),(863,'象山县','象山县',88),(864,'宁海县','宁海县',88),(865,'余姚市','余姚市',88),(866,'慈溪市','慈溪市',88),(867,'奉化市','奉化市',88),(868,'鹿城区','鹿城区',89),(869,'龙湾区','龙湾区',89),(870,'瓯海区','瓯海区',89),(871,'洞头县','洞头县',89),(872,'永嘉县','永嘉县',89),(873,'平阳县','平阳县',89),(874,'苍南县','苍南县',89),(875,'文成县','文成县',89),(876,'泰顺县','泰顺县',89),(877,'瑞安市','瑞安市',89),(878,'乐清市','乐清市',89),(879,'秀城区','秀城区',90),(880,'秀洲区','秀洲区',90),(881,'嘉善县','嘉善县',90),(882,'海盐县','海盐县',90),(883,'海宁市','海宁市',90),(884,'平湖市','平湖市',90),(885,'桐乡市','桐乡市',90),(886,'吴兴区','吴兴区',91),(887,'南浔区','南浔区',91),(888,'德清县','德清县',91),(889,'长兴县','长兴县',91),(890,'安吉县','安吉县',91),(891,'越城区','越城区',92),(892,'绍兴县','绍兴县',92),(893,'新昌县','新昌县',92),(894,'诸暨市','诸暨市',92),(895,'上虞市','上虞市',92),(896,'嵊州市','嵊州市',92),(897,'婺城区','婺城区',93),(898,'金东区','金东区',93),(899,'武义县','武义县',93),(900,'浦江县','浦江县',93),(901,'磐安县','磐安县',93),(902,'兰溪市','兰溪市',93),(903,'义乌市','义乌市',93),(904,'东阳市','东阳市',93),(905,'永康市','永康市',93),(906,'柯城区','柯城区',94),(907,'衢江区','衢江区',94),(908,'常山县','常山县',94),(910,'龙游县','龙游县',94),(911,'江山市','江山市',94),(912,'定海区','定海区',95),(913,'普陀区','普陀区',95),(914,'岱山县','岱山县',95),(915,'嵊泗县','嵊泗县',95),(916,'椒江区','椒江区',96),(917,'黄岩区','黄岩区',96),(918,'路桥区','路桥区',96),(919,'玉环县','玉环县',96),(920,'三门县','三门县',96),(921,'天台县','天台县',96),(922,'仙居县','仙居县',96),(923,'温岭市','温岭市',96),(924,'临海市','临海市',96),(925,'莲都区','莲都区',97),(926,'青田县','青田县',97),(927,'缙云县','缙云县',97),(928,'遂昌县','遂昌县',97),(929,'松阳县','松阳县',97),(930,'云和县','云和县',97),(931,'庆元县','庆元县',97),(932,'景宁畲族自治县','景宁畲族自治县',97),(933,'龙泉市','龙泉市',97),(934,'瑶海区','瑶海区',98),(935,'庐阳区','庐阳区',98),(936,'蜀山区','蜀山区',98),(937,'包河区','包河区',98),(938,'长丰县','长丰县',98),(939,'肥东县','肥东县',98),(940,'肥西县','肥西县',98),(941,'镜湖区','镜湖区',99),(942,'马塘区','马塘区',99),(943,'新芜区','新芜区',99),(944,'鸠江区','鸠江区',99),(945,'芜湖县','芜湖县',99),(946,'繁昌县','繁昌县',99),(947,'南陵县','南陵县',99),(948,'龙子湖区','龙子湖区',100),(949,'蚌山区','蚌山区',100),(950,'禹会区','禹会区',100),(951,'淮上区','淮上区',100),(952,'怀远县','怀远县',100),(953,'五河县','五河县',100),(954,'固镇县','固镇县',100),(955,'大通区','大通区',101),(956,'田家庵区','田家庵区',101),(957,'谢家集区','谢家集区',101),(958,'八公山区','八公山区',101),(959,'潘集区','潘集区',101),(960,'凤台县','凤台县',101),(961,'金家庄区','金家庄区',102),(962,'花山区','花山区',102),(963,'雨山区','雨山区',102),(964,'当涂县','当涂县',102),(965,'杜集区','杜集区',103),(966,'相山区','相山区',103),(967,'烈山区','烈山区',103),(968,'濉溪县','濉溪县',103),(969,'铜官山区','铜官山区',104),(970,'狮子山区','狮子山区',104),(971,'郊区','郊区',104),(972,'铜陵县','铜陵县',104),(973,'迎江区','迎江区',105),(974,'大观区','大观区',105),(975,'郊区','郊区',105),(976,'怀宁县','怀宁县',105),(977,'枞阳县','枞阳县',105),(978,'潜山县','潜山县',105),(979,'太湖县','太湖县',105),(980,'宿松县','宿松县',105),(981,'望江县','望江县',105),(982,'岳西县','岳西县',105),(983,'桐城市','桐城市',105),(984,'屯溪区','屯溪区',106),(985,'黄山区','黄山区',106),(986,'徽州区','徽州区',106),(987,'歙县','歙县',106),(988,'休宁县','休宁县',106),(989,'黟县','黟县',106),(990,'祁门县','祁门县',106),(991,'琅琊区','琅琊区',107),(992,'南谯区','南谯区',107),(993,'来安县','来安县',107),(994,'全椒县','全椒县',107),(995,'定远县','定远县',107),(996,'凤阳县','凤阳县',107),(997,'天长市','天长市',107),(998,'明光市','明光市',107),(999,'颍州区','颍州区',108),(1000,'颍东区','颍东区',108),(1001,'颍泉区','颍泉区',108),(1002,'临泉县','临泉县',108),(1003,'太和县','太和县',108),(1004,'阜南县','阜南县',108),(1005,'颍上县','颍上县',108),(1006,'界首市','界首市',108),(1007,'埇桥区','埇桥区',109),(1008,'砀山县','砀山县',109),(1009,'萧县','萧县',109),(1011,'泗县','泗县',109),(1012,'居巢区','居巢区',110),(1013,'庐江县','庐江县',110),(1014,'无为县','无为县',110),(1015,'含山县','含山县',110),(1016,'和县','和县',110),(1017,'金安区','金安区',111),(1018,'裕安区','裕安区',111),(1019,'寿县','寿县',111),(1020,'霍邱县','霍邱县',111),(1021,'舒城县','舒城县',111),(1022,'金寨县','金寨县',111),(1023,'霍山县','霍山县',111),(1024,'谯城区','谯城区',112),(1025,'涡阳县','涡阳县',112),(1026,'蒙城县','蒙城县',112),(1027,'利辛县','利辛县',112),(1028,'贵池区','贵池区',113),(1029,'东至县','东至县',113),(1030,'石台县','石台县',113),(1031,'青阳县','青阳县',113),(1032,'宣州区','宣州区',114),(1033,'郎溪县','郎溪县',114),(1034,'广德县','广德县',114),(1035,'泾县','泾县',114),(1036,'绩溪县','绩溪县',114),(1037,'旌德县','旌德县',114),(1038,'宁国市','宁国市',114),(1039,'鼓楼区','鼓楼区',115),(1040,'台江区','台江区',115),(1041,'仓山区','仓山区',115),(1042,'马尾区','马尾区',115),(1043,'晋安区','晋安区',115),(1044,'闽侯县','闽侯县',115),(1045,'连江县','连江县',115),(1046,'罗源县','罗源县',115),(1047,'闽清县','闽清县',115),(1048,'永泰县','永泰县',115),(1049,'平潭县','平潭县',115),(1050,'福清市','福清市',115),(1051,'长乐市','长乐市',115),(1052,'思明区','思明区',116),(1053,'海沧区','海沧区',116),(1054,'湖里区','湖里区',116),(1055,'集美区','集美区',116),(1056,'同安区','同安区',116),(1057,'翔安区','翔安区',116),(1058,'城厢区','城厢区',117),(1059,'涵江区','涵江区',117),(1060,'荔城区','荔城区',117),(1061,'秀屿区','秀屿区',117),(1062,'仙游县','仙游县',117),(1063,'梅列区','梅列区',118),(1064,'三元区','三元区',118),(1065,'明溪县','明溪县',118),(1066,'清流县','清流县',118),(1067,'宁化县','宁化县',118),(1068,'大田县','大田县',118),(1069,'尤溪县','尤溪县',118),(1070,'沙县','沙县',118),(1071,'将乐县','将乐县',118),(1072,'泰宁县','泰宁县',118),(1073,'建宁县','建宁县',118),(1074,'永安市','永安市',118),(1075,'鲤城区','鲤城区',119),(1076,'丰泽区','丰泽区',119),(1077,'洛江区','洛江区',119),(1078,'泉港区','泉港区',119),(1079,'惠安县','惠安县',119),(1080,'安溪县','安溪县',119),(1081,'永春县','永春县',119),(1082,'德化县','德化县',119),(1083,'金门县','金门县',119),(1084,'石狮市','石狮市',119),(1085,'晋江市','晋江市',119),(1086,'南安市','南安市',119),(1087,'芗城区','芗城区',120),(1088,'龙文区','龙文区',120),(1089,'云霄县','云霄县',120),(1090,'漳浦县','漳浦县',120),(1091,'诏安县','诏安县',120),(1092,'长泰县','长泰县',120),(1093,'东山县','东山县',120),(1094,'南靖县','南靖县',120),(1095,'平和县','平和县',120),(1096,'华安县','华安县',120),(1097,'龙海市','龙海市',120),(1098,'延平区','延平区',121),(1099,'顺昌县','顺昌县',121),(1100,'浦城县','浦城县',121),(1101,'光泽县','光泽县',121),(1102,'松溪县','松溪县',121),(1103,'政和县','政和县',121),(1104,'邵武市','邵武市',121),(1105,'武夷山市','武夷山市',121),(1106,'建瓯市','建瓯市',121),(1107,'建阳市','建阳市',121),(1108,'新罗区','新罗区',122),(1109,'长汀县','长汀县',122),(1110,'永定县','永定县',122),(1112,'武平县','武平县',122),(1113,'连城县','连城县',122),(1114,'漳平市','漳平市',122),(1115,'蕉城区','蕉城区',123),(1116,'霞浦县','霞浦县',123),(1117,'古田县','古田县',123),(1118,'屏南县','屏南县',123),(1119,'寿宁县','寿宁县',123),(1120,'周宁县','周宁县',123),(1121,'柘荣县','柘荣县',123),(1122,'福安市','福安市',123),(1123,'福鼎市','福鼎市',123),(1124,'东湖区','东湖区',124),(1125,'西湖区','西湖区',124),(1126,'青云谱区','青云谱区',124),(1127,'湾里区','湾里区',124),(1128,'青山湖区','青山湖区',124),(1129,'南昌县','南昌县',124),(1130,'新建县','新建县',124),(1131,'安义县','安义县',124),(1132,'进贤县','进贤县',124),(1133,'昌江区','昌江区',125),(1134,'珠山区','珠山区',125),(1135,'浮梁县','浮梁县',125),(1136,'乐平市','乐平市',125),(1137,'安源区','安源区',126),(1138,'湘东区','湘东区',126),(1139,'莲花县','莲花县',126),(1140,'上栗县','上栗县',126),(1141,'芦溪县','芦溪县',126),(1142,'庐山区','庐山区',127),(1143,'浔阳区','浔阳区',127),(1144,'九江县','九江县',127),(1145,'武宁县','武宁县',127),(1146,'修水县','修水县',127),(1147,'永修县','永修县',127),(1148,'德安县','德安县',127),(1149,'星子县','星子县',127),(1150,'都昌县','都昌县',127),(1151,'湖口县','湖口县',127),(1152,'彭泽县','彭泽县',127),(1153,'瑞昌市','瑞昌市',127),(1154,'渝水区','渝水区',128),(1155,'分宜县','分宜县',128),(1156,'月湖区','月湖区',129),(1157,'余江县','余江县',129),(1158,'贵溪市','贵溪市',129),(1159,'章贡区','章贡区',130),(1160,'赣县','赣县',130),(1161,'信丰县','信丰县',130),(1162,'大余县','大余县',130),(1163,'上犹县','上犹县',130),(1164,'崇义县','崇义县',130),(1165,'安远县','安远县',130),(1166,'龙南县','龙南县',130),(1167,'定南县','定南县',130),(1168,'全南县','全南县',130),(1169,'宁都县','宁都县',130),(1170,'于都县','于都县',130),(1171,'兴国县','兴国县',130),(1172,'会昌县','会昌县',130),(1173,'寻乌县','寻乌县',130),(1174,'石城县','石城县',130),(1175,'瑞金市','瑞金市',130),(1176,'南康市','南康市',130),(1177,'吉州区','吉州区',131),(1178,'青原区','青原区',131),(1179,'吉安县','吉安县',131),(1180,'吉水县','吉水县',131),(1181,'峡江县','峡江县',131),(1182,'新干县','新干县',131),(1183,'永丰县','永丰县',131),(1184,'泰和县','泰和县',131),(1185,'遂川县','遂川县',131),(1186,'万安县','万安县',131),(1187,'安福县','安福县',131),(1188,'永新县','永新县',131),(1189,'井冈山市','井冈山市',131),(1190,'袁州区','袁州区',132),(1191,'奉新县','奉新县',132),(1192,'万载县','万载县',132),(1193,'上高县','上高县',132),(1194,'宜丰县','宜丰县',132),(1195,'靖安县','靖安县',132),(1196,'铜鼓县','铜鼓县',132),(1197,'丰城市','丰城市',132),(1198,'樟树市','樟树市',132),(1199,'高安市','高安市',132),(1200,'临川区','临川区',133),(1201,'南城县','南城县',133),(1202,'黎川县','黎川县',133),(1203,'南丰县','南丰县',133),(1204,'崇仁县','崇仁县',133),(1205,'乐安县','乐安县',133),(1206,'宜黄县','宜黄县',133),(1207,'金溪县','金溪县',133),(1208,'资溪县','资溪县',133),(1209,'东乡县','东乡县',133),(1210,'广昌县','广昌县',133),(1211,'信州区','信州区',134),(1213,'广丰县','广丰县',134),(1214,'玉山县','玉山县',134),(1215,'铅山县','铅山县',134),(1216,'横峰县','横峰县',134),(1217,'弋阳县','弋阳县',134),(1218,'余干县','余干县',134),(1219,'鄱阳县','鄱阳县',134),(1220,'万年县','万年县',134),(1221,'婺源县','婺源县',134),(1222,'德兴市','德兴市',134),(1223,'历下区','历下区',135),(1224,'市中区','市中区',135),(1225,'槐荫区','槐荫区',135),(1226,'天桥区','天桥区',135),(1227,'历城区','历城区',135),(1228,'长清区','长清区',135),(1229,'平阴县','平阴县',135),(1230,'济阳县','济阳县',135),(1231,'商河县','商河县',135),(1232,'章丘市','章丘市',135),(1233,'市南区','市南区',136),(1234,'市北区','市北区',136),(1235,'四方区','四方区',136),(1236,'黄岛区','黄岛区',136),(1237,'崂山区','崂山区',136),(1238,'李沧区','李沧区',136),(1239,'城阳区','城阳区',136),(1240,'胶州市','胶州市',136),(1241,'即墨市','即墨市',136),(1242,'平度市','平度市',136),(1243,'胶南市','胶南市',136),(1244,'莱西市','莱西市',136),(1245,'淄川区','淄川区',137),(1246,'张店区','张店区',137),(1247,'博山区','博山区',137),(1248,'临淄区','临淄区',137),(1249,'周村区','周村区',137),(1250,'桓台县','桓台县',137),(1251,'高青县','高青县',137),(1252,'沂源县','沂源县',137),(1253,'市中区','市中区',138),(1254,'薛城区','薛城区',138),(1255,'峄城区','峄城区',138),(1256,'台儿庄区','台儿庄区',138),(1257,'山亭区','山亭区',138),(1258,'滕州市','滕州市',138),(1259,'东营区','东营区',139),(1260,'河口区','河口区',139),(1261,'垦利县','垦利县',139),(1262,'利津县','利津县',139),(1263,'广饶县','广饶县',139),(1264,'芝罘区','芝罘区',140),(1265,'福山区','福山区',140),(1266,'牟平区','牟平区',140),(1267,'莱山区','莱山区',140),(1268,'长岛县','长岛县',140),(1269,'龙口市','龙口市',140),(1270,'莱阳市','莱阳市',140),(1271,'莱州市','莱州市',140),(1272,'蓬莱市','蓬莱市',140),(1273,'招远市','招远市',140),(1274,'栖霞市','栖霞市',140),(1275,'海阳市','海阳市',140),(1276,'潍城区','潍城区',141),(1277,'寒亭区','寒亭区',141),(1278,'坊子区','坊子区',141),(1279,'奎文区','奎文区',141),(1280,'临朐县','临朐县',141),(1281,'昌乐县','昌乐县',141),(1282,'青州市','青州市',141),(1283,'诸城市','诸城市',141),(1284,'寿光市','寿光市',141),(1285,'安丘市','安丘市',141),(1286,'高密市','高密市',141),(1287,'昌邑市','昌邑市',141),(1288,'市中区','市中区',142),(1289,'任城区','任城区',142),(1290,'微山县','微山县',142),(1291,'鱼台县','鱼台县',142),(1292,'金乡县','金乡县',142),(1293,'嘉祥县','嘉祥县',142),(1294,'汶上县','汶上县',142),(1295,'泗水县','泗水县',142),(1296,'梁山县','梁山县',142),(1297,'曲阜市','曲阜市',142),(1298,'兖州市','兖州市',142),(1299,'邹城市','邹城市',142),(1300,'泰山区','泰山区',143),(1301,'岱岳区','岱岳区',143),(1302,'宁阳县','宁阳县',143),(1303,'东平县','东平县',143),(1304,'新泰市','新泰市',143),(1305,'肥城市','肥城市',143),(1306,'环翠区','环翠区',144),(1307,'文登市','文登市',144),(1308,'荣成市','荣成市',144),(1309,'乳山市','乳山市',144),(1310,'东港区','东港区',145),(1311,'岚山区','岚山区',145),(1312,'五莲县','五莲县',145),(1314,'莱城区','莱城区',146),(1315,'钢城区','钢城区',146),(1316,'兰山区','兰山区',147),(1317,'罗庄区','罗庄区',147),(1318,'河东区','河东区',147),(1319,'沂南县','沂南县',147),(1320,'郯城县','郯城县',147),(1321,'沂水县','沂水县',147),(1322,'苍山县','苍山县',147),(1323,'费县','费县',147),(1324,'平邑县','平邑县',147),(1325,'莒南县','莒南县',147),(1326,'蒙阴县','蒙阴县',147),(1327,'临沭县','临沭县',147),(1328,'德城区','德城区',148),(1329,'陵县','陵县',148),(1330,'宁津县','宁津县',148),(1331,'庆云县','庆云县',148),(1332,'临邑县','临邑县',148),(1333,'齐河县','齐河县',148),(1334,'平原县','平原县',148),(1335,'夏津县','夏津县',148),(1336,'武城县','武城县',148),(1337,'乐陵市','乐陵市',148),(1338,'禹城市','禹城市',148),(1339,'东昌府区','东昌府区',149),(1340,'阳谷县','阳谷县',149),(1341,'莘县','莘县',149),(1342,'茌平县','茌平县',149),(1343,'东阿县','东阿县',149),(1344,'冠县','冠县',149),(1345,'高唐县','高唐县',149),(1346,'临清市','临清市',149),(1347,'滨城区','滨城区',150),(1348,'惠民县','惠民县',150),(1349,'阳信县','阳信县',150),(1350,'无棣县','无棣县',150),(1351,'沾化县','沾化县',150),(1352,'博兴县','博兴县',150),(1353,'邹平县','邹平县',150),(1354,'牡丹区','牡丹区',151),(1355,'曹县','曹县',151),(1356,'单县','单县',151),(1357,'成武县','成武县',151),(1358,'巨野县','巨野县',151),(1359,'郓城县','郓城县',151),(1360,'鄄城县','鄄城县',151),(1361,'定陶县','定陶县',151),(1362,'东明县','东明县',151),(1363,'中原区','中原区',152),(1364,'二七区','二七区',152),(1365,'管城回族区','管城回族区',152),(1366,'金水区','金水区',152),(1367,'上街区','上街区',152),(1368,'惠济区','惠济区',152),(1369,'中牟县','中牟县',152),(1370,'巩义市','巩义市',152),(1371,'荥阳市','荥阳市',152),(1372,'新密市','新密市',152),(1373,'新郑市','新郑市',152),(1374,'登封市','登封市',152),(1375,'龙亭区','龙亭区',153),(1376,'顺河回族区','顺河回族区',153),(1377,'鼓楼区','鼓楼区',153),(1378,'南关区','南关区',153),(1379,'郊区','郊区',153),(1380,'杞县','杞县',153),(1381,'通许县','通许县',153),(1382,'尉氏县','尉氏县',153),(1383,'开封县','开封县',153),(1384,'兰考县','兰考县',153),(1385,'老城区','老城区',154),(1386,'西工区','西工区',154),(1387,'廛河回族区','廛河回族区',154),(1388,'涧西区','涧西区',154),(1389,'吉利区','吉利区',154),(1390,'洛龙区','洛龙区',154),(1391,'孟津县','孟津县',154),(1392,'新安县','新安县',154),(1393,'栾川县','栾川县',154),(1394,'嵩县','嵩县',154),(1395,'汝阳县','汝阳县',154),(1396,'宜阳县','宜阳县',154),(1397,'洛宁县','洛宁县',154),(1398,'伊川县','伊川县',154),(1399,'偃师市','偃师市',154),(1400,'新华区','新华区',155),(1401,'卫东区','卫东区',155),(1402,'石龙区','石龙区',155),(1403,'湛河区','湛河区',155),(1404,'宝丰县','宝丰县',155),(1405,'叶县','叶县',155),(1406,'鲁山县','鲁山县',155),(1407,'郏县','郏县',155),(1408,'舞钢市','舞钢市',155),(1409,'汝州市','汝州市',155),(1410,'文峰区','文峰区',156),(1411,'北关区','北关区',156),(1412,'殷都区','殷都区',156),(1413,'龙安区','龙安区',156),(1415,'汤阴县','汤阴县',156),(1416,'滑县','滑县',156),(1417,'内黄县','内黄县',156),(1418,'林州市','林州市',156),(1419,'鹤山区','鹤山区',157),(1420,'山城区','山城区',157),(1421,'淇滨区','淇滨区',157),(1422,'浚县','浚县',157),(1423,'淇县','淇县',157),(1424,'红旗区','红旗区',158),(1425,'卫滨区','卫滨区',158),(1426,'凤泉区','凤泉区',158),(1427,'牧野区','牧野区',158),(1428,'新乡县','新乡县',158),(1429,'获嘉县','获嘉县',158),(1430,'原阳县','原阳县',158),(1431,'延津县','延津县',158),(1432,'封丘县','封丘县',158),(1433,'长垣县','长垣县',158),(1434,'卫辉市','卫辉市',158),(1435,'辉县市','辉县市',158),(1436,'解放区','解放区',159),(1437,'中站区','中站区',159),(1438,'马村区','马村区',159),(1439,'山阳区','山阳区',159),(1440,'修武县','修武县',159),(1441,'博爱县','博爱县',159),(1442,'武陟县','武陟县',159),(1443,'温县','温县',159),(1444,'济源市','济源市',159),(1445,'沁阳市','沁阳市',159),(1446,'孟州市','孟州市',159),(1447,'华龙区','华龙区',160),(1448,'清丰县','清丰县',160),(1449,'南乐县','南乐县',160),(1450,'范县','范县',160),(1451,'台前县','台前县',160),(1452,'濮阳县','濮阳县',160),(1453,'魏都区','魏都区',161),(1454,'许昌县','许昌县',161),(1455,'鄢陵县','鄢陵县',161),(1456,'襄城县','襄城县',161),(1457,'禹州市','禹州市',161),(1458,'长葛市','长葛市',161),(1459,'源汇区','源汇区',162),(1460,'郾城区','郾城区',162),(1461,'召陵区','召陵区',162),(1462,'舞阳县','舞阳县',162),(1463,'临颍县','临颍县',162),(1464,'市辖区','市辖区',163),(1465,'湖滨区','湖滨区',163),(1466,'渑池县','渑池县',163),(1467,'陕县','陕县',163),(1468,'卢氏县','卢氏县',163),(1469,'义马市','义马市',163),(1470,'灵宝市','灵宝市',163),(1471,'宛城区','宛城区',164),(1472,'卧龙区','卧龙区',164),(1473,'南召县','南召县',164),(1474,'方城县','方城县',164),(1475,'西峡县','西峡县',164),(1476,'镇平县','镇平县',164),(1477,'内乡县','内乡县',164),(1478,'淅川县','淅川县',164),(1479,'社旗县','社旗县',164),(1480,'唐河县','唐河县',164),(1481,'新野县','新野县',164),(1482,'桐柏县','桐柏县',164),(1483,'邓州市','邓州市',164),(1484,'梁园区','梁园区',165),(1485,'睢阳区','睢阳区',165),(1486,'民权县','民权县',165),(1487,'睢县','睢县',165),(1488,'宁陵县','宁陵县',165),(1489,'柘城县','柘城县',165),(1490,'虞城县','虞城县',165),(1491,'夏邑县','夏邑县',165),(1492,'永城市','永城市',165),(1493,'浉河区','浉河区',166),(1494,'平桥区','平桥区',166),(1495,'罗山县','罗山县',166),(1496,'光山县','光山县',166),(1497,'新县','新县',166),(1498,'商城县','商城县',166),(1499,'固始县','固始县',166),(1500,'潢川县','潢川县',166),(1501,'淮滨县','淮滨县',166),(1502,'息县','息县',166),(1503,'川汇区','川汇区',167),(1504,'扶沟县','扶沟县',167),(1505,'西华县','西华县',167),(1506,'商水县','商水县',167),(1507,'沈丘县','沈丘县',167),(1508,'郸城县','郸城县',167),(1509,'淮阳县','淮阳县',167),(1510,'太康县','太康县',167),(1511,'鹿邑县','鹿邑县',167),(1512,'项城市','项城市',167),(1513,'驿城区','驿城区',168),(1514,'西平县','西平县',168),(1516,'平舆县','平舆县',168),(1517,'正阳县','正阳县',168),(1518,'确山县','确山县',168),(1519,'泌阳县','泌阳县',168),(1520,'汝南县','汝南县',168),(1521,'遂平县','遂平县',168),(1522,'新蔡县','新蔡县',168),(1523,'江岸区','江岸区',169),(1524,'江汉区','江汉区',169),(1525,'硚口区','硚口区',169),(1526,'汉阳区','汉阳区',169),(1527,'武昌区','武昌区',169),(1528,'青山区','青山区',169),(1529,'洪山区','洪山区',169),(1530,'东西湖区','东西湖区',169),(1531,'汉南区','汉南区',169),(1532,'蔡甸区','蔡甸区',169),(1533,'江夏区','江夏区',169),(1534,'黄陂区','黄陂区',169),(1535,'新洲区','新洲区',169),(1536,'黄石港区','黄石港区',170),(1537,'西塞山区','西塞山区',170),(1538,'下陆区','下陆区',170),(1539,'铁山区','铁山区',170),(1540,'阳新县','阳新县',170),(1541,'大冶市','大冶市',170),(1542,'茅箭区','茅箭区',171),(1543,'张湾区','张湾区',171),(1544,'郧县','郧县',171),(1545,'郧西县','郧西县',171),(1546,'竹山县','竹山县',171),(1547,'竹溪县','竹溪县',171),(1548,'房县','房县',171),(1549,'丹江口市','丹江口市',171),(1550,'西陵区','西陵区',172),(1551,'伍家岗区','伍家岗区',172),(1552,'点军区','点军区',172),(1553,'猇亭区','猇亭区',172),(1554,'夷陵区','夷陵区',172),(1555,'远安县','远安县',172),(1556,'兴山县','兴山县',172),(1557,'秭归县','秭归县',172),(1558,'长阳土家族自治县','长阳土家族自治县',172),(1559,'五峰土家族自治县','五峰土家族自治县',172),(1560,'宜都市','宜都市',172),(1561,'当阳市','当阳市',172),(1562,'枝江市','枝江市',172),(1563,'襄城区','襄城区',173),(1564,'樊城区','樊城区',173),(1565,'襄阳区','襄阳区',173),(1566,'南漳县','南漳县',173),(1567,'谷城县','谷城县',173),(1568,'保康县','保康县',173),(1569,'老河口市','老河口市',173),(1570,'枣阳市','枣阳市',173),(1571,'宜城市','宜城市',173),(1572,'梁子湖区','梁子湖区',174),(1573,'华容区','华容区',174),(1574,'鄂城区','鄂城区',174),(1575,'东宝区','东宝区',175),(1576,'掇刀区','掇刀区',175),(1577,'京山县','京山县',175),(1578,'沙洋县','沙洋县',175),(1579,'钟祥市','钟祥市',175),(1580,'孝南区','孝南区',176),(1581,'孝昌县','孝昌县',176),(1582,'大悟县','大悟县',176),(1583,'云梦县','云梦县',176),(1584,'应城市','应城市',176),(1585,'安陆市','安陆市',176),(1586,'汉川市','汉川市',176),(1587,'沙市区','沙市区',177),(1588,'荆州区','荆州区',177),(1589,'公安县','公安县',177),(1590,'监利县','监利县',177),(1591,'江陵县','江陵县',177),(1592,'石首市','石首市',177),(1593,'洪湖市','洪湖市',177),(1594,'松滋市','松滋市',177),(1595,'黄州区','黄州区',178),(1596,'团风县','团风县',178),(1597,'红安县','红安县',178),(1598,'罗田县','罗田县',178),(1599,'英山县','英山县',178),(1600,'浠水县','浠水县',178),(1601,'蕲春县','蕲春县',178),(1602,'黄梅县','黄梅县',178),(1603,'麻城市','麻城市',178),(1604,'武穴市','武穴市',178),(1605,'咸安区','咸安区',179),(1606,'嘉鱼县','嘉鱼县',179),(1607,'通城县','通城县',179),(1608,'崇阳县','崇阳县',179),(1609,'通山县','通山县',179),(1610,'赤壁市','赤壁市',179),(1611,'曾都区','曾都区',180),(1612,'广水市','广水市',180),(1613,'恩施市','恩施市',181),(1614,'利川市','利川市',181),(1615,'建始县','建始县',181),(1617,'宣恩县','宣恩县',181),(1618,'咸丰县','咸丰县',181),(1619,'来凤县','来凤县',181),(1620,'鹤峰县','鹤峰县',181),(1621,'仙桃市','仙桃市',182),(1622,'潜江市','潜江市',182),(1623,'天门市','天门市',182),(1624,'神农架林区','神农架林区',182),(1625,'芙蓉区','芙蓉区',183),(1626,'天心区','天心区',183),(1627,'岳麓区','岳麓区',183),(1628,'开福区','开福区',183),(1629,'雨花区','雨花区',183),(1630,'长沙县','长沙县',183),(1631,'望城县','望城县',183),(1632,'宁乡县','宁乡县',183),(1633,'浏阳市','浏阳市',183),(1634,'荷塘区','荷塘区',184),(1635,'芦淞区','芦淞区',184),(1636,'石峰区','石峰区',184),(1637,'天元区','天元区',184),(1638,'株洲县','株洲县',184),(1639,'攸县','攸县',184),(1640,'茶陵县','茶陵县',184),(1641,'炎陵县','炎陵县',184),(1642,'醴陵市','醴陵市',184),(1643,'雨湖区','雨湖区',185),(1644,'岳塘区','岳塘区',185),(1645,'湘潭县','湘潭县',185),(1646,'湘乡市','湘乡市',185),(1647,'韶山市','韶山市',185),(1648,'珠晖区','珠晖区',186),(1649,'雁峰区','雁峰区',186),(1650,'石鼓区','石鼓区',186),(1651,'蒸湘区','蒸湘区',186),(1652,'南岳区','南岳区',186),(1653,'衡阳县','衡阳县',186),(1654,'衡南县','衡南县',186),(1655,'衡山县','衡山县',186),(1656,'衡东县','衡东县',186),(1657,'祁东县','祁东县',186),(1658,'耒阳市','耒阳市',186),(1659,'常宁市','常宁市',186),(1660,'双清区','双清区',187),(1661,'大祥区','大祥区',187),(1662,'北塔区','北塔区',187),(1663,'邵东县','邵东县',187),(1664,'新邵县','新邵县',187),(1665,'邵阳县','邵阳县',187),(1666,'隆回县','隆回县',187),(1667,'洞口县','洞口县',187),(1668,'绥宁县','绥宁县',187),(1669,'新宁县','新宁县',187),(1670,'城步苗族自治县','城步苗族自治县',187),(1671,'武冈市','武冈市',187),(1672,'岳阳楼区','岳阳楼区',188),(1673,'云溪区','云溪区',188),(1674,'君山区','君山区',188),(1675,'岳阳县','岳阳县',188),(1676,'华容县','华容县',188),(1677,'湘阴县','湘阴县',188),(1678,'平江县','平江县',188),(1679,'汨罗市','汨罗市',188),(1680,'临湘市','临湘市',188),(1681,'武陵区','武陵区',189),(1682,'鼎城区','鼎城区',189),(1683,'安乡县','安乡县',189),(1684,'汉寿县','汉寿县',189),(1685,'澧县','澧县',189),(1686,'临澧县','临澧县',189),(1687,'桃源县','桃源县',189),(1688,'石门县','石门县',189),(1689,'津市市','津市市',189),(1690,'永定区','永定区',190),(1691,'武陵源区','武陵源区',190),(1692,'慈利县','慈利县',190),(1693,'桑植县','桑植县',190),(1694,'资阳区','资阳区',191),(1695,'赫山区','赫山区',191),(1696,'南县','南县',191),(1697,'桃江县','桃江县',191),(1698,'安化县','安化县',191),(1699,'沅江市','沅江市',191),(1700,'北湖区','北湖区',192),(1701,'苏仙区','苏仙区',192),(1702,'桂阳县','桂阳县',192),(1703,'宜章县','宜章县',192),(1704,'永兴县','永兴县',192),(1705,'嘉禾县','嘉禾县',192),(1706,'临武县','临武县',192),(1707,'汝城县','汝城县',192),(1708,'桂东县','桂东县',192),(1709,'安仁县','安仁县',192),(1710,'资兴市','资兴市',192),(1711,'芝山区','芝山区',193),(1712,'冷水滩区','冷水滩区',193),(1713,'祁阳县','祁阳县',193),(1714,'东安县','东安县',193),(1715,'双牌县','双牌县',193),(1716,'道县','道县',193),(1718,'宁远县','宁远县',193),(1719,'蓝山县','蓝山县',193),(1720,'新田县','新田县',193),(1721,'江华瑶族自治县','江华瑶族自治县',193),(1722,'鹤城区','鹤城区',194),(1723,'中方县','中方县',194),(1724,'沅陵县','沅陵县',194),(1725,'辰溪县','辰溪县',194),(1726,'溆浦县','溆浦县',194),(1727,'会同县','会同县',194),(1728,'麻阳苗族自治县','麻阳苗族自治县',194),(1729,'新晃侗族自治县','新晃侗族自治县',194),(1730,'芷江侗族自治县','芷江侗族自治县',194),(1731,'靖州苗族侗族自治县','靖州苗族侗族自治县',194),(1732,'通道侗族自治县','通道侗族自治县',194),(1733,'洪江市','洪江市',194),(1734,'娄星区','娄星区',195),(1735,'双峰县','双峰县',195),(1736,'新化县','新化县',195),(1737,'冷水江市','冷水江市',195),(1738,'涟源市','涟源市',195),(1739,'吉首市','吉首市',196),(1740,'泸溪县','泸溪县',196),(1741,'凤凰县','凤凰县',196),(1742,'花垣县','花垣县',196),(1743,'保靖县','保靖县',196),(1744,'古丈县','古丈县',196),(1745,'永顺县','永顺县',196),(1746,'龙山县','龙山县',196),(1747,'东山区','东山区',197),(1748,'荔湾区','荔湾区',197),(1749,'越秀区','越秀区',197),(1750,'海珠区','海珠区',197),(1751,'天河区','天河区',197),(1752,'芳村区','芳村区',197),(1753,'白云区','白云区',197),(1754,'黄埔区','黄埔区',197),(1755,'番禺区','番禺区',197),(1756,'花都区','花都区',197),(1757,'增城市','增城市',197),(1758,'从化市','从化市',197),(1759,'武江区','武江区',198),(1760,'浈江区','浈江区',198),(1761,'曲江区','曲江区',198),(1762,'始兴县','始兴县',198),(1763,'仁化县','仁化县',198),(1764,'翁源县','翁源县',198),(1765,'乳源瑶族自治县','乳源瑶族自治县',198),(1766,'新丰县','新丰县',198),(1767,'乐昌市','乐昌市',198),(1768,'南雄市','南雄市',198),(1769,'罗湖区','罗湖区',199),(1770,'福田区','福田区',199),(1771,'南山区','南山区',199),(1772,'宝安区','宝安区',199),(1773,'龙岗区','龙岗区',199),(1774,'盐田区','盐田区',199),(1775,'香洲区','香洲区',200),(1776,'斗门区','斗门区',200),(1777,'金湾区','金湾区',200),(1778,'龙湖区','龙湖区',201),(1779,'金平区','金平区',201),(1780,'濠江区','濠江区',201),(1781,'潮阳区','潮阳区',201),(1782,'潮南区','潮南区',201),(1783,'澄海区','澄海区',201),(1784,'南澳县','南澳县',201),(1785,'禅城区','禅城区',202),(1786,'南海区','南海区',202),(1787,'顺德区','顺德区',202),(1788,'三水区','三水区',202),(1789,'高明区','高明区',202),(1790,'蓬江区','蓬江区',203),(1791,'江海区','江海区',203),(1792,'新会区','新会区',203),(1793,'台山市','台山市',203),(1794,'开平市','开平市',203),(1795,'鹤山市','鹤山市',203),(1796,'恩平市','恩平市',203),(1797,'赤坎区','赤坎区',204),(1798,'霞山区','霞山区',204),(1799,'坡头区','坡头区',204),(1800,'麻章区','麻章区',204),(1801,'遂溪县','遂溪县',204),(1802,'徐闻县','徐闻县',204),(1803,'廉江市','廉江市',204),(1804,'雷州市','雷州市',204),(1805,'吴川市','吴川市',204),(1806,'茂南区','茂南区',205),(1807,'茂港区','茂港区',205),(1808,'电白县','电白县',205),(1809,'高州市','高州市',205),(1810,'化州市','化州市',205),(1811,'信宜市','信宜市',205),(1812,'端州区','端州区',206),(1813,'鼎湖区','鼎湖区',206),(1814,'广宁县','广宁县',206),(1815,'怀集县','怀集县',206),(1816,'封开县','封开县',206),(1817,'德庆县','德庆县',206),(1819,'四会市','四会市',206),(1820,'惠城区','惠城区',207),(1821,'惠阳区','惠阳区',207),(1822,'博罗县','博罗县',207),(1823,'惠东县','惠东县',207),(1824,'龙门县','龙门县',207),(1825,'梅江区','梅江区',208),(1826,'梅县','梅县',208),(1827,'大埔县','大埔县',208),(1828,'丰顺县','丰顺县',208),(1829,'五华县','五华县',208),(1830,'平远县','平远县',208),(1831,'蕉岭县','蕉岭县',208),(1832,'兴宁市','兴宁市',208),(1833,'城区','城区',209),(1834,'海丰县','海丰县',209),(1835,'陆河县','陆河县',209),(1836,'陆丰市','陆丰市',209),(1837,'源城区','源城区',210),(1838,'紫金县','紫金县',210),(1839,'龙川县','龙川县',210),(1840,'连平县','连平县',210),(1841,'和平县','和平县',210),(1842,'东源县','东源县',210),(1843,'江城区','江城区',211),(1844,'阳西县','阳西县',211),(1845,'阳东县','阳东县',211),(1846,'阳春市','阳春市',211),(1847,'清城区','清城区',212),(1848,'佛冈县','佛冈县',212),(1849,'阳山县','阳山县',212),(1850,'连山壮族瑶族自治县','连山壮族瑶族自治县',212),(1851,'连南瑶族自治县','连南瑶族自治县',212),(1852,'清新县','清新县',212),(1853,'英德市','英德市',212),(1854,'连州市','连州市',212),(1855,'湘桥区','湘桥区',215),(1856,'潮安县','潮安县',215),(1857,'饶平县','饶平县',215),(1858,'榕城区','榕城区',216),(1859,'揭东县','揭东县',216),(1860,'揭西县','揭西县',216),(1861,'惠来县','惠来县',216),(1862,'普宁市','普宁市',216),(1863,'云城区','云城区',217),(1864,'新兴县','新兴县',217),(1865,'郁南县','郁南县',217),(1866,'云安县','云安县',217),(1867,'罗定市','罗定市',217),(1868,'兴宁区','兴宁区',218),(1869,'青秀区','青秀区',218),(1870,'江南区','江南区',218),(1871,'西乡塘区','西乡塘区',218),(1872,'良庆区','良庆区',218),(1873,'邕宁区','邕宁区',218),(1874,'武鸣县','武鸣县',218),(1875,'隆安县','隆安县',218),(1876,'马山县','马山县',218),(1877,'上林县','上林县',218),(1878,'宾阳县','宾阳县',218),(1879,'横县','横县',218),(1880,'城中区','城中区',219),(1881,'鱼峰区','鱼峰区',219),(1882,'柳南区','柳南区',219),(1883,'柳北区','柳北区',219),(1884,'柳江县','柳江县',219),(1885,'柳城县','柳城县',219),(1886,'鹿寨县','鹿寨县',219),(1887,'融安县','融安县',219),(1888,'融水苗族自治县','融水苗族自治县',219),(1889,'三江侗族自治县','三江侗族自治县',219),(1890,'秀峰区','秀峰区',220),(1891,'叠彩区','叠彩区',220),(1892,'象山区','象山区',220),(1893,'七星区','七星区',220),(1894,'雁山区','雁山区',220),(1895,'阳朔县','阳朔县',220),(1896,'临桂县','临桂县',220),(1897,'灵川县','灵川县',220),(1898,'全州县','全州县',220),(1899,'兴安县','兴安县',220),(1900,'永福县','永福县',220),(1901,'灌阳县','灌阳县',220),(1902,'龙胜各族自治县','龙胜各族自治县',220),(1903,'资源县','资源县',220),(1904,'平乐县','平乐县',220),(1905,'荔蒲县','荔蒲县',220),(1906,'恭城瑶族自治县','恭城瑶族自治县',220),(1907,'万秀区','万秀区',221),(1908,'蝶山区','蝶山区',221),(1909,'长洲区','长洲区',221),(1910,'苍梧县','苍梧县',221),(1911,'藤县','藤县',221),(1912,'蒙山县','蒙山县',221),(1913,'岑溪市','岑溪市',221),(1914,'海城区','海城区',222),(1915,'银海区','银海区',222),(1916,'铁山港区','铁山港区',222),(1917,'合浦县','合浦县',222),(1918,'港口区','港口区',223),(1920,'上思县','上思县',223),(1921,'东兴市','东兴市',223),(1922,'钦南区','钦南区',224),(1923,'钦北区','钦北区',224),(1924,'灵山县','灵山县',224),(1925,'浦北县','浦北县',224),(1926,'港北区','港北区',225),(1927,'港南区','港南区',225),(1928,'覃塘区','覃塘区',225),(1929,'平南县','平南县',225),(1930,'桂平市','桂平市',225),(1931,'玉州区','玉州区',226),(1932,'容县','容县',226),(1933,'陆川县','陆川县',226),(1934,'博白县','博白县',226),(1935,'兴业县','兴业县',226),(1936,'北流市','北流市',226),(1937,'右江区','右江区',227),(1938,'田阳县','田阳县',227),(1939,'田东县','田东县',227),(1940,'平果县','平果县',227),(1941,'德保县','德保县',227),(1942,'靖西县','靖西县',227),(1943,'那坡县','那坡县',227),(1944,'凌云县','凌云县',227),(1945,'乐业县','乐业县',227),(1946,'田林县','田林县',227),(1947,'西林县','西林县',227),(1948,'隆林各族自治县','隆林各族自治县',227),(1949,'八步区','八步区',228),(1950,'昭平县','昭平县',228),(1951,'钟山县','钟山县',228),(1952,'富川瑶族自治县','富川瑶族自治县',228),(1953,'金城江区','金城江区',229),(1954,'南丹县','南丹县',229),(1955,'天峨县','天峨县',229),(1956,'凤山县','凤山县',229),(1957,'东兰县','东兰县',229),(1958,'罗城仫佬族自治县','罗城仫佬族自治县',229),(1959,'环江毛南族自治县','环江毛南族自治县',229),(1960,'巴马瑶族自治县','巴马瑶族自治县',229),(1961,'都安瑶族自治县','都安瑶族自治县',229),(1962,'大化瑶族自治县','大化瑶族自治县',229),(1963,'宜州市','宜州市',229),(1964,'兴宾区','兴宾区',230),(1965,'忻城县','忻城县',230),(1966,'象州县','象州县',230),(1967,'武宣县','武宣县',230),(1968,'金秀瑶族自治县','金秀瑶族自治县',230),(1969,'合山市','合山市',230),(1970,'江洲区','江洲区',231),(1971,'扶绥县','扶绥县',231),(1972,'宁明县','宁明县',231),(1973,'龙州县','龙州县',231),(1974,'大新县','大新县',231),(1975,'天等县','天等县',231),(1976,'凭祥市','凭祥市',231),(1977,'秀英区','秀英区',232),(1978,'龙华区','龙华区',232),(1979,'琼山区','琼山区',232),(1980,'美兰区','美兰区',232),(1981,'五指山市','五指山市',233),(1982,'琼海市','琼海市',233),(1983,'儋州市','儋州市',233),(1984,'文昌市','文昌市',233),(1985,'万宁市','万宁市',233),(1986,'东方市','东方市',233),(1987,'定安县','定安县',233),(1988,'屯昌县','屯昌县',233),(1989,'澄迈县','澄迈县',233),(1990,'临高县','临高县',233),(1991,'白沙黎族自治县','白沙黎族自治县',233),(1992,'昌江黎族自治县','昌江黎族自治县',233),(1993,'乐东黎族自治县','乐东黎族自治县',233),(1994,'陵水黎族自治县','陵水黎族自治县',233),(1995,'保亭黎族苗族自治县','保亭黎族苗族自治县',233),(1996,'琼中黎族苗族自治县','琼中黎族苗族自治县',233),(1997,'西沙群岛','西沙群岛',233),(1998,'南沙群岛','南沙群岛',233),(1999,'中沙群岛的岛礁及其海域','中沙群岛的岛礁及其海域',233),(2000,'万州区','万州区',234),(2001,'涪陵区','涪陵区',234),(2002,'渝中区','渝中区',234),(2003,'大渡口区','大渡口区',234),(2004,'江北区','江北区',234),(2005,'沙坪坝区','沙坪坝区',234),(2006,'九龙坡区','九龙坡区',234),(2007,'南岸区','南岸区',234),(2008,'北碚区','北碚区',234),(2009,'万盛区','万盛区',234),(2010,'双桥区','双桥区',234),(2011,'渝北区','渝北区',234),(2012,'巴南区','巴南区',234),(2013,'黔江区','黔江区',234),(2014,'长寿区','长寿区',234),(2015,'綦江县','綦江县',234),(2016,'潼南县','潼南县',234),(2017,'铜梁县','铜梁县',234),(2018,'大足县','大足县',234),(2019,'荣昌县','荣昌县',234),(2021,'梁平县','梁平县',234),(2022,'城口县','城口县',234),(2023,'丰都县','丰都县',234),(2024,'垫江县','垫江县',234),(2025,'武隆县','武隆县',234),(2026,'忠县','忠县',234),(2027,'开县','开县',234),(2028,'云阳县','云阳县',234),(2029,'奉节县','奉节县',234),(2030,'巫山县','巫山县',234),(2031,'巫溪县','巫溪县',234),(2032,'石柱土家族自治县','石柱土家族自治县',234),(2033,'秀山土家族苗族自治县','秀山土家族苗族自治县',234),(2034,'酉阳土家族苗族自治县','酉阳土家族苗族自治县',234),(2035,'彭水苗族土家族自治县','彭水苗族土家族自治县',234),(2036,'江津市','江津市',234),(2037,'合川市','合川市',234),(2038,'永川市','永川市',234),(2039,'南川市','南川市',234),(2040,'锦江区','锦江区',235),(2041,'青羊区','青羊区',235),(2042,'金牛区','金牛区',235),(2043,'武侯区','武侯区',235),(2044,'成华区','成华区',235),(2045,'龙泉驿区','龙泉驿区',235),(2046,'青白江区','青白江区',235),(2047,'新都区','新都区',235),(2048,'温江区','温江区',235),(2049,'金堂县','金堂县',235),(2050,'双流县','双流县',235),(2051,'郫县','郫县',235),(2052,'大邑县','大邑县',235),(2053,'蒲江县','蒲江县',235),(2054,'新津县','新津县',235),(2055,'都江堰市','都江堰市',235),(2056,'彭州市','彭州市',235),(2057,'邛崃市','邛崃市',235),(2058,'崇州市','崇州市',235),(2059,'自流井区','自流井区',236),(2060,'贡井区','贡井区',236),(2061,'大安区','大安区',236),(2062,'沿滩区','沿滩区',236),(2063,'荣县','荣县',236),(2064,'富顺县','富顺县',236),(2065,'东区','东区',237),(2066,'西区','西区',237),(2067,'仁和区','仁和区',237),(2068,'米易县','米易县',237),(2069,'盐边县','盐边县',237),(2070,'江阳区','江阳区',238),(2071,'纳溪区','纳溪区',238),(2072,'龙马潭区','龙马潭区',238),(2073,'泸县','泸县',238),(2074,'合江县','合江县',238),(2075,'叙永县','叙永县',238),(2076,'古蔺县','古蔺县',238),(2077,'旌阳区','旌阳区',239),(2078,'中江县','中江县',239),(2079,'罗江县','罗江县',239),(2080,'广汉市','广汉市',239),(2081,'什邡市','什邡市',239),(2082,'绵竹市','绵竹市',239),(2083,'涪城区','涪城区',240),(2084,'游仙区','游仙区',240),(2085,'三台县','三台县',240),(2086,'盐亭县','盐亭县',240),(2087,'安县','安县',240),(2088,'梓潼县','梓潼县',240),(2089,'北川羌族自治县','北川羌族自治县',240),(2090,'平武县','平武县',240),(2091,'江油市','江油市',240),(2092,'市中区','市中区',241),(2093,'元坝区','元坝区',241),(2094,'朝天区','朝天区',241),(2095,'旺苍县','旺苍县',241),(2096,'青川县','青川县',241),(2097,'剑阁县','剑阁县',241),(2098,'苍溪县','苍溪县',241),(2099,'船山区','船山区',242),(2100,'安居区','安居区',242),(2101,'蓬溪县','蓬溪县',242),(2102,'射洪县','射洪县',242),(2103,'大英县','大英县',242),(2104,'市中区','市中区',243),(2105,'东兴区','东兴区',243),(2106,'威远县','威远县',243),(2107,'资中县','资中县',243),(2108,'隆昌县','隆昌县',243),(2109,'市中区','市中区',244),(2110,'沙湾区','沙湾区',244),(2111,'五通桥区','五通桥区',244),(2112,'金口河区','金口河区',244),(2113,'犍为县','犍为县',244),(2114,'井研县','井研县',244),(2115,'夹江县','夹江县',244),(2116,'沐川县','沐川县',244),(2117,'峨边彝族自治县','峨边彝族自治县',244),(2118,'马边彝族自治县','马边彝族自治县',244),(2119,'峨眉山市','峨眉山市',244),(2120,'顺庆区','顺庆区',245),(2122,'嘉陵区','嘉陵区',245),(2123,'南部县','南部县',245),(2124,'营山县','营山县',245),(2125,'蓬安县','蓬安县',245),(2126,'仪陇县','仪陇县',245),(2127,'西充县','西充县',245),(2128,'阆中市','阆中市',245),(2129,'东坡区','东坡区',246),(2130,'仁寿县','仁寿县',246),(2131,'彭山县','彭山县',246),(2132,'洪雅县','洪雅县',246),(2133,'丹棱县','丹棱县',246),(2134,'青神县','青神县',246),(2135,'翠屏区','翠屏区',247),(2136,'宜宾县','宜宾县',247),(2137,'南溪县','南溪县',247),(2138,'江安县','江安县',247),(2139,'长宁县','长宁县',247),(2140,'高县','高县',247),(2141,'珙县','珙县',247),(2142,'筠连县','筠连县',247),(2143,'兴文县','兴文县',247),(2144,'屏山县','屏山县',247),(2145,'广安区','广安区',248),(2146,'岳池县','岳池县',248),(2147,'武胜县','武胜县',248),(2148,'邻水县','邻水县',248),(2149,'华蓥市','华蓥市',248),(2150,'通川区','通川区',249),(2151,'达县','达县',249),(2152,'宣汉县','宣汉县',249),(2153,'开江县','开江县',249),(2154,'大竹县','大竹县',249),(2155,'渠县','渠县',249),(2156,'万源市','万源市',249),(2157,'雨城区','雨城区',250),(2158,'名山县','名山县',250),(2159,'荥经县','荥经县',250),(2160,'汉源县','汉源县',250),(2161,'石棉县','石棉县',250),(2162,'天全县','天全县',250),(2163,'芦山县','芦山县',250),(2164,'宝兴县','宝兴县',250),(2165,'巴州区','巴州区',251),(2166,'通江县','通江县',251),(2167,'南江县','南江县',251),(2168,'平昌县','平昌县',251),(2169,'雁江区','雁江区',252),(2170,'安岳县','安岳县',252),(2171,'乐至县','乐至县',252),(2172,'简阳市','简阳市',252),(2173,'汶川县','汶川县',253),(2174,'理县','理县',253),(2175,'茂县','茂县',253),(2176,'松潘县','松潘县',253),(2177,'九寨沟县','九寨沟县',253),(2178,'金川县','金川县',253),(2179,'小金县','小金县',253),(2180,'黑水县','黑水县',253),(2181,'马尔康县','马尔康县',253),(2182,'壤塘县','壤塘县',253),(2183,'阿坝县','阿坝县',253),(2184,'若尔盖县','若尔盖县',253),(2185,'红原县','红原县',253),(2186,'康定县','康定县',254),(2187,'泸定县','泸定县',254),(2188,'丹巴县','丹巴县',254),(2189,'九龙县','九龙县',254),(2190,'雅江县','雅江县',254),(2191,'道孚县','道孚县',254),(2192,'炉霍县','炉霍县',254),(2193,'甘孜县','甘孜县',254),(2194,'新龙县','新龙县',254),(2195,'德格县','德格县',254),(2196,'白玉县','白玉县',254),(2197,'石渠县','石渠县',254),(2198,'色达县','色达县',254),(2199,'理塘县','理塘县',254),(2200,'巴塘县','巴塘县',254),(2201,'乡城县','乡城县',254),(2202,'稻城县','稻城县',254),(2203,'得荣县','得荣县',254),(2204,'西昌市','西昌市',255),(2205,'木里藏族自治县','木里藏族自治县',255),(2206,'盐源县','盐源县',255),(2207,'德昌县','德昌县',255),(2208,'会理县','会理县',255),(2209,'会东县','会东县',255),(2210,'宁南县','宁南县',255),(2211,'普格县','普格县',255),(2212,'布拖县','布拖县',255),(2213,'金阳县','金阳县',255),(2214,'昭觉县','昭觉县',255),(2215,'喜德县','喜德县',255),(2216,'冕宁县','冕宁县',255),(2217,'越西县','越西县',255),(2218,'甘洛县','甘洛县',255),(2219,'美姑县','美姑县',255),(2220,'雷波县','雷波县',255),(2221,'南明区','南明区',256),(2223,'花溪区','花溪区',256),(2224,'乌当区','乌当区',256),(2225,'白云区','白云区',256),(2226,'小河区','小河区',256),(2227,'开阳县','开阳县',256),(2228,'息烽县','息烽县',256),(2229,'修文县','修文县',256),(2230,'清镇市','清镇市',256),(2231,'钟山区','钟山区',257),(2232,'六枝特区','六枝特区',257),(2233,'水城县','水城县',257),(2234,'盘县','盘县',257),(2235,'红花岗区','红花岗区',258),(2236,'汇川区','汇川区',258),(2237,'遵义县','遵义县',258),(2238,'桐梓县','桐梓县',258),(2239,'绥阳县','绥阳县',258),(2240,'正安县','正安县',258),(2241,'道真仡佬族苗族自治县','道真仡佬族苗族自治县',258),(2242,'务川仡佬族苗族自治县','务川仡佬族苗族自治县',258),(2243,'凤冈县','凤冈县',258),(2244,'湄潭县','湄潭县',258),(2245,'余庆县','余庆县',258),(2246,'习水县','习水县',258),(2247,'赤水市','赤水市',258),(2248,'仁怀市','仁怀市',258),(2249,'西秀区','西秀区',259),(2250,'平坝县','平坝县',259),(2251,'普定县','普定县',259),(2252,'镇宁布依族苗族自治县','镇宁布依族苗族自治县',259),(2253,'关岭布依族苗族自治县','关岭布依族苗族自治县',259),(2254,'紫云苗族布依族自治县','紫云苗族布依族自治县',259),(2255,'铜仁市','铜仁市',260),(2256,'江口县','江口县',260),(2257,'玉屏侗族自治县','玉屏侗族自治县',260),(2258,'石阡县','石阡县',260),(2259,'思南县','思南县',260),(2260,'印江土家族苗族自治县','印江土家族苗族自治县',260),(2261,'德江县','德江县',260),(2262,'沿河土家族自治县','沿河土家族自治县',260),(2263,'松桃苗族自治县','松桃苗族自治县',260),(2264,'万山特区','万山特区',260),(2265,'兴义市','兴义市',261),(2266,'兴仁县','兴仁县',261),(2267,'普安县','普安县',261),(2268,'晴隆县','晴隆县',261),(2269,'贞丰县','贞丰县',261),(2270,'望谟县','望谟县',261),(2271,'册亨县','册亨县',261),(2272,'安龙县','安龙县',261),(2273,'毕节市','毕节市',262),(2274,'大方县','大方县',262),(2275,'黔西县','黔西县',262),(2276,'金沙县','金沙县',262),(2277,'织金县','织金县',262),(2278,'纳雍县','纳雍县',262),(2279,'威宁彝族回族苗族自治县','威宁彝族回族苗族自治县',262),(2280,'赫章县','赫章县',262),(2281,'凯里市','凯里市',263),(2282,'黄平县','黄平县',263),(2283,'施秉县','施秉县',263),(2284,'三穗县','三穗县',263),(2285,'镇远县','镇远县',263),(2286,'岑巩县','岑巩县',263),(2287,'天柱县','天柱县',263),(2288,'锦屏县','锦屏县',263),(2289,'剑河县','剑河县',263),(2290,'台江县','台江县',263),(2291,'黎平县','黎平县',263),(2292,'榕江县','榕江县',263),(2293,'从江县','从江县',263),(2294,'雷山县','雷山县',263),(2295,'麻江县','麻江县',263),(2296,'丹寨县','丹寨县',263),(2297,'都匀市','都匀市',264),(2298,'福泉市','福泉市',264),(2299,'荔波县','荔波县',264),(2300,'贵定县','贵定县',264),(2301,'瓮安县','瓮安县',264),(2302,'独山县','独山县',264),(2303,'平塘县','平塘县',264),(2304,'罗甸县','罗甸县',264),(2305,'长顺县','长顺县',264),(2306,'龙里县','龙里县',264),(2307,'惠水县','惠水县',264),(2308,'三都水族自治县','三都水族自治县',264),(2309,'五华区','五华区',265),(2310,'盘龙区','盘龙区',265),(2311,'官渡区','官渡区',265),(2312,'西山区','西山区',265),(2313,'东川区','东川区',265),(2314,'呈贡县','呈贡县',265),(2315,'晋宁县','晋宁县',265),(2316,'富民县','富民县',265),(2317,'宜良县','宜良县',265),(2318,'石林彝族自治县','石林彝族自治县',265),(2319,'嵩明县','嵩明县',265),(2320,'禄劝彝族苗族自治县','禄劝彝族苗族自治县',265),(2321,'寻甸回族彝族自治县','寻甸回族彝族自治县',265),(2322,'安宁市','安宁市',265),(2324,'马龙县','马龙县',266),(2325,'陆良县','陆良县',266),(2326,'师宗县','师宗县',266),(2327,'罗平县','罗平县',266),(2328,'富源县','富源县',266),(2329,'会泽县','会泽县',266),(2330,'沾益县','沾益县',266),(2331,'宣威市','宣威市',266),(2332,'红塔区','红塔区',267),(2333,'江川县','江川县',267),(2334,'澄江县','澄江县',267),(2335,'通海县','通海县',267),(2336,'华宁县','华宁县',267),(2337,'易门县','易门县',267),(2338,'峨山彝族自治县','峨山彝族自治县',267),(2339,'新平彝族傣族自治县','新平彝族傣族自治县',267),(2340,'元江哈尼族彝族傣族自治县','元江哈尼族彝族傣族自治县',267),(2341,'隆阳区','隆阳区',268),(2342,'施甸县','施甸县',268),(2343,'腾冲县','腾冲县',268),(2344,'龙陵县','龙陵县',268),(2345,'昌宁县','昌宁县',268),(2346,'昭阳区','昭阳区',269),(2347,'鲁甸县','鲁甸县',269),(2348,'巧家县','巧家县',269),(2349,'盐津县','盐津县',269),(2350,'大关县','大关县',269),(2351,'永善县','永善县',269),(2352,'绥江县','绥江县',269),(2353,'镇雄县','镇雄县',269),(2354,'彝良县','彝良县',269),(2355,'威信县','威信县',269),(2356,'水富县','水富县',269),(2357,'古城区','古城区',270),(2358,'玉龙纳西族自治县','玉龙纳西族自治县',270),(2359,'永胜县','永胜县',270),(2360,'华坪县','华坪县',270),(2361,'宁蒗彝族自治县','宁蒗彝族自治县',270),(2362,'翠云区','翠云区',271),(2363,'普洱哈尼族彝族自治县','普洱哈尼族彝族自治县',271),(2364,'墨江哈尼族自治县','墨江哈尼族自治县',271),(2365,'景东彝族自治县','景东彝族自治县',271),(2366,'景谷傣族彝族自治县','景谷傣族彝族自治县',271),(2367,'镇沅彝族哈尼族拉祜族自治县','镇沅彝族哈尼族拉祜族自治县',271),(2368,'江城哈尼族彝族自治县','江城哈尼族彝族自治县',271),(2369,'孟连傣族拉祜族佤族自治县','孟连傣族拉祜族佤族自治县',271),(2370,'澜沧拉祜族自治县','澜沧拉祜族自治县',271),(2371,'西盟佤族自治县','西盟佤族自治县',271),(2372,'临翔区','临翔区',272),(2373,'凤庆县','凤庆县',272),(2374,'云县','云县',272),(2375,'永德县','永德县',272),(2376,'镇康县','镇康县',272),(2377,'双江拉祜族佤族布朗族傣族自治县','双江拉祜族佤族布朗族傣族自治县',272),(2378,'耿马傣族佤族自治县','耿马傣族佤族自治县',272),(2379,'沧源佤族自治县','沧源佤族自治县',272),(2380,'楚雄市','楚雄市',273),(2381,'双柏县','双柏县',273),(2382,'牟定县','牟定县',273),(2383,'南华县','南华县',273),(2384,'姚安县','姚安县',273),(2385,'大姚县','大姚县',273),(2386,'永仁县','永仁县',273),(2387,'元谋县','元谋县',273),(2388,'武定县','武定县',273),(2389,'禄丰县','禄丰县',273),(2390,'个旧市','个旧市',274),(2391,'开远市','开远市',274),(2392,'蒙自县','蒙自县',274),(2393,'屏边苗族自治县','屏边苗族自治县',274),(2394,'建水县','建水县',274),(2395,'石屏县','石屏县',274),(2396,'弥勒县','弥勒县',274),(2397,'泸西县','泸西县',274),(2398,'元阳县','元阳县',274),(2399,'红河县','红河县',274),(2400,'金平苗族瑶族傣族自治县','金平苗族瑶族傣族自治县',274),(2401,'绿春县','绿春县',274),(2402,'河口瑶族自治县','河口瑶族自治县',274),(2403,'文山县','文山县',275),(2404,'砚山县','砚山县',275),(2405,'西畴县','西畴县',275),(2406,'麻栗坡县','麻栗坡县',275),(2407,'马关县','马关县',275),(2408,'丘北县','丘北县',275),(2409,'广南县','广南县',275),(2410,'富宁县','富宁县',275),(2411,'景洪市','景洪市',276),(2412,'勐海县','勐海县',276),(2413,'勐腊县','勐腊县',276),(2414,'大理市','大理市',277),(2415,'漾濞彝族自治县','漾濞彝族自治县',277),(2416,'祥云县','祥云县',277),(2417,'宾川县','宾川县',277),(2418,'弥渡县','弥渡县',277),(2419,'南涧彝族自治县','南涧彝族自治县',277),(2420,'巍山彝族回族自治县','巍山彝族回族自治县',277),(2421,'永平县','永平县',277),(2422,'云龙县','云龙县',277),(2423,'洱源县','洱源县',277),(2425,'鹤庆县','鹤庆县',277),(2426,'瑞丽市','瑞丽市',278),(2427,'潞西市','潞西市',278),(2428,'梁河县','梁河县',278),(2429,'盈江县','盈江县',278),(2430,'陇川县','陇川县',278),(2431,'泸水县','泸水县',279),(2432,'福贡县','福贡县',279),(2433,'贡山独龙族怒族自治县','贡山独龙族怒族自治县',279),(2434,'兰坪白族普米族自治县','兰坪白族普米族自治县',279),(2435,'香格里拉县','香格里拉县',280),(2436,'德钦县','德钦县',280),(2437,'维西傈僳族自治县','维西傈僳族自治县',280),(2438,'城关区','城关区',281),(2439,'林周县','林周县',281),(2440,'当雄县','当雄县',281),(2441,'尼木县','尼木县',281),(2442,'曲水县','曲水县',281),(2443,'堆龙德庆县','堆龙德庆县',281),(2444,'达孜县','达孜县',281),(2445,'墨竹工卡县','墨竹工卡县',281),(2446,'昌都县','昌都县',282),(2447,'江达县','江达县',282),(2448,'贡觉县','贡觉县',282),(2449,'类乌齐县','类乌齐县',282),(2450,'丁青县','丁青县',282),(2451,'察雅县','察雅县',282),(2452,'八宿县','八宿县',282),(2453,'左贡县','左贡县',282),(2454,'芒康县','芒康县',282),(2455,'洛隆县','洛隆县',282),(2456,'边坝县','边坝县',282),(2457,'乃东县','乃东县',283),(2458,'扎囊县','扎囊县',283),(2459,'贡嘎县','贡嘎县',283),(2460,'桑日县','桑日县',283),(2461,'琼结县','琼结县',283),(2462,'曲松县','曲松县',283),(2463,'措美县','措美县',283),(2464,'洛扎县','洛扎县',283),(2465,'加查县','加查县',283),(2466,'隆子县','隆子县',283),(2467,'错那县','错那县',283),(2468,'浪卡子县','浪卡子县',283),(2469,'日喀则市','日喀则市',284),(2470,'南木林县','南木林县',284),(2471,'江孜县','江孜县',284),(2472,'定日县','定日县',284),(2473,'萨迦县','萨迦县',284),(2474,'拉孜县','拉孜县',284),(2475,'昂仁县','昂仁县',284),(2476,'谢通门县','谢通门县',284),(2477,'白朗县','白朗县',284),(2478,'仁布县','仁布县',284),(2479,'康马县','康马县',284),(2480,'定结县','定结县',284),(2481,'仲巴县','仲巴县',284),(2482,'亚东县','亚东县',284),(2483,'吉隆县','吉隆县',284),(2484,'聂拉木县','聂拉木县',284),(2485,'萨嘎县','萨嘎县',284),(2486,'岗巴县','岗巴县',284),(2487,'那曲县','那曲县',285),(2488,'嘉黎县','嘉黎县',285),(2489,'比如县','比如县',285),(2490,'聂荣县','聂荣县',285),(2491,'安多县','安多县',285),(2492,'申扎县','申扎县',285),(2493,'索县','索县',285),(2494,'班戈县','班戈县',285),(2495,'巴青县','巴青县',285),(2496,'尼玛县','尼玛县',285),(2497,'普兰县','普兰县',286),(2498,'札达县','札达县',286),(2499,'噶尔县','噶尔县',286),(2500,'日土县','日土县',286),(2501,'革吉县','革吉县',286),(2502,'改则县','改则县',286),(2503,'措勤县','措勤县',286),(2504,'林芝县','林芝县',287),(2505,'工布江达县','工布江达县',287),(2506,'米林县','米林县',287),(2507,'墨脱县','墨脱县',287),(2508,'波密县','波密县',287),(2509,'察隅县','察隅县',287),(2510,'朗县','朗县',287),(2511,'新城区','新城区',288),(2512,'碑林区','碑林区',288),(2513,'莲湖区','莲湖区',288),(2514,'灞桥区','灞桥区',288),(2515,'未央区','未央区',288),(2516,'雁塔区','雁塔区',288),(2517,'阎良区','阎良区',288),(2518,'临潼区','临潼区',288),(2519,'长安区','长安区',288),(2520,'蓝田县','蓝田县',288),(2521,'周至县','周至县',288),(2522,'户县','户县',288),(2523,'高陵县','高陵县',288),(2524,'王益区','王益区',289),(2526,'耀州区','耀州区',289),(2527,'宜君县','宜君县',289),(2528,'渭滨区','渭滨区',290),(2529,'金台区','金台区',290),(2530,'陈仓区','陈仓区',290),(2531,'凤翔县','凤翔县',290),(2532,'岐山县','岐山县',290),(2533,'扶风县','扶风县',290),(2534,'眉县','眉县',290),(2535,'陇县','陇县',290),(2536,'千阳县','千阳县',290),(2537,'麟游县','麟游县',290),(2538,'凤县','凤县',290),(2539,'太白县','太白县',290),(2540,'秦都区','秦都区',291),(2541,'杨凌区','杨凌区',291),(2542,'渭城区','渭城区',291),(2543,'三原县','三原县',291),(2544,'泾阳县','泾阳县',291),(2545,'乾县','乾县',291),(2546,'礼泉县','礼泉县',291),(2547,'永寿县','永寿县',291),(2548,'彬县','彬县',291),(2549,'长武县','长武县',291),(2550,'旬邑县','旬邑县',291),(2551,'淳化县','淳化县',291),(2552,'武功县','武功县',291),(2553,'兴平市','兴平市',291),(2554,'临渭区','临渭区',292),(2555,'华县','华县',292),(2556,'潼关县','潼关县',292),(2557,'大荔县','大荔县',292),(2558,'合阳县','合阳县',292),(2559,'澄城县','澄城县',292),(2560,'蒲城县','蒲城县',292),(2561,'白水县','白水县',292),(2562,'富平县','富平县',292),(2563,'韩城市','韩城市',292),(2564,'华阴市','华阴市',292),(2565,'宝塔区','宝塔区',293),(2566,'延长县','延长县',293),(2567,'延川县','延川县',293),(2568,'子长县','子长县',293),(2569,'安塞县','安塞县',293),(2570,'志丹县','志丹县',293),(2571,'吴旗县','吴旗县',293),(2572,'甘泉县','甘泉县',293),(2573,'富县','富县',293),(2574,'洛川县','洛川县',293),(2575,'宜川县','宜川县',293),(2576,'黄龙县','黄龙县',293),(2577,'黄陵县','黄陵县',293),(2578,'汉台区','汉台区',294),(2579,'南郑县','南郑县',294),(2580,'城固县','城固县',294),(2581,'洋县','洋县',294),(2582,'西乡县','西乡县',294),(2583,'勉县','勉县',294),(2584,'宁强县','宁强县',294),(2585,'略阳县','略阳县',294),(2586,'镇巴县','镇巴县',294),(2587,'留坝县','留坝县',294),(2588,'佛坪县','佛坪县',294),(2589,'榆阳区','榆阳区',295),(2590,'神木县','神木县',295),(2591,'府谷县','府谷县',295),(2592,'横山县','横山县',295),(2593,'靖边县','靖边县',295),(2594,'定边县','定边县',295),(2595,'绥德县','绥德县',295),(2596,'米脂县','米脂县',295),(2597,'佳县','佳县',295),(2598,'吴堡县','吴堡县',295),(2599,'清涧县','清涧县',295),(2600,'子洲县','子洲县',295),(2601,'汉滨区','汉滨区',296),(2602,'汉阴县','汉阴县',296),(2603,'石泉县','石泉县',296),(2604,'宁陕县','宁陕县',296),(2605,'紫阳县','紫阳县',296),(2606,'岚皋县','岚皋县',296),(2607,'平利县','平利县',296),(2608,'镇坪县','镇坪县',296),(2609,'旬阳县','旬阳县',296),(2610,'白河县','白河县',296),(2611,'商州区','商州区',297),(2612,'洛南县','洛南县',297),(2613,'丹凤县','丹凤县',297),(2614,'商南县','商南县',297),(2615,'山阳县','山阳县',297),(2616,'镇安县','镇安县',297),(2617,'柞水县','柞水县',297),(2618,'城关区','城关区',298),(2619,'七里河区','七里河区',298),(2620,'西固区','西固区',298),(2621,'安宁区','安宁区',298),(2622,'红古区','红古区',298),(2623,'永登县','永登县',298),(2624,'皋兰县','皋兰县',298),(2625,'榆中县','榆中县',298),(2627,'永昌县','永昌县',300),(2628,'白银区','白银区',301),(2629,'平川区','平川区',301),(2630,'靖远县','靖远县',301),(2631,'会宁县','会宁县',301),(2632,'景泰县','景泰县',301),(2633,'秦城区','秦城区',302),(2634,'北道区','北道区',302),(2635,'清水县','清水县',302),(2636,'秦安县','秦安县',302),(2637,'甘谷县','甘谷县',302),(2638,'武山县','武山县',302),(2639,'张家川回族自治县','张家川回族自治县',302),(2640,'凉州区','凉州区',303),(2641,'民勤县','民勤县',303),(2642,'古浪县','古浪县',303),(2643,'天祝藏族自治县','天祝藏族自治县',303),(2644,'甘州区','甘州区',304),(2645,'肃南裕固族自治县','肃南裕固族自治县',304),(2646,'民乐县','民乐县',304),(2647,'临泽县','临泽县',304),(2648,'高台县','高台县',304),(2649,'山丹县','山丹县',304),(2650,'崆峒区','崆峒区',305),(2651,'泾川县','泾川县',305),(2652,'灵台县','灵台县',305),(2653,'崇信县','崇信县',305),(2654,'华亭县','华亭县',305),(2655,'庄浪县','庄浪县',305),(2656,'静宁县','静宁县',305),(2657,'肃州区','肃州区',306),(2658,'金塔县','金塔县',306),(2659,'安西县','安西县',306),(2660,'肃北蒙古族自治县','肃北蒙古族自治县',306),(2661,'阿克塞哈萨克族自治县','阿克塞哈萨克族自治县',306),(2662,'玉门市','玉门市',306),(2663,'敦煌市','敦煌市',306),(2664,'西峰区','西峰区',307),(2665,'庆城县','庆城县',307),(2666,'环县','环县',307),(2667,'华池县','华池县',307),(2668,'合水县','合水县',307),(2669,'正宁县','正宁县',307),(2670,'宁县','宁县',307),(2671,'镇原县','镇原县',307),(2672,'安定区','安定区',308),(2673,'通渭县','通渭县',308),(2674,'陇西县','陇西县',308),(2675,'渭源县','渭源县',308),(2676,'临洮县','临洮县',308),(2677,'漳县','漳县',308),(2678,'岷县','岷县',308),(2679,'武都区','武都区',309),(2680,'成县','成县',309),(2681,'文县','文县',309),(2682,'宕昌县','宕昌县',309),(2683,'康县','康县',309),(2684,'西和县','西和县',309),(2685,'礼县','礼县',309),(2686,'徽县','徽县',309),(2687,'两当县','两当县',309),(2688,'临夏市','临夏市',310),(2689,'临夏县','临夏县',310),(2690,'康乐县','康乐县',310),(2691,'永靖县','永靖县',310),(2692,'广河县','广河县',310),(2693,'和政县','和政县',310),(2694,'东乡族自治县','东乡族自治县',310),(2695,'积石山保安族东乡族撒拉族自治县','积石山保安族东乡族撒拉族自治县',310),(2696,'合作市','合作市',311),(2697,'临潭县','临潭县',311),(2698,'卓尼县','卓尼县',311),(2699,'舟曲县','舟曲县',311),(2700,'迭部县','迭部县',311),(2701,'玛曲县','玛曲县',311),(2702,'碌曲县','碌曲县',311),(2703,'夏河县','夏河县',311),(2704,'城东区','城东区',312),(2705,'城中区','城中区',312),(2706,'城西区','城西区',312),(2707,'城北区','城北区',312),(2708,'大通回族土族自治县','大通回族土族自治县',312),(2709,'湟中县','湟中县',312),(2710,'湟源县','湟源县',312),(2711,'平安县','平安县',313),(2712,'民和回族土族自治县','民和回族土族自治县',313),(2713,'乐都县','乐都县',313),(2714,'互助土族自治县','互助土族自治县',313),(2715,'化隆回族自治县','化隆回族自治县',313),(2716,'循化撒拉族自治县','循化撒拉族自治县',313),(2717,'门源回族自治县','门源回族自治县',314),(2718,'祁连县','祁连县',314),(2719,'海晏县','海晏县',314),(2720,'刚察县','刚察县',314),(2721,'同仁县','同仁县',315),(2722,'尖扎县','尖扎县',315),(2723,'泽库县','泽库县',315),(2724,'河南蒙古族自治县','河南蒙古族自治县',315),(2725,'共和县','共和县',316),(2726,'同德县','同德县',316),(2728,'兴海县','兴海县',316),(2729,'贵南县','贵南县',316),(2730,'玛沁县','玛沁县',317),(2731,'班玛县','班玛县',317),(2732,'甘德县','甘德县',317),(2733,'达日县','达日县',317),(2734,'久治县','久治县',317),(2735,'玛多县','玛多县',317),(2736,'玉树县','玉树县',318),(2737,'杂多县','杂多县',318),(2738,'称多县','称多县',318),(2739,'治多县','治多县',318),(2740,'囊谦县','囊谦县',318),(2741,'曲麻莱县','曲麻莱县',318),(2742,'格尔木市','格尔木市',319),(2743,'德令哈市','德令哈市',319),(2744,'乌兰县','乌兰县',319),(2745,'都兰县','都兰县',319),(2746,'天峻县','天峻县',319),(2747,'兴庆区','兴庆区',320),(2748,'西夏区','西夏区',320),(2749,'金凤区','金凤区',320),(2750,'永宁县','永宁县',320),(2751,'贺兰县','贺兰县',320),(2752,'灵武市','灵武市',320),(2753,'大武口区','大武口区',321),(2754,'惠农区','惠农区',321),(2755,'平罗县','平罗县',321),(2756,'利通区','利通区',322),(2757,'盐池县','盐池县',322),(2758,'同心县','同心县',322),(2759,'青铜峡市','青铜峡市',322),(2760,'原州区','原州区',323),(2761,'西吉县','西吉县',323),(2762,'隆德县','隆德县',323),(2763,'泾源县','泾源县',323),(2764,'彭阳县','彭阳县',323),(2765,'沙坡头区','沙坡头区',324),(2766,'中宁县','中宁县',324),(2767,'海原县','海原县',324),(2768,'天山区','天山区',325),(2769,'沙依巴克区','沙依巴克区',325),(2770,'新市区','新市区',325),(2771,'水磨沟区','水磨沟区',325),(2772,'头屯河区','头屯河区',325),(2773,'达坂城区','达坂城区',325),(2774,'东山区','东山区',325),(2775,'乌鲁木齐县','乌鲁木齐县',325),(2776,'独山子区','独山子区',326),(2777,'克拉玛依区','克拉玛依区',326),(2778,'白碱滩区','白碱滩区',326),(2779,'乌尔禾区','乌尔禾区',326),(2780,'吐鲁番市','吐鲁番市',327),(2781,'鄯善县','鄯善县',327),(2782,'托克逊县','托克逊县',327),(2783,'哈密市','哈密市',328),(2784,'巴里坤哈萨克自治县','巴里坤哈萨克自治县',328),(2785,'伊吾县','伊吾县',328),(2786,'昌吉市','昌吉市',329),(2787,'阜康市','阜康市',329),(2788,'米泉市','米泉市',329),(2789,'呼图壁县','呼图壁县',329),(2790,'玛纳斯县','玛纳斯县',329),(2791,'奇台县','奇台县',329),(2792,'吉木萨尔县','吉木萨尔县',329),(2793,'木垒哈萨克自治县','木垒哈萨克自治县',329),(2794,'博乐市','博乐市',330),(2795,'精河县','精河县',330),(2796,'温泉县','温泉县',330),(2797,'库尔勒市','库尔勒市',331),(2798,'轮台县','轮台县',331),(2799,'尉犁县','尉犁县',331),(2800,'若羌县','若羌县',331),(2801,'且末县','且末县',331),(2802,'焉耆回族自治县','焉耆回族自治县',331),(2803,'和静县','和静县',331),(2804,'和硕县','和硕县',331),(2805,'博湖县','博湖县',331),(2806,'阿克苏市','阿克苏市',332),(2807,'温宿县','温宿县',332),(2808,'库车县','库车县',332),(2809,'沙雅县','沙雅县',332),(2810,'新和县','新和县',332),(2811,'拜城县','拜城县',332),(2812,'乌什县','乌什县',332),(2813,'阿瓦提县','阿瓦提县',332),(2814,'柯坪县','柯坪县',332),(2815,'阿图什市','阿图什市',333),(2816,'阿克陶县','阿克陶县',333),(2817,'阿合奇县','阿合奇县',333),(2818,'乌恰县','乌恰县',333),(2819,'喀什市','喀什市',334),(2820,'疏附县','疏附县',334),(2821,'疏勒县','疏勒县',334),(2822,'英吉沙县','英吉沙县',334),(2823,'泽普县','泽普县',334),(2824,'莎车县','莎车县',334),(2825,'叶城县','叶城县',334),(2826,'麦盖提县','麦盖提县',334),(2827,'岳普湖县','岳普湖县',334),(2828,'伽师县','伽师县',334),(2829,'巴楚县','巴楚县',334),(2830,'塔什库尔干塔吉克自治县','塔什库尔干塔吉克自治县',334),(2831,'和田市','和田市',335),(2832,'和田县','和田县',335),(2833,'墨玉县','墨玉县',335),(2834,'皮山县','皮山县',335),(2835,'洛浦县','洛浦县',335),(2836,'策勒县','策勒县',335),(2837,'于田县','于田县',335),(2838,'民丰县','民丰县',335),(2839,'伊宁市','伊宁市',336),(2840,'奎屯市','奎屯市',336),(2841,'伊宁县','伊宁县',336),(2842,'察布查尔锡伯自治县','察布查尔锡伯自治县',336),(2843,'霍城县','霍城县',336),(2844,'巩留县','巩留县',336),(2845,'新源县','新源县',336),(2846,'昭苏县','昭苏县',336),(2847,'特克斯县','特克斯县',336),(2848,'尼勒克县','尼勒克县',336),(2849,'塔城市','塔城市',337),(2850,'乌苏市','乌苏市',337),(2851,'额敏县','额敏县',337),(2852,'沙湾县','沙湾县',337),(2853,'托里县','托里县',337),(2854,'裕民县','裕民县',337),(2855,'和布克赛尔蒙古自治县','和布克赛尔蒙古自治县',337),(2856,'阿勒泰市','阿勒泰市',338),(2857,'布尔津县','布尔津县',338),(2858,'富蕴县','富蕴县',338),(2859,'福海县','福海县',338),(2860,'哈巴河县','哈巴河县',338),(2861,'青河县','青河县',338),(2862,'吉木乃县','吉木乃县',338);
/*!40000 ALTER TABLE `district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dream`
--

DROP TABLE IF EXISTS `dream`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dream` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '梦想名称',
  `min_money` int DEFAULT NULL COMMENT '最低资金要求',
  `max_money` int DEFAULT NULL COMMENT '最高资金要求',
  `difficulty` int NOT NULL COMMENT '难度等级',
  `important_level` decimal(5,1) NOT NULL COMMENT '重要等级',
  `expect_days` int NOT NULL COMMENT '预计完成时长',
  `status` smallint NOT NULL COMMENT '状态',
  `rate` int DEFAULT NULL COMMENT '进度',
  `proposed_date` date NOT NULL COMMENT '提出日期',
  `deadline` date DEFAULT NULL COMMENT '截止日期',
  `finished_date` date DEFAULT NULL COMMENT '实现日期',
  `delays` int DEFAULT NULL,
  `date_change_history` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_plan_id` bigint DEFAULT NULL,
  `plan_value` bigint DEFAULT NULL,
  `remind` tinyint DEFAULT '0',
  `reward_point` int NOT NULL DEFAULT '0',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人生梦想表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dream`
--

LOCK TABLES `dream` WRITE;
/*!40000 ALTER TABLE `dream` DISABLE KEYS */;
/*!40000 ALTER TABLE `dream` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dream_remind`
--

DROP TABLE IF EXISTS `dream_remind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dream_remind` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `dream_id` bigint NOT NULL,
  `form_rate` int NOT NULL,
  `from_proposed_days` int NOT NULL,
  `finished_remind` tinyint NOT NULL,
  `last_remind_time` datetime DEFAULT NULL,
  `trigger_type` smallint DEFAULT NULL,
  `trigger_interval` int DEFAULT NULL,
  `remind_time` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dream_remind`
--

LOCK TABLES `dream_remind` WRITE;
/*!40000 ALTER TABLE `dream_remind` DISABLE KEYS */;
/*!40000 ALTER TABLE `dream_remind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `error_code_define`
--

DROP TABLE IF EXISTS `error_code_define`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `error_code_define` (
  `code` int NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `level` int NOT NULL,
  `notifiable` tinyint NOT NULL,
  `realtime_notify` tinyint NOT NULL,
  `loggable` tinyint NOT NULL DEFAULT '1',
  `causes` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `solutions` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `buss_type` smallint NOT NULL,
  `count` int DEFAULT '0',
  `limit_period` int DEFAULT '0',
  `url` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mobile_url` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `error_code_define`
--

LOCK TABLES `error_code_define` WRITE;
/*!40000 ALTER TABLE `error_code_define` DISABLE KEYS */;
INSERT INTO `error_code_define` VALUES (-1,'未知错误代码',2,1,0,1,'没有定义','',4,91,0,NULL,NULL,'','2019-03-04 11:04:06','2019-12-23 10:50:35'),(1,'获取对象失败(通用类型)',0,0,0,1,'主键不存在',NULL,4,19,0,NULL,NULL,NULL,'2019-03-04 10:59:12',NULL),(2,'增加对象失败(通用类型)',0,1,0,1,'字段没有设置全',NULL,4,26,0,NULL,NULL,NULL,'2019-03-03 18:03:51','2019-03-04 10:59:31'),(3,'更新对象失败(通用类型)',0,1,0,1,'未知',NULL,4,3,0,NULL,NULL,NULL,'2019-03-04 10:59:55',NULL),(4,'删除对象失败(通用类型)',0,1,0,1,'未知',NULL,4,0,0,NULL,NULL,NULL,'2019-03-04 11:00:14',NULL),(5,'获取对象列表失败(通用类型)',0,0,0,1,'未知',NULL,4,105,0,NULL,NULL,NULL,'2019-03-04 11:00:33',NULL),(6,'构建查询参数对象异常',1,1,0,1,'未知',NULL,4,0,0,NULL,NULL,NULL,'2019-03-04 11:00:57',NULL),(8,'系统异常',2,0,0,1,'未知','',4,409,0,NULL,NULL,'','2019-03-04 11:01:17','2019-12-30 13:17:17'),(9,'系统遇到未知的异常',0,1,0,1,'未知',NULL,4,230,0,NULL,NULL,NULL,'2019-03-04 11:01:40',NULL),(10,'主键冲突',2,1,0,1,'未知',NULL,4,8,0,NULL,NULL,NULL,'2019-03-04 11:02:15',NULL),(11,'Http请求异常',2,1,0,1,'网络异常或者接口无法访问',NULL,4,0,0,NULL,NULL,NULL,'2019-03-04 11:02:36',NULL),(12,'通用类型的业务操作异常',1,1,0,1,'视具体业务而定',NULL,4,27,0,NULL,NULL,NULL,'2019-03-04 11:03:13',NULL),(13,'系统启动',1,1,1,1,'系统启动',NULL,4,918,0,NULL,NULL,NULL,'2017-01-21 17:11:56','2020-01-19 15:17:03'),(10006,'用户密码错误',1,1,1,1,'用户密码错误',NULL,4,0,0,NULL,NULL,NULL,'2020-06-23 18:19:36',NULL),(10007,'未授权操作',2,1,1,1,'未授权操作',NULL,3,42,0,NULL,NULL,NULL,'2017-01-21 17:11:56','2019-03-03 17:49:51'),(10014,'FTP异常',2,1,0,1,'ftp传输异常，网络、账户等原因',NULL,4,0,0,NULL,NULL,NULL,'2019-04-21 09:53:42',NULL),(10016,'用户操作过于频繁',1,1,1,1,'用户操作过于频繁',NULL,4,0,0,NULL,NULL,NULL,'2020-06-23 18:18:38',NULL),(10017,'用户的某个功能点操作过于频繁',1,1,1,1,'用户的某个功能点操作过于频繁',NULL,4,0,0,NULL,NULL,NULL,'2020-06-23 18:18:15',NULL),(10018,'用户登录次数达到最大次数',1,1,1,1,'用户登录次数达到最大次数',NULL,4,0,0,NULL,NULL,NULL,'2020-06-23 18:17:35',NULL),(10019,'二次认证失败',1,1,1,1,'回话已经过期',NULL,4,0,0,NULL,NULL,NULL,'2020-06-23 18:17:06','2020-07-05 19:11:44'),(10020,'命令执行结果通知',0,1,0,1,'无',NULL,4,0,0,NULL,NULL,NULL,'2020-06-07 15:48:56',NULL),(10021,'命令执行异常',2,1,0,1,'脚本错误或者操作系统判断异常',NULL,4,0,0,NULL,NULL,NULL,'2020-06-07 15:49:36',NULL),(30014,'调度执行异常',2,1,0,1,'调度执行异常',NULL,4,0,0,NULL,NULL,NULL,'2019-04-01 11:24:55',NULL),(1010004,'用户操作重要功能点',2,1,1,1,'操作重要功能点',NULL,3,0,0,NULL,NULL,NULL,'2019-01-31 17:11:56',NULL),(1010005,'用户操作敏感功能点',2,1,1,1,'操作敏感功能点',NULL,3,0,0,NULL,NULL,NULL,'2019-01-31 17:11:56',NULL),(1010006,'用户登录系统',1,1,1,1,'用户登录系统',NULL,4,278,0,NULL,NULL,NULL,'2019-01-31 17:11:56',NULL),(1070103,'错误代码未定义',0,0,0,1,'没有在数据库中定义的错误代码',NULL,4,167,0,NULL,NULL,NULL,'2019-03-03 11:11:56','2020-06-11 17:42:10'),(1080104,'获取不到请求参数信息',1,0,0,0,'未知',NULL,4,2221,0,NULL,NULL,NULL,'2019-03-04 10:03:53','2019-05-23 17:41:44'),(1080107,'网络恢复正常',0,1,0,1,'网络断掉后重新连接上',NULL,4,0,0,NULL,NULL,NULL,'2020-06-08 13:14:51',NULL),(1080108,'磁盘报警',2,1,1,1,'磁盘空间不足',NULL,4,0,0,NULL,NULL,NULL,'2020-06-22 17:03:44',NULL),(1080109,'内存报警',2,1,1,1,'内存不足',NULL,4,0,0,NULL,NULL,NULL,'2020-06-22 17:04:19',NULL),(1080110,'CPU报警',2,1,1,1,'CPU过载',NULL,4,0,0,NULL,NULL,NULL,'2020-06-22 17:04:37',NULL),(1080113,'系统报警后执行自动执行任务',1,1,0,1,'系统异常，自动执行调度任务，以求恢复',NULL,4,0,0,NULL,NULL,NULL,'2020-06-22 17:19:48',NULL),(1130102,'获取股票价格异常',2,0,0,1,'网络或者新浪服务器异常',NULL,3,749,0,NULL,NULL,NULL,'2019-02-27 11:11:56','2019-04-20 08:45:43'),(1130103,'获取股票价格失败',2,1,1,1,'股票代码不存在',NULL,3,2,0,NULL,NULL,NULL,'2019-02-27 11:11:56',NULL),(1140100,'通用消息提醒代码',1,1,0,1,'针对没有配置错误代码配置的代码',NULL,6,6,0,NULL,NULL,NULL,'2019-02-27 11:11:56',NULL),(1140101,'积分奖励日统计',0,1,0,1,'积分奖励日统计',NULL,6,354,0,NULL,NULL,NULL,'2019-03-03 11:11:56',NULL),(1140102,'用户提醒类统计',0,1,0,1,'用户提醒类统计',NULL,6,1935,0,NULL,NULL,NULL,'2019-03-03 11:11:56',NULL),(1140103,'用户计划统计(未完成)',1,1,0,1,'用户计划统计(未完成)',NULL,6,549,0,'/report/notify/notifyStatList','/report/notify/stat',NULL,'2019-03-03 11:11:56','2021-01-31 08:46:02'),(1140104,'用户计划统计(已完成)',0,1,0,1,'用户计划统计(已完成)',NULL,6,86,0,'/report/notify/notifyStatList','/report/notify/stat',NULL,'2019-03-03 11:11:56','2021-01-31 08:46:16'),(1140105,'用户梦想统计(未完成)',1,1,0,1,'用户梦想统计(未完成)',NULL,6,36,0,'/dream2/dream',NULL,NULL,'2019-03-03 11:11:56','2021-01-31 09:18:52'),(1140106,'用户梦想统计(已完成)',0,1,0,1,'用户梦想统计(已完成)',NULL,6,0,0,'/dream2/dream',NULL,NULL,'2019-03-03 11:11:56','2021-01-31 09:18:56'),(1140107,'用户每日任务统计',1,1,0,1,'用户每日任务统计',NULL,6,354,0,'/data/userCalendar/myCalendar','/data/userCalendar',NULL,'2019-03-03 11:11:56','2021-01-31 09:19:27'),(1140108,'用户股票统计',0,1,0,1,'用户股票统计',NULL,6,1385,0,NULL,NULL,NULL,'2019-03-03 11:11:56',NULL),(1140109,'用户股票资产统计',0,1,0,1,'用户股票资产统计',NULL,6,24,0,NULL,NULL,NULL,'2019-03-03 11:11:56',NULL),(1140110,'用户用药统计',1,1,0,1,'用户用药统计',NULL,6,884,0,'/health/treat/treatDrug','/health/treat/treatDrug',NULL,'2019-03-03 11:11:56','2021-01-31 09:19:51'),(1140111,'执行了一句命令代码',1,1,0,1,'执行了一句命令代码',NULL,4,0,0,NULL,NULL,NULL,'2019-03-03 11:11:56',NULL),(1140112,'消息重复发送',1,0,0,1,'多线程导致重复发送',NULL,4,0,0,NULL,NULL,NULL,'2019-03-16 09:33:22','2019-03-16 09:34:03'),(1140113,'花费超出预算',1,1,0,1,'花费太多',NULL,4,7,0,'/consume/buyRecord','/consume/buyRecord',NULL,'2019-03-31 14:26:17','2021-01-31 09:20:18'),(1140114,'花费没有超出预算',0,1,0,1,'啊',NULL,4,10,0,'/fund/budget','/fund/budget/stat',NULL,'2019-03-31 14:26:34','2021-01-31 09:20:43'),(1140115,'预算没有完成',1,1,0,1,'预算记录没有记录',NULL,4,21,0,'/fund/budget','/fund/budget/stat',NULL,'2019-04-24 22:35:22','2021-01-31 09:21:00'),(1140116,'预算检查',0,1,0,1,'预算检查',NULL,4,275,0,'/fund/budget','/fund/budget/stat',NULL,'2019-04-24 22:35:48','2021-01-31 09:21:21'),(1140117,'手术复查',0,1,0,1,'手术有复查时间，但是没有去复查',NULL,4,9,0,'/health/treat/treatOperation',NULL,NULL,'2019-05-10 17:19:46','2021-01-31 09:21:38'),(1140118,'发送用户日历消息',0,1,1,1,'用户手动发送',NULL,4,0,0,'/data/userCalendar/myCalendar','/data/userCalendar',NULL,'2020-06-11 17:41:18','2021-01-31 09:22:09'),(1140119,'饮食多样性统计',0,1,0,1,'饮食多样性统计',NULL,4,0,0,NULL,NULL,NULL,'2020-07-04 09:13:45',NULL);
/*!40000 ALTER TABLE `error_code_define` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `family`
--

DROP TABLE IF EXISTS `family`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `family` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `status` smallint DEFAULT NULL COMMENT '状态',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='家庭表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `family`
--

LOCK TABLES `family` WRITE;
/*!40000 ALTER TABLE `family` DISABLE KEYS */;
/*!40000 ALTER TABLE `family` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `family_user`
--

DROP TABLE IF EXISTS `family_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `family_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `family_id` bigint NOT NULL COMMENT '家庭编号',
  `alias_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `admin` tinyint NOT NULL COMMENT '是否管理员',
  `status` smallint DEFAULT NULL COMMENT '状态',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='家庭用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `family_user`
--

LOCK TABLES `family_user` WRITE;
/*!40000 ALTER TABLE `family_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `family_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fast_menu`
--

DROP TABLE IF EXISTS `fast_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fast_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL DEFAULT '1',
  `function_id` bigint NOT NULL,
  `order_index` smallint NOT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='快捷菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fast_menu`
--

LOCK TABLES `fast_menu` WRITE;
/*!40000 ALTER TABLE `fast_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `fast_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods_lifetime`
--

DROP TABLE IF EXISTS `goods_lifetime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods_lifetime` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `keywords` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `days` int NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods_lifetime`
--

LOCK TABLES `goods_lifetime` WRITE;
/*!40000 ALTER TABLE `goods_lifetime` DISABLE KEYS */;
INSERT INTO `goods_lifetime` VALUES (1,'内衣类','内衣,内裤',180,NULL,'2020-09-28 16:36:09',NULL),(2,'袜子类','袜子',180,NULL,'2020-09-28 16:37:15',NULL),(3,'眼镜类','眼镜,墨镜,太阳镜',1095,NULL,'2020-09-28 16:38:59',NULL),(4,'秋衣秋裤类','秋衣,秋裤',730,NULL,'2020-09-28 16:39:43',NULL),(5,'电脑类','笔记本电脑,电脑',1825,NULL,'2020-09-28 16:52:31',NULL),(6,'T恤类','T恤',1095,NULL,'2020-09-30 15:17:48',NULL),(7,'硬盘类','移动硬盘,机械硬盘',3650,NULL,'2020-09-30 15:18:25',NULL),(8,'鞋子类','鞋子',730,NULL,'2020-11-20 21:52:01',NULL);
/*!40000 ALTER TABLE `goods_lifetime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods_type`
--

DROP TABLE IF EXISTS `goods_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods_type` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL DEFAULT '1',
  `pid` int DEFAULT '0' COMMENT '上级ID，支持多级，实际中只有两级，否则比较复杂',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `behavior_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '默认',
  `status` smallint NOT NULL COMMENT '状态',
  `order_index` smallint NOT NULL COMMENT '排序好',
  `statable` bit(1) DEFAULT NULL,
  `tags` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品类别';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods_type`
--

LOCK TABLES `goods_type` WRITE;
/*!40000 ALTER TABLE `goods_type` DISABLE KEYS */;
INSERT INTO `goods_type` VALUES (0,0,NULL,'根','根',1,1,_binary '',NULL),(90,0,0,'生活家居','生活家居',1,1,_binary '',NULL),(91,0,0,'旅游','旅游',1,2,_binary '',NULL),(92,0,0,'餐饮','餐饮',1,3,_binary '',NULL),(93,0,0,'交通','交通',1,4,_binary '',NULL);
/*!40000 ALTER TABLE `goods_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `income`
--

DROP TABLE IF EXISTS `income`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `income` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` smallint NOT NULL,
  `account_id` bigint DEFAULT NULL,
  `amount` decimal(9,2) NOT NULL,
  `status` smallint NOT NULL,
  `occur_time` datetime NOT NULL,
  `buy_record_id` bigint DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `income`
--

LOCK TABLES `income` WRITE;
/*!40000 ALTER TABLE `income` DISABLE KEYS */;
/*!40000 ALTER TABLE `income` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `level_config`
--

DROP TABLE IF EXISTS `level_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `level_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `level` int NOT NULL COMMENT '等级',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `points` int NOT NULL COMMENT '达到该等级的最低积分数要求',
  `points_days` int NOT NULL COMMENT '达到该等级的最低积分数的至少连续天数',
  `score` int NOT NULL COMMENT '达到该等级的最低评分要求',
  `score_days` int NOT NULL COMMENT '达到该等级的最低评分的至少连续天数',
  `role_id` bigint DEFAULT NULL COMMENT '角色编号',
  `status` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `level` (`level`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户等级配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `level_config`
--

LOCK TABLES `level_config` WRITE;
/*!40000 ALTER TABLE `level_config` DISABLE KEYS */;
INSERT INTO `level_config` VALUES (1,1,'待删除',100,1,10,1,NULL,1,NULL,'2020-08-27 15:22:27',NULL),(2,2,'降级用户',300,1,40,5,NULL,1,NULL,'2020-08-27 15:22:49',NULL),(3,3,'普通用户',100,7,60,7,NULL,1,NULL,'2020-08-27 15:23:09',NULL),(4,4,'进阶用户',5000,7,65,7,NULL,1,NULL,'2020-08-27 15:23:28',NULL);
/*!40000 ALTER TABLE `level_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `life_archives`
--

DROP TABLE IF EXISTS `life_archives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `life_archives` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `date` datetime NOT NULL COMMENT '发生时间',
  `buss_type` smallint NOT NULL COMMENT '业务类型',
  `related_beans` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联类',
  `source_id` bigint DEFAULT NULL COMMENT '源ID',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人生档案';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `life_archives`
--

LOCK TABLES `life_archives` WRITE;
/*!40000 ALTER TABLE `life_archives` DISABLE KEYS */;
/*!40000 ALTER TABLE `life_archives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `life_experience`
--

DROP TABLE IF EXISTS `life_experience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `life_experience` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` int NOT NULL COMMENT '类型',
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `days` int NOT NULL COMMENT '天数',
  `cost` decimal(9,2) NOT NULL DEFAULT '0.00',
  `tags` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `lc_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `location` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人生经历记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `life_experience`
--

LOCK TABLES `life_experience` WRITE;
/*!40000 ALTER TABLE `life_experience` DISABLE KEYS */;
/*!40000 ALTER TABLE `life_experience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `life_experience_consume`
--

DROP TABLE IF EXISTS `life_experience_consume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `life_experience_consume` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `life_experience_detail_id` bigint NOT NULL COMMENT '人生经历明细',
  `consume_type_id` bigint NOT NULL COMMENT '消费类型编号',
  `cost` decimal(9,2) NOT NULL COMMENT '花费',
  `buy_record_id` bigint DEFAULT NULL COMMENT '消费记录ID',
  `statable` bit(1) NOT NULL COMMENT '是否加入统计',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人生经历记录明细消费';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `life_experience_consume`
--

LOCK TABLES `life_experience_consume` WRITE;
/*!40000 ALTER TABLE `life_experience_consume` DISABLE KEYS */;
/*!40000 ALTER TABLE `life_experience_consume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `life_experience_detail`
--

DROP TABLE IF EXISTS `life_experience_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `life_experience_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `life_experience_id` bigint NOT NULL COMMENT '人生经历ID',
  `country_id` int DEFAULT NULL,
  `country_location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `province_id` int DEFAULT NULL,
  `city_id` int DEFAULT NULL,
  `district_id` int DEFAULT NULL,
  `occur_date` date NOT NULL,
  `start_city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '出发城市',
  `sc_location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `arrive_city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '结束城市',
  `ac_location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cost` decimal(9,2) NOT NULL DEFAULT '0.00',
  `map_stat` bit(1) NOT NULL DEFAULT b'1',
  `international` bit(1) DEFAULT b'0',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人生经历记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `life_experience_detail`
--

LOCK TABLES `life_experience_detail` WRITE;
/*!40000 ALTER TABLE `life_experience_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `life_experience_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `life_experience_sum`
--

DROP TABLE IF EXISTS `life_experience_sum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `life_experience_sum` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `year` int NOT NULL,
  `total_days` int NOT NULL DEFAULT '365',
  `work_days` int NOT NULL DEFAULT '0',
  `travel_days` int NOT NULL,
  `study_days` int NOT NULL DEFAULT '0',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `life_experience_sum`
--

LOCK TABLES `life_experience_sum` WRITE;
/*!40000 ALTER TABLE `life_experience_sum` DISABLE KEYS */;
/*!40000 ALTER TABLE `life_experience_sum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `model_config`
--

DROP TABLE IF EXISTS `model_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `model_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `file_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `du` tinyint NOT NULL,
  `algorithm` smallint NOT NULL,
  `status` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model_config`
--

LOCK TABLES `model_config` WRITE;
/*!40000 ALTER TABLE `model_config` DISABLE KEYS */;
INSERT INTO `model_config` VALUES (1,'月度预算与消费比例预测','budgetConsume.m','RandomTreeRegressor_budget_consume_m.pmml',0,3,0,NULL,'2023-06-25 10:20:22','2023-06-25 11:03:28'),(2,'年度预算与消费比例预测','budgetConsume.y','LinearRegressor_budget_consume_y.pmml',0,4,1,NULL,'2023-06-25 10:47:52','2023-06-27 20:51:11'),(3,'月度预算与消费比例预测','budgetConsume.m','LinearRegressor_budget_consume_m.pmml',0,4,1,NULL,'2023-06-25 10:50:22','2023-06-27 20:50:24');
/*!40000 ALTER TABLE `model_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music_instrument`
--

DROP TABLE IF EXISTS `music_instrument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `music_instrument` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint NOT NULL DEFAULT '1',
  `order_index` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music_instrument`
--

LOCK TABLES `music_instrument` WRITE;
/*!40000 ALTER TABLE `music_instrument` DISABLE KEYS */;
INSERT INTO `music_instrument` VALUES (11,'钢琴',0,1,NULL,'2020-02-11 18:12:55',NULL),(12,'口琴',0,2,NULL,'2020-02-11 18:13:07',NULL),(13,'小提琴',0,3,NULL,'2020-02-11 18:13:40',NULL);
/*!40000 ALTER TABLE `music_instrument` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music_practice`
--

DROP TABLE IF EXISTS `music_practice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `music_practice` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `music_instrument_id` bigint NOT NULL,
  `minutes` int NOT NULL COMMENT '时长',
  `practice_date` date NOT NULL,
  `practice_start_time` datetime DEFAULT NULL,
  `practice_end_time` datetime DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='口琴练习记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music_practice`
--

LOCK TABLES `music_practice` WRITE;
/*!40000 ALTER TABLE `music_practice` DISABLE KEYS */;
/*!40000 ALTER TABLE `music_practice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music_practice_tune`
--

DROP TABLE IF EXISTS `music_practice_tune`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `music_practice_tune` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL DEFAULT '1',
  `music_practice_id` bigint NOT NULL COMMENT '练习外键',
  `tune` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '曲子名称',
  `times` int NOT NULL COMMENT '次数',
  `level` smallint DEFAULT NULL,
  `tune_type` smallint NOT NULL DEFAULT '0',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `AK_Key_1` (`id`),
  KEY `music_practice_fk1` (`music_practice_id`),
  CONSTRAINT `music_practice_fk1` FOREIGN KEY (`music_practice_id`) REFERENCES `music_practice` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='口琴练习曲目';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music_practice_tune`
--

LOCK TABLES `music_practice_tune` WRITE;
/*!40000 ALTER TABLE `music_practice_tune` DISABLE KEYS */;
/*!40000 ALTER TABLE `music_practice_tune` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notify_config`
--

DROP TABLE IF EXISTS `notify_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notify_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sql_type` smallint NOT NULL COMMENT '查询语句类型0:sql,1:hql',
  `sql_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `result_type` smallint NOT NULL COMMENT '返回结果类型:0:日期, 1：数字',
  `value_type` smallint DEFAULT NULL COMMENT '值的类型:0天，1小时，2分钟',
  `order_index` smallint DEFAULT NULL COMMENT '排序号',
  `notify_type` smallint DEFAULT NULL COMMENT '提醒类型:0告警类1完成类',
  `status` smallint NOT NULL COMMENT '状态',
  `user_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `related_beans` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `level` int NOT NULL DEFAULT '3',
  `reward_point` int NOT NULL DEFAULT '0',
  `buss_key` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `default_calendar_title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `url` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tab_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notify_config`
--

LOCK TABLES `notify_config` WRITE;
/*!40000 ALTER TABLE `notify_config` DISABLE KEYS */;
INSERT INTO `notify_config` VALUES (1,'统计最近一次某项运动','距离上一次{某项运动}已过去',0,'select max(exercise_date) as resultValue from sport_exercise where sport_type_id={0}',0,0,8,0,1,'user_id','SportExercise',3,10,'SportExercise','锻炼身体','SportExercise','锻炼管理',NULL,'2017-02-02 16:17:31','2020-12-10 10:11:04'),(2,'统计最后一次练习某种乐器','距离上一次练习{乐器}已过去',0,'select max(practice_date) as resultValue from music_practice \n where music_instrument_id={0}',0,0,5,0,1,'user_id','MusicPractice',3,10,'MusicPractice','练习乐器','MusicPractice','音乐练习记录管理',NULL,'2017-02-02 17:16:08','2020-12-10 10:08:54'),(3,'统计最近一个月加班时长','最近一个月加班',0,'select round(sum(hours)) as resultValue from work_overtime where work_date >= date_sub(NOW(), interval 1 MONTH) ',1,1,6,0,1,'user_id','WorkOvertime',3,10,'WorkOvertime','按时下班','WorkOvertime','加班记录管理',NULL,'2017-02-02 17:37:12','2020-12-10 10:09:34'),(4,'统计最后一次在医院某个科室看病的时间','距离上一次{某个科室}已过去',0,'select max(treat_date) as resultValue from treat_record where department=\'\'{0}\'\'',0,0,3,0,1,'user_id','TreatRecord',3,10,'TreatRecord','注意身体','TreatRecord','看病记录管理','','2017-02-02 17:48:56','2020-12-10 18:01:18'),(5,'统计今年还未实现的梦想','今年未实现的梦想',0,'select count(0) from dream where status in (0,1)  and year(proposed_date)<=year(NOW())',1,3,1,0,1,'user_id','Dream',3,10,'Dream','完成梦想','Dream','梦想管理','','2017-02-02 17:56:59','2020-12-10 10:04:59'),(6,'统计所有未实现梦想','人生还未实现的梦想还有',0,'select count(0) from dream where status in (0,1) ',1,3,8,0,1,'user_id','Dream',3,10,'Dream','实现梦想','Dream','梦想管理',NULL,'2017-02-02 17:58:38','2020-12-10 10:11:17'),(7,'统计最近一次旅行','距离上一次旅行已经过去',0,'select max(start_date) as resultValue from life_experience where type=2',0,0,9,0,1,'user_id','LifeExperience',3,10,'LifeExperience2','出去旅行','LifeExperience','人生经历管理',NULL,'2017-02-02 18:35:49','2020-12-10 10:11:41'),(8,'统计最近一年某项手术数量','最近一年在医院{某项手术}检查',0,'select count(0) as resultValue from treat_operation t1,treat_record t2 where t1.treat_record_id=t2.id and t2.treat_date>= date_sub(NOW(), interval 1 YEAR)  and t1.name like \'\'%{0}%\'\'',1,6,7,0,1,'t1.user_id','TreatRecord',3,10,'TreatOperation','注意身体','TreatOperation','手术管理','','2017-02-02 20:44:37','2020-12-10 10:10:04'),(9,'统计最近三个月看病次数','最近三个月看病次数',0,'select count(0) as resultValue from treat_record where treat_date >= date_sub(NOW(), interval 3 MONTH)  and treat_type=0',1,6,5,0,1,'user_id','TreatRecord',3,10,'TreatRecord','注意身体','TreatRecord','看病记录管理',NULL,'2017-02-03 11:53:39','2020-12-10 10:08:41'),(10,'统计今年梦想完成进度','今年梦想完成进度',0,'select round(rate*100/(n*100))  from (\n select sum(rate) as rate, count(0) as n from dream where status in (0,1,2) \n and user_id=?0 and year(proposed_date)=year(NOW()) ) as aa',1,4,4,1,1,'user_id','Dream',3,10,'Dream','','Dream','梦想管理','','2017-02-03 12:59:51','2020-12-10 10:06:57'),(11,'统计今年的某项运动计划','今年{某项运动}计划已经完成',0,'select round(sum(kilometres)) as resultValue  from sport_exercise \n where sport_type_id={0} and year(exercise_date)=year(NOW()) ',1,5,6,1,1,'user_id','SportExercise',3,10,'SportExercise','锻炼身体','SportExercise','锻炼管理',NULL,'2017-02-03 13:21:01','2020-12-10 10:09:09'),(12,'统计去过的国家数','一生去过的国家数',0,'select count(0) from (select distinct country_id from life_experience_detail where user_id=?0 ) as aa',1,3,10,1,1,'user_id','LifeExperience',3,10,'LifeExperienceDetail','出去旅行','LifeExperience','人生经历管理','','2017-02-03 13:35:24','2023-07-14 20:37:09'),(13,'统计今年的花费','今年已经花费',0,'select round(sum(total_price)) as resultValue from buy_record where  year(buy_date)=year(NOW())  and price >=0',1,7,11,0,1,'user_id','BuyRecord',3,10,'BuyRecord','克制消费','BuyRecord','消费记录管理',NULL,'2017-02-03 13:45:24','2020-12-10 10:12:20'),(14,'统计一生去过的国内省份/直辖市','一生去过的国内省份/直辖市',0,'select count(0) from (select distinct province_id from life_experience_detail where country_id=290 and user_id=?0 ) as aa',1,3,13,1,1,'user_id','LifeExperience',3,10,'LifeExperienceChina','该去国内旅行了','LifeExperience','人生经历管理','中国的名称必须要用英文,百度地图统计时是这个名称，中文目前还未找到方法','2017-02-03 14:23:25','2020-12-10 10:12:37'),(15,'统计今年已经读的书籍','今年已经读过书籍',0,'select count(0) as resultValue from reading_record where status=2 and  year(finished_date)=year(NOW())',1,8,7,1,1,'user_id','ReadingRecord',3,10,'ReadingRecord','多读书','ReadingRecord','阅读管理',NULL,'2017-02-03 15:08:45','2020-12-10 10:09:50'),(16,'统计最近一个月练习某种乐器时长','最近一个月练习{某种乐器}时长',0,'select round(sum(minutes)/60) as resultValue from music_practice where  practice_date>=date_sub(NOW(), interval 1 MONTH)  and music_instrument_id={0}',1,1,5,1,1,'user_id','MusicPractice',3,10,'MusicPractice','练习乐器','MusicPractice','音乐练习记录管理',NULL,'2017-02-03 15:27:52','2020-12-10 10:08:23'),(17,'统计最近一个月身体不适次数','最近一个月身体不适次数',0,'select count(0) as resultValue from body_abnormal_record where occur_date >= date_sub(NOW(), interval 1 MONTH) ',1,6,7,0,1,'user_id','TreatRecord',3,10,'BodyAbnormalRecord','注意身体','BodyAbnormalRecord','身体不适管理',NULL,'2017-02-03 16:04:11','2020-12-10 10:10:22'),(18,'统计最容易生病的器官','最容易生病的器官',0,'select organ,count(0) n  from treat_record where is_sick=1 and user_id=?0 group by organ order by n desc limit 1',3,6,6,0,1,'user_id','TreatRecord',3,10,'TreatRecord','注意身体','TreatRecord','看病记录管理','','2017-02-03 22:36:55','2020-12-10 10:09:23'),(19,'统计最近一年看的最多的病','最近一年看的最多的病',0,'select disease,count(0) n  from treat_record where treat_date>= date_sub(NOW(), interval 1 YEAR) and user_id=?0 group by disease order by n desc limit 1',3,6,5,0,1,'user_id','TreatRecord',3,10,'TreatRecord','注意身体','TreatRecord','看病记录管理','','2017-02-03 22:45:24','2020-12-10 10:08:04'),(20,'统计最近一次回家','距离上一次回家已过去',0,'select max(buy_date) as resultValue from buy_record where keywords like \'%回家%\'',0,0,7,0,1,'user_id','LifeExperience',3,10,'backup_home','该回家了','BuyRecord ','回家次数统计',NULL,'2017-08-09 17:22:42','2020-12-10 10:10:37'),(21,'统计所有未完成的某项运动里程碑','{某项运动}里程碑未完成的还有',0,'select count(*) from sport_milestone where sport_exercise_id is null and sport_type_id={0}',1,3,10,0,0,'user_id','SportExercise',3,10,'SportMilestone','注意锻炼效果','SportMilestone','运动里程碑管理',NULL,'2017-08-10 16:02:21','2020-12-10 10:11:53'),(22,'统计最近30天某餐没吃的次数','最近30天{某餐}没吃的次数',0,'select 30-n as days from (\n SELECT count(0) as n FROM diet where occur_time>=date_sub(NOW(), interval 30 DAY) and diet_type={0} and user_id=?0 ) as aa',1,6,22,0,1,'user_id','Diet',3,10,'Diet','按时吃饭','Diet','饮食管理','','2018-01-04 15:29:36','2020-12-10 10:12:49'),(23,'统计最近一次看书','距离上一次看书已过去',0,'SELECT max(read_time) FROM reading_record_detail',0,0,7,0,1,'user_id','ReadingRecord',3,10,'ReadingRecordDetail','看书','ReadingRecord','阅读管理',NULL,'2018-01-07 15:39:52','2020-12-10 10:10:51'),(24,'统计最近一次运动锻炼','距离上一次锻炼已过去',0,'select max(exercise_date) as resultValue from sport_exercise where 1=1 ',0,0,8,0,1,'user_id','SportExercise',3,10,'SportExercise','锻炼身体','SportExercise','锻炼管理',NULL,'2018-01-20 09:45:03','2020-12-10 10:11:29'),(25,'统计最近一个月饮食的食物种类数','最近一个月饮食的食物种类数',0,'select count(0) as resultValue from  \n ( select distinct substring_index(substring_index(a.foods,\',\',b.help_topic_id+1),\',\',-1)  as name \n from diet a join mysql.help_topic b on b.help_topic_id < (length(a.foods) - length(replace(a.foods,\',\',\'\'))+1)  \n where occur_time >= date_sub(NOW(), interval 1 WEEK) \n   and a.user_id=?0  ) \n as res ',1,9,25,1,1,'user_id','Diet',3,10,'DietCounts','请注意饮食多样性','Diet','饮食管理','','2018-02-20 10:51:42','2020-12-10 10:13:06'),(26,'统计最近一个月某餐饮食的食物种类数','最近一个月某餐饮食的食物种类数',0,'select count(0) as resultValue from  \n ( select distinct substring_index(substring_index(a.foods,\'\',\'\',b.help_topic_id+1),\'\',\'\',-1)  as name \n from diet a join mysql.help_topic b on b.help_topic_id < (length(a.foods) - length(replace(a.foods,\'\',\'\',\'\'\'\'))+1)  \n where occur_time >= date_sub(NOW(), interval 1 WEEK) \n and diet_type={0}  and a.user_id=?0  ) \n as res ',1,9,26,1,1,'user_id','Diet',3,10,'DietCounts','请注意饮食多样性','Diet','饮食管理','涉及到参数的绑定替换，sql中的单个单引号全部写成两个单引号','2018-02-20 11:07:34','2020-12-10 10:13:21'),(27,'统计最后一次某项通用记录（大于）','距离{某项通用记录}已过去（大于）',0,'select max(occur_time) as resultValue from common_record \n where common_record_type_id={0}',0,0,27,1,1,'user_id','CommonRecord',3,10,'CommonRecord','{某项通用记录}','CommonRecord','通用记录管理',NULL,'2018-02-20 16:39:38','2020-12-10 10:13:33'),(28,'统计最后一次音乐练习','统计最后一次音乐练习',0,'select max(practice_date) as resultValue from music_practice ',0,0,5,0,1,'user_id','MusicPractice',3,10,'music_practice','练习乐器','MusicPractice','音乐练习记录管理',NULL,'2018-02-20 19:53:23','2020-12-10 10:07:50'),(29,'统计最近一次看的病','统计最近一次看的病',0,'select diagnosed_disease,treat_date from treat_record where user_id=?0 order by treat_date desc limit 1',2,6,5,0,1,'user_id','TreatRecord',3,10,'TreatRecord','','TreatRecord','看病记录管理','','2018-02-21 10:36:39','2020-01-08 13:55:53'),(30,'统计最后一次在医院看某个病的时间','距离上一次{看某个病}已过去',0,'select max(treat_date) as resultValue from treat_record where disease=\'\'{0}\'\'',0,0,3,0,1,'user_id','TreatRecord',3,10,'TreatRecord','注意身体','TreatRecord','看病记录管理','','2018-02-25 11:54:15','2020-12-10 10:06:34'),(32,'统计最近一个月看书时长','最近一个月看书时长',0,'select round(sum(minutes)/60) as resultValue from reading_record_detail where  read_time>=date_sub(NOW(), interval 1 MONTH) ',1,1,5,1,1,'user_id','ReadingRecord',3,10,'ReadingRecordDetail','看书','/read/readingRecordList.html','阅读管理',NULL,'2018-03-03 09:13:02','2020-01-08 13:56:09'),(33,'统计最近一次统计身体基本数据','距离上一次统计身体基本数据',0,'select max(record_date) as resultValue from body_basic_info where 1=1 ',0,0,32,0,1,'user_id','TreatRecord',3,10,'BodyBasicInfo',NULL,'BodyBasicInfo','身体基本情况管理',NULL,'2018-03-04 21:07:31','2020-12-10 10:14:25'),(34,'统计最后一次在医院看某种看病类型的时间','距离上一次{看某种看病类型}已过去',0,'select max(treat_date) as resultValue from treat_record where treat_type={0}',0,0,3,0,1,'user_id','TreatRecord',3,10,'TreatRecord','注意身体','TreatRecord','看病记录管理','','2018-07-07 21:25:08','2020-12-10 10:05:48'),(35,'统计最后一次某个关键字的消费记录','距离上一次{某种消费}已过去',0,'select max(buy_date) as resultValue from buy_record where keywords=\'\'{0}\'\'',0,0,3,0,1,'user_id','BuyRecord',3,10,'BuyRecord','注意检查','BuyRecord','消费管理','','2018-09-01 07:33:52','2020-12-10 10:05:32'),(36,'统计某种餐次类型吃的最多的食物','统计某种餐次类型吃的最多的食物',0,'select name,count(0) as totalCount from  ( select substring_index(substring_index(a.foods,\'\',\'\',b.help_topic_id+1),\'\',\'\',-1)  as name from diet a\n  join mysql.help_topic b on b.help_topic_id < (length(a.foods) - length(replace(a.foods,\'\',\'\',\'\'\'\'))+1)  \n  where 1=1  \n  and a.diet_type = {0}  and a.user_id=?0  \n  ) as res \n  group by name  order by totalCount desc limit 1',3,6,30,0,1,'user_id','Diet',3,0,'Diet','注意饮食','Diet','饮食管理','','2019-01-08 17:40:16','2020-12-10 10:14:00'),(38,'统计某种食物类型吃的最多的食物','统计某种食物类型吃的最多的食物',0,'select name,count(0) as totalCount from  ( select substring_index(substring_index(a.foods,\'\',\'\',b.help_topic_id+1),\'\',\'\',-1)  as name from diet a\n  join mysql.help_topic b on b.help_topic_id < (length(a.foods) - length(replace(a.foods,\'\',\'\',\'\'\'\'))+1)  \n  where 1=1  \n  and a.food_type = {0}  and a.user_id=?0  \n  ) as res \n  group by name  order by totalCount desc limit 1',3,6,31,0,1,'user_id','Diet',3,0,'Diet','注意饮食','Diet','饮食管理','','2019-01-08 18:10:46','2020-12-10 10:14:14'),(40,'统计某种食物来源吃的最多的食物','统计某种食物来源吃的最多的食物',0,'select name,count(0) as totalCount from  ( select substring_index(substring_index(a.foods,\'\',\'\',b.help_topic_id+1),\'\',\'\',-1)  as name from diet a\n  join mysql.help_topic b on b.help_topic_id < (length(a.foods) - length(replace(a.foods,\'\',\'\',\'\'\'\'))+1)  \n  where 1=1  \n  and a.diet_source = {0}  and a.user_id=?0  \n  ) as res \n  group by name  order by totalCount desc limit 1',3,6,38,0,1,'user_id','Diet',3,0,'Diet','注意饮食','Diet','饮食管理','','2019-01-08 18:32:15','2020-12-10 10:14:40'),(41,'统计最后一次某项通用记录（小于）','距离{某项通用记录}已过去（小于）',0,'select max(occur_time) as resultValue from common_record \n where common_record_type_id={0}',0,0,27,0,1,'user_id','CommonRecord',3,10,'CommonRecord','{某项通用记录}','CommonRecord','通用记录管理',NULL,'2019-06-06 10:04:17','2020-12-10 10:13:45'),(42,'统计使用时间最长的商品','使用时间最长的商品',0,'select goods_name,datediff(delete_date,buy_date) as days FROM buy_record where delete_date is not null and user_id = ?0\n order by days desc limit 1',3,0,39,0,1,'user_id','BuyRecord',3,0,'BuyRecord','注意商品使用周期','BuyRecord','消费记录管理','','2019-07-02 21:58:49','2020-12-10 10:14:52'),(43,'统计使用时间最短的商品','使用时间最短的商品',0,'select goods_name,datediff(delete_date,buy_date) as days FROM buy_record where delete_date is not null and user_id = ?0\n order by days asc limit 1',3,0,40,1,1,'user_id','BuyRecord',3,0,'BuyRecord','注意商品使用周期','BuyRecord','消费记录管理','','2019-07-02 22:01:38','2020-12-10 10:15:03');
/*!40000 ALTER TABLE `notify_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation_log`
--

DROP TABLE IF EXISTS `operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operation_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `username` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `system_function_id` bigint DEFAULT NULL,
  `url_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `method` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `occur_start_time` datetime DEFAULT NULL,
  `occur_end_time` datetime NOT NULL,
  `store_time` datetime NOT NULL,
  `handle_duration` int NOT NULL DEFAULT '0',
  `store_duration` int NOT NULL DEFAULT '0',
  `ip_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `location_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mac_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `host_ip_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `paras` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_value` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `return_data` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation_log`
--

LOCK TABLES `operation_log` WRITE;
/*!40000 ALTER TABLE `operation_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `operation_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_config`
--

DROP TABLE IF EXISTS `plan_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `plan_type` smallint NOT NULL,
  `sql_type` smallint NOT NULL,
  `sql_content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `date_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `unit` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `compare_type` smallint NOT NULL,
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  `related_beans` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `default_plan_count_value` bigint DEFAULT '0',
  `default_plan_value` bigint NOT NULL DEFAULT '20',
  `level` int NOT NULL DEFAULT '3',
  `reward_point` int NOT NULL DEFAULT '0',
  `buss_key` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `default_calendar_title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `url` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_config`
--

LOCK TABLES `plan_config` WRITE;
/*!40000 ALTER TABLE `plan_config` DISABLE KEYS */;
INSERT INTO `plan_config` VALUES (2,'年度某项运动计划','年度某项运动计划',4,0,'select count(*) as c,round(sum(kilometres)) as resultValue  from sport_exercise \r\nwhere sport_type_id={0}','exercise_date','user_id','公里',0,1,2,'SportExercise',108,1080,3,10,'SportExercise',NULL,'SportExercise',NULL,'2017-08-27 15:20:43','2020-12-10 11:25:28'),(3,'月度读书计划','月度读书计划',2,0,'select count(*) as c,count(0) as vv from reading_record \r\nwhere status=2','finished_date','user_id','本',0,1,3,'ReadingRecord',1,1,3,10,'ReadingRecord',NULL,'ReadingRecord ','本数就是count值','2017-08-27 16:53:18','2020-12-10 11:19:34'),(4,'月度某项乐器练习计划','月度某项乐器练习计划',2,0,'select count(*) as c,floor(sum(minutes)/60) as resultValue from music_practice \r\nwhere music_instrument_id={0}\r\n','practice_date','user_id','小时',0,1,4,'MusicPractice',16,16,3,10,'MusicPractice',NULL,'MusicPractice',NULL,'2017-08-27 17:25:41','2020-12-10 11:19:59'),(5,'年度看病限制计划','年度看病限制计划',4,0,'select count(*) as c,round(sum(total_fee)) as resultValue from treat_record ','treat_date','user_id','元',1,1,5,'TreatRecord',3,1000,3,10,'TreatRecord',NULL,'TreatRecord','算总的金额（个人支付+医保支付）','2017-08-27 17:41:48','2020-12-10 11:20:36'),(11,'年度某项消费记录计划(两层分类)','年度某项消费记录计划(两层分类)',4,0,'select count(*) as c,round(sum(total_price)) as resultValue from buy_record where goods_type_id={0}  and sub_goods_type_id={1} ','buy_date','user_id','元',0,1,8,'BuyRecord',6,1000,3,10,'BuyRecord',NULL,'BuyRecord',NULL,'2017-08-27 21:38:44','2020-12-10 11:21:41'),(12,'年度旅行计划','年度旅行计划',4,0,'select count(*) as c,sum(days) as resultValue from life_experience \r\nwhere type=2','start_date','user_id','天',0,1,7,'LifeExperience',2,6,3,10,'LifeExperience2',NULL,'LifeExperience',NULL,'2017-08-28 13:32:29','2020-12-10 11:21:26'),(13,'年度梦想实现计划','年度梦想实现计划',4,0,'select count(*) as c,count(0) as v  from dream \r\nwhere status=2','finished_date','user_id','个',0,1,8,'Dream',3,3,3,10,'Dream',NULL,'Dream',NULL,'2017-08-28 13:40:11','2020-12-10 11:21:58'),(14,'年度用药限制计划','年度用药限制计划',4,0,'select count(0) as cc,sum(td.amount) as vv from treat_drug td,treat_record tr \r\nwhere td.treat_record_id=tr.id','tr.treat_date',' tr.user_id','个',1,1,8,'TreatRecord',5,5,3,10,'TreatDrug',NULL,'TreatDrug',NULL,'2017-08-28 14:24:02','2020-12-10 11:22:10'),(15,'年度体检计划','年度体检计划',4,0,'select count(*) as c,count(0) as vv from treat_record \r\nwhere department=\'体检中心\'','treat_date','user_id','次',0,1,10,'TreatRecord',0,20,3,10,'TreatRecord',NULL,'TreatRecord',NULL,'2017-08-28 14:39:34','2020-12-10 11:22:37'),(17,'年度某项手术限制计划','年度某项手术限制计划',4,0,'select count(0) as cc,count(0) as vv from treat_operation t1,treat_record t2 where t1.treat_record_id=t2.id and t1.name=\'\'{0}\'\'','t2.treat_date','t1.user_id','次',1,1,5,'TreatRecord',1,1,3,10,'TreatOperation','','TreatOperation','','2017-08-28 16:33:00','2020-12-10 11:20:21'),(18,'年度日记计划','年度日记计划',4,0,'select sum(pieces) as c,sum(words) as resultValue  from diary ','first_day','user_id','字数',0,1,2,'Diary',1,10000,3,10,'Diary',NULL,'Diary',NULL,'2017-08-29 11:31:02','2020-12-10 11:19:20'),(19,'年度某项乐器练习计划','年度某项乐器练习计划',4,0,'select count(*) as c,floor(sum(minutes)/60) as resultValue from music_practice \r\nwhere music_instrument_id={0}\r\n','practice_date','user_id','小时',0,1,4,'MusicPractice',192,192,3,10,'MusicPractice',NULL,'MusicPractice',NULL,'2017-08-29 11:49:00','2020-12-10 11:20:03'),(21,'年度读书计划','年度读书计划',4,0,'select count(*) as c,count(0) as vv from reading_record \r\nwhere status=2','finished_date','user_id','本',0,1,3,'ReadingRecord',12,12,3,10,'ReadingRecord',NULL,'ReadingRecord ','本数就是count值','2017-08-31 11:22:16','2020-12-10 11:19:39'),(22,'年度肾检查计划','年度肾检查计划',4,0,'select count(0) as cc,count(0) as vv from treat_operation t1,treat_record t2 where t1.treat_record_id=t2.id \r\nand  (t1.name =\'B超\' or t1.name =\'b超\') \r\nand t2.organ=\'肾\'','t2.treat_date','t1.user_id','次',0,1,5,'TreatRecord',1,1,3,10,'TreatOperation',NULL,'TreatRecord',NULL,'2017-08-31 23:00:08','2020-12-10 11:20:41'),(23,'月度加班限制计划','月度加班限制计划',2,0,'select count(*) as c,round(sum(hours)) as resultValue from work_overtime ','work_date','user_id','小时',1,1,6,'WorkOvertime',9,36,3,10,'WorkOvertime',NULL,'WorkOvertime',NULL,'2017-09-05 12:34:29','2020-12-10 11:20:52'),(25,'月度锻炼计划','月度锻炼计划',2,0,'select count(*) as c,round(sum(kilometres)) as resultValue from sport_exercise ','exercise_date','user_id','公里',0,1,1,'SportExercise',0,20,3,10,'SportExercise','注意锻炼身体','SportExercise',NULL,'2017-11-04 21:20:28','2020-12-10 11:25:15'),(26,'月度某项通用记录次数限制计划','月度某项通用记录次数限制计划',2,0,'select count(0) as c,count(0) as resultValue from common_record  where common_record_type_id={0}','occur_time','user_id','次数',1,1,7,'CommonRecord',10,10,3,10,'CommonRecord',NULL,'CommonRecord',NULL,'2017-11-17 19:35:03','2020-12-10 11:21:07'),(27,'月度某项运动计划','月度某项运动计划',2,0,'select count(*) as c,round(sum(kilometres)) as resultValue from sport_exercise \r\nwhere  sport_type_id={0}','exercise_date','user_id','公里',0,1,1,'SportExercise',4,80,3,10,'SportExercise','锻炼身体','SportExercise','','2017-11-19 21:49:01','2020-12-10 11:25:09'),(28,'年度某项消费记录计划(一层分类)','年度某项消费记录计划(一层分类)',4,0,'select count(*) as c,round(sum(total_price)) as resultValue from buy_record where goods_type_id={0} ','buy_date','user_id','元',0,1,8,'BuyRecord',6,1000,3,10,'BuyRecord',NULL,'BuyRecord',NULL,'2017-11-22 22:51:19','2020-12-10 11:21:52'),(29,'月度睡觉时长计划','月度睡觉时长计划',2,0,'select count(0) as c, count(0) as vv from sleep \r\nwhere total_minutes <60* {0}','sleep_date','user_id','次',1,1,1,'Sleep',3,3,3,10,'Sleep',NULL,'Sleep',NULL,'2018-02-17 23:50:58','2020-12-10 11:18:34'),(30,'月度早睡计划','月度早睡计划',2,0,'select count(0) as c, count(0) as vv  from sleep where  hour(sleep_time)<={0}','sleep_time','user_id','次',0,1,1,'Sleep',3,3,3,10,'Sleep',NULL,'Sleep',NULL,'2018-02-20 14:30:37','2020-12-10 11:18:24'),(31,'月度消费预算','月度消费预算',2,0,'select count(*) as c,round(sum(total_price)) as resultValue from buy_record','buy_date','user_id','元',1,1,21,'BuyRecord',6,1000,3,10,'BuyRecord',NULL,'BuyRecord',NULL,'2019-02-04 16:28:12','2020-12-10 11:22:51'),(32,'月度某项通用记录次数大于计划','月度某项通用记录次数大于计划',2,0,'select count(0) as c,count(0) as resultValue from common_record  where common_record_type_id={0}','occur_time','user_id','次数',0,1,8,'CommonRecord',10,10,3,10,'CommonRecord',NULL,'CommonRecord',NULL,'2020-03-20 08:05:57','2020-12-10 11:22:26'),(33,'年度某项通用记录次数限制计划','年度某项通用记录次数限制计划',4,0,'select count(0) as c,count(0) as resultValue from common_record  where common_record_type_id={0}','occur_time','user_id','次数',1,1,24,'CommonRecord',10,10,3,10,'CommonRecord',NULL,'CommonRecord',NULL,'2020-05-17 10:25:57','2020-12-10 11:23:04'),(34,'年度某项通用记录次数大于计划','年度某项通用记录次数大于计划',4,0,'select count(0) as c,count(0) as resultValue from common_record  where common_record_type_id={0}','occur_time','user_id','次数',0,1,25,'CommonRecord',10,10,3,10,'CommonRecord',NULL,'CommonRecord',NULL,'2020-05-17 10:27:12','2020-12-10 11:23:08');
/*!40000 ALTER TABLE `plan_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_report`
--

DROP TABLE IF EXISTS `plan_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_report` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `buss_day` int NOT NULL,
  `buss_stat_date` date NOT NULL,
  `user_plan_id` bigint NOT NULL,
  `report_count_value` bigint NOT NULL,
  `report_value` bigint NOT NULL,
  `count_value_stat_result` smallint DEFAULT NULL,
  `value_stat_result` smallint DEFAULT NULL,
  `plan_count_value` bigint NOT NULL,
  `plan_value` bigint NOT NULL,
  `plan_config_year` int DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_report`
--

LOCK TABLES `plan_report` WRITE;
/*!40000 ALTER TABLE `plan_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_report_timeline`
--

DROP TABLE IF EXISTS `plan_report_timeline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_report_timeline` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `buss_day` int NOT NULL,
  `buss_stat_date` date NOT NULL,
  `user_plan_id` bigint NOT NULL,
  `report_count_value` bigint NOT NULL,
  `report_value` bigint NOT NULL,
  `count_value_stat_result` smallint DEFAULT NULL,
  `value_stat_result` smallint DEFAULT NULL,
  `plan_count_value` bigint NOT NULL,
  `plan_value` bigint NOT NULL,
  `plan_config_year` int DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_report_timeline`
--

LOCK TABLES `plan_report_timeline` WRITE;
/*!40000 ALTER TABLE `plan_report_timeline` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_report_timeline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `price_region`
--

DROP TABLE IF EXISTS `price_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `price_region` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint NOT NULL DEFAULT '1',
  `min_price` decimal(9,2) NOT NULL,
  `max_price` decimal(9,2) NOT NULL,
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `price_region`
--

LOCK TABLES `price_region` WRITE;
/*!40000 ALTER TABLE `price_region` DISABLE KEYS */;
/*!40000 ALTER TABLE `price_region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `province`
--

DROP TABLE IF EXISTS `province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `province` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `map_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='省份表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `province`
--

LOCK TABLES `province` WRITE;
/*!40000 ALTER TABLE `province` DISABLE KEYS */;
INSERT INTO `province` VALUES (1,'北京市','北京市'),(2,'天津市','天津'),(3,'河北省','河北'),(4,'山西省','山西'),(5,'内蒙古','内蒙古'),(6,'辽宁省','辽宁'),(7,'吉林省','吉林'),(8,'黑龙江省','黑龙江'),(9,'上海市','上海'),(10,'江苏省','江苏'),(11,'浙江省','浙江'),(12,'安徽省','安徽'),(13,'福建省','福建'),(14,'江西省','江西'),(15,'山东省','山东'),(16,'河南省','河南'),(17,'湖北省','湖北'),(18,'湖南省','湖南'),(19,'广东省','广东'),(20,'广西','广西'),(21,'海南省','海南'),(22,'重庆市','重庆'),(23,'四川省','四川'),(24,'贵州省','贵州'),(25,'云南省','云南'),(26,'西藏','西藏'),(27,'陕西省','陕西'),(28,'甘肃省','甘肃'),(29,'青海省','青海'),(30,'宁夏','宁夏'),(31,'新疆','新疆'),(32,'香港','香港'),(33,'澳门','澳门'),(34,'台湾省','台湾');
/*!40000 ALTER TABLE `province` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qa_config`
--

DROP TABLE IF EXISTS `qa_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qa_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parent_id` bigint DEFAULT NULL,
  `result_type` smallint NOT NULL,
  `keywords` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `replay_content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `replay_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `column_template` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `refer_qa_id` bigint DEFAULT NULL,
  `handle_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `match_cache` tinyint DEFAULT '0',
  `order_index` smallint NOT NULL,
  `status` smallint DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qa_config`
--

LOCK TABLES `qa_config` WRITE;
/*!40000 ALTER TABLE `qa_config` DISABLE KEYS */;
INSERT INTO `qa_config` VALUES (0,'默认处理器',NULL,1,'  ',NULL,NULL,NULL,3,'default',0,0,1,NULL,'2020-06-07 09:47:14','2020-07-01 21:52:09'),(1,'命令处理器',NULL,3,'发送命令,命令,cmd',NULL,NULL,NULL,NULL,'cmd',0,0,1,NULL,'2020-06-07 09:47:14','2020-07-01 20:22:17'),(2,'日历处理器',NULL,3,'日历,任务,cld',NULL,NULL,NULL,NULL,'cld',0,1,1,NULL,'2020-06-07 09:47:14','2020-07-01 20:22:22'),(3,'帮助处理器',NULL,0,'帮助,help,怎么使用','请输入关键字查询你想要的操作:\r\n1. 命令：查询目前所有支持的命令列表\r\n2. 日历：查询您的日历',NULL,NULL,NULL,'help',0,2,1,NULL,'2020-06-07 09:47:14','2020-07-01 10:04:46'),(5,'健康处理器',NULL,3,'身体怎么样,看病情况,吃药,用药',NULL,NULL,NULL,NULL,'health',1,4,1,NULL,'2020-06-28 17:26:10','2020-07-02 15:40:32'),(6,'咨询处理器',NULL,1,'你好,您好,在吗',NULL,NULL,NULL,3,'ask',0,1,1,NULL,'2020-06-29 16:19:03','2020-07-01 20:22:31'),(7,'消费处理器',NULL,3,'消费,购买,花费',NULL,NULL,NULL,NULL,'consume',1,7,1,NULL,'2020-06-29 16:25:02','2020-07-02 15:40:36'),(8,'不文明用语处理器',NULL,0,'垃圾,shit,笨蛋,你妈的,他妈的,TMD,狗日的','请注意文明用语!',NULL,NULL,NULL,'qq',0,1,1,NULL,'2020-06-29 16:52:48','2021-03-23 13:46:58'),(9,'身体概况',5,2,'体重,身高','SELECT DATE_FORMAT(record_date,\'%Y-%m-%d\'),height,weight,bmi FROM body_basic_info \r\nwhere user_id=?0 and record_date>=?1 and record_date<=?2\r\norder by record_date desc','您最近的身体基本情况:','{0}:身高:{1}cm,体重:{2}kg,BMI指数:{3}',NULL,'health_bodyInfo',1,9,1,NULL,'2020-06-30 11:13:45','2021-03-23 13:35:07'),(10,'吃药',5,3,'吃药',NULL,NULL,NULL,NULL,'health_drug',1,10,1,NULL,'2020-06-30 23:12:10','2021-03-23 13:39:31'),(11,'手术',5,3,'手术',NULL,NULL,NULL,NULL,'health_operation',1,11,1,NULL,'2020-06-30 23:13:34','2021-03-23 13:43:50'),(12,'做了哪些手术',11,2,'做了什么手术,做了哪些手术','SELECT name,treat_date,review_date FROM treat_operation\r\nwhere user_id=?0 and treat_date>=?1 and treat_date<=?2\r\norder by review_date desc\r\n','您最近做过的手术','{0}:,手术日期:{1},复查日期:{2}.',NULL,'health_operation_done',1,12,1,NULL,'2020-06-30 23:15:46','2021-03-23 13:44:14'),(13,'要哪些手术',11,2,'手术复查','SELECT name,review_date FROM treat_operation\r\nwhere review_date is not null and review_date>=date_sub(NOW(), interval 3 MONTH)\r\nand user_id=?0\r\nand review_date>=?1 and review_date<=?2','最近三个月需要做的手术','{0}:,复查日期:{1}.',NULL,'health_operation_need',1,13,1,NULL,'2020-06-30 23:16:31','2021-03-23 13:44:20'),(14,'身体不适',5,2,'身体不适,不舒服','SELECT DATE_FORMAT(occur_date,\'%Y-%m-%d\'),disease,organ FROM body_abnormal_record \r\nwhere user_id=?0 and occur_date>=?1 and occur_date<=?2\r\norder by occur_date desc','您最近的身体不适:','{0}:疾病症状:{1},器官:{2}',NULL,'health_body_abnormal',1,14,1,NULL,'2020-07-01 15:01:01','2021-03-23 13:44:30'),(15,'吃了什么药',10,2,'吃了什么药,用过什么药','SELECT DATE_FORMAT(tdd.occur_time,\'%Y-%m-%d %H:%i:%s\') ,td.name\r\nFROM treat_drug_detail tdd,treat_drug td\r\nwhere tdd.treat_drug_id = td.id\r\nand tdd.user_id=?0 and tdd.occur_time>=?1 and tdd.occur_time<=?2\r\norder by tdd.occur_time desc','您的用药记录','{0}:药品:{1}',NULL,'health_drug_done',1,15,1,NULL,'2020-07-02 10:04:19','2021-03-23 13:44:03'),(16,'翻页处理器',NULL,3,'上一页,翻页,下一页,',NULL,NULL,NULL,NULL,'scroll_page',0,1,1,NULL,'2020-07-02 10:41:22','2020-07-02 15:46:30'),(17,'阅读处理器',NULL,2,'阅读,看书','SELECT DATE_FORMAT(rrd.read_time,\'%Y-%m-%d %H:%i:%s\'),rr.book_name,rrd.minutes \nFROM reading_record_detail rrd,reading_record rr\nwhere rrd.reading_record_id = rr.id\nand rrd.user_id=?0 and rrd.read_time<=?1 and rrd.read_time>=?2\norder by rrd.read_time desc','您最近的阅读情况:','{0}:{1},阅读时间:{2}分钟',NULL,'reading',1,17,1,NULL,'2020-07-05 15:03:24','2021-03-23 08:35:48');
/*!40000 ALTER TABLE `qa_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reading_record`
--

DROP TABLE IF EXISTS `reading_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reading_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `book_category_id` bigint DEFAULT NULL,
  `book_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书名',
  `author` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `isbn` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `published_year` int DEFAULT NULL,
  `press` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `country_id` int DEFAULT NULL,
  `book_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `score` decimal(5,1) DEFAULT NULL,
  `language` smallint DEFAULT NULL,
  `proposed_date` date DEFAULT NULL COMMENT '对于未读的书期望时间',
  `begin_date` date DEFAULT NULL,
  `finished_date` date DEFAULT NULL COMMENT '完成时间',
  `store_date` date DEFAULT NULL,
  `status` smallint NOT NULL COMMENT '状态',
  `source` smallint DEFAULT NULL,
  `secondhand` tinyint DEFAULT '0',
  `cost_days` int DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNI_KEY_ISBN` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='看书记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reading_record`
--

LOCK TABLES `reading_record` WRITE;
/*!40000 ALTER TABLE `reading_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `reading_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reading_record_detail`
--

DROP TABLE IF EXISTS `reading_record_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reading_record_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `reading_record_id` bigint NOT NULL,
  `read_time` datetime NOT NULL,
  `minutes` int NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reading_record_detail`
--

LOCK TABLES `reading_record_detail` WRITE;
/*!40000 ALTER TABLE `reading_record_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `reading_record_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report_config`
--

DROP TABLE IF EXISTS `report_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sql_type` smallint NOT NULL COMMENT '查询语句类型0:sql,1:hql',
  `sql_content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `result_columns` int NOT NULL,
  `warning_value` int DEFAULT NULL COMMENT '警告值',
  `alert_value` int DEFAULT NULL COMMENT '告警值',
  `value_type` smallint DEFAULT NULL COMMENT '值的类型:0天，1小时，2分钟',
  `order_index` smallint DEFAULT NULL COMMENT '排序号',
  `status` smallint NOT NULL COMMENT '状态',
  `user_band` bit(1) DEFAULT NULL,
  `result_template` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `level` int NOT NULL DEFAULT '3',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_config`
--

LOCK TABLES `report_config` WRITE;
/*!40000 ALTER TABLE `report_config` DISABLE KEYS */;
INSERT INTO `report_config` VALUES (1,'年度消费情况统计','年度消费情况统计',0,'select count(*) as c,round(sum(total_price)) as resultValue from buy_record where  year(buy_date)=?0  and user_id=?1 and price >=0',2,100000,200000,0,1,1,_binary '','{0}&nbsp;&nbsp;次<br>{1}&nbsp;&nbsp;元',3,'','2019-12-25 12:21:21','2019-12-25 12:21:21'),(2,'年度某项消费统计(单层分类)','年度{某项消费}统计(单层分类)',0,'select count(*) as c,round(sum(total_price)) as resultValue from buy_record where  year(buy_date)=?0  and user_id=?1 and goods_type_id={0}',2,0,0,0,5,1,_binary '','{0}&nbsp;&nbsp;次<br>{1}&nbsp;&nbsp;元',3,'','2019-12-25 12:22:33','2019-12-25 12:22:33'),(3,'年度某项消费统计(两层分类)','年度{某项消费}统计(两层分类)',0,'select count(*) as c,round(sum(total_price)) as resultValue from buy_record where  year(buy_date)=?0  and user_id=?1 and goods_type_id={0}  and sub_goods_type_id={1}',2,0,0,0,4,1,_binary '','{0}&nbsp;&nbsp;次<br>{1}&nbsp;&nbsp;元',3,'','2019-12-25 12:22:23','2019-12-25 12:22:23'),(4,'年度练习某项乐器统计','年度练习{某项乐器}统计',0,'select count(*) as c,floor(sum(minutes)/60) as resultValue from music_practice where  year(practice_date)=?0  and user_id=?1 and music_instrument_id={0}',2,0,0,0,1,1,_binary '','{0}&nbsp;&nbsp;次<br>{1}&nbsp;&nbsp;个小时',3,'','2019-12-25 12:21:44','2019-12-25 12:21:44'),(5,'年度某项运动统计','年度{某项运动}统计',0,'select count(*) as c,round(sum(kilometres)) as resultValue,max(max_heart_rate) aa,max(kilometres) bb from sport_exercise where  year(exercise_date)=?0  and user_id=?1 and sport_type_id={0}',4,0,0,0,7,1,_binary '','{0}&nbsp;&nbsp;次<br>{1}&nbsp;&nbsp;公里<br>最高心率{2}&nbsp;&nbsp;次/分钟<br>最长路程{3}&nbsp;&nbsp;公里',3,'','2019-12-25 12:23:00','2019-12-25 12:23:00'),(6,'年度已实现梦想统计','年度已实现梦想统计',0,'select count(*) as c from dream where  year(finished_date)=?0  and user_id=?1 and status=2',1,0,0,NULL,3,1,_binary '','{0}&nbsp;&nbsp;个',3,'','2019-12-25 12:22:06','2019-12-25 12:22:06'),(7,'年度旅行情况统计','年度旅行情况统计',0,'select count(*) as c,sum(days) as resultValue from life_experience where  year(start_date)=?0  and user_id=?1 and type=2',2,0,0,NULL,3,1,_binary '','{0}&nbsp;&nbsp;次<br>{1}&nbsp;&nbsp;天',3,'','2019-12-25 12:22:15','2019-12-25 12:22:15'),(8,'年度已读的书统计','年度已读的书统计',0,'select count(*) as c from reading_record where  year(finished_date)=?0 and user_id=?1 and status=2',1,0,0,NULL,6,1,_binary '','{0}&nbsp;&nbsp;本',3,'','2019-12-25 12:22:53','2019-12-25 12:22:53'),(9,'年度练习曲子数统计','年度练习曲子数统计',0,'select count(*) as c from (select distinct tune from music_practice_tune mpt left join music_practice mp on mpt.music_practice_id=mp.id where year(mp.practice_date)=?0  and mp.user_id=?1 ) as res\r\n',1,0,0,NULL,5,1,_binary '','{0}&nbsp;&nbsp;首',3,'','2019-12-25 12:22:40','2019-12-25 12:22:40'),(10,'年度看病情况统计','年度看病情况统计',0,'select count(*) as c,sum(total_fee) as resultValue,sum(medical_insurance_paid_fee) as aa,sum(personal_paid_fee) as bb from treat_record where  year(treat_date)=?0  and user_id=?1',4,0,0,NULL,2,1,_binary '','{0}&nbsp;&nbsp;次<br>总计{1}&nbsp;&nbsp;元<br>医保支付{2}&nbsp;&nbsp;元<br>个人支付{3}&nbsp;&nbsp;元',3,'','2019-12-25 12:21:54','2019-12-25 12:21:54'),(11,'年度某项运动里程碑完成统计','年度{某项运动}里程碑完成统计',0,'select count(*) from sport_milestone where year(to_exercise_date)=?0 and user_id=?1 and sport_type_id={0}',1,10,20,NULL,8,1,_binary '','{0}&nbsp;&nbsp;个',3,'','2019-12-25 12:23:08','2019-12-25 12:23:08');
/*!40000 ALTER TABLE `report_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'超级管理员',1,1,NULL,'2020-01-09 18:59:17','2020-01-09 19:00:29'),(2,'普通管理员',1,2,NULL,'2020-01-09 19:57:35',NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_function`
--

DROP TABLE IF EXISTS `role_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_function` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `function_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6739 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_function`
--

LOCK TABLES `role_function` WRITE;
/*!40000 ALTER TABLE `role_function` DISABLE KEYS */;
INSERT INTO `role_function` VALUES (6014,2,761),(6015,2,762),(6016,2,763),(6017,2,369),(6018,2,440),(6019,2,165),(6020,2,772),(6021,2,775),(6022,2,255),(6023,2,256),(6024,2,237),(6025,2,257),(6026,2,820),(6027,2,821),(6028,2,708),(6029,2,709),(6030,2,712),(6031,2,768),(6032,2,769),(6033,2,579),(6034,2,190),(6035,2,755),(6036,2,188),(6037,2,189),(6038,2,944),(6039,2,373),(6040,2,945),(6041,2,725),(6042,2,792),(6043,2,793),(6044,2,970),(6045,2,971),(6046,2,120),(6047,2,121),(6048,2,123),(6049,2,125),(6050,2,794),(6051,2,242),(6052,2,243),(6053,2,395),(6054,2,246),(6055,2,893),(6056,2,895),(6057,2,896),(6058,2,897),(6059,2,898),(6060,2,0),(6513,1,0),(6514,1,685),(6515,1,686),(6516,1,316),(6517,1,113),(6518,1,114),(6519,1,761),(6520,1,762),(6521,1,763),(6522,1,369),(6523,1,440),(6524,1,165),(6525,1,772),(6526,1,775),(6527,1,255),(6528,1,256),(6529,1,237),(6530,1,257),(6531,1,820),(6532,1,821),(6533,1,708),(6534,1,709),(6535,1,712),(6536,1,768),(6537,1,769),(6538,1,579),(6539,1,190),(6540,1,755),(6541,1,188),(6542,1,189),(6543,1,944),(6544,1,373),(6545,1,945),(6546,1,725),(6547,1,792),(6548,1,793),(6549,1,970),(6550,1,971),(6551,1,120),(6552,1,121),(6553,1,123),(6554,1,125),(6555,1,794),(6556,1,242),(6557,1,243),(6558,1,395),(6559,1,246),(6560,1,893),(6561,1,895),(6562,1,896),(6563,1,897),(6564,1,898),(6565,1,797),(6566,1,866),(6567,1,868),(6568,1,870),(6569,1,871),(6570,1,872),(6571,1,873),(6572,1,799),(6573,1,800),(6574,1,705),(6575,1,728),(6576,1,225),(6577,1,729),(6578,1,297),(6579,1,731),(6580,1,732),(6581,1,748),(6582,1,749),(6583,1,804),(6584,1,695),(6585,1,924),(6586,1,1024),(6587,1,696),(6588,1,697),(6589,1,698),(6590,1,699),(6591,1,700),(6592,1,701),(6593,1,702),(6594,1,805),(6595,1,337),(6596,1,82),(6597,1,636),(6598,1,74),(6599,1,76),(6600,1,659),(6601,1,75),(6602,1,77),(6603,1,73),(6604,1,79),(6605,1,806),(6606,1,807),(6607,1,348),(6608,1,1105),(6609,1,227),(6610,1,67),(6611,1,69),(6612,1,635),(6613,1,1068),(6614,1,1069),(6615,1,1104),(6616,1,808),(6617,1,451),(6618,1,307),(6619,1,1070),(6620,1,809),(6621,1,607),(6622,1,465),(6623,1,814),(6624,1,534),(6625,1,535),(6626,1,536),(6627,1,717),(6628,1,65),(6629,1,66),(6630,1,1057),(6631,1,1059),(6632,1,1060),(6633,1,1101),(6634,1,1102),(6635,1,1103),(6636,1,810),(6637,1,811),(6638,1,637),(6639,1,812),(6640,1,543),(6641,1,1089),(6642,1,581),(6643,1,603),(6644,1,604),(6645,1,605),(6646,1,632),(6647,1,454),(6648,1,455),(6649,1,456),(6650,1,457),(6651,1,458),(6652,1,459),(6653,1,813),(6654,1,849),(6655,1,542),(6656,1,703),(6657,1,541),(6658,1,815),(6659,1,876),(6660,1,878),(6661,1,879),(6662,1,880),(6663,1,881),(6664,1,882),(6665,1,884),(6666,1,816),(6667,1,423),(6668,1,424),(6669,1,694),(6670,1,745),(6671,1,817),(6672,1,704),(6673,1,818),(6674,1,601),(6675,1,658),(6676,1,819),(6677,1,727),(6678,1,846),(6679,1,367),(6680,1,847),(6681,1,850),(6682,1,1048),(6683,1,1054),(6684,1,1088),(6685,1,889),(6686,1,998),(6687,1,999),(6688,1,1001),(6689,1,1002),(6690,1,1003),(6691,1,1007),(6692,1,1000),(6693,1,1004),(6694,1,1005),(6695,1,1006),(6696,1,1008),(6697,1,1009),(6698,1,1010),(6699,1,1011),(6700,1,1012),(6701,1,1013),(6702,1,1041),(6703,1,1019),(6704,1,1020),(6705,1,1021),(6706,1,1022),(6707,1,1023),(6708,1,1026),(6709,1,1028),(6710,1,1029),(6711,1,1030),(6712,1,1031),(6713,1,1032),(6714,1,1039),(6715,1,1034),(6716,1,1035),(6717,1,1036),(6718,1,1037),(6719,1,1038),(6720,1,1062),(6721,1,1063),(6722,1,1064),(6723,1,1065),(6724,1,1066),(6725,1,1043),(6726,1,1044),(6727,1,1045),(6728,1,1046),(6729,1,1047),(6730,1,1121),(6731,1,1122),(6732,1,1123),(6733,1,1124),(6734,1,1125),(6735,1,1126),(6736,1,1127),(6737,1,1128),(6738,1,1129);
/*!40000 ALTER TABLE `role_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `score_config`
--

DROP TABLE IF EXISTS `score_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `score_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `group_id` bigint NOT NULL DEFAULT '1',
  `sql_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `limit_value` decimal(9,2) NOT NULL DEFAULT '1.00',
  `compare_type` smallint NOT NULL DEFAULT '1',
  `max_score` int NOT NULL,
  `status` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score_config`
--

LOCK TABLES `score_config` WRITE;
/*!40000 ALTER TABLE `score_config` DISABLE KEYS */;
INSERT INTO `score_config` VALUES (1,'消费评分',1,'select  count(0) as n from buy_record where user_id=?0 and buy_date>=?1 and buy_date<=?2',2.00,1,5,1,'平均每天的消费次数超过2次为0分,0次为满分','2019-09-09 11:11:11',NULL),(2,'收入评分',1,'select  count(0) as n from income where user_id=?0 and occur_time>=?1 and occur_time<=?2',0.03,0,5,1,'每个月的收入次数至少1次为满分,0次为0分','2019-09-09 11:11:11',NULL),(3,'音乐练习评分',1,'select  count(0) as n from music_practice where user_id=?0 and practice_date>=?1 and practice_date<=?2',0.50,0,10,1,'平均每两天至少练习1次为满分,0次为0分','2019-09-09 11:11:11',NULL),(4,'锻炼评分',1,'select  count(0) as n from sport_exercise where user_id=?0 and exercise_date>=?1 and exercise_date<=?2',0.50,0,10,1,'平均每两天至少锻炼1次为满分,0次为0分','2019-09-09 11:11:11',NULL),(5,'看病评分',1,'select  count(0) as n from treat_record where user_id=?0 and treat_date>=?1 and treat_date<=?2 and is_sick=1 and treat_type in (0,3)',0.04,1,10,1,'每个月看病次数超过1次为0分,0次为满分','2019-09-09 11:11:11',NULL),(6,'用药评分',1,'select  count(0) as n from treat_drug_detail where user_id=?0 and occur_time>=?1 and occur_time<=?2',0.10,1,5,1,'每个月吃药次数超过3次为0分,0次为满分','2019-09-09 11:11:11','2020-10-27 08:14:54'),(7,'身体不适评分',1,'select  count(0) as n from body_abnormal_record where user_id=?0 and occur_date>=?1 and occur_date<=?2',0.10,1,5,1,'每个月身体不适次数超过3次为0分,0次为满分','2019-09-09 11:11:11',NULL),(8,'睡眠评分',1,'select  count(0) as n from sleep where user_id=?0 and sleep_date>=?1 and sleep_date<=?2 and total_minutes<450',0.10,1,5,1,'每个月睡眠时长小于7个半小时超过3次为0分,0次为满分','2019-09-09 11:11:11',NULL),(9,'阅读评分',1,'select  count(0) as n from reading_record_detail where user_id=?0 and read_time>=?1 and read_time<=?2',0.50,0,10,1,'平均每两天至少看书1次为满分,0次为0分','2019-09-09 11:11:11',NULL),(10,'饮食评分',1,'select  count(0) as n from diet where user_id=?0 and occur_time>=?1 and occur_time<=?2 and diet_type in (0,1,2)',3.00,0,10,1,'每天一日三餐正常为满分,0次为0分','2019-09-09 11:11:11',NULL),(11,'加班评分',1,'select  sum(hours)  as n from work_overtime where user_id=?0 and work_date>=?1 and work_date<=?2 ',2.00,1,5,1,'平均每天加班小时数大于2个小时为0分,0为满分','2019-09-09 11:11:11',NULL),(12,'积分体系评分',1,'select  sum(rewards)  as n from user_reward_point_record where user_id=?0 and created_time>=?1 and created_time<=?2 ',20.00,0,20,1,'平均每天积分大于20为满分,0为0分','2019-09-09 11:11:11',NULL);
/*!40000 ALTER TABLE `score_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `score_config_group`
--

DROP TABLE IF EXISTS `score_config_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `score_config_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score_config_group`
--

LOCK TABLES `score_config_group` WRITE;
/*!40000 ALTER TABLE `score_config_group` DISABLE KEYS */;
INSERT INTO `score_config_group` VALUES (1,'默认配置','0',1,NULL,'2019-09-12 22:11:08',NULL);
/*!40000 ALTER TABLE `score_config_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sleep`
--

DROP TABLE IF EXISTS `sleep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sleep` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `sleep_date` date NOT NULL,
  `sleep_time` datetime DEFAULT NULL,
  `get_up_time` datetime DEFAULT NULL,
  `first_wake_up_time` datetime DEFAULT NULL,
  `last_wake_up_time` datetime DEFAULT NULL,
  `wake_up_count` int DEFAULT NULL,
  `total_minutes` int DEFAULT NULL,
  `light_sleep` int DEFAULT NULL,
  `deep_sleep` int DEFAULT NULL,
  `quality` int DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`created_time`),
  UNIQUE KEY `sleepDate_UNIQUE` (`sleep_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sleep`
--

LOCK TABLES `sleep` WRITE;
/*!40000 ALTER TABLE `sleep` DISABLE KEYS */;
/*!40000 ALTER TABLE `sleep` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sport_exercise`
--

DROP TABLE IF EXISTS `sport_exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sport_exercise` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `sport_type_id` bigint NOT NULL COMMENT '锻炼类型',
  `exercise_date` datetime NOT NULL,
  `kilometres` decimal(5,2) NOT NULL COMMENT '锻炼公里数',
  `minutes` int NOT NULL COMMENT '锻炼时间',
  `speed` decimal(5,2) DEFAULT NULL COMMENT '速度',
  `max_speed` decimal(5,2) DEFAULT NULL,
  `pace` decimal(5,2) DEFAULT NULL,
  `max_pace` decimal(5,2) DEFAULT NULL,
  `max_heart_rate` int DEFAULT NULL COMMENT '最高心率',
  `average_heart_rate` int DEFAULT NULL COMMENT '平均心率',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `mileage_best` smallint DEFAULT NULL,
  `fast_best` smallint DEFAULT NULL,
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `FK_pkkjnt72kmbvk9n87ht1wxeq9` (`sport_type_id`),
  CONSTRAINT `FK_pkkjnt72kmbvk9n87ht1wxeq9` FOREIGN KEY (`sport_type_id`) REFERENCES `sport_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='运动锻炼表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sport_exercise`
--

LOCK TABLES `sport_exercise` WRITE;
/*!40000 ALTER TABLE `sport_exercise` DISABLE KEYS */;
/*!40000 ALTER TABLE `sport_exercise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sport_milestone`
--

DROP TABLE IF EXISTS `sport_milestone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sport_milestone` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `alais` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `sport_type_id` bigint NOT NULL,
  `kilometres` decimal(5,2) NOT NULL,
  `minutes` int DEFAULT NULL,
  `sport_exercise_id` bigint DEFAULT NULL,
  `from_exercise_date` date DEFAULT NULL,
  `to_exercise_date` date DEFAULT NULL,
  `cost_days` int DEFAULT NULL,
  `order_index` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sport_milestone`
--

LOCK TABLES `sport_milestone` WRITE;
/*!40000 ALTER TABLE `sport_milestone` DISABLE KEYS */;
/*!40000 ALTER TABLE `sport_milestone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sport_type`
--

DROP TABLE IF EXISTS `sport_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sport_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `user_id` bigint NOT NULL DEFAULT '1',
  `status` smallint NOT NULL COMMENT '状态',
  `unit` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '公里',
  `order_index` smallint NOT NULL COMMENT '排序号',
  PRIMARY KEY (`id`),
  KEY `AK_Key_1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='锻炼类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sport_type`
--

LOCK TABLES `sport_type` WRITE;
/*!40000 ALTER TABLE `sport_type` DISABLE KEYS */;
INSERT INTO `sport_type` VALUES (11,'跑步',0,1,'公里',1);
/*!40000 ALTER TABLE `sport_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stat_value_config`
--

DROP TABLE IF EXISTS `stat_value_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stat_value_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` smallint NOT NULL,
  `source` smallint NOT NULL DEFAULT '0',
  `value_class` smallint NOT NULL,
  `fid` bigint NOT NULL,
  `sql_content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `enum_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `enum_id_type` smallint DEFAULT NULL,
  `dict_group_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `json_data` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cas_cade_type` tinyint NOT NULL DEFAULT '0',
  `user_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `order_index` smallint NOT NULL,
  `prompt_msg` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stat_value_config`
--

LOCK TABLES `stat_value_config` WRITE;
/*!40000 ALTER TABLE `stat_value_config` DISABLE KEYS */;
INSERT INTO `stat_value_config` VALUES (1,'运动类型',2,0,0,27,'select id,name from sport_type where user_id ={user_id}',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置运动类型'),(3,'乐器',2,0,0,4,'select id,name from music_instrument where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置乐器种类'),(4,'锻炼类型',2,0,0,2,'select id,name from sport_type where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置运动类型'),(5,'乐器',2,0,0,19,'select  id,name from music_instrument where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置乐器种类'),(6,'类型',2,0,0,26,'select id,name from common_record_type where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置通用记录种类'),(7,'商品类型一级分类',2,0,1,11,'select id,name from goods_type where pid=0 and user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,1,'user_id',1,'请先去设置商品类型'),(8,'商品类型二级分类',2,0,1,11,'select id,name from goods_type where pid>0 and user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,2,'user_id',2,'请先去设置商品子类型'),(9,'手术名称',2,0,3,17,'select name as id,name as name from (SELECT distinct name FROM treat_operation where user_id ={user_id} ) as aa',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去记录手术信息'),(10,'商品大类',2,0,1,28,'select id,name from goods_type where pid=0 and user_id ={user_id}  order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置商品类型'),(11,'乐器',0,0,0,2,'select id,name from music_instrument where user_id ={user_id}  order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请选择乐器'),(12,'医院科室',0,0,3,4,'select department as id,department as name from (select distinct department from treat_record where user_id ={user_id} ) as aa',NULL,NULL,NULL,NULL,0,'user_id',1,'请选择医院科室'),(13,'乐器',0,0,0,16,'select id,name from music_instrument where user_id ={user_id}  order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置乐器种类'),(14,'运动',0,0,0,11,'select id,name from sport_type where user_id ={user_id}',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置运动类型'),(15,'手术名称',0,0,3,8,'select name as id,name as name from (SELECT distinct name FROM pms.treat_operation where user_id ={user_id} ) as aa',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去记录手术信息'),(16,'运动类型',0,0,0,1,'select id,name from sport_type where user_id ={user_id}',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置运动类型'),(17,'运动类型',0,0,0,21,'select id,name from sport_type where user_id ={user_id}',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置运动类型'),(18,'餐次类型',0,0,1,22,'select 0 as id,\'早餐\'as name from dual\r\nunion select 1 as id,\'午餐\'as name from dual\r\nunion select 2 as id,\'晚餐\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'请选择餐次'),(19,'乐器',1,0,0,4,'select id,name from music_instrument where user_id ={user_id}  order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置乐器种类'),(20,'商品类型一级分类',1,0,1,3,'select id,name from goods_type where pid=0 and user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置商品类型'),(21,'商品类型二级分类',1,0,1,3,'select id,name from goods_type where pid>0 and user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',2,'请先去设置商品子类型'),(22,'商品大类',1,0,1,2,'select id,name from goods_type where pid=0 order by order_index',NULL,NULL,NULL,NULL,0,NULL,1,'请先去设置商品类型'),(23,'运动类型',1,0,1,5,'select id,name from sport_type where user_id ={user_id}',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置运动类型'),(24,'运动类型',1,0,1,11,'select id,name from sport_type where user_id ={user_id}',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置运动类型'),(25,'人生经历类型',3,0,0,3,'select 0 as id,\'生活\' as name from dual\r\nunion\r\nselect 1 as id,\'工作\' as name from dual\r\nunion\r\nselect 2 as id,\'旅行\' as name from dual\r\nunion\r\nselect 3 as id,\'读书\' as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'请设置人生经历类型'),(26,'商品大类',3,0,1,10,'select id,name from goods_type where pid=0 and user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置商品类型'),(27,'商品类型一级分类',3,0,1,11,'select id,name from goods_type where pid=0 and user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,1,'user_id',1,'请先去设置商品类型'),(28,'商品类型二级分类',3,0,1,11,'select id,name from goods_type where pid>0 and user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,2,'user_id',2,'请先去设置商品子类型'),(29,'睡眠时长小于',2,0,0,29,'select 8 as id,\'8小时\' as name from dual\r\nunion\r\nselect 7 as id,\'7小时\' as name from dual\r\nunion\r\nselect 6 as id,\'6小时\' as name from dual\r\nunion\r\nselect 5 as id,\'5小时\' as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'请选择时长'),(30,'餐次类型',0,0,1,26,'select 0 as id,\'早餐\'as name from dual\r\nunion select 1 as id,\'午餐\'as name from dual\r\nunion select 2 as id,\'晚餐\'as name from dual\r\nunion select 3 as id,\'其他\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'请选择餐次'),(31,'不得晚于',2,0,1,30,'select 23 as id,\'23点\' as name from dual\r\nunion select 22 as id,\'22点\' as name from dual\r\nunion select 21 as id,\'21点\' as name from dual\r\nunion select 20 as id,\'20点\' as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'请选择'),(33,'类型',0,0,0,27,'select id,name from common_record_type where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置通用记录种类'),(34,'疾病',0,0,3,30,'select disease as id,disease as name from (\r\nselect distinct disease from treat_record  where user_id={user_id}\r\n) as res',NULL,NULL,NULL,NULL,0,'user_id',1,'请选择'),(35,'看病类型',0,0,1,34,'select 0 as id,\'看病\'as name from dual\r\nunion select 1 as id,\'买药\'as name from dual\r\nunion select 2 as id,\'体检\'as name from dual\r\nunion select 3 as id,\'看牙\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'请选择看病类型'),(36,'关键字',0,0,3,35,'select keywords as id,keywords as name from (\r\nselect distinct keywords from buy_record  where user_id={user_id} and keywords is not null\r\n) as res',NULL,NULL,NULL,NULL,0,'user_id',1,'请选择关键字'),(37,'餐次类型',0,0,1,36,'select 0 as id,\'早餐\'as name from dual\r\nunion select 1 as id,\'午餐\'as name from dual\r\nunion select 2 as id,\'晚餐\'as name from dual\r\nunion select 3 as id,\'看其他\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'请选择餐次类型'),(38,'食物类型',0,0,1,38,'select 0 as id,\'米饭\'as name from dual\r\nunion select 1 as id,\'面食\'as name from dual\r\nunion select 2 as id,\'其他\'as name from dual\r\nunion select 3 as id,\'水果\'as name from dual\r\nunion select 4 as id,\'零食\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'请选择食物类型'),(39,'食物来源',0,0,1,40,'select 0 as id,\'自己做\'as name from dual\r\nunion select 1 as id,\'餐馆\'as name from dual\r\nunion select 2 as id,\'外卖\'as name from dual\r\nunion select 3 as id,\'其他\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'请选择食物来源'),(40,'类型',0,0,0,41,'select id,name from common_record_type where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置通用记录种类'),(41,'统计分组',4,0,3,2,'select \'goods_type_id\' as id,\'商品类型\'as name from dual\r\nunion select \'buy_type_id\' as id,\'购买来源\'as name from dual\r\nunion select \'price_region\' as id,\'价格区间\'as name from dual\r\nunion select \'sub_goods_type_id\' as id,\'商品子类\'as name from dual\r\nunion select \'payment\' as id,\'支付方式\'as name from dual\r\nunion select \'shop_name\' as id,\'店铺名称\'as name from dual\r\nunion select \'brand\' as id,\'品牌\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'请选择统计分组'),(42,'时间分组类型',4,0,3,3,'select \'MONTH\' as id,\'月\'as name from dual\r\nunion select \'DAY\' as id,\'天\'as name from dual\r\nunion select \'WEEK\' as id,\'周\'as name from dual\r\nunion select \'YEAR\' as id,\'年\'as name from dual\r\nunion select \'YEARMONTH\' as id,\'年月\'as name from dual\r\nunion select \'DAYOFMONTH\' as id,\'天的序号\'as name from dual\r\nunion select \'DAYOFWEEK\' as id,\'星期的序号\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'分组类型'),(43,'乐器',4,0,0,8,'select id,name from music_instrument where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置乐器种类'),(44,'乐器',4,0,0,9,'select id,name from music_instrument where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置乐器种类'),(45,'运动类型',4,0,0,11,'select id,name from sport_type where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置运动类型'),(46,'时间分组类型',4,0,3,11,'select \'MONTH\' as id,\'月\'as name from dual\r\nunion select \'DAY\' as id,\'天\'as name from dual\r\nunion select \'WEEK\' as id,\'周\'as name from dual\r\nunion select \'YEAR\' as id,\'年\'as name from dual\r\nunion select \'YEARMONTH\' as id,\'年月\'as name from dual\r\nunion select \'DAYOFMONTH\' as id,\'天的序号\'as name from dual\r\nunion select \'DAYOFWEEK\' as id,\'星期的序号\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,2,'分组类型'),(47,'分组',4,0,3,12,'select \'diagnosed_disease\' as id,\'确诊疾病\'as name from dual\r\nunion select \'disease\' as id,\'疾病症状\'as name from dual\r\nunion select \'organ\' as id,\'器官\'as name from dual\r\nunion select \'hospital\' as id,\'医院\'as name from dual\r\nunion select \'department\' as id,\'科室\'as name from dual\r\nunion select \'tags\' as id,\'标签\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'请选择分组'),(48,'时间分组类型',4,0,3,13,'select \'MONTH\' as id,\'月\'as name from dual\r\nunion select \'DAY\' as id,\'天\'as name from dual\r\nunion select \'WEEK\' as id,\'周\'as name from dual\r\nunion select \'YEAR\' as id,\'年\'as name from dual\r\nunion select \'YEARMONTH\' as id,\'年月\'as name from dual\r\nunion select \'DAYOFMONTH\' as id,\'天的序号\'as name from dual\r\nunion select \'DAYOFWEEK\' as id,\'星期的序号\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'分组类型'),(49,'时间分组类型',4,0,3,14,'select \'MONTH\' as id,\'月\'as name from dual\r\nunion select \'DAY\' as id,\'天\'as name from dual\r\nunion select \'WEEK\' as id,\'周\'as name from dual\r\nunion select \'YEAR\' as id,\'年\'as name from dual\r\nunion select \'YEARMONTH\' as id,\'年月\'as name from dual\r\nunion select \'DAYOFMONTH\' as id,\'天的序号\'as name from dual\r\nunion select \'DAYOFWEEK\' as id,\'星期的序号\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'分组类型'),(50,'分组类型',4,0,3,15,'select \'DISEASE\' as id,\'疾病\'as name from dual\r\nunion select \'ORGAN\' as id,\'器官\'as name from dual\r\nunion select \'PAINLEVEL\' as id,\'疼痛级别\'as name from dual\r\nunion select \'IMPORTANTLEVEL\' as id,\'重要等级\'as name from dual\r\nunion select \'LASTDAYS\' as id,\'持续天数\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'分组类型'),(51,'时间分组类型',4,0,3,16,'select \'MONTH\' as id,\'月\'as name from dual\r\nunion select \'DAY\' as id,\'天\'as name from dual\r\nunion select \'WEEK\' as id,\'周\'as name from dual\r\nunion select \'YEAR\' as id,\'年\'as name from dual\r\nunion select \'YEARMONTH\' as id,\'年月\'as name from dual\r\nunion select \'DAYOFMONTH\' as id,\'天的序号\'as name from dual\r\nunion select \'DAYOFWEEK\' as id,\'星期的序号\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'时间分组类型'),(52,'X轴数据',4,0,3,17,'select \'MONTH\' as id,\'月\'as name from dual\r\nunion select \'DAY\' as id,\'天\'as name from dual\r\nunion select \'WEEK\' as id,\'周\'as name from dual\r\nunion select \'YEAR\' as id,\'年\'as name from dual\r\nunion select \'YEARMONTH\' as id,\'年月\'as name from dual\r\nunion select \'DAYOFMONTH\' as id,\'天的序号\'as name from dual\r\nunion select \'DAYOFWEEK\' as id,\'星期的序号\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'X轴数据'),(53,'Y轴数据',4,0,3,17,'select \'DURATION\' as id,\'睡眠时长\'as name from dual\r\nunion select \'SLEEP_TIME\' as id,\'睡眠点\'as name from dual\r\nunion select \'GETUP_TIME\' as id,\'起床点\'as name from dual\r\nunion select \'QUALITY\' as id,\'睡眠质量\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,2,'Y轴数据'),(54,'时间分组类型',4,0,3,18,'select \'MONTH\' as id,\'月\'as name from dual\r\nunion select \'DAY\' as id,\'天\'as name from dual\r\nunion select \'WEEK\' as id,\'周\'as name from dual\r\nunion select \'YEAR\' as id,\'年\'as name from dual\r\nunion select \'YEARMONTH\' as id,\'年月\'as name from dual\r\nunion select \'DAYOFMONTH\' as id,\'天的序号\'as name from dual\r\nunion select \'DAYOFWEEK\' as id,\'星期的序号\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'分组类型'),(55,'时间分组类型',4,0,3,19,'select \'MONTH\' as id,\'月\'as name from dual\r\nunion select \'DAY\' as id,\'天\'as name from dual\r\nunion select \'WEEK\' as id,\'周\'as name from dual\r\nunion select \'YEAR\' as id,\'年\'as name from dual\r\nunion select \'YEARMONTH\' as id,\'年月\'as name from dual\r\nunion select \'DAYOFMONTH\' as id,\'天的序号\'as name from dual\r\nunion select \'DAYOFWEEK\' as id,\'星期的序号\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'时间分组类型'),(56,'统计分组',4,0,3,20,'select \'BOOKCATEGORY\' as id,\'图书分类\'as name from dual\r\nunion select \'BOOKTYPE\' as id,\'书籍类型\'as name from dual\r\nunion select \'LANGUAGE\' as id,\'语言\'as name from dual\r\nunion select \'SCORE\' as id,\'评分\'as name from dual\r\nunion select \'STATUS\' as id,\'状态\'as name from dual\r\nunion select \'PUBLISHEDYEAR\' as id,\'出版年份\'as name from dual\r\nunion select \'PRESS\' as id,\'出版社\'as name from dual\r\nunion select \'NATION\' as id,\'国家\'as name from dual\r\nunion select \'PERIOD\' as id,\'完成天数\'as name from dual\r\nunion select \'TIME\' as id,\'花费时间\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'请选择统计分组'),(57,'时间分组类型',4,0,3,21,'select \'MONTH\' as id,\'月\'as name from dual\r\nunion select \'DAY\' as id,\'天\'as name from dual\r\nunion select \'WEEK\' as id,\'周\'as name from dual\r\nunion select \'YEAR\' as id,\'年\'as name from dual\r\nunion select \'YEARMONTH\' as id,\'年月\'as name from dual\r\nunion select \'DAYOFMONTH\' as id,\'天的序号\'as name from dual\r\nunion select \'DAYOFWEEK\' as id,\'星期的序号\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'时间分组类型'),(58,'时间分组类型',4,0,3,22,'select \'MONTH\' as id,\'月\'as name from dual\r\nunion select \'DAY\' as id,\'天\'as name from dual\r\nunion select \'WEEK\' as id,\'周\'as name from dual\r\nunion select \'YEAR\' as id,\'年\'as name from dual\r\nunion select \'YEARMONTH\' as id,\'年月\'as name from dual\r\nunion select \'DAYOFMONTH\' as id,\'天的序号\'as name from dual\r\nunion select \'DAYOFWEEK\' as id,\'星期的序号\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'时间分组类型'),(59,'类型',4,0,0,23,'select id,name from common_record_type where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置通用记录种类'),(60,'时间分组类型',4,0,3,23,'select \'MONTH\' as id,\'月\'as name from dual\r\nunion select \'DAY\' as id,\'天\'as name from dual\r\nunion select \'WEEK\' as id,\'周\'as name from dual\r\nunion select \'YEAR\' as id,\'年\'as name from dual\r\nunion select \'YEARMONTH\' as id,\'年月\'as name from dual\r\nunion select \'DAYOFMONTH\' as id,\'天的序号\'as name from dual\r\nunion select \'DAYOFWEEK\' as id,\'星期的序号\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,2,'分组类型'),(61,'时间分组类型',4,0,3,27,'select \'MONTH\' as id,\'月\'as name from dual\r\nunion select \'DAY\' as id,\'天\'as name from dual\r\nunion select \'WEEK\' as id,\'周\'as name from dual\r\nunion select \'YEAR\' as id,\'年\'as name from dual\r\nunion select \'YEARMONTH\' as id,\'年月\'as name from dual\r\nunion select \'DAYOFMONTH\' as id,\'天的序号\'as name from dual\r\nunion select \'DAYOFWEEK\' as id,\'星期的序号\'as name from dual',NULL,NULL,NULL,NULL,0,NULL,1,'时间分组类型'),(62,'类型',2,0,0,32,'select id,name from common_record_type where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置通用记录种类'),(63,'类型',2,0,0,33,'select id,name from common_record_type where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置通用记录种类'),(64,'类型',2,0,0,34,'select id,name from common_record_type where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置通用记录种类'),(65,'乐器',5,0,0,1,'select id,name from music_instrument where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置乐器种类'),(66,'运动类型',5,0,0,2,'select id,name from sport_type where user_id ={user_id}',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置运动类型'),(67,'类型',5,0,0,3,'select id,name from common_record_type where user_id ={user_id} order by order_index',NULL,NULL,NULL,NULL,0,'user_id',1,'请先去设置通用记录种类');
/*!40000 ALTER TABLE `stat_value_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_config`
--

DROP TABLE IF EXISTS `system_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '代码',
  `config_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置值',
  `status` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_config`
--

LOCK TABLES `system_config` WRITE;
/*!40000 ALTER TABLE `system_config` DISABLE KEYS */;
INSERT INTO `system_config` VALUES (1,'备份文件目录','system.backupPath','/mulanbay/server/serverUpdate/mulanbaybackup',1,NULL,'2020-08-27 15:20:41',NULL),(2,'用户等级判断最大持续天数','user.level.maxCompareDays','10',1,NULL,'2020-08-27 15:20:58',NULL),(3,'用户评分的评测区间天数','user.score.days','30',1,NULL,'2020-08-27 15:21:24','2020-08-27 15:21:44'),(4,'系统移动端的网站前缀','system.mobile.baseUrl','http://127.0.0.1/mobile',1,NULL,'2020-09-02 10:58:45',NULL),(5,'系统移动端的主页地址','system.mobile.mainUrl','/main/main.html',1,NULL,'2020-09-02 11:01:03',NULL),(6,'最大心率基数','system.maxHeartRate.base','220',1,NULL,'2020-09-02 11:03:17',NULL),(7,'每月的工作天数','work.days.of.month','20.83',1,NULL,'2020-09-02 11:06:47',NULL),(8,'每周的工作天数','work.days.of.week','5',1,NULL,'2020-09-02 11:09:20',NULL),(9,'每年的工作天数','work.days.of.year','250',1,NULL,'2020-09-02 11:10:29',NULL),(10,'回家的关键字','system.backhome.keywords','回家',1,NULL,'2020-09-02 11:12:14',NULL),(11,'购买记录标签获取的时间跨度(天)','buyRecord.tags.days','365',1,NULL,'2020-10-08 07:50:15','2020-12-15 17:24:47'),(12,'消费记录的商品名分词关键字获取个数','nlp.buyRecord.goodsName.ekNum','5',1,NULL,'2020-11-11 11:04:37',NULL),(13,'看病记录的各下拉框数据获取时间跨度(天)','treat.category.days','365',1,NULL,'2020-12-15 16:49:29','2020-12-15 17:22:24'),(14,'看病记录的标签获取时间跨度(天)','treat.tags.days','1095',1,NULL,'2020-12-15 16:55:25','2020-12-15 17:22:16'),(15,'饮食的标签获取时间跨度(天)','diet.tags.days','14',1,NULL,'2020-12-15 17:21:40',NULL),(16,'用户行为的的分词个数','nlp.userOperation.ekNum','4',1,NULL,'2021-03-15 09:37:52',NULL),(17,'QA的请求内容分词','nlp.userQa.requestContent.ekNum','4',1,NULL,'2021-03-23 09:58:59',NULL);
/*!40000 ALTER TABLE `system_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_function`
--

DROP TABLE IF EXISTS `system_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_function` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `short_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `support_methods` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `url_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `url_type` smallint NOT NULL,
  `function_type` smallint NOT NULL,
  `function_data_type` smallint NOT NULL DEFAULT '0',
  `pid` bigint DEFAULT NULL,
  `image_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` smallint NOT NULL,
  `order_index` int NOT NULL,
  `bean_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_field_type` smallint NOT NULL DEFAULT '0',
  `do_log` bit(1) NOT NULL DEFAULT b'1',
  `trigger_stat` bit(1) NOT NULL DEFAULT b'0',
  `diff_user` bit(1) NOT NULL DEFAULT b'1',
  `login_auth` tinyint NOT NULL DEFAULT '0',
  `permission_auth` tinyint NOT NULL DEFAULT '0',
  `ip_auth` tinyint NOT NULL DEFAULT '0',
  `always_show` tinyint NOT NULL DEFAULT '0',
  `request_limit` tinyint NOT NULL DEFAULT '0',
  `request_limit_period` int NOT NULL DEFAULT '5',
  `day_limit` int NOT NULL DEFAULT '0',
  `record_return_data` bit(1) NOT NULL DEFAULT b'0',
  `reward_point` int NOT NULL DEFAULT '0',
  `error_code` int NOT NULL DEFAULT '0',
  `tree_stat` tinyint NOT NULL DEFAULT '1',
  `sec_auth` tinyint DEFAULT '0',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `visible` bit(1) DEFAULT b'0',
  `router` bit(1) DEFAULT b'0',
  `frame` bit(1) DEFAULT b'0',
  `cache` bit(1) DEFAULT b'1',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `URL_METHOD` (`url_address`,`support_methods`)
) ENGINE=InnoDB AUTO_INCREMENT=1132 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_function`
--

LOCK TABLES `system_function` WRITE;
/*!40000 ALTER TABLE `system_function` DISABLE KEYS */;
INSERT INTO `system_function` VALUES (0,'根目录',NULL,'root','root',0,5,3,NULL,NULL,1,1,'root','id',0,_binary '\0',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,0,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-09 11:19:37','2019-08-14 08:08:07'),(1,'新增消费记录',NULL,'POST','/buyRecord/create',0,0,1,648,NULL,1,3,'BuyRecord','id',0,_binary '',_binary '',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-01 13:40:13','2020-07-23 15:47:19'),(2,'修改消费记录',NULL,'POST','/buyRecord/edit',0,1,1,648,NULL,1,2,'BuyRecord','id',0,_binary '',_binary '',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-01 13:40:49','2020-07-23 15:47:06'),(3,'新增音乐练习',NULL,'POST','/musicPractice/create',0,0,1,665,NULL,1,3,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,3,_binary '\0',10,0,1,0,'music:musicPractice:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-01 19:28:23','2020-07-31 08:43:59'),(4,'修改音乐练习',NULL,'POST','/musicPractice/edit',0,1,1,665,NULL,1,4,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPractice:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-01 19:28:51','2020-07-31 08:44:10'),(6,'新增音乐练习曲子',NULL,'POST','/musicPracticeTune/create',0,0,1,164,NULL,1,1,'MusicPracticeTune','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPracticeTune:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-01 20:52:18','2020-07-31 15:16:22'),(7,'修改音乐练习曲子',NULL,'POST','/musicPracticeTune/edit',0,1,1,164,NULL,1,1,'MusicPracticeTune','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPracticeTune:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-01 20:53:32','2020-07-31 15:16:30'),(47,'新增身体异常情况',NULL,'POST','/bodyAbnormalRecord/create',0,0,1,682,NULL,1,3,'BodyAbnormalRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyAbnormalRecord:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-08-10 08:51:31'),(50,'身体不适实时统计',NULL,'GET','/bodyAbnormalRecord/stat',0,4,0,676,'chart',1,4,'BodyAbnormalRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyAbnormalRecord:stat','health/body/bodyAbnormalRecord/stat','bodyAbnormalRecord/stat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-10-12 19:30:11'),(51,'身体不适分析',NULL,'GET','/bodyAbnormalRecord/analyse',0,0,0,676,'analyse',1,3,'BodyAbnormalRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyAbnormalRecord:analyse','health/body/bodyAbnormalRecord/analyse','bodyAbnormalRecord/analyse',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-10-12 19:30:00'),(52,'获取消费记录详情',NULL,'GET','/buyRecord/get',0,3,1,648,'',1,3,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 09:53:28','2019-12-30 10:27:48'),(54,'消费记录关键字列表',NULL,'GET,POST','/buyRecord/getKeywordsTree',0,3,2,650,'',1,1,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 09:53:28','2020-08-06 08:48:30'),(56,'获取购买来源列表树',NULL,'GET,POST','/buyType/getBuyTypeTree',0,3,2,643,'',1,5,'BuyType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 09:53:28','2019-12-30 09:43:40'),(57,'获取商品类型树',NULL,'GET,POST','/goodsType/getGoodsTypeTree',0,3,2,644,NULL,1,1,'GoodsType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-03-08 14:57:23'),(59,'获取乐器树',NULL,'GET,POST','/musicInstrument/getMusicInstrumentTree',0,3,2,664,'',1,3,'MusicInstrument','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 09:53:28','2019-12-30 14:41:54'),(60,'获取音乐练习详情',NULL,'GET','/musicPractice/get',0,3,1,665,'',1,1,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 09:53:28','2019-12-30 14:47:42'),(64,'提醒统计','列表','GET','/notifyStat/getData',0,4,0,762,'notify',1,3,'NotifyConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:notify:userNotify:notifyStat','report/notify/userNotify/notifyStatList','notifyStatList',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-12-09 22:56:39'),(65,'操作日志比较','获取比较数据','GET','/operationLog/getCompareData',0,3,0,806,'yoy',1,6,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:operationLog:logCompare','log/operationLog/logCompare','operationLog/logCompare',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-10-12 22:01:47'),(66,'获取操作日志最近的比较数据','获取最近的比较数据','GET','/operationLog/getNearstCompareData',0,3,1,65,NULL,1,2,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:operationLog:getNearstCompareData',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-08-26 12:59:58'),(67,'获取操作日志请求参数详情','参数详情','GET','/operationLog/getParas',0,3,1,807,NULL,1,4,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-02-13 20:51:50'),(69,'关联功能点','设置功能点','GET','/operationLog/setFunctionId',0,5,1,807,NULL,1,5,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:operationLog:setFunctionId',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-08-20 13:03:22'),(71,'获取计划配置树','配置树','GET,POST','/planConfig/getPlanConfigTree',0,3,2,775,NULL,1,5,'PlanConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-10-16 10:40:03'),(73,'新增功能点','新增','POST','/systemFunction/create',0,0,1,805,NULL,1,6,'SystemFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:systemFunction:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-08-05 13:57:39'),(74,'获取功能点详情','详情','GET','/systemFunction/get',0,3,1,805,NULL,1,3,'SystemFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-02-13 20:43:03'),(75,'获取功能点列表数据','列表','GET','/systemFunction/getData',0,3,1,805,NULL,1,5,'SystemFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-02-13 20:42:28'),(76,'获取功能点树','功能点树','GET,POST','/systemFunction/getSystemFunctionTree',0,3,2,805,NULL,1,4,'SystemFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-03-07 09:48:21'),(77,'自动初始化功能点数据','自动初始化','GET','/systemFunction/initUnConfigedFunctions',0,5,1,805,NULL,1,5,'SystemFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-02-13 20:43:32'),(79,'刷新内存中功能点列表','刷新缓存','POST','/systemFunction/refreshSystemConfig',0,5,1,805,NULL,1,6,'SystemFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:systemFunction:refreshSystemConfig',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 09:53:28','2020-08-05 20:40:10'),(82,'修改功能点','修改','POST','/systemFunction/edit',0,1,1,805,NULL,1,2,'SystemFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:systemFunction:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:37:12','2020-08-05 13:57:28'),(84,'回家次数统计',NULL,'GET','/backHome/dateStat',0,4,0,685,'life',1,0,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:backHome:dateStat','life/backHome/dateStat','backHome',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-10-19 16:13:39'),(86,'身体不适统计',NULL,'GET','/bodyAbnormalRecord/dateStat',0,4,0,676,'chart',1,2,'BodyAbnormalRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyAbnormalRecord:dateStat','health/body/bodyAbnormalRecord/dateStat','bodyAbnormalRecord/dateStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-10-12 19:29:53'),(88,'身体数据统计',NULL,'GET','/bodyBasicInfo/dateStat',0,4,0,675,'chart',1,2,'BodyBasicInfo','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyBasicInfo:dateStat','health/body/bodyBasicInfo/dateStat','bodyBasicInfo/dateStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-10-12 19:23:08'),(89,'修改身体数据',NULL,'POST','/bodyBasicInfo/edit',0,1,1,683,NULL,1,2,'BodyBasicInfo','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyBasicInfo:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-08-09 21:26:39'),(90,'获取身体基本数据',NULL,'GET','/bodyBasicInfo/get',0,3,1,683,NULL,1,3,'BodyBasicInfo','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-08-09 21:27:09'),(94,'同期比对',NULL,'GET','/bodyBasicInfo/yoyStat',0,3,0,675,'yoy',1,3,'BodyBasicInfo','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyBasicInfo:yoyStat','health/body/bodyBasicInfo/yoyStat','bodyBasicInfo/yoyStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-10-12 19:27:57'),(95,'修改图书分类','修改','POST','/bookCategory/edit',0,1,1,823,NULL,1,3,'BookCategory','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:bookCategory:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-08-15 09:03:58'),(96,'获取图书分类详情','详情','GET','/bookCategory/get',0,3,1,823,NULL,1,3,'BookCategory','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-02-13 21:41:39'),(97,'获取图书分类树','分类树','GET,POST','/bookCategory/getBookCategoryTree',0,3,2,823,NULL,1,4,'BookCategory','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-03-08 14:50:37'),(101,'消费实时分析',NULL,'GET','/buyRecord/analyseStat',0,5,0,640,'analyse',1,5,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:analyseStat','consume/buyRecord/analyseStat','buyRecord/analyseStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-10-12 18:22:46'),(103,'消费统计',NULL,'GET','/buyRecord/dateStat',0,5,0,640,'chart',1,8,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:dateStat','consume/buyRecord/dateStat','buyRecord/dateStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-10-12 18:23:13'),(106,'消费记录概要统计',NULL,'GET','/buyRecord/stat',0,4,1,648,'',1,6,'BuyRecord','id',0,_binary '',_binary '',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:03','2019-12-30 10:48:15'),(108,'消费同期比对',NULL,'GET','/buyRecord/yoyStat',0,5,0,640,'yoy',1,7,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:yoyStat','consume/buyRecord/yoyStat','buyRecord/yoyStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-10-12 18:23:05'),(109,'修改购买来源',NULL,'POST','/buyType/edit',0,1,1,643,NULL,1,2,'BuyType','id',1,_binary '',_binary '',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyType:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-08-06 08:06:43'),(110,'获取购买来源详情',NULL,'GET','/buyType/get',0,3,1,643,'',1,3,'BuyRecord','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:03','2019-12-30 09:44:22'),(113,'修改城市位置','修改','POST','/cityLocation/edit',0,1,1,686,NULL,1,3,'CityLocation','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'life:cityLocation:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-08-10 16:00:47'),(114,'获取城市位置信息','详情','GET','/cityLocation/get',0,3,1,686,NULL,1,3,'CityLocation','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-01-16 09:52:33'),(117,'获取年份列表','获取年份列表','GET,POST','/common/getYearTree',0,3,2,785,NULL,1,3,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-02-13 16:25:10'),(120,'修改数据录入分析','修改','POST','/dataInputAnalyse/edit',0,1,1,793,NULL,1,3,'DataInputAnalyse','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:dataInputAnalyse:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-10-16 10:36:41'),(121,'获取数据录入分析详情','详情','GET','/dataInputAnalyse/get',0,3,1,793,NULL,1,4,'DataInputAnalyse','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-10-16 10:36:47'),(123,'获取数据录入分析树','分析树','GET,POST','/dataInputAnalyse/getDataInputAnalyseTree',0,3,2,793,NULL,1,4,'DataInputAnalyse','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-10-16 10:36:54'),(125,'数据录入分析统计','分析统计','GET','/dataInputAnalyse/stat',0,4,1,793,NULL,1,5,'DataInputAnalyse','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:dataInputAnalyse:stat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-10-16 10:36:59'),(126,'获取日记统计数据','统计','GET','/diary/dateStat',0,4,1,127,NULL,1,1,'Diary','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-02-13 15:42:54'),(127,'日记统计首页','日记统计','GET','/diary/diaryDateStatList.html',0,5,0,776,NULL,1,1,'Diary','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-02-13 15:42:05'),(128,'修改日记','修改','POST','/diary/edit',0,1,1,777,NULL,1,3,'Diary','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-02-13 15:42:19'),(129,'获取日记详情','详情','GET','/diary/get',0,3,1,777,NULL,1,4,'Diary','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-02-13 15:42:34'),(130,'获取日记列表数据','列表','GET','/diary/getData',0,3,1,777,NULL,1,1,'Diary','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:03','2020-02-13 15:40:57'),(132,'梦想统计',NULL,'GET','/dream/statList',0,4,0,667,'chart',1,2,'Dream','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'dream:stat','dream/stat','dream/stat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-10-12 19:07:06'),(133,'修改梦想',NULL,'POST','/dream/edit',0,1,1,668,NULL,1,3,'Dream','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'dream:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-08-01 12:57:34'),(134,'获取梦想详情',NULL,'GET','/dream/get',0,3,1,668,'',1,4,'Dream','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:04','2019-12-30 14:54:16'),(138,'修改商品类型',NULL,'POST','/goodsType/edit',0,1,1,644,NULL,1,3,'GoodsType','id',1,_binary '',_binary '',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:goodsType:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-07-23 15:10:06'),(139,'获取商品类型详情',NULL,'GET','/goodsType/get',0,3,1,644,'',1,3,'GoodsType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:04','2019-12-30 09:55:55'),(143,'获取人生经历开始城市列表树',NULL,'GET,POST','/lifeExperience/getStartCityTree',0,3,2,692,NULL,1,4,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-08-10 16:01:52'),(146,'经历地图统计','人生经历地图统计','GET','/lifeExperience/mapStat',0,5,0,685,'map',1,6,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperience:mapStat','life/lifeExperience/mapStat','lifeExperience/mapStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2021-03-15 18:56:12'),(148,'线路地图统计','人生经历线路地图统计','GET','/lifeExperience/transferMapStat',0,5,0,685,'map',1,7,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperience:transferMapStat','life/lifeExperience/transferMapStat','lifeExperience/transferMapStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2021-03-15 18:55:57'),(150,'人生经历同期比对','人生经历同期比对','GET','/lifeExperience/yoyStat',0,5,0,685,'yoy',1,5,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperience:yoyStat','life/lifeExperience/yoyStat','lifeExperience/yoyStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-10-12 21:09:53'),(151,'修改乐器',NULL,'POST','/musicInstrument/edit',0,1,1,664,NULL,1,4,'MusicInstrument','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicInstrument:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-07-30 21:48:16'),(152,'获取乐器详情',NULL,'GET','/musicInstrument/get',0,0,1,664,'',1,6,'MusicInstrument','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:04','2019-12-30 14:42:18'),(156,'音乐练习统计',NULL,'GET','/musicPractice/dateStat',0,5,0,663,'chart',1,3,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPractice:dateStat','music/musicPractice/dateStat','musicPractice/dateStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-10-12 19:04:38'),(157,'音乐练习概要统计',NULL,'GET','/musicPractice/stat',0,4,1,665,'',1,3,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:04','2019-12-30 14:48:05'),(161,'音乐练习同期比对',NULL,'GET','/musicPractice/yoyStat',0,0,0,663,'yoy',1,6,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPractice:yoyStat','music/musicPractice/yoyStat','musicPractice/yoyStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-10-12 19:05:01'),(163,'音乐练习曲子记录统计',NULL,'GET','/musicPracticeTune/stat',0,4,1,164,'',1,1,'MusicPracticeTune','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:04','2019-12-30 14:50:44'),(164,'音乐练习曲子管理',NULL,'GET','/musicPracticeTune/getData',0,5,0,663,'music',1,7,'MusicPracticeTune','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPracticeTune:query','music/musicPracticeTune/index','musicPracticeTune',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-10-12 19:05:18'),(165,'修改提醒配置','修改','POST','/notifyConfig/edit',0,1,1,763,NULL,1,3,'NotifyConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:notify:notifyConfig:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-08-11 12:58:46'),(166,'获取提醒配置详情','获取详情','GET','/notifyConfig/get',0,3,1,763,NULL,1,1,'NotifyConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-10-16 10:38:51'),(172,'平均值统计','平均值统计','GET','/planReport/avgStat',0,5,0,772,'chart',1,6,'PlanReport','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planReport:avgStat','report/plan/planReport/avgStat','planReport/avgStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-08-14 07:47:08'),(174,'执行统计','计划执行统计','GET','/planReport/dateStat',0,5,0,772,'chart',1,5,'PlanReport','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planReport:dateStat','report/plan/planReport/dateStat','planReport/dateStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-08-14 07:47:01'),(176,'报告分析','报告统计','GET','/planReport/stat',0,4,0,772,'analyse',1,4,'PlanReport','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planReport:stat','report/plan/planReport/stat','planReport/stat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-10-12 21:19:07'),(177,'修改价格区间',NULL,'POST','/priceRegion/edit',0,1,1,645,NULL,1,3,'PriceRegion','id',1,_binary '',_binary '',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:priceRegion:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-07-23 15:14:21'),(178,'获取价格区间详情',NULL,'GET','/priceRegion/get',0,3,1,645,'',1,4,'PriceRegion','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:04','2019-12-30 10:24:37'),(182,'阅读分析','阅读分析','GET','/readingRecord/analyseStat',0,5,0,778,'analyse',1,4,'ReadingRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecord:analyseStat','read/readingRecord/analyseStat','readingRecord/analyseStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-10-12 21:21:32'),(184,'阅读统计','阅读统计','GET','/readingRecord/dateStat',0,5,0,778,'chart',1,3,'ReadingRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecord:dateStat','read/readingRecord/dateStat','readingRecord/dateStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-10-12 21:21:26'),(187,'阅读概要统计','概要统计','GET','/readingRecord/stat',0,4,1,779,NULL,1,4,'ReadingRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-02-13 15:50:30'),(188,'修改报表配置','修改','POST','/reportConfig/edit',0,1,1,769,NULL,1,3,'ReportConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-02-13 13:55:13'),(189,'获取报表配置详情','详情','GET','/reportConfig/get',0,3,1,769,NULL,1,4,'ReportConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-02-13 13:55:27'),(190,'获取报表配置列表数据','列表','GET','/reportConfig/getData',0,3,1,769,NULL,1,1,'ReportConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-02-13 13:54:59'),(192,'获取报表统计数据','列表','GET','/reportStat/getData',0,4,1,773,NULL,1,1,'ReportConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-02-13 13:56:50'),(195,'锻炼统计',NULL,'GET','/sportExercise/dateStat',0,5,0,669,'chart',1,4,'SportExercise','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportExercise:dateStat','sport/sportExercise/dateStat','sportExercise/dateStat',_binary '',_binary '',_binary '\0',_binary '','','2017-09-02 10:46:04','2020-10-12 19:10:04'),(196,'锻炼最大最小值统计',NULL,'GET','/sportExercise/getByMultiStat',0,4,1,672,'',1,2,'SportExercise','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:04','2019-12-30 15:02:06'),(199,'锻炼统计最大值、最小值',NULL,'GET','/sportExercise/multiStat',0,4,1,672,NULL,1,4,'SportExercise','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportExercise:multiStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-08-04 18:00:34'),(200,'刷新锻炼的最佳记录',NULL,'GET','/sportExercise/refreshMaxStat',0,4,1,672,NULL,1,5,'SportExercise','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportExercise:refreshMaxStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-08-05 07:07:27'),(201,'锻炼概要统计',NULL,'GET','/sportExercise/stat',0,4,1,672,'',1,2,'SportExercise','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:04','2019-12-30 15:01:20'),(203,'锻炼同期对比',NULL,'GET','/sportExercise/yoyStat',0,4,0,669,'yoy',1,5,'SportExercise','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportExercise:yoyStat','sport/sportExercise/yoyStat','sportExercise/yoyStat',_binary '',_binary '',_binary '\0',_binary '','','2017-09-02 10:46:04','2020-10-12 19:10:11'),(206,'刷新运动里程碑统计',NULL,'GET','/sportMilestone/refresh',0,5,1,671,NULL,1,3,'SportMilestone','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportMilestone:refresh',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-08-03 21:23:15'),(207,'运动里程碑统计',NULL,'GET','/sportMilestone/stat',0,4,1,671,NULL,1,2,'SportMilestone','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportMilestone:stat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-08-03 20:05:08'),(208,'修改运动类型',NULL,'POST','/sportType/edit',0,1,1,670,NULL,1,3,'SportType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportType:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-08-02 17:17:19'),(209,'获取运动类型详情',NULL,'GET','/sportType/get',0,3,1,670,'',1,4,'SportType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:04','2019-12-30 15:00:52'),(211,'获取运动类型列表树',NULL,'GET,POST','/sportType/getSportTypeTree',0,3,2,670,NULL,1,5,'SportType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:04','2020-03-08 11:04:00'),(214,'获取看病用药的疾病分类列表',NULL,'GET,POST','/treatDrug/getTreatDrugDiseaseCategoryTree',0,3,2,679,'',1,2,'TreatDrug','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:49','2019-12-30 15:21:27'),(219,'看病分析',NULL,'GET','/treatRecord/analyseStat',0,5,0,674,'analyse',1,4,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatRecord:analyseStat','health/treat/treatRecord/analyseStat','treatRecord/analyseStat',_binary '',_binary '',_binary '\0',_binary '','','2017-09-02 10:46:49','2020-10-12 19:14:34'),(221,'看病统计',NULL,'GET','/treatRecord/dateStat',0,5,0,674,'chart',1,3,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatRecord:dateStat','health/treat/treatRecord/dateStat','treatRecord/dateStat',_binary '',_binary '',_binary '\0',_binary '','','2017-09-02 10:46:49','2020-10-12 19:13:58'),(222,'看病概要统计',NULL,'GET','/treatRecord/stat',0,4,1,678,'',1,7,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatRecord:stat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-02 10:46:49','2020-10-28 13:48:29'),(224,'看病同期对比',NULL,'GET','/treatRecord/yoyStat',0,5,0,674,'yoy',1,5,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatRecord:yoyStat','health/treat/treatRecord/yoyStat','treatRecord/yoyStat',_binary '',_binary '',_binary '\0',_binary '','','2017-09-02 10:46:49','2020-10-12 19:15:02'),(225,'获取用户列表树','用户列表树','GET,POST','/user/getUserTree',0,3,2,800,NULL,1,3,'User','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 10:46:49','2020-02-13 22:05:05'),(226,'获取操作日志中所操作的对象详情','对象详情','GET','/operationLog/getBeanDetail',0,3,1,807,NULL,1,2,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 12:18:31','2020-08-20 11:18:28'),(227,'设置业务主键值','设置业务主键值','GET','/operationLog/setIdValue',0,5,1,807,NULL,1,3,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:operationLog:setIdValue',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-02 14:50:08','2020-08-20 12:55:49'),(229,'获取已经实现的运动里程碑数据',NULL,'GET','/sportExercise/getAchieveMilestones',0,4,1,672,'',1,3,'SportExercise','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-03 14:45:19','2019-12-30 15:02:14'),(230,'获取用药详情',NULL,'GET','/treatDrug/get',0,3,1,679,'',1,3,'TreatDrug','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-03 14:45:19','2019-12-30 15:20:44'),(231,'统计手术信息',NULL,'GET','/treatOperation/stat',0,4,1,680,'',1,4,'TreatOperation','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatOperation:stat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-03 14:45:19','2020-08-09 08:26:44'),(232,'新增看病记录',NULL,'POST','/treatRecord/create',0,0,1,678,'',1,2,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatRecord:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-03 14:45:19','2020-08-06 13:00:28'),(233,'删除看病记录','删除','POST','/treatRecord/delete',0,2,1,678,NULL,1,6,'TreatRecord','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatRecord:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-03 14:45:19','2020-08-06 13:00:45'),(234,'获取看病信息详情',NULL,'GET','/treatRecord/get',0,3,1,678,'',1,3,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-03 14:45:19','2019-12-30 15:18:56'),(235,'获取疾病或者器官列表',NULL,'GET,POST','/treatRecord/getTreatCategoryTree',0,3,1,678,'',1,6,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-03 14:45:19','2019-12-30 15:18:29'),(236,'获取计划配置详情','详情','GET','/planConfig/get',0,3,1,775,NULL,1,3,'PlanConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-04 21:08:50','2020-10-16 10:40:53'),(237,'获取计划配置值列表数据','配置值列表','GET','/planConfigValue/getData',0,3,1,775,NULL,1,5,'PlanConfigValue','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-04 21:08:50','2020-02-13 15:34:10'),(238,'修改锻炼信息',NULL,'POST','/sportExercise/edit',0,1,1,672,NULL,1,3,'SportExercise','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportExercise:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-04 21:08:50','2020-08-03 22:00:38'),(239,'获取锻炼信息详情',NULL,'GET','/sportExercise/get',0,3,1,672,'',1,4,'SportExercise','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-04 21:08:50','2019-12-30 15:02:51'),(242,'新增用户行为分析模板','新增','POST','/userBehaviorConfig/create',0,0,1,794,NULL,1,2,'userBehaviorConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehaviorConfig:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-04 21:08:50','2020-08-16 12:26:56'),(243,'修改用户行为模板','修改','POST','/userBehaviorConfig/edit',0,1,1,794,NULL,1,4,'UserBehaviorConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehaviorConfig:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-04 21:08:50','2020-08-16 12:27:11'),(244,'获取用户行为模板详情','详情','GET','/userBehaviorConfig/get',0,3,1,794,NULL,1,4,'userBehaviorConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-04 21:08:50','2020-10-16 10:47:42'),(246,'获取用户行为模板树','模板树','GET,POST','/userBehaviorConfig/getUserBehaviorConfigTree',0,3,2,794,NULL,1,6,'userBehaviorConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-04 21:08:50','2020-02-13 19:54:56'),(250,'获取公司树','公司树','GET,POST','/company/getCompanyTree',0,3,2,782,NULL,1,4,'Company','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-02-13 15:57:14'),(252,'人生经历统计','人生经历统计首页','GET','/lifeExperience/dateStat',0,5,0,685,'chart',1,4,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperience:dateStat','life/lifeExperience/dateStat','lifeExperience/dateStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-10-12 21:09:45'),(253,'修改人生经历','修改','POST','/lifeExperience/edit',0,1,1,692,NULL,1,3,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperience:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-08-10 16:52:27'),(254,'获取人生经历详情','获取详情','GET','/lifeExperience/get',0,3,1,692,NULL,1,5,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-02-13 10:32:38'),(255,'新增计划配置','新增','POST','/planConfig/create',0,0,1,775,NULL,1,3,'PlanConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planConfig:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-08-12 14:02:12'),(256,'修改计划配置','修改','POST','/planConfig/edit',0,1,1,775,NULL,1,3,'PlanConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planConfig:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-08-12 14:02:04'),(257,'获取计划配置的第一天统计日期','配置的第一天统计日期','POST','/planConfig/getFirstStatDay',0,3,1,775,NULL,1,6,'PlanConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-02-13 15:34:40'),(261,'手动统计计划报告','手动统计','POST','/planReport/manualStat',0,4,1,770,NULL,1,4,'PlanReport','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planReport:manualStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-08-13 12:56:16'),(262,'获取阅读详情','详情','GET','/readingRecord/get',0,3,1,779,NULL,1,3,'ReadingRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-02-13 15:47:10'),(263,'新增阅读记录','新增','POST','/readingRecordDetail/create',0,0,1,780,NULL,1,4,'ReadingRecordDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',10,0,1,0,'read:readingRecordDetail:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-08-15 11:05:58'),(264,'修改阅读详情','修改','POST','/readingRecordDetail/edit',0,1,1,780,NULL,1,4,'ReadingRecordDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecordDetail:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-08-15 11:06:09'),(265,'获取阅读详情的详细信息','详细','GET','/readingRecordDetail/get',0,3,1,780,NULL,1,4,'ReadingRecordDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-02-13 15:50:18'),(272,'加班统计','加班分析','GET','/workOvertime/dateStat',0,5,0,781,'chart',1,3,'WorkOvertime','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:workOvertime:dateStat','work/workOvertime/dateStat','workOvertime/dateStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2017-09-05 12:39:42','2020-10-12 21:25:05'),(275,'获取音乐练习曲子详情',NULL,'GET','/musicPracticeTune/get',0,3,1,164,'',1,3,'MusicPracticeTune','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-06 11:47:49','2019-12-30 14:50:53'),(276,'获取修改类操作日志记录','获取修改记录','GET','/operationLog/getEditLogData',0,3,1,65,NULL,1,3,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'log:operationLog:getEditLogData',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 11:47:49','2020-08-26 13:56:55'),(277,'新增阅读记录','新增','POST','/readingRecord/create',0,0,1,779,NULL,1,2,'ReadingRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecord:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 11:47:49','2020-08-15 09:21:10'),(278,'删除阅读记录','删除','POST','/readingRecord/delete',0,2,1,779,NULL,1,5,'ReadingRecord','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecord:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 11:47:49','2020-08-15 09:21:28'),(279,'修改阅读记录','修改','POST','/readingRecord/edit',0,1,1,779,NULL,1,3,'ReadingRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecord:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 11:47:49','2020-08-15 09:21:18'),(280,'修改里程碑',NULL,'POST','/sportMilestone/edit',0,1,1,671,NULL,1,3,'SportMilestone','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportMilestone:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 12:05:18','2020-08-03 18:10:17'),(281,'获取里程碑详情',NULL,'GET','/sportMilestone/get',0,3,1,671,'',1,4,'SportMilestone','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-06 12:05:18','2019-12-30 15:01:52'),(282,'修改看病记录',NULL,'POST','/treatRecord/edit',0,1,1,678,'',1,3,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatRecord:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-06 12:17:34','2020-08-06 13:00:37'),(283,'修改用药管理',NULL,'POST','/treatDrug/edit',0,1,1,679,'',1,3,'TreatDrug','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatDrug:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-06 12:19:20','2020-08-07 08:32:54'),(284,'修改手术信息',NULL,'POST','/treatOperation/edit',0,1,1,680,'',1,3,'TreatOperation','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatOperation:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-06 12:19:20','2020-08-07 08:36:19'),(285,'获取手术信息详情',NULL,'GET','/treatOperation/get',0,3,1,680,'',1,3,'TreatOperation','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-06 12:19:20','2019-12-30 15:21:00'),(286,'修改身体异常信息',NULL,'POST','/bodyAbnormalRecord/edit',0,1,1,682,NULL,1,3,'BodyAbnormalRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyAbnormalRecord:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 12:25:30','2020-08-10 08:51:20'),(287,'获取身体异常详情',NULL,'GET','/bodyAbnormalRecord/get',0,3,1,682,NULL,1,4,'BodyAbnormalRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 12:25:30','2020-01-08 13:24:48'),(288,'修改公司信息','修改','POST','/company/edit',0,1,1,782,NULL,1,3,'Company','id',0,_binary '',_binary '',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:company:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 12:49:12','2020-08-15 15:34:15'),(289,'获取公司详情','详情','GET','/company/get',0,3,1,782,NULL,1,4,'Company','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 12:49:12','2020-02-13 15:57:04'),(290,'修改加班记录','修改','POST','/workOvertime/edit',0,1,1,783,NULL,1,3,'WorkOvertime','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:workOvertime:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 12:51:26','2020-08-15 15:47:22'),(291,'获取加班信息详情','详情','GET','/workOvertime/get',0,3,1,783,NULL,1,3,'WorkOvertime','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 12:51:26','2020-02-13 16:00:17'),(292,'修改出差信息','修改','POST','/businessTrip/edit',0,1,1,784,NULL,1,3,'BusinessTrip','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:businessTrip:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 12:53:41','2020-08-15 16:23:39'),(293,'获取出差详情','详情','GET','/businessTrip/get',0,3,1,784,NULL,1,4,'BusinessTrip','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 12:53:41','2020-02-13 16:03:45'),(296,'修改用户','修改','POST','/user/edit',0,1,1,800,NULL,1,3,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:user:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 13:01:26','2020-08-19 14:21:44'),(297,'获取用户详情','用户详情','GET','/user/get',0,3,1,800,NULL,1,4,'User','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-06 13:01:26','2020-02-13 20:22:39'),(300,'新增身体数据',NULL,'POST','/bodyBasicInfo/create',0,0,1,683,NULL,1,3,'BodyBasicInfo','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyBasicInfo:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-08 17:25:11','2020-08-09 21:26:55'),(301,'删除商品类型','删除','POST','/goodsType/delete',0,2,1,644,NULL,1,4,'GoodsType','ids',1,_binary '',_binary '',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:goodsType:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-08 17:25:11','2020-07-23 15:10:18'),(303,'新增运动锻炼信息',NULL,'POST','/sportExercise/create',0,0,1,672,NULL,1,3,'SportExercise','id',0,_binary '',_binary '',_binary '',1,0,0,0,0,5,0,_binary '\0',10,0,1,0,'sport:sportExercise:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-08 17:25:11','2020-08-03 22:00:30'),(304,'获取下一个待实现的里程碑',NULL,'GET','/sportExercise/getNextAchieveMilestone',0,3,1,672,'',1,4,'SportExercise','id',0,_binary '',_binary '',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-08 17:25:11','2019-12-30 15:02:28'),(305,'获取用户行为类型树','类型树','GET,POST','/userBehaviorConfig/getUserBehaviorTypeTree',0,3,2,794,NULL,1,5,'UserBehaviorConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-08 17:25:11','2020-10-16 10:47:02'),(307,'获取系统日志请求参数信息','参数信息','GET','/systemLog/getParas',0,3,1,808,NULL,1,4,'SystemLog','id',0,_binary '',_binary '',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-08 23:35:53','2020-02-13 20:53:45'),(309,'删除音乐练习曲子','删除','POST','/musicPracticeTune/delete',0,2,1,164,NULL,1,4,'MusicPracticeTune','ids',0,_binary '',_binary '',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPracticeTune:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-09 11:09:38','2020-07-31 15:16:38'),(310,'获取音乐练习曲子树',NULL,'GET,POST','/musicPracticeTune/getMusicPracticeTuneTree',0,5,2,164,'',1,5,'MusicPracticeTune','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-09 11:09:38','2019-12-30 14:48:27'),(316,'创建城市地理位置','新增','POST','/cityLocation/create',0,0,1,686,NULL,1,0,'CityLocation','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'life:cityLocation:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-10 16:00:37'),(317,'创建消费类型',NULL,'POST','/consumeType/create',0,0,1,691,NULL,1,0,'ConsumeType','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:consumeType:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-10 16:27:29'),(318,'修改消费类型',NULL,'POST','/consumeType/edit',0,0,1,691,NULL,1,0,'ConsumeType','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:consumeType:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-10 16:27:39'),(319,'获取消费类型',NULL,'GET','/consumeType/get',0,0,1,691,NULL,1,0,'ConsumeType','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-01-08 14:53:18'),(320,'获取消费类型树',NULL,'GET,POST','/consumeType/getConsumeTypeTree',0,0,2,691,NULL,1,0,NULL,'id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-03-08 14:51:57'),(323,'创建梦想',NULL,'POST','/dream/create',0,0,1,668,'user',1,0,'Dream','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'dream:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-01 12:57:06'),(324,'梦想统计',NULL,'GET','/dream/stat',0,0,1,668,'',1,0,'Dream','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-15 10:17:17','2019-12-30 14:53:52'),(325,'修正人生经历中花费和天数',NULL,'POST','/lifeExperienceSum/revise',0,0,1,693,NULL,1,3,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceSum:revise',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-11 10:04:55'),(326,'人生经历迁徙地图','地图详情统计','GET','/lifeExperience/transferMapByLifeExpStat',0,4,1,692,NULL,1,0,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperience:transferMapByLifeExpStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-10 18:44:41'),(327,'新增人生经历消费','新增','POST','/lifeExperienceConsume/create',0,0,1,759,NULL,1,4,'LifeExperienceSum','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceConsume:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-10 21:52:13'),(328,'删除人生经历消费','删除','POST','/lifeExperienceConsume/delete',0,2,1,759,NULL,1,3,'LifeExperienceSum','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceConsume:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-10 21:52:01'),(329,'修改人生经历消费','修改','POST','/lifeExperienceConsume/edit',0,1,1,759,NULL,1,2,'LifeExperienceSum','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceConsume:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-10 22:14:07'),(330,'获取人生经历消费详情','获取详情','GET','/lifeExperienceConsume/get',0,3,1,759,NULL,1,4,'LifeExperienceSum','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-02-13 10:26:43'),(331,'获取人生经历消费中的购买记录',NULL,'GET,POST','/lifeExperienceConsume/getBuyRecordTree',0,0,1,648,'',1,6,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-15 10:17:17','2019-12-30 10:33:30'),(333,'新增人生经历详情','新增','POST','/lifeExperienceDetail/create',0,0,1,760,NULL,1,0,'LifeExperienceDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceDetail:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-10 20:47:10'),(334,'修改人生经历详情','修改','POST','/lifeExperienceDetail/edit',0,1,1,760,NULL,1,1,'LifeExperienceDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceDetail:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-10 20:47:19'),(335,'获取人生经历详情的记录','获取详情','GET','/lifeExperienceDetail/get',0,3,1,760,NULL,1,2,'LifeExperienceDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-02-13 10:29:37'),(337,'获取功能点树数据','功能点树数据','GET,POST','/systemFunction/getSystemFunctionMenuTree',0,5,1,805,NULL,1,0,'SystemFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-02-27 13:54:05'),(339,'新增药品记录',NULL,'POST','/treatDrug/create',0,0,1,679,'',1,0,'TreatDrug','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatDrug:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-15 10:17:17','2020-08-07 08:32:34'),(340,'删除药品记录',NULL,'POST','/treatDrug/delete',0,0,1,679,NULL,1,0,'TreatDrug','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatDrug:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-07 08:32:45'),(341,'用药日历统计',NULL,'GET','/treatDrugDetail/calendarStat',0,4,1,346,'',1,7,'TreatDrugDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatDrugDetail:calendarStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-15 10:17:17','2020-08-08 10:32:08'),(342,'新增用药详情',NULL,'POST','/treatDrugDetail/create',0,0,1,346,'',1,8,'','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatDrugDetail:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-15 10:17:17','2020-08-08 09:43:03'),(343,'删除用药记录',NULL,'POST','/treatDrugDetail/delete',0,2,1,346,NULL,1,9,'TreatDrugDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatDrugDetail:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-15 10:17:17','2020-08-08 09:47:15'),(344,'修改用药记录',NULL,'POST','/treatDrugDetail/edit',0,0,1,346,'',1,10,'TreatDrugDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatDrugDetail:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-15 10:17:17','2020-08-08 09:47:07'),(345,'获取用药记录详情',NULL,'GET','/treatDrugDetail/get',0,0,1,346,'',1,11,'TreatDrugDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-15 10:17:17','2020-08-08 09:43:53'),(346,'用药记录',NULL,'GET','/treatDrugDetail/getData',0,0,0,674,'drug',1,8,'TreatDrugDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatDrugDetail:query','health/treat/treatDrugDetail/index',NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2017-09-15 10:17:17','2020-10-12 19:18:04'),(347,'刷新进度',NULL,'GET','/dream/refreshRate',0,4,1,668,NULL,1,5,'Dream','id',0,_binary '',_binary '',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'dream:refreshRate',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-16 16:50:33','2020-08-02 14:58:53'),(348,'获取操作日志的返回值','返回值','GET','/operationLog/getReturnData',0,5,1,807,NULL,1,0,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2017-09-16 16:50:33','2020-02-13 20:49:53'),(349,'新增通用记录','新增','POST','/commonRecord/create',0,0,1,791,NULL,1,0,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'common:commonRecord:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:39','2020-08-16 08:55:30'),(350,'获取通用记录详情','详情','GET','/commonRecord/get',0,3,1,791,NULL,1,2,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:39','2020-02-13 16:22:40'),(354,'获取通用记录类型详情','详情','GET','/commonRecordType/get',0,3,1,790,NULL,1,0,'CommonRecordType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:39','2020-10-19 21:50:37'),(359,'八小时之外统计',NULL,'GET','/dataAnalyse/afterEightHourAnalyseStat',0,3,0,792,'aeh',1,8,'DataAnalyse','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehavior:aehAnalyseStat','behavior/userBehavior/aehAnalyseStat','userBehavior/aehAnalyseStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2018-01-22 21:45:39','2020-10-12 21:54:33'),(360,'新增饮食记录','新增','POST','/diet/create',0,0,1,787,NULL,1,0,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:diet:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:39','2020-08-15 17:12:01'),(361,'获取饮食记录详情','详情','GET','/diet/get',0,3,1,787,NULL,1,0,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:39','2020-02-13 16:14:56'),(365,'饮食统计','饮食统计首页','GET','/diet/stat',0,3,0,786,'chart',1,3,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:diet:stat','food/diet/stat','diet/stat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2018-01-22 21:45:39','2020-10-12 21:40:05'),(366,'维护类-删除缓存',NULL,'POST','/maintain/deleteCache',0,2,1,785,NULL,1,0,'Maintain','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-02-28 10:25:00'),(367,'维护类获取缓存',NULL,'GET','/maintain/getCache',0,3,1,846,NULL,1,0,'Maintain','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-09-06 17:12:57'),(368,'删除音乐练习记录',NULL,'POST','/musicPractice/delete',0,0,1,665,NULL,1,0,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPractice:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-07-31 08:44:27'),(369,'新增提醒配置模板','新增','POST','/notifyConfig/create',0,0,1,763,NULL,1,0,'NotifyConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:notify:notifyConfig:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-08-11 12:58:24'),(370,'获取提醒配置模板树','获取配置树','GET,POST','/notifyConfig/getNotifyConfigTree',0,3,1,763,NULL,1,1,'NotifyConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-03-08 14:54:09'),(372,'时间线统计','时间线统计','GET','/planReport/timelineStat',0,3,0,772,'chart',1,7,'PlanReport','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planReport:timelineStat','report/plan/planReport/timelineStat','planReport/timelineStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-08-14 07:47:14'),(373,'新增模板值配置',NULL,'POST','/statValueConfig/create',0,0,1,944,NULL,1,0,'StatValueConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:statValueConfig:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-10-16 10:29:59'),(374,'获取模板值配置',NULL,'GET','/statValueConfig/get',0,3,1,944,NULL,1,0,'StatValueConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-10-16 10:35:16'),(375,'获取模板值配置列表',NULL,'GET','/statValueConfig/getConfigs',0,3,1,944,NULL,1,0,'StatValueConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-10-16 10:35:24'),(382,'用户行为比对','用户行为比对','GET','/userBehavior/compare',0,4,0,792,'yoy',1,7,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehavior:compare','behavior/userBehavior/compare','userBehavior/compare',_binary '',_binary '',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-10-26 16:52:27'),(383,'新增用户行为','新增','POST','/userBehavior/create',0,0,1,796,NULL,1,0,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehavior:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-08-16 13:20:14'),(384,'修改用户行为','修改','POST','/userBehavior/edit',0,1,1,796,NULL,1,0,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehavior:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-08-16 13:20:21'),(385,'获取用户行为详情','详情','GET','/userBehavior/get',0,3,1,796,NULL,1,2,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-02-13 19:45:42'),(387,'获取用户行为配置树','配置树','GET,POST','/userBehavior/getUserBehaviorConfigTree',0,3,2,796,NULL,1,5,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-02-13 19:46:10'),(388,'用户用户行为关键字树','行为关键字树','GET,POST','/userBehavior/getUserBehaviorKeywordsTree',0,3,1,796,NULL,1,6,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-02-13 19:46:27'),(389,'获取用户行为树','树列表','GET,POST','/userBehavior/getUserBehaviorTree',0,3,1,796,NULL,1,6,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-02-13 19:47:27'),(392,'用户行为统计','用户行为统计','GET','/userBehavior/stat',0,4,0,792,'chart',1,4,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehavior:stat','behavior/userBehavior/stat','userBehavior/stat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-10-12 21:53:13'),(395,'删除用户行为模板','删除','POST','/userBehaviorConfig/delete',0,2,1,794,NULL,1,4,'UserBehaviorConfig','ids',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehaviorConfig:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-08-16 12:27:03'),(397,'新增或修改用户提醒的提醒配置','新增或修改提醒配置','POST','/userNotify/addOrEditRemind',0,0,1,764,NULL,1,3,'UserNotifyRemind','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-02-13 10:49:09'),(398,'新增用户提醒','新增','POST','/userNotify/create',0,0,1,764,NULL,1,0,'UserNotify','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:notify:userNotify:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-08-11 16:56:18'),(399,'修改用户提醒','修改','POST','/userNotify/edit',0,1,1,764,NULL,1,3,'UserNotify','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:notify:userNotify:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-08-11 16:56:38'),(400,'获取用户提醒配置详情','获取详情','GET','/userNotify/get',0,3,1,764,NULL,1,4,'UserNotify','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-02-13 10:49:51'),(402,'获取用户行为配置树','获取用户行为配置树','GET,POST','/userNotify/getNotifyConfigTree',0,3,1,764,NULL,1,6,'UserNotify','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:40','2020-02-13 10:50:29'),(403,'获取用户行为配置提醒详情','获取配置提醒','GET','/userNotify/getRemind',0,3,1,764,NULL,1,8,'UserNotifyRemind','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:41','2020-02-13 10:51:05'),(405,'新增或修改用户计划的提醒配置','新增或修改提醒配置','POST','/userPlan/addOrEditRemind',0,0,1,774,NULL,1,1,'UserPlanRemind','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:41','2020-02-13 15:27:08'),(406,'获取用户计划详情','详情','GET','/userPlan/get',0,3,1,774,NULL,1,0,'UserPlan','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:41','2020-02-13 15:28:00'),(408,'获取用户计划树','配置树','GET,POST','/userPlan/getUserPlanTree',0,3,1,774,NULL,1,3,'PlanConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:41','2020-08-13 08:45:59'),(409,'获取用户计划的提醒配置','获取提醒配置','GET','/userPlan/getRemind',0,3,1,774,NULL,1,4,'UserPlanRemind','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:41','2020-02-13 15:28:56'),(412,'新增加班记录','新增','POST','/workOvertime/create',0,0,1,783,NULL,1,0,'WorkOvertime','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:workOvertime:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-01-22 21:45:41','2020-08-15 15:47:15'),(413,'新增出差记录','新增','POST','/businessTrip/create',0,0,1,784,NULL,1,0,'BusinessTrip','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:businessTrip:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:10','2020-08-15 16:23:22'),(414,'删除出差记录','删除','POST','/businessTrip/delete',0,2,1,784,NULL,1,0,'BusinessTrip','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:businessTrip:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:10','2020-08-15 16:23:30'),(415,'删除消费记录',NULL,'POST','/buyRecord/delete',0,2,1,648,NULL,1,0,'BuyRecord','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:10','2020-07-23 15:46:50'),(416,'新增购买类型',NULL,'POST','/buyType/create',0,0,1,643,NULL,1,1,'BuyType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyType:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:10','2020-07-23 14:59:34'),(417,'通用记录统计','通用记录统计','GET','/commonRecord/dateStat',0,4,0,789,'chart',1,3,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'common:commonRecord:dateStat','common/commonRecord/dateStat','commonRecord/dateStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2018-02-17 14:12:10','2020-10-12 21:47:22'),(418,'修改通用记录','修改','POST','/commonRecord/edit',0,1,1,791,NULL,1,0,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'common:commonRecord:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:10','2020-08-16 08:55:38'),(419,'新增通用记录类型','新增','POST','/commonRecordType/create',0,0,1,790,NULL,1,0,'CommonRecordType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'common:commonRecordType:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:10','2020-10-19 21:50:42'),(420,'删除通用记录类型','删除','POST','/commonRecordType/delete',0,2,1,790,NULL,1,2,'CommonRecordType','ids',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'common:commonRecordType:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:10','2020-10-19 21:50:58'),(421,'新增公司','新增','POST','/company/create',0,0,1,782,NULL,1,0,'Company','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:company:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:10','2020-08-15 15:34:07'),(422,'删除消费类型','删除','POST','/consumeType/delete',0,2,1,691,NULL,1,0,'ConsumeType','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:consumeType:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:10','2020-08-10 16:27:47'),(423,'修改数据库清理配置','修改','POST','/databaseClean/edit',0,1,1,816,NULL,1,0,'DatabaseClean','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:databaseClean:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:10','2020-08-22 14:58:21'),(424,'获取数据库清理配置','详情','GET','/databaseClean/get',0,3,1,816,NULL,1,1,'DatabaseClean','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:11','2020-02-13 21:14:52'),(427,'新增日记','新增','POST','/diary/create',0,0,1,777,NULL,1,0,'Diary','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:11','2020-02-13 15:40:21'),(428,'删除日记','删除','POST','/diary/delete',0,2,1,777,NULL,1,0,'Diary','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:11','2020-05-25 10:34:48'),(429,'获取日记统计数据','统计','GET','/diary/stat',0,4,1,777,NULL,1,0,'Diary','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:11','2020-02-13 15:40:45'),(430,'修改饮食习惯','修改','POST','/diet/edit',0,1,1,787,NULL,1,0,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:diet:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:11','2020-08-15 17:12:07'),(431,'删除梦想',NULL,'POST','/dream/delete',0,2,1,668,NULL,1,0,'Dream','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'dream:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:11','2020-08-01 12:57:23'),(432,'新增商品类型',NULL,'POST','/goodsType/create',0,0,1,644,NULL,1,0,'GoodsType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:goodsType:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:11','2020-07-23 15:09:53'),(433,'新增人生经历','新增','POST','/lifeExperience/create',0,0,1,692,NULL,1,0,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperience:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:11','2020-08-10 16:51:55'),(434,'删除人生经历','删除','POST','/lifeExperience/delete',0,2,1,692,NULL,1,2,'LifeExperience','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperience:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:11','2020-08-10 16:52:03'),(435,'删除人生经历详情','删除','POST','/lifeExperienceDetail/delete',0,2,1,760,NULL,1,4,'LifeExperienceDetail','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceDetail:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:11','2020-08-10 20:47:32'),(436,'登出',NULL,'GET','/main/logout',0,0,1,922,NULL,1,0,NULL,'id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:12','2020-08-05 19:45:48'),(437,'新增乐器',NULL,'POST','/musicInstrument/create',0,0,1,664,NULL,1,0,'MusicInstrument','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicInstrument:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:12','2020-07-30 21:48:37'),(438,'删除乐器','删除','POST','/musicInstrument/delete',0,2,1,664,NULL,1,1,'MusicInstrument','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicInstrument:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:12','2020-07-30 21:47:59'),(439,'音乐练习分析',NULL,'GET','/musicPractice/timeStat',0,4,0,663,'analyse',1,4,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPractice:timeStat','music/musicPractice/timeStat','musicPractice/timeStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2018-02-17 14:12:12','2020-10-12 19:04:47'),(440,'删除提醒配置模板','删除','POST','/notifyConfig/delete',0,2,1,763,NULL,1,0,'NotifyConfig','ids',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:notify:notifyConfig:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:12','2020-08-11 12:58:33'),(441,'修改睡眠数据',NULL,'POST','/sleep/edit',0,1,1,684,NULL,1,0,'Sleep','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:sleep:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:12','2020-08-10 13:47:17'),(442,'获取睡眠详情',NULL,'GET','/sleep/get',0,3,1,684,NULL,1,0,'Sleep','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:12','2020-01-08 14:36:57'),(444,'初始化睡眠数据',NULL,'GET','/sleep/init',0,5,1,684,NULL,1,0,'Sleep','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:12','2020-01-08 14:37:05'),(446,'删除锻炼数据',NULL,'POST','/sportExercise/delete',0,2,1,672,NULL,1,0,'SportExercise','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportExercise:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:13','2020-08-03 22:00:19'),(447,'新增锻炼里程碑',NULL,'POST','/sportMilestone/create',0,0,1,671,NULL,1,0,'SportMilestone','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportMilestone:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:13','2020-08-03 18:09:59'),(448,'删除锻炼里程碑','删除','POST','/sportMilestone/delete',0,2,1,671,NULL,1,0,'SportMilestone','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportMilestone:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:13','2020-08-03 18:10:09'),(449,'新增锻炼类型',NULL,'POST','/sportType/create',0,0,1,670,NULL,1,0,'SportType','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportType:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:13','2020-08-02 17:17:34'),(450,'删除锻炼类型','删除','POST','/sportType/delete',0,2,1,670,NULL,1,1,'SportType','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportType:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:13','2020-08-02 17:17:43'),(451,'获取系统日志详情','详情','GET','/systemLog/get',0,3,1,808,NULL,1,0,'SystemLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:13','2020-02-13 20:53:27'),(454,'新增调度触发器','新增','POST','/taskTrigger/create',0,0,1,812,NULL,1,7,'TaskTrigger','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskTrigger:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:13','2020-08-20 21:11:24'),(455,'删除调度触发器','删除','POST','/taskTrigger/delete',0,2,1,812,NULL,1,9,'TaskTrigger','ids',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskTrigger:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:13','2020-08-20 21:11:33'),(456,'修改调度触发器','修改','POST','/taskTrigger/edit',0,1,1,812,NULL,1,11,'TaskTrigger','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskTrigger:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:13','2020-08-20 21:11:41'),(457,'获取调度触发器详情','详情','GET','/taskTrigger/get',0,3,1,812,NULL,1,12,'TaskTrigger','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:14','2020-02-13 21:05:55'),(458,'获取调度触发器列表树','列表树','GET,POST','/taskTrigger/getTaskTriggerTree',0,3,1,812,NULL,1,14,'TaskTrigger','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:14','2020-02-13 21:06:09'),(459,'手动触发调度','手动触发','POST','/taskTrigger/manualNew',0,5,1,812,NULL,1,16,'TaskTrigger','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskTrigger:manualNew',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:14','2020-08-21 19:22:20'),(460,'新增手术',NULL,'POST','/treatOperation/create',0,0,1,680,'',1,1,'TreatOperation','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatOperation:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2018-02-17 14:12:14','2020-08-07 08:36:01'),(461,'删除手术','删除','POST','/treatOperation/delete',0,2,1,680,NULL,1,2,'TreatOperation','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatOperation:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:14','2020-08-07 08:36:10'),(462,'获取用户消息详情','详情','GET','/userMessage/get',0,3,1,809,NULL,1,3,'UserMessage','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:14','2020-03-06 14:40:06'),(465,'重发用户消息','重发','GET','/userMessage/resend',0,5,1,809,NULL,1,5,'UserMessage','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:userMessage:resend',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:14','2020-08-20 15:51:03'),(466,'获取用户报表配置数据','列表','GET','/userReportConfig/getData',0,3,1,771,NULL,1,0,'UserReportConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:14','2020-02-13 13:50:31'),(469,'获取用户积分记录的消息详情','消息详情','GET','/userRewardPointRecord/getMessageContent',0,3,1,801,NULL,1,2,'UserRewardPointRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:15','2020-02-13 20:29:06'),(471,'积分来源统计','来源统计用户积分','GET','/userRewardPointRecord/pointsSourceStat',0,4,0,797,'chart',1,5,'UserRewardPointRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userRewardPointRecord:pointsSourceStat','data/userRewardPointRecord/pointsSourceStat','userRewardPointRecord/pointsSourceStat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2018-02-17 14:12:15','2020-10-12 21:57:20'),(473,'用户积分统计','时间线统计','GET','/userRewardPointRecord/pointsTimelineStat',0,4,0,797,'chart',1,6,'UserRewardPointRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userRewardPointRecord:pointsTimelineStat','data/userRewardPointRecord/pointsTimelineStat','userRewardPointRecord/pointsTimelineStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2018-02-17 14:12:15','2020-10-12 21:57:31'),(474,'用户积分值统计','值统计','GET','/userRewardPointRecord/pointsValueStat',0,4,1,801,NULL,1,2,'UserRewardPointRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userRewardPointRecord:pointsValueStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-02-17 14:12:15','2020-08-19 10:31:05'),(475,'饮食分析','饮食分析','GET','/diet/analyse',0,4,0,786,'analyse',1,4,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:diet:analyse','food/diet/analyse','diet/analyse',_binary '',_binary '',_binary '\0',_binary '',NULL,'2018-03-25 11:09:20','2020-10-12 21:44:19'),(476,'获取饮食习惯的最后一次地点','最后一次地点','GET','/diet/getLastLocation',0,3,1,787,NULL,1,4,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-25 11:09:20','2020-02-13 16:17:04'),(477,'新增睡眠记录',NULL,'POST','/sleep/create',0,0,1,684,NULL,1,0,'Sleep','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:sleep:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-25 11:09:20','2020-08-10 13:47:32'),(478,'用户行为的操作记录统计','操作记录统计','GET','/userBehavior/userOperationStat',0,4,1,479,NULL,1,0,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-25 11:09:20','2020-02-13 19:51:53'),(479,'用户操作记录统计','用户操作记录统计','GET','/userBehavior/userOperationStatList.html',0,3,0,792,'chart',1,5,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-25 11:09:20','2020-10-12 21:53:32'),(480,'用户提醒配置统计','统计','GET','/userNotify/getStat',0,4,1,764,NULL,1,3,'UserNotify','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:notify:userNotify:stat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-25 11:09:20','2020-08-12 10:59:48'),(481,'获取用户提醒配置树','获取配置树','GET,POST','/userNotify/getUserNotifyTree',0,3,1,764,NULL,1,0,'UserNotify','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-25 11:09:20','2020-03-08 14:53:27'),(482,'获取业务类型树','业务类型树','GET,POST','/common/getBussTypeTree',0,3,1,785,NULL,1,0,'BussType','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-29 22:34:42','2020-03-08 14:53:35'),(483,'登录',NULL,'GET','/main/login',0,5,1,922,NULL,1,0,NULL,'id',0,_binary '',_binary '\0',_binary '',0,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-29 22:34:42','2020-08-05 19:46:01'),(484,'个人设置-用户详情',NULL,'GET','/main/myInfo',0,3,1,657,NULL,1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-29 22:34:42','2020-01-30 13:12:20'),(487,'获取用户级别范围内的提醒配置模板','获取配置模板树','GET,POST','/notifyConfig/getNotifyConfigForUserTree',0,3,1,763,NULL,1,4,'NotifyConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-29 22:34:42','2020-02-13 10:52:17'),(488,'获取用户级别范围内的计划配置模板','计划配置模板列表','GET,POST','/planConfig/getPlanConfigForUserTree',0,3,1,774,NULL,1,5,'PlanConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-29 22:34:42','2020-02-13 15:29:34'),(489,'获取用户级别范围内的报表配置模板','用户报表配置模板列表','GET,POST','/reportConfig/getReportConfigForUserTree',0,3,1,769,NULL,1,2,'ReportConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-29 22:34:42','2020-02-13 13:48:21'),(490,'修改个人信息',NULL,'POST','/user/editMyInfo',0,1,1,657,NULL,1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-29 22:34:42','2020-01-30 13:12:27'),(491,'查询个人信息详情','个人信息详情','GET','/user/getMyInfo',0,3,1,657,NULL,1,3,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-29 22:34:42','2020-02-13 20:17:34'),(492,'设置任务完成','完成','POST','/userCalendar/finish',0,5,1,798,NULL,1,3,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userCalendar:finish',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-29 22:34:42','2020-08-18 10:33:38'),(495,'今日任务列表','今日任务列表','GET','/userCalendar/todayCalendarList',0,3,0,798,NULL,1,5,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-29 22:34:42','2020-02-13 20:03:24'),(497,'获取用户的消息详情','用户的消息详情','GET','/userMessage/getByUser',0,3,1,809,NULL,1,1,'UserMessage','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-29 22:34:42','2020-02-13 20:57:32'),(498,'获取用户计划统计数据','统计数据','GET','/userPlan/getStat',0,3,1,774,NULL,1,0,'UserPlan','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-03-29 22:34:42','2020-02-13 15:30:09'),(499,'获取最后一次饮食习惯记录','最后一次饮食习惯记录','GET','/diet/getLastDiet',0,3,1,787,NULL,1,6,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-04-21 20:32:57','2020-02-13 16:17:44'),(500,'登录验证',NULL,'POST','/main/loginAuth',0,5,1,922,NULL,1,0,NULL,'id',0,_binary '',_binary '\0',_binary '',0,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2018-04-21 20:32:57','2020-08-05 19:46:08'),(503,'睡眠分析统计',NULL,'GET','/sleep/analyseStat',0,4,0,677,'analyse',1,2,'Sleep','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:sleep:analyseStat','health/body/sleep/analyseStat','sleep/analyseStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2018-04-21 20:32:57','2020-10-12 19:31:34'),(504,'创建账户',NULL,'POST','/account/create',1,0,1,652,NULL,1,0,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:account:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-01-31 12:27:26','2020-07-25 11:24:54'),(505,'生成账户快照',NULL,'POST','/account/createSnapshot',1,0,1,652,NULL,1,6,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:account:createSnapshotInfo',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-01-31 12:27:26','2020-07-26 17:12:48'),(506,'修改账户',NULL,'POST','/account/edit',1,0,1,652,NULL,1,1,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:account:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-01-31 12:27:26','2020-07-25 11:25:06'),(507,'获取账号信息',NULL,'GET','/account/get',1,0,1,652,'',1,2,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-01-31 12:27:26','2019-12-30 13:27:53'),(508,'获取账号树',NULL,'GET,POST','/account/getAccountTree',1,0,2,652,'',1,6,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-01-31 12:27:26','2019-12-30 13:28:41'),(511,'账户统计','账户统计','GET','/account/stat',0,0,0,642,'chart',1,9,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,1,'fund:account:stat','fund/account/stat','account/stat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2019-01-31 12:27:26','2020-10-12 18:28:53'),(512,'账户流水分析','分析','GET','/accountFlow/analyse',1,4,0,642,'analyse',1,3,'AccountFlow','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:accountFlow:analyse','fund/accountFlow/analyse','accountFlow/analyse',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2019-01-31 12:27:26','2021-02-26 14:59:28'),(517,'概要统计',NULL,'GET','/main/generalStat',1,4,0,657,'chart',1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'dashboard:generalStat','dashboard/generalStat','generalStat',_binary '\0',_binary '',_binary '\0',_binary '','','2019-01-31 12:27:26','2021-03-09 16:47:58'),(518,'用户积分日统计','日统计','GET','/userRewardPointRecord/dailyStat',0,4,1,801,NULL,1,1,'UserRewardPointRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-01-31 12:27:26','2020-02-13 20:28:51'),(519,'修改账户',NULL,'POST','/account/change',0,0,1,652,NULL,1,8,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:account:change',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-04-21 19:38:32','2020-07-27 09:21:18'),(520,'新增预算',NULL,'POST','/budget/create',0,0,1,656,NULL,1,0,'Budget','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budget:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-07-28 21:40:03'),(521,'修改预算',NULL,'POST','/budget/edit',0,0,1,656,NULL,1,1,'Budget','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budget:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-07-28 21:40:20'),(522,'获取预算详情',NULL,'GET','/budget/get',0,0,1,656,'',1,2,'Budget','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-04-21 19:38:33','2019-12-30 13:43:50'),(523,'获取预算树',NULL,'GET,POST','/budget/getBudgetTree',0,0,2,656,'',1,4,'Budget','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-04-21 19:38:33','2019-12-30 13:44:17'),(525,'获取预算详情列表',NULL,'GET','/budget/getInfoList',0,0,1,656,'',1,6,'Budget','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-04-21 19:38:33','2019-12-30 13:45:02'),(526,'获取未完成的预算列表',NULL,'GET','/budget/getUnCompletedBudget',0,0,1,656,'',1,0,'Budget','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-04-21 19:38:33','2019-12-30 13:45:24'),(528,'预算统计',NULL,'GET','/budget/stat',0,0,0,642,'chart',1,8,'Budget','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budget:stat','fund/budget/stat','budget/stat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-10-12 18:28:46'),(529,'新增预算流水',NULL,'POST','/budgetLog/create',0,0,1,912,NULL,1,7,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetLog:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-08-06 09:46:07'),(532,'预算执行统计',NULL,'GET','/budgetLog/stat',0,0,0,642,'chart',1,11,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetLog:stat','fund/budgetLog/stat','budgetLog/stat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-10-12 18:29:27'),(534,'新增错误代码','新增','POST','/errorCodeDefine/create',0,0,1,814,NULL,1,0,'ErrorCodeDefine','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:errorCodeDefine:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-08-20 18:56:22'),(535,'修改错误代码','修改','POST','/errorCodeDefine/edit',0,1,1,814,NULL,1,1,'ErrorCodeDefine','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:errorCodeDefine:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-08-20 18:56:29'),(536,'获取错误代码详情','详情','GET','/errorCodeDefine/get',0,3,1,814,NULL,1,4,'ErrorCodeDefine','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-02-13 21:11:27'),(541,'执行花费时间统计','花费时间','GET','/taskLog/costTimeStat',0,5,0,810,'time-range',1,4,'TaskLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskLog:costTimeStat','schedule/taskLog/costTimeStat','taskLog/costTimeStat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-08-21 10:38:30'),(542,'获取最后一次调度日志','最后一次调度日志','GET','/taskLog/getLastTaskLog',0,3,1,813,NULL,1,3,'TaskLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-02-13 21:08:10'),(543,'修改调度服务器状态','修改状态','POST','/taskTrigger/changeScheduleStatus',0,1,1,812,NULL,1,0,'TaskServer','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-02-13 21:02:24'),(544,'获取疾病标签树',NULL,'GET,POST','/treatRecord/getTagsTree',0,0,1,678,NULL,1,0,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-03-08 14:50:54'),(545,'获取用户日历详情','详情','GET','/userCalendar/get',0,3,1,798,NULL,1,0,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-02-13 20:01:27'),(546,'修改用户计划','修改','POST','/userPlan/edit',0,1,1,774,NULL,1,2,'UserPlan','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:userPlan:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-04-21 19:38:33','2020-08-12 16:03:56'),(547,'股票分析',NULL,'GET','/userShares/analyse',0,0,1,662,'',1,4,'UserShares','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-04-21 19:38:33','2019-12-30 14:35:59'),(548,'修改股票',NULL,'POST','/userShares/edit',0,0,1,662,'',1,0,'UserShares','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-04-21 19:38:33','2019-12-30 14:36:18'),(549,'获取股票详情',NULL,'GET','/userShares/get',0,0,1,662,'',1,1,'UserShares','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-04-21 19:38:33','2019-12-30 14:36:33'),(550,'获取股票当前价格',NULL,'GET','/userShares/getCurrentPrice',0,0,1,662,'',1,2,'UserShares','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-04-21 19:38:33','2019-12-30 14:36:51'),(552,'获取股票评分统计',NULL,'GET','/userShares/getScoreStat',0,0,1,662,'',1,4,'UserShares','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-04-21 19:38:33','2019-12-30 14:37:31'),(553,'获取用户股票树',NULL,'GET,POST','/userShares/getUserSharesTree',0,0,2,662,'',1,6,'','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-04-21 19:38:33','2019-12-30 14:37:48'),(555,'用户股票统计',NULL,'GET','/userShares/stat',0,0,1,662,'',1,7,'UserShares','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-04-21 19:38:33','2019-12-30 14:38:05'),(556,'股票告警统计',NULL,'GET','/userShares/warnDateStat',0,0,1,662,'',1,8,'UserShares','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-04-21 19:38:33','2019-12-30 14:38:22'),(557,'获取当前账户信息 列表',NULL,'GET','/account/getCurrentAccountInfo',0,0,1,652,'',1,8,'','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:13','2019-12-30 13:29:35'),(558,'获取账户的快照树列表',NULL,'GET,POST','/accountSnapshotInfo/getSnapshotInfoTree',0,0,1,652,'',1,7,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:13','2019-12-30 13:29:17'),(559,'获取身体不适类型树',NULL,'GET,POST','/bodyAbnormalRecord/getAbnormalCategoryTree',0,3,2,682,NULL,1,0,'BodyAbnormalRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-09-07 13:37:14','2020-03-08 14:43:02'),(560,'获取预算分析数据',NULL,'GET','/budget/analyse',0,0,1,656,NULL,1,10,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budget:analyse',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-09-07 13:37:14','2020-07-30 08:32:30'),(561,'根据标签统计预算',NULL,'GET','/budget/statByKeywords',0,0,1,656,'',1,11,'Budget','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:14','2019-12-30 13:49:07'),(562,'重新保存预算日志',NULL,'POST','/budgetLog/reSave',0,0,1,656,'',1,12,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:14','2019-12-30 13:49:26'),(563,'消费记录标签详情统计数据',NULL,'GET','/buyRecord/keywordsDetailStat',0,0,1,650,'',1,2,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:14','2019-12-30 10:30:19'),(566,'获取饮食的店铺标签树','店铺标签树','GET,POST','/diet/getShopTree',0,5,2,787,NULL,1,0,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-09-07 13:37:14','2020-03-08 14:51:41'),(567,'获取饮食的标签树','饮食的标签树','GET,POST','/diet/getTagsTree',0,3,2,787,NULL,1,1,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-09-07 13:37:15','2020-03-08 14:54:01'),(568,'价格分析','价格分析','GET','/diet/priceAnalyse',0,4,0,786,'analyse',1,6,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:diet:priceAnalyse','food/diet/priceAnalyse','diet/priceAnalyse',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-09-07 13:37:15','2020-10-12 21:44:39'),(571,'获取梦想的提醒信息',NULL,'GET','/dream/getRemind',0,0,1,668,'',1,0,'DreamRemind','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:15','2019-12-30 14:53:36'),(574,'收入分析统计','收入分析统计','GET','/income/stat',0,0,0,642,'chart',1,9,NULL,'id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:income:stat','fund/income/stat','income/stat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2019-09-07 13:37:15','2020-10-12 18:29:06'),(575,'获取人生经历类型树',NULL,'GET,POST','/lifeExperience/getTypeTree',0,0,2,692,NULL,1,0,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-09-07 13:37:15','2020-03-08 14:51:49'),(579,'获取报表配置模板树','获取模板树','GET,POST','/reportConfig/getReportConfigTree',0,3,1,769,NULL,1,0,'ReportConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-09-07 13:37:15','2020-03-08 14:52:04'),(580,'根据模板新增锻炼记录',NULL,'POST','/sportExercise/createByTemplate',0,0,1,672,'',1,0,'SportExercise','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:15','2019-12-30 14:59:44'),(581,'获取调度组树','调度组树','GET,POST','/taskTrigger/getTaskTriggerCategoryTree',0,3,1,812,NULL,1,2,'TaskServer','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-09-07 13:37:15','2020-02-13 21:06:37'),(584,'获取用药分类树',NULL,'GET,POST','/treatDrug/getTreatDrugCategoryTree',0,0,2,679,NULL,1,0,'TreatDrug','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-09-07 13:37:16','2020-03-08 14:52:11'),(585,'用药详情分析',NULL,'GET','/treatDrugDetail/stat',0,0,1,346,'',1,0,'TreatDrugDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatDrugDetail:stat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:16','2020-08-08 09:58:48'),(586,'获取最后一次手术',NULL,'GET','/treatOperation/getLastTreatOperation',0,0,1,680,'',1,0,'TreatOperation','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:16','2019-12-30 15:11:08'),(587,'获取手术分类树',NULL,'GET,POST','/treatOperation/getTreatOperationCategoryTree',0,0,1,680,'',1,2,'','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:16','2019-12-30 15:11:30'),(589,'疾病概况',NULL,'GET','/treatRecord/fullStat',0,3,0,674,'chart',1,2,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatRecord:fullStat','health/treat/treatRecord/fullStat','treatRecord/fullStat',_binary '',_binary '',_binary '\0',_binary '','','2019-09-07 13:37:17','2020-10-12 19:13:52'),(590,'获取检验报告详情',NULL,'GET','/treatTest/get',0,0,1,681,'',1,0,'TreatTest','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:17','2019-12-30 15:13:54'),(592,'获取最近一次检验报告',NULL,'GET','/treatTest/getLastTest',0,0,1,681,'',1,3,'TreatTest','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:17','2019-12-30 15:14:37'),(593,'获取检验报告树',NULL,'GET,POST','/treatTest/getTreatTestCategoryTree',0,0,1,681,'',1,4,'TreatTest','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:18','2019-12-30 15:14:55'),(595,'检验报告统计',NULL,'GET','/treatTest/stat',0,0,1,681,'',1,4,'TreatTest','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:21','2019-12-30 15:15:22'),(596,'获取股票监控配置',NULL,'GET','/userShares/getMonitorConfig',0,0,1,662,'',1,9,'UserShares','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:27','2019-12-30 14:38:40'),(597,'获取股票配置',NULL,'GET','/userShares/getScoreConfig',0,0,1,662,'',1,11,'UserShares','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-09-07 13:37:27','2019-12-30 14:39:00'),(598,'重新统计预算的时间线数据',NULL,'POST','/budget/reStatTimeline',0,0,1,656,NULL,1,12,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budget:reStatTimeline',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-10-13 11:27:19','2020-07-29 14:36:38'),(599,'预算进度统计',NULL,'GET','/budget/timelineStat',0,0,0,642,'chart',1,9,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budget:timelineStat','fund/budget/timelineStat','budget/timelineStat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2019-10-13 11:27:19','2020-10-12 18:29:12'),(600,'删除通用记录','删除','POST','/commonRecord/delete',0,0,1,791,NULL,1,0,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'common:commonRecord:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-10-13 11:27:19','2020-08-16 08:55:23'),(601,'处理器详情','详情','GET','/handler/getHandlerInfo',0,5,1,818,NULL,1,1,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:handler:getHandlerInfo',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-10-13 11:27:19','2020-08-22 16:42:26'),(602,'获取阅读花费时间','花费时间','GET','/readingRecord/getCostTimes',0,4,1,779,NULL,1,6,'ReadingRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecord:getCostTimes',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-10-13 11:27:19','2020-08-15 11:05:32'),(603,'获取调度参数定义','参数定义','GET','/taskTrigger/getParaDefine',0,3,1,812,NULL,1,3,'TaskServer','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-10-13 11:27:19','2020-02-13 21:03:33'),(604,'获取调度信息','调度信息','GET','/taskTrigger/getScheduleInfo',0,3,1,812,NULL,1,4,'TaskTrigger','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskTrigger:getScheduleInfo',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-10-13 11:27:19','2020-08-21 20:54:47'),(605,'刷新调度','刷新调度','POST','/taskTrigger/refreshSchedule',0,5,1,812,NULL,1,5,'TaskTrigger','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskTrigger:refreshSchedule',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-10-13 11:27:19','2020-08-21 20:43:59'),(606,'获取用户评分','获取用户评分','GET','/user/getScore',0,3,1,800,NULL,1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-10-13 11:27:19','2020-02-13 22:04:08'),(607,'发送用户消息','发送','POST','/userMessage/send',0,0,1,809,NULL,1,0,'UserMessage','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:userMessage:send',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-10-13 11:27:19','2020-08-20 15:50:52'),(632,'获取调度明细','明细','GET','/taskTrigger/getScheduleDetail',0,3,1,812,NULL,1,6,'TaskTrigger','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-12-28 08:17:10','2020-02-13 21:04:50'),(633,'删除购买来源',NULL,'POST','/buyType/delete',0,2,1,643,NULL,1,4,'BuyType','ids',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyType:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-12-29 13:31:23','2020-08-06 08:04:12'),(634,'错误页面','错误页面','GET','/error',0,5,1,785,NULL,1,0,'ErrorCodeDefine','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-12-29 13:31:23','2020-02-13 21:24:43'),(635,'操作日志树形统计','统计','GET','/operationLog/treeStat',0,4,0,806,'log',1,2,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:operationLog:treeStat','log/operationLog/treeStat','operationLog/treeStat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2019-12-29 13:31:23','2020-09-28 23:30:45'),(636,'获取功能点映射实体树','映射实体树','GET,POST','/systemFunction/getDomainClassNamesTree',0,5,1,805,NULL,1,2,'SystemFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-12-29 13:31:23','2020-02-13 20:41:54'),(637,'获取服务器树','服务器树','GET,POST','/taskServer/getTaskServerTree',0,5,1,811,NULL,1,0,'TaskServer','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-12-29 13:31:23','2020-03-08 14:53:12'),(639,'删除统计缓存','删除缓存','POST','/userNotify/deleteStatCache',0,2,1,764,NULL,1,2,'UserNotify','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:notify:userNotify:deleteStatCache',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-12-29 13:31:23','2020-08-12 13:44:10'),(640,'消费管理','消费管理','GET','consume2',0,5,3,0,'consume',1,1,'BuyRecord',NULL,0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'consume','consume',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 09:32:49','2020-10-16 10:54:52'),(642,'资金管理',NULL,'GET','fund',0,5,3,0,'fund',1,2,'Account',NULL,0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'fund','fund',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 09:34:02','2020-10-12 18:27:11'),(643,'购买来源管理',NULL,'GET','/buyType/getData',0,5,0,640,'consume',1,1,'BuyType','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyType:query','consume/buyType/index','buyType',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 09:35:16','2020-10-12 18:20:34'),(644,'商品类型管理',NULL,'GET','/goodsType/getData',0,5,0,640,'consume',1,2,'GoodsType',NULL,1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:goodType:query','consume/goodsType/index','goodsType',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 09:55:04','2020-10-12 18:21:38'),(645,'价格区间管理',NULL,'GET','/priceRegion/getData',0,5,0,640,'consume',1,3,'PriceRegion','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:priceRegion:query','consume/priceRegion/index','priceRegion',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 10:23:57','2020-10-12 18:21:45'),(646,'新增价格区间',NULL,'POST','/priceRegion/create',0,0,1,645,NULL,1,2,'PriceRegion','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:priceRegion:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-12-30 10:25:33','2020-07-23 15:13:56'),(647,'删除价格区间',NULL,'POST','/priceRegion/delete',0,2,1,645,NULL,1,5,'PriceRegion','ids',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:priceRegion:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-12-30 10:26:10','2020-07-23 15:14:04'),(648,'消费记录管理','消费记录管理','GET','/buyRecord/getData',0,5,0,640,'consume',1,4,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:query','consume/buyRecord/index','buyRecord',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 10:27:14','2020-10-12 18:21:52'),(650,'消费标签统计',NULL,'GET','/buyRecord/keywordsStat',0,4,0,640,'chart',1,9,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:keywordsStat','consume/buyRecord/keywordsStat','buyRecord/keywordsStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 10:29:30','2020-10-12 18:23:22'),(651,'商品寿命分析',NULL,'GET','/buyRecord/useTimeStat',0,0,0,640,'analyse',1,6,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:useTimeStat','consume/buyRecord/useTimeStat','buyRecord/useTimeStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 10:31:11','2020-10-12 18:22:55'),(652,'账户管理',NULL,'GET','/account/getData',0,0,0,642,'fund',1,1,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,1,'fund:account:query','fund/account/index','account',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 13:26:57','2020-10-12 18:27:24'),(653,'账户流水','账户流水','GET','/accountFlow/getData',0,5,0,642,'fund',1,2,'AccountFlow','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:accountFlow:query','fund/accountFlow/index','accountFlow',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 13:30:51','2020-10-12 18:27:33'),(654,'收入管理',NULL,'GET','/income/getData',0,5,0,642,'fund',1,3,'Income','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,1,'fund:income:query','fund/income/index','income',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 13:32:53','2020-10-12 18:27:42'),(655,'收入统计',NULL,'GET','/income/dateStat',0,0,0,642,'chart',1,4,'Income','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:income:dateStat','fund/income/dateStat','income/dateStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 13:34:57','2020-10-12 18:27:49'),(656,'预算管理',NULL,'GET','/budget/getData',0,0,0,642,'fund',1,5,'Budget','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budget:query','fund/budget/index','budget',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 13:42:49','2020-10-12 18:28:25'),(657,'首页功能',NULL,'GET','/main/main.html',0,5,3,0,'dashboard',1,96,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'dashboard','main',_binary '\0',_binary '',_binary '\0',_binary '','','2019-12-30 13:51:37','2020-10-01 08:10:31'),(658,'处理器支持的命令列表','命令列表','GET','/handler/getSupportCmd',0,5,1,818,NULL,1,3,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:handler:getSupportCmd',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-12-30 13:53:10','2020-08-22 16:42:45'),(659,'删除功能点','删除','POST','/systemFunction/delete',0,2,1,805,NULL,1,4,'SystemFunction','ids',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:systemFunction:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2019-12-30 13:53:10','2020-08-05 13:57:18'),(661,'评分统计','统计','GET','/userScore/stat',0,4,0,797,'chart',1,8,'UserScore','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userScore:stat','data/userScore/stat','userScore/stat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 13:53:11','2020-10-12 21:57:45'),(662,'股票管理',NULL,'GET','/userShares/getData',0,0,0,642,'fund',1,7,'UserShares','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '','','2019-12-30 14:35:16','2020-10-12 18:28:39'),(663,'音乐练习',NULL,'GET','music',0,5,3,0,'music',1,3,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'music','music',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 14:39:45','2020-10-12 19:03:56'),(664,'乐器管理',NULL,'GET','/musicInstrument/getData',0,0,0,663,'music',1,1,'MusicInstrument','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicInstrument:query','music/musicInstrument/index','musicInstrument',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 14:41:20','2020-10-12 19:04:09'),(665,'音乐练习管理',NULL,'GET','/musicPractice/getData',0,3,0,663,'music',1,2,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPractice:query','music/musicPractice/index','musicPractice',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 14:43:04','2020-10-12 19:04:28'),(667,'梦想管理',NULL,'GET','dream',0,0,3,0,'dream',1,4,'Dream','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'dream','dream2',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 14:52:03','2020-12-10 10:18:09'),(668,'梦想管理',NULL,'GET','/dream/getData',0,3,0,667,'dream',1,1,'Dream','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'dream:query','dream/index','dream',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 14:52:32','2020-12-10 10:18:19'),(669,'锻炼管理',NULL,'GET','sportExercise',0,0,3,0,'sport',1,5,'SportType','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'sport','sport',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 14:57:10','2020-10-12 19:08:28'),(670,'运动类型管理',NULL,'GET','/sportType/getData',0,3,0,669,'sport',1,1,'SportType','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportType:query','sport/sportType/index','sportType',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 14:58:02','2020-10-12 19:09:01'),(671,'运动里程碑管理',NULL,'GET','/sportMilestone/getData',0,0,0,669,'sport',1,2,'SportMilestone','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportMilestone:query','sport/sportMilestone/index','sportMilestone',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 14:58:29','2020-10-12 19:09:45'),(672,'锻炼管理',NULL,'GET','/sportExercise/getData',0,0,0,669,'sport',1,3,'SportExercise','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportExercise:query','sport/sportExercise/index','sportExercise',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 14:59:05','2020-10-12 19:09:54'),(673,'健康管理',NULL,'GET','health',0,5,3,0,'health',1,6,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'health','health',_binary '',_binary '',_binary '\0',_binary '','','2019-12-30 15:04:39','2020-10-12 19:11:19'),(674,'看病管理',NULL,'GET','treatRecord',0,5,3,673,'treat',1,1,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'health/treat/index','treat',_binary '',_binary '',_binary '\0',_binary '','','2019-12-30 15:05:32','2020-10-12 19:13:08'),(675,'身体基本情况',NULL,'GET','bodyBasicInfo',0,5,3,673,'body',1,2,'BodyBasicInfo','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'health/body/index','body',_binary '',_binary '',_binary '\0',_binary '',NULL,'2019-12-30 15:06:09','2020-10-12 19:19:58'),(676,'身体不适',NULL,'GET','bodyAbnormalRecord',0,0,3,673,'abnormal',0,3,'BodyAbnormalRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'health/body/index','body2',_binary '',_binary '',_binary '\0',_binary '','','2019-12-30 15:06:43','2020-10-12 19:29:32'),(677,'睡眠管理',NULL,'GET','sleep',0,0,3,673,'sleep',1,4,'Sleep','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'health/body/index','body3',_binary '',_binary '',_binary '\0',_binary '','','2019-12-30 15:07:07','2020-10-12 19:31:13'),(678,'看病记录管理',NULL,'GET','/treatRecord/getData',0,3,0,674,'treat',1,1,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatRecord:query','health/treat/treatRecord/index','treatRecord',_binary '',_binary '',_binary '\0',_binary '','','2019-12-30 15:07:35','2020-10-12 19:13:41'),(679,'药品管理',NULL,'GET','/treatDrug/getData',0,0,0,674,'drug',1,6,'TreatDrug','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatDrug:query','health/treat/treatDrug/index','treatDrug',_binary '',_binary '',_binary '\0',_binary '','','2019-12-30 15:09:11','2020-10-12 19:17:00'),(680,'手术管理',NULL,'GET','/treatOperation/getData',0,3,0,674,'operation',1,7,'TreatOperation','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatOperation:query','health/treat/treatOperation/index','treatOperation',_binary '',_binary '',_binary '\0',_binary '','','2019-12-30 15:10:39','2020-10-12 19:17:49'),(681,'检验报告',NULL,'GET','/treatTest/getData',0,0,0,674,'treat',1,9,'TreatTest','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatTest:query','health/treat/treatTest/index','treatTest',_binary '',_binary '',_binary '\0',_binary '','','2019-12-30 15:13:19','2020-10-12 19:18:35'),(682,'身体不适管理',NULL,'GET','/bodyAbnormalRecord/getData',0,0,0,676,'abnormal',1,1,'BodyAbnormalRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyAbnormalRecord:query','health/body/bodyAbnormalRecord/index','bodyAbnormalRecord',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-01-08 13:21:31','2020-10-12 19:29:41'),(683,'身体数据',NULL,'GET','/bodyBasicInfo/getData',0,0,0,675,'body',1,1,'BodyBasicInfo','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyBasicInfo:query','health/body/bodyBasicInfo/index','bodyBasicInfo',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-01-08 13:28:56','2020-10-12 19:20:59'),(684,'睡眠管理',NULL,'GET','/sleep/getData',0,5,0,677,'sleep',1,1,'Sleep','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:sleep:query','health/body/sleep/index','sleep',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-01-08 14:36:10','2020-10-12 19:31:24'),(685,'人生经历管理',NULL,'GET','life',0,5,3,0,'life',1,7,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,1,0,1,0,5,0,_binary '\0',0,0,1,0,NULL,'life','life',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-01-08 14:46:39','2020-10-19 15:06:33'),(686,'城市地理维护','城市地理','GET','/cityLocation/getData',0,3,0,685,'location',1,1,'CityLocation','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'life:cityLocation:query','life/cityLocation/index','cityLocation',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-01-08 14:48:04','2020-10-19 15:05:46'),(691,'消费类型管理',NULL,'GET','/consumeType/getData',0,0,0,685,'consume',1,2,'ConsumeType','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:consumeType:query','life/consumeType/index','consumeType',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-01-08 14:52:59','2020-10-12 21:09:30'),(692,'人生经历管理',NULL,'GET','/lifeExperience/getData',0,0,0,685,'life',1,3,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperience:query','life/lifeExperience/index','lifeExperience',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-01-08 14:54:30','2020-10-12 21:09:37'),(693,'人生经历汇总',NULL,'GET','/lifeExperienceSum/getData',0,0,0,685,'chart',1,8,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceSum:query','life/lifeExperienceSum/index','life/lifeExperienceSum',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-01-08 14:56:42','2020-10-12 21:11:37'),(694,'获取表记录数','表记录数','GET','/databaseClean/getCounts',0,3,1,816,NULL,1,3,'DatabaseClean','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:26','2020-02-13 21:15:34'),(695,'新增角色','新增','POST','/role/create',0,0,1,804,NULL,1,0,'Role','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:role:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:26','2020-08-05 20:58:25'),(696,'修改角色','修改','POST','/role/edit',0,1,1,804,NULL,1,1,'Role','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:role:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:26','2020-08-05 20:58:35'),(697,'获取角色详情','详情','GET','/role/get',0,3,1,804,NULL,1,2,'Role','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:26','2020-02-13 20:39:20'),(698,'获取角色列表','列表','GET','/role/getData2',0,3,1,804,NULL,1,4,'Role','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:26','2020-02-13 20:39:25'),(699,'获取角色功能点树','获取角色功能点树','GET,POST','/role/getRoleFunctionTree',0,3,1,804,NULL,1,5,'RoleFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:26','2020-02-13 20:39:30'),(700,'刷新角色的缓存','刷新缓存','POST','/role/refreshSystemConfig',0,5,1,804,NULL,1,6,'RoleFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:role:refreshSystemConfig',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:26','2020-08-20 10:25:23'),(701,'保存角色的功能点信息','保存功能点信息','POST','/role/saveRoleFunction',0,1,1,804,NULL,1,6,'RoleFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:26','2020-02-13 20:39:39'),(702,'修改用户角色','修改用户角色','POST','/role/saveUserRole',0,1,1,804,NULL,1,7,'UserRole','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:27','2020-02-13 20:39:44'),(703,'重做调度日志','重做','GET','/taskLog/redo',0,5,1,813,NULL,1,4,'TaskLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskLog:redo',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:27','2020-08-21 09:49:27'),(704,'线程详情','详情','GET','/thread/getThreadInfo',0,5,1,817,NULL,1,2,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:thread:getThreadInfo',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:27','2020-08-22 16:19:25'),(705,'获取用户角色列表','用户角色列表','GET,POST','/user/getUserRoleTree',0,3,1,800,NULL,1,1,'UserRole','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:27','2020-02-13 20:14:19'),(706,'获取评分详情','详情','GET','/userScore/getScoreDetail',0,0,1,802,NULL,1,2,'UserScoreDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-10 11:24:27','2020-02-13 20:33:30'),(708,'新增图表配置模板','新增','POST','/chartConfig/create',0,0,1,821,NULL,1,0,'ChartConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:chart:chartConfig:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:44','2020-08-14 14:13:49'),(709,'修改图表配置模板','修改','POST','/chartConfig/edit',0,1,1,821,NULL,1,1,'ChartConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:chart:chartConfig:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:44','2020-08-14 14:14:00'),(710,'获取图表配置模板详情','详情','GET','/chartConfig/get',0,3,1,821,NULL,1,2,'ChartConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:44','2020-10-16 10:45:48'),(711,'图表配置模板树(用户)','模板树(用户)','GET,POST','/chartConfig/getChartConfigForUserTree',0,3,1,821,NULL,1,0,'ChartConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:44','2020-03-08 14:53:20'),(712,'图表配置模板树','模板树','GET,POST','/chartConfig/getChartConfigTree',0,3,1,821,NULL,1,4,'ChartConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:44','2020-02-13 21:28:54'),(714,'城市列表','城市列表','GET,POST','/city/getCityList',0,3,1,785,NULL,1,0,'City','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:44','2020-08-10 21:33:09'),(715,'获取枚举值树','删除','GET,POST','/common/getEnumTree',0,0,1,785,NULL,1,1,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:44','2020-03-07 09:48:03'),(716,'区县列表','区县列表','GET,POST','/district/getDistrictList',0,5,1,785,NULL,1,1,'District','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-08-10 21:33:22'),(717,'刷新错误代码缓存','刷新缓存','POST','/errorCodeDefine/reloadCacheConfig',0,5,1,814,NULL,1,5,'ErrorCodeDefine','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:errorCodeDefine:reloadCacheConfig',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-08-20 19:26:43'),(718,'修改收入','修改','POST','/income/edit',0,1,1,654,NULL,1,1,'Income','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:income:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-07-27 10:27:38'),(719,'收入详情','详情','GET','/income/get',0,3,1,654,NULL,1,2,'Income','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-02-13 21:32:01'),(720,'获取二次认证详情','二次认证详情','GET','/main/getSecAuthInfo',0,5,1,657,NULL,1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-02-13 21:32:40'),(721,'二次认证','二次认证','POST','/main/secAuth',0,5,1,657,NULL,1,1,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-02-13 21:32:59'),(722,'发送二次认证码','发送二次认证码','POST','/main/sendSecAuthCode',0,5,1,657,NULL,1,2,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-02-13 21:33:24'),(723,'以模板新增音乐练习','以模板新增','POST','/musicPractice/createByTemplate',0,0,1,665,NULL,1,5,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPractice:createByTemplate',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-08-06 09:59:20'),(724,'获取全部省列表','省列表','GET,POST','/province/getAll',0,3,1,785,NULL,1,4,'Province','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-08-10 21:32:55'),(725,'修改模板配置值','修改配置值','POST','/statValueConfig/edit',0,1,1,944,NULL,1,5,'StatValueConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:statValueConfig:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-10-16 10:30:27'),(727,'系统信息统计','统计','GET','/system/stat',0,5,1,819,NULL,1,1,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-02-13 21:23:25'),(728,'修改用户微信信息','修改微信信息','POST','/user/editUserWxpayInfo',0,1,1,800,NULL,1,2,'UserWxpayInfo','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-02-13 20:14:48'),(729,'获取系统监控配置','获取系统监控配置','GET,POST','/user/getSystemMonitorTree',0,3,1,800,NULL,1,3,'SystemMonitorUser','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-02-13 20:15:22'),(730,'获取用户微信信息','获取微信信息','GET','/user/getUserWxpayInfo',0,3,1,800,NULL,1,4,'UserWxpayInfo','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-02-13 20:15:47'),(731,'强制离线','强制离线','POST','/user/offline',0,0,1,800,NULL,1,5,'User','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:user:offline',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-08-20 09:30:12'),(732,'修改用户系统监控配置','修改系统监控配置','POST','/user/saveSystemMonitor',0,1,1,800,NULL,1,5,'SystemMonitorUser','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-02-13 20:19:02'),(733,'获取用户日历当前日期的总数','当前日期的总数','GET','/userCalendar/dailyCountStat',0,0,1,798,NULL,1,1,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-02-13 20:02:08'),(734,'修改用户日历','修改','POST','/userCalendar/edit',0,1,1,798,NULL,1,1,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-02-13 20:02:27'),(735,'新增用户图表','新增','POST','/userChart/create',0,0,1,822,NULL,1,0,'UserChart','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:chart:userChart:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:45','2020-08-14 15:14:30'),(736,'删除用户图表','删除','POST','/userChart/delete',0,2,1,822,NULL,1,1,'UserChart','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:chart:userChart:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:46','2020-08-14 15:14:38'),(737,'修改用户图表','修改','POST','/userChart/edit',0,1,1,822,NULL,1,2,'UserChart','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:chart:userChart:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:46','2020-08-14 15:14:45'),(738,'获取用户图表详情','详情','GET','/userChart/get',0,3,1,822,NULL,1,3,'UserChart','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:46','2020-02-13 21:37:51'),(739,'获取用户图表参数','获取参数','GET','/userChart/getChartPara',0,3,1,960,NULL,1,4,'UserChart','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:46','2020-08-14 15:36:00'),(741,'获取用户图表树','图表树','GET,POST','/userChart/getUserChartTree',0,3,1,960,NULL,1,5,'UserChart','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:46','2020-08-14 15:36:13'),(742,'加班记录概要统计','概要统计','GET','/workOvertime/stat',0,4,1,783,NULL,1,0,'WorkOvertime','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-01-19 15:01:46','2020-02-13 15:58:59'),(743,'新增图书分类','新增','POST','/bookCategory/create',0,0,1,823,NULL,1,0,'BookCategory','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:bookCategory:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-08-15 09:03:51'),(744,'消费统计+看病统计','消费及看病统计','GET','/buyRecord/statWithTreat',0,4,1,648,NULL,1,0,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-02-13 21:42:49'),(745,'清空表数据','清空','GET','/databaseClean/truncate',0,5,1,816,NULL,1,4,'DatabaseClean','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-02-13 21:16:01'),(746,'获取数据字典项数','字典项数','GET,POST','/dictItem/getDictItemTree',0,3,1,1000,NULL,1,0,'DictItem','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-08-24 22:22:00'),(747,'获取饮食的食物标签树','食物标签树','GET,POST','/diet/getFoodsTree',0,0,1,787,NULL,1,5,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-02-13 16:17:32'),(748,'格式化用户信息','格式化','POST','/user/deleteUserData',0,1,1,800,NULL,1,7,'User','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:user:deleteUserData',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-08-20 09:30:33'),(749,'初始化用户数据','初始化','POST','/user/initUserData',0,1,1,800,NULL,1,8,'User','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:user:initUserData',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-08-20 09:30:46'),(750,'获取用户图表树(简化)','图表树(简化)','GET,POST','/userChart/getUserChartTreeSm',0,3,1,771,NULL,1,0,'UserChart','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-03-08 14:53:53'),(751,'获取当前用户的消息列表','当前用户的消息列表','GET','/userMessage/getMyList',0,0,1,809,NULL,1,0,'UserMessage','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-02-13 20:57:09'),(752,'获取用户提醒树(简化)','获取用户提醒树(简化)','GET,POST','/userNotify/getUserNotifyTreeNm',0,5,1,764,NULL,1,9,'UserNotify','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-02-13 10:52:58'),(753,'新增用户计划','新增','POST','/userPlan/create',0,0,1,774,NULL,1,0,'UserPlan','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:userPlan:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-08-12 16:03:48'),(754,'查询计划值','配置值详情','GET','/userPlanConfigValue/get',0,3,1,950,NULL,1,2,'UserPlanConfigValue','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-08-13 08:04:29'),(755,'新增报表配置模板','新增','POST','/userReportConfig/create',0,0,1,769,NULL,1,1,'UserReportConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-02-13 13:48:53'),(756,'获取用户报表提醒配置','获取提醒配置','GET','/userReportConfig/getRemind',0,3,1,771,NULL,1,2,'UserReportRemind','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-02-13 13:51:08'),(757,'获取用户微信支付详情','用户微信支付详情','GET','/wechat/getUserWxpayInfo',0,3,1,994,NULL,1,0,'UserWxpayInfo','id',0,_binary '',_binary '\0',_binary '',0,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-08-27 07:39:35'),(758,'获取微信APP信息','微信APP信息','GET','/wechat/getWxAppInfo',0,3,1,826,NULL,1,1,'UserWxpayInfo','id',0,_binary '',_binary '\0',_binary '',0,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 09:49:47','2020-06-12 11:08:32'),(759,'人生经历消费','人生经历消费','GET','/lifeExperienceConsume/getData',0,5,0,692,NULL,1,2,'LifeExperienceSum','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceConsume:query',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 10:24:29','2020-08-10 21:51:42'),(760,'人生经历详情','人生经历详情','GET','/lifeExperienceDetail/getData',0,3,0,692,NULL,1,2,'LifeExperienceDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceDetail:query',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 10:28:09','2020-08-10 20:46:44'),(761,'报表管理','报表管理','GET','report',0,5,3,0,'report',1,8,'PlanConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,1,0,5,0,_binary '\0',0,0,1,0,NULL,'report','report',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 10:37:10','2020-10-16 10:26:19'),(762,'提醒管理','提醒管理','GET','notify',0,5,3,761,'notify',1,1,'NotifyConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,1,0,5,0,_binary '\0',0,0,1,0,NULL,'report/notify/index','notify',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 10:43:00','2020-10-16 10:28:48'),(763,'提醒配置模板','提醒配置模板','GET','/notifyConfig/getData',0,5,0,762,'notify',1,1,'NotifyConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:notify:notifyConfig:query','report/notify/notifyConfig/index','notifyConfig',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 10:44:43','2020-10-12 21:16:26'),(764,'用户提醒管理','用户提醒管理','GET','/userNotify/getData',0,5,0,762,'notify',1,2,'UserNotify','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:notify:userNotify:query','report/notify/userNotify/index','userNotify',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 10:46:10','2020-10-12 21:16:40'),(768,'报表管理2','报表管理','GET','report2',0,5,3,761,'report',1,4,'ReportConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 10:56:38','2020-10-12 21:14:29'),(769,'报表配置模板首页','报表配置模板','GET','/report/reportConfigList.html',0,5,0,768,NULL,1,0,'ReportConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 10:59:49','2020-02-13 13:53:37'),(770,'计划执行报告','计划执行报告','GET','/planReport/getData',0,5,0,772,'chart',1,3,'PlanReport','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planReport:query','report/plan/planReport/index','planReport',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 13:46:07','2020-08-13 09:24:36'),(771,'用户报表配置首页','用户报表配置','GET','/report/userReportConfigList.html',0,5,0,768,NULL,1,1,'UserReportConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 13:50:16',NULL),(772,'计划管理','计划管理','GET','plan',0,5,3,761,'plan',1,2,'PlanConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,1,0,5,0,_binary '\0',0,0,1,0,NULL,'report/plan/index','plan',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 13:52:26','2020-10-16 10:28:59'),(773,'报表统计首页','报表统计','GET','/report/reportStatList.html',0,5,0,768,NULL,1,2,'ReportConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 13:56:29',NULL),(774,'用户计划管理','用户计划','GET','/userPlan/getData',0,5,0,772,'plan',1,2,'UserPlan','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:userPlan:query','report/plan/userPlan/index','userPlan',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 15:25:26','2020-10-12 21:18:54'),(775,'计划配置模板','计划配置模板','GET','/planConfig/getData',0,5,0,772,'plan',1,1,'PlanConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planConfig:query','report/plan/planConfig/index','planConfig',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 15:31:52','2020-10-12 21:18:44'),(776,'日记管理','日记管理','GET','dairy',0,5,3,0,NULL,1,95,'Diary','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 15:39:20','2020-08-11 16:52:14'),(777,'日记管理首页','日记管理','GET','/diary/diaryList.html',0,5,0,776,NULL,1,0,'Diary','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 15:39:58',NULL),(778,'阅读管理','阅读管理','GET','read',0,5,3,0,'education',1,9,'ReadingRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'read','read',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 15:43:44','2020-08-15 09:02:35'),(779,'阅读管理','阅读管理','GET','/readingRecord/getData',0,3,0,778,'education',1,2,'ReadingRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecord:query','read/readingRecord/index','readingRecord',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 15:44:30','2020-08-15 09:20:54'),(780,'阅读详情','阅读详情','GET','/readingRecordDetail/getData',0,5,0,779,'education',1,1,'ReadingRecordDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecordDetail:query','read/readingRecordDetail/index','readingRecordDetail',_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 15:48:00','2020-08-15 11:18:51'),(781,'工作管理','工作管理','GET','work',0,5,3,0,'work',1,10,'WorkOvertime','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'work','work',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 15:54:38','2020-10-12 21:24:37'),(782,'公司管理','公司管理','GET','/company/getData',0,5,0,781,'company',1,1,'Company','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:company:query','work/company/index','company',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 15:56:22','2020-10-12 21:24:49'),(783,'加班记录管理','加班记录管理','GET','/workOvertime/getData',0,5,0,781,'workout',1,2,'WorkOvertime','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:workOvertime:query','work/workOvertime/index','workOvertime',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 15:58:21','2020-10-12 21:24:58'),(784,'出差管理','出差管理','GET','/businessTrip/getData',0,5,0,781,'businessTrip',1,4,'BusinessTrip','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:businessTrip:query','work/businessTrip/index','businessTrip',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 16:01:25','2020-10-12 21:25:13'),(785,'通用模块','通用模块','GET','cc',0,5,3,0,NULL,1,99,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 16:02:50',NULL),(786,'食物管理','饮食管理','GET','food',0,5,3,0,'food',1,11,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'food','food',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 16:04:40','2020-10-12 21:26:16'),(787,'饮食管理','饮食管理','GET','/diet/getData',0,5,0,786,'food',1,2,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:diet:query','food/diet/index','diet',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 16:05:21','2020-10-12 21:27:18'),(788,'饮食分类','饮食分类','GET','/dietCategory/getData',0,5,0,786,'food',1,1,'DietCategory','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:dietCategory:query','food/dietCategory/index','dietCategory',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 16:14:01','2020-10-12 21:26:28'),(789,'通用记录管理','通用记录管理','GET','common',0,5,3,0,'common',1,12,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'common','common',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 16:19:18','2020-10-12 21:46:55'),(790,'通用记录类型管理','通用记录类型管理','GET','/commonRecordType/getData',0,5,0,789,'common',1,1,'CommonRecordType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'common:commonRecordType:query','common/commonRecordType','commonRecordType',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 16:20:04','2020-10-19 21:50:31'),(791,'通用记录管理','通用记录管理','GET','/commonRecord/getData',0,5,0,789,'common',1,2,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'common:commonRecord:query','common/commonRecord/index','commonRecord',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 16:20:48','2020-10-12 21:47:14'),(792,'行为分析','数据分析','GET','behavior',0,5,3,0,'behavior',1,13,'DataInputAnalyse','id',0,_binary '',_binary '\0',_binary '',1,1,0,1,0,5,0,_binary '\0',0,0,1,0,NULL,'behavior','behavior',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 16:26:22','2020-10-16 10:30:53'),(793,'数据录入分析','数据录入分析管理','GET','/dataInputAnalyse/getData',0,5,0,792,'behavior',1,1,'DataInputAnalyse','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:dataInputAnalyse:query','behavior/dataInputAnalyse/index','dataInputAnalyse',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 16:26:56','2020-10-16 10:36:14'),(794,'用户行为模板','用户行为模板','GET','/userBehaviorConfig/getData',0,5,0,792,'behavior',1,2,'UserBehaviorConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehaviorConfig:query','behavior/userBehaviorConfig/index','userBehaviorConfig',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 16:34:58','2020-10-12 21:52:56'),(795,'用户行为分析(小时)','用户行为分析(小时)','GET','/userBehavior/hourStat',0,5,0,792,'analyse',1,5,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehavior:hourStat','behavior/userBehavior/hourStat','userBehavior/hourStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 19:43:26','2020-10-12 21:53:43'),(796,'用户行为管理','用户行为管理','GET','/userBehavior/getData',0,5,0,792,'behavior',1,3,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehavior:query','behavior/userBehavior/index','userBehavior',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 19:45:00','2020-10-12 21:53:04'),(797,'用户数据','用户数据','GET','UserData',0,5,3,0,'people',1,14,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,1,0,1,0,5,0,_binary '\0',0,0,1,0,NULL,'data','data',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 19:59:59','2020-10-16 10:31:19'),(798,'用户日历管理','用户日历管理','GET','/userCalendar/getData',0,5,0,797,'date',1,2,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userCalendar:query','data/userCalendar/index','userCalendar',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:00:58','2020-10-12 21:56:53'),(799,'权限管理','权限管理','GET','auth',0,5,3,0,'auth',1,15,'User','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'auth','auth',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:04:18','2020-10-12 22:00:59'),(800,'用户管理','用户列表','GET','/user/getData',0,5,0,799,'people',1,1,'User','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:user:query','auth/user/index','user',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:05:14','2020-08-19 14:21:17'),(801,'用户积分记录','用户积分记录','GET','/userRewardPointRecord/getData',0,5,0,797,'points',1,4,'UserRewardPointRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userRewardPointRecord:query','data/userRewardPointRecord/index','userRewardPointRecord',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:26:58','2020-10-12 21:57:11'),(802,'用户评分','用户评分','GET','/userScore/getData',0,5,0,797,'score',1,7,'UserScore','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userScore:query','data/userScore/index','userScore',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:32:12','2020-10-12 21:57:39'),(803,'评分与积分比对','评分与积分比对','GET','/userScore/scorePointsCompare',0,5,0,797,'yoy',1,9,'UserScore','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userScore:scorePointsCompare','data/userScore/scorePointsCompare','userScore/scorePointsCompare',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:34:01','2020-10-12 21:57:52'),(804,'角色管理','角色','GET','/role/getData',0,5,0,799,'role',1,2,'Role','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:role:query','auth/role/index','role',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:35:39','2020-10-12 22:01:17'),(805,'功能管理','功能点管理','GET','/auth/systemFunctionList.html',0,5,0,799,'function',1,3,'SystemFunction','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'auth/systemFunction/index','systemFunction',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:40:35','2020-10-12 22:01:28'),(806,'日志管理','日志管理','GET','log',0,5,3,0,'log',1,16,'SystemLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'log','log',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:44:47','2020-08-20 10:29:24'),(807,'操作日志管理','操作日志管理','GET','/operationLog/getData',0,5,0,806,'log',1,1,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:operationLog:query','log/operationLog/index','operationLog',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:49:15','2020-08-20 10:30:41'),(808,'系统日志管理','系统日志','GET','/systemLog/getData',0,5,0,806,'log',1,3,'SystemLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:systemLog:query','log/systemLog/index','systemLog',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:53:02','2020-08-20 14:55:14'),(809,'用户消息管理','用户消息管理','GET','/userMessage/getData',0,0,0,806,'message',1,4,'UserMessage','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:userMessage:query','log/userMessage/index','userMessage',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:55:18','2020-08-20 15:50:26'),(810,'调度管理','调度管理','GET','schedule',0,5,3,0,'time-range',1,17,'TaskServer','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'schedule','schedule',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:58:35','2020-08-20 20:47:28'),(811,'调度服务器','服务器管理','GET','/taskServer/getData',0,5,0,810,'time-range',1,1,'TaskServer','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskServer:query','schedule/taskServer/index','taskServer',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 20:59:26','2020-08-21 09:49:02'),(812,'调度管理','调度管理','GET','/taskTrigger/getData',0,5,0,810,'time-range',1,2,'TaskTrigger','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskTrigger:query','schedule/taskTrigger/index','taskTrigger',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 21:01:49','2020-08-20 21:11:12'),(813,'调度日志','调度日志','GET','/taskLog/getData',0,5,0,810,'time-range',1,3,'TaskLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskLog:query','schedule/taskLog','taskLog',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 21:07:17','2020-08-21 09:50:28'),(814,'错误代码管理','错误代码管理','GET','/errorCodeDefine/getData',0,5,0,806,'bug',1,5,'ErrorCodeDefine','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:errorCodeDefine:query','log/errorCodeDefine/index','errorCodeDefine',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 21:10:12','2020-08-20 16:50:06'),(815,'系统管理','系统管理','GET','system',0,5,3,0,'server',1,18,'DatabaseClean','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'system','system',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 21:12:46','2020-08-22 06:36:30'),(816,'数据库清理','数据库清理','GET','/databaseClean/getData',0,5,0,815,'database',1,2,'DatabaseClean','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:databaseClean:query','system/databaseClean/index','databaseClean',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 21:13:34','2020-10-12 22:09:23'),(817,'线程管理','线程管理','GET','/thread/getData',0,5,0,815,'logininfor',1,3,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:thread:query','system/thread/index','thread',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 21:17:12','2020-08-22 16:41:25'),(818,'处理器管理','处理器管理','GET','/handler/getData',0,5,0,815,'documentation',1,4,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:handler:query','system/handler/index','handler',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 21:19:01','2020-08-22 16:42:16'),(819,'系统监控','系统监控','GET','/system/getSystemDetail',0,5,0,815,'monitor',1,5,'CommonRecord','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:systemMonitor:query','system/systemMonitor/index','systemMonitor',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 21:21:22','2020-08-22 17:59:50'),(820,'图表管理','图表管理','GET','chart',0,5,3,761,'chart',1,3,'ChartConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,1,0,5,0,_binary '\0',0,0,1,0,NULL,'report/plan/index','chart',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 21:26:04','2020-10-16 10:29:11'),(821,'图表配置模板','图表配置模板','GET','/chartConfig/getData',0,5,0,820,'chart',1,1,'ChartConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:chart:chartConfig:query','report/chart/chartConfig/index','chartConfig',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 21:26:50','2020-08-14 14:13:35'),(822,'用户图表管理','用户图表管理','GET','/userChart/getData',0,5,0,820,'chart',1,2,'UserChart','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:chart:userChart:query','report/chart/userChart/index','userChart',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 21:36:20','2020-08-14 15:14:19'),(823,'图书分类管理','图书分类管理','GET','/bookCategory/getData',0,3,0,778,'education',1,1,'BookCategory','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:bookCategory:query','read/bookCategory/index','bookCategory',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-02-13 21:40:20','2020-08-15 09:03:43'),(826,'微信管理','微信管理','GET','wechat',0,5,3,0,'wechat',1,97,'UserWxpayInfo','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-13 21:47:30','2020-10-12 22:18:42'),(827,'获取通用记录类型树','类型树','GET,POST','/commonRecordType/getCommonRecordTypeTree',0,3,2,790,NULL,1,0,'CommonRecordType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-02-27 13:55:37','2020-10-19 21:50:47'),(828,'获取微信授权','获取微信授权','GET','/wechat/getWxJsapiAuth',0,0,1,826,NULL,1,0,'UserWxpayInfo','id',0,_binary '',_binary '\0',_binary '',0,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-03-01 11:25:07','2020-06-12 11:08:59'),(830,'删除用户消息','删除','POST','/userMessage/delete',0,2,1,809,NULL,1,0,'UserMessage','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'log:userMessage:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-03-07 09:50:30','2020-08-20 15:50:38'),(831,'删除饮食记录','删除','POST','/diet/delete',0,2,1,787,NULL,1,0,'Diet','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:diet:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-03-14 15:01:50','2020-08-15 17:11:54'),(832,'新增收入','新增','POST','/income/create',0,0,1,654,NULL,1,0,'Income','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:income:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-03-15 19:38:40','2020-07-27 10:27:47'),(833,'获取用户评分比对','评分比对','GET','/user/getScoreCompare',0,3,1,800,NULL,1,0,'UserScore','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-03-18 09:18:23',NULL),(834,'获取常驻城市','常驻城市','GET','/user/getResidentCity',0,3,1,800,NULL,1,0,'UserSetting','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-03-18 09:56:13',NULL),(835,'修改计划值','修改计划值','POST','/userPlanConfigValue/edit',0,1,1,950,NULL,1,0,'UserPlanConfigValue','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:userPlan:configValue:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-03-20 08:07:43','2020-08-13 08:04:37'),(836,'删除加班记录','删除','POST','/workOvertime/delete',0,2,1,783,NULL,1,0,'WorkOvertime','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:workOvertime:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-03-22 21:52:38','2020-08-15 15:47:07'),(837,'饮食比对','比对','GET','/diet/compare',0,4,0,786,'yoy',1,5,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:diet:compare','food/diet/compare','diet/compare',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-03-29 14:42:00','2020-10-12 21:44:31'),(838,'预算统计日志','周期性统计','GET','/budgetLog/getPeriodStat',0,3,0,642,'fund',1,12,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetLog:periodStat','fund/budgetLog/periodStat','budgetLog/periodStat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-04-01 10:22:53','2021-03-10 11:19:05'),(839,'预算快照','预算快照列表','GET','/budgetSnapshot/getData',0,3,0,642,'fund',1,6,'BudgetSnapshot','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetSnapshot:query','fund/budgetSnapshot/index','budgetSnapshot',_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-04-07 10:14:34','2021-03-07 17:10:29'),(840,'预算快照统计','预算快照统计','GET','/budgetSnapshot/stat',0,4,1,656,NULL,1,0,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-04-10 21:14:07',NULL),(843,'获取人生经历树','人生经历树','GET,POST','/lifeExperience/getLifeExperienceTree',0,0,2,692,NULL,1,0,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-04-17 11:20:16',NULL),(844,'花费统计','花费统计','GET','/lifeExperience/costStat',0,0,1,692,NULL,1,0,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperience:costStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-04-17 11:22:27','2020-08-10 18:40:52'),(845,'增加检验报告','增加','POST','/treatTest/create',0,0,1,681,NULL,1,0,'TreatTest','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatTest:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-05-15 16:45:30','2020-08-08 16:54:13'),(846,'系统维护','系统维护','GET','/commandConfig/getData',0,0,0,815,'tool',1,6,'DatabaseClean','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:commandConfig:query','system/commandConfig/index','commandConfig',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-06-07 08:28:24','2020-10-19 16:27:25'),(847,'发送命令','发送命令','POST','/commandConfig/sendCmd',0,5,1,846,NULL,1,0,'DatabaseClean','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:commandConfig:sendCmd',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-07 08:29:08','2020-10-19 16:27:19'),(849,'获取调度日志详情','详情','GET','/taskLog/get',0,3,1,813,NULL,1,0,'TaskLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-07 10:58:13',NULL),(850,'备份管理','备份管理','GET','/backup/getData',0,5,0,815,'excel',1,7,'SystemLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:backup:query','system/backup/index','backup',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-06-07 17:45:01','2020-08-24 18:09:59'),(852,'我的日历','获取用户日历数据列表','GET','/userCalendar/getList',0,3,0,797,'date',1,3,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userCalendar:myCalendar','data/userCalendar/myCalendar','userCalendar/myCalendar',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-06-08 22:22:01','2020-10-12 21:57:01'),(853,'增加用户日历','增加','POST','/userCalendar/create',0,0,1,798,NULL,1,0,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userCalendar:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-09 11:42:25','2020-08-17 22:14:41'),(854,'更新用户日历','更新','POST','/userCalendar/update',0,1,1,798,NULL,1,0,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userCalendar:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-09 13:50:48','2020-08-17 22:14:57'),(855,'删除用户日历','删除','POST','/userCalendar/delete',0,2,1,798,NULL,1,0,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userCalendar:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-09 14:16:37','2020-08-17 22:15:19'),(856,'新增日历2','新增2','POST','/userCalendar/create2',0,0,1,798,NULL,1,0,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userCalendar:create2',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-09 14:33:50','2020-08-17 22:15:09'),(857,'获取用户日历源信息','获取源信息','GET','/userCalendar/getSource',0,3,1,798,NULL,1,0,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-09 22:22:49',NULL),(858,'用户行为分析(日历)','用户行为分析(日历)','GET','/userBehavior/calendarStat',0,5,0,792,'date',1,6,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehavior:calendarStat','behavior/userBehavior/calendarStat','userBehavior/calendarStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-06-10 09:58:01','2020-10-12 21:54:15'),(861,'重开用户日历','重开','POST','/userCalendar/reOpen',0,5,1,798,NULL,1,0,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userCalendar:reOpen',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-11 12:20:06','2020-08-18 10:34:09'),(862,'发送用户日历消息','发送消息','POST','/userCalendar/sendCalendarMessage',0,5,1,798,NULL,1,0,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-11 17:34:28',NULL),(863,'公众号回调','公众号回调','GET,POST','/wechat/wx_mp_notify',0,5,1,826,NULL,1,0,'WorkOvertime','id',0,_binary '\0',_binary '\0',_binary '',0,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-12 11:03:42','2020-06-12 11:33:06'),(864,'获取微信授权地址','获取微信授权地址','GET','/wechat/wx_auth_access_url',0,5,1,826,NULL,1,0,'UserWxpayInfo','id',0,_binary '',_binary '\0',_binary '',0,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-12 13:12:32',NULL),(866,'用户日历模板管理','管理首页','GET','/userCalendarConfig/getData',0,0,0,797,'date',1,1,'UserCalendarConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userCalendarConfig:query','data/userCalendarConfig/index','userCalendarConfig',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-06-21 08:29:09','2020-10-12 21:56:45'),(868,'获取列表树','列表树','GET,POST','/userCalendarConfig/getUserCalendarConfigTree',0,3,2,866,NULL,1,0,'UserCalendarConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-21 08:30:49',NULL),(869,'获取用户列表树','用户列表树','GET,POST','/userCalendarConfig/getUserCalendarConfigForUserTree',0,3,2,866,NULL,1,0,'UserCalendarConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-21 08:31:35','2020-06-21 08:46:06'),(870,'新增模板','新增','POST','/userCalendarConfig/create',0,0,1,866,NULL,1,0,'UserCalendarConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userCalendarConfig:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-21 08:32:14','2020-08-18 13:06:20'),(871,'修改模板','修改','POST','/userCalendarConfig/edit',0,1,1,866,NULL,1,0,'UserCalendarConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userCalendarConfig:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-21 08:32:41','2020-08-18 13:06:40'),(872,'删除配置','删除','POST','/userCalendarConfig/delete',0,2,1,866,NULL,1,0,'UserCalendarConfig','ids',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userCalendarConfig:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-21 08:33:16','2020-08-18 13:06:34'),(873,'获取模板详情','详情','GET','/userCalendarConfig/get',0,3,1,866,NULL,1,0,'UserCalendarConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-21 08:45:47',NULL),(874,'曲子统计','曲子统计','GET','/musicPracticeTune/nameStat',0,3,1,164,NULL,1,0,'MusicPracticeTune','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPracticeTune:nameStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-22 11:14:48','2020-08-01 08:13:05'),(875,'水平统计','水平统计','GET','/musicPracticeTune/levelStat',0,3,1,164,NULL,1,0,'MusicPracticeTune','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPracticeTune:levelStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-22 15:37:42','2020-08-01 08:13:15'),(876,'QA配置','QA配置首页','GET','/qaConfig/getData',0,5,0,815,'cs',1,1,'QaConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:qaConfig:query','system/qaConfig/index','qaConfig',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-06-24 12:29:29','2020-10-12 22:08:33'),(878,'新增QA配置','新增','POST','/qaConfig/create',0,0,1,876,NULL,1,0,'QaConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:qaConfig:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-24 12:30:42','2020-08-22 06:53:50'),(879,'修改QA配置','修改','POST','/qaConfig/edit',0,0,1,876,NULL,1,0,'QaConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:qaConfig:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-24 12:31:09','2020-08-22 06:53:58'),(880,'获取QA配置详情','详情','GET','/qaConfig/get',0,0,1,876,NULL,1,0,'QaConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-24 12:31:35',NULL),(881,'删除QA配置','删除','POST','/qaConfig/delete',0,0,1,876,NULL,1,0,'QaConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:qaConfig:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-24 12:31:58','2020-08-22 06:54:06'),(882,'QA配置树','配置树','GET,POST','/qaConfig/getQaConfigTree',0,3,2,876,NULL,1,0,'QaConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-24 12:48:51',NULL),(883,'发送文本客服请求','发送文本客服请求','POST','/qaConfig/textReq',0,5,1,876,NULL,1,0,'QaConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'system:qaConfig:textReq',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-27 15:03:54','2020-08-22 08:11:53'),(884,'刷新缓存','刷新缓存','POST','/qaConfig/reloadCache',0,5,1,876,NULL,1,0,'QaConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:qaConfig:reloadCache',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-06-28 15:15:35','2020-08-22 07:48:01'),(885,'多样性统计','多样性统计','GET','/diet/getFoodsAvgSimilarity',0,4,0,786,'similarity',1,7,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:diet:foodsAvgSimilarity','food/diet/foodsAvgSimilarity','diet/foodsAvgSimilarity',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-07-03 22:18:51','2020-10-12 21:45:20'),(886,'多样性日志','多样性日志统计','GET','/diet/statFoodsAvgSimLog',0,4,0,786,'log',1,8,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:diet:statFoodsAvgSimLog','food/diet/statFoodsAvgSimLog','diet/statFoodsAvgSimLog',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-07-04 12:01:17','2020-10-12 21:45:31'),(888,'商品多样性分析','商品多样性分析','GET','/buyRecord/getGoodsNameAvgSimilarity',0,0,0,640,'similarity',1,11,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:goodsNameAvgSimilarity','consume/buyRecord/goodsNameAvgSimilarity','buyRecord/goodsNameAvgSimilarity',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-07-04 13:07:50','2020-10-12 18:23:43'),(889,'QA拓扑统计','拓扑结构','GET','/qaConfig/stat',0,4,0,815,'analyse',1,9,'QaConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:qaConfig:stat','system/qaConfig/stat','qaConfig/stat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-07-05 13:56:49','2021-03-14 16:07:43'),(890,'饮食词云','词云','GET','/diet/statWordCloud',0,4,0,786,'wordcloud',1,9,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:diet:statWordCloud','food/diet/statWordCloud','diet/statWordCloud',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-07-07 16:14:51','2020-10-12 21:45:40'),(891,'商品词云统计','商品词云统计','GET','/buyRecord/statWordCloud',0,0,0,640,'wordcloud',1,10,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:wordCloudStat','consume/buyRecord/wordCloudStat','buyRecord/wordCloudStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-07-07 22:00:30','2020-10-12 18:23:32'),(893,'用户操作行为配置','用户操作行为配置首页','GET','/userOperationConfig/getData',0,5,0,792,'behavior',1,9,'UserOperationConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userOperationConfig:query','behavior/userOperationConfig/index','userOperationConfig',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-07-08 10:10:42','2020-10-12 21:54:46'),(895,'新增操作行为配置','新增','POST','/userOperationConfig/create',0,0,1,893,NULL,1,0,'UserOperationConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userOperationConfig:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-08 10:12:29','2020-08-19 08:15:43'),(896,'修改用户操作行为配置','修改','POST','/userOperationConfig/edit',0,1,1,893,NULL,1,0,'UserOperationConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userOperationConfig:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-08 10:13:15','2020-08-19 08:15:51'),(897,'获取用户操作行为配置详情','详情','GET','/userOperationConfig/get',0,3,1,893,NULL,1,0,'UserOperationConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-08 10:13:51',NULL),(898,'删除用户操作行为配置','删除','POST','/userOperationConfig/delete',0,2,1,893,NULL,1,0,'UserOperationConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userOperationConfig:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-08 10:14:33','2020-08-19 08:16:09'),(899,'获取用户操作行为配置树','配置树','GET,POST','/userOperationConfig/getUserOperationConfigTree',0,3,2,893,NULL,1,0,'UserOperationConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-08 10:15:15',NULL),(900,'统计用户操作行为','统计用户操作行为','GET','/userOperationConfig/userOperationStat',0,4,1,893,NULL,1,0,'UserOperationConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-08 11:18:52',NULL),(901,'我的词云','词云','GET','/userOperationConfig/wordCloudStat',0,4,0,792,'wordcloud',1,10,'UserOperationConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userOperationConfig:wordCloudStat','behavior/userOperationConfig/wordCloudStat','userOperationConfig/wordCloudStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-07-08 12:11:01','2020-10-12 21:54:57'),(902,'获取商品类型列表数据(普通)','获取商品类型列表数据(普通)','GET','/goodsType/getData2',0,3,1,644,NULL,1,0,'GoodsType','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-21 22:35:08',NULL),(903,'获取路由','获取路由','GET','/main/getRouters',0,0,1,657,NULL,1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-23 10:25:15',NULL),(904,'获取验证码','获取验证码','GET','/captcha/captchaImage',0,3,1,657,NULL,1,0,'User','id',0,_binary '',_binary '\0',_binary '',0,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-23 10:25:56','2020-08-26 08:16:34'),(905,'登出','登出','POST','/main/logout',0,5,1,657,NULL,1,0,'User','id',0,_binary '',_binary '\0',_binary '',0,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-23 11:22:05',NULL),(906,'获取个人信息授权信息',NULL,'GET','/user/getMyInfoWithPerms',0,3,1,657,NULL,1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-24 21:37:35',NULL),(907,'删除账号',NULL,'POST','/account/delete',0,2,1,652,NULL,1,0,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:account:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-25 11:26:15','2020-08-06 09:19:09'),(908,'删除账户快照','删除账户快照','POST','/accountSnapshotInfo/delete',0,2,1,652,NULL,1,0,'AccountSnapshotInfo','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:accountSnapshotInfo:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-26 14:57:13','2020-08-06 09:19:17'),(909,'账户预测','账户预测','GET','/account/forecast',0,5,0,642,'chart',1,10,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:account:forecast','fund/account/forecast','account/forecast',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-07-26 20:14:30','2020-10-12 18:29:20'),(910,'删除收入',NULL,'POST','/income/delete',0,2,1,654,NULL,1,0,'Income','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:income:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-27 10:28:34','2020-08-06 09:33:58'),(911,'删除预算','删除','POST','/budget/delete',0,2,1,656,NULL,1,0,'Budget','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budget:delete',NULL,NULL,_binary '',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-28 21:41:39',NULL),(912,'预算流水',NULL,'GET','/budgetLog/getData',0,5,0,642,'fund',1,6,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetLog:query','fund/budgetLog/index','budgetLog',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-07-30 15:00:59','2020-10-12 18:28:29'),(914,'修改预算流水',NULL,'POST','/budgetLog/edit',0,1,1,912,NULL,1,0,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetLog:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-30 15:03:03','2020-08-06 09:45:23'),(915,'删除预算流水',NULL,'POST','/budgetLog/delete',0,0,1,912,NULL,1,0,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetLog:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-30 15:03:50','2020-08-06 09:45:32'),(916,'流水详情',NULL,'GET','/budgetLog/get',0,3,1,912,NULL,1,0,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-07-30 15:53:10','2020-08-06 09:45:48'),(917,'音乐练习比对',NULL,'GET','/musicPractice/compareStat',0,3,0,663,'yoy',1,5,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPractice:compareStat','music/musicPractice/compareStat','musicPractice/compareStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-07-31 16:50:24','2020-10-12 19:05:10'),(918,'音乐练习曲子统计',NULL,'GET','/music/musicPracticeTuneStat.html',0,0,0,663,'chart',1,8,'MusicPracticeTune','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPracticeTune:stat','music/musicPracticeTune/stat','musicPracticeTune/stat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-08-01 09:32:53','2020-10-12 19:05:26'),(919,'修改提醒',NULL,'POST','/dream/addOrEditRemind',0,0,1,668,NULL,1,0,'DreamRemind','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'dream:remind:edit',NULL,NULL,_binary '',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-02 14:45:38',NULL),(920,'获取功能点列表',NULL,'GET','/systemFunction/getList',0,3,1,805,NULL,1,0,'SystemFunction','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-05 14:01:50',NULL),(922,'安全模块',NULL,'POST','login',0,0,0,0,'system',1,97,'User','id',0,_binary '',_binary '\0',_binary '',0,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-05 19:45:17','2020-08-05 19:49:13'),(924,'删除角色',NULL,'POST','/role/delete',0,2,1,804,'icon',1,0,'Role','ids',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:role:delete',NULL,NULL,_binary '',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-05 21:11:48',NULL),(925,'导出购买来源',NULL,'POST','/buyType/export',0,5,1,643,'icon',1,6,'BuyType','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyType:export',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-06 07:58:41','2020-08-06 08:11:43'),(926,'导出价格区间',NULL,'POST','/priceRegion/export',0,5,1,645,'icon',1,4,'PriceRegion','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:priceRegion:export',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-06 08:23:30',NULL),(927,'导出消费记录',NULL,'POST','/buyRecord/export',0,0,1,648,'icon',1,8,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:export',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-06 08:38:15',NULL),(928,'导出账户',NULL,'POST','/account/export',0,5,1,652,'icon',1,0,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:account:export',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-06 09:18:26',NULL),(929,'删除账户流水',NULL,'POST','/accountFlow/delete',0,2,1,653,'icon',1,2,'AccountFlow','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:accountFlow:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-06 09:30:56',NULL),(930,'导出收入',NULL,'POST','/income/export',0,5,1,654,'icon',1,3,'Income','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:income:export',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-06 09:33:47',NULL),(931,'最后一次药品',NULL,'GET','/treatDrug/getLastTreatDrug',0,3,1,679,'icon',1,0,'TreatDrug','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-08 14:44:46',NULL),(932,'修改检验报告',NULL,'POST','/treatTest/edit',0,1,1,681,'icon',1,0,'TreatTest','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatTest:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-08 16:55:01',NULL),(933,'删除检验报告',NULL,'POST','/treatTest/delete',0,2,1,681,'icon',1,0,'TreatTest','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatTest:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-08 16:55:50',NULL),(934,'删除身体基本数据',NULL,'POST','/bodyBasicInfo/delete',0,2,1,683,'icon',1,0,'BodyBasicInfo','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyBasicInfo:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-09 21:28:22',NULL),(935,'删除身体不适',NULL,'POST','/bodyAbnormalRecord/delete',0,2,1,682,'icon',1,0,'BodyAbnormalRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:bodyAbnormalRecord:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-10 08:52:26',NULL),(936,'删除睡眠',NULL,'POST','/sleep/delete',0,2,1,684,'icon',1,0,'Sleep','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:body:sleep:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-10 13:48:10',NULL),(937,'删除城市地理',NULL,'POST','/cityLocation/delete',0,2,1,686,'icon',1,0,'CityLocation','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:cityLocation:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-10 16:01:33','2020-08-10 16:27:56'),(938,'新增人生经历汇总',NULL,'POST','/lifeExperienceSum/create',0,0,1,693,'icon',1,0,'LifeExperienceSum','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceSum:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-11 09:49:51',NULL),(939,'修改人生经历汇总',NULL,'POST','/lifeExperienceSum/edit',0,1,1,693,'icon',1,0,'LifeExperienceSum','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceSum:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-11 09:50:23',NULL),(940,'删除人生经历汇总',NULL,'POST','/lifeExperienceSum/delete',0,2,1,693,'icon',1,0,'LifeExperienceSum','ids',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceSum:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-11 09:50:55',NULL),(941,'获取人生经历汇总详情',NULL,'GET','/lifeExperienceSum/get',0,3,1,693,'icon',1,0,'LifeExperienceSum','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-11 09:55:03',NULL),(942,'人生经历汇总分析',NULL,'GET','/lifeExperienceSum/analyse',0,4,1,693,'icon',1,0,'LifeExperienceSum','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperienceSum:analyse',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-11 10:10:19',NULL),(944,'模板值配置',NULL,'GET','/statValueConfig/getData',0,0,0,761,'template',1,5,'StatValueConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:statValueConfig:query','report/statValueConfig/index','statValueConfig',_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-11 15:21:57','2020-10-16 10:29:52'),(945,'删除模板配置值',NULL,'POST','/statValueConfig/delete',0,2,1,944,'icon',1,0,'StatValueConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'report:statValueConfig:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-11 15:28:12','2020-10-16 10:30:21'),(946,'删除用户提醒',NULL,'POST','/userNotify/delete',0,2,1,764,'icon',1,0,'UserNotify','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:notify:userNotify:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-11 16:54:56','2020-08-11 16:56:25'),(947,'删除计划配置模板',NULL,'POST','/planConfig/delete',0,2,1,775,'icon',1,0,'PlanConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planConfig:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-12 14:03:20',NULL),(948,'删除用户计划',NULL,'POST','/userPlan/delete',0,2,1,774,'icon',1,0,'UserPlan','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:userPlan:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-12 16:04:45',NULL),(950,'计划值',NULL,'GET','/userPlanConfigValue/getData',0,3,0,774,'icon',1,0,'UserPlanConfigValue','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:userPlan:configValue:query','report/plan/userPlan/configValue','userPlan/configValue',_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-13 07:59:28','2020-08-13 08:00:31'),(951,'新增计划值',NULL,'POST','/userPlanConfigValue/create',0,0,1,950,'icon',1,0,'UserPlanConfigValue','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:userPlan:configValue:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-13 08:04:15','2020-08-13 08:39:13'),(952,'删除计划值',NULL,'POST','/userPlanConfigValue/delete',0,2,1,950,'icon',1,0,'UserPlanConfigValue','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:userPlan:configValue:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-13 08:05:19',NULL),(953,'删除计划报告',NULL,'POST','/planReport/delete',0,2,1,770,'icon',1,0,'PlanReport','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planReport:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-13 10:13:26',NULL),(954,'重新统计',NULL,'POST','/planReport/reStat',0,0,1,770,'icon',1,0,'PlanReport','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planReport:reStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-13 11:01:10','2020-08-13 12:29:31'),(955,'删除报告数据',NULL,'POST','/planReport/cleanData',0,2,1,770,'icon',1,0,'PlanReport','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planReport:cleanData',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-13 13:12:13',NULL),(956,'获取枚举类树',NULL,'GET','/common/getEnumClassNamesTree',0,0,1,785,'icon',1,0,'AccountSnapshotInfo','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-14 10:18:37',NULL),(958,'获取数据字段组树',NULL,'GET','/dictGroup/getDictGroupTree',0,3,1,999,'icon',1,0,'DictGroup','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-14 10:34:51','2020-08-24 22:19:53'),(959,'删除图标配置模板',NULL,'POST','/chartConfig/delete',0,2,1,821,'icon',1,0,'ChartConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:chart:chartConfig:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-14 14:14:48',NULL),(960,'图表统计',NULL,'GET','/userChart/stat',0,0,0,820,'chart',1,3,'UserChart','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:chart:userChart:stat','report/chart/userChart/stat','userChart/stat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-08-14 15:35:25','2020-08-14 15:36:40'),(961,'删除图书分类',NULL,'POST','/bookCategory/delete',0,0,1,823,'icon',1,0,'BookCategory','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:bookCategory:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-15 09:04:37',NULL),(962,'删除阅读详情',NULL,'POST','/readingRecordDetail/delete',0,2,1,780,'icon',1,0,'ReadingRecordDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecordDetail:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-15 11:06:48',NULL),(963,'删除公司',NULL,'POST','/company/delete',0,0,1,782,'icon',1,0,'Company','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'work:company:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-15 15:34:45',NULL),(964,'新增饮食分类',NULL,'POST','/dietCategory/create',0,0,1,788,'icon',1,0,'DietCategory','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:dietCategory:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-15 16:55:04','2020-08-15 17:11:16'),(965,'修改饮食分类',NULL,'POST','/dietCategory/edit',0,1,1,788,'icon',1,0,'DietCategory','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:dietCategory:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-15 16:55:40','2020-08-15 17:11:21'),(966,'删除饮食分类',NULL,'POST','/dietCategory/delete',0,2,1,788,'icon',1,0,'DietCategory','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:dietCategory:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-15 16:56:15','2020-08-15 17:11:28'),(967,'获取详情',NULL,'GET','/dietCategory/get',0,3,1,788,'icon',1,0,'DietCategory','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-15 16:56:41',NULL),(969,'修改通用记录类型',NULL,'POST','/commonRecordType/edit',0,1,1,790,'icon',1,0,'CommonRecordType','id',1,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'common:commonRecordType:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-16 08:37:10','2020-10-19 21:50:53'),(970,'新增数据录入分析',NULL,'POST','/dataInputAnalyse/create',0,0,1,793,'icon',1,0,'DataInputAnalyse','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:dataInputAnalyse:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-16 11:43:21','2020-10-16 10:36:20'),(971,'删除数据录入分析',NULL,'POST','/dataInputAnalyse/delete',0,2,1,793,'icon',1,0,'DataInputAnalyse','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:dataInputAnalyse:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-16 11:43:54','2020-10-16 10:36:28'),(972,'删除用户行为',NULL,'POST','/userBehavior/delete',0,2,1,796,'icon',1,0,'UserBehavior','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'behavior:userBehavior:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-16 13:20:59',NULL),(973,'查询统计',NULL,'POST','/userScore/reSave',0,0,1,802,'icon',1,0,'UserScore','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userScore:reSave',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-19 14:37:48',NULL),(974,'新增用户',NULL,'POST','/user/create',0,0,1,800,'icon',1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:user:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-19 17:17:20',NULL),(975,'删除用户',NULL,'POST','/user/delete',0,2,1,800,'icon',1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'auth:user:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-19 17:17:53',NULL),(976,'获取操作对象值',NULL,'GET','/systemLog/getBeanDetail',0,0,1,808,'icon',1,0,'SystemLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'log:systemLog:getBeanDetail',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-20 15:05:02',NULL),(977,'删除错误代码',NULL,'POST','/errorCodeDefine/delete',0,2,1,814,'icon',1,0,'ErrorCodeDefine','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'log:errorCodeDefine:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-20 18:57:06',NULL),(978,'重置总次数',NULL,'POST','/taskTrigger/resetTotalCount',0,5,1,812,'icon',1,0,'TaskTrigger','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskTrigger:resetTotalCount',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-21 12:21:40',NULL),(979,'重置总失败次数',NULL,'POST','/taskTrigger/resetFailCount',0,5,1,812,'icon',1,0,'TaskTrigger','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'schedule:taskTrigger:resetFailCount',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-21 12:22:23',NULL),(980,'新增配置',NULL,'POST','/databaseClean/create',0,0,1,816,'icon',1,0,'DatabaseClean','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'system:databaseClean:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-22 14:59:06',NULL),(981,'删除配置',NULL,'POST','/databaseClean/delete',0,2,1,816,'icon',1,0,'DatabaseClean','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'system:databaseClean:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-22 14:59:39',NULL),(982,'手动清理',NULL,'POST','/databaseClean/manualClean',0,2,1,816,'icon',1,0,'DatabaseClean','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'system:databaseClean:manualClean',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-22 16:02:37',NULL),(983,'处理命令',NULL,'POST','/handler/handCmd',0,5,1,818,'icon',1,0,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'system:handler:handCmd',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-22 17:06:14',NULL),(984,'自检',NULL,'POST','/handler/check',0,0,1,818,'icon',1,0,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'system:handler:check',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-22 17:13:15',NULL),(985,'重新加载',NULL,'POST','/handler/reload',0,0,1,818,'icon',1,0,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'system:handler:reload',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-22 17:13:41',NULL),(987,'通用计划统计',NULL,'GET','/userPlan/aa',0,4,0,772,'chart',1,8,'UserPlan','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:userPlan:commonStat','report/plan/userPlan/commonStat','userPlan/commonStat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-08-23 10:09:32','2020-10-12 21:19:18'),(988,'获取在首页显示的图表',NULL,'GET','/userChart/getShowIndexChart',0,3,1,822,'icon',1,0,'UserChart','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-23 20:59:45',NULL),(989,'获取用户快捷菜单',NULL,'GET','/fastMenu/getList',0,3,1,994,'icon',1,0,'FastMenu','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 09:03:56','2020-08-24 14:12:44'),(990,'上传头像',NULL,'POST','/user/uploadAvatar',0,0,1,994,'icon',1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'system:user:uploadAvatar',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 10:08:51','2020-08-24 14:13:01'),(991,'获取用户个人信息',NULL,'GET','/user/getProfile',0,3,1,994,'icon',1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 11:12:16','2020-08-24 14:13:15'),(992,'修改用户信息',NULL,'POST','/user/editProfile',0,1,1,994,'icon',1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 13:04:58','2020-08-24 14:13:21'),(993,'用户修改密码',NULL,'POST','/user/editPassword',0,1,1,994,'icon',1,0,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 13:14:39','2020-08-24 14:13:28'),(994,'个人中心',NULL,'GET','/userCenter',0,0,3,0,'people',1,19,'User','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'userCenter','userCenter',_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 14:12:21','2020-10-12 22:12:43'),(995,'用户快捷菜单树',NULL,'GET','/fastMenu/getMenuTree',0,3,1,994,'icon',1,0,'FastMenu','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 14:14:37',NULL),(996,'保存快捷菜单',NULL,'POST','/fastMenu/saveFastMenu',0,0,1,994,'icon',1,0,'FastMenu','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 14:26:34',NULL),(997,'获取最新的消息',NULL,'GET','/userMessage/getLatestMessage',0,3,1,809,'icon',1,0,'UserMessage','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 16:57:21',NULL),(998,'配置管理',NULL,'GET','config',0,5,3,0,'config',1,20,'DictGroup','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'config','config',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-08-24 22:15:46','2020-10-12 22:15:01'),(999,'数据字典组',NULL,'GET','/dictGroup/getData',0,3,0,998,'config',1,1,'DictGroup','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:dictGroup:query','config/dictGroup/index','dictGroup',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-08-24 22:18:17','2020-10-12 22:17:00'),(1000,'数据字典项',NULL,'GET','/dictItem/getData',0,3,0,998,'config',1,2,'DictItem','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:dictItem:query','config/dictItem/index','dictItem',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-08-24 22:21:30','2020-10-12 22:17:07'),(1001,'新增字典组',NULL,'POST','/dictGroup/create',0,0,1,999,'icon',1,0,'DictGroup','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:dictGroup:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 22:23:57',NULL),(1002,'修改字典组',NULL,'POST','/dictGroup/edit',0,1,1,999,'icon',1,0,'DictGroup','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:dictGroup:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 22:24:31','2020-08-24 22:25:28'),(1003,'删除字典组',NULL,'POST','/dictGroup/delete',0,2,1,999,'icon',1,0,'DictGroup','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:dictGroup:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 22:25:13','2020-08-25 07:10:10'),(1004,'新增字典项',NULL,'POST','/dictItem/create',0,0,1,1000,'icon',1,0,'DictItem','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:dictItem:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 22:26:25',NULL),(1005,'修改字典项',NULL,'POST','/dictItem/edit',0,1,1,1000,'icon',1,0,'DictItem','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:dictItem:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 22:27:01',NULL),(1006,'删除字典项',NULL,'POST','/dictItem/delete',0,2,1,1000,'icon',1,0,'DictItem','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:dictItem:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-24 22:27:35',NULL),(1007,'获取详情',NULL,'GET','/dictGroup/get',0,3,1,999,'icon',1,0,'DictGroup','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 07:11:09','2020-08-25 07:11:20'),(1008,'获取详情',NULL,'GET','/dictItem/get',0,3,1,1000,'icon',1,0,'DictItem','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 07:11:51',NULL),(1009,'系统配置',NULL,'GET','/systemConfig/getData',0,3,0,998,'config',1,3,'SystemConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:systemConfig:query','config/systemConfig/index','systemConfig',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-08-25 09:59:58','2020-10-12 22:17:15'),(1010,'新增配置',NULL,'POST','/systemConfig/create',0,0,1,1009,'icon',1,0,'SystemConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:systemConfig:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 10:00:35',NULL),(1011,'修改配置',NULL,'POST','/systemConfig/edit',0,1,1,1009,'icon',1,0,'SystemConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:systemConfig:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 10:01:26',NULL),(1012,'删除配置',NULL,'POST','/systemConfig/delete',0,2,1,1009,'icon',1,0,'SystemConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:systemConfig:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 10:02:00',NULL),(1013,'获取配置',NULL,'GET','/systemConfig/get',0,3,1,1009,'icon',1,0,'SystemConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:systemConfig:get',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 10:02:33','2020-08-25 10:02:46'),(1014,'人生档案',NULL,'GET','/lifeArchives/getData',0,3,0,685,'life',1,9,'LifeArchives','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeArchives:query','life/lifeArchives/index','lifeArchives',_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 14:16:27','2020-10-12 21:11:46'),(1015,'新增人生档案',NULL,'POST','/lifeArchives/create',0,0,1,1014,'icon',1,0,'LifeArchives','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeArchives:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 14:18:09',NULL),(1016,'修改人生档案',NULL,'POST','/lifeArchives/edit',0,1,1,1014,'icon',1,0,'LifeArchives','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeArchives:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 14:18:42',NULL),(1017,'删除人生档案',NULL,'POST','/lifeArchives/delete',0,2,1,1014,'icon',1,0,'LifeArchives','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeArchives:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 14:19:28',NULL),(1018,'获取人生档案详情',NULL,'GET','/lifeArchives/get',0,3,1,1014,'icon',1,0,'LifeArchives','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 14:20:07',NULL),(1019,'用户等级配置',NULL,'GET','/levelConfig/getData',0,3,0,998,'level',1,4,'LevelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:levelConfig:query','config/levelConfig/index','levelConfig',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-08-25 16:39:35','2020-10-12 22:17:28'),(1020,'新增等级配置',NULL,'POST','/levelConfig/create',0,0,1,1019,'icon',1,0,'LevelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:levelConfig:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 16:40:26','2020-08-25 16:41:12'),(1021,'修改等级配置',NULL,'POST','/levelConfig/edit',0,1,1,1019,'icon',1,0,'LevelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:levelConfig:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 16:41:03',NULL),(1022,'删除用户等级',NULL,'POST','/levelConfig/delete',0,2,1,1019,'icon',1,0,'LevelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:levelConfig:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 16:41:55',NULL),(1023,'获取等级配置详情',NULL,'GET','/levelConfig/get',0,3,1,1019,'icon',1,0,'LevelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 16:42:36',NULL),(1024,'获取角色树',NULL,'GET','/role/getRoleTree',0,5,2,804,'icon',1,0,'Role','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 17:01:22',NULL),(1025,'获取源数据',NULL,'GET','/lifeArchives/getSource',0,3,1,1014,'icon',1,0,'LifeArchives','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeArchives:getSource',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 18:53:21',NULL),(1026,'用户等级评定',NULL,'POST','/levelConfig/judgeLevel',0,5,1,1019,'icon',1,0,'LevelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:levelConfig:judgeLevel',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-25 22:09:47','2020-08-25 22:59:32'),(1027,'用户自评',NULL,'POST','/levelConfig/selfJudge',0,5,1,1019,'icon',1,0,'LevelConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'config:levelConfig:selfJudge',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-26 06:40:30',NULL),(1028,'评分配置组',NULL,'GET','/scoreConfigGroup/getData',0,3,0,998,'documentation',1,5,'ScoreConfigGroup','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:scoreConfigGroup:query','config/scoreConfigGroup/index','scoreConfigGroup',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-08-26 15:44:00',NULL),(1029,'新增配置组',NULL,'POST','/scoreConfigGroup/create',0,0,1,1028,'icon',1,0,'ScoreConfigGroup','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:scoreConfigGroup:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-26 15:44:33','2020-08-26 15:57:34'),(1030,'修改配载组',NULL,'POST','/scoreConfigGroup/edit',0,1,1,1028,'icon',1,0,'ScoreConfigGroup','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:scoreConfigGroup:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-26 15:45:15',NULL),(1031,'删除配置组',NULL,'POST','/scoreConfigGroup/delete',0,2,1,1028,'icon',1,0,'ScoreConfigGroup','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:scoreConfigGroup:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-26 15:45:57',NULL),(1032,'获取配置组详情',NULL,'GET','/scoreConfigGroup/get',0,3,1,1028,'icon',1,0,'ScoreConfigGroup','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:scoreConfigGroup:get',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-26 15:46:33',NULL),(1033,'获取配置组树',NULL,'GET','/scoreConfigGroup/getScoreConfigGroupTree',0,5,2,1028,'icon',1,0,'ScoreConfigGroup','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-26 15:47:20',NULL),(1034,'评分配置项',NULL,'GET','/scoreConfig/getData',0,3,0,998,'score',1,6,'ScoreConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:scoreConfig:query','config/scoreConfig/index','scoreConfig',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-08-26 15:48:28','2020-10-12 22:17:39'),(1035,'新增配置项',NULL,'POST','/scoreConfig/create',0,0,1,1034,'icon',1,0,'ScoreConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:scoreConfig:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-26 15:49:56',NULL),(1036,'修改配置项',NULL,'POST','/scoreConfig/edit',0,1,1,1034,'icon',1,0,'ScoreConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:scoreConfig:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-26 15:50:25',NULL),(1037,'删除配置项',NULL,'POST','/scoreConfig/delete',0,2,1,1034,'icon',1,0,'ScoreConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:scoreConfig:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-26 15:51:00',NULL),(1038,'获取配置项详情',NULL,'GET','/scoreConfig/get',0,3,1,1034,'icon',1,0,'ScoreConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-26 15:51:32',NULL),(1039,'以模板新增',NULL,'POST','/scoreConfigGroup/createByTemplate',0,0,1,1028,'icon',1,0,'ScoreConfigGroup','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:scoreConfigGroup:createByTemplate',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-26 16:49:56',NULL),(1040,'用户自评',NULL,'POST','/userScore/selfJudge',0,3,1,802,'icon',1,0,'UserScore','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userScore:selfJudge',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-27 08:58:10',NULL),(1041,'刷新缓存',NULL,'POST','/systemConfig/refreshCache',0,5,1,1009,'icon',1,0,'SystemConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'config:systemConfig:refreshCache',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-27 20:23:36',NULL),(1042,'获取我的家庭',NULL,'GET','/family/getMyFamily',0,3,1,1044,'icon',1,0,'Family','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-27 22:30:23','2020-08-28 10:09:56'),(1043,'家庭管理',NULL,'GET','familyGroup',0,5,3,0,'peoples',1,21,'Family','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'familyGroup','familyGroup',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-08-28 10:04:58','2020-10-12 22:18:26'),(1044,'家庭管理',NULL,'GET','/family/getData',0,5,0,1043,'peoples',1,0,'Family','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'familyGroup:family:query','familyGroup/family/index','family',_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-28 10:06:54','2020-10-12 22:18:15'),(1045,'新增家庭',NULL,'POST','/family/create',0,0,1,1044,'icon',1,0,'Family','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'familyGroup:family:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-28 10:07:34',NULL),(1046,'修改家庭',NULL,'POST','/family/edit',0,1,1,1044,'icon',1,0,'Family','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'familyGroup:family:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-28 10:08:09',NULL),(1047,'解散家庭',NULL,'POST','/family/dismiss',0,2,1,1044,'icon',1,0,'Family','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'familyGroup:family:dismiss',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-08-28 10:09:34',NULL),(1048,'下载',NULL,'GET','/backup/download',0,5,1,850,'icon',1,0,'Account','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:backup:download',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-02 13:25:34','2020-09-25 14:21:04'),(1049,'其他链接',NULL,'GET','/other',0,5,3,0,'international',1,93,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,'other',_binary '',_binary '',_binary '',_binary '',NULL,'2020-09-06 10:49:11','2020-09-06 14:45:40'),(1050,'电脑版JQ',NULL,'GET','http://git.mulanbay.cn/pc/html/main/main.html',0,0,0,1049,'icon',1,1,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,'http://git.mulanbay.cn/pc/html/main/main.html',_binary '',_binary '',_binary '',_binary '',NULL,'2020-09-06 10:50:31','2020-10-16 12:45:48'),(1051,'移动版JQ',NULL,'GET','http://git.mulanbay.cn/mobile/html/main/main.html',0,5,0,1049,'icon',1,2,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,'http://git.mulanbay.cn/mobile/html/main/main.html',_binary '',_binary '',_binary '',_binary '',NULL,'2020-09-06 10:51:14','2021-01-26 16:51:52'),(1052,'阅读时长统计',NULL,'GET','/readingRecordDetail/dateStat',0,4,0,778,'chart',1,5,'ReadingRecordDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecordDetail:dateStat','read/readingRecordDetail/dateStat','readingRecordDetail/dateStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-09-06 19:36:58','2020-10-12 21:21:41'),(1053,'距离此刻统计',NULL,'GET','/musicPractice/fromThisStat',0,3,1,665,'icon',1,0,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-06 21:00:20',NULL),(1054,'删除',NULL,'POST','/backup/delete',0,2,1,850,'icon',1,0,'Account','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'system:backup:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-07 12:25:38','2020-09-07 12:51:55'),(1055,'根据位置名称获取',NULL,'GET','/cityLocation/getByLocation',0,3,1,686,'icon',1,0,'CityLocation','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-09 18:09:39',NULL),(1056,'商品智能匹配',NULL,'POST','/buyRecord/aiMatch',0,5,1,648,'icon',1,0,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-14 08:16:10',NULL),(1057,'消费匹配日志',NULL,'GET','/buyRecordMatchLog/getData',0,3,0,806,'yoy',1,7,'BuyRecordMatchLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:buyRecordMatchLog:query','log/buyRecordMatchLog/index','buyRecordMatchLog',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-09-15 11:12:32','2020-10-12 22:02:10'),(1058,'新增匹配日志',NULL,'POST','/buyRecordMatchLog/create',0,0,1,1057,'icon',1,0,'BuyRecordMatchLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-15 11:13:42','2020-09-15 14:08:37'),(1059,'查询消费信息',NULL,'GET','/buyRecordMatchLog/getBuyRecord',0,3,1,1057,'icon',1,0,'BuyRecordMatchLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:buyRecordMatchLog:getBuyRecord',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-15 13:33:20','2020-09-15 14:08:44'),(1060,'查询比较信息',NULL,'GET','/buyRecordMatchLog/getCompareData',0,3,1,1057,'icon',1,0,'BuyRecordMatchLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-15 13:34:18','2020-09-15 14:08:52'),(1061,'根据源获取日历',NULL,'GET','/userCalendar/getBySource',0,3,1,798,'icon',1,0,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-28 16:22:24',NULL),(1062,'商品寿命配置',NULL,'GET','/goodsLifetime/getData',0,3,0,998,'consume',1,8,'GoodsLifetime','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:goodsLifetime:query','consume/goodsLifetime/index','goodsLifetime',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-09-28 16:32:11','2020-10-12 22:17:55'),(1063,'新增商品寿命配置',NULL,'POST','/goodsLifetime/create',0,0,1,1062,'icon',1,0,'GoodsLifetime','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:goodsLifetime:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-28 16:32:42',NULL),(1064,'修改商品寿命配置',NULL,'POST','/goodsLifetime/edit',0,1,1,1062,'icon',1,0,'GoodsLifetime','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:goodsLifetime:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-28 16:33:17',NULL),(1065,'删除商品寿命配置',NULL,'POST','/goodsLifetime/delete',0,2,1,1062,'icon',1,0,'GoodsLifetime','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:goodsLifetime:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-28 16:33:46',NULL),(1066,'获取商品寿命配置',NULL,'GET','/goodsLifetime/get',0,3,1,1062,'icon',1,0,'GoodsLifetime','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-28 16:34:27',NULL),(1067,'商品寿命匹配',NULL,'POST','/goodsLifetime/aiMatch',0,3,1,1062,'icon',1,0,'GoodsLifetime','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-09-28 16:35:03',NULL),(1068,'操作日志统计',NULL,'GET','/operationLog/stat',0,4,0,806,'log',1,2,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:operationLog:stat','log/operationLog/stat','operationLog/stat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-09-28 23:04:01','2020-09-28 23:31:01'),(1069,'操作日志分时统计',NULL,'GET','/operationLog/dateStat',0,4,0,806,'log',1,2,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:operationLog:dateStat','log/operationLog/dateStat','operationLog/dateStat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-09-29 08:01:47',NULL),(1070,'系统日志分析',NULL,'GET','/systemLog/analyseStat',0,4,0,806,'log',1,3,'SystemLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:systemLog:analyseStat','log/systemLog/analyseStat','systemLog/analyseStat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-09-29 08:50:48',NULL),(1075,'重新统计时间线',NULL,'POST','/planReport/reStatTimeline',0,4,1,770,'icon',1,0,'PlanReport','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:plan:planReport:reStatTimeline',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-10-15 10:25:30',NULL),(1076,'阅读总体统计',NULL,'GET','/readingRecord/fullStat',0,4,0,778,'analyse',1,6,'ReadingRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecord:fullStat','read/readingRecord/fullStat','readingRecord/fullStat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-10-21 10:32:28',NULL),(1077,'用户积分总体统计',NULL,'GET','/userRewardPointRecord/fullStat',0,4,0,797,'analyse',1,9,'UserRewardPointRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userRewardPointRecord:fullStat','data/userRewardPointRecord/fullStat','userRewardPointRecord/fullStat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-10-21 14:06:54',NULL),(1078,'人生经历词云',NULL,'GET','/lifeExperience/wordCloudStat',0,4,0,685,'wordcloud',1,10,'LifeExperience','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'life:lifeExperience:wordCloudStat','life/lifeExperience/wordCloudStat','lifeExperience/wordCloudStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-10-22 13:06:50',NULL),(1079,'评分分析',NULL,'GET','/userScore/analyse',0,4,0,797,'analyse',1,8,'UserScore','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userScore:analyse','data/userScore/analyse','userScore/analyse',_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-10-26 21:02:54',NULL),(1080,'获取下一个ID',NULL,'GET','/userScore/getNextId',0,3,1,802,'icon',1,0,'UserScore','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'data:userScore:getNextId',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-10-26 21:34:02',NULL),(1081,'饮食时间点统计',NULL,'GET','/diet/timeStat',0,4,0,786,'analyse',1,10,'Diet','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'food:diet:timeStat','food/diet/timeStat','diet/timeStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2020-10-30 10:09:45',NULL),(1082,'更新预算日志账户变化',NULL,'POST','/account/updateBudgetLogAccountChange',0,5,1,652,'icon',1,9,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:account:updateBudgetLogAccountChange',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2020-12-13 19:14:10',NULL),(1083,'预算误差统计',NULL,'GET','/budgetLog/valueErrorStat',0,4,0,642,'chart',1,13,'BudgetLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetLog:valueErrorStat','fund/budgetLog/valueErrorStat','budgetLog/valueErrorStat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2020-12-13 21:33:27',NULL),(1084,'移动端VUE',NULL,'GET','http://git.mulanbay.cn:81/home',0,5,0,1049,'icon',1,0,'Account','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'',NULL,'http://git.mulanbay.cn:81/home',_binary '',_binary '',_binary '',_binary '',NULL,'2021-01-26 16:52:44',NULL),(1085,'快速睡眠',NULL,'POST','/sleep/sleep',0,0,1,684,'icon',1,0,'Sleep','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-01-27 19:45:04',NULL),(1086,'快速起床',NULL,'POST','/sleep/getUp',0,1,1,684,'icon',1,0,'Sleep','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-01-27 19:45:28',NULL),(1087,'每日任务数',NULL,'GET','/userCalendar/dailyCount',0,3,1,798,'icon',1,0,'UserCalendar','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-02-05 09:15:31',NULL),(1088,'缓存监控',NULL,'GET','/cache/info',0,5,0,815,'monitor',1,8,'Account','id',0,_binary '',_binary '\0',_binary '',1,1,0,1,0,5,0,_binary '\0',0,0,1,0,'system:cache:query','system/cache/index','cache',_binary '',_binary '',_binary '\0',_binary '',NULL,'2021-02-22 11:05:32',NULL),(1089,'最近调度',NULL,'GET','/taskTrigger/recentSchedules',0,5,1,812,'icon',1,0,'TaskTrigger','id',0,_binary '',_binary '\0',_binary '',1,1,0,1,0,5,0,_binary '\0',0,0,1,0,'schedule:taskTrigger:recentSchedules',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-02-23 10:47:12',NULL),(1090,'用药时间点统计',NULL,'GET','/treatDrugDetail/timeStat',0,4,1,346,'icon',1,2,'TreatDrugDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatDrugDetail:timeStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-03-05 11:21:38',NULL),(1091,'用药日历',NULL,'GET','/treatDrug/calendar',0,3,1,679,'icon',1,0,'TreatDrug','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatDrug:calendar',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-03-05 17:53:24',NULL),(1092,'预算快照列表',NULL,'GET','/budgetSnapshot/getList',0,3,1,839,'icon',1,0,'BudgetSnapshot','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetSnapshot:list',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-03-07 17:11:51',NULL),(1093,'预算快照详情',NULL,'GET','/budgetSnapshot/getChildren',0,3,0,642,'fund',1,6,'BudgetSnapshot','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetSnapshot:children','fund/budgetSnapshot/children','budgetSnapshot/children',_binary '\0',_binary '',_binary '\0',_binary '\0',NULL,'2021-03-08 11:21:58','2021-03-09 10:07:32'),(1094,'预算快照历史',NULL,'GET','/budgetSnapshot/history',0,3,0,642,'fund',1,6,'BudgetSnapshot','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetSnapshot:history','fund/budgetSnapshot/history','budgetSnapshot/history',_binary '\0',_binary '',_binary '\0',_binary '\0',NULL,'2021-03-09 11:03:51',NULL),(1095,'交易记录',NULL,'GET','/budgetSnapshot/buyRecord',0,3,1,839,'icon',1,0,'BudgetSnapshot','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetSnapshot:buyRecord',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-03-09 14:04:35',NULL),(1096,'看病记录',NULL,'GET','/budgetSnapshot/treatRecord',0,3,1,839,'icon',1,0,'BudgetSnapshot','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:budgetSnapshot:treatRecord',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-03-09 14:05:20',NULL),(1097,'生活各项概要统计',NULL,'GET','/main/generalLifeStat',0,4,1,657,'icon',1,0,'Income','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'dashboard:generalLifeStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-03-09 16:50:00','2021-03-09 17:00:46'),(1098,'财务速览',NULL,'GET','/fund/mainStat',0,4,0,642,'fund',1,4,'Income','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'fund:finance:main','fund/finance/index','finance',_binary '',_binary '',_binary '\0',_binary '',NULL,'2021-03-09 17:09:59','2021-03-09 18:51:41'),(1099,'获取国家位置',NULL,'GET','/lifeExperienceDetail/getCountryLocation',0,3,1,760,'icon',1,0,'LifeExperienceDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-03-15 16:34:22',NULL),(1100,'获取城市地理位置',NULL,'GET','/lifeExperienceDetail/getCityLocation',0,3,1,760,'icon',1,0,'LifeExperienceDetail','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-03-15 18:49:42',NULL),(1101,'用户QA',NULL,'GET','/userQa/getData',0,3,0,806,'wordcloud',1,8,'UserQa','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:userQa:query','log/userQa/index','userQa',_binary '',_binary '',_binary '\0',_binary '',NULL,'2021-03-23 08:38:49','2021-03-23 10:08:11'),(1102,'获取详情',NULL,'GET','/userQa/get',0,3,1,1101,'icon',1,0,'UserQa','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:userQa:get',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-03-23 09:03:48',NULL),(1103,'QA词云',NULL,'GET','/userQa/statWordCloud',0,4,0,806,'guide',1,9,'UserQa','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:userQa:statWordCloud','log/userQa/statWordCloud','userQa/statWordCloud',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2021-03-23 10:02:48','2021-03-23 13:53:18'),(1104,'操作日志流水',NULL,'GET','/operationLog/flow',0,3,0,806,'log',1,2,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:operationLog:flow','log/operationLog/flow','operationLog/flow',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2021-03-30 19:29:28',NULL),(1105,'获取操作日志详情',NULL,'GET','/operationLog/get',0,3,1,807,'icon',1,0,'OperationLog','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'log:operationLog:get',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2021-03-30 20:34:02',NULL),(1106,'删除缓存',NULL,'POST','/notifyStat/deleteCache',0,4,1,64,'icon',1,0,'NotifyConfig','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'report:notify:userNotify:deleteCache',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2022-07-31 16:17:29','2022-07-31 16:19:02'),(1107,'锻炼总体统计',NULL,'GET','/sportExercise/overallStat',0,4,0,669,'chart',1,6,'SportExercise','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'sport:sportExercise:overallStat','sport/sportExercise/overallStat','sportExercise/overallStat',_binary '',_binary '',_binary '\0',_binary '','','2022-08-14 13:48:25','2022-08-14 14:16:36'),(1108,'看病总体统计',NULL,'GET','/treatRecord/overallStat',0,5,0,674,'yoy',1,5,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatRecord:overallStat','health/treat/treatRecord/overallStat','treatRecord/overallStat',_binary '',_binary '',_binary '\0',_binary '','','2022-08-14 18:04:48',NULL),(1109,'音乐练习总体统计',NULL,'GET','/musicPractice/overallStat',0,4,0,663,'yoy',1,9,'MusicPractice','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'music:musicPractice:overallStat','music/musicPractice/overallStat','musicPractice/overallStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2022-08-14 20:11:57',NULL),(1110,'阅读总体分析',NULL,'GET','/readingRecord/overallStat',0,5,0,778,'analyse',1,4,'ReadingRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'read:readingRecord:overallStat','read/readingRecord/overallStat','readingRecord/overallStat',_binary '',_binary '',_binary '\0',_binary '',NULL,'2022-08-20 09:31:14',NULL),(1111,'消费级联',NULL,'GET','/buyRecord/cascade',0,3,0,648,'buy',1,9,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,0,0,'consume:buyRecord:cascade',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-05-18 08:49:26','2023-05-18 08:50:58'),(1112,'设置上级商品关联',NULL,'POST','/buyRecord/setParent',0,1,1,1111,'icon',1,0,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:setParent',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-05-18 10:32:16','2023-05-18 12:01:02'),(1113,'取消上级商品关联',NULL,'POST','/buyRecord/deleteParent',0,1,1,1111,'icon',1,1,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:deleteParent',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-05-18 10:33:31','2023-05-18 12:01:12'),(1114,'取消下级商品关联',NULL,'POST','/buyRecord/deleteChildren',0,1,1,1111,'icon',1,2,'BuyRecord','pid',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:deleteChildren',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-05-18 12:00:13',NULL),(1115,'成本统计',NULL,'GET','/buyRecord/costStat',0,4,1,648,'icon',1,0,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:costStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-05-18 14:53:31','2023-05-20 22:47:03'),(1116,'商品树形统计',NULL,'GET','/buyRecord/treeStat',0,4,1,1111,'icon',1,5,'BuyRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'consume:buyRecord:treeStat',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-05-19 01:43:09',NULL),(1117,'商品寿命匹配及获取',NULL,'POST','/goodsLifetime/getAndMath',0,3,1,1062,'icon',1,2,'GoodsLifetime','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'config:goodsLifetime:getAndMath',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-05-19 10:54:11',NULL),(1118,'商品寿命比较匹配',NULL,'POST','/goodsLifetime/compareAndMath',0,3,1,1062,'icon',1,1,'GoodsLifetime','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'config:goodsLifetime:compareAndMath',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-05-19 12:08:16',NULL),(1119,'看病关系图',NULL,'GET','/treatRecord/relation',0,5,0,674,'chart',1,7,'TreatRecord','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'health:treat:treatRecord:relation','health/treat/treatRecord/relation','treatRecord/relation',_binary '',_binary '',_binary '\0',_binary '','','2023-06-09 21:29:06',NULL),(1120,'消费匹配日志分析',NULL,'GET','/buyRecordMatchLog/stat',0,3,0,806,'yoy',1,7,'BuyRecordMatchLog','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,'log:buyRecordMatchLog:stat','log/buyRecordMatchLog/stat','buyRecordMatchLog/stat',_binary '\0',_binary '',_binary '\0',_binary '',NULL,'2023-06-14 21:37:09',NULL),(1121,'AI管理',NULL,'GET','ai',0,0,3,0,'lock',1,22,'ModuleConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,'ai','ai',_binary '',_binary '',_binary '\0',_binary '',NULL,'2023-06-25 09:52:22','2023-06-25 10:01:35'),(1122,'模型配置管理',NULL,'GET','/modelConfig/getData',0,3,0,1121,'lock',1,0,'ModelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'ai:modelConfig:query','ai/modelConfig/index','modelConfig',_binary '',_binary '',_binary '\0',_binary '',NULL,'2023-06-25 09:54:15','2023-07-03 17:35:35'),(1123,'新增模型配置',NULL,'POST','/modelConfig/create',0,0,1,1122,'icon',1,0,'ModelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'ai:modelConfig:create',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-06-25 09:55:27','2023-07-03 17:35:54'),(1124,'修改模型配置',NULL,'POST','/modelConfig/edit',0,1,1,1122,'icon',1,1,'ModelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'ai:modelConfig:edit',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-06-25 09:55:59','2023-07-03 17:36:09'),(1125,'获取模型配置',NULL,'POST','/modelConfig/get',0,3,1,1122,'icon',1,2,'ModelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'ai:modelConfig:get',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-06-25 09:56:24','2023-07-03 17:36:19'),(1126,'发布模型配置',NULL,'POST','/modelConfig/publish',0,0,1,1122,'icon',1,3,'ModelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'ai:modelConfig:publish',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-06-25 09:56:51','2023-07-03 17:36:33'),(1127,'刷新模型配置',NULL,'POST','/modelConfig/refresh',0,1,1,1122,'icon',1,4,'ModelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'ai:modelConfig:refresh',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-06-25 09:57:13','2023-07-03 17:36:43'),(1128,'撤销模型配置',NULL,'POST','/modelConfig/revoke',0,1,1,1122,'icon',1,4,'ModelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'ai:modelConfig:revoke',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-06-25 09:57:33','2023-07-03 17:36:59'),(1129,'删除模型配置',NULL,'POST','/modelConfig/delete',0,2,1,1122,'icon',1,5,'ModelConfig','id',0,_binary '',_binary '\0',_binary '',1,1,0,0,0,5,0,_binary '\0',0,0,1,0,'ai:modelConfig:delete',NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-06-25 09:57:53','2023-07-03 17:37:09'),(1130,'国家列表',NULL,'GET','/country/getAll',0,3,1,785,NULL,1,0,'City','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-07-14 19:31:55',NULL),(1131,'国家树列表',NULL,'GET','/country/getCountryTree',0,3,1,785,NULL,1,0,'City','id',0,_binary '',_binary '\0',_binary '',1,0,0,0,0,5,0,_binary '\0',0,0,1,0,NULL,NULL,NULL,_binary '\0',_binary '\0',_binary '\0',_binary '',NULL,'2023-07-14 19:59:22',NULL);
/*!40000 ALTER TABLE `system_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_log`
--

DROP TABLE IF EXISTS `system_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `username` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `error_code` int NOT NULL DEFAULT '0',
  `log_level` smallint NOT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `system_function_id` bigint DEFAULT NULL,
  `url_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `method` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `occur_time` datetime NOT NULL,
  `store_time` datetime NOT NULL,
  `store_duration` int NOT NULL DEFAULT '0',
  `ip_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `location_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mac_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `host_ip_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `paras` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_value` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `exception_class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_log`
--

LOCK TABLES `system_log` WRITE;
/*!40000 ALTER TABLE `system_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_monitor_user`
--

DROP TABLE IF EXISTS `system_monitor_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_monitor_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `buss_type` smallint NOT NULL,
  `sms_notify` tinyint NOT NULL,
  `wx_notify` tinyint NOT NULL,
  `sys_msg_notify` tinyint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_monitor_user`
--

LOCK TABLES `system_monitor_user` WRITE;
/*!40000 ALTER TABLE `system_monitor_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_monitor_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_log`
--

DROP TABLE IF EXISTS `task_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `task_trigger_id` bigint NOT NULL,
  `buss_date` date NOT NULL,
  `schedule_identity_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `cost_time` bigint NOT NULL,
  `execute_result` smallint NOT NULL,
  `sub_task_execute_results` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deploy_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ip_address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `redo_times` int DEFAULT NULL,
  `last_start_time` datetime DEFAULT NULL,
  `last_end_time` datetime DEFAULT NULL,
  `log_comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_log`
--

LOCK TABLES `task_log` WRITE;
/*!40000 ALTER TABLE `task_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_server`
--

DROP TABLE IF EXISTS `task_server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_server` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `deploy_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ip_address` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `support_distri` tinyint DEFAULT NULL,
  `cejc` int DEFAULT NULL,
  `sjc` int DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `shutdown_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_server`
--

LOCK TABLES `task_server` WRITE;
/*!40000 ALTER TABLE `task_server` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_trigger`
--

DROP TABLE IF EXISTS `task_trigger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_trigger` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint NOT NULL,
  `deploy_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `task_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `distriable` tinyint NOT NULL,
  `redo_type` smallint NOT NULL,
  `sub_task_codes` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sub_task_names` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `allowed_redo_times` int NOT NULL,
  `timeout` bigint NOT NULL,
  `group_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_type` smallint NOT NULL,
  `trigger_interval` int NOT NULL,
  `trigger_paras` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cron_expression` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `offset_days` int NOT NULL,
  `first_execute_time` datetime NOT NULL,
  `next_execute_time` datetime DEFAULT NULL,
  `trigger_status` smallint NOT NULL,
  `last_execute_result` smallint DEFAULT NULL,
  `last_execute_time` datetime DEFAULT NULL,
  `total_count` bigint NOT NULL,
  `fail_count` bigint NOT NULL,
  `check_unique` tinyint NOT NULL,
  `unique_type` int NOT NULL DEFAULT '0',
  `loggable` tinyint NOT NULL,
  `notifiable` tinyint NOT NULL,
  `exec_time_periods` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  `comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `version` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_trigger`
--

LOCK TABLES `task_trigger` WRITE;
/*!40000 ALTER TABLE `task_trigger` DISABLE KEYS */;
INSERT INTO `task_trigger` VALUES (1,'测试调度',1,'lh-01','cn.mulanbay.schedule.job.TestJob',1,1,'','',5,60,'test',1,10,NULL,'',0,'2017-11-19 10:23:16','2018-01-05 22:09:06',0,2,'2019-12-23 14:07:51',313,0,1,0,1,0,'{\"times\":\"\",\"weeks\":[\"1\",\"2\"]}','2017-11-19 10:23:57','2019-12-23 13:33:00','',9),(11,'月度计划检查调度',1,'smp01','cn.mulanbay.pms.handler.job.UserPlanRemindJob',1,1,NULL,NULL,5,60,'userPlan',4,1,'{\"planType\":\"MONTH\"}',NULL,-1,'2017-11-26 08:00:02','2023-05-11 08:00:02',1,0,'2023-05-10 08:00:02',1993,0,1,1,1,0,NULL,'2017-11-25 10:35:15','2019-02-28 21:30:06',NULL,1436),(12,'数据库清理任务',1,'smp01','cn.mulanbay.pms.handler.job.DatabaseCleanJob',1,1,NULL,NULL,5,600,'database',4,1,NULL,NULL,0,'2017-11-26 02:00:00','2023-05-11 02:00:00',1,0,'2023-05-10 02:00:00',1450,0,0,1,1,0,NULL,'2017-11-25 21:51:30','2020-10-17 22:34:08',NULL,1442),(13,'发送提醒消息（基于数据库）',1,'smp01','cn.mulanbay.pms.handler.job.SendDatabaseUserMessageJob',1,1,NULL,NULL,5,600,'message',2,1,'{\"maxRows\":\"1000\",\"compareMinutes\":\"0\"}',NULL,0,'2017-11-26 00:02:00','2019-07-16 22:29:00',0,2,'2019-07-16 22:28:00',60963,0,0,0,0,0,'','2017-11-26 00:01:59','2019-10-11 08:35:05',NULL,60991),(14,'定时备份数据库',1,'smp01','cn.mulanbay.pms.handler.job.PmsCommandJob',1,1,NULL,NULL,5,600,'backup',4,1,'{\"cmd\":\"\",\"osType\":\"LINUX\",\"asyn\":\"false\",\"code\":\"BACKUP_DB\"}',NULL,0,'2017-11-27 03:30:46','2023-05-11 03:30:46',1,0,'2023-05-10 03:30:48',1456,5,1,0,1,0,NULL,'2017-11-26 11:04:01','2020-06-07 10:32:40',NULL,1445),(15,'定时备份整个木兰湾项目',1,'smp01','cn.mulanbay.pms.handler.job.PmsCommandJob',1,1,NULL,NULL,5,600,'backup',4,7,'{\"cmd\":\"\",\"osType\":\"LINUX\",\"asyn\":\"false\",\"code\":\"BACKUP_MULANBAY\"}',NULL,0,'2017-11-27 04:00:00','2023-05-15 04:00:00',1,0,'2023-05-08 04:01:21',293,0,1,0,1,0,NULL,'2017-11-26 12:19:04','2020-06-07 10:38:46',NULL,208),(16,'同步备份数据到FTP服务器',1,'smp01','cn.mulanbay.pms.handler.job.FtpSyncToRemoteJob',1,1,NULL,NULL,5,600,'backup',4,1,'{\"serverHost\":\"192.168.123.105\",\"username\":\"software\",\"password\":\"fenghong007\",\"port\":\"21\",\"remotePath\":\"mulanbay\",\"localPath\":\"/mulanbay/server/serverUpdate/mulanbaybackup\"}',NULL,0,'2017-11-27 05:00:00','2020-03-28 05:00:00',0,1,'2020-03-27 05:00:35',310,310,0,0,1,1,NULL,'2017-11-26 15:47:08','2020-03-27 13:30:11',NULL,299),(17,'年度计划检查调度',1,'smp01','cn.mulanbay.pms.handler.job.UserPlanRemindJob',1,1,NULL,NULL,5,600,'userPlan',6,1,'{\"planType\":\"YEAR\"}',NULL,-1,'2017-12-01 03:00:00','2023-06-01 03:00:00',1,0,'2023-05-01 03:00:00',68,0,1,1,1,1,NULL,'2017-11-26 17:32:37','2019-02-28 21:23:59',NULL,47),(18,'计划报告时间线统计任务',1,'smp01','cn.mulanbay.pms.handler.job.PlanReportTimelineStatJob',1,1,NULL,NULL,5,600,'userPlan',4,1,NULL,NULL,-1,'2017-11-28 00:30:00','2023-05-11 00:30:00',1,0,'2023-05-10 00:30:00',1452,8,1,1,1,1,NULL,'2017-11-27 21:53:30','2019-03-09 13:57:55',NULL,1440),(19,'月度计划统计任务',1,'smp01','cn.mulanbay.pms.handler.job.PlanReportStatJob',1,1,NULL,NULL,5,600,'userPlan',6,1,'{\"planType\":\"MONTH\"}',NULL,-1,'2017-12-01 01:00:00','2023-06-01 01:00:00',1,0,'2023-05-01 01:00:01',69,1,1,1,1,0,NULL,'2017-11-27 21:55:08','2019-02-28 21:23:25',NULL,48),(20,'年度计划统计任务',1,'smp01','cn.mulanbay.pms.handler.job.PlanReportStatJob',1,1,NULL,NULL,5,600,'userPlan',8,1,'{\"planType\":\"YEAR\"}',NULL,-1,'2018-01-01 01:40:00','2024-01-01 01:40:00',1,0,'2023-01-01 01:40:01',7,1,1,1,1,0,NULL,'2017-11-27 21:56:37','2019-02-28 21:30:55',NULL,5),(21,'定时更新梦想进度',0,'smp01','cn.mulanbay.pms.handler.job.RefreshDreamRateJob',1,1,NULL,NULL,5,60,'dream',4,1,NULL,NULL,-1,'2018-01-02 05:00:00','2023-05-11 05:00:00',1,0,'2023-05-10 05:00:00',1955,0,1,1,1,0,NULL,'2018-01-01 20:59:28','2018-01-05 22:09:01',NULL,1436),(22,'用户提醒列表的提醒统计',1,'smp01','cn.mulanbay.pms.handler.job.UserNotifyRemindJob',1,1,'','',5,60,'userPlan',4,1,'{\"cacheResult\":\"true\",\"expireSeconds\":\"86400\"}','',-1,'2018-01-02 06:00:00','2023-05-11 06:00:00',1,0,'2023-05-10 06:00:01',1960,0,0,1,1,0,NULL,'2018-01-01 21:00:45','2019-12-28 08:00:40','',1438),(23,'用药提醒任务',1,'smp01','cn.mulanbay.pms.handler.job.TreadDrugRemindJob',1,1,NULL,NULL,5,60,'health',4,1,'{\"remindTime\":\"08:30\"}',NULL,-1,'2018-01-12 21:15:12','2023-05-11 04:00:00',1,2,'2023-05-10 04:00:00',1449,0,1,0,1,0,NULL,'2018-01-12 21:12:24','2019-02-28 21:24:51',NULL,1436),(24,'统计用户每天的积分总奖励',1,'smp01','cn.mulanbay.pms.handler.job.DailyRewardPointsStatJob',1,1,NULL,NULL,5,60,'user',4,1,'{\"remindTime\":\"08:30\"}',NULL,-1,'2018-01-23 23:06:23','2023-05-11 05:30:00',1,0,'2023-05-10 05:30:00',1925,10,0,0,1,0,NULL,'2018-01-23 23:04:32','2019-02-28 21:26:44',NULL,1437),(25,'备份文件夹清理',1,'smp01','cn.mulanbay.schedule.job.FileClearJob',1,1,NULL,NULL,5,60,'file',4,1,'{\"sourcePath\":\"/mulanbay/server/serverUpdate/mulanbaybackup\",\"backupPath\":\"/mulanbay/server/serverUpdate/mulanbaybackup\",\"backup\":\"false\",\"zip\":\"false\",\"backupDateFormat\":\"yyyy-MM-dd\",\"keepDays\":\"7\"}',NULL,-1,'2018-03-11 06:00:00','2023-05-11 06:00:00',1,0,'2023-05-10 06:00:00',1888,1,0,0,1,0,NULL,'2018-03-10 16:17:37','2019-02-28 21:29:40',NULL,1437),(26,'用户日历统计发送',1,'smp01','cn.mulanbay.pms.handler.job.UserCalendarRemindJob',1,1,NULL,NULL,5,60,'message',4,1,NULL,NULL,0,'2018-03-14 08:00:00','2023-05-11 08:00:00',1,0,'2023-05-10 08:00:00',1887,2,0,0,1,0,NULL,'2018-03-13 21:27:24','2018-03-13 21:35:48',NULL,1436),(27,'设置过期的用户日历为超时',1,'smp01','cn.mulanbay.pms.handler.job.UserCalendarTimeoutJob',1,1,NULL,NULL,5,60,'remind',4,1,NULL,NULL,-1,'2018-09-22 02:40:00','2023-05-11 02:40:00',1,0,'2023-05-10 02:40:00',1449,0,0,0,1,0,NULL,'2018-09-21 09:54:54','2018-09-21 10:52:31',NULL,1437),(28,'梦想进度提醒检查任务',1,'smp01','cn.mulanbay.pms.handler.job.DreamRemindJob',1,1,NULL,NULL,5,600,'remind',4,1,NULL,NULL,0,'2018-11-18 00:30:00','2023-05-11 00:30:00',1,0,'2023-05-10 00:30:00',1449,0,1,0,1,0,NULL,'2018-11-17 11:49:57','2018-11-17 11:56:22',NULL,1437),(29,'账户快照生成',1,'smp01','cn.mulanbay.pms.handler.job.AccountSnapshotJob',1,1,NULL,NULL,5,60,'fund',6,1,NULL,NULL,-5,'2019-02-03 03:00:00',NULL,0,0,'2019-01-31 09:45:55',2,1,1,0,1,0,NULL,'2019-01-31 09:44:12','2019-02-02 20:59:29',NULL,0),(30,'股票价格监控',1,'smp01','cn.mulanbay.pms.handler.job.SharesMonitorJob',1,1,NULL,NULL,5,60,'fund',3,2,'{\"failPercent\":5.0,\"failCounts\":5,\"gainPercent\":5.0,\"gainCounts\":5,\"cacheHours\":24,\"notifyScoreWarn\":false,\"minWarnScore\":0,\"maxWarnScore\":100,\"sharesLastPriceCacheHours\":48,\"indexPointRange\":150,\"minTurnOver\":2.0,\"maxTurnOver\":10.0,\"resetGf\":false,\"notify\":true}',NULL,0,'2019-02-24 14:00:00','2019-06-26 15:30:00',0,0,'2019-06-26 13:30:02',422,0,0,0,0,1,'{\"times\":\"09:00-16:00\",\"weeks\":[\"1\",\"2\",\"3\",\"4\",\"5\"]}','2019-02-24 13:49:14','2019-12-21 11:47:01',NULL,280),(31,'股票统计',1,'smp01','cn.mulanbay.pms.handler.job.SharesStatJob',1,1,NULL,NULL,5,60,'fund',4,1,NULL,NULL,0,'2019-02-25 09:30:00','2019-03-18 09:30:00',0,0,'2019-03-17 09:30:01',36,0,0,0,1,0,NULL,'2019-02-24 22:43:11','2019-03-17 20:52:17',NULL,0),(32,'月度预算与实际消费统计',1,'smp01','cn.mulanbay.pms.handler.job.BudgetExecStatJob',1,1,NULL,NULL,5,60,'fund',6,1,'{\"period\":\"MONTHLY\",\"overBudgetScore\":\"-50\",\"lessBudgetScore\":\"50\"}',NULL,-1,'2019-04-01 04:00:00','2023-06-01 04:00:00',1,0,'2023-05-01 04:00:01',81,0,1,1,1,1,NULL,'2019-03-30 19:11:29','2019-03-31 14:37:43',NULL,52),(33,'预算提醒',1,'smp01','cn.mulanbay.pms.handler.job.BudgetRemindJob',1,1,NULL,NULL,5,60,'fund',4,5,'{\"score\":\"-10\",\"minDays\":\"5\"}',NULL,0,'2019-04-10 05:00:36','2023-05-14 05:00:36',1,0,'2023-05-09 05:00:36',301,0,0,0,1,1,NULL,'2019-04-09 19:13:57','2019-05-21 22:03:38',NULL,288),(34,'年度预算与实际消费统计',1,'smp01','cn.mulanbay.pms.handler.job.BudgetExecStatJob',1,1,NULL,NULL,5,60,'fund',8,1,'{\"period\":\"YEARLY\",\"overBudgetScore\":\"-50\",\"lessBudgetScore\":\"50\"}',NULL,-1,'2020-01-01 05:30:00','2024-01-01 05:30:00',1,0,'2023-01-01 05:30:00',10,0,1,0,1,1,NULL,'2019-04-24 21:47:31','2019-04-25 18:59:05',NULL,10),(35,'月度预算进度检查',1,'smp01','cn.mulanbay.pms.handler.job.BudgetCheckJob',1,3,NULL,NULL,5,60,'fund',4,1,'{\"period\":\"MONTHLY\",\"floatRate\":\"10\",\"overBudgetScore\":\"-5\",\"lessBudgetScore\":\"5\",\"checkFromRate\":\"50\"}',NULL,-1,'2019-04-25 05:00:00','2023-05-11 05:00:00',1,0,'2023-05-10 05:00:00',1494,1,1,0,1,1,NULL,'2019-04-24 21:49:48','2019-08-22 14:25:44',NULL,1438),(36,'年度预算进度检查',1,'smp01','cn.mulanbay.pms.handler.job.BudgetCheckJob',1,3,NULL,NULL,5,60,'fund',4,1,'{\"period\":\"YEARLY\",\"floatRate\":\"5\",\"overBudgetScore\":\"-1\",\"lessBudgetScore\":\"1\",\"checkFromRate\":\"70\"}',NULL,-1,'2019-04-25 06:00:00','2023-05-11 06:00:00',1,0,'2023-05-10 06:00:00',1488,1,1,0,1,1,NULL,'2019-04-24 22:27:54','2019-04-25 09:09:38',NULL,1436),(37,'手术复查提醒',0,'smp01','cn.mulanbay.pms.handler.job.TreadOperationRemindJob',1,1,NULL,NULL,5,60,'health',4,1,'{\"remindTime\":\"08:30\",\"checkDays\":\"7\",\"verifyDays\":\"7\"}',NULL,-1,'2019-05-11 02:45:00','2023-05-11 02:45:00',1,2,'2023-05-10 02:45:00',1449,0,0,0,1,1,NULL,'2019-05-10 17:33:23',NULL,NULL,1437),(38,'用户评分统计',0,'smp01','cn.mulanbay.pms.handler.job.UserScoreStatJob',1,1,NULL,NULL,5,60,'user',4,1,NULL,NULL,-1,'2019-09-10 01:40:00','2023-05-11 01:40:00',1,0,'2023-05-10 01:40:00',1365,10,1,0,1,0,NULL,'2019-09-09 17:52:28',NULL,NULL,1380),(39,'发送提醒消息（基于Redis）',1,'smp01','cn.mulanbay.pms.handler.job.SendRedisDelayMessageJob',1,1,'','',5,60,'message',1,10,'{\"maxFails\":\"3\",\"delaySeconds\":\"60\"}','',0,'2019-10-10 22:11:30','2023-05-10 21:14:30',1,2,'2023-05-10 21:14:20',11083419,1,1,0,0,0,NULL,'2019-10-10 22:11:17','2019-12-25 20:14:41','',11078746),(40,'网络监测',1,'smp01','cn.mulanbay.pms.handler.job.NetworkCheckJob',1,1,NULL,NULL,5,60,'message',2,5,'{\"url\":\"https://www.baidu.com\"}',NULL,0,'2019-12-12 18:50:00','2020-09-01 19:25:00',0,2,'2020-09-01 19:20:05',75257,0,0,0,0,0,'{\"times\":\"08:00-22:00\"}','2019-12-12 18:25:37','2020-09-01 19:22:54',NULL,75101),(41,'系统监控',1,'smp01','cn.mulanbay.pms.handler.job.SystemMonitorJob',1,1,NULL,NULL,5,60,'remind',3,1,'{\"diskMaxRate\":\"0.8\",\"memoryMaxRate\":\"0.8\",\"cpuMaxRate\":\"0.8\",\"queueSize\":\"100\",\"days\":\"7\",\"diskAlertSelfJobs\":\"12,25\",\"memoryAlertSelfJobs\":\"42\",\"cpuAlertSelfJobs\":\"\",\"autoDoJob\":\"true\"}',NULL,0,'2020-01-15 14:36:00','2023-05-10 22:00:00',1,2,'2023-05-10 21:00:01',28631,0,0,0,0,0,NULL,'2020-01-15 14:35:48','2020-09-02 18:29:33',NULL,28645),(42,'定时释放系统缓存',1,'smp01','cn.mulanbay.pms.handler.job.PmsCommandJob',1,1,NULL,NULL,5,60,'system',4,1,'{\"cmd\":\"\",\"osType\":\"UNKNOWN\",\"asyn\":\"false\",\"code\":\"BUFF_CACHE\"}',NULL,0,'2020-06-09 06:00:00','2023-05-11 06:00:00',1,0,'2023-05-10 06:00:00',1116,0,0,0,1,0,NULL,'2020-06-08 12:59:37','2020-06-08 13:04:24','操作系统内存经常达到90%以上，不知为什么一直无法释放',1208),(43,'饮食多样性统计',1,'smp01','cn.mulanbay.pms.handler.job.DietVarietyStatJob',1,1,NULL,NULL,5,60,'diet',4,7,'{\"days\":\"30\",\"orderByField\":\"occur_time\"}',NULL,0,'2020-07-06 07:00:00','2023-05-16 05:20:00',1,0,'2023-05-09 05:20:00',278,1,1,0,1,0,NULL,'2020-07-04 09:25:37','2020-08-31 09:07:32',NULL,282),(44,'自动重做失败的调度日志',0,'smp01','cn.mulanbay.schedule.job.AutoRedoJob',1,0,NULL,NULL,5,60,'backup',4,1,'{\"startDays\":7,\"endDays\":1}',NULL,0,'2020-09-05 07:00:00','2023-05-11 07:00:00',1,0,'2023-05-10 07:00:00',978,0,1,0,1,0,NULL,'2020-09-04 22:14:32',NULL,NULL,978);
/*!40000 ALTER TABLE `task_trigger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treat_check_standard`
--

DROP TABLE IF EXISTS `treat_check_standard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treat_check_standard` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `lower_limit` decimal(9,2) DEFAULT NULL,
  `upper_limit` decimal(9,2) DEFAULT NULL,
  `unit` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `year` int DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`name`,`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treat_check_standard`
--

LOCK TABLES `treat_check_standard` WRITE;
/*!40000 ALTER TABLE `treat_check_standard` DISABLE KEYS */;
/*!40000 ALTER TABLE `treat_check_standard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treat_drug`
--

DROP TABLE IF EXISTS `treat_drug`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treat_drug` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `treat_record_id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '药名称',
  `unit` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单位',
  `amount` int NOT NULL COMMENT '数量',
  `disease` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '治疗疾病',
  `unit_price` decimal(9,2) NOT NULL COMMENT '单价',
  `total_price` decimal(9,2) NOT NULL COMMENT '总价',
  `per_day` int NOT NULL DEFAULT '1',
  `per_times` int NOT NULL DEFAULT '1',
  `eu` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ec` int DEFAULT NULL,
  `use_way` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `treat_date` date NOT NULL,
  `begin_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `available` bit(1) NOT NULL DEFAULT b'1',
  `remind` tinyint NOT NULL DEFAULT '1',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `FK_h13ygwsedoyd7oe9owa4u0nct` (`treat_record_id`),
  CONSTRAINT `FK_h13ygwsedoyd7oe9owa4u0nct` FOREIGN KEY (`treat_record_id`) REFERENCES `treat_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='看病开药表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treat_drug`
--

LOCK TABLES `treat_drug` WRITE;
/*!40000 ALTER TABLE `treat_drug` DISABLE KEYS */;
/*!40000 ALTER TABLE `treat_drug` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treat_drug_detail`
--

DROP TABLE IF EXISTS `treat_drug_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treat_drug_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `treat_drug_id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `occur_time` datetime NOT NULL,
  `ec` decimal(5,1) DEFAULT NULL,
  `eu` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用药详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treat_drug_detail`
--

LOCK TABLES `treat_drug_detail` WRITE;
/*!40000 ALTER TABLE `treat_drug_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `treat_drug_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treat_operation`
--

DROP TABLE IF EXISTS `treat_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treat_operation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `treat_record_id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手术名',
  `category` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `treat_date` date NOT NULL,
  `review_date` date DEFAULT NULL,
  `fee` decimal(9,2) NOT NULL COMMENT '手术费用',
  `available` bit(1) NOT NULL,
  `is_sick` bit(1) NOT NULL DEFAULT b'0',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `FK_c2k7741waiya3f10forpyfsv0` (`treat_record_id`),
  CONSTRAINT `FK_c2k7741waiya3f10forpyfsv0` FOREIGN KEY (`treat_record_id`) REFERENCES `treat_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='看病手术表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treat_operation`
--

LOCK TABLES `treat_operation` WRITE;
/*!40000 ALTER TABLE `treat_operation` DISABLE KEYS */;
/*!40000 ALTER TABLE `treat_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treat_record`
--

DROP TABLE IF EXISTS `treat_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treat_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `treat_type` smallint NOT NULL DEFAULT '0',
  `hospital` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '医院',
  `department` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '挂号科室',
  `organ` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `disease` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '疾病',
  `pain_level` int NOT NULL COMMENT '疼痛等级',
  `important_level` decimal(5,1) NOT NULL COMMENT '重要等级',
  `diagnosed_disease` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '确诊疾病',
  `is_sick` smallint NOT NULL COMMENT '是否有病',
  `treat_date` datetime NOT NULL COMMENT '看病日期',
  `os_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `doctor` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `registered_fee` decimal(9,2) NOT NULL COMMENT '挂号费',
  `drug_fee` decimal(9,2) NOT NULL COMMENT '药费',
  `operation_fee` decimal(9,2) NOT NULL COMMENT '手术费',
  `total_fee` decimal(9,2) NOT NULL COMMENT '总费用',
  `medical_insurance_paid_fee` decimal(9,2) NOT NULL COMMENT '医保担负费用',
  `personal_paid_fee` decimal(9,2) NOT NULL COMMENT '个人支付费用',
  `tags` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `stage` smallint DEFAULT NULL,
  `remark` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='看病记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treat_record`
--

LOCK TABLES `treat_record` WRITE;
/*!40000 ALTER TABLE `treat_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `treat_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treat_test`
--

DROP TABLE IF EXISTS `treat_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treat_test` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `treat_operation_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `unit` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `value` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `min_value` decimal(9,3) DEFAULT NULL,
  `max_value` decimal(9,3) DEFAULT NULL,
  `refer_scope` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `test_date` datetime NOT NULL,
  `result` int DEFAULT NULL,
  `type_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treat_test`
--

LOCK TABLES `treat_test` WRITE;
/*!40000 ALTER TABLE `treat_test` DISABLE KEYS */;
/*!40000 ALTER TABLE `treat_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `level` int NOT NULL DEFAULT '3',
  `points` int NOT NULL DEFAULT '0',
  `sec_auth_type` smallint DEFAULT '0',
  `last_login_token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `last_family_mode` smallint DEFAULT '0',
  `last_login_time` datetime DEFAULT NULL,
  `last_login_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` smallint DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (0,'admin','管理员','1D5CD27E237FF81C8C7601BA479C33F2',NULL,NULL,'2000-01-01',3,0,0,NULL,NULL,'2023-05-10 21:48:23','127.0.0.1',0,'2049-09-02 19:00:00',NULL,NULL,'2002-08-06 22:01:26','2023-05-10 21:55:05'),(1,'mulanbay','木兰湾','1D5CD27E237FF81C8C7601BA479C33F2','13712345678','123456@qq.com','2000-01-01',3,0,0,'cf32cef1-2e0f-422f-9ebe-8f63fd5d3845',NULL,'2023-05-10 21:57:23','127.0.0.1',0,'2099-09-02 19:00:00','/20210319/a1b72cbc-5643-4471-b189-76a430656de6.jpeg','','2002-08-06 22:01:26','2023-05-10 21:56:21');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_behavior`
--

DROP TABLE IF EXISTS `user_behavior`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_behavior` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_behavior_config_id` bigint NOT NULL,
  `bind_values` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` smallint NOT NULL,
  `month_stat` tinyint NOT NULL DEFAULT '1',
  `day_stat` tinyint NOT NULL DEFAULT '1',
  `hour_stat` tinyint NOT NULL DEFAULT '1',
  `order_index` smallint NOT NULL,
  `unit` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_behavior`
--

LOCK TABLES `user_behavior` WRITE;
/*!40000 ALTER TABLE `user_behavior` DISABLE KEYS */;
INSERT INTO `user_behavior` VALUES (35,0,'音乐练习统计',1,NULL,1,1,1,1,1,'小时','','2020-02-11 18:55:48','2020-10-13 09:26:39'),(36,0,'运动锻炼统计',2,NULL,1,1,1,1,2,'小时',NULL,'2020-02-11 18:55:48','2020-10-13 09:26:45'),(37,0,'看病统计',4,NULL,1,1,1,1,4,'次',NULL,'2020-02-11 18:55:48','2020-10-13 09:26:52'),(38,0,'身体不适统计',5,NULL,1,1,1,1,5,'次',NULL,'2020-02-11 18:55:48','2020-10-13 09:26:56'),(39,0,'消费统计',6,NULL,1,1,1,1,6,'元',NULL,'2020-02-11 18:55:48','2020-10-13 09:27:00'),(40,0,'看书统计',7,NULL,1,1,1,1,7,'本',NULL,'2020-02-11 18:55:48','2020-10-13 09:27:05'),(41,0,'加班统计',8,NULL,1,1,1,1,8,'小时',NULL,'2020-02-11 18:55:48','2020-10-13 09:27:09'),(42,0,'出差统计',9,NULL,1,1,1,1,9,'小时',NULL,'2020-02-11 18:55:48','2020-10-13 09:27:14'),(43,0,'回家统计',13,NULL,1,1,1,1,10,'次',NULL,'2020-02-11 18:55:48','2020-10-13 09:27:19'),(44,0,'用药统计',14,NULL,1,1,1,1,11,'次',NULL,'2020-02-11 18:55:48','2020-10-13 09:27:25'),(45,0,'通用数据统计',16,NULL,1,1,1,1,12,'次',NULL,'2020-02-11 18:55:48','2020-10-13 09:27:36'),(46,0,'操作日志统计',12,NULL,1,0,0,0,17,'次',NULL,'2020-02-11 18:55:48','2020-10-13 09:27:41'),(47,0,'饮食餐次',17,NULL,1,0,1,0,17,'次',NULL,'2020-02-11 18:55:48','2020-10-13 09:27:45'),(48,0,'睡觉',18,NULL,1,0,1,0,18,'次',NULL,'2020-02-11 18:55:48','2020-10-13 09:28:36'),(49,0,'起床',19,NULL,1,0,1,0,19,'次',NULL,'2020-02-11 18:55:48','2020-10-13 09:28:39');
/*!40000 ALTER TABLE `user_behavior` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_behavior_config`
--

DROP TABLE IF EXISTS `user_behavior_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_behavior_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `behavior_type` smallint NOT NULL,
  `sql_type` smallint NOT NULL,
  `sql_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `date_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `unit` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `keywords` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `keywords_search_sql` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `date_region` bit(1) NOT NULL DEFAULT b'0',
  `status` smallint NOT NULL,
  `month_stat` bit(1) DEFAULT b'1',
  `level` int NOT NULL DEFAULT '3',
  `order_index` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_behavior_config`
--

LOCK TABLES `user_behavior_config` WRITE;
/*!40000 ALTER TABLE `user_behavior_config` DISABLE KEYS */;
INSERT INTO `user_behavior_config` VALUES (1,'音乐练习统计','音乐练习',0,0,'select mp.practice_start_time as date,mi.name,mp.minutes as value from music_practice mp,music_instrument mi  where mp.music_instrument_id = mi.id \r\n','mp.practice_start_time','mp.user_id','分钟','mi.name','select name from music_instrument order by order_index',_binary '\0',1,_binary '',3,1,'','2017-09-04 20:17:04','2019-12-23 09:39:14'),(2,'运动锻炼统计','运动锻炼',1,0,'select mp.exercise_date as date,mi.name,mp.minutes as value from sport_exercise mp,sport_type mi \r\nwhere mp.sport_type_id =mi.id\r\n\r\n','mp.exercise_date','mp.user_id','分钟','mi.name','select name from sport_type order by order_index',_binary '\0',1,NULL,3,1,NULL,'2017-09-04 20:30:36','2019-04-04 08:03:34'),(3,'某项人生经历统计','某项人生经历',5,0,'select start_date as date,\r\n    case \r\n     when type=0 then \'\'生活\'\'\r\n     when type=1 then \'\'工作\'\'\r\n     when type=2 then \'\'旅行\'\'\r\n     when type=3 then \'\'读书\'\'\r\n     else \'\'其他\'\' END\r\n	as name,\r\n    days as value from life_experience where type={0}\r\n','start_date','user_id','次','name',NULL,_binary '',1,NULL,3,6,NULL,'2017-09-04 20:37:14','2020-06-10 14:25:56'),(4,'看病统计','看病',3,0,'select treat_date as date,\'看病\' as name,1 as value from treat_record\r\n','treat_date','user_id','次','hospital,department,organ,disease,diagnosed_disease','select distinct disease from treat_record ',_binary '\0',1,_binary '',3,1,NULL,'2017-09-04 20:39:31','2019-04-04 08:04:06'),(5,'身体不适统计','身体不适',3,0,'select occur_date as date,disease as name,last_days as value from body_abnormal_record\r\n','occur_date','user_id','次','organ,disease','select distinct disease from body_abnormal_record',_binary '',1,_binary '',3,1,NULL,'2017-09-04 20:56:04','2019-04-04 08:04:18'),(6,'消费统计','消费',2,0,'select consume_date as date,\'消费\' as name,1 as value from buy_record\r\n','consume_date','user_id','次','goods_name,keywords,shop_name,remark',NULL,_binary '\0',1,_binary '\0',3,1,NULL,'2017-09-04 20:59:33','2019-04-04 08:08:13'),(7,'看书统计','看书',4,0,'select read_time as date,\'看书\' as name,\r\nminutes as value from reading_record_detail\r\n','read_time','user_id','分钟',NULL,NULL,_binary '\0',1,_binary '',3,1,NULL,'2017-09-05 11:48:16','2019-04-04 08:04:43'),(8,'加班统计','加班',7,0,'select work_date as date,\'加班\' as name,round(hours) as value from work_overtime\r\n','work_date','user_id','小时',NULL,NULL,_binary '\0',1,_binary '',3,1,NULL,'2017-09-05 12:18:35','2019-04-04 08:04:52'),(9,'出差统计','出差',7,0,'select trip_date as date,\'出差\' as name,days as value from business_trip\r\n','trip_date','user_id','天','country,province,city',NULL,_binary '',1,_binary '',3,1,NULL,'2017-09-05 12:20:00','2019-04-04 08:08:30'),(10,'某项消费记录统计(一层分类)','某项消费记录(一层分类)',2,0,'select consume_date as date,gt.behavior_name as name,1 as value \r\nfrom buy_record br,goods_type gt where br.goods_type_id=gt.id and br.goods_type_id={0}\r\n','br.consume_date','br.user_id','次',NULL,NULL,_binary '\0',1,NULL,3,7,NULL,'2017-09-06 16:06:24','2019-04-04 08:07:32'),(11,'某项消费记录统计(两层分类)','某项消费记录统计(两层分类)',2,0,'select consume_date as date,gt.behavior_name as name,1 as value \r\nfrom buy_record br,goods_type gt where br.sub_goods_type_id=gt.id and br.goods_type_id={0} and br.sub_goods_type_id={1} \r\n','br.consume_date','br.user_id','次',NULL,NULL,_binary '\0',1,NULL,3,8,NULL,'2017-09-06 16:08:33','2019-04-04 08:07:42'),(12,'操作日志统计','操作日志',6,0,'select occur_end_time as date,\'操作日志\' as name,1 as value from operation_log\r\n','occur_end_time','user_id','次','paras',NULL,_binary '\0',1,_binary '\0',3,9,NULL,'2017-09-07 11:07:10','2019-04-04 08:07:51'),(13,'回家统计','回家',2,0,'select consume_date as date,\'回家\' as name,1 as value from buy_record where keywords like \'%回家%\' and goods_name like \'%临海\'\r\n','consume_date','user_id','次','goods_name,keywords,shop_name,remark',NULL,_binary '\0',1,_binary '',3,1,'需要注意的是商品名需要以临海结尾','2017-09-14 09:02:11','2019-04-04 08:05:02'),(14,'用药统计','用药',3,0,'select tdd.occur_time as date,\'吃药\' as name,1 as value from treat_drug_detail tdd,treat_drug td where tdd.treat_drug_id=td.id\r\n','tdd.occur_time','tdd.user_id','次','td.name','select distinct name from treat_drug',_binary '\0',1,_binary '',3,1,NULL,'2017-09-14 10:56:17','2019-04-04 08:07:10'),(16,'通用数据统计','通用数据',8,0,'select cr.occur_time as date,crt.name,cr.value as value from common_record cr,common_record_type crt  where cr.common_record_type_id = crt.id and crt.month_stat=1\r\n','cr.occur_time','cr.user_id','分钟','crt.name','select name from common_record_type where year_stat=1',_binary '\0',1,_binary '',3,1,NULL,'2017-10-14 22:13:01','2019-04-04 08:07:20'),(17,'饮食餐次统计','饮食餐次',9,0,'select occur_time as date,\r\n    case \r\n     when diet_type=0 then \'早餐\'\r\n     when diet_type=1 then \'午餐\'\r\n     when diet_type=2 then \'晚餐\'\r\n     when (diet_type=3 and food_type=3) then \'水果\'\r\n     when (diet_type=3 and food_type=4) then \'零食\'\r\n     when (diet_type=3 and food_type=5) then \'茶\'\r\n     else \'其他\' END\r\n	as name,\r\n    1 as value from diet\r\n','occur_time','user_id','次','foods',NULL,_binary '\0',1,NULL,3,16,NULL,'2019-04-09 08:43:03','2020-06-10 14:17:27'),(18,'睡觉统计','睡觉',3,0,'select sleep_time as date,\'睡觉\' as name,\r\n1 as value from sleep\r\n','sleep_time','user_id','次',NULL,NULL,_binary '\0',1,NULL,3,17,NULL,'2019-04-09 09:54:53','2019-04-09 09:55:45'),(19,'起床统计','起床',3,0,'select get_up_time as date,\'起床\' as name,\r\n1 as value from sleep\r\n','get_up_time','user_id','次',NULL,NULL,_binary '\0',1,NULL,3,18,NULL,'2019-04-09 09:55:37','2019-04-09 09:56:43');
/*!40000 ALTER TABLE `user_behavior_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_calendar`
--

DROP TABLE IF EXISTS `user_calendar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_calendar` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `delay_counts` int NOT NULL,
  `buss_day` datetime NOT NULL,
  `expire_time` datetime NOT NULL,
  `buss_identity_key` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `source_type` smallint NOT NULL,
  `source_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `finished_time` datetime DEFAULT NULL,
  `finish_type` smallint DEFAULT NULL,
  `message_id` bigint DEFAULT NULL,
  `finish_source_id` bigint DEFAULT NULL,
  `all_day` tinyint DEFAULT '1',
  `location` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `read_only` tinyint DEFAULT '0',
  `period` smallint DEFAULT '0',
  `period_values` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `calendar_config_id` bigint DEFAULT NULL,
  `bind_values` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`source_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_calendar`
--

LOCK TABLES `user_calendar` WRITE;
/*!40000 ALTER TABLE `user_calendar` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_calendar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_calendar_config`
--

DROP TABLE IF EXISTS `user_calendar_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_calendar_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sql_type` smallint NOT NULL COMMENT '查询语句类型0:sql,1:hql',
  `sql_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `order_index` smallint DEFAULT NULL COMMENT '排序号',
  `status` smallint NOT NULL COMMENT '状态',
  `user_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `level` int NOT NULL DEFAULT '3',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_calendar_config`
--

LOCK TABLES `user_calendar_config` WRITE;
/*!40000 ALTER TABLE `user_calendar_config` DISABLE KEYS */;
INSERT INTO `user_calendar_config` VALUES (1,'某项乐器练习','某项乐器练习',0,'select practice_start_time as date,minutes as resultValue,\'\'分钟\'\' as unit from music_practice \r\nwhere music_instrument_id={0} and user_id=?0 and practice_start_time>=?1 and practice_start_time<=?2\r\norder by practice_start_time',1,1,NULL,3,NULL,'2020-06-21 08:43:57','2020-06-21 11:49:45'),(2,'某项运动锻炼','某项运动锻炼',0,'select exercise_date as date,minutes as value,\'\'分钟\'\' as unit from sport_exercise \r\nwhere  sport_type_id={0} and user_id=?0 and exercise_date>=?1 and exercise_date<=?2 \r\norder by exercise_date',2,1,NULL,3,NULL,'2020-06-21 11:27:14','2020-06-21 14:52:41'),(3,'某项通用记录','某项通用记录',0,'select cr.occur_time as date,cr.value as value,crt.unit as unit,cr.name as name \r\nfrom common_record  cr,common_record_type crt\r\nwhere cr.common_record_type_id=crt.id and\r\ncr.common_record_type_id={0} and cr.user_id=?0 and cr.occur_time>=?1 and cr.occur_time<=?2 order by cr.occur_time',3,1,NULL,3,NULL,'2020-06-21 11:35:41','2020-06-21 14:56:20'),(4,'记录身体基本情况','记录身体基本情况',0,'select record_date as date,weight as value,\'KG\' as unit from body_basic_info \r\n where user_id=?0 and record_date>=?1 and record_date<=?2\r\n order by record_date',4,1,NULL,3,NULL,'2020-06-21 11:56:12','2020-06-21 11:57:37'),(5,'运动锻炼','运动锻炼',0,'select exercise_date as date,minutes as value,\'分钟\' as unit from sport_exercise \r\nwhere user_id=?0 and exercise_date>=?1 and exercise_date<=?2 \r\norder by exercise_date',5,1,NULL,3,NULL,'2020-06-22 16:28:46','2020-06-22 16:31:39'),(6,'账户快照','账户快照',0,'select created_time as date,name as value\r\n from account_snapshot_info\r\nwhere user_id=?0 and created_time>=?1 and created_time<=?2 \r\norder by created_time',6,1,NULL,3,NULL,'2020-06-22 21:55:39',NULL);
/*!40000 ALTER TABLE `user_calendar_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_chart`
--

DROP TABLE IF EXISTS `user_chart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_chart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint NOT NULL,
  `chart_config_id` bigint NOT NULL,
  `bind_values` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  `show_in_index` tinyint DEFAULT '0',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_chart`
--

LOCK TABLES `user_chart` WRITE;
/*!40000 ALTER TABLE `user_chart` DISABLE KEYS */;
INSERT INTO `user_chart` VALUES (60,'消费记录实时分析(商品类型)',0,1,NULL,1,1,1,NULL,'2020-02-11 18:52:27',NULL),(61,'消费记录实时分析(购买来源)',0,2,'buy_type_id',1,2,0,NULL,'2020-02-11 18:52:27',NULL),(62,'消费统计(月报)',0,3,'MONTH',1,3,0,NULL,'2020-02-11 18:52:27',NULL),(63,'账户统计',0,4,NULL,1,4,0,NULL,'2020-02-11 18:52:27',NULL),(64,'预算统计',0,5,NULL,1,5,0,NULL,'2020-02-11 18:52:27',NULL),(65,'消费标签统计',0,6,NULL,1,6,0,NULL,'2020-02-11 18:52:27',NULL),(66,'收入统计',0,7,NULL,1,7,0,NULL,'2020-02-11 18:52:27',NULL),(67,'梦想统计',0,10,NULL,1,10,0,NULL,'2020-02-11 18:52:27',NULL),(68,'看病分析(确诊疾病)',0,12,'diagnosed_disease',1,12,0,NULL,'2020-02-11 18:52:27',NULL),(69,'看病统计(月报)',0,13,'MONTH',1,12,0,NULL,'2020-02-11 18:52:27',NULL),(70,'身体基本情况统计(日报)',0,14,'DAY',1,14,0,NULL,'2020-02-11 18:52:27',NULL),(71,'身体不适分析(疾病)',0,15,'DISEASE',1,16,1,NULL,'2020-02-11 18:52:27',NULL),(72,'身体不适分析(月报)',0,16,'MONTH',1,17,0,NULL,'2020-02-11 18:52:27',NULL),(73,'睡眠分析(每周每天的睡眠时长)',0,17,'DAYOFWEEK,DURATION',1,18,0,NULL,'2020-02-11 18:52:27',NULL),(74,'人生经历统计(月报)',0,18,'MONTH',1,19,0,NULL,'2020-02-11 18:52:27',NULL),(75,'阅读统计(月报)',0,19,'MONTH',1,19,0,NULL,'2020-02-11 18:52:27',NULL),(76,'阅读分析(按图书分类)',0,20,'BOOKCATEGORY',1,20,0,NULL,'2020-02-11 18:52:27',NULL),(77,'加班统计(月报)',0,21,'MONTH',1,21,0,NULL,'2020-02-11 18:52:27',NULL),(78,'饮食价格分析(月报)',0,22,'MONTH',1,22,0,NULL,'2020-02-11 18:52:27',NULL),(79,'用户行为分析(月报)',0,24,NULL,1,24,0,NULL,'2020-02-11 18:52:27',NULL),(80,'八小时外分析',0,25,NULL,1,25,0,NULL,'2020-02-11 18:52:27',NULL),(81,'用户评分统计',0,26,NULL,1,26,0,NULL,'2020-02-11 18:52:27',NULL),(82,'用户积分统计(月报)',0,27,'MONTH',1,27,0,NULL,'2020-02-11 18:52:27',NULL);
/*!40000 ALTER TABLE `user_chart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_data_delete`
--

DROP TABLE IF EXISTS `user_data_delete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_data_delete` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `table_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_field` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `order_index` smallint DEFAULT NULL,
  `status` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`table_name`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户数据清理配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_data_delete`
--

LOCK TABLES `user_data_delete` WRITE;
/*!40000 ALTER TABLE `user_data_delete` DISABLE KEYS */;
INSERT INTO `user_data_delete` VALUES (1,'用户账户','account','user_id',1,1,NULL,'2020-02-10 10:00:00',NULL),(2,'账户流水','account_flow','user_id',2,1,NULL,'2020-02-10 10:00:00',NULL),(3,'用户账户快照','account_snapshot_info','user_id',3,1,NULL,'2020-02-10 10:00:00',NULL),(4,'身体不适记录','body_abnormal_record','user_id',4,1,NULL,'2020-02-10 10:00:00',NULL),(5,'身体基本信息','body_basic_info','user_id',5,1,NULL,'2020-02-10 10:00:00',NULL),(6,'书籍类型','book_category','user_id',6,1,NULL,'2020-02-10 10:00:00',NULL),(7,'预算','budget','user_id',7,1,NULL,'2020-02-10 10:00:00',NULL),(8,'预算流水','budget_log','user_id',8,1,NULL,'2020-02-10 10:00:00',NULL),(9,'预算时间线','budget_timeline','user_id',9,1,NULL,'2020-02-10 10:00:00',NULL),(10,'出差记录','business_trip','user_id',10,1,NULL,'2020-02-10 10:00:00',NULL),(11,'消费记录','buy_record','user_id',11,1,NULL,'2020-02-10 10:00:00',NULL),(12,'购买来源','buy_type','user_id',12,1,NULL,'2020-02-10 10:00:00',NULL),(13,'通用记录','common_record','user_id',13,1,NULL,'2020-02-10 10:00:00',NULL),(14,'通用记录类型','common_record_type','user_id',14,1,NULL,'2020-02-10 10:00:00',NULL),(15,'公司','company','user_id',15,1,NULL,'2020-02-10 10:00:00',NULL),(16,'人生记录的消费类型','consume_type','user_id',16,1,NULL,'2020-02-10 10:00:00',NULL),(17,'日记','diary','user_id',17,1,NULL,'2020-02-10 10:00:00',NULL),(18,'饮食记录','diet','user_id',18,1,NULL,'2020-02-10 10:00:00',NULL),(19,'食物分类','diet_category','user_id',19,0,'这个应该是系统自带','2020-02-10 10:00:00',NULL),(20,'梦想','dream','user_id',20,1,NULL,'2020-02-10 10:00:00',NULL),(21,'梦想提醒','dream_remind','user_id',21,1,NULL,'2020-02-10 10:00:00',NULL),(22,'商品类型','goods_type','user_id',22,1,NULL,'2020-02-10 10:00:00',NULL),(23,'收入','income','user_id',23,1,NULL,'2020-02-10 10:00:00',NULL),(24,'人生经历','life_experience','user_id',24,1,NULL,'2020-02-10 10:00:00',NULL),(25,'人生经历消费记录','life_experience_consume','user_id',25,1,NULL,'2020-02-10 10:00:00',NULL),(26,'人生经历详情','life_experience_detail','user_id',26,1,NULL,'2020-02-10 10:00:00',NULL),(27,'人生经历汇总统计','life_experience_sum','user_id',27,1,NULL,'2020-02-10 10:00:00',NULL),(28,'乐器','music_instrument','user_id',28,1,NULL,'2020-02-10 10:00:00',NULL),(29,'音乐练习','music_practice','user_id',29,1,NULL,'2020-02-10 10:00:00',NULL),(30,'音乐练习曲子','music_practice_tune','user_id',30,1,NULL,'2020-02-10 10:00:00',NULL),(31,'计划执行报告','plan_report','user_id',31,1,NULL,'2020-02-10 10:00:00',NULL),(32,'计划执行时间线','plan_report_timeline','user_id',32,1,NULL,'2020-02-10 10:00:00',NULL),(33,'价格区间','price_region','user_id',33,1,NULL,'2020-02-10 10:00:00',NULL),(34,'阅读记录','reading_record','user_id',34,1,NULL,'2020-02-10 10:00:00',NULL),(35,'阅读详情','reading_record_detail','user_id',35,1,NULL,'2020-02-10 10:00:00',NULL),(36,'股票价格','shares_price','user_id',36,1,NULL,'2020-02-10 10:00:00',NULL),(37,'睡眠记录','sleep','user_id',37,1,NULL,'2020-02-10 10:00:00',NULL),(38,'锻炼记录','sport_exercise','user_id',38,1,NULL,'2020-02-10 10:00:00',NULL),(39,'锻炼里程碑','sport_milestone','user_id',39,1,NULL,'2020-02-10 10:00:00',NULL),(40,'运动类型','sport_type','user_id',40,1,NULL,'2020-02-10 10:00:00',NULL),(41,'药品记录','treat_drug','user_id',41,1,NULL,'2020-02-10 10:00:00',NULL),(42,'用药详情','treat_drug_detail','user_id',42,1,NULL,'2020-02-10 10:00:00',NULL),(43,'手术记录','treat_operation','user_id',43,1,NULL,'2020-02-10 10:00:00',NULL),(44,'看病记录','treat_record','user_id',44,1,NULL,'2020-02-10 10:00:00',NULL),(45,'看病的检测记录','treat_test','user_id',45,1,NULL,'2020-02-10 10:00:00',NULL),(46,'用户行为配置','user_behavior','user_id',46,1,NULL,'2020-02-10 10:00:00',NULL),(47,'用户日历','user_calendar','user_id',47,1,NULL,'2020-02-10 10:00:00',NULL),(48,'用户图表','user_chart','user_id',48,1,NULL,'2020-02-10 10:00:00',NULL),(49,'用户消息','user_message','user_id',49,1,NULL,'2020-02-10 10:00:00',NULL),(50,'用户提醒','user_notify','user_id',50,1,NULL,'2020-02-10 10:00:00',NULL),(51,'用户提醒的提醒设置','user_notify_remind','user_id',51,1,NULL,'2020-02-10 10:00:00',NULL),(52,'用户计划','user_plan','user_id',52,1,NULL,'2020-02-10 10:00:00',NULL),(53,'用户计划配置值','user_plan_config_value','user_id',53,1,NULL,'2020-02-10 10:00:00',NULL),(54,'用户计划提醒','user_plan_remind','user_id',54,1,NULL,'2020-02-10 10:00:00',NULL),(55,'用户报表','user_report_config','user_id',55,1,NULL,'2020-02-10 10:00:00',NULL),(56,'用户报表提醒','user_report_remind','user_id',56,1,NULL,'2020-02-10 10:00:00',NULL),(57,'用户积分记录','user_reward_point_record','user_id',57,1,NULL,'2020-02-10 10:00:00',NULL),(58,'用户评分','user_score','user_id',58,1,NULL,'2020-02-10 10:00:00',NULL),(59,'用户评分详情','user_score_detail','user_id',59,1,NULL,'2020-02-10 10:00:00',NULL),(60,'用户股票','user_shares','user_id',60,1,NULL,'2020-02-10 10:00:00',NULL),(61,'用户股票评分','user_shares_score','user_id',61,1,NULL,'2020-02-10 10:00:00',NULL),(62,'用户股票评分设置','user_shares_score_config','user_id',62,1,NULL,'2020-02-10 10:00:00',NULL),(63,'用户股票报警记录','user_shares_warn','user_id',63,1,NULL,'2020-02-10 10:00:00',NULL),(64,'用户微信信息','user_wxpay_info','user_id',64,1,NULL,'2020-02-10 10:00:00',NULL),(65,'加班信息','work_overtime','user_id',65,1,NULL,'2020-02-10 10:00:00',NULL),(66,'人生档案信息','life_archives','user_id',66,1,NULL,'2020-02-10 10:00:00',NULL),(67,'账户快照','budget_snapshot','user_id',67,1,NULL,'2020-02-10 10:00:00',NULL),(68,'消费记录匹配日志','buy_record_match_log','user_id',68,1,NULL,'2020-02-10 10:00:00',NULL),(69,'饮食重复度日志','diet_variety_log','user_id',69,1,NULL,'2020-02-10 10:00:00',NULL),(70,'家庭','family','user_id',70,1,NULL,'2020-02-10 10:00:00',NULL),(71,'家庭成员','family_user','user_id',71,1,NULL,'2020-02-10 10:00:00',NULL),(72,'快捷菜单','fast_menu','user_id',72,1,NULL,'2020-02-10 10:00:00',NULL),(73,'系统监控用户配置','system_monitor_user','user_id',73,1,NULL,'2020-02-10 10:00:00',NULL),(74,'用户角色','user_role','user_id',74,1,NULL,'2020-02-10 10:00:00',NULL);
/*!40000 ALTER TABLE `user_data_delete` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_data_init`
--

DROP TABLE IF EXISTS `user_data_init`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_data_init` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sql_content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `order_index` smallint DEFAULT NULL,
  `status` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户数据初始化表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_data_init`
--

LOCK TABLES `user_data_init` WRITE;
/*!40000 ALTER TABLE `user_data_init` DISABLE KEYS */;
INSERT INTO `user_data_init` VALUES (1,'用户账户','insert into account(user_id,name,type,amount,status,created_time) \nselect t_user_id,name,type,amount,status,now() from account where user_id=0',1,1,NULL,'2020-02-10 10:00:00',NULL);
/*!40000 ALTER TABLE `user_data_init` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_message`
--

DROP TABLE IF EXISTS `user_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '1',
  `message_type` smallint NOT NULL,
  `buss_type` smallint NOT NULL DEFAULT '0',
  `send_status` smallint NOT NULL,
  `log_level` smallint NOT NULL DEFAULT '0',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fail_count` int NOT NULL,
  `expect_send_time` datetime DEFAULT NULL,
  `last_send_time` datetime DEFAULT NULL,
  `node_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `code` int DEFAULT '0',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_message`
--

LOCK TABLES `user_message` WRITE;
/*!40000 ALTER TABLE `user_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notify`
--

DROP TABLE IF EXISTS `user_notify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_notify` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint NOT NULL,
  `notify_config_id` bigint NOT NULL,
  `bind_values` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remind` tinyint NOT NULL,
  `show_in_index` tinyint NOT NULL DEFAULT '1',
  `warning_value` int NOT NULL DEFAULT '0',
  `alert_value` int NOT NULL DEFAULT '0',
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  `calendar_title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `calendar_time` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `unit` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notify`
--

LOCK TABLES `user_notify` WRITE;
/*!40000 ALTER TABLE `user_notify` DISABLE KEYS */;
INSERT INTO `user_notify` VALUES (70,'今年已经花费',0,13,NULL,1,1,50000,100000,1,1,'克制消费',NULL,'元',NULL,'2020-02-11 18:29:29','2020-10-13 09:21:34'),(71,'使用时间最长的商品',0,42,NULL,0,1,3650,73000,1,2,'注意商品使用周期',NULL,'天',NULL,'2020-02-11 18:30:08','2020-10-13 09:21:39'),(72,'使用时间最短的商品',0,43,NULL,0,1,7300,3600,1,3,'注意商品使用周期',NULL,'天',NULL,'2020-02-11 18:30:44','2020-10-13 09:21:43'),(73,'最近30天早餐没吃的次数',0,22,'0',1,1,3,5,1,4,'按时吃饭',NULL,'次',NULL,'2020-02-11 18:31:10','2020-10-13 09:21:48'),(74,'最近一个月饮食的食物种类数',0,25,NULL,0,1,80,50,1,5,'请注意饮食多样性',NULL,'种',NULL,'2020-02-11 18:31:31','2020-10-13 09:21:56'),(75,'最近一个月午餐饮食的食物种类数',0,26,'1',0,1,30,20,1,6,'请注意饮食多样性',NULL,'种',NULL,'2020-02-11 18:31:59','2020-10-13 09:22:01'),(76,'早餐最喜欢吃的食物',0,36,'0',0,1,30,40,1,7,'注意饮食',NULL,'次',NULL,'2020-02-11 18:32:34','2020-10-13 09:22:04'),(77,'最喜欢吃的外卖',0,40,'2',0,1,100,200,1,8,'注意饮食',NULL,'次',NULL,'2020-02-11 18:33:09','2020-10-13 09:22:08'),(78,'今年未实现的梦想',0,5,NULL,1,1,2,4,1,9,'完成梦想',NULL,'个',NULL,'2020-02-11 18:33:44','2020-10-13 09:22:11'),(79,'人生还未实现的梦想还有',0,6,NULL,0,1,20,30,1,10,'实现梦想',NULL,'个',NULL,'2020-02-11 18:34:12','2020-10-13 09:22:15'),(80,'今年梦想完成进度',0,10,NULL,0,1,35,20,1,11,'注意梦想进度',NULL,'%',NULL,'2020-02-11 18:34:47','2020-10-13 09:22:26'),(81,'距离上一次旅行已经过去',0,7,NULL,1,1,60,90,1,12,'出去旅行',NULL,'天',NULL,'2020-02-11 18:35:08','2020-10-13 09:22:33'),(82,'一生去过的国家数',0,12,NULL,1,1,10,5,1,13,'出国旅行',NULL,'个',NULL,'2020-02-11 18:35:33','2020-10-13 09:22:38'),(83,'一生去过的国内省份/直辖市',0,14,NULL,1,1,15,10,1,14,'该去国内旅行了',NULL,'个',NULL,'2020-02-11 18:35:54','2020-10-13 09:22:41'),(84,'距离上一次回家已过去',0,20,NULL,1,1,30,60,1,15,'该回家了',NULL,'天',NULL,'2020-02-11 18:36:13','2020-10-13 09:22:45'),(85,'统计最后一次音乐练习',0,28,NULL,0,1,3,7,1,16,'练习乐器',NULL,'天',NULL,'2020-02-11 18:36:45','2020-10-13 09:22:50'),(86,'今年已经读过书籍',0,15,NULL,1,1,10,6,1,17,'多读书',NULL,'本',NULL,'2020-02-11 18:37:04','2020-10-13 09:22:54'),(87,'距离上一次看书已过去',0,23,NULL,1,1,3,7,1,18,'看书',NULL,'天',NULL,'2020-02-11 18:37:20','2020-10-13 09:23:00'),(88,'最近一个月看书时长',0,32,NULL,1,1,15,10,1,19,'看书',NULL,'小时',NULL,'2020-02-11 18:37:48','2020-10-13 09:23:18'),(89,'距离上一次锻炼已过去',0,24,NULL,1,1,3,7,1,20,'锻炼身体',NULL,'天',NULL,'2020-02-11 18:38:07','2020-10-13 09:23:25'),(90,'最近三个月看病次数',0,9,NULL,1,1,3,5,1,21,'注意身体',NULL,'次',NULL,'2020-02-11 18:38:23','2020-10-13 09:23:30'),(91,'最近一个月身体不适次数',0,17,NULL,1,1,3,5,1,22,'注意身体',NULL,'次',NULL,'2020-02-11 18:38:37','2020-10-13 09:23:33'),(92,'最容易生病的器官',0,18,NULL,1,1,100,200,1,23,'注意身体',NULL,'次',NULL,'2020-02-11 18:38:55','2020-10-13 09:23:37'),(93,'最近一年看的最多的病',0,19,NULL,1,1,3,10,1,24,'注意身体',NULL,'次',NULL,'2020-02-11 18:39:17','2020-10-13 09:23:40'),(94,'最近一次看的病',0,29,NULL,1,1,200,500,1,25,'最近一次看的病',NULL,'次',NULL,'2020-02-11 18:39:53','2020-10-13 09:23:44'),(95,'距离上一次统计身体基本数据',0,33,NULL,1,1,3,7,1,26,'记录身体基本数据',NULL,'天',NULL,'2020-02-11 18:40:14','2020-10-13 09:23:48'),(96,'最近一个月加班',0,3,NULL,1,1,22,44,1,27,'按时下班',NULL,'小时',NULL,'2020-02-11 18:40:42','2020-10-13 09:23:52');
/*!40000 ALTER TABLE `user_notify` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notify_remind`
--

DROP TABLE IF EXISTS `user_notify_remind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_notify_remind` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '1',
  `user_notify_id` bigint NOT NULL,
  `last_remind_time` datetime DEFAULT NULL,
  `trigger_type` int NOT NULL,
  `trigger_interval` int NOT NULL,
  `over_warning_remind` tinyint NOT NULL,
  `over_alert_remind` tinyint NOT NULL,
  `remind_time` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notify_remind`
--

LOCK TABLES `user_notify_remind` WRITE;
/*!40000 ALTER TABLE `user_notify_remind` DISABLE KEYS */;
INSERT INTO `user_notify_remind` VALUES (55,0,70,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:29:29',NULL),(56,0,71,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:30:08',NULL),(57,0,72,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:30:44',NULL),(58,0,73,'2020-10-15 06:00:00',4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:31:10',NULL),(59,0,74,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:31:31',NULL),(60,0,75,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:31:59',NULL),(61,0,76,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:32:34',NULL),(62,0,77,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:33:09',NULL),(63,0,78,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:33:44',NULL),(64,0,79,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:34:12',NULL),(65,0,80,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:34:47',NULL),(66,0,81,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:35:08',NULL),(67,0,82,'2020-10-15 06:00:00',4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:35:33',NULL),(68,0,83,'2020-10-15 06:00:01',4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:35:54',NULL),(69,0,84,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:36:13',NULL),(70,0,85,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:36:45',NULL),(71,0,86,'2020-10-15 06:00:01',4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:37:04',NULL),(72,0,87,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:37:20',NULL),(73,0,88,'2020-10-15 06:00:01',4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:37:48',NULL),(74,0,89,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:38:07',NULL),(75,0,90,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:38:23',NULL),(76,0,91,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:38:37',NULL),(77,0,92,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:38:55',NULL),(78,0,93,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:39:17',NULL),(79,0,94,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:39:53',NULL),(80,0,95,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:40:14',NULL),(81,0,96,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:40:42',NULL),(109,0,124,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(110,0,125,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(111,0,126,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(112,0,127,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(113,0,128,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(114,0,129,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(115,0,130,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(116,0,131,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(117,0,132,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(118,0,133,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(119,0,134,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(120,0,135,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(121,0,136,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(122,0,137,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(123,0,138,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(124,0,139,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(125,0,140,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(126,0,141,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(127,0,142,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(128,0,143,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(129,0,144,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(130,0,145,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(131,0,146,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(132,0,147,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(133,0,148,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(134,0,149,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL),(135,0,150,NULL,4,1,1,1,'08:30','由表单页面自动生成','2020-02-11 18:58:15',NULL);
/*!40000 ALTER TABLE `user_notify_remind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_operation_config`
--

DROP TABLE IF EXISTS `user_operation_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_operation_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `behavior_type` smallint NOT NULL,
  `sql_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `column_template` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `order_index` smallint NOT NULL,
  `status` smallint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_operation_config`
--

LOCK TABLES `user_operation_config` WRITE;
/*!40000 ALTER TABLE `user_operation_config` DISABLE KEYS */;
INSERT INTO `user_operation_config` VALUES (1,'消费',2,'SELECT buy_date,goods_name,total_price,\'元\' \r\nFROM buy_record\r\nwhere user_id=?0 and buy_date>=?1 and buy_date<=?2\r\norder by buy_date desc ','{0},花费:{1}{2}.',0,1,NULL,'2020-07-08 10:28:17','2020-07-08 11:17:51'),(2,'音乐练习',0,'SELECT mp.practice_start_time,mi.name,mp.minutes,\'分钟\'\r\nFROM music_practice mp ,music_instrument mi\r\nwhere mp.music_instrument_id =mi.id\r\nand mp.user_id=?0 and mp.practice_start_time>=?1 and mp.practice_start_time<=?2\r\norder by mp.practice_start_time desc','{0},练习:{1}{2}.',1,1,NULL,'2020-07-08 11:24:34',NULL),(3,'锻炼',1,'SELECT se.exercise_date,st.name,se.kilometres,st.unit,se.minutes,\'分钟\' \r\nFROM sport_exercise se,sport_type st\r\nwhere se.sport_type_id =st.id\r\nand se.user_id=?0 and se.exercise_date>=?1 and se.exercise_date<=?2\r\norder by se.exercise_date desc','{0},练习:{1}{2},{3}{4}',2,1,NULL,'2020-07-08 11:28:33',NULL),(4,'看病',3,'SELECT treat_date,disease,hospital,organ,total_fee,\'元\'\r\nFROM treat_record\r\nwhere user_id=?0 and treat_date>=?1 and treat_date<=?2\r\norder by treat_date desc','在[{1}]看病:{0},器官:{2},花费{3}{4}',3,1,NULL,'2020-07-08 11:33:25','2020-07-08 11:33:39'),(5,'身体不适',3,'SELECT occur_date,disease,organ,last_days,\'天\' \r\nFROM body_abnormal_record\r\nwhere user_id=?0 and occur_date>=?1 and occur_date<=?2\r\norder by occur_date desc','{0},器官:{1},持续时间{2}{3}.',4,1,NULL,'2020-07-08 11:36:54','2020-07-08 11:37:13'),(6,'人生经历',5,'SELECT start_date,name,days,cost \r\nFROM life_experience\r\nwhere user_id=?0 and start_date>=?1 and start_date<=?2\r\norder by start_date desc','{0},持续{1}天,花费{2}元.',5,1,NULL,'2020-07-08 11:41:09',NULL),(7,'阅读',4,'SELECT rrd.read_time,rr.book_name,rrd.minutes\r\nFROM reading_record_detail rrd,reading_record rr\r\nwhere rrd.reading_record_id=rr.id\r\nand rrd.user_id=?0 and rrd.read_time>=?1 and rrd.read_time<=?2 \r\norder by rrd.read_time desc','{0},阅读时间{1}分钟.',6,1,NULL,'2020-07-08 11:43:46',NULL),(8,'加班',7,'SELECT wo.work_start_time,c.name,wo.hours\r\n FROM work_overtime wo,company c\r\n where wo.company_id=c.id \r\nand wo.user_id=?0 and wo.work_start_time>=?1 and wo.work_start_time<=?2\r\norder by wo.work_start_time desc','{0},加班:{1}小时.',7,1,NULL,'2020-07-08 11:47:19','2020-07-08 11:47:47'),(9,'通用记录',8,'SELECT cr.occur_time,cr.name as nn,crt.name,cr.value,crt.unit \r\nFROM common_record cr ,common_record_type crt \r\nwhere cr.common_record_type_id=crt.id\r\nand cr.user_id=?0 and cr.occur_time>=?1 and cr.occur_time<=?2\r\norder by cr.occur_time desc','[{1}]{0},操作:{2}{3}.',8,1,NULL,'2020-07-08 11:51:08','2020-07-08 11:52:37'),(10,'出差',7,'SELECT bt.trip_date,bt.province,bt.city,bt.days,c.name \r\nFROM business_trip bt,company c\r\n where bt.company_id=c.id \r\nand bt.user_id=?0 and bt.trip_date>=?1 and bt.trip_date<=?2\r\norder by bt.trip_date desc','[{3}]在{0}{1}出差{2}天.',9,1,NULL,'2020-07-08 11:56:33',NULL),(11,'吃药',3,'SELECT tdd.occur_time,td.name \r\nFROM treat_drug_detail tdd,treat_drug td\r\nwhere tdd.treat_drug_id =td.id\r\nand tdd.user_id=?0 and tdd.occur_time>=?1 and tdd.occur_time<=?2\r\norder by tdd.occur_time desc','用药:{0}',10,1,NULL,'2020-07-08 11:59:26',NULL);
/*!40000 ALTER TABLE `user_operation_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_plan`
--

DROP TABLE IF EXISTS `user_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_plan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `plan_config_id` bigint NOT NULL,
  `title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `bind_values` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  `first_stat_day` date DEFAULT NULL,
  `remind` tinyint DEFAULT NULL,
  `calendar_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `calendar_time` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `unit` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_plan`
--

LOCK TABLES `user_plan` WRITE;
/*!40000 ALTER TABLE `user_plan` DISABLE KEYS */;
INSERT INTO `user_plan` VALUES (38,0,25,'月度锻炼计划',NULL,1,1,NULL,1,'注意锻炼身体',NULL,'小时',NULL,'2020-02-11 18:45:48','2020-10-13 09:24:06'),(39,0,29,'月度睡觉时长计划','8',1,2,NULL,1,'注意睡眠',NULL,'次',NULL,'2020-02-11 18:46:15','2020-10-13 09:24:10'),(40,0,30,'月度早睡计划','23',1,3,NULL,1,'及时早睡',NULL,'次',NULL,'2020-02-11 18:46:39','2020-10-13 09:24:27'),(41,0,3,'月度读书计划',NULL,1,4,NULL,1,'多读书',NULL,'本',NULL,'2020-02-11 18:46:54','2020-10-13 09:24:15'),(42,0,23,'月度加班限制计划',NULL,1,5,NULL,1,'及时下班',NULL,'小时',NULL,'2020-02-11 18:47:14','2020-10-13 09:24:42'),(43,0,31,'月度消费预算',NULL,1,6,NULL,1,'注意花费',NULL,'元',NULL,'2020-02-11 18:47:41','2020-10-13 09:24:50'),(44,0,21,'年度读书计划',NULL,1,7,NULL,1,'多读书',NULL,'本',NULL,'2020-02-11 18:47:58','2020-10-13 09:25:24'),(45,0,5,'年度看病限制计划',NULL,1,8,NULL,1,'注意身体',NULL,'次',NULL,'2020-02-11 18:48:23','2020-10-13 09:25:27'),(46,0,12,'年度旅行计划',NULL,1,9,NULL,1,'多出去旅行',NULL,'天',NULL,'2020-02-11 18:48:41','2020-10-13 09:25:31');
/*!40000 ALTER TABLE `user_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_plan_config_value`
--

DROP TABLE IF EXISTS `user_plan_config_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_plan_config_value` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '1',
  `user_plan_id` bigint DEFAULT NULL,
  `year` int NOT NULL,
  `plan_count_value` bigint NOT NULL,
  `plan_value` bigint DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_plan_config_value`
--

LOCK TABLES `user_plan_config_value` WRITE;
/*!40000 ALTER TABLE `user_plan_config_value` DISABLE KEYS */;
INSERT INTO `user_plan_config_value` VALUES (41,0,38,2020,0,20,NULL,'2020-02-11 18:45:48',NULL),(42,0,39,2020,3,3,NULL,'2020-02-11 18:46:15',NULL),(43,0,40,2020,3,3,NULL,'2020-02-11 18:46:39',NULL),(44,0,41,2020,1,1,NULL,'2020-02-11 18:46:54',NULL),(45,0,42,2020,9,36,NULL,'2020-02-11 18:47:14',NULL),(46,0,43,2020,6,1000,NULL,'2020-02-11 18:47:41',NULL),(47,0,44,2020,12,12,NULL,'2020-02-11 18:47:58',NULL),(48,0,45,2020,3,1000,NULL,'2020-02-11 18:48:23',NULL),(49,0,46,2020,2,6,NULL,'2020-02-11 18:48:41',NULL);
/*!40000 ALTER TABLE `user_plan_config_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_plan_remind`
--

DROP TABLE IF EXISTS `user_plan_remind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_plan_remind` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '1',
  `user_plan_id` bigint NOT NULL,
  `form_time_passed_rate` int NOT NULL,
  `finished_remind` tinyint NOT NULL,
  `last_remind_time` datetime DEFAULT NULL,
  `trigger_type` smallint NOT NULL,
  `trigger_interval` int NOT NULL,
  `remind_time` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_plan_remind`
--

LOCK TABLES `user_plan_remind` WRITE;
/*!40000 ALTER TABLE `user_plan_remind` DISABLE KEYS */;
INSERT INTO `user_plan_remind` VALUES (34,0,38,50,1,'2023-05-01 08:00:02',4,1,'08:30','由表单页面自动生成','2020-02-11 18:45:48',NULL),(35,0,39,50,1,'2023-05-01 08:00:02',4,1,'08:30','由表单页面自动生成','2020-02-11 18:46:15',NULL),(36,0,40,50,1,'2023-05-01 08:00:02',4,1,'08:30','由表单页面自动生成','2020-02-11 18:46:39',NULL),(37,0,41,50,1,'2023-05-01 08:00:02',4,1,'08:30','由表单页面自动生成','2020-02-11 18:46:54',NULL),(38,0,42,50,1,'2023-05-01 08:00:02',4,1,'08:30','由表单页面自动生成','2020-02-11 18:47:14',NULL),(39,0,43,50,1,'2023-05-01 08:00:02',4,1,'08:30','由表单页面自动生成','2020-02-11 18:47:41',NULL),(40,0,44,50,1,'2023-01-01 03:00:00',4,1,'08:30','由表单页面自动生成','2020-02-11 18:47:58',NULL),(41,0,45,50,1,'2023-01-01 03:00:00',4,1,'08:30','由表单页面自动生成','2020-02-11 18:48:23',NULL),(42,0,46,50,1,'2023-01-01 03:00:00',4,1,'08:30','由表单页面自动生成','2020-02-11 18:48:41',NULL);
/*!40000 ALTER TABLE `user_plan_remind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_qa`
--

DROP TABLE IF EXISTS `user_qa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_qa` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `open_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `source` smallint NOT NULL,
  `request_content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `reply_qa_id` bigint DEFAULT NULL,
  `reply_content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `session_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `match_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_qa`
--

LOCK TABLES `user_qa` WRITE;
/*!40000 ALTER TABLE `user_qa` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_qa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_report_config`
--

DROP TABLE IF EXISTS `user_report_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_report_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `bind_values` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `report_config_id` bigint NOT NULL,
  `status` smallint NOT NULL,
  `order_index` smallint NOT NULL,
  `remind` tinyint NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_report_config`
--

LOCK TABLES `user_report_config` WRITE;
/*!40000 ALTER TABLE `user_report_config` DISABLE KEYS */;
INSERT INTO `user_report_config` VALUES (17,0,'年底消费情况统计',NULL,1,1,1,0,NULL,'2020-02-11 18:44:10',NULL),(18,0,'年度看病情况统计',NULL,10,1,2,0,NULL,'2020-02-11 18:44:23',NULL),(19,0,'年度已实现梦想统计',NULL,6,1,3,0,NULL,'2020-02-11 18:44:39',NULL),(20,0,'年度旅行情况统计',NULL,7,1,4,0,NULL,'2020-02-11 18:44:51',NULL),(21,0,'年度已读的书统计',NULL,8,1,5,0,NULL,'2020-02-11 18:45:20',NULL);
/*!40000 ALTER TABLE `user_report_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_report_remind`
--

DROP TABLE IF EXISTS `user_report_remind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_report_remind` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '1',
  `user_report_config_id` bigint NOT NULL,
  `last_remind_time` datetime DEFAULT NULL,
  `remind_time` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_report_remind`
--

LOCK TABLES `user_report_remind` WRITE;
/*!40000 ALTER TABLE `user_report_remind` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_report_remind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_reward_point_record`
--

DROP TABLE IF EXISTS `user_reward_point_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_reward_point_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `rewards` int NOT NULL,
  `source_id` bigint DEFAULT NULL,
  `reward_source` smallint NOT NULL DEFAULT '0',
  `after_points` int NOT NULL DEFAULT '0',
  `message_id` bigint DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_reward_point_record`
--

LOCK TABLES `user_reward_point_record` WRITE;
/*!40000 ALTER TABLE `user_reward_point_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_reward_point_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (4,1,1),(5,1,0);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_score`
--

DROP TABLE IF EXISTS `user_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_score` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `score` int NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_score`
--

LOCK TABLES `user_score` WRITE;
/*!40000 ALTER TABLE `user_score` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_score_detail`
--

DROP TABLE IF EXISTS `user_score_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_score_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `user_score_id` bigint NOT NULL,
  `score_config_id` bigint NOT NULL,
  `score` int NOT NULL,
  `stat_value` decimal(9,2) NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_score_detail`
--

LOCK TABLES `user_score_detail` WRITE;
/*!40000 ALTER TABLE `user_score_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_score_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_setting`
--

DROP TABLE IF EXISTS `user_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_setting` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `send_email` tinyint NOT NULL,
  `send_wx_message` tinyint NOT NULL,
  `stat_score` tinyint NOT NULL DEFAULT '1',
  `score_group` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0',
  `resident_city` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `treat_goods_type_id` int DEFAULT NULL,
  `treat_sub_goods_type_id` int DEFAULT NULL,
  `payment` smallint DEFAULT '0',
  `buy_type_id` int DEFAULT NULL,
  `treat_buy_type_id` int DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_setting`
--

LOCK TABLES `user_setting` WRITE;
/*!40000 ALTER TABLE `user_setting` DISABLE KEYS */;
INSERT INTO `user_setting` VALUES (0,0,0,0,1,'0',NULL,NULL,NULL,0,NULL,NULL,NULL,'2020-01-10 15:57:02','2023-05-10 21:55:05'),(1,1,0,1,1,'0','杭州',NULL,NULL,0,NULL,NULL,NULL,'2018-03-16 09:55:00','2023-05-10 21:56:21');
/*!40000 ALTER TABLE `user_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_wxpay_info`
--

DROP TABLE IF EXISTS `user_wxpay_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_wxpay_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `login_account` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `subscribe` tinyint DEFAULT '0',
  `subscribe_time` datetime DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_wxpay_info`
--

LOCK TABLES `user_wxpay_info` WRITE;
/*!40000 ALTER TABLE `user_wxpay_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_wxpay_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work_overtime`
--

DROP TABLE IF EXISTS `work_overtime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `work_overtime` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `company_id` bigint NOT NULL COMMENT '公司',
  `work_date` date NOT NULL,
  `work_start_time` datetime DEFAULT NULL,
  `work_end_time` datetime DEFAULT NULL,
  `hours` decimal(5,1) NOT NULL COMMENT '时长',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='加班记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_overtime`
--

LOCK TABLES `work_overtime` WRITE;
/*!40000 ALTER TABLE `work_overtime` DISABLE KEYS */;
/*!40000 ALTER TABLE `work_overtime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'mulanbay_init'
--
/*!50003 DROP FUNCTION IF EXISTS `getBuyRecordChildren` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE FUNCTION `getBuyRecordChildren`(rootId BigInt) RETURNS varchar(1000) CHARSET utf8mb4 COLLATE utf8mb4_general_ci
BEGIN
	DECLARE ptemp VARCHAR(1000);
	DECLARE ctemp VARCHAR(1000);
	SET ptemp = '#';
	SET ctemp = CAST(rootId AS CHAR);
	WHILE ctemp IS NOT NULL DO
		SET ptemp = CONCAT(ptemp,',',ctemp);
SELECT GROUP_CONCAT(id) INTO ctemp FROM buy_record
WHERE FIND_IN_SET(pid, ctemp) > 0;
END WHILE;
    set ptemp = replace(ptemp,concat('#,',rootId),'');
RETURN ptemp;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getFunctionChild` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE FUNCTION `getFunctionChild`(rootId BigInt) RETURNS varchar(1000) CHARSET utf8mb4 COLLATE utf8mb4_general_ci
BEGIN
	DECLARE ptemp VARCHAR(1000);
	DECLARE ctemp VARCHAR(1000);
	SET ptemp = '#';
	SET ctemp =CAST(rootId AS CHAR);
	WHILE ctemp IS NOT NULL DO
		SET ptemp = CONCAT(ptemp,',',ctemp);
		SELECT GROUP_CONCAT(id) INTO ctemp FROM system_function
		WHERE FIND_IN_SET(pid, ctemp) > 0; 
	END WHILE;  
	RETURN ptemp;  
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getPriceRegionId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE FUNCTION `getPriceRegionId`(p_price DECIMAL,p_user_id BIGINT) RETURNS int
BEGIN

RETURN (select id from price_region where min_price<=p_price and max_price>p_price and user_id=p_user_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_user_data` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `delete_user_data`(IN p_user_id bigint)
BEGIN
	
	declare v_table_name varchar(45);
    declare v_user_field varchar(45);
    declare flag int default 0;
    
	declare s_list cursor for (select table_name,user_field from user_data_delete where status=1 order by order_index);
     
    declare continue handler for not found set flag=1;
    
    open s_list;
    
        fetch s_list into v_table_name, v_user_field;
        while flag <> 1 do
            
            set @temp_s = CONCAT('delete from ',v_table_name,' where ',v_user_field,'=',p_user_id);
            
			prepare stmt from @temp_s;
			
			EXECUTE stmt;
			
			deallocate prepare stmt;
            
            fetch s_list into v_table_name, v_user_field;
        end while;
    close s_list;  
    
    update user set level=3,points=0 where id=p_user_id;
    
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
