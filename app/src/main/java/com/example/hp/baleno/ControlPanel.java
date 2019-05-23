package com.example.hp.baleno;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ControlPanel extends AppCompatActivity implements View.OnClickListener {

    EditText tableEdt;
    Button tableBtn;
    MySqliteHandler mySqliteHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        tableEdt = findViewById(R.id.tableEdt);
        tableBtn = findViewById(R.id.tableBtn);

        tableBtn.setOnClickListener(ControlPanel.this);
    }

    @Override
    public void onClick(View v) {
        String tableNum = tableEdt.getText().toString();
        if(tableNum == null){
            return;
        }
        mySqliteHandler = new MySqliteHandler(ControlPanel.this);
        SQLiteDatabase mDatabase = mySqliteHandler.getWritableDatabase();
        mDatabase.delete(MySqliteHandler.TABEL_NAME_TABLE, null, null);

        ContentValues contentValues = new ContentValues();
        contentValues.put(MySqliteHandler.COLUMN_TABLE_NUM, tableNum);
        contentValues.put(MySqliteHandler.COLUMN_CHECK_OUT, "no");
        mDatabase.insert(MySqliteHandler.TABEL_NAME_TABLE, null, contentValues);
        mDatabase.close();
        finish();
    }


}
