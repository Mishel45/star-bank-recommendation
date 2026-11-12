# 🚀 Руководство по развертыванию приложения

## 1. Необходимые сервисы
* **Java JDK 17** или выше.
* **PostgreSQL 14+** (основная БД для хранения динамических правил и статистики).
* **Файл базы данных транзакций `transaction.mv.db`** в корневом каталоге запуска (подключается в режиме `READ_ONLY`).

## 2. Переменные среды (Environment Variables)
Для запуска в продакшене конфигурация задается через переменные окружения ОС:

* `SPRING_DATASOURCE_URL` — JDBC URL основной БД PostgreSQL (например: `jdbc:postgresql://localhost:5432/starbank_rules`)
* `SPRING_DATASOURCE_USERNAME` — логин пользователя PostgreSQL (например: `postgres`)
* `SPRING_DATASOURCE_PASSWORD` — пароль пользователя PostgreSQL
* `STARBANK_TRANSACTIONS_DATASOURCE_URL` — путь к файлу H2 транзакций (`jdbc:h2:file:./transaction;ACCESS_MODE_DATA=r`)
* `TELEGRAM_BOT_USERNAME` — имя Telegram-бота
* `TELEGRAM_BOT_TOKEN` — API-токен Telegram-бота

## 3. Команды сборки и запуска
1. **Сборка JAR-архива с генерацией `build-info.properties`:**
   ```bash
   ./mvnw clean package
   ```
2. **Запуск собранного приложения:**
   ```bash
   java -jar target/recommendation-0.0.1-SNAPSHOT.jar
   ```