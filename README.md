# Minimal demo for starting a RemoteWorker in Android

this demo app starts a RemoteWorker in a separate service process

## necessary steps done in this project

- custom application class RemoteWorkerDemoApp to initialize WorkManager
- create a vanilla `RemoteSampleWorkerService`
- register a service for this class in AndroidManifest.xml
- created a remote worker class `RemoteSampleWorker`
- in the `MainActivity`
    - create a `OneTimeWorkRequest`
    - enqueue this request into `WorkManager`
    
## Problem

starting the worker works fine, but it is desired that the worker service process terminates
itself after the worker has finished.
But the worker service process  with the name `demo_remote_sample_worker_service` is still present,
what can be verified by checking the process list with "adb shell"