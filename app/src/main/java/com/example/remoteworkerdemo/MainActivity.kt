package com.example.remoteworkerdemo

import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.work.*
import androidx.work.multiprocess.RemoteListenableWorker
import androidx.work.multiprocess.RemoteWorkerService

internal class RemoteSampleWorkerService : RemoteWorkerService()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onStartSampleWorkerRemote(v: View) {
        val text = "Starting local RemoteSampleWorker"
        val duration = Toast.LENGTH_SHORT
        val serviceName = RemoteSampleWorkerService::class.java.name
        val componentName = ComponentName("com.example.remoteworkerdemo", serviceName)

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()

        val oneTimeWorkRequest = buildOneTimeWorkRemoteWorkRequest(
            componentName,
            RemoteSampleWorker::class.java
        )

        val operation: Operation = WorkManager
            .getInstance(this)
            .enqueue(oneTimeWorkRequest)

        Toast.makeText(applicationContext, "operation enqueued", duration).show()
    }

    private fun buildOneTimeWorkRemoteWorkRequest(
        componentName: ComponentName
        , listenableWorkerClass: Class<out ListenableWorker>
    ): OneTimeWorkRequest {

        // ARGUMENT_PACKAGE_NAME and ARGUMENT_CLASS_NAME are used to determine the service
        // that a Worker binds to. By specifying these parameters, we can designate the process a
        // Worker runs in.
        val data: Data = Data.Builder()
            .putString(RemoteListenableWorker.ARGUMENT_PACKAGE_NAME, componentName.packageName)
            .putString(RemoteListenableWorker.ARGUMENT_CLASS_NAME, componentName.className)
            .build()

        return OneTimeWorkRequest.Builder(listenableWorkerClass)
            .setInputData(data)
            .build()
    }

}