package movie_uni.presentation.login.navigation

import javax.inject.Inject
import movie_uni.presentation.navigation.NavigationDispatcher
import movie_uni.presentation.navigation.NavigationType
import movie_uni.presentation.navigation.navigator.LoginNavigator

class LoginNavigatorImpl @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher
) : LoginNavigator {
    override fun navigateToProfileScreen() {
        navigationDispatcher.navigate(
            NavigationType.ToRoute(
                route = ROUTE_PROFILE
            )
        )
    }
}