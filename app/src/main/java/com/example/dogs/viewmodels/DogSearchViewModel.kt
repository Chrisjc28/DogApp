package com.example.dogs.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogs.extensions.safeDispose
import com.example.dogs.extensions.setDefaultSchedulers
import com.example.dogs.services.DogService
import io.reactivex.rxjava3.disposables.Disposable

class DogSearchViewModel(private val dogService: DogService): ViewModel() {

    private var disposable: Disposable? = null

    private val _dogSearchResult = MutableLiveData<List<String>>()

    val dogSearchResult: LiveData<List<String>>
        get() = _dogSearchResult

    fun fetchDogByBreed(breed: String) {
        disposable =
            dogService.fetchBreed(breed).setDefaultSchedulers().subscribe({ specificBreedResponse ->
                _dogSearchResult.postValue(specificBreedResponse.message)
            }, {
                Log.i("Test", it.localizedMessage)
            })
    }


    override fun onCleared() {
        super.onCleared()
        _dogSearchResult.value = null
        disposable?.safeDispose()
    }
}