package com.example.polycareer.screens.quiz.quiz_results.recycler

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.polycareer.R
import com.example.polycareer.domain.model.Direction


class DirectionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var number: AppCompatTextView = itemView.findViewById(R.id.dir_item__tv_num1)
    private var title: AppCompatTextView = itemView.findViewById(R.id.dir_item__tv_dest)
    private var description: AppCompatTextView = itemView.findViewById(R.id.dir_item__tv_descr)

    fun bind(number: Int, direction: Direction) {
        this.number.text = number.toString()
        title.text = direction.title
        if (direction.description.isEmpty()) {
            description.visibility = View.GONE
        }
        description.text = direction.description
        itemView.setOnClickListener { openUrl(direction.url) }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(itemView.context, intent, null)
    }
}