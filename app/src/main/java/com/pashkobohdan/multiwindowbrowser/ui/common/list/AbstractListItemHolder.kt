package com.pashkobohdan.multiwindowbrowser.ui.common.list

import android.support.v7.widget.RecyclerView
import android.view.View


class AbstractListItemHolder<T>(itemView: View, private val okClickCallback: (T)->Unit, private val onBindAction: (T)->Unit) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private var data: T? = null

    init {
        itemView.setOnClickListener(this)
    }

    fun onBindViewHolder(t: T) {
        this.data = t
        onBindAction(t)
    }

    override fun onClick(v: View) {
        okClickCallback(data?:throw IllegalStateException("Data in Holder ${this} is null"))
    }
}
