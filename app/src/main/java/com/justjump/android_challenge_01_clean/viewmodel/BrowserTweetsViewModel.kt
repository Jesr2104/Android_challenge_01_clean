package com.justjump.android_challenge_01_clean.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justjump.data.datasources.GetterTweets
import com.justjump.data.datasources.remote.RealTimeTweets
import com.justjump.data.repositories.SearchRepository
import com.justjump.domain.tweets.TweetsDataModel
import com.justjump.usecases.SearchTweetsUseCases

class BrowserTweetsViewModel : ViewModel() {

    private var listOfTweets: ArrayList<TweetsDataModel> = arrayListOf()
    private var dataTest = DataTest()
    var liveSpanValue = MutableLiveData<Int>()
    var newPin = MutableLiveData<TweetsDataModel>()

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
