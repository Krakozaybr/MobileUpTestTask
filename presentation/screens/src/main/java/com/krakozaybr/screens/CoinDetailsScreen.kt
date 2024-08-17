package com.krakozaybr.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.krakozaybr.components.details.CoinDetailsImage
import com.krakozaybr.components.details.CoinDetailsSubtitle
import com.krakozaybr.components.details.CoinDetailsText
import com.krakozaybr.components.error.ErrorBanner
import com.krakozaybr.components.loader.Loader
import com.krakozaybr.components.theme.AppTheme
import com.krakozaybr.components.toolbars.CoinDetailsToolbar
import com.krakozaybr.domain.model.CoinDetails
import com.krakozaybr.navigation.details_screen.CoinDetailsScreenComponent
import com.krakozaybr.navigation.details_screen.State
import com.krakozaybr.navigation.details_screen.State.LoadFailed
import com.krakozaybr.navigation.details_screen.State.LoadSuccess
import com.krakozaybr.navigation.details_screen.State.Loading
import com.krakozaybr.navigation.root.RootComponent

@Composable
fun CoinDetailsScreen(
    component: CoinDetailsScreenComponent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        val model by component.model.collectAsState()
        val title by remember {
            derivedStateOf {
                model.title
            }
        }

        CoinDetailsToolbar(
            title = title,
            onGoBackClick = component::onGoBack
        )

        when (val it = model.state) {
            Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Loader()
                }
            }
            LoadFailed -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorBanner(onRetryClick = component::retryLoading)
                }
            }
            is LoadSuccess -> {
                CoinDetailsContent(details = it.coinDetails)
            }
        }
    }
}

@Composable
private fun CoinDetailsContent(
    details: CoinDetails,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(AppTheme.sizes.screenPadding)
        ,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CoinDetailsImage(
                imageLink = details.imageLink,
                contentDescription = details.description,
                modifier = Modifier.fillMaxWidth(0.4f)
            )
        }
        CoinDetailsSubtitle(title = stringResource(id = R.string.description))
        CoinDetailsText(text = details.description)
        CoinDetailsSubtitle(title = stringResource(id = R.string.categories))
        CoinDetailsText(text = details.categories.joinToString(", "))
    }
}
