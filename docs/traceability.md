# 🗺️ Матрица отслеживания требований (Traceability Matrix)

| ID Требования | Краткое описание | GitHub Issue | Ветка разработки | Статус |
| :--- | :--- | :--- | :--- | :--- |
| **US-FR-1** | REST API получения рекомендаций по `user_id` | `#1` | `feature/diana-api-and-tests` | Выполнено |
| **US-FR-2** | Рекомендации через Telegram-бота (`/recommend`) | `#18` | `feature/diana-telegram-bot` | Выполнено |
| **US-FR-3, 4, 5** | CRUD REST API для динамических правил (`/rule`) | `#13` | `feature/diana-dynamic-api` | Выполнено |
| **US-FR-6** | Сбор и выдача статистики срабатываний (`/rule/stats`) | `#16` | `feature/mihail-stats-and-bot-backend` | Выполнено |
| **US-FR-7, 8** | Сброс кэша и метаданные сборки (`/management/*`) | `#17` | `feature/mihail-final-sprint` | Выполнено |
| **NFR-1** | Кэширование SQL-запросов через Caffeine | `#12` | `feature/mihail-dynamic-engine` | Выполнено |
| **NFR-2** | Глобальный перехват ошибок и логирование (`@ControllerAdvice`) | `#19` | `feature/mihail-final-refactoring` | Выполнено |
| **NFR-3** | Read-Only изоляция H2 и каскадное удаление в PostgreSQL | `#2, #15` | `feature/mihail-stats-and-bot-backend` | Выполнено |