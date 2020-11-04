package zcy.demo.mediaplayer.layoutmanager

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView


class VideoItemManager(context: Context?) : LinearLayoutManager(context) {

    private lateinit var pagerSnapHelper: PagerSnapHelper
    private lateinit var onVideoItemListener: OnVideoItemListener

    companion object{
        val TAG = "VideoItemManager:"
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(view)

    }

    override fun onScrollStateChanged(state: Int) {
        when(state){
            RecyclerView.SCROLL_STATE_IDLE -> {

                val idleView = pagerSnapHelper.findSnapView(this)
                val idlePos = idleView?.let { getPosition(it) }
                Log.d(TAG, "onScrollStateChanged: $idlePos ")
                onVideoItemListener.onCurrentItem(idlePos)

            }
            RecyclerView.SCROLL_STATE_DRAGGING ->{

            }
            RecyclerView.SCROLL_STATE_SETTLING ->{

            }
        }
    }


    fun setOnVideoItemListener(onVideoItemListener: OnVideoItemListener){
        this.onVideoItemListener = onVideoItemListener
    }
}