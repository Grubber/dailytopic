package com.github.xtorrent.dailytopic.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.xtorrent.dailytopic.R

/**
 * Created by zhihao.zeng on 16/11/29.
 */
abstract class PagingRecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_FOOTER = TYPE_HEADER + 1
        const val TYPE_NORMAL = TYPE_FOOTER + 1
        const val TYPE_EMPTY = TYPE_NORMAL + 1
        const val TYPE_LOADING_MORE = TYPE_EMPTY + 1
        const val TYPE_LOADING_MORE_ERROR = TYPE_LOADING_MORE + 1

        const val STATE_LOADING_SUCCEED = 0
        const val STATE_LOADING_FAILED = 1
        const val STATE_LOADING_COMPLETED = 2
    }

    private var _pageNumber = 1
    private val _items by lazy {
        arrayListOf<T>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return when (viewType) {
            TYPE_HEADER -> onCreateHeaderViewHolder(parent, viewType)

            TYPE_FOOTER -> onCreateFooterViewHolder(parent, viewType)

            TYPE_EMPTY -> onCreateEmptyViewHolder(parent, viewType)

            TYPE_LOADING_MORE -> onCreateLoadingMoreItemViewHolder(parent, viewType)

            TYPE_LOADING_MORE_ERROR -> onCreateLoadingMoreErrorItemViewHolder(parent, viewType)

            else -> return onCreateBasicItemViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder.itemViewType) {
            TYPE_HEADER -> onBindHeaderView(holder, position)

            TYPE_FOOTER -> onBindFooterView(holder, position)

            TYPE_EMPTY -> onBindEmptyView(holder, position)

            TYPE_LOADING_MORE -> onBindLoadingMoreItemView(holder, position)

            TYPE_LOADING_MORE_ERROR -> onBindLoadingMoreErrorItemView(holder, position)

            else -> onBindBasicItemView(holder, if (hasHeader()) position - 1 else position)
        }
    }

    override fun getItemCount(): Int {
        var itemCount = _items.size

        if (itemCount == 0) {
            return 1
        }

        if (hasHeader()) {
            itemCount += 1
        }
        if (hasFooter() || shouldShowLoading()) {
            itemCount += 1
        }
        if (!hasFooter() && shouldShowLoading() && _loadingState == STATE_LOADING_COMPLETED) {
            itemCount -= 1
        }
        return itemCount
    }

    override fun getItemViewType(position: Int): Int {
        if (itemCount == 1) {
            return TYPE_EMPTY
        }

        if (position == 0 && hasHeader()) {
            return TYPE_HEADER
        }

        if (position == itemCount - 1 && shouldShowLoading()) {
            if (_loadingState == STATE_LOADING_SUCCEED) {
                return TYPE_LOADING_MORE
            } else if (_loadingState == STATE_LOADING_FAILED) {
                return TYPE_LOADING_MORE_ERROR
            }
        }

        if (position == itemCount - 1 && hasFooter()) {
            return TYPE_FOOTER
        }

        return TYPE_NORMAL
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView?.let {
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!_isLoading && _loadingState == STATE_LOADING_SUCCEED && itemCount != 1) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            val layoutManager = it.layoutManager as LinearLayoutManager
                            if (shouldShowLoading()) {
                                val lastChildView = layoutManager.getChildAt(layoutManager.childCount - 2)
                                val lastChildBottom = lastChildView.bottom + (lastChildView.layoutParams as RecyclerView.LayoutParams).bottomMargin
                                val lastPosition = layoutManager.getPosition(lastChildView)
                                val footerView = layoutManager.getChildAt(layoutManager.childCount - 1)
                                val footerTop = footerView.top - (footerView.layoutParams as RecyclerView.LayoutParams).topMargin
                                if (lastChildBottom == footerTop && lastPosition == layoutManager.itemCount - 2) {
                                    _isLoading = true
                                    onLoadMore(++_pageNumber)
                                }
                            } else {
                                val lastChildView = layoutManager.getChildAt(layoutManager.childCount - 1)
                                val lastChildBottom = lastChildView.bottom + (lastChildView.layoutParams as RecyclerView.LayoutParams).bottomMargin
                                val lastPosition = layoutManager.getPosition(lastChildView)
                                val parentBottom = it.bottom - it.paddingBottom
                                if (lastChildBottom == parentBottom && lastPosition == layoutManager.itemCount - 1) {
                                    _isLoading = true
                                    onLoadMore(++_pageNumber)
                                }
                            }
                        }
                    }
                }
            })
        }
    }

    abstract fun getLoadCount(): Int

    fun addItems(items: List<T>?, state: Int) {
        _isLoading = false
        _loadingState = state

        if (items != null) {
            _items.addAll(items)
            if (items.size < getLoadCount()) {
                _loadingState = STATE_LOADING_COMPLETED
            }
        } else {
            if (state == STATE_LOADING_SUCCEED) {
                _loadingState = STATE_LOADING_COMPLETED
            }
        }

        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return _items[position]
    }

    private var _loadingState = STATE_LOADING_SUCCEED
    private var _isLoading = false

    abstract fun onLoadMore(pageNumber: Int)

    open fun hasHeader(): Boolean = false

    open fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? = null

    open fun onBindHeaderView(holder: RecyclerView.ViewHolder, position: Int) {
    }

    open fun hasFooter(): Boolean = true

    open fun onCreateFooterViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_end, parent, false)) {}
    }

    open fun onBindFooterView(holder: RecyclerView.ViewHolder, position: Int) {
    }

    abstract fun onCreateBasicItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun onBindBasicItemView(holder: RecyclerView.ViewHolder, position: Int)

    open fun onCreateEmptyViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_empty, parent, false)) {}
    }

    open fun onBindEmptyView(holder: RecyclerView.ViewHolder, position: Int) {
    }

    open fun shouldShowLoading(): Boolean = true

    open fun onCreateLoadingMoreItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_loading_more, parent, false)) {}
    }

    open fun onBindLoadingMoreItemView(holder: RecyclerView.ViewHolder, position: Int) {
    }

    open fun onCreateLoadingMoreErrorItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_loading_more_error, parent, false)) {}
    }

    open fun onBindLoadingMoreErrorItemView(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            _loadingState = STATE_LOADING_SUCCEED
            notifyDataSetChanged()
            onRetry(_pageNumber)
        }
    }

    abstract fun onRetry(pageNumber: Int)
}