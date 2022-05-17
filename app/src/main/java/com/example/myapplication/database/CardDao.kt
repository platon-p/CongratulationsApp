package com.example.myapplication.database

import androidx.room.*
import io.reactivex.Completable

import io.reactivex.Flowable

@Dao
interface CardDao {
    @Query("SELECT * FROM card")
    fun getAll(): Flowable<List<Card>>

    @Query("SELECT COUNT(*) FROM card")
    fun getCount(): Int

    @Query("SELECT * FROM card where id = :id")
    fun getById(id: Long): Flowable<Card>

    @Insert
    fun insert(card: Card): Completable

    @Update
    fun update(card: Card)

    @Delete
    fun delete(card: Card)
}