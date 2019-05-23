package com.example.hp.baleno;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.example.hp.baleno.Menu_Items_Arrays.arrayBakery;
import static com.example.hp.baleno.Menu_Items_Arrays.arrayDesserts;
import static com.example.hp.baleno.Menu_Items_Arrays.arrayHotDrinks;

public class Main2Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    MySqliteHandler mySqliteHandler;
    RecyclerVAdapter2 adapter;
    String item_nameE, item_nameA, item_type, item_desc;
    int currentCount, item_id, img;
    double item_price , item_priceS, item_priceL;
    SQLiteDatabase database;

    static ArrayList<ItemsDetails> itemArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fab();
        addItemsToArray();

        adapter = new RecyclerVAdapter2(Main2Activity.this, itemArray);
        createRecyclerView();
        adapter.setOnClickL2(new RecyclerVAdapter2.OnClickL2() {
            @Override
            public void onC2(int position) {
                ItemsDetails currentItem = itemArray.get(position);

                 item_nameE = currentItem.getItem_nameE();
                 item_nameA = currentItem.getItem_nameA();
                 item_id = currentItem.getItemID();
                 item_type = currentItem.getItem_type();
                 item_price = currentItem.getItem_price();
                 item_priceS = currentItem.getItem_priceS();
                 item_priceL = currentItem.getItem_priceL();
                 item_desc = currentItem.getItem_des();
                 img = currentItem.getItem_image();
                 byte[] img2 = generate_img_byte(img);

                Intent intentItemDetails = new Intent(Main2Activity.this, Main3Activity.class);

                intentItemDetails.putExtra("NAMEE", item_nameE);
                intentItemDetails.putExtra("NAMEA", item_nameA);
                intentItemDetails.putExtra("ID", item_id);
                intentItemDetails.putExtra("TYPE", item_type);
                intentItemDetails.putExtra("PRICE", item_price);
                intentItemDetails.putExtra("PRICES", item_priceS);
                intentItemDetails.putExtra("PRICEL", item_priceL);
                intentItemDetails.putExtra("DESC", item_desc);
                intentItemDetails.putExtra("IMG", img2);

                startActivity(intentItemDetails);
            }

            @Override
            public void onCart(int position) {
                addItemCart(position);
                Toast.makeText(Main2Activity.this, "added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addItemCart(int position){

        ItemsDetails currentItem = itemArray.get(position);

        item_nameE = currentItem.getItem_nameE();
        item_nameA = currentItem.getItem_nameA();
        item_id = currentItem.getItemID();
        item_type = currentItem.getItem_type();
        item_price = currentItem.getItem_price();
        item_desc = currentItem.getItem_des();
        img = currentItem.getItem_image();

         mySqliteHandler = new MySqliteHandler(Main2Activity.this);
        database = mySqliteHandler.getWritableDatabase();

        Cursor cursorItem_cart = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_CART+" WHERE "+
                MySqliteHandler.COLUMN_ITEM_NAMEE_CART+" = ? AND "+
                MySqliteHandler.COLUMN_ITEM_SIZE+" = ? AND "+
                MySqliteHandler.COLUMN_SUGAR_CART+" = ? AND "+
                MySqliteHandler.COLUMN_CAFE1_CART+" = ? AND "+
                MySqliteHandler.COLUMN_CAFE2_CART+" = ?",
                new String[] {item_nameE, "M", " ", " ", " "});

        if(cursorItem_cart.getCount() != 0){

            cursorItem_cart.moveToFirst();
            currentCount = cursorItem_cart.getInt(cursorItem_cart.getColumnIndex(MySqliteHandler.COLUMN_ITEM_COUNT_CART));
            String nameE = cursorItem_cart.getString(cursorItem_cart.getColumnIndex(MySqliteHandler.COLUMN_ITEM_NAMEE_CART));
            String size = cursorItem_cart.getString(cursorItem_cart.getColumnIndex(MySqliteHandler.COLUMN_ITEM_SIZE));
            String sugar = cursorItem_cart.getString(cursorItem_cart.getColumnIndex(MySqliteHandler.COLUMN_SUGAR_CART));
            String cafe1 = cursorItem_cart.getString(cursorItem_cart.getColumnIndex(MySqliteHandler.COLUMN_CAFE1_CART));
            String cafe2 = cursorItem_cart.getString(cursorItem_cart.getColumnIndex(MySqliteHandler.COLUMN_CAFE2_CART));

            ContentValues values = new ContentValues();
            values.put(MySqliteHandler.COLUMN_ITEM_COUNT_CART, ++currentCount);

            database.update(MySqliteHandler.TABEL_NAME_CART, values,
                    MySqliteHandler.COLUMN_ITEM_NAMEE_CART+" = ? AND "+
                            MySqliteHandler.COLUMN_ITEM_SIZE+" = ? AND "+
                            MySqliteHandler.COLUMN_SUGAR_CART+" = ? AND "+
                            MySqliteHandler.COLUMN_CAFE1_CART+" = ? AND "+
                            MySqliteHandler.COLUMN_CAFE2_CART+" = ?",
                            new String[] {nameE, size, sugar, cafe1, cafe2});
            }else{
                ContentValues contentValues = new ContentValues();
                contentValues.put(MySqliteHandler.COLUMN_ITEM_NAMEE_CART, item_nameE);
                contentValues.put(MySqliteHandler.COLUMN_ITEM_NAMEA_CART, item_nameA);
                contentValues.put(MySqliteHandler.COLUMN_ITEM_ID, item_id);
                contentValues.put(MySqliteHandler.COLUMN_ITEM_PRICE_CART, item_price);
                contentValues.put(MySqliteHandler.COLUMN_ITEM_TYPE_CART, item_type);
                contentValues.put(MySqliteHandler.COLUMN_ITEM_COUNT_CART, 1);
                contentValues.put(MySqliteHandler.COLUMN_ITEM_SIZE, "M");
                contentValues.put(MySqliteHandler.COLUMN_SUGAR_CART, " ");
                contentValues.put(MySqliteHandler.COLUMN_CAFE1_CART, " ");
                contentValues.put(MySqliteHandler.COLUMN_CAFE2_CART, " ");

                database.insert(MySqliteHandler.TABEL_NAME_CART, null, contentValues);
                database.close();
            }
    }

    public void addItemsToArray(){

        Menu_Items_Arrays menu_items_arrays = new Menu_Items_Arrays();
        switch (IDENTITY()){
            case "Salads":
                itemArray = menu_items_arrays.arraySalads;
                break;
            case "Pizza":
                itemArray = menu_items_arrays.arrayPizza;
                break;

            case "Desserts":
                itemArray = menu_items_arrays.arrayDesserts;
                break;

            case "Bakery":
                itemArray = menu_items_arrays.arrayBakery;
                break;

            case "Extras":
                itemArray = menu_items_arrays.arrayExtras;
                break;

            case "Grilled":
                itemArray = menu_items_arrays.arrayGrilled;
                break;

            case "HotDrinks":
                itemArray = menu_items_arrays.arrayHotDrinks;
                break;

            case "ColdDrinks":
                itemArray = menu_items_arrays.arrayColdDrinks;
                break;
        }
    }
    public void createRecyclerView(){
        recyclerView = findViewById(R.id.recyclerV2);
        recyclerView.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
        recyclerView.setAdapter(adapter);
    }

    public byte[] generate_img_byte(int image){
        byte[] byteImg;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), image);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
        byteImg = stream.toByteArray();

        return byteImg;
    }

    public String IDENTITY(){
        Bundle data = getIntent().getExtras();
        String IDENTITY = data.getString("IDENTITY");
        return IDENTITY;
    }

    public void fab(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, Cart.class);
                startActivity(intent);
            }
        });
    }
}
