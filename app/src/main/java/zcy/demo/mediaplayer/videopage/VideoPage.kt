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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import zcy.demo.mediaplayer.R
import zcy.demo.mediaplayer.database.VideoDatabase
import zcy.demo.mediaplayer.database.VideoInfo
import zcy.demo.mediaplayer.databinding.VideoPageFragmentBinding


class VideoPage : Fragment() {

    companion object {
        val TAG = "Thyme VideoPage :"
    }

    private lateinit var viewModel: VideoPageViewModel
    private lateinit var binding: VideoPageFragmentBinding
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.video_page_fragment, container, false)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = VideoDatabase.getInstance(application).videoDatabaseDao
        val factory = VideoPageFactory(dataSource, application)

        val args = VideoPageArgs.fromBundle(requireArguments())

        viewModel = ViewModelProvider(this, factory).get(VideoPageViewModel::class.java)


        videoAdapter = VideoAdapter(requireContext(), args.videoId)
        binding.videoRecyclerView.adapter = videoAdapter

        viewModel.videos.observe(viewLifecycleOwner, {
            videoAdapter.submitList(it)
        })

        Log.d(TAG, "onCreateView: createSuc")
        return binding.root
    }



}

