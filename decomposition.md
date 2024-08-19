## Декомпозиция задачи:
Глобально задачу можно разделить на работу по нескольким направлениям:
- Domain layer (25m)
- Data layer (120m)
- Presentation layer:
    - Navigation and logic (225m)
    - Screens (100m) and components (230m)

### Domain
Начнём с domain слоя, т.к. от него зависят все остальные:
- Models (5m):
    - CoinInfo
    - CoinDetails
    - Currency
- Repository (5m):
    - CoinRepository
    - CurrencyRepository
- UseCases (5m):
    - GetCoinListUseCase
    - GetCurrencyListUseCase
    - GetCoinInfoUseCase

Также добавим вспомогательные классы для обработки ошибок (10m):
- Resource
- FailureReason

Тестировать тут нечего (UseCase-ы тривиальные), так что идем дальше к, пожалуй, Presentation navigation

### Presentation navigation and logic
- CurrencyListComponent (75m)
- CoinListComponent (75m)
- CoinDetailsComponent (75m)

Каждый компонент включает в себя интерфейс компонента, его реализацию и Store.
Также для них нужны тесты. Если не знакомы с decompose + mvikotlin, то я
постараюсь с помощью комментариев объяснить большинство моментов.

### Presentation ui components
На первый взгляд напрашиваются такие компоненты:
- Toolbar для списка криптовалют (10m)
- Toolbar для дополнительной информации (10m)
    - ToolbarTitle (10m)
    - ToolbarBackButton (15m)
- CurrencyList (15m + 10m)
    - CurrencyChip (10m)
- ListLoader (10m)
- ErrorDisplay (10m + 30m)
    - ErrorDisplayLogo (10m)
    - ErrorDisplayText (10m)
    - ErrorDisplayButton (10m)
- CoinList (10m + 70m)
    - CoinItem (10m + 60m)
        - CoinItemImage (20m)
        - CoinItemTitle (10m)
        - CoinItemPrice (10m)
        - CoinItemPriceChange (10m)
        - CoinItemNotation - BTC, BNB, подпись снизу (10m)
- CoinDetailsImage (10m)
- CoinDetailsSubtitle - подзаголовки на экране 2 - Описание, Категории (10m)
- CoinDetailsText (10m)

Также нужно создать Compose configuration file
с перечислением моделей из Domain модуля, т.к. даже несмотря на то,
что они будут data class-ами с неизменяемыми полями, compose compiler
не будет об этом знать, т.к. они располагаются в другом модуле.<br>
[Подробнее об этом](https://developer.android.com/develop/ui/compose/performance/stability/fix#modules-solution)

### Data layer
- Repositories impls (20m на каждый)
- API (60 min)
    - /coins/markets - криптовалюты
    - /coins/<id> - детальная информация
    - /simple/supported_vs_currencies - валюты в тулбаре (RUB, USD...)
- DTOs and mappers (20 min)
