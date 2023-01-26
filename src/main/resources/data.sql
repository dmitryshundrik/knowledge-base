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

       (6, '', '', ''),
       (7, '', '', ''),
       (8, '', '', ''),
       (9, '', '', ''),
       (10, '', '', '');

INSERT INTO compositions (id, slug, title, musician_id, feature, "date")
VALUES (1, 'WAP', 'WAP', 1, '(feat. Megan Thee Stallion)', '2020-01-01'),
       (2, 'describe', 'Describe', 2, '', '2020-01-01'),
       (3, 'gaslighter', 'Gaslighter', 3, '', '2020-01-01'),
       (4, 'anything', 'Anything', 4, '', '2020-01-01'),
       (5, 'time', 'Time', 5, '', '2020-01-01'),

       (6, '', '', null, '', '2020-01-01'),
       (7, '', '', null, '', '2020-01-01'),
       (8, '', '', null, '', '2020-01-01'),
       (9, '', '', null, '', '2020-01-01'),
       (10, '', '', null, '', '2020-01-01');

INSERT INTO composition_genres (composition_id, genres)
VALUES (1, 'HIP_HOP'),
       (1, 'RAP'),
       (2, ''),
       (3, ''),
       (4, ''),
       (5, ''),
       (1, ''),
       (1, ''),
       (1, ''),
       (1, '');

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