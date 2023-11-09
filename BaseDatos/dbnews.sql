/*
SQLyog Ultimate v12.4.1 (64 bit)
MySQL - 10.4.27-MariaDB : Database - sitionoticias
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sitionoticias` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `sitionoticias`;

/*Table structure for table `comentario` */

DROP TABLE IF EXISTS `comentario`;

CREATE TABLE `comentario` (
  `idcomentario` bigint(20) NOT NULL AUTO_INCREMENT,
  `comentario` varchar(255) NOT NULL,
  `fechahora` datetime DEFAULT NULL,
  `idnota` bigint(20) NOT NULL,
  `idusuario` bigint(20) NOT NULL,
  PRIMARY KEY (`idcomentario`),
  KEY `idnota` (`idnota`),
  KEY `idusuario` (`idusuario`),
  CONSTRAINT `comentario_ibfk_1` FOREIGN KEY (`idnota`) REFERENCES `nota` (`idnota`),
  CONSTRAINT `comentario_ibfk_2` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `comentario` */

insert  into `comentario`(`idcomentario`,`comentario`,`fechahora`,`idnota`,`idusuario`) values 
(16,'Gracias por la infromacion','2023-11-09 06:51:47',14,4);

/*Table structure for table `nota` */

DROP TABLE IF EXISTS `nota`;

CREATE TABLE `nota` (
  `idnota` bigint(20) NOT NULL AUTO_INCREMENT,
  `nota` text DEFAULT NULL,
  `fechahora` datetime DEFAULT NULL,
  `idpersonal` bigint(20) NOT NULL,
  PRIMARY KEY (`idnota`),
  KEY `idpersonal` (`idpersonal`),
  CONSTRAINT `nota_ibfk_1` FOREIGN KEY (`idpersonal`) REFERENCES `personal` (`idpersonal`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `nota` */

insert  into `nota`(`idnota`,`nota`,`fechahora`,`idpersonal`) values 
(14,'El dia 20 de noviembre, es dia festivo, por lo cual se suspenden labores.','2023-11-09 06:51:19',5);

/*Table structure for table `personal` */

DROP TABLE IF EXISTS `personal`;

CREATE TABLE `personal` (
  `idpersonal` bigint(20) NOT NULL AUTO_INCREMENT,
  `apepaterno` varchar(20) NOT NULL,
  `apematerno` varchar(20) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `fechadeingreso` date NOT NULL,
  `idusuario` bigint(20) NOT NULL,
  PRIMARY KEY (`idpersonal`),
  KEY `idusuario` (`idusuario`),
  CONSTRAINT `personal_ibfk_1` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `personal` */

insert  into `personal`(`idpersonal`,`apepaterno`,`apematerno`,`nombre`,`direccion`,`fechadeingreso`,`idusuario`) values 
(5,'Urdiales','Mena','Flavio','etc','2023-11-09',3),
(6,'Rodriguez','Muñoz','Carlos','etc','2023-11-09',4);

/*Table structure for table `respuesta` */

DROP TABLE IF EXISTS `respuesta`;

CREATE TABLE `respuesta` (
  `idrespuesta` bigint(20) NOT NULL AUTO_INCREMENT,
  `respuesta` varchar(255) DEFAULT NULL,
  `fechahora` datetime DEFAULT NULL,
  `idcomentario` bigint(20) DEFAULT NULL,
  `idusuario` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idrespuesta`),
  KEY `idcomentario` (`idcomentario`),
  KEY `idusuario` (`idusuario`),
  CONSTRAINT `respuesta_ibfk_1` FOREIGN KEY (`idcomentario`) REFERENCES `comentario` (`idcomentario`),
  CONSTRAINT `respuesta_ibfk_2` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `respuesta` */

insert  into `respuesta`(`idrespuesta`,`respuesta`,`fechahora`,`idcomentario`,`idusuario`) values 
(9,'De nada :)','2023-11-09 06:52:07',16,3);

/*Table structure for table `usuario` */

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `idusuario` bigint(20) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(20) NOT NULL,
  `contrasenia` varchar(15) NOT NULL,
  `int_ext` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `usuario` */

insert  into `usuario`(`idusuario`,`usuario`,`contrasenia`,`int_ext`) values 
(3,'UserTest','1234',1),
(4,'UserTest2','1234',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
