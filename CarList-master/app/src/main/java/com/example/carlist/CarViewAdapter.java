package com.example.carlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carlist.CarListFragment.OnListFragmentInteractionListener;

import java.util.List;

public class CarViewAdapter extends RecyclerView.Adapter<CarViewAdapter.ViewHolder> {

    private final List<Car> mValues;
    private final OnListFragmentInteractionListener mListener;

    public CarViewAdapter(List<Car> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public List<Car> getValues() {
        return mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_car, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //ładowanie i odswiezanie listy elementow typu car
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.name.setText(mValues.get(position).getBrand());
        if (mValues.get(position).getPhoto() != null) {//ladowanie zdjecia lub obrazku
            holder.image.setImageURI(mValues.get(position).getPhoto());
        } else {
            holder.image.setImageResource(mValues.get(position).getResource());
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentDeleted(holder.mItem, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
//pojedynczy wiersz naliscie
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final ImageView image;
        public final ImageView delete_icon;
        public Car mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.name_text);
            image = (ImageView) view.findViewById(R.id.image);
            delete_icon = (ImageView) view.findViewById(R.id.delete_icon);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }
    }
}
