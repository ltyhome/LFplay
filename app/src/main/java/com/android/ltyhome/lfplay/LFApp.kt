package com.android.ltyhome.lfplay

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.text.TextUtils
import com.android.ltyhome.lfplay.utils.RealmHelper
import com.tencent.bugly.beta.Beta

/**
 *创建日期：18-6-5 on 上午9:56
 *描述：
 *作者：ltyhome
 *邮箱：ltyhome@yahoo.com.hk
 */
class LFApp : Application() {

    companion object {
        lateinit var instance:LFApp
            private set
    }


    override fun onCreate() {
        super.onCreate()
        val pic = getProcessName()
        if(!TextUtils.isEmpty(pic)&&pic==packageName)
            initApplication()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        Beta.installTinker()
    }

    private fun initApplication(){
        instance = this
    }



    private fun getProcessName():String{
        val pid = android.os.Process.myPid()
        var processName = ""
        val `object` = getSystemService(Context.ACTIVITY_SERVICE)
        if (`object` is ActivityManager) {
            for (appProcess in `object`.runningAppProcesses) {
                if (appProcess.pid == pid)
                    processName = appProcess.processName
            }
        }
        return processName
    }
}