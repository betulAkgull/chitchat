package com.example.chatapp.common

import android.view.View


fun View.visible() {
    visibility = View.VISIBLE
}

fun setViewsVisible(vararg views: View) {
    views.forEach { it.visible() }
}

fun View.gone() {
    visibility = View.GONE
}

fun setViewsGone(vararg views: View) {
    views.forEach { it.gone() }
}