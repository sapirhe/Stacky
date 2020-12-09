package com.example.finalprojectstackoverflowapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageActivity extends AppCompatActivity implements LocationListener {

    public static final String sharedName = "stackoverflow_app_save_data";
    public static final String lastActivityKeyName = "last activity that open";
    public static final String SEARCH_WORD = "search word";

    //כדי להכיל מסד נתונים באפליקציה שלנו ראשית נייצר מספר משתנים שנעזר בהם
    MyDataBaseHelper myDBHelper;//משתנה שיעזור לנו "לפרוש" את הדאטה בייס
    SQLiteDatabase dbRead;//באמצעותו נקרא את הDB
    SQLiteDatabase dbWrite;//באמצעותו נרשום לDB

    LocationManager locationManager;
    MediaPlayer sound;
    EditText searchWord;
    String loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchWord = findViewById(R.id.searchWord);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this,"Check your location permissions",Toast.LENGTH_LONG).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3 * 1000, 10, this);

        myDBHelper=new MyDataBaseHelper(this);//שורה זו מפעילה את כל המנגון של דאטה בייס אלפר שיצרנו (המחלקה שיצרנו)

        dbWrite=myDBHelper.getWritableDatabase();
        dbRead=myDBHelper.getReadableDatabase();

        SharedPreferences sharedPreferences=getSharedPreferences(sharedName,0);

        String stateOfEditText = sharedPreferences.getString(SEARCH_WORD, "");
        searchWord.setText(stateOfEditText);

        // באמצעות שורות אלו אנו מאפשרים למשתנים dbWrite ו-dbRead את האפשרות לרשום ולקרוא ל-db

    }

    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences;

        sharedPreferences=getSharedPreferences(sharedName,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString(SEARCH_WORD,searchWord.getText().toString());
        editor.putString(lastActivityKeyName,getClass().getName());
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuHomepage) {
            Intent i=new Intent(this,HomePageActivity.class);
            startActivity(i);
        }

        else if (id == R.id.menuSearch) {
            Intent i=new Intent(this,SearchResultsActivity.class);
            startActivity(i);
        }

        else if (id == R.id.menuHistorySearch) {
            Intent i=new Intent(this,SearchHistoryActivity.class);
            startActivity(i);
        }

        else if (id == R.id.menuDevelopers) {
            Intent i=new Intent(this,DevelopersActivity.class);
            startActivity(i);
        }

//        else if (id == R.id.exit) {
//            finish();
//        }
        return super.onOptionsItemSelected(item);
    }

    public void search(View view) {
        sound = MediaPlayer.create(this, R.raw.searchingsound);
        sound.start();

        Intent intentSearchResult=new Intent(this,SearchResultsActivity.class);

        SharedPreferences sharedPreferences;
        sharedPreferences=getSharedPreferences(sharedName,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString(SEARCH_WORD,searchWord.getText().toString());
        editor.commit();

        String search_word =searchWord.getText().toString();

        //Log.i("sqLite",newRowId+" "+" "+ search_word+" "+loc);//הצגת מספר שורה

        ContentValues values=new ContentValues();//נשתמש במשתנה זה על מנת שנוכל לשמור את הפרמטרים של השאילתה
        values.put(MyDatabaseContract.searchHistoryTable.COL_SEARCH_WORD,search_word);//השמה של הנתונים לתוך העמודה המתאימה
        values.put(MyDatabaseContract.searchHistoryTable.COL_LOCATION,loc);//""

        Long newRowId=dbWrite.insert(MyDatabaseContract.searchHistoryTable.Table_Name,null,values);//שורה זו מחזירה לנו מספר שורה מסוג long
        Log.i("sqLite",newRowId+"");//הצגת מספר שורה
        //addTest.setText(newRowId+" "+" "+ search_word+" "+loc);
        startActivity(intentSearchResult);
    }

    @Override
    public void onLocationChanged(Location location) {
        double longtitude=location.getLongitude();
        double latitude=location.getLatitude();

        loc="https://www.google.com/maps/place/"+latitude+","+longtitude;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
