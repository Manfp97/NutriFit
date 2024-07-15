-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)

--

-- Host: localhost    Database: Nutrifit

-- ------------------------------------------------------

-- Server version   8.0.31



create database Nutrifit;
CREATE TABLE `cliente` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `nombre` varchar(45) NOT NULL DEFAULT '"sin nombre"',
                           `edad` tinyint DEFAULT NULL,
                           `direccion` varchar(80) DEFAULT 'null',
                           `dni` varchar(45) DEFAULT 'null',
                           `emilio` varchar(45) DEFAULT 'null',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
