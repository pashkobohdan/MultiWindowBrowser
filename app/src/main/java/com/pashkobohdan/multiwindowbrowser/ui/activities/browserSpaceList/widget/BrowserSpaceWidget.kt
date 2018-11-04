package com.pashkobohdan.multiwindowbrowser.ui.activities.browserSpaceList.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpaceDTO
import com.pashkobohdan.multiwindowbrowser.database.utils.stringToBitMap
import com.pashkobohdan.multiwindowbrowser.databinding.WidgetBrowserSpaceListItemBinding
import com.pashkobohdan.multiwindowbrowser.ui.common.list.AbstractListItemHolder
import com.pashkobohdan.multiwindowbrowser.ui.common.list.AbstractListItemWidget
import javax.inject.Inject



class BrowserSpaceWidget @Inject constructor(): AbstractListItemWidget<BrowserSpaceDTO>() {

    private var changeNameCallback: (BrowserSpaceDTO)->Unit = {}

    fun getHolder(parent: ViewGroup, okClickCallback: (BrowserSpaceDTO) -> Unit, changeNameCallback: (BrowserSpaceDTO)->Unit): AbstractListItemHolder<BrowserSpaceDTO> {
        this.changeNameCallback = changeNameCallback
        return getHolder(parent, okClickCallback)
    }
    override fun getHolder(parent: ViewGroup, okClickCallback: (BrowserSpaceDTO) -> Unit): AbstractListItemHolder<BrowserSpaceDTO> {
        val binding = WidgetBrowserSpaceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AbstractListItemHolder(binding.root, okClickCallback, { space ->
            binding.space = space
            space.previewImage?.bitmapString?.let {bitmapString ->
                space.previewImage?.let { binding.previewImage.setImageBitmap(stringToBitMap(bitmapString)) }
            }
            binding.editName.setOnClickListener { changeNameCallback(space) }
        })
    }
}