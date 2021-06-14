package com.example.projetfinalandroid.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinalandroid.ui.ParametreActivity
import com.example.projetfinalandroid.R

class ParametreAdapter(private val parametreList: Array<ParametreActivity.ParametreItem>) : RecyclerView.Adapter<ParametreAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        // Fonction qui permet d'afficher l'item
        fun displayItem(item: ParametreActivity.ParametreItem){
            itemView.findViewById<TextView>(R.id.textItem).text = item.name
            itemView.findViewById<ImageView>(R.id.imageItem).setImageResource(item.icone)
            itemView.setOnClickListener({
                item.onClick.invoke()
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.parametre_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.displayItem(parametreList[position])
    }

    override fun getItemCount(): Int {
        return parametreList.size
    }

}