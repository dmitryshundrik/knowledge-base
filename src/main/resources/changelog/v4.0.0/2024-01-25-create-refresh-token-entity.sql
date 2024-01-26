create table if not exists refresh_token
(
    id uuid not null primary key,
    created timestamp,
    refresh_token text
);

alter table refresh_token
    owner to postgres;