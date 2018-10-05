package kevinlamcs.android.com.yummlydummy.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kevinlamcs.android.com.yummlydummy.BuildConfig;
import kevinlamcs.android.com.yummlydummy.R;
import kevinlamcs.android.com.yummlydummy.data.remote.RecipeRepository;
import kevinlamcs.android.com.yummlydummy.data.remote.YummlyService;
import kevinlamcs.android.com.yummlydummy.ui.recipe.RecipeListingAdapter;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private RxPermissions permissions;
    private RecipeListingAdapter recipeAdapter;
    private RecyclerView.LayoutManager recipeLayoutManager;

    @BindView(R.id.recipe_listing)
    RecyclerView recipeListing;

    @BindView(R.id.search_bar)
    EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        permissions = new RxPermissions(this);

        setupAPIService();
        setupObservables();
        setupRecyclerView();
        setupSearch();
    }

    private void setupObservables() {
        viewModel.getRecipes().observe(this, recipes -> recipeAdapter.setRecipes(recipes));
    }

    private void setupRecyclerView() {
        recipeAdapter = new RecipeListingAdapter(new ArrayList<>());
        recipeLayoutManager = new LinearLayoutManager(this);
        recipeListing.setLayoutManager(recipeLayoutManager);
        recipeListing.setAdapter(recipeAdapter);
    }

    private void setupSearch() {
        RxTextView.editorActions(searchBar)
                .filter(actionId -> actionId == EditorInfo.IME_ACTION_DONE)
                .compose(permissions.ensure(Manifest.permission.INTERNET))
                .subscribe(granted -> {
                    viewModel.searchRecipes(searchBar.getText().toString());
                });
    }

    private void setupAPIService() {
        YummlyService yummlyService = new Retrofit.Builder()
                .baseUrl(BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(setupClient())
                .build()
                .create(YummlyService.class);
        RecipeRepository recipeRepository = new RecipeRepository(yummlyService);
        viewModel = new MainViewModel(recipeRepository);

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
