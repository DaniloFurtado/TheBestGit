package desenv.danilo.thebestgit.util.bindable

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import desenv.danilo.thebestgit.R
import desenv.danilo.thebestgit.util.CircleTransform


@BindingAdapter("app:setUrlImage")
fun ImageView.setUrlImage(url: String?) {
    if (url != null)
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.ic_broken_image)
            .transform(CircleTransform())
            .into(this)

}


@BindingAdapter("app:setUrlImageNoRound")
fun ImageView.setUrlImageNoRound(url: String?) {
    if (url != null && url.isNotEmpty())
        Picasso.get()
            .load(url)
            .error(R.drawable.ic_broken_image)
            .into(this)
}
