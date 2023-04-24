
create table if not exists recipes
(
    id      uuid not null primary key,
    created timestamp,
    slug    varchar(255),
    title   varchar(255),
    country varchar(255),
    about text,
    ingredients text,
    "method" text
);

create table if not exists cocktails
(
    id      uuid not null primary key,
    created timestamp,
    slug    varchar(255),
    title   varchar(255),
    country varchar(255),
    about text,
    ingredients text,
    "method" text
);

alter table events
alter column era_type type varchar(255),
alter column event_type type varchar(255);
