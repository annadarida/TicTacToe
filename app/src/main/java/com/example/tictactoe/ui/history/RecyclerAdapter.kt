package com.example.tictactoe.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domains.entity.ScoreClass
import com.example.tictactoe.R

class RecyclerAdapter(val dataList: List<ScoreClass>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val imageView1 = itemView.findViewById<ImageView>(R.id.icon_player1)
        val imageView2 = itemView.findViewById<ImageView>(R.id.icon_player2)

        val score1 = itemView.findViewById<TextView>(R.id.player1_score)
        val score2 = itemView.findViewById<TextView>(R.id.player2_score)

        val player1 = itemView.findViewById<TextView>(R.id.player1_name)
        val player2 = itemView.findViewById<TextView>(R.id.player2_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val scoreCl: ScoreClass = dataList[position]
        holder.imageView1.setImageResource(R.drawable.pizza)
        holder.score1.text = scoreCl.scoreX.toString()
        holder.player1.text = scoreCl.playerX

        holder.imageView2.setImageResource(R.drawable.donut)
        holder.score2.text = scoreCl.scoreO.toString()
        holder.player2.text = scoreCl.playerO
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}