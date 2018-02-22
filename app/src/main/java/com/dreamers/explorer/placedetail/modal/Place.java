package com.dreamers.explorer.placedetail.modal;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by c029312 on 2/7/18.
 */

public class Place implements Serializable, Parcelable{

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

    public Place(){
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
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(place_id);
        dest.writeString(url);
        dest.writeStringArray(types);
        dest.writeString(formatted_address);
        dest.writeParcelable(geometry, flags);
        dest.writeParcelableArray(photos, flags);
        dest.writeString(reference);
        dest.writeString(icon);
        dest.writeFloat(rating);
    }

    /**
     * Retrieving Student data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private Place(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.place_id = in.readString();
        this.url = in.readString();
        this.types = (String[]) in.readArray(String.class.getClassLoader());
        this.formatted_address = in.readString();
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
        this.photos = (Photo[]) in.readArray(Photo.class.getClassLoader());
        this.reference = in.readString();
        this.icon = in.readString();
        this.rating = in.readFloat();
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {

        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

}
