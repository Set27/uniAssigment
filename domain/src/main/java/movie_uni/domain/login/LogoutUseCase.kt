package movie_uni.domain.login

import javax.inject.Inject
import javax.inject.Singleton
import movie_uni.domain.account.ClearAllUserDataUseCase

@Singleton
class LogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val clearAllUserDataUseCase: ClearAllUserDataUseCase,
) {

    suspend fun execute(): Boolean {
        val isLoggedOut = authenticationRepository.logout()

        if (isLoggedOut) {
            clearAllUserDataUseCase.execute()

            return true
        }

        return false
    }
}