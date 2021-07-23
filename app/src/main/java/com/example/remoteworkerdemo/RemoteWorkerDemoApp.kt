package com.example.remoteworkerdemo

import android.app.Application
import androidx.work.Configuration

class RemoteWorkerDemoApp  : Application(), Configuration.Provider {

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setDefaultProcessName("com.example.remoteworkerdemo")
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
}
