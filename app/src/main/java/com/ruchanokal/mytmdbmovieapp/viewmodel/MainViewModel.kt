package com.ruchanokal.mytmdbmovieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ruchanokal.mytmdbmovieapp.activities.MainActivity
import com.ruchanokal.mytmdbmovieapp.model.*
import com.ruchanokal.mytmdbmovieapp.service.MovieAPIService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

val genresHashMap = hashMapOf<Int,String>()

class MainViewModel : ViewModel() {

    private var TAG = "MainViewModel"
    private val movieAPIService = MovieAPIService()
    private val compositeDisposable  = CompositeDisposable()

    val searchedMovieData = MutableLiveData<MovieModel>()
    val popularMovieDetailsData = MutableLiveData<Popular>()
    val latestMovieDetailsData = MutableLiveData<Latest>()
    val upcomingMovieDetailsData = MutableLiveData<UpcomingMovies>()
    val topRatedMovieDetailsData = MutableLiveData<TopRated>()
    val nowPlayingMovieDetailsData = MutableLiveData<NowPlaying>()

    val movieError = MutableLiveData<Boolean>()
    val movieLoading = MutableLiveData<Boolean>()


    //uygulama ilk açıldığında film datalarını çekmeye yarar
    fun getFilmDatas() {

        Log.e(TAG,"get movie datas")

        movieLoading.value  = true

        val requests = ArrayList<Observable<*>>()

        requests.add(movieAPIService.getPopularMovieDataService())
        requests.add(movieAPIService.getLatestMovieDataService())
        requests.add(movieAPIService.getUpcomingMovieDataService())
        requests.add(movieAPIService.getTopRatedMovieDataService())
        requests.add(movieAPIService.getNowPlayingMovieDataService())
        requests.add(movieAPIService.getGenresData())


        //Burada Observable.zip yaparak birden fazla requesti doğru bir şekilde yapıyoruz. Bir request tamamlandıktan
        //sonra diğer requeste geçiliyor. Bu sayede Network Exception gerçekleşmiyor ve doğru sonuçları almış
        //oluyoruz.

        compositeDisposable.add(Observable
            .zip(requests) {

                if( it != null && it.size > 4){

                    movieLoading.value = false
                    movieError.value = false

                    val popular = it.get(0) as Popular
                    val latest = it.get(1) as Latest
                    val upcomingMovies = it.get(2) as UpcomingMovies
                    val topRated = it.get(3) as TopRated
                    val nowPlaying = it.get(4) as NowPlaying
                    val genres = it.get(5) as Genres

                    popularMovieDetailsData.value = popular
                    latestMovieDetailsData.value = latest
                    upcomingMovieDetailsData.value = upcomingMovies
                    topRatedMovieDetailsData.value = topRated
                    nowPlayingMovieDetailsData.value = nowPlaying

                    if (genres.genres != null && genres.genres.size > 0) {

                        for (a in 0..genres.genres.size-1){
                            genresHashMap.put(genres.genres.get(a).id,genres.genres.get(a).name)
                        }

                    }

                }


                Any()

            }
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }) {

                it.stackTrace
                Log.e(TAG,"error: " + it)
                movieError.value = true
                movieLoading.value  = false
            })

    }

    //Bu fonksiyonda arama yapılan film datalarının çekilmesi sağlanır
    fun getSearchMovieDatas(movieName : String) {

        Log.e(TAG,"get search movie datas")

        movieLoading.value  = true

        compositeDisposable.add(movieAPIService
            .getSearchDataService(movieName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<MovieModel>(){
                override fun onSuccess(t: MovieModel) {

                    movieLoading.value = false
                    movieError.value = false
                    searchedMovieData.value = t

                }

                override fun onError(e: Throwable) {
                    movieLoading.value = false
                    movieError.value = true
                }


            }))


    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}