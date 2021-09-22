package com.example.carappweek5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.carappweek5.provider.Car;
import com.example.carappweek5.provider.CarViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    ArrayList<Car> carArrayList;
    ArrayList<Car> carArrayFilterList;
    RecyclerView recyclerView;
    W6Adapter w6Adapter;
    W6Adapter w6AdapterFilter;

    private CarViewModel mCarViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        recyclerView = findViewById(R.id.listAllCarsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carArrayList = getIntent().getExtras().getParcelableArrayList("message");
        w6Adapter = new W6Adapter(carArrayList);
        recyclerView.setAdapter(w6Adapter);

        mCarViewModel = new ViewModelProvider(this).get(CarViewModel.class);
        mCarViewModel.getAllCars().observe(this, newData -> {
            w6Adapter.setCars(newData);
            w6Adapter.notifyDataSetChanged();
        });


        /*String receiveValue = getIntent().getStringExtra("message");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Car>>() {}.getType();
        carArrayList = gson.fromJson(receiveValue,type);*/



    }
}