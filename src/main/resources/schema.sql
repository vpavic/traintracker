CREATE TABLE IF NOT EXISTS client_registration (
  id            VARCHAR(20) PRIMARY KEY,
  client_id     VARCHAR(100) NOT NULL UNIQUE,
  client_secret VARCHAR(100) NOT NULL
);
