-- Inserindo mais itens no cardápio (menu_items)
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES (gen_random_uuid(), 'Pizza Margherita', 'Molho de tomate, mussarela e manjericão', 45.00, 30);
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES (gen_random_uuid(), 'Pizza Pepperoni', 'Molho de tomate, mussarela e pepperoni', 52.00, 25);
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES (gen_random_uuid(), 'Hambúrguer de Costela', 'Hambúrguer de costela 200g, queijo prato e cebola caramelizada', 42.90, 20);
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES (gen_random_uuid(), 'Salada Caesar', 'Alface americana, croutons, parmesão e molho caesar', 32.50, 15);
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES (gen_random_uuid(), 'Iscas de Tilápia', 'Porção de tilápia empanada com molho tártaro', 48.90, 12);
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES (gen_random_uuid(), 'Petit Gâteau', 'Bolinho quente de chocolate com sorvete de baunilha', 24.90, 10);
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES (gen_random_uuid(), 'Cerveja Artesanal IPA', 'Lata 473ml', 18.00, 50);
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES (gen_random_uuid(), 'Água Mineral', 'Com ou sem gás 500ml', 5.00, 100);

-- Inserindo mais adicionais (add_ons)
INSERT INTO add_ons (id, name, price) VALUES (gen_random_uuid(), 'Cebola Caramelizada', 4.50);
INSERT INTO add_ons (id, name, price) VALUES (gen_random_uuid(), 'Picles', 2.50);
INSERT INTO add_ons (id, name, price) VALUES (gen_random_uuid(), 'Maionese de Ervas', 3.00);
INSERT INTO add_ons (id, name, price) VALUES (gen_random_uuid(), 'Borda Recheada de Catupiry', 8.00);
INSERT INTO add_ons (id, name, price) VALUES (gen_random_uuid(), 'Pepperoni Extra', 6.00);
INSERT INTO add_ons (id, name, price) VALUES (gen_random_uuid(), 'Sorvete de Baunilha (Bola Extra)', 7.00);
