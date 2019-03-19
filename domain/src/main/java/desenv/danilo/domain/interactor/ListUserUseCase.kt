package desenv.danilo.domain.interactor

import desenv.danilo.data.UserRepository
import desenv.danilo.domain.SingleUseCase
import desenv.danilo.domain.convertbind.UserConvert
import desenv.danilo.domain.executor.BaseSchedulerProvider
import desenv.danilo.modelbind.UserBind
import io.reactivex.Single
import javax.inject.Inject

open class ListUserUseCase @Inject constructor (
    private val repository: UserRepository,
    schedulerProvider: BaseSchedulerProvider
): SingleUseCase<List<UserBind>, Unit>(schedulerProvider) {
    override fun buildSingleUseCase(params: Unit?): Single<List<UserBind>> {
        return repository.getAllUser()
            .map {
                it.map { user ->  UserConvert.fromData(user) }
            }
    }


}