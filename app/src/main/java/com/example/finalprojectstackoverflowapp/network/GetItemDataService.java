package com.example.finalprojectstackoverflowapp.network;

import com.example.finalprojectstackoverflowapp.model.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetItemDataService {
    @GET("search")
    Call<Results> getSearchResults(
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("site") String site,
            @Query("tagged") String tagged
    );
}
