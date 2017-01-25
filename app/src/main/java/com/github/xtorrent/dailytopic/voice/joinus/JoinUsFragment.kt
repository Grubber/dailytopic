package com.github.xtorrent.dailytopic.voice.joinus

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseFragment

/**
 * Created by grubber on 2017/1/25.
 */
class JoinUsFragment : BaseFragment() {
    companion object {
        fun newInstance(): JoinUsFragment {
            return JoinUsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_join_us, container, false)
    }

    private val _contentView by bindView<TextView>(R.id.contentView)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val content = """
        <p><strong>1.文章内容选择。</strong></p>
        <p>以经典的短篇为主，题材可以是小说，散文。
        时长控制在20分钟内。</p>

        <p><strong>2.录音设备工具选择</strong></p>
        <p>
            iPhone用户可以使用iPhone自带录音软件或者garageband来录音，其他手机也可以尝试一下手机录音。<br>
            Mac用户可以使用garageband来录音和后期处理，Windows用户可以尝试使用cooledit(Adobe audition)来录音和后期处理。
        </p>
        <p><strong>3.开始</strong></p>
        <p>可以选择这样自然的开始'欢迎收听每日一文，我是主播xxx,今天给大家分享的文是⋯⋯'</p>

        <p><strong>4.背影音乐。</strong></p>
        <p>尽量保持简单，保证人声清晰。</p>

        <p><strong>5.投递。</strong></p>
        <p>保存作品格式为<strong>mp3</strong>，文件包括声音文件、文字稿、主播名，可附一个个人的微博。如果你不擅长后期处理可以发送你录制好的干音和挑选好的音乐给我们，我们很乐意帮你处理，并且给你意见。</p>
        <p><strong>作品投递到zhubo@meiriyiwen.com</strong></p>
        <p><strong>6.更多了解</strong></p>
        <p>加入我们的主播群 QQ群：124829539</p>
         """
        _contentView.text = Html.fromHtml(content)
    }

    override fun getTitle(): String? {
        return getString(R.string.title_join_us)
    }
}