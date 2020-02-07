package `in`.rahultyagi.reports.helper

import `in`.rahultyagi.reports.helper.DataDao
import `in`.rahultyagi.reports.model.*
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Area::class, Country::class, Employee::class, Region::class, Zone::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao?
}