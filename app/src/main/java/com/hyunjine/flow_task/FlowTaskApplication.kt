package com.hyunjine.flow_task

import android.app.Application
import com.hyunjine.flow_task.common.loggerW
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException

@HiltAndroidApp
class FlowTaskApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        setRxJava()
        val user = User("홍길동", "2023-02-22")
        user.get().name = ""
    }

    data class User(
        private var _name: String,
        private var _birth: String
    ) {
        var name: String = ""
            get() = _name
        var birth: String = ""
            get() = _birth
        fun get(): User = User(_name, _birth)
    }

    private fun setRxJava() {
        RxJavaPlugins.setErrorHandler { e ->
            var error = e
            if (error is UndeliverableException) {
                e.cause?.let { error = it }
            }
            if (error is IOException || error is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (error is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (error is NullPointerException || error is IllegalArgumentException) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler
                    ?.uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            if (error is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler
                    ?.uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            loggerW("Undeliverable exception", error)
        }
    }
}