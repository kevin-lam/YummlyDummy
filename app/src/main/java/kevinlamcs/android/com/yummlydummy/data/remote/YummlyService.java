package kevinlamcs.android.com.yummlydummy.data.remote;

import java.util.List;
import java.util.Observable;

import io.reactivex.Single;
import kevinlamcs.android.com.yummlydummy.BuildConfig;
import kevinlamcs.android.com.yummlydummy.data.model.Recipe;
import kevinlamcs.android.com.yummlydummy.data.model.RecipeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface YummlyService {

    @GET("api/recipes")
    Single<RecipeResponse> searchRecipes(@Query("q") String query);
}
