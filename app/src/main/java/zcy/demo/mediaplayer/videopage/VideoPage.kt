package zcy.demo.mediaplayer.videopage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import zcy.demo.mediaplayer.R
import zcy.demo.mediaplayer.database.VideoInfo
import zcy.demo.mediaplayer.databinding.VideoPageFragmentBinding

class VideoPage : Fragment() {

    companion object {
        fun newInstance() = VideoPage()
        val TAG = "VideoPage :"
    }

    private lateinit var binding: VideoPageFragmentBinding
    private lateinit var viewModel: VideoPageViewModel
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.
            inflate(layoutInflater, R.layout.video_page_fragment, container, false)

        viewModel = ViewModelProvider(this).get(VideoPageViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        videoAdapter = VideoAdapter()
        binding.videoRecyclerView.adapter = videoAdapter

        val videos = ArrayList<VideoInfo>()
        videos.add(VideoInfo("0", R.drawable.pic1))
        videos.add(VideoInfo("1", R.drawable.pic2))
        videos.add(VideoInfo("2", R.drawable.pic3))
        videos.add(VideoInfo("3", R.drawable.pic4))

        for (item in videos){
            Log.d(TAG, "onCreateView: "+ item.videoId)
        }

        videoAdapter.submitList(videos)

        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(VideoPageViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}