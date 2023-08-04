package com.example.devtest.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.devtest.R
import com.example.devtest.api.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Fragment2 : Fragment() {

    private lateinit var view: View;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_2, container, false)

        val idOfSpecie = requireArguments().getString("id")?.toInt() as Int

        val progressBar: ProgressBar = view.findViewById(R.id.progressBar1)

        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvSciname: TextView = view.findViewById(R.id.tvSciname)
        val tvEnvironment: TextView = view.findViewById(R.id.tvEnvironment)
        val ivThumbnail: ImageView = view.findViewById(R.id.ivThumbnail)

        CoroutineScope(Dispatchers.IO).launch {
            // Do the GET request and get response
            val response = RetrofitInstance.api.getSpeciesById(idOfSpecie)

            withContext(Dispatchers.Main) {
                progressBar.isVisible = true
                if (response.isSuccessful) {
                    progressBar.isVisible = false
                    val spice = response.body()?.data!!

                    tvName.text = spice.name
                    tvSciname.text = spice.sciname
                    tvEnvironment.text = TextUtils.join(", ", spice.environment)

                    Picasso
                        .get()
                        .load("http://www.fishbase.org/images/thumbnails/jpg/tn_Abhia_u1.jpg")
                        .into(ivThumbnail);

                } else {
                    Log.e("response error", "error")
                }
            }
        }
        return view
    }
}