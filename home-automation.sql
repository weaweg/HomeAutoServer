CREATE OR REPLACE TABLE measurements
(
	device_id		CHAR(5)			NOT NULL,
	sensor_id		INT	UNSIGNED	NOT NULL,
	m_time			LONG			NOT NULL,
	val				FLOAT			NOT NULL
);

CREATE OR REPLACE TABLE sensors
(
	device_id		char(5)			NOT NULL,
	id				INT	UNSIGNED	NOT NULL,
	data_type		INT	UNSIGNED	NOT NULL,
	current_state	INT	UNSIGNED	NOT NULL,
	units			varchar(5),
	period			INT	UNSIGNED	NOT NULL,
	PRIMARY KEY (device_id, id),
	CONSTRAINT CHECK(data_type = 0 or data_type = 1)
);

CREATE OR REPLACE TABLE devices
(
	id				char(5)		NOT NULL,
	name			varchar(30)	NOT NULL UNIQUE,
	location		varchar(30),
	PRIMARY KEY (id)
);

ALTER TABLE measurements ADD CONSTRAINT measurements_fk0
FOREIGN KEY (device_id, sensor_id) REFERENCES sensors (device_id, id)
ON DELETE CASCADE;

ALTER TABLE sensors ADD CONSTRAINT sensors_fk0
FOREIGN KEY (device_id) REFERENCES devices (id)
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