package movie_uni.presentation.main

import androidx.navigation.NavController
import movie_uni.presentation.movie.navigation.GRAPH_MOVIE
import movie_uni.presentation.navigation.NavigationDispatcher
import movie_uni.presentation.navigation.NavigationType
import javax.inject.Inject
import movie_uni.presentation.navigation.navigator.MainNavigator
import timber.log.Timber

class HomeNavigatorImpl @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher
) : MainNavigator {
    override var navController: NavController? = null

    override fun navigateToHome() {
        try {
            navController!!.navigate(GRAPH_MOVIE) {
                launchSingleTop = true
                popUpTo(GRAPH_MOVIE) {
                    inclusive = true
                }
            }
        } catch (e: NullPointerException) {
            Timber.e("navController was not set.")
        }
    }

    override fun navigateUp(popUpTo: String?, isInclusive: Boolean) {
        navigationDispatcher.navigate(NavigationType.Up(popUpTo))
    }

    override fun navigateBack() {
        navigationDispatcher.navigate(NavigationType.Back())
    }

    override fun navigateToTab(route: String) {
        navigationDispatcher.navigate(NavigationType.ToTab(route))
    }
}