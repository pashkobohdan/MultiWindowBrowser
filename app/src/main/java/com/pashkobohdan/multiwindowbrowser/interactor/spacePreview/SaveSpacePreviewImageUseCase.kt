package com.pashkobohdan.multiwindowbrowser.interactor.spacePreview

import com.pashkobohdan.multiwindowbrowser.database.AppDatabase
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpacePreviewImageDTO
import com.pashkobohdan.ttsreader.data.usecase.RunUseCase
import javax.inject.Inject

class SaveSpacePreviewImageUseCase @Inject constructor(): RunUseCase<BrowserSpacePreviewImageDTO>() {

    @Inject
    internal lateinit var database: AppDatabase

    override fun justDoThis(request: BrowserSpacePreviewImageDTO) {
        database.getBrowserSpaceDAO().insertBrowserSpacePreviewImageDTO(request)
    }
}