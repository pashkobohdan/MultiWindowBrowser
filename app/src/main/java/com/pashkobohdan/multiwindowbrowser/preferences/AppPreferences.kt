package com.pashkobohdan.multiwindowbrowser.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Inject

class AppPreferences @Inject constructor() {

    @Inject
    lateinit var context: Context

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    var spaceCount: Int = 0
        set(value) {
            preferences.edit().putInt(SPACE_COUNT_KEY, value).apply()
            field = value
        }
        get() {
            if (field == 0) {
                field = preferences.getInt(SPACE_COUNT_KEY, 0)
            }
            return field
        }

    var lastOpenSpaceId: Long = 0
        set(value) {
            preferences.edit().putLong(LAST_OPEN_SPACE_ID, value).apply()
            field = value
        }
        get() {
            if (field == 0L) {
                field = preferences.getLong(LAST_OPEN_SPACE_ID, 0L)
            }
            return field
        }

    companion object {
        const val SPACE_COUNT_KEY = "spaceCountKey"
        const val LAST_OPEN_SPACE_ID = "lastSpaceId"
    }
}