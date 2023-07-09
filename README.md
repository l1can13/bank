# **Bank Application**
Это приложение представляет собой простую банковскую систему, разработанную с использованием Java и Spring Framework. Оно предоставляет API для управления пользователями, банковскими счетами и банковскими картами.

# **Функционал**

## **Управление пользователями:**
* Создание нового пользователя с указанием имени, даты рождения и адреса.
* Получение списка всех пользователей.
* Получение списка счетов пользователя по его идентификатору.
* Получение списка карт пользователя по его идентификатору.
* Получение общего баланса пользователя (суммы балансов всех его счетов).

## **Управление банковскими счетами:**
* Создание нового счета с указанием владельца, валюты и начального баланса.
* Удаление счета по его номеру.
* Обновление данных счета по его номеру.

## **Управление банковскими картами:**
* Создание новой карты с указанием номера, связанного счета, даты окончания срока действия и CVV-кода.
* Удаление карты по ее номеру.
* Обновление данных карты по ее номеру.

## **Технологии:**
* Java
* Spring Framework
* Spring Boot
* Spring Data JPA
* JUnit
* PostgreSQL
* Swagger (документация API)
* Maven (управление зависимостями и сборка проекта)