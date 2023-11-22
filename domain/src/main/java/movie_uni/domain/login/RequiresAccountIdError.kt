package movie_uni.domain.login

class RequiresAccountIdError(override val message: String?) : Throwable(message)