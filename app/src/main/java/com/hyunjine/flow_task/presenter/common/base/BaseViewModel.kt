package com.hyunjine.flow_task.presenter.common.base

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

open class BaseViewModel : ViewModel() {
    companion object {
        protected val IO_SCHEDULER: Scheduler = Schedulers.io()
        protected val SINGLE_SCHEDULER: Scheduler = Schedulers.single()
        protected val UI_SCHEDULER: Scheduler = AndroidSchedulers.mainThread()
        protected val NETWORK_TIME_OUT_MILLISECONDS: Long = 8000L
    }

    private val compositeDisposable = CompositeDisposable()

    protected val methodName: String get() = Throwable().stackTrace[1].methodName

    protected open fun<T> runAsync(caller: String, receiver: Single<T>, scheduler: Scheduler = IO_SCHEDULER): Single<T> = receiver
        .subscribeOn(scheduler)
        .observeOn(UI_SCHEDULER)
        .timeout(NETWORK_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)

    protected open fun runAsync(caller: String, receiver: Completable): Completable = receiver
        .subscribeOn(IO_SCHEDULER)
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