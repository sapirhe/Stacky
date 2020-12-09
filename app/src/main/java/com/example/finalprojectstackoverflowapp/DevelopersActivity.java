package com.example.finalprojectstackoverflowapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

public class DevelopersActivity extends AppCompatActivity {

    VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        video=findViewById(R.id.stackoverflowvideo);

        String uriStr = "android.resource://" + getPackageName() + "/" + R.raw.stackoverflowvideo;//משיכת הקובץ מהתיקיה בה הוא יושב לתוך משתנה
        Uri uri = Uri.parse(uriStr);//המרה
        video.setVideoURI(uri);//הצגת הוידיאו באפליקציה

        video.setMediaController(new MediaController(this));//יצירת פקדים לשליטה לוידיאו
        video.requestFocus();//השמת פוקוס על הוידיאו
        video.start();
    }

    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences;

        sharedPreferences=getSharedPreferences(HomePageActivity.sharedName,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString(HomePageActivity.lastActivityKeyName,getClass().getName());
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
}
