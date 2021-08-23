package com.justjump.data.datasources.remote

import android.util.Log
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.oauth.OAuth2
import com.tycz.tweedle.lib.dtos.tweet.Add
import com.tycz.tweedle.lib.dtos.tweet.rules.Rule
import com.tycz.tweedle.lib.tweets.stream.TweetsStream
import com.tycz.tweedle.lib.tweets.stream.filter.Filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect

class Tweedle {

    companion object {
        private const val TOKEN_TWITTER = "AAAAAAAAAAAAAAAAAAAAAAfKSAEAAAAAInN4lud2YJ3jqBRFykvq1ZUuPys%3DK1ApIYkbp4X90pqZlCa5tDd28Lo5PCH0GXO5puNn7fjzbxjDY6"
    }

    private val oAuth2 = OAuth2(TOKEN_TWITTER)
    private val _tweetStream = TweetsStream(oAuth2)

    fun makeFlowRegister(textFilter: String) = flow {
        _tweetStream.addRules(setUpFilter(textFilter))
        _tweetStream.startTweetStream().collect {
            when (it) {
                is Response.Success -> { emit(it.data.data) }
                is Response.Error -> { Log.e("error ->", it.exception.message!! ) }
            }
        }
    }

    // search filter settings
    private fun setUpFilter(textFilter: String): Rule {
        val filters:MutableList<Add> = mutableListOf()

        val filter: Filter = Filter.Builder()
            .addOperator(textFilter)
            .and()
            .setLanguage(Filter.ENGLISH)
            .build()

        filters.add(Add(filter.filter, textFilter))

        return Rule(filters)
    }
}
