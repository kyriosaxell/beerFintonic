package com.example.fintonicbeer.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fintonicbeer.util.Converters

@TypeConverters(Converters::class)
@Database(entities = [Beer::class], version = 2, exportSchema = false)
abstract class BeerDataBase : RoomDatabase() {
    abstract fun beerDAO(): BeerDAO

    companion object {
        private const val DB_NAME = "beerDataBase.db"

        @Volatile
        private var instance: BeerDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        /**
         * Creates a new instance for DB
         * @param context
         */
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BeerDataBase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }
}