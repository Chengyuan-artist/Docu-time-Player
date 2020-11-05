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
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.
            inflate(layoutInflater, R.layout.video_page_fragment, container, false)

        viewModel = ViewModelProvider(this).get(VideoPageViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        layoutManager = LinearLayoutManager(context)
        binding.videoRecyclerView.layoutManager = layoutManager


        val videoInfos = ArrayList<VideoInfo>()

        for (x in 1 until 7){
            videoInfos.add(VideoInfo(x, transToUri(pickFile(x))))
        }

        videoAdapter = VideoAdapter(requireContext())

        binding.videoRecyclerView.adapter = videoAdapter

        videoAdapter.submitList(videoInfos)

        return binding.root
    }


    fun transToUri(x :Int) : Uri{
        return Uri.parse("android.resource://" + requireContext().packageName + "/" + x)
    }

    fun pickFile(x : Int) : Int{
        return when(x){
            1 -> R.raw.v1
            2 -> R.raw.v2
            3 -> R.raw.v3
            4 -> R.raw.v4
            5 -> R.raw.v5
            6 -> R.raw.v6
            7 -> R.raw.v7
            else -> R.raw.v7
        }
    }


}

