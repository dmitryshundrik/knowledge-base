
create table if not exists foundations
(
    id      uuid not null primary key,
    created timestamp,
    title   varchar(255),
    description text,
    link text
);