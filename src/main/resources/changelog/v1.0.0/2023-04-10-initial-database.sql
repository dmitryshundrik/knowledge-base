create table if not exists timelineofmusic
(
    id      uuid not null
    primary key,
    created timestamp,
    slug    varchar(255),
    title   varchar(255)
    );

alter table timelineofmusic
    owner to postgres;

create table if not exists timelineofmusic_events
(
    timeline_of_music_id uuid not null
    constraint fkri2bxsgvsrxihgkttcm94wtgv
    references timelineofmusic,
    events_id            uuid not null
    constraint uk_lvnp6wmur8qrfqhtofftmico4
    unique
);

alter table timelineofmusic_events
    owner to postgres;

create table if not exists events
(
    id           uuid not null
    primary key,
    another_year integer,
    created      timestamp,
    description  text,
    era_type     integer,
    event_type   integer,
    title        varchar(255),
    year         integer
    );

alter table events
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
    compositions_sort_type varchar(255),
    created                timestamp,
    death_date             date,
    died                   integer,
    first_name             varchar(255),
    image                  text,
    last_name              varchar(255),
    nick_name              varchar(255),
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

create table if not exists musicians_events
(
    musician_id uuid not null
    constraint fk8p38bn6s7a9x27kd3k8381d6l
    references musicians,
    events_id   uuid not null
    constraint uk_fnl2xgc2pkdnbrjeatlq73vyu
    unique
    constraint fkj2ifrn821w3rrldda282xjsks
    references events
);

alter table musicians_events
    owner to postgres;

create table if not exists musicians_music_genres
(
    musician_id     uuid not null
    constraint fkdnclo88ww6laq7ny2y4c8s63j
    references musicians,
    music_genres_id uuid not null
    constraint fkbmexomlp2xug6xodea3i64lha
    references musicgenres
);

alter table musicians_music_genres
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

create table if not exists albums_music_periods
(
    album_id         uuid not null
    constraint fk54tgx7mfhtnc9fb9aqx3n6rdy
    references albums,
    music_periods_id uuid not null
    constraint fk1tofbdi63hel6ivjgg5t0bgkl
    references musicperiods
);

alter table albums_music_periods
    owner to postgres;

create table if not exists compositions_music_periods
(
    composition_id   uuid not null
    constraint fkj6d38tg5wopl0r8u4qwc107er
    references compositions,
    music_periods_id uuid not null
    constraint fka27y0rl9219otjcpkfhs00pr9
    references musicperiods
);

alter table compositions_music_periods
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

create table if not exists users
(
    id                         uuid    not null
    primary key,
    is_account_non_expired     boolean not null,
    is_account_non_locked      boolean not null,
    is_credentials_non_expired boolean not null,
    is_enabled                 boolean not null,
    name                       varchar(255),
    password                   varchar(255),
    username                   varchar(255)
    );

alter table users
    owner to postgres;

create table if not exists user_user_roles
(
    user_id    uuid not null
    constraint fkjf1u0yxde3fbgqssn06deuwuw
    references users,
    user_roles varchar(255)
    );

alter table user_user_roles
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

INSERT INTO users(id, username, password, is_account_non_expired, is_account_non_locked, is_credentials_non_expired,
                  is_enabled)
VALUES ('112C8DD8-346B-426E-B06C-000000000001', 'dmitryshundrik', '$2a$10$OzjntV3S0f8R0vGoRtzHuOwEuUkBEaRYgQRjGedLUVzd23Rnm9oCu',
        true, true, true, true);

