package com.example.carappweek5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carappweek5.provider.Car;

import java.util.ArrayList;
import java.util.List;

public class W6Adapter extends RecyclerView.Adapter<W6Adapter.ViewHolder> {

    ArrayList<Car> cars = new ArrayList();
    public W6Adapter(List<Car> cars) {
        this.cars = (ArrayList<Car>) cars;
     }

    Context self;

    @NonNull
    @Override
    // responsible for creating the object
    public W6Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         // reference to my card
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
         ViewHolder viewHolder = new ViewHolder(v);

         return viewHolder;
    }

    @Override
    // responsible for binding the object filling the object with your data
    public void onBindViewHolder(@NonNull W6Adapter.ViewHolder holder, int position) {
        holder.maker.setText(cars.get(position).getMaker());
        holder.model.setText(cars.get(position).getModel());
        holder.year.setText(cars.get(position).getYear());
        holder.seat.setText(cars.get(position).getSeat());
        holder.price.setText(cars.get(position).getPrice());
        holder.color.setText(cars.get(position).getColor());

        final int fPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() { //set back to itemView for students
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Item at position " + fPosition + " was clicked!",Toast.LENGTH_SHORT).show();
                //Snackbar.make(v, "Item at position " + fPosition + " was clicked!", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
    }

    @Override
    // tells the adapter how many items to handle or have in the list
    public int getItemCount() {
        return cars.size();
    }

    public void setCars(List<Car> cars) {
        this.cars = (ArrayList<Car>) cars;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView maker;
        TextView model;
        TextView year;
        TextView color;
        TextView seat;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            maker = itemView.findViewById(R.id.makerCard);
            model = itemView.findViewById(R.id.modelCard);
            year = itemView.findViewById(R.id.yearCard);
            color = itemView.findViewById(R.id.colorCard);
            seat = itemView.findViewById(R.id.seatCard);
            price = itemView.findViewById(R.id.priceCard);
        }
    }


}
