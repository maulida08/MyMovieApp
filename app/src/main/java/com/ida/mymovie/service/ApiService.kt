package com.ida.mymovie.service

import com.ida.mymovie.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("movie/popular?api_key=7fd85d1ae16130aa2bbe3d705027b5be")
    fun getAllMoviePopular() : Call<GetAllMoviePopular>

    @GET("movie/upcoming?api_key=7fd85d1ae16130aa2bbe3d705027b5be")
    fun getAllMovieUpcoming() : Call<GetAllMovieUpcoming>

    @GET("movie/top_rated?api_key=7fd85d1ae16130aa2bbe3d705027b5be")
    fun getAllMovieTop() : Call<GetAllMovieTopRated>

    @GET("movie/{movie_id}?api_key=7fd85d1ae16130aa2bbe3d705027b5be")
    fun getALLDetail(@Path("movie_id") movie_id: Int) : Call<GetDetailMovie>
}