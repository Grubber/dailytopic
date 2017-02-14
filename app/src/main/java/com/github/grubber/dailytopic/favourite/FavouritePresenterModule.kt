package com.github.grubber.dailytopic.favourite

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 2017/1/30.
 */
@Module
class FavouritePresenterModule(val view: FavouriteContract.View) {
    @Provides
    fun provideFavouriteContractView(): FavouriteContract.View {
        return view
    }
}