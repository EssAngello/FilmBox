package com.example.filmbox;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    @GET("movie/popular")
    Call<MovieRespon> getPopularMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                       @Query("page") int page);

    @GET("movie/now_playing")
    Call<MovieRespon> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                          @Query("page") int page);

    @GET("movie/upcoming")
    Call<MovieRespon> getUpcomingMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                        @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieRespon> getTopRatedMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                        @Query("page") int page);

    @GET("search/movie")
    Call<MovieRespon> searchMovie(@Query("api_key") String apiKey, @Query("language") String language,
                                  @Query("query") String query, @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") String id, @Query("language") String language, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<MovieCredits> getMovieCredits(@Path("movie_id") String id, @Query("language") String language, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/watch/providers")
    Call<MovieProviders> getMovieProviders(@Path("movie_id") String id, @Query("api_key") String apiKey);

}
