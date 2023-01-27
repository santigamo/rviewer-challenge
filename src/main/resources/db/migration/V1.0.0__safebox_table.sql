create table if not exists safebox(
    id varchar(64) primary key,
    name varchar(255) unique not null,
    password varchar(255) not null,
    items text[] not null default '{}'
);