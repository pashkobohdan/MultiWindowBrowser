package com.pashkobohdan.multiwindowbrowser.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import com.pashkobohdan.multiwindowbrowser.R
import kotlinx.android.synthetic.main.dialog_new_browser_space_type.view.*

enum class NewBrowserSpaceType {
    HORIZONTAL,
    VERTICAL,
    HORIZONTAL_WITH_DEFAULT_SITES,
    VERTICAL_WITH_DEFAULT_SITES
}

class NewBrowserSpaceTypeDialog : DialogFragment() {

    var typeChosenCallback: (NewBrowserSpaceType) -> Unit = {}

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var chosenType: NewBrowserSpaceType? = null

        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_new_browser_space_type, null)

        val typeViewList = mapOf(Pair(view.verticalType, NewBrowserSpaceType.VERTICAL),
                Pair(view.horizontalType, NewBrowserSpaceType.HORIZONTAL),
                Pair(view.verticalTypeWithDefaultSites, NewBrowserSpaceType.VERTICAL_WITH_DEFAULT_SITES),
                Pair(view.horizontalTypeWithDefaultSites, NewBrowserSpaceType.HORIZONTAL_WITH_DEFAULT_SITES))
        typeViewList.forEach { typeView, type ->
            typeView.setOnClickListener { chosenTypeView ->
                chosenType = type
                typeViewList.keys.forEach { it.isSelected = false }
                chosenTypeView.isSelected = true
            }
        }

        builder.setView(view)
                .setPositiveButton("Create") { _, id ->
                    val spaceType = chosenType
                    if(spaceType == null) {
                        //TODO show alert or smth else
                    } else {
                        typeChosenCallback(spaceType)
                    }
                }
                .setNegativeButton("Cancel") { _, id -> this.dialog.cancel() }
        return builder.create()
    }
}