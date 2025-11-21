-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: tfg
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alquiler`
--

DROP TABLE IF EXISTS `alquiler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alquiler` (
  `idalquiler` int NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `material` int NOT NULL,
  `responsable` int NOT NULL,
  PRIMARY KEY (`idalquiler`),
  KEY `material_idx` (`material`),
  KEY `responsable_idx` (`responsable`),
  CONSTRAINT `` FOREIGN KEY (`responsable`) REFERENCES `usuario` (`id`),
  CONSTRAINT `material` FOREIGN KEY (`material`) REFERENCES `material` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alquiler`
--

LOCK TABLES `alquiler` WRITE;
/*!40000 ALTER TABLE `alquiler` DISABLE KEYS */;
INSERT INTO `alquiler` VALUES (1,'2024-02-21','12:00:00',1,1),(2,'2024-04-12','17:00:00',1,1),(3,'2024-04-12','17:00:00',1,1),(4,'2024-04-12','17:00:00',1,1),(5,'2024-04-12','17:00:00',1,1),(7,'2024-04-14','18:00:00',2,1),(10,'2024-04-15','11:00:00',1,1),(42,'2024-04-15','16:00:00',1,1),(43,'2024-04-15','16:00:00',1,1),(44,'2024-04-15','16:00:00',1,1),(45,'2024-04-15','16:00:00',1,1),(46,'2024-04-15','16:00:00',3,1),(47,'2024-04-15','16:00:00',3,1),(48,'2024-04-15','16:00:00',3,1),(49,'2024-04-15','16:00:00',3,1),(50,'2024-04-15','16:00:00',3,1),(51,'2024-04-15','16:00:00',3,1),(52,'2024-04-26','10:00:00',1,1),(53,'2024-04-26','08:00:00',1,1),(54,'2024-04-26','08:00:00',1,1),(55,'2024-04-26','08:00:00',1,1),(56,'2024-05-03','08:00:00',1,1),(57,'2024-05-03','08:00:00',5,1),(58,'2024-04-15','16:00:00',1,1),(59,'2024-05-03','08:00:00',1,1),(60,'2024-05-03','08:00:00',1,1),(61,'2024-05-03','08:00:00',5,1),(62,'2024-05-06','08:00:00',1,1),(63,'2024-05-06','08:00:00',5,1),(64,'2024-05-06','08:00:00',5,1),(65,'2024-05-06','08:00:00',5,1),(66,'2024-05-07','20:00:00',3,3),(67,'2024-05-09','08:00:00',1,1),(68,'2024-05-09','08:00:00',1,1),(69,'2024-05-09','08:00:00',2,1),(70,'2024-05-09','08:00:00',1,1),(71,'2024-05-09','09:00:00',1,1),(72,'2024-05-09','09:00:00',1,1),(73,'2024-05-09','09:00:00',3,1),(74,'2024-05-09','09:00:00',3,1),(75,'2024-05-10','14:00:00',3,1),(76,'2024-05-10','14:00:00',5,1),(77,'2024-05-10','17:00:00',1,28),(78,'2024-05-10','17:00:00',1,28),(79,'2024-05-10','17:00:00',1,28),(80,'2024-05-10','16:00:00',1,2),(81,'2024-05-23','12:00:00',1,1),(82,'2024-06-07','18:00:00',2,1),(83,'2024-06-07','18:00:00',2,1),(84,'2024-06-08','08:00:00',1,1),(85,'2024-06-08','08:00:00',1,1),(86,'2024-06-08','08:00:00',1,1),(87,'2024-06-09','12:00:00',1,30),(88,'2024-06-09','17:00:00',1,30),(89,'2024-06-09','08:00:00',2,30),(90,'2024-06-09','08:00:00',2,30),(91,'2024-06-09','08:00:00',5,30),(92,'2024-06-10','18:00:00',1,1),(93,'2024-06-10','18:00:00',1,1),(94,'2024-06-15','17:00:00',3,1),(95,'2024-06-15','17:00:00',3,1),(96,'2024-06-17','15:00:00',1,1),(97,'2024-06-15','19:00:00',2,1),(98,'2024-06-15','20:00:00',2,1),(99,'2024-06-15','20:00:00',2,1),(100,'2024-06-17','14:00:00',2,1);
/*!40000 ALTER TABLE `alquiler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articulo`
--

DROP TABLE IF EXISTS `articulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articulo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `precio` double NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `imagen` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulo`
--

LOCK TABLES `articulo` WRITE;
/*!40000 ALTER TABLE `articulo` DISABLE KEYS */;
INSERT INTO `articulo` VALUES (1,12,'Camiseta Azul','Ropa','/images/camiseta_azul.jpg'),(2,25,'Balón de Fútbol','Deportivo','/images/balon_futbol.jpg'),(3,5,'Pantalon','Ropa','/images/pantalon.png'),(4,10.4,'Zapatillas','ropa','/images/zapatillas_azul.png'),(6,20,'Balon de Voleybol','deportivo','/images/balon_voleibol.jpg'),(40,10,'Camiseta Roja','ropa','/images/camiseta_roja.jpg'),(79,10,'Rodilleras','Ropa','/images/rodilleras.png');
/*!40000 ALTER TABLE `articulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cancha`
--

DROP TABLE IF EXISTS `cancha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cancha` (
  `idcancha` int NOT NULL AUTO_INCREMENT,
  `deporte` varchar(45) NOT NULL,
  `precio` double NOT NULL,
  `imagen` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idcancha`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cancha`
--

LOCK TABLES `cancha` WRITE;
/*!40000 ALTER TABLE `cancha` DISABLE KEYS */;
INSERT INTO `cancha` VALUES (1,'Fútbol',14,'/images/futbol.jpg'),(2,'baloncesto',10.5,'/images/baloncesto.jpg'),(3,'voleibol',15.75,'/images/voleibol.jpg'),(7,'Voley Playa',17,'/images/voley_playa.jpg');
/*!40000 ALTER TABLE `cancha` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deportivo`
--

DROP TABLE IF EXISTS `deportivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deportivo` (
  `iddeportivo` int NOT NULL,
  `deporte` varchar(45) NOT NULL,
  `cantidad` int NOT NULL,
  PRIMARY KEY (`iddeportivo`),
  CONSTRAINT `iddeportivo` FOREIGN KEY (`iddeportivo`) REFERENCES `articulo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deportivo`
--

LOCK TABLES `deportivo` WRITE;
/*!40000 ALTER TABLE `deportivo` DISABLE KEYS */;
INSERT INTO `deportivo` VALUES (2,'futbol',39),(6,'voleibol',32);
/*!40000 ALTER TABLE `deportivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descuento`
--

DROP TABLE IF EXISTS `descuento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descuento` (
  `iddescuento` int NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `valor` int NOT NULL,
  `coste` int NOT NULL,
  `articulo` int NOT NULL,
  `usuario` int DEFAULT NULL,
  `imagen` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`iddescuento`),
  KEY ` _idx` (`articulo`),
  KEY `user_idx` (`usuario`),
  CONSTRAINT `art` FOREIGN KEY (`articulo`) REFERENCES `articulo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuento`
--

LOCK TABLES `descuento` WRITE;
/*!40000 ALTER TABLE `descuento` DISABLE KEYS */;
INSERT INTO `descuento` VALUES (2,'Valor',2,15,2,-1,'/images/ticket_descuento.png'),(3,'porcentaje',10,50,3,-1,'/images/ticket_descuento.png'),(21599760,'Porcentaje',50,200,79,-1,'/images/ticket_descuento.png'),(44965325,'Valor',2,15,2,1,'/images/ticket_descuento.png'),(48188083,'Valor',2,15,2,30,'/images/ticket_descuento.png'),(49356116,'Porcentaje',50,200,79,1,'/images/ticket_descuento.png'),(68318790,'porcentaje',10,50,3,30,'/images/ticket_descuento.png'),(86602401,'porcentaje',10,50,3,1,'/images/ticket_descuento.png'),(87463108,'porcentaje',10,50,3,1,'/images/ticket_descuento.png');
/*!40000 ALTER TABLE `descuento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `precio` double NOT NULL,
  `deporte` varchar(45) NOT NULL,
  `imagen` varchar(100) DEFAULT NULL,
  `cantidad` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
INSERT INTO `material` VALUES (1,'pelota de futbol',5,'Fútbol','/images/balon_futbol.jpg',6),(2,'pelota de baloncesto',4,'baloncesto','/images/pelota_baloncesto.png',5),(3,'Pelota de Voleibol',7,'voleibol','/images/balon_voleibol.jpg',7),(5,'Rodilleras',20,'Voleibol','/images/rodilleras.png',6);
/*!40000 ALTER TABLE `material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `noticia`
--

DROP TABLE IF EXISTS `noticia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `noticia` (
  `idnoticia` int NOT NULL AUTO_INCREMENT,
  `cabecera` varchar(45) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `contenido` varchar(10000) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  PRIMARY KEY (`idnoticia`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `noticia`
--

LOCK TABLES `noticia` WRITE;
/*!40000 ALTER TABLE `noticia` DISABLE KEYS */;
INSERT INTO `noticia` VALUES (1,'Primer Torneo','Torneo','Bienvenidos a todos al primer torneo inagurual del polideportivo. Queremos inaugurar esta gran apertura con un torneo del deporte más popular en nuestro país. Además, solo para este torneo, la entrada es gratuita, por lo que animaos a venir.','2024-05-03','15:41:04'),(2,'Prueba','Novedad','Hola2','2024-05-03','15:39:40'),(58,'Copa Arena','Torneo','Torneo de Voleibol por equipos de 2 personas','2024-06-02','22:18:47'),(60,'TorneoDePrueba','Torneo','Torneo de prueba para la memoria del Trabajo de Fin de Grado','2024-06-09','12:57:22'),(62,'Torneo','Torneo','Torneo','2024-06-10','17:25:48'),(63,'Torneo defensa','Torneo','Torneo de defensa','2024-06-16','13:29:48'),(65,'Noticia','Novedad','Noticia','2024-06-15','18:45:45'),(66,'Torneo','Torneo','Torneo','2024-06-15','18:46:18'),(67,'Torneo','Torneo','Torneo','2024-06-17','12:28:12');
/*!40000 ALTER TABLE `noticia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserva`
--

DROP TABLE IF EXISTS `reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserva` (
  `idreserva` int NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `hora_inicio` time NOT NULL,
  `hora_fin` time NOT NULL,
  `cancha` int NOT NULL,
  `responsable` int NOT NULL,
  PRIMARY KEY (`idreserva`),
  KEY `pista_idx` (`cancha`),
  KEY `responsable_idx` (`responsable`),
  CONSTRAINT `pista` FOREIGN KEY (`cancha`) REFERENCES `cancha` (`idcancha`),
  CONSTRAINT `responsable` FOREIGN KEY (`responsable`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserva`
--

LOCK TABLES `reserva` WRITE;
/*!40000 ALTER TABLE `reserva` DISABLE KEYS */;
INSERT INTO `reserva` VALUES (1,'2024-02-21','13:00:00','14:00:00',1,1),(2,'2024-04-06','11:00:00','12:00:00',1,1),(3,'2024-04-06','12:00:00','13:00:00',1,1),(4,'2024-04-14','18:00:00','19:00:00',2,1),(5,'2024-04-12','17:00:00','18:00:00',1,1),(6,'2024-04-12','18:00:00','19:00:00',1,1),(7,'2024-04-15','11:00:00','12:00:00',1,1),(8,'2024-04-15','12:00:00','13:00:00',1,1),(9,'2024-04-14','08:00:00','09:00:00',1,1),(10,'2024-04-27','14:00:00','15:00:00',1,1),(11,'2024-04-27','15:00:00','16:00:00',1,1),(12,'2024-04-26','13:00:00','14:00:00',1,1),(13,'2024-04-26','14:00:00','15:00:00',1,1),(14,'2024-04-26','12:00:00','13:00:00',1,1),(15,'2024-04-26','11:00:00','12:00:00',1,1),(16,'2024-04-26','10:00:00','11:00:00',1,1),(17,'2024-04-28','14:00:00','15:00:00',1,22),(18,'2024-04-30','20:00:00','21:00:00',2,-1),(19,'2024-04-28','19:00:00','20:00:00',1,26),(20,'2024-04-29','19:00:00','20:00:00',1,-1),(21,'2024-04-28','21:00:00','22:00:00',1,-1),(22,'2024-04-29','08:00:00','09:00:00',1,-1),(23,'2024-04-29','10:00:00','11:00:00',2,1),(24,'2024-05-03','16:00:00','17:00:00',1,-1),(25,'2024-05-03','08:00:00','09:00:00',1,1),(26,'2024-05-06','10:00:00','11:00:00',1,1),(27,'2024-05-06','11:00:00','12:00:00',1,1),(28,'2024-05-06','12:00:00','13:00:00',1,1),(29,'2024-05-06','13:00:00','14:00:00',1,1),(30,'2024-05-06','14:00:00','15:00:00',1,1),(31,'2024-05-06','15:00:00','16:00:00',1,1),(32,'2024-05-06','15:00:00','16:00:00',1,1),(33,'2024-05-06','15:00:00','16:00:00',1,1),(34,'2024-05-06','16:00:00','17:00:00',1,1),(35,'2024-05-06','16:00:00','17:00:00',1,1),(36,'2024-05-08','13:00:00','14:00:00',1,26),(37,'2024-05-07','20:00:00','21:00:00',3,3),(38,'2024-05-26','15:00:00','16:00:00',3,-1),(39,'2024-05-09','14:00:00','15:00:00',1,1),(40,'2024-05-09','15:00:00','16:00:00',1,1),(41,'2024-05-10','14:00:00','15:00:00',3,1),(42,'2024-05-17','15:00:00','16:00:00',3,-1),(43,'2024-05-24','11:00:00','12:00:00',7,3),(44,'2024-05-10','15:00:00','16:00:00',3,28),(45,'2024-05-10','08:00:00','09:00:00',1,2),(46,'2024-05-10','16:00:00','17:00:00',1,2),(47,'2024-05-11','08:00:00','09:00:00',1,1),(48,'2024-05-23','12:00:00','13:00:00',1,1),(49,'2024-05-25','15:00:00','16:00:00',3,-1),(50,'2024-05-25','08:00:00','09:00:00',1,-1),(51,'2024-05-30','19:00:00','20:00:00',1,1),(52,'2024-06-07','13:00:00','14:00:00',1,20),(53,'2024-06-08','10:00:00','11:00:00',1,29),(54,'2024-06-09','12:00:00','13:00:00',1,30),(55,'2024-06-09','15:00:00','16:00:00',1,30),(56,'2024-06-09','17:00:00','18:00:00',1,30),(57,'2024-06-24','17:00:00','18:00:00',2,-1),(58,'2024-06-11','13:00:00','14:00:00',3,1),(59,'2024-06-16','08:00:00','09:00:00',7,-1),(60,'2024-06-17','14:00:00','15:00:00',7,1),(61,'2024-06-24','15:00:00','16:00:00',2,-1),(62,'2024-06-17','15:00:00','16:00:00',1,1),(63,'2024-06-27','08:00:00','09:00:00',1,-1),(64,'2024-06-22','08:00:00','09:00:00',1,-1),(65,'2024-06-17','17:00:00','18:00:00',1,1),(66,'2024-06-19','17:00:00','18:00:00',3,-1),(67,'2024-06-17','16:00:00','17:00:00',1,1),(68,'2024-06-17','14:00:00','15:00:00',2,1),(69,'2024-06-23','08:00:00','09:00:00',1,-1),(70,'2025-01-09','15:00:00','16:00:00',3,1);
/*!40000 ALTER TABLE `reserva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ropa`
--

DROP TABLE IF EXISTS `ropa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ropa` (
  `idropa` int NOT NULL,
  `talla` varchar(45) NOT NULL,
  `cantidad` int NOT NULL,
  PRIMARY KEY (`idropa`,`talla`),
  CONSTRAINT `idropa` FOREIGN KEY (`idropa`) REFERENCES `articulo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ropa`
--

LOCK TABLES `ropa` WRITE;
/*!40000 ALTER TABLE `ropa` DISABLE KEYS */;
INSERT INTO `ropa` VALUES (1,'L',14),(1,'M',25),(3,'L',14),(3,'M',4),(4,'36-38',6),(40,'M',9),(79,'L',2);
/*!40000 ALTER TABLE `ropa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjeta`
--

DROP TABLE IF EXISTS `tarjeta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarjeta` (
  `idtarjeta` int NOT NULL,
  `saldo` double NOT NULL,
  PRIMARY KEY (`idtarjeta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjeta`
--

LOCK TABLES `tarjeta` WRITE;
/*!40000 ALTER TABLE `tarjeta` DISABLE KEYS */;
INSERT INTO `tarjeta` VALUES (1,48650.24999999999),(3,-1);
/*!40000 ALTER TABLE `tarjeta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `torneo`
--

DROP TABLE IF EXISTS `torneo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `torneo` (
  `idtorneo` int NOT NULL,
  `deporte` varchar(45) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `participantes` int NOT NULL,
  `premio` double NOT NULL,
  `coste` double NOT NULL,
  `cancha` int NOT NULL,
  PRIMARY KEY (`idtorneo`),
  KEY `cancha_idx` (`cancha`),
  CONSTRAINT `cancha` FOREIGN KEY (`cancha`) REFERENCES `cancha` (`idcancha`),
  CONSTRAINT `idtorneo` FOREIGN KEY (`idtorneo`) REFERENCES `noticia` (`idnoticia`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `torneo`
--

LOCK TABLES `torneo` WRITE;
/*!40000 ALTER TABLE `torneo` DISABLE KEYS */;
INSERT INTO `torneo` VALUES (1,'Fútbol','2024-05-03','16:00:00',16,0,0,1),(58,'Voleibol','2024-05-25','15:00:00',8,20,2,3),(60,'Baloncesto','2024-06-24','17:00:00',16,50,5,2),(62,'Voley playa','2024-06-16','08:00:00',8,50,5,7),(63,'Bailee','2024-06-24','15:00:00',32,10,0.5,2),(66,'VoleyBall','2024-06-19','17:00:00',8,0,0,3),(67,'Voleibol','2024-06-23','08:00:00',8,10,1,1);
/*!40000 ALTER TABLE `torneo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `correo` varchar(45) NOT NULL,
  `contraseña` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `puntos` int NOT NULL,
  `puntosT` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (-1,'Administrador','admin@gmail.com','admin','627226192',1000000,10000000),(1,'Diego','diego@gmail.com','1234','123456789',1461,101164),(2,'Registro','Registro@gmail.com','Prueba','987654321',57,125),(3,'Sam','JiraldoSarmiento@gmail.com','Sam','846513279',21,173),(5,'POST','POST@gmail.com','Hola','487591623',10000,50000),(6,'Pablo','diego2@gmail.com','1234','123456789',50,150),(12,'PruebaWeb3','prueba3@gmail.com','1244','123456789',0,0),(14,'Azucena','bruji4171@gmail.com','bruji','123456789',0,0),(16,'Manueh','Manueh@yahoo.com','abcd','659610112',0,0),(17,'Diego3','diego3@gmail.com','1234','123456789',0,0),(18,'Diego4','diego4@gmail.com','1234','123456789',0,0),(19,'Illojuan','juanillo@gmail.com','elbokeron','279321015',0,0),(20,'pablos','pablos@gmail.com','1','111111111',48,132),(21,'hermelindo','kdjdgi@hotmail.es','123','639279461',0,0),(22,'Sonia','sonia@gmail.com','1234','753692148',-21,97),(25,'PruebaFinal','PruebaFinal@gmail.com','prueba','123456789',0,0),(26,'javi','diego.sanchezj@edu.uahes','123','987654321',298,996),(27,'Prueba Final','pruebaFinal2@gmail.com','1234','123456789',0,0),(28,'Rossana','rosangel_1968@hotmail.com','1234','777777777',53,1362),(29,'Mercedes','mercedes@gmail.com','merce','123456789',12,24),(30,'UsuarioDeEjemplo','CorreoDeEjemplo@gmail.com','777777','111111111',181,1059);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario-participa-torneo`
--

DROP TABLE IF EXISTS `usuario-participa-torneo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario-participa-torneo` (
  `idusuario` int NOT NULL,
  `idtorneo` int NOT NULL,
  PRIMARY KEY (`idusuario`,`idtorneo`),
  KEY `torneo_idx` (`idtorneo`),
  CONSTRAINT `idusuario` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `torneo` FOREIGN KEY (`idtorneo`) REFERENCES `torneo` (`idtorneo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario-participa-torneo`
--

LOCK TABLES `usuario-participa-torneo` WRITE;
/*!40000 ALTER TABLE `usuario-participa-torneo` DISABLE KEYS */;
INSERT INTO `usuario-participa-torneo` VALUES (-1,1),(1,1),(2,1),(3,1),(5,1),(6,1),(22,1),(26,1),(28,1),(30,1),(1,58),(3,58),(1,62),(1,63),(1,66);
/*!40000 ALTER TABLE `usuario-participa-torneo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-21 10:43:35
