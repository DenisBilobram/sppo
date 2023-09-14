CREATE TABLE fortress_timestamp (
    fort_id integer,
    entr_date date
);

CREATE OR REPLACE FUNCTION fort_add_stamp() RETURNS trigger AS $$
    begin
      insert into fortress_timestamp (fort_id, entr_date) values (new.id, current_timestamp);
      return new;
    end;
$$ LANGUAGE plpgsql;

CREATE TRIGGER fort_add AFTER INSERT ON FORTRESS FOR EACH ROW EXECUTE PROCEDURE fort_add_stamp();

CREATE OR REPLACE FUNCTION show_last_fort() RETURNS TABLE(fort_id integer, entr_date date) AS 'SELECT fort_id, entr_date FROM fortress_timestamp ORDER BY entr_date DESC LIMIT 1' LANGUAGE sql;