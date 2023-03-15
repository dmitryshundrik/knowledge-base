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

INSERT INTO musicians (id, slug, first_name, last_name, nick_name, birth_date, image)
VALUES ('112C8DD8-346B-426E-B06C-01BBA97DCD00', 'cardi-b', 'Belcalis Marlenis', 'Almánzar', 'Cardi B', '1992-10-11',
        '/images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD01', 'perfume-genius', 'Michael Alden', 'Hadreas', 'Perfume Genius',
        '1981-09-25',
        '/images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD02', 'the-chicks', 'none', 'none', 'The Chicks', '2020-01-01',
        '/images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD03', 'adrianne-lenker', 'Adrianne', 'Lenker', 'Adrianne Lenker',
        '2020-01-01',
        '/images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD04', 'arca', 'none', 'none', 'Arca', '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD05', 'kelly-lee-owens', 'Kelly', 'Lee-Owens', 'Kelly Lee Owens',
        '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD06', 'jessie-ware', 'Jessie', 'Ware', 'Jessie Ware', '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD07', 'phoebe-bridgers', 'Phoebe', 'Bridgers', 'Phoebe Bridgers',
        '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD08', 'dua-lipa', 'Dua', 'Lipa', 'Dua Lipa', '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD09', 'charli-xcx', 'none', 'none', 'Charli XCX', '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD10', 'megan-thee-stallion', 'none', 'none', 'Megan Thee Stallion',
        '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD11', 'laura-marling', 'Laura', 'Marling', 'Laura Marling', '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD12', 'waxahatchee', 'none', 'none', 'Waxahatchee', '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD13', 'dorian-electra', 'none', 'none', 'Dorian Electra', '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD14', 'róisín-murphy', 'Róisín', 'Murphy', 'Róisín Murphy', '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD15', 'harry-styles', 'Harry', 'Styles', 'Harry Styles', '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD16', 'run-the-jewels', 'none', 'none', 'Run The Jewels', '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD17', 'bruce-springsteen', 'Bruce', 'Springsteen', 'Bruce Springsteen',
        '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD18', 'cakes-da-killa', 'none', 'none', 'Cakes Da Killa', '2020-01-01',
        '../images/cardi-b-img.png'),
       ('112C8DD8-346B-426E-B06C-01BBA97DCD19', '645AR', 'none', 'none', '645AR', '2020-01-01',
        '../images/cardi-b-img.png');
--
--
INSERT INTO compositions (id, slug, title, musician_id, feature, "year", year_end_rank)
--      Top 2020
VALUES ('112C8DD8-346B-426E-B06C-02BBA97DCD00', 'WAP', 'WAP', '112C8DD8-346B-426E-B06C-01BBA97DCD00',
        '(feat. Megan Thee Stallion)', 2020, 1),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD01', 'describe', 'Describe', '112C8DD8-346B-426E-B06C-01BBA97DCD01', '',
        2020, 2),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD02', 'gaslighter', 'Gaslighter', '112C8DD8-346B-426E-B06C-01BBA97DCD02', '',
        2020, 3),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD03', 'anything', 'Anything', '112C8DD8-346B-426E-B06C-01BBA97DCD03', '',
        2020, 4),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD04', 'time', 'Time', '112C8DD8-346B-426E-B06C-01BBA97DCD04', '', 2020, 5),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD05', 'melt!', 'Melt!', '112C8DD8-346B-426E-B06C-01BBA97DCD05', '', 2020, 6),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD06', 'spotlight', 'Spotlight', '112C8DD8-346B-426E-B06C-01BBA97DCD06', '',
        2020, 7),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD07', 'garden-song', 'Garden Song', '112C8DD8-346B-426E-B06C-01BBA97DCD07',
        '', 2020, 8),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD08', 'break-my-heart', 'Break My Heart',
        '112C8DD8-346B-426E-B06C-01BBA97DCD08', '', 2020, 9),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD09', 'claws', 'Claws', '112C8DD8-346B-426E-B06C-01BBA97DCD09', '', 2020, 10),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD10', 'savage-remix', 'Savage Remix', '112C8DD8-346B-426E-B06C-01BBA97DCD10',
        '(feat. Beyoncé)', 2020, 11),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD11', 'held-down', 'Held Down', '112C8DD8-346B-426E-B06C-01BBA97DCD11', '',
        2020, 12),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD12', 'lilacs', 'Lilacs', '112C8DD8-346B-426E-B06C-01BBA97DCD12', '', 2020,
        13),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD13', 'edgelord', 'Edgelord', '112C8DD8-346B-426E-B06C-01BBA97DCD13',
        '(feat. Rebecca Black)', 2020, 14),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD14', 'simulation', 'Simulation', '112C8DD8-346B-426E-B06C-01BBA97DCD14', '',
        2020, 15),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD15', 'watermelon-sugar', 'Watermelon Sugar',
        '112C8DD8-346B-426E-B06C-01BBA97DCD15', '(feat. 2 Chainz)', 2020, 16),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD16', 'out-of-sight', 'Out Of Sight', '112C8DD8-346B-426E-B06C-01BBA97DCD16',
        '', 2020, 17),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD17', 'the-power-of-prayer', 'The Power Of Prayer',
        '112C8DD8-346B-426E-B06C-01BBA97DCD17', '', 2020, 18),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD18', 'don-dada', 'Don Dada', '112C8DD8-346B-426E-B06C-01BBA97DCD18',
        '& Proper Villains', 2020, 19),
       ('112C8DD8-346B-426E-B06C-02BBA97DCD19', 'sum-bout-u', 'Sum Bout U', '112C8DD8-346B-426E-B06C-01BBA97DCD19',
        '(feat. FKA twigs)', 2020, 20);

INSERT INTO musicgenres (id, slug, title, music_genre_type)
VALUES ('112C8DD8-346B-426E-B06C-03BBA97DCD00', 'rap', 'Rap', 'CONTEMPORARY'),
       ('112C8DD8-346B-426E-B06C-03BBA97DCD01', 'pop', 'Pop', 'CONTEMPORARY'),
       ('112C8DD8-346B-426E-B06C-03BBA97DCD02', 'rock', 'Rock', 'CONTEMPORARY'),
       ('112C8DD8-346B-426E-B06C-03BBA97DCD03', 'jazz', 'Jazz', 'CONTEMPORARY');

INSERT INTO musicperiods(id, slug, title)
VALUES ('112C8DD8-346B-426E-B06C-04BBA97DCD00', 'renaissance', 'Возрождение'),
       ('112C8DD8-346B-426E-B06C-04BBA97DCD01', 'baroque', 'Барокко'),
       ('112C8DD8-346B-426E-B06C-04BBA97DCD02', 'classic', 'Классицизм');
--
-- INSERT INTO composition_genres (composition_id, genres)
-- VALUES (1, 'RAP'),
--        (1, 'HIP_HOP'),
--        (2, 'ROCK'),
--        (2, 'SHOEGASE');
--
-- INSERT INTO events (id, "year", description)
-- VALUES (1, 2020, 'This application has no explicit mapping for error, so you are seeing this as a fallback.'),
--        (2, 2022, 'This application has no explicit mapping for error, so you are seeing this as a fallback.');
--
-- INSERT INTO musicians_events(musician_id, events_id)
-- VALUES (1, 1),
--        (1, 2);
--
-- INSERT INTO timelines (id, "type", title)
-- VALUES (1, 'MUSIC', 'Краткая история музыки');
--
-- INSERT INTO timelines_events (timeline_id, events_id)
-- VALUES (1, 1);