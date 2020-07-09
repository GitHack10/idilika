package com.idrisov.idilika;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TestAPI {
    @GET("api/catalogList.php?section=21&session_id=f3e82db3d0b2bcce07eae17dd9cb46d3")
    Call<ArrayList<Item>> getTestModel();
}
