package com.firmino.geriodonto.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MedEntity::class, InteractionEntity::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(GeriodontoConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun medDao(): MedDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "geriodonto_database",
                )
                    .fallbackToDestructiveMigration(true)
                    .build()

                INSTANCE = instance
                instance
            }
        }

    }
}
