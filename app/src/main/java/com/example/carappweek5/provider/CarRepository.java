package com.example.carappweek5.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CarRepository {
    private CarDAO mCarDao;
    private LiveData<List<Car>> mAllCars;
    private LiveData<List<String>> mAllPrices;

    CarRepository(Application application) {
        // can access getDatabase because it is static
        CarDatabase db = CarDatabase.getDatabase(application);
        //
        mCarDao = db.carDAO();
        mAllCars = mCarDao.getAllCar();
    }

    LiveData<List<Car>> getAllCustomers() {
        return mAllCars;
    }

    void insert(Car car) {
        CarDatabase.databaseWriteExecutor.execute(() -> mCarDao.addCar(car));
    }

    void deleteAll(){
        CarDatabase.databaseWriteExecutor.execute(()->{
            mCarDao.deleteAllCars();
        });
    }
}
