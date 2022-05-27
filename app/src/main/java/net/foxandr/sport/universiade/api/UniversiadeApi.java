package net.foxandr.sport.universiade.api;

import net.foxandr.sport.universiade.ui.home.games.GamesDTO;
import net.foxandr.sport.universiade.ui.home.games.events.EventsDTO;
import net.foxandr.sport.universiade.ui.home.games.mainsports.SportsDTO;
import net.foxandr.sport.universiade.ui.lostfound.model.LostFoundDTO;
import net.foxandr.sport.universiade.ui.medals.model.MedalsDTO;
import net.foxandr.sport.universiade.ui.news.model.NewsDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UniversiadeApi {

    @GET("news?")
    Call<List<NewsDTO>> getNewsByLocale(@Query("locale") String locale);

    @GET("games/1/medals?")
    Call<List<MedalsDTO>> getMedalsByLocale(@Query("locale") String locale);

    @GET("games/{gameId}/sports?")
    Call<List<SportsDTO>> getSportsByGameIdAndLocale(@Path("gameId") Long gameId,
                                                     @Query("locale") String locale);

    @GET("games?")
    Call<List<GamesDTO>> getGamesByLocale(@Query("locale") String locale);


    @GET("games/{gameId}/sports/{sportId}/events?")
    Call<List<EventsDTO>> getEventsByGameIdAndSportIdAndLocale(@Path("gameId") Long gameId,
                                                               @Path("sportId") Long sportId,
                                                               @Query("locale") String locale);

    @Multipart
    @POST("lost-found")
    Call<Object> postLostfoundRequest(@Part RequestBody lostFoundDTO,
                                      @Part MultipartBody.Part image);


}
