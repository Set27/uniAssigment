package movie_uni.presentation.main

import movie_uni.presentation.login.navigation.GRAPH_LOGIN
import movie_uni.presentation.movie.navigation.GRAPH_MOVIE
import movie_uni.presentation.multisearch.navigation.ROUTE_MULTI_SEARCH
import movie_uni.presentation.tvshow.navigation.GRAPH_TV_SHOW

sealed class TabItem(val route: String, val titleKey: String) {
    object Movie : TabItem(route = GRAPH_MOVIE, titleKey = "Movie")
    object TvShow : TabItem(route = GRAPH_TV_SHOW, titleKey = "Tv Show")
    object MultiSearch :
        TabItem(route = ROUTE_MULTI_SEARCH, titleKey = "Multi Search")

    object Login : TabItem(route = GRAPH_LOGIN, titleKey = "Login")

    companion object {
        fun all(): List<TabItem> {
            return listOf(Movie, TvShow, MultiSearch, Login)
        }
    }
}