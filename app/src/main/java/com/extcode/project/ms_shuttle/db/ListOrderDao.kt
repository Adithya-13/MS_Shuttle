package com.extcode.project.ms_shuttle.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.extcode.project.ms_shuttle.entity.ListOrder

@Dao
interface ListOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: ListOrder): Long

    @Query("DELETE FROM listOrderTable WHERE ID = :id")
    fun deleteUser(id: String): Int

    @Query("SELECT * FROM listOrderTable ORDER BY DATE ASC")
    fun query(): LiveData<List<ListOrder>>

    @Query("SELECT * FROM listOrderTable WHERE ID = :id")
    fun queryById(id: String): LiveData<ListOrder>

}