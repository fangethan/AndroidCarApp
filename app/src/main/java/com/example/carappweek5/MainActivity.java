package com.example.carappweek5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carappweek5.provider.Car;
import com.example.carappweek5.provider.CarViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private EditText maker, model, year, color, seats, price;
    Context self;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

//    ListView listView;
//    ArrayList<String> data;
//    ArrayAdapter<String> adapter;

    // primitive data something that java provides you to use
    // shared preference only stores primitive data in key value pairs
    // database saves the structured data in a private database using the Room library

    FloatingActionButton floatingActionButton;

    W6Adapter w6Adapter;
    RecyclerView recyclerView;
    ArrayList<Car> carArrayList;

    private CarViewModel mCarViewModel;

    DatabaseReference ref;

    private GestureDetectorCompat mDetector;

    int x;
    int y;
    int finalY;
    int finalX;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFinalY() {
        return finalY;
    }

    public void setFinalY(int finalY) {
        this.finalY = finalY;
    }

    public int getFinalX() {
        return finalX;
    }

    public void setFinalX(int finalX) {
        this.finalX = finalX;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        // change to drawer layout so the nav bar can work
        setContentView(R.layout.drawer_layout);

        maker = findViewById(R.id.makerEditText);
        model = findViewById(R.id.modelEditText);
        color = findViewById(R.id.colorEditText);
        year = findViewById(R.id.editYearNumber);
        seats = findViewById(R.id.editSeatNumber);
        price = findViewById(R.id.editPriceNumber);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carArrayList = new ArrayList<>();
        w6Adapter = new W6Adapter(carArrayList);
        recyclerView.setAdapter(w6Adapter);

        mCarViewModel = new ViewModelProvider(this).get(CarViewModel.class);
        mCarViewModel.getAllCars().observe(this, newData -> {
            w6Adapter.notifyDataSetChanged();
//            // week 12
//            if (mCarViewModel.getAllCars().getValue().size() > 0) {
//                for (int i = 0; i < mCarViewModel.getAllCars().getValue().size(); i++) {
//                    carArrayList.add(mCarViewModel.getAllCars().getValue().get(i));
//                }
//            }
        });


        mDetector = new GestureDetectorCompat(this, this);

        drawerLayout = findViewById(R.id.drawer_layout);
        // link tool bar with the navigation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //
        drawerLayout.addDrawerListener(toggle);
        // sync the data between the toolbar and drawer layout
        toggle.syncState();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference();

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(self, maker.getText() + " | " + model.getText(),Toast.LENGTH_SHORT).show();
//                data.add(maker.getText() + " | " + model.getText());
                Car car = new Car(maker.getText().toString().toLowerCase(), model.getText().toString(),
                        year.getText().toString(),color.getText().toString(),
                        seats.getText().toString(),price.getText().toString());
                carArrayList.add(car);
                mCarViewModel.insert(car);
                ref.push().setValue(car);
                w6Adapter.notifyDataSetChanged();
            }
        });

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavHandler());


        // decide where the ontouchlistner should go
        // boolean decides whether particular view was interested in a certain event
        // view event parameters

        // event regarding that action on that view, used to report movement events
        // action move and up in this situation are dependent on action down
        // action move and up arent dependent on each other

        // diagonal swipe
        // handle that error pls
        // slight deviation in y axis when swiping
        View view = findViewById(R.id.my_layout);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                // left corner, 0,0, right corner, max x max y
                // getRawX how many pixels its away from the left screen
                // getX related to the current view the ontouchlistner is implemented on
//                int rawX = (int)event.getRawX();
//                int rawY = (int)event.getRawY();
//
//                // swipe down - action down, save the x and y value
//                // looking for, a significant change in y value, little in x value
//                // swipe right - action down, save the x and y value
//                // looking for, a significant change in x value, little in y value
//                int action = event.getActionMasked();
//                switch(action) {
//                    case (MotionEvent.ACTION_DOWN) :
//                        setX(rawX);
//                        setY(rawY);
//                        return true;
//                    case (MotionEvent.ACTION_MOVE) :
//                        return true;
//                    // action up happens, use coordinates from action down and conquer it with action up
//                    case (MotionEvent.ACTION_UP) :
//                        setFinalX(rawX);
//                        setFinalY(rawY);
//
//                        if (getX() <= 100 && getY() <= 300) {
//                            if (price.getText().toString().length() != 0) {
//                                int priceNum = Integer.parseInt(price.getText().toString());
//                                priceNum = priceNum - 500;
//                                if (priceNum < 0) {
//                                    Toast.makeText(self, "price can't be less than 0",Toast.LENGTH_SHORT).show();
//                                } else {
//                                    price.setText(String.valueOf(priceNum));
//                                }
//                            }
//                        }
//
//                        if (getX() >= Resources.getSystem().getDisplayMetrics().widthPixels && getY() <= 300) {
//                            int priceNum = Integer.parseInt(price.getText().toString());
//                            priceNum = priceNum + 500;
//                            price.setText(String.valueOf(priceNum));
//                        }
//
//                        int distanceX = getFinalX() - getX();
//                        int distanceY = getFinalY() - getY();
//                        if (distanceX > 200 && distanceY < 50) {
//                            Car car = new Car(maker.getText().toString().toLowerCase(), model.getText().toString(),
//                                    year.getText().toString(),color.getText().toString(),seats.getText().toString(),price.getText().toString());
//                            Toast.makeText(self, maker.getText() + " | " + model.getText(),Toast.LENGTH_SHORT).show();
//                            carArrayList.add(car);
//                            mCarViewModel.insert(car);
//                        } else if (distanceY > 200 && distanceX < 50) {
//                            Toast.makeText(self, "Cleared car list",Toast.LENGTH_SHORT).show();
//                            carArrayList.clear();
//                            mCarViewModel.deleteAll();
//                        }
//                        return true;
//                    default :
//                        return false;
//                }
                return true;
            }
        });


//        data = new ArrayList<>();
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);
//        listView.setAdapter(adapter);

        // self is used because the this in MyLocalBroadCastReceiver is referring to that local class and not the MainActivity
        self = this;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        /* Create and instantiate the local broadcast receiver
           This class listens to messages come from class SMSReceiver
         */
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();

        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11
         *  extract that broadcast only
         * second parameter is the action string that is used in declaring the broadcast intent and register the receiver
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

        // setOnClickListener overrides the onClick method in the buttons
//        addNewCar.setOnClickListener(new View.OnClickListener() {
//            // view reference to the url element, view is the button
//            @Override
//            // determine the click behaviour
//            public void onClick(View v) {
//                if (maker.getText().toString().equals("BMW") && model.getText().toString().equals("X6") &&
//                        color.getText().toString().equals("gold") && year.getText().toString().equals("2020") &&
//                        seats.getText().toString().equals("5") && price.getText().toString().equals("213900")) {
//
//                    SharedPreferences sharedPreferences = getPreferences(0);
//                    // open sharedPreferences to edit
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                    // get the data of each editTextView
//                    String makerData = maker.getText().toString();
//                    String modelData = model.getText().toString();
//                    String colorData = color.getText().toString();
//                    String yearData = year.getText().toString();
//                    String seatsData = seats.getText().toString();
//                    String priceData = price.getText().toString();
//
//                    // first is the key, second is the value
//                    // putString sets a string value in the preference editor
//                    // to be written back once apply is called
//                    editor.putString("Maker Key", makerData);
//                    editor.putString("Model Key", modelData);
//                    editor.putString("Color Key", colorData);
//                    editor.putString("Year Key", yearData);
//                    editor.putString("Seats Key", seatsData);
//                    editor.putString("Price Key", priceData);
//                    // commit your preferences changes back from this Editor to the Shared Preferences object it is editing
//                    editor.apply();
//
//                    // first parameter: context which is where the toast needs to be act to
//                    // second parameter is the text that will be displayed
//                    // third parameter is how long the toast will be displayed for
//                    // .show() makes the toast appear
//                    Toast.makeText(MainActivity.this, "BMW X6",Toast.LENGTH_SHORT).show();
//
//
//                } else {
//                    Toast.makeText(MainActivity.this, "Try again. No car found",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        // 0 means private
        // if used file its just a place to store the data
        SharedPreferences sharedPreferences = getPreferences(0);

        // retrieves the key of each value and assigns it to a variable
        // default value in case there is nothing in the value of the key

        String makerData = sharedPreferences.getString("Maker Key","");

        String modelData = sharedPreferences.getString("Model Key","");
        String colorData = sharedPreferences.getString("Color Key","");
        String yearData = sharedPreferences.getString("Year Key","");
        String seatsData = sharedPreferences.getString("Seats Key","");
        String priceData = sharedPreferences.getString("Price Key","");

        // set the text back to when the app was last destroyed
        maker.setText(makerData);
        model.setText(modelData);
        color.setText(colorData);
        year.setText(yearData);
        seats.setText(seatsData);
        price.setText(priceData);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    //    Options menu with the following item (2m)
//    Clear Fields: clear all fields.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.clearFields) {
            Toast.makeText(this,"Clear Fields",Toast.LENGTH_SHORT).show();
            maker.setText("");
            model.setText("");
            color.setText("");
            year.setText("");
            seats.setText("");
            price.setText("");
            SharedPreferences sharedPreferences = getPreferences(0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
        }
        return true;
    }

    //     onStart is called when the activity is visible to the user
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Week3Lab","onStart");
    }

    // onResume is called when the activity starts interacting with the user
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Week3Lab","onResume");
    }

    // onPause is called when an activity is going into the background but hasn't been killed
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Week3Lab","onPause");
    }

    // onStop is called when the activity is not visible to the user
    // persistance data is data available after fully closing the app
    @Override
    protected void onStop() {
        super.onStop();
        // used for debugging purposes, if you want to print out a bunch of messages
        // so you can log the exact flow of your program, use this.
        // If you want to keep a log of variable values, use this.
        Log.d("Week3Lab","onStop");
    }

    // onDestroy is called when the activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Week3Lab","onDestroy");
    }

    // method is used to keep the state when the orientation changes
    // non persistance data is not available after fully closing the applicatgion
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Week3Lab","onSaveInstanceState");

    }

    // method is called only when recreating activity after it is called
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Week3Lab","onRestoreInstanceState");

    }

    // The gesture detector classes are used to detect common gestures through a set of motion events.
    // onGestureListener The listener that is used to notify when gestures occur
    // onDoubleTapListener The listener that is used to notify when a double-tap or a confirmed single-tap occur.
    // convenience class is a class that simplifies acccess to other clasess

    // ACTION_DOWN	The first pointer (finger) touches the screen.  The motion event object contains the initial starting location.
    // ACTION_POINTER_UP	A non-primary (secondary) pointer leaves the screen
    // ACTION_POINTER_DOWN	A non-primary (secondary) pointer touches the screen
    // ACTION_MOVE	A motion happened to primary or non-primary pointer
    // ACTION_UP	The last pointer leaves the screen

    // on single tap: increment the number of seats by one
    // Notified when a single-tap occurs.
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }
    // on double-tap: load default values to all fields (load your favourite car)
    // Notified when a double-tap occurs.
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        maker.setText("BMW");
        model.setText("X7");
        color.setText("Gold");
        year.setText("2020");
        seats.setText("5");
        price.setText("1000");
        return true;
    }
    // Notified when an event within a double-tap gesture occurs, including the down, move, and up events.
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
    }
    // Notified when a tap occurs with the down MotionEvent that triggered it.
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }
    // The user has performed a down MotionEvent and not performed a move or up yet.
    @Override
    public void onShowPress(MotionEvent e) {

    }
    // on single tap: increment the number of seats by one
    // Notified when a tap occurs with the up MotionEvent that triggered it.
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        int seatNumber;
        if (seats.getText().toString().equals("")) {
            seatNumber = 0;
            seatNumber++;
            seats.setText(String.valueOf(seatNumber));
        } else {
            seatNumber = Integer.parseInt(seats.getText().toString());
            seatNumber++;
            seats.setText(String.valueOf(seatNumber));
        }

        return true;
    }
    // on horizontal right to left scroll:  increment the price by the amount of scroll (i.e. distance)
    // on horizontal left to right scroll:  decrement the price by the amount of scroll (i.e. distance)
    // Notified when a scroll occurs with the initial on down MotionEvent and the current move MotionEvent.
    // e1 is the first event (touch down)
    // e2 is the current event
    // distanceX: the distance between e2 and the previous event (not e1) along the X-axis
    // distanceX: the distance between e2 and the previous event (not e1) along the Y-axis
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int min = 0;
        int max = 5000;
        int priceNum;
        int seatNum = 0;
        
        if (price.getText().toString().equals("")) {
            priceNum = 0;
            price.setText(String.valueOf(priceNum));
        }
        if (seats.getText().toString().equals("")) {
            seatNum = 0;
            seats.setText(String.valueOf(seatNum));
        }

        priceNum = Integer.parseInt(price.getText().toString());
        seatNum = Integer.parseInt(seats.getText().toString());

        // on horizontal right to left scroll:  increment the price by the amount of scroll (i.e. distance)
        if (distanceX > 0 && distanceY == 0) {
            if (priceNum > 5000) {
                priceNum = max;
            } else {
                priceNum = priceNum + (int) distanceX;
            }
        } // on horizontal left to right scroll:  decrement the price by the amount of scroll (i.e. distance)
        else if (distanceX < 0 && distanceY == 0) {
            if (priceNum < 0) {
                priceNum = min;
            } else {
                priceNum = priceNum - (int) Math.abs(distanceX);
                if (priceNum < 0) {
                    priceNum = min;
                }
            }
        } // increment down to up
        else if (distanceY > 0 && distanceX == 0) {
            if (seatNum < 0) {
                seatNum = min;
            } else {
                seatNum = seatNum + (int) distanceY/50;

                if (seatNum < 0) {
                    seatNum = min;
                }
            }
        } // subtract up to down
        else if (distanceY < 0 && distanceX == 0) {
            if (seatNum < 0) {
                seatNum = min;
            } else {
                seatNum = seatNum - (int) Math.abs(distanceY/50);
            }
        }

        price.setText(String.valueOf(priceNum));
        seats.setText(String.valueOf(seatNum));
        

        return true;
    }
    // on long-press: clear all the fields
    // Notified when a long press occurs with the initial on down MotionEvent that trigged it.
    @Override
    public void onLongPress(MotionEvent e) {
        maker.setText("");
        model.setText("");
        color.setText("");
        year.setText("");
        seats.setText("");
        price.setText("");
        SharedPreferences sharedPreferences = getPreferences(0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
    }
    // on Fling: move the app (activity) to the background by calling moveTaskToBack(true);
    // Notified of a fling event when it occurs with the initial on down MotionEvent and the matching up MotionEvent.
    // e1 is the first event (touch down)
    // e2 is the motion event that triggered the current event
    // velocityX: The velocity of this event (fling event) along the X-axis
    // velocityY: The velocity of this event (fling event) along the Y-axis
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (velocityX > 400 || velocityY > 400) {
            moveTaskToBack(true);
        }
        return true;
    }


    class NavHandler implements NavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int id= menuItem.getItemId();
            Car car = new Car(maker.getText().toString().toLowerCase(), model.getText().toString(),
                    year.getText().toString(),color.getText().toString(),seats.getText().toString(),price.getText().toString());
            if (id == R.id.addCar) {
                Toast.makeText(self, maker.getText() + "|" + model.getText(),Toast.LENGTH_SHORT).show();
                carArrayList.add(car);
                mCarViewModel.insert(car);
                ref.push().setValue(car);
                /** Push object to x-axis position at the start of its container, not changing its size. */
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (id == R.id.removeLastCar) {
                int index = carArrayList.size() - 1;
                carArrayList.remove(index);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (id == R.id.removeAllCars) {
                carArrayList.clear();
                mCarViewModel.deleteAll();
                ref.removeValue();
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (id == R.id.listAllItems) {
//                mCarViewModel.insert(car);
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putParcelableArrayListExtra("message",carArrayList);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (id == R.id.totalCars) {
                // week 12
                int count = 0;
                if (mCarViewModel.getAllCars().getValue().size() > 0) {
                    for (int i = 0; i < mCarViewModel.getAllCars().getValue().size(); i++) {
                        count++;
                    }
                }

                Toast.makeText(self, "There are " + count + " cars added",Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (id == R.id.totalCost) {
                // week 12
                double cost = 0;
                if (mCarViewModel.getAllCars().getValue().size() > 0) {
                    for (int i = 0; i < mCarViewModel.getAllCars().getValue().size(); i++) {
                        double priceCar = 0;
                        if (mCarViewModel.getAllCars().getValue().get(i).getPrice().equals("")) {
                            cost += priceCar;
                        } else {
                            priceCar = Double.parseDouble(mCarViewModel.getAllCars().getValue().get(i).getPrice());
                            cost += priceCar;
                        }
                    }
                }
                Toast.makeText(self, "Total cost of all cars: $" + cost ,Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (id == R.id.aveCost) {
                // week 12
                double cost = 0;
                if (mCarViewModel.getAllCars().getValue().size() > 0) {
                    for (int i = 0; i < mCarViewModel.getAllCars().getValue().size(); i++) {
                        double priceCar = 0;
                        if (mCarViewModel.getAllCars().getValue().get(i).getPrice().equals("")) {
                            cost += priceCar;
                        } else {
                            priceCar = Double.parseDouble(mCarViewModel.getAllCars().getValue().get(i).getPrice());
                            cost += priceCar;
                        }
                    }
                }
                cost = cost / mCarViewModel.getAllCars().getValue().size();
                Toast.makeText(self, "Total cost of all cars: $" + cost ,Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            // notifies the data changed and the view should refresh itself
            w6Adapter.notifyDataSetChanged();
            return true;
        }
    }


    // class allows it to listen for specific broadcast, by registering broadcast receiver
    class MyBroadCastReceiver extends BroadcastReceiver {
        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            /*
             * Retrieve the message from the intent
             * intent is where you can find all the data items
             * */
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the account holder name and account number separate by a semicolon
             * */

            StringTokenizer sT = new StringTokenizer(msg, ";");
            String makerSMS = sT.nextToken();
            String modelSMS = sT.nextToken();
            String yearSMS = sT.nextToken();
            String colorSMS = sT.nextToken();
            String seatsSMS = sT.nextToken();
            String priceSMS = sT.nextToken();

            /*
             * Now, its time to update the UI
             * */
            maker.setText(makerSMS);
            model.setText(modelSMS);
            year.setText(yearSMS);
            color.setText(colorSMS);
            seats.setText(seatsSMS);
            price.setText(priceSMS);
            Toast.makeText(self,msg,Toast.LENGTH_SHORT).show();

        }
    }
}