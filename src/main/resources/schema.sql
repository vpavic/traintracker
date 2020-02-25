CREATE TABLE IF NOT EXISTS client_registration
(
  id            VARCHAR(20) PRIMARY KEY,
  client_id     VARCHAR(100) NOT NULL UNIQUE,
  client_secret VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_profile
(
  email       VARCHAR(50) PRIMARY KEY,
  first_login BIGINT NOT NULL,
  last_login  BIGINT NOT NULL,
  favorites   JSONB  NOT NULL DEFAULT '{}' :: JSONB
);
