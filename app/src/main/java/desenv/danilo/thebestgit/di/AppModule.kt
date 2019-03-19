package desenv.danilo.thebestgit.di

import dagger.Module
import dagger.Provides
import desenv.danilo.presentation.UserVmFactory
import desenv.danilo.thebestgit.adapter.UserLisAdatper
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesVmUserFactory() = UserVmFactory()

    @Provides
    fun providesAdapterUserList() = UserLisAdatper(listOf())
}