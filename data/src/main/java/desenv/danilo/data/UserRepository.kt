package desenv.danilo.data

import desenv.danilo.data.data.User
import io.reactivex.Single

interface UserRepository {

    fun getAllUser(): Single<List<User>>

    fun getUser(id: String): Single<User>
}