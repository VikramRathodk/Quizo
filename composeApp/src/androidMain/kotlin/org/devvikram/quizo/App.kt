package org.devvikram.quizo

import androidx.compose.runtime.*
import org.devvikram.quizo.navigations.AppNavigation
import org.devvikram.quizo.presentation.AppViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    appViewmodel: AppViewModel
) {
    AppTheme  {
       AppNavigation(
           appViewmodel = appViewmodel
       )
    }
}