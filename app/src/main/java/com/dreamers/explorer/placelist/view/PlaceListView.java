package com.dreamers.explorer.placelist.view;

import android.support.annotation.StringRes;

import com.dreamers.explorer.common.BaseView;
import com.dreamers.explorer.placelist.modal.PlaceListResult;

/**
 * Created by c029312 on 2/7/18.
 */

public interface PlaceListView extends BaseView {
    void onGetPlacesListResponse(boolean success, PlaceListResult placeListResult);
    void showToastMessage(@StringRes int resId);
    void showToastMessage(String message);
}
