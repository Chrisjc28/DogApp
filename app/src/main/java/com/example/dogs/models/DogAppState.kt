package com.example.dogs.models

sealed class DogAppState {
    class Loading()
    class Error(val error: String)
}