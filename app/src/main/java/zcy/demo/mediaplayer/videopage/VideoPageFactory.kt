package zcy.demo.mediaplayer.videopage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import zcy.demo.mediaplayer.database.VideoDatabaseDao

class VideoPageFactory(
    private val dataSource: VideoDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoPageViewModel::class.java)) {
            return VideoPageViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}