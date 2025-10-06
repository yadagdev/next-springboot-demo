-- PostgreSQL（pgcrypto使用）:
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE posts (
  id         UUID PRIMARY KEY,
  title      VARCHAR(200) NOT NULL,
  body       TEXT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
