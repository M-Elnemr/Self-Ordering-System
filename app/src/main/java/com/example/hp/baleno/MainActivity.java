package com.example.hp.baleno;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    ArrayList<Items> itemsArrayList ;
    String[] namesE = {"Salads", "Pizza", "Grilled", "HotDrinks", "ColdDrinks", "Bakery", "Desserts", "Extras"};
    String[] namesA = {"سلطات", "بيتزا", "مشويات", "مشروبات ساخنة", "مشروبات باردة", "مخبوزات", "حلوي", "اضافات"};
    int[] Pics = {R.drawable.salads, R.drawable.pizza, R.drawable.grilled, R.drawable.hot_drinks,
            R.drawable.cold_drinks, R.drawable.bakery, R.drawable.desserts, R.drawable.extras};

    RecyclerView recyclerV;
    RecyclerVAdapter adapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab();
        dialog = new ProgressDialog(this,R.style.MyTheme);
        dialog.setCancelable(false);
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Inverse);

        addItemsToList();
        createRecyclerView();
    }

    public void createRecyclerView(){
        recyclerV = findViewById(R.id.recyclerV);
        recyclerV.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new RecyclerVAdapter(itemsArrayList) ;
        recyclerV.setAdapter(adapter);

        adapter.setOnClickL(new RecyclerVAdapter.OnClickL() {
            @Override
            public void onC(int position) {
                dialog.show();
                new Task(position).execute();
            }
        });
    }

    private class Task extends AsyncTask<Void, Void, Void>{

        int position;
        public Task (int position){
            this.position = position;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            intent.putExtra("IDENTITY", namesE[position]);
            startActivity(intent);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.cancel();
        }
    }

    public void addItemsToList(){
        itemsArrayList = new ArrayList<>();
        for(int i=0; i<namesE.length; i++){
            itemsArrayList.add(new Items(namesE[i], namesA[i],Pics[i]));
        }
    }

    public void fab(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(MainActivity.this, Cart.class);
                 startActivity(intent);
            }
        });
    }
}
