create table if not exists images
(
    id          uuid not null
    primary key,
    "data"              text,
    description       text,
    created     timestamp,
    slug        varchar(255),
    title       varchar(255)
    );

alter table images
    owner to postgres;