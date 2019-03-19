package desenv.danilo.thebestgit

import android.os.Bundle
import desenv.danilo.thebestgit.util.bindable.setUrlImage
import kotlinx.android.synthetic.main.activity_show_picture.*

class ShowPictureActivity : BaseActivity() {

    companion object {
        const val URL_IMAGE = "url_image"
        const val TRANSTION_NAME = "id_transition"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configTransition()
        setContentView(R.layout.activity_show_picture)
        initUrlImage()
        startTransition()
        showImage.setOnClickListener {
            onBackPressed()
        }

        initToolbarBack(toolbarPhoto)
        title = ""
    }

    private fun initUrlImage() {
        val urlImage = intent.extras!!.getString(URL_IMAGE, "")
        // Carregar a imagem usando o BindingFunction para a ImageView (ImageViewBinding)
        showImage.setUrlImage(urlImage)
    }

    private fun startTransition() {
        if (avaliableTransition() && intent.extras != null) {
            showImage.transitionName = intent.extras!!.getString(TRANSTION_NAME, "")
            startPostponedEnterTransition()
        }
    }


    private fun configTransition() {
        if (avaliableTransition())
            postponeEnterTransition()
    }

}
