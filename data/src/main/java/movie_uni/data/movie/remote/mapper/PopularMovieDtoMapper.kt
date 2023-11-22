package movie_uni.data.movie.remote.mapper

import javax.inject.Inject
import movie_uni.data.account.local.AccountDao
import movie_uni.data.movie.local.model.PopularMovieEntity
import movie_uni.data.movie.remote.model.PopularMovieDto
import movie_uni.domain.library.SessionManager

class PopularMovieDtoMapper @Inject constructor(
    private val accountDao: AccountDao,
    private val sessionManager: SessionManager,
) {
    suspend fun mapToEntity(
        popularMovieDto: PopularMovieDto,
        page: Int
    ): PopularMovieEntity {
        return PopularMovieEntity(
            id = popularMovieDto.id,
            page = page,
            posterPath = popularMovieDto.posterPath,
            title = popularMovieDto.title,
            voteAverage = popularMovieDto.voteAverage,
            isFavorite = isMovieFavorite(popularMovieDto.id)
        )
    }

    private suspend fun isMovieFavorite(id: Int): Boolean? {
        return if (sessionManager.isLoggedIn()) {
            accountDao.isMovieFavorite(id)
        } else {
            null
        }
    }
}