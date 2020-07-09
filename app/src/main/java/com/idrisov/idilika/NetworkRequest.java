package com.idrisov.idilika;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRequest {
    private static NetworkRequest request;
    private String BASE_URL = "https://idillika.com/";
    private Retrofit retrofit;

    private NetworkRequest() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkRequest getRequest(){
        if (request == null){
            request = new NetworkRequest();
        }
            return request;
    }

    public TestAPI getTestApi(){
        return retrofit.create(TestAPI.class);
    }

    
}
