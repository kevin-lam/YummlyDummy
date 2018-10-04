package kevinlamcs.android.com.yummlydummy.ui.main;

import android.Manifest;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import kevinlamcs.android.com.yummlydummy.BuildConfig;
import kevinlamcs.android.com.yummlydummy.R;
import kevinlamcs.android.com.yummlydummy.data.model.Recipe;
import kevinlamcs.android.com.yummlydummy.data.remote.YummlyService;
import kevinlamcs.android.com.yummlydummy.ui.recipe.RecipeListingAdapter;
import kevinlamcs.android.com.yummlydummy.util.PermissionManager;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    private RecipeListingAdapter recipeAdapter;
    private RecyclerView.LayoutManager recipeLayoutManager;
    private YummlyService recipeService;

    @BindView(R.id.recipe_listing) RecyclerView recipeListing;

    @BindView(R.id.search_bar) EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupRequestService();
        setupRecyclerView();
        setupSearch();
    }

    private void setupRecyclerView() {
        recipeAdapter = new RecipeListingAdapter(new ArrayList<Recipe>());
        recipeLayoutManager = new LinearLayoutManager(this);
        recipeListing.setLayoutManager(recipeLayoutManager);
        recipeListing.setAdapter(recipeAdapter);
    }

    private void setupSearch() {
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    requestSearchPermission();
                    return true;
                }
                return false;
            }
        });
    }

    private void requestSearchPermission() {
        PermissionManager.requestPermission(this, Manifest.permission.INTERNET, new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                searchRecipes();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        });
    }

    private void searchRecipes() {
        recipeService.searchRecipes(searchBar.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> recipeAdapter.setRecipes(response.getRecipes()),
                        throwable -> Log.e(this.getClass().getSimpleName(), throwable.getMessage()));
    }

    private void setupRequestService() {
        recipeService = new Retrofit.Builder()
                .baseUrl(BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(setupClient())
                .build()
                .create(YummlyService.class);
    }

    private OkHttpClient setupClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor())
                .addInterceptor(parameterInterceptor())
                .build();
    }

    private Interceptor loggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return loggingInterceptor;
    }

    private Interceptor parameterInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl originalUrl = originalRequest.url();
                HttpUrl newUrl = originalUrl.newBuilder()
                        .addQueryParameter("_app_id", BuildConfig.API_ID)
                        .addQueryParameter("_app_key", BuildConfig.API_KEY)
                        .build();
                Request newRequest = originalRequest.newBuilder().url(newUrl).build();
                return chain.proceed(newRequest);
            }
        };
    }

}
