package com.example.treescompose.data.remote.responses

data class Tree(
    val datasetid: String,
    val fields: Fields,
    val geometry: Geometry,
    val record_timestamp: String,
    val recordid: String
)