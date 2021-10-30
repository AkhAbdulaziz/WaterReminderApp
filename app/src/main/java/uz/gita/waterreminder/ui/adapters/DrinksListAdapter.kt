package uz.gita.waterreminder.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.waterreminder.R
import uz.gita.waterreminder.data.entity.DrinksItemEntity

class DrinksListAdapter(private val list: ArrayList<DrinksItemEntity>) :
    RecyclerView.Adapter<DrinksListAdapter.VH>() {

    private var deleteListener: ((Int) -> Unit)? = null
    fun setDeleteListener(block: (Int) -> Unit) {
        deleteListener = block
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val itemImage = view.findViewById<ImageView>(R.id.itemImage)
        private val time = view.findViewById<TextView>(R.id.time)
        private val nextTimeText = view.findViewById<TextView>(R.id.nextTime)
        private val glassSize = view.findViewById<TextView>(R.id.glassSize)
        private val itemDeleteImg = view.findViewById<ImageView>(R.id.itemDeleteImg)

        init {
            itemDeleteImg.setOnClickListener {
                deleteListener?.invoke(absoluteAdapterPosition)
            }
        }

        fun bind() {
            val data = list[absoluteAdapterPosition]
            itemImage.setImageResource(data.imageId)
            time.text = data.time
            nextTimeText.visibility = if (data.haveNextTimeText) View.VISIBLE else View.GONE
            glassSize.text = "${data.glassSize} ml"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.drinks_list_item, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    override fun getItemCount(): Int = list.size
}