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

CREATE TABLE IF NOT EXISTS spring_session
(
  primary_id            CHAR(36) PRIMARY KEY,
  session_id            CHAR(36) NOT NULL UNIQUE,
  creation_time         BIGINT   NOT NULL,
  last_access_time      BIGINT   NOT NULL,
  max_inactive_interval INT      NOT NULL,
  expiry_time           BIGINT   NOT NULL,
  principal_name        VARCHAR(100)
);

CREATE INDEX ON spring_session (expiry_time);
CREATE INDEX ON spring_session (principal_name);

CREATE TABLE IF NOT EXISTS spring_session_attributes
(
  session_primary_id CHAR(36)     NOT NULL REFERENCES spring_session (primary_id) ON DELETE CASCADE,
  attribute_name     VARCHAR(200) NOT NULL,
  attribute_bytes    BYTEA        NOT NULL
);
