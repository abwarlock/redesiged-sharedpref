package com.abdev.pref

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import java.lang.reflect.Type

class GlobalData private constructor(private var context: ContextInterface) {

    companion object {
        private var instance: GlobalData? = null

        fun CreateInstance(contextParam: ContextInterface) {
            if (instance == null) {
                instance = GlobalData(contextParam)
            }
        }

        fun getInstance(): GlobalData {
            if (instance !is GlobalData) {
                throw RuntimeException("Instance not created, call GlobalData.createInstance on app create")
            }
            return instance as GlobalData
        }
    }


    fun <T> put(key: String, data: T) {
        Gson().toJson(data).apply {
            putValue(key, this)
        }
    }

    fun <T> get(key: String, type: Type): T? {
        val json = getString(key, "")
        if (TextUtils.isEmpty(json)) {
            return null
        }
        val gson = Gson()
        return gson.fromJson<T>(json, type)
    }

    private fun getSharedPref() =
        context.getContext().getSharedPreferences("LocalData", Context.MODE_PRIVATE)

    private fun putValue(key: String, value: String) {
        getSharedPref().edit().apply {
            if (!TextUtils.isEmpty(value)) {
                putString(key, value).apply()
            }
        }
    }

    fun getString(key: String, defValue: String): String? {
        getSharedPref().apply {
            if (contains(key)) {
                getString(key, "").let { value ->
                    if (!TextUtils.isEmpty(value)) {
                        return value
                    }
                }
            }
        }
        return defValue
    }


    fun setString(key: String, value: String) {
        putValue(key, value)
    }

    fun setInt(key: String, value: Int) {
        putValue(key, Integer.toString(value))
    }

    fun setBoolean(key: String, value: Boolean) {
        putValue(key, java.lang.Boolean.toString(value))
    }

    fun setFloat(key: String, value: Float) {
        putValue(key, java.lang.Float.toString(value))
    }

    fun setLong(key: String, value: Long) {
        putValue(key, java.lang.Long.toString(value))
    }


    fun getString(key: String): String? {
        return getString(key, "")
    }

    fun getInt(key: String): Int {
        return getInt(key, 0)
    }

    fun getInt(key: String, defValue: Int): Int {
        val sharedPreferences = getSharedPref()
        if (containsKey(key)) {
            val securedEncodedValue = sharedPreferences.getString(key, "")
            if (!TextUtils.isEmpty(securedEncodedValue))
                return Integer.parseInt(securedEncodedValue!!)
        }
        return defValue
    }

    fun getBoolean(key: String): Boolean {
        return getBoolean(key, false)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        val sharedPreferences = getSharedPref()
        if (containsKey(key)) {
            val securedEncodedValue = sharedPreferences.getString(key, "")
            if (!TextUtils.isEmpty(securedEncodedValue))
                return java.lang.Boolean.parseBoolean(securedEncodedValue)
        }
        return defValue
    }


    fun getFloat(key: String): Float {
        val sharedPreferences = getSharedPref()
        if (containsKey(key)) {
            val securedEncodedValue = sharedPreferences.getString(key, "")
            if (!TextUtils.isEmpty(securedEncodedValue))
                return java.lang.Float.parseFloat(securedEncodedValue!!)
        }
        return 0f
    }

    fun getLong(key: String): Float {
        return getLong(key, 0)
    }

    fun getLong(key: String, defValue: Long): Float {
        val sharedPreferences = getSharedPref()
        if (containsKey(key)) {
            val securedEncodedValue = sharedPreferences.getString(key, "")
            if (!TextUtils.isEmpty(securedEncodedValue))
                return java.lang.Long.parseLong(securedEncodedValue!!).toFloat()
        }
        return defValue.toFloat()
    }

    fun containsKey(key: String): Boolean {
        val sharedPreferences = getSharedPref()
        return sharedPreferences.contains(key)
    }


    fun clearData() =
        getSharedPref().edit().clear().apply()

}