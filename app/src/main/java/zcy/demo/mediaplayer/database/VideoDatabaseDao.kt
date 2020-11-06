package zcy.demo.mediaplayer.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VideoDatabaseDao {
    @Insert
    fun insert(videoInfo: VideoInfo)

    @Update
    fun update(videoInfo: VideoInfo)

    @Delete
    fun delete(videoInfo: VideoInfo)

    @Query("SELECT * FROM video_info_table ORDER BY videoId ASC")
    fun getAllVideos(): LiveData<List<VideoInfo>>
}