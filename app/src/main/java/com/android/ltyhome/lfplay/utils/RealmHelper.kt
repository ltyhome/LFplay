package com.android.ltyhome.lfplay.utils

import android.app.Application
import com.android.ltyhome.lfplay.utils.Constants.REALM_NAME
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.Sort
import java.lang.reflect.InvocationTargetException

/**
 *创建日期：18-6-15 on 下午2:18
 *描述：
 *作者：ltyhome
 *邮箱：ltyhome@yahoo.com.hk
 */
class RealmHelper private constructor(app:Application,realmName:String){
    private var mRealm: Realm? = null
    init {
        Realm.init(app)
        val config = RealmConfiguration.Builder().directory(app.filesDir).name(realmName).schemaVersion(0).build()
        Realm.setDefaultConfiguration(config)
        openRealm()
    }

    @Synchronized
    private fun openRealm(): Realm {
        if(null == mRealm)
            mRealm = Realm.getDefaultInstance()
        return mRealm!!
    }

    companion object {
        fun init(app: Application) {
            RealmHelper(app,REALM_NAME)
        }
    }

    /**
     *  增加单条数据到数据库中
     */
    fun add(bean:RealmObject){
        mRealm!!.executeTransaction({ realm -> realm.copyToRealm(bean) })
    }

    /**
     * 增加多条数据到数据库中
     */
    fun add(beans: List<RealmObject>) {
        mRealm!!.executeTransaction({ realm -> realm.copyToRealm(beans) })
    }

    /**
     * 增加多条数据到数据库中
     */
    fun addAsync(beans: List<RealmObject>) {
        mRealm!!.executeTransactionAsync({ realm -> realm.copyToRealm(beans) })
    }

    /**
     * 删除数据库中clazz类所属所有元素
     */
    fun deleteAll(clazz: Class<out RealmObject>) {
        val beans = mRealm!!.where(clazz).findAll()
        mRealm!!.executeTransaction({ beans.deleteAllFromRealm() })
    }

    /**
     * 删除数据库中clazz类所属所有元素
     */
    fun deleteAllAsync(clazz: Class<out RealmObject>) {
        val beans = mRealm!!.where(clazz).findAll()
        mRealm!!.executeTransactionAsync({ beans.deleteAllFromRealm() })
    }

    /**
     * 删除数据库中clazz类所属第一个元素
     */
    fun deleteFirst(clazz: Class<out RealmObject>) {
        val beans = mRealm!!.where(clazz).findAll()
        mRealm!!.executeTransaction({ beans.deleteFirstFromRealm() })
    }

    /**
     * 删除数据库中clazz类所属最后一个元素
     */
    fun deleteLast(clazz: Class<out RealmObject>) {
        val beans = mRealm!!.where(clazz).findAll()
        mRealm!!.executeTransaction({ beans.deleteLastFromRealm() })
    }

    /**
     * 删除数据库中clazz类所属数据中某一位置的元素
     */
    fun deleteElement(clazz: Class<out RealmObject>, position: Int) {
        val beans = mRealm!!.where(clazz).findAll()
        mRealm!!.executeTransaction({ beans.deleteFromRealm(position) })
    }

    /**
     * 查询数据库中clazz类所属所有数据
     */
    fun queryAll(clazz: Class<out RealmObject>): RealmResults<out RealmObject> {
        return  mRealm!!.where(clazz).findAll()
    }

    /**
     * 查询数据库中clazz类所属所有数据
     */
    fun queryAllAsync(clazz: Class<out RealmObject>): RealmResults<out RealmObject> {
        return mRealm!!.where(clazz).findAllAsync()
    }

    /**
     * 查询满足条件的第一个数据
     */
    @Throws(NoSuchFieldException::class)
    fun queryByFieldFirst(clazz: Class<out RealmObject>, fieldName: String, value: String): RealmObject {
        return mRealm!!.where(clazz).equalTo(fieldName, value).findFirst() as RealmObject
    }

    /**
     * 查询满足条件的所有数据
     */
    @Throws(NoSuchFieldException::class)
    fun queryByFieldAll(clazz: Class<out RealmObject>, fieldName: String, value: String): RealmResults<out RealmObject> {
        return mRealm!!.where(clazz).equalTo(fieldName, value).findAll()
    }

    /**
     * 查询满足条件的所有数据
     */
    @Throws(NoSuchFieldException::class)
    fun queryByFieldAllAsync(clazz: Class<out RealmObject>, fieldName: String, value: String): RealmResults<out RealmObject> {
        return mRealm!!.where(clazz).equalTo(fieldName, value).findAllAsync()
    }

    /**
     * 查询满足条件的第一个数据
     */
    @Throws(NoSuchFieldException::class)
    fun queryByFieldFirst(clazz: Class<out RealmObject>, fieldName: String, value: Int): RealmObject {
        return mRealm!!.where(clazz).equalTo(fieldName, value).findFirst() as RealmObject
    }

    /**
     * 查询满足条件的所有数据
     */
    @Throws(NoSuchFieldException::class)
    fun queryByFieldAll(clazz: Class<out RealmObject>, fieldName: String, value: Int): RealmResults<out RealmObject> {
        return mRealm!!.where(clazz).equalTo(fieldName, value).findAll()
    }

    /**
     * 查询满足条件的所有数据
     */
    @Throws(NoSuchFieldException::class)
    fun queryByFieldAllAsync(clazz: Class<out RealmObject>, fieldName: String, value: Int): RealmResults<out RealmObject> {
        return mRealm!!.where(clazz).equalTo(fieldName, value).findAllAsync()
    }

    /**
     * 查询数据，按增量排序
     */
    fun queryAllByAscending(clazz: Class<out RealmObject>, fieldName: String): List<RealmObject> {
        val beans = mRealm!!.where(clazz).findAll()
        val results = beans.sort(fieldName, Sort.ASCENDING)
        return mRealm!!.copyFromRealm(results)
    }

    /**
     * 查询数据，按降量排序
     */
    fun queryAllByDescending(clazz: Class<out RealmObject>, fieldName: String): List<RealmObject> {
        val beans = mRealm!!.where(clazz).findAll()
        val results = beans.sort(fieldName, Sort.DESCENDING)
        return mRealm!!.copyFromRealm(results)
    }

    /**
     * 更新满足某个条件的第一个数据的属性值
     */
    @Throws(NoSuchMethodException::class, InvocationTargetException::class, IllegalAccessException::class)
    fun updateFirstByField(clazz: Class<out RealmObject>, fieldName: String, oldValue: String, newValue: String) {
        val bean = mRealm!!.where(clazz).equalTo(fieldName, oldValue).findFirst()
        mRealm!!.beginTransaction()
        val method = clazz.getMethod(fieldName, String::class.java)
        method.invoke(bean, newValue)
        mRealm!!.commitTransaction()
    }

    /**
     * 更新满足某个条件的第一个数据的属性值
     */
    @Throws(NoSuchMethodException::class, InvocationTargetException::class, IllegalAccessException::class)
    fun updateFirstByField(clazz: Class<out RealmObject>, fieldName: String, oldValue: Int, newValue: Int) {
        val bean = mRealm!!.where(clazz).equalTo(fieldName, oldValue).findFirst()
        mRealm!!.beginTransaction()
        val method = clazz.getMethod(fieldName, Int::class.javaPrimitiveType)
        method.invoke(bean, newValue)
        mRealm!!.commitTransaction()
    }

    /**
     * 更新满足某个条件的第一个数据的属性值
     */
    @Throws(NoSuchMethodException::class, InvocationTargetException::class, IllegalAccessException::class)
    fun updateAllByField(clazz: Class<out RealmObject>, fieldName: String, oldValue: String, newValue: String) {
        val beans = mRealm!!.where(clazz).equalTo(fieldName, oldValue).findAll()
        mRealm!!.beginTransaction()
        val method = clazz.getMethod(fieldName, String::class.java)
        for (i in beans.indices) {
            val realmObject = beans.get(i)
            method.invoke(realmObject, newValue)
        }
        mRealm!!.commitTransaction()
    }

    /**
     * 更新满足某个条件的第一个数据的属性值
     */
    @Throws(NoSuchMethodException::class, InvocationTargetException::class, IllegalAccessException::class)
    fun updateAllByField(clazz: Class<out RealmObject>, fieldName: String, oldValue: Int, newValue: Int) {
        val beans = mRealm!!.where(clazz).equalTo(fieldName, oldValue).findAll()
        mRealm!!.beginTransaction()
        val method = clazz.getMethod(fieldName, Int::class.javaPrimitiveType)
        for (i in beans.indices) {
            val realmObject = beans.get(i)
            method.invoke(realmObject, newValue)
        }
        mRealm!!.commitTransaction()
    }
}