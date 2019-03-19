package desenv.danilo.domain

import com.nhaarman.mockitokotlin2.*
import desenv.danilo.data.UserRepository
import desenv.danilo.domain.data.DataFactoryUser
import desenv.danilo.domain.executor.BaseSchedulerProvider
import desenv.danilo.domain.interactor.ViewUserUseCase
import io.reactivex.Single
import org.junit.Test
import java.util.concurrent.TimeoutException

class ViewUserUseCaseTest {


    private val schedulerTest = mock<BaseSchedulerProvider>()
    private val userUse = DataFactoryUser.dummyUser()
    private val userRepository = mock<UserRepository>{
        on { getUser(any()) } doReturn Single.just(userUse)
    }
    private val viewUserUseCase = ViewUserUseCase(userRepository, schedulerTest)

    @Test
    fun testCallRightMethod(){
        viewUserUseCase.buildSingleUseCase(eq("1"))
        verify(userRepository, times(1))
            .getUser(eq("1"))
    }

    @Test
    fun testGetUser(){
        val testObserver = viewUserUseCase.buildSingleUseCase(eq("1")).test()
        testObserver.assertNoErrors()
        testObserver.assertTerminated()
        testObserver.assertComplete()
        testObserver.dispose()
    }


    @Test
    fun testGetUserWithRightData(){
        val testObserver = viewUserUseCase.buildSingleUseCase(eq("1")).test()
        testObserver.assertNoErrors()
        testObserver.assertTerminated()
        testObserver.assertComplete()
        testObserver.assertValue {
            it.avatarUrl == userUse.avatarUrl
                    && it.id == userUse.id
                    && it.location == userUse.location
                    && it.blog == userUse.blog
                    && it.company == userUse.company
                    && it.email == userUse.email
                    && it.followers == userUse.followers
                    && it.following == userUse.following
                    && it.name == userUse.name
                    && it.updatedAt == userUse.updatedAt
                    && it.createdAt == userUse.createdAt
        }
        testObserver.dispose()
    }

    @Test
    fun testIllegalArgumentWithNull(){
        val testObserver = viewUserUseCase.buildSingleUseCase(null).test()
        testObserver.assertError(IllegalArgumentException::class.java)
        testObserver.assertErrorMessage("Paramter must be not null")
        testObserver.assertTerminated()
        testObserver.dispose()
    }

    @Test
    fun testIllegalArgumentWithEmpty(){
        val testObserver = viewUserUseCase.buildSingleUseCase("").test()
        testObserver.assertError(IllegalArgumentException::class.java)
        testObserver.assertErrorMessage("Paramter must be not null")
        testObserver.assertTerminated()
        testObserver.dispose()
    }

    @Test
    fun testCatcheExcpetionFromRepository(){
        val userRepositoryIntern = mock<UserRepository>{
            on { getUser(any()) } doReturn Single.error(Exception("Exception"))
        }
        val viewUserUseCaseIntern = ViewUserUseCase(userRepositoryIntern, schedulerTest)
        val testObserver = viewUserUseCaseIntern.buildSingleUseCase(eq("1")).test()
        testObserver.assertError(Exception::class.java)
        testObserver.assertErrorMessage("Exception")
        testObserver.assertTerminated()
        testObserver.dispose()

    }


    @Test
    fun testCatcheTimeOutExcpetionFromRepository(){
        val userRepositoryIntern = mock<UserRepository>{
            on { getUser(any()) } doReturn Single.error(TimeoutException("Time Out Exception"))
        }
        val viewUserUseCaseIntern = ViewUserUseCase(userRepositoryIntern, schedulerTest)
        val testObserver = viewUserUseCaseIntern.buildSingleUseCase(eq("1")).test()
        testObserver.assertError(TimeoutException::class.java)
        testObserver.assertErrorMessage("Time Out Exception")
        testObserver.assertTerminated()
        testObserver.dispose()

    }
}