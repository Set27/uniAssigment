package movie_uni.presentation.person.navigation

import javax.inject.Inject
import movie_uni.presentation.navigation.NavigationDispatcher
import movie_uni.presentation.navigation.NavigationType
import movie_uni.presentation.navigation.navigator.PersonNavigator

class PersonNavigatorImpl @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher
) : PersonNavigator {
    override fun navigateToPerson(personId: Int) {
        navigationDispatcher.navigate(
            NavigationType.ToRoute(
                route = ROUTE_PERSON,
                args = listOf(
                    KEY_PERSON_ID to personId
                )
            )
        )
    }
}