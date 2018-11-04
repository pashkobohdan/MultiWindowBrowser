package com.pashkobohdan.multiwindowbrowser.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.pashkobohdan.multiwindowbrowser.MultiwindowBrowserApplication
import com.pashkobohdan.multiwindowbrowser.R
import com.pashkobohdan.multiwindowbrowser.preferences.AppPreferences
import com.pashkobohdan.multiwindowbrowser.ui.activities.browserSpaceList.BrowserSpaceListActivity
import com.pashkobohdan.multiwindowbrowser.ui.activities.space.BrowserActivity
import javax.inject.Inject

class SplashActivity : Activity() {

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiwindowBrowserApplication.applicationComponent.inject(this)
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            val lastSpaceId = appPreferences.lastOpenSpaceId
            val intent = if (lastSpaceId  == 0L) {
                Intent(this, BrowserSpaceListActivity::class.java)
            } else {
                Intent(this, BrowserActivity::class.java).apply {
                    putExtra(BrowserActivity.BROWSER_SPACE_ID_KEY, lastSpaceId)
                }
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }, SPLASH_DELAY)
    }

    companion object {
        private const val SPLASH_DELAY = 500L
    }
}