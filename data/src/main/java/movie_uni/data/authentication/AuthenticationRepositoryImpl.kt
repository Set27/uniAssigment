package movie_uni.data.authentication

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javax.inject.Inject
import movie_uni.data.authentication.model.SessionIdDto
import movie_uni.data.authentication.model.ValidRequestTokenResponse
import movie_uni.data.network.toResult
import movie_uni.domain.Failure
import movie_uni.domain.library.SessionManager
import movie_uni.domain.login.AuthenticationRepository
import timber.log.Timber

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val sessionManager: SessionManager,
) : AuthenticationRepository {

    override suspend fun fetchRequestToken(): Result<String, Failure> {
        authenticationApi.getRequestToken()
            .toResult()
            .onSuccess {
                return if (!it.success) {
                    Err(Failure(message = "Unsuccessful request token"))
                } else {
                    Ok(it.requestToken)
                }
            }

        return Err(Failure(message = ""))
    }

    override suspend fun fetchSessionIdWithValidatedRequestToken(requestToken: String): Result<String, Failure> {
        authenticationApi.getSessionWithValidatedRequestToken(
            validRequestTokenResponse = ValidRequestTokenResponse(
                validRequestToken = requestToken
            )
        ).toResult()
            .onSuccess {
                return if (!it.success) {
                    Err(Failure(message = "Unsuccessful request token validation."))
                } else {
                    Ok(it.sessionId)
                }
            }

        return Err(Failure(message = ""))
    }

    override suspend fun logout(): Boolean {
        val sessionId = sessionManager.getSessionId()

        if (sessionId != null) {
            authenticationApi.deleteSession(SessionIdDto(sessionId))
                .toResult()
                .onFailure {
                    if (it.statusCode == AuthenticationError.RESOURCE_NOT_FOUND.statusCode) {
                        Timber.e("Delete Session Request Error! Message: ${it.message}")

                        return true
                    }

                    return false
                }
        }

        return true
    }
}