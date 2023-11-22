package movie_uni.presentation.tvshow.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import movie_uni.domain.tvshow.AddTvShowToFavoriteUseCase
import movie_uni.domain.tvshow.GetPopularTvShowsPagingFlowUseCase
import movie_uni.domain.tvshow.GetTopRatedTvShowsPagingFlowUseCase
import movie_uni.presentation.navigation.navigator.TvShowNavigator

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val getPopularTvShowsPagingFlowUseCase: GetPopularTvShowsPagingFlowUseCase,
    private val getTopRatedTvShowsPagingFlowUseCase: GetTopRatedTvShowsPagingFlowUseCase,
    private val tvShowNavigator: TvShowNavigator,
    private val addTvShowToFavoriteUseCase: AddTvShowToFavoriteUseCase,
) : ViewModel() {
    val popularTvShows = getPopularTvShowsPagingFlowUseCase.execute()
        .cachedIn(viewModelScope)

    val topRatedTvShows = getTopRatedTvShowsPagingFlowUseCase.execute()
        .cachedIn(viewModelScope)

    fun navigateToTvShowDetails(tvShowId: Int) {
        tvShowNavigator.navigateToDetails(tvShowId)
    }

    fun addTvShowToFavorites(isFavorite: Boolean, tvShowId: Int) {
        viewModelScope.launch {
            addTvShowToFavoriteUseCase.execute(
                isFavorite = isFavorite,
                tvShowId = tvShowId
            )
        }
    }
}