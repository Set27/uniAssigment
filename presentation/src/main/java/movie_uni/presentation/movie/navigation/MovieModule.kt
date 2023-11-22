package movie_uni.presentation.movie.navigation

import movie_uni.presentation.navigation.FeatureNavigationBuilder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import movie_uni.presentation.navigation.navigator.MovieNavigator

@Module
@InstallIn(SingletonComponent::class)
interface MovieModule {

    @Binds
    @IntoSet
    fun bindMovieNavigationBuilder(builder: MovieNavigationBuilder): FeatureNavigationBuilder

    @Binds
    fun bindMovieNavigator(navigator: MovieNavigatorImpl): MovieNavigator
}