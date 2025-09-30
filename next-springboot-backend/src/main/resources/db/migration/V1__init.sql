create table if not exists posts (
  id bigserial primary key,
  title text not null,
  body text not null,
  created_at timestamptz default now()
);
