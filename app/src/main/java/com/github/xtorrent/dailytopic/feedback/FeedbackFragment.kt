package com.github.xtorrent.dailytopic.feedback

import android.os.Bundle
import android.view.*
import android.widget.EditText
import butterknife.bindView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseFragment
import com.github.xtorrent.dailytopic.core.api.model.CommonResponse
import com.github.xtorrent.dailytopic.feedback.model.Feedback
import com.github.xtorrent.dailytopic.widget.dialog.LoadingDialog

/**
 * @author Grubber
 */
class FeedbackFragment : BaseFragment(), FeedbackContract.View {
    companion object {
        fun newInstance(): FeedbackFragment {
            return FeedbackFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_feedback, container, false)
    }

    private val _feedbackView by bindView<EditText>(R.id.feedbackView)
    private val _contactView by bindView<EditText>(R.id.contactView)

    private lateinit var _presenter: FeedbackContract.Presenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_feedback, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.submit -> {
                if (_feedbackView.text.toString().isNullOrBlank()) {
                    toastHelper().show(R.string.message_feedback_empty)
                } else {
                    val feedback = Feedback(_contactView.text.toString(), _feedbackView.text.toString())
                    _presenter.setFeedback(feedback)
                    _presenter.subscribe()
                }

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private val _dialog by lazy {
        LoadingDialog.create(context)
    }

    override fun shouldShowLoadingDialog(isLoading: Boolean) {
        if (isLoading) _dialog.show() else _dialog.dismiss()
    }

    override fun setPresenter(presenter: FeedbackContract.Presenter) {
        _presenter = presenter
    }

    override fun setContentView(data: CommonResponse) {
        toastHelper().show(R.string.message_feedback_succeed)
        _feedbackView.text.clear()
        _contactView.text.clear()
    }

    override fun setErrorView() {
        toastHelper().show(R.string.message_feedback_failed)
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return getString(R.string.drawer_menu_feedback)
    }
}