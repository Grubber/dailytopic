package com.github.xtorrent.dailytopic.book.source

import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/18.
 */
@BookScope
@Subcomponent(modules = arrayOf(BookRepositoryModule::class))
interface BookRepositoryComponent {

}