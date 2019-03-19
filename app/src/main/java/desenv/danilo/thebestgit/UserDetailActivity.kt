package desenv.danilo.thebestgit

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.lifecycle.autoDisposable
import desenv.danilo.modelbind.UserBind
import desenv.danilo.presentation.UserDetailsViewModel
import desenv.danilo.presentation.UserVmFactory
import desenv.danilo.presentation.ViewErrorState
import desenv.danilo.presentation.ViewState
import desenv.danilo.thebestgit.databinding.ActivityUserDetailBinding
import kotlinx.android.synthetic.main.activity_user_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import javax.inject.Inject

// para classes que querem fazer reload quando voltar do back extender a BaseReload
class UserDetailActivity : BaseReloadActivity() {

    companion object {
        const val USER_SELECTED = "user_selected"
    }

    private lateinit var factoryVm: UserVmFactory

    @Inject
    fun setFactory(facotryInjected: UserVmFactory){
        factoryVm = facotryInjected
    }

    private val viewModel: UserDetailsViewModel by lazy {
        ViewModelProviders.of(this,
            factoryVm)
            .get(UserDetailsViewModel::class.java)
    }

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configTransition()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
        addObserver(viewModel)
        startTransition()
        viewModel.showUser(binding.itemUser!!.id)
        initToolbarBack(toolbarDetail)
        initListeners()
    }

    private fun startTransition() {
        if (avaliableTransition()) {
            val userBinde: UserBind? = intent.extras!!.getParcelable(USER_SELECTED)
            imageUser.transitionName = userBinde?.id
            binding.itemUser = userBinde
            startPostponedEnterTransition()
        }
    }

    private fun configTransition() {
        if (avaliableTransition())
            postponeEnterTransition()
    }

    override fun onResumeReload() {
        //Se o Snack estiver fixado na tela, esconde e tenta recarregar novamente.
        hideSnkack()
        viewModel.showUser(binding.itemUser!!.id)
    }


    private fun initListeners() {
        // Ouvindo retorno de api do usuario
        viewModel.getStateUser()
            .observe(this, Observer {
                when(it.status){
                    ViewState.Status.SUCCESS -> {
                        if (it.data != null)
                            binding.itemUser = it.data
                    }
                    ViewState.Status.LOADING -> {}
                }
            })

        // Ouvindo se retornar algum erro
        viewModel.getStatsViewError()
            .autoDisposable(scopeProvider)
            .subscribe {
                when(it.type){
                    ViewErrorState.TypeExibe.SNACK ->{
                        // Função que defini se exibe botão retry ou não do SnackBar
                        showSnackError(rootLayouDetail, it, ::onResumeReload)
                    }
                    ViewErrorState.TypeExibe.TOAST -> toast(it.idMessage).show()
                    ViewErrorState.TypeExibe.ALERT -> alert(it.idMessage).show()
                }
            }


        imageUser.setOnClickListener {
            showImageFull()
        }
    }

    private fun showImageFull() {
        // configura a chamada com animação para Próxima Activity
        if(binding.itemUser != null) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageUser,
                binding.itemUser!!.id
            )
            val bundle = Bundle()
            bundle.putString(ShowPictureActivity.URL_IMAGE, binding.itemUser!!.avatarUrl)
            bundle.putString(ShowPictureActivity.TRANSTION_NAME, binding.itemUser!!.id)
            startActivity(Intent(this, ShowPictureActivity::class.java)
                .apply {
                    // Notifica a ControlReloadFromBackGround sobre o controle de recarregar ou não
                    // a view quando voltar do backcground
                    hasCalledActivity()
                    putExtras(bundle)
                }
                , options.toBundle())
        }
    }
}
