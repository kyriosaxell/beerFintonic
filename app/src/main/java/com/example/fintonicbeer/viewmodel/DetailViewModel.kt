package com.example.fintonicbeer.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.fintonicbeer.model.Beer
import com.example.fintonicbeer.model.BeerDataBase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val beerListData = MutableLiveData<Beer>()

    /**
     * @param uuid Indetifier of a beer in list
     */
    fun fetch(uuid: Int) {
        launch {
            val beer = BeerDataBase(getApplication()).beerDAO().getBeerById(uuid)
            beerListData.value = beer
        }
    }
}