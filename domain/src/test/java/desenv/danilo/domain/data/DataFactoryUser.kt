package desenv.danilo.domain.data

import desenv.danilo.data.data.User

object DataFactoryUser {
    fun dummyListUser() =
        listOf(
            User("1", "danilotest_1", "http:sdafdasfdsads_1", "sdfads_1", "fdaf090_1" , "dsfa08_1" , "afdsfds_1", "sdafds0809_1", "dfads_1", "sdfdafs_1", "fddsa_1"),
            User("2", "danilotest_2", "http:sdafdasfdsads_2", "sdfads_2", "fdaf090_2" , "dsfa08_2" , "afdsfds_2", "sdafds0809_2", "dfads_2", "sdfdafs_2", "fddsa_2"),
            User("3", "danilotest_3", "http:sdafdasfdsads", "sdfads", "fdaf090" , "dsfa08" , "afdsfds", "sdafds0809", "dfads", "sdfdafs", "fddsa"),
            User("4", "danilotest_4", "http:sdafdasfdsads", "sdfads", "fdaf090" , "dsfa08" , "afdsfds", "sdafds0809", "dfads", "sdfdafs", "fddsa"),
            User("5", "danilotest_5", "http:sdafdasfdsads_5", "sdfads_5", "fdaf090_5" , "dsfa08_5" , "afdsfds_5", "sdafds0809", "dfads", "sdfdafs", "fddsa"),
            User("6", "danilotest_6", "http:sdafdasfdsads", "sdfads", "fdaf090" , "dsfa08" , "afdsfds", "sdafds0809", "dfads", "sdfdafs", "fddsa")
        )

    fun dummyUser() = User("1",
        "danilotest_1",
        "http:sdafdasfdsads",
        "afdsf4",
        "afdgs65",
        "342dsfd",
        "3454dfd",
        "345dfgs",
        "234",
        "573",
        "fdsfads",
        "dafdsfa")
}