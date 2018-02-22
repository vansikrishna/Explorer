package com.dreamers.explorer.placedetail;

import com.dreamers.explorer.R;
import com.dreamers.explorer.RetrofitService;
import com.dreamers.explorer.common.Constants;
import com.dreamers.explorer.placedetail.modal.Place;
import com.dreamers.explorer.placedetail.modal.PlaceResult;
import com.dreamers.explorer.placedetail.presenter.PlaceDetailPresenter;
import com.dreamers.explorer.placedetail.view.PlaceDetailView;

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
 * Created by c029312 on 2/22/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlaceDetailFragmentTest {

    @Mock
    RetrofitService retrofitService;
    @InjectMocks
    PlaceDetailPresenter placeDetailPresenter;

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
        retrofitService = null;
        placeDetailPresenter = null;
    }

    @Test
    public void testEmptyPlaceId(){
        String placeId = null;
        PlaceDetailView placeDetailView = Mockito.mock(PlaceDetailView.class);
        placeDetailPresenter.attach(placeDetailView, retrofitService);
        placeDetailPresenter.getPlaceDetail(placeId);
        Mockito.verify(placeDetailPresenter.getView()).showToastMessage(R.string.empty_place_id);
    }

    @Test
    public void testDummyPlaceIdForDetail(){
        String placeId = "Abcde";
        PlaceDetailView placeDetailView = Mockito.mock(PlaceDetailView.class);
        placeDetailPresenter.attach(placeDetailView, retrofitService);
        PlaceResult placeResultItem = new PlaceResult();
        placeResultItem.status = "INVALID REQUEST";
        Mockito.when(retrofitService.searchForPlaceInDetail(placeId, Constants.API_KEY)).thenReturn(Flowable.just(placeResultItem));
        placeDetailPresenter.getPlaceDetail(placeId);
        Mockito.verify(placeDetailPresenter.getView()).showToastMessage("INVALID REQUEST");
    }

    @Test
    public void testValidPlaceIdForDetail(){
        String placeId = "cvRamOLSKLLSASjnasnASASK";
        PlaceDetailView placeDetailView = Mockito.mock(PlaceDetailView.class);
        placeDetailPresenter.attach(placeDetailView, retrofitService);
        PlaceResult placeResultItem = new PlaceResult();
        placeResultItem.status = "OK";
        placeResultItem.place = new Place();
        Mockito.when(retrofitService.searchForPlaceInDetail(placeId, Constants.API_KEY)).thenReturn(Flowable.just(placeResultItem));
        placeDetailPresenter.getPlaceDetail(placeId);
        Mockito.verify(placeDetailPresenter.getView()).onGetPlaceDetailResponse(placeResultItem.place);
    }

}