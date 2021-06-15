
CREATE SCHEMA IF NOT EXISTS `packsTuristicos` DEFAULT CHARACTER SET utf8 ;
USE `packsTuristicos` ;


CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `rol` ENUM('admin', 'cliente') NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `mail` VARCHAR(45) NOT NULL,
  `telefono` INT(9) UNSIGNED NULL,
  `tarjeta` INT(20) UNSIGNED NULL,
  `contraseña` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;



CREATE TABLE IF NOT EXISTS `packs` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT 'Mi Pack',
  `descripcion` TEXT NULL,
  `fecha_creacion` DATETIME NOT NULL,
  `fecha_inicio` DATETIME NOT NULL,
  `fecha_final` DATETIME NOT NULL,
  `id_usuario` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_pack_usuario_idx` (`id_usuario` ASC) ,
  CONSTRAINT `fk_pack_usuario`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `usuarios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



CREATE TABLE IF NOT EXISTS `tipos` (
  `codigo` VARCHAR(10) NOT NULL,
  `nombre` VARCHAR(45) NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;




CREATE TABLE IF NOT EXISTS `actividades` (
  `id` INT(4) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` TEXT NULL,
  `imagen` VARCHAR(45) NULL,
  `url` VARCHAR(200) NULL,
  `calidad` ENUM('1', '2', '3', '4', '5') NULL,
  `tipo` VARCHAR(10) NOT NULL,
  `precio` DECIMAL(6,2) NULL,
  `ubicacion` VARCHAR(150) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_actividad_tipo1_idx` (`tipo` ASC) ,
  CONSTRAINT `fk_actividad_tipo1`
    FOREIGN KEY (`tipo`)
    REFERENCES `tipos` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



CREATE TABLE IF NOT EXISTS `detalle_packs` (
  `id_pack` INT(10) UNSIGNED NOT NULL,
  `num_linea` INT(2) NOT NULL,
  `id_actividad` INT(4) NOT NULL,
  `num_plazas` INT(2) NULL,
  `fecha_inicio` DATETIME NULL,
  `fecha_final` DATETIME NULL,
  `duracion` TIME NULL,
  PRIMARY KEY (`id_pack`, `num_linea`),
  INDEX `fk_pack_actividad_actividad1_idx` (`id_actividad` ASC) ,
  INDEX `fk_pack_actividad_pack1_idx` (`id_pack` ASC) ,
  CONSTRAINT `fk_pack_actividad_pack1`
    FOREIGN KEY (`id_pack`)
    REFERENCES `packs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pack_actividad_actividad1`
    FOREIGN KEY (`id_actividad`)
    REFERENCES `actividades` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


