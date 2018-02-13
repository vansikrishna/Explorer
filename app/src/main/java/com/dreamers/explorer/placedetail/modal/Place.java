package com.dreamers.explorer.placedetail.modal;

import java.io.Serializable;

/**
 * Created by c029312 on 2/7/18.
 */

public class Place implements Serializable{

    public String id;
    public String name;
    public String place_id;
    public String url;
    public String[] types;
    public String formatted_address;
    public Geometry geometry;
    public Photo[] photos;
    public String reference;
    public float rating;
    public String icon;
}
