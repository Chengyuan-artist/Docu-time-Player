package zcy.demo.mediaplayer.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import zcy.demo.mediaplayer.database.VideoDatabaseDao
import zcy.demo.mediaplayer.database.VideoInfo

class ListViewModel(private val dataSource: VideoDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    companion object{
        val TAG="Thyme ListViewModel:"
    }
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToPlayPage = MutableLiveData<Long?>()
    val navigateToPlayPage :LiveData<Long?>
        get() = _navigateToPlayPage

    val videos = dataSource.getAllVideos()

    private suspend fun insert(item: VideoInfo){
        withContext(Dispatchers.IO){
            dataSource.insert(item)
        }
    }

    private suspend fun getVideoList(){
        return withContext(Dispatchers.IO){
            dataSource.getAllVideos()
        }
    }

    fun resolveTestData(videos : ArrayList<VideoInfo>){
        uiScope.launch {
            for (item in videos){
                insert(item)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onItemClicked(videoId: Long) {
        _navigateToPlayPage.value = videoId
        Log.d(TAG, "onItemClicked: invoked")
    }
    fun onDoneNavigateToVideoPage(){
        _navigateToPlayPage.value = null
    }
}