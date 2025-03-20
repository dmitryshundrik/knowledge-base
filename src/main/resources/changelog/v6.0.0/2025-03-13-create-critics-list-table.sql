create table if not exists critics_list (
    id uuid not null primary key,
    created timestamp,
    slug varchar(255),
    title varchar(255),
    year integer,
    synopsis text
)