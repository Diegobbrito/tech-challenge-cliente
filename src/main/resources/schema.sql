CREATE TABLE clientes (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    cpf VARCHAR(20) NOT NULL,
    nome VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL
);