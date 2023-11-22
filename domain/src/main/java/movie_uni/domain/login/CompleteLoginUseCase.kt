package movie_uni.domain.login

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javax.inject.Inject
import javax.inject.Singleton
import movie_uni.domain.account.ClearAllUserDataUseCase
import movie_uni.domain.account.FetchAccountDetailsUseCase
import movie_uni.domain.library.SessionManager
import timber.log.Timber

@Singleton
class CompleteLoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val sessionManager: SessionManager,
    private val fetchAccountDetailsUseCase: FetchAccountDetailsUseCase,
    private val clearAllUserDataUseCase: ClearAllUserDataUseCase,
) {

    suspend fun execute(): Boolean {
        val requestToken = sessionManager.getRequestToken() ?: return false

        authenticationRepository.fetchSessionIdWithValidatedRequestToken(requestToken)
            .onFailure {
                clearAllUserDataUseCase.execute()
                Timber.d(it.message)

                return false
            }
            .onSuccess { sessionId ->
                clearAllUserDataUseCase.execute()
                sessionManager.insertSessionId(sessionId)

                fetchAccountDetailsUseCase.execute()
            }

        return true
    }
}