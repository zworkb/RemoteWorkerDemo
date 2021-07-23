package com.example.remoteworkerdemo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.work.WorkerParameters;
import androidx.work.multiprocess.RemoteListenableWorker;

import com.google.common.util.concurrent.ListenableFuture;


public class RemoteSampleWorker extends RemoteListenableWorker implements Runnable {
    Thread pythonThread = null;
    CallbackToFutureAdapter.Completer workCompleter = null;

    public RemoteSampleWorker(Context context, WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startRemoteWork() {
        return CallbackToFutureAdapter.getFuture(completer -> {
            workCompleter = completer;
            pythonThread = new Thread(this);
            pythonThread.start();
            Log.d("RemoteSampleWorker", "thread started");
            pythonThread.join();
            Log.d("RemoteSampleWorker", "thread finished");
            return "RemoteSampleWorker started";
        });
    }

    @Override
    public void run() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            Log.d("SampleWorker", "interruped: " + e);
        }
        Log.d("RemoteSampleWorker", "HOHOHO");
        workCompleter.set(Result.success());
    }
}
