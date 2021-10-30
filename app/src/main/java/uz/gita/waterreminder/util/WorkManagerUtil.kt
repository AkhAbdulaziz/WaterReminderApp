package uz.gita.waterreminder.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.*
import uz.gita.waterreminder.app.App
import uz.gita.waterreminder.data.MyPeriodicWorker
import java.util.*
import java.util.concurrent.TimeUnit

fun cancelRequest(id: String) {
    WorkManager.getInstance(App.instance)
        .cancelWorkById(UUID.fromString(id))
}

fun createPeriodicWorkRequest(interval: Long, notificationId: Int): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel("T", "Demo", NotificationManager.IMPORTANCE_DEFAULT)
        val manager =
            App.instance.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    val constraints = Constraints.Builder()
        .setRequiresCharging(false)
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .setRequiresBatteryNotLow(true)
        .build()

    val data = Data.Builder()
    data.putInt("id", notificationId)
    data.putString("title", "Hi, please drink water!")

    val periodicWorkRequest = PeriodicWorkRequest.Builder(
        MyPeriodicWorker::class.java, interval, TimeUnit.MINUTES
    )
        .setConstraints(constraints)
        .setInputData(data.build())
        .build()

    WorkManager.getInstance(App.instance).enqueue(periodicWorkRequest)

    return periodicWorkRequest.id.toString()
}
