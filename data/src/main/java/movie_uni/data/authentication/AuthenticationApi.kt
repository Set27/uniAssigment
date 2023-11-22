package movie_uni.data.authentication

import movie_uni.data.authentication.model.DeleteSessionResponse
import movie_uni.data.authentication.model.RequestTokenResponse
import movie_uni.data.authentication.model.SessionIdDto
import movie_uni.data.authentication.model.SessionResponse
import movie_uni.data.authentication.model.ValidRequestTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface AuthenticationApi {

    @GET("authentication/token/new")
    suspend fun getRequestToken(): Response<RequestTokenResponse>

    @POST("authentication/session/new")
    suspend fun getSessionWithValidatedRequestToken(
        @Body validRequestTokenResponse: ValidRequestTokenResponse
    ): Response<SessionResponse>

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(@Body sessionIdDto: SessionIdDto): Response<DeleteSessionResponse>
}