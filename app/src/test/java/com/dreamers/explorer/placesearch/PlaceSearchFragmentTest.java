package com.dreamers.explorer.placesearch;

import com.dreamers.explorer.R;
import com.dreamers.explorer.RetrofitService;
import com.dreamers.explorer.common.Constants;
import com.dreamers.explorer.placesearch.modal.PlaceSearchResult;
import com.dreamers.explorer.placesearch.presenter.PlaceSearchPresenter;
import com.dreamers.explorer.placesearch.view.PlaceSearchView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;

/**
 * Created by c029312 on 2/7/18.
 */
public class PlaceSearchFragmentTest {

    //interfaces are mocked with @Mock and MockitoAnnotations.initMocks(this)
    @Mock
    PlaceSearchView placeSearchView;
    //interfaces are mocked with @Mock and MockitoAnnotations.initMocks(this)
    @Mock
    RetrofitService retrofitService;
    //dependency injection classes are mocked with @InjectMocks and MockitoAnnotations.initMocks(this)
    @InjectMocks
    PlaceSearchPresenter placeSearchPresenter;
    TestSubscriber<PlaceSearchResult> placeSearchResultTestSubscriber;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });
        PlaceSearchView placeSearchView = Mockito.mock(PlaceSearchView.class);
        placeSearchPresenter.attach(placeSearchView, retrofitService);
        placeSearchResultTestSubscriber = new TestSubscriber<>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testForEmptySearchQuery() throws Exception {
        placeSearchPresenter.attach(placeSearchView, retrofitService);
        placeSearchPresenter.lookupForPlaces(Mockito.anyString());
        Mockito.verify(placeSearchView).showToastMessage(R.string.empty_search_query);
    }

    @Test
    public void testForNonEmptySearchQuery() throws Exception {
        placeSearchPresenter.attach(placeSearchView, retrofitService);
        String query = "Paris";
        PlaceSearchResult placeSearchResult = new PlaceSearchResult();
        placeSearchResult.status = "OK";
        Mockito.when(retrofitService.queryForPlace(query, Constants.QUERY_TYPES, Constants.API_KEY)).thenReturn(Flowable.just(placeSearchResult));
        placeSearchPresenter.lookupForPlaces(query);
        Mockito.verify(placeSearchView).onLookupQueryResponse(Mockito.anyBoolean(), Mockito.eq(placeSearchResult));
    }

    //Not yet working
    @Test
    public void testForSearchQueryService() throws Exception {
        String query = "Paris";
        PlaceSearchResult placeSearchResult = new PlaceSearchResult();
        placeSearchResult.status = "OK";
//        Mockito.when(placeSearchResult.status).thenReturn("OK");
        retrofitService.queryForPlace(query, Constants.QUERY_TYPES, Constants.API_KEY).subscribe(placeSearchResultTestSubscriber);
        placeSearchResultTestSubscriber.awaitTerminalEvent();
        placeSearchResultTestSubscriber
                .assertNoErrors()
                .assertValue(Mockito.any(PlaceSearchResult.class));
    }



}