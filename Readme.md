# Task Tracker

## Описание

Проект состоит из нескольких страниц: главная страница пользователя, на которой отображаются все его проекты;
страницы регистрации-аутентификации; страницы проектов и тасков. Также в проекте предусмотрен сервис отправки
email-ов о изменениях внесённых в проекты пользователя, а также сервис сбора статистики и логов.

## Микросервисы

1. [Task Tracker Main](https://github.com/antidimon/TaskTracker/tree/main/TaskTrackerMain): Представляет собой основную часть работы с пользователем
и создание событий, которые отлавливают другие сервисы через Kafka. Также отвечает за web представления.

2. [Task Tracker Emails](https://github.com/antidimon/TaskTracker/tree/main/TaskTrackerEmails): Сервис отправки email-ов.
Получает либо событие через kafka от Main сервиса, либо используя REST получает данные по дневной статистике от сервиса статистики, 
после чего отправляет email сообщения пользователям.

3. [Task Tracker Statistics](https://github.com/antidimon/TaskTracker/tree/main/TaskTrackerStatistics): Собирает статистику по действиям пользователей и
в полночь отправляет данные о проделанной работе каждого пользователя за день.


## Технологии

* Java
* Spring Boot
* Spring REST
* Spring MVC
* Spring Security
* Spring Data JPA
* PostgreSQL
* Flyway
* Apache Kafka
* Docker
* JUnit

## Запуск 

1. Склонировать репозиторий

    ```bash
    git clone https://github.com/antidimon/TaskTracker
    ```

2. Назначить в файлах .env необходимые параметры

3. Собрать контейнер в корне проекта

   ```bash
    docker-compose up -d --build
    ```
