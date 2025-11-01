package com.example.a4.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "fun_facts")
data class FunFact(
    @SerialName("id")
    val apiId: String,

    @SerialName("text")
    val text: String,

    @SerialName("source")
    val source: String,

    @SerialName("source_url")
    val sourceUrl: String,

    @SerialName("language")
    val language: String,

    @SerialName("permalink")
    val permalink: String,

    @PrimaryKey(autoGenerate = true)
    @kotlinx.serialization.Transient
    val id: Int = 0
)
