INSERT INTO role (id, name)
VALUES (1, 'ROLE_DOCTOR'),
       (2, 'ROLE_PATIENT');

INSERT INTO doctor (id, birth_date, full_name, password, phone_number, username, role_id, specialization, unit)
VALUES (1, '1973-07-31', 'Oleg Vynnyk', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa', '3800980000001',
        'OlegVynnyk', 1, 'Orthopedic surgeons', 'Surgical'),
       (32, '1949-11-28', 'Mykhailo Poplavskyi', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000002',
        'Poplavok', 1, 'Pediatrician', 'Pediatric'),
       (33, '1928-05-26', 'Jack Kevorkian', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000002',
        'JackKevorkian', 1, 'Euthanasia medicine', 'Geriatrics');

INSERT INTO patient (id, birth_date, full_name, password, phone_number, username, role_id, address, category,
                     emergency_contact)
VALUES (2, '1994-03-01', 'Justin Drew Bieber', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000025', 'JustinBieber', 2, 'Kyiv, Baby st. 1', 'SOCIAL_WORKER', '3800980000013'),

       (4, '1947-04-25', 'Elton Hercules John', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000065', 'EltonJohn', 2, 'Kyiv, Rocket st. 12', 'ELDERLY', '38009802568033'),

       (5, '1970-05-01', 'Queen Latifah', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000035', 'QueenLatifah', 2, 'Kyiv, Baby st. 12', 'SOCIAL_WORKER', '3800925900065'),

       (6, '1960-06-25', 'Meryl Streep', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000088', 'MerylStreep', 2, 'Kyiv, Rocket st. 14', 'ELDERLY', '3800980000014'),

       (7, '1970-03-01', 'Lady Gaga', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000025', 'LadyGaga', 2, 'Kyiv, Baby st. 1', 'SOCIAL_WORKER', '3800267000097'),

       (8, '1947-05-25', 'Emeril Lagasse', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000087', 'EmerilLagasse', 2, 'Kyiv, Rocket st. 16', 'ELDERLY', '3800996800014'),

       (9, '1901-06-01', 'Edgar Allan Poe', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000091', 'EdgarAllanPoe', 2, 'Kyiv, Baby st. 1', 'ELDERLY', '3800980000055'),

       (10, '1970-08-25', 'Cameron Diaz', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000087', 'CameronDiaz', 2, 'Kyiv, Rocket st. 18', 'ELDERLY', '38009236565014'),

       (11, '1974-10-01', 'Bethenny Frankel', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000065', 'BethennyFrankel', 2, 'Kyiv, Baby st. 10', 'SOCIAL_WORKER', '3800936500065'),

       (12, '1966-12-25', 'Wolfgang Puck', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000011', 'WolfgangPuck', 2, 'Kyiv, Rocket st. 22', 'ELDERLY', '3800980000014'),

       (13, '1955-03-01', 'Bruce Springsteen', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000093', 'BruceSpringsteen', 2, 'Kyiv, Baby st. 54', 'SOCIAL_WORKER', '3800657890013'),

       (14, '1965-04-25', 'Tiger Woods', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000064', 'TigerWoods', 2, 'Kyiv, Rocket st. 32', 'ELDERLY', '3800980000096'),

       (15, '1963-05-01', 'John Bon Jovi', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000083', 'JohnBonJovi', 2, 'Kyiv, Baby st. 211', 'OTHER', '3800670000088'),

       (16, '1961-06-25', 'Liv Tyler', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000074', 'LivTyler', 2, 'Kyiv, Rocket st. 36', 'OTHER', '3800550000068'),

       (17, '1960-09-01', 'Barack Obama', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000093', 'BarackObama', 2, 'Kyiv, Baby st. 118', 'OTHER', '380555000096'),

       (18, '1962-10-25', 'Scarlett Johansson', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000055', 'ScarlettJohansson', 2, 'Kyiv, Rocket st. 12', 'ELDERLY', '3800960000014'),

       (19, '1964-11-01', 'JKate Moss', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000093', 'KateMoss', 2, 'Kyiv, Baby st. 100', 'OTHER', '3800670110083'),

       (20, '1966-12-25', 'Ryan Reynolds', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000054', 'RyanReynolds', 2, 'Kyiv, Rocket st. 132', 'OTHER', '3800985600044'),

       (21, '1968-01-01', 'Kanye West', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000003', 'KanyeWest', 2, 'Kyiv, Baby st. 199', 'OTHER', '38006306500022'),

       (22, '1970-05-25', 'George Clooney', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000099', 'GeorgeClooney', 2, 'Kyiv, Rocket st. 121', 'OTHER', '3800910058065');


INSERT INTO queue (id, application_date, vaccination_date, patient_id)
VALUES (1, '2021-02-20', '2021-03-06T10:28:39', 2),
       (2, '2021-03-06', '2021-04-13T15:05:00', 2),
       (3, '2021-03-14', '2021-04-14T13:15:00', 4),
       (4, '2021-03-15', '2021-04-15T16:35:00', 5),
       (5, '2021-03-15', '2021-04-15T18:00:00', 6),
       (6, '2021-03-15', '2021-04-15T19:20:00', 7),
       (7, '2021-03-11', '2021-04-20T10:20:00', 8),
       (8, '2021-03-12', '2021-04-20T10:30:00', 9),
       (9, '2021-03-22', '2021-04-20T11:20:00', 10),
       (10, '2021-03-12', '2021-04-20T13:20:00', 11),
       (11, '2021-03-12', '2021-04-20T18:20:00', 12),
       (12, '2021-03-14', '2021-04-20T12:20:00', 13),
       (13, '2021-03-18', '2021-04-20T19:20:00', 14),
       (14, '2021-03-18', '2021-04-21T10:20:00', 15),
       (15, '2021-03-20', '2021-04-21T11:20:00', 16),
       (16, '2021-04-21', '2021-04-21T12:20:00', 17),
       (17, '2021-04-21', null, 18),
       (18, '2021-04-20', null, 19),
       (19, '2021-04-20', null, 20),
       (20, '2021-04-20', null, 21),
       (21, '2021-04-12', null, 22);

INSERT INTO vaccination (id, additional_information, doze_number, vaccination_date, vaccine_type, doctor_id)
VALUES (1, 'Sang Boyfriend while he was getting the vaccine', 1, '2021-04-15T14:25:10', 'MODERNA', 1);
INSERT INTO vaccination (id, additional_information, doze_number, vaccination_date, vaccine_type, doctor_id)
VALUES (2, 'headache, fever', 1, '2021-04-01T14:25:10', 'JOHNSON', 32);
INSERT INTO vaccination (id, additional_information, doze_number, vaccination_date, vaccine_type, doctor_id)
VALUES (3, 'headache', 2, '2021-04-21T14:25:10', 'JOHNSON', 33);
INSERT INTO vaccination (id, additional_information, doze_number, vaccination_date, vaccine_type, doctor_id)
VALUES (4, 'Sang Boyfriend while he was getting the vaccine', 1, '2021-04-15T14:25:10', 'MODERNA', 1);
INSERT INTO vaccination (id, additional_information, doze_number, vaccination_date, vaccine_type, doctor_id)
VALUES (5, 'headache', 1, '2021-04-15T14:25:10', 'JOHNSON', 32);
INSERT INTO vaccination (id, additional_information, doze_number, vaccination_date, vaccine_type, doctor_id)
VALUES (6, 'allergic reaction', 1, '2021-04-15T14:25:10', 'PFIZER', 33);
INSERT INTO vaccination (id, additional_information, doze_number, vaccination_date, vaccine_type, doctor_id)
VALUES (7, 'allergic reaction', 1, '2021-04-15T14:25:10', 'MODERNA', 1);
INSERT INTO vaccination (id, additional_information, doze_number, vaccination_date, vaccine_type, doctor_id)
VALUES (8, 'allergic reaction', 1, '2021-04-15T14:25:10', 'JOHNSON', 32);
INSERT INTO vaccination (id, additional_information, doze_number, vaccination_date, vaccine_type, doctor_id)
VALUES (9, 'headache', 1, '2021-04-15T14:25:10', 'PFIZER', 33);


INSERT INTO patient_vaccinations(patient_id, vaccinations_id)
VALUES (2, 1),
       (4, 2),
       (4, 3),
       (5, 4),
       (6, 5),
       (7, 6),
       (8, 7),
       (9, 8),
       (10, 9)
;

INSERT INTO diary_entry(id, date, temperature)
VALUES (1, '2021-04-15', 36.6),
       (2, '2021-04-16', 37.3),
       (3, '2021-04-17', 36.8),
       (4, '2021-04-15', 36.6),
       (5, '2021-04-16', 37.3),
       (6, '2021-04-17', 36.8),
       (7, '2021-04-15', 36.6),
       (8, '2021-04-16', 37.3),
       (9, '2021-04-17', 36.8),
       (10, '2021-04-15', 36.6),
       (11, '2021-04-16', 36.3),
       (12, '2021-04-17', 36.8),
       (13, '2021-04-15', 36.6),
       (14, '2021-04-16', 36.3),
       (15, '2021-04-17', 36.8),
       (16, '2021-04-15', 36.6),
       (17, '2021-04-16', 36.3),
       (18, '2021-04-17', 36.8),
       (19, '2021-04-15', 36.6),
       (20, '2021-04-16', 36.3),
       (21, '2021-04-17', 36.8),
       (22, '2021-04-15', 36.6),
       (23, '2021-04-16', 37.3),
       (24, '2021-04-17', 36.8)
;

INSERT INTO patient_health_diary_entries(patient_id, health_diary_entries_id)
VALUES (2, 1),
       (2, 2),
       (2, 3),
       (4, 4),
       (4, 5),
       (4, 6),
       (5, 7),
       (6, 8),
       (5, 9),
       (6, 10),
       (6, 11),
       (6, 12),
       (7, 13),
       (7, 14),
       (7, 15),
       (8, 16),
       (8, 17),
       (8, 18),
       (9, 19),
       (9, 20),
       (9, 21),
       (10, 22),
       (10, 23),
       (10, 24)
;