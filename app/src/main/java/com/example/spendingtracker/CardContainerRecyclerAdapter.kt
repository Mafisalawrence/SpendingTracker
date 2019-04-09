package com.example.spendingtracker

import android.content.Context
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.spendingtracker.RealmObjects.CategoryObject
import io.realm.RealmResults
import kotlinx.android.synthetic.main.card_layout.view.*

class CardContainerRecyclerAdapter(val context: Context?, val titles: RealmResults<CategoryObject>) : RecyclerView.Adapter<CardContainerRecyclerAdapter.ViewHolder>()
{
    override fun getItemCount(): Int {
        return titles.size
    }
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false))
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.itemView.card_text.text = titles.get(p1)?.name

        p0.itemView.setOnClickListener {
            Toast.makeText(context , titles.get(p1)?.name ,Toast.LENGTH_SHORT).show()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

    }
}
