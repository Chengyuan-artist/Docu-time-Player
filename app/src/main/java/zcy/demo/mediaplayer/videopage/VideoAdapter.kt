package zcy.demo.mediaplayer.videopage


import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import zcy.demo.mediaplayer.R
import zcy.demo.mediaplayer.database.VideoInfo
import zcy.demo.mediaplayer.databinding.VideoPlayViewBinding
import java.math.MathContext
import kotlin.coroutines.coroutineContext


class VideoAdapter(private val context: Context) : ListAdapter<VideoInfo, VideoAdapter.ViewHolder>(VideoDiffCallback()){

    companion object{
        val TAG = "VideoAdapter:"
    }
    private val pagerSnapHelper = PagerSnapHelper()


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        pagerSnapHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
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

    class ViewHolder private constructor(private val binding:VideoPlayViewBinding, context: Context):
        RecyclerView.ViewHolder(binding.root){

            private val player = SimpleExoPlayer.Builder(context).build()

            fun bind(item: VideoInfo){
                binding.playerView.player = player
                val mediaItem = MediaItem.fromUri(item.uri)
                player.setMediaItem(mediaItem)
                player.prepare()
            }

            fun play(){
                player.play()
            }

            fun pause(){
                player.pause()
            }

            companion object{

                fun from(parent: ViewGroup, context: Context):ViewHolder{
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = VideoPlayViewBinding.inflate(layoutInflater, parent, false)
                    return ViewHolder(binding, context)
                }

            }
    }
}

class VideoDiffCallback : DiffUtil.ItemCallback<VideoInfo>(){
    override fun areItemsTheSame(oldItem: VideoInfo, newItem: VideoInfo): Boolean {
        return oldItem.videoId == newItem.videoId
    }

    override fun areContentsTheSame(oldItem: VideoInfo, newItem: VideoInfo): Boolean {
        return oldItem == newItem
    }

}
