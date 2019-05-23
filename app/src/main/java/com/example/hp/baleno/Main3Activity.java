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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    String nameE, nameA, type, desc, radioSize_text, radioSugar_text, radioCafe1_text, radioCafe2_text;
    int count =1, currentCount, id;
    double priceM, priceS, priceL, totalPrice;
    double price ;
    byte[] imgBytes;

    ImageView img_details, imgRemove, imgAdd;
    TextView txtNameE_details, txtNameA_details, txtDesc_details, item_count, item_price, item_total_price;
    Button btnAddCart;
    RadioGroup radioSize, radioSugar, radioCafe1, radioCafe2;
    RadioButton RBSize, RBSugar, RBCafe1, RBCafe2, radioButton11;
    LinearLayout linearSugar, linearCafe1, linearCafe2, linearSize;
    MySqliteHandler mySqliteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        fab();
        Verification();

        txtNameE_details.setText(nameE);
        txtNameA_details.setText(nameA);
        txtDesc_details.setText(desc);
        item_count.setText(String.valueOf(count));
        item_price.setText(String.valueOf(price));
        item_total_price.setText(String.valueOf(price));
        radioButton11 = findViewById(R.id.radioButton11);

        radioButton11.setChecked(true);

        Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        img_details.setImageBitmap(bitmap);

        btnAddCart.setOnClickListener(Main3Activity.this);
        imgAdd.setOnClickListener(Main3Activity.this);
        imgRemove.setOnClickListener(Main3Activity.this);
        radioSize.setOnCheckedChangeListener(Main3Activity.this);
    }

    void Verification(){
        mySqliteHandler = new MySqliteHandler(Main3Activity.this);
        totalPrice = price * count;

        Bundle itemData = getIntent().getExtras();

        nameE = itemData.getString("NAMEE");
        nameA = itemData.getString("NAMEA");
        id = itemData.getInt("ID");
        type = itemData.getString("TYPE");
        priceM = itemData.getDouble("PRICE");
        priceS = itemData.getDouble("PRICES");
        priceL = itemData.getDouble("PRICEL");
        desc = itemData.getString("DESC");
        imgBytes = itemData.getByteArray("IMG");

        price = priceM;
        img_details = findViewById(R.id.img_details);
        imgRemove = findViewById(R.id.imgRemove);
        imgAdd = findViewById(R.id.imgAdd);
        txtNameE_details = findViewById(R.id.txtNameE_details);
        txtNameA_details = findViewById(R.id.txtNameA_details);
        txtDesc_details = findViewById(R.id.txtDesc_details);
        item_count = findViewById(R.id.item_count);
        item_price = findViewById(R.id.item_price);
        item_total_price = findViewById(R.id.item_total_price);
        btnAddCart = findViewById(R.id.btnAddCart);
        radioSize = findViewById(R.id.radioSize);
        radioSugar = findViewById(R.id.radioSugar);
        radioCafe1 = findViewById(R.id.radioCafe1);
        radioCafe2 = findViewById(R.id.radioCafe2);
        linearSize = findViewById(R.id.linearSize);
        linearSugar = findViewById(R.id.linearSugar);
        linearCafe1 = findViewById(R.id.linearCafe1);
        linearCafe2 = findViewById(R.id.linearCafe2);

////////////////////////////////////////////////////////////////////////////////////////////
        if (type.equals("HotDrinks") || type.equals("ColdDrinks")){
            linearSugar.setVisibility(View.VISIBLE);
            if(nameE.equals("Turkish Coffee")){
                linearCafe1.setVisibility(View.VISIBLE);
                linearCafe2.setVisibility(View.VISIBLE);
            }
        }
        if(type.equals("Grilled") || type.equals("Bakery") || type.equals("Desserts") || type.equals("Extras")){
            linearSize.setVisibility(View.GONE);
        }
//////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddCart:
                addItemCart();
                Toast.makeText(this, "Added To Cart", Toast.LENGTH_SHORT).show();
                break;

            case R.id.imgAdd:
                count++;
                totalPrice = price*count;
                item_count.setText(String.valueOf(count));
                item_total_price.setText(String.valueOf(new DecimalFormat("####.##").format(totalPrice)));
                break;

            case R.id.imgRemove:
                if (count > 1){
                    count--;
                    totalPrice = price*count;
                    item_count.setText(String.valueOf(count));
                    item_total_price.setText(String.valueOf(new DecimalFormat("####.##").format(totalPrice)));
                }else {
                    Toast.makeText(this, "You Can't Order Less", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void addItemCart(){
        SQLiteDatabase database = mySqliteHandler.getWritableDatabase();

        getRadioValues();

        Cursor cursorItem_cart = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_CART+" WHERE "+
                MySqliteHandler.COLUMN_ITEM_NAMEE_CART+" = ? AND "+
                MySqliteHandler.COLUMN_ITEM_SIZE+" = ? AND "+
                MySqliteHandler.COLUMN_SUGAR_CART+" = ? AND "+
                MySqliteHandler.COLUMN_CAFE1_CART+" = ? AND "+
                MySqliteHandler.COLUMN_CAFE2_CART+" = ?",
                new String[] {nameE, radioSize_text, radioSugar_text, radioCafe1_text, radioCafe2_text});

        if(cursorItem_cart.getCount() != 0){
            cursorItem_cart.moveToFirst();

            currentCount = cursorItem_cart.getInt(cursorItem_cart.getColumnIndex(MySqliteHandler.COLUMN_ITEM_COUNT_CART));
            String name = cursorItem_cart.getString(cursorItem_cart.getColumnIndex(MySqliteHandler.COLUMN_ITEM_NAMEE_CART));
            String size = cursorItem_cart.getString(cursorItem_cart.getColumnIndex(MySqliteHandler.COLUMN_ITEM_SIZE));
            String sugar = cursorItem_cart.getString(cursorItem_cart.getColumnIndex(MySqliteHandler.COLUMN_SUGAR_CART));
            String cafe1 = cursorItem_cart.getString(cursorItem_cart.getColumnIndex(MySqliteHandler.COLUMN_CAFE1_CART));
            String cafe2 = cursorItem_cart.getString(cursorItem_cart.getColumnIndex(MySqliteHandler.COLUMN_CAFE2_CART));

            int newCount = currentCount + count;

            ContentValues values = new ContentValues();
            values.put(MySqliteHandler.COLUMN_ITEM_COUNT_CART, newCount);

            database.update(MySqliteHandler.TABEL_NAME_CART, values,
                 MySqliteHandler.COLUMN_ITEM_NAMEE_CART+" = ? AND "+
                            MySqliteHandler.COLUMN_ITEM_SIZE+" = ? AND "+
                            MySqliteHandler.COLUMN_SUGAR_CART+" = ? AND "+
                            MySqliteHandler.COLUMN_CAFE1_CART+" = ? AND "+
                            MySqliteHandler.COLUMN_CAFE2_CART+" = ?",
                    new String[] {name, size, sugar, cafe1, cafe2});
        }else{

            getRadioValues();

            ContentValues contentValues = new ContentValues();
            contentValues.put(MySqliteHandler.COLUMN_ITEM_NAMEE_CART, nameE);
            contentValues.put(MySqliteHandler.COLUMN_ITEM_NAMEA_CART, nameA);
            contentValues.put(MySqliteHandler.COLUMN_ITEM_ID, id);
            contentValues.put(MySqliteHandler.COLUMN_ITEM_PRICE_CART, price);
            contentValues.put(MySqliteHandler.COLUMN_ITEM_TYPE_CART, type);
            contentValues.put(MySqliteHandler.COLUMN_ITEM_COUNT_CART, count);
            contentValues.put(MySqliteHandler.COLUMN_ITEM_SIZE, radioSize_text);
            contentValues.put(MySqliteHandler.COLUMN_SUGAR_CART, radioSugar_text);
            contentValues.put(MySqliteHandler.COLUMN_CAFE1_CART, radioCafe1_text);
            contentValues.put(MySqliteHandler.COLUMN_CAFE2_CART, radioCafe2_text);

            database.insert(MySqliteHandler.TABEL_NAME_CART, null, contentValues);
            database.close();
            }
                }

    void getRadioValues(){


        Integer sizeID = radioSize.getCheckedRadioButtonId();
        RBSize = findViewById(sizeID);
        if(RBSize != null){
            radioSize_text= RBSize.getText().toString();
        }else{
            radioSize_text="M";
        }

        Integer sugarID = radioSugar.getCheckedRadioButtonId();
        RBSugar = findViewById(sugarID);
        if(RBSugar != null){
            radioSugar_text= RBSugar.getText().toString();
        }else{
            radioSugar_text=" ";
        }

        Integer cafe1ID = radioCafe1.getCheckedRadioButtonId();
        RBCafe1 = findViewById(cafe1ID);
        if(RBCafe1 != null){
            radioCafe1_text= RBCafe1.getText().toString();
        }else{
            radioCafe1_text=" ";
        }

        Integer cafe2ID = radioCafe2.getCheckedRadioButtonId();
        RBCafe2 = findViewById(cafe2ID);
        if(RBCafe2 != null){
            radioCafe2_text= RBCafe2.getText().toString();
        }else{
            radioCafe2_text=" ";
        }
    }

    void fab(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main3Activity.this, Cart.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radioButton10:
                price = priceS;
                item_price.setText(String.valueOf(price));
                item_total_price.setText(String.valueOf(price * count));
                break;

            case R.id.radioButton11:
                price = priceM;
                item_price.setText(String.valueOf(price));
                item_total_price.setText(String.valueOf(price * count));
                break;

            case R.id.radioButton12:
                price = priceL;
                item_price.setText(String.valueOf(price));
                item_total_price.setText(String.valueOf(price * count));
                break;
        }
    }
}