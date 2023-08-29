create table if not exists settings
(
    id uuid not null primary key,
    created timestamp,
    "name" varchar(255),
    "value" varchar(255)
);

alter table settings owner to postgres;

insert into settings (id, created, "name", "value")
values ('5c175dce-4571-11ee-be56-0242ac120002', now(), 'TIME_INTERVAL_FOR_UPDATES', '3'),
       ('74541f5a-4579-11ee-be56-0242ac120002', now(), 'LIMIT_FOR_UPDATES', '25');

