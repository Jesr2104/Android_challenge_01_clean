package com.justjump.framework

import androidx.lifecycle.MutableLiveData
import com.justjump.domain.tweets.TweetsDataModel

class DataTest {

    var liveSpanValue = MutableLiveData<TweetsDataModel>()

    fun getDataFromServer(textFilter: String){

        // aqui se tiene que ejecutar un proceso en segundo plano que con obtegan el nuevo tweet que se va a guardar en el livedata
        // el filtro es la cadena de caracteres que se tiene que incluir en la busqueda del tweet

        liveSpanValue.value = DummyData().getTweet()
    }
}