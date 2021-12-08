-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.6.4-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para packsturisticos
CREATE DATABASE IF NOT EXISTS `packsturisticos` /*!40100 DEFAULT CHARACTER SET utf8mb3 */;
USE `packsturisticos`;

-- Volcando estructura para tabla packsturisticos.actividades
CREATE TABLE IF NOT EXISTS `actividades` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `imagen` varchar(45) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `calidad` enum('1','2','3','4','5') DEFAULT NULL,
  `tipo` varchar(10) NOT NULL,
  `precio` decimal(6,2) DEFAULT NULL,
  `ubicacion` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_actividad_tipo1_idx` (`tipo`),
  CONSTRAINT `fk_actividad_tipo1` FOREIGN KEY (`tipo`) REFERENCES `tipos` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;

-- Volcando datos para la tabla packsturisticos.actividades: ~15 rows (aproximadamente)
/*!40000 ALTER TABLE `actividades` DISABLE KEYS */;
INSERT IGNORE INTO `actividades` (`id`, `nombre`, `descripcion`, `imagen`, `url`, `calidad`, `tipo`, `precio`, `ubicacion`) VALUES
	(1, 'Museo Picasso Málaga', '¿Te gusta el arte Renacentista? Disfruta de esta experiencia donde las doscientas ochenta y cinco obras que reúne esta colección abarcan las innovaciones revolucionarias de Picasso, así como la amplia variedad de estilos, materiales y técnicas que dominó.', 'museoPicasso.jpg', 'https://www.museopicassomalaga.org/', '4', '2', 12.00, 'https://goo.gl/maps/U2mNTBzMkzn22CGR8'),
	(2, 'Catedral Málaga', 'La Santa Iglesia Catedral Basílica de la Encarnación es la catedral de Málaga, en España. Situada enfrente de la plaza del Obispo, el templo es considerado una de las joyas renacentistas más valiosas de Andalucía.', 'catedral-malaga.jpg', 'https://malagacatedral.com/visita-cultural/visita-a-la-catedral/', '4', '2', 6.00, 'https://goo.gl/maps/HezePuvwgFLVTj6r8'),
	(3, 'Jardín botánico La Concepción', 'El Jardín Botánico Histórico La Concepción es un jardín de estilo paisajista inglés con más de ciento cincuenta años de historia. Situado en la entrada norte de la ciudad de Málaga, se trata de uno de los escasos jardines con plantas de clima subtropical que existen en Europa.', 'jardin-botanico.jpg', 'https://laconcepcion.malaga.eu/', '4', '2', 5.20, 'https://goo.gl/maps/MWFP1joP2dsK3k9h7'),
	(4, 'Hotel NH Málaga', 'Este elegante hotel se encuentra en un edificio de estilo moderno a la orilla del río Guadalmedina. Se sitúa a 11 minutos a pie de la catedral del Málaga, del siglo XVI, y a 1,3 km de la Alcazaba, la fortaleza medieval.', 'nh-malaga.jpg', 'https://www.nh-hoteles.es/hotel/nh-malaga', '4', '1', 80.00, 'https://g.page/NHMalaga?share'),
	(5, 'Hotel Soho las Vegas', 'Este moderno hotel, con vistas a la playa de la Malagueta, se encuentra a 1 minuto a pie de la parada de autobús de Paseo de Sancha y a 1 km de la Alcazaba de Málaga, una fortaleza medieval.', 'sohoHotel.jpg', 'https://www.sohohoteles.com/hotel-soho-vegas-en-malaga/', '3', '1', 65.00, 'https://goo.gl/maps/pEi9cjwcgmFRzQFdA'),
	(6, 'Debambú Larios', 'El Debambu Larios está ubicado en Málaga, a 100 metros del mercado de Atarazanas y a 2,4 km de la playa de la Misericordia, y ofrece alojamiento con WiFi gratuita y TV de pantalla plana.', 'debambuApartamento.jpg', 'http://www.debambu.es/rooms/apartamento-larios/', '4', '1', 70.00, 'https://goo.gl/maps/5vn6RQWi5M9ERhpz8'),
	(7, 'Terra Mia', 'Pizzas y recetas clásicas de la cocina tradicional napolitana en un alegre y colorido restaurante-pizzería.', 'terra-mia.jpg', 'https://terramiamalaga.com/', '4', '3', 10.00, 'https://goo.gl/maps/kBbbXpUQo3DxrPco7'),
	(8, 'Tercer Acto', 'Gastronomía & aplausos. El teatro es lo más parecido a la vida, y comer lo más parecido al teatro: entremeses,primer acto, segundo acto... y el tercer acto, donde uno, jubiloso, aplaude satisfecho en su interior', 'tercerActo.jpg', 'https://www.terceractogastro.com/', '5', '3', 20.00, 'https://g.page/terceracto_gastro?share'),
	(9, 'La Alacena de Francis', 'Amplia tapería delicatessen Francis, una larga trayectoria avala el prestigio y calidad a este espléndido restaurante, situado en el barrio más amplio y castizo de nuestra Málaga "EL PERCHEL".', 'alacenaFrancis.jpg', 'http://laalacenadefrancis.com/', '5', '3', 20.00, 'https://goo.gl/maps/qsXtJ4BFVcxQXWZu7'),
	(10, 'Hotel Brö', '​Este hotel sencillo solo para adultos, ubicado en una calle estrecha llena de tiendas, se encuentra a 15 minutos a pie de la playa de La Malagueta junto al mar Mediterráneo y a 3 km de la estación de tren Málaga María Zambrano.', 'hotelBro.jpg', 'https://hotelbro.com/', '3', '1', 60.00, 'https://g.page/hotelbro?share'),
	(11, 'Hotel Posadas de España Málaga', 'Este hotel, que ocupa un edificio con fachada de ladrillo rojo, se encuentra a 10 km del Jardín Botánico y a 14 del frondoso Jardín Oriental Bienquerido.', 'hotelPosada.jpg', 'https://www.posadasdeespanamalaga.com/', '3', '1', 55.00, 'https://goo.gl/maps/8miS3A5pgzoecF9R6'),
	(12, 'Blossom', 'Informal, cálido, moderno y elegante. Saborea y descubre nuestra propuesta culinaria destinada a sibaritas de todo el mundo. Cocina contemporánea.', 'restauranteBlossom.jpg', 'https://blossommalaga.com/menu/', '5', '3', 30.00, 'https://g.page/blossomrestaurante?share'),
	(13, 'Sabor a Nápoles', 'En este acogedor restaurante podrás teletransportarte al magnífico sabor típico italiano. Disfruta de nuestra gastronomía variada.', 'restauranteSN.jpg', 'https://saboranapoles.negocio.site/', '3', '3', 15.00, 'https://g.page/Saboranapoles?share'),
	(14, 'AquaMijas', 'Una docena de atracciones para todas las edades en un espacio único en la Costa del Sol, entre Mijas y Fuengirola. ¡Pásalo en grande en AquaMijas! Diversión asegurada en la Costa del Sol.', 'aquamijas.jpg', 'https://aquamijas.com/', '3', '2', 22.00, 'https://g.page/parqueaquamijas?share'),
	(15, 'SouldPark Fuengirola', 'Sould Park, parques de atracciones infantiles por todo el territorio español. Ocio asegurado para toda la familia. ¡Descubre tu Sould Park más cercano!', 'souldpark.jpg', 'https://www.souldpark.com/', '2', '2', 10.00, 'https://goo.gl/maps/9ZhLBq8w9fausjmH9');
/*!40000 ALTER TABLE `actividades` ENABLE KEYS */;

-- Volcando estructura para tabla packsturisticos.detalle_packs
CREATE TABLE IF NOT EXISTS `detalle_packs` (
  `id_pack` int(10) unsigned NOT NULL,
  `num_linea` int(2) NOT NULL,
  `id_actividad` int(4) NOT NULL,
  `num_plazas` int(2) DEFAULT NULL,
  `fecha_inicio` datetime DEFAULT NULL,
  `fecha_final` datetime DEFAULT NULL,
  `duracion` time DEFAULT NULL,
  PRIMARY KEY (`id_pack`,`num_linea`),
  KEY `fk_pack_actividad_actividad1_idx` (`id_actividad`),
  KEY `fk_pack_actividad_pack1_idx` (`id_pack`),
  CONSTRAINT `fk_pack_actividad_actividad1` FOREIGN KEY (`id_actividad`) REFERENCES `actividades` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pack_actividad_pack1` FOREIGN KEY (`id_pack`) REFERENCES `packs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Volcando datos para la tabla packsturisticos.detalle_packs: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `detalle_packs` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_packs` ENABLE KEYS */;

-- Volcando estructura para tabla packsturisticos.packs
CREATE TABLE IF NOT EXISTS `packs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT 'Mi Pack',
  `descripcion` text DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL,
  `fecha_inicio` datetime NOT NULL,
  `fecha_final` datetime NOT NULL,
  `id_usuario` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pack_usuario_idx` (`id_usuario`),
  CONSTRAINT `fk_pack_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Volcando datos para la tabla packsturisticos.packs: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `packs` DISABLE KEYS */;
/*!40000 ALTER TABLE `packs` ENABLE KEYS */;

-- Volcando estructura para tabla packsturisticos.tipos
CREATE TABLE IF NOT EXISTS `tipos` (
  `codigo` varchar(10) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Volcando datos para la tabla packsturisticos.tipos: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `tipos` DISABLE KEYS */;
INSERT IGNORE INTO `tipos` (`codigo`, `nombre`) VALUES
	('1', 'Alojamiento'),
	('2', 'Ocio'),
	('3', 'Restaurante');
/*!40000 ALTER TABLE `tipos` ENABLE KEYS */;

-- Volcando estructura para tabla packsturisticos.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `rol` enum('admin','cliente') NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `mail` varchar(45) NOT NULL,
  `telefono` int(9) unsigned DEFAULT NULL,
  `tarjeta` int(10) unsigned DEFAULT NULL,
  `contraseña` varchar(50) NOT NULL,
  `date_expire` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Volcando datos para la tabla packsturisticos.usuarios: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT IGNORE INTO `usuarios` (`id`, `rol`, `nombre`, `mail`, `telefono`, `tarjeta`, `contraseña`, `date_expire`) VALUES
	(1, 'admin', 'admin', 'admin@gmail.com', 685673598, 1234567890, '21232f297a57a5a743894a0e4a801fc3', '0000-00-00');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
