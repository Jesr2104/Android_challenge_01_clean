package com.justjump.usecases

import androidx.lifecycle.LiveData
import com.justjump.data.repositories.SearchRepository
import com.justjump.domain.tweets.TweetsDataModel

class SearchTweetsUseCases(private val searchRepository: SearchRepository ) {

    fun invoke(textFilter: String): LiveData<TweetsDataModel> =
        searchRepository.searchTweets(textFilter)
}