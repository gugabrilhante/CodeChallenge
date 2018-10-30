package com.arctouch.codechallenge.base

import androidx.appcompat.app.AppCompatActivity
import com.arctouch.codechallenge.api.ServerApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class BaseActivity : AppCompatActivity() {

    protected val api: ServerApi = Retrofit.Builder()
        .baseUrl(ServerApi.URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ServerApi::class.java)
}
