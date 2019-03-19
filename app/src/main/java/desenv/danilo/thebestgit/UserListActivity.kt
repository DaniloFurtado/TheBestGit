package desenv.danilo.thebestgit

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.uber.autodispose.lifecycle.autoDisposable
import desenv.danilo.modelbind.UserBind
import desenv.danilo.presentation.UserListViewModel
import desenv.danilo.presentation.UserVmFactory
import desenv.danilo.presentation.ViewErrorState
import desenv.danilo.presentation.ViewState
import desenv.danilo.thebestgit.adapter.UserLisAdatper
import desenv.danilo.thebestgit.util.extensions.gone
import desenv.danilo.thebestgit.util.extensions.show
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import javax.inject.Inject


class UserListActivity : BaseReloadActivity(){


    private val viewModel: UserListViewModel by lazy {
        ViewModelProviders.of(this,
            factoryVm)
            .get(UserListViewModel::class.java)
    }
    private lateinit var factoryVm: UserVmFactory
    private lateinit var userListAdapter: UserLisAdatper

    @Inject
    fun setFactory(facotryInjected: UserVmFactory){
        factoryVm = facotryInjected
    }

    @Inject
    fun setAdatper(adatper: UserLisAdatper){
        userListAdapter = adatper
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userListAdapter.setInilistenerClick(this::userSelected)
        initToolbar()
        addObserver(viewModel)
        initActivity()
    }

    override fun onResumeReload() {
        //Se o Snack estiver fixado na tela, esconde e tenta recarregar novamente.
        hideSnkack()
        viewModel.listUser()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initActivity() {
        // Ouvindo retorno da api com a lista de usuarios
        viewModel.getStateListUser()
            .observe(this, Observer {
                when(it.status){
                    ViewState.Status.SUCCESS -> {
                        finishLoad()
                        initRecyclerView(it.data)
                    }
                    ViewState.Status.LOADING -> {
                        progressBar.show()
                    }
                }
            })

        // Ouvindo se retornar algum erro
        viewModel.getStatsViewError()
            .autoDisposable(scopeProvider)
            .subscribe {
                when(it.type) {
                    ViewErrorState.TypeExibe.SNACK -> {
                        finishLoad()
                        showSnackError(rootLayouMain, it, ::onResumeReload)
                    }
                    ViewErrorState.TypeExibe.TOAST -> {
                        finishLoad()
                        toast(it.idMessage).show()
                    }
                    ViewErrorState.TypeExibe.ALERT -> {
                        finishLoad()
                        alert(getString(it.idMessage)) {
                            title = getString(R.string.algo_errado)
                            isCancelable = false
                            if (it.retry){
                                positiveButton(R.string.retry) {
                                    onResumeReload()
                                }
                                cancelButton {

                                }
                            }else
                                yesButton {

                                }
                        }.show()
                    }
                }
            }
    }


    private fun finishLoad() {
        recyclerList.show()
        progressBar.gone()
    }

    private fun initRecyclerView(listUsers: List<UserBind>?) {
        if (listUsers != null) {
            val qtdColumns = resources.getInteger(R.integer.columns_grid)
            // Define o Staggered para o efeito Waterfall
            recyclerList.layoutManager = StaggeredGridLayoutManager(qtdColumns, StaggeredGridLayoutManager.VERTICAL)
            userListAdapter.setItems(listUsers)
            recyclerList.adapter = userListAdapter
        }
    }

    private fun userSelected(user: UserBind, position: Int, view: View){
        // configura a chamada com animação para Próxima Activity
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            view,
            user.id
        )
        val bundle = Bundle()
        bundle.putParcelable(UserDetailActivity.USER_SELECTED, user)
        startActivity(Intent(this, UserDetailActivity::class.java)
            .apply {
                // Notifica a ControlReloadFromBackGround sobre o controle de recarregar ou não
                // a view quando voltar do backcground
                hasCalledActivity()
                putExtras(bundle)
            }, options.toBundle())
    }


}
