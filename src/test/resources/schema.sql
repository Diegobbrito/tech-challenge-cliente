CREATE TABLE clientes (
    id INT NOT NULL AUTO_INCREMENT,
    cpf VARCHAR(20),
    nome VARCHAR(200),
    email VARCHAR(200),
    PRIMARY KEY (id),
    UNIQUE KEY (cpf),
    UNIQUE KEY (email)
);