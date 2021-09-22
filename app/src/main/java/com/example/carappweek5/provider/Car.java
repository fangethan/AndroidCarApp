package com.example.carappweek5.provider;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cars")
public class Car implements Parcelable {
    // system provides a value for your id
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "carID")
    private int carID;

    @ColumnInfo(name = "maker")
    private String maker;

    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "seat")
    private String seat;

    @ColumnInfo(name = "price")
    private String price;

    //@ColumnInfo(name = "rating")
    private String rating;



    public Car(String maker, String model, String year, String color, String seat, String price) {
        this.maker = maker;
        this.model = model;
        this.year = year;
        this.color = color;
        this.seat = seat;
        this.price = price;
        this.rating = "5 Start";
    }

    protected Car(Parcel in) {
        maker = in.readString();
        model = in.readString();
        year = in.readString();
        color = in.readString();
        seat = in.readString();
        price = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maker);
        dest.writeString(model);
        dest.writeString(year);
        dest.writeString(color);
        dest.writeString(seat);
        dest.writeString(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getSeat() {
        return seat;
    }

    public String getPrice() {
        return price;
    }

    public int getCarID() {
        return carID;
    }



    public void setCarID(@NonNull int carID) {
        this.carID = carID;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

