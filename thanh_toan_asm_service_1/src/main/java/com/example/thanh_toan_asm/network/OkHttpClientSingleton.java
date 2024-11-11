package com.example.thanh_toan_asm.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import java.util.Optional;

public class OkHttpClientSingleton {

    private static OkHttpClientSingleton instance;
    private OkHttpClient client;

    // Private constructor to prevent direct instantiation
    private OkHttpClientSingleton() {
        client = new OkHttpClient();
    }

    // Get the singleton instance
    public static synchronized OkHttpClientSingleton getInstance() {
        if (instance == null) {
            instance = new OkHttpClientSingleton();
        }
        return instance;
    }

    // Method for performing POST request
    public String post(String url, RequestBody body, String bearerToken) throws IOException {
        Request request;
        if(bearerToken!=null){
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + bearerToken)
                    .build();
        }else {
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
        }


        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
            }
            return response.body().string();  // Return the response body as a string
        }
    }
}
