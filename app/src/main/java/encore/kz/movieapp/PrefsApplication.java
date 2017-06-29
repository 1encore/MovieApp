package encore.kz.movieapp;

/**
 * Created by hg1zadr on 6/29/2017.
 */

import android.app.Application;
import android.content.ContextWrapper;
import android.util.Log;

import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class PrefsApplication extends Application {

    public static final String HOST = "http://api.themoviedb.org/3/";

    private static RestInterface restInterface;

    private Retrofit retrofit;

    private static PrefsApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", "b24b155781b11c6f1857ff445d1ee0ef").build();
                        request = request.newBuilder().url(url).build();
                        Log.d("test", url.toString());
                        return chain.proceed(request);
                    }
                }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(HOST) //Базовая часть адреса
                .addConverterFactory(JacksonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .client(client)
                .build();
        restInterface = retrofit.create(RestInterface.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public  RestInterface getApi() {
        return restInterface;
    }

    public PrefsApplication() {
        sInstance = this;
    }

    public static PrefsApplication get() {
        return sInstance;
    }
}