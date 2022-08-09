package com.khldaliyeva.mymaps

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khldaliyeva.mymaps.models.UserMap

class MapsAdapter(
    private val context: Context,
    private val userMaps: List<UserMap>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<MapsAdapter.MapsViewHolder>() {

    companion object {
        private const val TAG = "MapsAdapter"
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    // EXPENSIVE
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsViewHolder {
        val view =
            LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return MapsViewHolder(view)
    }

    // INEXPENSIVE
    override fun onBindViewHolder(holder: MapsViewHolder, position: Int) {
        val userMap = userMaps[position]
        holder.tvTitle.text = userMap.title

        holder.itemView.setOnClickListener {
            Log.i(TAG, "setOnClickListener, Tapped on position $position")
            onClickListener.onItemClick(position)
        }
    }

    override fun getItemCount() = userMaps.size

    class MapsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(android.R.id.text1)
    }
}