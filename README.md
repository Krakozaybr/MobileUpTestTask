# Тестовое задание для стажировки "Нашкодим - Android"
![Demo](demo.mp4)
Приложение получает список криптовалют и список валют-измерений (chips) из [CoinGecko API](https://docs.coingecko.com/v3.0.1/reference/endpoint-overview)
и отображает их.
## Особенности
* Список валют-измерений подгружается с сервера (узнал, что это не нужно, когда уже сделал)
* Все данные, получаемые с API кэшируются<br>
Списки криптовалют и детальная информация удаляются по прошествие 1 минуты с последнего использования,
а список валют-измерений остаётся на всю сессию (т.к. они очень редко изменяются)
* Pull to refresh
* API ключ не используется (хотя добавить - не проблема), так что количество запросов в минуту довольно ограничено
* Presentation navigation and logic имеет тесты
## Декомпозиция задачи
Изначально накидал [такой план](decomposition.md). Он частично оправдал себя:
* Presentation ui components оправдали ожидания
* Domain +- тоже, однако иногда требовались изменения в нём
* Data layer занял немного больше времени
* Presentation navigation and logic был сделан по-другому, т.к. я не учел,
что два подгружаемых списка сильно связаны друг с другом и разбивка их на компоненты не стоит того
## Дополнительные библиотеки
* [Decompose](https://github.com/arkivanov/Decompose/) + [MVIKotlin](https://github.com/arkivanov/mvikotlin)
* [Koin](https://insert-koin.io/)
* [Kotlin Immutable collections](https://github.com/Kotlin/kotlinx.collections.immutable)
* [Html-text](https://github.com/ch4rl3x/HtmlText)
* [Immutable collections](https://github.com/Kotlin/kotlinx.collections.immutable)
* [Coil](https://github.com/coil-kt/coil)
* Для тестов используется JUnit5
