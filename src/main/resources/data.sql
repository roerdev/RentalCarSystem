drop table if exists rental;
drop table if exists car;
drop table if exists price_condition;
drop table if exists client;
drop table if exists type_car;
drop table if exists status_car;

CREATE TABLE status_car
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    status VARCHAR(20),
    CONSTRAINT pk_statuscar PRIMARY KEY (id)
);

CREATE TABLE type_car
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name            VARCHAR(20)                            NOT NULL,
    price           numeric(38,2),
    price_extra_day numeric(38,2),
    loyalty_points  INT                                     NOT NULL,
    CONSTRAINT pk_typecar PRIMARY KEY (id)
);

CREATE TABLE client
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name           VARCHAR(60),
    loyalty_points INT                                     NOT NULL,
    CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE car
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    model   VARCHAR(255),
    type_id BIGINT,
   status_id BIGINT,
    CONSTRAINT pk_car PRIMARY KEY (id)
);

ALTER TABLE car ADD CONSTRAINT FK_CAR_ON_TYPE FOREIGN KEY (type_id) REFERENCES type_car (id);
ALTER TABLE car ADD CONSTRAINT FK_CAR_ON_STATUS FOREIGN KEY (status_id) REFERENCES status_car (id);

CREATE TABLE price_condition
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    type_car_id BIGINT,
    min_days    INT                                     NOT NULL,
    max_days    INT,
    discount    DOUBLE PRECISION                        NOT NULL,
    CONSTRAINT pk_pricecondition PRIMARY KEY (id)
);

ALTER TABLE price_condition ADD CONSTRAINT FK_PRICECONDITION_ON_TYPECAR FOREIGN KEY (type_car_id) REFERENCES type_car (id);

CREATE TABLE rental
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    car_id      BIGINT,
    client_id   BIGINT,
    start_date  date,
    end_date    date,
    rental_days INT                                     NOT NULL,
    price       DECIMAL,
    CONSTRAINT pk_rental PRIMARY KEY (id)
);

ALTER TABLE rental ADD CONSTRAINT FK_RENTAL_ON_CAR FOREIGN KEY (car_id) REFERENCES car (id);
ALTER TABLE rental ADD CONSTRAINT FK_RENTAL_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

INSERT INTO status_car (status) VALUES ('AVAILABLE');
INSERT INTO status_car (status) VALUES ('RENTED');
INSERT INTO status_car (status) VALUES ('MAINTENANCE');
INSERT INTO status_car (status) VALUES ('LOST');
INSERT INTO status_car (status) VALUES ('DAMAGED');

INSERT INTO type_car (name, price, price_extra_day, loyalty_points) VALUES ('PREMIUM', 300.00, (300.00 * 1.20), 5);
INSERT INTO type_car (name, price, price_extra_day, loyalty_points) VALUES ('SUV', 150.00, (150 + (50 * 0.6)), 3);
INSERT INTO type_car (name, price, price_extra_day, loyalty_points) VALUES ('SMALL', 50.00, (50 * 1.3), 1);

INSERT INTO client (name, loyalty_points) VALUES ('John Doe', 0);
INSERT INTO client (name, loyalty_points) VALUES ('Jane Doe', 0);
INSERT INTO client (name, loyalty_points) VALUES ('Sam Smith', 0);

INSERT INTO car (model, type_id, status_id) VALUES ('BMW 7', (SELECT id FROM type_car WHERE name = 'PREMIUM'), (SELECT id FROM status_car WHERE status = 'AVAILABLE'));
INSERT INTO car (model, type_id, status_id) VALUES ('Kia Sorento', (SELECT id FROM type_car WHERE name = 'SUV'), (SELECT id FROM status_car WHERE status = 'AVAILABLE'));
INSERT INTO car (model, type_id, status_id) VALUES ('Nissan Juke', (SELECT id FROM type_car WHERE name = 'SUV'), (SELECT id FROM status_car WHERE status = 'AVAILABLE'));
INSERT INTO car (model, type_id, status_id) VALUES ('Seat Ibiza', (SELECT id FROM type_car WHERE name = 'SMALL'), (SELECT id FROM status_car WHERE status = 'AVAILABLE'));

INSERT INTO price_condition (type_car_id, min_days, max_days, discount) VALUES ((SELECT id FROM type_car WHERE name = 'PREMIUM'), 1, NULL, 0.00);
INSERT INTO price_condition (type_car_id, min_days, max_days, discount) VALUES ((SELECT id FROM type_car WHERE name = 'SUV'), 1, 7, 0.00);
INSERT INTO price_condition (type_car_id, min_days, max_days, discount) VALUES ((SELECT id FROM type_car WHERE name = 'SUV'), 8, 30, 0.20);
INSERT INTO price_condition (type_car_id, min_days, max_days, discount) VALUES ((SELECT id FROM type_car WHERE name = 'SUV'), 31, NULL, 0.50);
INSERT INTO price_condition (type_car_id, min_days, max_days, discount) VALUES ((SELECT id FROM type_car WHERE name = 'SMALL'), 1, 7, 0.00);
INSERT INTO price_condition (type_car_id, min_days, max_days, discount) VALUES ((SELECT id FROM type_car WHERE name = 'SMALL'), 8, NULL, 0.40);