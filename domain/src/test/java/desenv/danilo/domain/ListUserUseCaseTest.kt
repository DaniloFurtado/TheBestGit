package desenv.danilo.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import desenv.danilo.data.UserRepository
import desenv.danilo.domain.data.DataFactoryUser
import desenv.danilo.domain.executor.BaseSchedulerProvider
import desenv.danilo.domain.interactor.ListUserUseCase
import io.reactivex.Single
import org.junit.Test
import java.util.concurrent.TimeoutException
import kotlin.random.Random
import kotlin.test.assertEquals

class ListUserUseCaseTest {

    private val schedulerTest = mock<BaseSchedulerProvider>()
    private val listUsersUse = DataFactoryUser.dummyListUser()
    private val userRepository = mock<UserRepository>{
        on { getAllUser() } doReturn Single.just(listUsersUse)
    }
    private val listUserUseCase = ListUserUseCase(userRepository, schedulerTest)

    @Test
    fun testCallRightMethod(){
        listUserUseCase.buildSingleUseCase(null)
        verify(userRepository, times(1))
            .getAllUser()
    }


    @Test
    fun testListAllUsers(){
        val testObserver = listUserUseCase.buildSingleUseCase(null).test()
        testObserver.assertNoErrors()
        testObserver.assertTerminated()
        testObserver.assertValue {
            listUsersUse.size == it.size
        }
        testObserver.dispose()
    }

    @Test
    fun testIllegalArgumentParameter(){

        val userRepository2 = mock<UserRepository>{
            on { getAllUser() } doReturn Single.error(IllegalArgumentException("Invalid Parameter"))
        }
        val listUserUseCase2 = ListUserUseCase(userRepository2, schedulerTest)

        val testObserver = listUserUseCase2.buildSingleUseCase(null).test()

        testObserver.assertError(IllegalArgumentException::class.java)
        testObserver.assertErrorMessage("Invalid Parameter")
        testObserver.dispose()
    }
    @Test
    fun testListUserHasConvertedRight(){
        val testObserver = listUserUseCase.buildSingleUseCase(null).test()
        val index = Random.nextInt(0, (listUsersUse.size -1))
        testObserver.assertNoErrors()
        testObserver.assertTerminated()
        val userBind = testObserver.values()[0][index]
        val user = listUsersUse[index]
        assertEquals(user.avatarUrl, userBind.avatarUrl)
        assertEquals(user.blog, userBind.blog)
        assertEquals(user.company, userBind.company)
        assertEquals(user.createdAt, userBind.createdAt)
        assertEquals(user.email, userBind.email)
        assertEquals(user.type, userBind.type)
        assertEquals(user.id, userBind.id)
        assertEquals(user.location, userBind.location)
        assertEquals(user.name, userBind.name)
        assertEquals(user.login, userBind.login)

        testObserver.dispose()
    }

    @Test
    fun testCatcheExcpetionFromRepository(){
        val userRepositoryIntern = mock<UserRepository>{
            on { getAllUser() } doReturn Single.error(Exception("Exception"))
        }
        val listUserUseCaseInternt = ListUserUseCase(userRepositoryIntern, schedulerTest)
        val testObserver = listUserUseCaseInternt.buildSingleUseCase(null).test()
        testObserver.assertError(Exception::class.java)
        testObserver.assertErrorMessage("Exception")
        testObserver.assertTerminated()
        testObserver.dispose()

    }


    @Test
    fun testCatcheTimeOutExcpetionFromRepository(){
        val userRepositoryIntern = mock<UserRepository>{
            on { getAllUser() } doReturn Single.error(TimeoutException("Time Out Exception"))
        }
        val listUserUseCaseIntern = ListUserUseCase(userRepositoryIntern, schedulerTest)
        val testObserver = listUserUseCaseIntern.buildSingleUseCase(null).test()
        testObserver.assertError(TimeoutException::class.java)
        testObserver.assertErrorMessage("Time Out Exception")
        testObserver.assertTerminated()
        testObserver.dispose()

    }
}