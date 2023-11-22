package movie_uni.data.tvshow.remote.mapper

import javax.inject.Inject
import movie_uni.data.account.local.AccountDao
import movie_uni.data.tvshow.local.model.PopularTvShowEntity
import movie_uni.data.tvshow.remote.model.PopularTvShowDto
import movie_uni.domain.library.SessionManager

class PopularTvShowDtoMapper @Inject constructor(
    private val accountDao: AccountDao,
    private val sessionManager: SessionManager,
) {
    suspend fun mapToEntity(
        popularTvShowDto: PopularTvShowDto,
        page: Int
    ): PopularTvShowEntity {
        return PopularTvShowEntity(
            id = popularTvShowDto.id,
            page = page,
            posterPath = popularTvShowDto.posterPath,
            title = popularTvShowDto.name,
            voteAverage = popularTvShowDto.voteAverage,
            isFavorite = isFavoriteTvShow(popularTvShowDto.id)
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