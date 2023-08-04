package com.example.devtest.models

data class Specie(
    val id: Int,
    val name: String,
    val sciname: String,
    val environment: List<String>,
    val thumbnail: String
)