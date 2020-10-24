DROP TABLE IF EXISTS transactions, rates;

CREATE TABLE transactions (
  id             INT          NOT NULL PRIMARY KEY,
  transaction_date     DATE NOT NULL,
  title      VARCHAR(50)  NOT NULL,
  price DECIMAL NOT NULL,
  currency VARCHAR(50) NOT NULL
);

CREATE TABLE rates (
  id             INT auto_increment PRIMARY KEY,
  from_date       DATE NOT NULL,
  to_date       DATE NOT NULL,
  price       DECIMAL NOT NULL
);