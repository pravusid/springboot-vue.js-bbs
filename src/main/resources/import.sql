INSERT INTO
    USER (id, moddate, regdate, email, name, password, username)
    VALUES (
        1,
        '2018-05-24 01:04:11.3',
        '2018-05-24 01:04:11.3',
        'test@com',
        'tester',
        '$2a$10$CjlC8QXTgnkHzUEM/b9yZuEb/2wE4opnhA279Q4OHBDF7j2ovGOZW',
        'user'
    );
INSERT INTO
    USER_AUTHORITIES (user_id, authority)
    VALUES (1, 'USER');
