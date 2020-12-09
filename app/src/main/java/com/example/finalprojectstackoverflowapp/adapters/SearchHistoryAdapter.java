package com.example.finalprojectstackoverflowapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectstackoverflowapp.MyDatabaseContract;
import com.example.finalprojectstackoverflowapp.R;
import com.example.finalprojectstackoverflowapp.model.SearchWord;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.searchHistoryViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public  SearchHistoryAdapter (Context context, Cursor cursor){
        mContext=context;
        mCursor=cursor;
    }


    public class searchHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView searchId;
        private TextView searchWord;
        private WebView map;

        public searchHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            searchId=itemView.findViewById(R.id.search_id);
            searchWord=itemView.findViewById(R.id.search_word);
            map=itemView.findViewById(R.id.map);

            map.setWebViewClient(new WebViewClient());
            WebSettings webSettings=map.getSettings();
            webSettings.setJavaScriptEnabled(true);
        }
    }

    @NonNull
    @Override
    public searchHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View searchView=inflater.inflate(R.layout.search_word_card_view, parent, false);
        return new searchHistoryViewHolder(searchView);
    }

    @Override
    public void onBindViewHolder(@NonNull searchHistoryViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }

        int searchID=mCursor.getInt(mCursor.getColumnIndex(MyDatabaseContract.searchHistoryTable.COL_SEARCH_ID));
        String searchWord=mCursor.getString(mCursor.getColumnIndex(MyDatabaseContract.searchHistoryTable.COL_SEARCH_WORD));
        String locationStr=mCursor.getString(mCursor.getColumnIndex(MyDatabaseContract.searchHistoryTable.COL_LOCATION));

        holder.searchId.setText(mContext.getString(R.string.Search_ID)+" "+searchID);
        holder.searchWord.setText(mContext.getString(R.string.Search_word)+" "+searchWord);
        holder.map.loadUrl(locationStr);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
