package com.justjump.data.datasources._interfaces

import androidx.lifecycle.LiveData
import com.justjump.domain.tweets.TweetsDataModel

interface TweetsIDataSource {
    fun getTweet(textFilter: String): LiveData<TweetsDataModel>
}