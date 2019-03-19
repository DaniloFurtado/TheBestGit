package desenv.danilo.domain.interactor

import desenv.danilo.data.UserRepository
import desenv.danilo.domain.SingleUseCase
import desenv.danilo.domain.convertbind.UserConvert
import desenv.danilo.domain.executor.BaseSchedulerProvider
import desenv.danilo.modelbind.UserBind
import io.reactivex.Single

import javax.inject.Inject

open class ViewUserUseCase @Inject constructor(
    private val repository: UserRepository,
    schedulerProvider: BaseSchedulerProvider
): SingleUseCase<UserBind, String>(schedulerProvider) {
    override fun buildSingleUseCase(params: String?): Single<UserBind> {
        return if (params != null && params.isNotEmpty())
            repository.getUser(params)
                .map { user ->
                    UserConvert.fromData(user)
                }

        else
            Single.error(IllegalArgumentException("Paramter must be not null"))
    }


}