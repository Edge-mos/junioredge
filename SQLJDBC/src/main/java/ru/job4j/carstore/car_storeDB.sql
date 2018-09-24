CREATE TABLE car_body(
body_id SERIAL PRIMARY KEY,
body_desc VARCHAR(30)
);

CREATE TABLE engine(
engine_id SERIAL PRIMARY KEY,
engine_desc VARCHAR(30)
);

CREATE TABLE transmission(
transmission_id SERIAL PRIMARY KEY,
transmission_desc VARCHAR(30)
);

CREATE TABLE car_model(
id SERIAL PRIMARY KEY,
car_model VARCHAR(50),
body INT REFERENCES car_body(body_id),
engine INT REFERENCES engine(engine_id),
transmission INT REFERENCES transmission(transmission_id)
);

INSERT INTO car_body(body_desc)
VALUES ('G11'), ('W222'), ('J200 suv 11'), ('4MB');

INSERT INTO engine(engine_desc)
VALUES('B48'), ('S65 AMG'), ('2UZ-FE'), ('CREC turbo');

INSERT INTO transmission(transmission_desc)
VALUES('ZF 8HP50'), ('9G-Tronic'), ('AB60F'), ('3.0 TFSI quattro tiptronic');

INSERT INTO car_model(car_model, body, engine, transmission)
VALUES('BMW 730i', 1, 1, 1), ('Mercedes-Benz W222', 2, 2, 2), 
('Toyota Land Cruiser 200', 3, 3, 3);

INSERT INTO car_model(car_model)
VALUES (NULL);

-- 1. Вывести список всех машин и все привязанные к ним детали.

SELECT cm.car_model, cb.body_desc, e.engine_desc, t.transmission_desc
FROM car_model AS cm
INNER JOIN
car_body AS cb
ON cm.body = cb.body_id
INNER JOIN
engine AS e
ON cm.engine = e.engine_id
INNER JOIN
transmission AS t
ON cm.transmission = t.transmission_id;

-- 2. Вывести отдельно детали, которые не используются в машине- 
-- кузова, двигатели, коробки передач.

-- 1 вариант на подзапросах, нерациональный
SELECT cb.body_desc, e.engine_desc, t.transmission_desc
FROM car_body AS cb, engine AS e, transmission AS t
WHERE
NOT EXISTS(
SELECT cm.id FROM car_model AS cm WHERE cb.body_id = cm.id)
AND
NOT EXISTS(
SELECT cm.id FROM car_model AS cm WHERE e.engine_id = cm.id)
AND
NOT EXISTS(
SELECT cm.id FROM car_model AS cm WHERE t.transmission_id = cm.id
);

-- 2 вариант на союзах и соединениях
SELECT cb.body_desc AS detail_not_in_use
FROM car_body AS cb
LEFT JOIN
car_model AS cm
ON cm.body = cb.body_id
WHERE cm.car_model IS NULL
UNION ALL
SELECT e.engine_desc
FROM engine AS e
LEFT JOIN
car_model AS cm
ON e.engine_id = cm.engine
WHERE cm.car_model IS NULL
UNION ALL
SELECT t.transmission_desc
FROM transmission AS t
LEFT JOIN
car_model AS cm
ON t.transmission_id = cm.transmission
WHERE cm.car_model IS NULL;










