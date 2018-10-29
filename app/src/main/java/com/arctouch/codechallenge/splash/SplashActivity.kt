package com.arctouch.codechallenge.splash

import android.content.Intent
import android.os.Bundle
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.ServerApi
import com.arctouch.codechallenge.base.BaseActivity
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.modules.home.HomeActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        api.genres(ServerApi.API_KEY, ServerApi.DEFAULT_LANGUAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Cache.cacheGenres(it.genres)
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
    }
}
