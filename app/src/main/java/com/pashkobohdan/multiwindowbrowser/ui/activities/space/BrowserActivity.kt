package com.pashkobohdan.multiwindowbrowser.ui.activities.space

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.pashkobohdan.multiwindowbrowser.MultiwindowBrowserApplication
import com.pashkobohdan.multiwindowbrowser.R
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.BrowserSpace
import com.pashkobohdan.multiwindowbrowser.mvp.browser.BrowserPresenter
import com.pashkobohdan.multiwindowbrowser.mvp.browser.view.BrowserView
import com.pashkobohdan.multiwindowbrowser.ui.activities.browserSpaceList.BrowserSpaceListActivity
import com.pashkobohdan.multiwindowbrowser.ui.browserUiCreator.BrowserUiCreatorFactory
import com.pashkobohdan.multiwindowbrowser.ui.common.activity.AbstractScreenActivity
import com.pashkobohdan.multiwindowbrowser.ui.doOnceAfterCreate
import com.pashkobohdan.multiwindowbrowser.ui.pieceCreator.UICreator
import kotlinx.android.synthetic.main.activity_browser.*


class BrowserActivity : AbstractScreenActivity<BrowserPresenter>(), BrowserView {

    @InjectPresenter
    lateinit var presenter: BrowserPresenter

    lateinit var uiCreator: UICreator

    @ProvidePresenter
    fun providePresenter(): BrowserPresenter {
        return presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiwindowBrowserApplication.applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)

        addPieceButton.setOnClickListener { presenter.addNewPiece() }
        removePieceButton.setOnClickListener { presenter.removeLastPiece() }

        openList.setOnClickListener {
            val intent = Intent(this, BrowserSpaceListActivity::class.java)
            startActivity(intent)
        }

        if (savedInstanceState == null) {
            openBookFromIntent(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        openBookFromIntent(intent)
    }

    private fun openBookFromIntent(intent: Intent?) {
        intent?.let { browserIntent ->
            val spaceId = browserIntent.getLongExtra(BROWSER_SPACE_ID_KEY, 0)
            if (spaceId != 0L) {
                presenter.openBrowserSpaceById(spaceId)
            }
        }
    }

    override fun initUiCreator(browserSpace: BrowserSpace) {
        uiCreator = BrowserUiCreatorFactory.createUiCreator(browserSpace)
        uiCreator.historyChangedCallback = { presenter.historyChanged() }
        uiCreator.pageLoadedCallback = { makePrintScreenForSave() }
        uiCreator.goToPageCallback = { piece, url -> presenter.goToPage(piece, url) }

        val browserSpaceView = uiCreator.inflateRootView()
        browserSpaceRoot.addView(browserSpaceView)
        browserSpaceView.doOnceAfterCreate {
            browserSpace.browserPieces.forEach { uiCreator.tryRefreshPieceSize(it) }
        }
    }

    override fun removePiece(browserPiece: BrowserPiece) {
        //TODO
    }

    override fun refreshPieces(pieceList: Set<BrowserPiece>) {
        pieceList.forEach {
            uiCreator.tryRefreshPieceSize(it)
        }
    }

    override fun goToUrl(piece: BrowserPiece, url: String) {
        uiCreator.goToUrl(piece, url)
    }

    private fun makePrintScreenForSave() {
        browserSpaceRoot.setDrawingCacheEnabled(true)
        browserSpaceRoot.buildDrawingCache()
        val bm = browserSpaceRoot.getDrawingCache()
        presenter.saveSpacePreviewImage(bm)
    }

    companion object {
        const val BROWSER_SPACE_ID_KEY = "spaceIdKey"
    }
}
