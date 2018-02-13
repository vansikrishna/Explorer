package com.dreamers.explorer;

import com.dreamers.explorer.common.Constants;
import com.dreamers.explorer.placedetail.modal.PlaceResult;
import com.dreamers.explorer.placelist.modal.PlaceListResult;
import com.dreamers.explorer.placesearch.modal.PlaceSearchResult;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by c029312 on 1/19/18.
 */

public interface RetrofitService {

    @GET(Constants.ENDPOINT_PLACE_QUERY_LOOKUP)
    Flowable<PlaceSearchResult> queryForPlace(@Query("input") String query,
                                              @Query("types") String types,
                                              @Query("key") String apiKey);

    @GET(Constants.ENDPOINT_PLACE_DETAIL)
    Flowable<PlaceResult> searchForPlaceInDetail(@Query("placeid") String placeId,
                                                   @Query("key") String apiKey);

    @GET(Constants.ENDPOINT_PLACES_NEARBY)
    Flowable<PlaceListResult> searchForPlacesNearby(@Query("location") String location,
                                                    @Query("radius") long radius,
                                                    @Query("key")String apiKey);

}
