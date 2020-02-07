package `in`.rahultyagi.reports.helper

import android.content.Context
import androidx.room.Room


class DatabaseClient private constructor(mCtx: Context) {
    val appDatabase: AppDatabase = Room.databaseBuilder(mCtx, AppDatabase::class.java, "Reports")
        .allowMainThreadQueries().build()

    companion object {
        private var mInstance: DatabaseClient? = null
        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance =
                    DatabaseClient(mCtx)
            }
            return mInstance
        }
    }

}
