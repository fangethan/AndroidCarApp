package com.example.carappweek5.provider;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// interface like a protocol, only has method signatures, has no body
@Dao
public interface CarDAO {

    // select all data from the car entity
    @Query("select * from cars")
    LiveData<List<Car>> getAllCar();

    //Retrieve the current number of cars in the database and display it in a text view. (2m)
    @Query("select * from cars")
    Cursor getAllCarsCursor();

//    @Query("delete from cars where year < :year")
//    int deleteYearCar(String year);

    // insert that car into the database
    @Insert
    void addCar(Car car);

    // delete all data from the car entity
    @Query("delete FROM cars")
    void deleteAllCars();


}
