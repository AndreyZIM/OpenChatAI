# OpenChatAI

Приложение чат с искусственным интеллектом.
Используемое API: https://rapidapi.com/openai-api-openai-api-default/api/openai80

Стек: Jetpack Compose, Kotlin Flows, MVVM, Room, Retrofit, Clean Archtecture.

Пример запросов:
![ezgif-3-794bba65be](https://user-images.githubusercontent.com/82813533/233616875-0be191c8-e56c-4922-aed4-17bba711bdc3.gif)

![Снимок экрана 2023-04-21 134549](https://user-images.githubusercontent.com/82813533/233617464-3101ce58-0db6-4886-b26a-8cce9e1868f2.jpg)
Присутствуют два App модуля. Один для Compose, другой для XML. Пока реализован только модуль на Compose.

![Снимок экрана 2023-04-21 134602](https://user-images.githubusercontent.com/82813533/233617627-3ec18395-be90-488d-beba-cd2c0fe4576c.jpg)
Так есть два других слоя для безнес логики и получения и сохранения данных.

### В планах:
- Сделать обработку ошибок.
- Реализовать app модуль на XML.
- Реализовать проверку на интернет.
- Реализовать экран с настройками, где можно будет вставить свой собственным **API_KEY**.
