package com.krakozaybr.components.toolbars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.krakozaybr.components.theme.AppTheme

@Composable
fun ToolbarText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = AppTheme.extraColors.toolbarTitle,
        style = AppTheme.extraTypography.toolbarTitle,
        modifier = modifier
    )
}

@Preview
@Composable
private fun ToolbarTextPreview() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ToolbarText(
                text = "Some toolbar",
                modifier = Modifier
            )
        }
    }
}
