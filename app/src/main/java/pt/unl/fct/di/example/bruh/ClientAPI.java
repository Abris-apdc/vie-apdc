package pt.unl.fct.di.example.bruh;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ClientAPI {
    private static ClientAPI instance;

    private RegisterService registerService;

    public static ClientAPI getInstance() {
        if (instance == null) {
            instance = new ClientAPI();
        }
        return instance;
    }

    public ClientAPI() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://amazing-office-313314.appspot.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        registerService = retrofit.create(RegisterService.class);
    }

    public RegisterService getRegisterService() {
        return registerService;
    }
}
