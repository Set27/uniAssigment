package movie_uni.data.authentication.model

import com.squareup.moshi.Json

data class SessionIdDto(
    @Json(name = "session_id") val sessionId: String
)
