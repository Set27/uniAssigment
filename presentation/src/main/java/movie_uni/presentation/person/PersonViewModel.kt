package movie_uni.presentation.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutkuensert.androidphase.Phase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import movie_uni.domain.person.GetPersonDetailsUseCase
import movie_uni.domain.person.GetPersonMovieCastingUseCase
import movie_uni.domain.person.GetPersonTvCastingUseCase
import movie_uni.domain.person.model.PersonDetails
import movie_uni.domain.person.model.PersonMovieCast
import movie_uni.domain.person.model.PersonTvCast
import movie_uni.presentation.navigation.navigator.MovieNavigator
import movie_uni.presentation.navigation.navigator.TvShowNavigator
import movie_uni.presentation.person.navigation.KEY_PERSON_ID

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val movieNavigator: MovieNavigator,
    private val tvShowNavigator: TvShowNavigator,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val getPersonMovieCastingUseCase: GetPersonMovieCastingUseCase,
    private val getPersonTvCastingUseCase: GetPersonTvCastingUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val personId: Int = requireNotNull(savedStateHandle[KEY_PERSON_ID]) {
        "Provide $KEY_PERSON_ID before navigating."
    }
    private val _details: MutableStateFlow<Phase<PersonDetails>> =
        MutableStateFlow(Phase.Standby())
    val details = _details.asStateFlow()

    private val _movieCasting: MutableStateFlow<Phase<List<PersonMovieCast>>> =
        MutableStateFlow(Phase.Standby())
    val movieCasting = _movieCasting.asStateFlow()

    private val _tvCasting: MutableStateFlow<Phase<List<PersonTvCast>>> =
        MutableStateFlow(Phase.Standby(null))
    val tvCasting = _tvCasting.asStateFlow()

    fun getPersonDetails() {
        viewModelScope.launch {
            getPersonDetailsUseCase.execute(personId).collectLatest {
                _details.value = it
            }
        }
    }

    fun getCasting() {
        viewModelScope.launch {
            getMovieCasting(personId = personId)
            getTvCasting(personId = personId)
        }
    }

    private suspend fun getMovieCasting(personId: Int) {
        viewModelScope.launch {
            getPersonMovieCastingUseCase.execute(personId).collect {
                _movieCasting.value = it
            }
        }
    }

    private suspend fun getTvCasting(personId: Int) {
        viewModelScope.launch {
            getPersonTvCastingUseCase.execute(personId).collect {
                _tvCasting.value = it
            }
        }
    }

    fun navigateToMovieDetails(movieId: Int) {
        movieNavigator.navigateToDetails(movieId)
    }

    fun navigateToTvShowDetails(tvShowId: Int) {
        tvShowNavigator.navigateToDetails(tvShowId)
    }
}