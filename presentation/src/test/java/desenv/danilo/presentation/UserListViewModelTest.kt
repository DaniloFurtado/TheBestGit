package desenv.danilo.presentation


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import desenv.danilo.domain.interactor.ListUserUseCase
import desenv.danilo.modelbind.UserBind
import desenv.danilo.presentation.data.DataFactoryUser
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Example local unit test, which will executeUseCase on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class UserListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val listUserUseCase  = mock<ListUserUseCase>()
    @Captor
    private val captorListUser = argumentCaptor<((List<UserBind>) -> Unit)>()
    @Captor
    private val captorError = argumentCaptor<(e: Throwable) -> Unit>()

    private val viewModel = UserListViewModel(listUserUseCase)

    @Test
    fun testExecuteRightMethodOnCreate(){
        viewModel.onCreate()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), any(), any(), any(), eq(null))
    }

    @Test
    fun testExecuteRightMethod(){
        viewModel.listUser()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), any(), any(), any(), eq(null))
    }

    @Test
    fun testShowLoad(){
        viewModel.showProgress()
        assertEquals(
            ViewState.Status.LOADING,
            viewModel.getStateListUser().value?.status)
    }


    @Test
    fun testListAllUsers() {
        viewModel.listUser()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), captorListUser.capture(), any(), any(), eq(null))
        captorListUser.firstValue.invoke(DataFactoryUser.dummyListUser())

        assertEquals(
            ViewState.Status.SUCCESS,
            viewModel.getStateListUser().value?.status)
    }

    @Test
    fun testListAllUsersQtdRight() {
        viewModel.listUser()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), captorListUser.capture(), any(), any(), eq(null))
        captorListUser.firstValue.invoke(DataFactoryUser.dummyListUser())

        assertEquals(
            ViewState.Status.SUCCESS,
            viewModel.getStateListUser().value?.status)

        assertEquals(
            6,
            viewModel.getStateListUser().value?.data?.size)
    }


    @Test
    fun testListAllTheSameList() {
        viewModel.listUser()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), captorListUser.capture(), any(), any(), eq(null))
        val items = DataFactoryUser.dummyListUser()
        captorListUser.firstValue.invoke(items)

        assertEquals(
            items,
            viewModel.getStateListUser().value?.data)

    }

    @Test
    fun testCatchErrorException() {
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.listUser()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), any(), captorError.capture(), any(), eq(null))
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
        viewModel.listUser()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), any(), captorError.capture(), any(), eq(null))
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
        viewModel.listUser()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), any(), captorError.capture(), any(), eq(null))
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
        viewModel.listUser()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), any(), captorError.capture(), any(), eq(null))
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
        viewModel.listUser()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), any(), captorError.capture(), any(), eq(null))
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
        viewModel.listUser()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), any(), captorError.capture(), any(), eq(null))
        captorError.firstValue.invoke(TimeoutException("This is a Error of timeout exception"))

        testObserveError.assertValue {
            "This is a Error of timeout exception" == it.error?.message &&
                    ViewErrorState.TypeExibe.SNACK == it.type &&
                    R.string.error_time_out == it.idMessage
        }

        testObserveError.dispose()
    }
}