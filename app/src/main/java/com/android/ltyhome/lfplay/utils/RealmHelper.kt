package com.android.ltyhome.lfplay.utils

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 *创建日期：18-6-15 on 下午2:18
 *描述：
 *作者：ltyhome
 *邮箱：ltyhome@yahoo.com.hk
 */
class RealmHelper private constructor(app:Application,realmName:String){
    init {
        Realm.init(app)
        val config = RealmConfiguration.Builder().directory(app.filesDir).name(realmName).schemaVersion(0).build()
        Realm.setDefaultConfiguration(config)
    }
    companion object {
        fun get():Realm{
            return Realm.getDefaultInstance()
        }
    }
}