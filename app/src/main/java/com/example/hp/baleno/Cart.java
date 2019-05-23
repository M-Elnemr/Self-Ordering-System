package com.example.hp.baleno;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

public class Cart extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerCart;
    Button btnOrder;
    TextView txt_total;
    RecyclerCartAdapter cartAdapter;
    MySqliteHandler mySqliteHandler;
    Cursor cursor;
    SQLiteDatabase database;
    OutputStream oStream;

    int cart_item_count;
    double total_price = 0;
    String cart_item_nameE, cart_item_nameA ;

    String tableNumber = "Table 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Virifications();

        getCartItems();
        cartAdapter = new RecyclerCartAdapter(Cart.this, cursor);
        recyclerCart.setAdapter(cartAdapter);

        btnOrder.setOnClickListener(Cart.this);

        //to handle Delete By Swap
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

                new AlertDialog.Builder(Cart.this)
                        .setTitle("Delete").setMessage("Are You Sure To Delete This Item")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                total_price =0;
                                removeItem((String) viewHolder.itemView.getTag());
                                getCartItems();
                                txt_total.setText(String.valueOf(new DecimalFormat("####.##").format(total_price)));
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                           @Override
                              public void onClick(DialogInterface dialog, int which) {

                               SQLiteDatabase database = mySqliteHandler.getReadableDatabase();

                               cursor = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_CART, null);

                               cartAdapter.notifyDataSetChanged();
                               cartAdapter.swapCursor(cursor);
                              }
                            })
                        .setIcon(R.drawable.ic_delete).show();

            }
        }).attachToRecyclerView(recyclerCart);

        cartAdapter.setListener_cart(new RecyclerCartAdapter.OnCartClick() {

            @Override
            public void onCartAdd(int position) {

                total_price = 0;
                cursor.moveToPosition(position);
                cart_item_nameE = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_NAMEE_CART));
                cart_item_nameA = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_NAMEA_CART));
                cart_item_count = cursor.getInt(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_COUNT_CART));
                Long id = cursor.getLong(cursor.getColumnIndex(MySqliteHandler.COLUMN_ID_CART));

                int newCount = cart_item_count + 1;

                ContentValues values = new ContentValues();
                values.put(MySqliteHandler.COLUMN_ITEM_COUNT_CART, newCount);

                database.update(MySqliteHandler.TABEL_NAME_CART, values, MySqliteHandler.COLUMN_ID_CART+"=?",
                        new String[] {String.valueOf(id)});

                SQLiteDatabase database = mySqliteHandler.getReadableDatabase();

                cursor = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_CART, null);

                cartAdapter.notifyDataSetChanged();
                cartAdapter.swapCursor(cursor);

                getCartItems();
                txt_total.setText(String.valueOf(new DecimalFormat("####.##").format(total_price)));
            }

            @Override
            public void onCartRemove(int position) {
                total_price = 0;
                cursor.moveToPosition(position);
                cart_item_nameE = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_NAMEE_CART));
                cart_item_nameA = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_NAMEA_CART));
                cart_item_count = cursor.getInt(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_COUNT_CART));
                Long id = cursor.getLong(cursor.getColumnIndex(MySqliteHandler.COLUMN_ID_CART));

                if (cart_item_count > 1){
                    ContentValues values = new ContentValues();
                    values.put(MySqliteHandler.COLUMN_ITEM_COUNT_CART, --cart_item_count);

                    database.update(MySqliteHandler.TABEL_NAME_CART, values, MySqliteHandler.COLUMN_ID_CART+"=?",
                            new String[] {String.valueOf(id)});

                    cartAdapter.notifyItemChanged(position);

                }else {
                    Toast.makeText(Cart.this, "You Can't Order Less", Toast.LENGTH_SHORT).show();
                }
                SQLiteDatabase database = mySqliteHandler.getReadableDatabase();

                cursor = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_CART, null);

                cartAdapter.notifyDataSetChanged();
                cartAdapter.swapCursor(cursor);
                getCartItems();
                txt_total.setText(String.valueOf(new DecimalFormat("####.##").format(total_price)));
            }
        });

        txt_total.setText(String.valueOf(new DecimalFormat("####.##").format(total_price)));
    }

    @Override
    public void onClick(View v) {

        if(total_price <1){
            return;
        }
        //sending the order to the device  ////////////////////////////////////
        new AlertDialog.Builder(Cart.this)
                .setTitle("Order").setMessage("Are You Sure To Set The Order ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyTask myTask = new MyTask();
                        myTask.execute();
                        /////////////////////////////////////////////
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                return;
            }
        }).setIcon(R.drawable.ic_shopping_cart_black).show();

        ////////////////////////////////////////////////////////////////////////////
    }

    private void getCartItems(){
        SQLiteDatabase database = mySqliteHandler.getReadableDatabase();

        cursor = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_CART, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            double price = cursor.getDouble(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_PRICE_CART));
            double count = cursor.getInt(cursor.getColumnIndex(MySqliteHandler.COLUMN_ITEM_COUNT_CART));
            double price_item = price * count;
            total_price = total_price + price_item;
        }
    }

    private void removeItem(String id){
        SQLiteDatabase mDatabase = mySqliteHandler.getWritableDatabase();
        mDatabase.delete(MySqliteHandler.TABEL_NAME_CART,
                MySqliteHandler.COLUMN_ID_CART + "=?" , new String[] {id});

        SQLiteDatabase database = mySqliteHandler.getReadableDatabase();

        cursor = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_CART, null);

        cartAdapter.swapCursor(cursor);
    }


    private class MyTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            getItemsToPrint();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            /* to check if the device are connected or not
            if(oStream == null){
                Toast.makeText(Cart.this, "can't send your Order , Try Again Later", Toast.LENGTH_LONG).show();
                return;
            }
            */
                Toast.makeText(Cart.this, "your order accepted", Toast.LENGTH_LONG).show();
                txt_total.setVisibility(View.GONE);
                recyclerCart.setVisibility(View.GONE);

                SQLiteDatabase mDatabase = mySqliteHandler.getWritableDatabase();
                mDatabase.delete(MySqliteHandler.TABEL_NAME_CART, null, null);


            cursor = mDatabase.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_TABLE, null);
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put(MySqliteHandler.COLUMN_CHECK_OUT, "yes");
            mDatabase.update(MySqliteHandler.TABEL_NAME_TABLE, contentValues,
                    MySqliteHandler.COLUMN_TABLE_NUM+" = ?",
                    new String[] {cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_TABLE_NUM))});

                 Intent intent = new Intent(Cart.this, Luncher.class);
                 startActivity(intent);
                 mDatabase.close();
                 finish();
            }
    }

    private void getItemsToPrint(){
        SQLiteDatabase database = mySqliteHandler.getReadableDatabase();
        cursor = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_CART, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
/*
        try
        {

             you cant connect to any device within the local network and send the order through it ..
            Socket sock = new Socket();
            sock.connect(new InetSocketAddress(the device IP, the PORT), 1500);
            oStream =  sock.getOutputStream();

            if(oStream == null){
                return;
            }
            for(int i=0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);

                 write your code here
            }



           oStream.close();
            sock.close();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
*/
    }

    private void Virifications(){

        mySqliteHandler = new MySqliteHandler(Cart.this);
        database = mySqliteHandler.getWritableDatabase();
        cursor = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_TABLE, null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            tableNumber = "Table "+cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_TABLE_NUM));
        }

        recyclerCart = findViewById(R.id.recyclerCart);
        recyclerCart.setLayoutManager(new LinearLayoutManager(Cart.this));
        recyclerCart.setHasFixedSize(true);
        btnOrder = findViewById(R.id.btnOrder);
        txt_total = findViewById(R.id.txt_total);
    }
}
