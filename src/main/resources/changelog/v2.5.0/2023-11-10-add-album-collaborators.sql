
create table if not exists albums_collaborators
(
album_id uuid not null constraint albums_collaborators_album_id_constraint references albums,
collaborators_id uuid not null constraint albums_collaborators_collaborators_id_constraint references musicians
);

alter table albums_collaborators owner to postgres;

alter table musicians add column based varchar(255);