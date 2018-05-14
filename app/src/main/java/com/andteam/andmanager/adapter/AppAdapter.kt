package com.andteam.andmanager.adapter

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.andteam.andmanager.R
import com.andteam.andmanager.util.OnItemClickListener
import kotlinx.android.synthetic.main.item_app.view.*
import org.jetbrains.anko.image
import java.util.ArrayList

/**
* Created by hyunjin on 2018. 5. 11..
*/
class BaseAdapter(private var mItems: ArrayList<RecyclerItem>, listener : OnItemClickListener) : RecyclerView.Adapter<BaseAdapter.ItemViewHolder>() {

    val listeners : OnItemClickListener = listener

    // 새로운 뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)

        return ItemViewHolder(view)
    }

    // View 의 내용을 해당 포지션의 데이터로 바꿉니다.
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.appName.text = mItems[position].name
        holder.appIcon.image = mItems[position].icon
    }

    // 데이터 셋의 크기를 리턴
    override fun getItemCount(): Int {
        return mItems.size
    }

    // 커스텀 뷰홀더
    // binding widgets on item layout
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            listeners.onItemClick(layoutPosition)
        }

        val appName: TextView = itemView.appName
        val appIcon : ImageView = itemView.appIcon

    }
}

class RecyclerItem(val name: String, val icon: Drawable, val packageNames: String)