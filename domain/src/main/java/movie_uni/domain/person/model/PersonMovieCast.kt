package movie_uni.domain.person.model

data class PersonMovieCast(
    val character: String,
    val title: String,
    val id: Int,
    val imageUrl: String?
)
