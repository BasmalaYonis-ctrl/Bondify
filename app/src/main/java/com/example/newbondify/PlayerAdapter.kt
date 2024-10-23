package com.example.newbondify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlayerAdapter(private val players: List<Player>) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)
        val playerNameTextView: TextView = view.findViewById(R.id.playerNameTextView)
        val playerScoreTextView: TextView = view.findViewById(R.id.playerScoreTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_list_item, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.avatarImageView.setImageResource(player.avatarResId)
        holder.playerNameTextView.text = player.name
        holder.playerScoreTextView.text = player.score.toString()
    }

    override fun getItemCount(): Int = players.size
}
