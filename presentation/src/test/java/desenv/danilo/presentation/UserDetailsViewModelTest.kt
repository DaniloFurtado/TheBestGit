package desenv.danilo.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import desenv.danilo.domain.interactor.ViewUserUseCase
import desenv.danilo.modelbind.UserBind
import desenv.danilo.presentation.data.DataFactoryUser
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val detailUserUseCase  = mock<ViewUserUseCase>()
    @Captor
    private val captorUser = argumentCaptor<((UserBind) -> Unit)>()
    @Captor
    private val captorError = argumentCaptor<(e: Throwable) -> Unit>()

    private val viewModel = UserDetailsViewModel(detailUserUseCase)
    private val userBind = DataFactoryUser.dummyUser()


    @Test
    fun testShowLoad(){
        viewModel.showProgress()
        assertEquals(
            ViewState.Status.LOADING,
            viewModel.getStateUser().value?.status
        )
    }

    @Test
    fun testExecuteRightMethod(){
        viewModel.showUser("1")
        verify(detailUserUseCase, times(1))
            .executeUseCase(eq("1"), any(), any(), any(), eq(null))
    }

    @Test
    fun testShowUserSuccess(){
        viewModel.showUser("1")
        verify(detailUserUseCase, times(1))
            .executeUseCase(eq("1"), captorUser.capture(), any(), any(), eq(null))
        captorUser.firstValue.invoke(userBind)
        assertEquals(ViewState.Status.SUCCESS, viewModel.getStateUser().value?.status)
    }

    @Test
    fun testShowUserCantBeNull(){
        viewModel.showUser("1")
        verify(detailUserUseCase, times(1))
            .executeUseCase(eq("1"), captorUser.capture(), any(), any(), eq(null))
        captorUser.firstValue.invoke(userBind)
        assertNotNull(viewModel.getStateUser().value)
        assertNotNull(viewModel.getStateUser().value?.data)
    }

    @Test
    fun testCatcheRightUser(){
        viewModel.showUser("1")
        verify(detailUserUseCase, times(1))
            .executeUseCase(eq("1"), captorUser.capture(), any(), any(), eq(null))
        captorUser.firstValue.invoke(userBind)
        assertEquals(ViewState.Status.SUCCESS, viewModel.getStateUser().value?.status)
        assertEquals(userBind, viewModel.getStateUser().value?.data)
    }

    @Test
    fun testCatchErrorException() {
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.showUser("1")
        verify(detailUserUseCase, times(1))
            .executeUseCase(eq("1"), any(), captorError.capture(), any(), eq(null))
        captorError.firstValue.invoke(Exception("Error Test"))

        testObserveError.assertValue {
            "Error Test" == it.error?.message
        }
        testObserveError.assertValue {
            ViewErrorState.TypeExibe.SNACK == it.type
        }
        testObserveError.assertValue {
            R.string.error == it.idMessage
        }

        testObserveError.dispose()
    }

    @Test
    fun testCatchErrorIllegalArgumentExcpetion() {
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.showUser("1")
        verify(detailUserUseCase, times(1))
            .executeUseCase(eq("1"), any(), captorError.capture(), any(), eq(null))
        captorError.firstValue.invoke(IllegalArgumentException("Error Argument wrong Test"))

        testObserveError.assertValue {
            "Error Argument wrong Test" == it.error?.message
        }

        testObserveError.assertValue {
            ViewErrorState.TypeExibe.SNACK == it.type
        }
        testObserveError.assertValue {
            R.string.invalid_paramter == it.idMessage
        }

        testObserveError.dispose()
    }

    @Test
    fun testCatchErrorIRuntimeException() {
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.showUser("1")
        verify(detailUserUseCase, times(1))
            .executeUseCase(eq("1"), any(), captorError.capture(), any(), eq(null))
        captorError.firstValue.invoke(RuntimeException("This is a Error of runtime exception"))

        testObserveError.assertValue {
            "This is a Error of runtime exception" == it.error?.message
        }

        testObserveError.assertValue {
            ViewErrorState.TypeExibe.SNACK == it.type
        }
        testObserveError.assertValue {
            R.string.error_api == it.idMessage
        }

        testObserveError.dispose()
    }



    @Test
    fun testCatchUnknownHostException(){
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.showUser("1")
        verify(detailUserUseCase, times(1))
            .executeUseCase(eq("1"), any(), captorError.capture(), any(), eq(null))
        captorError.firstValue.invoke(UnknownHostException("This is a Error of unknow exception"))

        testObserveError.assertValue {
            "This is a Error of unknow exception" == it.error?.message &&
                    ViewErrorState.TypeExibe.SNACK == it.type &&
                    R.string.error_connectin == it.idMessage
        }

        testObserveError.dispose()
    }


    @Test
    fun testCatchConnectException(){
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.showUser("1")
        verify(detailUserUseCase, times(1))
            .executeUseCase(eq("1"), any(), captorError.capture(), any(), eq(null))
        captorError.firstValue.invoke(ConnectException("This is a Error of connect exception"))

        testObserveError.assertValue {
            "This is a Error of connect exception" == it.error?.message &&
                    ViewErrorState.TypeExibe.SNACK == it.type &&
                    R.string.error_connectin == it.idMessage
        }

        testObserveError.dispose()
    }


    @Test
    fun testCatchTimeoutException(){
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.showUser("1")
        verify(detailUserUseCase, times(1))
            .executeUseCase(eq("1"), any(), captorError.capture(), any(), eq(null))
        captorError.firstValue.invoke(TimeoutException("This is a Error of timeout exception"))

        testObserveError.assertValue {
            "This is a Error of timeout exception" == it.error?.message &&
                    ViewErrorState.TypeExibe.SNACK == it.type &&
                    R.string.error_time_out == it.idMessage
        }

        testObserveError.dispose()
    }
}