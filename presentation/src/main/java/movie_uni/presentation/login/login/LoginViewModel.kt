package movie_uni.presentation.login.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import movie_uni.domain.library.SessionManager
import movie_uni.domain.login.CollectLoginEventsUseCase
import movie_uni.domain.login.CompleteAnyUnfinishedLoginTaskUseCase
import movie_uni.domain.login.CompleteLoginUseCase
import movie_uni.domain.login.GetRequestTokenUseCase
import movie_uni.presentation.login.navigation.KEY_IS_REDIRECTED_FROM_TMDB_LOGIN
import movie_uni.presentation.navigation.navigator.LoginNavigator


const val APP_DEEP_LINK = "mutkuensert.movie_uni://app/"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getRequestTokenUseCase: GetRequestTokenUseCase,
    private val sessionManager: SessionManager,
    private val completeLoginUseCase: CompleteLoginUseCase,
    private val completeAnyUnfinishedLoginTaskUseCase: CompleteAnyUnfinishedLoginTaskUseCase,
    private val collectLoginEventsUseCase: CollectLoginEventsUseCase,
    private val loginNavigator: LoginNavigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var redirectedFromTmdbLogin: String? =
        savedStateHandle[KEY_IS_REDIRECTED_FROM_TMDB_LOGIN]

    val loginEvent = collectLoginEventsUseCase.execute()

    private val _requestToken = MutableStateFlow<String?>(null)
    val requestToken = _requestToken.asStateFlow()

    init {
        if(sessionManager.isLoggedIn()) loginNavigator.navigateToProfileScreen()

        if (redirectedFromTmdbLogin != null && !sessionManager.isLoggedIn()) {
            viewModelScope.launch {
                if (completeLoginUseCase.execute()) loginNavigator.navigateToProfileScreen()
            }
        } else {
            viewModelScope.launch { completeAnyUnfinishedLoginTaskUseCase.execute() }
        }
    }

    fun login() {
        viewModelScope.launch {
            _requestToken.value = getRequestTokenUseCase.execute()
        }
    }
}
