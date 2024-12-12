ALTER TABLE composition
ALTER COLUMN catalog_number TYPE numeric USING catalog_number::numeric;

ALTER TABLE composition
ALTER COLUMN catalog_number TYPE double precision;


