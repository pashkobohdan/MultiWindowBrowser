package com.pashkobohdan.multiwindowbrowser.mvp.browserSpaceList.view

import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpaceDTO
import com.pashkobohdan.multiwindowbrowser.mvp.common.AbstractScreenView

interface BrowserSpaceListView : AbstractScreenView {

    fun showSpaces(spaceList: List<BrowserSpaceDTO>)

    fun addSpace(space: BrowserSpaceDTO)

    fun showProgress()

    fun hideProgress()

    fun showNewBrowserTypeDialog()

    fun goToBrowserSpaceWithId(id: Long)
}