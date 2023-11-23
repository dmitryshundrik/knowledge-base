create table if not exists words
(
    id uuid not null primary key,
    created timestamp,
    writer_id uuid not null constraint words_writer_id_constraint references writers,
    "title" varchar(255),
    "description" varchar(255)
);

alter table words owner to postgres;