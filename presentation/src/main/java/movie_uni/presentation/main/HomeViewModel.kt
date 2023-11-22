package movie_uni.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import movie_uni.presentation.navigation.navigator.MainNavigator

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainNavigator: MainNavigator,
) : ViewModel() {
    fun navigateToMovie() {
        mainNavigator.navigateToTab(route = TabItem.Movie.route)
    }

    fun navigateToTvShow() {
        mainNavigator.navigateToTab(route = TabItem.TvShow.route)
    }

    fun navigateToMultiSearch() {
        mainNavigator.navigateToTab(route = TabItem.MultiSearch.route)
    }

    fun navigateToLogin() {
        mainNavigator.navigateToTab(route = TabItem.Login.route)
    }
}