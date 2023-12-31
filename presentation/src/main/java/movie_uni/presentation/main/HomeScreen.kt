package movie_uni.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import movie_uni.presentation.movie.navigation.GRAPH_MOVIE
import movie_uni.presentation.navigation.NavigationBuilder
import movie_uni.presentation.theme.AppColors
import movie_uni.resources.R

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigationBuilder: NavigationBuilder
) {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<HomeViewModel>()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavBar(
                navController = navController,
                viewModel = viewModel
            )
        }
    ) { padding ->
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navController,
            startDestination = GRAPH_MOVIE,
            builder = navigationBuilder::buildNavGraph
        )
    }

    navigationBuilder.ObserveNavigation(navController = navController)
}

@Composable
private fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController, viewModel: HomeViewModel
) {
    val lastTabItemRoute by navController.lastTabItemRouteAsState()

    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
        contentColor = Color.DarkGray
    ) {
        BottomNavigationItem(
            selected = lastTabItemRoute == TabItem.Movie.route,
            unselectedContentColor = Color.Gray,
            selectedContentColor = AppColors.DarkCyan,
            onClick = viewModel::navigateToMovie,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_movie),
                    contentDescription = null
                )
            })

        BottomNavigationItem(
            selected = lastTabItemRoute == TabItem.TvShow.route,
            unselectedContentColor = Color.Gray,
            selectedContentColor = AppColors.DarkCyan,
            onClick = viewModel::navigateToTvShow,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_tvshow),
                    contentDescription = null
                )
            })

        BottomNavigationItem(
            selected = lastTabItemRoute == TabItem.MultiSearch.route,
            unselectedContentColor = Color.Gray,
            selectedContentColor = AppColors.DarkCyan,
            onClick = viewModel::navigateToMultiSearch,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null
                )
            })

        BottomNavigationItem(
            selected = lastTabItemRoute == TabItem.Login.route,
            unselectedContentColor = Color.Gray,
            selectedContentColor = AppColors.DarkCyan,
            onClick = viewModel::navigateToLogin,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_account),
                    contentDescription = null
                )
            })
    }
}

@Composable
private fun NavController.lastTabItemRouteAsState(): State<String?> {
    val destination = remember { mutableStateOf<String?>(null) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { navController, _, _ ->
            destination.value = navController.getLastNavigatedTabItem()?.route
        }

        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return destination
}

private fun NavController.getLastNavigatedTabItem(): TabItem? {
    val currentTabItem = TabItem.all().find {
        val destination = currentBackStackEntry?.destination

        it.route == (destination?.parent?.route ?: destination?.route)
    }

    if (currentTabItem != null) return currentTabItem

    val backIterator = currentBackStack.value.iterator()
    var lastTabItem: TabItem? = null

    while (backIterator.hasNext()) {
        val entry = backIterator.next()
        val tabItem = TabItem.all().find { it.route == entry.destination.route }

        if (tabItem != null) {
            lastTabItem = tabItem
        }
    }

    return lastTabItem
}