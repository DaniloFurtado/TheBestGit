package desenv.danilo.presentation.data

import desenv.danilo.modelbind.UserBind

object DataFactoryUser {
    fun dummyListUser() =
        listOf(
            UserBind("1", "danilotest_1", "http:sdafdasfdsads"),
            UserBind("2", "danilotest_2", "http:sdafdasfdsads"),
            UserBind("3", "danilotest_3", "http:sdafdasfdsads"),
            UserBind("4", "danilotest_4", "http:sdafdasfdsads"),
            UserBind("5", "danilotest_5", "http:sdafdasfdsads"),
            UserBind("6", "danilotest_6", "http:sdafdasfdsads")
        )

    fun dummyUser() = UserBind("1", "danilotest_1", "http:sdafdasfdsads")
}