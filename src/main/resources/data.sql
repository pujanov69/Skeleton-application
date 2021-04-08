DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    first_name varchar(50) NOT NULL,
    middle_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    email varchar(100) NOT NULL,
    password VARCHAR(60) NOT NULL,
    role_id INT(2) NOT NULL,
    enabled TINYINT(1) NOT NULL,
    locked TINYINT(1) NOT NULL
);

INSERT INTO user (username, first_name, middle_name, last_name, last_name, email, password, role_id, enabled, locked)
VALUES
('yoomes', 'Umesh', 'B.', 'Bhujel', 'pujanov69@gmail.com', '$11$H9wDLxAPTX5qp0doFKank.w6vgB7xPo1CJojH2AC0ovBY4Iu31oTS', 1, 1, 0);

DROP TABLE IF EXISTS role;
CREATE TABLE role(
    id INT AUTO_INCREMENT PRIMARY KEY,
    role varchar(10) NOT NULL,
);

INSERT INTO role(id, role)
VALUES
(1, 'ADMIN'),
(2, 'USER');
