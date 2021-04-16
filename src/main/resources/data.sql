INSERT INTO role (id, name)
VALUES (1, 'ROLE_DOCTOR'),
       (2, 'ROLE_PATIENT');

INSERT INTO doctor (id, birth_date, full_name, password, phone_number, username, role_id, specialization, unit)
VALUES (1, '1973-07-31', 'Oleg Vynnyk', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa', '3800980000001',
        'OlegVynnyk', 1, 'Orthopedic surgeons', 'Surgical'),
       (2, '1949-11-28', 'Mykhailo Poplavskyi', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000002',
        'Poplavok', 1, 'Pediatrician', 'Pediatric');

INSERT INTO patient (id, birth_date, full_name, password, phone_number, username, role_id, address, category,
                     emergency_contact)
VALUES (1, '1994-03-01', 'Justin Drew Bieber', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000003',
        'JustinBieber', 2, 'Kyiv, Baby st. 1', 'Social worker', '3800980000013'),
       (2, '1947-03-25', 'Elton Hercules John', '$2a$10$nezDN5pqRv9P3U2y8cTac.1zic9AnMbKyIqO4/dvL7wIIm7tQrpUa',
        '3800980000004',
        'EltonJohn', 2, 'Kyiv, Rocket st. 12', 'Elderly', '3800980000014');