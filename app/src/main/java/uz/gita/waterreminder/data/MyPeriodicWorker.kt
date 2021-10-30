package uz.gita.waterreminder.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import uz.gita.waterreminder.R
import uz.gita.waterreminder.domain.BaseRepository
import uz.gita.waterreminder.domain.IntroRepository
import java.util.*

class MyPeriodicWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    private val introRepository = IntroRepository()
    private val baseRepository = BaseRepository()

    override fun doWork(): Result {

        if (baseRepository.isNotificationEnabled) {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)

            if (hour > introRepository.getWakeUpTimeHourInt() + 1 && hour < introRepository.getBedTimeHourInt() - 1) {
                setNotification(
                    inputData.getInt("id", 200),
                    inputData.getString("title") as String
                )
            }
        }
        return Result.success()
    }

    private fun setNotification(id: Int, title: String) {
        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "workmanager",
                "Work Manager",
                NotificationManager.IMPORTANCE_HIGH
            )

            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, "workmanager")
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)

        manager.notify(id, builder.build())
    }
}
