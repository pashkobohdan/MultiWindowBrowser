package com.pashkobohdan.multiwindowbrowser.ui.common.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.pashkobohdan.multiwindowbrowser.ui.common.list.AbstractListItemHolder


abstract class AbstractListAdapter<T> : RecyclerView.Adapter<AbstractListItemHolder<T>>() {

    var dataList: MutableList<T> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItems(vararg items: T) {
        dataList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractListItemHolder<T> {
        return createItemHolder(parent)
    }

    override fun onBindViewHolder(holder: AbstractListItemHolder<T>, position: Int) {
        holder.onBindViewHolder(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    abstract fun createItemHolder(parent: ViewGroup): AbstractListItemHolder<T>
}
