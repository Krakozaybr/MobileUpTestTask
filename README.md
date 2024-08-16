# Тестовое задание для стажировки "Нашкодим - Android"
## Дополнительные библиотеки
* [Decompose](https://github.com/arkivanov/Decompose/) + [MVIKotlin](https://github.com/arkivanov/mvikotlin)
* [Koin](https://insert-koin.io/)
* [Kotlin Immutable collections](https://github.com/Kotlin/kotlinx.collections.immutable)
## Декомпозиция задачи:
Глобально задачу можно разделить на работу по нескольким направлениям:
- Domain layer
- Data layer
- Presentation layer:
  - Navigation and logic
  - Screens and components
- Testing

### Domain
Начнём с domain слоя, т.к. от него зависят все остальные:
- Models:
  - Coin<br>
    (*Можно было бы разбить на CoinInfo и CoinDetails, но оставим так*) 
  - Currency
- Repository:
  - CoinRepository
  - CurrencyRepository
- UseCases:
  - GetCoinListUseCase
  - GetCurrencyListUseCase
  - GetCoinInfoUseCase

Также добавим вспомогательные классы для обработки ошибок:
- Resource
- FailureReason

Тестировать тут нечего (UseCase-ы тривиальные), так что идем дальше к, пожалуй, Presentation navigation

### Presentation navigation and logic
- CurrencyListComponent
- CoinListComponent
- CoinDetailsComponent

Каждый компонент включает в себя интерфейс компонента, его реализацию и Store.
Также для них нужны тесты. Если не знакомы с decompose + mvikotlin, то я 
постараюсь с помощью комментариев объяснить большинство моментов.

### Presentation ui components
На первый взгляд напрашиваются такие компоненты:
- Toolbar для списка криптовалют
- Toolbar для дополнительной информации
  - ToolbarTitle
  - ToolbarBackButton
- CurrencyList
  - CurrencyChip
- ListLoader
- ErrorDisplay
  - ErrorDisplayLogo
  - ErrorDisplayText
  - ErrorDisplayButton
- CoinList
  - CoinItem
    - CoinItemImage
    - CoinItemTitle
    - CoinItemPrice
    - CoinItemPriceChange
    - CoinItemNotation - BTC, BNB, подпись снизу
- CoinDetailsImage
- CoinDetailsSubtitle - подзаголовки на экране 2 - Описание, Категории
- CoinDetailsText

Также нужно создать Compose configuration file
с перечислением моделей из Domain модуля, т.к. даже несмотря на то,
что они будут data class-ами с неизменяемыми полями, compose compiler
не будет об этом знать, т.к. они располагаются в другом модуле.<br>
[Подробнее об этом](https://developer.android.com/develop/ui/compose/performance/stability/fix#modules-solution)

### Data layer
- Repositories impls
- API
  - /coins/markets - криптовалюты
  - /simple/supported_vs_currencies - валюты в тулбаре (RUB, USD...)
- DTOs and mappers
