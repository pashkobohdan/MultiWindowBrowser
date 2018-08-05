package com.pashkobohdan.multiwindowbrowser.browser.browsing

import java.util.*

class HistoryManager {

    val previousPages: Stack<Page> = Stack()

    fun goToPage(page: Page, cleanTop: Boolean = false) {
        if (cleanTop) {
            previousPages.clear()
        }
        previousPages.add(page)
    }

    fun canGoBack(): Boolean = !previousPages.isEmpty()

    fun back(): Page {
        if (!canGoBack()) throw IllegalArgumentException("Cannot go back")

        return previousPages.pop()
    }
}