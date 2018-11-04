package com.pashkobohdan.multiwindowbrowser.interactor.spaceList

import com.pashkobohdan.multiwindowbrowser.database.AppDatabase
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpaceDTO
import com.pashkobohdan.multiwindowbrowser.interactor.common.GetUseCase
import javax.inject.Inject

class GetSpaceListUseCase @Inject constructor(): GetUseCase<List<BrowserSpaceDTO>>() {

    @Inject
    internal lateinit var database: AppDatabase

    override fun getData() = database.getBrowserSpaceDAO().allBrowserSpaces
}