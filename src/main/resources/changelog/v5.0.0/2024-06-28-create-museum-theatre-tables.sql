create table if not exists museum (
    id uuid not null primary key,
    created timestamp,
    title varchar(255),
    based varchar(255),
    founded integer,
    description text
);

create table if not exists theatre (
    id uuid not null primary key,
    created timestamp,
    title varchar(255),
    based varchar(255),
    founded integer,
    description text
)