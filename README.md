# Дипломный проект по профессии «Тестировщик»

В рамках проекта необходимо автоматизировать позитивные и негативные сценарии покупки тура по карте и в кредит.
Данные по картам обрабатываются отдельными сервисами:
- сервисом платежей Payment Gate;
- кредитным сервисом Credit Gate.

Необходимо выполнить запросы в базу, проверяющие корректность внесения информации приложением.

## Начало работы

* Для получения копии проекта для работы, необходимо склонировать его к себе на ПК с помощью команды 
git clone git@github.com:NekrasovaMN/GraduateWork.git в терминале Git

### Prerequisites

Для работы с проектом необходимо к себе на ПК установить Git, InetelliJ IDEA, Docker Desktop

### Установка и запуск

* Установить на ПК [Git](https://netology-code.github.io/guides/git/)

* Установить на ПК [IntelliJ IDEA](https://harrix.dev/blog/2019/install-intellij-idea/)

* Открыть в IntelliJ IDEA проект GraduateWork (File-Open-выбрать папку проекта)

* Установить на ПК
[Docker Desktop](https://github.com/netology-code/aqa-homeworks/blob/master/docker/installation.md) и открыть его для работы. 

* Запустить необходимые базы данных (MySQL и PostgreSQL), а также NodeJS. Параметры для запуска хранятся в файле `docker-compose.yml`.
 Для их запуска необходимо ввести в терминале IntelliJ IDEA команду:

  docker-compose up --build

* Для запуска сервиса с указанием пути к базе данных выполнить следующие команды в командной строке:

-для mysql

java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar aqa-shop.jar

-для postgresql

java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar aqa-shop.jar



* Запуск тестов также стоит выполнить с параметрами, указав путь к базе данных в командной строке:

-для mysql:

./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"

-для postgresql:

./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
