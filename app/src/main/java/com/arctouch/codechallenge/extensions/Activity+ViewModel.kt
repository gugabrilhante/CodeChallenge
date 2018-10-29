package com.arctouch.codechallenge.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel() = ViewModelProviders.of(this).get(T::class.java)
