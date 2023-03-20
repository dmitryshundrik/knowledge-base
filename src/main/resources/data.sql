INSERT INTO users(id, username, password, is_account_non_expired, is_account_non_locked, is_credentials_non_expired,
                  is_enabled)
VALUES ('112C8DD8-346B-426E-B06C-000000000001', 'dmitryshundrik', '$2a$10$OzjntV3S0f8R0vGoRtzHuOwEuUkBEaRYgQRjGedLUVzd23Rnm9oCu',
        true, true, true, true);

INSERT INTO sotylists (id, slug, title, "year", description, spotify_link)
VALUES ('112C8DD8-346B-426E-B06C-00BBA97DCD00', 'best-songs-2020', '20 лучших песен 2020 года', 2020,
        'Откровенный трэп-бэнгер от Cardi B, Perfume Genius и другие.',
        'https://open.spotify.com/playlist/5VH3DyCjQfdqfRWHyvJYvQ?si=e2a2189011ea459d'),

       ('112C8DD8-346B-426E-B06C-00BBA97DCD01', 'best-songs-2021', '25 лучших песен 2021 года', 2021,
        'Кавер Arooj Aftab на традиционную газель на урду, индастриал хип-хоп от Smerz и другие.',
        'https://open.spotify.com/playlist/7dLtbBxcj9kIhVVGgm6EtI?si=2d9dd932221d409c'),

       ('112C8DD8-346B-426E-B06C-00BBA97DCD02', 'best-songs-2022', '25 лучших песен 2022 года', 2022,
        'Элегантные мутации фальцета Perfume Genius, 23-минутная декламация стихов экспрессиониста Георга
                    Гейма от Diamanda Galás и другие.',
        'https://open.spotify.com/playlist/01Zvl7BwHNnnkqrmURAx1R?si=b255595d03384698');

INSERT INTO musicians (id, slug, first_name, last_name, nick_name, born, birthplace)
VALUES ('112C8DD8-346B-426E-B06C-01BBA97DCD00', 'cardi-b', 'Belcalis Marlenis', 'Almánzar', 'Cardi B', 1992,
        'Нью-Йорк, США'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD01', 'perfume-genius', 'Michael Alden', 'Hadreas', 'Perfume Genius',
        1985, 'Де-Мойн, США'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD02', 'the-chicks', '', '', 'The Chicks', 1989, ''),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD03', 'johann-sebastian-bach', 'Johann Sebastian', 'Bach',
        'Иоганн Себастьян Бах', 1685, 'Эйзенах, Тюрингия');

INSERT INTO albums (id, slug, title, musician_id, "year", rating)
VALUES ('112C8DD8-346B-426E-B06C-05BBA97DCD00', 'invasion-in-privacy', 'Invasion In Privacy',
        '112C8DD8-346B-426E-B06C-01BBA97DCD00', 2017, 7.5),
       ('112C8DD8-346B-426E-B06C-05BBA97DCD01', 'set-my-heart-on-fire-immediately', 'Set My Heart On Fire Immediately',
        '112C8DD8-346B-426E-B06C-01BBA97DCD01', 2020, 8.0),
       ('112C8DD8-346B-426E-B06C-05BBA97DCD02', 'gaslighter', 'Gaslighter',
        '112C8DD8-346B-426E-B06C-01BBA97DCD02', 2020, 4.0),
       ('112C8DD8-346B-426E-B06C-05BBA97DCD03', 'cb2', 'CB2',
        '112C8DD8-346B-426E-B06C-01BBA97DCD00', 2023, 7.0);

INSERT INTO compositions (id, slug, title, musician_id, "year", year_end_rank, catalog_number, rating)
VALUES ('112C8DD8-346B-426E-B06C-02BBA97DCD00', 'wap', 'WAP', '112C8DD8-346B-426E-B06C-01BBA97DCD00',
        2020, 1, '', 8),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD01', 'describe', 'Describe', '112C8DD8-346B-426E-B06C-01BBA97DCD01',
        2020, 2, '', 8),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD02', 'gaslighter', 'Gaslighter', '112C8DD8-346B-426E-B06C-01BBA97DCD02',
        2020, 3, '', 7.5),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD03', 'brandenburg-concerto-number-1-BWV1046',
        'Концерт № 1 фа мажор',
        '112C8DD8-346B-426E-B06C-01BBA97DCD03', 1712, 0, 'BWV1047', 9.0),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD04', 'brandenburg-concerto-number-2-BWV1047',
        'Концерт № 2 фа мажор',
        '112C8DD8-346B-426E-B06C-01BBA97DCD03', 1715, 0, 'BWV1043', 8.0),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD05', 'brandenburg-concerto-number-5-BWV1052',
        'Концерт № 5 ре мажор',
        '112C8DD8-346B-426E-B06C-01BBA97DCD03', 1716, 0, 'BWV1041', 9.5),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD06', 'bodak-yellow', 'Bodak Yellow', '112C8DD8-346B-426E-B06C-01BBA97DCD00',
        2017, 1, '', 8);

INSERT INTO musicperiods(id, slug, title)
VALUES ('112C8DD8-346B-426E-B06C-04BBA97DCD00', 'renaissance', 'Возрождение'),
       ('112C8DD8-346B-426E-B06C-04BBA97DCD01', 'baroque', 'Барокко'),
       ('112C8DD8-346B-426E-B06C-04BBA97DCD02', 'classic', 'Классицизм');

INSERT INTO musicgenres (id, slug, title, music_genre_type)
VALUES ('112C8DD8-346B-426E-B06C-03BBA97DCD00', 'hip-hop-rap', 'Хип-хоп/Рэп', 'CONTEMPORARY'),
       ('112C8DD8-346B-426E-B06C-03BBA97DCD01', 'pop', 'Поп', 'CONTEMPORARY'),
       ('112C8DD8-346B-426E-B06C-03BBA97DCD02', 'rock', 'Рок', 'CONTEMPORARY'),
       ('112C8DD8-346B-426E-B06C-03BBA97DCD03', 'country', 'Кантри', 'CONTEMPORARY'),
       ('112C8DD8-346B-426E-B06C-03BBA97DCD04', 'concerto', 'Оркестровый концерт', 'CLASSICAL');


INSERT INTO musicians_music_genres(musician_id, music_genres_id)
VALUES ('112C8DD8-346B-426E-B06C-01BBA97DCD00', '112C8DD8-346B-426E-B06C-03BBA97DCD00'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD01', '112C8DD8-346B-426E-B06C-03BBA97DCD02'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD02', '112C8DD8-346B-426E-B06C-03BBA97DCD03'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD03', '112C8DD8-346B-426E-B06C-03BBA97DCD04');

INSERT INTO albums_music_genres(album_id, music_genres_id)
VALUES ('112C8DD8-346B-426E-B06C-05BBA97DCD00', '112C8DD8-346B-426E-B06C-03BBA97DCD00'),
       ('112C8DD8-346B-426E-B06C-05BBA97DCD01', '112C8DD8-346B-426E-B06C-03BBA97DCD01'),
       ('112C8DD8-346B-426E-B06C-05BBA97DCD01', '112C8DD8-346B-426E-B06C-03BBA97DCD02'),
       ('112C8DD8-346B-426E-B06C-05BBA97DCD02', '112C8DD8-346B-426E-B06C-03BBA97DCD03');

INSERT INTO compositions_music_genres (composition_id, music_genres_id)
VALUES ('112C8DD8-346B-426E-B06C-02BBA97DCD00', '112C8DD8-346B-426E-B06C-03BBA97DCD00'),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD01', '112C8DD8-346B-426E-B06C-03BBA97DCD01'),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD02', '112C8DD8-346B-426E-B06C-03BBA97DCD02'),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD03', '112C8DD8-346B-426E-B06C-03BBA97DCD04'),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD04', '112C8DD8-346B-426E-B06C-03BBA97DCD04'),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD05', '112C8DD8-346B-426E-B06C-03BBA97DCD04');