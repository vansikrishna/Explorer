package com.dreamers.explorer.placedetail.modal;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by c029312 on 2/7/18.
 */

public class GeoLocation implements Serializable, Parcelable{

    public double lat;
    public double lng;

    public GeoLocation(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Storing the Student data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }

    /**
     * Retrieving Student data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private GeoLocation(Parcel in){
        this.lat = in.readDouble();
        this.lng = in.readDouble();
    }

    public static final Parcelable.Creator<GeoLocation> CREATOR = new Parcelable.Creator<GeoLocation>() {

        @Override
        public GeoLocation createFromParcel(Parcel source) {
            return new GeoLocation(source);
        }

        @Override
        public GeoLocation[] newArray(int size) {
            return new GeoLocation[size];
        }
    };

    public String toLatLng() {
        return lat+","+lng;
    }
}
