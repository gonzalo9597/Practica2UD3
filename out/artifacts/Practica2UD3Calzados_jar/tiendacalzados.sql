DROP DATABASE IF EXISTS tiendacalzados;
CREATE DATABASE IF NOT EXISTS tiendacalzados;
USE tiendacalzados;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `existeSku` (`f_sku` VARCHAR(40)) RETURNS BIT(1)
BEGIN
    DECLARE i INT;
    SET i = 0;

    WHILE ( i < (SELECT MAX(idcalzado) FROM calzado) ) DO
        IF ( (SELECT sku FROM calzado WHERE idcalzado = (i + 1)) LIKE f_sku ) THEN
            RETURN 1;
        END IF;
        SET i = i + 1;
    END WHILE;

    RETURN 0;
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `existeNombreComprador` (`f_name` VARCHAR(202)) RETURNS BIT(1)
BEGIN
    DECLARE i INT;
    SET i = 0;

    WHILE ( i < (SELECT MAX(idcomprador) FROM comprador) ) DO
        IF ( (SELECT CONCAT(apellidos, ', ', nombre) FROM comprador WHERE idcomprador = (i + 1)) LIKE f_name ) THEN
            RETURN 1;
        END IF;
        SET i = i + 1;
    END WHILE;

    RETURN 0;
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `existeNombreMarca` (`f_name` VARCHAR(50)) RETURNS BIT(1)
BEGIN
    DECLARE i INT;
    SET i = 0;

    WHILE ( i < (SELECT MAX(idmarca) FROM marca) ) DO
        IF ( (SELECT nombre FROM marca WHERE idmarca = (i + 1)) LIKE f_name ) THEN
            RETURN 1;
        END IF;
        SET i = i + 1;
    END WHILE;

    RETURN 0;
END$$

DELIMITER ;

CREATE TABLE `comprador` (
  `idcomprador` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(150) NOT NULL,
  `dni` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `comprador` (`idcomprador`, `nombre`, `apellidos`, `dni`) VALUES
(1, 'Marc', 'Gual', '535444201W'),
(2, 'Raul', 'Guti', '18568541J'),
(3, 'Josep', 'Pedrerol', '995487562D');

CREATE TABLE `comprador_calzado` (
  `idcompradorcalzado` int(11) NOT NULL,
  `idcomprador` int(11) DEFAULT NULL,
  `idcalzado` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `comprador_tienda` (
  `idcompradortienda` int(11) NOT NULL,
  `idcomprador` int(11) DEFAULT NULL,
  `idtienda` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `marca` (
  `idmarca` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `telefono` varchar(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `marca` (`idmarca`, `nombre`, `telefono`) VALUES
(3, 'Adidas', '654321654'),
(5, 'Nike', '62421531'),
(6, 'Puma', '632123451'),
(7, 'Converse', '601535486');

-- "calzado" reemplaza a "puzzle"
CREATE TABLE `calzado` (
  `idcalzado` int(11) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `sku` varchar(40) NOT NULL,
  `idmarca` int(11) DEFAULT NULL,
  `idcomprador` int(11) DEFAULT NULL,
  `idtienda` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `calzado` (`idcalzado`, `modelo`, `sku`, `idmarca`, `idcomprador`, `idtienda`) VALUES
(10, 'Zapatilla Runner Pro', '1', 7, NULL, NULL),
(11, 'Bota Sierra X', '2', 7, NULL, NULL),
(12, 'Sandalia Urban', '3', 7, NULL, NULL),
(15, 'Mocasín Classic', '4', 7, NULL, NULL),
(16, 'Zapatilla Nevada', '5', 3, NULL, NULL);

CREATE TABLE `calzado_tienda` (
  `idcalzadotienda` int(11) NOT NULL,
  `idcalzado` int(11) DEFAULT NULL,
  `idtienda` int(11) DEFAULT NULL,
  `precio` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `calzado_tienda` (`idcalzadotienda`, `idcalzado`, `idtienda`, `precio`) VALUES
(10, 11, 5, 15),
(11, 12, 13, 14),
(12, 10, 5, 10);

CREATE TABLE `tienda` (
  `idtienda` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `telefono` varchar(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `tienda` (`idtienda`, `nombre`, `telefono`) VALUES
(5, 'Nike Puerto Venecia', '987654321'),
(13, 'Todo Deporte Torre Outlet', '976542135');

ALTER TABLE `comprador`
  ADD PRIMARY KEY (`idcomprador`),
  ADD UNIQUE KEY `dni` (`dni`);

ALTER TABLE `comprador_calzado`
  ADD PRIMARY KEY (`idcompradorcalzado`);

ALTER TABLE `comprador_tienda`
  ADD PRIMARY KEY (`idcompradortienda`);

ALTER TABLE `marca`
  ADD PRIMARY KEY (`idmarca`);

ALTER TABLE `calzado`
  ADD PRIMARY KEY (`idcalzado`),
  ADD UNIQUE KEY `sku` (`sku`),
  ADD KEY `idmarca` (`idmarca`);

ALTER TABLE `calzado_tienda`
  ADD PRIMARY KEY (`idcalzadotienda`);

ALTER TABLE `tienda`
  ADD PRIMARY KEY (`idtienda`);

ALTER TABLE `comprador`
  MODIFY `idcomprador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `comprador_calzado`
  MODIFY `idcompradorcalzado` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `comprador_tienda`
  MODIFY `idcompradortienda` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `marca`
  MODIFY `idmarca` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

ALTER TABLE `calzado`
  MODIFY `idcalzado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

ALTER TABLE `calzado_tienda`
  MODIFY `idcalzadotienda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

ALTER TABLE `tienda`
  MODIFY `idtienda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

ALTER TABLE `calzado`
  ADD CONSTRAINT `calzado_ibfk_1` FOREIGN KEY (`idmarca`) REFERENCES `marca` (`idmarca`);

COMMIT;
