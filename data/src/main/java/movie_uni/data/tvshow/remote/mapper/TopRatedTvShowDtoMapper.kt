package movie_uni.data.tvshow.remote.mapper

import javax.inject.Inject
import movie_uni.data.account.local.AccountDao
import movie_uni.data.tvshow.local.model.TopRatedTvShowEntity
import movie_uni.data.tvshow.remote.model.TopRatedTvShowDto
import movie_uni.domain.library.SessionManager

class TopRatedTvShowDtoMapper @Inject constructor(
    private val accountDao: AccountDao,
    private val sessionManager: SessionManager,
) {
    suspend fun mapToEntity(
        topRatedTvShowDto: TopRatedTvShowDto,
        page: Int
    ): TopRatedTvShowEntity {
        return TopRatedTvShowEntity(
            id = topRatedTvShowDto.id,
            page = page,
            posterPath = topRatedTvShowDto.posterPath,
            title = topRatedTvShowDto.name,
            voteAverage = topRatedTvShowDto.voteAverage,
            isFavorite = isFavoriteTvShow(topRatedTvShowDto.id)
        )
    }

    private suspend fun isFavoriteTvShow(id: Int): Boolean? {
        return if (sessionManager.isLoggedIn()) {
            accountDao.isTvShowFavorite(id)
        } else {
            null
        }
    }
}