CREATE OR REPLACE TABLE measurements
(
   id          BIGINT    NOT NULL AUTO_INCREMENT,
   device_id   CHAR(5)   NOT NULL,
   sensor_id   CHAR(3)   NOT NULL,
   val         FLOAT     NOT NULL,
   m_time      TIMESTAMP NOT NULL,
   PRIMARY KEY (id)
);

CREATE OR REPLACE TABLE automatons
(
   name           VARCHAR(30) NOT NULL,
   device_id_sens CHAR(5)     NOT NULL,
   sensor_id_sens CHAR(3)     NOT NULL,
   val_top        FLOAT       NOT NULL,
   val_bot        FLOAT       NOT NULL,
   device_id_acts CHAR(5)     NOT NULL,
   sensor_id_acts CHAR(3)     NOT NULL,
   state_up       INT         NOT NULL,
   state_down     INT         NOT NULL,
   PRIMARY KEY (name)
);

CREATE OR REPLACE TABLE sensors
(
   device_id   CHAR(5)     NOT NULL,
   sensor_id   CHAR(3)     NOT NULL,
   discrete    BOOLEAN     NOT NULL,
   name        VARCHAR(30) NOT NULL DEFAULT ('Nowe Urządzenie'),
   current_val FLOAT,
   m_time      TIMESTAMP   NULL,
   json_desc   JSON,
   PRIMARY KEY (device_id, sensor_id),
   CHECK (JSON_VALID(json_desc))
);

CREATE OR REPLACE TABLE devices
(
   device_id   CHAR(5)     NOT NULL,
   name        VARCHAR(30) NOT NULL,
   location    VARCHAR(30) NOT NULL DEFAULT ('Brak lokacji'),
   PRIMARY KEY (device_id)
);

ALTER TABLE measurements
    ADD CONSTRAINT measurements_fk0
        FOREIGN KEY (device_id, sensor_id) REFERENCES sensors (device_id, sensor_id)
            ON DELETE CASCADE;

ALTER TABLE sensors
    ADD CONSTRAINT sensors_fk0
        FOREIGN KEY (device_id) REFERENCES devices (device_id)
            ON DELETE CASCADE;

ALTER TABLE automatons
    ADD CONSTRAINT automatons_fk0
        FOREIGN KEY (device_id_sens, sensor_id_sens) REFERENCES sensors (device_id, sensor_id)
            ON DELETE CASCADE;

ALTER TABLE automatons
    ADD CONSTRAINT automatons_fk1
        FOREIGN KEY (device_id_acts, sensor_id_acts) REFERENCES sensors (device_id, sensor_id)
            ON DELETE CASCADE;

INSERT INTO devices (device_id, name)
VALUES ("bb002", "test2"),
       ("bb003", "test3"),
	   ("bb004", "test4"),
	   ("bb005", "test5");

INSERT INTO sensors (device_id, sensor_id, discrete)
VALUES ("bb003", "t01", 0),
       ("bb003", "t02", 0),
       ("bb003", "m01", 1),
	   ("bb003", "m02", 1),
	   ("bb003", "t05", 0),
	   ("bb003", "t06", 0),
	   ("bb003", "t07", 0),
	   ("bb003", "t08", 0),
	   ("bb003", "t09", 0),
	   ("bb003", "t10", 0),
	   ("bb003", "t11", 0),
	   ("bb003", "t12", 0),
	   ("bb003", "t13", 0),
	   ("bb003", "t14", 0),
	   ("bb003", "t15", 0),
	   ("bb003", "t16", 0),
	   ("bb003", "t17", 0),
	   ("bb003", "t18", 0),
	   ("bb003", "t19", 0),
	   ("bb003", "t20", 0),
	   ("bb003", "t21", 0),
	   ("bb003", "t22", 0),
	   ("bb003", "t23", 0);
	   
INSERT INTO automatons
VALUES ("test1", "bb003", "t01", 15, 20, "bb003", "m01", 1, 0);