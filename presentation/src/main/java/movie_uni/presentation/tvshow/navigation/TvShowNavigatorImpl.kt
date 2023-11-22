package movie_uni.presentation.tvshow.navigation

import javax.inject.Inject
import movie_uni.presentation.navigation.NavigationDispatcher
import movie_uni.presentation.navigation.NavigationType
import movie_uni.presentation.navigation.navigator.TvShowNavigator

class TvShowNavigatorImpl @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher
) : TvShowNavigator {
    override fun navigateToDetails(tvShowId: Int) {
        navigationDispatcher.navigate(
            NavigationType.ToRoute(
                route = ROUTE_TV_SHOW_DETAILS, args = listOf(
                    KEY_TV_SHOW_ID to tvShowId
                )
            )
        )
    }
}