package movie_uni.domain.account

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import movie_uni.domain.library.SessionManager
import movie_uni.domain.library.UserManager

@Singleton
class ClearAllUserDataUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val userManager: UserManager,
    private val sessionManager: SessionManager,
) {

    suspend fun execute() {
        withContext(Dispatchers.IO) {
            accountRepository.clearAllFavoriteMoviesAndTvShowsInCache()
            userManager.removeCurrentUser()
            sessionManager.removeRequestToken()
            sessionManager.removeSession()
        }
    }
}