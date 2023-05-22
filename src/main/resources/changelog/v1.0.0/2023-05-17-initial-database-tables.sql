
create table if not exists events
(
    id           uuid not null
    primary key,
    another_year integer,
    created      timestamp,
    description  text,
    era_type     varchar(255),
    event_type   varchar(255),
    title        varchar(255),
    year         integer
    );

alter table events
    owner to postgres;

create table if not exists articles
(
    id          uuid not null
    primary key,
    created     timestamp,
    description text,
    title       varchar(255)
    );

alter table articles
    owner to postgres;

create table if not exists cocktails
(
    id          uuid not null
    primary key,
    about       text,
    country     varchar(255),
    created     timestamp,
    ingredients text,
    method      text,
    slug        varchar(255),
    title       varchar(255)
    );

alter table cocktails
    owner to postgres;

create table if not exists foundations
(
    id          uuid not null
    primary key,
    created     timestamp,
    description text,
    link        text,
    title       varchar(255)
    );

alter table foundations
    owner to postgres;

create table if not exists musicgenres
(
    id               uuid not null
    primary key,
    count            integer,
    created          timestamp,
    description      text,
    music_genre_type varchar(255),
    slug             varchar(255),
    title            varchar(255),
    title_en         varchar(255)
    );

alter table musicgenres
    owner to postgres;

create table if not exists musicians
(
    id                     uuid not null
    primary key,
    albums_sort_type       varchar(255),
    birth_date             date,
    birthplace             varchar(255),
    born                   integer,
    catalog_title          varchar(255),
    compositions_sort_type varchar(255),
    created                timestamp,
    death_date             date,
    died                   integer,
    first_name             varchar(255),
    founded                integer,
    image                  text,
    last_name              varchar(255),
    nick_name              varchar(255),
    occupation             varchar(255),
    slug                   varchar(255)
    constraint uk_5cx6bjbyqfy7si93l4vytaru
    unique,
    spotify_link           text
    );

alter table musicians
    owner to postgres;

create table if not exists albums
(
    id                    uuid not null
    primary key,
    artwork               text,
    catalog_number        varchar(255),
    created               timestamp,
    description           text,
    essential_albums_rank integer,
    feature               varchar(255),
    highlights            text,
    rating                double precision,
    slug                  varchar(255),
    title                 varchar(255),
    year                  integer,
    year_end_rank         integer,
    musician_id           uuid
    constraint fka9xig9kvjj21otb72adbosea8
    references musicians
    );

alter table albums
    owner to postgres;

create table if not exists albums_music_genres
(
    album_id        uuid not null
    constraint fktensrjk8k477gcxd3js0km5gb
    references albums,
    music_genres_id uuid not null
    constraint fk5clv3l9akduni0r7ciqn6ei5q
    references musicgenres
);

alter table albums_music_genres
    owner to postgres;

create table if not exists compositions
(
    id                          uuid not null
    primary key,
    catalog_number              varchar(255),
    created                     timestamp,
    description                 text,
    essential_compositions_rank integer,
    feature                     varchar(255),
    highlights                  text,
    lyrics                      text,
    rating                      double precision,
    slug                        varchar(255),
    title                       varchar(255),
    translation                 text,
    year                        integer,
    year_end_rank               integer,
    album_id                    uuid
    constraint fk1de589006s99jm260d8yffay1
    references albums,
    musician_id                 uuid
    constraint fkfhox2qkj1r2wrn5wf1f86qax8
    references musicians
    );

alter table compositions
    owner to postgres;

create table if not exists compositions_music_genres
(
    composition_id  uuid not null
    constraint fkonv9acdpoqr9oro0mwdg0prhm
    references compositions,
    music_genres_id uuid not null
    constraint fknut8ta06r47ktbgb4ncpm6ndp
    references musicgenres
);

alter table compositions_music_genres
    owner to postgres;

create table if not exists musicperiods
(
    id                uuid not null
    primary key,
    approximate_end   integer,
    approximate_start integer,
    created           timestamp,
    description       text,
    slug              varchar(255),
    title             varchar(255),
    title_en          varchar(255)
    );

alter table musicperiods
    owner to postgres;

create table if not exists musicians_music_periods
(
    musician_id      uuid not null
    constraint fkhfnn84spusn9balktlirqdphk
    references musicians,
    music_periods_id uuid not null
    constraint fkfxx01e3q4i9xgut14r3l5qe5
    references musicperiods
);

alter table musicians_music_periods
    owner to postgres;

create table if not exists personevents
(
    id           uuid not null
    primary key,
    another_year integer,
    created      timestamp,
    description  text,
    title        varchar(255),
    year         integer
    );

alter table personevents
    owner to postgres;

create table if not exists musicians_events
(
    musician_id uuid not null
    constraint fk8p38bn6s7a9x27kd3k8381d6l
    references musicians,
    events_id   uuid not null
    constraint uk_fnl2xgc2pkdnbrjeatlq73vyu
    unique
    constraint fkstfn15m37krt9p83m8sp8e14q
    references personevents
);

alter table musicians_events
    owner to postgres;

create table if not exists recipes
(
    id          uuid not null
    primary key,
    about       text,
    country     varchar(255),
    created     timestamp,
    ingredients text,
    method      text,
    slug        varchar(255),
    title       varchar(255)
    );

alter table recipes
    owner to postgres;

create table if not exists resources
(
    id          uuid not null
    primary key,
    created     timestamp,
    description text,
    link        text,
    title       varchar(255)
    );

alter table resources
    owner to postgres;

create table if not exists timelineevents
(
    id                  uuid not null
    primary key,
    another_year        integer,
    created             timestamp,
    description         text,
    era_type            varchar(255),
    timeline_event_type varchar(255),
    title               varchar(255),
    year                integer
    );

alter table timelineevents
    owner to postgres;

create table if not exists yearinmusic
(
    id                    uuid not null
    primary key,
    aoty_list_description text,
    aoty_spotify_link     text,
    created               timestamp,
    slug                  varchar(255)
    constraint uk_6f7fecyxp7hnx860lhtq1mkc5
    unique,
    soty_list_description text,
    soty_spotify_link     text,
    title                 varchar(255),
    year                  integer,
    best_composer_id      uuid
    constraint fkbtl19ew900fe7xve6iwik8cu
    references musicians,
    best_female_singer_id uuid
    constraint fkox1kbw45cfwrqsg0e1nraquqn
    references musicians,
    best_group_id         uuid
    constraint fkmw6fuxu5kn6bja0sfok04fjqm
    references musicians,
    best_male_singer_id   uuid
    constraint fki72tokrd56hm3pyno9t0ejgqh
    references musicians
    );

alter table yearinmusic
    owner to postgres;

