CREATE TABLE clientes (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(255),
    celular VARCHAR(20),
    endereco VARCHAR(255),
    email VARCHAR(255),
    senha VARCHAR(255)
);

CREATE TABLE produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255),
    preco INT
);

CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_cpf VARCHAR(11),
    data_hora_pagamento TIMESTAMP,
    status VARCHAR(50),
    valor DOUBLE,
    desconto DOUBLE,
    impostos DOUBLE,
    valor_cobrado DOUBLE,
    FOREIGN KEY (cliente_cpf) REFERENCES clientes(cpf)
);

CREATE TABLE itens_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT,
    produto_id BIGINT,
    quantidade INT,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
);