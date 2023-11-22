package movie_uni.presentation.person.navigation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import movie_uni.presentation.navigation.FeatureNavigationBuilder
import movie_uni.presentation.navigation.navigator.PersonNavigator

@Module
@InstallIn(SingletonComponent::class)
interface PersonModule {

    @Binds
    @IntoSet
    fun bindPersonNavigationBuilder(builder: PersonNavigationBuilder): FeatureNavigationBuilder

    @Binds
    fun bindPersonNavigator(navigator: PersonNavigatorImpl): PersonNavigator
}