-- Создание последовательности для идентификаторов.
create sequence hibernate_sequence start 1 increment 1;

-- Создание таблицы "account".
create table account
(
    "number"  bigint not null,
    balance   float(53),
    "user_id" bigint,
    currency  varchar(255),
    primary key ("number")
);

-- Создание таблицы "card".
create table card
(
    "number"        bigint not null,
    cvv             integer,
    account_number  bigint,
    expiration_date timestamp(6),
    primary key ("number")
);

-- Создание таблицы "user".
create table "user"
(
    id        bigint not null,
    birthdate timestamp(6),
    address   varchar(255),
    "name"    varchar(255),
    primary key (id)
);

-- Добавление внешних ключей.
alter table if exists account
    add constraint FKlpehafsh6t7ka32286sp7ipi7
    foreign key ("user_id") references "user";

alter table if exists card
    add constraint FK8v67eys6tqflsm6hrdgru2phu
    foreign key (account_number) references account;