package com.arctouch.codechallenge.extensions

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.verticalLinearLayout(context: Context) {
    val linearLayoutManager = LinearLayoutManager(context)
    linearLayoutManager.orientation = RecyclerView.VERTICAL
    this.layoutManager = linearLayoutManager
}

fun RecyclerView.reachedBottomLinearLayout(fistPosition: Int, lastPosition: Int): Boolean {
    (this.layoutManager as? LinearLayoutManager)?.let {
        return (!this.canScrollVertically(1) &&
                (it.findFirstCompletelyVisibleItemPosition() != fistPosition
                        || it.findLastCompletelyVisibleItemPosition() != lastPosition))
    } ?: return false
}