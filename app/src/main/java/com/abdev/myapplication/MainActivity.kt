package com.abdev.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.abdev.pref.ContextInterface
import com.abdev.pref.GlobalData
import com.abdev.pref.GlobalKey

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlobalData.CreateInstance(object : ContextInterface {
            override fun getContext() = applicationContext
        })
        GlobalData.getInstance().getString(GlobalKey.Key, "")

    }
}
