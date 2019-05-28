package com.example.carlist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailsFragment extends Fragment {

    private View view;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details, container, false);
        return view;
    }

    //usupełnianie widkoku
    public void setCar(Car car) {
        TextView brand_name = (TextView) view.findViewById(R.id.brand_name);
        TextView color = (TextView) view.findViewById(R.id.color);
        TextView date = (TextView) view.findViewById(R.id.date);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        brand_name.setText(car.getBrand());
        color.setText(car.getColor());
        date.setText(car.getDate());
        if (car.getPhoto() != null) {
            image.setImageURI(car.getPhoto());
        } else {
            image.setImageResource(car.getResource());
        }
    }
}
