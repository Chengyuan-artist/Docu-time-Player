package zcy.demo.mediaplayer.videopage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import zcy.demo.mediaplayer.database.VideoDatabaseDao

class VideoPageViewModel(private val dataSource: VideoDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val videos = dataSource.getAllVideos()


    private suspend fun getVideoList(){
        return withContext(Dispatchers.IO){
            dataSource.getAllVideos()
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}