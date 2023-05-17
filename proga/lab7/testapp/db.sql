CREATE TYPE color AS ENUM('RED', 'BLACK', 'YELLOW', 'BROWN');

CREATE TYPE difficulty AS ENUM('NORMAL', 'HARD', 'INSANE', 'TERRIBLE');

CREATE TABLE APP_PERSONS(
	id serial UNIQUE,
	name varchar(30) NOT NULL CHECK(name != ''),
	height real NOT NULL CHECK(height > 0),
	eyecolor color NOT NULL
);

CREATE TABLE APP_USERS(
	id serial UNIQUE PRIMARY KEY,
	username varchar(30) UNIQUE NOT NULL,
	password varchar NOT NULL,
	person_id integer UNIQUE NOT NULL,
	FOREIGN KEY (person_id) REFERENCES APP_PERSONS(id)
);

CREATE TABLE APP_LABWORKS(
	id serial UNIQUE PRIMARY KEY,
	lab_name varchar(30) NOT NULL,
	x_coord bigint NOT NULL CHECK(x_coord <= 689),
	y_coord bigint NOT NULL CHECK(y_coord <= 475),
	creation_date date NOT NULL,
	minimal_point bigint,
	tuned_in_works bigint,
	difficulty difficulty NOT NULL,
	user_id integer NOT NULL,
	FOREIGN KEY (user_id) REFERENCES "APP_USERS"(id)
);
