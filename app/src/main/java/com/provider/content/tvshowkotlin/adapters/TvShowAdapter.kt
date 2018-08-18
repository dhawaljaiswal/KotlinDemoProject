package com.provider.content.tvshowkotlin.adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.provider.content.tvshowkotlin.R
import com.provider.content.tvshowkotlin.models.TvShow

/**
 * Created by Nand Kishor Patidar on 06,August,2018
 * Email nandkishor.patidar@ratufa.com.
 *
 */
class TvShowAdapter(val showList: MutableList<TvShow>, val context: Context) : RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {

    lateinit var clickListener : OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tv_show_item, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return showList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val tvShow = showList.get(position)
        Glide.with(context)
                .load(tvShow.imageMedium)
                .into(viewHolder.imgTvShow);

        viewHolder.tvTitle.text = tvShow.name
        viewHolder.tvType.text = tvShow.type
        try {
            if (tvShow.rating != null)
                viewHolder.tvRating.text = (tvShow.rating.toFloat() / 2).toString()
            else
                viewHolder.tvRating.text = "0"
        }catch (e : NumberFormatException){
            viewHolder.tvRating.text = "0"
        }


        viewHolder.tvShedule.text = tvShow.schedule

        viewHolder.itemMainContainer.setOnClickListener {
            clickListener.onClick(it, position)
        }
    }

    // This two methods useful for avoiding duplicate item
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.clickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgTvShow: ImageView
        var tvTitle: TextView
        var tvType: TextView
        var tvRating: TextView
        var tvShedule: TextView
        var itemMainContainer: ConstraintLayout

        init {
            imgTvShow = itemView.findViewById(R.id.imgTvShow)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvType = itemView.findViewById(R.id.tvType)
            tvRating = itemView.findViewById(R.id.tvRating)
            tvShedule = itemView.findViewById(R.id.tvShedule)
            itemMainContainer = itemView.findViewById(R.id.itemMainContainer)
        }
    }
}