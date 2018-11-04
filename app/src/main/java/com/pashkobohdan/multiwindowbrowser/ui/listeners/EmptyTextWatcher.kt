package com.pashkobohdan.multiwindowbrowser.ui.listeners

import android.text.Editable
import android.text.TextWatcher

open class EmptyTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
//nop
   }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//nop
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//nop
    }
}