INSERT INTO
    USER (id, moddate, regdate, email, name, password, username)
    VALUES (
        1,
        'aced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e205080a2e0809a7ec8078',
        'aced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770e05000007e205080a2e0809a7ec8078',
        'test@com',
        'tester',
        '$2a$10$CjlC8QXTgnkHzUEM/b9yZuEb/2wE4opnhA279Q4OHBDF7j2ovGOZW',
        'user'
    );
INSERT INTO
    USER_AUTHORITIES (user_id, authority)
    VALUES (1, 'USER');
