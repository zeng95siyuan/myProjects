DROP TABLE users IF EXISTS;
DROP TABLE transactions IF EXISTS;

CREATE TABLE users (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR_IGNORECASE(30),
  user_name    VARCHAR(255),
  password       VARCHAR(80),
);
CREATE INDEX users_last_name ON users (last_name);

CREATE TABLE transactions (
    id      INTEGER IDENTITY PRIMARY KEY,
    user_id INTEGER NOT NULL,
    stock_name VARCHAR(4),
    stock_quantity INTEGER,
    stock_price DECIMAL(10,2),
);

ALTER TABLE transactions ADD CONSTRAINT fk_transactions_user_id FOREIGN KEY (user_id) REFERENCES users (id);