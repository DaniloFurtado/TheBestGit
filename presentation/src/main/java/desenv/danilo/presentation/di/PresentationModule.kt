package desenv.danilo.presentation.di

import dagger.Module
import dagger.Provides
import desenv.danilo.data.UserRepository
import desenv.danilo.dataimp.UserRepositoryImp
import desenv.danilo.dataimp.api.APIClient
import desenv.danilo.dataimp.api.ApiUserService
import desenv.danilo.domain.executor.BaseSchedulerProvider
import desenv.danilo.presentation.executor.SchedulerProvider
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
open class PresentationModule {

    @Singleton
    @Provides
    fun providesUserRepository( apiUserService: ApiUserService): UserRepository {
        return UserRepositoryImp(apiUserService)
    }

    @Singleton
    @Provides
    fun providesSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider()

    @Singleton
    @Provides
    fun providesRetrofit() = APIClient.getAPIService()


    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiUserService = retrofit.create(ApiUserService::class.java)

}