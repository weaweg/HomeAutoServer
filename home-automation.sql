CREATE OR REPLACE TABLE measurements
(
    id        BIGINT    NOT NULL AUTO_INCREMENT,
    device_id CHAR(5)   NOT NULL,
    sensor_id CHAR(3)   NOT NULL,
    m_time    TIMESTAMP NOT NULL,
    val       FLOAT     NOT NULL,
    PRIMARY KEY (id)
);

CREATE OR REPLACE TABLE automatons
(
    name           VARCHAR(30) NOT NULL,
    device_id_sens CHAR(5)     NOT NULL,
    sensor_id_sens CHAR(3)     NOT NULL,
    val            FLOAT       NOT NULL,
	hysteresis     FLOAT       NOT NULL,
    device_id_acts CHAR(5)     NOT NULL,
    sensor_id_acts CHAR(3)     NOT NULL,
    state_up       INT         NOT NULL,
	state_down     INT         NOT NULL,
    PRIMARY KEY (name)
);

CREATE OR REPLACE TABLE sensors
(
    device_id     CHAR(5) NOT NULL,
    sensor_id     CHAR(3) NOT NULL,
    data_type     TINYINT NOT NULL,
    current_state INT,
    units         VARCHAR(5),
    PRIMARY KEY (device_id, sensor_id),
    CONSTRAINT CHECK (data_type = 0 or data_type = 1)
);

CREATE OR REPLACE TABLE devices
(
    device_id  CHAR(5)     NOT NULL,
    name       VARCHAR(30) UNIQUE,
    location   VARCHAR(30),
    PRIMARY KEY (device_id)
);

ALTER TABLE measurements
    ADD CONSTRAINT measurements_fk0
        FOREIGN KEY (device_id, sensor_id) REFERENCES sensors
            ON DELETE CASCADE;

ALTER TABLE sensors
    ADD CONSTRAINT sensors_fk0
        FOREIGN KEY (device_id) REFERENCES devices
            ON DELETE CASCADE;

ALTER TABLE automatons
    ADD CONSTRAINT automatons_fk0
        FOREIGN KEY (device_id_sens, sensor_id_sens) REFERENCES sensors (device_id, sensor_id)
            ON DELETE CASCADE;

ALTER TABLE automatons
    ADD CONSTRAINT automatons_fk1
        FOREIGN KEY (device_id_acts, sensor_id_acts) REFERENCES sensors (device_id, sensor_id)
            ON DELETE CASCADE;

INSERT INTO devices
VALUES ("bb001", "new_bb001", null),
       ("bb002", "test_bb002", "salon");

INSERT INTO sensors
VALUES ("bb001", "t01", 0, null, "°C"),
       ("bb001", "m01", 1, 5, null),
       ("bb002", "t01", 0, null, "°F");

INSERT INTO measurements (device_id, sensor_id, m_time, val)
VALUES ("bb001", "t01", "2023-02-08T15:04:19", 12.5),
       ("bb001", "t01", "2023-03-03T14:53:37", 13),
       ("bb001", "t01", "2023-04-13T11:22:38", 13.5),
       ("bb001", "t01", "2023-04-24T14:37:06", 11),
       ("bb001", "m01", "2023-05-04T08:16:43", 1),
       ("bb001", "m01", "2023-05-05T01:53:51", 5),
       ("bb001", "m01", "2023-05-14T10:32:29", 3);
	   
INSERT INTO automatons (name, device_id_sens, sensor_id_sens, val, hysteresis,
device_id_acts, sensor_id_acts, state_up, state_down)
VALUES ("test1", "bb001", "t01", 15, 2, "bb001", "m01", 1, 0);