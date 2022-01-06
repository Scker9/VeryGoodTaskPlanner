package com.example.verygoodtaskplanner.presentation.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.verygoodtaskplanner.R
import com.example.verygoodtaskplanner.Screens
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val navigatorHolder by inject<NavigatorHolder>()
    private val router by inject<Router>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigator = AppNavigator(this, R.id.mainContainer)
        navigatorHolder.setNavigator(navigator)
        router.newRootScreen(Screens.getCalendarWithTasksScreen())
    }
}