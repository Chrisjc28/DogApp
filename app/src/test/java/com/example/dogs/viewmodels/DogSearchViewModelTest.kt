package com.example.dogs.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.dogs.extensions.TrampolineSchedulerProviderImpl
import com.example.dogs.models.BreedResponse
import com.example.dogs.services.DogService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4





@RunWith(JUnit4::class)
class DogSearchViewModelTest {

    @Rule
    @JvmField
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun givenTheBreedIsNullWhenWeCallTheDogServiceToFetchTheInformationThenAErrorIsReturned() {
        val breed = null

        val testSchedulerProvider = TrampolineSchedulerProviderImpl()

        val mockDogService: DogService = mock()
        val mockDogBreedObserver: Observer<List<String>> = mock()

        val dogSearchViewModel = DogSearchViewModel(mockDogService, testSchedulerProvider)

        dogSearchViewModel.dogSearchResult.observeForever(mockDogBreedObserver)

        whenever(mockDogService.fetchBreed(breed)).thenReturn(null)

        dogSearchViewModel.fetchDogByBreed(breed)

        assertNotNull(dogSearchViewModel.fetchDogByBreed(breed))
        assertTrue(dogSearchViewModel.dogSearchResult.hasObservers())
    }


    @Test
    fun givenTheBreedIsEmptyWhenWeCallTheDogServiceToFetchTheInformationThenAErrorIsReturned() {
        val breed = ""

        val testSchedulerProvider = TrampolineSchedulerProviderImpl()

        val throwable = Throwable("There was an error loading the dog information")

        val mockDogService: DogService = mock()
        val mockDogBreedObserver: Observer<List<String>> = mock()
        val mockErrorDogBreedObserver: Observer<Throwable> = mock()

        val dogSearchViewModel = DogSearchViewModel(mockDogService, testSchedulerProvider)

        dogSearchViewModel.dogSearchResult.observeForever(mockDogBreedObserver)
        dogSearchViewModel.error.observeForever(mockErrorDogBreedObserver)

        whenever(mockDogService.fetchBreed(breed)).thenReturn(Single.error(throwable))

        dogSearchViewModel.fetchDogByBreed(breed)

        verify(mockErrorDogBreedObserver).onChanged(throwable)

    }


    @Test
    fun givenTheBreedIsNotEmptyWhenWeCallTheDogServiceToFetchTheInformationThenASpecificBreedResponseIsReturned() {
        val breed = "pug"

        val testSchedulerProvider = TrampolineSchedulerProviderImpl()

        val mockDogService: DogService = mock()
        val mockDogBreedObserver: Observer<List<String>> = mock()

        val listOfBreeds = listOf(
            "pug_image_1",
            "pug_image_2",
            "pug_image_3"
        )

        val dogSearchViewModel = DogSearchViewModel(mockDogService, testSchedulerProvider)

        dogSearchViewModel.dogSearchResult.observeForever(mockDogBreedObserver)

        whenever(mockDogService.fetchBreed(breed)).thenReturn(Single.just(BreedResponse(
            listOfBreeds, "successful")))

        dogSearchViewModel.fetchDogByBreed(breed)

        verify(mockDogBreedObserver).onChanged(listOfBreeds)
    }

    @Test
    fun givenWeHaveRequestedRandomDogWhenWeCallTheDogServiceToFetchRandomDogInformationThenARandomBreedResponseIsReturned() {

        val testSchedulerProvider = TrampolineSchedulerProviderImpl()

        val mockDogService: DogService = mock()
        val mockDogBreedObserver: Observer<List<String>> = mock()

        val listOfBreeds = listOf(
            "pug_image_1",
            "dane_image_2",
            "spaniel_image_3"
        )

        val dogSearchViewModel = DogSearchViewModel(mockDogService, testSchedulerProvider)

        dogSearchViewModel.dogRandomSearchResult.observeForever(mockDogBreedObserver)

        whenever(mockDogService.getRandomDogs()).thenReturn(Single.just(BreedResponse(
            listOfBreeds, "successful")))

        dogSearchViewModel.fetchRandomDogs()

        verify(mockDogBreedObserver).onChanged(listOfBreeds)
    }



}