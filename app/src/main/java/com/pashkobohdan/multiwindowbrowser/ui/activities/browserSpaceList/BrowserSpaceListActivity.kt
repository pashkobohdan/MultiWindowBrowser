package com.pashkobohdan.multiwindowbrowser.ui.activities.browserSpaceList

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.pashkobohdan.multiwindowbrowser.MultiwindowBrowserApplication
import com.pashkobohdan.multiwindowbrowser.R
import com.pashkobohdan.multiwindowbrowser.database.dto.BrowserSpaceDTO
import com.pashkobohdan.multiwindowbrowser.mvp.browserSpaceList.BrowserSpaceListPresenter
import com.pashkobohdan.multiwindowbrowser.mvp.browserSpaceList.view.BrowserSpaceListView
import com.pashkobohdan.multiwindowbrowser.ui.activities.browserSpaceList.widget.BrowserSpaceWidget
import com.pashkobohdan.multiwindowbrowser.ui.activities.space.BrowserActivity
import com.pashkobohdan.multiwindowbrowser.ui.activities.space.BrowserActivity.Companion.BROWSER_SPACE_ID_KEY
import com.pashkobohdan.multiwindowbrowser.ui.common.activity.AbstractListScreenActivity
import com.pashkobohdan.multiwindowbrowser.ui.common.list.AbstractListItemHolder
import com.pashkobohdan.multiwindowbrowser.ui.dialog.InputDialog
import com.pashkobohdan.multiwindowbrowser.ui.dialog.NewBrowserSpaceTypeDialog
import kotlinx.android.synthetic.main.activity_browser_space_list.*
import javax.inject.Inject
import javax.inject.Provider

class BrowserSpaceListActivity : AbstractListScreenActivity<BrowserSpaceListPresenter, BrowserSpaceDTO>(), BrowserSpaceListView {

    @InjectPresenter
    lateinit var presenter: BrowserSpaceListPresenter
    @Inject
    lateinit var widgetProvider: Provider<BrowserSpaceWidget>

    @ProvidePresenter
    fun createPresenter() = presenterProvider.get()

    var adapter = ListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiwindowBrowserApplication.applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser_space_list)

        addAnotherOneSpace.setOnClickListener { presenter.addAnotherOneSpace() }

        browserSpaceList.setHasFixedSize(true)
        browserSpaceList.layoutManager = LinearLayoutManager(this)
        browserSpaceList.adapter = adapter
    }

    override fun getItemHolder(parent: ViewGroup): AbstractListItemHolder<BrowserSpaceDTO> {
        return widgetProvider.get().getHolder(parent, {
            presenter.goToBrowserSpace(it)
        }, { space ->
            InputDialog.Builder(this)
                    .setTitle("Set space name")
                    .setText(space.spaceName)
                    .setEditCallback {
                        presenter.renameSpace(space, it)
                    }
                    .build()
                    .show()
        })
    }

    override fun showSpaces(spaceList: List<BrowserSpaceDTO>) {
        adapter.dataList = spaceList.toMutableList()
    }

    override fun addSpace(space: BrowserSpaceDTO) {
        adapter.addItems(space)
    }

    override fun showProgress() {
        progressContainer.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressContainer.visibility = View.GONE
    }

    override fun showNewBrowserTypeDialog() {
        val dialog = NewBrowserSpaceTypeDialog()
        dialog.typeChosenCallback = {
            presenter.saveAnotherOneSpaceAndOpen(it)
        }
        dialog.show(fragmentManager, "NewBrowserSpaceTypeDialog")
    }

    override fun goToBrowserSpaceWithId(id: Long) {
        val intent = Intent(this, BrowserActivity::class.java)
        intent.putExtra(BROWSER_SPACE_ID_KEY, id)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}