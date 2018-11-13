package com.pashkobohdan.multiwindowbrowser.ui.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pashkobohdan.multiwindowbrowser.R
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.BrowserSpace
import com.pashkobohdan.multiwindowbrowser.ui.browserUiCreator.BrowserUiCreatorFactory
import com.pashkobohdan.multiwindowbrowser.ui.browserUiCreator.piece.BrowserPieceView
import com.pashkobohdan.multiwindowbrowser.ui.doOnceAfterCreate
import com.pashkobohdan.multiwindowbrowser.ui.fragments.spacePieces.SpacePiecesUIHandler
import com.pashkobohdan.multiwindowbrowser.ui.pieceCreator.UICreator
import kotlinx.android.synthetic.main.fragment_space_pieces.*

class SpacePiecesFragment : Fragment(), SpacePiecesUIHandler {

    var uiCreator: UICreator? = null

    private val pieceViewMap = mutableMapOf<BrowserPiece, BrowserPieceView>()

    private var isViewCreated = false
    override var browserSpace: BrowserSpace? = null
        set(value) {
            if (field == null && value != null) {
                field = value
                if (isViewCreated) {
                    initUiCreator(value, null)
                }
            }
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_space_pieces, container, false)
                ?: throw IllegalStateException("Layout inflator is null")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        browserSpace?.let {
            initUiCreator(it, savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let { state ->
            pieceViewMap.forEach {key, value->
                val bundle = Bundle()
                value.onSaveInstanceState(bundle)
                outState.putBundle("" + key.hashCode(), bundle)
            }
        }
    }

    private fun initUiCreator(browserSpace: BrowserSpace, savedInstanceState: Bundle?) {
        uiCreator = BrowserUiCreatorFactory.createUiCreator(browserSpace).apply {
            val browserSpaceView = this.inflateRootView()
            spacePiecesContainer.addView(browserSpaceView)

            browserSpaceView.doOnceAfterCreate {
                pieceViewMap.clear()
                browserSpace.browserPieces.forEach { piece ->

                    val pieceView = this.createViewForPiece(piece)
                    pieceViewMap.put(piece, pieceView)

                    this.refreshPieceSize(piece)

                    if (savedInstanceState == null) {
                        this.goToRootPage(piece)
                    } else {
                        val bundle = savedInstanceState.getBundle("" + piece.hashCode())
                        pieceView.onViewStateRestored(bundle)
                    }
                }

            }
        }
    }

    override fun removePiece(browserPiece: BrowserPiece) {
        uiCreator?.let {
            it.removePiece(browserPiece)
            pieceViewMap.remove(browserPiece)

            browserSpace?.browserPieces?.forEach {
                uiCreator?.refreshPieceSize(it)
            }
        }
    }

    override fun addPiece(browserPiece: BrowserPiece) {
        uiCreator?.let {
            pieceViewMap.put(browserPiece, it.createViewForPiece(browserPiece))
            it.refreshPieceSize(browserPiece)
            it.goToRootPage(browserPiece)

            browserSpace?.browserPieces?.forEach {
                uiCreator?.refreshPieceSize(it)
            }
        }
    }

    override fun goToNewUrl(piece: BrowserPiece, url: String) {
        uiCreator?.goToUrl(piece, url)
    }

    override fun setPageCompletedCallback(toDo: (String) -> Unit) {
        uiCreator?.pageCompletedCallback = toDo
    }

    override fun setNavigatedToNewUrlCallback(toDo: (BrowserPiece, String) -> Unit) {
        uiCreator?.navigatedToUrlCallback = toDo
    }

    override fun setGoToUrlOrSearchCallback1(toDo: (BrowserPiece, String) -> Unit) {
        uiCreator?.goToUrlOrSearchCallback = toDo
    }
}