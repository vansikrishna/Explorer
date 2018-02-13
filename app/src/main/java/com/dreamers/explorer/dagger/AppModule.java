package com.dreamers.explorer.dagger;

/**
 * Created by c029312 on 2/7/18.
 */

import com.dreamers.explorer.placelist.presenter.PlaceListPresenter;
import com.dreamers.explorer.placesearch.PlaceSearchFragment;
import com.dreamers.explorer.placesearch.presenter.PlaceSearchPresenter;
import com.dreamers.explorer.placesearch.view.PlaceSearchView;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    public PlaceSearchPresenter providePlaceSearchPresenter(){
        return new PlaceSearchPresenter();
    }

    @Provides
    public PlaceListPresenter providePlaceListPresenter(){
        return new PlaceListPresenter();
    }

    @Provides
    public PlaceSearchView provideSearchView(PlaceSearchFragment fragment){
        return (PlaceSearchView) fragment;
    }

    @Provides
    public PlaceSearchFragment providePlaceSearchFragment(){
        return new PlaceSearchFragment();
    }
}
