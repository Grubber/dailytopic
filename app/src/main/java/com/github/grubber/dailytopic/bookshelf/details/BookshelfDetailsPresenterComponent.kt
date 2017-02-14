package com.github.grubber.dailytopic.bookshelf.details

import com.github.grubber.dailytopic.core.di.scope.ViewScope
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/22.
 */
@ViewScope
@Subcomponent(modules = arrayOf(BookshelfDetailsPresenterModule::class))
interface BookshelfDetailsPresenterComponent {
    fun inject(bookshelfDetailsActivity: BookshelfDetailsActivity)
}