package com.justjump.data.repositories

import androidx.lifecycle.LiveData
import com.justjump.data.datasources._interfaces.TweetsIDataSource
import com.justjump.domain.tweets.TweetsDataModel

class SearchRepository(private val tweetsIDataSource: TweetsIDataSource) {
    fun searchTweets(textFilter: String): LiveData<TweetsDataModel> {
        return tweetsIDataSource.getTweet(textFilter)
    }
}