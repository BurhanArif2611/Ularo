package com.pixelmarketo.ularo.utility;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfig {

    private static Retrofit retrofit;
    private static LoadInterface loadInterface;

        public static Retrofit getClient() {

        if (retrofit==null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static LoadInterface getLoadInterface(){

        if (loadInterface==null)
            loadInterface = AppConfig.getClient().create(LoadInterface.class);
        return loadInterface;
    }
}
