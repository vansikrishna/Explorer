package com.dreamers.explorer.placedetail.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by c029312 on 2/7/18.
 */

public class PlaceResult implements Serializable{

    public String status;
    @SerializedName("result")
    public Place place;
}
