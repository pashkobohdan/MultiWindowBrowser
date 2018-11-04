package com.pashkobohdan.multiwindowbrowser.browser.browsing

import java.util.*

class HistoryManager {

    val previousPages: Stack<Page> = Stack()

    fun goToPage(page: Page, cleanTop: Boolean = false) {
        if (cleanTop) {
            previousPages.clear()
        }
        previousPages.push(page)
    }

    fun canGoBack(): Boolean = !previousPages.isEmpty()

    fun back(): Page {
        if (!canGoBack()) throw IllegalArgumentException("Cannot go back")

        return previousPages.pop()
    }

    fun currentPage(): Page {
        val peekedElement = if(previousPages.size > 0) previousPages.peek() else throw IllegalArgumentException("History is empty")
        return peekedElement
    }
}