package com.hyunjine.flow_task.presenter.common.base.util

import androidx.lifecycle.MutableLiveData

open class ListLiveData<T> : MutableLiveData<MutableList<T>>() {
    init { value = arrayListOf() }

    fun add(item: T) {
        value = value!!.apply { add(item) }
    }

    fun addAll(list: List<T>) {
        postValue(value!!.apply { addAll(list) })
//        value = value!!.apply { addAll(list) }
    }

    fun clear() {
        value!!.clear()
    }
}