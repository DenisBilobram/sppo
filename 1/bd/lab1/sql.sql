DROP TABLE IF EXISTS material CASCADE;
DROP TABLE IF EXISTS fortress CASCADE;
DROP TABLE IF EXISTS layer CASCADE;
DROP TABLE IF EXISTS wall CASCADE;
DROP TABLE IF EXISTS block CASCADE;
DROP TABLE IF EXISTS "window" CASCADE;
DROP TABLE IF EXISTS ornament CASCADE;
DROP TABLE IF EXISTS partition CASCADE;

CREATE TABLE material (
    id serial PRIMARY KEY,
    name varchar(30),
    density integer
);

CREATE TABLE fortress (
    id serial PRIMARY KEY,
    name varchar(20),
    square integer NOT NULL
);

CREATE TABLE layer (
    id serial PRIMARY KEY,
    depth integer,
    fortress_id integer NOT NULL,
    material_id integer NOT NULL,
    FOREIGN KEY (fortress_id) REFERENCES fortress (id),
    FOREIGN KEY (material_id) REFERENCES material (id)
);

CREATE TABLE wall (
    id serial PRIMARY KEY,
    fortress_id integer NOT NULL,
    height integer NOT NULL,
    length integer NOT NULL,
    depth integer NOT NULL,
    FOREIGN KEY (fortress_id) REFERENCES fortress (id)
);

CREATE TABLE block (
    id serial PRIMARY KEY,
    wall_id integer,
    width integer NOT NULL,
    length integer NOT NULL,
    FOREIGN KEY (wall_id) REFERENCES wall (id)
);

CREATE TABLE "window" (
    id serial PRIMARY KEY,
    wall_id integer NOT NULL,
    length integer NOT NULL,
    width integer NOT NULL,
    has_partition boolean,
    FOREIGN KEY (wall_id) REFERENCES wall (id)
);

CREATE TABLE ornament (
    id serial PRIMARY KEY,
    wall_id integer,
    image bytea,
    FOREIGN KEY (wall_id) REFERENCES wall (id)
);

CREATE TABLE partition (
    id serial PRIMARY KEY,
    window_id integer NOT NULL,
    count integer NOT NULL,
    FOREIGN KEY (window_id) REFERENCES "window" (id)
);
