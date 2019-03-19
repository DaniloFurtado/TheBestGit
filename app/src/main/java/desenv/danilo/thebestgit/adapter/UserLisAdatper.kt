package desenv.danilo.thebestgit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import desenv.danilo.modelbind.UserBind
import desenv.danilo.thebestgit.BR
import desenv.danilo.thebestgit.databinding.ItemUserListBinding
import kotlinx.android.synthetic.main.item_user_list.view.*


class UserLisAdatper(
    private var listUser: List<UserBind>
): BaseRecyclerView() {

    private var onItemClick: ((UserBind, Int, View) -> Unit)? = null

    fun setInilistenerClick(listenerCLick: (UserBind, Int, View) -> Unit){
        onItemClick = listenerCLick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserListBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val user = listUser[position]
            holder.bind(user)
            holder.itemView.itemRoot.setOnClickListener {
                  onItemClick?.invoke(user, position, holder.itemView.imageUser)
            }
        }else{
            throw RuntimeException("Type ViewHolder not implemented")
        }
    }

    inner class MyViewHolder(private val binding: ItemUserListBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bind(userBind: UserBind) {
            binding.setVariable(BR.itemUser, userBind)
            binding.executePendingBindings()
        }
    }

    fun setItems(items: List<UserBind>) {
        listUser = items
        notifyDataChanged()
    }

    // Verifica se pode notificar a mudança, se já foi adicionado a RecyclerView
    private fun notifyDataChanged() {
        if (getHasRecyclerAtached()) {
            notifyDataSetChanged()
        }
    }

}