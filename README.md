# 202012-otuskotlin-marketplace

Учебный проект курса [Kotlin Backend
Developer](https://otus.ru/lessons/kotlin/?int_source=courses_catalog&int_term=programming). Marketplace -- это
площадка, на которой пользователи выставляют предложения и потребности. Задача площадки -- предоставить наиболее
подходящие варианты в обоих случаях: для предложения -- набор вариантов с потребностями, для потребностей -- набор
вариантов с предложениями.

## Учебный маркетинг приложения

Считаем, что целевая аудитория учебного сервиса -- это средние и крупные предприятия, желающие (а) продать свою
высокотехнологичную продукцию или (б) найти поставщиков для ее производства.

### Гипотетический портрет пользователя для службы Потребностей

1. Высококвалифицированный инженер среднего или высшего звена, который ищет поставщиков компонентов для разрабатываемого
   продукта.

1. Мужчина от 30 до 50 лет

1. С высшим образованием

1. С критическим складом ума, со склонностью сопоставлять цифры, технические характеристики и не ведется на броскую
   рекламу

1. Как правило женат, имеет детей (как маленьких, так и взрослых), обеспечен, имеет жилье и автомобиль

### Гипотетический портрет пользователя для службы Предложений

Можно выделить несколько сегментов пользователей

1. Высококвалифицированный и опытный сотрудник отдела продаж, знающий как продукт, так и технические детали

    1. Мужчина от 30 до 50 лет

    1. С высшим образованием, знает технологические нюансы продаваемой продукции и как эта продукция используется

    1. Знаком лично с большинством клиентов, большая часть всех продаж через личные контакты

1. Начинающий или низкоквалифицированный продажник

    1. Молодой мужчина до 30-35 лет

    1. Без высшего образование или без опыта работы в соответствующей отрасли

    1. Делает продажи посредством холодных звонков

    1. и т.д.

1. И т.д.

## Структура проекта

### Общие модули для фронтенда и бэкенда

1. [`ok-marketplace-mp-common`](ok-marketplace-mp-common) -- Базовые общие модули для фронтенда и бэкенда: валидация,
   тестирование

1. [`ok-marketplace-mp-transport-mp`](ok-marketplace-mp-transport-mp) -- Транспортные модели для API на базе Kotlin
   Multiplatform для коммуникации между бэкендом и фронтендом

### Модули фронтенда

1. [`ok-marketplace-fe-common`](ok-marketplace-fe-common) -- Общие классы для компонентов фронтенд-приложения. Прежде
   всего, внутренние модели

1. [`ok-marketplace-fe-mappers-mp`](ok-marketplace-fe-mappers-mp) -- Маперы для конвертации данных из транспортных
   моделей на базе Kotlin Multiplatform во внутренние модели и обратно

1. [`ok-marketplace-fe-app-kreact`](ok-marketplace-fe-app-kreact) -- Фронтенд-приложение на Kotlin-React

### Модули бэкенда

1. [`ok-marketplace-be-common`](ok-marketplace-be-common) -- Общие классы для компонентов бэкенд-приложения. Прежде
   всего, внутренние модели и интерфейсы

1. [`ok-marketplace-be-mappers-mp`](ok-marketplace-be-mappers-mp) -- Маперы для конвертации данных из транспортных
   моделей на базе Kotlin Multiplatform во внутренние модели и обратно

### Модули приложений (фреймворки)

1. [`ok-marketplace-be-app-spring`](ok-marketplace-be-app-spring) -- Бэкенд-приложение на базе фреймвока Spring Boot с
   использованием библиотеки [Spring-Fu](https://github.com/spring-projects-experimental/spring-fu)

1. [`ok-marketplace-be-app-ktor`](ok-marketplace-be-app-ktor) -- Бэкенд-приложение на базе фреймвока Kotlin KTOR

1. [`ok-marketplace-be-app-kotless`](ok-marketplace-be-app-kotless) -- Бэкенд-приложение на базе фреймвока Kotlin 
   Kotless с демонстрацией возможностей бессерверных вычислени в Amazon Web Services (AWS)



