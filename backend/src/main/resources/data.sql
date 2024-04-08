-- INSERT INTO Users (email, username, password, is_admin) VALUES ('user1@example.com', 'user1', 'password1', FALSE);
-- INSERT INTO Users (email, username, password, is_admin) VALUES ('user2@example.com', 'user2', 'password2', FALSE);
-- INSERT INTO Users (email, username, password, is_admin) VALUES ('user3@example.com', 'user3', 'password3', FALSE);
-- INSERT INTO Users (email, username, password, is_admin) VALUES ('user4@example.com', 'user4', 'password4', TRUE);
-- INSERT INTO Users (email, username, password, is_admin) VALUES ('user5@example.com', 'user5', 'password5', TRUE);

-- INSERT INTO FishQSet (title, language, visibility, owner_id, description) VALUES ('Podstawy niemieckiego', 'Niemiecki', TRUE, 1, 'Podstawowe słówka i zwroty');
-- INSERT INTO FishQSet (title, language, visibility, owner_id, description) VALUES ('Zaawansowany angielski', 'Angielski', TRUE, 2, 'Zaawansowane słownictwo biznesowe');
-- INSERT INTO FishQSet (title, language, visibility, owner_id, description) VALUES ('Francuski dla początkujących', 'Francuski', FALSE, 3, 'Podstawowe wyrażenia i gramatyka');
-- INSERT INTO FishQSet (title, language, visibility, owner_id, description) VALUES ('Włoski kulinarny', 'Włoski', TRUE, 4, 'Słownictwo związane z kuchnią i gotowaniem');
-- INSERT INTO FishQSet (title, language, visibility, owner_id, description) VALUES ('Hiszpański podróżniczy', 'Hiszpański', FALSE, 5, 'Przydatne zwroty na wakacje');

-- INSERT INTO files (ftp_path) VALUES ('/ftp/slowka/niemiecki_podstawy_front.pdf');
-- INSERT INTO files (ftp_path) VALUES ('/ftp/slowka/niemiecki_podstawy_back.pdf');
-- INSERT INTO files (ftp_path) VALUES ('/ftp/slowka/angielski_zaawansowany_front.pdf');
-- INSERT INTO files (ftp_path) VALUES ('/ftp/slowka/angielski_zaawansowany_back.pdf');
-- INSERT INTO files (ftp_path) VALUES ('/ftp/slowka/francuski_poczatkujacy_front.pdf');

-- INSERT INTO FishQa (set_id, file_id_front, file_id_back) VALUES (1, 1, 2);
-- INSERT INTO FishQa (set_id, file_id_front, file_id_back) VALUES (2, 3, 4);
-- INSERT INTO FishQa (set_id, file_id_front, file_id_back) VALUES (3, 5, 1);
-- INSERT INTO FishQa (set_id, file_id_front, file_id_back) VALUES (4, 2, 3);
-- INSERT INTO FishQa (set_id, file_id_front, file_id_back) VALUES (5, 4, 5);
