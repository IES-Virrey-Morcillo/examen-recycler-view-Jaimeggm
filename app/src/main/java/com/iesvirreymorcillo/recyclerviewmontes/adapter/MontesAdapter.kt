import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iesvirreymorcillo.recyclerviewmontes.Montes
import com.iesvirreymorcillo.recyclerviewmontes.adapter.MontesViewHolder
import com.iesvirreymorcillo.recyclerviewmontes.databinding.ItemMontesBinding

class MontesAdapter(
    private var montesList: MutableList<Montes>,
    private val onClickListener: (Montes) -> Unit,
    private val onClickDelete: (Int) -> Unit
) : RecyclerView.Adapter<MontesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MontesViewHolder {
        val binding = ItemMontesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MontesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MontesViewHolder, position: Int) {
        holder.render(montesList[position], onClickListener, onClickDelete)
    }

    override fun getItemCount(): Int = montesList.size

    fun getMontesList(): MutableList<Montes> = montesList

    fun filterByName(filteredList: MutableList<Montes>) {
        montesList = filteredList
        notifyDataSetChanged()
    }
}
