package com.example.hp.baleno;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySqliteHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "item_db";

    //Table Cart
    public static final String TABEL_NAME_CART = "cart";
    ////////////////////////////////////////////////////////////////
    public static final String TABEL_NAME_TABLE = "tableNum";

    // table columns
    public static final String COLUMN_ID_CART = "id";
    public static final String COLUMN_ITEM_NAMEE_CART = "itemNameE";
    public static final String COLUMN_ITEM_NAMEA_CART = "itemNameA";
    public static final String COLUMN_ITEM_ID = "itemID";
    public static final String COLUMN_ITEM_TYPE_CART = "itemType";
    public static final String COLUMN_ITEM_PRICE_CART = "itemPrice";
    public static final String COLUMN_ITEM_COUNT_CART = "count";
    public static final String COLUMN_ITEM_SIZE = "size";
    public static final String COLUMN_SUGAR_CART = "sugar";
    public static final String COLUMN_CAFE1_CART = "cafe1";
    public static final String COLUMN_CAFE2_CART = "cafe2";
    ////////////////////////////////////////////////////////////////
    public static final String COLUMN_TABLE_NUM = "tableNumber";
    public static final String COLUMN_CHECK_OUT = "checkOut";

    public MySqliteHandler (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_CART = "CREATE TABLE "+TABEL_NAME_CART+"( "+COLUMN_ID_CART+" INTEGER PRIMARY KEY, "+
                COLUMN_ITEM_NAMEE_CART+ " TEXT , "+
                COLUMN_ITEM_NAMEA_CART+ " TEXT , "+
                COLUMN_ITEM_ID+ " INTEGER , "+
                COLUMN_ITEM_TYPE_CART+ " TEXT, "+
                COLUMN_ITEM_PRICE_CART+ " DOUBLE, "+
                COLUMN_ITEM_COUNT_CART+ " INTEGER, "+
                COLUMN_ITEM_SIZE+ " TEXT, "+
                COLUMN_SUGAR_CART+ " TEXT, "+
                COLUMN_CAFE1_CART+ " TEXT, "+
                COLUMN_CAFE2_CART+ " TEXT)";

        String CREATE_TABLE_TABLE = "CREATE TABLE "+TABEL_NAME_TABLE+"( "+
                COLUMN_TABLE_NUM+ " TEXT , "+
                COLUMN_CHECK_OUT+ " TEXT)";



        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(CREATE_TABLE_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABEL_NAME_CART);

        onCreate(db);
    }

    /*
     //DATA_BASE operations (Create/insert - Read - Update - Delete)

    //1-Create
    public void addComputer(Computer computer){

        SQLiteDatabase database = MySqliteHandler.this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COMPUTER_NAME, computer.getComputerName());
        contentValues.put(COLUMN_COMPUTER_TYPE, computer.getComputerType());

        database.insert(TABEL_NAME, null, contentValues);
        database.close();
    }

    //2-Read only selected
    public Computer getComputer(int id){

        String[] columns = {COLUMN_ID, COLUMN_COMPUTER_NAME, COLUMN_COMPUTER_TYPE};
        SQLiteDatabase database = MySqliteHandler.this.getReadableDatabase();
        Cursor cursor = database.query(TABEL_NAME, columns, COLUMN_ID + "=?",new String[] {String.valueOf(id)},
                null, null, null );

        if(cursor != null){
            cursor.moveToFirst();
        }

        Computer computer = new Computer(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2));

        return computer;
    }

    //3-Read all the data
    public List<Computer> getAllComputers(){
        List<Computer> computersArray = new ArrayList<>();
        SQLiteDatabase database = MySqliteHandler.this.getReadableDatabase();
        String q = "SELECT * FROM "+TABEL_NAME;
        Cursor cursor = database.rawQuery(q, null);

        if(cursor.moveToFirst()){
            do{
                Computer computer = new Computer(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2));
                computersArray.add(computer);
            }while (cursor.moveToNext());

        }
        return computersArray;
    }

    //4- Update a Single full record Computer
    public  int UpdateComputer(Computer computer){

        SQLiteDatabase database = MySqliteHandler.this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPUTER_NAME, computer.getComputerName());
        values.put(COLUMN_COMPUTER_TYPE, computer.getComputerType());

        return  database.update(TABEL_NAME, values, COLUMN_ID+"=?",
                new String[] {String.valueOf(computer.getId())});
    }

    //5-Delete a single computer
    public void deleteComputer(Computer computer){
        SQLiteDatabase database = MySqliteHandler.this.getWritableDatabase();
        database.delete(TABEL_NAME, COLUMN_ID+"=?", new String[] {String.valueOf(computer.getId())});
        database.close();

    }

    //6-Get the Number Of Computers
    public int getComputersCount(){
        SQLiteDatabase database = MySqliteHandler.this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABEL_NAME, null);
        cursor.close();
        return cursor.getCount();

    }


   */

}
