package com.arctouch.codechallenge.extensions

import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}