package com.dreamers.explorer.placedetail.modal;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by c029312 on 2/7/18.
 */

public class Photo implements Serializable, Parcelable{

    public int height;
    public int width;
    public String photo_reference;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeString(photo_reference);
    }

    private Photo(Parcel in){
        this.height = in.readInt();
        this.width = in.readInt();
        this.photo_reference = in.readString();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
