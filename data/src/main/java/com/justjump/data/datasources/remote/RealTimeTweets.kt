package com.justjump.data.datasources.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.justjump.domain.tweets.TweetsDataModel
import com.justjump.data.datasources.remote.utils.DummyDataLocations
import com.tycz.tweedle.lib.dtos.tweet.TweetData
import kotlinx.coroutines.flow.collect

class RealTimeTweets {

    var newTweetItem = MutableLiveData<TweetsDataModel>()
    private val getTwitterData = Tweedle()

    suspend fun getData(textFilter: String){
        getTwitterData.makeFlowRegister(textFilter)
            .collect { newTweetItem.value = it.toMap(DummyDataLocations())
                Log.e("hola->>>>>>>>", it.text )}
    }

    // I have filled in the location values with test values as most tweets do not have this information
    private fun TweetData.toMap(dataTest: DummyDataLocations): TweetsDataModel {
        val location = dataTest.geoLocation()
        return TweetsDataModel(
            id = this.id,
            location_latitude = location.latitude,
            location_longitude = location.longitude,
            text = this.text,
            user = this.author_id.orEmpty()
        )
    }
}