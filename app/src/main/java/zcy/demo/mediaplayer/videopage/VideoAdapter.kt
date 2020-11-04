package zcy.demo.mediaplayer.videopage


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.SimpleExoPlayer
import zcy.demo.mediaplayer.R
import zcy.demo.mediaplayer.database.VideoInfo
import zcy.demo.mediaplayer.databinding.VideoPlayViewBinding


class VideoAdapter(private var player: SimpleExoPlayer) : ListAdapter<VideoInfo, VideoAdapter.ViewHolder>(VideoDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, player)
    }

    class ViewHolder private constructor(private val binding:VideoPlayViewBinding):
        RecyclerView.ViewHolder(binding.root){

            fun bind(item: VideoInfo, player: SimpleExoPlayer){
                binding.videoInfo = item
                binding.playerView.player = player

            }

            companion object{

                fun from(parent: ViewGroup):ViewHolder{
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = VideoPlayViewBinding.inflate(layoutInflater, parent, false)
                    return ViewHolder(binding)
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
