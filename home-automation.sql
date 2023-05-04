CREATE OR REPLACE TABLE measurments (
	sensor_id INT NOT NULL,
	device_id char(10) NOT NULL,
	measure_time TIMESTAMP NOT NULL,
	measurment FLOAT NOT NULL
);

CREATE OR REPLACE TABLE sensors (
	id INT NOT NULL,
	device_id char(10) NOT NULL,
	data_type INT NOT NULL,
	current_state INT,
	units varchar(5),
	period INT,
	PRIMARY KEY (id, device_id)
);

CREATE OR REPLACE TABLE devices (
	id char(5) NOT NULL,
	name varchar(30) NOT NULL UNIQUE,
	location varchar(30),
	sens_nb INT NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE measurments ADD CONSTRAINT measurments_fk0
FOREIGN KEY (sensor_id, device_id) REFERENCES sensors(id, device_id);

ALTER TABLE sensors ADD CONSTRAINT sensors_fk0 FOREIGN KEY (device_id) REFERENCES devices(id);

INSERT INTO devices VALUES("bb001", "new_bb001", null, 2);
INSERT INTO sensors VALUES(0, "bb001", 0, null, "°C", 10);
INSERT INTO sensors VALUES(1, "bb001", 1, 0, null, null);
