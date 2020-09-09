package com.extcode.project.ms_shuttle.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.extcode.project.ms_shuttle.entity.ListOrder

@Database(entities = [ListOrder::class], version = 1, exportSchema = false)
abstract class ListOrderDatabase: RoomDatabase() {

    abstract fun listOrderDao():ListOrderDao

    companion object{
        @Volatile
        private var INSTANCE: ListOrderDatabase? = null

        fun getInstance(context: Context?): ListOrderDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context!!.applicationContext,
                    ListOrderDatabase::class.java,
                    "listOrderDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}