package com.example.dogs.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.dogs.R
import com.example.dogs.adapters.DogResultAdapter
import com.example.dogs.extensions.SchedulerProviderImpl
import com.example.dogs.extensions.visible
import com.example.dogs.extensions.gone
import com.example.dogs.extensions.hideKeyboard
import com.example.dogs.models.Breeds
import com.example.dogs.network.Network
import com.example.dogs.viewmodels.DogSearchViewModel
import java.util.*

class HomeActivity : AppCompatActivity() {

    private val LOG_TAG = HomeActivity::class.java.simpleName

    private val dogSearch: EditText by lazy {
        findViewById<EditText>(R.id.dog_search_edit_text)
    }

    private val errorMessage: TextView by lazy {
        findViewById<TextView>(R.id.error_message)
    }

    private val searchBtn: Button by lazy {
        findViewById<Button>(R.id.dog_search_button)
    }

    private val dogPager: ViewPager2 by lazy {
        findViewById<ViewPager2>(R.id.dog_view_page)
    }

    private val schedulerProviderImpl: SchedulerProviderImpl = SchedulerProviderImpl()

    private val dogSearchViewModel: DogSearchViewModel = DogSearchViewModel(Network.dogService, schedulerProviderImpl)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.setHomeButtonEnabled(true)

        dogSearchViewModel.dogSearchResult.observe(this, Observer {
            if (it != null) {
                errorMessage.gone()
                dogPager.visible()
                dogPager.adapter = DogResultAdapter(it)
            }
        })

        dogSearchViewModel.error.observe(this, Observer { throwable ->
            Log.i(LOG_TAG, throwable.localizedMessage!!)
        })

        searchBtn.setOnClickListener {
            hideKeyboard()
            val searchText = performBasicValidationEditText(dogSearch)

            if (searchText == null) {
                handleErrorState(getString(R.string.blank_input_error_message))
            } else {
                if (Breeds.listOfBreeds.contains(searchText)) {
                    dogSearchViewModel.fetchDogByBreed(searchText)
                } else {
                    handleErrorState(getString(R.string.no_dog_found_error))
                }
            }
        }
    }

    private fun handleErrorState(errorMessageValue: String) {
        errorMessage.text = errorMessageValue
        errorMessage.visible()
        dogPager.gone()
    }

    private fun performBasicValidationEditText(dogSearch: EditText): String? {
        return if (dogSearch.text.isNotEmpty()) {
            dogSearch.text.toString().toLowerCase(Locale.getDefault()).trim()
        } else {
            null
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