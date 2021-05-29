package com.example.fintonicbeer.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fintonicbeer.model.Beer
import com.example.fintonicbeer.model.BeerDataBase
import com.example.fintonicbeer.service.BeerApiService
import com.example.fintonicbeer.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {
    private var prefHelper = SharedPreferencesHelper(getApplication())

    // 5 minutos
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    private val beerService = BeerApiService()
    private val disposable = CompositeDisposable()

    val beersList = MutableLiveData<List<Beer>>()
    val beersLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        checkCacheDuration()
        val updateTime = prefHelper.getUpdatedTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }
    }

    fun refreshBypassCache() {
        fetchFromRemote()
    }

    private fun checkCacheDuration() {
        val cachePreference = prefHelper.getCacheDuration()
        try {
            val cachePreferenceInt = cachePreference?.toInt() ?: 5 * 60
            refreshTime = cachePreferenceInt.times(1000 * 1000 * 1000L)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val beers = BeerDataBase(getApplication()).beerDAO().getAllBeers()
            beersRetrieved(beers)
            Toast.makeText(getApplication(), "desde la DB :)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            beerService.getBeers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Beer>>() {
                    override fun onSuccess(beersList: List<Beer>) {
                        storeBeersLocally(beersList)
                        Toast.makeText(getApplication(), "desde la API :)", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        beersLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun storeBeersLocally(beersList: List<Beer>) {
        launch {
            val dao = BeerDataBase(getApplication()).beerDAO()
            dao.deleteAllBeers()
            val result = dao.insertAll(*beersList.toTypedArray())
            var i = 0
            while (i < beersList.size) {
                beersList[i].uuid = result[i].toInt()
                ++i
            }
            beersRetrieved(beersList)
        }
        // save the time
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    /**
     *
     * @param beers
     */
    private fun beersRetrieved(beers: List<Beer>) {
        beersList.value = beers
        beersLoadError.value = false
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}