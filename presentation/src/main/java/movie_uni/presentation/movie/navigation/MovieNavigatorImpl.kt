package movie_uni.presentation.movie.navigation

import movie_uni.presentation.navigation.NavigationDispatcher
import movie_uni.presentation.navigation.NavigationType
import javax.inject.Inject
import movie_uni.presentation.navigation.navigator.MovieNavigator

class MovieNavigatorImpl @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher
) : MovieNavigator {
    override fun navigateToDetails(movieId: Int) {
        navigationDispatcher.navigate(
            NavigationType.ToRoute(
                route = ROUTE_MOVIE_DETAILS,
                args = listOf(KEY_MOVIE_ID to movieId)
            )
        )
    }
}