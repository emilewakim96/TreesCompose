package com.example.treescompose.data.remote.responses

data class Trees(
    val facet_groups: List<FacetGroup>,
    val nhits: Int,
    val parameters: Parameters,
    val records: List<Tree>
)