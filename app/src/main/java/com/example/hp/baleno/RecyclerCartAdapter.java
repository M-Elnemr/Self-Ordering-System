package com.example.hp.baleno;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class RecyclerCartAdapter extends RecyclerView.Adapter<RecyclerCartAdapter.RecyclerCartHolder> {
    Context context;
    Cursor cursor;
    OnCartClick listener_cart;
    int cart_item_count ;
    String cart_item_nameE, cart_item_nameA, cart_item_size, cart_item_sugar, cart_item_cafe1, cart_item_cafe2, cart_item_type;
    Long id;
    double cart_item_price, cart_item_total_price;

    public interface OnCartClick{
        void onCartAdd(int position);
        void onCartRemove(int position);
    }

    public void setListener_cart(OnCartClick listener_cart) {
        this.listener_cart = listener_cart;
    }

    public RecyclerCartAdapter(Context context, Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    public class RecyclerCartHolder extends RecyclerView.ViewHolder{

        LinearLayout sugarDetails;
        TextView nameE_cart, nameA_cart, item_count, txt_sugar, txt_cafe1, txt_cafe2, item_price, item_total_price, txt_size;
        ImageView imgRemove, imgAdd;

        public RecyclerCartHolder(@NonNull View itemView,final OnCartClick listener_cart) {
            super(itemView);
            sugarDetails = itemView.findViewById(R.id.sugarDetails);
            nameE_cart = itemView.findViewById(R.id.nameE_cart);
            nameA_cart = itemView.findViewById(R.id.nameA_cart);
            item_count = itemView.findViewById(R.id.item_count);
            txt_size = itemView.findViewById(R.id.txt_size);
            txt_sugar = itemView.findViewById(R.id.txt_sugar);
            txt_cafe1 = itemView.findViewById(R.id.txt_cafe1);
            txt_cafe2 = itemView.findViewById(R.id.txt_cafe2);
            item_price = itemView.findViewById(R.id.item_price);
            item_total_price = itemView.findViewById(R.id.item_total_price);
            imgRemove = itemView.findViewById(R.id.imgRemove);
            imgAdd = itemView.findViewById(R.id.imgAdd);

            imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener_cart != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener_cart.onCartAdd(position);
                        }
                    }
                }
            });

              imgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener_cart != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener_cart.onCartRemove(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerCartHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_list, viewGroup, false);
        RecyclerCartHolder RCH = new RecyclerCartHolder(v, listener_cart);
        return RCH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerCartHolder recyclerCartHolder, int i) {
        if(!cursor.moveToPosition(i)){
            return;
        }

        id = cursor.getLong(cursor.getColumnIndex(MySqliteHandler.COLUMN_ID_CART));
        cart_item_nameE = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_NAMEE_CART));
        cart_item_nameA = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_NAMEA_CART));
        cart_item_size = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_SIZE));
        cart_item_price = cursor.getDouble(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_PRICE_CART));
        cart_item_count = cursor.getInt(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_COUNT_CART));
        cart_item_size = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_SIZE));
        cart_item_sugar = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_SUGAR_CART));
        cart_item_cafe1 = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_CAFE1_CART));
        cart_item_cafe2 = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_CAFE2_CART));
        cart_item_type = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_TYPE_CART));
        cart_item_total_price = cart_item_count * cart_item_price;

        /*
        if(!cart_item_sugar.equals(" ")){
            recyclerCartHolder.sugarDetails.setVisibility(View.VISIBLE);
            recyclerCartHolder.txt_sugar.setVisibility(View.VISIBLE);
        }
        if(!cart_item_cafe1.equals(" ")){
            recyclerCartHolder.sugarDetails.setVisibility(View.VISIBLE);
            recyclerCartHolder.txt_cafe1.setVisibility(View.VISIBLE);
        }
        if(!cart_item_cafe2.equals(" ")){
            recyclerCartHolder.sugarDetails.setVisibility(View.VISIBLE);
            recyclerCartHolder.txt_cafe2.setVisibility(View.VISIBLE);
        }
        */

        recyclerCartHolder.nameE_cart.setText(cart_item_nameE);
        recyclerCartHolder.nameA_cart.setText(cart_item_nameA);

        recyclerCartHolder.item_price.setText(String.valueOf(new DecimalFormat("####.##").format(cart_item_price)));
        recyclerCartHolder.item_total_price.setText(String.valueOf(new DecimalFormat("####.##").format(cart_item_total_price)));
        recyclerCartHolder.item_count.setText(String.valueOf(cart_item_count));
        recyclerCartHolder.txt_size.setText(cart_item_size);
        recyclerCartHolder.txt_sugar.setText(cart_item_sugar);
        recyclerCartHolder.txt_cafe1.setText(cart_item_cafe1);
        recyclerCartHolder.txt_cafe2.setText(cart_item_cafe2);

        recyclerCartHolder.itemView.setTag(String.valueOf(id));
        recyclerCartHolder.itemView.setTag(R.string.app_name,i);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(cursor != null){
            cursor.close();
        }
        cursor = newCursor;
        if(newCursor != null){
            notifyDataSetChanged();
        }
    }
}
