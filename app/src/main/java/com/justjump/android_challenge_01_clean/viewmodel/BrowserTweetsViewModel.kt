package com.justjump.android_challenge_01_clean.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justjump.domain.tweets.TweetsDataModel
import com.justjump.framework.DataTest
import com.justjump.usecases.SearchTweetsUseCases

class BrowserTweetsViewModel: ViewModel() {

    private var listOfTweets: ArrayList<TweetsDataModel> = arrayListOf()
    private var dataTest = DataTest()
    var liveSpanValue = MutableLiveData<Int>()
    var newPin = MutableLiveData<TweetsDataModel>()

    init { liveSpanValue.value = 30 }

    fun searchTweets(textFilter: String) {
        dataTest.liveSpanValue.observeForever{
            newPin.value = it
        }

        dataTest.getDataFromServer("testData")

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
