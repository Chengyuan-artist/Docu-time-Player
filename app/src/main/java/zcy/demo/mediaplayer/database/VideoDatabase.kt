package zcy.demo.mediaplayer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VideoInfo::class], version = 1, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {

    abstract val videoDatabaseDao: VideoDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: VideoDatabase? = null
        fun getInstance(context: Context): VideoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        VideoDatabase::class.java,
                        "video_info_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}