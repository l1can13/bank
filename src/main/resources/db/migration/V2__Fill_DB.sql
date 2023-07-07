-- Добавление данных в таблицу "user"
insert into "user" (id, birthdate, address, "name")
values (1, '1990-01-01', '123 Main St, City', 'John Smith'),
       (2, '1995-02-02', '456 Elm St, Town', 'Jane Doe'),
       (3, '1988-05-10', '789 Oak St, Village', 'Michael Johnson');

-- Добавление данных в таблицу "account"
insert into account ("number", balance, "user_id", currency)
values (1001001001001001, 5000.00, 1, 'USD'),
       (2002002002002002, 7000.00, 2, 'EUR'),
       (3003003003003003, 3500.00, 3, 'GBP');

-- Добавление данных в таблицу "card"
insert into card ("number", cvv, account_number, expiration_date)
values (1111222233334444, 123, 1001001001001001, '2024-12-01'),
       (2222333344445555, 456, 2002002002002002, '2023-10-01'),
       (3333444455556666, 789, 3003003003003003, '2025-06-30'),
       (2222444433331111, 321, 2002002002002002, '2023-05-01');