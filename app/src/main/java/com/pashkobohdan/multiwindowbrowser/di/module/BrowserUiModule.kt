package com.pashkobohdan.multiwindowbrowser.di.module

import android.webkit.WebChromeClient
import com.pashkobohdan.multiwindowbrowser.ui.pieceCreator.webClient.DefaultWebClient
import dagger.Module
import dagger.Provides

@Module
class BrowserUiModule {

    @Provides
    fun providesWebChromeClient(): WebChromeClient {
        return DefaultWebClient()
    }
}