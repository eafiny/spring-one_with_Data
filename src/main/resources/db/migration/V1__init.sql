BEGIN;

create table categories (id bigserial primary key, title varchar(255));
insert into categories (title) values ('Food');

CREATE TABLE products (id bigserial primary key, title varchar (255), price int, category_id bigint references categories(id));
INSERT INTO products (title, price, category_id) VALUES
('Bread', 25, 1),
('Cheese', 320, 1),
('Water', 30, 1),
('Milk', 80, 1),
('Coca-Cola', 80, 1);

COMMIT;