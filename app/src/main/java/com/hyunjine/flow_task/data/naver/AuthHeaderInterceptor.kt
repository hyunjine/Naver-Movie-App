package com.hyunjine.flow_task.data.naver

import com.hyunjine.flow_task.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("X-Naver-Client-Id", BuildConfig.NAVER_SEARCH_CLIENT_ID)
            .addHeader("X-Naver-X-Naver-Client-Secret-Id", BuildConfig.NAVER_SEARCH_CLIENT_SECRET)
            .build()

        return chain.proceed(newRequest)
    }
}