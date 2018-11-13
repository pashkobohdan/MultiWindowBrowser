package com.pashkobohdan.multiwindowbrowser.ui.activities.space

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.pashkobohdan.multiwindowbrowser.MultiwindowBrowserApplication
import com.pashkobohdan.multiwindowbrowser.R
import com.pashkobohdan.multiwindowbrowser.browser.browserPiece.BrowserPiece
import com.pashkobohdan.multiwindowbrowser.browser.browserSpace.BrowserSpace
import com.pashkobohdan.multiwindowbrowser.mvp.browser.BrowserPresenter
import com.pashkobohdan.multiwindowbrowser.mvp.browser.view.BrowserView
import com.pashkobohdan.multiwindowbrowser.ui.activities.browserSpaceList.BrowserSpaceListActivity
import com.pashkobohdan.multiwindowbrowser.ui.common.activity.AbstractScreenActivity
import com.pashkobohdan.multiwindowbrowser.ui.doIfSeveralFingersTouch
import com.pashkobohdan.multiwindowbrowser.ui.fragments.SpacePiecesFragment
import kotlinx.android.synthetic.main.activity_browser.*


class BrowserActivity : AbstractScreenActivity<BrowserPresenter>(), BrowserView {

    @InjectPresenter
    lateinit var presenter: BrowserPresenter

    @ProvidePresenter
    fun providePresenter(): BrowserPresenter {
        return presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiwindowBrowserApplication.applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)

        // Initialization pieces-containing fragment
        val fm = fragmentManager
        val savedFragment = fm.findFragmentByTag("pieces") as SpacePiecesFragment?
        val spacePiecesFragment: SpacePiecesFragment
        if (savedFragment == null) {
            spacePiecesFragment = SpacePiecesFragment();
            fm.beginTransaction().add(R.id.browserSpaceRoot, spacePiecesFragment, "pieces").commit()
        } else {
            spacePiecesFragment = savedFragment
        }
        presenter.spacePiecesUIHandler = spacePiecesFragment
        //

        addPieceButton.setOnClickListener { presenter.addNewPiece() }
        removePieceButton.setOnClickListener { presenter.removeLastPiece() }

        browserSpaceRoot.doIfSeveralFingersTouch(4, {
            toolbar.visibility = if(toolbar.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        })

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
//        spacePiecesUIHandler.browserSpace = browserSpace
//        spacePiecesUIHandler.setPageCompletedCallback {
//            //TODO add time comparing. Don't do more than 1 screenshot by 30-60 sec
//            makePrintScreenForSave()
//        }
//        spacePiecesUIHandler.setNavigatedToNewUrlCallback { browserPiece, url ->
//            //TODO check what's going on in presenter !'
//            presenter.navigatedToUrl(browserPiece, url)
//        }
//        spacePiecesUIHandler.setGoToUrlOrSearchCallback { browserPiece, url ->
//            //TODO check what's going on in presenter !'
//            presenter.goToUrlOrSearch(browserPiece, url)
//        }
    }

    override fun removePiece(browserPiece: BrowserPiece) {
//        spacePiecesUIHandler.removePiece(browserPiece)
    }

    override fun addPiece(browserPiece: BrowserPiece) {
//        spacePiecesUIHandler.addPiece(browserPiece)
    }

    override fun goToUrl(piece: BrowserPiece, url: String) {
//        spacePiecesUIHandler.goToNewUrl(piece, url)
    }

    override fun makePrintScreenForSave() {
        browserSpaceRoot.setDrawingCacheEnabled(true)
        browserSpaceRoot.buildDrawingCache()
        val bm = browserSpaceRoot.getDrawingCache()
        presenter.saveSpacePreviewImage(bm)
    }

    override fun onBackPressed() {
        if(!presenter.tryGoBackOnActivePiece()) {
            super.onBackPressed()
        }
    }

    companion object {
        const val BROWSER_SPACE_ID_KEY = "spaceIdKey"
    }
}
