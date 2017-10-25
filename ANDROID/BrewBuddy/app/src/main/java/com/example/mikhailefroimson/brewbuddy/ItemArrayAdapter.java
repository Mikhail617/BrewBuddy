package com.example.mikhailefroimson.brewbuddy;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikhail.efroimson on 10/23/2017.
 */

public class ItemArrayAdapter extends ArrayAdapter {
    public ItemArrayAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
    private List breweries = new ArrayList();

    static class ItemViewHolder {
        TextView name;
        TextView address;
    }

    public void add(String[] object) {
        breweries.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.breweries.size();
    }

    @Override
    public String[] getItem(int index) {
        return (String[]) this.breweries.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_layout, parent, false);
            viewHolder = new ItemViewHolder();
            viewHolder.name = (TextView) row.findViewById(R.id.name);
            viewHolder.address = (TextView) row.findViewById(R.id.address);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder)row.getTag();
        }
        String[] stat = getItem(position);
        viewHolder.name.setText(stat[0]);
        viewHolder.address.setText(stat[1]);
        return row;
    }
}