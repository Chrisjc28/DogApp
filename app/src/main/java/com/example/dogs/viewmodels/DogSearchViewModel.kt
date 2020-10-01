package com.example.dogs.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogs.extensions.SchedulerProvider
import com.example.dogs.extensions.safeDispose
import com.example.dogs.services.DogService
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

class DogSearchViewModel(
    private val dogService: DogService,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private var disposable: Disposable? = null

    private val _dogSearchResult = MutableLiveData<List<String>>()

    val dogSearchResult: LiveData<List<String>>
        get() = _dogSearchResult

    private val _error = MutableLiveData<Throwable>()

    val error: LiveData<Throwable>
        get() = _error

    fun fetchDogByBreed(breed: String?) {
        disposable =
            dogService.fetchBreed(breed)
                ?.subscribeOn(schedulerProvider.io())
                ?.observeOn(schedulerProvider.ui())
                ?.subscribe({ specificBreedResponse ->
                    _dogSearchResult.postValue(specificBreedResponse.message)
                }, {
                    _error.postValue(it)
                })
    }


    override fun onCleared() {
        super.onCleared()
        _dogSearchResult.value = null
        disposable?.safeDispose()
    }
}