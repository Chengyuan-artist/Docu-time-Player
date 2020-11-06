package zcy.demo.mediaplayer.list

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import zcy.demo.mediaplayer.R
import zcy.demo.mediaplayer.database.VideoInfo
import zcy.demo.mediaplayer.databinding.ListItemViewBinding

class ListItemAdapter(
    private val application: Application,
    private val clickListener: (VideoId: Long) -> Unit
) : ListAdapter<VideoInfo, ListItemAdapter.ViewHolder>(ItemDiffCallback()) {


    @RequiresApi(Build.VERSION_CODES.Q)
    fun loadBitmap(uriString: String) = application.contentResolver.loadThumbnail(
        Uri.parse(uriString),
        Size(640, 480), null
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            holder.binding.imageView.setImageBitmap(loadBitmap(item.uri))
        } else {
            holder.binding.imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }

    class ViewHolder private constructor(val binding: ListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VideoInfo, clickListener: (VideoId: Long) -> Unit) {
            binding.textView.text = item.videoName
            binding.imageView.setOnClickListener{
                clickListener(item.videoId)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

}

class ItemDiffCallback : DiffUtil.ItemCallback<VideoInfo>() {
    override fun areItemsTheSame(oldItem: VideoInfo, newItem: VideoInfo): Boolean {
        return oldItem.videoId == newItem.videoId
    }

    override fun areContentsTheSame(oldItem: VideoInfo, newItem: VideoInfo): Boolean {
        return oldItem == newItem
    }

}
