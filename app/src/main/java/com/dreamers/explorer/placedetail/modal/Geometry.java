package com.dreamers.explorer.placedetail.modal;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by c029312 on 2/7/18.
 */

public class Geometry implements Serializable, Parcelable{
    public GeoLocation location;

    public Geometry(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(location, flags);
    }

    private Geometry(Parcel in){
        this.location = in.readParcelable(GeoLocation.class.getClassLoader());
    }

    public static final Parcelable.Creator<Geometry> CREATOR = new Creator<Geometry>() {
        @Override
        public Geometry createFromParcel(Parcel source) {
            return new Geometry(source);
        }

        @Override
        public Geometry[] newArray(int size) {
            return new Geometry[size];
        }
    };
}
