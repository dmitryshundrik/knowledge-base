INSERT INTO sotylists (id, slug, title, "date", description, spotify_link)
VALUES (1, '2020', '20 лучших песен 2020 года', '2020-01-01',
        'Откровенный трэп-бэнгер от Cardi B, Perfume Genius и другие.',
        'https://open.spotify.com/playlist/5VH3DyCjQfdqfRWHyvJYvQ?si=e2a2189011ea459d'),

       (2, '2021', '25 лучших песен 2021 года', '2021-01-01',
        'Кавер Arooj Aftab на традиционную газель на урду, индастриал хип-хоп от Smerz и другие.',
        'https://open.spotify.com/playlist/7dLtbBxcj9kIhVVGgm6EtI?si=2d9dd932221d409c'),

       (3, '2022', '25 лучших песен 2022 года', '2022-01-01',
        'Элегантные мутации фальцета Perfume Genius, 23-минутная декламация стихов экспрессиониста Георга
                    Гейма от Diamanda Galás и другие.',
        'https://open.spotify.com/playlist/01Zvl7BwHNnnkqrmURAx1R?si=b255595d03384698');

INSERT INTO musicians (id, first_name, last_name, nick_name)
VALUES (1, '', '', 'Cardi B'),
       (2, '', '', 'Perfume Genius'),
       (3, '', '', 'The Chicks'),
       (4, 'Adrianne', 'Lenker', 'Adrianne Lenker'),
       (5, '', '', 'Arca'),
       (6, '', '', 'Kelly Lee Owens'),
       (7, 'Jessie', 'Ware', 'Jessie Ware'),
       (8, '', '', 'Phoebe Bridgers'),
       (9, 'Dua', 'Lipa', 'Dua Lipa'),
       (10, '', '', 'Charli XCX'),
       (11, '', '', 'Megan Thee Stallion'),
       (12, '', '', 'Laura Marling'),
       (13, '', '', 'Waxahatchee'),
       (14, '', '', 'Dorian Electra'),
       (15, '', '', 'Róisín Murphy'),
       (16, '', '', 'Harry Styles'),
       (17, '', '', 'Run The Jewels'),
       (18, '', '', 'Bruce Springsteen'),
       (19, '', '', 'Cakes Da Killa'),
       (20, '', '', '645AR');


INSERT INTO compositions (id, slug, title, musician_id, feature, "date")
--      Top 2020
VALUES (1, 'WAP', 'WAP', 1, '(feat. Megan Thee Stallion)', '2020-01-01'),
       (2, 'describe', 'Describe', 2, '', '2020-01-01'),
       (3, 'gaslighter', 'Gaslighter', 3, '', '2020-01-01'),
       (4, 'anything', 'Anything', 4, '', '2020-01-01'),
       (5, 'time', 'Time', 5, '', '2020-01-01'),
       (6, 'melt!', 'Melt!', 6, '', '2020-01-01'),
       (7, 'spotlight', 'Spotlight', 7, '', '2020-01-01'),
       (8, 'garden-song', 'Garden Song', 8, '', '2020-01-01'),
       (9, 'break-my-heart', 'Break My Heart', 9, '', '2020-01-01'),
       (10, 'claws', 'Claws', 10, '', '2020-01-01'),
       (11, 'savage-remix', 'Savage Remix', 11, '(feat. Beyoncé)', '2020-01-01'),
       (12, 'held-down', 'Held Down', 12, '', '2020-01-01'),
       (13, 'lilacs', 'Lilacs', 13, '', '2020-01-01'),
       (14, 'edgelord', 'Edgelord', 14, '(feat. Rebecca Black)', '2020-01-01'),
       (15, 'simulation', 'Simulation', 15, '', '2020-01-01'),
       (16, 'watermelon-sugar', 'Watermelon Sugar', 16, '(feat. 2 Chainz)', '2020-01-01'),
       (17, 'out-of-sight', 'Out Of Sight', 17, '', '2020-01-01'),
       (18, 'the-power-of-prayer', 'The Power Of Prayer', 18, '', '2020-01-01'),
       (19, 'don-dada', 'Don Dada', 19, '& Proper Villains', '2020-01-01'),
       (20, 'sum-bout-u', 'Sum Bout U', 20, '(feat. FKA twigs)', '2020-01-01');

INSERT INTO composition_genres (composition_id, genres)
VALUES (1, 'RAP'),
       (1, 'RAP'),
       (2, 'RAP'),
       (3, 'RAP'),
       (4, 'RAP'),
       (5, 'RAP'),
       (1, 'RAP'),
       (1, 'RAP'),
       (1, 'RAP'),
       (1, 'RAP');

INSERT INTO sotylistcompositions (id, rank, composition_id)
VALUES (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3),
       (4, 4, 4),
       (5, 5, 5),
       (6, 6, 6),
       (7, 7, 7),
       (8, 8, 8),
       (9, 9, 9),
       (10, 10, 10);

INSERT INTO sotylists_soty_list_compositions (sotylist_id, soty_list_compositions_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5);

INSERT INTO events (id, "date", description)
VALUES (1, '2020-01-01', 'This application has no explicit mapping for error, so you are seeing this as a fallback.');

INSERT INTO timelines (id, "type", title)
VALUES (1, 'MUSIC', 'Краткая история музыки');

INSERT INTO timelines_events (timeline_id, events_id)
VALUES (1, 1);