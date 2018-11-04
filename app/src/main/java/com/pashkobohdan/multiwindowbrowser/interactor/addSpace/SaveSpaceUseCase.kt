package com.pashkobohdan.multiwindowbrowser.interactor.addSpace

import com.pashkobohdan.multiwindowbrowser.database.AppDatabase
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpaceDTO
import com.pashkobohdan.ttsreader.data.usecase.RunUseCase
import javax.inject.Inject

class SaveSpaceUseCase @Inject constructor() : RunUseCase<BrowserSpaceDTO>() {

    @Inject
    internal lateinit var database: AppDatabase

    override fun justDoThis(request: BrowserSpaceDTO) {
        database.getBrowserSpaceDAO().insertAllBrowserSpaceDTOs(request)
    }
}