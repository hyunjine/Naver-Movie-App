package com.hyunjine.flow_task.presenter.common.base.util

import androidx.lifecycle.MutableLiveData

open class ListLiveData<T> : MutableLiveData<MutableList<T>>() {
    init { value = mutableListOf() }

    fun add(item: T) {
        value = value!!.apply { add(item) }
    }

    fun addAll(list: List<T>) {
        value = value!!.apply { addAll(list) }
    }

    fun clear() {
        value!!.clear()
    }
}