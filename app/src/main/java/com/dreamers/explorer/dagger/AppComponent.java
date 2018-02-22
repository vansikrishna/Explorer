package com.dreamers.explorer.dagger;

import com.dreamers.explorer.HomeActivity;
import com.dreamers.explorer.HomeApp;
import com.dreamers.explorer.placedetail.PlaceDetailFragment;
import com.dreamers.explorer.placelist.PlaceListFragment;
import com.dreamers.explorer.placesearch.PlaceSearchFragment;
import com.dreamers.explorer.placesearch.presenter.PlaceSearchPresenter;

import dagger.Component;

/**
 * Created by c029312 on 2/7/18.
 */
@Component(modules = {NetworkModule.class, AppModule.class})
public interface AppComponent {

    void inject(HomeApp homeApp);
    void inject(HomeActivity homeActivity);
    void inject(PlaceSearchFragment placeSearchFragment);
    void inject(PlaceSearchPresenter presenter);

    void inject(PlaceListFragment placeListFragment);

    void inject(PlaceDetailFragment placeDetailFragment);
}
