package com.example.treescompose.data.remote.responses

data class Trees(
    val facet_groups: List<FacetGroup>? = null,
    val nhits: Int? = null,
    val parameters: Parameters? = null,
    val records: List<Tree>
)