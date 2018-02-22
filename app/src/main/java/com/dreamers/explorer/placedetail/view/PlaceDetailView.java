package com.dreamers.explorer.placedetail.view;

import android.support.annotation.StringRes;

import com.dreamers.explorer.common.BaseView;
import com.dreamers.explorer.placedetail.modal.Place;

/**
 * Created by c029312 on 2/22/18.
 */

public interface PlaceDetailView extends BaseView {
    void onGetPlaceDetailResponse(Place place);
    void showToastMessage(@StringRes int resId);
    void showToastMessage(String message);
}
