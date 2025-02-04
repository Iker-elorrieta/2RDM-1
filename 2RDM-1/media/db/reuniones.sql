-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-02-2025 a las 23:18:34
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `elorbase`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reuniones`
--

CREATE TABLE `reuniones` (
  `id_reunion` int(11) NOT NULL,
  `estado` enum('pendiente','aceptada','denegada','conflicto') DEFAULT NULL,
  `estado_eus` enum('onartzeke','onartuta','ezeztatuta','gatazka') DEFAULT NULL,
  `profesor_id` int(11) NOT NULL,
  `alumno_id` int(11) NOT NULL,
  `id_centro` varchar(6) DEFAULT '15112',
  `titulo` varchar(150) DEFAULT NULL,
  `asunto` varchar(200) DEFAULT NULL,
  `aula` varchar(10) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reuniones`
--

INSERT INTO `reuniones` (`id_reunion`, `estado`, `estado_eus`, `profesor_id`, `alumno_id`, `id_centro`, `titulo`, `asunto`, `aula`, `fecha`) VALUES
(1, 'aceptada', NULL, 1, 3, '15112', 'Seguimiento reto', 'Sprint 3', '5.105', '2025-01-13 11:30:00'),
(2, 'pendiente', NULL, 1, 3, '15112', 'Reto 3', 'Prueba reto 3', '5.105', '2025-02-05 12:30:00'),
(3, 'conflicto', NULL, 1, 3, '15112', 'Examen marcas', 'bla blab bal', '5.105', '2025-02-07 11:30:00');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `reuniones`
--
ALTER TABLE `reuniones`
  ADD PRIMARY KEY (`id_reunion`),
  ADD KEY `profesor_id` (`profesor_id`),
  ADD KEY `alumno_id` (`alumno_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `reuniones`
--
ALTER TABLE `reuniones`
  MODIFY `id_reunion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
