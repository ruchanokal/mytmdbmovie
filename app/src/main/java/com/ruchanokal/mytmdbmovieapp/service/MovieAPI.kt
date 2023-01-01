package com.ruchanokal.mytmdbmovieapp.service

import com.ruchanokal.mytmdbmovieapp.model.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    // Örnek Linkler//

    // Search Link : https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    // Upcoming --> https://api.themoviedb.org/3/movie/upcoming?api_key=<<api_key>>&language=en-US&page=1
    // TopRated --> https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1
    // NowPlaying --> https://api.themoviedb.org/3/movie/now_playing?api_key=<<api_key>>&language=en-US&page=1
    // Popular --> https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1
    // GetLatest --> https://api.themoviedb.org/3/movie/latest?api_key=<<api_key>>&language=en-US

    @GET("search/movie?api_key=3821d63954d2b0afa52a3e29b642271e")
    fun getSearchData( @Query("query") movieName : String ) : Single<MovieModel>

    @GET("genre/movie/list?api_key=3821d63954d2b0afa52a3e29b642271e")
    fun getGenreData() : Observable<Genres>

    @GET("movie/upcoming?api_key=3821d63954d2b0afa52a3e29b642271e")
    fun getUpcomingData() : Observable<UpcomingMovies>

    @GET("movie/top_rated?api_key=3821d63954d2b0afa52a3e29b642271e")
    fun getTopRated() : Observable<TopRated>

    @GET("movie/now_playing?api_key=3821d63954d2b0afa52a3e29b642271e")
    fun getNowPlaying() : Observable<NowPlaying>

    @GET("movie/popular?api_key=3821d63954d2b0afa52a3e29b642271e")
    fun getPopular() : Observable<Popular>

    @GET("movie/latest?api_key=3821d63954d2b0afa52a3e29b642271e")
    fun getLatest() : Observable<Latest>
}