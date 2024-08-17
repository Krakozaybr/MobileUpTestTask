package com.krakozaybr.screens.preview

import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.navigation.details_screen.CoinDetailsScreenComponent
import com.krakozaybr.navigation.details_screen.State
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PreviewDetailsScreenComponent : CoinDetailsScreenComponent {
    override val model: StateFlow<CoinDetailsScreenComponent.Model>
        get() = MutableStateFlow(
            CoinDetailsScreenComponent.Model(
                title = "BitCoin",
                state = State.LoadFailed
            )
        )

    override fun onGoBack() {

    }

    override fun retryLoading() {

    }

    companion object {

        private val loadSuccess = State.LoadSuccess(
            coinDetails = CoinDetails(
                id = "btc",
                imageLink = "https://media.licdn.com/dms/image/v2/C510BAQGyuGalyYxfXQ/company-logo_200_200/company-logo_200_200/0/1631334696178?e=2147483647&v=beta&t=5TmFyg4zbmhC3J_ByYHr6aYCmFD8ZmNcpoRT8RNs2Kw",
                description = """
                            Bitcoin is a decentralized cryptocurrency originally described in a 2008 whitepaper by a person, or group of people, using the alias Satoshi Nakamoto. It was launched soon after, in January 2009.

                            Bitcoin is a peer-to-peer online currency, meaning that all transactions happen directly between equal, independent network participants, without the need for any intermediary to permit or facilitate them. Bitcoin was created, according to Nakamoto’s own words, to allow “online payments to be sent directly from one party to another without going through a financial institution.”
                            Bitcoin is a peer-to-peer online currency, meaning that all transactions happen directly between equal, independent network participants, without the need for any intermediary to permit or facilitate them. Bitcoin was created, according to Nakamoto’s own words, to allow “online payments to be sent directly from one party to another without going through a financial institution.”
                        """.trimIndent(),
                categories = persistentListOf(
                    "Smart Contract Platform", "Ethereum Ecosystems"
                )
            )
        )

    }
}