package com.justjump.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.justjump.data.datasources._interfaces.TweetsIDataSource
import com.justjump.data.datasources.remote.RealTimeTweets
import com.justjump.domain.tweets.TweetsDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GetterTweets(private val realTimeTweetDataSource: RealTimeTweets): TweetsIDataSource {

    private var newDataTweet = MutableLiveData<TweetsDataModel>()

    override fun getTweet(textFilter: String): LiveData<TweetsDataModel> {
        GlobalScope.launch(Dispatchers.Main) {
            realTimeTweetDataSource.newTweetItem.observeForever{
                newDataTweet.value = it
            }
            realTimeTweetDataSource.getData(textFilter)
        }
        return newDataTweet
    }
}