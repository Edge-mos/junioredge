CREATE TABLE type(
type_id SERIAL PRIMARY KEY,
product_type VARCHAR(30)
);

CREATE TABLE product(
id SERIAL PRIMARY KEY,
name VARCHAR(30),
type_id INT REFERENCES type(type_id),
expired_date DATE,
price NUMERIC(6, 2),
quantity INT
);

INSERT INTO type(product_type)
VALUES('сыр'), ('молоко'), ('масло'), ('мясо'), ('мороженое'), ('фрукты'), ('овощи');

INSERT INTO product(name, type_id, expired_date, price, quantity)
VALUES 
('Ламбер твёрдый', 1, '2018-10-19', 660.00, 57),
('Домик в деревне', 2, '2018-09-25', 82.90, 43),
('Аланталь', 3, '2018-11-25', 99.90, 5280),
('Отмороженное', 5, '2019-01-25', 98.50, 99),
('Киприно', 1, '2018-10-23', 329.00, 150),
('Первая свежесть', 4, '2018-09-25', 189.90, 329),
('Ближние горки', 4, '2018-09-21', 269.00, 30),
('Бананы связка', 6, '2018-09-22', 54.90, 300),
('Картофель красный', 7, '2018-10-20', 105.00, 15),
('Movenpick мороженое', 5, '2018-12-01', 105.00, 9),
('Дыня Торпеда', 6, '2018-09-30', 34.90, 5),
('Огурцы гладкие', 7, '2018-09-25', 34.90, 9999),
('Бургер Мираторг', 4, '2018-10-20', 229.00, 8);

SELECT * FROM product;

-- 1. Написать запрос получение всех продуктов с типом "СЫР"
SELECT p.name, t.product_type
FROM product AS p
INNER JOIN
type AS t
ON p.type_id = t.type_id
WHERE t.product_type = 'сыр';

-- 2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное"
SELECT name
FROM product
WHERE name LIKE '%мороженное%'  OR name LIKE '%мороженое%';

-- 3. Написать запрос, который выводит все продукты, срок годности 
-- которых заканчивается в следующем месяце.
SELECT name, expired_date
FROM product
WHERE expired_date BETWEEN '2018-10-01' AND '2018-10-31';

-- 4. Написать запрос, который выводит самый дорогой продукт.
SELECT name, MAX(price) AS max_price
FROM product
GROUP BY name
ORDER BY max_price DESC
LIMIT 1;

-- 5. Написать запрос, который выводит количество всех продуктов определенного типа.
SELECT p.name, p.quantity
FROM product AS p
INNER JOIN
type AS t
ON
p.type_id = t.type_id
WHERE t.product_type = 'овощи';

-- или второй вариант, который суммирует количество всех продуктов по типу
SELECT t.product_type, SUM(p.quantity) AS Total
FROM product AS p
INNER JOIN
type AS t
ON
p.type_id = t.type_id
GROUP BY t.product_type
ORDER BY Total;

-- 6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО".
SELECT p.name, t.product_type
FROM product AS p
INNER JOIN
type AS t
ON
p.type_id = t.type_id
WHERE t.product_type IN ('сыр', 'молоко');

-- 7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.  
SELECT t.product_type, p.quantity
FROM product AS p
INNER JOIN
type AS t
ON
p.type_id = t.type_id
WHERE p.quantity < 10;

-- 8. Вывести все продукты и их тип.
SELECT p.name, t.product_type AS Тип_продукта
FROM product AS p
INNER JOIN
type AS t
ON
p.type_id = t.type_id;