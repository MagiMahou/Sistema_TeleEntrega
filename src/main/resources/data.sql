-- Inserção dos clientes com senha
INSERT INTO clientes (cpf, nome, celular, endereco, email, senha) VALUES ('9001', 'Huguinho Pato', '51985744566', 'Rua das Flores, 100', 'huguinho.pato@email.com', 'senha123');
INSERT INTO clientes (cpf, nome, celular, endereco, email, senha) VALUES ('9002', 'Luizinho Pato', '5199172079', 'Av. Central, 200', 'zezinho.pato@email.com', 'senha123');

-- Inserção dos ingredientes (Pizzas e Bebidas)
INSERT INTO ingredientes (id, descricao) VALUES (1, 'Disco de pizza');
INSERT INTO ingredientes (id, descricao) VALUES (2, 'Porcao de tomate');
INSERT INTO ingredientes (id, descricao) VALUES (3, 'Porcao de mussarela');
INSERT INTO ingredientes (id, descricao) VALUES (4, 'Porcao de presunto');
INSERT INTO ingredientes (id, descricao) VALUES (5, 'Porcao de calabresa');
INSERT INTO ingredientes (id, descricao) VALUES (6, 'Molho de tomate (200ml)');
INSERT INTO ingredientes (id, descricao) VALUES (7, 'Porcao de azeitona');
INSERT INTO ingredientes (id, descricao) VALUES (8, 'Porcao de oregano');
INSERT INTO ingredientes (id, descricao) VALUES (9, 'Porcao de cebola');
INSERT INTO ingredientes (id, descricao) VALUES (10, 'Lata de Coca-Cola 350ml');
INSERT INTO ingredientes (id, descricao) VALUES (11, 'Garrafa de Água Mineral 500ml');
INSERT INTO ingredientes (id, descricao) VALUES (12, 'Lata de Guaraná Antarctica 350ml');

-- Inserção dos itens de estoque (CORRIGIDO PARA itens_estoque)
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (1, 30, 1);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (2, 30, 2);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (3, 30, 3);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (4, 30, 4);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (5, 30, 5);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (6, 30, 6);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (7, 30, 7);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (8, 30, 8);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (9, 30, 9);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (10, 50, 10);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (11, 50, 11);
INSERT INTO itens_estoque (id, quantidade, ingrediente_id) VALUES (12, 50, 12);

-- Inserção das receitas (Pizzas e Bebidas)
INSERT INTO receitas (id, titulo) VALUES (1, 'Pizza calabresa');
INSERT INTO receitas (id, titulo) VALUES (2, 'Pizza queijo e presunto');
INSERT INTO receitas (id, titulo) VALUES (3, 'Pizza margherita');
INSERT INTO receitas (id, titulo) VALUES (4, 'Lata de Coca-Cola');
INSERT INTO receitas (id, titulo) VALUES (5, 'Garrafa de Água');
INSERT INTO receitas (id, titulo) VALUES (6, 'Lata de Guaraná');

-- Associação dos ingredientes às receitas das Pizzas
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 1); 
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 6); 
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 3); 
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (1, 5); 

INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 1); 
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 6); 
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 3); 
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (2, 4); 

INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 1); 
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 6); 
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 3); 
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (3, 8); 

-- Associação dos ingredientes às receitas das Bebidas
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (4, 10);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (5, 11);
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) VALUES (6, 12);

-- Inserção dos produtos
INSERT INTO produtos (id, descricao, preco) VALUES (1, 'Pizza calabresa', 5500);
INSERT INTO produtos (id, descricao, preco) VALUES (2, 'Pizza queijo e presunto', 6000);
INSERT INTO produtos (id, descricao, preco) VALUES (3, 'Pizza margherita', 4000);
INSERT INTO produtos (id, descricao, preco) VALUES (4, 'Coca-Cola Lata', 600);
INSERT INTO produtos (id, descricao, preco) VALUES (5, 'Água Mineral 500ml', 400);
INSERT INTO produtos (id, descricao, preco) VALUES (6, 'Guaraná Antarctica Lata', 550);

-- Associação dos produtos com as receitas
INSERT INTO produto_receita (produto_id, receita_id) VALUES (1, 1);
INSERT INTO produto_receita (produto_id, receita_id) VALUES (2, 2);
INSERT INTO produto_receita (produto_id, receita_id) VALUES (3, 3);
INSERT INTO produto_receita (produto_id, receita_id) VALUES (4, 4);
INSERT INTO produto_receita (produto_id, receita_id) VALUES (5, 5);
INSERT INTO produto_receita (produto_id, receita_id) VALUES (6, 6);

-- Inserção dos cardápios
INSERT INTO cardapios (id, titulo) VALUES (1, 'Cardapio de Agosto');
INSERT INTO cardapios (id, titulo) VALUES (2, 'Cardapio de Setembro');

-- Associação dos cardápios com os produtos
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (1, 1);
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (1, 2);
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (1, 3);
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (1, 4); 
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (1, 5); 
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (1, 6); 

INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (2, 1);
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (2, 3);
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (2, 4); 
INSERT INTO cardapio_produto (cardapio_id, produto_id) VALUES (2, 5);