package com.pashkobohdan.multiwindowbrowser.mvp.browser

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.BrowserSpace
import com.pashkobohdan.multiwindowbrowser.browser.browsing.Page
import com.pashkobohdan.multiwindowbrowser.browser.utils.getValidUrlOrGoogleSearch
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpaceDTO
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpacePreviewImageDTO
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpaceToBrowserSpaceDTOConverter
import com.pashkobohdan.multiwindowbrowser.database.utils.bitMapToString
import com.pashkobohdan.multiwindowbrowser.interactor.addSpace.SaveSpaceUseCase
import com.pashkobohdan.multiwindowbrowser.interactor.common.observers.DefaultObserver
import com.pashkobohdan.multiwindowbrowser.interactor.spaceList.GetSpaceListUseCase
import com.pashkobohdan.multiwindowbrowser.interactor.spacePreview.SaveSpacePreviewImageUseCase
import com.pashkobohdan.multiwindowbrowser.mvp.browser.view.BrowserView
import com.pashkobohdan.multiwindowbrowser.mvp.common.AbstractPresenter
import com.pashkobohdan.multiwindowbrowser.preferences.AppPreferences
import com.pashkobohdan.multiwindowbrowser.ui.fragments.spacePieces.SpacePiecesUIHandler
import javax.inject.Inject

@InjectViewState
class BrowserPresenter @Inject constructor() : AbstractPresenter<BrowserView>() {

    private val ROOT_PAGE = "http://www.google.com/"

    @Inject
    lateinit var spaceListUseCase: GetSpaceListUseCase
    @Inject
    lateinit var saveSpaceUseCase: SaveSpaceUseCase
    @Inject
    lateinit var saveSpacePreviewImageUseCase: SaveSpacePreviewImageUseCase
    @Inject
    lateinit var appPreferences: AppPreferences

    private lateinit var browserSpace: BrowserSpace

    lateinit var spacePiecesUIHandler: SpacePiecesUIHandler
    lateinit var activePiece: BrowserPiece

    override fun onFirstViewAttach() {
        //TODO reading current space

//        spaceListUseCase.execute(object : DefaultObserver<List<BrowserSpaceDTO>>() {
//            override fun onNext(spaceList: List<BrowserSpaceDTO>) {
//                if (spaceList.isEmpty()) {
//                    browserSpace = HorizontalPartsBrowserSpace()
//                } else {
//                    browserSpace = BrowserSpaceToBrowserSpaceDTOConverter.convertToSpace(spaceList[0])
//                }
//                viewState.initUiCreator(browserSpace)
//            }
//
//            override fun onFinally() {
//                //TODO
//            }
//        })
//        browserSpace = HorizontalPartsBrowserSpace()
//        viewState.initUiCreator(browserSpace)
    }

    fun openBrowserSpaceById(id: Long) {
        //TODO  progress
        spaceListUseCase.execute(object : DefaultObserver<List<BrowserSpaceDTO>>() {
            override fun onNext(spaceList: List<BrowserSpaceDTO>) {
                spaceList.find { it.id == id }?.let { browserDTO ->
                    appPreferences.lastOpenSpaceId = id
                    browserSpace = BrowserSpaceToBrowserSpaceDTOConverter.convertToSpace(browserDTO)
//                    viewState.initUiCreator(browserSpace)

                    activePiece = browserSpace.browserPieces.firstOrNull() ?: throw IllegalStateException("There's no piece in this space")
                    spacePiecesUIHandler.browserSpace = browserSpace
                    spacePiecesUIHandler.setPageCompletedCallback {
                        //TODO add time comparing. Don't do more than 1 screenshot by 30-60 sec
                        viewState.makePrintScreenForSave()
                    }
                    spacePiecesUIHandler.setNavigatedToNewUrlCallback { browserPiece, url ->
                        //TODO check what's going on in presenter !'
                        navigatedToUrl(browserPiece, url)
                    }
                    spacePiecesUIHandler.setGoToUrlOrSearchCallback { browserPiece, url ->
                        //TODO check what's going on in presenter !'
                        goToUrlOrSearch(browserPiece, url)
                    }
                    spacePiecesUIHandler.setChangeActivePieceCallback { browserPiece ->
                        activePiece = browserPiece
                    }
                }
            }

            override fun onFinally() {
                //TODO
            }
        })
    }

    fun navigatedToUrl(piece: BrowserPiece, url: String) {
        piece.historyManager.goToPage(Page(url))
        saveSpace()
    }

    fun goToUrlOrSearch(piece: BrowserPiece, url: String) {
        val validUrl = url.getValidUrlOrGoogleSearch()
        navigatedToUrl(piece, validUrl)
//        viewState.goToUrl(piece, validUrl)
        spacePiecesUIHandler.goToNewUrl(piece, validUrl)
    }

    private fun saveSpace() {
        val spaceDTO = BrowserSpaceToBrowserSpaceDTOConverter.convertToDTO(browserSpace)
        saveSpaceUseCase.execute(spaceDTO, object : DefaultObserver<Unit>() {
            override fun onNext(t: Unit) {
                println("saved !!!")
            }

            override fun onFinally() {
//TODO
            }
        })
    }

    fun saveSpacePreviewImage(bitmap: Bitmap) {
        browserSpace.browserSpaceDTO?.id?.let { spaceId ->

            val previewImageAsString = bitMapToString(bitmap)
            val previewImageDTO = BrowserSpacePreviewImageDTO(spaceId, previewImageAsString)
            saveSpacePreviewImageUseCase.execute(previewImageDTO, object : DefaultObserver<Unit>() {
                override fun onNext(t: Unit) {

                }

                override fun onFinally() {
                    //TODO smth (for example progress bar stopping)
                }
            })
        }

    }

    fun addNewPiece() {
        val addedPiece = browserSpace.createNewPiece(Page(ROOT_PAGE))
//        viewState.addPiece(addedPiece)
        spacePiecesUIHandler.addPiece(addedPiece)
        saveSpace()
    }

    fun removeLastPiece() {
        if (browserSpace.browserPieces.size > 1) {
            val removingPiece = browserSpace.browserPieces.last()
            browserSpace.removeLastPiece(removingPiece)
//            viewState.removePiece(removingPiece)
            spacePiecesUIHandler.removePiece(removingPiece)
            saveSpace()
        } else {
            //TODO dialog for removing space or smth else
        }
    }

    fun tryGoBackOnActivePiece(): Boolean {
        if (activePiece.historyManager.canGoBack()) {
            val previuosPage = activePiece.historyManager.back()
            spacePiecesUIHandler.goToNewUrl(activePiece, previuosPage.url)
            return true
        }
        return false
    }
}