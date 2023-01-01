package com.ruchanokal.mytmdbmovieapp.service

import com.ruchanokal.mytmdbmovieapp.model.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieAPIService {

    // Örnek Linkler //

    // Search Link : https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    // Upcoming --> https://api.themoviedb.org/3/movie/upcoming?api_key=<<api_key>>&language=en-US&page=1
    // TopRated --> https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1
    // NowPlaying --> https://api.themoviedb.org/3/movie/now_playing?api_key=<<api_key>>&language=en-US&page=1
    // Popular --> https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1
    // GetLatest --> https://api.themoviedb.org/3/movie/latest?api_key=<<api_key>>&language=en-US

    private val BASE_URL = "https://api.themoviedb.org/3/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(MovieAPI::class.java)

    fun getSearchDataService(movieName : String) : Single<MovieModel> {
        return api.getSearchData(movieName)
    }

    fun getUpcomingMovieDataService() : Observable<UpcomingMovies> {
        return api.getUpcomingData()
    }

    fun getTopRatedMovieDataService() : Observable<TopRated> {
        return api.getTopRated()
    }

    fun getPopularMovieDataService() : Observable<Popular> {
        return api.getPopular()
    }

    fun getNowPlayingMovieDataService() : Observable<NowPlaying> {
        return api.getNowPlaying()
    }

    fun getLatestMovieDataService() : Observable<Latest> {
        return api.getLatest()
    }

    fun getGenresData() : Observable<Genres> {
        return api.getGenreData()
    }

}