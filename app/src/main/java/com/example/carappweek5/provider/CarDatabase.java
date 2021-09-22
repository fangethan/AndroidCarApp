package com.example.carappweek5.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// abstract class is a restricted class that cannot be used to create objects
// cannot use the new keyword
@Database(entities = {Car.class}, version = 1)
public abstract class CarDatabase extends RoomDatabase {
    public static final String CUSTOMER_DATABASE_NAME = "car_database";

    public abstract CarDAO carDAO();

    // marking the instance as volatile to ensure atomic access to the variable
    // static means it belongs to the class
    private static volatile CarDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // static belongs to the class, does not require an instance
    static CarDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CarDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CarDatabase.class, CUSTOMER_DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
