package com.example.carlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CarListFragment.OnListFragmentInteractionListener {

    static final int CREATE_RANDOM_IMAGE = 1;
    static final int CREATE_PHOTO_IMAGE = 2;

    private SharedPreferences sharedPreferences;

    private List<Car> cars;//lista z samochodami
    private CarListFragment carListFragment;//obiekt reperezntujacy liste samochodow

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        carListFragment = (CarListFragment) getSupportFragmentManager().findFragmentById(R.id.car_list_fragment);//pobieramy widok aut

        FloatingActionButton create = findViewById(R.id.create); //+
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create_intent = new Intent(getApplicationContext(), CreateActivity.class);//odpalamy classe create activity
                create_intent.putExtra("take_photo", false);
                startActivityForResult(create_intent, CREATE_RANDOM_IMAGE);
            }
        });

        FloatingActionButton create_photo = findViewById(R.id.create_photo);//camera
        create_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create_intent = new Intent(getApplicationContext(), CreateActivity.class);
                create_intent.putExtra("take_photo", true);
                startActivityForResult(create_intent, CREATE_PHOTO_IMAGE);
            }
        });

        if (getResources().getConfiguration().orientation == 2) {
            DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);
            detailsFragment.getView().setVisibility(View.GONE);
        }

        cars = getData();
        refresh_list();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_RANDOM_IMAGE && resultCode == RESULT_OK) {
            Log.d("brand", data.getStringExtra("brand"));
            Log.d("color", data.getStringExtra("color"));
            Log.d("date", data.getStringExtra("date"));

            TypedArray images = getResources().obtainTypedArray(R.array.loading_images);
            int random_index = (int) (Math.random() * images.length());
            Car new_car = new Car(data.getStringExtra("brand"), data.getStringExtra("color"), data.getStringExtra("date"), images.getResourceId(random_index, R.drawable.car1));
            images.recycle();
            cars.add(new_car);
            saveData();
            refresh_list();

        } else if (requestCode == CREATE_PHOTO_IMAGE && resultCode == RESULT_OK) {
            Log.d("brand", data.getStringExtra("brand"));
            Log.d("color", data.getStringExtra("color"));
            Log.d("date", data.getStringExtra("date"));
            Uri uri = Uri.parse(data.getStringExtra("photo"));

            Car new_car = new Car(data.getStringExtra("brand"), data.getStringExtra("color"), data.getStringExtra("date"), R.drawable.car1);
            new_car.setPhoto(Uri.parse(data.getStringExtra("photo")));
            cars.add(new_car);
            saveData();
            refresh_list();
        }
    }

    public void refresh_list() {
        carListFragment.getCarViewAdapter().getValues().clear();
        carListFragment.getCarViewAdapter().getValues().addAll(cars);
        carListFragment.getCarViewAdapter().notifyDataSetChanged();
    }

    public ArrayList<Car> getData() {
        String json = sharedPreferences.getString("DATA", "[]");
        return new ArrayList<Car>(Arrays.asList(new Gson().fromJson(json, Car[].class)));
    }

    public void saveData() {
        String json = new Gson().toJson(cars);//JSON String
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DATA", json);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Car item) { //funckcja odpowiadajaca za akcj po kliknieciu na samochod
        if (getResources().getConfiguration().orientation == 1) {
            Intent detailsIntent = new Intent(this, DetailsActivity.class);
            detailsIntent.putExtra("car", item);
            startActivity(detailsIntent);
        } else {
            DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);
            detailsFragment.getView().setVisibility(View.VISIBLE);
            detailsFragment.setCar(item);
        }
    }

    @Override
    public void onListFragmentDeleted(Car item, int position) {  //funckcja odpowiadajaca za akcj po kliknieciu na smietnik
        for (Car car : cars) {
            if (car.equals(item)) {
                cars.remove(car);
                saveData();
                refresh_list();
                break;
            }
        }
        if (getResources().getConfiguration().orientation == 2) {
            DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);
            detailsFragment.getView().setVisibility(View.GONE);//znikanie rugieo fragmentu
        }
    }
}
