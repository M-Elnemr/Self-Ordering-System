package com.example.hp.baleno;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Luncher extends AppCompatActivity implements View.OnClickListener {

    Button btnM;
    Button btnOut;
    ProgressDialog dialog;
    MySqliteHandler mySqliteHandler;
    SQLiteDatabase database;
    Cursor cursor;
    String tableNumber, CHECK;
    OutputStream oStream;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luncher);

        dialog = new ProgressDialog(this,R.style.MyTheme);
        dialog.setCancelable(false);
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        btnM = findViewById(R.id.btnM);
        btnOut = findViewById(R.id.btnOut);
       // btnOut.setVisibility(View.GONE);

        mySqliteHandler = new MySqliteHandler(Luncher.this);
        database = mySqliteHandler.getWritableDatabase();
        cursor = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_TABLE, null);
        if(cursor.getCount() > 0){

            cursor.moveToFirst();
            CHECK = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_CHECK_OUT));
            if(CHECK.equals("yes")){
                btnOut.setVisibility(View.VISIBLE);
            }
        }

        btnM.setOnClickListener(Luncher.this);
        btnOut.setOnClickListener(Luncher.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_about:
                Intent intent = new Intent(Luncher.this, About.class);
                startActivity(intent);
                break;

            case R.id.action_settings:
                showDialog();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnM:
                cursor = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_TABLE, null);
                if(cursor.getCount() == 0){
                    Toast.makeText(Luncher.this, "Set The Table Number", Toast.LENGTH_SHORT).show();
                }else{
                    dialog.show();
                    new Task().execute();
                }
                break;

                case R.id.btnOut:
                    new android.support.v7.app.AlertDialog.Builder(Luncher.this)
                            .setTitle("Check Out").setMessage("Are You Sure To Check Out Now ?")
                            .setPositiveButton("Yes, Check Out Now", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    new TaskP().execute();

                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            return;
                        }
                    }).setIcon(R.drawable.ic_check_out).show();
                break;
        }
    }

    private class TaskP extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            getItemsToPrint();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            /*
            if(oStream == null){
                Toast.makeText(Luncher.this, "can't send your Order , Try Again Later", Toast.LENGTH_LONG).show();
                return;
            }
            */
            mySqliteHandler = new MySqliteHandler(Luncher.this);
            database = mySqliteHandler.getWritableDatabase();
            cursor = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_TABLE, null);
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                tableNumber = cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_TABLE_NUM));
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(MySqliteHandler.COLUMN_CHECK_OUT, "no");
            database.update(MySqliteHandler.TABEL_NAME_TABLE, contentValues,
                    MySqliteHandler.COLUMN_TABLE_NUM+" = ?",
                    new String[] {tableNumber});
            btnOut.setVisibility(View.GONE);
            Toast.makeText(Luncher.this, "CheckOut has been sent ....", Toast.LENGTH_LONG).show();

        }
    }

    private class Task extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent = new Intent(Luncher.this, MainActivity.class);
            startActivity(intent);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.cancel();
        }
    }

    public void showDialog()
    {

        LayoutInflater li = LayoutInflater.from(Luncher.this);
        View promptsView = li.inflate(R.layout.searchprompt, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Luncher.this);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput =  promptsView
                .findViewById(R.id.user_input);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Enter",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                String user_text = (userInput.getText()).toString();

                                if (user_text.equals("admin88"))
                                {
                                    Intent intent2 = new Intent(Luncher.this, ControlPanel.class);
                                    startActivity(intent2);

                                }
                                else{
                                    Log.d(user_text,"string is empty");
                                    String message = "The password you have entered is incorrect." + " \n \n" + "Please try again!";
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Luncher.this);
                                    builder.setTitle("Error");
                                    builder.setMessage(message);
                                    builder.setPositiveButton("Cancel", null);
                                    builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            showDialog();
                                        }
                                    });
                                    builder.create().show();

                                }
                            }
                        })
                .setPositiveButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.dismiss();
                            }
                        }
                );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    private void getItemsToPrint(){

        SQLiteDatabase database = mySqliteHandler.getReadableDatabase();
        cursor = database.rawQuery("SELECT * FROM "+MySqliteHandler.TABEL_NAME_TABLE, null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            tableNumber = "Table "+cursor.getString(cursor.getColumnIndex(MySqliteHandler.COLUMN_TABLE_NUM));
        }
/*
        try
        {

            Socket sock = new Socket();
            sock.connect(new InetSocketAddress(the device IP, the PORT), 1500);
            oStream =  sock.getOutputStream();

            if(oStream == null){
                Toast.makeText(Luncher.this, "Cant Send the Check Out Now ,Please Try Again Later", Toast.LENGTH_SHORT).show();
                return;
            }

            write your code of checkout here


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
}
