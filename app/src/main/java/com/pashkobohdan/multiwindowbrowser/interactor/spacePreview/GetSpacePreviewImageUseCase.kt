package com.pashkobohdan.multiwindowbrowser.interactor.spacePreview

import com.pashkobohdan.multiwindowbrowser.database.AppDatabase
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpacePreviewImageDTO
import com.pashkobohdan.ttsreader.data.usecase.ExchangeUseCase
import javax.inject.Inject

class GetSpacePreviewImageUseCase @Inject constructor() : ExchangeUseCase<Long, BrowserSpacePreviewImageDTO?>() {

    @Inject
    internal lateinit var database: AppDatabase

    override fun getData(request: Long): BrowserSpacePreviewImageDTO {
        return database.getBrowserSpaceDAO().getBrowserSpacePreviewImageDTOById(request)[0]
    }
}