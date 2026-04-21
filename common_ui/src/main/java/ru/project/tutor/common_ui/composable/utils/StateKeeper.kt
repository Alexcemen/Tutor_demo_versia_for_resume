package ru.project.tutor.common_ui.composable.utils

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import com.google.gson.Gson
import java.lang.reflect.Type


class StateKeeperImpl() : StateKeeper {
    init {
        Log.d("StateKeeper", "New instance created: ${this.hashCode()}")
    }
    private var _initialState: Bundle = bundleOf()

    private val gson = Gson()

    override fun <T> setObject(key: String, value: T) {
        val json = gson.toJson(value)
        _initialState.putString(key, json)
    }

    override fun <T> getObject(key: String, clazz: Class<T>): T? {
        return _initialState.getString(key)?.let { gson.fromJson(it, clazz) }
    }

    override fun <T> getObject(key: String, type: Type): T? {
        return _initialState.getString(key)?.let { gson.fromJson(it, type) }
    }

    override fun register(bundle: Bundle) {
        _initialState.putAll(bundle)
    }

    override fun update(bundle: Bundle) {
        _initialState = bundle
    }

    override fun set(key: String, value: String) {
        _initialState.putString(key, value)
    }

    override fun set(key: String, value: Int) {
        _initialState.putInt(key, value)
    }

    override fun getInt(key: String): Int? {
        return _initialState.getInt(key).takeIf { it != 0 }
    }

    override fun getAll(): Bundle = _initialState

    override fun getString(key: String): String {
        return _initialState.getString(key).orEmpty()
    }

}

interface StateKeeper {
    fun register(bundle: Bundle)
    fun update(bundle: Bundle)
    fun set(key: String, value: String)
    fun set(key: String, value: Int)
    fun getInt(key: String): Int?
    fun getAll(): Bundle
    fun getString(key: String): String
    fun <T> setObject(key: String, value: T)
    fun <T> getObject(key: String, clazz: Class<T>): T?
    fun <T> getObject(key: String, type: Type): T?
}