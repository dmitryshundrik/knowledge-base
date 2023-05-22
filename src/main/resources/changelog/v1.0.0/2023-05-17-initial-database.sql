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

INSERT INTO users(id, username, password, is_account_non_expired, is_account_non_locked, is_credentials_non_expired,
                  is_enabled)
VALUES ('112C8DD8-346B-426E-B06C-000000000001', 'dmitryshundrik@gmail.com', '$2a$10$OzjntV3S0f8R0vGoRtzHuOwEuUkBEaRYgQRjGedLUVzd23Rnm9oCu',
        true, true, true, true);