package com.justjump.android_challenge_01_clean.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justjump.domain.tweets.TweetsDataModel
import com.justjump.framework.DataTest
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.oauth.OAuth2
import com.tycz.tweedle.lib.dtos.tweet.Add
import com.tycz.tweedle.lib.dtos.tweet.rules.Rule
import com.tycz.tweedle.lib.tweets.stream.TweetsStream
import com.tycz.tweedle.lib.tweets.stream.filter.Filter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BrowserTweetsViewModel: ViewModel() {

    private val token: String = "AAAAAAAAAAAAAAAAAAAAAAfKSAEAAAAAInN4lud2YJ3jqBRFykvq1ZUuPys%3DK1ApIYkbp4X90pqZlCa5tDd28Lo5PCH0GXO5puNn7fjzbxjDY6"
    private var listOfTweets: ArrayList<TweetsDataModel> = arrayListOf()
    private var dataTest = DataTest()
    var liveSpanValue = MutableLiveData<Int>()
    var newPin = MutableLiveData<TweetsDataModel>()

    val oAuth2 = OAuth2(token)
    val _tweetStream = TweetsStream(oAuth2)


    init { liveSpanValue.value = 30

        viewModelScope.launch {
            _tweetStream.addRules(setUpFilter("Android"))
            _tweetStream.startTweetStream().collect { value ->
                when(value){
                    is Response.Success -> Log.e("MY TWEET ==============> ",  value.data.toString())
                    is Response.Error ->Log.d("Error", value.exception.message!!)
                }
            }
        }
    }

    fun setUpFilter(textFilter: String): Rule {
        val filters:MutableList<Add> = mutableListOf()

        val filter: Filter = Filter.Builder()
            .addOperator(textFilter)
            .and()
            .setLanguage(Filter.ENGLISH)
            .build()

        filters.add(Add(filter.filter, textFilter))

        return Rule(filters)
    }

    fun searchTweets(textFilter: String) {





//        dataTest.liveSpanValue.observeForever{
//            newPin.value = it
//        }
//
//        dataTest.getDataFromServer("testData")

//        SearchTweetsUseCases().invoke(textFilter).observeForever{
//
//        }
    }

    fun addNewTweets(newTweetsDataModel: TweetsDataModel) = listOfTweets.add(newTweetsDataModel)

    fun secondToMillisecond(quantityInSeconds: Int) = (quantityInSeconds*1000).toLong()

    fun getTweetForTitle(title: String): TweetsDataModel? {
        listOfTweets.forEach {
            if (it.user == title){
                return it
            }
        }
        return null
    }
}
