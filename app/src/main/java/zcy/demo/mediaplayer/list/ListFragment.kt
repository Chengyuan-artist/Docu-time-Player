package zcy.demo.mediaplayer.list

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import zcy.demo.mediaplayer.R
import zcy.demo.mediaplayer.database.VideoDatabase
import zcy.demo.mediaplayer.database.VideoInfo
import zcy.demo.mediaplayer.databinding.ListFragmentBinding


class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ListFragmentBinding
    private lateinit var itemAdapter: ListItemAdapter

    companion object{
        val TAG ="Thyme ListFragment :"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = VideoDatabase.getInstance(application).videoDatabaseDao
        val factory = ListViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, factory).get(ListViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)


        binding.lifecycleOwner = this

        val videoInfos = ArrayList<VideoInfo>()

        for (x in 1L until 9L) {
            videoInfos.add(VideoInfo(0, x.toString(), transToUri(pickFile(x)).toString()))
        }

//        viewModel.resolveTestData(videoInfos)

        itemAdapter = ListItemAdapter(application,requireContext()) { videoId ->
            viewModel.onItemClicked(videoId)
        }
        viewModel.navigateToPlayPage.observe(viewLifecycleOwner, { videoId ->
            if (videoId!=null){
                this.findNavController()
                    .navigate(ListFragmentDirections.actionListFragmentToVideoPage(videoId))
                viewModel.onDoneNavigateToVideoPage()
            }
        })

        binding.listRecyclerView.adapter = itemAdapter
        binding.listRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        viewModel.videos.observe(viewLifecycleOwner, {
            itemAdapter.submitList(it)
        })

        Log.d(TAG, "onCreateView: createSuc")
        return binding.root

    }



    fun transToUri(x: Int): Uri {
        return Uri.parse("android.resource://" + requireContext().packageName + "/" + x)
    }

    fun pickFile(x: Long): Int {
        return when (x) {
            1L -> R.raw.v1
            2L -> R.raw.v2
            3L -> R.raw.v3
            4L -> R.raw.v4
            5L -> R.raw.v5
            6L -> R.raw.v6
            7L -> R.raw.v7
            8L -> R.raw.v8
            else -> R.raw.v8
        }
    }

}