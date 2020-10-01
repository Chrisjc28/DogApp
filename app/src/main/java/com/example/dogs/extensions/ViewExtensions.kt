package com.example.dogs.extensions

import android.view.View

fun View.visible() {
    this.apply {
        visibility = View.VISIBLE
    }
}

fun View.gone() {
    this.apply {
        visibility = View.GONE
    }
}