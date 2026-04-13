CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    username VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS restaurant_tables (
    table_number INTEGER PRIMARY KEY,
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS menu_items (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    price DOUBLE PRECISION,
    available_quantity INTEGER
);

CREATE TABLE IF NOT EXISTS add_ons (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY,
    table_number INTEGER REFERENCES restaurant_tables(table_number),
    user_id UUID REFERENCES users(id),
    created_at TIMESTAMP,
    active BOOLEAN
);

CREATE TABLE IF NOT EXISTS order_items (
    id UUID PRIMARY KEY,
    order_id UUID REFERENCES orders(id),
    menu_item_id UUID REFERENCES menu_items(id),
    waiter_id UUID REFERENCES users(id),
    observations VARCHAR(255),
    status VARCHAR(50),
    price DOUBLE PRECISION,
    create_at TIMESTAMP,
    update_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_item_addons (
    order_item_id UUID REFERENCES order_items(id),
    add_on_id UUID REFERENCES add_ons(id)
);