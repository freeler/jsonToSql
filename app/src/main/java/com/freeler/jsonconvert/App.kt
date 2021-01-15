package com.freeler.jsonconvert


import android.app.Application
import org.litepal.LitePal

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //数据库
        LitePal.initialize(this)
    }

}
