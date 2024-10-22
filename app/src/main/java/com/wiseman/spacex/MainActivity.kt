package com.wiseman.spacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mindera.rocketscience.core_ui.ui.navigation.Route
import com.mindera.rocketscience.core_ui.ui.ui.theme.MyApplicationTheme
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import com.mindera.rocketscience.spacex_presentation.screen.details.DetailsScreen
import com.mindera.rocketscience.spacex_presentation.screen.home.SpaceXHomeScreen
import com.wiseman.spacex.ui.theme.SpaceXTheme

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navHostController = rememberNavController()
                NavHost(
                    navController = navHostController,
                    startDestination = Route.Home
                ) {
                    composable<Route.Home> {
                        SpaceXHomeScreen(
                            onItemClicked = { spaceXLaunchItem: SpaceXLaunchItem ->
                                navHostController.navigate(
                                    Route.Details(
                                        wikipediaLink = spaceXLaunchItem.wikipediaLink,
                                        articleLink = spaceXLaunchItem.articleLink
                                    )
                                )
                            })
                    }
                    composable<Route.Details> { navBackStackEntry: NavBackStackEntry ->
                        val args = navBackStackEntry.toRoute<Route.Details>()
                        val link = args.articleLink ?: args.wikipediaLink
                        DetailsScreen(link) {
                            navHostController.navigate(Route.Home) {
                                popUpTo(Route.Home) { inclusive = true }
                            }
                        }
                    }
                }
            }
        }
    }
}
