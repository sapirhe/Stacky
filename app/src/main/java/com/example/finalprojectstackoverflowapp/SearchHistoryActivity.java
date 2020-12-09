package com.example.finalprojectstackoverflowapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalprojectstackoverflowapp.adapters.SearchHistoryAdapter;
import com.example.finalprojectstackoverflowapp.model.SearchWord;

import java.util.List;

public class SearchHistoryActivity extends AppCompatActivity {

    public static final String IDTODELETE = "id to delete";

    RecyclerView rvSearchesHistory;
    MyDataBaseHelper myDBHelper;
    SQLiteDatabase dbRead;
    EditText IdToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_history);

        myDBHelper=new MyDataBaseHelper(this);
        dbRead=myDBHelper.getReadableDatabase();
        rvSearchesHistory=findViewById(R.id.rvSearchHistory);
        IdToDelete=findViewById(R.id.idToDeleteET);

        String[] projection={MyDatabaseContract.searchHistoryTable.COL_SEARCH_ID,
                MyDatabaseContract.searchHistoryTable.COL_SEARCH_WORD,
                MyDatabaseContract.searchHistoryTable.COL_LOCATION};//משתנה שמציג את בחירת השדות שנרצה להציג

        //מיון הטבלה לפי עמודת ה-ID, ובאמצעות המילה desc. יש לשים לב לרווח לפני המילה דאסק
        String sortOrder=MyDatabaseContract.searchHistoryTable.COL_SEARCH_ID+ " DESC";

        //באמצעות שורות אלו אנו מציגים את כל הנתונים שבמסד נתונים שלנו

        //הצגת שורה בודדת באמצעות השמת מספר id בפקד באקטיבי
        Cursor cursor=dbRead.query(
                MyDatabaseContract.searchHistoryTable.Table_Name,
                projection,null,null,null,null,sortOrder
        );


        SearchHistoryAdapter adapter = new SearchHistoryAdapter(this, cursor);
        rvSearchesHistory.setAdapter(adapter);
        rvSearchesHistory.setLayoutManager(new LinearLayoutManager(SearchHistoryActivity.this));

        SharedPreferences sharedPreferences=getSharedPreferences(HomePageActivity.sharedName,0);

        String stateOfEditText = sharedPreferences.getString(IDTODELETE, "");
        IdToDelete.setText(stateOfEditText);


    }
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences;

        sharedPreferences=getSharedPreferences(HomePageActivity.sharedName,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString(IDTODELETE,IdToDelete.getText().toString());
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

    public void deleteSearchButton(View view) {
        String id=IdToDelete.getText().toString();

        String selection = MyDatabaseContract.searchHistoryTable.COL_SEARCH_ID+" "+" LIKE ?";
        String[] selecionArgs={String.valueOf(id)};

        dbRead.delete(MyDatabaseContract.searchHistoryTable.Table_Name,selection,selecionArgs);
        startActivity(getIntent());

    }
}
