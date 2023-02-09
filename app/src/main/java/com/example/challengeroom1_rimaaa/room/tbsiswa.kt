package com.example.challengeroom1_rimaaa.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tbsiswa(
    @PrimaryKey
    val nis: Int,
    val nama: String,
    val kelas: String,
    val alamat: String
)