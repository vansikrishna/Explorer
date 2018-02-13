package com.dreamers.explorer.placelist;

import com.dreamers.explorer.R;
import com.dreamers.explorer.RetrofitService;
import com.dreamers.explorer.common.Constants;
import com.dreamers.explorer.placedetail.modal.GeoLocation;
import com.dreamers.explorer.placedetail.modal.Geometry;
import com.dreamers.explorer.placedetail.modal.Place;
import com.dreamers.explorer.placedetail.modal.PlaceResult;
import com.dreamers.explorer.placelist.modal.PlaceListResult;
import com.dreamers.explorer.placelist.presenter.PlaceListPresenter;
import com.dreamers.explorer.placelist.view.PlaceListView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by c029312 on 2/12/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlaceListFragmentTest {
    //interfaces are mocked with @Mock and MockitoAnnotations.initMocks(this)
    @Mock
    RetrofitService retrofitService;
    //dependency injection classes are mocked with @InjectMocks and MockitoAnnotations.initMocks(this)
    @InjectMocks
    PlaceListPresenter placeListPresenter;
    //interfaces are mocked with @Mock and MockitoAnnotations.initMocks(this)
    @Mock
    PlaceListView placeListView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEmptyPlaceIdForList(){
        placeListPresenter.attach(placeListView, retrofitService);
        String placeId = null;
        placeListPresenter.lookupForNearbyPlaces(placeId);
        Mockito.verify(placeListView).showToastMessage(R.string.empty_place_id);
    }

    @Test
    public void testValidPlaceIdForList(){
        placeListPresenter.attach(placeListView, retrofitService);
        String placeId = "ChIJD7fiBh9u5kcRYJSMaMOCCwQ";
        PlaceResult placeResult = new PlaceResult();
        placeResult.status = "OK";
        Place place = new Place();
        Geometry geometry = new Geometry();
        GeoLocation location = new GeoLocation(48.85661400000001,2.3522219);
        geometry.location =location;
        place.geometry = geometry;
        placeResult.place = place;
        PlaceListResult placeListResult = new PlaceListResult();
        placeListResult.status = "OK";
        Mockito.when(retrofitService.searchForPlacesNearby(location.toLatLng(), Constants.RADIUS, Constants.API_KEY)).thenReturn(Flowable.just(placeListResult));
        Mockito.when(retrofitService.searchForPlaceInDetail(placeId, Constants.API_KEY)).thenReturn(Flowable.just(placeResult));
        placeListPresenter.lookupForNearbyPlaces(placeId);
//        Mockito.verify(placeListView).showToastMessage("ERROR");
        Mockito.verify(placeListView).onGetPlacesListResponse(Mockito.anyBoolean(), Mockito.any(PlaceListResult.class));
    }

}