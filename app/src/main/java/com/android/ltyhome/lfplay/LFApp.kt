package com.android.ltyhome.lfplay

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.tencent.bugly.beta.Beta

/**
 *创建日期：18-6-5 on 上午9:56
 *描述：
 *作者：ltyhome
 *邮箱：ltyhome@yahoo.com.hk
 */
class LFApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        Beta.installTinker()
    }
}