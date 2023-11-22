package movie_uni.presentation.login.navigation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import movie_uni.presentation.navigation.FeatureNavigationBuilder
import movie_uni.presentation.navigation.navigator.LoginNavigator

@Module
@InstallIn(SingletonComponent::class)
interface LoginModule {

    @Binds
    @IntoSet
    fun bindLoginNavigationBuilder(builder: LoginNavigationBuilder): FeatureNavigationBuilder

    @Binds
    fun bindLoginNavigator(loginNavigatorImpl: LoginNavigatorImpl): LoginNavigator
}