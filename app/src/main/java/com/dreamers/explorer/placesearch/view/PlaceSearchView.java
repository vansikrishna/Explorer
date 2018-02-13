package com.dreamers.explorer.placesearch.view;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.EditText;

import com.dreamers.explorer.common.BaseView;
import com.dreamers.explorer.placesearch.modal.PlaceSearchResult;

/**
 * Created by c029312 on 2/7/18.
 */

public interface PlaceSearchView extends BaseView {

    void onLookupQueryResponse(boolean success, PlaceSearchResult placeSearchResult);
    void showToastMessage(@StringRes int resId);
    void showToastMessage(String message);

    EditText getSearchText();

    Context getContext();
}
