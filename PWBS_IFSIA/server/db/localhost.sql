/*
SQLyog Ultimate v12.4.3 (64 bit)
MySQL - 10.4.17-MariaDB : Database - db_pwbs_ifsia
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_pwbs_ifsia` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `db_pwbs_ifsia`;

/*Table structure for table `tb_karyawan` */

DROP TABLE IF EXISTS `tb_karyawan`;

CREATE TABLE `tb_karyawan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nik` char(10) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `telepon` varchar(13) NOT NULL,
  `jabatan` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `tb_karyawan` */

insert  into `tb_karyawan`(`id`,`nik`,`nama`,`alamat`,`telepon`,`jabatan`) values 
(2,'0002','Riskhi','Sukabumi','008988','CEO'),
(3,'0003','Adhit','Bandung','089399','Si paling Julid'),
(4,'896780','ucup yahut','jalan melati','0897675747','Specialist Forensik'),
(5,'252627','Ridho Slebew','Jalan Manja','089897969594','Direktur'),
(6,'252627','Ridho Slebew Kurma','Jalan Manja','089897969594','Direktur');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
