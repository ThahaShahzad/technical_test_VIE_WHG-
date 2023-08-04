package com.example.devtest.api

import com.example.devtest.models.Specie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: SpeciesApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.fishdex.org/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpeciesApi::class.java)
    }
}
data class NestedJSONModelAll(
    var data: NestedJSONModel1
)
data class NestedJSONModel1(
    var species: List<Specie>
)

data class NestedJSONModelById(
    var data: Specie
)