package zcy.demo.mediaplayer.videopage

import androidx.lifecycle.ViewModel
import zcy.demo.mediaplayer.R
import zcy.demo.mediaplayer.database.VideoInfo

class VideoPageViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var videos = ArrayList<VideoInfo>()

   init {
       videos.add(VideoInfo("0", R.drawable.p1))
       videos.add(VideoInfo("1", R.drawable.p2))
       videos.add(VideoInfo("2", R.drawable.p3))
   }

    fun getBind(adapter: VideoAdapter){
        adapter.submitList(videos)
    }

}