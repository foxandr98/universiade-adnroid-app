package net.foxandr.sport.universiade.api;

import net.foxandr.sport.universiade.ui.home.sports.SportsDTO;
import net.foxandr.sport.universiade.ui.medals.model.MedalsDTO;
import net.foxandr.sport.universiade.ui.news.model.NewsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UniversiadeApi {

    @GET("news?")
    Call<List<NewsDTO>> getNewsByLocale(@Query("locale") String locale);

    @GET("games/1/medals?")
    Call<List<MedalsDTO>> getMedalsByLocale(@Query("locale") String locale);

    @GET("games/1/sports?")
    Call<List<SportsDTO>> getSportsByLocale(@Query("locale") String locale);


}
