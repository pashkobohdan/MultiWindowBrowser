package com.pashkobohdan.multiwindowbrowser.mvp.browserSpaceList

import com.arellomobile.mvp.InjectViewState
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.impl.HorizontalPartsBrowserSpace
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.impl.VerticalPartsBrowserSpace
import com.pashkobohdan.multiwindowbrowser.browser.browsing.Page
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpaceDTO
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpacePreviewImageDTO
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpaceToBrowserSpaceDTOConverter
import com.pashkobohdan.multiwindowbrowser.interactor.addSpace.SaveSpaceUseCase
import com.pashkobohdan.multiwindowbrowser.interactor.common.observers.DefaultObserver
import com.pashkobohdan.multiwindowbrowser.interactor.spaceList.GetSpaceListUseCase
import com.pashkobohdan.multiwindowbrowser.interactor.spacePreview.GetSpacePreviewImageUseCase
import com.pashkobohdan.multiwindowbrowser.mvp.browserSpaceList.view.BrowserSpaceListView
import com.pashkobohdan.multiwindowbrowser.mvp.common.AbstractPresenter
import com.pashkobohdan.multiwindowbrowser.preferences.AppPreferences
import com.pashkobohdan.multiwindowbrowser.ui.dialog.NewBrowserSpaceType
import javax.inject.Inject

@InjectViewState
class BrowserSpaceListPresenter @Inject constructor() : AbstractPresenter<BrowserSpaceListView>() {

    @Inject
    lateinit var spaceListUseCase: GetSpaceListUseCase
    @Inject
    lateinit var saveSpaceUseCase: SaveSpaceUseCase
    @Inject
    lateinit var getSpacePreviewImageUseCase: GetSpacePreviewImageUseCase
    @Inject
    lateinit var preferences: AppPreferences

    private var list: List<BrowserSpaceDTO> = listOf()

    override fun onFirstViewAttach() {
        viewState.showProgress()
        spaceListUseCase.execute(object : DefaultObserver<List<BrowserSpaceDTO>>() {
            override fun onNext(spaces: List<BrowserSpaceDTO>) {
                list = spaces
                loadPreviewImagesAndShow()
            }

            override fun onFinally() = viewState.hideProgress()
        })
    }

    private fun loadPreviewImagesAndShow() {
        list.forEach { space ->
            getSpacePreviewImageUseCase.execute(space.id, object : DefaultObserver<BrowserSpacePreviewImageDTO?>() {

                override fun onNext(preview: BrowserSpacePreviewImageDTO) {
                    addSpaceToShow(space, preview)
                }

                override fun onError(e: Throwable) {
                    addSpaceToShow(space, null)
                }
            })
        }
    }

    private fun addSpaceToShow(space: BrowserSpaceDTO, previewImage: BrowserSpacePreviewImageDTO?) {
        space.previewImage = previewImage
        viewState.addSpace(space)
    }

    fun addAnotherOneSpace() {
        viewState.showNewBrowserTypeDialog()
    }

    fun saveAnotherOneSpaceAndOpen(type: NewBrowserSpaceType) {
        val newBrowserSpace = when (type) {
            NewBrowserSpaceType.HORIZONTAL -> HorizontalPartsBrowserSpace()
            NewBrowserSpaceType.VERTICAL -> VerticalPartsBrowserSpace()
            NewBrowserSpaceType.HORIZONTAL_WITH_DEFAULT_SITES -> HorizontalPartsBrowserSpace().apply {
                createNewPiece(Page("http://www.google.com"))
                createNewPiece(Page("https://www.youtube.com/"))
            }
            NewBrowserSpaceType.VERTICAL_WITH_DEFAULT_SITES -> VerticalPartsBrowserSpace().apply {
                createNewPiece(Page("http://www.google.com"))
                createNewPiece(Page("https://www.youtube.com/"))
            }
        }

        viewState.showProgress()
        val newSpaceDTO = BrowserSpaceToBrowserSpaceDTOConverter.convertToDTO(newBrowserSpace)
        preferences.spaceCount = preferences.spaceCount + 1
        newSpaceDTO.spaceName = "Space " + preferences.spaceCount
        saveSpaceUseCase.execute(newSpaceDTO, object : DefaultObserver<Unit>() {
            override fun onNext(t: Unit) {
                goToBrowserSpace(newSpaceDTO)
            }

            override fun onFinally() = viewState.hideProgress()
        })
    }

    fun goToBrowserSpace(space: BrowserSpaceDTO) {
        viewState.goToBrowserSpaceWithId(space.id)
    }

    fun renameSpace(space: BrowserSpaceDTO, newName: String) {
        space.spaceName = newName
        viewState.showProgress()
        saveSpaceUseCase.execute(space, object : DefaultObserver<Unit>() {
            override fun onNext(t: Unit) {
                viewState.showSpaces(list)
            }

            override fun onFinally() = viewState.hideProgress()
        })
    }
}