package com.example.finalprojectstackoverflowapp;

public final class MyDatabaseContract {
    public MyDatabaseContract(){}

    public static final String DB_NAME="db_name.db";

    public static abstract class searchHistoryTable{
        public static final String Table_Name="searchHistory";
        public static final String COL_SEARCH_ID="search_id";
        public static final String COL_SEARCH_WORD="search_word";
        public static final String COL_LOCATION="search_location";
    }

}
