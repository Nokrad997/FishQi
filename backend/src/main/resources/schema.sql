-- CREATE TABLE Users (
--     user_id INT AUTO_INCREMENT PRIMARY KEY,
--     email VARCHAR(255) UNIQUE NOT NULL,
--     username VARCHAR(255) NOT NULL,
--     password VARCHAR(255) NOT NULL,
--     is_admin BOOLEAN NOT NULL
-- );

-- CREATE TABLE FishQSet (
--     set_id INT AUTO_INCREMENT PRIMARY KEY,
--     title VARCHAR(255) NOT NULL,
--     language VARCHAR(255) NOT NULL,
--     visibility BOOLEAN NOT NULL,
--     owner_id INT,
--     description TEXT,
--     FOREIGN KEY (owner_id) REFERENCES Users(user_id) ON DELETE CASCADE
-- );

-- CREATE TABLE files (
--     file_id INT AUTO_INCREMENT PRIMARY KEY,
--     ftp_path VARCHAR(255) NOT NULL
-- );

-- CREATE TABLE FishQa (
--     fishQa_id INT AUTO_INCREMENT PRIMARY KEY,
--     set_id INT,
--     file_id_front INT,
--     file_id_back INT,
--     FOREIGN KEY (set_id) REFERENCES FishQSet(set_id) ON DELETE CASCADE,
--     FOREIGN KEY (file_id_front) REFERENCES files(file_id) ON DELETE RESTRICT,
--     FOREIGN KEY (file_id_back) REFERENCES files(file_id) ON DELETE RESTRICT
-- );


