package com.example.dogs.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.dogs.R
import com.example.dogs.adapters.DogResultAdapter
import com.example.dogs.extensions.hideKeyboard
import com.example.dogs.models.Breeds
import com.example.dogs.network.Network
import com.example.dogs.viewmodels.DogSearchViewModel

class HomeActivity : AppCompatActivity() {

    private val dogSearch: EditText by lazy {
        findViewById<EditText>(R.id.dog_search_edit_text)
    }

    private val searchBtn: Button by lazy {
        findViewById<Button>(R.id.dog_search_button)
    }

    private val dogPager: ViewPager2 by lazy {
        findViewById<ViewPager2>(R.id.dog_view_page)
    }

    private val dogSearchViewModel: DogSearchViewModel = DogSearchViewModel(Network.dogService)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.setHomeButtonEnabled(true)

        dogSearchViewModel.dogSearchResult.observe(this, Observer {
            if (it != null) {
                dogPager.adapter = DogResultAdapter(it)
            }
        })

        searchBtn.setOnClickListener {

            hideKeyboard()

            val searchText = performBasicValidationEditText(dogSearch)

            if (searchText.isEmpty()) {
                Log.i("Test", "Empty search")
            } else {
                if (Breeds.listOfBreeds.contains(searchText)) {
                    dogSearchViewModel.fetchDogByBreed(searchText)
                } else {
                    Log.i("Test", "Sorry the search for dog can not be found")
                }
            }

            //TODO: handle if not text have been entered into the search box but the user clicks search
            //TODO: Perform api call to check if search for dog is in the the list of dogs in the api
            //TODO: If the dog is in the list load up the dogs information on the screen in some UI
            //TODO: if the dog searched for is not in the list show an error
            //TODO: create basic sealed class to represent an success state and error state
        }

    }

    private fun performBasicValidationEditText(dogSearch: EditText): String {
        return if (dogSearch.text.isNotEmpty()) {
            dogSearch.text.toString().toLowerCase().trim()
        } else {
            ""
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}