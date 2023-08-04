package com.example.devtest.api

import com.example.devtest.models.Specie
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpeciesApi {

    @GET("species/top/popular")
    suspend fun getSpecies(): Response<NestedJSONModelAll>

    @GET("species/{id}")
    suspend fun getSpeciesById(@Path("id") id: Int): Response<NestedJSONModelById>

}
