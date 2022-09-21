package com.example.app

import android.app.Application
import com.example.library.Tool

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Tool().helloTool()
    }

}