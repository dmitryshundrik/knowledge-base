create table if not exists writers
(
 id uuid not null primary key,
 created timestamp,
 slug varchar(255) constraint writers_slug_constraint unique,
 nick_name varchar(255),
 first_name varchar(255),
 last_name varchar(255),
 image text,
 born integer,
 died integer,
 birth_date date,
 death_date date,
 birthplace varchar(255),
 occupation varchar(255)
);

create table if not exists prose
(
 id uuid not null primary key,
 created timestamp,
 title varchar(255),
 slug varchar(255) constraint prose_slug_constraint unique,
 writer_id uuid constraint prose_writer_id_constraint references writers,
 year integer,
 rating double precision,
 description text
);

create table if not exists quotes
(
 id uuid not null primary key,
 created timestamp,
 writer_id uuid constraint quotes_writer_id_constraint references writers,
 prose_id uuid constraint quotes_prose_id_constraint references prose,
 location varchar(255),
 description text
);

create table if not exists writers_events
(
 writer_id uuid not null constraint writers_events_writer_id_constraint references writers,
 events_id uuid not null constraint writers_events_event_id_constraint references personevents
)