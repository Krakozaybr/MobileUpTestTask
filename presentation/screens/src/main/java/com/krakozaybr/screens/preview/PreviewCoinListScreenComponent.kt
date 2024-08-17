package com.krakozaybr.screens.preview

import com.krakozaybr.domain.model.CoinInfo
import com.krakozaybr.domain.model.Currency
import com.krakozaybr.navigation.coin_list_screen.CoinListScreenComponent
import com.krakozaybr.navigation.coin_list_screen.State
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PreviewCoinListScreenComponent : CoinListScreenComponent {
    override val model: StateFlow<State>
        get() = MutableStateFlow(loadSuccess)

    override fun onSelectCurrency(currency: Currency) {
    }

    override fun onShowDetails(coinInfo: CoinInfo) {
    }

    override fun reloadAll() {
    }

    companion object {

        private val loadSuccess = State(
            selectedCurrency = Currency("RUB"),
            currencyState = State.CurrencyState.LoadSuccess(
                persistentListOf(
                    Currency("RUB"),
                    Currency("USD"),
                )
            ),
            coinState = State.CoinState.LoadSuccess(
                List(10) {
                    CoinInfo(
                        id = "id$it",
                        priceChangePercentage = Math.pow(-1.0, it.toDouble()).toFloat() * it * 2,
                        name = "$it name",
                        imageLink = "https://media.licdn.com/dms/image/v2/C510BAQGyuGalyYxfXQ/company-logo_200_200/company-logo_200_200/0/1631334696178?e=2147483647&v=beta&t=5TmFyg4zbmhC3J_ByYHr6aYCmFD8ZmNcpoRT8RNs2Kw",
                        price = it * 1000f,
                        symbol = "${it}SB"
                    )
                }.toImmutableList()
            )
        )

    }
}