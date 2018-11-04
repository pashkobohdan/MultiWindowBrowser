package com.pashkobohdan.multiwindowbrowser.browser.utils

import android.util.Patterns

private const val VALID_LINK_START = "http"
private const val LINK_PREFIX = "http://"

fun String.getValidUrlOrGoogleSearch(): String {
    return if (Patterns.WEB_URL.toRegex().matches(this)) {
        if(this.startsWith(VALID_LINK_START)) this else LINK_PREFIX + this
    } else {
        "https://www.google.com/search?q=" + this
    }
}