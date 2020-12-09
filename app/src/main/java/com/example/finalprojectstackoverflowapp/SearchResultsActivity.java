package com.example.finalprojectstackoverflowapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalprojectstackoverflowapp.adapters.ItemAdapter;
import com.example.finalprojectstackoverflowapp.model.Item;
import com.example.finalprojectstackoverflowapp.model.Owner;
import com.example.finalprojectstackoverflowapp.model.Results;
import com.example.finalprojectstackoverflowapp.network.GetItemDataService;
import com.example.finalprojectstackoverflowapp.network.RetrofitinInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity {

    private GetItemDataService itemService;
    List<Item> items;
    Owner owner;
    //TextView test;
    RecyclerView rvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        rvItems=findViewById(R.id.rvItems);
        //test=findViewById(R.id.test);
        SharedPreferences sharedPreferences=getSharedPreferences(HomePageActivity.sharedName,0);

        String searchWord="?";
        searchWord=sharedPreferences.getString(HomePageActivity.SEARCH_WORD,"");
        //test.setText(searchWord);

        itemService= RetrofitinInstance.getRetrofitInstance().create(GetItemDataService.class);
        itemService.getSearchResults("desc", "creation","stackoverflow",searchWord).enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Results results=response.body();
                items=results.getItems();

                for(Item i:items){
                    owner=i.getOwner();
                    Log.i("item title: " ,i.getTitle());
                }

                ItemAdapter adapter=new ItemAdapter(items);
                rvItems.setAdapter(adapter);
                rvItems.setLayoutManager(new LinearLayoutManager(SearchResultsActivity.this));
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {

            }
        });
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
