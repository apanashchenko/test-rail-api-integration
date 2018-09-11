package com.test.rail.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Base64;

import static com.test.rail.api.conf.ConfigLoader.load;


/**
 * Created by alpa on 10/25/17
 */
public class RestClient {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    public static <T> T createService(Class<T> service) {
        return createService(service, load().testRailSchema(), load().testRailHost(),
                load().testRailUser(), load().testRailPassword());
    }

    public static <T> T createService(Class<T> service, String schema, String host, String user, String password) {
        return getBuilder(getBaseUrl(schema, host))
                .client(getClient(user, password).build())
                .build()
                .create(service);
    }

    private static Retrofit.Builder getBuilder(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
    }

    private static OkHttpClient.Builder getClient(String user, String password) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC));
        if (user != null && password != null) {
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                String url = original.url().toString().replaceAll("%3F", "?");
                final String credentials = user + ":" + password;
                String headerValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
                Request.Builder requestBuilder = original.newBuilder()
                        .header(AUTHORIZATION_HEADER, headerValue)
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            });
        }

        return httpClient;
    }


    private static String getBaseUrl(String schema, String host) {
       return String.format("%s://%s", schema, host);
    }

}
