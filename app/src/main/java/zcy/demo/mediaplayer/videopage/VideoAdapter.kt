package zcy.demo.mediaplayer.videopage


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.analytics.PlaybackStatsListener
import zcy.demo.mediaplayer.database.VideoInfo
import zcy.demo.mediaplayer.databinding.VideoPlayViewBinding


class VideoAdapter(private val context: Context, private val beginPos:Long) :
    ListAdapter<VideoInfo, VideoAdapter.ViewHolder>(VideoDiffCallback()) {

    private lateinit var recyclerView: RecyclerView
    private val pagerSnapHelper = PagerSnapHelper()
    private val linearLayoutManager = LinearLayoutManager(context)

    companion object {
        val TAG = "VideoAdapter:"
    }

    private fun getChildCount() = recyclerView.layoutManager!!.itemCount

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        pagerSnapHelper.attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = linearLayoutManager


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        Log.d(TAG, "onBindViewHolder: ${getChildCount()}")
        val playbackStatsListener = object : Player.EventListener {
            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    ExoPlayer.STATE_ENDED -> {
                        if (position == getChildCount()-1) {
                            recyclerView.layoutManager!!.scrollToPosition(0)
                        }else{
                            recyclerView.layoutManager!!.smoothScrollToPosition(
                                recyclerView,
                                RecyclerView.State(), position + 1
                            )
                        }
                    }
                    else -> {

                    }
                }
            }
        }

        holder.player.addListener(playbackStatsListener)


        Log.d(TAG, "onBindViewHolder: bind suc in $position")
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.play()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.pause()
    }

    class ViewHolder private constructor(
        private val binding: VideoPlayViewBinding,
        context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {

        val player = SimpleExoPlayer.Builder(context).build()

        fun bind(item: VideoInfo) {
            binding.playerView.player = player

            binding.playerView.setOnClickListener {
                if (player.isPlaying) {
                    player.pause()
                } else {
                    player.play()
                }
            }

            val mediaItem = MediaItem.fromUri(item.uri)
            player.setMediaItem(mediaItem)
            player.prepare()
        }

        fun play() {
            player.seekTo(0)
            player.play()
        }

        fun pause() {
            player.pause()
        }

        companion object {

            fun from(parent: ViewGroup, context: Context): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VideoPlayViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, context)
            }

        }
    }
}


class VideoDiffCallback : DiffUtil.ItemCallback<VideoInfo>() {
    override fun areItemsTheSame(oldItem: VideoInfo, newItem: VideoInfo): Boolean {
        return oldItem.videoId == newItem.videoId
    }

    override fun areContentsTheSame(oldItem: VideoInfo, newItem: VideoInfo): Boolean {
        return oldItem == newItem
    }

}
