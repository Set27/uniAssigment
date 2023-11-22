package movie_uni.presentation.movie.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutkuensert.androidphase.Phase
import movie_uni.presentation.movie.navigation.KEY_MOVIE_ID
import movie_uni.presentation.navigation.navigator.PersonNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import movie_uni.domain.movie.GetMovieCastUseCase
import movie_uni.domain.movie.GetMovieDetailsUseCase
import movie_uni.domain.movie.model.MovieCast
import movie_uni.domain.movie.model.MovieDetails

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val personNavigator: PersonNavigator,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val movieId: Int = requireNotNull(savedStateHandle[KEY_MOVIE_ID]) {
        "Provide $KEY_MOVIE_ID before navigating."
    }
    private val _movieDetails: MutableStateFlow<Phase<MovieDetails>> =
        MutableStateFlow(Phase.Standby())
    val movieDetails = _movieDetails.asStateFlow()

    private val _movieCast: MutableStateFlow<Phase<List<MovieCast>>> =
        MutableStateFlow(Phase.Standby())
    val movieCast = _movieCast.asStateFlow()

    fun getMovieDetails() {
        viewModelScope.launch {
            getMovieDetailsUseCase.execute(movieId).collectLatest {
                _movieDetails.value = it
            }
        }
    }

    fun getMovieCast() {
        viewModelScope.launch {
            getMovieCastUseCase.execute(movieId).collectLatest {
                _movieCast.value = it
            }
        }
    }

    fun navigateToPerson(personId: Int) {
        personNavigator.navigateToPerson(personId)
    }
}