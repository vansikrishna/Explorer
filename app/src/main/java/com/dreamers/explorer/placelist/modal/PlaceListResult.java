package com.dreamers.explorer.placelist.modal;

import com.dreamers.explorer.placedetail.modal.Place;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by c029312 on 2/7/18.
 */

public class PlaceListResult implements Serializable{
    public String status;
    @SerializedName("results")
    public Place[] places;
    public String next_page_token;
}
