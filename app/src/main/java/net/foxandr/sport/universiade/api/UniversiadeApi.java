package net.foxandr.sport.universiade.api;

import net.foxandr.sport.universiade.ui.home.games.GamesDTO;
import net.foxandr.sport.universiade.ui.home.games.events.EventsDTO;
import net.foxandr.sport.universiade.ui.home.games.events.competitors.CompetitorsResultsDTO;
import net.foxandr.sport.universiade.ui.home.games.sports.SportsDTO;
import net.foxandr.sport.universiade.ui.users.LoggedInUserDTO;
import net.foxandr.sport.universiade.ui.lostfound.model.LostFoundDTOResponse;
import net.foxandr.sport.universiade.ui.medals.model.MedalsDTO;
import net.foxandr.sport.universiade.ui.news.model.NewsDTO;
import net.foxandr.sport.universiade.ui.users.volunteers.VolunteerScheduleDTO;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UniversiadeApi {

    @GET("news?")
    Call<List<NewsDTO>> getNewsByLocale(@Query("locale") String locale);

    @GET("games/{gameId}/medals?")
    Call<List<MedalsDTO>> getMedalsByLocale(@Path("gameId") Long gameId,
                                            @Query("locale") String locale);

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
    Call<LostFoundDTOResponse> postLostfoundRequest(@Part("lostFoundDTO") Map<String, String> lostFoundDTO,
                                                    @Part MultipartBody.Part imageFile);


    @GET("images/{imageUuid}")
    Call<ResponseBody> getImageBytesByUuid(@Path("imageUuid") String imageUuid);


    @GET("games/{gameId}/events/{eventId}/results?")
    Call<List<CompetitorsResultsDTO>> getCompetitorsResultsByGameIdAndEventsId(@Path("gameId") Long gameId,
                                                                               @Path("eventId") Long eventId,
                                                                               @Query("locale") String locale);


    @GET("login")
    Call<LoggedInUserDTO> loginAndGetLoggedInUserDTO(@Header("Authorization") String credential);


    @GET("volunteer/{volunteerName}/schedule")
    Call<List<VolunteerScheduleDTO>> getVolunteerSchedule(@Header("Authorization") String credentials,
                                                    @Path("volunteerName") String volunteerName);


}
