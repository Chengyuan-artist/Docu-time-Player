package zcy.demo.mediaplayer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_info_table")
data class VideoInfo(
    @PrimaryKey(autoGenerate = true)
    var videoId :Long = 0,

    @ColumnInfo(name = "video_name")
    var videoName : String = "",

    @ColumnInfo(name = "video_uri")
    var uri :String = ""
)