package com.github.grubber.dailytopic.voice

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.github.grubber.dailytopic.R
import com.github.grubber.dailytopic.base.ContentFragment
import com.github.grubber.dailytopic.base.PagingRecyclerViewAdapter
import com.github.grubber.dailytopic.voice.details.VoiceDetailsActivity
import com.github.grubber.dailytopic.voice.joinus.JoinUsActivity
import com.github.grubber.dailytopic.voice.model.Voice
import com.squareup.picasso.Picasso

/**
 * @author Grubber
 */
class VoiceFragment : ContentFragment(), VoiceContract.View {
    companion object {
        fun newInstance(): VoiceFragment {
            return VoiceFragment()
        }
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_voice, container, false)
    }

    private lateinit var _presenter: VoiceContract.Presenter

    private val _recyclerView by bindView<RecyclerView>(R.id.recyclerView)
    private val _adapter by lazy {
        VoiceItemAdapter(context, _presenter, picasso())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        _recyclerView.layoutManager = GridLayoutManager(context, 2)
        _recyclerView.adapter = _adapter
        _recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> picasso().resumeTag(context)
                    else -> picasso().pauseTag(context)
                }
            }
        })

        _presenter.setPageNumber(1)
        _presenter.subscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_voice, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.joinUs -> {
                JoinUsActivity.start(context)
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun setPresenter(presenter: VoiceContract.Presenter) {
        _presenter = presenter
    }

    override fun setContentView(data: List<Voice>) {
        // Ignored.
    }

    override fun setContentView(data: List<Voice>?, loadedError: Boolean) {
        val loadingState = if (data == null && loadedError) {
            PagingRecyclerViewAdapter.STATE_LOADING_FAILED
        } else {
            PagingRecyclerViewAdapter.STATE_LOADING_SUCCEED
        }
        _adapter.addItems(data, loadingState)
        displayContentView()
    }

    class VoiceItemAdapter(private val context: Context,
                           private val presenter: VoiceContract.Presenter,
                           private val picasso: Picasso) : PagingRecyclerViewAdapter<Voice, Any>() {
        override fun getLoadCount(): Int {
            return 12
        }

        override fun onLoadMore(pageNumber: Int) {
            presenter.setPageNumber(pageNumber)
            presenter.subscribe()
        }

        override fun onCreateBasicItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return VoiceItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_voice, parent, false))
        }

        override fun onBindBasicItemView(holder: RecyclerView.ViewHolder, position: Int) {
            holder as VoiceItemViewHolder
            val item = getItem(position)
            picasso.load(item.coverImage())
                    .placeholder(ColorDrawable(R.color.colorAccent))
                    .error(ColorDrawable(R.color.colorAccent))
                    .fit()
                    .tag(context)
                    .into(holder.coverView)
            holder.titleView.text = item.title()
            holder.authorView.text = item.author()
            holder.tagView.text = item.tag()
            holder.itemView.setOnClickListener {
                VoiceDetailsActivity.start(context, item)
            }
        }

        override fun onRetry(pageNumber: Int) {
            presenter.setPageNumber(pageNumber)
            presenter.subscribe()
        }
    }

    class VoiceItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val coverView by bindView<ImageView>(R.id.coverView)
        val titleView by bindView<TextView>(R.id.titleView)
        val authorView by bindView<TextView>(R.id.authorView)
        val tagView by bindView<TextView>(R.id.tagView)
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return null
    }
}