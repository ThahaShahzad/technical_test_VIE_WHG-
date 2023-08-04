package com.example.devtest.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.devtest.R
import com.example.devtest.adapters.SpeciesAdapter
import com.example.devtest.api.RetrofitInstance
import com.example.devtest.models.Specie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Fragment1 : Fragment(R.layout.fragment_1) {

    private lateinit var speciesAdapter: SpeciesAdapter

    private lateinit var view: View;

    private var originalSpeciesList: List<Specie>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_1, container, false)

        val rvListAll: RecyclerView = view.findViewById(R.id.rvListAll)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)

        rvListAll.apply {
            speciesAdapter = SpeciesAdapter()
            adapter = speciesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        CoroutineScope(Dispatchers.IO).launch {

            // Do the GET request and get response
            val response = RetrofitInstance.api.getSpecies()

            withContext(Dispatchers.Main) {
                progressBar.isVisible = true
                if (response.isSuccessful) {
                    progressBar.isVisible = false
                    val items = response.body()?.data?.species
                    speciesAdapter.species = items
                    originalSpeciesList = items
                } else {
                    Log.e("response error", "error")
                }
            }
        }
       return view
    }

    override fun onResume() {
        super.onResume()
        val etSearch: EditText = view.findViewById(R.id.etSearch)
        val btnSearch: Button = view.findViewById(R.id.btnSearch)

        etSearch.addTextChangedListener(textWatcher)

        // onclick button to filter
        btnSearch.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                filter(etSearch.text.toString())
            }
        })
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredList: MutableList<Specie> = mutableListOf()
        val currentSpeciesList = speciesAdapter.species

        // running a for loop to compare elements.
        if (currentSpeciesList != null) {
            for (item in currentSpeciesList) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.name.lowercase().contains(text.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredList.add(item)
                }
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list
            println("no items found")
            val toast = Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        } else {
            // filter list
            speciesAdapter.filterList(filteredList)
        }
    }

    // textWatcher is for watching any changes in editText
    var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }
        override fun afterTextChanged(s: Editable) {
            // this function is called after text is edited
            if (s.toString() == ""){
                speciesAdapter.species = originalSpeciesList
            }
        }
    }
}

