-- 1. Marcar como OCCUPIED todas as mesas que possuem comandas ativas
UPDATE restaurant_tables 
SET status = 'OCCUPIED' 
WHERE table_number IN (SELECT table_number FROM orders WHERE active = true);

-- 2. Garantir que mesas sem comandas ativas estejam AVAILABLE
UPDATE restaurant_tables 
SET status = 'AVAILABLE' 
WHERE table_number NOT IN (SELECT table_number FROM orders WHERE active = true);

-- 3. Inserir mais algumas mesas para teste
INSERT INTO restaurant_tables (table_number, status) VALUES (7, 'AVAILABLE');
INSERT INTO restaurant_tables (table_number, status) VALUES (8, 'AVAILABLE');
INSERT INTO restaurant_tables (table_number, status) VALUES (9, 'AVAILABLE');
INSERT INTO restaurant_tables (table_number, status) VALUES (10, 'AVAILABLE');

-- 4. Adicionar datas de criação e atualização para itens que não possuem (evitar nulls se a JPA não gerenciar)
UPDATE order_items SET create_at = NOW() WHERE create_at IS NULL;
UPDATE order_items SET update_at = NOW() WHERE update_at IS NULL;

-- 5. Ajustar preços de itens que possuem adicionais mas o preço salvo no V2 não reflete a soma
-- Exemplo: Item 22220001 (X-Burguer 35.90 + Bacon 5.00 + Cheddar 4.00 = 44.90) - Já está correto no V2, mas garantindo consistência
UPDATE order_items oi
SET price = (
    SELECT mi.price + COALESCE(SUM(ao.price), 0)
    FROM menu_items mi
    LEFT JOIN order_item_addons oia ON oia.order_item_id = oi.id
    LEFT JOIN add_ons ao ON ao.id = oia.add_on_id
    WHERE mi.id = oi.menu_item_id
    GROUP BY mi.price
)
WHERE id IN (SELECT order_item_id FROM order_item_addons);
