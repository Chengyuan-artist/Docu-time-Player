package zcy.demo.mediaplayer.videopage

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import zcy.demo.mediaplayer.R
import zcy.demo.mediaplayer.database.VideoInfo
import zcy.demo.mediaplayer.databinding.VideoPageFragmentBinding
import zcy.demo.mediaplayer.layoutmanager.OnVideoItemListener
import zcy.demo.mediaplayer.layoutmanager.VideoItemManager

class VideoPage : Fragment() {

    companion object {
        fun newInstance() = VideoPage()
        val TAG = "VideoPage :"
    }

    private lateinit var binding: VideoPageFragmentBinding
    private lateinit var viewModel: VideoPageViewModel
    private lateinit var videoAdapter: VideoAdapter

    private lateinit var videoItemManager: VideoItemManager
    private lateinit var player: SimpleExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.
            inflate(layoutInflater, R.layout.video_page_fragment, container, false)

        viewModel = ViewModelProvider(this).get(VideoPageViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        videoItemManager = VideoItemManager(context)
        binding.videoRecyclerView.layoutManager = videoItemManager

        player = SimpleExoPlayer.Builder(requireContext()).build()

        videoAdapter = VideoAdapter(player)

        binding.videoRecyclerView.adapter = videoAdapter

        val videoInfos = ArrayList<VideoInfo>()


        videoItemManager.setOnVideoItemListener(object :OnVideoItemListener{
            override fun onCurrentItem(position: Int?) {
                val mediaItem = MediaItem.fromUri(videoInfos[position!!].uri)
                player.setMediaItem(mediaItem)
                player.play()
            }
        })
        




        videoAdapter.submitList(videoInfos)

        return binding.root
    }


    fun transToUri(x :String) : String{
        return Uri.parse("android.resource://" + requireContext().packageName + "/" + x).toString()
    }



}

