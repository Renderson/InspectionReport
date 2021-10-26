package com.example.inspectionreport.model

import java.io.Serializable

data class Report(
    val id: Int,
    val company: String,
    val sanitarian: String,
    val note: String,
    val date: String
) : Serializable