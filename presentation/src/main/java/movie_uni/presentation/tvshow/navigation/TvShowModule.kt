package movie_uni.presentation.tvshow.navigation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import movie_uni.presentation.navigation.FeatureNavigationBuilder
import movie_uni.presentation.navigation.navigator.TvShowNavigator

@Module
@InstallIn(SingletonComponent::class)
interface TvShowModule {

    @Binds
    @IntoSet
    fun bindTvShowNavigationBuilder(builder: TvShowNavigationBuilder): FeatureNavigationBuilder

    @Binds
    fun bindTvShowNavigator(navigator: TvShowNavigatorImpl): TvShowNavigator
}