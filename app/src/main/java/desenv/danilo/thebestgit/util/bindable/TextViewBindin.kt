package desenv.danilo.thebestgit.util.bindable

import android.widget.TextView
import androidx.databinding.BindingAdapter
import desenv.danilo.thebestgit.R


@BindingAdapter("app:setTextShowItem")
fun TextView.setTextShowItem(textSetup: String?) {
    if ( textSetup != null && textSetup.isNotEmpty()) {
        this.text = textSetup
    }else
        this.text = this.context.getString(R.string.not_informed)
}