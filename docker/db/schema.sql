CREATE DATABASE IF NOT EXISTS lanchonete;
USE lanchonete;

CREATE TABLE clientes (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    cpf VARCHAR(20) NOT NULL,
    nome VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    endereco VARCHAR(200),
    telefone VARCHAR(15),
    active BOOLEAN
);