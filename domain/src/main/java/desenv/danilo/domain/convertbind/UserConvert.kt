package desenv.danilo.domain.convertbind

import desenv.danilo.data.data.User
import desenv.danilo.modelbind.UserBind

object UserConvert {

    fun fromData(user: User): UserBind {
        return UserBind(
            user.id,
            user.login,
            user.avatarUrl,
            user.name,
            user.blog,
            user.location,
            user.email,
            user.repos,
            user.followers,
            user.following,
            user.createdAt,
            user.updatedAt,
            user.type,
            user.company)
    }

    fun toData(userBind: UserBind): User{
        return User(
            userBind.id,
            userBind.login,
            userBind.avatarUrl,
            userBind.name,
            userBind.blog,
            userBind.location,
            userBind.email,
            userBind.repos,
            userBind.followers,
            userBind.following,
            userBind.createdAt,
            userBind.updatedAt,
            userBind.type,
            userBind.company)
    }
}