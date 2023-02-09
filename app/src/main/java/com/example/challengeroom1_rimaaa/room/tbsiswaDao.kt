package com.example.challengeroom1_rimaaa.room

import androidx.room.*

@Dao
interface tbsiswaDao {

    @Insert
    suspend fun addtbsiswa (tbsis: tbsiswa)

    @Update
    suspend fun updatetbsiswa(tbsis: tbsiswa)

    @Delete
    suspend fun deletetbsiswa(tbsis: tbsiswa)

    @Query("SELECT * FROM tbsiswa")
    suspend fun tampilsemua() : List<tbsiswa>

    @Query("SELECT * FROM tbsiswa WHERE nis=:tbsis_nis")
    suspend fun tampilid(tbsis_nis: Int) : List<tbsiswa>

}