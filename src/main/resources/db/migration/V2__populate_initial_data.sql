-- Usuários
INSERT INTO users (id, name, username, email, password) VALUES ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'João Silva', 'joao', 'joao@gmail.com', '1234');
INSERT INTO users (id, name, username, email, password) VALUES ('b2c3d4e5-f6a7-8901-bcde-f12345678901', 'Maria Santos', 'maria', 'maria@gmail.com', '1234');
INSERT INTO users (id, name, username, email, password) VALUES ('c3d4e5f6-a7b8-9012-cdef-123456789012', 'Pedro Costa', 'pedro', 'pedro@gmail.com', '1234');

-- Mesas
INSERT INTO restaurant_tables (table_number, status) VALUES (1, 'AVAILABLE');
INSERT INTO restaurant_tables (table_number, status) VALUES (2, 'AVAILABLE');
INSERT INTO restaurant_tables (table_number, status) VALUES (3, 'AVAILABLE');
INSERT INTO restaurant_tables (table_number, status) VALUES (4, 'AVAILABLE');
INSERT INTO restaurant_tables (table_number, status) VALUES (5, 'AVAILABLE');
INSERT INTO restaurant_tables (table_number, status) VALUES (6, 'AVAILABLE');

-- Menu items
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES ('d4e5f6a7-b8c9-0123-defa-234567890123', 'X-Burguer', 'Hamburguer artesanal', 35.90, 20);
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES ('e5f6a7b8-c9d0-1234-efab-345678901234', 'Frango Grelhado', 'File de frango grelhado', 28.90, 15);
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES ('f6a7b8c9-d0e1-2345-fabc-456789012345', 'Batata Frita', 'Porcao de batata frita', 18.90, 20);
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES ('a7b8c9d0-e1f2-3456-abcd-567890123456', 'Refrigerante', 'Lata 350ml', 8.90, 30);
INSERT INTO menu_items (id, name, description, price, available_quantity) VALUES ('b8c9d0e1-f2a3-4567-bcde-678901234567', 'Suco Natural', 'Suco de laranja 500ml', 12.90, 15);

-- Adicionais
INSERT INTO add_ons (id, name, price) VALUES ('c9d0e1f2-a3b4-5678-cdef-789012345678', 'Bacon Extra', 5.00);
INSERT INTO add_ons (id, name, price) VALUES ('d0e1f2a3-b4c5-6789-defa-890123456789', 'Queijo Extra', 3.00);
INSERT INTO add_ons (id, name, price) VALUES ('e1f2a3b4-c5d6-7890-efab-901234567890', 'Molho Especial', 2.00);
INSERT INTO add_ons (id, name, price) VALUES ('f2a3b4c5-d6e7-8901-fabc-012345678901', 'Ovo Frito', 3.50);
INSERT INTO add_ons (id, name, price) VALUES ('a3b4c5d6-e7f8-9012-abcd-123456789012', 'Cheddar', 4.00);

-- Comandas

INSERT INTO orders (id, table_number, user_id, created_at, active) VALUES ('b4c5d6e7-f8a9-0123-bcde-234567890123', 1, 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', NOW(), true);
INSERT INTO orders (id, table_number, user_id, created_at, active) VALUES ('c5d6e7f8-a9b0-1234-cdef-345678901234', 2, 'b2c3d4e5-f6a7-8901-bcde-f12345678901', NOW(), true);
INSERT INTO orders (id, table_number, user_id, created_at, active) VALUES ('d6e7f8a9-b0c1-2345-defa-456789012345', 3, 'c3d4e5f6-a7b8-9012-cdef-123456789012', NOW(), true);
INSERT INTO orders (id, table_number, user_id, created_at, active) VALUES ('e7f8a9b0-c1d2-3456-efab-567890123456', 4, 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', NOW(), true);
INSERT INTO orders (id, table_number, user_id, created_at, active) VALUES ('f8a9b0c1-d2e3-4567-fabc-678901234568', 5, 'b2c3d4e5-f6a7-8901-bcde-f12345678901', NOW(), true);

-- Itens da comanda 1
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('11110001-1111-1111-1111-111111111111', 'b4c5d6e7-f8a9-0123-bcde-234567890123', 'e5f6a7b8-c9d0-1234-efab-345678901234', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '', 'PENDING', 28.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('11110002-1111-1111-1111-111111111111', 'b4c5d6e7-f8a9-0123-bcde-234567890123', 'f6a7b8c9-d0e1-2345-fabc-456789012345', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '', 'PENDING', 18.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('11110003-1111-1111-1111-111111111111', 'b4c5d6e7-f8a9-0123-bcde-234567890123', 'a7b8c9d0-e1f2-3456-abcd-567890123456', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '', 'PENDING', 8.90);

-- Itens da comanda 2
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('22220001-2222-2222-2222-222222222222', 'c5d6e7f8-a9b0-1234-cdef-345678901234', 'd4e5f6a7-b8c9-0123-defa-234567890123', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', 'sem cebola', 'PREPARATION', 44.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('22220002-2222-2222-2222-222222222222', 'c5d6e7f8-a9b0-1234-cdef-345678901234', 'e5f6a7b8-c9d0-1234-efab-345678901234', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', '', 'PENDING', 30.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('22220003-2222-2222-2222-222222222222', 'c5d6e7f8-a9b0-1234-cdef-345678901234', 'f6a7b8c9-d0e1-2345-fabc-456789012345', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', '', 'PENDING', 18.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('22220004-2222-2222-2222-222222222222', 'c5d6e7f8-a9b0-1234-cdef-345678901234', 'b8c9d0e1-f2a3-4567-bcde-678901234567', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', '', 'FINISHED', 12.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('22220005-2222-2222-2222-222222222222', 'c5d6e7f8-a9b0-1234-cdef-345678901234', 'a7b8c9d0-e1f2-3456-abcd-567890123456', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', '', 'PENDING', 8.90);

-- Adicionais da comanda 2
INSERT INTO order_item_addons (order_item_id, add_on_id) VALUES ('22220001-2222-2222-2222-222222222222', 'c9d0e1f2-a3b4-5678-cdef-789012345678'); -- X-Burguer + Bacon Extra
INSERT INTO order_item_addons (order_item_id, add_on_id) VALUES ('22220001-2222-2222-2222-222222222222', 'a3b4c5d6-e7f8-9012-abcd-123456789012'); -- X-Burguer + Cheddar
INSERT INTO order_item_addons (order_item_id, add_on_id) VALUES ('22220002-2222-2222-2222-222222222222', 'e1f2a3b4-c5d6-7890-efab-901234567890'); -- Frango + Molho Especial

-- Itens da comanda 3
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('33330001-3333-3333-3333-333333333333', 'd6e7f8a9-b0c1-2345-defa-456789012345', 'd4e5f6a7-b8c9-0123-defa-234567890123', 'c3d4e5f6-a7b8-9012-cdef-123456789012', 'bem passado', 'FINISHED', 42.40);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('33330002-3333-3333-3333-333333333333', 'd6e7f8a9-b0c1-2345-defa-456789012345', 'd4e5f6a7-b8c9-0123-defa-234567890123', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '', 'PREPARATION', 40.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('33330003-3333-3333-3333-333333333333', 'd6e7f8a9-b0c1-2345-defa-456789012345', 'd4e5f6a7-b8c9-0123-defa-234567890123', 'c3d4e5f6-a7b8-9012-cdef-123456789012', 'sem molho', 'PENDING', 35.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('33330004-3333-3333-3333-333333333333', 'd6e7f8a9-b0c1-2345-defa-456789012345', 'e5f6a7b8-c9d0-1234-efab-345678901234', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', '', 'FINISHED', 30.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('33330005-3333-3333-3333-333333333333', 'd6e7f8a9-b0c1-2345-defa-456789012345', 'e5f6a7b8-c9d0-1234-efab-345678901234', 'c3d4e5f6-a7b8-9012-cdef-123456789012', 'sem pimenta', 'PENDING', 28.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('33330006-3333-3333-3333-333333333333', 'd6e7f8a9-b0c1-2345-defa-456789012345', 'f6a7b8c9-d0e1-2345-fabc-456789012345', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '', 'PENDING', 18.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('33330007-3333-3333-3333-333333333333', 'd6e7f8a9-b0c1-2345-defa-456789012345', 'a7b8c9d0-e1f2-3456-abcd-567890123456', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', '', 'PENDING', 8.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('33330008-3333-3333-3333-333333333333', 'd6e7f8a9-b0c1-2345-defa-456789012345', 'b8c9d0e1-f2a3-4567-bcde-678901234567', 'c3d4e5f6-a7b8-9012-cdef-123456789012', '', 'PENDING', 12.90);


-- Itens da comanda 4
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('44440001-4444-4444-4444-444444444444', 'e7f8a9b0-c1d2-3456-efab-567890123456', 'd4e5f6a7-b8c9-0123-defa-234567890123', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'ponto medio', 'FINISHED', 43.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('44440002-4444-4444-4444-444444444444', 'e7f8a9b0-c1d2-3456-efab-567890123456', 'd4e5f6a7-b8c9-0123-defa-234567890123', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', '', 'PREPARATION', 39.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('44440003-4444-4444-4444-444444444444', 'e7f8a9b0-c1d2-3456-efab-567890123456', 'd4e5f6a7-b8c9-0123-defa-234567890123', 'c3d4e5f6-a7b8-9012-cdef-123456789012', 'sem cebola', 'PENDING', 39.40);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('44440004-4444-4444-4444-444444444444', 'e7f8a9b0-c1d2-3456-efab-567890123456', 'd4e5f6a7-b8c9-0123-defa-234567890123', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '', 'PENDING', 35.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('44440005-4444-4444-4444-444444444444', 'e7f8a9b0-c1d2-3456-efab-567890123456', 'e5f6a7b8-c9d0-1234-efab-345678901234', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', '', 'FINISHED', 30.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('44440006-4444-4444-4444-444444444444', 'e7f8a9b0-c1d2-3456-efab-567890123456', 'e5f6a7b8-c9d0-1234-efab-345678901234', 'c3d4e5f6-a7b8-9012-cdef-123456789012', 'bem grelhado', 'PENDING', 28.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('44440007-4444-4444-4444-444444444444', 'e7f8a9b0-c1d2-3456-efab-567890123456', 'f6a7b8c9-d0e1-2345-fabc-456789012345', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '', 'PENDING', 18.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('44440008-4444-4444-4444-444444444444', 'e7f8a9b0-c1d2-3456-efab-567890123456', 'f6a7b8c9-d0e1-2345-fabc-456789012345', 'b2c3d4e5-f6a7-8901-bcde-f12345678901', 'crocante', 'PENDING', 18.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('44440009-4444-4444-4444-444444444444', 'e7f8a9b0-c1d2-3456-efab-567890123456', 'b8c9d0e1-f2a3-4567-bcde-678901234567', 'c3d4e5f6-a7b8-9012-cdef-123456789012', '', 'PENDING', 12.90);
INSERT INTO order_items (id, order_id, menu_item_id, waiter_id, observations, status, price) VALUES ('44440010-4444-4444-4444-444444444444', 'e7f8a9b0-c1d2-3456-efab-567890123456', 'a7b8c9d0-e1f2-3456-abcd-567890123456', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '', 'PENDING', 8.90);
