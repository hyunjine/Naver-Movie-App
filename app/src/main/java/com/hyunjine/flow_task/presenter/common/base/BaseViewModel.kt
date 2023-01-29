package com.hyunjine.flow_task.presenter.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyunjine.flow_task.presenter.common.base.util.Event
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

open class BaseViewModel<T> : ViewModel() {
    companion object {
        @JvmStatic
        protected val IO_SCHEDULER: Scheduler = Schedulers.io()
        @JvmStatic
        protected val SINGLE_SCHEDULER: Scheduler = Schedulers.single()
        @JvmStatic
        protected val UI_SCHEDULER: Scheduler = AndroidSchedulers.mainThread()
        protected val NETWORK_TIME_OUT_MILLISECONDS: Long = 8000L
    }

    private val compositeDisposable = CompositeDisposable()

    protected val methodName: String get() = Throwable().stackTrace[1].methodName

    private val _state = MutableLiveData<Event<T>>()
    val state: LiveData<Event<T>> get() = _state

    protected fun setState(state: T) { _state.value = Event(state) }
    protected fun postState(state: T) { _state.postValue(Event(state)) }

    protected open fun<T> runAsync(caller: String, receiver: Single<T>, scheduler: Scheduler = IO_SCHEDULER): Single<T> = receiver
        .subscribeOn(scheduler)
        .observeOn(UI_SCHEDULER)
        .timeout(NETWORK_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)

    protected open fun runAsync(caller: String, receiver: Completable, scheduler: Scheduler = IO_SCHEDULER): Completable = receiver
        .subscribeOn(scheduler)
        .observeOn(UI_SCHEDULER)
        .timeout(NETWORK_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)


    protected fun Disposable.addDispose() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}