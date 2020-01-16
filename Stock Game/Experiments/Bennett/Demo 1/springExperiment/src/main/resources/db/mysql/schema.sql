CREATE DATABASE IF NOT EXISTS springExperiment2;

ALTER DATABASE springExperiment2
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES ON springExperiment2.* TO pc@localhost IDENTIFIED BY 'pc';

USE springExperiment2;

CREATE TABLE IF NOT EXISTS users (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  user_name VARCHAR(255),
  password VARCHAR(80),
  INDEX(last_name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS transactions (
    id      INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users(id),
    stock_name VARCHAR(4),
    stock_quantity INTEGER,
    stock_price DOUBLE(10,2),
)engine=InnoDB;
