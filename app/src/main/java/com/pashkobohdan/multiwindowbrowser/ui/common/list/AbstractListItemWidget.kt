package com.pashkobohdan.multiwindowbrowser.ui.common.list

import android.view.ViewGroup

abstract class AbstractListItemWidget<T : Any> {

    lateinit var item: T;

    abstract fun getHolder(parent: ViewGroup, okClickCallback: (T)->Unit): AbstractListItemHolder<T>
}
