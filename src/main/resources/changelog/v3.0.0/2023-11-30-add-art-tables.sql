create table if not exists painters
(
    id uuid not null primary key,
    created timestamp,
    slug varchar(255) constraint painters_slug_constraint unique,
    nick_name varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    image text,
    born integer,
    died integer,
    birth_date date,
    death_date date,
    approximate_years varchar(255),
    birthplace varchar(255),
    occupation varchar(255)
);

alter table painters
    owner to postgres;

create table if not exists paintings
(
    id uuid not null primary key,
    created timestamp,
    slug varchar(255) constraint painting_slug_constraint unique,
    title varchar(255),
    painter_id uuid constraint paintings_painter_id_constraint references painters,
    year1 integer,
    year2 integer,
    approximate_years varchar(255),
    based varchar(255),
    painter_top_rank integer,
    all_time_top_rank integer,
    description text,
    image_id uuid constraint paintinds_image_id_constraint references images
);

alter table paintings
    owner to postgres;

create table if not exists painting_styles
(
    id uuid not null primary key,
    created timestamp,
    slug varchar(255) constraint painting_styles_slug_constraint unique,
    title varchar(255),
    title_en varchar(255),
    "count" integer,
    description text
);

alter table painting_styles
    owner to postgres;

create table if not exists painters_events
(
    painters_id uuid not null constraint painters_events_painter_id_constraint references painters,
    events_id uuid not null constraint painters_events_event_id_constraint references personevents
);

alter table painters_events
    owner to postgres;

create table if not exists paintings_painting_styles
(
    paintings_id uuid not null constraint paintings_painting_styles_paintings_id_constraint references paintings,
    painting_styles_id uuid not null constraint paintings_painting_styles_painting_styles_id_constraint references painting_styles
);

alter table paintings_painting_styles
    owner to postgres;