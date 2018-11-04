package com.pashkobohdan.multiwindowbrowser.ui.common.activity

import android.view.ViewGroup
import com.pashkobohdan.multiwindowbrowser.mvp.common.AbstractPresenter
import com.pashkobohdan.multiwindowbrowser.ui.common.list.AbstractListItemHolder
import com.pashkobohdan.multiwindowbrowser.ui.common.list.adapter.AbstractListAdapter



abstract class AbstractListScreenActivity<T : AbstractPresenter<*>, H> : AbstractScreenActivity<T>() {

    inner class ListAdapter() : AbstractListAdapter<H>() {

        override fun onBindViewHolder(holder: AbstractListItemHolder<H>, position: Int) {
            holder.onBindViewHolder(dataList[position])
        }

        override fun createItemHolder(parent: ViewGroup): AbstractListItemHolder<H> {
            return getItemHolder(parent)
        }
    }

    abstract fun getItemHolder(parent: ViewGroup): AbstractListItemHolder<H>
}