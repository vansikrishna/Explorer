package com.dreamers.explorer.placeimages;

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamers.explorer.R;
import com.dreamers.explorer.common.Constants;
import com.dreamers.explorer.placedetail.modal.Photo;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class PlaceImagesAdapter extends PagerAdapter {
    private Photo[] photos;
    private LayoutInflater inflater;
    private Context context;

    public PlaceImagesAdapter(Context context, Photo[] photos) {
        this.context = context;
        this.photos = photos;
        inflater = LayoutInflater.from(context);
        Fresco.initialize(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return photos.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.place_image_item, view, false);
        final SimpleDraweeView draweeView = (SimpleDraweeView) imageLayout.findViewById(R.id.placeImage);
        draweeView.setImageURI(Uri.parse(String.format("https://maps.googleapis.com/maps/api/place/photo?maxheight=%s&photoreference=%s&key=%s",
                String.valueOf(photos[position].height),
                String.valueOf(photos[position].photo_reference),
                Constants.API_KEY)));
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
