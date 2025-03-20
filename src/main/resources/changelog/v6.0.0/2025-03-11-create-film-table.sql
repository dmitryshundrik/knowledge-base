create table if not exists film (
    id uuid not null primary key,
    created timestamp,
    title varchar(255),
    director varchar(255),
    starring varchar(255),
    year integer,
    rating double precision,
    year_rank double precision,
    all_time_rank double precision,
    synopsis text,
    image text
)