package com.justjump.framework.tweedle

import com.tycz.tweedle.lib.authentication.oauth.OAuth2
import com.tycz.tweedle.lib.dtos.tweet.Add
import com.tycz.tweedle.lib.dtos.tweet.rules.Rule
import com.tycz.tweedle.lib.tweets.stream.TweetsStream
import com.tycz.tweedle.lib.tweets.stream.filter.Filter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class Tweedle {

    private val token: String = "AAAAAAAAAAAAAAAAAAAAAAfKSAEAAAAAInN4lud2YJ3jqBRFykvq1ZUuPys%3DK1ApIYkbp4X90pqZlCa5tDd28Lo5PCH0GXO5puNn7fjzbxjDY6"
    private val oAuth2 = OAuth2(token)
    private val _tweetStream = TweetsStream(oAuth2)

    suspend fun estasolucion(textFilter: String){
        val myData = makeFlowRegister(textFilter)
            .collect { print(it) }
    }


    private fun makeFlowRegister(textFilter: String) = flow {
        _tweetStream.addRules(setUpFilter(textFilter))
        _tweetStream.startTweetStream().collect {
            emit(it)
        }
    }

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
