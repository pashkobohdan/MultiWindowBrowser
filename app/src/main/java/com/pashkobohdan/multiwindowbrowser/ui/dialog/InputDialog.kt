package com.pashkobohdan.multiwindowbrowser.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.pashkobohdan.multiwindowbrowser.R
import kotlinx.android.synthetic.main.dialog_input.view.*



class InputDialog
private constructor(context: Context,
        var editCallback: (String) -> Unit = {},
                    var title: String? = null,
                    var text: String? = null): Dialog(context) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_input, null)
        view.input.setText(text)
        setContentView(view)

    }

    override fun show() {

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_input, null)
        view.input.setText(text)
        builder.setView(view)

        builder.setPositiveButton(android.R.string.ok, { dialog, which ->
            dialog.dismiss()
            editCallback(view.input.text.toString())
        })
        builder.setNegativeButton(android.R.string.cancel, { dialog, which -> dialog.cancel() })

        builder.show()
    }

    class Builder(val context: Context) {

        private var editCallback: (String) -> Unit = {}
        private var title: String? = null
        private var text: String? = null

        fun setEditCallback(editCallback: (String) -> Unit): Builder {
            this.editCallback = editCallback
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setText(text: String): Builder {
            this.text = text
            return this
        }

        fun build(): InputDialog {
            val validTitle = title ?: throw IllegalArgumentException("Set title")
            val validText = text ?: throw IllegalArgumentException("Set text")
            return InputDialog(context, editCallback, validTitle, validText)
        }
    }
}