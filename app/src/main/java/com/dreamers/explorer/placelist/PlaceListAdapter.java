package com.dreamers.explorer.placelist;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dreamers.explorer.R;
import com.dreamers.explorer.common.Constants;
import com.dreamers.explorer.placedetail.modal.Place;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fiaa on 2/17/2016.
 */
public class PlaceListAdapter extends ArrayAdapter<Place> {

    private ArrayList<Place> items;
    private Activity activity;
    private LayoutInflater mInflater;

    public PlaceListAdapter(Activity activity, int resourceId,
                            ArrayList<Place> itemsList) {
        super(activity, resourceId, itemsList);
        Fresco.initialize(activity);
        this.activity = activity;
        items = itemsList;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View view, ViewGroup parent) {
        DataViewHolder holder;
        if (view != null) {
            holder = (DataViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.place_list_item, parent, false);
            holder = new DataViewHolder(view);
            view.setTag(holder);
        }
        Place place = items.get(position);
        view.setId(position);
        view.setTag(R.id.place, place);
        holder.name.setText(place.name);
        if(place.photos != null && place.photos.length > 0) {
            holder.draweeView.setImageURI(Uri.parse(String.format("https://maps.googleapis.com/maps/api/place/photo?maxheight=%s&photoreference=%s&key=%s",
                    String.valueOf(place.photos[0].height),
                    String.valueOf(place.photos[0].photo_reference),
                    Constants.API_KEY)));
        }
        if(place.icon != null && place.icon.length() > 0){
            holder.placeTypeDraweeView.setImageURI(Uri.parse(place.icon));
        }
        holder.ratingBar.setRating(place.rating);
        return view;
    }

    public void setItems(ArrayList<Place> itemsList) {
        clearItems();
        items.addAll(itemsList);
        notifyDataSetChanged();
    }

    private void clearItems() {
        this.items.clear();
    }

    static class DataViewHolder{
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.placeImage)
        SimpleDraweeView draweeView;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.placeType)
        SimpleDraweeView placeTypeDraweeView;

        DataViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
