package com.github.xtorrent.dailytopic.book

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseFragment
import com.github.xtorrent.dailytopic.base.ContentFragment
import com.github.xtorrent.dailytopic.base.PagingRecyclerViewAdapter
import com.github.xtorrent.dailytopic.book.model.Bookshelf
import com.github.xtorrent.dailytopic.book.model.BookshelfHeaderImage
import com.github.xtorrent.dailytopic.widget.viewpager.LoopViewPager
import com.jakewharton.rxbinding.view.clicks
import com.squareup.picasso.Picasso
import com.viewpagerindicator.CirclePageIndicator
import java.util.*

/**
 * @author Grubber
 */
class BookshelfFragment : ContentFragment(), BookshelfContract.View {
    companion object {
        fun newInstance(): BookshelfFragment {
            return BookshelfFragment()
        }
    }

    private lateinit var _presenter: BookshelfContract.Presenter

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_bookshelf, container, false)
    }

    private val _recyclerView by bindView<RecyclerView>(R.id.recyclerView)

    private val _adapter by lazy {
        BookshelfItemAdapter(context, childFragmentManager, _presenter, picasso())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

        _presenter.subscribe()
    }

    override fun setContentView(data: List<Bookshelf>) {
    }

    override fun setContentView(pair: Pair<List<BookshelfHeaderImage>?, List<Bookshelf>?>, loadedError: Boolean) {
        pair.first?.let {
            _adapter.headerItem = it
        }

        val loadingState = if (pair.second == null && loadedError) {
            PagingRecyclerViewAdapter.STATE_LOADING_FAILED
        } else {
            PagingRecyclerViewAdapter.STATE_LOADING_SUCCEED
        }
        _adapter.addItems(pair.second, loadingState)

        displayContentView()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun setLoadingView() {
        displayLoadingView()
    }

    override fun setPresenter(presenter: BookshelfContract.Presenter) {
        _presenter = presenter
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return null
    }

    class BookshelfItemAdapter(private val context: Context,
                               private val fragmentManager: FragmentManager,
                               private val presenter: BookshelfContract.Presenter,
                               private val picasso: Picasso) : PagingRecyclerViewAdapter<Bookshelf, List<BookshelfHeaderImage>>() {
        override fun getLoadCount(): Int {
            return 12
        }

        override fun onLoadMore(pageNumber: Int) {
            presenter.setPageNumber(pageNumber)
            presenter.subscribe()
        }

        override fun onCreateBasicItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return BookshelfItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bookshelf, parent, false))
        }

        override fun onBindBasicItemView(holder: RecyclerView.ViewHolder, position: Int) {
            holder as BookshelfItemViewHolder
            val item = getItem(position)
            picasso.load(item.image())
                    .placeholder(ColorDrawable(R.color.colorAccent))
                    .error(ColorDrawable(R.color.colorAccent))
                    .fit()
                    .tag(context)
                    .into(holder.coverView)
            holder.titleView.text = item.title()
            holder.authorView.text = item.author()
            holder.itemView.setOnClickListener {
                // TODO
            }
        }

        override fun onRetry(pageNumber: Int) {
            presenter.setPageNumber(pageNumber)
            presenter.subscribe()
        }

        override fun hasHeader(): Boolean {
            return true
        }

        override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
            return BookshelfHeaderImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_bookshelf_header_image, parent, false))
        }

        private var _pagerAdapter: HeaderImagePagerAdapter? = null

        override fun onBindHeaderView(holder: RecyclerView.ViewHolder, position: Int) {
            super.onBindHeaderView(holder, position)
            holder as BookshelfHeaderImageViewHolder
            if (_pagerAdapter == null) {
                _pagerAdapter = HeaderImagePagerAdapter(fragmentManager)
                holder.headerImageViewPager.setPageTransformer(true, ZoomOutPageTransformer())
                holder.headerImageViewPager.pageMargin = context.resources.getDimensionPixelOffset(R.dimen.page_margin)
                headerItem?.map {
                    HeaderImageFragment.newInstance(it.url(), it.image())
                }?.let {
                    _pagerAdapter!!.fragments = it as ArrayList<HeaderImageFragment>
                }
                headerItem?.first()?.let {
                    _pagerAdapter!!.fragments.add(HeaderImageFragment.newInstance(it.url(), it.image()))
                }
                headerItem?.last()?.let {
                    _pagerAdapter!!.fragments.add(0, HeaderImageFragment.newInstance(it.url(), it.image()))
                }
                holder.headerImageViewPager.adapter = _pagerAdapter
                holder.headerImageViewPager.offscreenPageLimit = _pagerAdapter!!.count
                holder.headerImageIndicator.setViewPager(holder.headerImageViewPager)
            }
        }
    }

    class ZoomOutPageTransformer : ViewPager.PageTransformer {
        private val _MIN_SCALE = 0.85f

        override fun transformPage(page: View?, position: Float) {
            val pageWidth = page!!.width
            val pageHeight = page.height
            val scaleFactor = Math.max(_MIN_SCALE, 1 - Math.abs(position))
            val vertMargin = pageHeight * (1 - scaleFactor) / 2
            val horzMargin = pageWidth * (1 - scaleFactor) / 2
            if (position < 0) {
                page.translationX = horzMargin - vertMargin / 2
            } else {
                page.translationX = -horzMargin + vertMargin / 2
            }

            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
        }
    }

    class HeaderImagePagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        var fragments = arrayListOf<HeaderImageFragment>()

        override fun getCount(): Int {
            return fragments.size - 2
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }
    }

    class HeaderImageFragment : BaseFragment() {
        companion object {
            private const val EXTRA_URL = "url"
            private const val EXTRA_IMAGE = "image"

            fun newInstance(url: String, image: String): HeaderImageFragment {
                val fragment = HeaderImageFragment()
                val args = Bundle()
                args.putString(EXTRA_URL, url)
                args.putString(EXTRA_IMAGE, image)
                fragment.arguments = args
                return fragment
            }
        }

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater?.inflate(R.layout.fragment_bookshelf_header_image, container, false)
        }

        private val _imageView by bindView<ImageView>(R.id.imageView)

        private val _url by lazy {
            arguments.getString(EXTRA_URL)
        }
        private val _image by lazy {
            arguments.getString(EXTRA_IMAGE)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            picasso().load(_image)
                    .placeholder(ColorDrawable(R.color.colorAccent))
                    .error(ColorDrawable(R.color.colorAccent))
                    .fit()
                    .into(_imageView)
            bindSubscribe(_imageView.clicks()) {
                // TODO url
            }
        }

        override fun getTitle(): String? {
            return null
        }
    }

    class BookshelfHeaderImageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val headerImageViewPager by bindView<LoopViewPager>(R.id.headerImageViewPager)
        val headerImageIndicator by bindView<CirclePageIndicator>(R.id.headerImageIndicator)
    }

    class BookshelfItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val coverView by bindView<ImageView>(R.id.coverView)
        val titleView by bindView<TextView>(R.id.titleView)
        val authorView by bindView<TextView>(R.id.authorView)
    }
}