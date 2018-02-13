package com.dreamers.explorer.placesearch;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dreamers.explorer.R;
import com.dreamers.explorer.placesearch.modal.Prediction;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fiaa on 2/17/2016.
 */
public class PlaceSearchResultAdapter extends ArrayAdapter<Prediction> {

    private ArrayList<Prediction> items;
    private Activity activity;
    private LayoutInflater mInflater;

    public PlaceSearchResultAdapter(Activity activity, int resourceId,
                           ArrayList<Prediction> itemsList) {
        super(activity, resourceId, itemsList);
        this.activity = activity;
        items = itemsList;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View view, ViewGroup parent) {
        DataViewHolder holder;
        if (view != null) {
            holder = (DataViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.search_result_list_item, parent, false);
            holder = new DataViewHolder(view);
            view.setTag(holder);
        }
        Prediction prediction = items.get(position);
        view.setId(position);
        view.setTag(R.id.prediction, prediction);
        holder.label.setText(prediction.description);
        return view;
    }

    public void setItems(ArrayList<Prediction> itemsList) {
        clearItems();
        items.addAll(itemsList);
        notifyDataSetChanged();
    }

    private void clearItems() {
        this.items.clear();
    }

    static class DataViewHolder{
        @BindView(R.id.label)
        TextView label;

        DataViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
