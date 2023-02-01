INSERT INTO sotylists (id, slug, title, "year", description, spotify_link)
VALUES (1, '2020', '20 лучших песен 2020 года', 2020,
        'Откровенный трэп-бэнгер от Cardi B, Perfume Genius и другие.',
        'https://open.spotify.com/playlist/5VH3DyCjQfdqfRWHyvJYvQ?si=e2a2189011ea459d'),

       (2, '2021', '25 лучших песен 2021 года', 2021,
        'Кавер Arooj Aftab на традиционную газель на урду, индастриал хип-хоп от Smerz и другие.',
        'https://open.spotify.com/playlist/7dLtbBxcj9kIhVVGgm6EtI?si=2d9dd932221d409c'),

       (3, '2022', '25 лучших песен 2022 года', 2022,
        'Элегантные мутации фальцета Perfume Genius, 23-минутная декламация стихов экспрессиониста Георга
                    Гейма от Diamanda Galás и другие.',
        'https://open.spotify.com/playlist/01Zvl7BwHNnnkqrmURAx1R?si=b255595d03384698');

INSERT INTO musicians (id, slug, first_name, last_name, nick_name, birth_date)
VALUES (1, 'cardi-b', 'Belcalis Marlenis', 'Almánzar', 'Cardi B', '1992-10-11'),
       (2, 'perfume-genius', 'Michael Alden', 'Hadreas', 'Perfume Genius', '1981-09-25'),
       (3, 'the-chicks', '', '', 'The Chicks', '2020-01-01'),
       (4, 'adrianne-lenker', 'Adrianne', 'Lenker', 'Adrianne Lenker', '2020-01-01'),
       (5, 'arca', '', '', 'Arca', '2020-01-01'),
       (6, 'kelly-lee-owens', '', '', 'Kelly Lee Owens', '2020-01-01'),
       (7, 'jessie-ware', 'Jessie', 'Ware', 'Jessie Ware', '2020-01-01'),
       (8, 'phoebe-bridgers', '', '', 'Phoebe Bridgers', '2020-01-01'),
       (9, 'dua-lipa', 'Dua', 'Lipa', 'Dua Lipa', '2020-01-01'),
       (10, 'charli-xcx', '', '', 'Charli XCX', '2020-01-01'),
       (11, 'megan-thee-stallion', '', '', 'Megan Thee Stallion', '2020-01-01'),
       (12, 'laura-marling', '', '', 'Laura Marling', '2020-01-01'),
       (13, 'waxahatchee', '', '', 'Waxahatchee', '2020-01-01'),
       (14, 'dorian-electra', '', '', 'Dorian Electra', '2020-01-01'),
       (15, 'róisín-murphy', '', '', 'Róisín Murphy', '2020-01-01'),
       (16, 'harry-styles', '', '', 'Harry Styles', '2020-01-01'),
       (17, 'run-the-jewels', '', '', 'Run The Jewels', '2020-01-01'),
       (18, 'bruce-springsteen', '', '', 'Bruce Springsteen', '2020-01-01'),
       (19, 'cakes-da-killa', '', '', 'Cakes Da Killa', '2020-01-01'),
       (20, '645AR', '', '', '645AR', '2020-01-01');


INSERT INTO compositions (id, slug, title, musician_id, feature, "year", year_end_rank)
--      Top 2020
VALUES (1, 'WAP', 'WAP', 1, '(feat. Megan Thee Stallion)', 2020, 1),
       (2, 'describe', 'Describe', 2, '', 2020, 2),
       (3, 'gaslighter', 'Gaslighter', 3, '', 2020, 3),
       (4, 'anything', 'Anything', 4, '', 2020, 4),
       (5, 'time', 'Time', 5, '', 2020, 5),
       (6, 'melt!', 'Melt!', 6, '', 2020, 6),
       (7, 'spotlight', 'Spotlight', 7, '', 2020, 7),
       (8, 'garden-song', 'Garden Song', 8, '', 2020, 8),
       (9, 'break-my-heart', 'Break My Heart', 9, '', 2020, 9),
       (10, 'claws', 'Claws', 10, '', 2020, 10),
       (11, 'savage-remix', 'Savage Remix', 11, '(feat. Beyoncé)', 2020, 11),
       (12, 'held-down', 'Held Down', 12, '', 2020, 12),
       (13, 'lilacs', 'Lilacs', 13, '', 2020, 13),
       (14, 'edgelord', 'Edgelord', 14, '(feat. Rebecca Black)', 2020, 14),
       (15, 'simulation', 'Simulation', 15, '', 2020, 15),
       (16, 'watermelon-sugar', 'Watermelon Sugar', 16, '(feat. 2 Chainz)', 2020, 16),
       (17, 'out-of-sight', 'Out Of Sight', 17, '', 2020, 17),
       (18, 'the-power-of-prayer', 'The Power Of Prayer', 18, '', 2020, 18),
       (19, 'don-dada', 'Don Dada', 19, '& Proper Villains', 2020, 19),
       (20, 'sum-bout-u', 'Sum Bout U', 20, '(feat. FKA twigs)', 2020, 20);

INSERT INTO composition_genres (composition_id, genres)
VALUES (1, 'RAP'),
       (1, 'HIP_HOP'),
       (2, 'ROCK'),
       (2, 'SHOEGASE');

INSERT INTO events (id, "date", description)
VALUES (1, '2020-01-01', 'This application has no explicit mapping for error, so you are seeing this as a fallback.');

INSERT INTO timelines (id, "type", title)
VALUES (1, 'MUSIC', 'Краткая история музыки');

INSERT INTO timelines_events (timeline_id, events_id)
VALUES (1, 1);