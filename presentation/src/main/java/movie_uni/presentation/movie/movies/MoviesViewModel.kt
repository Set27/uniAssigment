package movie_uni.presentation.movie.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import movie_uni.domain.movie.AddMovieToFavoriteUseCase
import movie_uni.domain.movie.GetMoviesNowPlayingPagingFlowUseCase
import movie_uni.domain.movie.GetPopularMoviesPagingFlowUseCase
import movie_uni.presentation.navigation.navigator.MovieNavigator

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val getMoviesNowPlayingPagingFlowUseCase: GetMoviesNowPlayingPagingFlowUseCase,
    private val getPopularMoviesPagingFlowUseCase: GetPopularMoviesPagingFlowUseCase,
    private val movieNavigator: MovieNavigator,
) : ViewModel() {

    val popularMovies = getPopularMoviesPagingFlowUseCase.execute()
        .cachedIn(viewModelScope)

    val moviesNowPlaying = getMoviesNowPlayingPagingFlowUseCase.execute()
        .cachedIn(viewModelScope)

    fun addMovieToFavorites(isFavorite: Boolean, movieId: Int) {
        viewModelScope.launch {
            addMovieToFavoriteUseCase.execute(
                isFavorite = isFavorite,
                movieId = movieId
            )
        }
    }

    fun navigateToMovieDetails(movieId: Int) {
        movieNavigator.navigateToDetails(movieId)
    }
}