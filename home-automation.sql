CREATE OR REPLACE TABLE measurements
(
	id				BIGINT,
	device_id		CHAR(5)			NOT NULL,
	sensor_id		CHAR(3)			NOT NULL,
	m_time			TIMESTAMP		NOT NULL,
	val				FLOAT			NOT NULL,
	PRIMARY KEY (id)
);

CREATE OR REPLACE TABLE sensors
(
	device_id		CHAR(5),
	sensor_id		CHAR(3),
	data_type		BOOLEAN			NOT NULL,
	states_count	INT UNSIGNED,
	current_state	INT	UNSIGNED,
	units			VARCHAR(5),
	period			INT	UNSIGNED,
	PRIMARY KEY (device_id, sensor_id),
	CONSTRAINT CHECK(data_type = 0 or data_type = 1)
);

CREATE OR REPLACE TABLE devices
(
	device_id		CHAR(5),
	ip_address		INET4			NOT NULL UNIQUE,
	name			VARCHAR(30)		NOT NULL UNIQUE,
	location		VARCHAR(30),
	PRIMARY KEY (device_id)
);

CREATE OR REPLACE TABLE users
(
	user_id			INT,
	username		VARCHAR(30) 	NOT NULL,
	password		VARCHAR(		NOT NULL,
	PRIMARY KEY (user_id)
);

ALTER TABLE measurements ADD CONSTRAINT measurements
FOREIGN KEY (device_id, sensor_id) REFERENCES sensors
ON DELETE CASCADE;

ALTER TABLE sensors ADD CONSTRAINT sensors_fk0
FOREIGN KEY (device_id) REFERENCES devices
ON DELETE CASCADE;

INSERT INTO devices VALUES
("bb001", "new_bb001", null),
("bb002", "test_bb002", "salon");

INSERT INTO sensors VALUES
("bb001", 0, 0, 0, "°C", 10),
("bb001", 1, 1, 0, null, 5),
("bb002", 0, 0, 0, null, 5);

INSERT INTO measurements VALUES
("bb001", 0, 1683190612, 12.5),
("bb001", 0, 1683190622, 13),
("bb001", 0, 1683190632, 13.5),
("bb001", 0, 1683190642, 11),
("bb001", 1, 1683190612, 1),
("bb001", 1, 1683190617, 5),
("bb001", 1, 1683190622, 3);