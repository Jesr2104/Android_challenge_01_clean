package com.justjump.android_challenge_01_clean.koin

import android.app.Application
import com.justjump.android_challenge_01_clean.BrowserTweets
import com.justjump.android_challenge_01_clean.viewmodel.BrowserTweetsViewModel
import com.justjump.data.datasources.GetterTweets
import com.justjump.data.datasources._interfaces.TweetsIDataSource
import com.justjump.data.datasources.remote.RealTimeTweets
import com.justjump.data.repositories.SearchRepository
import com.justjump.usecases.SearchTweetsUseCases
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI(){
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(mymodules, presentation))
    }
}

var mymodules = module {
    factory<RealTimeTweets>{RealTimeTweets()}
    factory<TweetsIDataSource>{ GetterTweets(get()) }
    factory<SearchRepository>{SearchRepository(get())}
    factory<SearchTweetsUseCases>{SearchTweetsUseCases(get())}
}

var presentation = module {
    scope(named<BrowserTweets>()){
        viewModel { BrowserTweetsViewModel(get()) }
    }
}


