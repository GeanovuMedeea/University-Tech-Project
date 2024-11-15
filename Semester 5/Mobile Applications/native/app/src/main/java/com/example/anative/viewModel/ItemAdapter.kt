package com.example.anative.viewModel
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anative.domain.Item
import logd
import com.example.anative.R

class ItemAdapter(private var itemList: List<Item>, private val onItemClick: (Item) -> Unit, private val onDeleteClick: (Item) -> Unit, private val onUpdateClick: (Item) -> Unit) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.itemNameText)
        val itemDescription: TextView = view.findViewById(R.id.itemDescriptionText)
        val itemOrigin: TextView = view.findViewById(R.id.itemOriginText)
        val updateButton: View = view.findViewById(R.id.updateButton)
        val deleteButton: View = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemName.text = item.name
        var originAndYearText = item.origin

        if (item.year > 0) {
            originAndYearText += " ${item.year} "
            originAndYearText += "AD"
        } else {
            originAndYearText += " ${-item.year} "
            originAndYearText += "BC"
        }

        if(item.description.length > 255)
            holder.itemDescription.text = item.description.substring(255)
        else
            holder.itemDescription.text = item.description

        holder.itemOrigin.text = originAndYearText

        holder.itemView.setOnClickListener{onItemClick(item)}
        holder.updateButton.setOnClickListener{onUpdateClick(item)}
        holder.deleteButton.setOnClickListener{onDeleteClick(item)}
        logd("Item created: ${item.name} at position $position")
    }

    override fun getItemCount(): Int {
        return itemList.size }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Item>) {
        itemList = newList
        notifyDataSetChanged()
    }

    override fun onViewDetachedFromWindow(holder: ItemViewHolder) {
        super.onViewDetachedFromWindow(holder)

        val position = holder.adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            val item = itemList[position]
            logd("Item detached from view: ${item.name} at position $position")
        }
    }
}
