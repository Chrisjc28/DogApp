package com.example.dogs.extensions

import io.reactivex.rxjava3.disposables.Disposable

fun Disposable?.safeDispose() {
    if (this != null && !this.isDisposed) {
        this.dispose()
    }
}