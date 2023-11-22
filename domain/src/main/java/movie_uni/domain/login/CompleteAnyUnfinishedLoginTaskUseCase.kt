package movie_uni.domain.login

import javax.inject.Inject
import javax.inject.Singleton
import movie_uni.domain.library.SessionManager

@Singleton
class CompleteAnyUnfinishedLoginTaskUseCase @Inject constructor(
    private val sessionManager: SessionManager,
    private val completeLoginUseCase: CompleteLoginUseCase,
) {

    suspend fun execute() {
        if (!sessionManager.isLoggedIn() && sessionManager.getRequestToken() != null) {
            completeLoginUseCase.execute()
        }
    }
}