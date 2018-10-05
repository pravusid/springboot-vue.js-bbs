INSERT INTO
    user (id, moddate, regdate, email, name, password, username)
    VALUES (
        null,
        '2018-05-24 01:04:11.3',
        '2018-05-24 01:04:11.3',
        'test@com',
        'tester',
        '$2a$10$CjlC8QXTgnkHzUEM/b9yZuEb/2wE4opnhA279Q4OHBDF7j2ovGOZW',
        'user'
    );

INSERT INTO
    user_authorities (user_id, authority)
    VALUES (1, 'USER');

INSERT INTO
  oauth_client_details (
    id,
    client_id,
    client_secret,
    resource_ids,
    scope,
    authorized_grant_types,
    web_server_redirect_uri,
    authorities,
    access_token_validity,
    refresh_token_validity,
    additional_information,
    autoapprove
  )
  VALUES (
    1,
    'vueclient',
    'vueclientpwd',
    'spring-boot-application',
    'read,write',
    'authorization_code,password,implicit,refresh_token',
    null,
    'USER',
    36000,
    2592000,
    null,
    'true'
  );
