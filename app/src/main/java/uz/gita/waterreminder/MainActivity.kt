package uz.gita.waterreminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//
//        val navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
//        val graph = navHost.navController.navInflater.inflate(R.navigation.navigation_app)
//        navHost.navController.graph = graph
    }
}

