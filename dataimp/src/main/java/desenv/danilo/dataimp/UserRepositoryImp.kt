package desenv.danilo.dataimp

import desenv.danilo.data.UserRepository
import desenv.danilo.data.data.User
import desenv.danilo.dataimp.api.ApiUserService
import io.reactivex.Single

class UserRepositoryImp(
    private val apiUserService: ApiUserService) : UserRepository {

    override fun getAllUser(): Single<List<User>> {
        return apiUserService
            .getAllUser()
    }

    override fun getUser(id: String): Single<User> {
        return apiUserService.getUser(id)

    }


}