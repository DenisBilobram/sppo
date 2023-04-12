delete from partition;
delete from \"window\";
delete from block;
delete from ornament;
delete from wall;
delete from layer;
delete from fortress;
delete from material;

INSERT INTO material (name, density) VALUES ('snow', 2);
insert into material (name, density) VALUES ('ice', 500);

INSERT INTO fortress (name, square) VALUES ('Castle1', '321');

INSERT INTO layer (fortress_id, material_id, depth) VALUES (1, 1, 10);
INSERT INTO layer (fortress_id, material_id, depth) VALUES (1, 2, 10);

INSERT INTO wall (fortress_id, height, length, depth) VALUES (1, 100, 200, 30);
INSERT INTO wall (fortress_id, height, length, depth) VALUES (1, 100, 200, 20);
INSERT INTO wall (fortress_id, height, length, depth) VALUES (1, 100, 150, 25);
INSERT INTO wall (fortress_id, height, length, depth) VALUES (1, 100, 175, 30);

INSERT INTO block (wall_id, width, length) VALUES (1, 20, 40);
INSERT INTO block (wall_id, width, length) VALUES (1, 20, 40);
INSERT INTO block (wall_id, width, length) VALUES (2, 20, 40);
INSERT INTO block (wall_id, width, length) VALUES (2, 20, 40);
INSERT INTO block (wall_id, width, length) VALUES (3, 20, 40);
INSERT INTO block (wall_id, width, length) VALUES (3, 20, 40);
INSERT INTO block (wall_id, width, length) VALUES (4, 20, 40);
INSERT INTO block (wall_id, width, length) VALUES (4, 20, 40);

INSERT INTO \"window\" (wall_id, length, width, has_partition) VALUES (2, 20, 20, true);

INSERT INTO ornament (wall_id) VALUES (3);

INSERT INTO partition (\"window_id\", count) VALUES (1, 10);